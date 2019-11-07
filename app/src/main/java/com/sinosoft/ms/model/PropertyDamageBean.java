package com.sinosoft.ms.model;

import java.io.Serializable;
import java.util.List;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午11:44:34
 */

public class PropertyDamageBean implements Serializable {

	private String reportNo;
	private String losingParty;
	private String contactPersonDamage;// 联系人
	private String contactMode;// 联系方式
	private String estimatedLossAmountDamage;// 估损金额
	private String claimCaseCategory;
	private String surveyAddr;// 查勘地点
	private String rescueProcess;// 施救过程
	private String remarksExt;// 备注
	private String surveyDate;// 查勘日期

	private List<DamageBean> damageList;

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getLosingParty() {
		return losingParty;
	}

	public void setLosingParty(String losingParty) {
		this.losingParty = losingParty;
	}

	public String getContactPersonDamage() {
		return contactPersonDamage;
	}

	public void setContactPersonDamage(String contactPersonDamage) {
		this.contactPersonDamage = contactPersonDamage;
	}

	public String getContactMode() {
		return contactMode;
	}

	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}

	public String getEstimatedLossAmountDamage() {
		return estimatedLossAmountDamage;
	}

	public void setEstimatedLossAmountDamage(String estimatedLossAmountDamage) {
		this.estimatedLossAmountDamage = estimatedLossAmountDamage;
	}

	public String getClaimCaseCategory() {
		return claimCaseCategory;
	}

	public void setClaimCaseCategory(String claimCaseCategory) {
		this.claimCaseCategory = claimCaseCategory;
	}

	public String getSurveyAddr() {
		return surveyAddr;
	}

	public void setSurveyAddr(String surveyAddr) {
		this.surveyAddr = surveyAddr;
	}

	public String getRescueProcess() {
		return rescueProcess;
	}

	public void setRescueProcess(String rescueProcess) {
		this.rescueProcess = rescueProcess;
	}

	public String getRemarksExt() {
		return remarksExt;
	}

	public void setRemarksExt(String remarksExt) {
		this.remarksExt = remarksExt;
	}

	public List<DamageBean> getDamageList() {
		return damageList;
	}

	public void setDamageList(List<DamageBean> damageList) {
		this.damageList = damageList;
	}

	/**
	 * @return the surveyDate
	 */
	public String getSurveyDate() {
		return surveyDate;
	}

	/**
	 * @param surveyDate
	 *            the surveyDate to set
	 */
	public void setSurveyDate(String surveyDate) {
		this.surveyDate = surveyDate;
	}

}
