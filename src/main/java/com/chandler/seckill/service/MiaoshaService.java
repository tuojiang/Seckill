package com.chandler.seckill.service;

import com.chandler.seckill.domain.MiaoshaOrder;
import com.chandler.seckill.domain.OrderInfo;
import com.chandler.seckill.domain.SeckillUser;
import com.chandler.seckill.redis.MiaoshaKey;
import com.chandler.seckill.redis.RedisService;
import com.chandler.seckill.redis.SeckillUserKey;
import com.chandler.seckill.result.CodeMsg;
import com.chandler.seckill.util.MD5Util;
import com.chandler.seckill.util.UUIDUtil;
import com.chandler.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

/**
 * @program: Seckill
 * @Date: 2018/11/21
 * @Author: chandler
 * @Description:
 */
@Service
public class MiaoshaService {

    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(SeckillUser user, GoodsVo goods) {
        //减库存，下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if (success){
            return orderService.createOder(user,goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }

    public long getMiaoshaResult(Long userId, long goodId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId,goodId);
        if (order != null) {
            //秒杀成功
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodId);
            if (isOver){
                return -1;
            } else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId){
        redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,true);
    }

    private boolean getGoodsOver(long goodId) {
        return redisService.exists(MiaoshaKey.isGoodsOver,""+goodId);

    }

    public String getMiaoshaPath(SeckillUser user, long goodsId) {
        if (user == null || goodsId <=0){
            return null;
        }
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisService.set(MiaoshaKey.getMiaoshaPath,""+user.getId()+"_"+goodsId,str);
        return str;
    }

    public boolean checkPath(SeckillUser user,long goodsId, String path) {
        if (user == null || path == null){
            return false;
        }
        String pathOld = redisService.get(MiaoshaKey.getMiaoshaPath,""+user.getId()+"_"+goodsId,String.class);
        return path.equals(pathOld);
    }

    /**
     * 创建验证码
     * @param user
     * @param goodsId
     * @return
     */
    public BufferedImage createVerifyCode(SeckillUser user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, rnd);
        //输出图片
        return image;
    }

    private static int calc(String verifyCode) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            return (int) engine.eval(verifyCode);
        } catch (ScriptException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * + - *
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    public boolean checkVerifyCode(SeckillUser user, long goodsId, int verifyCode) {
        if (user == null || goodsId<=0) {
            return false;
        }
        Integer codeOld = redisService.get(MiaoshaKey.getMiaoshaVerifyCode,user.getId()+","+goodsId,Integer.class);
        if (codeOld == null || codeOld- verifyCode!=0) {
            return false;
        }
        redisService.delete(MiaoshaKey.getMiaoshaVerifyCode,user.getId()+","+goodsId);
        return true;
    }
}
