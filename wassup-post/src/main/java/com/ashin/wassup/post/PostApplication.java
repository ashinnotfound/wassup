package com.ashin.wassup.post;

import com.ashin.wassup.api.client.AuthFeign;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.ashin.wassup.common", "com.ashin.wassup.post"})
@MapperScan("com/ashin/wassup/post/mapper")
@EnableFeignClients(clients = AuthFeign.class)
public class PostApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }

}
