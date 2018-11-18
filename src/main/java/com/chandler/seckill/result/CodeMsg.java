package com.chandler.seckill.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: Seckill
 * @Date: 2018/11/17
 * @Author: chandler
 * @Description:
 */
@Data
@AllArgsConstructor
public class CodeMsg {

    private int code;
    private String msg;

    //通用异常

    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");

}
