package com.chandler.seckill.controller;

import com.chandler.seckill.domain.OrderInfo;
import com.chandler.seckill.domain.SeckillUser;
import com.chandler.seckill.redis.RedisService;
import com.chandler.seckill.result.CodeMsg;
import com.chandler.seckill.service.GoodsService;
import com.chandler.seckill.service.MiaoshaService;
import com.chandler.seckill.service.OrderService;
import com.chandler.seckill.service.SeckillUserService;
import com.chandler.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: Seckill
 * @Date: 2018/11/21
 * @Author: chandler
 * @Description:
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orederService;

    @Autowired
    SeckillUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String list(Model model, SeckillUser user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user",user);
        if (model == null) {
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock<=0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //是否秒杀完成
        MiaoshaService order = orederService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null) {
            model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存，下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
    }
}
