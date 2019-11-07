package com.sinosoft.ms.model;

import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author wuhaijie
 * @createTime 上午9:33:26
 */

public class BankInfo {
	private int id;
	private String registNo;//报案号
	private String task_id;//工作流任务ID
	private String province;		//开户行省份
	private String city;			//开户行城市
	private String accountsName;	//银行账户名称
	private String accounts;		//银行账号
	private String bankName;		//银行开户行
	private String bankType;		//银行类型
	private String bankOutlets;	//银行网点名称
	private String bankNumber;	//银行号
	private String mobile;		//电话号码
	private String cityFlag;//是否同城
	private String priorityFlag;//汇款模式
	private String purpose;	//用途
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegistNo() {
		return registNo;
	}
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAccountsName() {
		return accountsName;
	}
	public void setAccountsName(String accountsName) {
		this.accountsName = accountsName;
	}
	public String getAccounts() {
		return accounts;
	}
	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBankOutlets() {
		return bankOutlets;
	}
	public void setBankOutlets(String bankOutlets) {
		this.bankOutlets = bankOutlets;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCityFlag() {
		return cityFlag;
	}
	public void setCityFlag(String cityFlag) {
		this.cityFlag = cityFlag;
	}
	public String getPriorityFlag() {
		return priorityFlag;
	}
	public void setPriorityFlag(String priorityFlag) {
		this.priorityFlag = priorityFlag;
	}
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public void init(){
		registNo=registNo==null?"":registNo;                        
		task_id=task_id==null?"":task_id; 
		province=province==null?"":province; 
		city=city==null?"":city; 
		accountsName=accountsName==null?"":accountsName; 
		accounts=accounts==null?"":accounts; 
		bankName=bankName==null?"":bankName; 
		bankType=bankType==null?"":bankType; 
		bankOutlets=bankOutlets==null?"":bankOutlets; 
		bankNumber=bankNumber==null?"":bankNumber; 
		mobile=mobile==null?"":mobile; 
		cityFlag=cityFlag==null?"":cityFlag; 
		priorityFlag=priorityFlag==null?"":priorityFlag; 
		purpose=purpose==null?"":purpose; 
	}
}

