package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.LossVehicleInsurance;

/**
 * 系统名：移动查勘定损 子系统名：定损银行信息控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Hijack
 * @createTime 上午10:24:11
 */

public interface IBankInfoService {
	/**
	 * 保存银行信息
	 * 
	 * @param bankInfo
	 *            定损银行信息
	 * @return false失败 否则为成功
	 * @throws Exception
	 */
	public boolean save(BankInfo bankInfo) throws Exception;

	/**
	 * 根据报案号获取定损车辆信息
	 * 
	 * @param registNo
	 *            报案号
	 * @return 定损车辆信息
	 * @throws Exception
	 */
	public BankInfo getByRegistNo(String registNo) throws Exception;
	
	/**
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public BankInfo getByTaskId(String taskId) throws Exception;

	/**
	 * 更新定损银行信息
	 * 
	 * @param lossVehicle
	 *            定损银行信息
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean update(BankInfo bankInfo) throws Exception;

	/**
	 * 删除定损银行信息
	 * 
	 * @param lossVehicleInsurance
	 *            定损银行信息
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean delete(String taskId)
			throws Exception;
}
