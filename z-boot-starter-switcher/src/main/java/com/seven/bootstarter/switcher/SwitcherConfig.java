package com.seven.bootstarter.switcher;

import com.seven.bootstarter.switcher.aspect.SwitcherAspect;
import com.seven.bootstarter.switcher.properties.SwitcherMapProperties;
import com.seven.bootstarter.switcher.provider.SwitcherMonitor;
import com.seven.bootstarter.switcher.provider.SwitcherProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/21 16:28
 **/
@EnableConfigurationProperties(SwitcherMapProperties.class)
@Import({SwitcherAspect.class, SwitcherProvider.class, SwitcherMonitor.class})
public class SwitcherConfig {
}
