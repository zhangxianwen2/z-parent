package com.seven.bootstarter.logger.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.alibaba.fastjson.JSONObject;
import com.seven.bootstarter.logger.utils.BreakSensitivityUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangxianwen
 * 2020/1/12 10:36
 **/
@Slf4j
public class RabbitMQLayOut extends AbstractLayout {
    @Override
    String buildLayout(ILoggingEvent iLoggingEvent) {
        JSONObject json = new JSONObject();
        writeMDC(json);
        writeBasic(json, iLoggingEvent);
        writeThrowable(json, iLoggingEvent);
        return BreakSensitivityUtil.unescapeJson(json.toString()) + "\n";
    }
}

