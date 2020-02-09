package com.seven.bootstarter.switcher.provider;

import com.seven.bootstarter.switcher.properties.SwitcherMapProperties;
import com.seven.bootstarter.switcher.properties.SwitcherProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private SwitcherMapProperties switcherPropertiesMap;

    public SwitcherProperties getSwitcherProperties(String switcherId) {
        return switcherPropertiesMap.get(switcherId);
    }

    /**
     * 判断服务是否可用
     *
     * @return true可用  false不可用
     */
    public Boolean isEnable(String switcherId) {
        SwitcherProperties switcherProperties = getSwitcherProperties(switcherId);
        if (switcherProperties == null || switcherProperties.getEnable() == null) {
            // 当无相应配置时或未配置是否可用时 返回true
            return true;
        }
        return switcherProperties.getEnable();
    }

    /**
     * 是否属于服务不可用生效期间内
     * 本校验仅当配置服务不可用时有意义，用于指明服务不可用的时间区间
     * 当若配置了服务不可用，但是当前时间在配置区间外，则服务仍然可用
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
            log.warn("请检查配置文件，开关配置生效时间yyyy-MM-dd HH:mm:ss格式转换错误，将视为无配置！");
            return false;
        }
        return LocalDateTime.now().isBefore(endTime) && LocalDateTime.now().isAfter(startTime);
    }

    public static LocalDateTime stringToLocalDateTime(String str) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
    }
}
