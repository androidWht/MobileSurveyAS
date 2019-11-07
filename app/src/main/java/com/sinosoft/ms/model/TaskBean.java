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
public class TaskBean implements Serializable {
	// 基本信息
	private String reportNO;// 报案号
	private String taskType;// 任务类型
	private String reporter;// 报案人
	private String accidentLocation;// 出险地点
	private String status;// 状态
	private String operating;// 操作

	// 详细
	private String accidentReason;// 出险原因
	private String accidentDate;// 出险时间
	private String contactPhone;// 联系电话
	private String reportDate;// 报案时间

	/**
	 * @param reportNO
	 * @param taskType
	 * @param reporter
	 * @param accidentLocation
	 * @param status
	 * @param operating
	 * @param accidentReason
	 * @param accidentDate
	 * @param contactPhone
	 * @param reportDate
	 */
	public TaskBean() {

	}

	public TaskBean(String reportNO, String taskType, String reporter,
			String accidentLocation, String status, String operating,
			String accidentReason, String accidentDate, String contactPhone,
			String reportDate) {

		this.reportNO = reportNO;
		this.taskType = taskType;
		this.reporter = reporter;
		this.accidentLocation = accidentLocation;
		this.status = status;
		this.operating = operating;
		this.accidentReason = accidentReason;
		this.accidentDate = accidentDate;
		this.contactPhone = contactPhone;
		this.reportDate = reportDate;
	}

	/**
	 * @return the reportNO
	 */
	public String getReportNO() {
		return reportNO;
	}

	/**
	 * @param reportNO
	 *            the reportNO to set
	 */
	public void setReportNO(String reportNO) {
		this.reportNO = reportNO;
	}

	/**
	 * @return the taskType
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType
	 *            the taskType to set
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the reporter
	 */
	public String getReporter() {
		return reporter;
	}

	/**
	 * @param reporter
	 *            the reporter to set
	 */
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	/**
	 * @return the accidentLocation
	 */
	public String getAccidentLocation() {
		return accidentLocation;
	}

	/**
	 * @param accidentLocation
	 *            the accidentLocation to set
	 */
	public void setAccidentLocation(String accidentLocation) {
		this.accidentLocation = accidentLocation;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the operating
	 */
	public String getOperating() {
		return operating;
	}

	/**
	 * @param operating
	 *            the operating to set
	 */
	public void setOperating(String operating) {
		this.operating = operating;
	}

	/**
	 * @return the accidentReason
	 */
	public String getAccidentReason() {
		return accidentReason;
	}

	/**
	 * @param accidentReason
	 *            the accidentReason to set
	 */
	public void setAccidentReason(String accidentReason) {
		this.accidentReason = accidentReason;
	}

	/**
	 * @return the accidentDate
	 */
	public String getAccidentDate() {
		return accidentDate;
	}

	/**
	 * @param accidentDate
	 *            the accidentDate to set
	 */
	public void setAccidentDate(String accidentDate) {
		this.accidentDate = accidentDate;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the reportDate
	 */
	public String getReportDate() {
		return reportDate;
	}

	/**
	 * @param reportDate
	 *            the reportDate to set
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

}
