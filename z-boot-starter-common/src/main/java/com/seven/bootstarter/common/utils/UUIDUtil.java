package com.seven.bootstarter.common.utils;

import java.util.UUID;

/**
 * @author zhangxianwen
 * 2020/1/11 14:37
 **/
public class UUIDUtil {

    public static String getShortUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
}
