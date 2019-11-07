package com.sinosoft.ms.model;

import java.util.Date;

/**
 * 报案三者车信息
 * 
 * @author MyEclipse Persistence Tools
 */
public class RegistThirdCarData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String registNo;// 报案号
	private String licenseNo;// 保单号
	private String company;// 承保公司
	private String brandName;// 厂牌名称
	private String thirdPolicyNo;// 三者车保单号

	private String type;// 类型 1.查勘 2.定损
	private String createOwner;
	private Date createDate;
	private String modfiyOwner;
	private Date modfiyDate;

	public RegistThirdCarData() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getThirdPolicyNo() {
		return thirdPolicyNo;
	}

	public void setThirdPolicyNo(String thirdPolicyNo) {
		this.thirdPolicyNo = thirdPolicyNo;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getCreateOwner() {
		return createOwner;
	}

	public void setCreateOwner(String createOwner) {
		this.createOwner = createOwner;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModfiyOwner() {
		return modfiyOwner;
	}

	public void setModfiyOwner(String modfiyOwner) {
		this.modfiyOwner = modfiyOwner;
	}

	public Date getModfiyDate() {
		return modfiyDate;
	}

	public void setModfiyDate(Date modfiyDate) {
		this.modfiyDate = modfiyDate;
	}

	public void init() {
		registNo = registNo == null ? "" : registNo;
		licenseNo = licenseNo == null ? "" : licenseNo;
		company = company == null ? "" : company;
		brandName = brandName == null ? "" : brandName;
		thirdPolicyNo = thirdPolicyNo == null ? "" : thirdPolicyNo;

		type = type == null ? "1" : type;
		createOwner = createOwner == null ? "" : createOwner;
		modfiyOwner = modfiyOwner == null ? "" : modfiyOwner;

	}
}
