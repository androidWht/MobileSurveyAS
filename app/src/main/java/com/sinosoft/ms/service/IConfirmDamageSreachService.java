package com.sinosoft.ms.service;
/**
 * 系统名：移动查勘定损 
 * 子系统名：核损信息查询服务接口 
 * 著作权：COPYRIGHT (C) 2016 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author gongshihao
 * @createTime 上午9:13:12
 */

public interface IConfirmDamageSreachService {
	/**
	 * 核损信息查询
	 * 
	 * @param requestUser
	 *            请求用户
	 * @param registNo
	 *            报案号
	 * @return 定损信息
	 * @throws Exception
	 */
	public void TestData(String requestUser,String registNo, String registId);
}

