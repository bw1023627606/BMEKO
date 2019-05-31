package com.baizhang.bmeko.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
public class BmekoManageWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmekoManageWebApplication.class, args);
    }

}
