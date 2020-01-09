package com.seven.springcloud.platform;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/07 20:37
 **/
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class}, scanBasePackages = {"com.seven"})
public class PlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}
