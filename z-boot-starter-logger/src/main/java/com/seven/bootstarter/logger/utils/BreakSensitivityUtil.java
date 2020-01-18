package com.seven.bootstarter.logger.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seven.bootstarter.logger.provider.SensitivityFieldProvider;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * @author zhangxianwen
 * 2020/1/11 19:10
 **/
public class BreakSensitivityUtil {

    public static String breakSensitivity(String str) {
        // 1. 字符属于JsonArray
        if (isJsonArray(str)) {
            str = transformJSONArray(str);
        }
        // 2. 字符属于JsonObject
        else if (isJsonObject(JSON.toJSONString(str))) {
            str = transformJSONObject(str);
        }
        // 3. 混合模式 相较于前两种模式相对不靠谱
        else {
            str = transformMix(str);
        }
        return unescapeJson(str);
    }

    public static void main(String[] args) {
        String str = "{\n \"reason\":\"成功 \",\"result\":{\"jobid\":\"JH2131171027170837443588J6\",\"realname\":\"李哪娜\",\"bankcard\":\"6226430106137525\",\"idcard\":\"130333198901192762\",\"mobile\":\"13210141605\",\"password\":{\"A\":\"a\",\"B\":\"b\"},\"message\":\"验证成功\"},\"error_code\":0}";
        // System.out.println(str);
        // System.out.println(unescapeJson(transformJSONObject(str)));
        String a = "{\"address\": \"18785062704\",\"applyAmount\": 0,\"bankMobileNo\": \"string\",\"career\": \"string\",\"certValidate\": \"string\",\"city\": 0,\"companyInfo\": {\"address\": \"string\",\"city\": 0,\"companyName\": \"string\",\"companyTelephone\": \"string\",\"district\": 0,\"province\": 0},\"contacts\": [{\"addr\": \"string\",\"kind\": 0,\"mobile\": \"string\",\"name\": \"string\"}],\"creditApplyId\": \"string\",\"custName\": \"string\",\"district\": 0,\"education\": \"string\",\"faceChannel\": 0,\"faceScore\": \"string\",\"hbUsrNo\": \"string\",\"idAddress\": \"string\",\"idNo\": \"522724199608250010\",\"imageKind\": 0,\"images\": {\"idCardBackInfo\": \"string\",\"idCardFrontInfo\": \"string\",\"livingImageInfos\": [\"string\"]},\"intRate\": 0,\"intRateType\": 0,\"merchantId\": \"string\",\"mobileNo\": \"18787676526\",\"period\": 0,\"productId\": \"string\",\"province\": 0,\"purpose\": \"string\",\"race\": \"string\",\"repayDay\": 0,\"riskData\": {}}";
        System.out.println(transformMix(a));
    }

    private static String transformMix(String str) {
        // 按照标点符号分组，根据拆分后的数组逐个追加到StringBuild中，由于split方法不会返回用于拆分的标点符号，
        // 因此当遇到标点符号时，则将标点符号进行追加
        StringBuilder resultStr = new StringBuilder();
        while (str.length() > 0) {
            final String cutStr = cutStr(str);
            resultStr.append(transformWord(cutStr));
            int index;
            if (StringUtils.isEmpty(cutStr)) {
                // 标点
                index = 1;
                // 标追添加至StringBuild
                resultStr.append(str.charAt(0));
            } else {
                // 非标点
                index = str.indexOf(cutStr) + cutStr.length();
            }
            str = str.substring(index);
        }
        return resultStr.toString();
    }

    private static String cutStr(String str) {
        String regex = "[\\pP\\p{Punct}\\s+]";
        String[] split = str.split(regex);
        if (split.length <= 0) {
            return str;
        }
        return split[0];
    }

    private static String transformWord(String str) {
        if (isBankCard(str)) {
            return breakBankCard(str);
        } else if (isIdCard(str)) {
            return breakIdCard(str);
        } else if (isMobile(str)) {
            return breakMobile(str);
        } else {
            return str;
        }
    }

    private static String transformJSONArray(String str) {
        str = unescapeJson((str));
        JSONArray jsonArray = JSON.parseArray(str);
        JSONArray arr = new JSONArray();
        for (Object o : jsonArray) {            arr.add(transformJSONObject(JSON.toJSONString(o)));
        }
        return JSON.toJSONString(arr);
    }

    private static String transformJSONObject(String string) {
        string = unescapeJson(string);
        if (!isJsonObject(string)) {
            return string;
        }
        JSONObject jsonObject = JSON.parseObject(string);
        JSONObject result = new JSONObject();
        jsonObject.forEach((key, value) -> {
            String str = JSON.toJSONString(value);
            if (isJsonObject(str)) {
                value = transformJSONObject(str);
            } else if (isJsonArray(str)) {
                JSONArray jsonArray = JSON.parseArray(str);
                JSONArray arr = new JSONArray();
                for (Object o : jsonArray) {
                    arr.add(transformJSONObject(JSON.toJSONString(o)));
                }
                value = JSON.toJSONString(arr);
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
                } else {
                    value = transformMix(str);
                }
            }
            result.put(key, value);
        });
        return JSON.toJSONString(result);
    }

    private static boolean isJsonObject(String str) {
        try {
            str = unescapeJson((str));
            JSON.parseObject(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isJsonArray(String str) {
        try {
            str = unescapeJson((str));
            JSON.parseArray(str);
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
        while (str.contains("\"{")) {
            str = str.replace("\"{", "{");

        }
        while (str.contains("}\"")) {
            str = str.replace("}\"", "}");
        }
        while (str.contains("\"[")) {
            str = str.replace("\"[", "[");

        }
        while (str.contains("]\"")) {
            str = str.replace("]\"", "]");
        }
        while (str.contains("\n")) {
            str = str.replace("\n", "");
        }
        while (str.contains("\r")) {
            str = str.replace("\r", "");
        }
        while (str.contains("\t")) {
            str = str.replace("\t", "");
        }

        return str.trim();
    }

    public static String removeBlank(String str) {
        while (str.contains(" ")) {
            str = str.replace(" ", "");
        }
        return str.trim();
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
