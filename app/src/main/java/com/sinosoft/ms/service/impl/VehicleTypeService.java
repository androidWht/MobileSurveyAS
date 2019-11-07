package com.sinosoft.ms.service.impl;

import java.util.List;

import android.util.Log;

import com.sinosoft.ms.model.VehicleType;
import com.sinosoft.ms.service.IVehicleTypeService;
import com.sinosoft.ms.utils.xml.VehicleTypeParse;

/**
 * 系统名：移动查勘定损系统
 * 子系统名：车辆定型查询服务接口
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT 
 *			INFORMATION SYSTEMS CORPORATION  ALL RIGHTS RESERVED.
 * @author nianchun.li
 * @createTime Thu Feb 07 06:15:10 CST 2013
 */
public class VehicleTypeService extends BasicHttp implements IVehicleTypeService {
	
	@Override
	public List<VehicleType> execute(String requestUser, String czid,
			String vehseriName) throws Exception {
		List<VehicleType> list = null;
		try {
			 //加载任务中心的xml的文件 
			String xmlText = requestXml.vehicleTypeXML(requestUser, czid,
					vehseriName); 
			//发送任务中心请求，获取服务器数据
			sendRequest(xmlText); 
			//解析任务中心列表
			VehicleTypeParse vehicleTypeParse = new VehicleTypeParse();
			vehicleTypeParse.parse(inputStream);
			list = vehicleTypeParse.getResult();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			destroy();
		}
		return list;
	}
}
