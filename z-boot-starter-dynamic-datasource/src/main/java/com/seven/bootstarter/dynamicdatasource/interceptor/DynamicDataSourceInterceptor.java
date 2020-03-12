package com.seven.bootstarter.dynamicdatasource.interceptor;

import com.seven.bootstarter.dynamicdatasource.DynamicDataSourceContextHolder;
import com.seven.bootstarter.dynamicdatasource.properties.DataSourceProperty;
import com.seven.bootstarter.dynamicdatasource.properties.DynamicDataSourceProperties;
import com.seven.bootstarter.dynamicdatasource.provider.DefaultDynamicDataSourceProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/09 17:02
 **/
@Slf4j
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
        }
)
public class DynamicDataSourceInterceptor implements Interceptor {
    /**
     * 动态数据源配置
     */
    private DefaultDynamicDataSourceProvider dataSourceProvider;

    public DynamicDataSourceInterceptor(DefaultDynamicDataSourceProvider properties) {
        this.dataSourceProvider = properties;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String dataSourceName = DynamicDataSourceContextHolder.getDataSourceName();
        if (StringUtils.isEmpty(dataSourceName)) {
            if (dataSourceProvider.loadDataSources().size() == 1) {
                // 如果只有一个数据源，就别瞎折腾了
                for (Map.Entry<String, DataSource> entry : dataSourceProvider.loadDataSources().entrySet()) {
                    DynamicDataSourceContextHolder.setDataSourceKey(entry.getKey());
                }
            } else {
                // TODO: 2020-2-23  当SQL在xml中时，该拦截器会被拦截两次  待测试拦截到xml文件时  数据源是否有效
                MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
                String resource = mappedStatement.getResource();
                if (resource.endsWith(".java")) {
                    String[] split = resource.split("/");
                    // 目录结构猜测为：*/datasourceName/mapper/*Mapper.java
                    String datasource = split[split.length - 3];
                    log.warn("Choose {} as your datasource is the best guess,if the guess is wrong,check your project struct or manually specify data source", datasource);
                    DynamicDataSourceContextHolder.setDataSourceKey(datasource);
                }
            }
        }
        try {
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSource();
        }
    }
}
