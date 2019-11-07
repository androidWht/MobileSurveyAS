package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：数据查询数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午3:14:45
 */
public class DataSreachBean implements Serializable {
	private static final long serialVersionUID = -8542649481213051060L;
	// 基本信息
	private String reportNo;// 报案号
	private String taskType;// 任务类型
	private String reportPeople;// 报案人／联系人
	private String phone;// 联系电话
	private String insuranceAddr;// 出险地点
	private String reportTime;// 报案时间

	// 详细
	private String insuranceReason;// 出险原因
	private String insuranceTime;// 出险时间

	public DataSreachBean() {
		super();
	}

	public DataSreachBean(String reportNo, String taskType,
			String reportPeople, String phone, String insuranceAddr,
			String reportTime, String insuranceReason, String insuranceTime) {
		super();
		this.reportNo = reportNo;
		this.taskType = taskType;
		this.reportPeople = reportPeople;
		this.phone = phone;
		this.insuranceAddr = insuranceAddr;
		this.reportTime = reportTime;
		this.insuranceReason = insuranceReason;
		this.insuranceTime = insuranceTime;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
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

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
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

}
