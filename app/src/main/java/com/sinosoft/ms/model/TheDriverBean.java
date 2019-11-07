package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：驾驶员信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午3:14:45
 */
public class TheDriverBean implements Serializable {
	private static final long serialVersionUID = 3930298299675379130L;
	// 基本信息
	private String name;// 姓名
	private String tel;// 电话
	private String quasiDrivingType;// 准驾车型
	private String drivingLicenseNumber;// 驾驶证号
	private String firstLicenseDate;// 初次领证日期
	private String option;// 操作

	// 详细
	private String sex;// 性别
	private String age;// 年龄
	private String certificatesType;// 证件类型
	private String certificatesNumber;// 证件号码

	public TheDriverBean() {
		super();
	}

	public TheDriverBean(String name, String tel, String quasiDrivingType,
			String drivingLicenseNumber, String firstLicenseDate, String sex,
			String age, String certificatesType, String certificatesNumber) {
		super();
		this.name = name;
		this.tel = tel;
		this.quasiDrivingType = quasiDrivingType;
		this.drivingLicenseNumber = drivingLicenseNumber;
		this.firstLicenseDate = firstLicenseDate;
		this.sex = sex;
		this.age = age;
		this.certificatesType = certificatesType;
		this.certificatesNumber = certificatesNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getQuasiDrivingType() {
		return quasiDrivingType;
	}

	public void setQuasiDrivingType(String quasiDrivingType) {
		this.quasiDrivingType = quasiDrivingType;
	}

	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	public String getFirstLicenseDate() {
		return firstLicenseDate;
	}

	public void setFirstLicenseDate(String firstLicenseDate) {
		this.firstLicenseDate = firstLicenseDate;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(String certificatesType) {
		this.certificatesType = certificatesType;
	}

	public String getCertificatesNumber() {
		return certificatesNumber;
	}

	public void setCertificatesNumber(String certificatesNumber) {
		this.certificatesNumber = certificatesNumber;
	}
}
