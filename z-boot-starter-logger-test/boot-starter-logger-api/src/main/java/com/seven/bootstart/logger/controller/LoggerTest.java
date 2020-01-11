package com.seven.bootstart.logger.controller;

import com.alibaba.fastjson.JSON;
import com.seven.bootstart.logger.utils.RestTemplateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxianwen
 * 2020/1/11 16:17
 **/
@Api(tags = "链路日志")
@RestController
@RequestMapping("/logger")
@Slf4j
public class LoggerTest {
    @Autowired
    private RestTemplateUtil restTemplateUtil;


    @ApiOperation(value = "HTTP请求链路日志测试")
    @PostMapping("/post")
    public void postTest(@RequestBody LogReq logReq) {
        log.info("请求体：{}", JSON.toJSONString(logReq));
        restTemplateUtil.post(logReq.getUrl(), JSON.toJSONString(logReq));
    }

}
