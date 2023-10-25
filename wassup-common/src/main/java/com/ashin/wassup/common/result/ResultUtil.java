package com.ashin.wassup.common.result;

import cn.hutool.json.JSONUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;

import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class ResultUtil {

    public static <T> Mono<Void> printCode(ServerHttpResponse response, CommonResult<T> result, Integer statusCode){
        response.setRawStatusCode(statusCode);
        response.getHeaders().add("Content-Type", "application/json");
        DataBuffer buffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
