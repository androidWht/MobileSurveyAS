package com.sinosoft.ms.service.impl;

import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.BusinessFactory;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.IHeartbeatService;
import com.sinosoft.ms.utils.xml.BusinessParser;

/**
 * 系统名：移动查勘定损 子系统名：心跳服务接口实现 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午11:17:28
 */

public class HeartbeatService extends BasicHttp implements IHeartbeatService {

	@Override
	public Business requestTaskNum(User user) throws Exception {
		Business business = null;
		try {
			// 1. 加载任务中心的xml的文件
			String xmlText = requestXml.requestTaskNumXML(user);
			// 2. 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 3. 解析任务中心列表
			BusinessParser businessParser = new BusinessParser();
			businessParser.parse(inputStream);
			// 任务数
			business = businessParser.getResult();
			if (business != null) {
				BusinessFactory.setBusiness(business);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
		}
		return business;
	}

	

	@Override
	public Business requestTaskVin(String taskId, String registId,String licenseNo, String vinNo) throws Exception {
		Business business = null;
		try {
			// 1. 加载任务中心的xml的文件
			String xmlText = requestXml. requestVinXML(taskId, registId,licenseNo,vinNo);
			// 2. 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 3. 解析任务中心列表
			BusinessParser businessParser = new BusinessParser();
			businessParser.parse(inputStream);
			// 任务数
			business = businessParser.getResult();
			if (business != null) {
				BusinessFactory.setBusiness(business);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
		}
		return business;
	}	
	
	
	
	/**
	 * 检查网络连接
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean checkNetwork(User user) throws Exception {
		boolean flag = false;
		try {
			// 1. 加载任务中心的xml的文件
			String xmlText = requestXml.requestTaskNumXML(user);
			flag = sendRequestTest(xmlText);
		} catch (Exception e) {
			flag = false;
		} finally {
		}
		return flag;
	}

	public boolean checkNetwork2() throws Exception {
		boolean flag = false;
		try {
			// 1. 加载任务中心的xml的文件
			String xmlText = requestXml.requestVersionXML();
			flag = sendRequestTest(xmlText);
		} catch (Exception e) {
			flag = false;
		} finally {
		}
		return flag;
	}



	/* (non-Javadoc)
	 * @see com.sinosoft.ms.service.IHeartbeatService#requestTaskVin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */

	
	
	
	
}
