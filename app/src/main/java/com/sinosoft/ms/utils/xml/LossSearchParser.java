package com.sinosoft.ms.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.DeflossOptionData;
import com.sinosoft.ms.model.InsuredData;
import com.sinosoft.ms.model.KindCodeData;
import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.LossVehicleInsurance;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PolicyDetailData;
import com.sinosoft.ms.model.PolicyDriverData;
import com.sinosoft.ms.model.PolicyEngageData;
import com.sinosoft.ms.model.PolicyKindData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.utils.StringUtils;

public class LossSearchParser
{
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;

	private DeflossData deflossData;
	private List<LossVehicleInsurance> vehicleInsurList;
	private List<LossFitInfoItem> fitInfoList;
	private List<LossRepairInfoItem> repairInfoList;
	private List<LossAssistInfoItem> assistInfoList;
	private List<KindCodeData> kindCodeDataList;
	private LossVehicle lossVehicle;
	private LossVehicleInsurance lossVehicleInsur;
	private BankInfo bankInfo; // 2014-05-29新增，银行信息
	private List<DeflossOptionData> deflossOptionDatas; // 2014-05-29 新增，定损意见历史
	private DeflossOptionData deflossOptionData; // 定损历史意见

	private BasicVehicle basicVehicle;
	private LossFitInfoItem lossFitInfo;
	private LossRepairInfoItem lossRepairInfo;
	private LossAssistInfoItem lossAssistInfo;
	private KindCodeData kindCodeData;

	private InsuredData insuredData;

	private List<RegistThirdCarData> registThirdCarDatas;
	private RegistThirdCarData registThirdCarData;

	// 用来存放每次遍历后的元素名称(节点名称)
	private String tagName;
	private int type = 0;// 类型 0.定损基本信息 1.定损车辆信息 2.车辆基本信息 3.4.5.
	private int flag = 0;
	private String taskId;

	private List<String> tagNameList = new ArrayList<String>();

	private String tag = "";
	private List<PolicyData> policyDatas;// 保单信息 **public
	private PolicyData policyData;
	private PolicyDetailData policyDetailData;

	private List<PolicyKindData> policyKindDatas;
	private PolicyKindData policyKindData;

	private PolicyDriverData policyDriverData;
	private List<PolicyDriverData> policyDriverDatas;

	private List<PolicyEngageData> policyEngageDatas;
	private PolicyEngageData policyEngageData;

	private String registNo = "";

	public void parse(InputStream inputStream, String registNo) throws ParserConfigurationException, SAXException, IOException
	{
		this.registNo = registNo; // 报案号
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new BrandListHandler());
		xmlReader.parse(new InputSource(inputStream));
	}

	private class BrandListHandler extends DefaultHandler
	{
		// 只调用一次 初始化list集合
		@Override
		public void startDocument() throws SAXException
		{
			deflossData = new DeflossData();
			insuredData = new InsuredData();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length() != 0 ? localName : qName;
			if (tagName.equals("lossVehicle"))
			{
				lossVehicle = new LossVehicle();
				type = 1;
			}
			else if (tagName.equals("carPolicyDatas"))
			{
				vehicleInsurList = new LinkedList<LossVehicleInsurance>();
			}
			else if (tagName.equals("carPolicyData"))
			{
				lossVehicleInsur = new LossVehicleInsurance();
				type = 2;
			}
			else if (tagName.equals("basicVehicle"))
			{
				basicVehicle = new BasicVehicle();
				type = 3;
			}
			else if (tagName.equals("lossFitInfoItem"))
			{
				fitInfoList = new LinkedList<LossFitInfoItem>();
			}
			else if (tagName.equals("lossFitInfo"))
			{
				lossFitInfo = new LossFitInfoItem();
				type = 4;
			}
			else if (tagName.equals("lossRepairInfoItem"))
			{
				repairInfoList = new LinkedList<LossRepairInfoItem>();
			}
			else if (tagName.equals("lossRepairInfo"))
			{
				lossRepairInfo = new LossRepairInfoItem();
				type = 5;
			}
			else if (tagName.equals("lossAssistInfoItem"))
			{
				assistInfoList = new LinkedList<LossAssistInfoItem>();
			}
			else if (tagName.equals("lossAssistInfo"))
			{
				lossAssistInfo = new LossAssistInfoItem();
				type = 6;
			}
			else if (tagName.equals("kindCodeDatas"))
			{
				kindCodeDataList = new LinkedList<KindCodeData>();
			}
			else if (tagName.equals("kindCodeData"))
			{
				kindCodeData = new KindCodeData();
				type = 7;
			}
			else if (tagName.equals("registThirdCarDatas"))
			{
				registThirdCarDatas = new ArrayList<RegistThirdCarData>();

			}
			else if (tagName.equals("registThirdCarData"))
			{
				registThirdCarData = new RegistThirdCarData();
				registThirdCarData.setRegistNo(taskId);
				registThirdCarData.setType("1");
				type = 8;
			}
			else if (tagName.equals("policyDatas"))
			{// policyDatas
				policyDatas = new ArrayList<PolicyData>();
				type = 9;

			}
			else if (tagName.equals("policyKindDatas"))
			{
				policyKindDatas = new ArrayList<PolicyKindData>();
			}
			else if (tagName.equals("policyDriverDatas"))
			{
				policyDriverDatas = new ArrayList<PolicyDriverData>();
			}
			else if (tagName.equals("policyEngageDatas"))
			{
				policyEngageDatas = new ArrayList<PolicyEngageData>();
			}
			else if (tagName.equals("policyData"))
			{
				policyData = new PolicyData();
				policyData.setRegistNo(registNo);
				flag = 1;

			}
			else if (tagName.equals("policyDetailData"))
			{
				policyDetailData = new PolicyDetailData();
				flag = 2;

			}
			else if (tagName.equals("policyKindData"))
			{
				policyKindData = new PolicyKindData();
			}
			else if (tagName.equals("policyDriverData"))
			{
				policyDriverData = new PolicyDriverData();
			}
			else if (tagName.equals("policyEngageData"))
			{
				policyEngageData = new PolicyEngageData();
			}
			else if (tagName.equals("insuredData"))
			{
				insuredData = new InsuredData();
				type = 10;
			}
			else if (tagName.equals("bankInfoData"))
			{
				bankInfo = new BankInfo();
			}
			else if (tagName.equals("deflossOptionDatas"))
			{
				deflossOptionDatas = new ArrayList<DeflossOptionData>();
			}
			else if (tagName.equals("deflossOptionData"))
			{
				deflossOptionData = new DeflossOptionData();
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			super.characters(ch, start, length);
			if (tagName != null)
			{
				String data = new String(ch, start, length);
				if (null == data || "".equals(data))
				{
					return;
				}

				// 定损基本信息
				if (tagName.equals("registId"))
				{
					String temp = StringUtils.add(deflossData.getRegistId(), data);
				}
				else if (tagName.equals("registNo"))
				{
					deflossData.setRegistNo(StringUtils.add(deflossData.getRegistNo(), data));
				}
				else if (tagName.equals("taskId"))
				{
					deflossData.setTaskId(StringUtils.add(deflossData.getTaskId(), data));
					taskId = data;
				}
				else if (tagName.equals("subrogationFlag"))
				{
					deflossData.setSubrogationFlag(StringUtils.add(deflossData.getSubrogationFlag(), data));
				}
				else if (tagName.equals("claimSignFlag"))
				{
					deflossData.setClaimSignFlag(StringUtils.add(deflossData.getClaimSignFlag(), data));
				}
				else if (tagName.equals("cetainLossType"))
				{
					deflossData.setCetainLossType(StringUtils.add(deflossData.getCetainLossType(), data));
				}
				else if (tagName.equals("repairFactoryCode"))
				{
					deflossData.setRepairFactoryCode(StringUtils.add(deflossData.getRepairFactoryCode(), data));
				}
				else if (tagName.equals("repairFactoryType"))
				{
					deflossData.setRepairFactoryType(StringUtils.add(deflossData.getRepairFactoryType(), data));
				}
				else if (tagName.equals("repairFactoryName"))
				{
					// 2014.5.28新增
					deflossData.setRepairFactoryName(StringUtils.add(deflossData.getRepairFactoryName(), data));
				}
				else if (tagName.equals("quickClaimFlag"))
				{
					// 2014.5.28新增
					deflossData.setQuickClaimFlag(StringUtils.add(deflossData.getQuickClaimFlag(), data));
				}
				else if (tagName.equals("dispatchType"))
				{
					deflossData.setDispatchType(StringUtils.add(deflossData.getDispatchType(), data));
				}
				else if (tagName.equals("defLossDate"))
				{
					deflossData.setDefLossDate(StringUtils.add(deflossData.getDefLossDate(), data));
				}
				else if (tagName.equals("defSite"))
				{
					deflossData.setDefSite(StringUtils.add(deflossData.getDefSite(), data));
				}
				else if (tagName.equals("sendDate"))
				{
					deflossData.setSendDate(StringUtils.add(deflossData.getSendDate(), data));
				}
				else if (tagName.equals("lossLevel"))
				{
					deflossData.setLossLevel(StringUtils.add(deflossData.getLossLevel(), data));
				}
				else if (tagName.equals("handlerCode"))
				{
					deflossData.setHandlerCode(StringUtils.add(deflossData.getHandlerCode(), data));
				}
				else if (tagName.equals("handlerName"))
				{
					deflossData.setHandlerName(StringUtils.add(deflossData.getHandlerName(), data));
				}
				else if (tagName.equals("estimatedDate"))
				{
					deflossData.setEstimatedDate(StringUtils.add(deflossData.getEstimatedDate(), data));
				}
				else if (tagName.equals("caseFlag"))
				{
					deflossData.setCaseFlag(StringUtils.add(deflossData.getCaseFlag(), data));
				}
				else if (tagName.equals("lossPart"))
				{
					deflossData.setLossPart(StringUtils.add(deflossData.getLossPart(), data));
				}
				else if (tagName.equals("damagePurchasePrice"))
				{
					deflossData.setDamagePurchasePrice(StringUtils.add(deflossData.getDamagePurchasePrice(), data));
				}
				else if (tagName.equals("intermediaryFlag"))
				{
					deflossData.setIntermediaryFlag(StringUtils.add(deflossData.getIntermediaryFlag(), data));
				}
				else if (tagName.equals("intermediaryCode"))
				{
					deflossData.setIntermediaryCode(StringUtils.add(deflossData.getIntermediaryCode(), data));
				}
				else if (tagName.equals("intermediaryName"))
				{
					deflossData.setIntermediaryName(StringUtils.add(deflossData.getIntermediaryName(), data));
				}
				else if (tagName.equals("enabledSubrPlatform"))
				{
					deflossData.setEnabledSubrPlatform(StringUtils.add(deflossData.getEnabledSubrPlatform(), data));
				}
				else if (tagName.equals("isKindCodeA"))
				{
					deflossData.setIsKindCodeA(StringUtils.add(deflossData.getIsKindCodeA(), data));
				}
				else if (tagName.equals("isUnilateralAccident"))
				{
					deflossData.setIsUnilateralAccident(StringUtils.add(deflossData.getIsUnilateralAccident(), data));
					// 定损车辆信息
				}
				else if (tagName.equals("licenseNo"))
				{
					switch (type)
					{
					case 1:
						lossVehicle.setLicenseNo(StringUtils.add(lossVehicle.getLicenseNo(), data));
						break;
					case 8:
						registThirdCarData.setLicenseNo(StringUtils.add(registThirdCarData.getLicenseNo(), data));
						break;
					case 9:
						policyDetailData.setLicenseNo(StringUtils.add(policyDetailData.getLicenseNo(), data));
						break;
					}

				}
				else if (tagName.equals("defLossCarType"))
				{
					lossVehicle.setDefLossCarType(StringUtils.add(lossVehicle.getDefLossCarType(), data));
				}
				else if (tagName.equals("lossType"))
				{
					lossVehicle.setLossType(StringUtils.add(lossVehicle.getLossType(), data));
				}
				else if (tagName.equals("carOwner"))
				{
					switch (type)
					{
					case 1:
						lossVehicle.setCarOwner(StringUtils.add(lossVehicle.getCarOwner(), data));
						break;
					case 9:
						policyDetailData.setCarOwner(StringUtils.add(policyDetailData.getCarOwner(), data));
						break;

					}

				}
				else if (tagName.equals("enrollDate"))
				{
					switch (type)
					{
					case 1:
						lossVehicle.setEnrollDate(StringUtils.add(lossVehicle.getEnrollDate(), data));
						break;
					case 9:
						policyDetailData.setEnrollDate(StringUtils.add(policyDetailData.getEnrollDate(), data));
						break;

					}

				}
				else if (tagName.equals("carKindCode"))
				{
					lossVehicle.setCarKindCode(StringUtils.add(lossVehicle.getCarKindCode(), data));
				}
				else if (tagName.equals("frameNo"))
				{
					switch (type)
					{
					case 1:
						lossVehicle.setFrameNo(StringUtils.add(lossVehicle.getFrameNo(), data));
						break;
					case 9:
						policyDetailData.setFrameNo(StringUtils.add(policyDetailData.getFrameNo(), data));
						break;

					}
				}
				else if (tagName.equals("licenseColorCode"))
				{
					lossVehicle.setLicenseColorCode(StringUtils.add(lossVehicle.getLicenseColorCode(), data));
				}
				else if (tagName.equals("modelCode"))
				{
					lossVehicle.setModelCode(StringUtils.add(lossVehicle.getModelCode(), data));
				}
				else if (tagName.equals("vinNo"))
				{
					switch (type)
					{
					case 1:
						lossVehicle.setVinNo(StringUtils.add(lossVehicle.getVinNo(), data));
						break;
					case 9:
						policyDetailData.setVinNo(StringUtils.add(policyDetailData.getVinNo(), data));
						break;
					}
				}
				else if (tagName.equals("licenseType"))
				{
					lossVehicle.setLicenseType(StringUtils.add(lossVehicle.getLicenseType(), data));
				}
				else if (tagName.equals("gearboxType"))
				{
					lossVehicle.setGearboxType(StringUtils.add(lossVehicle.getGearboxType(), data));
				}
				else if (tagName.equals("gasType"))
				{
					lossVehicle.setGasType(StringUtils.add(lossVehicle.getGasType(), data));
				}
				else if (tagName.equals("carKindFrom"))
				{
					lossVehicle.setCarKindFrom(StringUtils.add(lossVehicle.getCarKindFrom(), data));
				}
				else if (tagName.equals("engineNo"))
				{
					switch (type)
					{
					case 1:
						lossVehicle.setEngineNo(StringUtils.add(lossVehicle.getEngineNo(), data));
						break;
					case 9:
						policyDetailData.setEngineNo(StringUtils.add(policyDetailData.getEngineNo(), data));
						break;
					}
				}
				else if (tagName.equals("insureComCode"))
				{
					lossVehicle.setInsureComCode(StringUtils.add(lossVehicle.getInsureComCode(), data));
				}
				else if (tagName.equals("indemDutyCI"))
				{
					lossVehicle.setIndemDutyCi(StringUtils.add(lossVehicle.getIndemDutyCi(), data));
				}
				else if (tagName.equals("indemnityDuty"))
				{
					lossVehicle.setIndemnityDuty(StringUtils.add(lossVehicle.getIndemnityDuty(), data));
				}
				else if (tagName.equals("indemnityDutyRate"))
				{
					lossVehicle.setIndemnityDutyRate(StringUtils.add(lossVehicle.getIndemnityDutyRate(), data));
					// 车辆承保信息
				}
				else if (tagName.equals("licenseColorCodeTo2"))
				{
					// 2014-05-28新增
					lossVehicle.setLicenseColorCodeTo2(StringUtils.add(lossVehicle.getLicenseColorCodeTo2(), data));

				}
				else if (tagName.equals("modelName"))
				{
					// 2014-05-28新增
					lossVehicle.setModelName(StringUtils.add(lossVehicle.getModelName(), data));
				}
				else if (tagName.equals("policyFlag"))
				{
					lossVehicleInsur.setPolicyFlag(StringUtils.add(lossVehicleInsur.getPolicyFlag(), data));
				}
				else if (tagName.equals("oppRegistNoBI"))
				{
					lossVehicleInsur.setOppRegistNoBi(StringUtils.add(lossVehicleInsur.getOppRegistNoBi(), data));
				}
				else if (tagName.equals("oppPolicyNoBI"))
				{
					lossVehicleInsur.setOppPolicyNoBi(StringUtils.add(lossVehicleInsur.getOppPolicyNoBi(), data));
				}
				else if (tagName.equals("oppInsurerCodeBI"))
				{
					lossVehicleInsur.setOppInsurerCodeBi(StringUtils.add(lossVehicleInsur.getOppInsurerCodeBi(), data));
				}
				else if (tagName.equals("oppInsurerAreaBI"))
				{
					lossVehicleInsur.setOppInsurerAreaBi(StringUtils.add(lossVehicleInsur.getOppInsurerAreaBi(), data));
				}
				else if (tagName.equals("oppRegistNoCI"))
				{
					lossVehicleInsur.setOppRegistNoCi(StringUtils.add(lossVehicleInsur.getOppRegistNoCi(), data));
				}
				else if (tagName.equals("oppPolicyNoCI"))
				{
					lossVehicleInsur.setOppPolicyNoCi(StringUtils.add(lossVehicleInsur.getOppPolicyNoCi(), data));
				}
				else if (tagName.equals("oppInsurerCodeCI"))
				{
					lossVehicleInsur.setOppInsurerCodeCi(StringUtils.add(lossVehicleInsur.getOppInsurerCodeCi(), data));
				}
				else if (tagName.equals("oppInsurerAreaCI"))
				{
					lossVehicleInsur.setOppInsurerAreaCi(StringUtils.add(lossVehicleInsur.getOppInsurerAreaCi(), data));
				}
				else if (tagName.equals("oppIdBi"))
				{
					lossVehicleInsur.setOppIdBi(StringUtils.add(lossVehicleInsur.getOppIdBi(), data));
				}
				else if (tagName.equals("oppIdCi"))
				{
					lossVehicleInsur.setOppIdCi(StringUtils.add(lossVehicleInsur.getOppIdCi(), data));

					// 车辆基本信息
				}
				else if (tagName.equals("caseNo"))
				{
					basicVehicle.setCaseNo(StringUtils.add(basicVehicle.getCaseNo(), data));
				}
				else if (tagName.equals("lossNo"))
				{
					basicVehicle.setLossNo(Long.parseLong(data));
				}
				else if (tagName.equals("vehKindCode"))
				{
					basicVehicle.setVehKindCode(StringUtils.add(basicVehicle.getVehKindCode(), data));
				}
				else if (tagName.equals("vehKindName"))
				{
					basicVehicle.setVehKindName(StringUtils.add(basicVehicle.getVehKindName(), data));
				}
				else if (tagName.equals("vehCertainCode"))
				{
					basicVehicle.setVehCertainCode(StringUtils.add(basicVehicle.getVehCertainCode(), data));
				}
				else if (tagName.equals("vehCertainName"))
				{
					basicVehicle.setVehCertainName(StringUtils.add(basicVehicle.getVehCertainName(), data));
				}
				else if (tagName.equals("vehSeriCode"))
				{
					basicVehicle.setVehSeriCode(StringUtils.add(basicVehicle.getVehSeriCode(), data));
				}
				else if (tagName.equals("vehSeriName"))
				{
					basicVehicle.setVehSeriName(StringUtils.add(basicVehicle.getVehSeriName(), data));
				}
				else if (tagName.equals("vehYearType"))
				{
					basicVehicle.setVehYearType(StringUtils.add(basicVehicle.getVehYearType(), data));
				}
				else if (tagName.equals("vehFactoryCode"))
				{
					basicVehicle.setVehFactoryCode(StringUtils.add(basicVehicle.getVehFactoryCode(), data));
				}
				else if (tagName.equals("vehFactoryName"))
				{
					basicVehicle.setVehFactoryName(StringUtils.add(basicVehicle.getVehFactoryName(), data));
				}
				else if (tagName.equals("vehBrandCode"))
				{
					basicVehicle.setVehBrandCode(StringUtils.add(basicVehicle.getVehBrandCode(), data));
				}
				else if (tagName.equals("vehBrandName"))
				{
					basicVehicle.setVehBrandName(StringUtils.add(basicVehicle.getVehBrandName(), data));
				}
				else if (tagName.equals("plateNo"))
				{
					basicVehicle.setPlateNo(StringUtils.add(basicVehicle.getPlateNo(), data));
				}
				else if (tagName.equals("selfConfigFlag"))
				{
					switch (type)
					{
					case 3:
						basicVehicle.setSelfConfigFlag(StringUtils.add(basicVehicle.getSelfConfigFlag(), data));
						break;
					case 4:
						lossFitInfo.setSelfConfigFlag(StringUtils.add(lossFitInfo.getSelfConfigFlag(), data));
						break;
					case 5:
						lossRepairInfo.setSelfConfigFlag(StringUtils.add(lossRepairInfo.getSelfConfigFlag(), data));
						break;
					}
				}
				else if (tagName.equals("version"))
				{
					basicVehicle.setVersion(StringUtils.add(basicVehicle.getVersion(), data));
				}
				else if (tagName.equals("remark"))
				{
					switch (type)
					{
					case 3:
						basicVehicle.setRemark(StringUtils.add(basicVehicle.getRemark(), data));
						break;
					case 4:
						lossFitInfo.setRemark(StringUtils.add(lossFitInfo.getRemark(), data));
						break;
					case 5:
						lossRepairInfo.setRemark(StringUtils.add(lossRepairInfo.getRemark(), data));
						break;
					case 6:
						lossAssistInfo.setRemark(StringUtils.add(lossAssistInfo.getRemark(), data));
						break;
					case 9:
						policyKindData.setRemark(StringUtils.add(policyKindData.getRemark(), data));
						break;
					}
				}
				else if (tagName.equals("vehGroupCode"))
				{
					basicVehicle.setVehGroupCode(StringUtils.add(basicVehicle.getVehGroupCode(), data));
				}
				else if (tagName.equals("vehGroupName"))
				{
					basicVehicle.setVehGroupName(StringUtils.add(basicVehicle.getVehGroupName(), data));
				}
				else if (tagName.equals("sumChgCompFee"))
				{
					basicVehicle.setSumChgCompFee(Double.parseDouble(data));
				}
				else if (tagName.equals("sumRepairFee"))
				{
					basicVehicle.setSumRepairFee(Double.parseDouble(data));
				}
				else if (tagName.equals("sumMaterialFee"))
				{
					basicVehicle.setSumMaterialFee(Double.parseDouble(data));
				}
				else if (tagName.equals("sumLossFee"))
				{
					basicVehicle.setSumLossFee(Double.parseDouble(data));
				}
				else if (tagName.equals("sumRemnant"))
				{
					basicVehicle.setSumRemnant(Double.parseDouble(data));
				}
				else if (tagName.equals("sumRescueFee"))
				{
					basicVehicle.setSumRescueFee(Double.parseDouble(data));
					// lossFitInfo
				}
				else if (tagName.equals("serialNo"))
				{
					switch (type)
					{
					case 4:
						lossFitInfo.setSerialNo(Long.parseLong(data));
						break;
					case 5:
						lossRepairInfo.setSerialNo(Long.parseLong(data));
						break;
					case 6:
						lossAssistInfo.setSerialNo(Long.parseLong(data));
						break;
					}
				}
				else if (tagName.equals("jyId"))
				{
					switch (type)
					{
					case 4:
						lossFitInfo.setJyId(StringUtils.add(lossFitInfo.getJyId(), data));
						break;
					case 5:
						lossRepairInfo.setJyId(StringUtils.add(lossRepairInfo.getJyId(), data));
						break;
					case 6:
						lossAssistInfo.setJyId(StringUtils.add(lossAssistInfo.getJyId(), data));
						break;
					}
				}
				else if (tagName.equals("originalId"))
				{
					lossFitInfo.setOriginalId(StringUtils.add(lossFitInfo.getOriginalId(), data));
				}
				else if (tagName.equals("originalName"))
				{
					lossFitInfo.setOriginalName(StringUtils.add(lossFitInfo.getOriginalName(), data));
				}
				else if (tagName.equals("partId"))
				{
					lossFitInfo.setPartId(StringUtils.add(lossFitInfo.getPartId(), data));
				}
				else if (tagName.equals("partStandardCode"))
				{
					lossFitInfo.setPartStandardCode(StringUtils.add(lossFitInfo.getPartStandardCode(), data));
				}
				else if (tagName.equals("partStandard"))
				{
					lossFitInfo.setPartStandard(StringUtils.add(lossFitInfo.getPartStandard(), data));
				}
				else if (tagName.equals("partGroupCode"))
				{
					lossFitInfo.setPartGroupCode(StringUtils.add(lossFitInfo.getPartGroupCode(), data));
				}
				else if (tagName.equals("partGroupName"))
				{
					lossFitInfo.setPartGroupName(StringUtils.add(lossFitInfo.getPartGroupName(), data));
				}
				else if (tagName.equals("lossCount"))
				{
					lossFitInfo.setLossCount(Long.parseLong(data));
				}
				else if (tagName.equals("lossPrice"))
				{
					lossFitInfo.setLossPrice(Double.parseDouble(data));
					lossFitInfo.setSurveyQuotedPrice(StringUtils.add(lossFitInfo.getSurveyQuotedPrice(), data));
				}
				else if (tagName.equals("chgRefPrice"))
				{
					lossFitInfo.setChgRefPrice(Double.parseDouble(data));
				}
				else if (tagName.equals("remnant"))
				{
					lossFitInfo.setRemnant(Double.parseDouble(data));
				}
				else if (tagName.equals("ifRemain"))
				{
					lossFitInfo.setIfRemain(StringUtils.add(lossFitInfo.getIfRemain(), data));
				}
				else if (tagName.equals("chgCompSetCode"))
				{
					lossFitInfo.setChgCompSetCode(StringUtils.add(lossFitInfo.getChgCompSetCode(), data));
				}
				else if (tagName.equals("status"))
				{
					switch (type)
					{
					case 4:
						lossFitInfo.setStatus(StringUtils.add(lossFitInfo.getStatus(), data));
						break;
					case 5:
						lossRepairInfo.setStatus(StringUtils.add(lossRepairInfo.getStatus(), data));
						break;
					case 6:
						lossAssistInfo.setStatus(StringUtils.add(lossAssistInfo.getStatus(), data));
						break;
					}
				}
				else if (tagName.equals("insureTermCode"))
				{
					switch (type)
					{
					case 4:
						lossFitInfo.setInsureTermCode(StringUtils.add(lossFitInfo.getInsureTermCode(), data));
						break;
					case 5:
						lossRepairInfo.setInsureTermCode(StringUtils.add(lossRepairInfo.getInsureTermCode(), data));
						break;
					case 6:
						lossAssistInfo.setInsureTermCode(StringUtils.add(lossAssistInfo.getInsureTermCode(), data));
						break;
					case 7:
						kindCodeData.setInsureTermCode(StringUtils.add(kindCodeData.getInsureTermCode(), data));
						break;
					}
				}
				else if (tagName.equals("insureTerm"))
				{
					switch (type)
					{
					case 4:
						lossFitInfo.setInsureTerm(StringUtils.add(lossFitInfo.getInsureTerm(), data));
						break;
					case 5:
						lossRepairInfo.setInsureTerm(StringUtils.add(lossRepairInfo.getInsureTerm(), data));
						break;
					case 6:
						lossAssistInfo.setInsureTerm(StringUtils.add(lossAssistInfo.getInsureTerm(), data));
						break;
					case 7:
						kindCodeData.setInsureTerm(StringUtils.add(kindCodeData.getInsureTerm(), data));
						break;
					}
				}
				else if (tagName.equals("chgLocPrice"))
				{
					lossFitInfo.setChgLocPrice(Double.parseDouble(data));
				}
				else if (tagName.equals("locPrice1"))
				{
					lossFitInfo.setLocPrice1(Double.parseDouble(data));
				}
				else if (tagName.equals("locPrice2"))
				{
					lossFitInfo.setLocPrice2(Double.parseDouble(data));
				}
				else if (tagName.equals("locPrice3"))
				{
					lossFitInfo.setLocPrice3(Double.parseDouble(data));
				}
				else if (tagName.equals("refPrice1"))
				{
					lossFitInfo.setRefPrice1(Double.parseDouble(data));
				}
				else if (tagName.equals("refPrice2"))
				{
					lossFitInfo.setRefPrice2(Double.parseDouble(data));
				}
				else if (tagName.equals("refPrice3"))
				{
					lossFitInfo.setRefPrice3(Double.parseDouble(data));
					// lossRepairInfo
				}
				else if (tagName.equals("repairItemName"))
				{
					lossRepairInfo.setRepairItemName(StringUtils.add(lossRepairInfo.getRepairItemName(), data));
				}
				else if (tagName.equals("repairCode"))
				{
					lossRepairInfo.setRepairCode(StringUtils.add(lossRepairInfo.getRepairCode(), data));
				}
				else if (tagName.equals("repairName"))
				{
					lossRepairInfo.setRepairName(StringUtils.add(lossRepairInfo.getRepairName(), data));
				}
				else if (tagName.equals("repairId"))
				{
					lossRepairInfo.setRepairId(StringUtils.add(lossRepairInfo.getRepairId(), data));
				}
				else if (tagName.equals("repairItemCode"))
				{
					lossRepairInfo.setRepairItemCode(StringUtils.add(lossRepairInfo.getRepairItemCode(), data));
				}
				else if (tagName.equals("levelCode"))
				{
					lossRepairInfo.setLevelCode(StringUtils.add(lossRepairInfo.getLevelCode(), data));
				}
				else if (tagName.equals("manHour"))
				{
					lossRepairInfo.setManHour(Double.parseDouble(data));
				}
				else if (tagName.equals("hourFee"))
				{
					lossRepairInfo.setHourFee(Double.parseDouble(data));
				}
				else if (tagName.equals("repairFee"))
				{
					lossRepairInfo.setRepairFee(Double.parseDouble(data));
					// lossAssistInfo
				}
				else if (tagName.equals("materialFee"))
				{
					lossAssistInfo.setMaterialFee(Double.parseDouble(data));
				}
				else if (tagName.equals("materialName"))
				{
					lossAssistInfo.setMaterialName(StringUtils.add(lossAssistInfo.getMaterialName(), data));
				}
				else if (tagName.equals("unitPrice"))
				{
					lossAssistInfo.setUnitPrice(Double.parseDouble(data));
				}
				else if (tagName.equals("count"))
				{
					lossAssistInfo.setCount(Long.parseLong(data));
				}

				// 保单信息
				else if (tagName.equals("policyNo"))
				{
					policyData.setPolicyNo(StringUtils.add(policyData.getPolicyNo(), data));
				}
				else if (tagName.equals("insuredName"))
				{
					switch (type)
					{
					case 9:
						switch (flag)
						{
						case 1:
							policyData.setInsuredName(StringUtils.add(policyData.getInsuredName(), data));
							break;
						case 2:
							policyDetailData.setInsuredName(StringUtils.add(policyDetailData.getInsuredName(), data));
							break;
						default:
							break;
						}
						break;
					case 10:
						insuredData.setTaskId(taskId);
						insuredData.setInsuredName(StringUtils.add(insuredData.getInsuredName(), data));
						break;
					}
				}
				else if (tagName.equals("comCName"))
				{
					switch (flag)
					{
					case 1:
						policyData.setComCName(StringUtils.add(policyData.getComCName(), data));
						break;
					case 2:
						policyDetailData.setComCName(StringUtils.add(policyDetailData.getComCName(), data));
						break;
					default:
						break;
					}
				}
				else if (tagName.equals("startDate"))
				{
					switch (flag)
					{
					case 1:
						policyData.setStartDate(StringUtils.add(policyData.getStartDate(), data));
						break;
					case 2:
						policyDetailData.setStartDate(StringUtils.add(policyDetailData.getStartDate(), data));
						break;
					default:
						break;
					}

				}
				else if (tagName.equals("endDate"))
				{

					switch (flag)
					{
					case 1:
						policyData.setEndDate(StringUtils.add(policyData.getEndDate(), data));
						break;
					case 2:
						policyDetailData.setEndDate(StringUtils.add(policyDetailData.getEndDate(), data));
						break;
					default:
						break;
					}

				}
				else if (tagName.equals("riskCode"))
				{

					switch (flag)
					{
					case 1:
						policyData.setRiskCode(StringUtils.add(policyData.getRiskCode(), data));
						break;
					case 2:
						policyDetailData.setRiskCode(StringUtils.add(policyDetailData.getRiskCode(), data));
						break;
					default:
						break;
					}

				}
				else if (tagName.equals("policyNo"))
				{
					policyDetailData.setPolicyNo(StringUtils.add(policyDetailData.getPolicyNo(), data));
				}
				else if (tagName.equals("licenseColor"))
				{
					policyDetailData.setLicenseColor(StringUtils.add(policyDetailData.getLicenseColor(), data));
				}
				else if (tagName.equals("purchasePrice"))
				{
					policyDetailData.setPurchasePrice(StringUtils.add(policyDetailData.getPurchasePrice(), data));
				}
				else if (tagName.equals("clauseType"))
				{
					policyDetailData.setClauseType(StringUtils.add(policyDetailData.getClauseType(), data));
				}
				else if (tagName.equals("carKind"))
				{
					policyDetailData.setCarKind(StringUtils.add(policyDetailData.getCarKind(), data));
				}
				else if (tagName.equals("runArea"))
				{
					policyDetailData.setRunArea(StringUtils.add(policyDetailData.getRunArea(), data));
				}
				else if (tagName.equals("seatCount"))
				{
					policyDetailData.setSeatCount(StringUtils.add(policyDetailData.getSeatCount(), data));
				}
				else if (tagName.equals("useNature"))
				{
					policyDetailData.setUseNature(StringUtils.add(policyDetailData.getUseNature(), data));
				}
				else if (tagName.equals("colorCode"))
				{
					policyDetailData.setColorCode(StringUtils.add(policyDetailData.getColorCode(), data));
				}
				else if (tagName.equals("endorseTimes"))
				{
					policyDetailData.setEndorseTimes(StringUtils.add(policyDetailData.getEndorseTimes(), data));
				}
				else if (tagName.equals("m2Flag"))
				{
					policyDetailData.setM2Flag(StringUtils.add(policyDetailData.getM2Flag(), data));
				}
				else if (tagName.equals("claimTimes"))
				{
					policyDetailData.setClaimTimes(StringUtils.add(policyDetailData.getClaimTimes(), data));
				}
				else if (tagName.equals("identifyNumber"))
				{
					policyDetailData.setIdentifyNumber(StringUtils.add(policyDetailData.getIdentifyNumber(), data));
				}
				else if (tagName.equals("vipType"))
				{
					policyDetailData.setVipType(StringUtils.add(policyDetailData.getVipType(), data));
				}
				else if (tagName.equals("kindName"))
				{
					policyKindData.setKindName(StringUtils.add(policyKindData.getKindName(), data));
				}
				else if (tagName.equals("amount"))
				{
					policyKindData.setAmount(StringUtils.add(policyKindData.getAmount(), data));
				}
				else if (tagName.equals("driverName"))
				{
					policyDriverData.setDriverName(StringUtils.add(policyDriverData.getDriverName(), data));
				}
				else if (tagName.equals("sex"))
				{
					policyDriverData.setSex(StringUtils.add(policyDriverData.getSex(), data));
				}
				else if (tagName.equals("identifyNumber"))
				{
					policyDriverData.setIdentifyNumber(StringUtils.add(policyDriverData.getIdentifyNumber(), data));
				}
				else if (tagName.equals("drivingLicenseNo"))
				{
					policyDriverData.setDrivingLicenseNo(StringUtils.add(policyDriverData.getDrivingLicenseNo(), data));
				}
				else if (tagName.equals("acceptLicenseDate"))
				{
					policyDriverData.setAcceptLicenseDate(StringUtils.add(policyDriverData.getAcceptLicenseDate(), data));
				}
				else if (tagName.equals("clauseCode"))
				{
					policyEngageData.setClauseCode(StringUtils.add(policyEngageData.getClauseCode(), data));
				}
				else if (tagName.equals("clauses"))
				{
					policyEngageData.setClauses(StringUtils.add(policyEngageData.getClauses(), data));
				}

				// insuredData
				else if (tagName.equals("phoneNumber"))
				{
					insuredData.setPhoneNumber(StringUtils.add(insuredData.getPhoneNumber(), data));
				}

				// registThirdCarDatas
				if (tagName.equals("company"))
				{
					registThirdCarData.setCompany(StringUtils.add(registThirdCarData.getCompany(), data));
				}
				else if (tagName.equals("brandName"))
				{
					switch (type)
					{
					case 1:
						lossVehicle.setBrandName(data);
						break;
					case 8:
						registThirdCarData.setBrandName(data);
						break;
					case 9:
						policyDetailData.setBrandName(StringUtils.add(policyDetailData.getBrandName(), data));
						break;
					}
				}
				else if (tagName.equals("thirdPolicyNo"))
				{

					registThirdCarData.setThirdPolicyNo(StringUtils.add(registThirdCarData.getThirdPolicyNo(), data));
				}
				// 2014-05-29新增银行信息节点解析
				else if (tagName.equals("province"))
				{
					bankInfo.setProvince(StringUtils.add(bankInfo.getProvince(), data));
				}
				else if (tagName.equals("city"))
				{
					bankInfo.setCity(StringUtils.add(bankInfo.getCity(), data));
				}
				else if (tagName.equals("accountsName"))
				{
					bankInfo.setAccountsName(StringUtils.add(bankInfo.getAccountsName(), data));
				}
				else if (tagName.equals("accounts"))
				{
					bankInfo.setAccounts(StringUtils.add(bankInfo.getAccounts(), data));
				}
				else if (tagName.equals("bankName"))
				{
					bankInfo.setBankName(StringUtils.add(bankInfo.getBankName(), data));
				}
				else if (tagName.equals("bankType"))
				{
					bankInfo.setBankType(StringUtils.add(bankInfo.getBankType(), data));
				}
				else if (tagName.equals("bankOutlets"))
				{
					bankInfo.setBankOutlets(StringUtils.add(bankInfo.getBankOutlets(), data));
				}
				else if (tagName.equals("bankNumber"))
				{
					bankInfo.setBankNumber(StringUtils.add(bankInfo.getBankNumber(), data));
				}
				else if (tagName.equals("mobile"))
				{
					bankInfo.setMobile(StringUtils.add(bankInfo.getMobile(), data));
				}
				else if (tagName.equals("cityFlag"))
				{
					bankInfo.setCityFlag(StringUtils.add(bankInfo.getCityFlag(), data));
				}
				else if (tagName.equals("priorityFlag"))
				{
					bankInfo.setPriorityFlag(StringUtils.add(bankInfo.getPriorityFlag(), data));
				}
				else if (tagName.equals("purpose"))
				{
					bankInfo.setPurpose(StringUtils.add(bankInfo.getPurpose(), data));
				}

				// 2014-05-29 新增
				else if (tagName.equals("operator"))
				{
					deflossOptionData.setOperator(StringUtils.add(deflossOptionData.getOperator(), data));
				}
				else if (tagName.equals("opinion"))
				{
					deflossOptionData.setOpinion(StringUtils.add(deflossOptionData.getOpinion(), data));
				}
				else if (tagName.equals("inputDate"))
				{
					deflossOptionData.setInputDate(StringUtils.add(deflossOptionData.getInputDate(), data));
				}

				// 共有
				else if (tagName.equals("responseType"))
				{
					responseType = data;
				}
				else if (tagName.equals("responseCode"))
				{
					responseCode = data;
				}
				if ("0".equals(responseCode) && tagName.equals("errorMessage"))
				{
					errorMessage = data;
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			super.endElement(uri, localName, qName);
			tagName = localName.length() != 0 ? localName : qName;
			if (tagName.equals("lossVehicle"))
			{
				lossVehicle.setLossVehicleInsuranceList(vehicleInsurList);
			}
			else if (tagName.equals("carPolicyData"))
			{
				vehicleInsurList.add(lossVehicleInsur);
			}
			else if (tagName.equals("lossFitInfo"))
			{
				fitInfoList.add(lossFitInfo);
			}
			else if (tagName.equals("lossRepairInfo"))
			{
				repairInfoList.add(lossRepairInfo);
			}
			else if (tagName.equals("lossAssistInfo"))
			{
				assistInfoList.add(lossAssistInfo);
			}
			else if (tagName.equals("kindCodeData"))
			{
				kindCodeDataList.add(kindCodeData);
			}
			else if (tagName.equals("registThirdCarData"))
			{
				registThirdCarDatas.add(registThirdCarData);
			}
			else if (tagName.equals("policyData"))
			{
				policyDatas.add(policyData);
			}
			else if (tagName.equals("policyDetailData"))
			{
				policyData.setPolicyDetailData(policyDetailData);
			}
			else if (tagName.equals("policyKindData"))
			{
				policyKindDatas.add(policyKindData);
			}
			else if (tagName.equals("policyKindDatas"))
			{
				policyData.setPolicyKindDataList(policyKindDatas);
			}
			else if (tagName.equals("policyDriverData"))
			{
				policyDriverDatas.add(policyDriverData);
			}
			else if (tagName.equals("policyDriverDatas"))
			{
				policyData.setPolicyDriverDataList(policyDriverDatas);
			}
			else if (tagName.equals("policyEngageData"))
			{
				policyEngageDatas.add(policyEngageData);
			}
			else if (tagName.equals("policyEngageDatas"))
			{
				policyData.setPolicyEngageDataList(policyEngageDatas);
			}
			else if (tagName.equals("deflossOptionData"))
			{
				deflossOptionDatas.add(deflossOptionData);
			}

			tagName = null;
		}

	}

	public DeflossData getDeflossData()
	{

		// 设置保单信息
		if (policyDatas != null && !policyDatas.isEmpty())
		{
			deflossData.setPolicyDatas(policyDatas);// 保单信息
		}

		if (null != lossVehicle)
		{
			deflossData.setLossVehicle(lossVehicle);
		}
		if (null != assistInfoList && !assistInfoList.isEmpty())
		{
			deflossData.setAssistInfoList(assistInfoList);
		}
		if (null != basicVehicle)
		{
			if (null != lossVehicle && StringUtils.isNotEmpty(lossVehicle.getLicenseNo()))
			{
				basicVehicle.setPlateNo(lossVehicle.getLicenseNo());
			}
			deflossData.setBasicVehicle(basicVehicle);
		}
		if (null != fitInfoList && !fitInfoList.isEmpty())
		{
			deflossData.setFitInfoList(fitInfoList);
		}
		if (null != repairInfoList && !repairInfoList.isEmpty())
		{
			deflossData.setRepairInfoList(repairInfoList);
		}
		if (null != kindCodeDataList && !kindCodeDataList.isEmpty())
		{
			deflossData.setKindCodeDataList(kindCodeDataList);
		}
		if (null != registThirdCarDatas)
		{
			deflossData.setRegistThirdCarDatas(registThirdCarDatas);
		}
		if (null != bankInfo)
		{
			bankInfo.setTask_id(taskId);
			deflossData.setBankInfo(bankInfo);
		}
		if (null != deflossOptionDatas)
		{
			deflossData.setHistoryOption(deflossOptionDatas);
		}
		return deflossData;
	}

	public InsuredData getInsuredData()
	{
		return insuredData;
	}

}
