package com.sinosoft.ms.model;


/**
 * 保单约定驾驶员信息
 */

public class PolicyDriverData implements java.io.Serializable {

	// Fields
	private long id;
	private String pddId;//标示数据的唯一
	private String policyId;
    private String policyNo;
	private String driverName;
	private String sex;
	private String identifyNumber;
	private String drivingLicenseNo;
	private String acceptLicenseDate;
    
	private PolicyData policyData;
	
	// Constructors

	/** default constructor */
	public PolicyDriverData() {
	}

	/** minimal constructor */
	public PolicyDriverData(long id, String driverName) {
		this.id = id;
		this.driverName = driverName;
	}

	/** full constructor */
	public PolicyDriverData(long id, String driverName, String sex,
			String identifyNumber, String drivingLicenseNo,
			String acceptLicenseDate) {
		this.id = id;
		this.driverName = driverName;
		this.sex = sex;
		this.identifyNumber = identifyNumber;
		this.drivingLicenseNo = drivingLicenseNo;
		this.acceptLicenseDate = acceptLicenseDate;
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

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	
	public String getDrivingLicenseNo() {
		return this.drivingLicenseNo;
	}

	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

	
	public String getAcceptLicenseDate() {
		return this.acceptLicenseDate;
	}

	public void setAcceptLicenseDate(String acceptLicenseDate) {
		this.acceptLicenseDate = acceptLicenseDate;
	}
	
	
	public PolicyData getPolicyData() {
		return policyData;
	}

	public void setPolicyData(PolicyData policyData) {
		this.policyData = policyData;
	}

}