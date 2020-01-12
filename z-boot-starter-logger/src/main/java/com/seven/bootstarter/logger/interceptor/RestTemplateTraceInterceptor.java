package com.seven.bootstarter.logger.interceptor;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

/**
 * intercept方法的重写，将会在restTemple请求的请求头中增加需要链路的键值
 *
 * @author zhangxianwen
 * 2020/1/11 15:25
 **/
public class RestTemplateTraceInterceptor extends BaseTraceInterceptor implements ClientHttpRequestInterceptor {
    public RestTemplateTraceInterceptor(List<String> needTraceHeaders) {
        super(needTraceHeaders);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        for (String needTraceHeader : needTraceHeaders) {
            headers.add(needTraceHeader, MDC.get(needTraceHeader));
        }
        return execution.execute(request, body);
    }
}
