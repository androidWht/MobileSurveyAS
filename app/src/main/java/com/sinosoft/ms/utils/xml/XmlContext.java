package com.sinosoft.ms.utils.xml;

import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.sub.TaskTag;

/**
 * 系统名：移动查勘定损 子系统名：XML内容 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 14:10:24
 */
public class XmlContext {
	/**
	 * 登录XML
	 * 
	 * @param user
	 *            用户信息
	 * @return xml文件内容
	 */
	public String loginXML(User user, TaskTag taskTag) {
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
				+ " <package> " + "  <head> "
				+ "   <requestType>U0001</requestType> " + "  </head> "
				+ "  <body/> " + " </package>";
		return result;
	}

	/**
	 * 测试XML
	 * 
	 * @param user
	 *            用户信息
	 * @return xml文件内容
	 */
	public String testXML() {
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
				+ " <package> " + "  <head> "
				+ "   <requestType>U0001</requestType> " + "  </head> "
				+ "  <body/> " + " </package>";
		return result;
	}
}
