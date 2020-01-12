package com.seven.bootstart.logger.controller;

import com.alibaba.fastjson.JSON;
import com.seven.bootstart.logger.async.LoggerTestAsync;
import com.seven.bootstart.logger.executor.BizThreadPoolTaskConfig;
import com.seven.bootstart.logger.model.LogReq;
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
public class LoggerTestController {
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private BizThreadPoolTaskConfig threadPoolTaskConfig;
    @Autowired
    private LoggerTestAsync loggerTestAsync;

    @ApiOperation(value = "HTTP请求链路日志测试")
    @PostMapping("/restTemplatePost")
    public void postTest(@RequestBody LogReq logReq) {
        log.info("{}", JSON.toJSONString(logReq));
        log.info("sdlkfhjlksdajflkdjsnfaiksfhjlisdjf");
        // restTemplateUtil.post("http://192.168.1.5:8001/logger/post", JSON.toJSONString(logReq));
    }

    @ApiOperation(value = "多线程链路日志测试")
    @PostMapping("/threadPost")
    public void threadTest(@RequestBody LogReq logReq) {
        log.info("{}", JSON.toJSONString(logReq));
        //
        threadPoolTaskConfig.bizTaskExecutor().execute(() -> {
            log.info("自定义线程池日志");
        });
        //
        threadPoolTaskConfig.bizTaskExecutor().execute(() -> {
            log.info("自定义线程池日志");
        });
        //
        threadPoolTaskConfig.bizTaskExecutor().execute(() -> {
            log.info("自定义线程池日志");
        });
        //
        loggerTestAsync.print();
        loggerTestAsync.print();
        loggerTestAsync.print();
    }

}
