package com.bootstarter.switcher.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/21 16:32
 **/
@Data
@ConfigurationProperties(prefix = SwitcherMapProperties.DEFAULT_SWITCHER_PREFIX)
public class SwitcherMapProperties {
    public static final String DEFAULT_SWITCHER_PREFIX = "z-switcher";
    private static final String DEFAULT_SWITCHER_SEPARATOR = ".";

    private Map<String, SwitcherProperties> switcherPropertiesMap = new HashMap<>();

    /**
     * 新增开关
     *
     * @param switcherId switcherId
     * @param properties SwitcherProperties
     */
    public void add(String switcherId, SwitcherProperties properties) {
        switcherPropertiesMap.put(switcherId, properties);
    }

    /**
     * 获取开关
     *
     * @param key switcherId
     * @return SwitcherProperties
     */
    public SwitcherProperties get(String key) {
        SwitcherProperties switcherProperties = switcherPropertiesMap.get(key);
        return switcherProperties == null ? new SwitcherProperties() : switcherProperties;
    }


    /**
     * 移除开关
     *
     * @param key switcherId
     */
    public void remove(String key) {
        switcherPropertiesMap.remove(key);
    }
}
