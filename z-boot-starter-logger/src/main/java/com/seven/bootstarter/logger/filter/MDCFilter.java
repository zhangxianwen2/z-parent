package com.seven.bootstarter.logger.filter;

import com.seven.bootstarter.logger.provider.ApplicationProvider;
import com.seven.bootstarter.logger.utils.UUIDUtil;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zhangxianwen
 * 2020/1/11 13:50
 **/
public class MDCFilter implements Filter {

    public final static String HEADER_KEY_TRACE_ID = "traceId";
    public final static String HEADER_KEY_SERIES_IP = "tranceSeriesIp";

    public final static String HEADER_KEY_HOST_NAME = "tranceHostName";
    public final static String HEADER_KEY_HOST_ADDRESS = "tranceHostAddress";

    public final static String HEADER_KEY_APP_ID = "tranceAppId";
    public final static String HEADER_KEY_APP_NAME = "tranceAppName";
    public final static String HEADER_KEY_EXTRA_SIGN = "tranceExtraSign";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ModifyHttpServletRequestWrapper modifyHttpServletRequestWrapper = new ModifyHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        modifyMdc(modifyHttpServletRequestWrapper);
        filterChain.doFilter(modifyHttpServletRequestWrapper, servletResponse);
    }

    private void modifyMdc(ModifyHttpServletRequestWrapper requestWrapper) {
        String tranceHostName = ApplicationProvider.getHostName();
        MDC.put(MDCFilter.HEADER_KEY_HOST_NAME, tranceHostName);

        String tranceHostAddress = ApplicationProvider.getHostAddress();
        MDC.put(MDCFilter.HEADER_KEY_HOST_ADDRESS, tranceHostAddress);
        requestWrapper.putCustomHeaders(MDCFilter.HEADER_KEY_HOST_ADDRESS, tranceHostAddress);

        String tranceAppId = ApplicationProvider.getAppId();
        MDC.put(MDCFilter.HEADER_KEY_APP_ID, tranceAppId);

        String tranceAppName = ApplicationProvider.getAppName();
        MDC.put(MDCFilter.HEADER_KEY_APP_NAME, tranceAppName);
        requestWrapper.putCustomHeaders(MDCFilter.HEADER_KEY_APP_NAME, tranceAppName);

        String tranceExtraSign = ApplicationProvider.getExtraSign();
        MDC.put(MDCFilter.HEADER_KEY_EXTRA_SIGN, tranceExtraSign);

        //
        String traceId = requestWrapper.getHeader(MDCFilter.HEADER_KEY_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUIDUtil.getShortUUID();
        }
        MDC.put(MDCFilter.HEADER_KEY_TRACE_ID, traceId);
        requestWrapper.putCustomHeaders(MDCFilter.HEADER_KEY_TRACE_ID, traceId);

        String seriesIp = requestWrapper.getHeader(MDCFilter.HEADER_KEY_SERIES_IP);
        if (!StringUtils.isEmpty(seriesIp)) {
            seriesIp = seriesIp + "->" + tranceHostAddress + "(" + tranceAppName + ")";
        } else {
            seriesIp = tranceHostAddress + "(" + tranceAppName + ")";
        }
        MDC.put(MDCFilter.HEADER_KEY_SERIES_IP, seriesIp);
        requestWrapper.putCustomHeaders(MDCFilter.HEADER_KEY_SERIES_IP, seriesIp);
    }
}
