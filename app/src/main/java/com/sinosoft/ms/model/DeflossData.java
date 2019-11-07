package com.sinosoft.ms.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * DeflossData entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class DeflossData implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private long id;
	private String registId;
	private String registNo;
	private String taskId;
	private String subrogationFlag;
	private String claimSignFlag;
	private String cetainLossType;
	private String repairFactoryCode;
	private String repairFactoryType;
	private String repairFactoryName;
	private String defLossDate;
	private String defSite;
	private String sendDate;
	private String lossLevel;
	private String handlerCode;
	private String estimatedDate;
	private String caseFlag;
	private String lossPart;
	private String damagePurchasePrice;
	private String intermediaryFlag;
	private String intermediaryCode;
	private String intermediaryName;
	private String enabledSubrPlatform;
	private String isKindCodeA;
	private String isUnilateralAccident;
	private String damageDate;
	private String option;
	private String insureTermCode;
	private String insureTerm;
	// 2013.8.16 快速理赔标志
	private String quickClaimFlag;
	// 2013.9.11 新增定损员名称
	private String handlerName;
	// 2014.5.28 派工类型代码（0.正常案件，1.核损退定损，2.理算退定损）
	private String dispatchType;

	private LossVehicle lossVehicle;
	private BasicVehicle basicVehicle;
	private BankInfo bankInfo;
	private List<LossFitInfoItem> fitInfoList;
	private List<LossRepairInfoItem> repairInfoList;
	private List<LossAssistInfoItem> assistInfoList;
	private List<KindCodeData> kindCodeDataList;

	private List<RegistThirdCarData> registThirdCarDatas;

	// 保单信息
	private List<PolicyData> policyDatas;// 保单信息

	// Constructors

	/** default constructor */
	public DeflossData()
	{
	}

	/** minimal constructor */
	public DeflossData(String registNo, String taskId, String subrogationFlag, String claimSignFlag, String defLossDate, String handlerCode, String caseFlag, String intermediaryFlag, String damageDate)
	{
		this.registNo = registNo;
		this.taskId = taskId;
		this.subrogationFlag = subrogationFlag;
		this.claimSignFlag = claimSignFlag;
		this.defLossDate = defLossDate;
		this.handlerCode = handlerCode;
		this.caseFlag = caseFlag;
		this.intermediaryFlag = intermediaryFlag;
		this.damageDate = damageDate;
	}

	/** full constructor */
	public DeflossData(String registNo, String taskId, String subrogationFlag, String claimSignFlag, String cetainLossType, String repairFactoryCode, String repairFactoryType, String defLossDate, String defSite, String sendDate, String lossLevel, String handlerCode, String estimatedDate, String caseFlag, String lossPart, String damagePurchasePrice, String intermediaryFlag, String intermediaryCode, String intermediaryName, String damageDate, String quickClaimFlag)
	{
		this.registNo = registNo;
		this.taskId = taskId;
		this.subrogationFlag = subrogationFlag;
		this.claimSignFlag = claimSignFlag;
		this.cetainLossType = cetainLossType;
		this.repairFactoryCode = repairFactoryCode;
		this.repairFactoryType = repairFactoryType;
		this.defLossDate = defLossDate;
		this.defSite = defSite;
		this.sendDate = sendDate;
		this.lossLevel = lossLevel;
		this.handlerCode = handlerCode;
		this.estimatedDate = estimatedDate;
		this.caseFlag = caseFlag;
		this.lossPart = lossPart;
		this.damagePurchasePrice = damagePurchasePrice;
		this.intermediaryFlag = intermediaryFlag;
		this.intermediaryCode = intermediaryCode;
		this.intermediaryName = intermediaryName;
		this.damageDate = damageDate;
		this.quickClaimFlag = quickClaimFlag;
	}

	// Property accessors
	public long getId()
	{
		return this.id;
	}

	public void setId(long id)
	{
		this.id = id;
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

	public String getRegistNo()
	{
		return this.registNo;
	}

	public void setRegistNo(String registNo)
	{
		this.registNo = registNo;
	}

	public String getTaskId()
	{
		return this.taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getSubrogationFlag()
	{
		return this.subrogationFlag;
	}

	public void setSubrogationFlag(String subrogationFlag)
	{
		this.subrogationFlag = subrogationFlag;
	}

	public String getClaimSignFlag()
	{
		return this.claimSignFlag;
	}

	public void setClaimSignFlag(String claimSignFlag)
	{
		this.claimSignFlag = claimSignFlag;
	}

	public String getCetainLossType()
	{
		return this.cetainLossType;
	}

	public void setCetainLossType(String cetainLossType)
	{
		this.cetainLossType = cetainLossType;
	}

	public String getRepairFactoryCode()
	{
		return this.repairFactoryCode;
	}

	public void setRepairFactoryCode(String repairFactoryCode)
	{
		this.repairFactoryCode = repairFactoryCode;
	}

	public String getRepairFactoryType()
	{
		return this.repairFactoryType;
	}

	public void setRepairFactoryType(String repairFactoryType)
	{
		this.repairFactoryType = repairFactoryType;
	}

	public String getDefLossDate()
	{
		return this.defLossDate;
	}

	public void setDefLossDate(String defLossDate)
	{
		this.defLossDate = defLossDate;
	}

	public String getDefSite()
	{
		return this.defSite;
	}

	public void setDefSite(String defSite)
	{
		this.defSite = defSite;
	}

	public String getSendDate()
	{
		return this.sendDate;
	}

	public void setSendDate(String sendDate)
	{
		this.sendDate = sendDate;
	}

	public String getLossLevel()
	{
		return this.lossLevel;
	}

	public void setLossLevel(String lossLevel)
	{
		this.lossLevel = lossLevel;
	}

	public String getHandlerCode()
	{
		return this.handlerCode;
	}

	public void setHandlerCode(String handlerCode)
	{
		this.handlerCode = handlerCode;
	}

	public String getEstimatedDate()
	{
		return this.estimatedDate;
	}

	public void setEstimatedDate(String estimatedDate)
	{
		this.estimatedDate = estimatedDate;
	}

	public String getCaseFlag()
	{
		return this.caseFlag;
	}

	public void setCaseFlag(String caseFlag)
	{
		this.caseFlag = caseFlag;
	}

	public String getLossPart()
	{
		return this.lossPart;
	}

	public void setLossPart(String lossPart)
	{
		this.lossPart = lossPart;
	}

	public String getDamagePurchasePrice()
	{
		return this.damagePurchasePrice;
	}

	public void setDamagePurchasePrice(String damagePurchasePrice)
	{
		this.damagePurchasePrice = damagePurchasePrice;
	}

	public String getIntermediaryFlag()
	{
		return this.intermediaryFlag;
	}

	public void setIntermediaryFlag(String intermediaryFlag)
	{
		this.intermediaryFlag = intermediaryFlag;
	}

	public String getIntermediaryCode()
	{
		return this.intermediaryCode;
	}

	public void setIntermediaryCode(String intermediaryCode)
	{
		this.intermediaryCode = intermediaryCode;
	}

	public String getIntermediaryName()
	{
		return this.intermediaryName;
	}

	public void setIntermediaryName(String intermediaryName)
	{
		this.intermediaryName = intermediaryName;
	}

	public String getEnabledSubrPlatform()
	{
		return enabledSubrPlatform;
	}

	public void setEnabledSubrPlatform(String enabledSubrPlatform)
	{
		this.enabledSubrPlatform = enabledSubrPlatform;
	}

	public String getIsKindCodeA()
	{
		return isKindCodeA;
	}

	public void setIsKindCodeA(String isKindCodeA)
	{
		this.isKindCodeA = isKindCodeA;
	}

	public String getIsUnilateralAccident()
	{
		return isUnilateralAccident;
	}

	public void setIsUnilateralAccident(String isUnilateralAccident)
	{
		this.isUnilateralAccident = isUnilateralAccident;
	}

	public String getDamageDate()
	{
		return this.damageDate;
	}

	public void setDamageDate(String damageDate)
	{
		this.damageDate = damageDate;
	}

	public List<KindCodeData> getKindCodeDataList()
	{
		return kindCodeDataList;
	}

	public void setKindCodeDataList(List<KindCodeData> kindCodeDataList)
	{
		this.kindCodeDataList = kindCodeDataList;
	}

	public LossVehicle getLossVehicle()
	{
		return lossVehicle;
	}

	public void setLossVehicle(LossVehicle lossVehicle)
	{
		this.lossVehicle = lossVehicle;
	}

	public BasicVehicle getBasicVehicle()
	{
		return basicVehicle;
	}

	public void setBasicVehicle(BasicVehicle basicVehicle)
	{
		this.basicVehicle = basicVehicle;
	}

	public List<LossFitInfoItem> getFitInfoList()
	{
		return fitInfoList;
	}

	public void setFitInfoList(List<LossFitInfoItem> fitInfoList)
	{
		this.fitInfoList = fitInfoList;
	}

	public List<LossRepairInfoItem> getRepairInfoList()
	{
		return repairInfoList;
	}

	public void setRepairInfoList(List<LossRepairInfoItem> repairInfoList)
	{
		this.repairInfoList = repairInfoList;
	}

	public List<LossAssistInfoItem> getAssistInfoList()
	{
		return assistInfoList;
	}

	public void setAssistInfoList(List<LossAssistInfoItem> assistInfoList)
	{
		this.assistInfoList = assistInfoList;
	}

	/**
	 * @return the registThirdCarDatas
	 */
	public List<RegistThirdCarData> getRegistThirdCarDatas()
	{
		return registThirdCarDatas;
	}

	/**
	 * @param registThirdCarDatas
	 *            the registThirdCarDatas to set
	 */
	public void setRegistThirdCarDatas(List<RegistThirdCarData> registThirdCarDatas)
	{
		this.registThirdCarDatas = registThirdCarDatas;
	}

	/**
	 * @return the repairFactoryName
	 */
	public String getRepairFactoryName()
	{
		return repairFactoryName;
	}

	/**
	 * @param repairFactoryName
	 *            the repairFactoryName to set
	 */
	public void setRepairFactoryName(String repairFactoryName)
	{
		this.repairFactoryName = repairFactoryName;
	}

	public BankInfo getBankInfo()
	{
		return bankInfo;
	}

	public void setBankInfo(BankInfo bankInfo)
	{
		this.bankInfo = bankInfo;
	}

	/**
	 * @return the option
	 */
	public String getOption()
	{
		return option;
	}

	/**
	 * @param option
	 *            the option to set
	 */
	public void setOption(String option)
	{
		this.option = option;
	}

	/**
	 * 获取本地定损意见
	 * 
	 * @return the option
	 */
	public String getLocalOption()
	{
		String opnStr = "";
		JSONObject optionObject = null;
		JSONObject o;
		try
		{
			// 将意见对象转换成JSON对象
			if (null == this.option || "".equals(this.option))
			{
				optionObject = new JSONObject();
			}
			else
			{
				optionObject = new JSONObject(this.option);
			}
			// 获取本地意见子节点
			opnStr = optionObject.getString("option");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return opnStr;
	}

	/**
	 * 设置本地定损意见
	 * 
	 * @param option
	 *            the option to set
	 */
	public void setLocalOption(String option)
	{
		JSONObject optionObject = null;
		try
		{
			if (null == this.option || "".equals(this.option))
			{
				optionObject = new JSONObject();
			}
			else
			{
				optionObject = new JSONObject(this.option);
			}
			// 设置本地定损意见子节点
			optionObject.put("option", option);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		this.option = optionObject.toString();
	}

	/**
	 * 获取历史意见
	 * 
	 * @return
	 */
	public List<DeflossOptionData> getHistoryOption()
	{
		JSONObject optionObject;
		// 历史对象JSON列表
		JSONArray hOptions;
		// 历史对象列表
		List<DeflossOptionData> history = new ArrayList<DeflossOptionData>();
		DeflossOptionData data;
		try
		{
			// 将意见转成JSON对象
			if (null == option || "".equals(option))
			{
				optionObject = new JSONObject();
			}
			else
			{
				optionObject = new JSONObject(option);
			}
			// 获取子节点列表
			hOptions = optionObject.getJSONArray("deflossOptionDatas");
			for (int i = 0; i < hOptions.length(); i++)
			{
				// 将子节点列表传入到List中
				data = new DeflossOptionData();
				data.init();
				optionObject = hOptions.getJSONObject(i);
				if (optionObject.has("operator"))
				{
					data.setOperator(optionObject.getString("operator"));
				}
				if (optionObject.has("opinion"))
				{
					data.setOpinion(optionObject.getString("opinion"));
				}
				if (optionObject.has("inputDate"))
				{
					data.setInputDate(optionObject.getString("inputDate"));
				}
				history.add(data);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return history;
	}

	/**
	 * 将历史意见转换为字符串
	 * 
	 * @return
	 */
	public String getHistoryString()
	{
		JSONObject optionObject;
		// 历史对象JSON列表
		JSONArray hOptions;
		// 历史对象列表
		String result = "";
		try
		{
			// 将意见转成JSON对象
			if (null == option || "".equals(option))
			{
				optionObject = new JSONObject();
			}
			else
			{
				optionObject = new JSONObject(option);
			}
			// 获取子节点列表
			hOptions = optionObject.getJSONArray("deflossOptionDatas");
			for (int i = 0; i < hOptions.length(); i++)
			{
				// 将子节点列表传入到List中
				optionObject = hOptions.getJSONObject(i);
				if (optionObject.has("inputDate"))
				{
					result = result + optionObject.getString("inputDate") + "\n";
				}
				if (optionObject.has("operator"))
				{
					result = result + optionObject.getString("operator") + "\n";
				}
				if (optionObject.has("opinion"))
				{
					result = result + optionObject.getString("opinion") + "\n";
				}
				result = result + "\n";

			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取历史意见
	 * 
	 * @return
	 */
	public void setHistoryOption(List<DeflossOptionData> history)
	{
		// 历史对象列表
		JSONArray hOptions = new JSONArray();
		JSONObject hOption;
		JSONObject optionObject = null;
		try
		{
			if (null == option || "".equals(option))
			{
				optionObject = new JSONObject();
			}
			else
			{
				optionObject = new JSONObject(option);
			}
			for (int i = 0; i < history.size(); i++)
			{
				// 将每个列表中的对象转成JSON对象，插入到JSONArray中
				DeflossOptionData data = history.get(i);
				hOption = new JSONObject();
				hOption.put("operator", data.getOperator());
				hOption.put("opinion", data.getOpinion());
				hOption.put("inputDate", data.getInputDate());
				hOptions.put(i, hOption);
			}
			// 将JSONArray插入到DeflossOptionDatas子节点中
			optionObject.put("deflossOptionDatas", hOptions);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		this.option = optionObject.toString();
	}

	public String getInsureTermCode()
	{
		return insureTermCode;
	}

	public void setInsureTermCode(String insureTermCode)
	{
		this.insureTermCode = insureTermCode;
	}

	public String getInsureTerm()
	{
		return insureTerm;
	}

	public void setInsureTerm(String insureTerm)
	{
		this.insureTerm = insureTerm;
	}

	public String getQuickClaimFlag()
	{
		return quickClaimFlag;
	}

	public void setQuickClaimFlag(String quickClaimFlag)
	{
		this.quickClaimFlag = quickClaimFlag;
	}

	public String getDispatchType()
	{
		return dispatchType;
	}

	public void setDispatchType(String dispatchType)
	{
		this.dispatchType = dispatchType;
	}

	public String getHandlerName()
	{
		return handlerName;
	}

	public void setHandlerName(String handlerName)
	{
		this.handlerName = handlerName;
	}

	public List<PolicyData> getPolicyDatas()
	{
		return policyDatas;
	}

	public void setPolicyDatas(List<PolicyData> policyDatas)
	{
		this.policyDatas = policyDatas;
	}

	public void init()
	{
		registId = registId == null ? "" : registId;
		repairFactoryName = repairFactoryName == null ? "" : repairFactoryName;
		registNo = registNo == null ? "" : registNo;
		taskId = taskId == null ? "" : taskId;
		subrogationFlag = subrogationFlag == null ? "" : subrogationFlag;
		claimSignFlag = claimSignFlag == null ? "" : claimSignFlag;
		cetainLossType = cetainLossType == null ? "" : cetainLossType;
		repairFactoryCode = repairFactoryCode == null ? "" : repairFactoryCode;
		repairFactoryType = repairFactoryType == null ? "" : repairFactoryType;
		defLossDate = defLossDate == null ? "" : defLossDate;
		defSite = defSite == null ? "" : defSite;
		sendDate = sendDate == null ? "" : sendDate;
		lossLevel = lossLevel == null ? "" : lossLevel;
		handlerCode = handlerCode == null ? "" : handlerCode;
		handlerName = handlerName == null ? "" : handlerName;
		estimatedDate = estimatedDate == null ? "" : estimatedDate;
		caseFlag = caseFlag == null ? "" : caseFlag;
		lossPart = lossPart == null ? "" : lossPart;
		damagePurchasePrice = damagePurchasePrice == null ? "" : damagePurchasePrice;
		intermediaryFlag = intermediaryFlag == null ? "" : intermediaryFlag;
		intermediaryCode = intermediaryCode == null ? "" : intermediaryCode;
		intermediaryName = intermediaryName == null ? "" : intermediaryName;
		damageDate = damageDate == null ? "" : damageDate;
		option = option == null ? "" : option;
		insureTermCode = insureTermCode == null ? "" : insureTermCode;
		insureTerm = insureTerm == null ? "" : insureTerm;
		quickClaimFlag = quickClaimFlag == null ? "0" : quickClaimFlag;
	}
}