package com.seven.bootstart.logger.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author zhangxianwen
 * 2020/1/11 16:02
 **/
@Component
public class RestTemplateUtil {
    @Autowired
    private RestTemplate restTemplate;

    public void post(String url, String body) {
        URI uri = URI.create(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置contentType
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<String>(body, httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        System.out.println(true);
    }
}
