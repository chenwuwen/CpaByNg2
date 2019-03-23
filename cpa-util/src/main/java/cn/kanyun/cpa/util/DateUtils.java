package cn.kanyun.cpa.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Date;

/**
 * 此工具类适用于JDK8(含)之后的版本  
 * 已使用 Instant 替代 Date
 * LocalDateTime 替代 Calendar
 * DateTimeFormatter 代替 SimpleDateFormat
 *
 * @Description: 日期工具类
 */
public class DateUtils {

    /**
     * 日期格式yyyy-MM-dd
     */
    public static final String pattern_date_horizontal_line = "yyyy-MM-dd";

    /**
     * 日期格式yyyy/MM/dd
     */
    public static final String pattern_date_oblique_line = "yyyy/MM/dd";

    /**
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     */
    public static final String pattern_time_horizontal_line = "yyyy-MM-dd HH:mm:ss";

    /**
     * 描述：日期格式化
     *
     * @param date 日期
     * @return 输出格式为 yyyy-MM-dd 的字串
     */
    public static String formatDate(Instant date) {
        return formatDate(date, pattern_time_horizontal_line);
    }

    /**
     * 描述：日期格式化
     *
     * @param date    日期
     * @param pattern 格式化类型
     * @return
     */
    public static String formatDate(Instant date, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 描述：解析日期字串
     *
     * @param dateStr 日期字串
     * @return 按 yyyy-MM-dd HH:mm:ss 格式解析
     */
    public static LocalDateTime parseString(String dateStr) {
        return parseString(dateStr, pattern_time_horizontal_line);
    }

    /**
     * 描述：解析日期字串
     *
     * @param dateStr 日期字串
     * @param pattern 字串日期格式
     * @return 对应日期类型数据
     */
    public static LocalDateTime parseString(String dateStr, String pattern) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        if (!StringHelper.isEmpty(dateStr)) {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, dateFormat);
            return dateTime;
        }
        return null;
    }

    /**
     * 描述：获取指定日期的中文星期数
     *
     * @param date 指定日期
     * @return 星期数，如：星期一
     */
    public static String getWeekStr(Instant date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());
//        返回的是枚举类型
        DayOfWeek week = localDateTime.getDayOfWeek();
        String weekStr = "";
        switch (week) {
            case MONDAY:
                weekStr = "星期一";
                break;
            case TUESDAY:
                weekStr = "星期二";
                break;
            case WEDNESDAY:
                weekStr = "星期三";
                break;
            case THURSDAY:
                weekStr = "星期四";
                break;
            case FRIDAY:
                weekStr = "星期五";
                break;
            case SATURDAY:
                weekStr = "星期六";
                break;
            default:  // SUNDAY
                weekStr = "星期日";
        }
        return weekStr;
    }

    /**
     * 描述：间隔时间
     *
     * @param date1
     * @param date2
     * @return 毫秒数
     */
    public static long getDateMiliDispersion(Date date1, Date date2) {
        if ((date1 == null) || (date2 == null)) {
            return 0L;
        }

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        return time1 - time2;
    }

    /**
     * 描述：间隔天数
     *
     * @param date1
     * @param date2
     * @return 天数
     */
    public static int getDateDiff(Date date1, Date date2) {
        if ((date1 == null) || (date2 == null)) {
            return 0;
        }
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long diff = time1 - time2;

        Long longValue = new Long(diff / 86400000L);
        return longValue.intValue();
    }

    /**
     * 描述：获取指定日期之前多少天的日期
     *
     * @param date 指定日期
     * @param day  天数
     * @return 日期
     */
    public static Date getDataDiff(Date date, int day) {
        if (date == null) {
            return null;
        }
        long time = date.getTime();
        time -= 86400000L * day;
        return new Date(time);
    }

    /**
     * 描述：获取当前周
     *
     * @return
     */
    public static int getCurrentWeek() {
        LocalDate localDate = LocalDate.now();
//        第一个参数代表周是按周几开始，第二个是限定第一个自然周最少要几天。结合ISO标准我们应当像上面那样进行初始化。
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
//        上下两个实例化WeekFields的方法都可以
//        WeekFields weekFields = WeekFields.ISO;
//        weekFields的有两个获取自然周的实例方法weekOfYear()和weekOfWeekBasedYear()他们的差异当weekOfYear()第一周少于最小天数时会返回0,另一个则是返回1或者去年的末尾周
        int week = localDate.get(weekFields.weekOfWeekBasedYear());
        return week;
    }

    /**
     * 描述：获取中文当前周
     *
     * @return
     */
    public static String getCurrentWeekStr() {
        return getWeekStr(Instant.now());
    }

    /**
     * 描述：获取本年
     *
     * @return
     */
    public static int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    /**
     * 描述：获取本月
     *
     * @return
     */
    public static int getCurrentMonth() {
        return LocalDate.now().getMonthValue();
    }

    /**
     * 描述：获取本月的当前日期数
     *
     * @return
     */
    public static int getCurrentDay() {
        return LocalDate.now().getDayOfMonth();
    }


    /**
     * 描述：去除日期字串中原“-”和“:”
     *
     * @param dateTime日期字串
     * @return
     */
    public static String formatString(String dateTime) {
        if ((dateTime != null) && (dateTime.length() >= 8)) {
            String formatDateTime = dateTime.replaceAll("-", "");
            formatDateTime = formatDateTime.replaceAll(":", "");
            String date = formatDateTime.substring(0, 8);
            return date;
        }

        return "";
    }

    /**
     * 描述：当前时间与指定时间的差
     *
     * @param str yyyy-MM-dd HH:mm:ss 格式的日期
     * @return 时间差，单位：秒
     */
    public static int getTimesper(String str, String pattern) {
        if ((str == null) || ("".equals(str))) {
            return 0;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(str, dateTimeFormatter);
        int ret =  LocalDateTime.now().getSecond() - localDateTime.getSecond();
        return ret;
    }

    /**
     * 描述：获取16位日期时间，yyyyMMddHHmmss
     *
     * @param dateTime 字串日期
     * @return
     */
    public static String formatDateTime(String dateTime) {
        if ((dateTime != null) && (dateTime.length() >= 8)) {
            String formatDateTime = dateTime.replaceAll("-", "");
            formatDateTime = formatDateTime.replaceAll(":", "");
            String date = formatDateTime.substring(0, 8);
            String time = formatDateTime.substring(8).trim();
            for (int i = time.length(); i < 6; ++i) {
                time = time + "0";
            }
            return date + time;
        }

        return "";
    }

    /**
     * 描述：获取16位日期时间，yyyyMMddHHmmss
     *
     * @param date 日期
     * @return
     */
    public static String formatDateTime(Instant date) {
        String dateTime = formatDate(date);
        return formatDateTime(dateTime);
    }

    /**
     * 获取两个日期中的最小日期
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static LocalDateTime min(LocalDateTime beginDate, LocalDateTime endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.isAfter(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    /**
     * 获取两个日期中的最大日期
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static LocalDateTime max(LocalDateTime beginDate, LocalDateTime endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.isBefore(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    /**
     * 获取某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static LocalDate getStartMonthDate(int year, int month) {
        LocalDate localDate = LocalDate.of(year, month - 1, 1);
        return localDate;
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static LocalDate getEndMonthDate(int year, int month) {
        LocalDate localDate = LocalDate.of(year, month, 1);
//        localDate.lengthOfMonth() 获取某个时间的当月天数
        LocalDate result = LocalDate.of(year, month, localDate.lengthOfMonth());
        return result;
    }

    /**
     * 获取昨天的开始时间
     *
     * @return 默认格式 Wed May 31 14:47:18 CST 2017
     */
    public static LocalDateTime getBeginDayOfYesterday() {
        LocalDateTime localDateTime = getDayStartTime(LocalDateTime.now().plusDays(-1));
        return localDateTime;
    }

    /**
     * 获取昨天的结束时间
     *
     * @return 默认格式 Wed May 31 14:47:18 CST 2017
     */
    public static LocalDateTime getEndDayOfYesterDay() {
        LocalDateTime localDateTime = getDayEndTime(LocalDateTime.now().plusDays(-1));
        return localDateTime;
    }

    /**
     * 获取明天的开始时间
     *
     * @return 默认格式 Wed May 31 14:47:18 CST 2017
     */
    public static LocalDateTime getBeginDayOfTomorrow() {
        LocalDateTime localDateTime = getDayStartTime(LocalDateTime.now().plusDays(1));

        return getDayEndTime(localDateTime);
    }

    /**
     * 获取明天的结束时间
     *
     * @return 默认格式 Wed May 31 14:47:18 CST 2017
     */
    public static LocalDateTime getEndDayOfTomorrow() {
        LocalDateTime localDateTime = getDayEndTime(LocalDateTime.now().plusDays(1));

        return localDateTime;
    }

    /**
     * 获取当天的开始时间
     *
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static LocalDateTime getDayBegin() {
        return getDayStartTime(LocalDateTime.now());
    }

    /**
     * 获取当天的结束时间
     *
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static LocalDateTime getDayEnd() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return getDayEndTime(localDateTime);
    }

    /**
     * 获取某个日期的开始时间
     *
     * @param d
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static LocalDateTime getDayStartTime(LocalDateTime d) {
        LocalDateTime localDateTime = LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), 0, 0, 0, 000);
        return localDateTime;
    }

    /**
     * 获取某个日期的结束时间
     *
     * @param d
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static LocalDateTime getDayEndTime(LocalDateTime d) {
        LocalDateTime endTime = null;
        if (null != d) {
            endTime = LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), 23, 59, 59, 999);
        }
        return endTime;
    }

}