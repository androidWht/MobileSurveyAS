package com.sinosoft.ms.model.sub;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：数据查询数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Administrator
 * @createTime 下午3:26:49
 */

public class DataSreach implements Serializable {
	private static final long serialVersionUID = -4857572125408350289L;
	private String reportNo;// 报案号
	private String taskTypeSreach;// 任务类型
	private String contactPerson;// 联系人
	private String contactPhone;// 联系电话
	private String reportTimeFrom;// 报案时间
	private String insuranceAddr;// 出险地点
	private String insuranceTime;// 出险时间
	private String insuranceReason;// 出险原因

	public DataSreach() {
		super();
	}

	public DataSreach(String reportNo, String taskTypeSreach,
			String contactPerson, String contactPhone, String reportTimeFrom,
			String insuranceAddr, String insuranceTime, String insuranceReason) {
		super();
		this.reportNo = reportNo;
		this.taskTypeSreach = taskTypeSreach;
		this.contactPerson = contactPerson;
		this.contactPhone = contactPhone;
		this.reportTimeFrom = reportTimeFrom;
		this.insuranceAddr = insuranceAddr;
		this.insuranceTime = insuranceTime;
		this.insuranceReason = insuranceReason;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getTaskTypeSreach() {
		return taskTypeSreach;
	}

	public void setTaskTypeSreach(String taskTypeSreach) {
		this.taskTypeSreach = taskTypeSreach;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getReportTimeFrom() {
		return reportTimeFrom;
	}

	public void setReportTimeFrom(String reportTimeFrom) {
		this.reportTimeFrom = reportTimeFrom;
	}

	public String getInsuranceAddr() {
		return insuranceAddr;
	}

	public void setInsuranceAddr(String insuranceAddr) {
		this.insuranceAddr = insuranceAddr;
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

}
