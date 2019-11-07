package com.sinosoft.ms.model;

/**
 * 系统名：移动查勘定损 子系统名：被保人信息 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Hijack
 * @createTime 下午4:19:14
 */
public class InsuredData
{
	private long id;
	private String registNo;
	private String taskId;
	private String insuredName; // 被保人姓名
	private String phoneNumber; // 被保人电话号码

	public String getInsuredName()
	{
		return insuredName;
	}

	public void setInsuredName(String insuredName)
	{
		this.insuredName = insuredName;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getRegistNo()
	{
		return registNo;
	}

	public void setRegistNo(String registNo)
	{
		this.registNo = registNo;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public void init()
	{
		registNo = registNo == null ? "" : registNo;
		taskId = taskId == null ? "" : taskId;
		insuredName = insuredName == null ? "" : insuredName;
		phoneNumber = phoneNumber == null ? "" : phoneNumber;
	}

}
