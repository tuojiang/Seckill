package com.chandler.seckill.vo;

import com.chandler.seckill.domain.Goods;
import com.chandler.seckill.domain.OrderInfo;
import lombok.Data;

/**
 * @Date: 18-11-26
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Data
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
}
