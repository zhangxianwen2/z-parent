package com.seven.bootstarter.dynamicdatasource.autoconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.seven.bootstarter.dynamicdatasource.autoconfig.stat.DruidSpringAopConfiguration;
import com.seven.bootstarter.dynamicdatasource.autoconfig.stat.DruidStatViewServletConfiguration;
import com.seven.bootstarter.dynamicdatasource.autoconfig.stat.DruidWebStatFilterConfiguration;
import com.seven.bootstarter.dynamicdatasource.properties.DruidStatProperties;
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
@EnableConfigurationProperties({DruidStatProperties.class})
@Import({
        DruidStatViewServletConfiguration.class,
        DruidSpringAopConfiguration.class,
        DruidWebStatFilterConfiguration.class})
public class DruidDynamicDataSourceConfiguration {
}
