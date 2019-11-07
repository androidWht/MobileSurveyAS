package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘
 * 子系统名：
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午6:04:58
 */

public class CarLossData implements Serializable{
	private String lossId;//唯一标识
	private String checkCarId;
	private String damageFlag;//损失情况	
	private String reserveFlag;//重大赔案标识	
	private String sumLossFee;//估损金额	
	private String rescueFee;//施救费用	
	private String checkSite;//查勘地点	
	private String checkDate;///查勘日期	
	private String defSite;//预约定损地点	
	private String kindCode;//险别	
	private String indemnityDuty;//责任比例 
	private String indemnityDutyRate;//比例	
	private String lossPart;//损失部位	
	
	
	
	/**
	 * @return the lossId
	 */
	public String getLossId() {
		return lossId;
	}
	/**
	 * @param lossId the lossId to set
	 */
	public void setLossId(String lossId) {
		this.lossId = lossId;
	}
	/**
	 * @return the chechCarId
	 */
	public String getCheckCarId() {
		return checkCarId;
	}
	/**
	 * @param chechCarId the chechCarId to set
	 */
	public void setCheckCarId(String checkCarId) {
		this.checkCarId = checkCarId;
	}
	/**
	 * @return the damageFlag
	 */
	public String getDamageFlag() {
		return damageFlag;
	}
	/**
	 * @param damageFlag the damageFlag to set
	 */
	public void setDamageFlag(String damageFlag) {
		this.damageFlag = damageFlag;
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
	 * @return the defSite
	 */
	public String getDefSite() {
		return defSite;
	}
	/**
	 * @param defSite the defSite to set
	 */
	public void setDefSite(String defSite) {
		this.defSite = defSite;
	}
	/**
	 * @return the kindCode
	 */
	public String getKindCode() {
		return kindCode;
	}
	/**
	 * @param kindCode the kindCode to set
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
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
	 * @return the lossPart
	 */
	public String getLossPart() {
		return lossPart;
	}
	/**
	 * @param lossPart the lossPart to set
	 */
	public void setLossPart(String lossPart) {
		this.lossPart = lossPart;
	}
    public void init(){
    	lossId=lossId==null?"":lossId;                                 
    	checkCarId=checkCarId==null?"":checkCarId;;                   
    	damageFlag=damageFlag==null?"":damageFlag;                     
    	reserveFlag=reserveFlag==null?"":reserveFlag;                  
    	sumLossFee=sumLossFee==null?"":sumLossFee;                     
    	rescueFee=rescueFee==null?"":rescueFee;                        
    	checkSite=checkSite==null?"":checkSite;                        
    	checkDate=checkDate==null?"":checkDate;                        
    	defSite=defSite==null?"":defSite;                              
    	kindCode=kindCode==null?"":kindCode;                           
    	indemnityDuty=indemnityDuty==null?"":indemnityDuty;            
    	indemnityDutyRate=indemnityDutyRate==null?"":indemnityDutyRate;
    	lossPart=lossPart==null?"":lossPart;                           

    	
    }
    
}

