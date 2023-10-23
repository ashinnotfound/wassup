package com.ashin.wassup.account.entity.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录bo
 *
 * @author ashinnotfound
 * @date 2023/10/22
 */
@Data
public class LoginBO {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    String password;
}
