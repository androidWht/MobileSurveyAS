package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.KindCodeData;

/**
 * 系统名：MobileSurvey 
 * 子系统名：定损险别数据层服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public interface IKindCodeDataService {

	/**
	 * 根据报案号获取定损险别
	 * 
	 * @param registNo
	 *            报案号
	 * @return 定损险别
	 * @throws Exception
	 */
	public List<KindCodeData> getById(long id) throws Exception;
	
	/**
	 * 更新定损险别
	 * 
	 * @param kindCodeData
	 *            定损险别
	 * @return true成功 false失败
	 * @throws Exception
	 */
	public boolean update(KindCodeData kindCodeData) throws Exception;
	
	/**
	 * 保存定损险别
	 * 
	 * @param kindCodeData
	 *            定损险别
	 * @return 0失败 否则为成功
	 * @throws Exception
	 */
	public int save(KindCodeData kindCodeData) throws Exception;
}
