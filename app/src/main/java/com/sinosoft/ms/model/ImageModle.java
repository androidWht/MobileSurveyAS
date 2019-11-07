package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘
 * 子系统名：
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午12:41:53
 */

public class ImageModle implements Serializable{
	private String registNo;   
	private String type;       
	private String pictureName;
	private String savePath;   
	private String pictureSize;
	private String pictureType;
	private String uploadTime; 
	private String gpsInfo;    
	private String remark;
	/**
	 * @return the registNo
	 */
	public String getRegistNo() {
		return registNo;
	}
	/**
	 * @param registNo the registNo to set
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the pictureName
	 */
	public String getPictureName() {
		return pictureName;
	}
	/**
	 * @param pictureName the pictureName to set
	 */
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	/**
	 * @return the savePath
	 */
	public String getSavePath() {
		return savePath;
	}
	/**
	 * @param savePath the savePath to set
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	/**
	 * @return the pictureSize
	 */
	public String getPictureSize() {
		return pictureSize;
	}
	/**
	 * @param pictureSize the pictureSize to set
	 */
	public void setPictureSize(String pictureSize) {
		this.pictureSize = pictureSize;
	}
	/**
	 * @return the pictureType
	 */
	public String getPictureType() {
		return pictureType;
	}
	/**
	 * @param pictureType the pictureType to set
	 */
	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}
	/**
	 * @return the uploadTime
	 */
	public String getUploadTime() {
		return uploadTime;
	}
	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	/**
	 * @return the gpsInfo
	 */
	public String getGpsInfo() {
		return gpsInfo;
	}
	/**
	 * @param gpsInfo the gpsInfo to set
	 */
	public void setGpsInfo(String gpsInfo) {
		this.gpsInfo = gpsInfo;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}     
   

}

