package com.chandler.seckill.service;

import com.chandler.seckill.dao.OrderDao;
import com.chandler.seckill.domain.OrderInfo;
import com.chandler.seckill.domain.SeckillUser;
import com.chandler.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: Seckill
 * @Date: 2018/11/21
 * @Author: chandler
 * @Description:
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public MiaoshaService getMiaoshaOrderByUserIdGoodsId(Long id, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(id,goodsId);
    }

}
