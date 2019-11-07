package com.sinosoft.ms.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.os.Environment;

import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.model.User;

/**
 * 系统名：移动查勘定损系统 
 * 子系统名：用户缓存信息
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * 			SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author nianchun.li
 * @createTime Fri Feb 22 23:39:49 CST 2013
 */
public class UserCashe {
	private static UserCashe instant = null;
	private User user = null;
	private static final String savePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath()+"/mobileSurvey/userName.txt";
	
	/**
	 * 获取缓存用户信息实例
	 * 
	 * @return 用户信息实例
	 */
	public static UserCashe getInstant() {
		if (null == instant) {
			instant = new UserCashe();
			instant.user = new User();
			instant.user.setName(getUserName());
		}
		return instant;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static void saveUserName(String userName){
		FileOutputStream out = null;   

        try {
			out = new FileOutputStream(new File(savePath));  
			out.write(userName.getBytes());   
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}
	
	public static String getUserName(){
		String userName="";
		try {
			FileReader fr = new FileReader(savePath);  
			char[] buffer = new char[1024];  
			int ch = 0;  
			while((ch = fr.read())!=-1 )  
			{  
				userName = userName + (char)ch;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userName;

	}

}
