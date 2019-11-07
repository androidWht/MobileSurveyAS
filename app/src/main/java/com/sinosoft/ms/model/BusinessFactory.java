package com.sinosoft.ms.model;
/**
 * 系统名：移动查勘定损 
 * 子系统名：任务数（单例模式）
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午10:30:45
 */

public class BusinessFactory {
	private static Business business;
	public static Business getInstance(){
		if(business==null){
			business = new Business();
		}
		return business;
		
	}
	
	/**
	 * @param business the business to set
	 */
	public static void setBusiness(Business bu) {
		business = bu;
	}
	
}

