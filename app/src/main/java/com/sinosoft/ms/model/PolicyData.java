package com.sinosoft.ms.model;

import java.util.List;


/**
 * PolicyData entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PolicyData implements java.io.Serializable {

	private long id;
	private String policyDataId;//用于标示数据的唯一
	private String policyNo;
	private String registNo;
	private String insuredName;
	private String comCName;
	private String startDate;
	private String endDate;
	private String riskCode;

	private PolicyDetailData policyDetailData;
	private List<PolicyDriverData> policyDriverDataList;
	private List<PolicyEngageData> policyEngageDataList;
	private List<PolicyKindData> policyKindDataList;
	// Constructors

	/** default constructor */
	public PolicyData() {
	}

	/** minimal constructor */
	public PolicyData(long id, String policyNo, String insuredName,
			String comCName, String startDate, String endDate, String riskCode) {
		this.id = id;
		this.policyNo = policyNo;
		this.insuredName = insuredName;
		this.comCName = comCName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.riskCode = riskCode;
	}

	/** full constructor */
	public PolicyData(long id, String policyNo, String registNo,
			String insuredName, String comCName, String startDate,
			String endDate, String riskCode) {
		this.id = id;
		this.policyNo = policyNo;
		this.registNo = registNo;
		this.insuredName = insuredName;
		this.comCName = comCName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.riskCode = riskCode;
	}

	// Property accessors
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
	/**
	 * @return the policyDataId
	 */
	public String getPolicyDataId() {
		return policyDataId;
	}

	/**
	 * @param policyDataId the policyDataId to set
	 */
	public void setPolicyDataId(String policyDataId) {
		this.policyDataId = policyDataId;
	}

	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String string) {
		this.policyNo = string;
	}

	
	public String getRegistNo() {
		return this.registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	
	public String getInsuredName() {
		return this.insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	
	public String getComCName() {
		return this.comCName;
	}

	public void setComCName(String comCName) {
		this.comCName = comCName;
	}

	
	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	
	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	public String getRiskCode() {
		return this.riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	
	public List<PolicyDriverData> getPolicyDriverDataList() {
		return policyDriverDataList;
	}

	public void setPolicyDriverDataList(List<PolicyDriverData> policyDriverDataList) {
		this.policyDriverDataList = policyDriverDataList;
	}
	
	
	public List<PolicyEngageData> getPolicyEngageDataList() {
		return policyEngageDataList;
	}

	public void setPolicyEngageDataList(List<PolicyEngageData> policyEngageDataList) {
		this.policyEngageDataList = policyEngageDataList;
	}

	
	public List<PolicyKindData> getPolicyKindDataList() {
		return policyKindDataList;
	}

	public void setPolicyKindDataList(List<PolicyKindData> policyKindDataList) {
		this.policyKindDataList = policyKindDataList;
	}

	
	public PolicyDetailData getPolicyDetailData() {
		return policyDetailData;
	}

	public void setPolicyDetailData(PolicyDetailData policyDetailData) {
		this.policyDetailData = policyDetailData;
	}
	

}