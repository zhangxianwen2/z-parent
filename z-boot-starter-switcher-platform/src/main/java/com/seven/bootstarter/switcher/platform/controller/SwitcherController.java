package com.seven.bootstarter.switcher.platform.controller;

import com.seven.bootstarter.switcher.annotation.Switcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/21 17:53
 **/
@Slf4j
@RestController
@RequestMapping("/switch")
public class SwitcherController {

    @GetMapping("/test")
    @Switcher(value = "testSwitcher")
    public void testSwitcher() {
        log.info("开关未生效");
    }
}
