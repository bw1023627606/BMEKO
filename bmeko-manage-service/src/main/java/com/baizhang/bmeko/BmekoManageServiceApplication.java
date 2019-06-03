package com.baizhang.bmeko;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.baizhang.bmeko.manage.mapper")
public class BmekoManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmekoManageServiceApplication.class, args);
    }

}
