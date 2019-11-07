package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘
 * 子系统名：涉案车驾驶员信息（隶属于涉案车辆信息）
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午10:50:22
 */

public class CarDriverData implements Serializable{
	  private String id;
	  private String carId;
	  private String driverId;//唯一标识
	  private String checkCarId;//涉案车辆ID	;
	  private String driverName;//驾驶员姓名	;
	  private String drivingCarType;//准驾车型	;
	  private String identifyType;//证件类型	;
	  private String identifyNumber;//证件号码	;
	  private String drivingLicenseNo;//驾驶证号码	;
	  private String linkPhoneNumber;//电话	;
	  
	  
	  
	  
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
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
	 * @return the driverId
	 */
	public String getDriverId() {
		return driverId;
	}
	/**
	 * @param driverId the driverId to set
	 */
	public void setDriverId(String driverId) {
		this.driverId = driverId;
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
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}
	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	/**
	 * @return the drivingCarType
	 */
	public String getDrivingCarType() {
		return drivingCarType;
	}
	/**
	 * @param drivingCarType the drivingCarType to set
	 */
	public void setDrivingCarType(String drivingCarType) {
		this.drivingCarType = drivingCarType;
	}
	/**
	 * @return the identifyType
	 */
	public String getIdentifyType() {
		return identifyType;
	}
	/**
	 * @param identifyType the identifyType to set
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	/**
	 * @return the identifyNumber
	 */
	public String getIdentifyNumber() {
		return identifyNumber;
	}
	/**
	 * @param identifyNumber the identifyNumber to set
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}
	/**
	 * @return the drivingLicenseNo
	 */
	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}
	/**
	 * @param drivingLicenseNo the drivingLicenseNo to set
	 */
	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}
	/**
	 * @return the linkPhoneNumber
	 */
	public String getLinkPhoneNumber() {
		return linkPhoneNumber;
	}
	/**
	 * @param linkPhoneNumber the linkPhoneNumber to set
	 */
	public void setLinkPhoneNumber(String linkPhoneNumber) {
		this.linkPhoneNumber = linkPhoneNumber;
	}

	public void init(){
		driverId=driverId==null?"":driverId;                        
		checkCarId=checkCarId==null?"":checkCarId;                  
		driverName=driverName==null?"":driverName;                  
		drivingCarType=drivingCarType==null?"":drivingCarType;      
		identifyType=identifyType==null?"":identifyType;            
		identifyNumber=identifyNumber==null?"":identifyNumber;      
		drivingLicenseNo=drivingLicenseNo==null?"":drivingLicenseNo;
		linkPhoneNumber=linkPhoneNumber==null?"":linkPhoneNumber;   

	}
	  
}

