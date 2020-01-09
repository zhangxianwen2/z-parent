package com.seven.bootstarter.dynamicdatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidSpringAopConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidStatViewServletConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidWebStatFilterConfiguration;
import com.seven.bootstarter.dynamicdatasource.properties.DruidStatProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/08 23:35
 **/
@Configuration
@ConditionalOnClass(DruidDataSource.class)
@AutoConfigureBefore({DruidDataSourceAutoConfigure.class})
@EnableConfigurationProperties({DruidStatProperties.class})
@Import({
        DruidStatViewServletConfiguration.class,
        DruidSpringAopConfiguration.class,
        DruidWebStatFilterConfiguration.class})
public class DruidDynamicDataSourceConfiguration {
}
