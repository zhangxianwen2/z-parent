package com.seven.bootstarter.dynamicdatasource.autoconfig;


import com.seven.bootstarter.dynamicdatasource.aspect.DynamicDataSourceAspect;
import com.seven.bootstarter.dynamicdatasource.interceptor.DynamicDataSourceInterceptor;
import com.seven.bootstarter.dynamicdatasource.properties.DynamicDataSourceProperties;
import com.seven.bootstarter.dynamicdatasource.provider.DefaultDynamicDataSourceProvider;
import com.seven.bootstarter.dynamicdatasource.routing.AbstractRoutingDataSource;
import com.seven.bootstarter.dynamicdatasource.routing.DynamicRoutingDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

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

    @Bean
    @Primary
    @DependsOn("dynamicRoutingDataSource")
    public LazyConnectionDataSourceProxy dataSource(AbstractRoutingDataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicRoutingDataSource dynamicRoutingDataSource(DynamicDataSourceProperties properties) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setProvider(new DefaultDynamicDataSourceProvider(properties));
        return dynamicRoutingDataSource;
    }

    @Bean
    public DynamicDataSourceInterceptor dynamicDataSourceInterceptor() {
        return new DynamicDataSourceInterceptor();
    }
}
