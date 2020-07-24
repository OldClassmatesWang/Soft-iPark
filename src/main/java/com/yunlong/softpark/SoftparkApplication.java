package com.yunlong.softpark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yunlong.softpark.mapper")
@SpringBootApplication
public class SoftparkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftparkApplication.class, args);
    }

}
