package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.po.GeneralParty;


/**
 * 系统名：移动查勘定损系统
 * 子系统名：价格方案接口服务
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT 
 *			INFORMATION SYSTEMS CORPORATION  ALL RIGHTS RESERVED.
 * @author nianchun.li
 * @createTime Thu Feb 07 06:15:10 CST 2013
 */
public interface IPricePlanService {
	
	/**
	 * 价格方案处理方法
	 * 
	 * @param requestUser
	 *            用户名
	 * @param deptCode
	 *            机构代码
	 * @return 价格方案信息列表
	 */
	public List<GeneralParty> execute(String requestUser,String deptCode) throws Exception;
}
