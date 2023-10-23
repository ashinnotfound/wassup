package com.ashin.wassup.account.service;

import com.ashin.wassup.account.entity.bo.LoginBO;
import com.ashin.wassup.account.entity.bo.RegisterBO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 账号服务
 *
 * @author ashinnotfound
 * @date 2023/10/22
 */
public interface AccountService {
    /**
     * 注册
     *
     * @param registerBO 注册bo
     */
    void register(RegisterBO registerBO);

    /**
     * 登录
     *
     * @param loginBO 登录bo
     * @return {@code String}
     */
    String login(LoginBO loginBO);

    /**
     * 刷新令牌
     *
     * @param httpServletRequest HTTP servlet 请求
     * @return {@code String}
     */
    String refreshToken(HttpServletRequest httpServletRequest);
}
