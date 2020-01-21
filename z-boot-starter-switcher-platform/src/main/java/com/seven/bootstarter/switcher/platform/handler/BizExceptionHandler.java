package com.seven.bootstarter.switcher.platform.handler;

import com.seven.bootstarter.switcher.exception.ServerUnableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/21 22:31
 **/
@Slf4j
@RestControllerAdvice
public class BizExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ServerUnableException.class)
    public void handleSwitcherUnableException(ServerUnableException e) {
        log.info("在这里捕捉到了服务停止转用异常，然后自行处理吧！");
        // todo something
    }
}
