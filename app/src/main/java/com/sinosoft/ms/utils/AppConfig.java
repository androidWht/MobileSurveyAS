package com.sinosoft.ms.utils;

import com.sinosoft.ms.model.User;

/**
 * 系统名：移动查勘定损 
 * 子系统名：系统 参数
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 下午3:20:28
 */

public class AppConfig {
	private static User loginUser;//当前登陆用户
	private static boolean isAcceptTask=false;//是否接受系统推送消息
	public static boolean loginFlag=false;
	/**
	 * @return the loginUser
	 */
	public static User getLoginUser() {
		return loginUser;
	}
	/**
	 * @param loginUser the loginUser to set
	 */
	public static void setLoginUser(User loginUser) {
		AppConfig.loginUser = loginUser;
	}
	/**
	 * @return the isAcceptTask
	 */
	public static boolean isAcceptTask() {
		return isAcceptTask;
	}
	/**
	 * @param isAcceptTask the isAcceptTask to set
	 */
	public static void setAcceptTask(boolean isAcceptTask) {
		AppConfig.isAcceptTask = isAcceptTask;
	}
	

}

