package com.chandler.seckill.service;

import com.chandler.seckill.domain.OrderInfo;
import com.chandler.seckill.domain.SeckillUser;
import com.chandler.seckill.redis.RedisService;
import com.chandler.seckill.redis.SeckillUserKey;
import com.chandler.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
        goodsService.reduceStock(goods);
        return orderService.createOder(user,goods);
    }

    /**
     * 获取Token信息
     * @param response
     * @param token
     * @return
     */
    public Object getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        SeckillUser user = redisService.get(SeckillUserKey.token,token,SeckillUser.class);
        //延长有效期
        if (user != null) {
            addCookie(response,token,user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
        redisService.set(SeckillUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
