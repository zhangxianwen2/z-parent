package com.seven.api.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/09 15:36
 **/
@Data
@ConfigurationProperties(prefix = SwaggerProperty.SWAGGER_CONFIG_PREFIX)
public class SwaggerProperty {
    public static final String SWAGGER_CONFIG_PREFIX = "z.api.start.swagger";

    /**
     * 是否开启Swagger API文档
     */
    private boolean enable = false;

    /**
     * 文档标题
     */
    private String apiTitle = "API";

    /**
     * 文档描述
     */
    private String apiDescription = "接口文档";

    /**
     * 文档版本
     */
    private String apiVersion = "1.0.0";

    /**
     * 扫描的基础包
     */
    private String scanPackage = "com.seven";

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人RUL
     */
    private String contactUrl;

    /**
     * 联系人邮箱
     */
    private String contactEmail;
}
