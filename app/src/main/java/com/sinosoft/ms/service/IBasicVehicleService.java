package com.sinosoft.ms.service;

import com.sinosoft.ms.model.BasicVehicle;

/**
 * 系统名：MobileSurvey 
 * 子系统名：车辆基本信息数据层服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public interface IBasicVehicleService {

	/**
	 * 根据报案号获取车辆基本信息
	 * 
	 * @param registNo
	 *            报案号
	 * @return 车辆基本信息
	 * @throws Exception
	 */
	public BasicVehicle getByRegistNo(String registNo) throws Exception;
	
	/**
	 * 更新车辆基本信息
	 * 
	 * @param basicVehicle
	 *            车辆基本信息
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean update(BasicVehicle basicVehicle) throws Exception;
	
	/**
	 * 保存车辆基本信息
	 * 
	 * @param basicVehicle
	 *            车辆基本信息
	 * @return 0失败 否则为成功
	 * @throws Exception
	 */
	public int save(BasicVehicle basicVehicle) throws Exception;

	/**
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public BasicVehicle getByTaskId(String taskId) throws Exception;
}
