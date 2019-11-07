package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘
 * 子系统名：责任比例
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午3:57:25
 */

public class LiabilityRatio implements Serializable{
	private String id;
	private String registNo;
	private String ratioId;//id标示
	private String flag;//标志字段	
	private String riskCode;//险种	;
	private String policyFlag;//保单交强/商业标志	
	private String policyNo;//保单号	
	private String claimFlag;//是否属于商业险责任	
	private String indemnityDuty;//商业险赔偿责任	
	private String indemnityDutyRate;//商业险赔偿责任比例	
	private String cIIndemDuty;//是否属于交强险责任	
	private String cIDutyFlag;//交强险赔偿责任	
	
	
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the registNo
	 */
	public String getRegistNo() {
		return registNo;
	}
	/**
	 * @param registNo the registNo to set
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	/**
	 * @return the ratioId
	 */
	public String getRatioId() {
		return ratioId;
	}
	/**
	 * @param ratioId the ratioId to set
	 */
	public void setRatioId(String ratioId) {
		this.ratioId = ratioId;
	}
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @return the riskCode
	 */
	public String getRiskCode() {
		return riskCode;
	}
	/**
	 * @param riskCode the riskCode to set
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	/**
	 * @return the policyFlag
	 */
	public String getPolicyFlag() {
		return policyFlag;
	}
	/**
	 * @param policyFlag the policyFlag to set
	 */
	public void setPolicyFlag(String policyFlag) {
		this.policyFlag = policyFlag;
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
	/**
	 * @return the claimFlag
	 */
	public String getClaimFlag() {
		return claimFlag;
	}
	/**
	 * @param claimFlag the claimFlag to set
	 */
	public void setClaimFlag(String claimFlag) {
		this.claimFlag = claimFlag;
	}
	/**
	 * @return the indemnityDuty
	 */
	public String getIndemnityDuty() {
		return indemnityDuty;
	}
	/**
	 * @param indemnityDuty the indemnityDuty to set
	 */
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}
	/**
	 * @return the indemnityDutyRate
	 */
	public String getIndemnityDutyRate() {
		return indemnityDutyRate;
	}
	/**
	 * @param indemnityDutyRate the indemnityDutyRate to set
	 */
	public void setIndemnityDutyRate(String indemnityDutyRate) {
		this.indemnityDutyRate = indemnityDutyRate;
	}
	/**
	 * @return the cIIndemDuty
	 */
	public String getcIIndemDuty() {
		return cIIndemDuty;
	}
	/**
	 * @param cIIndemDuty the cIIndemDuty to set
	 */
	public void setcIIndemDuty(String cIIndemDuty) {
		this.cIIndemDuty = cIIndemDuty;
	}
	/**
	 * @return the cIDutyFlag
	 */
	public String getcIDutyFlag() {
		return cIDutyFlag;
	}
	/**
	 * @param cIDutyFlag the cIDutyFlag to set
	 */
	public void setcIDutyFlag(String cIDutyFlag) {
		this.cIDutyFlag = cIDutyFlag;
	}
  
     public void init(){
    	 registNo=registNo==null?"":registNo;                           
    	 ratioId=ratioId==null?"":ratioId;                              
    	 flag=flag==null?"":flag;                                       
    	 riskCode=riskCode==null?"":riskCode;                           
    	 policyFlag=policyFlag==null?"":policyFlag;                     
    	 policyNo=policyNo==null?"":policyNo;                           
    	 claimFlag=claimFlag==null?"":claimFlag;                        
    	 indemnityDuty=indemnityDuty==null?"":indemnityDuty;            
    	 indemnityDutyRate=indemnityDutyRate==null?"":indemnityDutyRate;
    	 cIIndemDuty=cIIndemDuty==null?"":cIIndemDuty;                  
    	 cIDutyFlag=cIDutyFlag==null?"":cIDutyFlag;                     

     }
}

