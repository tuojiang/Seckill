package com.chandler.seckill.redis;

/**
 * @program: Seckill
 * @Date: 2018/11/17
 * @Author: chandler
 * @Description:
 */
public class OrderKey extends BasePrefix {
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
