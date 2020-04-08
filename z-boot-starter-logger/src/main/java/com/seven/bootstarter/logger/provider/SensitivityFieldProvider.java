package com.seven.bootstarter.logger.provider;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxianwen
 * 2020/1/11 21:02
 **/
public class SensitivityFieldProvider {
    private static List<String> PASSWORD_FIELD_LIST = new ArrayList<String>() {{
        add("pwd".toLowerCase());
        add("password".toLowerCase());
    }};
    private static List<String> MOBILE_FIELD_LIST = new ArrayList<String>() {{
        add("phone".toLowerCase());
        add("phoneNo".toLowerCase());
        add("telPhone".toLowerCase());
        add("telPhoneNo".toLowerCase());
        add("mobile".toLowerCase());
        add("mobileNo".toLowerCase());
        add("mobileNo".toLowerCase());
    }};
    private static List<String> ID_CARD_FIELD_LIST = new ArrayList<String>() {{
        add("idCard".toLowerCase());
        add("idNo".toLowerCase());
    }};
    private static List<String> BANK_CARD_FIELD_LIST = new ArrayList<String>() {{
        add("bankNo".toLowerCase());
        add("bankCard".toLowerCase());
    }};

    public SensitivityFieldProvider(@Value("${z.boot.starter.logger.mobile-regex:^[1][3589]\\d{9}}") String mobileRegex,
                                    @Value("${z.boot.starter.logger.idCard-regex:(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)}") String idCardRegex,
                                    @Value("${z.boot.starter.logger.bankCard-regex:^[34569]\\d{15,18}}") String bankCardRegex,
                                    @Value("${z.boot.starter.logger.max-length-valid:false}") String maxLoggerLengthValid,
                                    @Value("${z.boot.starter.logger.max-length:2048}") String maxLoggerLength,
                                    @Value("${z.boot.starter.logger.sensitivity-valid:false}") String sensitivityValid

    ) {
        SensitivityFieldProvider.mobileRegex = mobileRegex;
        SensitivityFieldProvider.idCardRegex = idCardRegex;
        SensitivityFieldProvider.bankCardRegex = bankCardRegex;
        SensitivityFieldProvider.maxLoggerLengthValid = maxLoggerLengthValid;
        SensitivityFieldProvider.maxLoggerLength = maxLoggerLength;
        SensitivityFieldProvider.sensitivityValid = sensitivityValid;
    }

    /**
     * 电话号码号正则
     */
    private static String mobileRegex = "^[1][3589]\\d{9}";
    /**
     * 身份证号正则
     */
    private static String idCardRegex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
    /**
     * 银行卡号正则
     */
    private static String bankCardRegex = "^[34569]\\d{15,18}";
    /**
     * 是否对message进行裁剪 true 超长裁剪  false 不裁剪
     */
    private static String maxLoggerLengthValid = "false";
    /**
     * 如果对日志超长处理  最大长度
     */
    private static String maxLoggerLength = "2048";
    /**
     * 是否脱敏 true 脱敏  false 不脱敏
     */
    private static String sensitivityValid = "false";


    public static List<String> getPasswordFieldList() {
        return PASSWORD_FIELD_LIST;
    }

    public static List<String> getMobileFieldList() {
        return MOBILE_FIELD_LIST;
    }

    public static List<String> getIdCardFieldList() {
        return ID_CARD_FIELD_LIST;
    }

    public static List<String> getBankCardFieldList() {
        return BANK_CARD_FIELD_LIST;
    }

    public static String getMobileRegex() {
        return mobileRegex;
    }

    public static String getIdCardRegex() {
        return idCardRegex;
    }

    public static String getBankCardRegex() {
        return bankCardRegex;
    }

    public static String getMaxLoggerLength() {
        return maxLoggerLength;
    }

    public static String getSensitivityValid() {
        return sensitivityValid;
    }

    public static String getMaxLoggerLengthValid() {
        return maxLoggerLengthValid;
    }
}
