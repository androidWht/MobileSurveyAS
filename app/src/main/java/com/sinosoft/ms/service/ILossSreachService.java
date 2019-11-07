package com.sinosoft.ms.service;

import com.sinosoft.ms.model.DeflossData;

/**
 * 系统名：移动查勘定损 
 * 子系统名：定损信息查询服务接口 
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 05 13:11:00 CST 2013
 */
public interface ILossSreachService {
	
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
	public DeflossData sreach(String requestUser,String taskId,String registNo, String registId) throws Exception;
	public DeflossData sreach(String requestUser,String taskId,String registNo, String registId,String time) throws Exception;
}
