package com.sinosoft.ms.model;


/**
 * PolicyDetailData entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PolicyDetailData implements java.io.Serializable {
	
	private String policyId;
	private String policyNo;
	private String riskCode;
	private String insuredName;
	private String licenseNo;
	private String licenseColor;
	private String brandName;
	private String purchasePrice;
	private String startDate;
	private String endDate;
	private String clauseType;
	private String carKind;
	private String carOwner;
	private String engineNo;
	private String frameNo;
	private String vinNo;
	private String runArea;
	private String enrollDate;
	private String seatCount;
	private String useNature;
	private String colorCode;
	private String endorseTimes;
	private String comCName;
	private String m2Flag;
	private String claimTimes;
	private String identifyNumber;
	private String vipType;

	private PolicyData policyData;
	// Constructors

	/** default constructor */
	public PolicyDetailData() {
	}

	/** minimal constructor */
	public PolicyDetailData(String policyDataId, String riskCode) {
		this.policyNo = policyDataId;
		this.riskCode = riskCode;
	}

	/** full constructor */
	public PolicyDetailData(String policyDataId, String riskCode,
			String insuredName, String licenseNo, String licenseColor,
			String brandName, String purchasePrice, String startDate,
			String endDate, String clauseType, String carKind, String carOwner,
			String engineNo, String frameNo, String vinNo, String runArea,
			String enrollDate, String seatCount, String useNature,
			String colorCode, String endorseTimes, String comCName,
			String m2Flag, String claimTimes, String identifyNumber,
			String vipType) {
		this.policyNo = policyDataId;
		this.riskCode = riskCode;
		this.insuredName = insuredName;
		this.licenseNo = licenseNo;
		this.licenseColor = licenseColor;
		this.brandName = brandName;
		this.purchasePrice = purchasePrice;
		this.startDate = startDate;
		this.endDate = endDate;
		this.clauseType = clauseType;
		this.carKind = carKind;
		this.carOwner = carOwner;
		this.engineNo = engineNo;
		this.frameNo = frameNo;
		this.vinNo = vinNo;
		this.runArea = runArea;
		this.enrollDate = enrollDate;
		this.seatCount = seatCount;
		this.useNature = useNature;
		this.colorCode = colorCode;
		this.endorseTimes = endorseTimes;
		this.comCName = comCName;
		this.m2Flag = m2Flag;
		this.claimTimes = claimTimes;
		this.identifyNumber = identifyNumber;
		this.vipType = vipType;
	}

	
	
   

	/**
	 * @return the policyId
	 */
	public String getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	/**
	 * @return the policyNo
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getRiskCode() {
		return this.riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	
	public String getInsuredName() {
		return this.insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	
	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	
	public String getLicenseColor() {
		return this.licenseColor;
	}

	public void setLicenseColor(String licenseColor) {
		this.licenseColor = licenseColor;
	}

	
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	
	public String getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
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

	
	public String getClauseType() {
		return this.clauseType;
	}

	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}

	
	public String getCarKind() {
		return this.carKind;
	}

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	
	public String getCarOwner() {
		return this.carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	
	public String getEngineNo() {
		return this.engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	
	public String getFrameNo() {
		return this.frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	
	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	
	public String getRunArea() {
		return this.runArea;
	}

	public void setRunArea(String runArea) {
		this.runArea = runArea;
	}

	
	public String getEnrollDate() {
		return this.enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	
	public String getSeatCount() {
		return this.seatCount;
	}

	public void setSeatCount(String seatCount) {
		this.seatCount = seatCount;
	}

	
	public String getUseNature() {
		return this.useNature;
	}

	public void setUseNature(String useNature) {
		this.useNature = useNature;
	}

	
	public String getColorCode() {
		return this.colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	
	public String getEndorseTimes() {
		return this.endorseTimes;
	}

	public void setEndorseTimes(String endorseTimes) {
		this.endorseTimes = endorseTimes;
	}

	
	public String getComCName() {
		return this.comCName;
	}

	public void setComCName(String comCName) {
		this.comCName = comCName;
	}

	
	public String getM2Flag() {
		return this.m2Flag;
	}

	public void setM2Flag(String m2Flag) {
		this.m2Flag = m2Flag;
	}

	
	public String getClaimTimes() {
		return this.claimTimes;
	}

	public void setClaimTimes(String claimTimes) {
		this.claimTimes = claimTimes;
	}

	
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	
	public String getVipType() {
		return this.vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

}