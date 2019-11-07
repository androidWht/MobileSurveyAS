package com.sinosoft.ms.service.impl;

import java.util.List;

import com.sinosoft.ms.model.DicInfoBean;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.IDictionaryService;
import com.sinosoft.ms.utils.FileUtils;

/**
 * 系统名：移动查勘定损 
 * 子系统名：取得字典信息服务
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午5:43:14
 */

public class DictionaryService extends BasicHttp implements IDictionaryService{

	@Override
	public List<DicInfoBean> getDicInfo(User user,String synTime,String deptNo) throws Exception {
		//
		String xml = requestXml.requestDicInfoXML(user,synTime,deptNo);
		new FileUtils().write2SDFromString("", "dd.xml", xml);
		//
		sendRequest(xml);
		//3.解释反馈报文
//		new FileUtils().write2SDFromInput("", "dict.xml", inputStream);
		return responseXML.dicInfoParser(inputStream);
	}

}

