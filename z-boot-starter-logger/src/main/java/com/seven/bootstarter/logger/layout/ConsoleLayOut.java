package com.seven.bootstarter.logger.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.alibaba.fastjson.JSONObject;
import com.seven.bootstarter.logger.filter.MDCFilter;
import org.springframework.util.StringUtils;

/**
 * @author zhangxianwen
 * 2020/1/12 10:41
 **/
public class ConsoleLayOut extends AbstractLayout {
    private static final String TAB = " ";
    private Boolean showThreadName = true;
    private Boolean showSeriesIp = true;

    public void setShowThreadName(Boolean showThreadName) {
        this.showThreadName = showThreadName;
    }

    public void setShowSeriesIp(Boolean showSeriesIp) {
        this.showSeriesIp = showSeriesIp;
    }

    @Override
    String buildLayout(ILoggingEvent iLoggingEvent) {
        JSONObject json = new JSONObject();
        writeMDC(json);
        writeBasic(json, iLoggingEvent);
        writeThrowable(json, iLoggingEvent);
        //
        StringBuilder out = new StringBuilder();
        out.append(json.get("time"));
        out.append(TAB);
        if (showThreadName) {
            out.append(json.get("threadName"));
            out.append(TAB);
        }
        out.append(json.get("level"));
        out.append(TAB);
        if (!StringUtils.isEmpty(json.get(MDCFilter.HEADER_VISITOR))) {
            out.append("[");
            out.append(json.get(MDCFilter.HEADER_VISITOR));
            out.append("]");
            out.append(TAB);
        }
        if (!StringUtils.isEmpty(json.get(MDCFilter.HEADER_KEY_TRACE_ID))) {
            out.append("[");
            out.append(json.get(MDCFilter.HEADER_KEY_TRACE_ID));
            out.append("]");
            out.append(TAB);
        }
        if (!StringUtils.isEmpty(json.get(MDCFilter.WEB_VISIT_HOST_ADDRESS))) {
            out.append("[");
            out.append(json.get(MDCFilter.WEB_VISIT_HOST_ADDRESS));
            out.append("]");
            out.append(TAB);
        }
        if (showSeriesIp && !StringUtils.isEmpty(json.get(MDCFilter.HEADER_KEY_SERIES_IP))) {
            out.append("[");
            out.append(json.get(MDCFilter.HEADER_KEY_SERIES_IP));
            out.append("]");
            out.append(TAB);
        }
        if (!StringUtils.isEmpty(json.get(MDCFilter.HEADER_KEY_EXTRA_SIGN))) {
            out.append("[");
            out.append(json.get(MDCFilter.HEADER_KEY_EXTRA_SIGN));
            out.append("]");

            out.append(TAB);
        }
        out.append(json.get("logger"));
        out.append(TAB);
        out.append("-");
        out.append(TAB);
        out.append(json.get("message"));
        if (json.get("throwable") != null) {
            out.append("\n");
            out.append(json.getJSONObject("throwable").get("className"));
            out.append("-");
            out.append(json.getJSONObject("throwable").get("message"));
//            out.append(json.getJSONObject("throwable").get("cause"));
        }
        if (json.get("stackTrace") != null) {
            for (Object stackTrace : json.getJSONArray("stackTrace")) {
                out.append("\n");
                out.append("\tat ");
                out.append(((JSONObject) stackTrace).get("class"));
                out.append(".");
                out.append(((JSONObject) stackTrace).get("method"));
                out.append("(");
                out.append(((JSONObject) stackTrace).get("file"));
                out.append(":");
                out.append(((JSONObject) stackTrace).get("line"));
                out.append(")");
            }
        } else if (json.get("throwable") != null && json.getJSONObject("throwable").get("cause") != null && json.getJSONObject("throwable").getJSONObject("cause").get("stackTrace") != null) {
            for (Object stackTrace : json.getJSONObject("throwable").getJSONObject("cause").getJSONArray("stackTrace")) {
                out.append("\n");
                out.append("\tat ");
                out.append(((JSONObject) stackTrace).get("className"));
                out.append(".");
                out.append(((JSONObject) stackTrace).get("methodName"));
                out.append("(");
                out.append(((JSONObject) stackTrace).get("fileName"));
                out.append(":");
                out.append(((JSONObject) stackTrace).get("lineNumber"));
                out.append(")");
            }
        }
        out.append("\n");
        return out.toString();
    }
}
