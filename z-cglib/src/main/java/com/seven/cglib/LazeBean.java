package com.seven.cglib;

import lombok.Data;
import net.sf.cglib.proxy.Enhancer;

/**
 * @decs:
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/4/2 12:01
 **/
@Data
public class LazeBean {
    private String name;
    private PropertyBean propertyBeanNormal;
    private PropertyBean propertyBean;
    private PropertyBean propertyBeanDispatcher;

    public LazeBean(String name) {
        this.name = name;
        this.propertyBeanNormal = new PropertyBean("normal","");
        this.propertyBean = createPropertyBean();
        this.propertyBeanDispatcher = createPropertyBeanDispatcher();
    }

    private PropertyBean createPropertyBean() {
        return (PropertyBean) Enhancer.create(PropertyBean.class, new ConcreteClassLazyLoader());
    }

    private PropertyBean createPropertyBeanDispatcher() {
        return (PropertyBean) Enhancer.create(PropertyBean.class, new ConcreteClassDispatcher());
    }


}
