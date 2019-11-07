package com.sinosoft.ms.service;

/**
 * 系统名：移动查勘定损系统
 * 子系统名：数据接收状态更新接口服务
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT 
 *			INFORMATION SYSTEMS CORPORATION  ALL RIGHTS RESERVED.
 * @author nianchun.li
 * @createTime Thu Feb 07 06:15:10 CST 2013
 */
public interface IReceiveUpdateService {

	/**
	 * 数据接收状态更新处理方法 (无用到)
	 * 
	 * @param requestUser
	 *            用户名
	 * @param type
	 *            业务类型 1.任务中心2. 案件中心3.
	 * @param serverIds
	 *            服务器记录ID列表 (多个记录ID之间用”|”分割 例: 1|2|3)
	 * @return 0失败 1成功
	 */
	public int execute(String requestUser,int type,String serverIds) throws Exception;
}
