package com.ashin.wassup.account.entity.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 注册博
 *
 * @author ashinnotfound
 * @date 2023/10/22
 */
@Data
public class RegisterBO {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(max = 20, message = "用户名长度限制为20个字符")
    String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    String password;
}
