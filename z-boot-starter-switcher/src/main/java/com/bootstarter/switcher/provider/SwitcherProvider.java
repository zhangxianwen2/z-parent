package com.bootstarter.switcher.provider;

import com.bootstarter.switcher.properties.SwitcherMapProperties;
import com.bootstarter.switcher.properties.SwitcherProperties;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/21 16:45
 **/
@Slf4j
public class SwitcherProvider {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private SwitcherMapProperties switcherPropertiesMap;

    public SwitcherProperties getSwitcherProperties(String switcherId) {
        return switcherPropertiesMap.get(switcherId);
    }

    /**
     * 判断服务是否可用
     *
     * @return true可用  false不可用
     */
    public boolean isEnable(String switcherId) {
        SwitcherProperties switcherProperties = getSwitcherProperties(switcherId);
        return switcherProperties.getEnable();
    }

    /**
     * 是否属于生效期间
     *
     * @return true生效  false未生效
     */
    public boolean isValid(String switcherId) {
        SwitcherProperties switcherProperties = getSwitcherProperties(switcherId);
        if (switcherProperties.getStartTime() == null || switcherProperties.getEndTime() == null) {
            return false;
        }
        final LocalDateTime endTime;
        final LocalDateTime startTime;
        try {
            startTime = stringToLocalDateTime(switcherProperties.getStartTime());
            endTime = stringToLocalDateTime(switcherProperties.getEndTime());
        } catch (Exception e) {
            log.warn("请检查配置文件，开关配置生效时间yyyy-MM-dd HH:mm:ss格式转换错误！");
            return false;
        }
        return LocalDateTime.now().isBefore(endTime) && LocalDateTime.now().isAfter(startTime);
    }

    private static LocalDateTime stringToLocalDateTime(String str) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
    }
}
