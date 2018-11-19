package com.chandler.seckill.vo;

import com.chandler.seckill.validator.IsMobile;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @program: Seckill
 * @Date: 2018/11/19
 * @Author: chandler
 * @Description:
 */
@Data
@ToString
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Length(min = 32)
    private String password;
}
