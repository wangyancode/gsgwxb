package com.jsdc.gsgwxb.utils;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期计算工具类
 */
public class TimeUtils {
    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(Date starttime, Date endtime) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long time1 = starttime.getTime();
        long time2 = endtime.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        return day + "天" + hour + "小时" + min + "分";
    }

    /**
     * 两个时间相差距离多少天
     */
    public static Integer getDiffDay(Date starttime, Date endtime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(cn.hutool.core.date.DateUtil.formatDate(starttime), formatter);
        LocalDate date2 = LocalDate.parse(cn.hutool.core.date.DateUtil.formatDate(endtime), formatter);
        long daysBetween = ChronoUnit.DAYS.between(date1, date2);
        if (daysBetween >= Integer.MIN_VALUE && daysBetween <= Integer.MAX_VALUE) {
            // 先将long转换为int，然后再装箱为Integer
            Integer integerValue = Integer.valueOf((int) daysBetween);
//            System.out.println("The Integer value is: " + integerValue);
            return integerValue;
        } else {
            System.out.println("The long value is out of the range of int.");
        }
        return 0;
    }

    /**
     * 计算两个时间相差距离 分钟
     */
    public static Integer computeMinute(Date starttime, Date endtime) {
        return Math.toIntExact(ChronoUnit.MINUTES.between(Instant.ofEpochMilli(starttime.getTime()), Instant.ofEpochMilli(endtime.getTime())));
    }

    /**
     * 计算两个时间相差距离 秒
     */
    public static Integer computeSeconds(Date starttime, Date endtime) {
        return Math.toIntExact(ChronoUnit.SECONDS.between(Instant.ofEpochMilli(starttime.getTime()), Instant.ofEpochMilli(endtime.getTime())));
    }

    /**
     * 根据分钟转小时
     */
    public static String formatTime(Integer minuteStr) {
        String resultStr = "";
        if (null != minuteStr) {
            if (minuteStr != 0) {
                if (minuteStr / 60 == 0) {
                    resultStr = minuteStr % 60 + "分";
                } else {
                    if (minuteStr % 60 == 0) {
                        resultStr = minuteStr / 60 + "小时";
                    } else {
                        resultStr = (minuteStr / 60 + "小时" + minuteStr % 60 + "分");
                    }
                }
            } else {
                resultStr = "0分";
            }
        } else {
            resultStr = "0分";
        }
        return resultStr;
    }

    /**
     * 根据分钟转 xx天xx小时xx分xx秒
     */
    public static String minConvertDayHourMin(Integer min) {

        String html = "0分";

        if (min != null) {

            Integer m = (Integer) min;

            String format;

            Object[] array;

            Integer days = (int) (m / (60 * 24));

            Integer hours = (int) (m / (60) - days * 24);

            Integer minutes = (int) (m - hours * 60 - days * 24 * 60);

            if (days > 0) {
                format = "%1$,d天%2$,d时%3$,d分";

                array = new Object[]{days, hours, minutes};

            } else if (hours > 0) {

                format = "%1$,d时%2$,d分";

                array = new Object[]{hours, minutes};

            } else {

                format = "%1$,d分";

                array = new Object[]{minutes};

            }
            html = String.format(format, array);

        }

        return html;

    }

    /**
     * 根据分钟转 xx天xx小时xx分
     * 1天按照8小时计算
     */
    public static String convertMinToDayHourMin(Integer totalMinutes) {

        // 定义自定义的一天有多少小时
        final int CUSTOM_DAY_HOURS = 8;
        final int MINUTES_PER_HOUR = 60;
        final int MINUTES_PER_CUSTOM_DAY = CUSTOM_DAY_HOURS * MINUTES_PER_HOUR;

        // 计算天数
        int customDays = totalMinutes / MINUTES_PER_CUSTOM_DAY;
        int remainingMinutesAfterDays = totalMinutes % MINUTES_PER_CUSTOM_DAY;

        // 计算小时数
        int hours = remainingMinutesAfterDays / MINUTES_PER_HOUR;
        int remainingMinutesAfterHours = remainingMinutesAfterDays % MINUTES_PER_HOUR;

        // 剩余的分钟数
        int minutes = remainingMinutesAfterHours;

        if (customDays > 0) {
            return customDays + "天" + hours + "时" + minutes + "分";
        } else if (hours > 0) {
            return hours + "时" + minutes + "分";
        } else {
            return minutes + "分";
        }

//        // 格式化输出
//        return customDays + "天" + hours + "时" + minutes + "分";

    }

    /**
     * 根据分钟转 xx天xx小时xx分
     * 1天按照24小时计算
     */
    public static String convertMinToDayHourMin24(Integer totalMinutes) {

        // 定义自定义的一天有多少小时
        final int CUSTOM_DAY_HOURS = 24;
        final int MINUTES_PER_HOUR = 60;
        final int MINUTES_PER_CUSTOM_DAY = CUSTOM_DAY_HOURS * MINUTES_PER_HOUR;

        // 计算天数
        int customDays = totalMinutes / MINUTES_PER_CUSTOM_DAY;
        int remainingMinutesAfterDays = totalMinutes % MINUTES_PER_CUSTOM_DAY;

        // 计算小时数
        int hours = remainingMinutesAfterDays / MINUTES_PER_HOUR;
        int remainingMinutesAfterHours = remainingMinutesAfterDays % MINUTES_PER_HOUR;

        // 剩余的分钟数
        int minutes = remainingMinutesAfterHours;

        if (customDays > 0) {
            return customDays + "天" + hours + "时" + minutes + "分";
        } else if (hours > 0) {
            return hours + "时" + minutes + "分";
        } else {
            return minutes + "分";
        }

//        // 格式化输出
//        return customDays + "天" + hours + "时" + minutes + "分";

    }

    /**
     * 根据分钟转 xx:xx:xx:
     * 小时：分钟：秒
     */
    public static String convertMinutesToTime(Integer totalMinutes) {
        Duration duration = Duration.ofMinutes(totalMinutes);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }

    /**
     * 将分钟转换为以8小时工作制计算的天数
     * @param minutes
     * @return
     */
    public static String convertMinutesToDaysBy8Hour(int minutes) {
        // 每天8小时，每小时60分钟
        double minutesPerDay = 8 * 60;
        // 将分钟转换为天数
        double days = (double) minutes / minutesPerDay;
        return formatDays(days);
    }

    /**
     * 将分钟转换为以8小时工作制计算的天数
     * @param minutes 分钟数
     * @return 转换后的天数
     */
    public static double convertMinutesToDays(int minutes) {
        // 每天8小时，每小时60分钟
        double minutesPerDay = 8 * 60;
        // 将分钟转换为天数
        double days = (double) minutes / minutesPerDay;
        return days;
    }

    /**
     * 格式化天数，保留1位小数
     * @param days 天数
     * @return 格式化后的字符串
     */
    public static String formatDays(double days) {
        // 使用DecimalFormat保留1位小数
//        DecimalFormat df = new DecimalFormat("#.0");
//        return df.format(days);

        String str = new BigDecimal(days).setScale(1, RoundingMode.HALF_UP).toString();
        return str;
    }



    /**
     * 两个日期对比，取最大时间
     */
    public static String onMaxTime(String startTime, String endTime) {
        Date date1 = new DateTime(startTime).toDate();
        Date date2 = new DateTime(endTime).toDate();

        // 比较两个日期
        int result = date1.compareTo(date2);

        if (result < 0) {
//            System.out.println("date1 is before date2");
        } else if (result > 0) {
//            System.out.println("date1 is after date2");
            return startTime;
        } else {
//            System.out.println("date1 is equal to date2");

        }

        return endTime;
    }

    /**
     * 两个日期对比，取最小时间
     */
    public static String onMinTime(String startTime, String endTime) {
        Date date1 = new DateTime(startTime).toDate();
        Date date2 = new DateTime(endTime).toDate();

        // 比较两个日期
        int result = date1.compareTo(date2);

        if (result < 0) {
//            System.out.println("date1 is before date2");
            return startTime;
        } else if (result > 0) {
//            System.out.println("date1 is after date2");
        } else {
//            System.out.println("date1 is equal to date2");
        }

        return endTime;
    }




    public static void main(String[] args) {
        Duration duration = Duration.ofMinutes(100);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;



        System.err.println(String.format("%02d:%02d:%02d", hours, minutes, seconds));
//        // 比较两个日期
//        int result = date1.compareTo(date2);
//
//        if (result < 0) {
//            System.out.println("date1 is before date2");
//        } else if (result > 0) {
//            System.out.println("date1 is after date2");
//        } else {
//            System.out.println("date1 is equal to date2");
//
//        }
    }

}
