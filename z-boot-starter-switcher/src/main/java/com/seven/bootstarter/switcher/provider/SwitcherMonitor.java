package com.seven.bootstarter.switcher.provider;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.seven.bootstarter.switcher.properties.SwitcherMapProperties;
import com.seven.bootstarter.switcher.properties.SwitcherProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zhangxianwen
 * 2020/2/9 21:26
 **/
@Slf4j
public class SwitcherMonitor {
    @Autowired
    private SwitcherMapProperties switcherPropertiesMap;

    /**
     * 开关所处的命名空间 默认：application
     */
    @Value("#{'${z-switch.namespace:application}'.split(',')}")
    private List<String> switchNamespaceList;

    @PostConstruct
    public void init() {
        switchNamespaceList.forEach(namespace -> {
            Config config = ConfigService.getConfig(namespace);
            config.addChangeListener(configChangeEvent -> {
                for (String changedKey : configChangeEvent.changedKeys()) {
                    if (changedKey.startsWith(SwitcherMapProperties.DEFAULT_SWITCHER_PREFIX + ".switcher")) {
                        // 当符合开关配置格式
                        ConfigChange change = configChangeEvent.getChange(changedKey);
                        log.info("开关调整:{}", JSON.toJSONString(change));
                        int switcherStartIndex = changedKey.indexOf(".", (SwitcherMapProperties.DEFAULT_SWITCHER_PREFIX + ".switcher").length());
                        int switcherEndIndex = changedKey.indexOf(".", switcherStartIndex + 1);
                        String switcherId = changedKey.substring(switcherStartIndex + 1, switcherEndIndex);
                        String switcherProperties = changedKey.substring(switcherEndIndex + 1);
                        //
                        if (switcherPropertiesMap.get(switcherId) == null) {
                            switcherPropertiesMap.add(switcherId, setSwitcherProperties(new SwitcherProperties(), switcherProperties, change.getNewValue(), switcherId));
                        } else {
                            switcherPropertiesMap.add(switcherId, setSwitcherProperties(switcherPropertiesMap.get(switcherId), switcherProperties, change.getNewValue(), switcherId));
                        }
                        // 当开关配置被清空时
                        if (switcherPropertiesMap.get(switcherId).equals(new SwitcherProperties())) {
                            switcherPropertiesMap.remove(switcherId);
                        }
                    }
                }
            });
        });
    }

    private SwitcherProperties setSwitcherProperties(SwitcherProperties switcherProperties, String properties, String value, String switcherId) {
        if (properties.equals("enable")) {
            try {
                boolean enable = Boolean.parseBoolean(value);
                switcherProperties.setEnable(value == null ? null : enable);
            } catch (Exception e) {
                log.warn("开关：{}配置错误，应当为Boolean类型，异常信息：", switcherId, e);
            }
        } else if (properties.equals("startTime")) {
            switcherProperties.setStartTime(value);
        } else if (properties.equals("endTime")) {
            switcherProperties.setEndTime(value);
        } else {
            log.warn("开关：{}配置失败，未定义的配置项：{}", switcherId, properties);
        }
        return switcherProperties;
    }
}
