package com.sinosoft.ms.model;

import java.io.Serializable;
import java.util.List;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Administrator
 * @createTime 下午4:20:05
 */

public class LoginModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Business business;
	private List<AppInfoBean> appInfoBeanList;

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public List<AppInfoBean> getAppInfoBeanList() {
		return appInfoBeanList;
	}

	public void setAppInfoBeanList(List<AppInfoBean> appInfoBeanList) {
		this.appInfoBeanList = appInfoBeanList;
	}

}
