package com.ashin.wassup.account.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 结果工具类
 *
 * @author ashinnotfound
 * @date 2023/05/20
 */
public class ResultUtil {
    public static <T> void printCode(HttpServletResponse response, CommonResult<T> result, Integer statusCode) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(statusCode);
        PrintWriter writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        writer.print(json);
        writer.close();
        response.flushBuffer();
    }
}
