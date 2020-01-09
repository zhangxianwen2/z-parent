package com.seven.bootstarter.dynamicdatasource.interceptor;

import com.seven.bootstarter.dynamicdatasource.DynamicDataSourceContextHolder;
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
import org.springframework.util.StringUtils;

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
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String dataSourceName = DynamicDataSourceContextHolder.getDataSourceName();
        if (StringUtils.isEmpty(dataSourceName)) {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            String resource = mappedStatement.getResource();
            String[] split = resource.split("/");
            // 目录结构猜测为：*/datasourceName/mapper/*Mapper.java
            String datasource = split[split.length - 3];
            log.warn("Choose {} as your datasource is the best guess,if the guess is wrong,check your project struct or manually specify data source", datasource);
            DynamicDataSourceContextHolder.setDataSourceKey(datasource);
        }
        try {
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSource();
        }
    }
}
