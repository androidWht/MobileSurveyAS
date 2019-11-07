package com.sinosoft.ms.model;

import com.sinosoft.ms.utils.StringUtils;


/**
 * 定损车辆承保信息数据结构类
 */
public class LossVehicleInsurance implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer lossVehicleId;
	private String policyFlag;//保单交强/商业标志 1.商业险 2.交强险
	private String oppRegistNoBi;
	private String oppPolicyNoBi;
	private String oppInsurerCodeBi;
	private String oppInsurerAreaBi;
	private String oppRegistNoCi;
	private String oppPolicyNoCi;
	private String oppInsurerCodeCi;
	private String oppInsurerAreaCi;
	/** add by zhengongsheng 2013-5-11 11:30 **/
	private String oppIdBi;
	private String oppIdCi;
	

	/** default constructor */
	public LossVehicleInsurance() {
	}

	/** full constructor */
	public LossVehicleInsurance(String policyFlag,
			String oppRegistNoBi, String oppPolicyNoBi,
			String oppInsurerCodeBi, String oppInsurerAreaBi,
			String oppRegistNoCi, String oppPolicyNoCi,
			String oppInsurerCodeCi, String oppInsurerAreaCi) {
		
		this.policyFlag = policyFlag;
		this.oppRegistNoBi = oppRegistNoBi;
		this.oppPolicyNoBi = oppPolicyNoBi;
		this.oppInsurerCodeBi = oppInsurerCodeBi;
		this.oppInsurerAreaBi = oppInsurerAreaBi;
		this.oppRegistNoCi = oppRegistNoCi;
		this.oppPolicyNoCi = oppPolicyNoCi;
		this.oppInsurerCodeCi = oppInsurerCodeCi;
		this.oppInsurerAreaCi = oppInsurerAreaCi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLossVehicleId() {
		return lossVehicleId;
	}

	public void setLossVehicleId(Integer lossVehicleId) {
		this.lossVehicleId = lossVehicleId;
	}

	public String getPolicyFlag() {
		return this.policyFlag;
	}

	public void setPolicyFlag(String policyFlag) {
		this.policyFlag = policyFlag;
	}

	public String getOppRegistNoBi() {
		return this.oppRegistNoBi;
	}

	public void setOppRegistNoBi(String oppRegistNoBi) {
		this.oppRegistNoBi = oppRegistNoBi;
	}

	public String getOppPolicyNoBi() {
		return this.oppPolicyNoBi;
	}

	public void setOppPolicyNoBi(String oppPolicyNoBi) {
		this.oppPolicyNoBi = oppPolicyNoBi;
	}

	public String getOppInsurerCodeBi() {
		return this.oppInsurerCodeBi;
	}

	public void setOppInsurerCodeBi(String oppInsurerCodeBi) {
		this.oppInsurerCodeBi = oppInsurerCodeBi;
	}

	public String getOppInsurerAreaBi() {
		return this.oppInsurerAreaBi;
	}

	public void setOppInsurerAreaBi(String oppInsurerAreaBi) {
		this.oppInsurerAreaBi = oppInsurerAreaBi;
	}

	public String getOppRegistNoCi() {
		return this.oppRegistNoCi;
	}

	public void setOppRegistNoCi(String oppRegistNoCi) {
		this.oppRegistNoCi = oppRegistNoCi;
	}

	public String getOppPolicyNoCi() {
		return this.oppPolicyNoCi;
	}

	public void setOppPolicyNoCi(String oppPolicyNoCi) {
		this.oppPolicyNoCi = oppPolicyNoCi;
	}

	public String getOppInsurerCodeCi() {
		return this.oppInsurerCodeCi;
	}

	public void setOppInsurerCodeCi(String oppInsurerCodeCi) {
		this.oppInsurerCodeCi = oppInsurerCodeCi;
	}

	public String getOppInsurerAreaCi() {
		return this.oppInsurerAreaCi;
	}

	public void setOppInsurerAreaCi(String oppInsurerAreaCi) {
		this.oppInsurerAreaCi = oppInsurerAreaCi;
	}
	public String getOppIdBi() {
		return oppIdBi;
	}

	public void setOppIdBi(String oppIdBi) {
		this.oppIdBi = oppIdBi;
	}
	public String getOppIdCi() {
		return oppIdCi;
	}

	/**
	 * @param oppIdCi the oppIdCi to set
	 */
	public void setOppIdCi(String oppIdCi) {
		this.oppIdCi = oppIdCi;
	}

public void init(){
	   policyFlag=policyFlag==null?"":policyFlag;                  
	   oppRegistNoBi=oppRegistNoBi==null?"":oppRegistNoBi;         
	   oppPolicyNoBi=oppPolicyNoBi==null?"":oppPolicyNoBi;         
	   oppInsurerCodeBi=oppInsurerCodeBi==null?"":oppInsurerCodeBi;
	   oppInsurerAreaBi=oppInsurerAreaBi==null?"":oppInsurerAreaBi;
	   oppRegistNoCi=oppRegistNoCi==null?"":oppRegistNoCi;         
	   oppPolicyNoCi=oppPolicyNoCi==null?"":oppPolicyNoCi;         
	   oppInsurerCodeCi=oppInsurerCodeCi==null?"":oppInsurerCodeCi;
	   oppInsurerAreaCi=oppInsurerAreaCi==null?"":oppInsurerAreaCi;
	   oppIdBi=StringUtils.isEmpty(oppIdBi)?"":oppIdBi;
	   oppIdCi=StringUtils.isEmpty(oppIdCi)?"":oppIdCi;
	   
   }
}