package com.seven.bootstarter.dynamicdatasource.routing;

import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/09 11:29
 **/
public abstract class AbstractRoutingDataSource extends AbstractDataSource {

    // 确定当前要使用的数据源
    protected abstract DataSource determineCurrentLookupKey();

    @Override
    public Connection getConnection() throws SQLException {
        return determineCurrentLookupKey().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineCurrentLookupKey().getConnection(username, password);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineCurrentLookupKey().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineCurrentLookupKey().isWrapperFor(iface));
    }
}
