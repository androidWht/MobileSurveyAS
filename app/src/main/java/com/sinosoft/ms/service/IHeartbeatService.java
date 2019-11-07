package com.sinosoft.ms.service;

import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.User;

/**
 * 系统名：移动查勘定损 
 * 子系统名：心跳（网络服务）
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 上午11:15:39
 */

public interface IHeartbeatService {
	
	/**
	 * 心跳处理
	 * 
	 * @param user 请求用户
	 * @return 任务数
	 * @throws Exception
	 */
     public Business requestTaskNum(User user) throws Exception;
     
     
 	 public Business requestTaskVin(String taskId, String registId,String licenseNo,String vinNo) throws Exception;

}

