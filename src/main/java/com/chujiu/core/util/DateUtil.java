package com.chujiu.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[日期处理工具类]
 * Description: [描述该类功能介绍]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public class DateUtil {

	/**缺省日期格式 yyyy-MM-dd*/
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**缺省时间格式 HH:mm:ss*/
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	/**缺省月格式 MONTH*/
	public static final String DEFAULT_MONTH = "MONTH";

	/**缺省年格式 YEAR*/
	public static final String DEFAULT_YEAR = "YEAR";

	/**缺省日格式 DAY*/
	public static final String DEFAULT_DATE = "DAY";

	/**缺省小时格式 HOUR*/
	public static final String DEFAULT_HOUR = "HOUR";

	/**缺省分钟格式 MINUTE*/
	public static final String DEFAULT_MINUTE = "MINUTE";

	/**缺省秒格式 SECOND*/
	public static final String DEFAULT_SECOND = "SECOND";

	/**缺省长日期格式 yyyy-MM-dd HH-mm*/
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH-mm";

	/**缺省长日期格式,精确到秒 yyyy-MM-dd HH:mm:ss*/
	public static final String DEFAULT_DATETIME_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";

	/**星期数组*/
	public static final String[] WEEKS = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	/**
	 * Created on   2016年5月16日
	 * Discription: [取当前日期的字符串表示 ]
	 * @return String 当前日期的字符串 ,如2010-05-28
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:02:40
	 */
	public static String today() {
		return today(DEFAULT_DATE_FORMAT);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据输入的格式得到当前日期的字符串 ]
	 * @param strFormat
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:03:13
	 */
	public static String today(String strFormat) {
		return toString(new Date(), strFormat);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [取当前时间的字符串表示]
	 * @return String 当前时间,如:21:10:12
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:03:28
	 */
	public static String currentTime() {
		return currentTime(DEFAULT_TIME_FORMAT);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据输入的格式获取时间的字符串表示 ]
	 * @param strFormat
	 * @return String 当前时间,如:21:10:12 
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:03:49
	 */
	public static String currentTime(String strFormat) {
		return toString(new Date(), strFormat);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [取得相对于当前时间增加天数/月数/年数后的日期  example : 欲取得当前日期5天前的日期,可做如下调用: getAddDay("DATE", -5). ]
	 * @param field 如"year","month","date",对大小写不敏感 
	 * @param String amount 增加的数量(减少用负数表示),如5,-1
	 * @return 格式化后的字符串 如"2010-05-28" 
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:04:46
	 */
	public static String getAddDay(String field, int amount) {
		return getAddDay(field, amount, null);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [取得相对于当前时间增加天数/月数/年数后的日期,按指定格式输出 ，欲取得当前日期5天前的日期,可做如下调用:getAddDay("DATE", -5,'yyyy-mm-dd hh:mm')]
	 * @param field 如"year","month","date",对大小写不敏感 
	 * @param amount 增加的数量(减少用负数表示),如5,-1 
	 * @param strFormat 输出格式,如"yyyy-mm-dd","yyyy-mm-dd hh:mm" 
	 * @return String 格式化后的字符串 如"2010-05-28" 
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:08:07
	 */
	public static String getAddDay(String field, int amount, String strFormat) {
		return getAddDay(null, field, amount, strFormat);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [功能：对于给定的时间增加天数/月数/年数后的日期,按指定格式输出 ]
	 * @param date  要改变的日期 
	 * @param field 日期改变的字段，YEAR,MONTH,DAY 
	 * @param amount 改变量
	 * @param strFormat 日期返回格式
	 * @return
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:09:11
	 */
	public static String getAddDay(String date, String field, int amount, String strFormat) {
		if (strFormat == null) {
			strFormat = DEFAULT_DATETIME_FORMAT_SEC;
		}
		Calendar rightNow = Calendar.getInstance();
		if (date != null && !"".equals(date.trim())) {
			rightNow.setTime(parseDate(date, strFormat));
		}
		if (field == null) {
			return toString(rightNow.getTime(), strFormat);
		}
		rightNow.add(getInterval(field), amount);
		return toString(rightNow.getTime(), strFormat);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取时间间隔类型 ]
	 * @param field 时间间隔类型 
	 * @return int 日历的时间间隔 
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:10:00
	 */
	protected static int getInterval(String field) {
		String tmpField = field.toUpperCase();
		if (tmpField.equals(DEFAULT_YEAR)) {
			return Calendar.YEAR;
		} else if (tmpField.equals(DEFAULT_MONTH)) {
			return Calendar.MONTH;
		} else if (tmpField.equals(DEFAULT_DATE)) {
			return Calendar.DATE;
		} else if (DEFAULT_HOUR.equals(tmpField)) {
			return Calendar.HOUR;
		} else if (DEFAULT_MINUTE.equals(tmpField)) {
			return Calendar.MINUTE;
		} else {
			return Calendar.SECOND;
		}
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取格式化对象 ]
	 * @param strFormat 格式化的格式 如"yyyy-MM-dd" 
	 * @return SimpleDateFormat
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:10:24
	 */
	public static SimpleDateFormat getSimpleDateFormat(String strFormat) {
		if (strFormat != null && !"".equals(strFormat.trim())) {
			return new SimpleDateFormat(strFormat);
		} else {
			return new SimpleDateFormat();
		}
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [得到当前日期的星期数 ]
	 * @return 当前日期的星期的字符串 
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:10:58
	 */
	public static String getWeekOfMonth() {
		return getWeekOfMonth(null, null);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据日期的到给定日期的在当月中的星期数 ]
	 * @param date 给定日期
	 * @param fromat
	 * @return
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:11:21
	 */
	public static String getWeekOfMonth(String date, String fromat) {
		Calendar rightNow = Calendar.getInstance();
		if (date != null && !"".equals(date.trim())) {
			rightNow.setTime(parseDate(date, fromat));
		}
		return WEEKS[rightNow.get(Calendar.WEEK_OF_MONTH)];
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [将java.util.date型按照指定格式转为字符串]
	 * @param date 源对象
	 * @param format 想得到的格式字符串 
	 * @return String 如：2010-05-28
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:11:39
	 */
	public static String toString(Date date, String format) {
		if(null == date){
			return "";
		}
		return getSimpleDateFormat(format).format(date);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [将java.util.date型按照缺省格式转为字符串]
	 * @param date
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:12:15
	 */
	public static String toString(Date date) {
		return toString(date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [强制类型转换 从串到日期 ]
	 * @param strDate 源字符串，采用yyyy-MM-dd格式 
	 * @param format
	 * @return 得到的日期对象
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:12:32
	 */
	public static Date parseDate(String strDate, String format) {
		Date rtnDate = null;
		try {
			if(strDate != null && !"".equals(strDate)){
				rtnDate = getSimpleDateFormat(format).parse(strDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtnDate;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据传入的毫秒数和格式，对日期进行格式化输出]
	 * @param millisecond
	 * @param format
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:13:02
	 */
	public static String millisecondFormat(Long millisecond, String format) {
		if (millisecond == null || millisecond <= 0) {
			throw new IllegalArgumentException(String.format("传入的时间毫秒数[%s]不合法", "" + millisecond));
		}
		if (format == null || "".equals(format.trim())) {
			format = DEFAULT_DATE_FORMAT;
		}
		return toString(new Date(millisecond), format);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [强制类型转换 从串到时间戳 ]
	 * @param strDate 源串
	 * @param format 遵循格式
	 * @return 取得的时间戳对象 
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:13:15
	 */
	public static Timestamp parseTimestamp(String strDate, String format) {
		Timestamp rtn = null;
		try {
			Date utildate = getSimpleDateFormat(format).parse(strDate);
			rtn = new Timestamp(utildate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtn;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [getCurDate 取当前日期(java.util.Date型日期 ) ]
	 * @return Date
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:13:47
	 */
	public static Date getCurDate() {
		return (new Date());
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [getCurTimestamp 取当前时间戳 (java.sql.Timestamp)]
	 * @return Timestamp
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:14:06
	 */
	public static Timestamp getCurTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [按指定格式取得日期]
	 * @param format 取遵循格式的当前时间 
	 * @return
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:14:30
	 */
	public static Date getCurDate(String format) {
		Date rtnDate = null;
		try {
			rtnDate = getSimpleDateFormat(format).parse(toString(new Date(), format));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtnDate;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [Timestamp按照指定格式转为字符串 ]
	 * @param timestamp 源对象
	 * @param format （如yyyy.mm.dd） 
	 * @return String 如：2010-05-28 或2010-05-281 13:21
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:15:29
	 */
	public static String toString(Timestamp timestamp, String format) {
		if (timestamp == null) {
			return "";
		}
		return toString(new Date(timestamp.getTime()), format);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [Timestamp按照缺省格式转为字符串]
	 * @param ts 源对象
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:15:58
	 */
	public static String toString(Timestamp ts) {
		return toString(ts, DEFAULT_DATE_FORMAT);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [Timestamp按照缺省格式转为字符串，可指定是否使用长格式]
	 * @param timestamp 欲转化之变量Timestamp
	 * @param fullFormat 是否使用长格式 
	 * @return String 如：2010-05-28 或2010-05-28 21:21 
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:16:17
	 */
	public static String toString(Timestamp timestamp, boolean fullFormat) {
		if (fullFormat) {
			return toString(timestamp, DEFAULT_DATETIME_FORMAT_SEC);
		} else {
			return toString(timestamp, DEFAULT_DATE_FORMAT);
		}
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [将sqldate型按照指定格式转为字符串]
	 * @param sqldate 源对象
	 * @param sFormat
	 * @return String 如：2010-05-28 或2010-05-28 00:00
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:16:51
	 */
	public static String toString(java.sql.Date sqldate, String sFormat) {
		if (sqldate == null) {
			return "";
		}
		return toString(new Date(sqldate.getTime()), sFormat);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [将sqldate型按照缺省格式转为字符串]
	 * @param sqldate
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:17:22
	 */
	public static String toString(java.sql.Date sqldate) {
		return toString(sqldate, DEFAULT_DATE_FORMAT);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [计算日期时间之间的差值， date1得时间必须大于date2的时间]
	 * @param date1
	 * @param date2
	 * @return Map<String,Long> Map的键分别为, day(天), hour(小时),minute(分钟)和second(秒)。 
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:17:35
	 */
	public static Map<String, Long> timeDifference(final Date date1, final Date date2) {
		if (date1 == null || date2 == null) {
			throw new NullPointerException("date1 and date2 can't null");
		}
		long mim1 = date1.getTime();
		long mim2 = date2.getTime();
		if (mim1 < mim2) {
			throw new IllegalArgumentException(String.format(
					"date1[%s] not be less than date2[%s].", mim1 + "", mim2 + ""));
		}
		long m = (mim1 - mim2 + 1) / 1000l;
		long mday = 24 * 3600;
		final Map<String, Long> map = new HashMap<String, Long>();
		map.put("day", m / mday);
		m = m % mday;
		map.put("hour", (m) / 3600);
		map.put("minute", (m % 3600) / 60);
		map.put("second", (m % 3600 % 60));
		return map;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [比较日期大小]
	 * @param date1
	 * @param date2
	 * @return Map<String,Integer>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午8:18:30
	 */
	public static Map<String, Integer> compareTo(final Date date1, final Date date2) {
		if (date1 == null || date2 == null) {
			return null;
		}
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long time = Math.max(time1, time2) - Math.min(time1, time2);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("year",
				(calendar.get(Calendar.YEAR) - 1970) > 0 ? (calendar.get(Calendar.YEAR) - 1970) : 0);
		map.put("month",
				(calendar.get(Calendar.MONTH) - 1) > 0 ? (calendar.get(Calendar.MONTH) - 1) : 0);
		map.put("day",
				(calendar.get(Calendar.DAY_OF_MONTH) - 1) > 0 ? (calendar
						.get(Calendar.DAY_OF_MONTH) - 1) : 0);
		map.put("hour",
				(calendar.get(Calendar.HOUR_OF_DAY) - 8) > 0 ? (calendar.get(Calendar.HOUR_OF_DAY) - 8)
						: 0);
		map.put("minute", calendar.get(Calendar.MINUTE) > 0 ? calendar.get(Calendar.MINUTE) : 0);
		map.put("second", calendar.get(Calendar.SECOND) > 0 ? calendar.get(Calendar.SECOND) : 0);
		return map;
	}

}