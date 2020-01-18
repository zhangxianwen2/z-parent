package com.seven.bootstart.logger.a;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/17 22:40
 **/
public class PeopleServiceImpl implements AnimalService {
    @Override
    public People printName() {
        System.out.println("我是人类");
        return new People("章贤文");
    }
}
