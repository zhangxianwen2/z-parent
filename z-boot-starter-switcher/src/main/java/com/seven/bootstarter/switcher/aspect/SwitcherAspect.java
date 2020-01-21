package com.seven.bootstarter.switcher.aspect;

import com.seven.bootstarter.switcher.annotation.Switcher;
import com.seven.bootstarter.switcher.properties.SwitcherProperties;
import com.seven.bootstarter.switcher.provider.SwitcherProvider;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/21 15:57
 **/
@Slf4j
@Aspect
public class SwitcherAspect {

    private SwitcherProvider switcherProvider;

    @Around("@annotation(switcher)")
    public void switcher(JoinPoint point, Switcher switcher) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Switcher switcherAnnotation = method.getAnnotation(Switcher.class);
        final String value = switcherAnnotation.value();
        if (!isEnable(value) && isValid(value)) {
            // do something
            log.info("服务不可用");
        }
    }

    private boolean isEnable(String switcherId) {
        return switcherProvider.isEnable(switcherId);
    }

    private boolean isValid(String switcherId) {
        return switcherProvider.isValid(switcherId);
    }
}
