package com.chandler.seckill.dao;

import com.chandler.seckill.service.MiaoshaService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: Seckill
 * @Date: 2018/11/21
 * @Author: chandler
 * @Description:
 */
@Mapper
public interface OrderDao {

    @Select("select * from miaosha_order where user_id = #{userId} and goods_id = #{goodsId}")
    MiaoshaService getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId,@Param("goodsId") long goodsId);

}
