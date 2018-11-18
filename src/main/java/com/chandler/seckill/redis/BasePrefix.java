package com.chandler.seckill.redis;

import lombok.AllArgsConstructor;

/**
 * @program: Seckill
 * @Date: 2018/11/17
 * @Author: chandler
 * @Description: 基础接口类
 */
@AllArgsConstructor
public abstract class BasePrefix implements KeyPrefix{
    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix) {
        //0代表永不过期
        this(0,prefix);
    }

    @Override
    public int expireSeconds(){
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }
}
