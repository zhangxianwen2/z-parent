package com.seven.bootstarter.logger.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seven.bootstarter.logger.provider.SensitivityFieldProvider;
import org.apache.commons.lang3.StringEscapeUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * @author zhangxianwen
 * 2020/1/11 19:10
 **/
public class BreakSensitivityUtil {

    public static String breakSensitivity(String str) {
        // 1. 字符属于Json格式
        if (isJsonObject(JSON.toJSONString(str))) {
            return transformJSONObject(str);
        }
        // 2. 字符属于对象格式
// TODO: 2020/1/11
        return null;
    }


    public static void main(String[] args) {
        String str = "\n" +
                "{\n" +
                "    \"idNo\":\"522724199608250010\",\n" +
                "    \"mobile\":\"18785062704\",\n" +
                "    \"bankNo\":\"62122624025012101\",\n" +
                "    \"password\":\"zxw0825.\",\n" +
                "    \"phone\":{\n" +
                "        \"idNo\":\"522724199608250010\",\n" +
                "    \t\"mobile\":\"18785062704\",\n" +
                "    \t\"bankNo\":\"62122624025012101\",\n" +
                "    \t\"password\":\"zxw0825.\",\n" +
                "        \"qwe\":\"qwe\"\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n";
        System.out.println(str);
        System.out.println(transformJSONObject(str));

        System.out.println(isMobile("18785062704"));
    }

    private static String transformJSONObject(String string) {
        string = unescapeJson(string);
        if (!isJsonObject(string)) {
            return string;
        }
        JSONObject jsonObject = JSONObject.parseObject(string);
        JSONObject result = new JSONObject();
        jsonObject.forEach((key, value) -> {
            String str = JSON.toJSONString(value);
            if (isJsonObject(str)) {
                value = transformJSONObject(str);
            } else {
                str = String.valueOf(value);
                if (SensitivityFieldProvider.getBankCardFieldList().contains(key.toLowerCase())) {
                    value = breakBankCard(str);
                } else if (SensitivityFieldProvider.getIdCardFieldList().contains(key.toLowerCase())) {
                    value = breakIdCard(str);
                } else if (SensitivityFieldProvider.getMobileFieldList().contains(key.toLowerCase())) {
                    value = breakMobile(str);
                } else if (SensitivityFieldProvider.getPasswordFieldList().contains(key.toLowerCase())) {
                    value = breakPassword(str);
                }
            }
            result.put(key, value);
        });
        return unescapeJson(JSON.toJSONString(result));
    }

    private static boolean isJsonObject(String str) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isMobile(String str) {
        String mobileRegex = "^[1][3589]\\d{9}";
        Pattern pattern = Pattern.compile(mobileRegex);
        // Pattern pattern = Pattern.compile(SensitivityFieldProvider.getMobileRegex());
        return pattern.matcher(str).matches();
    }

    /**
     * 电话号脱敏
     *
     * @param str
     * @return
     */
    private static String breakMobile(String str) {
        if (isMobile(str)) {
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (i - 2 > 0 && chars.length - i > 4) {
                    chars[i] = "*".charAt(0);
                }
            }
            return String.valueOf(chars);
        }
        return str;
    }

    private static boolean isBankCard(String str) {
        String bankCardRegex = "^[34569]\\d{15,18}";
        Pattern pattern = Pattern.compile(bankCardRegex);
        // Pattern pattern = Pattern.compile(SensitivityFieldProvider.getMobileRegex());
        return pattern.matcher(str).matches();
    }

    /**
     * 银行卡号脱敏
     *
     * @param str
     * @return
     */
    private static String breakBankCard(String str) {
        if (isBankCard(str)) {
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (i - 5 > 0 && chars.length - i > 4) {
                    chars[i] = "*".charAt(0);
                }
            }
            return String.valueOf(chars);
        }
        return str;
    }

    /**
     * 是否身份证 仅识别二代身份证18位
     *
     * @param str 待识别字符串
     * @return
     */
    private static boolean isIdCard(String str) {
        String idCardRegex = "(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        Pattern pattern = Pattern.compile(idCardRegex);
        // Pattern pattern = Pattern.compile(SensitivityFieldProvider.getIdCardRegex());
        if (!pattern.matcher(str).matches()) {
            return false;
        }
        String substring = str.substring(6, 14);
        return isDate(substring);
    }

    /**
     * 身份证号脱敏
     *
     * @param str
     * @return
     */
    private static String breakIdCard(String str) {
        if (isIdCard(str)) {
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (i - 5 > 0 && chars.length - i > 4) {
                    chars[i] = "*".charAt(0);
                }
            }
            return String.valueOf(chars);
        }
        return str;
    }

    /**
     * 密码脱敏
     *
     * @param str
     * @return
     */
    private static String breakPassword(String str) {
        return "******";
    }

    /**
     * Json去掉转义字符
     *
     * @param str
     * @return
     */
    public static String unescapeJson(String str) {
        while (str.contains("\\\"")) {
            str = str.replace("\\\"", "\"");
        }
        str = str.replace("\"{", "{");
        str = str.replace("}\"", "}");
        return str;
    }

    /**
     * 是否属于日期格式
     *
     * @param str
     * @param pattern
     * @return
     */
    private static boolean isDate(String str) {
        try {
            LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
