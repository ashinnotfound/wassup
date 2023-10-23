package com.ashin.wassup.account.auth.util;

import cn.hutool.json.JSONException;
import cn.hutool.jwt.JWT;
import com.ashin.wassup.account.auth.constant.WebSecurityConstant;

import java.util.Date;

/**
 * JWT工具类
 *
 * @author ashinnotfound
 * @date 2023/10/23
 */
public class JwtUtil {

    /**
     * 创建令牌
     *
     * @param userName 用户名
     * @return {@code String}
     */
    public static String createToken(String userName) {
        Date date = new Date();
        return JWT.create()
                .setPayload("userName", userName)
                .setKey(WebSecurityConstant.SIGN_KEY.getBytes())
                .setIssuer(WebSecurityConstant.ISSUER)
                .setIssuedAt(date)
                .setNotBefore(date)
                .setExpiresAt(new Date(date.getTime() + WebSecurityConstant.EXPIRE_TIME))
                .sign();
    }

    /**
     * 检查token是否合法
     *
     * @param token 令 牌
     * @return boolean
     */
    public static boolean legalCheck(String token) {
        try {
            return token != null && JWT.of(token).setKey(WebSecurityConstant.SIGN_KEY.getBytes()).validate(0);
        }catch (JSONException e){
            return false;
        }
    }

    /**
     * 检查是否允许刷新token
     *
     * @param token 令 牌
     * @return boolean
     */
    public static boolean refreshCheck(String token) {
        try {
            return token != null && JWT.of(token).setKey(WebSecurityConstant.SIGN_KEY.getBytes()).validate(WebSecurityConstant.LEEWAY);
        }catch (JSONException e){
            return false;
        }
    }
}
