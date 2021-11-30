package com.seven.cglib;

import net.sf.cglib.proxy.Dispatcher;

/**
 * @decs:
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/4/2 14:19
 **/
public class ConcreteClassDispatcher implements Dispatcher {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("before Dispatcher...");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("dispatcher");
        propertyBean.setValue("dispatcher");
        System.out.println("after Dispatcher...");
        return propertyBean;
    }
}
