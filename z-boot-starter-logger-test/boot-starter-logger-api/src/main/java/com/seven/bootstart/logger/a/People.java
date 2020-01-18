package com.seven.bootstart.logger.a;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/17 22:43
 **/
@Data
public class People {
    private String name;

    public People(String name) {
        this.name = name;
    }
}
