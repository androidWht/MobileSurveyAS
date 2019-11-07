package com.sinosoft.ms.model;
/**
 * 系统名：移动查勘定损 
 * 子系统名：系统网络信息类
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Administrator
 * @createTime 上午10:34:54
 */

public class NetInfo {

	/**
	 * 主数据接口地址
	 */
	private String url;
	/**
	 * 图片上传接口地址
	 */
	private String imgUrl;
	/**
	 * 图片下载接口地址
	 */
	private String downUrl;
	
	/**
	 * APP版本更新地址
	 */
	private String appUrl;
	
	/**
	 * 查勘相机下载地址
	 */
	private String scUrl;
	
	
	/**
	 * 照片下载地址
	 */
	private String downImagUrl;
	
	
	
	/**
	 * 主数据接口地址
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * 主数据接口地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 图片上传接口地址
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	
	/**
	 * 图片上传接口地址
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	/**
	 * 图片下载接口地址
	 */
	public String getDownUrl() {
		return downUrl;
	}
	
	/**
	 * 图片下载接口地址
	 */
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	/**
	 * APP版本更新地址
	 */
	public String getAppUrl() {
		return appUrl;
	}

	/**
	 * APP版本更新地址
	 */
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getScUrl() {
		return scUrl;
	}

	public void setScUrl(String scUrl) {
		this.scUrl = scUrl;
	}

	public String getDownImagUrl()
	{
		return downImagUrl;
	}

	public void setDownImagUrl(String downImagUrl)
	{
		this.downImagUrl = downImagUrl;
	}
	
	
}

