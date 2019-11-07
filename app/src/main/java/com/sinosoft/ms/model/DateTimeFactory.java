package com.sinosoft.ms.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 系统名：移动查勘定损 
 * 子系统名：系统时间工厂类
 * 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Hijack
 * @createTime 下午2:35:54
 */

public class DateTimeFactory {

	private static DateTimeFactory dateTimeFactory;
//	private Date dateTime;
	private Calendar calendar;
	
	public DateTimeFactory() {
		super();
		Date date = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
	}

	public static DateTimeFactory getInstance(){
		if(dateTimeFactory == null){
			dateTimeFactory = new DateTimeFactory();
		}
		return dateTimeFactory;
	}
	
	public void setDateTime(String dateStr){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTime;
		if(null != dateStr && !"".equals(dateStr)){
			try {
				dateTime = format.parse(dateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				dateTime = new Date();
			}
		}else{
			dateTime = new Date();
		}
		calendar.setTime(dateTime);
	}
	
	public String getDateTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTime = calendar.getTime();
		return format.format(dateTime);
	}
	
	public Date getDat(){
		return calendar.getTime();
	}
	
	public int getYear(){
		return calendar.get(Calendar.YEAR);
	}
	
	public int getMonth(){
		 return calendar.get(Calendar.MONTH);
	}
	
	public int getDate(){
		 return calendar.get(Calendar.DATE);
	}
	
	public int getHour(){
		return calendar.get(Calendar.HOUR);
	}
	
	public int getMinutes(){
		return calendar.get(Calendar.MINUTE);
	}
	
	public int getSeconds(){
		return calendar.get(Calendar.SECOND);
	}
	
	//当前时间增加时间戳数
	public Date addTime(int seconds){
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}
	
	
	
	
	
}

