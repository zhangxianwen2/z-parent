package com.seven.bootstarter.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/17 14:51
 **/
@Slf4j
public class TemporalAccessorUtil {

    public static final String HH_MM = "HH:mm";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据日期获取当天的00:00:00或者23:59:59
     *
     * @param localDate LocalDate格式的时间
     * @param isStart   true返回00:00:00   false返回23:59:59
     * @return 如：2019-12-12 00:00:00
     */
    public static LocalDateTime localDateToLocalDateTimeStart(LocalDate localDate, boolean isStart) {
        LocalDateTime localDateTime;
        if (isStart) {
            localDateTime = localDate.atTime(0, 0, 0);
        } else {
            localDateTime = localDate.atTime(23, 59, 59);
        }
        return localDateTime;
    }

    /**
     * 字符串转时间 HH：mm
     *
     * @param localTime 12:30
     * @return
     */
    public static LocalTime stringToLocalTime(String localTime) {
        return LocalTime.parse(localTime, DateTimeFormatter.ofPattern(HH_MM));
    }

    /**
     * 字符串根据pattern转LocalDate
     *
     * @param str     2019-12-12
     * @param pattern yyyy-MM-dd
     * @return
     */
    public static LocalDate stringToLocalDate(String str, String pattern) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串根据pattern转LocalDateTime
     *
     * @param str     2019-12-12 12:00:00
     * @param pattern yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String str, String pattern) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 校验身份证有效期
     *
     * @param idCardCertStr 522724190000000010
     * @return
     */
    public static boolean validateIdCardCert(String idCardCertStr) {
        try {
            LocalDate localDate = TemporalAccessorUtil.stringToLocalDate(idCardCertStr, YYYY_MM_DD);
            // 校验身份证有效期在今天以及今天以前
            return !localDate.isAfter(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将格式A的时间转为B的时间格式
     *
     * @param dataStr        被操作的时间 如：20190926
     * @param dataStrPattern 被操作的时间的格式 如：yyyyMMdd
     * @param pattern        将要转成的时间格式 如：yyyy-MM-dd
     * @return
     */
    public static String str2StrByPattern(String dataStr, String dataStrPattern, String pattern) throws IllegalAccessException {
        LocalDate localDate = TemporalAccessorUtil.stringToLocalDate(dataStr, dataStrPattern);
        return temporalAccessorToString(localDate, pattern);
    }

    /**
     * LocalDateTime转时间戳
     *
     * @param var
     * @return
     */
    public static long localDateTime2Timestamp(LocalDateTime var) {
        return var.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * TemporalAccessor类型(几个Local时间类型的父类)时间根据pattern转字符串
     *
     * @param var     LocalDate/LocalDateTime/LocalTime
     * @param pattern yyyy-MM-mm HH:mm:ss
     * @return
     */
    public static String temporalAccessorToString(TemporalAccessor var, String pattern) throws IllegalAccessException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        try {
            df.format(var);
        } catch (Exception ex) {
            throw new IllegalAccessException("时间格式转换错误");
        }
        return df.format(var);
    }
}
