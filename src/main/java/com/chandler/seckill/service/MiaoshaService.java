package com.chandler.seckill.service;

import com.chandler.seckill.domain.OrderInfo;
import com.chandler.seckill.domain.SeckillUser;
import com.chandler.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: Seckill
 * @Date: 2018/11/21
 * @Author: chandler
 * @Description:
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(SeckillUser user, GoodsVo goods) {
        //减库存，下订单 写入秒杀订单
        goodsService.reduceStock(goods);
        return orderService.createOder();
    }
}
