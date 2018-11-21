package com.chandler.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * @program: Seckill
 * @Date: 2018/11/20
 * @Author: chandler
 * @Description:
 */
@Data
public class MiaoshaGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
