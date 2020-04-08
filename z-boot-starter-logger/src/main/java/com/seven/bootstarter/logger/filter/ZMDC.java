package com.seven.bootstarter.logger.filter;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import org.slf4j.helpers.Util;
import org.slf4j.spi.MDCAdapter;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/04/03 17:06
 **/
public class ZMDC {
    static MDCAdapter mdcAdapter;

    private ZMDC() {

    }

    static {
        try {
            mdcAdapter = new LogbackMDCAdapter();
        } catch (Exception e) {
            // we should never get here
            Util.report("HyxfMDC binding unsuccessful.", e);
        }
    }

    public static void put(String key, String val) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null.");
        }
        mdcAdapter.put(key, val);
    }

    public static String get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }

        if (mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null ");
        }
        return mdcAdapter.get(key);
    }

    public static void remove(String key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }

        if (mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null");
        }
        mdcAdapter.remove(key);
    }

    public static void clear() {
        if (mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null");
        }
        mdcAdapter.clear();
    }

    public static void setContextMap(Map<String, String> contextMap) {
        if (mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null");
        }
        mdcAdapter.setContextMap(contextMap);
    }

    public static Map<String, String> getCopyOfContextMap() {
        if (mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null.");
        }
        return mdcAdapter.getCopyOfContextMap();
    }
}
