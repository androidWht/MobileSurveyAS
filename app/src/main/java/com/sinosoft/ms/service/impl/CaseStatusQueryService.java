package com.sinosoft.ms.service.impl;

import java.util.List;

import com.sinosoft.ms.model.po.NodeData;
import com.sinosoft.ms.service.ICaseStatusQueryService;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.FileUtil;
/**
 * 系统名：移动查勘定损 
 * 子系统名：案件状态查询服务接口实现 
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 05 13:11:00 CST 2013
 */
public class CaseStatusQueryService extends BasicHttp implements
		ICaseStatusQueryService {

	@Override
	public List<NodeData> sreach(String requestUser, String registNo, String registId, String taskId )
			throws Exception {
		List<NodeData> nodeDataList = null;
		try {
			//1.获取请求报文
			String xmlText = requestXml.caseQueryXml(requestUser, registNo, registId, taskId);
			//2.发送请求
			sendRequest(xmlText);
			//3.解释反馈报文
//			FileUtil.write2SDFromInput(AppConstants.PATH+"gong.txt", inputStream);
			nodeDataList = responseXML.caseQueryParse(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			destroy();
		}
		return nodeDataList;
	}

}
