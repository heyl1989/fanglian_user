package com.flzc.fanglian.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.widget.RemoteViews.ActionException;

public class DateUtils {

	private static SimpleDateFormat hourFormat = new SimpleDateFormat(
			"HH:mm:ss");
	private static SimpleDateFormat dayFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat secFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat simpledayFormat = new SimpleDateFormat(
			"MM-dd");
	private static SimpleDateFormat formatDay = new SimpleDateFormat(
			"yyyy年MM月dd日");

	public static int getNextMonth(int month) {
		if (month > 1) {
			return 12;
		} else {
			return month - 1;
		}
	}

	/**
	 * 
	 * @Title: getTime 
	 * @Description:  根据毫秒值格式化时间
	 * @param pattern
	 * @param time
	 * @return
	 * @return: String
	 */
	// yyyy-MM-dd HH:mm:ss
	public static String getTime(String pattern, long time) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date d1 = new Date(time);
		return format.format(d1);
	}

	/**
	 * 在start日期上增加day天
	 * 
	 * @param start
	 * @param day
	 *            增加的天数
	 * @param type
	 *            Calendar.DATE...
	 * @return java.sql.Date
	 * @throws ActionException
	 */
	public static Date addDate(Date start, int num, int type) {
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.add(type, num);

		return new Date(c.getTimeInMillis());
	}

	public static String getNextMonthName(int month) {
		if (month == 12) {
			return monthArray[0];
		} else {
			return monthArray[month];
		}
	}

	public static int getNextMonth(String _month) {
		if (_month == null) {
			return 1;
		}
		Integer month = Integer.parseInt(_month);
		if (month == 12) {
			return 1;
		} else {
			return month + 1;
		}
	}

	public static int getBeforeMonth(String _month) {
		if (_month == null) {
			return 1;
		}
		Integer month = Integer.parseInt(_month);
		if (month == 1) {
			return 12;
		} else {
			return month - 1;
		}
	}

	public static String getBeforeMonthName(String _month) {
		if (_month == null) {
			return "未知";
		}
		Integer month = Integer.parseInt(_month);
		return getBeforeMonthName(month);
	}

	public static String getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getMonthName(calendar.get(Calendar.MONTH));
	}

	public static String getBeforeMonthName(int month) {
		if (month == 1) {
			return monthArray[11];
		} else {
			return monthArray[month - 2];
		}
	}

	private static String[] monthArray = { "一月", "二月", "三月", "四月", "五月", "六月",
			"七月", "八月", "九月", "十月", "十一月", "十二月" };

	public static String getMonthName(int month) {
		return monthArray[month];
	}

	public static String getMonthName(String _month) {
		if (_month == null) {
			return "未知";
		}
		Integer month = Integer.parseInt(_month);
		return monthArray[month - 1];
	}

	public static String getNextMonthName(String _month) {
		if (_month == null) {
			return "未知";
		}
		Integer month = Integer.parseInt(_month);
		if (month == 12) {
			return monthArray[0];
		}
		return monthArray[month];
	}

	public static String formatDay(Date date) {
		return dayFormat.format(date);
	}

	/**
	 * 获取某个月份的第一天
	 * 
	 * @param date
	 *            输入的日期
	 * 
	 */
	public static Date firstDayOfMonth(Date date) {
		Date firstDay = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int index = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DATE, index);
			firstDay = new Date(calendar.getTimeInMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return firstDay;
	}

	public static String formatTime(long t, boolean shortable) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = null;

		String now = dayFormat.format(c.getTime());
		c.setTimeInMillis(t);
		String tTime = dayFormat.format(c.getTime());

		// 同一天就使用短的
		if (now.equals(tTime) && shortable) {
			formatter = hourFormat;
		} else {
			formatter = secFormat;
		}

		String dateString = formatter.format(c.getTime());
		return dateString;
	}

	public static String formatDate(long t) {
		return dayFormat.format(t);
	}

	public static String formatSec(long t) {
		return secFormat.format(t);
	}

	public static String formatDateDay(long t) {
		return formatDay.format(t);
	}

	public static String simpledayFormat(Date date) {
		return simpledayFormat(date.getTime());
	}

	public static String simpledayFormat(long t) {
		return simpledayFormat.format(t);
	}

	public static String formatTime(long t) {
		return formatTime(t, true);
	}

	public static String formatTime(Timestamp time, boolean shortable) {
		return formatTime(time.getTime(), shortable);
	}

	public static String formatTime(Timestamp time) {
		return formatTime(time.getTime(), true);
	}

	/**
	 * 类型转换
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public final static Timestamp strToDateTime(String dateStr) {
		int len = dateStr.length();
		String format = "yyyy-MM-dd HH:mm:ss.SSS".substring(0, len);
		DateFormat df = new SimpleDateFormat(format);
		Date timeDate = null;
		Timestamp dateTime = null;
		try {
			timeDate = df.parse(dateStr);
			dateTime = new java.sql.Timestamp(timeDate.getTime());// sql类型
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateTime;
	}

	/**
	 * 类型转换
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public final static Date strToDate(String dateStr) {
		int len = dateStr.length();
		String format = "yyyy-MM-dd HH:mm:ss.SSS".substring(0, len);
		DateFormat df = new SimpleDateFormat(format);
		Date timeDate = null;
		try {
			timeDate = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeDate;
	}

	/**
	 * 取当前时间
	 * 
	 * @return
	 */
	public static Timestamp currentDateTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String currentStrTime() {
		return formatTime(currentTime());
	}

	public static long currentTime() {
		return System.currentTimeMillis();
	}

	public static Timestamp longToTimestamp(long t) {
		return new Timestamp(t);
	}

	public static java.sql.Date longToDate(long t) {
		return new java.sql.Date(t);
	}

	public static String TimestampToString(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return DateToString(timestamp);
	}

	public static String DateToString(Date date) {
		if (date == null) {
			return null;
		}
		return secFormat.format(date);
	}

	public static long daysOfTwo(Date sDate, Date eDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(sDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(eDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (toCalendar.getTime().getTime() - fromCalendar.getTime()
				.getTime()) / (1000 * 60 * 60 * 24);
	}

	public static List<Date> datesBetween(Date start, Date end) {
		long differDays = daysOfTwo(start, end);
		System.out.println(differDays);
		List<Date> list = new ArrayList<Date>();
		try {
			Calendar cNow = Calendar.getInstance();
			cNow.setTime(start);
			Date realDate = null;
			list.add(start);
			if (differDays >= 0) {
				for (int i = 0; i < differDays; i++) {
					cNow.add(Calendar.DATE, 1);
					realDate = new Date(cNow.getTimeInMillis());
					list.add(realDate);
				}
			} else {
				for (long i = differDays; i < 0; i++) {
					cNow.add(Calendar.DATE, -1);
					realDate = new Date(cNow.getTimeInMillis());
					list.add(realDate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}

	/**
	 * 获得指定日期的后3天数据
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 2);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 取得当前时间的前n天数据
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static String getStatetime(int n) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -n + 1);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	/**
	 * 比较两个日期之间的大小
	 * 
	 * @param d1
	 * @param d2
	 * @return 前者大于后者返回true 反之false
	 */
	public static boolean compareDate(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);

		int result = c1.compareTo(c2);
		if (result >= 0)
			return true;
		else
			return false;
	}
}
