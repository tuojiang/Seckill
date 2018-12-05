package com.chandler.seckill.rabbitmq;

import com.chandler.seckill.domain.SeckillUser;
import lombok.Data;

/**
 * @Date: 18-12-4
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Data
public class MiaoshaMessage {
    private SeckillUser user;
    private long goodsId;
}
