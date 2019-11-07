package com.sinosoft.ms.service;

/**
 * 系统名：移动查勘定损 子系统名：查勘服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 16:45:12
 */
public interface ISurveyService {

	/**
	 * 更新查勘状态 说明：本方法执行查勘状态的更新，由系统程序调用 调用时机： 1.查勘员登录系统不接收调度任务时 2.查勘任务完成时
	 * 
	 * @param reportNo
	 *            报案号
	 * @return 0更新失败 1更新成功
	 * @throws Exception
	 */
	public int updataSurveyStatus(String reportNo) throws Exception;
}
