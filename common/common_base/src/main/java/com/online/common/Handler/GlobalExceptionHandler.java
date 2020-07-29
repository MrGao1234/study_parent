package com.online.common.Handler;

import com.onlin.common.ExceptionUtils;
import com.onlin.common.ResultApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultApi error(Exception e){
        e.printStackTrace();
        return ResultApi.error().message("全局异常。。");
    }

    //特定异常处理
    @ExceptionHandler(MyExeption.class)
    @ResponseBody
    public ResultApi error(MyExeption e){
        log.error(ExceptionUtils.getExceptionStackTrace(e));
        e.printStackTrace();
        return ResultApi.error().message(e.getMsg()).code(e.getCode());
    }
}
