package com.sinosoft.ms.model.sub;

import java.io.Serializable;
import java.util.Map;

/**
 * 系统名：移动查勘定损 子系统名：影像资料信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 14:35:22
 */

public class ImageData implements Serializable {
	private static final long serialVersionUID = -751353248228342873L;
	private String reportNo;// 报案号
	private String taskType;// 任务类型
	private String reportPeople;// 报案人／联系人
	private String contactPhone;// 联系电话
	private String insuranceAddr;// 出险地点
	private String insuranceReason;// 出险原因

	private Map<String, String[]> filesMap;

	public ImageData() {
		super();
	}

	public ImageData(String reportNo, String taskType, String reportPeople,
			String contactPhone, String insuranceAddr, String insuranceReason,
			Map<String, String[]> filesMap) {
		super();
		this.reportNo = reportNo;
		this.taskType = taskType;
		this.reportPeople = reportPeople;
		this.contactPhone = contactPhone;
		this.insuranceAddr = insuranceAddr;
		this.insuranceReason = insuranceReason;
		this.filesMap = filesMap;
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

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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

	public Map<String, String[]> getFilesMap() {
		return filesMap;
	}

	public void setFilesMap(Map<String, String[]> filesMap) {
		this.filesMap = filesMap;
	}

}
