package com.sinosoft.ms.model;

import java.io.Serializable;
import java.util.List;

/**
 * 系统名：移动查勘
 * 子系统名：人伤基本信息（多条）
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午11:56:15
 */

public class PersonData  implements Serializable{
	private String id;
	private String registNo;
	private String personDataId; 
	private String lossParty;    	
	private String severeNum;    
	private String minor;       
	private String deathNum;     
	private String sumLossFee;   
	private String rescueFee;    
	private String checkDate;    	
	private String checkSite;    
	private String rescueContext;
	
	private String createOwner;     //标的类型
	private String modfiyOwner;     //人伤属性
	
	
	
	
	private List<PersonDetailData> personDetailData;
	
	
	
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
	 * @return the personDataId
	 */
	public String getPersonDataId() {
		return personDataId;
	}
	/**
	 * @param personDataId the personDataId to set
	 */
	public void setPersonDataId(String personDataId) {
		this.personDataId = personDataId;
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
	 * @return the severeNum
	 */
	public String getSevereNum() {
		return severeNum;
	}
	/**
	 * @param severeNum the severeNum to set
	 */
	public void setSevereNum(String severeNum) {
		this.severeNum = severeNum;
	}
	/**
	 * @return the minor
	 */
	public String getMinor() {
		return minor;
	}
	/**
	 * @param minor the minor to set
	 */
	public void setMinor(String minor) {
		this.minor = minor;
	}
	/**
	 * @return the deathNum
	 */
	public String getDeathNum() {
		return deathNum;
	}
	/**
	 * @param deathNum the deathNum to set
	 */
	public void setDeathNum(String deathNum) {
		this.deathNum = deathNum;
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
	 * @return the rescueContext
	 */
	public String getRescueContext() {
		return rescueContext;
	}
	/**
	 * @param rescueContext the rescueContext to set
	 */
	public void setRescueContext(String rescueContext) {
		this.rescueContext = rescueContext;
	}
	
	
	
	
	
	public String getCreateOwner()
	{
		return createOwner;
	}
	public void setCreateOwner(String createOwner)
	{
		this.createOwner = createOwner;
	}
	public String getModfiyOwner()
	{
		return modfiyOwner;
	}
	public void setModfiyOwner(String modfiyOwner)
	{
		this.modfiyOwner = modfiyOwner;
	}
	/**
	 * @return the personDetailData
	 */
	public List<PersonDetailData> getPersonDetailData() {
		return personDetailData;
	}
	/**
	 * @param personDetailData the personDetailData to set
	 */
	public void setPersonDetailData(List<PersonDetailData> personDetailData) {
		this.personDetailData = personDetailData;
	}
	
    public void init(){
	   registNo=registNo==null?"":registNo;               
	   personDataId=personDataId==null?"":personDataId;   
	   lossParty=lossParty==null?"":lossParty;            
	   severeNum=severeNum==null?"":severeNum;            
	   minor=minor==null?"":minor;                        
	   deathNum=deathNum==null?"":deathNum;               
	   sumLossFee=sumLossFee==null?"":sumLossFee;         
	   rescueFee=rescueFee==null?"":rescueFee;            
	   checkDate=checkDate==null?"":checkDate;            
	   checkSite=checkSite==null?"":checkSite;            
	   rescueContext=rescueContext==null?"":rescueContext;

   }
}

