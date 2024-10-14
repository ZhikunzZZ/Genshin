package com.gstool.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.gstool.common.dao"})
public class GsToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsToolApplication.class, args);
    }

}
