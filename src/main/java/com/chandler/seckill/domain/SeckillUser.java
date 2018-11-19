package com.chandler.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * @program: Seckill
 * @Date: 2018/11/19
 * @Author: chandler
 * @Description:
 */
@Data
public class SeckillUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
