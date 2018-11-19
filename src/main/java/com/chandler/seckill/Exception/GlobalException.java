package com.chandler.seckill.Exception;

import com.chandler.seckill.result.CodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: Seckill
 * @Date: 2018/11/19
 * @Author: chandler
 * @Description:
 */
@Data
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }
}

