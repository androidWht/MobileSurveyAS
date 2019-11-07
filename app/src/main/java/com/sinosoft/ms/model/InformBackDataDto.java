package com.sinosoft.ms.model;

import java.io.Serializable;
import java.util.Date;

public class InformBackDataDto implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String taskId;
	private String type;// 状态类型 1.核损 2.核价 3.单证
	private String status;// 1.表示通过 2.表示不通过
	private String backOpinion;// 退回意见
	private Double sumPaid;// 核损金额
	private String insuredName;// 被保险人名称
	private Date   creatDate;// 创建时间
	private String definition1;
	private String definition2;
	private String definition3;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getBackOpinion()
	{
		return backOpinion;
	}

	public void setBackOpinion(String backOpinion)
	{
		this.backOpinion = backOpinion;
	}

	public Double getSumPaid()
	{
		return sumPaid;
	}

	public void setSumPaid(Double sumPaid)
	{
		this.sumPaid = sumPaid;
	}

	public String getInsuredName()
	{
		return insuredName;
	}

	public void setInsuredName(String insuredName)
	{
		this.insuredName = insuredName;
	}

	public Date getCreatDate()
	{
		return creatDate;
	}

	public void setCreatDate(Date creatDate)
	{
		this.creatDate = creatDate;
	}

	public String getDefinition1()
	{
		return definition1;
	}

	public void setDefinition1(String definition1)
	{
		this.definition1 = definition1;
	}

	public String getDefinition2()
	{
		return definition2;
	}

	public void setDefinition2(String definition2)
	{
		this.definition2 = definition2;
	}

	public String getDefinition3()
	{
		return definition3;
	}

	public void setDefinition3(String definition3)
	{
		this.definition3 = definition3;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
