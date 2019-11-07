package com.sinosoft.ms.model;

import java.io.Serializable;

public class RepairProject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String professionType;// 工种类型
	private String professionName;// 工种名称
	private String repairName;// 修理名称
	private String repairNumber;// 修理编码
	private String professionCode;// 工种代码
	private String remark;// 备注
	private String repairWorkeHours;// 维修工时
	private String workeHoursFee;// 工时费率
	private String repairMoney;// 维修金额
	private String insur;// 险别

	public RepairProject() {
		super();
	}

	public RepairProject(String professionType, String professionName,
			String repairName, String repairNumber, String professionCode,
			String remark, String repairWorkeHours, String workeHoursFee,
			String repairMoney, String insur) {
		super();
		this.professionType = professionType;
		this.professionName = professionName;
		this.repairName = repairName;
		this.repairNumber = repairNumber;
		this.professionCode = professionCode;
		this.remark = remark;
		this.repairWorkeHours = repairWorkeHours;
		this.workeHoursFee = workeHoursFee;
		this.repairMoney = repairMoney;
		this.insur = insur;
	}

	public String getProfessionType() {
		return professionType;
	}

	public void setProfessionType(String professionType) {
		this.professionType = professionType;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public String getRepairName() {
		return repairName;
	}

	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}

	public String getRepairNumber() {
		return repairNumber;
	}

	public void setRepairNumber(String repairNumber) {
		this.repairNumber = repairNumber;
	}

	public String getProfessionCode() {
		return professionCode;
	}

	public void setProfessionCode(String professionCode) {
		this.professionCode = professionCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRepairWorkeHours() {
		return repairWorkeHours;
	}

	public void setRepairWorkeHours(String repairWorkeHours) {
		this.repairWorkeHours = repairWorkeHours;
	}

	public String getWorkeHoursFee() {
		return workeHoursFee;
	}

	public void setWorkeHoursFee(String workeHoursFee) {
		this.workeHoursFee = workeHoursFee;
	}

	public String getRepairMoney() {
		return repairMoney;
	}

	public void setRepairMoney(String repairMoney) {
		this.repairMoney = repairMoney;
	}

	public String getInsur() {
		return insur;
	}

	public void setInsur(String insur) {
		this.insur = insur;
	}

}
