package com.sinosoft.ms.model;

import java.io.Serializable;
import java.util.List;

/**
 * 系统名：移动查勘
 * 子系统名：财产损失信息（多条）
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午11:22:52
 */

public class PropData implements Serializable{
	private String propId;  //               
    private String registNo;//报案号                          报案号
	private String checkPropId;//财产损失ID	 
	private String lossParty;//损失方	               损失名称
	private String relatePersonName;//联系人	    物损属性
	private String relatePhoneNum;//联系方式	    序号
	private String sumLossFee;//重大估损金额	     损失金额 
	private String rescueFee;//施救费	               
	private String checkDate;//查勘日期	              （车上物|三者物）类型
	private String reserveFlag;//重大赔案标示    险种	
	private String checkSite;//查勘地点	               费用名称（物损）
	private String rescueInfo;//施救过程描述	     损失程度描述
	private String remark;//备注	                                     备注	
	private String id;
	private List<PropDetailData> propDetailDatas;
	private PropDetailData propDetailData;
	
	
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
	 * @return the propId
	 */
	public String getPropId() {
		return propId;
	}
	/**
	 * @param propId the propId to set
	 */
	public void setPropId(String propId) {
		this.propId = propId;
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
	 * @return the checkPropId
	 */
	public String getCheckPropId() {
		return checkPropId;
	}
	/**
	 * @param checkPropId the checkPropId to set
	 */
	public void setCheckPropId(String checkPropId) {
		this.checkPropId = checkPropId;
	}
	/**
	 * @return the lossParty
	 */
	public String getLossParty() {
		return lossParty;
	}
	/**
	 * @param lossParty the lossParty to set
	 */
	public void setLossParty(String lossParty) {
		this.lossParty = lossParty;
	}
	/**
	 * @return the relatePersonName
	 */
	public String getRelatePersonName() {
		return relatePersonName;
	}
	/**
	 * @param relatePersonName the relatePersonName to set
	 */
	public void setRelatePersonName(String relatePersonName) {
		this.relatePersonName = relatePersonName;
	}
	/**
	 * @return the relatePhoneNum
	 */
	public String getRelatePhoneNum() {
		return relatePhoneNum;
	}
	/**
	 * @param relatePhoneNum the relatePhoneNum to set
	 */
	public void setRelatePhoneNum(String relatePhoneNum) {
		this.relatePhoneNum = relatePhoneNum;
	}
	/**
	 * @return the sumLossFee
	 */
	public String getSumLossFee() {
		return sumLossFee;
	}
	/**
	 * @param sumLossFee the sumLossFee to set
	 */
	public void setSumLossFee(String sumLossFee) {
		this.sumLossFee = sumLossFee;
	}
	/**
	 * @return the rescueFee
	 */
	public String getRescueFee() {
		return rescueFee;
	}
	/**
	 * @param rescueFee the rescueFee to set
	 */
	public void setRescueFee(String rescueFee) {
		this.rescueFee = rescueFee;
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
	 * @return the reserveFlag
	 */
	public String getReserveFlag() {
		return reserveFlag;
	}
	/**
	 * @param reserveFlag the reserveFlag to set
	 */
	public void setReserveFlag(String reserveFlag) {
		this.reserveFlag = reserveFlag;
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
	 * @return the rescueInfo
	 */
	public String getRescueInfo() {
		return rescueInfo;
	}
	/**
	 * @param rescueInfo the rescueInfo to set
	 */
	public void setRescueInfo(String rescueInfo) {
		this.rescueInfo = rescueInfo;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the propDetailDatas
	 */
	public List<PropDetailData> getPropDetailDatas() {
		return propDetailDatas;
	}
	/**
	 * @param propDetailDatas the propDetailDatas to set
	 */
	public void setPropDetailDatas(List<PropDetailData> propDetailDatas) {
		this.propDetailDatas = propDetailDatas;
	}
	/**
	 * @return the propDetailData
	 */
	public PropDetailData getPropDetailData() {
		return propDetailData;
	}
	/**
	 * @param propDetailData the propDetailData to set
	 */
	public void setPropDetailData(PropDetailData propDetailData) {
		this.propDetailData = propDetailData;
	}
    public void init(){
    	propId=propId==null?"":propId;
    	checkPropId=checkPropId==null?"":checkPropId;               
    	registNo=registNo==null?"":registNo;    
    	lossParty=lossParty==null?"":lossParty;                     
    	relatePersonName=relatePersonName==null?"":relatePersonName;
    	relatePhoneNum=relatePhoneNum==null?"":relatePhoneNum;      
    	sumLossFee=sumLossFee==null?"":sumLossFee;                  
    	rescueFee=rescueFee==null?"":rescueFee;                     
    	checkDate=checkDate==null?"":checkDate;                     
    	reserveFlag=reserveFlag==null?"":reserveFlag;               
    	checkSite=checkSite==null?"":checkSite;                     
    	rescueInfo=rescueInfo==null?"":rescueInfo;                  
    	remark=remark==null?"":remark;                              

    }
	
}

