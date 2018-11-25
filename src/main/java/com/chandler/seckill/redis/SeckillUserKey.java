package com.chandler.seckill.redis;

/**
 * @program: Seckill
 * @Date: 2018/11/17
 * @Author: chandler
 * @Description: 用户存储前缀类
 */
public class SeckillUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24*2;

    public SeckillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static SeckillUserKey token = new SeckillUserKey(TOKEN_EXPIRE,"tk");
    public static SeckillUserKey getById = new SeckillUserKey(0,"id");
}
