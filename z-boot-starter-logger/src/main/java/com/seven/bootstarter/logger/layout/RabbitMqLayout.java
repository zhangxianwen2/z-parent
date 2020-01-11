package com.seven.bootstarter.logger.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seven.bootstarter.logger.filter.MDCFilter;
import com.seven.bootstarter.logger.provider.ApplicationProvider;
import com.seven.bootstarter.logger.utils.BreakSensitivityUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author zhangxianwen
 * 2020/1/11 18:57
 **/
public class RabbitMqLayout extends LayoutBase<ILoggingEvent> {
    @Override
    public String doLayout(ILoggingEvent iLoggingEvent) {
        JSONObject json = new JSONObject();
        writeMDC(json, iLoggingEvent);
        writeBasic(json, iLoggingEvent);
        String message = null;
        try {
            message = BreakSensitivityUtil.breakSensitivity(JSON.toJSONString(json));
        } catch (Exception e) {
            // message = super.doLayout(iLoggingEvent);
        }
        return message;
    }

    private void writeMDC(JSONObject json, ILoggingEvent event) {
        Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();
        if (mdcPropertyMap != null) {
            json.putAll(mdcPropertyMap);
        }
    }

    private void writeBasic(JSONObject json, ILoggingEvent event) {
        json.put(MDCFilter.HEADER_KEY_APP_ID, ApplicationProvider.getAppId());
        json.put(MDCFilter.HEADER_KEY_APP_NAME, ApplicationProvider.getAppName());
        json.put(MDCFilter.HEADER_KEY_HOST_NAME, ApplicationProvider.getHostName());
        json.put(MDCFilter.HEADER_KEY_HOST_ADDRESS, ApplicationProvider.getHostAddress());

        json.put("logger", event.getLoggerName());
        json.put("threadName", event.getThreadName());
        json.put("level", event.getLevel().toString());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimeStamp()),
                ZoneId.systemDefault());
        json.put("time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(localDateTime));
    }

    private void writeThrowable(JSONObject jsonObject, ILoggingEvent iLoggingEvent) {

    }
}
