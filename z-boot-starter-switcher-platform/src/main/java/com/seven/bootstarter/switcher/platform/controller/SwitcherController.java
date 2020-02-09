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
        log.info("testSwitcher开关未生效");
    }


    @GetMapping("/test2")
    @Switcher(value = "testSwitcher2")
    public void testSwitcher2() {
        log.info("testSwitcher2开关未生效");
    }


    @GetMapping("/test3")
    @Switcher(value = "testSwitcher3")
    public void testSwitcher3() {
        log.info("testSwitcher3开关未生效");
    }
}
