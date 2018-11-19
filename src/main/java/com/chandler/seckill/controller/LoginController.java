package com.chandler.seckill.controller;

import com.chandler.seckill.domain.SeckillUser;
import com.chandler.seckill.domain.User;
import com.chandler.seckill.result.Result;
import com.chandler.seckill.service.SeckillUserService;
import com.chandler.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @program: Seckill
 * @Date: 2018/11/19
 * @Author: chandler
 * @Description:
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    SeckillUserService seckillUserService;

    @RequestMapping("to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
        seckillUserService.login(response,loginVo);
        return Result.success(true);
    }
}
