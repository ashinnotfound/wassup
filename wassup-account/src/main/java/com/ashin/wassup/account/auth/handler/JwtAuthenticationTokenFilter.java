package com.ashin.wassup.account.auth.handler;

import cn.hutool.jwt.JWTUtil;
import com.ashin.wassup.account.auth.constant.WebSecurityConstant;
import com.ashin.wassup.account.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * JWT 身份验证令牌筛选器
 *
 * @author ashinnotfound
 * @date 2023/10/22
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(WebSecurityConstant.AUTH_HEADER);
        if (Objects.isNull(authHeader) || !authHeader.startsWith(WebSecurityConstant.AUTH_HEADER_TYPE)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.split(" ")[1];

        if (!JwtUtil.legalCheck(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String userName = (String) JWTUtil.parseToken(token).getPayload("userName");

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userName, null, null);


        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}