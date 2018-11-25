package com.chandler.seckill.controller;

import com.chandler.seckill.domain.OrderInfo;
import com.chandler.seckill.domain.SeckillUser;
import com.chandler.seckill.redis.RedisService;
import com.chandler.seckill.result.CodeMsg;
import com.chandler.seckill.result.Result;
import com.chandler.seckill.service.GoodsService;
import com.chandler.seckill.service.MiaoshaService;
import com.chandler.seckill.service.OrderService;
import com.chandler.seckill.service.SeckillUserService;
import com.chandler.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: Seckill
 * @Date: 2018/11/21
 * @Author: chandler
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result info(Model model, SeckillUser user ) {
        return Result.success(user);
    }
}
