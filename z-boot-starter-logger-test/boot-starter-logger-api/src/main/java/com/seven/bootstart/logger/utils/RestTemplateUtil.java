package com.seven.bootstart.logger.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangxianwen
 * 2020/1/11 16:02
 **/
@Component
public class RestTemplateUtil {
    @Autowired
    private RestTemplate restTemplate;

    public void post() {
        // restTemplate.postForEntity("");
    }
}
