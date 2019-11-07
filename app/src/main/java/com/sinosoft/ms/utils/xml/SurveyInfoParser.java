package com.sinosoft.ms.utils.xml;

/**
 * 系统名：移动查勘
 * 子系统名：
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午2:07:52
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.CarDriverData;
import com.sinosoft.ms.model.CarLossData;
import com.sinosoft.ms.model.CarPolicyData;
import com.sinosoft.ms.model.KindCodeData2;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PersonDetailData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PolicyDetailData;
import com.sinosoft.ms.model.PolicyDriverData;
import com.sinosoft.ms.model.PolicyEngageData;
import com.sinosoft.ms.model.PolicyKindData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.PropDetailData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.SurveyInfo;
import com.sinosoft.ms.utils.LogFatory;
import com.sinosoft.ms.utils.LogRecoder;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：影像中心服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 14:10:24
 */
public class SurveyInfoParser {
	public String responseType;// 响应类型
	public String errorMessage;// 错误信息
	public String responseCode;// 响应代码

	private int result;
	private String tagName;// 节点名

	private SurveyInfo surveyInfo;

	private BasicSurvey basicSurvey;// 基本信息***public

	private List<LiabilityRatio> liabilityRatios;// 责任比例***public
	private LiabilityRatio liabilityRatio;// 责任比例

	private List<CarData> carDatas;// 涉案车辆 *****public
	private CarData carData;// 涉案车驾驶员信息（隶属于涉案车辆信息）
	private List<CarPolicyData> carPolicyDatas;
	private CarPolicyData carPolicyData = new CarPolicyData();
	private CarDriverData carDriverData = new CarDriverData();
	private CarLossData carLossData = new CarLossData();

	private List<PropData> propDatas;// 财产信息 ***public
	private PropData propData;
	private List<PropDetailData> propDetailDatas= new ArrayList<PropDetailData>();
	private PropDetailData propDetailData;

	private List<PersonData> personDatas;//人伤信息 ***public
	private PersonData personData;
	private List<PersonDetailData> personDetailDatas;//=new ArrayList<PersonDetailData>();
	
	private PersonDetailData personDetailData;

	private List<PolicyData> policyDatas;// 保单信息 **public
	private PolicyData policyData;
	private PolicyDetailData policyDetailData;

	private List<PolicyKindData> policyKindDatas;
	private PolicyKindData policyKindData;

	private PolicyDriverData policyDriverData;
	private List<PolicyDriverData> policyDriverDatas;
	
	private List<PolicyEngageData> policyEngageDatas;
	private PolicyEngageData policyEngageData;
	
	
	private List<RegistThirdCarData> registThirdCarDatas;
	private RegistThirdCarData registThirdCarData;

	private String tag = "";
	private List<String> tagNameList = new ArrayList<String>();
	private String registNo;
	
	private List<KindCodeData2> kindCodeDatas=new ArrayList<KindCodeData2>();
	private KindCodeData2 kindCodeData=null;

	public void parse(InputStream inputStream,String registNo)
			throws ParserConfigurationException, SAXException, IOException {
		this.registNo=registNo;
		surveyInfo = new SurveyInfo();
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); // 調用靜態方法newInstance得到SAXParseFactory實例
		SAXParser saxParser = saxParserFactory.newSAXParser(); // 調用newSAXParser創建SAXParser對象
		XMLReader xmlReader = saxParser.getXMLReader(); // 獲得XMLReader對象
		xmlReader.setContentHandler(new SurveyInfoFeedbackHandler()); // 設置處理XML的Handler
		xmlReader.parse(new InputSource(inputStream)); // 將InputStream裝飾為InputSource，進行解析
	}

	private class SurveyInfoFeedbackHandler extends DefaultHandler {
		// 只调用一次 初始化list集合
		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if (tagName != null) {
				tagName = tagName.trim();

				if (tagName.equals("basicSurvey")) {
					tagNameList.add(tagName);
				} else if (tagName.equals("liabilityRatio")) {
					tagNameList.add(tagName);
				} else if (tagName.equals("carData")) {
					tagNameList.add(tagName);
				} else if (tagName.equals("carPolicyData")) {

					tagNameList.add(tagName);
				} else if (tagName.equals("carDriverData")) {
					tagNameList.add(tagName);
				} else if (tagName.equals("carLossData")) {
					tagNameList.add(tagName);
				} else if (tagName.equals("propData")) {
					tagNameList.add(tagName);
				} 
				
				else if (tagName.equals("propDetailData")) {
					tagNameList.add(tagName);
				}
				
				else if (tagName.equals("personData")) {
					tagNameList.add(tagName);
				} 
				
				else if (tagName.equals("personDetailData")) {
					tagNameList.add(tagName);
				}				///else if (tagName.equals("personDetailDatas")) {
				///	tagNameList.add(tagName);
				///}
				
				else if (tagName.equals("policyData")) {
					tagNameList.add(tagName);

				} else if (tagName.equals("policyDetailData")) {
					tagNameList.add(tagName);
					
				} 
				
				else if (tagName.equals("policyKindData")) {
					tagNameList.add(tagName);
				}
				
				else if (tagName.equals("policyDriverData")) {
					tagNameList.add(tagName);

				}else if(tagName.equals("policyEngageData")){
					tagNameList.add(tagName);
					
				}else if(tagName.equals("registThirdCarData")){
					tagNameList.add(tagName);
					
				}else if(tagName.equals("kindCodeDatas")){
					tagNameList.add(tagName);
					
				}
				
				

				if (tagName.equals("basicSurvey")) {// 基本信息
					basicSurvey = new BasicSurvey();

				} else if (tagName.equals("liabilityRatios")) {// 责任比例
					liabilityRatios = new ArrayList<LiabilityRatio>();

				} else if (tagName.equals("liabilityRatio")) {// 责任比例
					liabilityRatio = new LiabilityRatio();
					liabilityRatio.setRegistNo(registNo);
					//liabilityRatio.setR

				} else if (tagName.equals("carDatas")) {// 涉案车辆
					carDatas = new ArrayList<CarData>();

				} else if (tagName.equals("carData")) {//
					carData = new CarData();
					carData.setRegistNo(registNo);

				} else if (tagName.equals("carDriverData")) {// carDriverData
					carDriverData = new CarDriverData();

				} else if (tagName.equals("propDatas")) {// propDatas
					propDatas = new ArrayList<PropData>();

				} else if (tagName.equals("propData")) {
					propData = new PropData();
					propData.setRegistNo(registNo);

				} else if (tagName.equals("propDetailDatas")) {
					propDetailDatas = new ArrayList<PropDetailData>();
				} else if (tagName.equals("propDetailData")) {
					propDetailData = new PropDetailData();

				} 
				
				else if (tagName.equals("personDatas"))
				{
					 personDatas = new ArrayList<PersonData>();
				} 
				
				else if (tagName.equals("personData"))
				{
					personData = new PersonData();
					personData.setRegistNo(registNo);

				} 
				
				else if (tagName.equals("personDetailDatas")) {// personDetailDatas
					personDetailDatas = new ArrayList<PersonDetailData>();
				} 
				
				else if (tagName.equals("personDetailData")) {
					personDetailData = new PersonDetailData();
				}
				
				
				else if (tagName.equals("policyKindDatas")) 
				{
					policyKindDatas = new ArrayList<PolicyKindData>();
				}
				
				else if (tagName.equals("policyKindData")) 
				{
					policyKindData = new PolicyKindData();
				} 
				
				else if(tagName.equals("policyEngageDatas")){
					policyEngageDatas=new ArrayList<PolicyEngageData>();
					
				}else if(tagName.equals("policyEngageData")){
					policyEngageData=new PolicyEngageData();
					
				}
				
				
				else if (tagName.equals("carLossData")) {// carLossData
					carLossData = new CarLossData();

				} else if (tagName.equals("policyDatas")) {// policyDatas
					policyDatas = new ArrayList<PolicyData>();
				} else if (tagName.equals("policyData")) {
					policyData = new PolicyData();
					policyData.setRegistNo(registNo);
				} else if (tagName.equals("policyDetailData")) {
					policyDetailData = new PolicyDetailData();
				} else if (tagName.equals("carPolicyDatas")) {// 列表
					carPolicyDatas = new ArrayList<CarPolicyData>();

				} else if (tagName.equals("carPolicyData")) {//
					carPolicyData = new CarPolicyData();
					carPolicyData.setCheckCarId("1");

				} else if (tagName.equals("policyDriverDatas")) {
					policyDriverDatas = new ArrayList<PolicyDriverData>();
				} else if (tagName.equals("policyDriverData")) {
					policyDriverData = new PolicyDriverData();
				}else if (tagName.equals("registThirdCarDatas")) {
					registThirdCarDatas=new ArrayList<RegistThirdCarData>();

				}
				else if(tagName.equals("registThirdCarData")){
					registThirdCarData=new RegistThirdCarData();
					registThirdCarData.setRegistNo(registNo);
					registThirdCarData.setType("0");
					
				}
				else if(tagName.equals("kindCodeDatas")){
					kindCodeDatas=new ArrayList<KindCodeData2>();
				}else if(tagName.equals("kindCodeData")){
					kindCodeData=new KindCodeData2();
					kindCodeData.setCarId(registNo);
				}
				
			}
		}
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			String date = new String(ch, start, length);
			if (tagNameList != null && !tagNameList.isEmpty()) {
				tag = tagNameList.get(tagNameList.size() - 1);
			}
			if (null != tagName && null != date) {
				date = date.trim();
				if (tagName.equals("responseType")) {
					responseType = date;
				} else if (tagName.equals("responseCode")) {
					responseCode = date;
					result = StringUtils.toInt(responseCode);// Integer.parseInt(responseCode);
				}
				if (tag.equals("basicSurvey")) {
					setBasicInfo(date);
				} else if (tag.equals("liabilityRatio")) {
					setLiabilityRatio(date);
				} else if (tag.equals("carData")) {
					setCarData(date);
				} else if (tag.equals("carPolicyData")) {
					setCarPolicyData(date);
				} else if (tag.equals("carDriverData")) {
					setCarDriverDate(date);
				} else if (tag.equals("carLossData")) {
					setCarLossData(date);
				} else if (tag.equals("propData")) {
					setPropData(date);
				} else if (tag.equals("propDetailData")) {
					if (tagName.equals("lossItemName")) {
						propDetailData.setLossItemName(StringUtils.add(propDetailData.getLossItemName(), date));
					} else if (tagName.equals("lossFee")) {
						propDetailData.setLossFee(StringUtils.add(propDetailData.getLossFee(), date));
					} else if (tagName.equals("lossDegreeCode")) {
						propDetailData.setLossDegreeCode(StringUtils.add(propDetailData.getLossDegreeCode(), date));
					} else if (tagName.equals("lossSpeciesCode")) {
						propDetailData.setLossSpeciesCode(StringUtils.add(propDetailData.getLossSpeciesCode(), date));
					}
				} else if (tag.equals("personData")) {
					if (tagName.equals("personDataId")) {
						personData.setPersonDataId(StringUtils.add(personData.getPersonDataId(), date));
					} else if (tagName.equals("lossParty")) {
						personData.setLossParty(StringUtils.add(personData.getLossParty(), date));
					} else if (tagName.equals("severeNum")) {
						personData.setSevereNum(StringUtils.add(personData.getSevereNum(), date));
					} else if (tagName.equals("minor")) {
						personData.setMinor(StringUtils.add(personData.getMinor(), date));
					} else if (tagName.equals("deathNum")) {
						personData.setDeathNum(StringUtils.add(personData.getDeathNum(), date));
					} else if (tagName.equals("sumLossFee")) {
						personData.setSumLossFee(StringUtils.add(personData.getSumLossFee(), date));
					} else if (tagName.equals("rescueFee")) {
						personData.setRescueFee(StringUtils.add(personData.getRescueFee(), date));
					} else if (tagName.equals("checkDate")) {
						personData.setCheckDate(StringUtils.add(personData.getCheckDate(), date));
					} else if (tagName.equals("checkSite")) {
						personData.setCheckSite(StringUtils.add(personData.getCheckSite(), date));
					} else if (tagName.equals("rescueContext")) {
						personData.setRescueContext(StringUtils.add(personData.getRescueContext(), date));
					}
					 
					else if (tagName.equals("createOwner")) 
					{
						personData.setCreateOwner(StringUtils.add(personData.getCreateOwner(), date));
					}
					else if (tagName.equals("modfiyOwner")) 
					{
						personData.setModfiyOwner(StringUtils.add(personData.getModfiyOwner(), date));
					}
				}
				else if (tag.equals("personDetailData")) {
					if (tagName.equals("personName")) {
						personDetailData.setPersonName(StringUtils.add(personDetailData.getPersonName(), date));
					} else if (tagName.equals("personSex")) {
						personDetailData.setPersonSex(StringUtils.add(personDetailData.getPersonSex(), date));
					} else if (tagName.equals("age")) {
						personDetailData.setAge(StringUtils.add(personDetailData.getAge(), date));
					} else if (tagName.equals("personPayType")) {
						personDetailData.setPersonPayType(StringUtils.add(personDetailData.getPersonPayType(), date));
					} else if (tagName.equals("treatType")) {
						personDetailData.setTreatType(StringUtils.add(personDetailData.getTreatType(), date));
					} else if (tagName.equals("hospitalName")) {
						personDetailData.setHospitalName(StringUtils.add(personDetailData.getHospitalName(), date));
					} else if (tagName.equals("lossFee")) {
						personDetailData.setLossFee(StringUtils.add(personDetailData.getLossFee(), date));
					} else if (tagName.equals("woundDetail")) {
						personDetailData.setWoundDetail(StringUtils.add(personDetailData.getWoundDetail(), date));
					}
				}
/*				else if (tag.equals("personDetailDatas")) {
					if (tagName.equals("personDetailDatas")) {
						personDetailDatas = new ArrayList<PersonDetailData>();
					} else if (tagName.equals("personName")) {
						personDetailData.setPersonName(StringUtils.add(personDetailData.getPersonName(), date));
					} else if (tagName.equals("personSex")) {
						personDetailData.setPersonSex(StringUtils.add(personDetailData.getPersonSex(), date));
					} else if (tagName.equals("age")) {
						personDetailData.setAge(StringUtils.add(personDetailData.getAge(), date));
					} else if (tagName.equals("personPayType")) {
						personDetailData.setPersonPayType(StringUtils.add(personDetailData.getPersonPayType(), date));
					} else if (tagName.equals("treatType")) {
						personDetailData.setTreatType(StringUtils.add(personDetailData.getTreatType(), date));
					} else if (tagName.equals("hospitalName")) {
						personDetailData.setHospitalName(StringUtils.add(personDetailData.getHospitalName(), date));
					} else if (tagName.equals("lossFee")) {
						personDetailData.setLossFee(StringUtils.add(personDetailData.getLossFee(), date));
					} else if (tagName.equals("woundDetail")) {
						personDetailData.setWoundDetail(StringUtils.add(personDetailData.getWoundDetail(), date));
					}
				}*/
				else if (tag.equals("policyData")) {
					if (tagName.equals("policyNo")) {
						policyData.setPolicyNo(StringUtils.add(policyData.getPolicyNo(), date));
					} else if (tagName.equals("insuredName")) {
						policyData.setInsuredName(StringUtils.add(policyData.getInsuredName(), date));
					} else if (tagName.equals("comCName")) {
						policyData.setComCName(StringUtils.add(policyData.getComCName(), date));
					} else if (tagName.equals("startDate")) {
						policyData.setStartDate(StringUtils.add(policyData.getStartDate(), date));
					} else if (tagName.equals("endDate")) {
						policyData.setEndDate(StringUtils.add(policyData.getEndDate(), date));
					} else if (tagName.equals("riskCode")) {
						policyData.setRiskCode(StringUtils.add(policyData.getRiskCode(), date));
					}

				} else if (tag.equals("policyDetailData")) {
					if (tagName.equals("policyNo")) {
						policyDetailData.setPolicyNo(StringUtils.add(policyDetailData.getPolicyNo(), date));
					} else if (tagName.equals("riskCode")) {
						policyDetailData.setRiskCode(StringUtils.add(policyDetailData.getRiskCode(), date));
					} else if (tagName.equals("insuredName")) {
						policyDetailData.setInsuredName(StringUtils.add(policyDetailData.getInsuredName(), date));
					} else if (tagName.equals("licenseNo")) {
						policyDetailData.setLicenseNo(StringUtils.add(policyDetailData.getLicenseNo(), date));
					} else if (tagName.equals("licenseColor")) {
						policyDetailData.setLicenseColor(StringUtils.add(policyDetailData.getLicenseColor(), date));
					} else if (tagName.equals("brandName")) {
						policyDetailData.setBrandName(StringUtils.add(policyDetailData.getBrandName(), date));
					} else if (tagName.equals("purchasePrice")) {
						policyDetailData.setPurchasePrice(StringUtils.add(policyDetailData.getPurchasePrice(), date));
					} else if (tagName.equals("startDate")) {
						policyDetailData.setStartDate(StringUtils.add(policyDetailData.getStartDate(), date));
					} else if (tagName.equals("endDate")) {
						policyDetailData.setEndDate(StringUtils.add(policyDetailData.getEndDate(), date));
					} else if (tagName.equals("clauseType")) {
						policyDetailData.setClauseType(StringUtils.add(policyDetailData.getClauseType(), date));
					} else if (tagName.equals("carKind")) {
						policyDetailData.setCarKind(StringUtils.add(policyDetailData.getCarKind(), date));
					} else if (tagName.equals("carOwner")) {
						policyDetailData.setCarOwner(StringUtils.add(policyDetailData.getCarOwner(), date));
					} else if (tagName.equals("engineNo")) {
						policyDetailData.setEngineNo(StringUtils.add(policyDetailData.getEngineNo(), date));
					} else if (tagName.equals("frameNo")) {
						policyDetailData.setFrameNo(StringUtils.add(policyDetailData.getFrameNo(), date));
					} else if (tagName.equals("vinNo")) {
						policyDetailData.setVinNo(StringUtils.add(policyDetailData.getVinNo(), date));
					} else if (tagName.equals("runArea")) {
						policyDetailData.setRunArea(StringUtils.add(policyDetailData.getRunArea(), date));
					} else if (tagName.equals("enrollDate")) {
						policyDetailData.setEnrollDate(StringUtils.add(policyDetailData.getEnrollDate(), date));
					} else if (tagName.equals("seatCount")) {
						policyDetailData.setSeatCount(StringUtils.add(policyDetailData.getSeatCount(), date));
					} else if (tagName.equals("useNature")) {
						policyDetailData.setUseNature(StringUtils.add(policyDetailData.getUseNature(), date));
					} else if (tagName.equals("colorCode")) {
						policyDetailData.setColorCode(StringUtils.add(policyDetailData.getColorCode(), date));
					} else if (tagName.equals("endorseTimes")) {
						policyDetailData.setEndorseTimes(StringUtils.add(policyDetailData.getEndorseTimes(), date));
					} else if (tagName.equals("comCName")) {
						policyDetailData.setComCName(StringUtils.add(policyDetailData.getComCName(), date));
					} else if (tagName.equals("m2Flag")) {
						policyDetailData.setM2Flag(StringUtils.add(policyDetailData.getM2Flag(), date));
					} else if (tagName.equals("claimTimes")) {
						policyDetailData.setClaimTimes(StringUtils.add(policyDetailData.getClaimTimes(), date));
					} else if (tagName.equals("identifyNumber")) {
						policyDetailData.setIdentifyNumber(StringUtils.add(policyDetailData.getIdentifyNumber(), date));
					} else if (tagName.equals("vipType")) {
						policyDetailData.setVipType(StringUtils.add(policyDetailData.getVipType(), date));
					}

				} else if (tag.equals("policyKindData")) {
					if (tagName.equals("kindName")) {
						policyKindData.setKindName(StringUtils.add(policyKindData.getKindName(), date));
					} else if (tagName.equals("amount")) {
						policyKindData.setAmount(StringUtils.add(policyKindData.getAmount(), date));
					} else if (tagName.equals("remark")) {
						policyKindData.setRemark(StringUtils.add(policyKindData.getRemark(), date));
					}

				} else if (tag.equals("policyDriverData")) {
					if (tagName.equals("driverName")) {
						policyDriverData.setDriverName(StringUtils.add(policyDriverData.getDriverName(), date));
					} else if (tagName.equals("sex")) {
						policyDriverData.setSex(StringUtils.add(policyDriverData.getSex(), date));
					} else if (tagName.equals("identifyNumber")) {
						policyDriverData.setIdentifyNumber(StringUtils.add(policyDriverData.getIdentifyNumber(), date));
					} else if (tagName.equals("drivingLicenseNo")) {
						policyDriverData.setDrivingLicenseNo(StringUtils.add(policyDriverData.getDrivingLicenseNo(), date));
					} else if (tagName.equals("acceptLicenseDate")) {
						policyDriverData.setAcceptLicenseDate(StringUtils.add(policyDriverData.getAcceptLicenseDate(), date));
					}

				}else if(tag.equals("policyEngageData")){
					if (tagName.equals("clauseCode")) {
						policyEngageData.setClauseCode(StringUtils.add(policyEngageData.getClauseCode(), date));
					} else if (tagName.equals("clauses")) {
						policyEngageData.setClauses(StringUtils.add(policyEngageData.getClauses(), date));
					} 
				}
				else if(tag.equals("registThirdCarData")){
					if (tagName.equals("licenseNo")) {
						registThirdCarData.setLicenseNo(StringUtils.add(registThirdCarData.getLicenseNo(), date));
					} else if (tagName.equals("company")) {
						registThirdCarData.setCompany(StringUtils.add(registThirdCarData.getCompany(), date));
					}  else if (tagName.equals("brandName")) {
						registThirdCarData.setBrandName(StringUtils.add(registThirdCarData.getBrandName(), date));
					}  else if (tagName.equals("thirdPolicyNo")) {
						registThirdCarData.setThirdPolicyNo(StringUtils.add(registThirdCarData.getThirdPolicyNo(), date));
					} 

				}else if(tag.equals("kindCodeDatas")){
					if (tagName.equals("insureTerm")) {
						kindCodeData.setInsureTerm(StringUtils.add(kindCodeData.getInsureTerm(), date));
					} else if (tagName.equals("insureTermCode")) {
						kindCodeData.setInsureTermCode(StringUtils.add(kindCodeData.getInsureTermCode(), date));
					}  
				}
				
				if ("0".equals(responseCode) && tagName.equals("errorMessage")) {
					errorMessage = date;
					LogFatory.d("login", "" + tagName + ":" + date);
				}

			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {

			Log.e("end name", qName + "");
			if (qName != null) {
				String tagName = qName.trim();
				if (!tagNameList.isEmpty()) {
					if (tagName.equals("basicSurvey")) {
						tagNameList.remove(tagNameList.size() - 1);
					} else if (tagName.equals("liabilityRatio")) {
						tagNameList.remove(tagNameList.size() - 1);
					} else if (tagName.equals("carData")) {
						tagNameList.remove(tagNameList.size() - 1);
					} else if (tagName.equals("carPolicyData")) {
						tagNameList.remove(tagNameList.size() - 1);

					} else if (tagName.equals("carDriverData")) {
						tagNameList.remove(tagNameList.size() - 1);
					} else if (tagName.equals("carLossData")) {
						tagNameList.remove(tagNameList.size() - 1);
					} else if (tagName.equals("propData")) {
						tagNameList.remove(tagNameList.size() - 1);
					} 
					
					else if (tagName.equals("propDetailData")) 
					{
						tagNameList.remove(tagNameList.size() - 1);
					}
					
					else if (tagName.equals("personData")) 
					{
						tagNameList.remove(tagNameList.size() - 1);
					} 
					else if (tagName.equals("personDetailData")) {
						tagNameList.remove(tagNameList.size() - 1);
					}					
					///else if (tagName.equals("personDetailDatas")) {
					///	tagNameList.remove(tagNameList.size() - 1);
					///} 
					
					else if (tagName.equals("policyData")) {

						tagNameList.remove(tagNameList.size() - 1);
					} else if (tagName.equals("policyDetailData")) {
						tagNameList.remove(tagNameList.size() - 1);
					} 
					else if (tagName.equals("policyKindData")) 
					{
						tagNameList.remove(tagNameList.size() - 1);
					} 
					
					else if (tagName.equals("policyDriverData")) {
						tagNameList.remove(tagNameList.size() - 1);

					}else if(tagName.equals("policyEngageData")){
						tagNameList.remove(tagNameList.size() - 1);
						
					}else if(tagName.equals("registThirdCarData")){
						tagNameList.remove(tagNameList.size() - 1);
						
					}else if(tagName.equals("kindCodeDatas")){
						tagNameList.remove(tagNameList.size() - 1);
					}
				}

				try {

					if (tagName.equals("liabilityRatio")) {
						liabilityRatios.add(liabilityRatio);

					} else if (tagName.equals("carData")) {
						carDatas.add(carData);

					} else if (tagName.equals("carPolicyData")) {
						carPolicyDatas.add(carPolicyData);

					} else if (tagName.equals("carPolicyDatas")) {
						carData.setCarpolicyList(carPolicyDatas);

					} else if (tagName.equals("carDriverData")) {
						carData.setCarDriverData(carDriverData);
					} else if (tagName.equals("carLossData")) {
						carData.setCarLossData(carLossData);
					} else if (tagName.equals("propData")) {
						propDatas.add(propData);
					} else if (tagName.equals("propDetailData")) {
						propDetailDatas.add(propDetailData);
						propData.setPropDetailDatas(propDetailDatas);
						
					} else if (tagName.equals("propDetailDatas")) {
						propData.setPropDetailDatas(propDetailDatas);
					} else if (tagName.equals("personData")) {
						 personDatas.add(personData);
					} 
					
					else if (tagName.equals("personDetailData")) {
						personDetailDatas.add(personDetailData);
					} 
					else if (tagName.equals("personDetailDatas")) {
						personData.setPersonDetailData(personDetailDatas);
					} 
					
					else if (tagName.equals("policyData")) {
						policyDatas.add(policyData);
					} else if (tagName.equals("policyDetailData")) {
						policyData.setPolicyDetailData(policyDetailData);
					}
					
					else if (tagName.equals("policyKindData")) {
						policyKindDatas.add(policyKindData);
					} 
					else if (tagName.equals("policyKindDatas")) {
						policyData.setPolicyKindDataList(policyKindDatas);
					}
					
					else if (tagName.equals("policyDriverData")) {
						policyDriverDatas.add(policyDriverData);
						
					}else if (tagName.equals("policyDriverDatas")) {
						policyData.setPolicyDriverDataList(policyDriverDatas);
						
					}else if (tagName.equals("policyEngageData")) {
						policyEngageDatas.add(policyEngageData);
					}else if (tagName.equals("policyEngageDatas")) {
						policyData.setPolicyEngageDataList(policyEngageDatas);
					}
					else if (tagName.equals("registThirdCarDatas")) {
						registThirdCarDatas.add(registThirdCarData);
					}else if (tagName.equals("kindCodeData")) {
						kindCodeDatas.add(kindCodeData);
					}	
					
					
				} catch (Exception e) {
					e.printStackTrace();

				}

			}

			tagName = null;
		}
	}

	
	/**
	 * 解析基本信息
	 * 
	 * @param date
	 */
	public void setBasicInfo(String date) {
		if (tagName.equals("registId")) {
			basicSurvey.setId(StringUtils.add(basicSurvey.getId(), date));
		}else if (tagName.equals("registNo")) {
			basicSurvey.setRegistNo(StringUtils.add(basicSurvey.getRegistNo(), date));

		} else if (tagName.equals("taskId")) {
			basicSurvey.setTask_id(StringUtils.add(basicSurvey.getTask_id(), date));

		} else if (tagName.equals("subrogationFlag")) {
			basicSurvey.setSubrogationFlag(StringUtils.add(basicSurvey.getSubrogationFlag(), date));

		} else if (tagName.equals("claimSignFlag")) {
			basicSurvey.setClaimSignFlag(StringUtils.add(basicSurvey.getClaimSignFlag(), date));

		} else if (tagName.equals("damageCode")) {
			basicSurvey.setDamageCode(StringUtils.add(basicSurvey.getDamageCode(), date));

		} else if (tagName.equals("checkSite")) {
			basicSurvey.setCheckSite(StringUtils.add(basicSurvey.getCheckSite(), date));

		} else if (tagName.equals("checkDate")) {
			basicSurvey.setCheckDate(StringUtils.add(basicSurvey.getCheckDate(), date));

		} else if (tagName.equals("checker1")) {
			basicSurvey.setChecker1(StringUtils.add(basicSurvey.getChecker1(), date));

		} else if (tagName.equals("checkerName")) {
			basicSurvey.setCheckerName(StringUtils.add(basicSurvey.getCheckerName(), date));

		} else if (tagName.equals("caseFlag")) {
			basicSurvey.setCaseFlag(StringUtils.add(basicSurvey.getCaseFlag(), date));

		} else if (tagName.equals("damageAddress")) {
			basicSurvey.setDamageAddress(StringUtils.add(basicSurvey.getDamageAddress(), date));

		} else if (tagName.equals("intermediaryFlag")) {
			basicSurvey.setIntermediaryFlag(StringUtils.add(basicSurvey.getIntermediaryFlag(), date));

		} else if (tagName.equals("intermediaryCode")) {
			basicSurvey.setIntermediaryCode(StringUtils.add(basicSurvey.getIntermediaryCode(), date));

		} else if (tagName.equals("intermediaryName")) {
			basicSurvey.setIntermediaryName(StringUtils.add(basicSurvey.getIntermediaryName(), date));

		} else if (tagName.equals("manageType")) {
			basicSurvey.setManageType(StringUtils.add(basicSurvey.getManageType(), date));

		} else if (tagName.equals("lossType")) {
			basicSurvey.setLossType(StringUtils.add(basicSurvey.getLossType(), date));

		} else if (tagName.equals("damageCaseCode")) {
			basicSurvey.setDamageCaseCode(StringUtils.add(basicSurvey.getDamageCaseCode(), date));

		} else if (tagName.equals("firstSiteFlag")) {
			basicSurvey.setFirstSiteFlag(StringUtils.add(basicSurvey.getFirstSiteFlag(), date));

		} else if (tagName.equals("solutionUnit")) {
			basicSurvey.setSolutionUnit(StringUtils.add(basicSurvey.getSolutionUnit(), date));

		} else if (tagName.equals("riskCode")) {
			basicSurvey.setRiskCode(StringUtils.add(basicSurvey.getRiskCode(), date));

		} else if (tagName.equals("opinion")) {

			basicSurvey.setOpinion(StringUtils.add(basicSurvey.getOpinion(), date));

		}else if (tagName.equals("enabledSubrPlatform")) {

			basicSurvey.setEnabledSubrPlatform(StringUtils.add(basicSurvey.getEnabledSubrPlatform(), date));

		}else if (tagName.equals("isKindCodeA")) {

			basicSurvey.setIsKindCodeA(StringUtils.add(basicSurvey.getIsKindCodeA(), date));

		}else if(tagName.equals("twoAvoidFlag")){
			basicSurvey.setTwoAvoidFlag(StringUtils.add(basicSurvey.getTwoAvoidFlag(), date));
		}

	}

	/**
	 * 责任比例
	 * 
	 * @param date
	 */
	public void setLiabilityRatio(String date) {
		if (tagName.equals("flag")) {
			liabilityRatio.setFlag(StringUtils.add(liabilityRatio.getFlag(), date));
			Log.e("the flag ",date);

		} else if (tagName.equals("riskCode")) {//
			liabilityRatio.setRiskCode(StringUtils.add(liabilityRatio.getRiskCode(), date));

		} else if (tagName.equals("policyFlag")) {//
			liabilityRatio.setPolicyFlag(StringUtils.add(liabilityRatio.getPolicyFlag(), date));

		} else if (tagName.equals("policyNo")) {//
			liabilityRatio.setPolicyNo(StringUtils.add(liabilityRatio.getPolicyNo(), date));

		} else if (tagName.equals("claimFlag")) {//
			liabilityRatio.setClaimFlag(StringUtils.add(liabilityRatio.getClaimFlag(), date));

		} else if (tagName.equals("indemnityDuty")) {//
			liabilityRatio.setIndemnityDuty(StringUtils.add(liabilityRatio.getIndemnityDuty(), date));

		} else if (tagName.equals("indemnityDutyRate")) {//
			liabilityRatio.setIndemnityDutyRate(StringUtils.add(liabilityRatio.getIndemnityDutyRate(), date));

		} else if (tagName.equals("cIIndemDuty")) {//
			liabilityRatio.setcIIndemDuty(StringUtils.add(liabilityRatio.getcIIndemDuty(), date));

		} else if (tagName.equals("cIDutyFlag")) {//
			liabilityRatio.setcIDutyFlag(StringUtils.add(liabilityRatio.getcIDutyFlag(), date));

		}

	}
    /**
     * 解析车辆信息
     * @param date
     */
	public void setCarData(String date) {
		if (tagName.equals("checkCarId")) {//
			carData.setCheckCarId(StringUtils.add(carData.getCheckCarId(), date));

		} else if (tagName.equals("lossItemType")) {//
			carData.setLossItemType(StringUtils.add(carData.getLossItemType(), date));

		} else if (tagName.equals("licenseNo")) {//
			carData.setLicenseNo(StringUtils.add(carData.getLicenseNo(), date));

		} else if (tagName.equals("carOwner")) {//
			carData.setCarOwner(StringUtils.add(carData.getCarOwner(), date));

		} else if (tagName.equals("engineNo")) {//
			carData.setEngineNo(StringUtils.add(carData.getEngineNo(), date));

		} else if (tagName.equals("vinNo")) {//
			carData.setVinNo(StringUtils.add(carData.getVinNo(), date));

		} else if (tagName.equals("frameNo")) {//
			carData.setFrameNo(StringUtils.add(carData.getFrameNo(), date));

		}else if (tagName.equals("licenseType")) {//
			carData.setLicenseType(StringUtils.add(carData.getLicenseType(), date));

		} else if (tagName.equals("liabilityType")) {//
			carData.setLiabilityType(StringUtils.add(carData.getLiabilityType(), date));

		}

	}
    /**
     * 车辆保单
     * @param date
     */
	public void setCarPolicyData(String date) {
		if (tagName.equals("policyFlag")) {//
			carPolicyData.setPolicyFlag(StringUtils.add(carPolicyData.getPolicyFlag(), date));
		} else if (tagName.equals("oppRegistNoBI")) {
			carPolicyData.setOppRegistNoBI(StringUtils.add(carPolicyData.getOppRegistNoBI(), date));
		} else if (tagName.equals("oppPolicyNoBI")) {
			carPolicyData.setOppPolicyNoBI(StringUtils.add(carPolicyData.getOppPolicyNoBI(), date));
		} else if (tagName.equals("oppInsurerCodeBI")) {
			carPolicyData.setOppInsurerCodeBI(StringUtils.add(carPolicyData.getOppInsurerCodeBI(), date));
		} else if (tagName.equals("oppInsurerAreaBI")) {
			carPolicyData.setOppInsurerAreaBI(StringUtils.add(carPolicyData.getOppInsurerAreaBI(), date));
		} else if (tagName.equals("oppRegistNoCI")) {
			carPolicyData.setOppRegistNoCI(StringUtils.add(carPolicyData.getOppRegistNoCI(), date));
		} else if (tagName.equals("oppPolicyNoCI")) {
			carPolicyData.setOppPolicyNoCI(StringUtils.add(carPolicyData.getOppPolicyNoCI(), date));
		} else if (tagName.equals("oppInsurerCodeCI")) {
			carPolicyData.setOppInsurerCodeCI(StringUtils.add(carPolicyData.getOppInsurerCodeCI(), date));
		} else if (tagName.equals("oppInsurerAreaCI")) {
			carPolicyData.setOppInsurerAreaCI(StringUtils.add(carPolicyData.getOppInsurerAreaCI(), date));
		}else if (tagName.equals("oppIdBi")) {
			carPolicyData.setOppIdBi(StringUtils.add(carPolicyData.getOppIdBi(), date));
		}else if (tagName.equals("oppIdCi")) {
			carPolicyData.setOppIdCi(StringUtils.add(carPolicyData.getOppIdCi(), date));
		}

	}
  /**
   * 解析驾驶员信息
   * @param date
   */
	public void setCarDriverDate(String date) {
		if (tagName.equals("checkCarId")) {
			carDriverData.setCheckCarId(StringUtils.add(carDriverData.getCheckCarId(), date));

		} else if (tagName.equals("driverName")) {
			carDriverData.setDriverName(StringUtils.add(carDriverData.getDriverName(), date));

		} else if (tagName.equals("drivingCarType")) {
			carDriverData.setDrivingCarType(StringUtils.add(carDriverData.getDrivingCarType(), date));

		} else if (tagName.equals("identifyType")) {
			carDriverData.setIdentifyType(StringUtils.add(carDriverData.getIdentifyType(), date));

		} else if (tagName.equals("identifyNumber")) {
			carDriverData.setIdentifyNumber(StringUtils.add(carDriverData.getIdentifyNumber(), date));

		} else if (tagName.equals("drivingLicenseNo")) {
			carDriverData.setDrivingLicenseNo(StringUtils.add(carDriverData.getDrivingLicenseNo(), date));

		} else if (tagName.equals("linkPhoneNumber")) {
			carDriverData.setLinkPhoneNumber(StringUtils.add(carDriverData.getLinkPhoneNumber(), date));

		}

	}
   /**
    * 解析车辆损失详情
    * @param date
    */
	public void setCarLossData(String date) {
		if (tagName.equals("damageFlag")) {
			carLossData.setDamageFlag(StringUtils.add(carLossData.getDamageFlag(), date));

		} else if (tagName.equals("reserveFlag")) {
			carLossData.setReserveFlag(StringUtils.add(carLossData.getReserveFlag(), date));

		} else if (tagName.equals("sumLossFee")) {
			carLossData.setSumLossFee(StringUtils.add(carLossData.getSumLossFee(), date));

		} else if (tagName.equals("rescueFee")) {
			carLossData.setRescueFee(StringUtils.add(carLossData.getRescueFee(), date));

		} else if (tagName.equals("checkSite")) {
			carLossData.setCheckSite(StringUtils.add(carLossData.getCheckSite(), date));

		} else if (tagName.equals("checkDate")) {
			carLossData.setCheckDate(StringUtils.add(carLossData.getCheckDate(), date));

		} else if (tagName.equals("defSite")) {
			carLossData.setDefSite(StringUtils.add(carLossData.getDefSite(), date));

		} else if (tagName.equals("kindCode")) {
			carLossData.setKindCode(StringUtils.add(carLossData.getKindCode(), date));

		} else if (tagName.equals("indemnityDuty")) {
			carLossData.setIndemnityDuty(StringUtils.add(carLossData.getIndemnityDuty(), date));

		} else if (tagName.equals("indemnityDutyRate")) {
			carLossData.setIndemnityDutyRate(StringUtils.add(carLossData.getIndemnityDutyRate(), date));

		} else if (tagName.equals("lossPart")) {
			carLossData.setLossPart(StringUtils.add(carLossData.getLossPart(), date));

		}

	}
   /**
    * 解析财产信息
    * @param date
    */
	public void setPropData(String date) {
		
		if (tagName.equals("propId")) {
			propData.setPropId(StringUtils.add(propData.getPropId(), date));
			propData.setId(date);
		}
		if (tagName.equals("checkPropId")) {
			propData.setCheckPropId(StringUtils.add(propData.getCheckPropId(), date));

		} else if (tagName.equals("lossParty")) {
			propData.setLossParty(StringUtils.add(propData.getLossParty(), date));

		} else if (tagName.equals("relatePersonName")) {
			propData.setRelatePersonName(StringUtils.add(propData.getRelatePersonName(), date));

		} else if (tagName.equals("relatePhoneNum")) {
			propData.setRelatePhoneNum(StringUtils.add(propData.getRelatePhoneNum(), date));

		} else if (tagName.equals("sumLossFee")) {
			propData.setSumLossFee(StringUtils.add(propData.getSumLossFee(), date));

		} else if (tagName.equals("rescueFee")) {
			propData.setRescueFee(StringUtils.add(propData.getRescueFee(), date));

		} else if (tagName.equals("checkDate")) {
			propData.setCheckDate(StringUtils.add(propData.getCheckDate(), date));

		} else if (tagName.equals("reserveFlag")) {
			propData.setReserveFlag(StringUtils.add(propData.getReserveFlag(), date));

		} else if (tagName.equals("checkSite")) {
			propData.setCheckSite(StringUtils.add(propData.getCheckSite(), date));

		} else if (tagName.equals("rescueInfo")) {
			propData.setRescueInfo(StringUtils.add(propData.getRescueInfo(), date));

		} else if (tagName.equals("remark")) {
			propData.setRemark(StringUtils.add(propData.getRemark(), date));

		}
	}

	
	
	
	public SurveyInfo getSurveyInfo() {
		
		surveyInfo.setBasicSurvey(basicSurvey);// 基本信息
		// surveyInfo.setCarData(carData);
		surveyInfo.setCarDatas(carDatas);// 车辆信息
		///surveyInfo.setPersonData(personData);// 人伤信息
		surveyInfo.setPersonDatas(personDatas);
		surveyInfo.setPolicyDatas(policyDatas);// 保单信息
		surveyInfo.setPropDatas(propDatas);// 财产信息
      
		surveyInfo.setLiabilityRatios(liabilityRatios);
		surveyInfo.setRegistThirdCarDatas(registThirdCarDatas);
		surveyInfo.setKindCodeDatas(kindCodeDatas);
		
		if(kindCodeDatas==null||kindCodeDatas.isEmpty()){
			LogRecoder.writeError(registNo+": 险别为空");
		}
		
		return surveyInfo;

	}

	public int getResult() {
		return result;
	}

}
