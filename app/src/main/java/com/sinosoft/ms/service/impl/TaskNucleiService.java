package com.sinosoft.ms.service.impl;

import com.sinosoft.ms.service.ITaskNucleiService;

/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Hijack
 * @createTime 上午10:49:11
 */

public class TaskNucleiService extends BasicHttp implements ITaskNucleiService{

	/* (non-Javadoc)
	 * @see com.sinosoft.ms.service.ITaskNucleiService#checkTaskNuclei(java.lang.String)
	 */
	@Override
	public int checkTaskNuclei(String taskId, String registId) {
		// TODO Auto-generated method stub
		int result = 0;
		//3.解释反馈报文
		try {
			String xmlText = requestXml.lossCheckNuclei(taskId, registId);
			//2.发送请求
			sendRequest(xmlText);
			result = responseXML.lossCheckNucleiParse(inputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			destroy();
		}
		return result;
	}

}

