package com.sinosoft.ms.model.sub;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：任务基本信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 11:12:52
 */

public class BasicTask implements Serializable {
	private static final long serialVersionUID = -5966148745665821786L;
	private String reportNo;// 报案号
	private String reportPeople;// 报案人／联系人
	private String phone;// 联系电话
	private String insuranceAddr;// 出险地点
	private String insuranceReason;// 出险原因
	private String insuranceTime;// 出险时间
	private String reportTime;// 报案时间

	public BasicTask() {
		super();
	}

	public BasicTask(String reportNo, String reportPeople, String phone,
			String insuranceAddr, String insuranceReason, String insuranceTime,
			String reportTime) {
		super();
		this.reportNo = reportNo;
		this.reportPeople = reportPeople;
		this.phone = phone;
		this.insuranceAddr = insuranceAddr;
		this.insuranceReason = insuranceReason;
		this.insuranceTime = insuranceTime;
		this.reportTime = reportTime;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInsuranceAddr() {
		return insuranceAddr;
	}

	public void setInsuranceAddr(String insuranceAddr) {
		this.insuranceAddr = insuranceAddr;
	}

	public String getInsuranceReason() {
		return insuranceReason;
	}

	public void setInsuranceReason(String insuranceReason) {
		this.insuranceReason = insuranceReason;
	}

	public String getInsuranceTime() {
		return insuranceTime;
	}

	public void setInsuranceTime(String insuranceTime) {
		this.insuranceTime = insuranceTime;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

}
