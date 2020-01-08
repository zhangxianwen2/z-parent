package com.seven.bootstarter.dynamicdatasource.provider;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/08 16:14
 **/
public interface DynamicDataSourceProvider {
    /**
     * 加载所有数据源
     *
     * @return 所有数据源，其中key为数据源名称
     */
    Map<String, DataSource> loadDataSources();
}
