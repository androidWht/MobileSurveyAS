package com.sinosoft.ms.service.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.sinosoft.ms.model.Item;
import com.sinosoft.ms.service.IRepairFactoryService;
import com.sinosoft.ms.utils.xml.RepairFactoryParse;

/**
 * 系统名：移动查勘
 * 子系统名：
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author wuhaijie
 * @createTime 上午10:41:43
 */

public class RepairFactoryService extends BasicHttp implements IRepairFactoryService{

	/* (non-Javadoc)
	 * @see com.sinosoft.ms.service.IRepairFactoryService#getRepairFactory(java.lang.String, java.lang.String, android.content.Context)
	 */
	@Override
	public List<Item> getRepairFactory(String keyword,
			Context context) throws Exception {
		// TODO Auto-generated method stub
		List<Item> result = new ArrayList<Item>();
		try {
			 //加载任务中心的xml的文件 
			String xmlText = requestXml.requestRepairFactoryXML(keyword); 
			//发送任务中心请求，获取服务器数据
			sendRequest(xmlText); 
			//解析任务中心列表
			RepairFactoryParse repairFactoryParse = new RepairFactoryParse();
			repairFactoryParse.parse(inputStream);
			result = repairFactoryParse.getResult();
//			Map<String,String> map = repairFactoryParse.getMap();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			destroy();
		}
		return result;
	}
	


}

