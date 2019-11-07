package com.sinosoft.ms.model;

import com.sinosoft.ms.utils.StringUtils;

/**
 * 案件信息数据结构类
 */
public class RegistData implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private long id;
	private String taskId;
	private String registNo;// 报案号
	private String registId;// 报案id号
	private String reportorName;// 报案人
	private String reportorNumber;// 报案人电话
	private String reportorMobile;// 报案人手机
	private String reportDate;// 报案日期
	private String reportHour;// 报案时间
	private String damageDate;// 出险日期
	private String damageHour;// 出险时间
	private String damageName;// 出险原因
	private String damageAddress;// 出险地点
	private long status;// 状态(0.未接收 1.接收 2.改派 3.拒绝 4.到达 5.完成 6.归档 )
	private long taskType;// 任务类型 (0.查勘 1.定损完成 2.简易定损 3.核损退定损)
	private String remark;// 出险经过说明
	private String salesman;// 处理员(理赔分配由谁来处理定损或查勘任务）
	private String licenseNo;// 车牌号码
	private String createDate;// 创建时间
	
	private long status1;        //通知状态：（缺省为0  1 。核价退回  2.核损退回  3。单证退回 ）
	private String insuredName;  //被保险人
	private String money;        //

	private String backOpinion;        //退回原因
	



	/** minimal constructor */
	public RegistData(String registNo, String reportorName, String reportDate, String reportHour, String damageDate, String damageHour, String damageName, String damageAddress, String remark)
	{
		this.registNo = registNo;
		this.reportorName = reportorName;
		this.reportDate = reportDate;
		this.reportHour = reportHour;
		this.damageDate = damageDate;
		this.damageHour = damageHour;
		this.damageName = damageName;
		this.damageAddress = damageAddress;
		this.remark = remark;
	}

	/** full constructor */
	public RegistData(String registNo, String reportorName, String reportorNumber, String reportorMobile, String reportDate, String reportHour, String damageDate, String damageHour, String damageName, String damageAddress, long status, long taskType, String remark, String salesman)
	{
		this.registNo = registNo;
		this.reportorName = reportorName;
		this.reportorNumber = reportorNumber;
		this.reportorMobile = reportorMobile;
		this.reportDate = reportDate;
		this.reportHour = reportHour;
		this.damageDate = damageDate;
		this.damageHour = damageHour;
		this.damageName = damageName;
		this.damageAddress = damageAddress;
		this.status = status;
		this.taskType = taskType;
		this.remark = remark;
		this.salesman = salesman;
	}

	/**
	 * @return the registId
	 */
	public String getRegistId()
	{
		return registId;
	}

	/**
	 * @param registId
	 *            the registId to set
	 */
	public void setRegistId(String registId)
	{
		this.registId = registId;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId()
	{
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public long getId()
	{
		return this.id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getRegistNo()
	{
		return this.registNo;
	}

	public void setRegistNo(String registNo)
	{
		this.registNo = registNo;
	}

	public String getReportorName()
	{
		return this.reportorName;
	}

	public void setReportorName(String reportorName)
	{
		this.reportorName = reportorName;
	}

	public String getReportorNumber()
	{
		return this.reportorNumber;
	}

	public void setReportorNumber(String reportorNumber)
	{
		this.reportorNumber = reportorNumber;
	}

	public String getReportorMobile()
	{
		return this.reportorMobile;
	}

	public void setReportorMobile(String reportorMobile)
	{
		this.reportorMobile = reportorMobile;
	}

	public String getReportDate()
	{
		return this.reportDate;
	}

	public void setReportDate(String reportDate)
	{
		this.reportDate = reportDate;
	}

	public String getReportHour()
	{
		return this.reportHour;
	}

	public void setReportHour(String reportHour)
	{
		this.reportHour = reportHour;
	}

	public String getDamageDate()
	{
		return this.damageDate;
	}

	public void setDamageDate(String damageDate)
	{
		this.damageDate = damageDate;
	}

	public String getDamageHour()
	{
		return this.damageHour;
	}

	public void setDamageHour(String damageHour)
	{
		this.damageHour = damageHour;
	}

	public String getDamageName()
	{
		return this.damageName;
	}

	public void setDamageName(String damageName)
	{
		this.damageName = damageName;
	}

	public String getDamageAddress()
	{
		return this.damageAddress;
	}

	public void setDamageAddress(String damageAddress)
	{
		this.damageAddress = damageAddress;
	}

	public long getStatus()
	{
		return this.status;
	}

	public void setStatus(long status)
	{
		this.status = status;
	}

	public long getTaskType()
	{
		return this.taskType;
	}

	public void setTaskType(long taskType)
	{
		this.taskType = taskType;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getSalesman()
	{
		return salesman;
	}

	public void setSalesman(String salesman)
	{
		this.salesman = salesman;
	}

	public String getLicenseNo()
	{
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo)
	{
		this.licenseNo = licenseNo;
	}

	public RegistData()
	{
	}

	public String getCreateDate()
	{
		return createDate;
	}

	/**
	 * @return the status1
	 */
	public long getStatus1()
	{
		return status1;
	}

	/**
	 * @param status1 the status1 to set
	 */
	public void setStatus1(long status1)
	{
		this.status1 = status1;
	}

	/**
	 * @return the insuredName
	 */
	public String getInsuredName()
	{
		return insuredName;
	}

	/**
	 * @param insuredName the insuredName to set
	 */
	public void setInsuredName(String insuredName)
	{
		this.insuredName = insuredName;
	}

	/**
	 * @return the money
	 */
	public String getMoney()
	{
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(String money)
	{
		this.money = money;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	/**
	 * @return the backOpinion
	 */
	public String getBackOpinion()
	{
		return backOpinion;
	}

	/**
	 * @param backOpinion the backOpinion to set
	 */
	public void setBackOpinion(String backOpinion)
	{
		this.backOpinion = backOpinion;
	}	
	
	public void init()
	{
		registNo = StringUtils.initWithNull(registNo);
		reportorName = StringUtils.initWithNull(reportorName);
		reportorNumber = StringUtils.initWithNull(reportorNumber);
		reportorMobile = StringUtils.initWithNull(reportorMobile);
		reportDate = StringUtils.initWithNull(reportDate);
		reportHour = StringUtils.initWithNull(reportHour);
		damageDate = StringUtils.initWithNull(damageDate);
		damageHour = StringUtils.initWithNull(damageHour);
		damageName = StringUtils.initWithNull(damageName);
		damageAddress = StringUtils.initWithNull(damageAddress);
		remark = StringUtils.initWithNull(remark);
		salesman = StringUtils.initWithNull(salesman);
		licenseNo = StringUtils.initWithNull(licenseNo);
	}

}