package com.seven.bootstarter.dynamicdatasource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 声明动态数据源
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/08 19:59
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface SwitchDataSource {
    /**
     * @return 数据源名称
     */
    String value();
}
