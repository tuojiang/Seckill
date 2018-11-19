package com.chandler.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * @program: Seckill
 * @Date: 2018/11/19
 * @Author: chandler
 * @Description: UUID类
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
