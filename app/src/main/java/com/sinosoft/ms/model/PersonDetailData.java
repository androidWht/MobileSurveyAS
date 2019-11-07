package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘
 * 子系统名：人伤清单（隶属于人伤基本信息 多条）
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午12:02:27
 */

public class PersonDetailData implements Serializable{
	private String id;
	private String personId;
	private String personName;    //姓名	
	private String personSex;     //性别	
	private String age;           //年龄	
	private String personPayType; //伤亡类型
	private String treatType;     //是否自行就医
	
	
	private String hospitalName;  //就诊医院	
	private String lossFee;       //损失金额	
	private String woundDetail;   //伤情描述	
	 
	
	public PersonDetailData(){
		
	}
	
	
	public PersonDetailData(PersonDetailData bean) {
		this.id = "";
		this.personId =bean. personId;
		this.personName = bean.personName;
		this.personSex = bean.personSex;
		this.age = bean.age;
		this.personPayType = bean.personPayType;
		this.treatType = bean.treatType;
		this.hospitalName =bean. hospitalName;
		this.lossFee = bean.lossFee;
		this.woundDetail =bean. woundDetail;
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
	 * @return the personId
	 */
	public String getPersonId() {
		return personId;
	}
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}
	/**
	 * @param personName the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	/**
	 * @return the personSex
	 */
	public String getPersonSex() {
		return personSex;
	}
	/**
	 * @param personSex the personSex to set
	 */
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}
	/**
	 * @return the personPayType
	 */
	public String getPersonPayType() {
		return personPayType;
	}
	/**
	 * @param personPayType the personPayType to set
	 */
	public void setPersonPayType(String personPayType) {
		
		this.personPayType = personPayType;
	}
	/**
	 * @return the treatType
	 */
	public String getTreatType() {
		return treatType;
	}
	/**
	 * @param treatType the treatType to set
	 */
	public void setTreatType(String treatType) {
		
		this.treatType = treatType;
	}
	/**
	 * @return the hospitalName
	 */
	public String getHospitalName() {
		return hospitalName;
	}
	/**
	 * @param hospitalName the hospitalName to set
	 */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
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
	 * @return the woundDetail
	 */
	public String getWoundDetail() {
		return woundDetail;
	}
	/**
	 * @param woundDetail the woundDetail to set
	 */
	public void setWoundDetail(String woundDetail) {
		this.woundDetail = woundDetail;
	}
	
	public void init(){
		personId=personId==null?"":personId;
		 personName=personName==null?"":personName;
		 personSex=personSex==null?"":personSex;
		 age=age==null?"":age;
		 personPayType=personPayType==null?"":personPayType;
		 treatType=treatType==null?"":treatType;
		 hospitalName=hospitalName==null?"":hospitalName;
		 lossFee=lossFee==null?"":lossFee;
		 woundDetail=woundDetail==null?"":woundDetail;     
		
	}
	

}

