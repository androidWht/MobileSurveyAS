/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:14:45
 */
package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * @author sinosoft
 * 
 */
public class CaseListBean implements Serializable {
	private static final long serialVersionUID = -9019312304046768259L;
	// 基本信息
	private String reportNo;// 报案号
	private String taskType;// 任务类型
	private String reportPeople;// 报案人
	private String insuranceAddr;// 出险地点
	private String status;// 状态
	private String option;// 操作

	// 详细
	private String insuranceReason;// 出险原因
	private String insuranceTime;// 出险时间
	private String phone;// 联系电话
	private String reportTime;// 报案时间

	public CaseListBean(String reportNo, String taskType, String reportPeople,
			String insuranceAddr, String status, String option,
			String insuranceReason, String insuranceTime, String phone,
			String reportTime) {
		super();
		this.reportNo = reportNo;
		this.taskType = taskType;
		this.reportPeople = reportPeople;
		this.insuranceAddr = insuranceAddr;
		this.status = status;
		this.option = option;
		this.insuranceReason = insuranceReason;
		this.insuranceTime = insuranceTime;
		this.phone = phone;
		this.reportTime = reportTime;
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

	public String getInsuranceAddr() {
		return insuranceAddr;
	}

	public void setInsuranceAddr(String insuranceAddr) {
		this.insuranceAddr = insuranceAddr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

}
