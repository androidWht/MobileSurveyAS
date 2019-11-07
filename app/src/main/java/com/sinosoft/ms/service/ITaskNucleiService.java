package com.sinosoft.ms.service;
/**
 * 系统名：移动查勘定损 
 * 子系统名：核损通过接口
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Hijack
 * @createTime 上午10:49:28
 */

public interface ITaskNucleiService {
	
	/**
	 * 定损任务核损通过接口
	 * @param taskId 任务id
	 * @return 0-核损不通过 1-核损通过
	 */
	public int checkTaskNuclei(String taskId,String registId);
}

