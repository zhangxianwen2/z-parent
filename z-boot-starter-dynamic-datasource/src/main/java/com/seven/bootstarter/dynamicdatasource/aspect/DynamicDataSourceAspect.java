package com.seven.bootstarter.dynamicdatasource.aspect;

import com.seven.bootstarter.dynamicdatasource.DynamicDataSourceContextHolder;
import com.seven.bootstarter.dynamicdatasource.annotation.SwitchDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

    @Around("@within(switchDataSource)||@annotation(switchDataSource)")
    public Object switchDataSource(ProceedingJoinPoint point, SwitchDataSource switchDataSource) throws Throwable {
        // 数据源注解在方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        SwitchDataSource annotation = method.getAnnotation(SwitchDataSource.class);
        // 数据源注解在类
        if (annotation == null) {
            annotation = point.getTarget().getClass().getAnnotation(SwitchDataSource.class);
            if (annotation == null) {
                log.warn("DataSource annotation is invalid，mybatis interceptor will decide it!");
                return point.proceed();
            }
        }
        final String value = annotation.value();
        if (!StringUtils.isEmpty(value)) {
            DynamicDataSourceContextHolder.setDataSourceKey(value);
        } else {
            log.warn("DataSource annotation value is blank，mybatis interceptor will decide it!");
        }
        try {
            return point.proceed();
        } finally {
            if (!StringUtils.isEmpty(value)) {
                DynamicDataSourceContextHolder.clearDataSource();
            }
        }
    }

}
