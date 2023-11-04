package com.ashin.wassup.account.controller;

import com.ashin.wassup.common.result.CommonResult;
import com.ashin.wassup.account.service.AuthService;
import com.ashin.wassup.account.entity.bo.LoginBO;
import com.ashin.wassup.account.entity.bo.RegisterBO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/register")
    public CommonResult<Void> register(@RequestBody @Validated RegisterBO registerBO) {
        authService.register(registerBO);
        return CommonResult.operateSuccess("注册成功");
    }

    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody @Validated LoginBO loginBO) {
        return CommonResult.operateSuccess("登陆成功", authService.login(loginBO));
    }

    @PostMapping("/refresh")
    public CommonResult<String> refresh(HttpServletRequest httpServletRequest) {
        return CommonResult.operateSuccess("刷新token成功", authService.refreshToken(httpServletRequest));
    }
}
