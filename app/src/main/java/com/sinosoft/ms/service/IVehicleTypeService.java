package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.VehicleType;

/**
 * 系统名：移动查勘定损系统
 * 子系统名：车辆定型查询服务接口
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT 
 *			INFORMATION SYSTEMS CORPORATION  ALL RIGHTS RESERVED.
 * @author nianchun.li
 * @createTime Thu Feb 07 06:15:10 CST 2013
 */
public interface IVehicleTypeService {

	/**
	 * 车辆定型查询处理方法
	 * 
	 * @param requestUser
	 *            用户名
	 * @param czid
	 *            车系ID
	 * @param vehseriName
	 *            车系名称
	 * @return 车辆定型信息列表
	 */
	public List<VehicleType> execute(String requestUser,String czid,String vehseriName) throws Exception;
}
