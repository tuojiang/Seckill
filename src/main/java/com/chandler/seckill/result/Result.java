package com.chandler.seckill.result;

import lombok.Data;


/**
 * @program: Seckill
 * @Date: 2018/11/17
 * @Author: chandler
 * @Description:
 */
@Data
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    /**
     * 成功时的调用
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 失败时候的调用
     * @param cm
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }

    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }
}
