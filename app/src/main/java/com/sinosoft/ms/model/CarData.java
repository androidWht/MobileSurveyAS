package com.sinosoft.ms.model;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘
 * 子系统名：涉案车辆
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午6:00:31
 */

public class CarData implements Serializable{
 private String carId;//唯一标示
 private String registNo;//报案号
 private String checkCarId;//涉案车辆ID	
 private String lossItemType;//损失类型	
 private String licenseNo;//车牌号
 private String carOwner;//车主	
 private String engineNo;//发动机号
 private String vinNo; //Vin码	
 private String frameNo;
 private String licenseType;//号牌种类
 private String liabilityType;//交强险责任类型	
 private CarDriverData carDriverData;//
 private CarLossData carLossData;

 
 private List<CarPolicyData> carpolicyList;//

 
 
/**
 * @return the carId
 */
public String getCarId() {
	return carId;
}

/**
 * @param carId the carId to set
 */
public void setCarId(String carId) {
	this.carId = carId;
}

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
 * @return the checkCarId
 */
public String getCheckCarId() {
	return checkCarId;
}

/**
 * @param checkCarId the checkCarId to set
 */
public void setCheckCarId(String checkCarId) {
	this.checkCarId = checkCarId;
}

/**
 * @return the lossItemType
 */
public String getLossItemType() {
	return lossItemType;
}

/**
 * @param lossItemType the lossItemType to set
 */
public void setLossItemType(String lossItemType) {
	this.lossItemType = lossItemType;
}

/**
 * @return the licenseNo
 */
public String getLicenseNo() {
	return licenseNo;
}

/**
 * @param licenseNo the licenseNo to set
 */
public void setLicenseNo(String licenseNo) {
	this.licenseNo = licenseNo;
}

/**
 * @return the carOwner
 */
public String getCarOwner() {
	return carOwner;
}

/**
 * @param carOwner the carOwner to set
 */
public void setCarOwner(String carOwner) {
	this.carOwner = carOwner;
}

/**
 * @return the engineNo
 */
public String getEngineNo() {
	return engineNo;
}

/**
 * @param engineNo the engineNo to set
 */
public void setEngineNo(String engineNo) {
	this.engineNo = engineNo;
}

/**
 * @return the vinNo
 */
public String getVinNo() {
	return vinNo;
}

/**
 * @param vinNo the vinNo to set
 */
public void setVinNo(String vinNo) {
	this.vinNo = vinNo;
}

/**
 * @return the licenseType
 */
public String getLicenseType() {
	return licenseType;
}

/**
 * @param licenseType the licenseType to set
 */
public void setLicenseType(String licenseType) {
	this.licenseType = licenseType;
}

/**
 * @return the liabilityType
 */
public String getLiabilityType() {
	return liabilityType;
}

/**
 * @param liabilityType the liabilityType to set
 */
public void setLiabilityType(String liabilityType) {
	this.liabilityType = liabilityType;
}

/**
 * @return the carpolicyList
 */
public List<CarPolicyData> getCarpolicyList() {
	return carpolicyList;
}

/**
 * @param carpolicyList the carpolicyList to set
 */
public void setCarpolicyList(List<CarPolicyData> carpolicyList) {
	this.carpolicyList = carpolicyList;
}

/**
 * @return the carDriverData
 */
public CarDriverData getCarDriverData() {
	return carDriverData;
}

/**
 * @param carDriverData the carDriverData to set
 */
public void setCarDriverData(CarDriverData carDriverData) {
	this.carDriverData = carDriverData;
}

/**
 * @return the carLossData
 */
public CarLossData getCarLossData() {
	return carLossData;
}

/**
 * @param carLossData the carLossData to set
 */
public void setCarLossData(CarLossData carLossData) {
	this.carLossData = carLossData;
}
 
 /**
 * @return the frameNo
 */
public String getFrameNo() {
	return frameNo;
}

/**
 * @param frameNo the frameNo to set
 */
public void setFrameNo(String frameNo) {
	this.frameNo = frameNo;
}

/**
 * @return the policyDatas
 */

public void init(){
	 carId=carId==null?"":carId;                         
	 registNo=registNo==null?"":registNo;                
	 checkCarId=checkCarId==null?"":checkCarId;          
	 lossItemType=lossItemType==null?"":lossItemType;    
	 licenseNo=licenseNo==null?"":licenseNo;             
	 carOwner=carOwner==null?"":carOwner;                
	 engineNo=engineNo==null?"":engineNo;                
	 vinNo=vinNo==null?"":vinNo;                      
	 licenseType=licenseType==null?"":licenseType;       
	 liabilityType=liabilityType==null?"":liabilityType; 
	 frameNo=StringUtils.isEmpty(frameNo)?"":frameNo;

 }
 
 
}