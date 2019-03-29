package com.jjn.mall.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author nibaoliang
 * Description:
 * Copyright: Copyright (c) 2018
 * Company: HBY
 * 2018年4月23日
 * @version 1.0
 */
@SuppressWarnings("all")
public class DateUtils {

	/**
	 * 当前时间的String类型
	 */
	public static String getDate() {
		// 这是创建的时间
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		String createTime = dateFormat.format(now);
		return createTime;
	}

	public static final long ONE_DAY_LONG = 86400000;
	private static DateUtils classInstance = new DateUtils();

	public static DateUtils getInstance() {
		return classInstance;
	}

	/**
	 * Timestamp时间类型转换String Created on 2014-6-6
	 * <p>
	 * Discription:[]
	 * </p>
	 * 
	 * @author:[]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param time
	 * @param pattern
	 * @return String
	 */
	public static String timestamp2string(Timestamp time, String pattern) {
		Date d = new Date(time.getTime());

		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return DateFormatUtils.format(d, pattern);
	}

	/**
	 * Date时间类型转换String Created on 2014-6-6 时间格式yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			date = new Date(System.currentTimeMillis());
		}

		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm";
		}
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * date传null获取当前时间 时间格式yyyy-MM-dd HH:mm Created on 2014-6-6
	 * 
	 * @param date
	 * @return String
	 */
	public static String defaultFormat(Date date) {
		return formatDate(date, null);
	}

	/**
	 * 获取当前时间Date类型 Created on 2014-6-6
	 * 
	 * @return Date
	 */
	public static Date parseDateFormat() {
		SimpleDateFormat fo = new SimpleDateFormat();
		Date date = new java.util.Date(System.currentTimeMillis());
		fo.applyPattern("yyyy-MM-dd");

		try {
			date = fo.parse(DateFormatUtils.format(date, "yyyy-MM-dd"));
		} catch (Exception e) {
		}

		return date;
	}

	/**
	 * 根据Timestamp类型返回2014-06-06格式String Created on 2014-6-6
	 * 
	 * @param time
	 * @return String
	 */
	public static String parseTimestampFormat(Timestamp time) {

		if (time != null && !time.equals("")) {

			return parseDateFormat(new Date(time.getTime()));

		} else {

			return "";
		}

	}

	/**
	 * 根据Date转换String格式yyyy-MM-dd Created on 2014-6-6
	 * 
	 * @param date
	 * @return String
	 */
	public static String parseDateFormat(Date date) {
		SimpleDateFormat fo = new SimpleDateFormat();
		fo.applyPattern("yyyy-MM-dd");
		String retVal = "0000-00-00";
		try {
			retVal = DateFormatUtils.format(date, "yyyy-MM-dd");
		} catch (Exception e) {
		}

		return retVal;
	}

	/**
	 * 根据String返回Timestamp Created on 2014-6-6
	 * 
	 * @param value
	 * @return Timestamp
	 */
	public static Timestamp formatFromYYYYMMDD(String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(value);
		} catch (ParseException e) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	public static Timestamp formatFromYYYYMMDDhhmmss(String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(value);
		} catch (ParseException e) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	public static Date string2Date(String str) {
		if (StringUtil.isEmpty(str))
			return new Date();
		return java.sql.Date.valueOf(str);
	}

	public static boolean between(Date srcDate, Date startDate, Date endDate) {
		if (startDate.after(srcDate))
			return false;
		if (endDate.before(srcDate))
			return false;
		return true;
	}

	public static Date getDayStart(Date date) {
		return string2Date(divideDateForDay(date, "yyyy-MM-dd", 0));
		// return Timestamp.valueOf(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
	}

	/**
	 * 根据传入时间在追加一天 Created on 2014-6-6
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		return string2Date(divideDateForDay(date, "yyyy-MM-dd", 1));
		// return Timestamp.valueOf(formatDate(date, "yyyy-MM-dd")+" 23:59:59");
	}

	/**
	 * 给指定时间 追加天数 Created on 2014-6-6
	 * 
	 * @param date
	 * @param pattern
	 *            显示格式
	 * @param num
	 *            需要加的天数
	 * @return
	 */
	public static String divideDateForDay(Date date, String pattern, int num) {
		if (date == null)
			date = new Date(System.currentTimeMillis());
		if (pattern == null)
			pattern = "yyyy-MM-dd HH:mm";
		Calendar cal = null;
		SimpleDateFormat fo = new SimpleDateFormat();
		fo.applyPattern(pattern);
		try {
			fo.format(date);
			cal = fo.getCalendar();
			cal.add(Calendar.DATE, num);
		} catch (Exception e) {
		}
		return fo.format(cal.getTime());
	}

	/**
	 * 算出两个时间的相差天数 Created on 2014-6-6
	 * 
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public static int subtrationDate(Date dateBegin, Date dateEnd) {

		GregorianCalendar gc1 = new GregorianCalendar(dateBegin.getYear(), dateBegin.getMonth(), dateBegin.getDate());
		GregorianCalendar gc2 = new GregorianCalendar(dateEnd.getYear(), dateEnd.getMonth(), dateEnd.getDate());
		// the above two dates are one second apart
		Date d1 = gc1.getTime();
		Date d2 = gc2.getTime();
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		long difference = l2 - l1;
		int date = (int) (difference / 24 / 60 / 60 / 1000);
		return date;

	}

	// 当前日期前几天或者后几天的日期
	public static Date afterNDay(int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		return d2;
	}

	// 当前日期前几天或者后几天的日期
	public static Date afterNDays(Timestamp time, int n) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time.getTime());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		return d2;
	}

	public static Timestamp transDate(Date date) {
		if (date != null) {
			long time = date.getTime();
			return new Timestamp(time);
		}
		return null;
	}

	public static Date transTimestamp(Timestamp time) {
		if (time != null) {
			long t = time.getTime();
			return new Date(t);
		}
		return null;
	}

	// 时间段的第一个时间
	public static java.sql.Timestamp stringToTime1(String d) {
		java.sql.Timestamp t = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Date d1;
		try {
			if (StringUtil.isNotEmpty(d)) {
				d1 = df.parse(d);
				t = new Timestamp(d1.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return t;
	}

	// 时间段的第二个时间
	public static java.sql.Timestamp stringToTime2(String d) {
		java.sql.Timestamp t = null;
		// StringUtils
		if (StringUtil.isNotEmpty(d)) {
			t = Timestamp.valueOf(d + " 23:59:59");
		}
		return t;
	}

	public static Calendar getYesterDayBegin() {
		Calendar before = Calendar.getInstance();

		before.set(Calendar.DAY_OF_MONTH, before.get(Calendar.DAY_OF_MONTH) - 1);
		before.set(Calendar.HOUR_OF_DAY, 0);
		before.set(Calendar.MINUTE, 0);
		before.set(Calendar.SECOND, 0);
		before.set(Calendar.MILLISECOND, 0);

		return before;
	}

	/**
	 * 查看昨天的日期 还需要调用transCalendarToTimestamp方法 Created on 2014-6-6
	 * 
	 * @return
	 */
	public static Calendar getYesterDayEnd() {
		Calendar after = Calendar.getInstance();
		after.set(Calendar.DAY_OF_MONTH, after.get(Calendar.DAY_OF_MONTH) - 1);
		after.set(Calendar.HOUR_OF_DAY, 23);
		after.set(Calendar.MINUTE, 59);
		after.set(Calendar.SECOND, 59);
		after.set(Calendar.MILLISECOND, 999);
		return after;
	}

	/**
	 * Calendar和Timestamp转换
	 * 
	 * @param cal
	 * @return
	 */
	public static Timestamp transCalendarToTimestamp(Calendar cal) {
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		return ts;
	}

	/**
	 * 根据Timestamp类型参数 返回年后两位月日(例如:140606) Created on 2014-6-6
	 * 
	 * @param time
	 * @return String
	 */
	public static String transTimestamptostr(Timestamp time) {
		if (time != null) {

			java.util.Calendar c = Calendar.getInstance();
			c.setTime(time);
			String year = String.valueOf(c.get(c.YEAR));
			String month = String.valueOf(c.get(c.MONTH) + 1);
			String day = String.valueOf(c.get(c.DATE));

			if (month.length() < 2)
				month = "0" + month;
			if (day.length() < 2)
				day = "0" + day;
			return year.substring(2, 4) + month + day;

		}
		return null;
	}

	/**
	 * 根据Calendar日历返回String Created on 2014-6-6
	 * 
	 * @param cal
	 * @return
	 */
	public static String getDataString(Calendar cal) {
		Calendar now = cal;
		String time = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
				+ now.get(Calendar.DAY_OF_MONTH) + " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE)
				+ ":" + now.get(Calendar.SECOND);
		return time;
	}

	public static Calendar parseCalendarDate(String date) {
		Calendar d11 = new GregorianCalendar();
		Date d1 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 时间格式自己设置
		try { // 一定要放到try里面,不然会报错的
			d1 = sdf.parse(date);
		} catch (Exception e) {
		}

		d11.setTime(d1); // OK了,d11就是结果了
		return d11;
	}

	public static Timestamp calendar2Timestamp(Calendar cal) {
		return new Timestamp(cal.getTimeInMillis());
	}

	/*
	 * public static String getDatePath() { return
	 * DateHelper.CalendarToStrByMark(Calendar.getInstance(), "yyyyMMdd"); }
	 */

	public static String getDatePath(Calendar cal, String pattern) {
		if (pattern == null) {
			pattern = "yyyy-MM-dd hh:mm:ss";
		}
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(cal.getTime());
	}

	/*
	 * public static String getDateTimePath() { return
	 * DateHelper.CalendarToStrByMark(Calendar.getInstance(), "yyyyMMddHHmmss");
	 * }
	 */

	// Date转化为Calendar
	public static Calendar date2Calendar(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal;
	}

	/**
	 * 日期比较是否相等
	 * 
	 * @param d1
	 * @param d2
	 * @param type
	 *            比较方式，complete,date,
	 * @return boolean
	 * @author zhou jun
	 */
	public static boolean compere(Date d1, Date d2, String type) {
		if (type.equals("date")) {
			String pattern = "yyyy-MM-dd";
			String date1 = formatDate(d1, pattern);
			String date2 = formatDate(d2, pattern);
			// System.out.println(date1+date2);
			if (date1.equals(date2)) {
				return true;
			}
			return false;
		} else {
			return d1.equals(d2);
		}
	}

	/**
	 * 功能: 将日期对象按照某种格式进行转换，返回转换后的字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            转换格式 例：yyyy-MM-dd
	 * @author yanhechao
	 */
	public static String DateToString(Date date, String pattern) {
		String strDateTime = null;
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		strDateTime = date == null ? null : formater.format(date);
		return strDateTime;
	}

	public static String StringToString(String date, String pattern) {
		String strDateTime = null;
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		try {
			strDateTime = date == null ? null : formater.format(formater.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDateTime;
	}

}
