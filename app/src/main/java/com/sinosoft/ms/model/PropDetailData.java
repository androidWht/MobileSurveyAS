package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘
 * 子系统名：财产损失清单信息（隶属于财产损失信息 多条）
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午11:45:28
 */

public class PropDetailData implements Serializable{
	private String id;
	private String propId;
	private String lossItemName;//损失财产名称	
	private String lossFee;//损失金额	
	private String lossDegreeCode;///损失程度	代码
	private String lossSpeciesCode;//损失种类	代码
	
	public PropDetailData() {}
	
	public PropDetailData(PropDetailData bean) {
		
		this.id = "";
		this.propId =bean.propId;
		this.lossItemName = bean.lossItemName;
		this.lossFee = bean.lossFee;
		this.lossDegreeCode =bean.lossDegreeCode;
		this.lossSpeciesCode =bean.lossSpeciesCode;
	}
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
	 * @return the lossItemName
	 */
	public String getLossItemName() {
		return lossItemName;
	}
	/**
	 * @param lossItemName the lossItemName to set
	 */
	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
	}
	/**
	 * @return the lossFee
	 */
	public String getLossFee() {
		return lossFee;
	}
	/**
	 * @param lossFee the lossFee to set
	 */
	public void setLossFee(String lossFee) {
		this.lossFee = lossFee;
	}
	/**
	 * @return the lossDegreeCode
	 */
	public String getLossDegreeCode() {
		return lossDegreeCode;
	}
	/**
	 * @param lossDegreeCode the lossDegreeCode to set
	 */
	public void setLossDegreeCode(String lossDegreeCode) {
		this.lossDegreeCode = lossDegreeCode;
	}
	/**
	 * @return the lossSpeciesCode
	 */
	public String getLossSpeciesCode() {
		return lossSpeciesCode;
	}
	/**
	 * @param lossSpeciesCode the lossSpeciesCode to set
	 */
	public void setLossSpeciesCode(String lossSpeciesCode) {
		this.lossSpeciesCode = lossSpeciesCode;
	}
	public void init(){
		propId=propId==null?"":propId;                           
		lossItemName=lossItemName==null?"":lossItemName;         
		lossFee=lossFee==null?"":lossFee;                        
		lossDegreeCode=lossDegreeCode==null?"":lossDegreeCode;   
		lossSpeciesCode=lossSpeciesCode==null?"":lossSpeciesCode;

		
	}
	
}

