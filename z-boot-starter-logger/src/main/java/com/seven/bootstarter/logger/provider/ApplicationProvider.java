package com.seven.bootstarter.logger.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * 项目初始化时提供应用的相关信息
 *
 * @author zhangxianwen
 * 2020/1/11 11:40
 **/
@Slf4j
public class ApplicationProvider {

    /**
     * 应用机器名称
     */
    private static String hostName;
    /**
     * 应用机器ip地址
     */
    private static String hostAddress;

    /**
     * 应用ID 配置获取
     */
    private static String appId;
    /**
     * 应用名称 配置获取
     */
    private static String appName;
    /**
     * 拓展标志 根据格各自应用自行决定是否设值
     */
    private static String extraSign;


    public ApplicationProvider(@Value("${z.logger.application.name:UNDEFINED}") String applicationName,
                               @Value("${z.logger.application.id:UNDEFINED}") String appId,
                               @Value("${z.logger.application.extra-sign:}") String extraSign) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ApplicationProvider.hostAddress = inetAddress.getHostAddress();
            ApplicationProvider.hostName = inetAddress.getHostName();
        } catch (UnknownHostException e) {
            log.warn("There is a UnknownHostException be found,fields hostAddress and hostName will be null!");
            // do nothing
        }
        ApplicationProvider.appName = applicationName;
        ApplicationProvider.appId = appId;
        ApplicationProvider.extraSign = extraSign;
    }

    public static String getHostName() {
        return hostName;
    }

    public static String getHostAddress() {
        return hostAddress;
    }

    public static String getAppId() {
        return appId;
    }

    public static String getAppName() {
        return appName;
    }

    public static String getExtraSign() {
        return extraSign;
    }
}
