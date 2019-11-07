package com.sinosoft.ms.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 系统名：移动查勘
 * 子系统名：查勘基本信息
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午3:13:06
 */

public class BasicSurvey {
	private String id;
	private String registNo;//报案号
	private String registId;//报案id号
	private String task_id;//工作流任务ID
	private String subrogationFlag;//是否代位求偿
	private String claimSignFlag;//索赔申请书标志
	private String damageCode;//出险原因
	private String checkSite;//查勘地点
	private String checkDate;//查勘时间
	private String checker1;//查勘员1
	private String checkerName;//查勘员名称
	private String caseFlag;//互碰自赔
	private String damageAddress;//出险地点
	private String intermediaryFlag;//查勘类别
	private String intermediaryCode;// 公估公司代码
	private String intermediaryName;//公估公司名称
	private String manageType;//事故处理类型
	private String lossType;//损失类别
	private String damageCaseCode;//事故类型
	private String firstSiteFlag;//第一现场查勘
	//新增双面标识
	private String twoAvoidFlag;//是否双免标识
	private String twoAvoidSelected; //双免选择标识
	
	private String solutionUnit;//事故处理部门
	private String riskCode;//险种
	private String opinion;//查勘意见
	
	private String enabledSubrPlatform;//启用代位求偿标识
	private String isKindCodeA;//标的车是否投保车损险标识。只有标的车的情况才传值。

	public String getId() {
		return id;
	}
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
	 * @return the task_id
	 */
	public String getTask_id() {
		return task_id;
	}
	/**
	 * @param task_id the task_id to set
	 */
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	/**
	 * @return the subrogationFlag
	 */
	public String getSubrogationFlag() {
		return subrogationFlag;
	}
	/**
	 * @param subrogationFlag the subrogationFlag to set
	 */
	public void setSubrogationFlag(String subrogationFlag) {
		this.subrogationFlag = subrogationFlag;
	}
	/**
	 * @return the claimSignFlag
	 */
	public String getClaimSignFlag() {
		return claimSignFlag;
	}
	/**
	 * @param claimSignFlag the claimSignFlag to set
	 */
	public void setClaimSignFlag(String claimSignFlag) {
		this.claimSignFlag = claimSignFlag;
	}
	/**
	 * @return the damageCode
	 */
	public String getDamageCode() {
		return damageCode;
	}
	/**
	 * @param damageCode the damageCode to set
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	/**
	 * @return the checkSite
	 */
	public String getCheckSite() {
		return checkSite;
	}
	/**
	 * @param checkSite the checkSite to set
	 */
	public void setCheckSite(String checkSite) {
		this.checkSite = checkSite;
	}
	/**
	 * @return the checkDate
	 */
	public String getCheckDate() {
		return checkDate;
	}
	/**
	 * @param checkDate the checkDate to set
	 */
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	/**
	 * @return the checker1
	 */
	public String getChecker1() {
		return checker1;
	}
	/**
	 * @param checker1 the checker1 to set
	 */
	public void setChecker1(String checker1) {
		this.checker1 = checker1;
	}
	
	
	public String getCheckerName() {
		return checkerName;
	}
	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}
	/**
	 * @return the caseFlag
	 */
	public String getCaseFlag() {
		return caseFlag;
	}
	/**
	 * @param caseFlag the caseFlag to set
	 */
	public void setCaseFlag(String caseFlag) {
		this.caseFlag = caseFlag;
	}
	/**
	 * @return the damageAddress
	 */
	public String getDamageAddress() {
		return damageAddress;
	}
	/**
	 * @param damageAddress the damageAddress to set
	 */
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}
	/**
	 * @return the intermediaryFlag
	 */
	public String getIntermediaryFlag() {
		return intermediaryFlag;
	}
	/**
	 * @param intermediaryFlag the intermediaryFlag to set
	 */
	public void setIntermediaryFlag(String intermediaryFlag) {
		this.intermediaryFlag = intermediaryFlag;
	}
	/**
	 * @return the intermediaryCode
	 */
	public String getIntermediaryCode() {
		return intermediaryCode;
	}
	/**
	 * @param intermediaryCode the intermediaryCode to set
	 */
	public void setIntermediaryCode(String intermediaryCode) {
		this.intermediaryCode = intermediaryCode;
	}
	/**
	 * @return the intermediaryName
	 */
	public String getIntermediaryName() {
		return intermediaryName;
	}
	/**
	 * @param intermediaryName the intermediaryName to set
	 */
	public void setIntermediaryName(String intermediaryName) {
		this.intermediaryName = intermediaryName;
	}
	/**
	 * @return the manageType
	 */
	public String getManageType() {
		return manageType;
	}
	/**
	 * @param manageType the manageType to set
	 */
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}
	/**
	 * @return the lossType
	 */
	public String getLossType() {
		return lossType;
	}
	/**
	 * @param lossType the lossType to set
	 */
	public void setLossType(String lossType) {
		this.lossType = lossType;
	}
	/**
	 * @return the damageCaseCode
	 */
	public String getDamageCaseCode() {
		return damageCaseCode;
	}
	/**
	 * @param damageCaseCode the damageCaseCode to set
	 */
	public void setDamageCaseCode(String damageCaseCode) {
		this.damageCaseCode = damageCaseCode;
	}
	/**
	 * @return the firstSiteFlag
	 */
	public String getFirstSiteFlag() {
		return firstSiteFlag;
	}
	/**
	 * @param firstSiteFlag the firstSiteFlag to set
	 */
	public void setFirstSiteFlag(String firstSiteFlag) {
		this.firstSiteFlag = firstSiteFlag;
	}
	/**
	 * @return the solutionUnit
	 */
	public String getSolutionUnit() {
		return solutionUnit;
	}
	/**
	 * @param solutionUnit the solutionUnit to set
	 */
	public void setSolutionUnit(String solutionUnit) {
		this.solutionUnit = solutionUnit;
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
	 * @return the opinion
	 */
	public String getOpinion() {
		return opinion;
	}
	/**
	 * @param opinion the opinion to set
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	/**
	 * @return the enabledSubrPlatform
	 */
	public String getEnabledSubrPlatform() {
		return enabledSubrPlatform;
	}
	/**
	 * @param enabledSubrPlatform the enabledSubrPlatform to set
	 */
	public void setEnabledSubrPlatform(String enabledSubrPlatform) {
		this.enabledSubrPlatform = enabledSubrPlatform;
	}
	/**
	 * @return the isKindCodeA
	 */
	public String getIsKindCodeA() {
		return isKindCodeA;
	}
	/**
	 * @param isKindCodeA the isKindCodeA to set
	 */
	public void setIsKindCodeA(String isKindCodeA) {
		this.isKindCodeA = isKindCodeA;
	}
	
	public String getTwoAvoidFlag() {
		return twoAvoidFlag;
	}
	public void setTwoAvoidFlag(String twoAvoidFlag) {
		this.twoAvoidFlag = twoAvoidFlag;
	}
	
	
	public String getTwoAvoidSelected() {
		return twoAvoidSelected;
	}
	public void setTwoAvoidSelected(String twoAvoidSelected) {
		this.twoAvoidSelected = twoAvoidSelected;
	}
	public void init(){
		
		registNo=registNo==null?"":registNo;                        
		task_id=task_id==null?"":task_id;                           
		subrogationFlag=subrogationFlag==null?"":subrogationFlag;   
		claimSignFlag=claimSignFlag==null?"":claimSignFlag;         
		damageCode=damageCode==null?"":damageCode;                  
		checkSite=checkSite==null?"":checkSite;                     
		checkDate=checkDate==null?"":checkDate;                     
		checker1=checker1==null?"":checker1;   
		checkerName=checkerName==null?"":checkerName;
		caseFlag=caseFlag==null?"":caseFlag;                        
		damageAddress=damageAddress==null?"":damageAddress;         
		intermediaryFlag=intermediaryFlag==null?"":intermediaryFlag;
		intermediaryCode=intermediaryCode==null?"":intermediaryCode;
		intermediaryName=intermediaryName==null?"":intermediaryName;
		manageType=manageType==null?"":manageType;                  
		lossType=lossType==null?"":lossType;                        
		damageCaseCode=damageCaseCode==null?"":damageCaseCode;      
		firstSiteFlag=firstSiteFlag==null?"":firstSiteFlag;         
		solutionUnit=solutionUnit==null?"":solutionUnit;            
		riskCode=riskCode==null?"":riskCode;                        
		opinion=opinion==null?"":opinion; 
		enabledSubrPlatform=enabledSubrPlatform==null?"":enabledSubrPlatform;//启用代位求偿标识
		isKindCodeA=isKindCodeA==null?"":isKindCodeA;
		twoAvoidFlag=twoAvoidFlag==null?"0":twoAvoidFlag;
		twoAvoidSelected=twoAvoidSelected==null?"0":twoAvoidSelected;
	}
	

}

