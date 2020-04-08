package com.seven.bootstarter.logger.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.LayoutBase;
import com.alibaba.fastjson.JSONObject;
import com.seven.bootstarter.logger.filter.MDCFilter;
import com.seven.bootstarter.logger.filter.ZMDC;
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
 * 2020/1/11 18:57
 **/
@Slf4j
public abstract class AbstractLayout extends LayoutBase<ILoggingEvent> {
    private String prefix;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    @Override
    public String doLayout(ILoggingEvent iLoggingEvent) {
        String out = buildLayout(iLoggingEvent);
        return prefix == null ? out : prefix + ":" + out;
    }

    /**
     * 构建日志输出
     *
     * @param iLoggingEvent
     * @return
     */
    abstract String buildLayout(ILoggingEvent iLoggingEvent);


    protected void writeMDC(JSONObject json) {
        Map<String, String> zMdcMap = ZMDC.getCopyOfContextMap();
        if (zMdcMap != null && zMdcMap.size() > 0) {
            json.putAll(zMdcMap);
        }
    }

    protected void writeBasic(JSONObject json, ILoggingEvent event) {
        json.put(MDCFilter.HEADER_KEY_APP_ID, ApplicationProvider.getAppId());
        json.put(MDCFilter.HEADER_KEY_APP_NAME, ApplicationProvider.getAppName());
        json.put(MDCFilter.HEADER_KEY_HOST_NAME, ApplicationProvider.getHostName());
        json.put(MDCFilter.HEADER_KEY_HOST_ADDRESS, ApplicationProvider.getHostAddress());

        json.put("logger", shortLoggerName(event.getLoggerName()));
        json.put("threadName", event.getThreadName());
        json.put("level", event.getLevel().toString());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimeStamp()),
                ZoneId.systemDefault());
        json.put("time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(localDateTime));
        String message = event.getFormattedMessage();
        // 超长截取
        if (Boolean.parseBoolean(SensitivityFieldProvider.getMaxLoggerLengthValid())) {
            if (!StringUtils.isEmpty(message)) {
                int maxLoggerLength = 2048;
                try {
                    maxLoggerLength = Integer.parseInt(SensitivityFieldProvider.getMaxLoggerLength().trim());
                } catch (NumberFormatException e) {
                    // do nothing
                    log.warn("field 'z.boot.starter.logger.max-length' in configuration properties maybe is illogical!");
                }
                message = message.length() <= maxLoggerLength ? message : message.substring(0, maxLoggerLength) + "...超长省略，总长度为: " + message.length();
            }
        }
        if (Boolean.parseBoolean(SensitivityFieldProvider.getSensitivityValid())) {
            // 脱敏
            json.put("message", BreakSensitivityUtil.breakSensitivity(message));
        } else {
            json.put("message", message);
        }
    }

    protected void writeThrowable(JSONObject json, ILoggingEvent event) {
        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy instanceof ThrowableProxy) {
            ThrowableProxy throwableProxy = (ThrowableProxy) iThrowableProxy;
            Throwable t = throwableProxy.getThrowable();
            Throwable ec = t.getCause();
            JSONObject throwable = new JSONObject();

            throwable.put("message", t.getMessage());
            throwable.put("className", t.getClass().getCanonicalName());

            if (ec == null) {
                List<JSONObject> traceObjects = new ArrayList<>();
                for (StackTraceElement ste : t.getStackTrace()) {
                    JSONObject element = new JSONObject();
                    element.put("class", ste.getClassName());
                    element.put("method", ste.getMethodName());
                    element.put("line", ste.getLineNumber());
                    element.put("file", ste.getFileName());
                    traceObjects.add(element);
                }
                json.put("stackTrace", traceObjects);
            } else {
                throwable.put("cause", ec);
            }
            json.put("throwable", throwable);
        }
    }

    private static String shortLoggerName(String str) {
        StringBuilder shortLoggerName = new StringBuilder();
        String[] split = str.split("\\.");
        for (int i = 0; i < split.length; i++) {
            if (i < split.length - 1) {
                shortLoggerName.append(split[i], 0, 1);
                shortLoggerName.append(".");
            } else {
                shortLoggerName.append(split[i]);
            }
        }
        return shortLoggerName.toString();
    }

    public static void main(String[] args) {
        String a = "com.seven.asd";
        System.out.println(shortLoggerName(a));
    }
}
