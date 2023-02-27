package com.seven.bootstarter.dynamicdatasource.properties;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Properties;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/07 21:51
 **/
@Data
@Accessors(chain = true)
public class DataSourceProperty {

    /**
     * 数据源名称，如果需要添加分组以-分割组名和数据源名。如slave-1
     */
    private String dataSourceName;

    /**
     * JDBC driver
     */
    private String driverClassName;

    /**
     * JDBC url
     */
    private String url;

    /**
     * JDBC 用户名
     */
    private String username;

    /**
     * JDBC 密码
     */
    private String password;

    /**
     *
     */
    private Integer initialSize;

    private Integer minIdle;

    private Integer maxActive;

    private Integer maxWait;

    private Integer timeBetweenEvictionRunsMillis;

    private Integer minEvictableIdleTimeMillis;

    private String validationQuery;

    private Boolean testWhileIdle;

    private Boolean testOnBorrow;

    private Boolean testOnReturn;

    private Boolean poolPreparedStatements;

    private Integer maxPoolPreparedStatementPerConnectionSize;

    private String filters;

    private Properties connectionProperties;

}
