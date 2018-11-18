package com.chandler.seckill.dao;

import com.chandler.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: Seckill
 * @Date: 2018/11/17
 * @Author: chandler
 * @Description:
 */
@Mapper
public interface UserDao {
    @Select("select * from user where id = #{id}")
    public User getById(@Param("id")int id);

    @Insert("insert into user(id,name) values(#{id},#{name})")
    public int insert(User user);
}
