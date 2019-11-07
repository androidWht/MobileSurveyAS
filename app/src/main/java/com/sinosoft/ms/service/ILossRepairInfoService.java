package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.LossRepairInfoItem;

/**
 * 系统名：MobileSurvey 
 * 子系统名：定损换件数据层服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public interface ILossRepairInfoService {

	/**
	 * 根据报案号获取定损换件
	 * 
	 * @param registNo
	 *            报案号
	 * @return 定损换件
	 * @throws Exception
	 */
	public List<LossRepairInfoItem> getByRegistNo(String registNo) throws Exception;
	
	/**
	 * 更新定损换件
	 * 
	 * @param LossRepairInfoItem
	 *            定损换件
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean update(LossRepairInfoItem lossRepairInfoItem) throws Exception;
	
	/**
	 * 保存定损换件
	 * 
	 * @param lossRepairInfoItem
	 *            定损换件
	 * @return 0失败 否则为成功
	 * @throws Exception
	 */
	public int save(LossRepairInfoItem lossRepairInfoItem) throws Exception;

	/**
	 * 根据修理ID获取修理数据
	 * 
	 * @param lossRepairItemId 
	 * 			   修理ID
	 * @return 修理数据
	 */
	public LossRepairInfoItem getById(int lossRepairItemId);

	/**
	 * 查询维修信息
	 * 
	 * @param professionId 子选项
	 * @param keyName 关键字
	 * @param professionName 工种名称
	 * @param professionType 工种类型
	 * @param repairType 工种类型名称
	 * @return 维修信息
	 */
	public List<LossRepairInfoItem> getRepairList(String professionId,
			String keyName,String registNo, String professionType, 
			String professionName,String repairType);

	/**
	 * 根据修理ID删除修理信息
	 * 
	 * @param id
	 *            修理ID
	 * @return true成功 false失败
	 */
	public boolean delete(int id);
	
	/**
	 * 
	 * @param jyId 服务器返回精友Id
	 * @return
	 */
	public boolean deleteByJyId(String jyId);
}
