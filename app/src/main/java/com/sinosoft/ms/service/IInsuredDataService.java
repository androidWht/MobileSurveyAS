package com.sinosoft.ms.service;

import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.InsuredData;

/**
 * 系统名：MobileSurvey 
 * 子系统名：定损数据层服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public interface IInsuredDataService {

	/**
	 * 根据报案号获取定损基本信息
	 * 
	 * @param registNo
	 *            报案号
	 * @return 定损基本信息
	 * @throws Exception
	 */
	public InsuredData getByRegistNo(String registNo) throws Exception;
	
	/**
	 * 根据报案号获取定损基本信息
	 * 
	 * @param registNo
	 *            报案号
	 * @return 定损基本信息
	 * @throws Exception
	 */
	public InsuredData getByTaskId(String taskId) throws Exception;
	
	/**
	 * 更新定损基本信息
	 * 
	 * @param deflossData
	 *            定损基本信息
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean update(InsuredData insuredData) throws Exception;
	
	/**
	 * 保存定损基本信息
	 * 
	 * @param deflossData
	 *            定损基本信息
	 * @return 0失败 否则为成功
	 * @throws Exception
	 */
	public int save(InsuredData insuredData) throws Exception;

	/**
	 * 更新定损险别代码
	 * 
	 * @param deflossData
	 */
	public void updateInsureData(InsuredData insuredData);
}
