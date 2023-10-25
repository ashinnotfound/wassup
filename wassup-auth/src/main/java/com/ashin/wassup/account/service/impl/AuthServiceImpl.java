package com.ashin.wassup.account.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.jwt.JWTUtil;
import com.ashin.wassup.account.entity.bo.LoginBO;
import com.ashin.wassup.account.entity.bo.RegisterBO;
import com.ashin.wassup.account.entity.po.Account;
import com.ashin.wassup.account.mapper.AccountMapper;
import com.ashin.wassup.account.service.AuthService;
import com.ashin.wassup.common.auth.constant.WebSecurityConstant;
import com.ashin.wassup.common.auth.util.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.time.Duration;

/**
 * 账户服务
 *
 * @author ashinnotfound
 * @date 2023/10/22
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private AccountMapper accountMapper;

    @Override
    public void register(RegisterBO registerBO) {
        Assert.isNull(accountMapper.selectOne(new QueryWrapper<Account>().eq("user_name", registerBO.getUserName())), "注册失败：用户名已存在");

        Account account = new Account();
        account.setUserName(registerBO.getUserName());
        account.setPassword(DigestUtil.bcrypt(registerBO.getPassword() + WebSecurityConstant.SIGN_KEY));

        Assert.isTrue(accountMapper.insert(account) == 1, "注册失败：数据库异常");
    }

    @Override
    public String login(LoginBO loginBO) {
        Account account = accountMapper.selectOne(new QueryWrapper<Account>().eq("user_name", loginBO.getUserName()));
        Assert.notNull(account, "登陆失败：用户不存在");
        Assert.isTrue(DigestUtil.bcryptCheck(loginBO.getPassword() + WebSecurityConstant.SIGN_KEY, account.getPassword()), "登录失败：密码错误");

        String token = redisTemplate.boundValueOps(loginBO.getUserName()).get();

        // 已经登录过
        if (JwtUtil.legalCheck(token)){
            return token;
        }

        // 生成新token
        token = JwtUtil.createToken(loginBO.getUserName());
        redisTemplate.boundValueOps(loginBO.getUserName()).set(token, Duration.ofMillis(WebSecurityConstant.EXPIRE_TIME + WebSecurityConstant.LEEWAY * 1000));

        return token;
    }

    @Override
    public String refreshToken(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader(WebSecurityConstant.AUTH_HEADER);
        Assert.isTrue(authHeader != null && authHeader.startsWith(WebSecurityConstant.AUTH_HEADER_TYPE), "刷新token失败：非法的token, 请重新登录");

        String token = authHeader.split(" ")[1];

        Assert.isTrue(JwtUtil.refreshCheck(token), "刷新token失败：非法的token, 请重新登录");

        String userName = (String) JWTUtil.parseToken(token).getPayload("userName");
        Assert.isTrue(token.equals(redisTemplate.boundValueOps(userName).get()), "刷新token失败：非法的token, 请重新登录");

        token = JwtUtil.createToken(userName);
        redisTemplate.boundValueOps(userName).set(token, Duration.ofMillis(WebSecurityConstant.EXPIRE_TIME + WebSecurityConstant.LEEWAY * 1000));

        return token;
    }
}
