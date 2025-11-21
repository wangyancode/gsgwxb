package com.jsdc.gsgwxb.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 日期工具类
 */
public class DateUtils {

    private static String format = "yyyy-MM-dd HH:mm:ss";
    private static String formatZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String parsePatterns[] = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM"};
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 将时间格式为 Fri Aug 09 13:40:04 CST 2024  转换年月日时分秒
     *
     * @param timeStr
     * @return
     */
    public static String strTimeConvert(String timeStr) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz uuuu", Locale.US);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(timeStr, inputFormat);
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化ZonedDateTime对象为新的字符串格式
        return zonedDateTime.format(outputFormat);
    }

    //计算两个Date的天数差
    public static long daysBetween(Date date1, Date date2) {
        // 将Date转换为LocalDate（忽略时区和时间部分，只计算日期差）
        LocalDate localDate1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate localDate2 = date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // 计算两个LocalDate之间的天数差
        return Math.abs(localDate1.until(localDate2).getDays());
    }

    /**
     * localDateTime转时间戳
     *
     * @param localDateTime
     * @return
     */
    public static Long LocalDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * localDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * localDateTime转String
     *
     * @param localDateTime
     * @return
     */
    public static String LocalDateTimeToStr(LocalDateTime localDateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(dtf);
    }

    /**
     * 根据日期获取当天最开始
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime getStartOfDay(LocalDate localDate) {
        //localDate.atStartOfDay();也可以达到相同效果
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    /**
     * 根据日期获取当天最晚时间
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime getEndOfDay(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }

    /**
     * localDate 转localDateTime
     * 此方法可以直接使用localDate.atStartOfDay()获取当天最开始时间
     * 如：2024-06-13T00:00
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime localDateToLocalDateTime(LocalDate localDate) {
        return localDate.atStartOfDay();
    }

    /**
     * localDate 转localDateTime
     * 指定时分秒
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime localDateToLocalDateTime(LocalDate localDate, Integer hour, Integer minutes, Integer second) {
        return localDate.atTime(hour, minutes, second);
    }

    /**
     * localDate 转localDateTime
     * 指定时分
     * 如  2024-06-13T15:30
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime localDateToLocalDateTime(LocalDate localDate, Integer hour, Integer minutes) {
        return localDate.atTime(LocalTime.of(hour, minutes));
    }

    /**
     * LocalDate转时间戳
     *
     * @param localDate
     * @return
     */
    public static Long localDateToLong(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static String localDateToStr(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    /**
     * Date转localDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date转String
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * Date转String
     *
     * @param date
     * @return
     */
    public static String dateToStrYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * Date转long
     *
     * @param date
     * @return
     */
    public static Long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * string转LocalTime
     *
     * @param timeStr
     * @return
     */
    public static LocalTime strToLocalTime(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(timeStr + ":00", formatter);
    }

    /**
     * string转LocalDate
     *
     * @param timeStr
     * @return
     */
    public static LocalDate strToLocalDate(String timeStr) {
        return LocalDate.parse(timeStr);
    }

    /**
     * string转LocalDateTime
     *
     * @param timeStr
     * @return
     */
    public static LocalDateTime strToLocalDateTime(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(timeStr, formatter);
    }

    /**
     * String转Date
     *
     * @param timeStr
     * @return
     */
    public static Date strToDate(String timeStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Date.from(LocalDateTime.parse(timeStr, dtf).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * long转localDateTime
     *
     * @param milli
     * @return
     */
    public static LocalDateTime longToLocalDateTime(Long milli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.systemDefault());
    }

    /**
     * long转localDate
     *
     * @param milli
     * @return
     */
    public static LocalDate longToLocalDate(Long milli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.systemDefault()).toLocalDate();
    }

    public static LinkedHashMap<String, String> getMonthDays(Date date) {
        LinkedHashMap<String, String> days = new LinkedHashMap<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 获取当月的天数
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 创建 SimpleDateFormat 对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 遍历每一天并格式化输出
        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            String formattedDate = sdf.format(calendar.getTime());
            days.put(formattedDate, "0.00");
        }
        return days;
    }

    public static LinkedHashMap<String, String> getLastMonthDays(Date date) {


        LinkedHashMap<String, String> days = new LinkedHashMap<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.MONTH, -1);

        // 获取当月的天数
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 创建 SimpleDateFormat 对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 遍历每一天并格式化输出
        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            String formattedDate = sdf.format(calendar.getTime());
            days.put(formattedDate, "0.00");
        }
        return days;
    }

    public static LinkedHashMap<String, String> getLastYearDays(Date date) {


        LinkedHashMap<String, String> days = new LinkedHashMap<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.YEAR, -1);

        // 获取当月的天数
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 创建 SimpleDateFormat 对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 遍历每一天并格式化输出
        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            String formattedDate = sdf.format(calendar.getTime());
            days.put(formattedDate, "0.00");
        }
        return days;
    }

    /**
     * 小时
     */
    public static LinkedHashMap<String, String> getHours(int hour) {
        LinkedHashMap<String, String> hours = new LinkedHashMap<>();
        for (int i = 0; i < 24 / hour; i++) {
            hours.put(String.format("%02d:00", i * hour), "0.00");
        }
        return hours;
    }

    public static LinkedHashMap<String, String> getTimeHours(String startTime, String endTime) {
        LinkedHashMap<String, String> hours = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            Date currentDate = new Date(startDate.getTime());

            while (currentDate.before(endDate)) {
                hours.put(sdf.format(currentDate), "0.00");
                currentDate.setTime(currentDate.getTime() + 3600000); // Add an hour
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hours;
    }

    /**
     * 分钟
     */
    public static LinkedHashMap<String, String> getMins(int min) {
        LinkedHashMap<String, String> mins = new LinkedHashMap<>();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60 / min; j++) {
                mins.put(String.format("%02d:%02d", i, j * min), "0.00");
            }
        }
        return mins;
    }

    /**
     * 获取指定日期的昨天
     *
     * @param date
     * @return
     */
    public static Date getLastday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的上个月某一天
     *
     * @param date
     * @return
     */
    public static Date getLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);

        return calendar.getTime();
    }

    /**
     * 获取指定日期的上周某一天
     *
     * @param date
     * @return
     */
    public static Date getLastWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, -1);

        return calendar.getTime();
    }

    /**
     * 获取指定日期的去年今天
     *
     * @param date
     * @return
     */
    public static Date getLastYearToday(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取指定天的开始时间
     *
     * @return
     */
    public static String getStartTimeOfCurrentDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        setMinTimeOfDay(calendar);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(calendar.getTime());
    }


    /**
     * 获取指定天的开始时间
     *
     * @return
     */
    public static Date getStartTimeOfCurrentDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        setMinTimeOfDay(calendar);
        return calendar.getTime();
    }

    /**
     * 获取指定天的结束时间
     *
     * @return
     */
    public static String getEndTimeOfCurrentDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        setMaxTimeOfDay(calendar);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(calendar.getTime());
    }


    /**
     * 获取指定周的开始时间
     *
     * @return
     */
    public static String getStartTimeOfCurrentWeek(Date date) {
        Calendar calendar = getStartTimeOfCurrentWeekCal(date);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取指定周的开始时间
     *
     * @return
     */
    public static Calendar getStartTimeOfCurrentWeekCal(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        setMinTimeOfDay(calendar);
        return calendar;
    }


    /**
     * 获取指定周的结束时间
     *
     * @return
     */
    public static String getEndTimeOfCurrentWeek(Date date) {
        Calendar calendar = getEndTimeOfCurrentWeekCal(date);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取指定周的结束时间
     *
     * @return
     */
    public static Calendar getEndTimeOfCurrentWeekCal(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        setMaxTimeOfDay(calendar);
        return calendar;
    }

    /**
     * 获取指定年每个月起始天
     *
     * @param year
     * @return
     */
    public static Map<String, List<String>> getMonthsByYear(String year) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, List<String>> data = new HashMap<>();
        if (year == null) {
            return null;
        }
        List<String> startTimes = new ArrayList<>();
        List<String> endTimes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Date date = null;
            try {
                date = dateFormat.parse(String.format("%s-%02d-01", year, i + 1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startTimes.add(getStartTimeOfCurrentMonth(date));
            endTimes.add(getEndTimeOfCurrentMonth(date));
        }

        data.put("startTimes", startTimes);
        data.put("endTimes", endTimes);


        return data;
    }

    /**
     * 获取指定年每个季度起始时间
     *
     * @param year
     * @return
     */
    public static Map<String, List<String>> getQuarterByYear(String year) {
        Map<String, List<String>> data = new HashMap<>();
        if (null == year) {
            return null;
        }
        List<String> startTimes = new ArrayList<>();
        List<String> endTimes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            startTimes.add(String.format("%s-%02d-01 00:00:00", year, i * 3 + 1));
            endTimes.add(String.format("%s-%02d-31 23:59:59", year, i * 3 + 3));
        }

        data.put("startTimes", startTimes);
        data.put("endTimes", endTimes);


        return data;

    }


    /**
     * 获取指定月的开始时间
     *
     * @return
     */
    public static String getStartTimeOfCurrentMonth(Date date) {
        Calendar calendar = getStartTimeOfCurrentMonthCal(date);
        return sdf.format(calendar.getTime());
    }

    public static Calendar getStartTimeOfCurrentMonthCal(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        setMinTimeOfDay(calendar);
        return calendar;
    }


    /**
     * 获取指定月的结束时间
     *
     * @return
     */
    public static String getEndTimeOfCurrentMonth(Date date) {
        Calendar calendar = getEndTimeOfCurrentMonthCal(date);
        return sdf.format(calendar.getTime());
    }

    public static Calendar getEndTimeOfCurrentMonthCal(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        int maxMonthDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), maxMonthDay);
        setMaxTimeOfDay(calendar);
        return calendar;
    }


    /**
     * 获取指定年的开始时间 注意月份要减1
     *
     * @return
     */
    public static String getStartTimeOfCurrentYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), 0, 1);
        setMinTimeOfDay(calendar);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取指定年的结束时间 注意月份要减1
     *
     * @return
     */
    public static String getEndTimeOfCurrentYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), 11, 31);
        setMaxTimeOfDay(calendar);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 设置当天的开始时间
     *
     * @param calendar
     */
    public static void setMinTimeOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 设置当天的结束时间
     *
     * @param calendar
     */
    public static void setMaxTimeOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    //今日最早
    public static Date atStartOfDay() {
        return DateUtils.LocalDateTimeToDate(LocalDate.now().atTime(LocalTime.MIN));
    }

    //当月最早时间
    public static Date atStartMonthOfDay() {
        // 获取当前年月
        YearMonth currentYearMonth = YearMonth.now();

        // 获取当月最早时间（当月第一天的 00:00:00）
        LocalDateTime firstDayOfMonth = currentYearMonth.atDay(1).atStartOfDay();
        return DateUtils.LocalDateTimeToDate(firstDayOfMonth);
    }

    //当月最晚时间
    public static Date atEndMonthOfDay() {
        // 获取当前年月
        YearMonth currentYearMonth = YearMonth.now();
        // 获取当月最晚时间（当月最后一天的 23:59:59.999999999）
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();
        LocalDateTime lastDayDateTime = lastDayOfMonth.atTime(LocalTime.MAX);
        return DateUtils.LocalDateTimeToDate(lastDayDateTime);
    }

    //今日最晚
    public static Date atEndOfDay() {
        return DateUtils.LocalDateTimeToDate(LocalDate.now().atTime(LocalTime.MAX));
    }

    /**
     * 查询日期对应星期 周一:1;周二:2.....
     */
    public static int dateToWeek(LocalDateTime localDateTime, String dayTime) {
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Instant instant = null;
        if (localDateTime != null) {
            instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        } else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

//            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate date2 = LocalDate.parse(dayTime, fmt);
//            System.out.println(date2.getDayOfWeek());
            Date date = null;
            try {
                date = format.parse(dayTime);
                instant = date.toInstant();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        DayOfWeek day = localDate.getDayOfWeek();

        int weekDay = 0;

        switch (day) {
            case MONDAY:
                weekDay = 1;
                break;
            case FRIDAY:
                weekDay = 5;
                break;
            case WEDNESDAY:
                weekDay = 3;
                break;
            case THURSDAY:
                weekDay = 4;
                break;
            case TUESDAY:
                weekDay = 2;
                break;
            case SATURDAY:
                weekDay = 6;
                break;
            case SUNDAY:
                weekDay = 7;
                break;
        }
        return weekDay;
    }

    /**
     * 将对应时分转为小数; 09:00-->9.0;00:59-->0.59
     *
     * @param localDateTime
     * @return
     */
    public static float stringTimeToFloat(String localDateTime) {
        return Float.valueOf(localDateTime.replace(":", "."));
    }

    /**
     * 将对应小树转为时间; 9.0-->09:00;0.59-->00:59
     *
     * @param timeValue
     * @return
     */
    public static String floatToTime(float timeValue) {

        String time = Float.toString(timeValue).replace(".", ":");

        String[] times = time.split(":");
        if (times[0].length() == 1) {
            times[0] = "0" + times[0];
        }
        if (times[1].length() == 1) {
            times[1] = times[1] + "0";
        }
        return String.format("%s:%s", times[0], times[1]);
    }


    /**
     * 返回正值是代表左侧日期大于参数日期，反之亦然，日期格式必须一致
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean compareToDate(Date dateA, Date dateB) {
        return dateA.compareTo(dateB) > 0 ? true : false;
    }

    /**
     * 取当前日期
     *
     * @param pattern 格式
     */
    public static String toDay(String pattern) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(date);
    }

    /**
     * @param dateStr 转成date
     * @return
     */
    public static Date parseDate(String dateStr) {
        Date d = null;
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, parsePatterns);
        } catch (Exception e) {
            d = new Date();
        }
        return d;
    }

    /**
     * 把所有日期字符串 统一输出 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static String formatDate(String dateStr) {
        Date d = null;
        try {
            d = org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, parsePatterns);
            return sdf.format(d);
        } catch (Exception e) {
            d = new Date();
            return sdf.format(d);
        }
    }

    public static String format(Date d) {
        return sdf.format(d);
    }

    /**
     * 获取当天每小时的开始和结束时间
     *
     * @param date
     * @return
     */
    public static List<Map<String, String>> getDayList(Date date) {
        List<Map<String, String>> dayList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Map<String, String> map = new HashMap<>();

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.clear();
            startCalendar.setTime(date);
            startCalendar.set(Calendar.HOUR_OF_DAY, i);
            startCalendar.set(Calendar.SECOND, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.MILLISECOND, 0);
            map.put("start", sdf.format(startCalendar.getTime()));

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.clear();
            endCalendar.setTime(date);
            endCalendar.set(Calendar.HOUR_OF_DAY, i);
            endCalendar.set(Calendar.SECOND, 59);
            endCalendar.set(Calendar.MINUTE, 59);
            endCalendar.set(Calendar.MILLISECOND, 999);

            map.put("end", sdf.format(endCalendar.getTime()));
            dayList.add(map);
        }
        return dayList;
    }

    /**
     * 提供字符串开始，结束日期 返回集合内每天的日期字符串
     *
     * @param startTimeStr
     * @param endTimeStr
     * @return
     */
    public static List<String> getDayList(String startTimeStr, String endTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将字符串转换为LocalDate对象
        LocalDate startTime = LocalDate.parse(startTimeStr, formatter);
        LocalDate endTime = LocalDate.parse(endTimeStr, formatter);

        List<String> dateStrings = new ArrayList<>();
        LocalDate currentDate = startTime;

        // 遍历日期范围
        while (!currentDate.isAfter(endTime)) {
            dateStrings.add(currentDate.format(formatter));
            currentDate = currentDate.plusDays(1);
        }

        return dateStrings;
    }


    public static List<String> getDayList2(String startTimeStr, String endTimeStr) {
        // 定义日期时间格式
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // 解析字符串为 LocalDateTime
        LocalDateTime startDate = LocalDateTime.parse(startTimeStr, inputFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endTimeStr, inputFormatter);

        // 创建一个列表来存储所有的日期
        List<String> dates = new ArrayList<>();

        // 遍历并添加每个月份
        for (LocalDateTime dateTime = startDate; !dateTime.isAfter(endDate.minusDays(1)); dateTime = dateTime.plusDays(1)) {
            // 格式化当前日期为 yyyy-MM
            String formattedDate = dateTime.format(outputFormatter);
            // 添加到集合中
            if (!formattedDate.equals("2024-10")) { // 确保不包括10月的数据
                dates.add(formattedDate);
            }
        }
        return dates;
    }


    public static List<String> getMonList(String startTimeStr, String endTimeStr) {
        // 定义日期时间格式
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // 解析字符串为 LocalDate
        LocalDate startDate = LocalDate.parse(startTimeStr, inputFormatter);
        LocalDate endDate = LocalDate.parse(endTimeStr, inputFormatter);

        // 创建一个列表来存储所有的月份
        List<String> months = new ArrayList<>();

        // 添加开始月份
        months.add(startDate.format(outputFormatter));

        // 遍历并添加每个月份
        while (startDate.isBefore(endDate)) { // 确保在进入下个月前检查
            // 跳到下个月的第一天
            startDate = startDate.with(TemporalAdjusters.firstDayOfNextMonth());
            // 只有当当前日期不在结束日期之后时才添加
            if (!startDate.isAfter(endDate.minusDays(1))) {
                months.add(startDate.format(outputFormatter));
            }
        }

        // 输出结果
        for (String month : months) {
            System.out.println(month);
        }
        return months;
    }

    public static String nextDay(String time) {
        // 定义日期时间格式
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 解析字符串为 YearMonth
        YearMonth yearMonth = YearMonth.parse(time, inputFormatter);

        // 获取下一个月
        YearMonth nextYearMonth = yearMonth.plusMonths(1);

        // 获取下一个月的第一天
        LocalDate nextMonthFirstDay = nextYearMonth.atDay(1);
        return nextMonthFirstDay.format(outputFormatter);
    }

    /**
     * 每天的开始时间和结束时间
     *
     * @param date
     * @return
     */
    public static List<Map<String, String>> getWeekList(Date date) {
        List<Map<String, String>> dayList = new ArrayList<>();
        Calendar c = getStartTimeOfCurrentWeekCal(date);
        for (int i = 0; i < 7; i++) {
            Map<String, String> map = new HashMap<>();

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(c.getTime());
            startCalendar.add(Calendar.DAY_OF_WEEK, i);
            map.put("start", sdf.format(startCalendar.getTime()));

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.clear();
            endCalendar.setTime(startCalendar.getTime());
            endCalendar.set(Calendar.HOUR_OF_DAY, 23);
            endCalendar.set(Calendar.SECOND, 59);
            endCalendar.set(Calendar.MINUTE, 59);
            endCalendar.set(Calendar.MILLISECOND, 999);

            map.put("end", sdf.format(endCalendar.getTime()));
            dayList.add(map);
        }
        return dayList;
    }

    /**
     * 获取<code>date</code>所有月 每天的开始和结束时间
     *
     * @param date
     * @return
     */
    public static List<Map<String, String>> getMonthList(Date date) {
        List<Map<String, String>> dayList = new ArrayList<>();
        Calendar c = getStartTimeOfCurrentMonthCal(date);
        int maxDate = getCurrentMonthDay(c.getTime());
        for (int i = 0; i < maxDate; i++) {
            Map<String, String> map = new HashMap<>();

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(c.getTime());
            startCalendar.add(Calendar.DAY_OF_WEEK, i);
            map.put("start", sdf.format(startCalendar.getTime()));

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.clear();
            endCalendar.setTime(startCalendar.getTime());
            endCalendar.set(Calendar.HOUR_OF_DAY, 23);
            endCalendar.set(Calendar.SECOND, 59);
            endCalendar.set(Calendar.MINUTE, 59);
            endCalendar.set(Calendar.MILLISECOND, 999);

            map.put("end", sdf.format(endCalendar.getTime()));
            dayList.add(map);
        }
        return dayList;
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        int maxDate = cal.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取<code>date</code>当年的 每月的开始和结束时间
     *
     * @param date
     * @return
     */
    public static List<Map<String, String>> getYearMonth(Date date) {
        Map<String, List<String>> monthsByYear = getMonthsByYear("2023");
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String startTimes = monthsByYear.get("startTimes").get(i);
            String endTimes = monthsByYear.get("endTimes").get(i);
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("start", startTimes);
            stringStringHashMap.put("end", endTimes);
            list.add(stringStringHashMap);
        }
        return list;
    }

    public static Map<String, String> getDays30(Date date) {
        Map<String, String> days = new LinkedHashMap<>();

        Calendar calendar = Calendar.getInstance();


        // 创建 SimpleDateFormat 对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 遍历每一天并格式化输出
        for (int day = 29; day >= 0; day--) {
            calendar.setTime(date);
            calendar.add(calendar.DAY_OF_YEAR, -day);
            String formattedDate = sdf.format(calendar.getTime());
            days.put(formattedDate, "0.00");
        }
        return days;
    }


    public static Map<String, String> getDayMouth(Date date, Integer start, Integer end) {
        Map<String, String> days = new LinkedHashMap<>();

        Calendar calendar = Calendar.getInstance();


        // 创建 SimpleDateFormat 对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 遍历每一天并格式化输出
        for (int day = start; day < end; day++) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(calendar.HOUR, day);
            String formattedDate = sdf.format(calendar.getTime());
            days.put(formattedDate.substring(11, 13), "0.00");
        }
        return days;
    }

    /**
     * 判断时间段1和时间段2是否有交集
     *
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    public static boolean doTimeRangesOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        // 检查两个时间段是否有交集，即一个时间段的结束时间在另一个时间段的开始时间之前
        return !end1.isBefore(start2) && !end2.isBefore(start1);
    }

    /**
     * 判断时间段1和时间段2是否有交集
     *
     * @param start11
     * @param end11
     * @param start22
     * @param end22
     * @return
     */
    public static boolean doTimeRangesOverlap(Date start11, Date end11, Date start22, Date end22) {
        // 检查时间段2的开始时间是否在时间段1内，同时结束时间也在时间段1内
        LocalDateTime start1 = dateTolocalDateTime(start11);
        LocalDateTime end1 = dateTolocalDateTime(end11);
        LocalDateTime start2 = dateTolocalDateTime(start22);
        LocalDateTime end2 = dateTolocalDateTime(end22);

        return doTimeRangesOverlap(start1, end1, start2, end2);
    }

    /**
     * date转localDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateTolocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 获取指定日期的前几天
     *
     * @param date
     * @return
     */
    public static List getLastSomeday(Date date, Integer time) {
        List<String> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = time; i >= 0; i--) {
            calendar.setTime(date);
            calendar.add(calendar.DATE, -i);
            String formattedDate = sdf.format(calendar.getTime());
            list.add(formattedDate);
        }
        return list;
    }

    public static Date getEndTimeOfCurrentDate(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(today);
        setMaxTimeOfDay(calendar);
        return calendar.getTime();
    }


    /**
     * 根据俩个日期，返回日期区间内每天的时间集合
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static LinkedHashMap<String, String> getDatesBetween(String startDate, String endDate) {
        LinkedHashMap<String, String> dateList = new LinkedHashMap<>();

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        while (!start.isAfter(end)) {
            dateList.put(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), "0.00");
            start = start.plusDays(1);
        }

        return dateList;
    }

    public static LinkedHashMap<String, String> getDatesBetweenMonths(Integer startYear, Integer startMonth, Integer endYear, Integer endMonth) {
        LinkedHashMap<String, String> dateList = new LinkedHashMap<>();
        // 定义起始日期和结束日期
        LocalDate startDate = LocalDate.of(startYear, startMonth, 1);
        LocalDate endDate = LocalDate.of(endYear, endMonth, 1);

        // 使用 Period.between() 方法计算两个日期之间的差值
        Period period = Period.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1));

        // 获取差值中的月份部分
        int months = period.getMonths();

        // 组装结果数组
        List<String> monthList = new ArrayList<>();
        LocalDate currentDate = startDate;
        for (int i = 0; i <= months; i++) {
            monthList.add(currentDate.toString());
            currentDate = currentDate.plusMonths(1);
        }

        // 将结果数组转换为字符串数组
        String[] resultArray = monthList.toArray(new String[0]);

        // 打印结果数组
        for (String month : resultArray) {
            System.out.println(month);
            dateList.put(month.substring(0, 7), "0.00");
        }
        return dateList;
    }

    public static Date monthToDate(String dateString) {
        // 定义日期格式化模式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 使用解析方法将字符串解析为 LocalDate 对象
        LocalDate localDate = LocalDate.parse(dateString + "-01", formatter);

        // 将 LocalDate 对象转换为 java.util.Date 对象
        Date date = java.sql.Date.valueOf(localDate);

        // 打印结果
        System.out.println("日期：" + date);

        return date;
    }


    public static List<String> getHoursList(int hour) {
        List<String> hours = new ArrayList<>();
        for (int i = 0; i < 24 / hour; i++) {
            hours.add(String.format("%02d:00", i * hour));
        }
        return hours;
    }


    public static List<String> getMonthDaysList(Date date) {
        List<String> days = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 获取当月的天数
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 创建 SimpleDateFormat 对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 遍历每一天并格式化输出
        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            String formattedDate = sdf.format(calendar.getTime());
            days.add(formattedDate);
        }
        return days;
    }

    public static void main(String[] args) {
        LinkedHashMap<String, String> hours = getHours(1);
        for (String s : hours.keySet()) {
            System.out.println(hours.get(s));
            System.out.println(s);
        }
    }
}
