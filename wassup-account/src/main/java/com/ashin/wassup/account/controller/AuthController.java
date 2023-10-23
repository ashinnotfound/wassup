package com.ashin.wassup.account.controller;

import com.ashin.wassup.account.result.CommonResult;
import com.ashin.wassup.account.service.AccountService;
import com.ashin.wassup.account.entity.bo.LoginBO;
import com.ashin.wassup.account.entity.bo.RegisterBO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@ResponseBody
public class AuthController {

    @Resource
    private AccountService accountService;

    @PostMapping("/register")
    public CommonResult<Void> register(@RequestBody @Validated RegisterBO registerBO) {
        accountService.register(registerBO);
        return CommonResult.operateSuccess("注册成功");
    }

    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody @Validated LoginBO loginBO) {
        return CommonResult.operateSuccess("登陆成功", accountService.login(loginBO));
    }

    @PostMapping("/refresh")
    public CommonResult<String> refresh(HttpServletRequest httpServletRequest) {
        return CommonResult.operateSuccess("刷新token成功", accountService.refreshToken(httpServletRequest));
    }
}
