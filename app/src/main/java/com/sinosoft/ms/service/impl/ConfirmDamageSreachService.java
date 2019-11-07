package com.sinosoft.ms.service.impl;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.sinosoft.ms.service.IConfirmDamageSreachService;
import com.sinosoft.ms.utils.ActivityManage;

/**
 * 系统名：移动查勘定损 
 * 子系统名：核损信息查询服务接口实现
 * 著作权：COPYRIGHT (C) 2016 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author gongshihao
 * @createTime 上午9:11:39
 */

public class ConfirmDamageSreachService extends BasicHttp implements IConfirmDamageSreachService{

	private Activity activity = null;
	private SQLiteDatabase db = null;
	
	/**
	 * 
	 */
	public ConfirmDamageSreachService() {
		// TODO Auto-generated constructor stub
		activity = ActivityManage.getInstance().peek();
	}
	@Override
	public void TestData(String requestUser,String registNo, String registId) {
		// TODO Auto-generated method stub
		
		try {
			String xmlText=requestXml.confirmdamageSreachXML(requestUser, registNo, registId);
			sendRequest(xmlText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

