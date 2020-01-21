package com.seven.bootstart.logger.a;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/17 22:41
 **/
public class BirdServiceImpl implements AnimalService {
    @Override
    public People printName() {
        System.out.println("我是一只鸟");
        return new People("秦菊");
    }
}
