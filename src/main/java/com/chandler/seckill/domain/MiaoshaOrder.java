package com.chandler.seckill.domain;

import lombok.Data;

/**
 * @program: Seckill
 * @Date: 2018/11/20
 * @Author: chandler
 * @Description:
 */
@Data
public class MiaoshaOrder {
    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;
}
