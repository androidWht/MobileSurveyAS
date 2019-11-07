package com.sinosoft.ms.service.impl;

import java.util.List;

import android.util.Log;

import com.sinosoft.ms.model.po.GeneralParty;
import com.sinosoft.ms.service.IPricePlanService;
import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.xml.PricePlanParse;
/**
 * 系统名：移动查勘定损系统
 * 子系统名：价格方案服务接口
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT 
 *			INFORMATION SYSTEMS CORPORATION  ALL RIGHTS RESERVED.
 * @author nianchun.li
 * @createTime Thu Feb 07 06:15:10 CST 2013
 */
public class PricePlanService extends BasicHttp implements IPricePlanService {

	@Override
	public List<GeneralParty> execute(String requestUser, String deptCode)
			throws Exception {
		List<GeneralParty> list = null;
		try {
			 //加载任务中心的xml的文件 
			String xmlText = requestXml.pricePlanXML(requestUser, deptCode); 
			//发送任务中心请求，获取服务器数据
			sendRequest(xmlText); 
			//解析任务中心列表
			PricePlanParse pricePlanParse = new PricePlanParse();
			//new FileUtils().write2SDFromString("", "request_price.xml", xmlText);
			//new FileUtils().write2SDFromInput("", "price.xml", inputStream);
			pricePlanParse.parse(inputStream);
			list = pricePlanParse.getResult();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
		}
		return list;
	}
	
}
