package com.ashin.wassup.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 前后端通讯标准
 *
 * @author ashinnotfound
 * @date 2023/01/29
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;

    private boolean isSuccess;

    private String message;

    private T data;

    public CommonResult(Integer code, boolean isSuccess, String message) {
        this.code = code;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public CommonResult(Integer code, boolean isSuccess, String message, T data) {
        this.code = code;
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static CommonResult<Void> operateFailWithMessage(String message) {
        return new CommonResult<>(ResultCode.FAIL_CODE, false, message);
    }

    public static CommonResult<Void> operateFailWithUNAUTHORIZED() {
        return new CommonResult<>(ResultCode.UNAUTHORIZED, false, "请先登录");
    }

    public static CommonResult<Void> operateFailWithExpiredToken() {
        return new CommonResult<>(ResultCode.EXPIRED_TOKEN, false, "token已过期, 请刷新或重新登陆");
    }

    public static CommonResult<Void> operateFailWithFORBIDDEN() {
        return new CommonResult<>(ResultCode.FORBIDDEN, false, "你没有访问的权限");
    }

    public static CommonResult<Void> operateSuccess(String message) {
        return new CommonResult<>(ResultCode.SUCCESS_CODE, true, message);
    }

    public static <P> CommonResult<P> operateSuccess(String message, P data) {
        return new CommonResult<>(
                ResultCode.SUCCESS_CODE,
                true,
                message,
                data
        );
    }
}
