package com.seven.bootstarter.dynamicdatasource.routing;

import com.seven.bootstarter.dynamicdatasource.DynamicDataSourceContextHolder;
import com.seven.bootstarter.dynamicdatasource.provider.DynamicDataSourceProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/08 15:48
 **/
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean {
    /**
     * 所有数据源
     */
    private Map<String, DataSource> allDataSource;
    /**
     * 默认数据源
     */
    private DataSource defaultDataSource;

    @Setter
    private DynamicDataSourceProvider provider;


    @Override
    public void afterPropertiesSet() {
        allDataSource = provider.loadDataSources();
        if (CollectionUtils.isEmpty(allDataSource)) {
            throw new IllegalArgumentException("Don't find any data source config!");
        }
        log.info("{} data sources were found", allDataSource.size());
        allDataSource.forEach((key, value) -> {
            if (defaultDataSource == null) {
                defaultDataSource = value;
            }
        });
    }

    @Override
    protected DataSource determineCurrentLookupKey() {
        String dataSourceName = DynamicDataSourceContextHolder.getDataSourceName();
        if (StringUtils.isEmpty(dataSourceName)) {
            return defaultDataSource;
        }
        return getDataSource(dataSourceName);
    }

    private DataSource getDataSource(String dataSourceName) {
        DataSource dataSource = allDataSource.get(dataSourceName);
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource name cant't be null!");
        }
        log.info("DataSource:{} is used!", dataSourceName);
        return dataSource;
    }
}
