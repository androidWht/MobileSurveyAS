package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.LossAssistInfoItem;

/**
 * 系统名：MobileSurvey 
 * 子系统名：定损辅料数据层服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public interface ILossAssistInfoService {

	/**
	 * 根据报案号获取定损辅料
	 * 
	 * @param taskId
	 *           报案id，就是传taskId
	 * @return 定损辅料
	 * @throws Exception
	 */
	public List<LossAssistInfoItem> getByRegistNo(String taskId) throws Exception;
	
	/**
	 * 更新定损辅料
	 * 
	 * @param lossAssistInfoItem
	 *            定损辅料
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean update(LossAssistInfoItem lossAssistInfoItem) throws Exception;
	
	/**
	 * 保存定损辅料
	 * 
	 * @param lossAssistInfoItem
	 *            定损辅料
	 * @return 0失败 否则为成功
	 * @throws Exception
	 */
	public int save(LossAssistInfoItem lossAssistInfoItem) throws Exception;

	public LossAssistInfoItem getById(int assistInfoId);
	
	
	/**
	 * 根据辅料ID删除辅料信息
	 * 
	 * @param id
	 *            辅料ID
	 * @return true成功 false失败
	 */
	public boolean delete(int assistInfoid);
	
	/**
	 * 
	 * @param assistId 服务器返回精友Id
	 * @return
	 */
	public boolean deleteByJyId(String jyId);
}
