package com.sinosoft.ms.model.po;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author pei
 * @createTime 下午9:30:34
 */

public class ImageBean {

	private String registNo;
	private boolean isUpload;

	/**
	 * @return the registNo
	 */
	public String getRegistNo() {
		return registNo;
	}

	/**
	 * @param registNo
	 *            the registNo to set
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * @return the isUpload
	 */
	public boolean isUpload() {
		return isUpload;
	}

	/**
	 * @param isUpload
	 *            the isUpload to set
	 */
	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}

}
