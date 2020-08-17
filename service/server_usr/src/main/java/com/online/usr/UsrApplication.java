package com.online.usr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.online.usr.mapper")
@ComponentScan("com.online")
public class UsrApplication {
    public static void main(String[] args){
        SpringApplication.run(UsrApplication.class,args);
    }
}
