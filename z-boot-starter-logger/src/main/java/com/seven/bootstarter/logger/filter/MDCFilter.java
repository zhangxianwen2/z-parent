package com.seven.bootstarter.logger.filter;

import com.seven.bootstarter.common.utils.UUIDUtil;
import com.seven.bootstarter.logger.provider.ApplicationProvider;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhangxianwen
 * 2020/1/11 13:50
 **/
public class MDCFilter implements Filter {

    public static final String HEADER_KEY_TRACE_ID = "traceId";
    public static final String HEADER_KEY_SERIES_IP = "tranceSeriesIp";

    public static final String HEADER_KEY_HOST_NAME = "tranceHostName";
    public static final String HEADER_KEY_HOST_ADDRESS = "tranceHostAddress";

    public static final String WEB_VISIT_HOST_ADDRESS = "webVisitHostAddress";

    public static final String HEADER_KEY_APP_ID = "tranceAppId";
    public static final String HEADER_KEY_APP_NAME = "tranceAppName";
    public static final String HEADER_KEY_EXTRA_SIGN = "tranceExtraSign";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ModifyHttpServletRequestWrapper modifyHttpServletRequestWrapper = new ModifyHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        modifyMdc(modifyHttpServletRequestWrapper);
        filterChain.doFilter(modifyHttpServletRequestWrapper, servletResponse);
    }

    private void modifyMdc(ModifyHttpServletRequestWrapper requestWrapper) {
        String tranceHostName = ApplicationProvider.getHostName();
        ZMDC.put(MDCFilter.HEADER_KEY_HOST_NAME, tranceHostName);

        ZMDC.put(MDCFilter.WEB_VISIT_HOST_ADDRESS, getVisitIp());

        String tranceHostAddress = ApplicationProvider.getHostAddress();
        ZMDC.put(MDCFilter.HEADER_KEY_HOST_ADDRESS, tranceHostAddress);
        requestWrapper.putCustomHeaders(MDCFilter.HEADER_KEY_HOST_ADDRESS, tranceHostAddress);

        String tranceAppId = ApplicationProvider.getAppId();
        ZMDC.put(MDCFilter.HEADER_KEY_APP_ID, tranceAppId);

        String tranceAppName = ApplicationProvider.getAppName();
        ZMDC.put(MDCFilter.HEADER_KEY_APP_NAME, tranceAppName);
        requestWrapper.putCustomHeaders(MDCFilter.HEADER_KEY_APP_NAME, tranceAppName);

        String tranceExtraSign = ApplicationProvider.getExtraSign();
        ZMDC.put(MDCFilter.HEADER_KEY_EXTRA_SIGN, tranceExtraSign);

        //
        String traceId = requestWrapper.getHeader(MDCFilter.HEADER_KEY_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUIDUtil.getShortUUID();
        }
        ZMDC.put(MDCFilter.HEADER_KEY_TRACE_ID, traceId);
        requestWrapper.putCustomHeaders(MDCFilter.HEADER_KEY_TRACE_ID, traceId);

        String seriesIp = requestWrapper.getHeader(MDCFilter.HEADER_KEY_SERIES_IP);
        if (!StringUtils.isEmpty(seriesIp)) {
            seriesIp = seriesIp + "->" + tranceHostAddress + "(" + tranceAppName + ")";
        } else {
            seriesIp = tranceHostAddress + "(" + tranceAppName + ")";
        }
        ZMDC.put(MDCFilter.HEADER_KEY_SERIES_IP, seriesIp);
        requestWrapper.putCustomHeaders(MDCFilter.HEADER_KEY_SERIES_IP, seriesIp);
    }

    /**
     * 取得访问者IP
     */
    public static String getVisitIp() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return "";
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

}
