package com.chandler.seckill.redis;

/**
 * @Date: 18-12-6
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
public class AccessKey extends BasePrefix {
    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }
}
