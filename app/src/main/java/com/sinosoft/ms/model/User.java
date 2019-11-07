package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：用户信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午9:37:41
 */

public class User implements Serializable {
	private static final long serialVersionUID = 5889770283042410076L;

	private String name;// 用户名
	private String password;// 密码
	private String uid;// 设备ID
	private String deptNo;// 机构代码
	private int send_time = 0;

	public int getSend_time() {
		return send_time;
	}

	public void setSend_time(int send_time) {
		this.send_time = send_time;
	}

	private boolean loginFlag;

	public User() {

	}

	public User(String name, String password, String uid) {

		this.name = name;
		this.password = password;
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public boolean isLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(boolean loginFlag) {
		this.loginFlag = loginFlag;
	}


}
