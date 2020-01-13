package com.seven.bootstarter.logger;

import com.seven.bootstarter.logger.filter.MDCFilter;
import com.seven.bootstarter.logger.interceptor.RestTemplateTraceInterceptor;
import com.seven.bootstarter.logger.provider.ApplicationProvider;
import com.seven.bootstarter.logger.provider.SensitivityFieldProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhangxianwen
 * 2020/1/11 15:32
 **/
@Configuration
@Import({ApplicationProvider.class, SensitivityFieldProvider.class})
public class LoggerAutoconfig {
    private static final List<String> NEED_TRACE_HEADERS = new ArrayList<String>() {
        {
            add(MDCFilter.HEADER_KEY_TRACE_ID);
            add(MDCFilter.HEADER_KEY_SERIES_IP);
        }
    };

    @Bean
    public FilterRegistrationBean<MDCFilter> mdcFilterRegistrationBean() {
        FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
        MDCFilter mdcFilter = new MDCFilter();
        registrationBean.setFilter(mdcFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        RestTemplateTraceInterceptor traceInterceptor = new RestTemplateTraceInterceptor(NEED_TRACE_HEADERS);
        restTemplate.setInterceptors(Collections.singletonList(traceInterceptor));
        return restTemplate;
    }
}
