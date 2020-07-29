package com.online.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.online"})
public class EduApplication {
    public static void  main(String[] args){
        SpringApplication.run(EduApplication.class,args);
    }
}
