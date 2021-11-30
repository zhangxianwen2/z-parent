package com.seven.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @decs:
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/4/2 11:44
 **/
public class TestCglib {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObj.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObj targetObj = (TargetObj) enhancer.create();

        // 延迟加载 ↓↓
        LazeBean zhangxianwen = new LazeBean("zhangxianwen");
        System.out.println(zhangxianwen.getPropertyBean());
        System.out.println(zhangxianwen.getPropertyBeanDispatcher());
        System.out.println();
    }
}
