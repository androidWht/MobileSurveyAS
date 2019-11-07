package com.sinosoft.ms.model;


/**
 * 保单特别约定信息
 */

public class PolicyEngageData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String policyNo;
	private String clauseCode;
	private String clauses;

	private PolicyData policyData;
	
	// Constructors

	/** default constructor */
	public PolicyEngageData() {
	}

	/** full constructor */
	public PolicyEngageData(long id, String clauseCode, String clauses) {
		this.id = id;
		this.clauseCode = clauseCode;
		this.clauses = clauses;
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

	public String getClauseCode() {
		return this.clauseCode;
	}

	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}

	
	public String getClauses() {
		return this.clauses;
	}

	public void setClauses(String clauses) {
		this.clauses = clauses;
	}
	
	
	public PolicyData getPolicyData() {
		return policyData;
	}

	public void setPolicyData(PolicyData policyData) {
		this.policyData = policyData;
	}

}