package com.sinosoft.ms.model.sub;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：数据查询参数数据结构 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 15:24:30
 */

public class ParamBean implements Serializable {
	private static final long serialVersionUID = 1526192680710208037L;
	private String registId = null;
	private String reportNo="";// 报案号
	private String taskId = "";
	private String taskTypeSreach="";// 任务类型
	private String contactPerson="";// 联系人
	private String contactPhone="";// 联系电话
	private String reportTimeFrom="";// 报案时间起
	private String reportTimeTo="";// 报案时间终
	private String insuranceTimeFrom="";// 出险时间起
	private String insuranceTimeTo="";// 出险时间终

	private int currPage;// 当前页
	private int pageSize;// 分页记录数

	public ParamBean() {
		super();
	}

	public ParamBean(String reportNo, String taskTypeSreach,
			String contactPerson, String contactPhone, String reportTimeFrom,
			String reportTimeTo, String insuranceTimeFrom,
			String insuranceTimeTo, int currPage, int pageSize) {
		super();
		this.reportNo = reportNo;
		this.taskTypeSreach = taskTypeSreach;
		this.contactPerson = contactPerson;
		this.contactPhone = contactPhone;
		this.reportTimeFrom = reportTimeFrom;
		this.reportTimeTo = reportTimeTo;
		this.insuranceTimeFrom = insuranceTimeFrom;
		this.insuranceTimeTo = insuranceTimeTo;
		this.currPage = currPage;
		this.pageSize = pageSize;
	}
	
	/**
	 * @return the registId
	 */
	public String getRegistId() {
		return registId;
	}

	/**
	 * @param registId the registId to set
	 */
	public void setRegistId(String registId) {
		this.registId = registId;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getReportTimeTo() {
		return reportTimeTo;
	}

	public void setReportTimeTo(String reportTimeTo) {
		this.reportTimeTo = reportTimeTo;
	}

	public String getInsuranceTimeFrom() {
		return insuranceTimeFrom;
	}

	public void setInsuranceTimeFrom(String insuranceTimeFrom) {
		this.insuranceTimeFrom = insuranceTimeFrom;
	}

	public String getInsuranceTimeTo() {
		return insuranceTimeTo;
	}

	public void setInsuranceTimeTo(String insuranceTimeTo) {
		this.insuranceTimeTo = insuranceTimeTo;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
