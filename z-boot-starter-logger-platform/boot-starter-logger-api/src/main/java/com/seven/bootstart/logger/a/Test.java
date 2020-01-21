package com.seven.bootstart.logger.a;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/17 22:41
 **/
public class Test {
    public static void main(String[] args) {
        AnimalService animalService = new PeopleServiceImpl();
        System.out.println(animalService.printName());

        AnimalService animalService2 = new BirdServiceImpl();
        System.out.println(animalService2.printName());
    }
}
