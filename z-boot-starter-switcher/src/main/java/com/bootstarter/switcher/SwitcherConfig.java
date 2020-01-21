package com.bootstarter.switcher;

import com.bootstarter.switcher.properties.SwitcherMapProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/21 16:28
 **/
@Configuration
@EnableConfigurationProperties(SwitcherMapProperties.class)
public class SwitcherConfig {
}
