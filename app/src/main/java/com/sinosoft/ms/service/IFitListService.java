package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.LossFitInfoItem;

/**
 * 系统名：移动查勘定损系统
 * 子系统名：换件价格查询接口服务
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT 
 *			INFORMATION SYSTEMS CORPORATION  ALL RIGHTS RESERVED.
 * @author nianchun.li
 * @createTime Thu Feb 07 06:15:10 CST 2013
 */
public interface IFitListService {
	
	/**
	 * 换件价格查询处理方法
	 * 
	 * @param requestUser
	 *            用户名
	 * @param type
	 *            价格方案
	 * @param vehicleType
	 *            车型编码
	 * @param carGroupId
	 *            车组ID
	 * @param areaId
	 *            区域ID
	 * @param typeName
	 *            零件名称
	 * @param damagePartId
	 * 				损伤部位零件Id
	 * @param keyWord
	 *            关键字
	 * @return 换件价格列表信息
	 */
	public List<LossFitInfoItem> execute(String requestUser, String type, String vehicleType,
			String carGroupId, String areaId, String typeName, String damagePartId,String keyWord,int tag)
			throws Exception;
}
