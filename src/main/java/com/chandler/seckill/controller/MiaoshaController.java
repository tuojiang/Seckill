package com.chandler.seckill.controller;

import com.chandler.seckill.access.AccessLimit;
import com.chandler.seckill.domain.MiaoshaOrder;
import com.chandler.seckill.domain.OrderInfo;
import com.chandler.seckill.domain.SeckillUser;
import com.chandler.seckill.rabbitmq.MQSender;
import com.chandler.seckill.rabbitmq.MiaoshaMessage;
import com.chandler.seckill.redis.GoodsKey;
import com.chandler.seckill.redis.MiaoshaKey;
import com.chandler.seckill.redis.OrderKey;
import com.chandler.seckill.redis.RedisService;
import com.chandler.seckill.result.CodeMsg;
import com.chandler.seckill.result.Result;
import com.chandler.seckill.service.GoodsService;
import com.chandler.seckill.service.MiaoshaService;
import com.chandler.seckill.service.OrderService;
import com.chandler.seckill.service.SeckillUserService;
import com.chandler.seckill.vo.GoodsVo;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @program: Seckill
 * @Date: 2018/11/21
 * @Author: chandler
 * @Description:
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MQSender sender;

    @AccessLimit(seconds = 5,maxCount = 5,needLogin = true)
    @RequestMapping(value = "/path",method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaPath(HttpServletRequest request, SeckillUser user,
                                         @RequestParam("goodsId")long goodsId,
                                         @RequestParam(value="verifyCode", defaultValue="0")int verifyCode){
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //验证verifyCode
        boolean check = miaoshaService.checkVerifyCode(user,goodsId,verifyCode);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String path = miaoshaService.getMiaoshaPath(user,goodsId);
        return Result.success(path);
    }

    @RequestMapping(value = "/{path}/do_miaosha",method= RequestMethod.POST)
    @ResponseBody
    public Result<Integer> list(Model model, SeckillUser user, @RequestParam("goodsId") long goodsId,
                                @PathVariable("path")String path) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //验证path
        boolean check = miaoshaService.checkPath(user,goodsId,path);
        if (!check){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        //内存标记减少Redis访问
        boolean over =localOverMap.get(goodsId);
        if (over){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //Redis预减库存，库存不足，直接返回，否则请求入队，立即返回排队中
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock,""+goodsId);
        if (stock<0){
            localOverMap.put(goodsId,true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //是否秒杀完成
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(mm);
        return Result.success(0);

        /*//判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //是否秒杀完成
        *//*MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }*//*
        //减库存，下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);*/
    }

    private HashMap<Long,Boolean> localOverMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goods.getId(),goods.getStockCount());
            localOverMap.put(goods.getId(),false);
        }
    }
    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsList);
        return Result.success(true);
    }

    /**
     *
     * @param model
     * @param user
     * @param goodId
     * @return orderId：成功,-1：秒杀失败,0： 排队中
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model,SeckillUser user,@RequestParam("goodsId")long goodId){
        model.addAttribute("user",user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(),goodId);
        return Result.success(result);
    }

    @RequestMapping(value="/verifyCode", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaVerifyCode(HttpServletResponse response,SeckillUser user,
                                               @RequestParam("goodsId")long goodsId){
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image = miaoshaService.createVerifyCode(user,goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image,"JPEG",out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
    }

}
