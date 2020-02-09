package com.seven.bootstarter.switcher.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/21 15:58
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Switcher {
    /**
     * the switcher tag
     */
    String value() default "";
}
