package com.seven.bootstarter.dynamicdatasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/08 11:11
 **/
@Data
@ConfigurationProperties(prefix = DynamicDataSourceProperties.DYNAMIC_DATA_SOURCE_PREFIX)
public class DynamicDataSourceProperties {

    // private DynamicDataSourceProperties() {
    // }

    public static final String DYNAMIC_DATA_SOURCE_PREFIX = "spring.datasource.dynamic";
    /**
     * 配置所有数据源
     */
    private Map<String, DataSourceProperty> datasource = new LinkedHashMap<>();

}
