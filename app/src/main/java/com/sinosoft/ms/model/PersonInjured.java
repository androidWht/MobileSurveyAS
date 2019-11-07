package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：人伤信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午3:14:45
 */
public class PersonInjured implements Serializable {
	private static final long serialVersionUID = 7236582599988292424L;

	private String name; // 姓名
	private int age;     // 年龄
	private String sex;   // 性别
	private String injuriesTypes;// 伤亡类型
	private String whetherTo;   // 是否自行就医
	private String hospital;    // 就诊医院
	private double lossAmount;  // 损失金额
	private String stateDescription;// 伤情描述

	public PersonInjured() {

	}

	public PersonInjured(String name, String sex, int age,
			String injuriesTypes, String whetherTo, String hospital,
			double lossAmount, String stateDescription) {

		this.name = name;
		this.age = age;
		this.sex = sex;
		this.injuriesTypes = injuriesTypes;
		this.whetherTo = whetherTo;
		this.hospital = hospital;
		this.lossAmount = lossAmount;
		this.stateDescription = stateDescription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getInjuriesTypes() {
		return injuriesTypes;
	}

	public void setInjuriesTypes(String injuriesTypes) {
		this.injuriesTypes = injuriesTypes;
	}

	public String getWhetherTo() {
		return whetherTo;
	}

	public void setWhetherTo(String whetherTo) {
		this.whetherTo = whetherTo;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public double getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(double lossAmount) {
		this.lossAmount = lossAmount;
	}

	public String getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

}
