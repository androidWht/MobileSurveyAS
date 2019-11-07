package com.sinosoft.ms.model;

import android.graphics.Bitmap;

/**
 * 系统名：移动查勘定损 
 * 子系统名：查看影像数据结构类
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午2:08:33
 */

public class LookImage implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String codeType;
	private String codeValue;
	private String imageId;
	private String bitmapXml;
	private Bitmap bitmap;
	
	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getBitmapXml() {
		return bitmapXml;
	}

	public void setBitmapXml(String bitmapXml) {
		this.bitmapXml = bitmapXml;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	
}

