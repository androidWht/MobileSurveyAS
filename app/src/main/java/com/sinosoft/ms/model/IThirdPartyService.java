package com.sinosoft.ms.model;

import java.util.List;

import com.sinosoft.ms.model.po.Brand;
import com.sinosoft.ms.model.po.Car;
import com.sinosoft.ms.model.po.Emus;
import com.sinosoft.ms.model.po.GeneralParty;
import com.sinosoft.ms.model.po.Manufacturer;
/**
 * 系统名：MobileSurvey 
 * 子系统名：第三方服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 说明:本接口定义与精友业务相关接口服务
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public interface IThirdPartyService {

	/**
	 * 获取厂家信息
	 * 
	 * @return 厂家信息
	 */
	public List<Manufacturer> getFactory();
	
	/**
	 * 获取品牌信息（根据厂家信息获取）
	 * 
	 * @param id 厂家信息ID
	 * @return
	 */
	public List<Brand> getBandByManufacturerId(String id);
	
	/**
	 * 获取车系
	 * 
	 * @param id 品牌ID
	 * @return
	 */
	public List<Car> getCarByBrandId(String id);
	
	/**
	 * 获取车组
	 * 
	 * @param id 车系ID
	 * @return
	 */
	public List<Emus> getEmusByCarId(String id);
	
	
	/**
	 * 获取车型编码
	 * 
	 * @param id 车系ID
	 * @param vehSeriName 车系名称
	 * @return 车型编码
	 */
	public List<VehicleType> getVehicleTypeByEmusId(String id, String vehSeriName);
	
	/**
	 * 获取更换部位信息
	 * 
	 * @return 更换部位信息
	 */
	public List<GeneralParty> getReplacementParts();

	/**
	 * 获取零件分组分类信息
	 *  说明:当有多个下级内容时递归调用本方法
	 * 
	 * @param id
	 *            零件分类ID
	 * @return 分组分类信息
	 */
	public List<GeneralParty> getPartsGroupName(String id);

	/**
	 * 获取换件关键字
	 * 
	 * @return 关键字列表
	 */
	public List<GeneralParty> getChangeItemKeywords();

	/**
	 * 根据ID获取维修工种类型
	 * 
	 * @param ID
	 * @return 维修工种类型
	 */
	public List<GeneralParty> getRepairTypeById(String id);

	/**
	 * 获取辅料选项
	 * 
	 * @return 辅料选项
	 */
	public List<GeneralParty> getAssistItem();
	
}
