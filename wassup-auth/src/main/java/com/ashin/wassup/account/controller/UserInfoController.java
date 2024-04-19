package com.ashin.wassup.account.controller;

import com.ashin.wassup.account.entity.dto.UserInfoDTO;
import com.ashin.wassup.account.service.UserInfoService;
import com.ashin.wassup.common.result.CommonResult;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/{id}")
    public CommonResult<UserInfoDTO> getUserInfo(@PathVariable("id") @NotNull Integer id) {
        return CommonResult.operateSuccess("获取id为" + id + "的用户信息成功", userInfoService.getUserInfo(id));
    }
}
