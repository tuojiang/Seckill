package com.chandler.seckill.redis;

/**
 * @program: Seckill
 * @Date: 2018/11/17
 * @Author: chandler
 * @Description: 前缀接口类
 */
public interface KeyPrefix {
    public int expireSeconds();
    public String getPrefix();
}
