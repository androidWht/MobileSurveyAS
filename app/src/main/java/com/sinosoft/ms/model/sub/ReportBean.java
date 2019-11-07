package com.sinosoft.ms.model.sub;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：报案信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 11:59:22
 */

public class ReportBean implements Serializable {
	private static final long serialVersionUID = -5966148745665821786L;
	private String reportNo;// 报案号
	private String reportPeople;// 报案人
	private String reportPhone;// 报案人电话

	private String contactPeople;// 联系人
	private String contactPhone;// 联系电话
	private String reportTime;// 报案时间

	private String relationshipToTheInsured;// 与被保人关系
	private String insuranceTime;// 出险时间
	private String insuranceReason;// 出险原因

	private String caseType;// 案件类别
	private String emergencyType;// 紧急类型
	private String caseNature;// 案件性质

	private String insuranceAddr;// 出险地点
	private String insurancePass;// 出险经过

	public ReportBean() {
		super();
	}

	public ReportBean(String reportNo, String reportPeople, String reportPhone,
			String contactPeople, String contactPhone, String reportTime,
			String relationshipToTheInsured, String insuranceTime,
			String insuranceReason, String caseType, String emergencyType,
			String caseNature, String insuranceAddr, String insurancePass) {
		super();
		this.reportNo = reportNo;
		this.reportPeople = reportPeople;
		this.reportPhone = reportPhone;
		this.contactPeople = contactPeople;
		this.contactPhone = contactPhone;
		this.reportTime = reportTime;
		this.relationshipToTheInsured = relationshipToTheInsured;
		this.insuranceTime = insuranceTime;
		this.insuranceReason = insuranceReason;
		this.caseType = caseType;
		this.emergencyType = emergencyType;
		this.caseNature = caseNature;
		this.insuranceAddr = insuranceAddr;
		this.insurancePass = insurancePass;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getReportPeople() {
		return reportPeople;
	}

	public void setReportPeople(String reportPeople) {
		this.reportPeople = reportPeople;
	}

	public String getReportPhone() {
		return reportPhone;
	}

	public void setReportPhone(String reportPhone) {
		this.reportPhone = reportPhone;
	}

	public String getContactPeople() {
		return contactPeople;
	}

	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getRelationshipToTheInsured() {
		return relationshipToTheInsured;
	}

	public void setRelationshipToTheInsured(String relationshipToTheInsured) {
		this.relationshipToTheInsured = relationshipToTheInsured;
	}

	public String getInsuranceTime() {
		return insuranceTime;
	}

	public void setInsuranceTime(String insuranceTime) {
		this.insuranceTime = insuranceTime;
	}

	public String getInsuranceReason() {
		return insuranceReason;
	}

	public void setInsuranceReason(String insuranceReason) {
		this.insuranceReason = insuranceReason;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getEmergencyType() {
		return emergencyType;
	}

	public void setEmergencyType(String emergencyType) {
		this.emergencyType = emergencyType;
	}

	public String getCaseNature() {
		return caseNature;
	}

	public void setCaseNature(String caseNature) {
		this.caseNature = caseNature;
	}

	public String getInsuranceAddr() {
		return insuranceAddr;
	}

	public void setInsuranceAddr(String insuranceAddr) {
		this.insuranceAddr = insuranceAddr;
	}

	public String getInsurancePass() {
		return insurancePass;
	}

	public void setInsurancePass(String insurancePass) {
		this.insurancePass = insurancePass;
	}

}
