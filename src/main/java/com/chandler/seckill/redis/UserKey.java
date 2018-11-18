package com.chandler.seckill.redis;

/**
 * @program: Seckill
 * @Date: 2018/11/17
 * @Author: chandler
 * @Description: 用户存储前缀类
 */
public class UserKey extends BasePrefix {
    public UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
