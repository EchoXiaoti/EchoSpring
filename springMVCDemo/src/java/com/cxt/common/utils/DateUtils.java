package com.cxt.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期常用方法类
 * @author xiaoti
 *
 */
public class DateUtils {

	public static final String PAT_DATE = "yyyy-MM-dd";
	
	public static final String PAT_DATE_UNINTERRUPTED = "yyyyMMdd";
	
	public static final String PAT_DATE_TIME = "yyyy-MM-dd HH:mm";
	
	public static final String PAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	public static final String PAT_DATETIME_UNINTERRUPTED = "yyyyMMddHHmmss";
	
	public static final String PAT_DATETIME_MILLISECOND = "yyyy-MM-dd HH:mm:ss SSS";
	
	public static Date parse(String date, String pattern){
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(date);
		} catch (Exception e) {
			throw new RuntimeException("DateFormat exception, date: " + date + ", pattern: " + pattern, e);
		}
	}
	
	/**
	 * 校验日期格式【默认yyyy-MM-dd】
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static DateTime checkDateFormat(String date) throws Exception{
		return checkDateFormat(date, PAT_DATE);
	}
	
	/**
	 * 校验日期格式
	 * @param date
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static DateTime checkDateFormat(String date, String pattern) throws Exception{
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(date, dateTimeFormatter);
	}
	
	/**
	 * 获取指定的日期的开始时间
	 * @param dateTime
	 * @param desPattern
	 * @return
	 */
	public static String withTimeAtStartOfDay(DateTime dateTime, String desPattern){
		return dateTime.withTimeAtStartOfDay().toString(desPattern);
	}
	
	/**
	 * 获取指定的日期的开始时间【yyyy-MM-dd HH:mm:ss】
	 * @param dateTime
	 * @return
	 */
	public static String withTimeAtStartOfDay(DateTime dateTime){
		return withTimeAtStartOfDay(dateTime, PAT_DATE);
	}
	
	/**
	 * 获取指定的日期的开始时间
	 * @param date
	 * @param sourcePattern	原日期
	 * @param desPattern	目标日期
	 * @return
	 * @throws Exception
	 */
	public static String withTimeAtStartOfDay(String date, String sourcePattern, String desPattern) throws Exception{
		DateTime dateTime = toDateTime(date, sourcePattern);
		return dateTime.withTimeAtStartOfDay().toString(desPattern);
	}
	
	/**
	 * 获取指定的日期的开始时间【格式：yyyy-MM-dd HH:mm:ss】
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String withTimeAtStartOfDay(String date) throws Exception{
		return withTimeAtStartOfDay(date, PAT_DATE, PAT_DATETIME);
	}
	
	/**
	 * 获取指定的日期的开始时间
	 * @param date
	 * @param sourcePattern
	 * @return
	 * @throws Exception
	 */
	public static String withTimeAtStartOfDay(String date, String sourcePattern) throws Exception{
		return withTimeAtStartOfDay(date, sourcePattern, PAT_DATETIME);
	}
	
	/**
	 * 获取指定的日期的结束时间
	 * @param dateTime
	 * @param desPattern
	 * @return
	 * @throws Exception
	 */
	public static String withMaxinumValue(DateTime dateTime, String desPattern) throws Exception{
		return dateTime.millisOfDay().withMaximumValue().toString(desPattern);
	}
	
	/**
	 * 获取指定的日期的结束时间【yyyy-MM-dd HH:mm:ss】
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	public static String withMaxinumValue(DateTime dateTime) throws Exception{
		return withMaxinumValue(dateTime, PAT_DATETIME);
	}
	
	/**
	 * 获取指定的日期的结束时间
	 * @param date
	 * @param sourcePattern
	 * @param desPattern
	 * @return
	 * @throws Exception
	 */
	public static String withMaxinumValue(String date, String sourcePattern, String desPattern) throws Exception{
		DateTime dateTime = toDateTime(date, sourcePattern);
		return dateTime.millisOfDay().withMaximumValue().toString(desPattern);
	}
	
	/**
	 * 获取指定的日期的结束时间【日期格式：yyyy-MM-dd】
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String withMaxinumValue(String date) throws Exception{
		return withMaxinumValue(date, PAT_DATE, PAT_DATETIME);
	}
	
	/**
	 * 获取指定的日期的结束时间
	 * @param date
	 * @param sourcePattern
	 * @return
	 * @throws Exception
	 */
	public static String withMaxinumValue(String date, String sourcePattern) throws Exception{
		return withMaxinumValue(date, sourcePattern, PAT_DATETIME);
	}
	
	/**
	 * 将日期字符串转换到DateTime
	 * @param date
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static DateTime toDateTime(String date, String pattern) throws Exception{
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(date, dateTimeFormatter);
	}
	
	/**
	 * 将日期字符串【yyyy-MM-dd】转换到DateTime
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static DateTime toDateTime(String date) throws Exception{
		return toDateTime(date, PAT_DATE);
	}
	
	/**
	 * 将日期字符串【yyyy-MM-dd HH:mm:ss】转换到DateTime
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static DateTime withTimeToDateTime(String date) throws Exception{
		return toDateTime(date, PAT_DATETIME);
	}
	
	/**
	 * 获取当前时间 格式为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentDateStr(){
		SimpleDateFormat sdFormat = new SimpleDateFormat(PAT_DATETIME);
		return sdFormat.format(new Date());
	}
}
