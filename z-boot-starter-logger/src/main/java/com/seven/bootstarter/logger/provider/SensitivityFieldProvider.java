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

    @Value("${z.boot.starter.logger.mobile-regex:^[1][3589]\\d{9}}")
    private static String mobileRegex;

    @Value("${z.boot.starter.logger.idCard-regex:(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)}")
    private static String idCardRegex;

    @Value("${z.boot.starter.logger.bankCard-regex:^[34569]\\d{15,18}}")
    private static String bankCardRegex;

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
}
