package com.ashin.wassup.gateway.filter;

import cn.hutool.jwt.JWTUtil;
import com.ashin.wassup.common.auth.constant.WebSecurityConstant;
import com.ashin.wassup.common.auth.util.JwtUtil;
import com.ashin.wassup.common.result.CommonResult;
import com.ashin.wassup.common.result.ResultCode;
import com.ashin.wassup.common.result.ResultUtil;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalGatewayFilter implements GlobalFilter, Ordered {

    private final static String WHITE_PATH = "/wassup-auth/auth/";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (request.getPath().toString().startsWith(WHITE_PATH)){
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(WebSecurityConstant.AUTH_HEADER);
        if (authHeader != null && authHeader.startsWith(WebSecurityConstant.AUTH_HEADER_TYPE)) {
            String token = authHeader.split(" ")[1];

            if (JwtUtil.legalCheck(token)) {
                String userName = (String) JWTUtil.parseToken(token).getPayload("userName");
                if (token.equals(redisTemplate.boundValueOps(userName).get())){
                    return chain.filter(exchange);
                }
            } else if (JwtUtil.refreshCheck(token)) {
                return ResultUtil.printCode(exchange.getResponse(), CommonResult.operateFailWithExpiredToken(), ResultCode.EXPIRED_TOKEN);
            }
        }

        return ResultUtil.printCode(exchange.getResponse(), CommonResult.operateFailWithUNAUTHORIZED(), ResultCode.UNAUTHORIZED);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
