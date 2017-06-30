package com.wondersgroup.advertisement.common.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wondersgroup.advertisement.common.exception.UnhandledException;


/**
 * 日期处理工具。
 * <p>
 * 
 * 相关的日期处理工具类为org.apache.commons.lang.time.DateFormatUtils
 * 
 * @version 1.0
 * @since 1.0
 */

public class DateUtil {

    /** 默认日期格式 */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    /** 默认时间格式 */
    public static final String FORMAT_TIME = "HH:mm:ss";

    /** 默认日期时间格式 */
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_DATE_ZWDT = "yyyy-MM-ddTHH:mm:ss";

    /** 默认中文日期格式 * */
    public static final String FORMAT_DATE_CN = "yyyy年MM月dd日";

    /** 日志 */
    private static Log logger = LogFactory.getLog(DateUtil.class);

    /**
     * 将日期转换成大写的中文日期
     * 
     * @param date
     *                java.util.Date类型
     * @return 无法成功转换则返回null
     */
    public static String date2Cn(java.util.Date date) {
	String result = null;
	String sDate = "";
	if (date == null) {
	    return result;
	}
	try {
	    sDate = DateFormatUtils.format(date, FORMAT_DATE_CN);
	} catch (Exception ex) {
	    logger.error("日期转换为大写日期格式错误，日期：" + date.toString() + "， 格式：" + FORMAT_DATE_CN);
	    throw new UnhandledException(ex);
	}
	String s = sDate.substring(0, 5);
	String s2 = sDate.substring(5, sDate.length());
	String[] a = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	String[] b = { "○", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	String[] c = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
		"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	String[] d = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七",
		"十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十", "三十一" };

	for (int i = 0; i < a.length; i++) {
	    for (int j = 0; j < s.length() - 1; j++) {
		if (s.substring(j, j + 1).equals(a[i]))
		    s = s.substring(0, j) + b[i] + s.substring(j + 1, s.length());
	    }
	}
	for (int i = 0; i < c.length; i++) {
	    for (int j = 0; j < s2.length() - 2; j++) {
		if (s2.substring(j, j + 2).equals(c[i]))
		    s2 = s2.substring(0, j) + d[i] + s2.substring(j + 2, s2.length());
	    }
	}
	result = s + s2;
	return result;
    }

    /**
     * 将日期转换成的中文日期
     * 
     * @param date
     *                java.util.Date类型
     * @return 无法成功转换则返回null
     */
    public static String date2Chinese(java.util.Date date) {
	String result = null;
	String sDate = "";
	if (date == null) {
	    return result;
	}
	try {
	    sDate = DateFormatUtils.format(date, FORMAT_DATE_CN);
	} catch (Exception ex) {
	    logger.error("日期转换为大写日期格式错误，日期：" + date.toString() + "， 格式：" + FORMAT_DATE_CN);
	    throw new UnhandledException(ex);
	}
	String s = sDate.substring(0, 5);
	String s2 = sDate.substring(5, sDate.length());
	String[] a = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	String[] b = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	String[] c = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
		"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	String[] d = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七",
		"十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十", "三十一" };

	for (int i = 0; i < a.length; i++) {
	    for (int j = 0; j < s.length() - 1; j++) {
		if (s.substring(j, j + 1).equals(a[i]))
		    s = s.substring(0, j) + b[i] + s.substring(j + 1, s.length());
	    }
	}
	for (int i = 0; i < c.length; i++) {
	    for (int j = 0; j < s2.length() - 2; j++) {
		if (s2.substring(j, j + 2).equals(c[i]))
		    s2 = s2.substring(0, j) + d[i] + s2.substring(j + 2, s2.length());
	    }
	}
	result = s + s2;
	return result;
    }

    /**
     * 将日期转换成字符格式
     * 
     * @param date
     *                java.util.Date类型
     * @param format
     *                如果为null或""，默认为DATE格式
     * @return 无法成功转换则返回null
     */
    public static String date2String(java.util.Date date, String format) {
	String result = null;
	if (date == null) {
	    return result;
	}
	if (StringUtils.isBlank(format)) {
	    format = FORMAT_DATE;
	}
	try {
	    result = DateFormatUtils.format(date, format);
	} catch (Exception ex) {
	    logger.error("日期转换为字符串错误，日期：" + date.toString() + "， 格式：" + format);
	    throw new UnhandledException(ex);
	}

	return result;
    }

	 public static String date2StringForZwdt(java.util.Date date, String format) {
    	String result = null;
    	if (date == null) {
    	    return result;
    	}
    	if (StringUtils.isBlank(format)) {
    	    format = FORMAT_DATE_ZWDT;
    	}
    	try {
    	    result = DateFormatUtils.format(date, "yyyy-MM-dd") + "T" + DateFormatUtils.format(date, "HH:mm:ss");
    	} catch (Exception ex) {
        	    throw new UnhandledException(ex);
    	}

    	return result;
        }


    /**
     * 将字符串转换成日期格式
     * 
     * @param str
     *                需要转换的字符串
     * @param format
     *                相应的转换格式，如果参数为Blank则表示按常规的三种格式转换
     * @return 如果不能正常转换则返回null
     */
    public static java.util.Date string2Date(String str, String format) {
	if (StringUtils.isBlank(str)) {
	    return null;
	}
	Date result = null;
	String[] formats = null;
	if (StringUtils.isBlank(format)) {
	    formats = new String[3];
	    formats[0] = FORMAT_DATETIME;
	    formats[1] = FORMAT_DATE;
	    formats[2] = FORMAT_TIME;
	} else {
	    formats = new String[4];
	    formats[0] = format;
	    formats[1] = FORMAT_DATETIME;
	    formats[2] = FORMAT_DATE;
	    formats[3] = FORMAT_TIME;
	}
	try {
	    result = DateUtils.parseDate(str, formats);
	} catch (Exception ex) {
	    logger.error("日期或时间格式不正确，日期时间字符串：" + str + "， 格式：" + format);
	    throw new UnhandledException(ex);
	}

	return result;
    }

    /**
     * 添加年。
     * 
     * @param date
     *                日期
     * @param num
     *                添加的年数
     * @return 添加后的日期
     */
    public static java.util.Date addYears(java.util.Date date, int num) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(cal.YEAR, num);
	return cal.getTime();
    }

    /**
     * 添加月份。
     * 
     * @param date
     *                日期
     * @param num
     *                添加对月数
     * @return 添加后的日期
     */
    public static java.util.Date addMonths(java.util.Date date, int num) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(cal.MONTH, num);
	return cal.getTime();
    }

    /**
     * 添加天数。
     * 
     * @param date
     *                日期
     * @param num
     *                添加的天数
     * @return 添加后的日期
     */
    public static java.util.Date addDays(java.util.Date date, int num) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(cal.DAY_OF_YEAR, num);
	return cal.getTime();
    }

    /**
     * 得到当年第一天的开始时间。
     * 
     * @param date
     *                日期
     * @return 当年第一天的开始时间
     */
    public static java.util.Date getFirstDayOfYear(java.util.Date date) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.set(cal.DAY_OF_YEAR, cal.getActualMinimum(cal.DAY_OF_YEAR));
	cal.set(cal.HOUR_OF_DAY, 0);
	cal.set(cal.MINUTE, 0);
	cal.set(cal.SECOND, 0);
	return cal.getTime();
    }

    /**
     * 得到当月第一天的开始时间。
     * 
     * @param date
     *                日期
     * @return 当月第一天的开始时间
     */
    public static java.util.Date getFirstDayOfMonth(java.util.Date date) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.set(cal.DAY_OF_MONTH, cal.getActualMinimum(cal.DAY_OF_MONTH));
	cal.set(cal.HOUR_OF_DAY, 0);
	cal.set(cal.MINUTE, 0);
	cal.set(cal.SECOND, 0);
	return cal.getTime();
    }

    /**
     * 得到当年的最后一天最后一秒。
     * 
     * @param date
     *                日期
     * @return 当年最后一天最后一秒
     */
    public static java.util.Date getLastDayOfYear(java.util.Date date) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.set(cal.DAY_OF_YEAR, cal.getActualMaximum(cal.DAY_OF_YEAR));
	cal.set(cal.HOUR_OF_DAY, 23);
	cal.set(cal.MINUTE, 59);
	cal.set(cal.SECOND, 59);
	return cal.getTime();
    }

    /**
     * 得到当月的最后一天最后一秒。
     * 
     * @param date
     *                日期
     * @return 当月最后一天最后一秒
     */
    public static java.util.Date getLastDayOfMonth(java.util.Date date) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
	cal.set(cal.HOUR_OF_DAY, 23);
	cal.set(cal.MINUTE, 59);
	cal.set(cal.SECOND, 59);
	return cal.getTime();
    }

    /**
     * 得到该日期的最后时间，精确到秒（即23：59：59）
     * 
     * @param dateStr
     * @return
     */
    public static java.util.Date getLastSecondOfDay(String dateStr) {
	if (StringUtils.isBlank(dateStr)) {
	    return null;
	}
	dateStr = dateStr.trim();
	String convertedDateStr = new StringBuffer(dateStr).append(" 23:59:59").toString();
	return string2Date(convertedDateStr, FORMAT_DATETIME);
    }

    /**
     * 得到该日期的最后时间，精确到秒（即23：59：59）
     * 
     * @param dateStr
     * @return
     */
    public static java.util.Date getLastSecondOfDay(Date date) {
	if (date == null) {
	    return getLastSecondOfDay(new Date());
	}
	String dateStr = date2String(date, FORMAT_DATE);
	String convertedDateStr = new StringBuffer(dateStr).append(" 23:59:59").toString();
	return string2Date(convertedDateStr, FORMAT_DATETIME);
    }

    /**
     * 获得2个日期的相差天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysDiffer(Date startDate, Date endDate) {

	if (startDate == null || endDate == null) {
	    return 0;
	}

	return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));

    }
    
    /**
     * 将时间戳转换成字符串格式
     * 
     * @param ts 时间戳
     * @param format 日期时间格式
     * @return 转换后的字符串
     */
    public static String timestamp2String(Timestamp ts, String format) {
        return ts == null ? null : date2String(new java.util.Date(ts.getTime()), format);
    }

	/**
	 * 获得该天的最早时刻
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getDayFirstTime(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得该天最晚的时刻
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getDayLastTime(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}
	
	/**
	 * 得到当月第一天的开始时间。
	 * 
	 * @param date
	 *            日期
	 * @return 当月第一天的开始时间
	 */
	public static java.util.Date getFirstDateOfMonth(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 得到当月的最后一天最后一秒。
	 * 
	 * @param date
	 *            日期
	 * @return 当月最后一天最后一秒
	 */
	public static java.util.Date getLastDateOfMonth(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	public static java.util.Date addMinutes(java.util.Date date, int mins) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, mins);
		return cal.getTime();
	}

	public static int getDiffDays(java.util.Date d1, java.util.Date d2) {
		return (int) Math.abs((d1.getTime() - d2.getTime()) / 1000 / 60 / 60 / 24);
	}
	
	public static int getDiffHours(java.util.Date d1, java.util.Date d2) {
		return (int) Math.abs((d1.getTime() - d2.getTime()) / 1000 / 60 / 60);
	}
	
	public static java.util.Date getDate(String year, String month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		if (month != null && !"".equals(month)) {
			cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		}
		return cal.getTime();
	}

	public static java.util.Date getDate(Date date, String day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		return cal.getTime();
	}
	
	/**
	 * 获得该月天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得该日期的号
	 * 
	 * @param date
	 * @return
	 */
	public static int getDate(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 获得该日期在一个星期中是第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(java.util.Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static int getDiffMonths(Date sd, Date ed) {

		if (sd == null || ed == null)
			return 0;

		if (sd.compareTo(ed) > 0) {
			Date t = sd;
			sd = ed;
			ed = t;
		}

		int sy = Integer.parseInt(date2String(sd, "yyyy"));
		int sm = Integer.parseInt(date2String(sd, "M"));
		int ey = Integer.parseInt(date2String(ed, "yyyy"));
		int em = Integer.parseInt(date2String(ed, "M"));

		if (ey == sy) {
			return em - sm + 1;
		} else if ((ey - sy) == 1) {
			return 12 - sm + em + 1;
		} else if ((ey - sy) > 1) {
			return 12 - sm + (ey - sy) * 12 + em + 1;
		}
		
		return 0;
	}
	
	public static int getDiffSeconds(String startTime,String endTime) {
		Date d1 = string2Date(startTime,"HH:mm:ss");
		Date d2 = string2Date(endTime,"HH:mm:ss");
		return (int) Math.abs((d1.getTime() - d2.getTime()) / 1000 );
	}
}
