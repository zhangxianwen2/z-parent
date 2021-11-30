package com.seven.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @decs:
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/4/2 11:42
 **/
public class TargetInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用方法:" + method.getName() + "前");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("调用方法:" + method.getName() + "后" + " - " + result);
        return result;
    }
}
