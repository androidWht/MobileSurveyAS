package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.LossVehicleInsurance;

/**
 * 系统名：MobileSurvey 
 * 子系统名：定损车辆数据层服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public interface ILossVehicleService {
	/**
	 * 保存定损车辆信息
	 * 
	 * @param lossVehicle
	 *            定损车辆信息
	 * @return 0失败 否则为成功
	 * @throws Exception
	 */
	public int save(LossVehicle lossVehicle) throws Exception;
	
	/**
	 * 根据报案号获取定损车辆信息
	 * 
	 * @param registNo
	 *            报案号
	 * @return 定损车辆信息
	 * @throws Exception
	 */
	public LossVehicle getByRegistNo(String registNo) throws Exception;
	
	/**
	 * 更新定损车辆信息
	 * 
	 * @param lossVehicle
	 *            定损车辆信息
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean update(LossVehicle lossVehicle) throws Exception;
	
	/**
	 * 保存定损车辆承保信息
	 * 
	 * @param lossVehicleInsurance
	 *            定损车辆承保信息
	 * @return 0失败 否则为成功
	 * @throws Exception
	 */
	public int save(LossVehicleInsurance lossVehicleInsurance) throws Exception;
	
	
	/**
	 * 根据报案号获取定损车辆承保信息
	 * 
	 * @param registNo
	 *            报案号
	 * @return 定损车辆承保信息
	 * @throws Exception
	 */
	public List<LossVehicleInsurance> getById(Integer id) throws Exception;
	
	/**
	 * 更新定损车辆承保信息
	 * 
	 * @param lossVehicleInsurance
	 *            定损车辆承保信息
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean update(LossVehicleInsurance lossVehicleInsurance) throws Exception;
	
	/**
	 * 删除定损车辆承保信息
	 * 
	 * @param lossVehicleInsurance
	 *            定损车辆承保信息
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean delete(LossVehicleInsurance lossVehicleInsurance) throws Exception;
	
	
	
	
	
}
