package com.ashin.wassup.common.auth.constant;

/**
 * 网络安全常数
 *
 * @author ashinnotfound
 * @date 2023/05/06
 */
public class WebSecurityConstant {
    /**
     * 身份验证标头
     */
    public final static String AUTH_HEADER = "Authorization";
    /**
     * 身份验证标头类型
     */
    public final static String AUTH_HEADER_TYPE = "Bearer";
    /**
     * 到期时间(ms) 30min
     */
    public static final Integer EXPIRE_TIME = 30 * 60 * 1000;

    /**
     * 容忍时间
     * 在容忍时间内允许更新token
     */
    public static final Integer LEEWAY = 5 * 60;

    public static final String SIGN_KEY = "ayowassup";

    public static final String ISSUER = "ashinnotfound";
}
