package com.seven.bootstarter.dynamicdatasource;


import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/07 20:57
 **/
@Slf4j
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> LOOKUP_KEY_HOLDER = ThreadLocal.withInitial(String::new);

    /**
     * 设置数据源
     *
     * @param dataSourcesName 数据源名称
     */
    public static void setDataSourceKey(String dataSourcesName) {
        LOOKUP_KEY_HOLDER.set(dataSourcesName);
        log.info("datasource {} is push to deque;", dataSourcesName);
    }

    /**
     * 获取数据源
     *
     * @return 数据源名称
     */
    public static String getDataSourceName() {
        // 获取但是不删除队列中的第一个元素
        return LOOKUP_KEY_HOLDER.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        LOOKUP_KEY_HOLDER.remove();
        log.info("datasource pool is cleared");
    }
}
