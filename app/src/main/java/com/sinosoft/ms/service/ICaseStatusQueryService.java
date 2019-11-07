package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.po.NodeData;

/**
 * 系统名：移动查勘定损 
 * 子系统名：定损信息查询服务接口 
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 05 13:11:00 CST 2013
 */
public interface ICaseStatusQueryService {
	
	/**
	 * 定损信息查询
	 * 
	 * @param requestUser
	 *            请求用户
	 * @param registNo
	 *            报案号
	 * @return 定损信息
	 * @throws Exception
	 */
	public List<NodeData> sreach(String requestUser,String taskId, String registId,String taskId1) throws Exception;
}
