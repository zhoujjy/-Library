package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.dao")
@EnableTransactionManagement // 开启事务
@SpringBootApplication
public class BookServerPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServerPlusApplication.class, args);
    }

}
