package com.seven.dynamic.datasource.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/09 15:24
 **/
@SpringBootApplication(scanBasePackages = {"com.seven"})
@MapperScan("com.seven")
public class DatasourceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceApiApplication.class, args);
    }
}
