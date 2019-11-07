package com.sinosoft.ms.service.impl;

import com.sinosoft.ms.model.AppInfoBean;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.IUpdateService;
import com.sinosoft.ms.utils.xml.LoginParse;

/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 下午4:15:32
 */

public class UpdateService extends BasicHttp implements IUpdateService{

	@Override
	public AppInfoBean checkAppVersion (User user) throws Exception {
		AppInfoBean bean=null;
		try{
			String xml = requestXml.requestUserXML(user);
			sendRequest(xml);
			LoginParse parser = new LoginParse();
			parser.parse(inputStream);
			parser.getResult();
			bean = parser.getApp();
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			destroy();
		}
		return bean;
	}
   
}

