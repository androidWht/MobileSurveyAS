package com.sinosoft.ms.utils.xml;

import java.util.List;
import java.util.Map;

import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.BusinessFactory;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.CarDriverData;
import com.sinosoft.ms.model.CarLossData;
import com.sinosoft.ms.model.CarPolicyData;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.LossVehicleInsurance;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PersonDetailData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.PropDetailData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.sub.ParamBean;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.LocationService;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.SystemUtil;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.component.SpinnerUtils;

/**
 * 系统名：移动查勘定损 子系统名：所有的请求xml都列在这里 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午10:58:47
 */

public class RequestXml implements IRequestXml {
	/**
	 * 登录接口 imei字段已经无用
	 */
	public String requestUserXML(User user) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><package><head><requestType>U0301</requestType></head><body>"
				+ "<user><name>"
				+ user.getName()
				+ "</name><pwd>"
				+ user.getPassword()
				+ "</pwd><imei>IUYGF875GFDCV542323</imei></user>"
		
				+ "<setting><mainSurvey>1</mainSurvey><schedulingTask>1</schedulingTask><lossTask>1</lossTask><driveTask>1</driveTask></setting></body></package>";
	}
	/****
	 * smallNo =-1请求用户退出
	 * 
	 */
	@Override
	public String requestExitLogin(User user)
	{
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "<head>" 
				+ "			<requestType>U0332</requestType>"
				+ "		</head>" 
				+ "		<body>" 
				+ "			<smallNo>"
				+ -1 + "</smallNo>" + "			<adapterAssembly>"
				+ user.getName() + "</adapterAssembly>"

				+ "		</body>" + "</package>";
	}
	// /
	/**
	 * 请求任务中心的任务列表
	 * 
	 * @param type筛选的类型
	 * @return 返回请求的xml字符串
	 */
	public String requestTaskListXML(int type) {
		User user = UserCashe.getInstant().getUser();
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
				+ "<package>                                  "
				+ "	<head>                                    "
				+ "		<requestType>U0302</requestType>      "
				+ "		<requestUser>" + user.getName() + "</requestUser>    "
				+ "	</head>                                   "
				+ "	<body>                                    " + "		<type>"
				+ type + "</type>                 "
				+ "	</body>                                   "
				+ "</package>                                 ";
		return result;
	}

	/**
	 * 请求查勘派工状态更新
	 * 
	 * @param type筛选的类型
	 * @return 返回请求的xml字符串
	 */
	public String workStatusXML(RegistData registData, String refusalReason) {
		Business business = BusinessFactory.getInstance();
		User user = UserCashe.getInstant().getUser();
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>          "
				+ "<package>                                           "
				+ "	<head>                                             "
				+ "		<requestType>U0306</requestType>               "
				+ "		<requestUser>"
				+ user.getName()
				+ "</requestUser>             "
				+ "	</head>                                            "
				+ "	<body>                                             "
				+ "<deptNo>"
				+ business.getDeptNo()
				+ "</deptNo>"
				+ "<taskId>"
				+ registData.getTaskId()
				+ "</taskId>"
				+ "<registId>"
				+ registData.getId()
				+ "</registId>"
				+ "		<ageing>                                       "
				+ "			<registNo>"
				+ registData.getRegistNo()
				+ "</registNo>"
				+ "			<reportorName>"
				+ registData.getReportorName()
				+ "</reportorName>"
				+ "			<reportDate>"
				+ registData.getReportDate()
				+ "</reportDate>"
				+ "			<damageName>"
				+ registData.getDamageName()
				+ "</damageName>"
				+ "			<damageDate>"
				+ registData.getDamageDate()
				+ "</damageDate>"
				+ "			<reportorNumber>"
				+ registData.getReportorNumber()
				+ "</reportorNumber>"
				+ "			<cause>"
				+ registData.getRemark()
				+ "</cause>   "
				+ "			<type>"
				+ registData.getStatus()
				+ "</type>   "
				+ "		</ageing>                                       "
				+ "	</body>                                             "
				+ "</package>                                           ";
		return result;
	}

	// //

	/**
	 * 拒绝任务中心的任务
	 * 
	 * @param reportNo
	 *            拒绝任务的报案号
	 * @param 拒绝理由
	 * @return 返回请求的xml字符串
	 */
	@Override
	public String requestRejectTaskXML(String reportNo, String reason) {
		return null;
	}

	/**
	 * 接受任务中心的任务
	 * 
	 * @param reportNo
	 *            接收任务的报案号
	 * @return 返回请求的xml字符串
	 */
	public String requestReceivingTaskXML(String reportNo) {
		return null;
	}

	/**
	 * 解析任务中心接受任务返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	@Override
	public String requestReassignTaskXml(String reportNo) {
		return null;
	}

	/**
	 * 归档任务 任务中心的任务
	 * 
	 * @param reportNo
	 *            任务的报案号
	 * @return 请求的字符串
	 */
	@Override
	public String requestArchiveTaskXml(String reportNo) {
		return null;
	}

	/**
	 * 到达处理 任务中心的任务
	 * 
	 * @param reportNo
	 *            任务的报案号
	 * @return 请求的字符串
	 */
	@Override
	public String requestArrivalProcessTaskXml(String reportNo) {
		return null;
	}

	/*
	 * 数据查询
	 * 
	 * @see
	 * com.sinosoft.ms.utils.xml.IRequestXmlFactory#requestDataSearch(com.sinosoft
	 * .ms.model.User)
	 */
	@Override
	public String requestDataSearch(ParamBean paramBean) {
		User user = UserCashe.getInstant().getUser();
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>                        "
				+ "<package>                                                     "
				+ "	<head>                                                "
				+ "		<requestType>U0311</requestType>              "
				+ "		<requestUser>"
				+ user.getName()
				+ "</requestUser>            "
				+ "	</head>                                               "
				+ "	<body>                                                "
				+ "		<data>                                        "
				+ "			<registNo>"
				+ paramBean.getReportNo()
				+ "</registNo>"
				+ "			<registId>"
				+ paramBean.getRegistId()
				+ "</registId>"
				+ "			<taskId>"
				+ paramBean.getTaskId()
				+ "</taskId>"
				+ "			<reportorName>"
				+ paramBean.getContactPerson()
				+ "</reportorName>         "
				+ "			<reportorNumber>"
				+ paramBean.getContactPhone()
				+ "</reportorNumber>     "
				+ "			<type>"
				+ paramBean.getTaskTypeSreach()
				+ "</type>                         "
				+ "			<reportDateStart>"
				+ paramBean.getReportTimeFrom()
				+ "</reportDateStart>   "
				+ "			<reportDateEnd>"
				+ paramBean.getReportTimeTo()
				+ "</reportDateEnd>       "
				+ "			<damageDateStart>"
				+ paramBean.getInsuranceTimeFrom()
				+ "</damageDateStart>   "
				+ "			<damageDateEnd>"
				+ paramBean.getInsuranceTimeTo()
				+ "</damageDateEnd>       "
				+ "			<currPage>"
				+ paramBean.getCurrPage()
				+ "</currPage>                 "
				+ "		</data>                                       "
				+ "	</body>                                               "
				+ "</package>                                                    ";

	}

	/**
	 * 实效信息
	 */
	public String requestPrescriptionXML(String reportNo) {
		User user = UserCashe.getInstant().getUser();
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "<head>" + "<requestType>U0310</requestType>"
				+ "<requestUser>" + user.getName() + "</requestUser>"
				+ "</head>" + "<body>" + "<registNo>" + reportNo
				+ "</registNo>" + "</body>" + "</package>";

	}

	/**
	 * 心跳信息
	 * 
	 * @param user
	 *            用户名 2015/5/16轮询接口 请求报文的body中新增经纬度字段 Xlongit:经度坐标 Ylatit：纬度坐标
	 *            2016/1/11
	 * @return
	 */
	public String requestTaskNumXML(User user) {
		String xlongit, ylatit;
		//检查后台service是否继续运行
		xlongit = LocationService.getLngS();
		ylatit = LocationService.getLatS();
		if (user.getSend_time() % 10 == 0 || user.getSend_time() == 1) {
			
		
		} else {
			///xlongit = "";
			///ylatit =""; 
		}
		
	
		
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "<head>" + "<requestType>U0314</requestType>"
				+ "<requestUser>" + user.getName() + "</requestUser>"
				+ "</head>" + "<body>" + "<smallNo>" + "5</smallNo>"
				+ "<xlongit>" + xlongit + "</xlongit>" + "<ylatit>" + ylatit
				+ "</ylatit>" + "</body>" + "</package>";

	}

	
	/**
	 * 心跳信息
	 * 
	 * @param user
	 *            用户名 2015/5/16轮询接口 请求报文的body中新增经纬度字段 Xlongit:经度坐标 Ylatit：纬度坐标
	 *            2016/1/11
	 * @return
	 */

	
	
	
	
	/**
	 * 字典信息
	 * 
	 * @param user
	 *            用户名
	 * @return
	 */
	public String requestDicInfoXML(User user, String synTime, String deptNo) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "<head>" + "<requestType>U0312</requestType>"
				+ "<requestUser>" + user.getName() + "</requestUser>"
				+ "</head>" + "<body><synTime>" + synTime
				+ "</synTime><appliArea>" + deptNo + "</appliArea></body>"
				+ "</package>";
	}

	// ******案件中心

	public String requestCaseCenterListXML(int type) {
		User user = UserCashe.getInstant().getUser();

		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "<head>" + "<requestType>U0303</requestType>"
				+ "<requestUser>" + user.getName() + "</requestUser>"
				+ "</head>" + "<body>" + "<type>" + type + "</type>"
				+ "</body>" + "</package>";
	}

	/**
	 * 案件中心 请求查勘信息 xml
	 * 
	 * @param reportNo
	 *            报案号
	 * @return
	 */
	public String requestCaseCenterSurveyInfoXML(String reportNo,
			String registId) {
		User user = UserCashe.getInstant().getUser();
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "<head>" + "<requestType>U0304</requestType>"
				+ "<requestUser>" + user.getName() + "</requestUser>"
				+ "</head>" + "<body>" + "<registNo>" + reportNo
				+ "</registNo>" + "<registId>" + registId + "</registId>"
				+ "</body>" + "</package>";
	}
	
	/**
	 * 查勘提交接口
	 * 
	 * 
	 * 新增phoneModel字段
	 *            
	 * 
	 */

	public String updateCaseCenterSurveyInfoXML(String registId,
			BasicSurvey basicSurvet,
			List<RegistThirdCarData> registThirdCarDatas,
			List<LiabilityRatio> liabilityRatios, List<CarData> carDatas,
			List<PropData> propDatas, List<PersonData> personDatas,
			List<PolicyData> policyDatas) {
		StringBuffer xml = new StringBuffer();

		User user = UserCashe.getInstant().getUser();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>               "
				+ "<package>                                            "
				+ "	<head>                                              "
				+ "		<requestType>U0307</requestType>                  "
				+ "		<requestUser>" + user.getName()
				+ "</requestUser>                                "
				+ "<phoneModel>" + SystemUtil.getModel() + "</phoneModel>"
				+ "	</head>                                             "
				+ "	<body>                                              ");

		xml.append("			<registId>" + registId + "</registId>");
		xml.append("		<basicSurvey>                                     "
				+ "			<registNo>"
				+ basicSurvet.getRegistNo()
				+ "</registNo>"
				+ "			<taskId>"
				+ basicSurvet.getTask_id()
				+ "</taskId>"
				+ "			<subrogationFlag>"
				+ basicSurvet.getSubrogationFlag()
				+ "</subrogationFlag>"
				+ "			<claimSignFlag>"
				+ basicSurvet.getClaimSignFlag()
				+ "</claimSignFlag>"
				+ "			<damageCode>"
				+ basicSurvet.getDamageCode()
				+ "</damageCode>"
				+ "			<checkSite>"
				+ basicSurvet.getCheckSite()
				+ "</checkSite>"
				+ "			<checkDate>"
				+ basicSurvet.getCheckDate()
				+ "</checkDate>"
				+ "			<checker1>"
				+ basicSurvet.getChecker1()
				+ "</checker1>"
				+ "			<caseFlag>"
				+ basicSurvet.getCaseFlag()
				+ "</caseFlag>"
				+ "			<damageAddress>"
				+ basicSurvet.getDamageAddress()
				+ "</damageAddress>"
				+ "			<intermediaryFlag>"
				+ basicSurvet.getIntermediaryFlag()
				+ "</intermediaryFlag>"
				+ "			<intermediaryCode>"
				+ basicSurvet.getIntermediaryCode()
				+ "</intermediaryCode>"
				+ "			<intermediaryName>"
				+ basicSurvet.getIntermediaryName()
				+ "</intermediaryName>"
				+ "			<manageType>"
				+ basicSurvet.getManageType()
				+ "</manageType>"
				+ "			<lossType>"
				+ basicSurvet.getLossType()
				+ "</lossType>"
				+ "			<damageCaseCode>"
				+ basicSurvet.getDamageCaseCode()
				+ "</damageCaseCode>"
				+ "			<firstSiteFlag>"
				+ basicSurvet.getFirstSiteFlag()
				+ "</firstSiteFlag>"
				+ "			<solutionUnit>"
				+ basicSurvet.getSolutionUnit()
				+ "</solutionUnit>"
				+ "			<riskCode>"
				+ basicSurvet.getRiskCode()
				+ "</riskCode>"
				+ "			<opinion>"
				+ basicSurvet.getOpinion()
				+ "</opinion>"
				+ "			<twoAvoidFlag>"
				+ basicSurvet.getTwoAvoidSelected()
				+ "</twoAvoidFlag>"
				+ "</basicSurvey>");

		xml.append("<registThirdCarDatas>");
		if (registThirdCarDatas != null && registThirdCarDatas.size() != 0) {
			for (RegistThirdCarData registThirdCarData : registThirdCarDatas) {
				// 如果三者车为空，则xml中不添加标签
				if (null == registThirdCarData) {
					continue;
				}
				registThirdCarData.init();
				xml.append("<registThirdCarData>        " + "				<licenseNo>"
						+ registThirdCarData.getLicenseNo() + "</licenseNo>  "
						+ "				<company>" + registThirdCarData.getCompany()
						+ "</company>             " + "				<brandName>"
						+ registThirdCarData.getBrandName()
						+ "</brandName>         " + "				<thirdPolicyNo>"
						+ registThirdCarData.getThirdPolicyNo()
						+ "</thirdPolicyNo> "
						+ "	</registThirdCarData>             ");
			}
		}
		xml.append("					</registThirdCarDatas>        ");

		xml.append("		<liabilityRatios>                                 ");
		// 添加责任比例

		if (liabilityRatios != null && !liabilityRatios.isEmpty()) {
			int lsize = liabilityRatios.size();
			for (int i = 0; i < lsize; i++) {
				LiabilityRatio liabilityRatio = liabilityRatios.get(i);
				liabilityRatio.init();
				xml.append("			<liabilityRatio>                                "
						+ "				<flag>"
						+ liabilityRatio.getFlag()
						+ "</flag>"
						+ "				<riskCode>"
						+ liabilityRatio.getRiskCode()
						+ "</riskCode>"
						+ "				<policyFlag>"
						+ liabilityRatio.getPolicyFlag()
						+ "</policyFlag>"
						+ "				<policyNo>"
						+ liabilityRatio.getPolicyNo()
						+ "</policyNo>"
						+ "				<claimFlag>"
						+ liabilityRatio.getClaimFlag()
						+ "</claimFlag>"
						+ "				<indemnityDuty>"
						+ liabilityRatio.getIndemnityDuty()
						+ "</indemnityDuty>"
						+ "				<indemnityDutyRate>"
						+ liabilityRatio.getIndemnityDutyRate()
						+ "</indemnityDutyRate>"
						+ "				<cIIndemDuty>"
						+ liabilityRatio.getcIIndemDuty()
						+ "</cIIndemDuty>"
						+ "				<cIDutyFlag>"
						+ liabilityRatio.getcIDutyFlag()
						+ "</cIDutyFlag>" + "			</liabilityRatio> ");
			}
		}
		xml.append("		</liabilityRatios>                                ");

		xml.append("		<carDatas>                                        ");
		if (carDatas != null && !carDatas.isEmpty()) {
			int csize = carDatas.size();
			for (int i = 0; i < csize; i++) {
				CarData carData = carDatas.get(i);
				carData.init();
				xml.append("			<carData>                                       "
						+ "				<checkCarId>"
						+ carData.getCheckCarId()
						+ "</checkCarId>"
						+ "				<lossItemType>"
						+ carData.getLossItemType()
						+ "</lossItemType>"
						+ "				<licenseNo>"
						+ carData.getLicenseNo()
						+ "</licenseNo>"
						+ "				<carOwner>"
						+ carData.getCarOwner()
						+ "</carOwner>"
						+ "				<engineNo>"
						+ carData.getEngineNo()
						+ "</engineNo>"
						+ "<vinNo>"
						+ carData.getVinNo()
						+ "</vinNo>"
						+ "<frameNo>"
						+ carData.getFrameNo()
						+ "</frameNo>"

						+ "				<licenseType>"
						+ carData.getLicenseType()
						+ "</licenseType>"
						+ "				<liabilityType>"
						+ carData.getLiabilityType() + "</liabilityType>");

				xml.append("				<carPolicyDatas>                              ");
				List<CarPolicyData> carPolicyDatas = carData.getCarpolicyList();
				if (carPolicyDatas != null && !carPolicyDatas.isEmpty()) {
					int psize = carPolicyDatas.size();
					for (int j = 0; j < psize; j++) {
						CarPolicyData carPolicyData = carPolicyDatas.get(j);
						carPolicyData.init();
						xml.append("<carPolicyData>" + "<policyFlag>"
								+ carPolicyData.getPolicyFlag()
								+ "</policyFlag>" + "<oppRegistNoBI>"
								+ carPolicyData.getOppRegistNoBI()
								+ "</oppRegistNoBI>" + "<oppPolicyNoBI>"
								+ carPolicyData.getOppPolicyNoBI()
								+ "</oppPolicyNoBI>" + "<oppInsurerCodeBI>"
								+ carPolicyData.getOppInsurerCodeBI()
								+ "</oppInsurerCodeBI>" + "<oppInsurerAreaBI>"
								+ carPolicyData.getOppInsurerAreaBI()
								+ "</oppInsurerAreaBI>" + "<oppRegistNoCI>"
								+ carPolicyData.getOppRegistNoCI()
								+ "</oppRegistNoCI>" + "<oppPolicyNoCI>"
								+ carPolicyData.getOppPolicyNoCI()
								+ "</oppPolicyNoCI>" + "<oppInsurerCodeCI>"
								+ carPolicyData.getOppInsurerCodeCI()
								+ "</oppInsurerCodeCI>" + "<oppInsurerAreaCI>"
								+ carPolicyData.getOppInsurerAreaCI()
								+ "</oppInsurerAreaCI>" + "<oppIdBi>"
								+ carPolicyData.getOppIdBi() + "</oppIdBi>"
								+ "<oppIdCi>" + carPolicyData.getOppIdCi()
								+ "</oppIdCi>" + "</carPolicyData>");
					}
				}
				xml.append("				</carPolicyDatas>                             ");
				CarDriverData carDriverData = carData.getCarDriverData();
				carDriverData.init();
				xml.append("				<carDriverData>                               "
						+ "					<checkCarId>" + carDriverData.getCheckCarId()
						+ "</checkCarId>" + "					<driverName>"
						+ carDriverData.getDriverName() + "</driverName>"
						+ "					<drivingCarType>"
						+ carDriverData.getDrivingCarType()
						+ "</drivingCarType>"
						+ "					<identifyType>"
						+ carDriverData.getIdentifyType()
						+ "</identifyType>"
						+ "					<identifyNumber>"
						+ carDriverData.getIdentifyNumber()
						+ "</identifyNumber>"
						+ "					<drivingLicenseNo>"
						+ carDriverData.getDrivingLicenseNo()// ********************
						+ "</drivingLicenseNo>" + "					<linkPhoneNumber>"
						+ carDriverData.getLinkPhoneNumber()
						+ "</linkPhoneNumber>"
						+ "				</carDriverData>                              ");
				CarLossData carLossData = carData.getCarLossData();
				carLossData.init();
				xml.append("				<carLossData>                                 "
						+ "					<damageFlag>"
						+ carLossData.getDamageFlag()
						+ "</damageFlag>"
						+ "					<reserveFlag>"
						+ carLossData.getReserveFlag()

						+ "</reserveFlag>"
						+ "					<sumLossFee>"
						+ carLossData.getSumLossFee()
						+ "</sumLossFee>"
						+ "					<rescueFee>"
						+ carLossData.getRescueFee()
						+ "</rescueFee>"
						+ "					<checkSite>"
						+ carLossData.getCheckSite()
						+ "</checkSite>"
						+ "					<checkDate>"
						+ carLossData.getCheckDate()
						+ "</checkDate>"
						+ "					<defSite>"
						+ carLossData.getDefSite()
						+ "</defSite>"
						+ "					<kindCode>"
						+ carLossData.getKindCode() //

						+ "</kindCode>"
						+ "					<indemnityDuty>"
						+ carLossData.getIndemnityDuty()
						+ "</indemnityDuty>"
						+ "					<indemnityDutyRate>"
						+ carLossData.getIndemnityDutyRate()
						+ "</indemnityDutyRate>"
						+ "					<lossPart>"
						+ carLossData.getLossPart()
						+ "</lossPart>"
						+ "				</carLossData>                                "
						+ "			</carData>                                      ");
			}
		}
		xml.append("		</carDatas>                                       ");
		xml.append("<propDatas>");
		if (propDatas != null && !propDatas.isEmpty()) {

			int prsize = propDatas.size();
			for (int n = 0; n < prsize; n++) {
				PropData propData = propDatas.get(n);
				propData.init();
				xml.append("<propData>                                      "
						+ "<propId>"
						+ propData.getPropId()
						+ "</propId>"
						+ "<checkPropId>"
						+ propData.getCheckPropId()
						+ "</checkPropId>"						
						+ "	<lossParty>"
						+ propData.getLossParty()
						+ "</lossParty>"
						+ "				<relatePersonName>"
						+ propData.getRelatePersonName()
						+ "</relatePersonName>"
						+ "				<relatePhoneNum>"
						+ propData.getRelatePhoneNum()
						+ "</relatePhoneNum>"
						+ "				<sumLossFee>"
						+ propData.getSumLossFee()
						+ "</sumLossFee>"
						+ "				<rescueFee>"
						+ propData.getRescueFee()
						+ "</rescueFee>"
						+ "				<checkDate>"
						+ propData.getCheckDate()
						+ "</checkDate>"
						+ "				<reserveFlag>"
						+ propData.getReserveFlag()
						+ "</reserveFlag>"
						+ "				<checkSite>"
						+ propData.getCheckSite()
						+ "</checkSite>"
						+ "				<rescueInfo>"
						+ propData.getRescueInfo()
						+ "</rescueInfo>"
						+ "	<remark>" + propData.getRemark() + "</remark>");
				xml.append("<propDetailDatas>                             ");
				List<PropDetailData> propDetailDatas = propData
						.getPropDetailDatas();
				if (propDetailDatas != null && propDetailDatas.size() > 0) {
					int pdsize = propDetailDatas.size();
					for (int m = 0; m < pdsize; m++) {
						PropDetailData propDetailData = propDetailDatas.get(m);
						xml.append("<propDetailData>                            "
								+ "<lossItemName>"
								+ propDetailData.getLossItemName()
								+ "</lossItemName>"
								+ "						<lossFee>"
								+ propDetailData.getLossFee()
								+ "</lossFee>"
								+ "						<lossDegreeCode>"
								+ propDetailData.getLossDegreeCode()
								+ "</lossDegreeCode>"
								+ "						<lossSpeciesCode>"
								+ propDetailData.getLossSpeciesCode()
								+ "</lossSpeciesCode>"
								+ "					</propDetailData>                           ");
					}
				}
				xml.append("</propDetailDatas>");
				xml.append("</propData> ");
			}
		}
		xml.append("		</propDatas>                                      "
				+ "		<personDatas>                                     ");
		if (personDatas != null && !personDatas.isEmpty()) {
			int persize = personDatas.size();
			for (int k = 0; k < persize; k++) {
				
				  PersonData personData = personDatas.get(k);
				  xml.append("	<personData>                                    "
				  + "				<personDataId>" 
			      + personData.getPersonDataId() 
				  +  "</personDataId>" 
				  + "				<lossParty>" 
				  +  personData.getLossParty() 
				  + "</lossParty>" 
				  + "				<severeNum>" 
				  + personData.getSevereNum() 
				  + "</severeNum>" 
				  + "				<minor>" 
				  + personData.getMinor() 
				  + "</minor>" 
				  + "				<deathNum>" 
				  + personData.getDeathNum() 
				  + "</deathNum>" 
				  + "				<sumLossFee>" 
				  + personData.getSumLossFee() 
				  + "</sumLossFee>" 
				  + "				<rescueFee>" 
				  + personData.getRescueFee() 
				  + "</rescueFee>" 
				  + "				<checkDate>" 
				  + personData.getCheckDate() 
				  + "</checkDate>" 
				  + "				<checkSite>" 
				  + personData.getCheckSite() 
				  + "</checkSite>" 
				  + "				<rescueContext>" 
				  + personData.getRescueContext() 
				  + "</rescueContext>"
				  + "				<createOwner>" 
				  + personData.getCreateOwner() 
				  + "</createOwner>"
				  + "				<modfiyOwner>" 
				  + personData.getModfiyOwner() 
				  + "</modfiyOwner>"
				  + "				<personDetailDatas>                           ");
				  List<PersonDetailData> personDetailDatas = personData.getPersonDetailData(); 
				  int pdesize = personDetailDatas.size(); 
				  for (int z = 0; z < pdesize; z++) {
				  PersonDetailData personDetailData = personDetailDatas.get(z);
				  xml.append("					<personDetailData>                          " 
				  +"						<personName>" 
				  + personDetailData.getPersonName() 
				  + "</personName>" 
				  + "						<personSex>" 
				  + personDetailData.getPersonSex() 
				  + "</personSex>" 
				  + "						<age>" 
				  + personDetailData.getAge() 
				  + "</age>" 
				  + "						<personPayType>" 
				  + personDetailData.getPersonPayType()
				  + "</personPayType>" 
				  + "						<treatType>" 
				  + personDetailData.getTreatType() 
				  + "</treatType>" 
				  + "						<hospitalName>" 
				  + personDetailData.getHospitalName() 
				  + "</hospitalName>" 
				  + "						<lossFee>" 
				  + personDetailData.getLossFee() 
				  + "</lossFee>" 
				  + "						<woundDetail>" 
				  + personDetailData.getWoundDetail() 
				  + "</woundDetail>" 
				  + "					</personDetailData>                         ");
				  }
				  xml.append("				</personDetailDatas>                          "
				  + "			</personData>                                   ");
			}
		}
		xml.append("		</personDatas>                                    ");
		xml.append("	</body>" + "</package>");

		return xml.toString();
	}

	// *******案件中心

	@Override
	public String lossSreachXML(String requestUser, String registNo,
			String registId) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "	<head>" + "		<requestType>U0305</requestType>"
				+ "		<requestUser>" + requestUser + "</requestUser>"
				+ "	</head>" + "	<body>" + "		<registNo>" + registNo
				+ "</registNo>" + "<registId>" + registId + "</registId>"
				+ "	</body>" + "</package>";
	}

	@Override
	public String lossChangeXML(String requestUser, int type,
			String carGroupId, String areaId, String typeName) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "	<head>" + "		<requestType>U0317</requestType>"
				+ "		<requestUser>" + requestUser + "</requestUser>"
				+ "	</head>" + "	<body>" + "		<type>" + type + "</type>"
				+ "		<carGroupId>" + carGroupId + "</carGroupId>"
				+ "		<areaId>" + areaId + "</areaId>" + "		<typeName>"
				+ typeName + "</typeName>" + "	</body>" + "</package>";
	}

	/**
	 * 定损更新xml
	 * 
	 * 新增phoneModel字段
	 */
	@Override
	public String updateLoss(String user, String reportNo, String registId,
			DeflossData deflossData,
			List<RegistThirdCarData> registThirdCarDatas,
			LossVehicle lossVehicle, BasicVehicle basicVehicle,
			List<LossRepairInfoItem> lossRepairInfoItems,
			List<LossFitInfoItem> lossFitInfoItems,
			List<LossAssistInfoItem> lossAssistInfoItems, BankInfo bankInfo) {
		// TODO Auto-generated method stub
		deflossData.init();
		lossVehicle.init();
		basicVehicle.init();
		bankInfo.init();
	//	User phonemodel = UserCashe.getInstant().getUser();
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "	<head>" + "		<requestType>U0308</requestType>"
				+ "		<requestUser>"
				+ user
				+ "</requestUser>"
				+ "<phoneModel>"
				+ SystemUtil.getModel()
				+ "</phoneModel>"
				+ "	</head>"
				+ "	<body>"
				+ "			<registId>"
				+ registId
				+ "</registId>"
				+ "		<deflossData>"
				+ "			<registNo>"
				+ deflossData.getRegistNo()
				+ "</registNo>"
				+ "			<taskId>"
				+ deflossData.getTaskId()
				+ "</taskId>"
				+ "			<subrogationFlag>"
				+ deflossData.getSubrogationFlag()
				+ "</subrogationFlag>"
				+ "			<claimSignFlag>"
				+ deflossData.getClaimSignFlag()
				+ "</claimSignFlag>"
				+ "			<cetainLossType>"
				+ deflossData.getCetainLossType()
				+ "</cetainLossType>"
				+ "			<repairFactoryCode>"
				+ deflossData.getRepairFactoryCode()
				+ "</repairFactoryCode>"
				+ "			<repairFactoryName>"
				+ deflossData.getRepairFactoryName()
				+ "</repairFactoryName>"
				+ "			<repairFactoryType>"
				+ deflossData.getRepairFactoryType()
				+ "</repairFactoryType>"
				+ "			<defLossDate>"
				+ deflossData.getDefLossDate()
				+ "</defLossDate>"
				+ "			<defSite>"
				+ deflossData.getDefSite()
				+ "</defSite>"
				+ "			<sendDate>"
				+ deflossData.getSendDate()
				+ "</sendDate>"
				+ "			<lossLevel>"
				+ deflossData.getLossLevel()
				+ "</lossLevel>"
				+ "			<handlerCode>"
				+ deflossData.getHandlerCode()
				+ "</handlerCode>"
				+ "			<estimatedDate>"
				+ deflossData.getEstimatedDate()
				+ "</estimatedDate>"
				+ "			<caseFlag>"
				+ deflossData.getCaseFlag()
				+ "</caseFlag>"
				+ "			<lossPart>"
				+ deflossData.getLossPart()
				+ "</lossPart>"
				+ "			<damagePurchasePrice>"
				+ deflossData.getDamagePurchasePrice()
				+ "</damagePurchasePrice>"
				+ "			<intermediaryFlag>"
				+ deflossData.getIntermediaryFlag()
				+ "</intermediaryFlag>"
				+ "			<intermediaryCode>"
				+ deflossData.getIntermediaryCode()
				+ "</intermediaryCode>"
				+ "<option>"
				+ deflossData.getLocalOption()
				+ "</option>"
				+ "		<quickClaimFlag>"
				+ deflossData.getQuickClaimFlag()
				+ "</quickClaimFlag>" + "		</deflossData>";
		xml += "<bankInfoData>" + "<taskId>" + bankInfo.getTask_id()
				+ "</taskId>" + "<province>" + bankInfo.getProvince()
				+ "</province>" + "<city>" + bankInfo.getCity() + "</city>"
				+ "<accountsName>" + bankInfo.getAccountsName()
				+ "</accountsName>" + "<accounts>" + bankInfo.getAccounts()
				+ "</accounts>" + "<bankName>" + bankInfo.getBankName()
				+ "</bankName>" + "<bankType>" + bankInfo.getBankType()
				+ "</bankType>" + "<bankOutlets>" + bankInfo.getBankOutlets()
				+ "</bankOutlets>" + "<bankNumber>" + bankInfo.getBankNumber()
				+ "</bankNumber>" + "<mobile>" + bankInfo.getMobile()
				+ "</mobile>" + "<cityFlag>" + bankInfo.getCityFlag()
				+ "</cityFlag>" + "<priorityFlag>" + bankInfo.getPriorityFlag()
				+ "</priorityFlag>" + "<purpose>" + bankInfo.getPurpose()
				+ "</purpose>" + "</bankInfoData>"
				// 报案三者车
				+ "<registThirdCarDatas>                  ";
		if (registThirdCarDatas != null && !registThirdCarDatas.isEmpty()) {
			for (RegistThirdCarData registThirdCarData : registThirdCarDatas) {
				registThirdCarData.init();
				xml += "<registThirdCarData>        " + "				<licenseNo>"
						+ registThirdCarData.getLicenseNo() + "</licenseNo>  "
						+ "				<company>" + registThirdCarData.getCompany()
						+ "</company>             " + "				<brandName>"
						+ registThirdCarData.getBrandName()
						+ "</brandName>         " + "				<thirdPolicyNo>"
						+ registThirdCarData.getThirdPolicyNo()
						+ "</thirdPolicyNo> "
						+ "	</registThirdCarData>             ";
			}
		}
		xml += "					</registThirdCarDatas>        "

		// 车辆信息

				+ "		<lossVehicle>" + "			<licenseNo>"
				+ lossVehicle.getLicenseNo() + "</licenseNo>"
				+ "			<defLossCarType>" + lossVehicle.getDefLossCarType()
				+ "</defLossCarType>" + "			<lossType>"
				+ lossVehicle.getLossType() + "</lossType>" + "			<carOwner>"
				+ lossVehicle.getCarOwner() + "</carOwner>" + "			<enrollDate>"
				+ lossVehicle.getEnrollDate() + "</enrollDate>"
				+ "			<carKindCode>" + lossVehicle.getCarKindCode()
				+ "</carKindCode>" + "			<frameNo>" + lossVehicle.getFrameNo()
				+ "</frameNo>" + "			<licenseColorCode>"
				+ lossVehicle.getLicenseColorCode() + "</licenseColorCode>"
				+ "			<modelCode>" + lossVehicle.getModelCode()
				+ "</modelCode>" + "			<vinNo>" + lossVehicle.getVinNo()
				+ "</vinNo>" + "			<licenseType>"
				+ lossVehicle.getLicenseType() + "</licenseType>"
				+ "			<brandName>" + lossVehicle.getBrandName()
				+ "</brandName>" + "			<gearboxType>"
				+ lossVehicle.getGearboxType() + "</gearboxType>"
				+ "			<gasType>" + lossVehicle.getGasType() + "</gasType>"
				+ "			<carKindFrom>" + lossVehicle.getCarKindFrom()
				+ "</carKindFrom> " + "			<engineNo>"
				+ lossVehicle.getEngineNo() + "</engineNo>"
				+ "			<insureComCode>" + lossVehicle.getInsureComCode()
				+ "</insureComCode>" + "			<indemDutyCI>"
				+ lossVehicle.getIndemDutyCi() + "</indemDutyCI>"
				+ "			<indemnityDuty>" + lossVehicle.getIndemnityDuty()
				+ "</indemnityDuty>" + "			<indemnityDutyRate>"
				+ lossVehicle.getIndemnityDutyRate() + "</indemnityDutyRate>"
				+ "<licenseColorCodeTo2>"
				+ lossVehicle.getLicenseColorCodeTo2()
				+ "</licenseColorCodeTo2>" + "<modelName>"
				+ lossVehicle.getModelName() + "</modelName>";

		xml += "			<carPolicyDatas>";
		List<LossVehicleInsurance> lossVehicleInsurances = lossVehicle
				.getLossVehicleInsuranceList();
		if (lossVehicleInsurances != null) {
			int size = lossVehicleInsurances.size();
			for (int i = 0; i < size; i++) {
				LossVehicleInsurance lossVehicleInsurance = lossVehicleInsurances
						.get(i);
				lossVehicleInsurance.init();
				xml += "				<carPolicyData>" + "					<policyFlag>"
						+ lossVehicleInsurance.getPolicyFlag()
						+ "</policyFlag>" + "					<oppRegistNoBI>"
						+ lossVehicleInsurance.getOppRegistNoBi()
						+ "</oppRegistNoBI>" + "					<oppPolicyNoBI>"
						+ lossVehicleInsurance.getOppPolicyNoBi()
						+ "</oppPolicyNoBI>" + "					<oppInsurerCodeBI>"
						+ lossVehicleInsurance.getOppInsurerCodeBi()
						+ "</oppInsurerCodeBI>" + "					<oppInsurerAreaBI>"
						+ lossVehicleInsurance.getOppInsurerAreaBi()
						+ "</oppInsurerAreaBI>" + "					<oppRegistNoCI>"
						+ lossVehicleInsurance.getOppRegistNoCi()
						+ "</oppRegistNoCI>" + "					<oppPolicyNoCI>"
						+ lossVehicleInsurance.getOppPolicyNoCi()
						+ "</oppPolicyNoCI>" + "					<oppInsurerCodeCI>"
						+ lossVehicleInsurance.getOppInsurerCodeCi()
						+ "</oppInsurerCodeCI>" + "					<oppInsurerAreaCI>"
						+ lossVehicleInsurance.getOppInsurerAreaCi()
						+ "</oppInsurerAreaCI>" + "<oppIdBi>"
						+ lossVehicleInsurance.getOppIdBi() + "</oppIdBi>"
						+ "<oppIdCi>" + lossVehicleInsurance.getOppIdCi()
						+ "</oppIdCi>" + "</carPolicyData>";
			}
		}
		xml += "			</carPolicyDatas>" + "		</lossVehicle>" +

		"		<basicVehicle>" + "			<caseNo>"
				+ basicVehicle.getCaseNo() // 暂时修复
				+ "</caseNo>" + "			<lossNo>" + basicVehicle.getLossNo()
				+ "</lossNo>" + "			<vehKindCode>"
				+ basicVehicle.getVehKindCode() + "</vehKindCode>"
				+ "			<vehKindName>" + basicVehicle.getVehKindName()
				+ "</vehKindName>" + "			<vehCertainCode>"
				+ basicVehicle.getVehCertainCode() + "</vehCertainCode>"
				+ "			<vehCertainName>" + basicVehicle.getVehCertainName()
				+ "</vehCertainName>" + "			<vehSeriCode>"
				+ basicVehicle.getVehSeriCode() + "</vehSeriCode>"
				+ "			<vehSeriName>" + basicVehicle.getVehSeriName()
				+ "</vehSeriName>" + "			<vehYearType>"
				+ basicVehicle.getVehYearType() + "</vehYearType>"
				+ "			<vehFactoryCode>" + basicVehicle.getVehFactoryCode()
				+ "</vehFactoryCode>" + "			<vehFactoryName>"
				+ basicVehicle.getVehFactoryName() + "</vehFactoryName>"
				+ "			<vehBrandCode>" + basicVehicle.getVehBrandCode()
				+ "</vehBrandCode>" + "			<vehBrandName>"
				+ basicVehicle.getVehBrandName() + "</vehBrandName>"
				+ "			<plateNo>" + basicVehicle.getPlateNo() + "</plateNo>"
				+ "			<selfConfigFlag>" + basicVehicle.getSelfConfigFlag()
				+ "</selfConfigFlag>" + "			<version>"
				+ basicVehicle.getVersion() + "</version>" + "			<remark>"
				+ basicVehicle.getRemark() + "</remark>" + "			<vehGroupCode>"
				+ basicVehicle.getVehGroupCode() + "</vehGroupCode>"
				+ "			<vehGroupName>" + basicVehicle.getVehGroupName()
				+ "</vehGroupName>" + "			<sumChgCompFee>"
				+ basicVehicle.getSumChgCompFee() + "</sumChgCompFee>"
				+ "			<sumRepairFee>" + basicVehicle.getSumRepairFee()
				+ "</sumRepairFee>" + "			<sumMaterialFee>"
				+ basicVehicle.getSumMaterialFee() + "</sumMaterialFee>"
				+ "			<sumLossFee>" + basicVehicle.getSumLossFee()
				+ "</sumLossFee>" + "<sumRemnant>"
				+ basicVehicle.getSumRemnant() + "</sumRemnant>"
				+ "<sumRescueFee>" + basicVehicle.getSumRescueFee()
				+ "</sumRescueFee>" + "</basicVehicle>";

		xml += "		<lossFitInfoItem>";
		int infoItems = lossFitInfoItems.size();
		for (int k = 0; k < infoItems; k++) {
			LossFitInfoItem lossFitInfoItem = lossFitInfoItems.get(k);
			lossFitInfoItem.init();
			xml += "			<lossFitInfo>" + "				" + "<jyId>"
					+ lossFitInfoItem.getJyId()
					+ "</jyId>"
					+ "<serialNo>"
					+ (k + 1)
					+ "</serialNo>"
					+ "				<originalId>"
					+ lossFitInfoItem.getOriginalId()
					+ "</originalId>"
					+ "				<originalName>"
					+ "<![CDATA["
					+ lossFitInfoItem.getOriginalName()
					+ "]]>"
					+ "</originalName>"
					+ "				<partId>"
					+ lossFitInfoItem.getPartId()// ******
					+ "</partId>"
					+ "				<partStandardCode>"
					+ lossFitInfoItem.getPartStandardCode() // ******
					+ "</partStandardCode>"
					+ "				<partStandard>"
					+ lossFitInfoItem.getPartStandard()
					+ "</partStandard>"
					+ "				<partGroupCode>"
					+ lossFitInfoItem.getPartGroupCode()
					+ "</partGroupCode>"
					+ "				<partGroupName>"
					+ lossFitInfoItem.getPartGroupName()
					+ "</partGroupName>"
					+ "				<lossCount>"
					+ lossFitInfoItem.getLossCount()
					+ "</lossCount>"
					+ "				<lossPrice>"
					+ lossFitInfoItem.getLossPrice()

					+ "</lossPrice>"
					+ "				<chgRefPrice>"
					+ lossFitInfoItem.getChgRefPrice()
					+ "</chgRefPrice>"
					+ "<repairSitePrice>"
					+ lossFitInfoItem.getModifyFactoryPrice()
					+ "</repairSitePrice>"
					+ "				<remnant>"
					+ lossFitInfoItem.getRemnant()
					+ "</remnant>"
					+ "				<selfConfigFlag>"
					+ lossFitInfoItem.getSelfConfigFlag()
					+ "</selfConfigFlag>"// *************
					+ "				<ifRemain>"
					+ lossFitInfoItem.getIfRemain()
					+ "</ifRemain>"
					+ "				<chgCompSetCode>"
					+ ((StringUtils.isNotEmpty(lossFitInfoItem
							.getChgCompSetCode()) && 2 == lossFitInfoItem
							.getChgCompSetCode().length()) ? "0"
							+ lossFitInfoItem.getChgCompSetCode()
							: lossFitInfoItem.getChgCompSetCode())
					+ "</chgCompSetCode>"
					+ "				<status>"
					+ "A"// lossFitInfoItem.getStatus()
					+ "</status>" + "				<remark>"
					+ lossFitInfoItem.getRemark() + "</remark>"
					+ "				<insureTermCode>"
					+ lossFitInfoItem.getInsureTermCode() + "</insureTermCode>"
					+ "				<insureTerm>" + lossFitInfoItem.getInsureTerm()
					+ "</insureTerm>" + "				<chgLocPrice>"
					+ lossFitInfoItem.getChgLocPrice() + "</chgLocPrice>"
					+ "				<locPrice1>" + lossFitInfoItem.getLocPrice1()
					+ "</locPrice1>" + "				<locPrice2>"
					+ lossFitInfoItem.getLocPrice2() + "</locPrice2>"
					+ "				<locPrice3>" + lossFitInfoItem.getLocPrice3()
					+ "</locPrice3>" + "				<refPrice1>"
					+ lossFitInfoItem.getRefPrice1() + "</refPrice1>"
					+ "				<refPrice2>" + lossFitInfoItem.getRefPrice2()
					+ "</refPrice2>" + "				<refPrice3>"
					+ lossFitInfoItem.getRefPrice3() + "</refPrice3>" +

					"			</lossFitInfo>";
		}

		xml += "</lossFitInfoItem>" + "		<lossRepairInfoItem>";

		int item = lossRepairInfoItems.size();
		for (int n = 0; n < item; n++) {
			LossRepairInfoItem lossRepairInfoItem = lossRepairInfoItems.get(n);
			lossRepairInfoItem.init();
			xml += "	<lossRepairInfo>" + "				<repairItemName>"
					+ lossRepairInfoItem.getRepairItemName()
					+ "</repairItemName>" + "<jyId>"
					+ lossRepairInfoItem.getJyId() + "</jyId>"
					+ "				<serialNo>" + (n + 1) + "</serialNo>"
					+ "				<repairCode>" + lossRepairInfoItem.getRepairCode()
					+ "</repairCode>" + "				<repairName>"
					+ lossRepairInfoItem.getRepairName() + "</repairName>"
					+ "				<repairId>" + lossRepairInfoItem.getRepairId()
					+ "</repairId>" + "				<repairItemCode>"
					+ lossRepairInfoItem.getRepairItemCode()
					+ "</repairItemCode>" + "				<levelCode>"
					+ lossRepairInfoItem.getLevelCode() + "</levelCode>"
					+ "				<manHour>" + lossRepairInfoItem.getManHour()
					+ "</manHour>" + "				<repairFee>"
					+ lossRepairInfoItem.getRepairFee() + "</repairFee>"
					+ "				<selfConfigFlag>"
					+ lossRepairInfoItem.getSelfConfigFlag()
					+ "</selfConfigFlag>" + "				<status>" + "A" + "</status>"
					+ "				<remark>" + lossRepairInfoItem.getRemark()
					+ "</remark>" + "				<insureTermCode>"
					+ lossRepairInfoItem.getInsureTermCode()
					+ "</insureTermCode>" + "				<insureTerm>"
					+ lossRepairInfoItem.getInsureTerm() + "</insureTerm>"
					+ "			</lossRepairInfo>";

		}

		xml += "		</lossRepairInfoItem>" + "		<lossAssistInfoItem>";
		int assistItem = lossAssistInfoItems.size();
		for (int j = 0; j < assistItem; j++) {
			LossAssistInfoItem lossAssistInfoItem = lossAssistInfoItems.get(j);
			lossAssistInfoItem.init();
			xml += "			<lossAssistInfo>" + "				" + "<jyId>"
					+ lossAssistInfoItem.getJyId() + "</jyId>" + "<serialNo>"
					+ (j + 1) + "</serialNo>" + "				<materialName>"
					+ lossAssistInfoItem.getMaterialName() + "</materialName>"
					+ "				<unitPrice>" + lossAssistInfoItem.getUnitPrice()
					+ "</unitPrice>" + "				<count>"
					+ lossAssistInfoItem.getCount() + "</count>"
					+ "				<materialFee>" + lossAssistInfoItem.getMaterialFee()
					+ "</materialFee>" + "				<status>" + "A" + "</status>"
					+ "				<remark>" + lossAssistInfoItem.getRemark()
					+ "</remark>" + "				<insureTermCode>"
					+ lossAssistInfoItem.getInsureTermCode()
					+ "</insureTermCode>" + "				<insureTerm>"
					+ lossAssistInfoItem.getInsureTerm() + "</insureTerm>"
					+ "			</lossAssistInfo>";
		}

		xml += "		</lossAssistInfoItem>" + "	</body>" + "</package>";

		return xml.trim();
	}

	public String toCode(String str) {
		String code = null;
		Map<String, Map<String, String>> map = AppConstants.getLocalItemConf();
		code = SpinnerUtils
				.getKey(str.replace("项目", ""), map.get("RepairCode"));
		return code;
	}

	public String rreceiceUpdateXML(String requestUser, int type,
			String serverIds) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<package>                                 "
				+ "	<head>                                   "
				+ "		<requestType>U0318</requestType> 	 " + "		<requestUser>"
				+ requestUser + "</requestUser>"
				+ "	</head>                                  "
				+ "	<body>                                   "
				+ "        <type>" + type + "</type>             "
				+ "        <ids>" + serverIds + "</ids>          "
				+ "    </body>                               "
				+ "</package>                                ";
	}

	/**
	 * 车辆定型
	 * 
	 * @param requestUser
	 *            用户名
	 * @param czid
	 *            车系ID
	 * @param vehseriName
	 *            车系名称
	 * @return XML报文
	 */
	public String vehicleTypeXML(String requestUser, String czid,
			String vehseriName) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>        "
				+ "<package>                                         "
				+ "	<head>                                           "
				+ "		<requestType>U0315</requestType>             "
				+ "		<requestUser>" + requestUser + "</requestUser>   "
				+ "	</head>                                          "
				+ "	<body>                                           "
				+ "		<czid>" + czid + "</czid>" + "		<vehSeriName>"
				+ vehseriName + "</vehSeriName>   "
				+ "	</body>                                          "
				+ "</package>                                        ";
	}

	/**
	 * 换件价格查询
	 * 
	 * @param requestUser
	 *            用户名
	 * @param type
	 *            价格方案
	 * @param vehicleType
	 *            车型编码
	 * @param carGroupId
	 *            车组ID
	 * @param areaId
	 *            区域ID
	 * @param typeName
	 *            零件名称
	 * @param damagePartId
	 *            损伤部位零件Id
	 * @param keyWord
	 *            关键字
	 * @return 换件价格XML报文
	 */
	public String fitListXML(String requestUser, String type,
			String vehicleType, String carGroupId, String areaId,
			String typeName, String damagePartId, String keyWord, int tag) {

		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "	<head>" + "		<requestType>U0317</requestType>"
				+ "		<requestUser>"
				+ requestUser
				+ "</requestUser>"
				+ "	</head>"
				+ "	<body>"
				+ "		<type>"
				+ type
				+ "</type>"
				+ "		<vehicleType>"
				+ vehicleType
				+ "</vehicleType>"
				+ "		<carGroupId>"
				+ carGroupId
				+ "</carGroupId>"
				+ "		<areaId>"
				+ areaId
				+ "</areaId>"
				+ "		<typeName>"
				+ typeName
				+ "</typeName>"
				+ "<damagePartId>"
				+ damagePartId
				+ "</damagePartId>"
				+ "		<keyWord>"
				+ (StringUtils.isEmpty(keyWord) ? "" : keyWord)
				+ "</keyWord>"
				+ "<tag>"
				+ tag
				+ "</tag>"
				+ "	</body>"
				+ "</package>";
		// return
		// "<?xml version=\"1.0\" encoding=\"UTF-8\"?>                                   "
		// +
		// "<package>                                                                "
		// +
		// "	<head>                                                           "
		// + "		<requestType>U0317</requestType>                         "
		// + "		<requestUser>8000161</requestUser>                       "
		// +
		// "	</head>                                                          "
		// +
		// "	<body>                                                           "
		// + "		<type>1</type>                                           "
		// + "		<vehicleType>BTA0AQ011</vehicleType>                     "
		// + "		<carGroupId>402880ac1167b1fa0111694afc170092</carGroupId>"
		// + "		<areaId>44000000</areaId>        "
		// + "		<typeName>前保险杠</typeName>                            "
		// + "		<key>前</key>                                            "
		// +
		// "	</body>                                                          "
		// +
		// "</package>                                                               ";
	}

	/**
	 * 价格方案
	 * 
	 * @param requestUser
	 *            用户名
	 * @param deptCode
	 *            机构代码
	 * @return 价格方案XML报文
	 */
	public String pricePlanXML(String requestUser, String deptCode) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
				+ "<package>                                  "
				+ "	<head>                                    "
				+ "		<requestType>U0316</requestType>      "
				+ "		<requestUser>" + requestUser + "</requestUser>"
				+ "	</head>                                   "
				+ "	<body>                                    "
				+ "		<deptCode>" + deptCode + "</deptCode>     "
				+ "	</body>                                   "
				+ "</package>                                 ";
	}

	public String updateImageXml(User user, List<ImageCenterBean> imageDatas) {

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<package>                             "
				+ "	<head>                               "
				+ "		<requestType>U0309</requestType>   " + "		<requestUser>"
				+ user.getName() + "</requestUser>                   "
				+ "	</head>                              "
				+ "	<body>                               " + "<deptNo>"
				+ user.getDeptNo() + "</deptNo>" + "<registId>"
				+ imageDatas.get(0).getRegistId() + "</registId>"
				+ "      <imageDatas> ";

		for (ImageCenterBean image : imageDatas) {
			if (!StringUtils.isEmpty(image.getType())) {
				xml += "			<imageData>                      "
						+ "				<registNo>"
						+ image.getReportNo()
						+ "</registNo>          "
						+ "				<taskId>"
						+ image.getTaskId()
						+ "</taskId>          "
						+ "				<registId>"
						+ image.getRegistId()
						+ "</registId>          "
						+ "				<type>"
						+ image.getType()
						+ "</type>                  "
						+ "				<pictureName>"
						+ image.getImgName().replaceAll("_", "m")
						+ "</pictureName>    "
						+ "				<savePath>"
						+ image.getReportNo()
						+ "/"
						+ image.getImgName().replaceAll("_", "m")
						+ "</savePath>          "
						+ "				<pictureSize>"
						+ image.getImgSize()
						+ "</pictureSize>    "
						+ "				<pictureType>"
						+ "jpg"
						+ "</pictureType>    "
						+ "				<uploadTime>"
						+ image.getCreateDate()
						+ "</uploadTime>      "
						+ "				<gpsInfo>"
						+ image.getLocation()
						+ "</gpsInfo>            "
						+ "				<remark>"
						+ ""
						+ "</remark>              "
						+ "			</imageData>                     ";
			}

		}
		xml += "		</imageDatas>                      "
				+ "	</body>                              "
				+ "</package>                            ";
		return xml;

	}

	// 上传单证
	public String updateDocumentsXml(User user, String registNo) {

		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>"
				+ "<PACKET type=\"REQUEST\" version=\"1.0\">" + "<HEAD>"
				+ "<SystemCode>80005</SystemCode>" + "<UserCode>"
				+ user.getName() + "</UserCode>" + "<Password>"
				+ user.getPassword() + "</Password>"
				+ "<ComCode>44000000</ComCode>" + "</HEAD>" + "<BODY>"
				+ "<INPUTDATA>" + "<BASE_DATA>" + "<RegistNo>" + registNo
				+ "</RegistNo>" + "<UserCode>" + user.getName() + "</UserCode>"
				+ "<ComCode>44000000</ComCode> " + "</BASE_DATA>";

		if ("测试版".equals(AppConstants.VERSION_NAME)) {
			xml += "<FTP_BASE_DATA>" + "<UserName>weblogic</UserName>"
					+ "<PassWord>weblogic@2013</PassWord>"
					+ "<IP>10.17.192.30</IP>" + "<Port>21</Port> "
					+ "</FTP_BASE_DATA>";
		} else {
			xml += "<FTP_BASE_DATA>" + "<UserName>mbyxftp</UserName>"
					+ "<PassWord>Yxmb2069</PassWord>"
					+ "<IP>192.168.192.69</IP>" + "<Port>21</Port>"
					+ "</FTP_BASE_DATA>";
		}

		xml += "<PHOTO_LIST>" + "<PHOTO_DATA>" + "<DocCode>0018</DocCode>"
				+ "<DocName>标的车修理发票</DocName>" + "<PathName></PathName>"
				+ "<FileName></FileName>" + "<ParentTypeCode></ParentTypeCode>"
				+ "<IsCustom>0</IsCustom>" + "</PHOTO_DATA>" + "</PHOTO_LIST>"
				+ "</INPUTDATA>" + "</BODY>" + "</PACKET>";
		return xml;

	}

	@Override
	public String caseQueryXml(String requestUser, String registNo,
			String registId, String taskId) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "	<head>" + "		<requestType>U0319</requestType>"
				+ "		<requestUser>" + requestUser + "</requestUser>"
				+ "	</head>" + "	<body>" + "		<registNo>" + registNo
				+ "</registNo>" + "		<registId>" + registId + "</registId>"
				+ "		<taskId>" + taskId + "</taskId>" + "	</body>"
				+ "</package>";
	}

	@Override
	public String lossBankPointQueryXml(String requestUser, BankInfo bankInfo) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "	<head>" + "		<requestType>U0327</requestType>"
				+ "		<requestUser>"
				+ requestUser
				+ "</requestUser>"
				+ "	</head>"
				+ "	<body>"
				+ "		<bankPointQueryData>"
				+ "<bankProvince>"
				+ bankInfo.getProvince()
				+ "</bankProvince>"
				+ "<bankCity>"
				+ bankInfo.getCity()
				+ "</bankCity>"
				+ "<bankName>"
				+ bankInfo.getBankName()
				+ "</bankName>"
				+ "<bankType>"
				+ bankInfo.getBankType()
				+ "</bankType>"
				+ "<bankNumber>"
				+ bankInfo.getBankNumber()
				+ "</bankNumber>"
				+ "<bankOutlets>"
				+ bankInfo.getBankOutlets()
				+ "</bankOutlets>"
				+ "</bankPointQueryData>" + "</body>" + "</package>";
	}

	/**
	 * 根据报案号后六位搜索请求任务中心的任务列表
	 * 
	 * @param type筛选的类型
	 * @param 报案号后几位
	 * @return 返回请求的xml字符串
	 */
	@Override
	public String requestTaskListXML(int type, String registNo) {
		// TODO Auto-generated method stub

		User user = UserCashe.getInstant().getUser();
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
				+ "<package>                                  "
				+ "	<head>                                    "
				+ "		<requestType>U0302</requestType>      "
				+ "		<requestUser>" + user.getName() + "</requestUser>    "
				+ "	</head>                                   "
				+ "	<body>                                    " + "		<type>"
				+ type + "</type>                 " + "		<registNo>" + registNo
				+ "</registNo>                 "
				+ "	</body>                                   "
				+ "</package>                                 ";
		return result;
	}

	/**
	 * VIN码检测接口
	 * 
	 * @param taskId任务号 registId报案号 licenseNo车牌号 vinNo  VIN码
	 * @return 0-重复  1-通过
	 * resultCode resultInfo
	 *  
	 */
	@Override
	public String requestVinXML(String taskId, String registNo,String licenseNo,String vinNo) {
		// TODO Auto-generated method stub
		User user = UserCashe.getInstant().getUser();
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
				+ "<package>                                  "
				+ "	<head>                                    "
				+ "		<requestType>U0430</requestType>      "
				+ "		<requestUser>" + user.getName() + "</requestUser>    "
				+ "	</head>                                   " + "	<body>"
				+ "<taskId>" + taskId + "</taskId>                 "
				+ "<registNo>" + registNo + "</registNo>                 "
				+ "<licenseNo>" + licenseNo + "</licenseNo>                 "
				+ "<vinNo>" + vinNo + "</vinNo>                 "
				+ "	</body>                                   "
				+ "</package>                                 ";
		return result;
	}
	
	
	/**
	 * 任务核损查询接口
	 * 
	 * @param 任务号
	 * @return 0-核损未通过 1-核损通过
	 */
	@Override
	public String lossCheckNuclei(String taskId, String registId) {
		// TODO Auto-generated method stub
		User user = UserCashe.getInstant().getUser();
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
				+ "<package>                                  "
				+ "	<head>                                    "
				+ "		<requestType>U0330</requestType>      "
				+ "		<requestUser>" + user.getName() + "</requestUser>    "
				+ "	</head>                                   " + "	<body>"
				+ "<registNo>" + taskId + "</registNo>                 "
				+ "<registId>" + registId + "</registId>                 "
				+ "	</body>                                   "
				+ "</package>                                 ";
		return result;
	}

	public String requestVersionXML() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "<head>" + "			<requestType>U0332</requestType>"
				+ "		</head>" + "		<body>" + "			<smallNo>"
				+ AppConstants.SMALLNO + "</smallNo>" + "			<adapterAssembly>"
				+ AppConstants.ADAPTER_ASSEMBLY + "</adapterAssembly>"
				+ "		</body>" + "</package>";
	}

	@Override
	public String requestRepairFactoryXML(String keyword) {
		// TODO Auto-generated method stub
		User user = UserCashe.getInstant().getUser();
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
				+ "<head>" + "			<requestType>U0333</requestType>"
				+ "		<requestUser>" + user.getName() + "</requestUser>    "
				+ "		</head>" + "		<body>" + "			<keyword>" + keyword
				+ "</keyword>" + "		</body>" + "</package>";
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ms.utils.xml.IRequestXml#confirmdamageSreachXML(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String confirmdamageSreachXML(String requestUser, String registNo,
			String registId) {
		// TODO Auto-generated method stub
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<package>"
		+ "	<head>" + "		<requestType>U0101</requestType>"
		+ "		<requestUser>" + requestUser + "</requestUser>"
		+ "	</head>" + "	<body>" + "		<registNo>" + registNo
		+ "</registNo>" + "<registId>" + registId + "</registId>"
		+ "	</body>" + "</package>";
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ms.utils.xml.IRequestXml#requestExitLogin()
	 */


}
