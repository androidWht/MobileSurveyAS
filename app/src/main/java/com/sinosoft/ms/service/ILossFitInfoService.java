package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.LossFitInfoItem;

/**
 * 系统名：MobileSurvey 
 * 子系统名：定损修理数据层服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public interface ILossFitInfoService {

	/**
	 * 根据报案号获取定损修理
	 * 
	 * @param taskId	//任务号，实际就是传taskId
	 * 
	 * @return 定损修理
	 * @throws Exception
	 */
	public List<LossFitInfoItem> getByRegistNo(String taskId) throws Exception;
	
	
	public List<LossFitInfoItem> getByString(String from,String to);
	
	/**
	 * 更新定损修理
	 * 
	 * @param lossFitInfoItem
	 *            定损修理
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean update(LossFitInfoItem lossFitInfoItem) throws Exception;
	
	/**
	 * 保存定损修理
	 * 
	 * @param lossFitInfoItem
	 *            定损修理
	 * @return 0失败 否则为成功
	 * @throws Exception
	 */
	public int save(LossFitInfoItem lossFitInfoItem) throws Exception;

	/**
	 * 根据换件ID获取换件信息
	 * 
	 * @param lossFitInfoItemId
	 *            换件ID
	 * @return 换件信息
	 */
	public LossFitInfoItem getById(int lossFitInfoItemId);

	/**
	 * 查询当前区域的前保险杠对应的零件价格信息（的）
	 *  说明:当类型使用特约修理厂进行查询时可以不用传区域ID
	 * 
	 * @param type
	 *            1.一类、二类修理厂 2.特约修理厂
	 * @param carGroupId
	 *            车组ID
	 * @param areaId
	 *            区域ID
	 * @param typeName
	 *            零件类型名称
	 * @return 零件价格信息
	 * @throws Exception
	 */
	public List<LossFitInfoItem> getFitList(String requestUser, int type,
			String carGroupId, String areaId, String typeName) throws Exception;

	/**
	 * 删除换件信息
	 * 
	 * @param Id 换件ID
	 * @return true成功 false失败
	 */
	public boolean delete(int Id)throws Exception;

	/**
	 * 删除换件信息
	 * @param jyId 服务器返回精友Id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteByJyId(String jyId) throws Exception;
	
	/**
	 * 更新价格方案
	 * 
	 * @param taskId 任务ID
	 * @param chgCompSetCode 方案代码
	 */
	public void updatePlan(String taskId, String chgCompSetCode);
	
}
