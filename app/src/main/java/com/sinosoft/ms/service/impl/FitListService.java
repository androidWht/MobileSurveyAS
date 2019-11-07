package com.sinosoft.ms.service.impl;

import java.util.List;

import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.service.IFitListService;
import com.sinosoft.ms.utils.xml.FitInfoParse;
/**
 * 系统名：移动查勘定损系统
 * 子系统名：换件价格查询服务接口实现
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT 
 *			INFORMATION SYSTEMS CORPORATION  ALL RIGHTS RESERVED.
 * @author nianchun.li
 * @createTime Thu Feb 07 06:15:10 CST 2013
 */
public class FitListService extends BasicHttp implements IFitListService {

	@Override
	public List<LossFitInfoItem> execute(String requestUser, String type, String vehicleType,
			String carGroupId, String areaId, String typeName, String damagePartId,String keyWord,int tag)
			throws Exception {
		List<LossFitInfoItem> list = null;
		try {
			//1. 加载任务中心的xml的文件
			String xmlText = requestXml.fitListXML(requestUser, type,
					vehicleType, carGroupId, areaId, typeName, damagePartId,keyWord,tag);
			//2. 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			//3. 解析任务中心列表
			FitInfoParse fitInfoParse = new FitInfoParse();
			fitInfoParse.parse(inputStream);
			list = fitInfoParse.getResult();

		} catch (Exception e) {
			e.printStackTrace();
			//Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
		}
		return list;
	}
}
