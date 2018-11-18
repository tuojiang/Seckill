package com.chandler.seckill.service;

import com.chandler.seckill.dao.UserDao;
import com.chandler.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: Seckill
 * @Date: 2018/11/18
 * @Author: chandler
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }
}
