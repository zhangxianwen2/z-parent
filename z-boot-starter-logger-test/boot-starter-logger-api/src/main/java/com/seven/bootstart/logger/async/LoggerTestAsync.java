package com.seven.bootstart.logger.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author zhangxianwen
 * 2020/1/12 12:36
 **/
@Async
@Slf4j
@Component
public class LoggerTestAsync {

    public void print() {
        log.info("@Async子线程日志");
    }
}
