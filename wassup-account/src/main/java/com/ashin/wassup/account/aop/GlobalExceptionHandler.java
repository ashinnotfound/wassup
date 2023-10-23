package com.ashin.wassup.account.aop;

import com.ashin.wassup.account.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


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
    public CommonResult<Void> handleBindException(BindException ex) {
        log.error("BindException", ex);
        return CommonResult.operateFailWithMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResult<Void> handleException(Exception ex) {
        log.error("Exception", ex);
        return CommonResult.operateFailWithMessage("系统繁忙，请稍后再试！" + ex.getMessage());
    }
}
