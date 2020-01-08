package com.seven.bootstarter.dynamicdatasource.provider;

import com.alibaba.druid.pool.DruidDataSource;
import com.seven.bootstarter.dynamicdatasource.properties.DataSourceProperty;
import com.seven.bootstarter.dynamicdatasource.properties.DynamicDataSourceProperties;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/08 16:14
 **/
public class DefaultDynamicDataSourceProvider implements DynamicDataSourceProvider {
    /**
     * 动态数据源配置
     */
    private DynamicDataSourceProperties properties;

    public DefaultDynamicDataSourceProvider(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSourceProperty> dataSourcePropertiesMap = properties.getDatasource();
        Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size());
        for (Map.Entry<String, DataSourceProperty> entry : dataSourcePropertiesMap.entrySet()) {
            String dataSourceName = entry.getKey();
            DataSourceProperty dataSourceProperty = entry.getValue();
            dataSourceProperty.setDataSourceName(dataSourceName);
            dataSourceMap.put(dataSourceName, createDataSource(dataSourceProperty));
        }
        return dataSourceMap;
    }

    private DataSource createDataSource(DataSourceProperty dataSourceProperty) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());
        dataSource.setUrl(dataSourceProperty.getUrl());
        dataSource.setDriverClassName(dataSourceProperty.getDriverClassName());
        dataSource.setName(dataSourceProperty.getDataSourceName());
        return dataSource;
    }
}
