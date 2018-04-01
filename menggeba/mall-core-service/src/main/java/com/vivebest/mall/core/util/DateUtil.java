package com.vivebest.mall.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author jiang.hl
 * @date 2015-07-27
 */
public class DateUtil 
{
	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMAT = "yyyy-MM-dd";
	
    /**
     * 计算两个日期的距离（单位：分钟）
     * 
     * @param begin 开始日期
     * @param end 结束日期
     * @return 时间差
     */
    public static long getTimeDiffOfMinutes(Date begin, Date end) 
    {
        // 毫秒ms
        long diff = end.getTime() - begin.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        return diffMinutes;
    }
    /**
     * 计算两个日期的距离（单位：天）   
     * @param begin
     * @param end
     * @return
     */
    public static int getTimeDiffOfDays(Date begin, Date end)    
    {    
    	long diff = end.getTime() - begin.getTime();
        long days = diff/(1000*3600*24);
        return Integer.parseInt(String.valueOf(days));          
    }
    /**
     * 日期字符串转日期
     * @param dateString
     * @return
     */
    public static Date StringToDate(String dateString)
    {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try
		{
			return format.parse(dateString);
		} 
    	catch (ParseException e)
		{
    		return null;
		}
    	
    }
    
    /**
     * 日期字符串转日期
     * @param dateString
     * @return
     */
    public static Date StringToDate(String dateString,String pattern)
    {
    	SimpleDateFormat format = new SimpleDateFormat(pattern);
    	try
		{
			return format.parse(dateString);
		} 
    	catch (ParseException e)
		{
    		return null;
		}
    	
    }
    /**
     * 日期转字符串
     * @param date
     * @return
     */
    public static String dateToString(Date date)
    {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return format.format(date);
    }
    
    /**
     * 日期转字符串
     * @param date
     * @return
     */
    public static String dateToString(Date date,String pattern)
    {
    	SimpleDateFormat format = new SimpleDateFormat(pattern);
    	return format.format(date);
    }
    
    /** 
     * 得到几天前的时间 
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date getDateBefore(Date d,int day)
    {  
	     Calendar now =Calendar.getInstance();  
	     now.setTime(d);  
	     now.set(Calendar.DATE,now.get(Calendar.DATE)-day);  
	     return now.getTime();  
    }  
      
    /** 
     * 得到几天后的时间 
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date getDateAfter(Date d,int day)
    {  
	     Calendar now =Calendar.getInstance();  
	     now.setTime(d);  
	     now.set(Calendar.DATE,now.get(Calendar.DATE)+day);  
	     return now.getTime();  
    }  
    
    /** 
     * 得到几秒后的时间 
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date addSecond(Date date,int second) {    
        Calendar calendar = Calendar.getInstance();    
        calendar.setTime(date);    
        calendar.add(Calendar.SECOND, second);    
        return calendar.getTime();    
    }    
    
	/**
	 * 得到当前日期是星期几
	 * 0:星期日  1:星期一  2:星期二  3:星期三  4:星期四  5:星期五  6:星期六
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int intDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (intDay < 0)
        {
            intDay = 0;
        }

	    return intDay;
	}

	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm".
	 */
	public static String formatDateTime(Date d) {
		return new SimpleDateFormat(DATETIME_FORMAT).format(d);
	}

	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm".
	 */
	public static String formatDateTime(long d) {
		return new SimpleDateFormat(DATETIME_FORMAT).format(d);
	}
	
	public static String formatDateTime(long d , String format) {
		return new SimpleDateFormat(format).format(d);
	}
}

