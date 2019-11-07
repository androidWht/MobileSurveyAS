package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：涉案处理数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午3:14:45
 */
public class InvolvedBean implements Serializable {
	private static final long serialVersionUID = -8542649481213051060L;
	// 基本信息
	private String reportNo;// 报案号
	private String lossType;// 损失类别
	private String licenseNo;// 车牌号
	private String theOwner;// 车主
	private String numberPlateType;// 号牌种类
	private String strongType;// 交强险责任类型
	private String option;// 操作
	// 详细
	private String engineNumber;// 发动机号
	private String frameNumber;// 车架号／VIN

	private LossDetailBean lossDetailBean;// 损失明细
	private TheDriverBean driverBean;// 驾驶员信息

	public InvolvedBean() {

	}

	public InvolvedBean(String lossType, String licenseNo, String theOwner,
			String numberPlateType, String strongType, String option,
			String engineNumber, String frameNumber) {
		super();
		this.lossType = lossType;
		this.licenseNo = licenseNo;
		this.theOwner = theOwner;
		this.numberPlateType = numberPlateType;
		this.strongType = strongType;
		this.option = option;
		this.engineNumber = engineNumber;
		this.frameNumber = frameNumber;
	}

	/**
	 * @return the reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}

	/**
	 * @param reportNo
	 *            the reportNo to set
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getLossType() {
		return lossType;
	}

	public void setLossType(String lossType) {
		this.lossType = lossType;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getTheOwner() {
		return theOwner;
	}

	public void setTheOwner(String theOwner) {
		this.theOwner = theOwner;
	}

	public String getNumberPlateType() {
		return numberPlateType;
	}

	public void setNumberPlateType(String numberPlateType) {
		this.numberPlateType = numberPlateType;
	}

	public String getStrongType() {
		return strongType;
	}

	public void setStrongType(String strongType) {
		this.strongType = strongType;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	/**
	 * @return the lossDetailBean
	 */
	public LossDetailBean getLossDetailBean() {
		return lossDetailBean;
	}

	/**
	 * @param lossDetailBean
	 *            the lossDetailBean to set
	 */
	public void setLossDetailBean(LossDetailBean lossDetailBean) {
		this.lossDetailBean = lossDetailBean;
	}

	/**
	 * @return the driverBean
	 */
	public TheDriverBean getDriverBean() {
		return driverBean;
	}

	/**
	 * @param driverBean
	 *            the driverBean to set
	 */
	public void setDriverBean(TheDriverBean driverBean) {
		this.driverBean = driverBean;
	}

}
