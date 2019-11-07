package com.sinosoft.ms.service.impl;

import android.util.Log;

import com.sinosoft.ms.service.IReceiveUpdateService;
import com.sinosoft.ms.utils.xml.ReceiveUpdateParse;

/**
 * 系统名：移动查勘定损系统
 * 子系统名：数据接收状态更新接口服务实现
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT 
 *			INFORMATION SYSTEMS CORPORATION  ALL RIGHTS RESERVED.
 * @author nianchun.li
 * @createTime Thu Feb 07 06:15:10 CST 2013
 */
public class ReceiveUpdateService extends BasicHttp implements IReceiveUpdateService {

	@Override
	public int execute(String requestUser, int type, String serverIds) throws Exception {
		int result = 0;
		try {
			 //加载任务中心的xml的文件 
			String xmlText = requestXml.rreceiceUpdateXML(requestUser, type, serverIds); 
			//发送任务中心请求，获取服务器数据
			sendRequest(xmlText); 
			//解析任务中心列表
			ReceiveUpdateParse receiveUpdateParse = new ReceiveUpdateParse();
			receiveUpdateParse.parse(inputStream);
			result = receiveUpdateParse.getResult();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			destroy();
		}
		return result;
	}

}
