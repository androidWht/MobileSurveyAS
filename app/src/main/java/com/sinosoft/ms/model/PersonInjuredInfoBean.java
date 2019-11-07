package com.sinosoft.ms.model;

import java.io.Serializable;
import java.util.List;

/**
 * 系统名：移动查勘定损 子系统名：人伤信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午3:14:45
 */
public class PersonInjuredInfoBean implements Serializable {

	private String reportNo;        // 报案号
	private String lossOwnership;   // 损失归属方
	private int injuredNumber;      // 重伤人数
	private int minorNumber;        // 轻伤人数
	private int deathNumber;        // 死亡人数
	private double estimatedLossAmountInjured;// 估损金额
	private double salvageChargesInjured;// 施救费用
	private String surveyAddr;      // 查勘地点
	private String rescueProcess;   // 施救过程
	private String insuranceDate;   // 查勘日期
	private List<PersonInjured> injuredList;// 人伤信息清单

	/**
	 * 
	 */
	public PersonInjuredInfoBean() {

	}

	/**
	 * @param reportNo
	 * @param lossOwnership
	 * @param injuredNumber
	 * @param minorNumber
	 * @param deathNumber
	 * @param estimatedLossAmountInjured
	 * @param salvageChargesInjured
	 * @param surveyAddr
	 * @param rescueProcess
	 * @param insuranceDate
	 * @param injuredList
	 */
	public PersonInjuredInfoBean(String reportNo, String lossOwnership,
			int injuredNumber, int minorNumber, int deathNumber,
			double estimatedLossAmountInjured, double salvageChargesInjured,
			String surveyAddr, String rescueProcess, String insuranceDate,
			List<PersonInjured> injuredList) {
		super();
		this.reportNo = reportNo;
		this.lossOwnership = lossOwnership;
		this.injuredNumber = injuredNumber;
		this.minorNumber = minorNumber;
		this.deathNumber = deathNumber;
		this.estimatedLossAmountInjured = estimatedLossAmountInjured;
		this.salvageChargesInjured = salvageChargesInjured;
		this.surveyAddr = surveyAddr;
		this.rescueProcess = rescueProcess;
		this.insuranceDate = insuranceDate;
		this.injuredList = injuredList;
	}

	/**
	 * @return the insuranceDate
	 */
	public String getInsuranceDate() {
		return insuranceDate;
	}

	/**
	 * @param insuranceDate
	 *            the insuranceDate to set
	 */
	public void setInsuranceDate(String insuranceDate) {
		this.insuranceDate = insuranceDate;
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

	/**
	 * @return the lossOwnership
	 */
	public String getLossOwnership() {
		return lossOwnership;
	}

	/**
	 * @param lossOwnership
	 *            the lossOwnership to set
	 */
	public void setLossOwnership(String lossOwnership) {
		this.lossOwnership = lossOwnership;
	}

	/**
	 * @return the injuredNumber
	 */
	public int getInjuredNumber() {
		return injuredNumber;
	}

	/**
	 * @param injuredNumber
	 *            the injuredNumber to set
	 */
	public void setInjuredNumber(int injuredNumber) {
		this.injuredNumber = injuredNumber;
	}

	/**
	 * @return the minorNumber
	 */
	public int getMinorNumber() {
		return minorNumber;
	}

	/**
	 * @param minorNumber
	 *            the minorNumber to set
	 */
	public void setMinorNumber(int minorNumber) {
		this.minorNumber = minorNumber;
	}

	/**
	 * @return the deathNumber
	 */
	public int getDeathNumber() {
		return deathNumber;
	}

	/**
	 * @param deathNumber
	 *            the deathNumber to set
	 */
	public void setDeathNumber(int deathNumber) {
		this.deathNumber = deathNumber;
	}

	/**
	 * @return the estimatedLossAmountInjured
	 */
	public double getEstimatedLossAmountInjured() {
		return estimatedLossAmountInjured;
	}

	/**
	 * @param estimatedLossAmountInjured
	 *            the estimatedLossAmountInjured to set
	 */
	public void setEstimatedLossAmountInjured(double estimatedLossAmountInjured) {
		this.estimatedLossAmountInjured = estimatedLossAmountInjured;
	}

	/**
	 * @return the salvageChargesInjured
	 */
	public double getSalvageChargesInjured() {
		return salvageChargesInjured;
	}

	/**
	 * @param salvageChargesInjured
	 *            the salvageChargesInjured to set
	 */
	public void setSalvageChargesInjured(double salvageChargesInjured) {
		this.salvageChargesInjured = salvageChargesInjured;
	}

	/**
	 * @return the surveyAddr
	 */
	public String getSurveyAddr() {
		return surveyAddr;
	}

	/**
	 * @param surveyAddr
	 *            the surveyAddr to set
	 */
	public void setSurveyAddr(String surveyAddr) {
		this.surveyAddr = surveyAddr;
	}

	/**
	 * @return the rescueProcess
	 */
	public String getRescueProcess() {
		return rescueProcess;
	}

	/**
	 * @param rescueProcess
	 *            the rescueProcess to set
	 */
	public void setRescueProcess(String rescueProcess) {
		this.rescueProcess = rescueProcess;
	}

	/**
	 * @return the injuredList
	 */
	public List<PersonInjured> getInjuredList() {
		return injuredList;
	}

	/**
	 * @param injuredList
	 *            the injuredList to set
	 */
	public void setInjuredList(List<PersonInjured> injuredList) {
		this.injuredList = injuredList;
	}

}
