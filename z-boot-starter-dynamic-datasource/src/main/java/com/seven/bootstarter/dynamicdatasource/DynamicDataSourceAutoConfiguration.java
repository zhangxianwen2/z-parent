package com.seven.bootstarter.dynamicdatasource;

import com.seven.bootstarter.dynamicdatasource.aspect.DynamicDataSourceAspect;
import com.seven.bootstarter.dynamicdatasource.properties.DynamicDataSourceProperties;
import com.seven.bootstarter.dynamicdatasource.provider.DefaultDynamicDataSourceProvider;
import com.seven.bootstarter.dynamicdatasource.routing.DynamicRoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/08 20:27
 **/
@Configuration
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@Import({DruidDynamicDataSourceConfiguration.class, DynamicDataSourceAspect.class})
public class DynamicDataSourceAutoConfiguration {
    @Autowired
    private DynamicDataSourceProperties properties;

    @Bean
    // @ConditionalOnMissingBean
    public DataSource dynamicRoutingDataSource() {
        System.out.println("123");
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setProvider(new DefaultDynamicDataSourceProvider(properties));
        return dynamicRoutingDataSource;
    }
}
