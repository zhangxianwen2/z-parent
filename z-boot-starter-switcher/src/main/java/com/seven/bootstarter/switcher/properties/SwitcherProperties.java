package com.seven.bootstarter.switcher.properties;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/21 16:12
 **/
@Data
public class SwitcherProperties {

    /**
     * 有效的apollo命名空间 最终获取的开关配置将在这些命名空间中获取
     * 多个使用逗号隔开
     * 默认application命名空间
     */
    private String nameSpace = "application";
    /**
     * 开关唯一Id，将被@Switcher注解定义
     */
    private String id;
    /**
     * 服务开启状态，默认true
     */
    private Boolean enable = true;
    /**
     * 服务关闭开始时间 yyyy-MM-dd HH:mm:ss
     */
    private String startTime;
    /**
     * 服务关闭结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String endTime;
}
