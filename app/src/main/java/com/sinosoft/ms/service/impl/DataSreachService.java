package com.sinosoft.ms.service.impl;

import java.util.List;

import com.sinosoft.ms.model.AgeingStatBean;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.sub.ParamBean;
import com.sinosoft.ms.service.IDataSreachService;
import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.xml.DataSearchParser;
import com.sinosoft.ms.utils.xml.PrescriptionListParser;
import com.sinosoft.ms.utils.xml.RequestXml;

/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 上午9:09:59
 */

public class DataSreachService extends BasicHttp implements IDataSreachService{
  
	@Override
	public List<RegistData> execute(ParamBean paramBean) throws Exception {
		String xml=new RequestXml().requestDataSearch(paramBean);//取得发送的报文
		sendRequest(xml);//发送请求
		DataSearchParser parser=new DataSearchParser();
		//LogFatory.d("取得数据", "sdfsfsf"+getString(inputStream));
	    new FileUtils().write2SDFromString("", "data_search.xml", xml);
	    //("", "data.xml", inputStream);
		parser.parse(inputStream);
		
		if(parser.responseCode!=null&&parser.responseCode.equals("0")){
			throw new IllegalArgumentException(""+parser.errorMessage);
		}
		List<RegistData> list=parser.getResult();
		
		return list;
	}

	@Override
	public List<AgeingStatBean> getPrescriptionListByReportNo(String reportNo)
			throws Exception {
		//取得发送的报文
		String xml=new RequestXml().requestPrescriptionXML(reportNo);
		//发送请求
		sendRequest(xml);
		//解释XML
		PrescriptionListParser parser=new PrescriptionListParser();
		parser.parse(inputStream);
		if(parser.responseCode!=null&&parser.responseCode.equals("0")){
			throw new IllegalArgumentException(""+parser.errorMessage);
		}
		List<AgeingStatBean> list=parser.getResult();
		return list;
	}
}

