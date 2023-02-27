package com.seven.bootstarter.dynamicdatasource.provider;

import com.alibaba.druid.pool.DruidDataSource;
import com.seven.bootstarter.dynamicdatasource.properties.DataSourceProperty;
import com.seven.bootstarter.dynamicdatasource.properties.DynamicDataSourceProperties;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.LinkedHashMap;
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
        // LinkedHashMap保证数据源在Map中的顺序与配置文件一致
        Map<String, DataSource> dataSourceMap = new LinkedHashMap<>(dataSourcePropertiesMap.size());
        for (Map.Entry<String, DataSourceProperty> entry : dataSourcePropertiesMap.entrySet()) {
            String dataSourceName = entry.getKey();
            DataSourceProperty dataSourceProperty = entry.getValue();
            dataSourceProperty.setDataSourceName(dataSourceName);
            dataSourceMap.put(dataSourceName, createDataSource(dataSourceProperty));
        }
        return dataSourceMap;
    }

    /**
     * 创建DruidDataSource为druid监控提供数据基础
     *
     * @param dataSourceProperty 数据源连接属性
     * @return DruidDataSource
     */
    private DruidDataSource createDataSource(DataSourceProperty dataSourceProperty) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());
        dataSource.setUrl(dataSourceProperty.getUrl());
        dataSource.setDriverClassName(dataSourceProperty.getDriverClassName());
        dataSource.setName(dataSourceProperty.getDataSourceName());
        //
        if (dataSourceProperty.getInitialSize() != null) {
            dataSource.setInitialSize(dataSourceProperty.getInitialSize());
        }
        if (dataSourceProperty.getMinIdle() != null) {
            dataSource.setMinIdle(dataSourceProperty.getMinIdle());
        }
        if (dataSourceProperty.getMaxActive() != null) {
            dataSource.setMaxActive(dataSourceProperty.getMaxActive());
        }
        if (dataSourceProperty.getTimeBetweenEvictionRunsMillis() != null) {
            dataSource.setTimeBetweenConnectErrorMillis(dataSourceProperty.getTimeBetweenEvictionRunsMillis());
        }
        if (dataSourceProperty.getMinEvictableIdleTimeMillis() != null) {
            dataSource.setMinEvictableIdleTimeMillis(dataSourceProperty.getMinEvictableIdleTimeMillis());
        }
        if (dataSourceProperty.getValidationQuery() != null) {
            dataSource.setValidationQuery(dataSourceProperty.getValidationQuery());
        }
        if (dataSourceProperty.getTestWhileIdle() != null) {
            dataSource.setTestWhileIdle(dataSourceProperty.getTestWhileIdle());
        }
        if (dataSourceProperty.getTestOnBorrow() != null) {
            dataSource.setTestOnBorrow(dataSourceProperty.getTestOnBorrow());
        }
        if (dataSourceProperty.getTestOnReturn() != null) {
            dataSource.setTestOnReturn(dataSourceProperty.getTestOnReturn());
        }
        if (dataSourceProperty.getPoolPreparedStatements() != null) {
            dataSource.setPoolPreparedStatements(dataSourceProperty.getPoolPreparedStatements());
        }
        if (dataSourceProperty.getMaxPoolPreparedStatementPerConnectionSize() != null) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceProperty.getMaxPoolPreparedStatementPerConnectionSize());
        }
        try {
            dataSource.setFilters(dataSourceProperty.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dataSourceProperty.getConnectionProperties() != null) {
            dataSource.setConnectProperties(dataSourceProperty.getConnectionProperties());
        }
        return dataSource;
    }

}
