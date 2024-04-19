package com.ashin.wassup.api.client;

import com.ashin.wassup.api.entity.UserInfoDTO;
import com.ashin.wassup.common.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("wassup-auth")
public interface AuthFeign{

    @GetMapping("/user/{id}")
    CommonResult<UserInfoDTO> getUserInfo(@PathVariable("id") Integer id);
}
