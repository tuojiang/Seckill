package com.chandler.seckill.redis;

/**
 * @program: Seckill
 * @Date: 2018/11/17
 * @Author: chandler
 * @Description: 用户存储前缀类
 */
public class GoodsKey extends BasePrefix {

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsList1 = new GoodsKey(60,"gd");
}
