package com.sinosoft.ms.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.service.IDeflossDataService;
import com.sinosoft.ms.service.ILossAssistInfoService;
import com.sinosoft.ms.service.ILossFitInfoService;
import com.sinosoft.ms.service.ILossRepairInfoService;
import com.sinosoft.ms.service.IPrintInfoService;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.db.BasicSurveyDatabase;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 
 * 子系统名：打印信息服务接口实现
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午10:36:01
 */


public class PrintInfoService implements IPrintInfoService {
	private Map<String, Map<String, String>> map = null;
	private Map<String, Map<String, String>> dbMap = null;
	public PrintInfoService(){
		map = AppConstants.getLocalItemConf();
		dbMap = DictCashe.getInstant().getDict();
	}
	
	@Override
	public String surveyVoucher(Context context,String taskId,RegistData registData) {
		String result = null;
		Map<String, Map<String, String>> dataMap = DictCashe.getInstant().getDict();
		Map<String, Map<String, String>> localItemConf = AppConstants.getLocalItemConf();
		try{
			SurveyTreatmentDatabase2 std = SurveyTreatmentDatabase2.getInstance();
			BasicSurveyDatabase surveydb = new BasicSurveyDatabase(context) ;
			BasicSurvey basicSurvey = surveydb.selectBasicSurvey("basic_survey", taskId, "") ;
			//BasicSurvey basicSurvey = std.getBasicSurvey();
			if(basicSurvey == null){
				basicSurvey = new BasicSurvey() ;
			}
			registData.init() ;
			basicSurvey.init();
			int length = 0;
			result =  "\n\n----------- 查勘信息 -----------\n"
					+ "报案号:"+basicSurvey.getRegistNo()+"\n"
					+ "出险时间：" + registData.getDamageDate() + "\n"
					+ "出险地点:"+basicSurvey.getDamageAddress()+"\n"
					+ "出险原因:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getDamageCode(),dbMap.get("DamageCode")))+"\n"
					+ "出险经过：" + registData.getRemark() + "\n"
					+ "互碰自赔:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getCaseFlag(),map.get("Status")))+"\n"
					+ "查勘日期:"+basicSurvey.getCheckDate()+"\n"
					+ "查勘地点:"+basicSurvey.getCheckSite()+"\n"
					+ "查勘员:"+basicSurvey.getCheckerName()+"\n" 
					+ "联系电话：" + registData.getReportorNumber() + "\n" ;
			
			result += "\n----------- 索赔须知 -----------\n";
			result += "    尊敬的客户，感谢您选择我公司，\n"
					+ "为了更好保障你的利益，您可以通过我公司\n"
					+ "网页www.urtrust.com.cn查询或拨打全\n"
					+ "国统一服务（定损）热线40098-95999。\n"
					+ "    简易案件需要提供材料如下：\n"
					+ "□索赔申请书（单位盖公章）\n"
					+ "□行驶证复印件（正副页）\n"
					+ "□驾驶员证复印件（正副页）\n"
					+ "□交警证明\n"
					+ "□快处快赔协议书\n"
					+ "□保险车辆维修发票\n"
					+ "□三者车辆维修发票\n"
					+ "□施救费发票\n"
					+ "□被保险人身份证复印件（正反面）\n"
					+ "□被保险人活期存折或银行卡复印件\n\n"
					+ "--------------------------------\n"
					+ "查勘员签字：\n\n\n"
					+ "客户签字：\n\n\n";
			
			
					//+ "索赔申请书标志:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getClaimSignFlag(), map.get("Status")))+"\n"
					//+ "事故处理类型:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getManageType(), dbMap.get("AccidentManageType")))+"\n"
					//+ "损失类别:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getLossType(), dbMap.get("LossType")))+"\n"
					//+ "事故类型:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getDamageCaseCode(),dbMap.get("AccidentDutyType")))+"\n"
					//+ "第一现场查勘:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getFirstSiteFlag(),map.get("FirstSiteFlag")))+"\n"
					//+ "事故处理部门:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getSolutionUnit(),dbMap.get("SolutionUnit")))+"\n"
					//+ "查勘类别:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getIntermediaryFlag(),map.get("IntermediaryFlags")))+"\n"
					//+ "公估公司代码:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getIntermediaryCode(),dbMap.get("IntermediaryInfoCode")))+"\n"
					//+ "公估公司名称:"+basicSurvey.getIntermediaryName()+"\n"
					//+ "是否代位求偿:"+MyUtils.toString(SpinnerUtils.getValue(basicSurvey.getSubrogationFlag(),map.get("Status"))
					//		)+"\n"
					// + "险种:"+basicSurvey.getRiskCode()+"\n";
			
//			result += "\n\n------责任比例信息------\n";
//			List<LiabilityRatio> liabilityRatios = std.getLiabilityRatios();
//			if(null!=liabilityRatios && ! liabilityRatios.isEmpty()){
//				length = liabilityRatios.size();
//				for(int i=0; i<length; i++){
//					LiabilityRatio liabilityRatio = liabilityRatios.get(i);
//					liabilityRatio.init();
//					result += "第"+(i+1)+"条\n"
//					//+ "险种:"+liabilityRatio.getRiskCode()+"\n"
//					+ "保单交强/商业标志:"+SpinnerUtils.getValue(liabilityRatio.getPolicyFlag(),map.get("PolicyFlag"))+"\n"
//					+ "保单号:"+liabilityRatio.getPolicyNo()+"\n"
//					
//					+ "是否属于商业险责任:"+SpinnerUtils.getValue(liabilityRatio.getClaimFlag(),map.get("Status"))+"\n"
//					+ "商业险赔偿责任:"+SpinnerUtils.getValue(liabilityRatio.getIndemnityDuty(),
//							dataMap.get("IndemnityDuty"))+"\n"
//					+ "商业险赔偿责任比例:"+liabilityRatio.getIndemnityDutyRate()+"\n"
//					+ "交强险赔偿责任比例:"+liabilityRatio.getcIIndemDuty()+"\n"
//					+ "交强险赔偿责任:"+SpinnerUtils.getValue(
//							liabilityRatio.getcIDutyFlag(),
//							localItemConf.get("CIDutyFlag"))+"\n";
//				}
//			}
			
//			result += "\n\n------涉案车辆信息------\n";
//			List<CarData> carList = std.getCarDatas();
//			if(null!=carList && ! carList.isEmpty()){
//				length = carList.size();
//				for(int i=0; i<length; i++){
//					CarData carData = carList.get(i);
//					carData.init();
//					CarLossData carLossData = carData.getCarLossData();
//					carLossData.init();
//					result += "第"+(i+1)+"条\n"
//					+ "车牌号:"+carData.getLicenseNo()+"\n"
//					+ "车主:"+carData.getCarOwner()+"\n"
//					+ "估损金额:"+carLossData.getSumLossFee()+"\n"
//					+ "施救费用:"+carLossData.getRescueFee()+"\n";
//				}
//			}
//			result += "\n\n------财产损失信息------\n";
//			List<PropData> propList = std.getProDatas();
//			if(null!=propList && ! propList.isEmpty()){
//				length = propList.size();
//				for(int i=0; i<length; i++){
//					PropData propData = propList.get(i);
//					propData.init();
//					result += "第"+(i+1)+"条\n"
//							+ "联系人:"+propData.getRelatePersonName()+"\n";
//					List<PropDetailData> propDetailDatasList = propData.getPropDetailDatas();
//					for(PropDetailData p : propDetailDatasList){
//						p.init();
//						result += "损失财产名称:"+p.getLossItemName()+"\n"
//						+ "损失金额:"+p.getLossFee()+"\n";
//					}
//				}
//			}
			
//			result += "\n\n--------人伤信息--------\n";
//			PersonData personData = std.getPersonData();
//			List<PersonDetailData> personDetailDataList = personData.getPersonDetailData();
//			if(null!=liabilityRatios && ! liabilityRatios.isEmpty()){
//				length = liabilityRatios.size();
//				for(int i=0; i<length; i++){
//					PersonDetailData personDetailData = personDetailDataList.get(i);
//					personDetailData.init();
//					result += "第"+(i+1)+"条\n"
//					+ "姓名:"+personDetailData.getPersonName()+"\n"
//					+ "损失金额:"+personDetailData.getLossFee()+"\n";
//				}
//			}
//			result += "\n\n查勘意见:"+basicSurvey.getOpinion()+"\n"
//					+ "================================\n"
//					+ "查勘人签字:\n\n\n\n"
//					+ "客户签字:\n\n\n\n"
//					+ "================================\n"
//					+ "打印时间："+MyUtils.format("yyyy-MM-dd HH:mm:ss", new Date());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			map = null;
			dbMap = null;
		}
		return result;
	}

	@Override
	public String lossVoucher(Context context,DeflossData deflossData,String taskId,String reportorMobile) {
		String result = null;
		Map<String,Double> fitMap = null;
		Map<String,Double> repairMap = null;
		Map<String,Double> materialMap = null;
		try {
			IDeflossDataService deflossDataService = new DeflossDataService();
			if(deflossData ==null){
				deflossData = deflossDataService.getByTaskId(taskId);
			}
			if(deflossData == null){
				deflossData = new DeflossData() ;
			}
			deflossData.init();
			deflossData.getDefLossDate();
			
			double sumLossFee = 0;
			double sumChgCompFee = 0;	
			double sumRepairFee	= 0;
			double sumMaterialFee= 0;
			
			String fitStr = "";
			String repairStr = "";
			String materialStr = "";
			
			String brandName = "";
			String licenseNo = "";
			String defLossCarType = "";
			
			ILossFitInfoService lossFitInfoService = new LossFitInfoService();
			List<LossFitInfoItem> lossFitList = lossFitInfoService.getByRegistNo(deflossData.getTaskId());
			if(null!=lossFitList && !lossFitList.isEmpty()){
				fitMap = new HashMap<String,Double>();
				for(LossFitInfoItem lossFitInfoItem : lossFitList){
					lossFitInfoItem.init();
					Double price = StringUtils.toDouble(lossFitInfoItem.getSurveyQuotedPrice())*lossFitInfoItem.getLossCount();
					if(null==fitMap.get(lossFitInfoItem.getOriginalName())){
						fitMap.put(lossFitInfoItem.getOriginalName(), price);
					}else{
						Double temp = fitMap.get(lossFitInfoItem.getOriginalName());
						temp = add(temp,price);
						fitMap.put(lossFitInfoItem.getOriginalName(), temp);
					}
					sumChgCompFee = add(sumChgCompFee,price);
//					fitStr += lossFitInfoItem.getOriginalName()+" 金额:"+add(0,price)+"\n";
				}
				
				Iterator iter = fitMap.keySet().iterator();
				while(iter.hasNext()){
					String key = (String)iter.next();
					Double value = fitMap.get(key);
					fitStr += key+" 金额:"+value+"\n";
				}
			}
			
			
			ILossRepairInfoService lossRepairInfoService = new LossRepairInfoService();
			List<LossRepairInfoItem> repairList = lossRepairInfoService.getByRegistNo(deflossData.getTaskId());
			if(null!=repairList && !repairList.isEmpty()){
				repairMap = new HashMap<String,Double>();
				for(LossRepairInfoItem lossRepairInfoItem : repairList){
					lossRepairInfoItem.init();
					if(null==repairMap.get(lossRepairInfoItem.getRepairName())){
						repairMap.put(lossRepairInfoItem.getRepairName(), lossRepairInfoItem.getRepairFee());
					}else{
						Double temp = repairMap.get(lossRepairInfoItem.getRepairName());
						temp = add(temp,lossRepairInfoItem.getRepairFee());
						repairMap.put(lossRepairInfoItem.getRepairName(), temp);
					}
					sumRepairFee = add(sumRepairFee,lossRepairInfoItem.getRepairFee());
//					repairStr += lossRepairInfoItem.getRepairName()+" 金额:"+lossRepairInfoItem.getRepairFee()+"\n";
				}
				
				Iterator iter = repairMap.keySet().iterator();
				while(iter.hasNext()){
					String key = (String)iter.next();
					Double value = repairMap.get(key);
					repairStr += key+" 金额:"+value+"\n";
				}
			}
			
			ILossAssistInfoService lossAssistInfoService = new LossAssistInfoService();
			List<LossAssistInfoItem> assistList = lossAssistInfoService.getByRegistNo(deflossData.getTaskId());
			if(null!=assistList && !assistList.isEmpty()){
				materialMap = new HashMap<String,Double>();
				for(LossAssistInfoItem lossAssistInfoItem : assistList){
					lossAssistInfoItem.init();
					if(null==materialMap.get(lossAssistInfoItem.getMaterialName())){
						materialMap.put(lossAssistInfoItem.getMaterialName(), lossAssistInfoItem.getMaterialFee());
					}else{
						Double temp = materialMap.get(lossAssistInfoItem.getMaterialName());
						temp = add(temp,lossAssistInfoItem.getMaterialFee());
						materialMap.put(lossAssistInfoItem.getMaterialName(), temp);
					}
					sumMaterialFee = add(sumMaterialFee,lossAssistInfoItem.getMaterialFee());
//					materialStr += lossAssistInfoItem.getMaterialName()+" 金额:"+lossAssistInfoItem.getMaterialFee()+"\n";
				}
				
				Iterator iter = materialMap.keySet().iterator();
				while(iter.hasNext()){
					String key = (String)iter.next();
					Double value = materialMap.get(key);
					materialStr += key+" 金额:"+value+"\n";
				}
			}

			BasicVehicleService  basicVehicleService = new BasicVehicleService();
			BasicVehicle basicVehicle = basicVehicleService.getByRegistNo(taskId);	
			if(basicVehicle == null){
				basicVehicle = new BasicVehicle() ;
			}
			basicVehicle.init() ;
			
			LossVehicleService lossVehicleService = new LossVehicleService();
			LossVehicle lossVehicle = lossVehicleService.getByRegistNo(taskId);
			
			if(lossVehicle == null){
				lossVehicle = new LossVehicle() ;
			}
			lossVehicle.init() ;
			
			if(null!=lossVehicle || null != basicVehicle){
				if(StringUtils.isNotEmpty(lossVehicle.getBrandName())){
					brandName = lossVehicle.getBrandName();
				}else if(StringUtils.isNotEmpty(basicVehicle.getVehBrandName())){
					brandName = basicVehicle.getVehBrandName();
				}
				if(StringUtils.isNotEmpty(lossVehicle.getLicenseNo())){
					licenseNo = lossVehicle.getLicenseNo();
				}else if(StringUtils.isNotEmpty(basicVehicle.getPlateNo())){
					licenseNo = basicVehicle.getPlateNo();
				}
				if(StringUtils.isNotEmpty(lossVehicle.getDefLossCarType())){
					defLossCarType = SpinnerUtils.getValue(lossVehicle.getDefLossCarType(), map.get("LossItemType"));
				}
			}
			
			sumLossFee = sumChgCompFee + sumRepairFee + sumMaterialFee;
			String lossLevel = null;
			if(StringUtils.isEmpty(deflossData.getLossLevel())){
				lossLevel = "轻度受损";
			}else{
				if(deflossData.getLossLevel().length()==1){
					deflossData.setLossLevel("0"+deflossData.getLossLevel());
				}
				lossLevel = SpinnerUtils.getValue(deflossData.getLossLevel(),dbMap.get("LossLevel"));
			}
			result =   "       移动定损单打印凭证 \n"
					 + "\n\n----------- 定损信息 -----------\n"
					 + "报案号:"+deflossData.getRegistNo()+"\n"  
					 + "定损车型:"+brandName+"\n"
					 + "损失程度:"+lossLevel+"\n"
					 + "定损日期:"+deflossData.getDefLossDate()+"\n"
					 + "定损地点:"+deflossData.getDefSite()+"\n"
					 + "修理厂类型:"+SpinnerUtils.getValue(deflossData.getRepairFactoryType(),dbMap.get("RepairFactoryType"))+"\n"
					 + "修理厂名称:"+deflossData.getRepairFactoryName()+"\n"
					 + "定损员:"+deflossData.getHandlerName()+"\n"
					 + "联系电话:"+reportorMobile+"\n"
					 + "\n\n----------- 定损合计 -----------\n"
					 + "车牌:"+licenseNo+"\n"
					 + "标的/三者:"+defLossCarType+"\n"
					 + (StringUtils.isEmpty(fitStr) ? "" : "换件信息:\n"+fitStr)
					 + (StringUtils.isEmpty(repairStr) ? "" : "维修信息:\n"+repairStr)
					 + (StringUtils.isEmpty(materialStr) ? "" : "辅料信息:\n"+materialStr)
					 + "\n"
					 + "合计:"+sumLossFee+"\n"
					 + "--------------------------------\n"
					 + "定损员签字:\n\n\n\n"
					 + "客户签字:\n\n\n\n"
					 + "--------------------------------\n"
					 + "打印时间："+MyUtils.format("yyyy-MM-dd HH:mm:ss", new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			map = null;
			dbMap = null;
		}
		return result;
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	public double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
}

