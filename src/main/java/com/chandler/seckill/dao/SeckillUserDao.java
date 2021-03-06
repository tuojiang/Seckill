package com.chandler.seckill.dao;

import com.chandler.seckill.domain.SeckillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @program: Seckill
 * @Date: 2018/11/19
 * @Author: chandler
 * @Description:
 */
@Mapper
public interface SeckillUserDao {

    @Select("select * from miaosha_user where id = #{id}")
    public SeckillUser getById(@Param("id")Long id);

    @Update("update miaosha_user set password = #{password} where id = #{id}")
    void update(SeckillUser toUpdate);
}
