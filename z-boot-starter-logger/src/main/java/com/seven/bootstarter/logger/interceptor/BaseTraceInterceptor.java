package com.seven.bootstarter.logger.interceptor;

import java.util.List;

/**
 * @author zhangxianwen
 * 2020/1/11 15:07
 **/
public abstract class BaseTraceInterceptor {

    protected List<String> needTraceHeaders;

    public BaseTraceInterceptor(List<String> needTraceHeaders) {
        this.needTraceHeaders = needTraceHeaders;
    }
}
