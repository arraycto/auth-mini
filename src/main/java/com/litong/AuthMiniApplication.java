package com.litong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.litong.mapper")
@SpringBootApplication
public class AuthMiniApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthMiniApplication.class, args);
    }

}
