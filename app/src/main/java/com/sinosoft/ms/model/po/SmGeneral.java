package com.sinosoft.ms.model.po;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author pei
 * @createTime 下午2:53:03
 */

public class SmGeneral {

	// 报案号
	private String registNo ;
	// 事故类型
	private String accidentType;
	// 是否代位求偿
	private String bSubrogationFlag;
	// 是否重大赔案
	private String reserveFlag;
	// 是否互碰自赔
	private String isMutableTouch;
	
	public void init(){
		if(registNo == null){
			registNo = "" ;
		}
		if(accidentType == null){
			accidentType = "" ;
		}
		if(bSubrogationFlag == null){
			bSubrogationFlag = "" ;
		}
		if(reserveFlag == null){
			reserveFlag = "" ;
		}
		if(isMutableTouch == null){
			isMutableTouch = "" ;
		}
	}

	/**
	 * @return the accidentType
	 */
	public String getAccidentType() {
		return accidentType;
	}

	/**
	 * @param accidentType
	 *            the accidentType to set
	 */
	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}

	/**
	 * @return the bSubrogationFlag
	 */
	public String getbSubrogationFlag() {
		return bSubrogationFlag;
	}

	/**
	 * @param bSubrogationFlag
	 *            the bSubrogationFlag to set
	 */
	public void setbSubrogationFlag(String bSubrogationFlag) {
		this.bSubrogationFlag = bSubrogationFlag;
	}

	/**
	 * @return the reserveFlag
	 */
	public String getReserveFlag() {
		return reserveFlag;
	}

	/**
	 * @param reserveFlag
	 *            the reserveFlag to set
	 */
	public void setReserveFlag(String reserveFlag) {
		this.reserveFlag = reserveFlag;
	}

	/**
	 * @return the isMutableTouch
	 */
	public String getIsMutableTouch() {
		return isMutableTouch;
	}

	/**
	 * @param isMutableTouch
	 *            the isMutableTouch to set
	 */
	public void setIsMutableTouch(String isMutableTouch) {
		this.isMutableTouch = isMutableTouch;
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
}
