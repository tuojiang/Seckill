package com.chandler.seckill.domain;

import lombok.Data;

/**
 * @program: Seckill
 * @Date: 2018/11/20
 * @Author: chandler
 * @Description:
 */
@Data
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
