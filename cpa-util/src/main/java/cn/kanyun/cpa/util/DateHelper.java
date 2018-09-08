package cn.kanyun.cpa.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *   
 * 
 * @Title: DateHelper.java
 * @Package com.jarvis.base.util
 * @Description:日期工具类
 * @author Jack 
 * @date 2017年9月2日 下午2:33:46
 * @version V1.0  
 */
public class DateHelper {

	/**
	 * 日期格式yyyy-MM-dd
	 */
	public static final String pattern_date = "yyyy-MM-dd";

	/**
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 */
	public static final String pattern_time = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 描述：日期格式化
	 * 
	 * @param date
	 *            日期
	 * @return 输出格式为 yyyy-MM-dd 的字串
	 */
	public static String formatDate(Date date) {
		return formatDate(date, pattern_time);
	}

	/**
	 * 描述：日期格式化
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式化类型
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 描述：解析日期字串
	 * 
	 * @param dateStr
	 *            日期字串
	 * @return 按 yyyy-MM-dd HH:mm:ss 格式解析
	 */
	public static Date parseString(String dateStr) {
		return parseString(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 描述：解析日期字串
	 * 
	 * @param dateStr
	 *            日期字串
	 * @param pattern
	 *            字串日期格式
	 * @return 对应日期类型数据
	 */
	public static Date parseString(String dateStr, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			if (!StringHelper.isEmpty(dateStr)) {
				return dateFormat.parse(dateStr);
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
			System.err.println(dateStr + "转换成日期失败，可能是不符合格式：" + pattern);
		}
		return null;
	}

	/**
	 * 描述：获取指定日期的中文星期数
	 * 
	 * @param date
	 *            指定日期
	 * @return 星期数，如：星期一
	 */
	public static String getWeekStr(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(7);
		--week;
		String weekStr = "";
		switch (week) {
		case 0:
			weekStr = "星期日";
			break;
		case 1:
			weekStr = "星期一";
			break;
		case 2:
			weekStr = "星期二";
			break;
		case 3:
			weekStr = "星期三";
			break;
		case 4:
			weekStr = "星期四";
			break;
		case 5:
			weekStr = "星期五";
			break;
		case 6:
			weekStr = "星期六";
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
	 * @param date
	 *            指定日期
	 * @param day
	 *            天数
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
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(7);
		--week;
		if (week == 0) {
			week = 7;
		}
		return week;
	}

	/**
	 * 描述：获取中文当前周
	 * 
	 * @return
	 */
	public static String getCurrentWeekStr() {
		return getWeekStr(new Date());
	}

	/**
	 * 描述：获取本年
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(1);
	}

	/**
	 * 描述：获取本月
	 * 
	 * @return
	 */
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(2) + 1;
	}

	/**
	 * 描述：获取本月的当前日期数
	 * 
	 * @return
	 */
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(5);
	}

	/**
	 * 描述：当前时间与指定时间的差
	 * 
	 * @param str
	 *            秒数
	 * @return 时间差，单位：秒
	 */
	public static int getUnixTime(String str) {
		if ((str == null) || ("".equals(str))) {
			return 0;
		}
		try {
			long utime = Long.parseLong(str) * 1000L;
			Date date1 = new Date(utime);

			Date date = new Date();

			long nowtime = (date.getTime() - date1.getTime()) / 1000L;
			return (int) nowtime;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("获取时差失败");
		}
		return 0;
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
	 * @param str
	 *            yyyy-MM-dd HH:mm:ss 格式的日期
	 * @return 时间差，单位：秒
	 */
	public static int getTimesper(String str) {
		if ((str == null) || ("".equals(str))) {
			return 0;
		}
		try {
			Date date1 = new Date(Long.parseLong(str));
			Date date = new Date();
			long nowtime = (date.getTime() - date1.getTime()) / 1000L;
			return (int) nowtime;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("日期转换出错");
		}
		return 0;
	}

	/**
	 * 描述：获取16位日期时间，yyyyMMddHHmmss
	 * 
	 * @param dateTime
	 *            字串日期
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
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatDateTime(Date date) {
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
	public static Date min(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
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
	public static Date max(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
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
	public static Date getStartMonthDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getTime();
	}

	/**
	 * 获取某年某月的最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getEndMonthDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}

	/**
	 * 获取昨天的开始时间
	 *
	 * @return 默认格式 Wed May 31 14:47:18 CST 2017
	 */
	public static Date getBeginDayOfYesterday() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取昨天的结束时间
	 *
	 * @return 默认格式 Wed May 31 14:47:18 CST 2017
	 */
	public static Date getEndDayOfYesterDay() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取明天的开始时间
	 *
	 * @return 默认格式 Wed May 31 14:47:18 CST 2017
	 */
	public static Date getBeginDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	/**
	 * 获取明天的结束时间
	 *
	 * @return 默认格式 Wed May 31 14:47:18 CST 2017
	 */
	public static Date getEndDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取当天的开始时间
	 *
	 * @return yyyy-MM-dd HH:mm:ss  格式
	 */
	public static Date getDayBegin() {
    	/*
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();*/
		Date date = new Date();
		return getDayStartTime(date);
	}

	/**
	 * 获取当天的结束时间
	 *
	 * @return yyyy-MM-dd HH:mm:ss  格式
	 */
	public static Date getDayEnd() {
        /*Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();*/
		Date date = new Date();
		return getDayEndTime(date);
	}

	/**
	 * 获取某个日期的开始时间
	 *
	 * @param d
	 * @return yyyy-MM-dd HH:mm:ss  格式
	 */
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d) {
            calendar.setTime(d);
        }
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 获取某个日期的结束时间
	 *
	 * @param d
	 * @return yyyy-MM-dd HH:mm:ss  格式
	 */
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d) {
            calendar.setTime(d);
        }
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

}