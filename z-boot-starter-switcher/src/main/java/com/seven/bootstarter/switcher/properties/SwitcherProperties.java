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
     * 服务开启状态
     */
    private Boolean enable;
    /**
     * 服务关闭开始时间 yyyy-MM-dd HH:mm:ss
     */
    private String startTime;
    /**
     * 服务关闭结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String endTime;
}
