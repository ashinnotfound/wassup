package com.ashin.wassup.gateway.filter;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.jwt.JWTUtil;
import com.ashin.wassup.common.auth.constant.RedisConstant;
import com.ashin.wassup.common.auth.constant.WebSecurityConstant;
import com.ashin.wassup.common.auth.util.JwtUtil;
import com.ashin.wassup.common.result.CommonResult;
import com.ashin.wassup.common.result.ResultCode;
import com.ashin.wassup.gateway.util.ResultUtil;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalGatewayFilter implements GlobalFilter, Ordered {

    private final static String WHITE_PATH = "/wassup-auth/auth/";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if ("OPTIONS".equals(request.getMethod().name())) {
            // 浏览器预检 直接放行
            return chain.filter(exchange);
        }

        if (request.getPath().toString().startsWith(WHITE_PATH)){
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(WebSecurityConstant.AUTH_HEADER);
        if (authHeader != null && authHeader.startsWith(WebSecurityConstant.AUTH_HEADER_TYPE)) {
            String token = authHeader.split(" ")[1];

            if (JwtUtil.legalCheck(token)) {
                int userId = ((NumberWithFormat)JWTUtil.parseToken(token).getPayload("userId")).intValue();
                if (token.equals(stringRedisTemplate.boundValueOps(RedisConstant.TOKEN_PREFIX + userId).get())){
                    exchange.getAttributes().put("userId", userId);
                    return chain.filter(exchange);
                }
            } else if (JwtUtil.refreshCheck(token)) {
                return ResultUtil.printCode(exchange.getResponse(), CommonResult.operateFailWithExpiredToken(), ResultCode.SUCCESS_CODE);
            }
        }

        return ResultUtil.printCode(exchange.getResponse(), CommonResult.operateFailWithUNAUTHORIZED(), ResultCode.SUCCESS_CODE);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
