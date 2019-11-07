package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：驾驶员信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午3:14:45
 */
public class DamageBean implements Serializable {
	private static final long serialVersionUID = 3930298299675379130L;
	// 基本信息
	private String lossPropertyName;// 损失财产名称
	private String lossAmount;// 损失金额
	private String lossExtent;// 损失程度
	private String lossTypeDamage;// 损失种类

	public DamageBean() {
		super();
	}

	public DamageBean(String lossPropertyName, String lossAmount,
			String lossExtent, String lossTypeDamage) {
		super();
		this.lossPropertyName = lossPropertyName;
		this.lossAmount = lossAmount;
		this.lossExtent = lossExtent;
		this.lossTypeDamage = lossTypeDamage;
	}

	public String getLossPropertyName() {
		return lossPropertyName;
	}

	public void setLossPropertyName(String lossPropertyName) {
		this.lossPropertyName = lossPropertyName;
	}

	public String getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(String lossAmount) {
		this.lossAmount = lossAmount;
	}

	public String getLossExtent() {
		return lossExtent;
	}

	public void setLossExtent(String lossExtent) {
		this.lossExtent = lossExtent;
	}

	public String getLossTypeDamage() {
		return lossTypeDamage;
	}

	public void setLossTypeDamage(String lossTypeDamage) {
		this.lossTypeDamage = lossTypeDamage;
	}

}
