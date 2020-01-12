package com.seven.bootstarter.logger.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import com.alibaba.fastjson.JSONObject;
import com.seven.bootstarter.logger.filter.MDCFilter;
import com.seven.bootstarter.logger.provider.ApplicationProvider;
import com.seven.bootstarter.logger.provider.SensitivityFieldProvider;
import com.seven.bootstarter.logger.utils.BreakSensitivityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangxianwen
 * 2020/1/12 10:36
 **/
@Slf4j
public class RabbitMQLayOut extends AbstractLayout {
    @Override
    String buildLayout(ILoggingEvent iLoggingEvent) {
        JSONObject json = new JSONObject();
        writeMDC(json, iLoggingEvent);
        writeBasic(json, iLoggingEvent);
        writeThrowable(json, iLoggingEvent);
        return BreakSensitivityUtil.unescapeJson(json.toString()) + "\n";
    }
}

