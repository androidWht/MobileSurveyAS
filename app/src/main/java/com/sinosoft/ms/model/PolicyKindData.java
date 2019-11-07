package com.sinosoft.ms.model;




/**
 * 保单承保险别信息
 */

public class PolicyKindData implements java.io.Serializable {

	// Fields

	private long id;
	private String pkdId;//
	private String policyId;
	private String policyNo;
	private String kindName;
	private String amount;
	private String remark;

	private PolicyData policyData;
	// Constructors

	/** default constructor */
	public PolicyKindData() {
	}

	/** full constructor */
	public PolicyKindData(long id, String kindName, String amount,
			String remark) {
		this.id = id;
		this.kindName = kindName;
		this.amount = amount;
		this.remark = remark;
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

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getKindName() {
		return this.kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}


	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public PolicyData getPolicyData() {
		return policyData;
	}

	public void setPolicyData(PolicyData policyData) {
		this.policyData = policyData;
	}

}