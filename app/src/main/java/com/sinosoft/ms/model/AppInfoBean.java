package com.sinosoft.ms.model;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 下午4:10:55
 */

public class AppInfoBean {
	private String sys;
	private String version;
	private String address;
	private String force;		//1：强制更新，2：非强制更新
	private String name;
	private String remark;	//备注
	
	public void init(){
		this.sys = this.sys == null ? "" : sys ;
		this.version = this.version == null ? "" : version ;
		this.address = this.address == null ? "" : address ;
		this.force = this.force == null ? "" : force ;
		this.name = this.name == null ? "" : name ;
		this.remark = this.remark == null ? "" : remark ;
	}

	/**
	 * @return the sys
	 */
	public String getSys() {
		return sys;
	}

	/**
	 * @param sys
	 *            the sys to set
	 */
	public void setSys(String sys) {
		this.sys = sys;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the force
	 */
	public String getForce() {
		return force;
	}

	/**
	 * @param force
	 *            the force to set
	 */
	public void setForce(String force) {
		this.force = force;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}
