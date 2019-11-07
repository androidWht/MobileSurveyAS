package com.sinosoft.ms.utils;

public class StringUtils {

	public static boolean isEmpty(String text) {
		return (null == text || "".equals(text) || "".equals(text.trim()));
	}

	public static boolean isNotEmpty(String text) {
		return (null != text && !"".equals(text) && !"".equals(text.trim()));
	}

	public static int toInt(String str) {
		int returnValue;
		try {
			returnValue = Integer.parseInt(str);
		} catch (Exception e) {
			returnValue = 0;
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * 将字符串转换成double
	 * 
	 * @param number 字符串内容
	 * @return 转换后double
	 */
	public static double toDouble(String number) {
		double result = 0;
		try {
			result = Double.parseDouble(number);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return result;
	}
	
	public static Long toLong(String str){
		long returnValue;
		try {
			returnValue = Long.parseLong(str);

		} catch (Exception e) {
			returnValue = 0;
			//e.printStackTrace();
		}

		return returnValue;
		
	}
	
	public static String initWithNull(String str){
		return isEmpty(str)?"":str;
	}
	
	/**
	 * 在原有的基础上添加字符串
	 * 
	 * @param text 原有字符串内容
	 * @param data 添加字符串
	 * @return 添加后字符串内容
	 */
	public static String add(String text,String data){
		return isEmpty(text) ? data : text + data;
	}
}
