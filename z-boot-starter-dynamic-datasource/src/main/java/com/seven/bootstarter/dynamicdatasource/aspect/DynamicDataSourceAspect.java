package com.seven.bootstarter.dynamicdatasource.aspect;

import com.seven.bootstarter.dynamicdatasource.DynamicDataSourceContextHolder;
import com.seven.bootstarter.dynamicdatasource.annotation.SwitchDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/08 20:02
 **/
@Slf4j
@Aspect
public class DynamicDataSourceAspect {

    @Before("@annotation(switchDataSource)")
    public void switchDataSource(JoinPoint point,SwitchDataSource switchDataSource) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        final SwitchDataSource annotation = method.getAnnotation(SwitchDataSource.class);
        final String value = annotation.value();
        if (!StringUtils.isEmpty(value)) {
            DynamicDataSourceContextHolder.setDataSourceKey(value);
        } else {
            log.warn("data source is blank,it will not take effect");
        }
    }
}
