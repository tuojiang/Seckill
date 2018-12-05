package com.chandler.seckill.redis;

/**
 * @Date: 18-12-5
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
public class MiaoshaKey extends BasePrefix {
    public MiaoshaKey(String prefix) {
        super(prefix);
    }
    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
