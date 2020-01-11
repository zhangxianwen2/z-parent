package com.seven.bootstarter.logger.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author zhangxianwen
 * 2020/1/11 14:04
 **/
public class ModifyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String> customHeaders;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public ModifyHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        customHeaders = new HashMap<>();
    }

    public void putCustomHeaders(String key, String value) {
        this.customHeaders.put(key, value);
    }

    @Override
    public String getHeader(String name) {
        String header = customHeaders.get(name);
        if (header != null) {
            return header;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet<>(customHeaders.keySet());
        Enumeration<String> enumerations = super.getHeaderNames();
        while (enumerations.hasMoreElements()) {
            set.add(enumerations.nextElement());
        }
        return Collections.enumeration(set);
    }
}
