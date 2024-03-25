package com.ashin.wassup.common.aop;

import com.ashin.wassup.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;


/**
 * 全局异常处理
 *
 * @author lql
 * @date 2023/02/12
 */
@ControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResult<Void> handlerIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException", ex);
        return CommonResult.operateFailWithMessage(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public CommonResult<Void> handleBindException(BindException ex){
        String errorMessage = "非法的参数: " + Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        log.error(errorMessage);
        return CommonResult.operateFailWithMessage(errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        log.error("非法的参数", ex);
        return CommonResult.operateFailWithMessage("非法的参数: " + ex.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResult<Void> handleException(Exception ex) {
        log.error("Exception", ex);
        return CommonResult.operateFailWithMessage("系统繁忙，请稍后再试！" + ex.getMessage());
    }
}
