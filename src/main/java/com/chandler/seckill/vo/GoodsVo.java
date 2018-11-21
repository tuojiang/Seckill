package com.chandler.seckill.vo;

import com.chandler.seckill.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @program: Seckill
 * @Date: 2018/11/20
 * @Author: chandler
 * @Description:
 */
@Data
public class GoodsVo extends Goods {
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
