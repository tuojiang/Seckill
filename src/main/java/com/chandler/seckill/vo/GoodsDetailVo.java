package com.chandler.seckill.vo;

import com.chandler.seckill.domain.SeckillUser;
import lombok.Data;

/**
 * @program: Seckill
 * @Date: 2018/11/25
 * @Author: chandler
 * @Description:
 */
@Data
public class GoodsDetailVo {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    public GoodsVo goods;
    private SeckillUser user;
}
