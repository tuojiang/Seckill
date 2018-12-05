package com.chandler.seckill.redis;

/**
 * @Date: 18-12-5
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
public class MiaoshaKey extends BasePrefix {


    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60,"go");
}
