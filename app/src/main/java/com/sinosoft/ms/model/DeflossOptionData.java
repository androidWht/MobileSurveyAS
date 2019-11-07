package com.sinosoft.ms.model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 系统名：移动查勘定损 
 * 子系统名：定损历史意见信息
 * 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 5:39:03 PM
 */

public class DeflossOptionData {
	private String operator;	//操作人
	private String opinion;		//意见信息
	private String inputDate;	//录入时间
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
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
	 * @return the inputDate
	 */
	public String getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate the inputDate to set
	 */
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	
	public void init(){
		operator = operator == null ? "" : operator ;
		opinion = opinion == null ? "" : opinion;
		inputDate = inputDate == null ? "" : inputDate;
	}
	
	
}

