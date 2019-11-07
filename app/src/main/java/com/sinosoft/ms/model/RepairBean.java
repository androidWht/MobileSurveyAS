package com.sinosoft.ms.model;

/**
 * 系统名：移动查勘定损 子系统名：简易定损（定损修理） 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 上午11:58:06
 */

public class RepairBean {

	private String repairName;// 工种名称
	private String repairItemName;// 维修名称
	private String repairItemCode;// 维修项代码
	private String manHour;// 维修工时
	private String repairFee;// 维修金额
	private String insureTerm;// 险别
	private String remark;// 备注

	/**
	 * @return the repairName
	 */
	public String getRepairName() {
		return repairName;
	}

	/**
	 * @param repairName
	 *            the repairName to set
	 */
	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}

	/**
	 * @return the repairItemName
	 */
	public String getRepairItemName() {
		return repairItemName;
	}

	/**
	 * @param repairItemName
	 *            the repairItemName to set
	 */
	public void setRepairItemName(String repairItemName) {
		this.repairItemName = repairItemName;
	}

	/**
	 * @return the repairItemCode
	 */
	public String getRepairItemCode() {
		return repairItemCode;
	}

	/**
	 * @param repairItemCode
	 *            the repairItemCode to set
	 */
	public void setRepairItemCode(String repairItemCode) {
		this.repairItemCode = repairItemCode;
	}

	/**
	 * @return the manHour
	 */
	public String getManHour() {
		return manHour;
	}

	/**
	 * @param manHour
	 *            the manHour to set
	 */
	public void setManHour(String manHour) {
		this.manHour = manHour;
	}

	/**
	 * @return the repairFee
	 */
	public String getRepairFee() {
		return repairFee;
	}

	/**
	 * @param repairFee
	 *            the repairFee to set
	 */
	public void setRepairFee(String repairFee) {
		this.repairFee = repairFee;
	}

	/**
	 * @return the insureTerm
	 */
	public String getInsureTerm() {
		return insureTerm;
	}

	/**
	 * @param insureTerm
	 *            the insureTerm to set
	 */
	public void setInsureTerm(String insureTerm) {
		this.insureTerm = insureTerm;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
