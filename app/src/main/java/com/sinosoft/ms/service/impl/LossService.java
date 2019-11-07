package com.sinosoft.ms.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.KindCodeData;
import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.LossVehicleInsurance;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.IBankInfoService;
import com.sinosoft.ms.service.IBasicVehicleService;
import com.sinosoft.ms.service.IDeflossDataService;
import com.sinosoft.ms.service.ILossAssistInfoService;
import com.sinosoft.ms.service.ILossFitInfoService;
import com.sinosoft.ms.service.ILossRepairInfoService;
import com.sinosoft.ms.service.ILossService;
import com.sinosoft.ms.service.ILossVehicleService;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.RegistThirdCarDatabase;
import com.sinosoft.ms.utils.xml.LossUpdateParse;
import com.sinosoft.ms.utils.xml.RequestXml;

/**
 * 系统名：移动查勘
 * 子系统名：
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午10:41:43
 */

public class LossService extends BasicHttp implements ILossService{
	private DeflossData deflossData = null;
	private LossVehicle lossVehicle = null;
	private List<LossVehicleInsurance> lossVehicleInsurances = null;
	private BankInfo bankInfo = null;
	private BasicVehicle basicVehicle = null;
	private List<LossRepairInfoItem> lossRepairInfoItems = null;
	private List<LossFitInfoItem> lossFitInfoItems = null;
	private List<LossAssistInfoItem> lossAssistInfoItems = null;
	
	@Override
	public int updataLossStatus(String taskId,String registId,  Context context) throws Exception {
		int result = 0;
		//定损基本信息
		IDeflossDataService deflossDataService = new DeflossDataService();
		deflossData = deflossDataService.getByTaskId(taskId);
		//定损银行信息
		IBankInfoService bankInfoService = new BankInfoService();
		bankInfo = bankInfoService.getByTaskId(taskId);
		if(null == bankInfo){
			bankInfo = new BankInfo();
			bankInfo.init();
		}
		
		//更新险别信息
		if(StringUtils.isNotEmpty(deflossData.getInsureTermCode())){
			deflossDataService.updateInsureCode(deflossData);
		}
		//定损车辆信息
		ILossVehicleService lossVehicleService = new LossVehicleService();
		lossVehicle = lossVehicleService.getByRegistNo(taskId);
		lossVehicleInsurances = lossVehicleService.getById((int) lossVehicle.getId());
		lossVehicle.setLossVehicleInsuranceList(lossVehicleInsurances);
		
		if(null!=lossVehicle && StringUtils.isEmpty(lossVehicle.getIndemnityDutyRate())){
			String key = toDutyRate(lossVehicle.getIndemnityDuty());
			lossVehicle.setIndemnityDutyRate(key);
		}
		
		if(null!=lossVehicle && StringUtils.isEmpty(lossVehicle.getLossType())){
			String lossType = toLossType(deflossData.getInsureTermCode());
			lossVehicle.setLossType(lossType);
		}
		
		
		//车辆基本信息
		IBasicVehicleService basicVehicleService = new BasicVehicleService();
		basicVehicle = basicVehicleService.getByRegistNo(taskId);
		
		Map<String, Map<String, String>> dicMap = DictCashe.getInstant().getDict();
		String cetainLossType = SpinnerUtils.getValue(AppConstants.cetainLossType,
						dicMap.get("CertainLossType"));
		// 初始化险别信息
		if (StringUtils.isNotEmpty(cetainLossType)
				&& cetainLossType.equals("一次性协议定损")) {
			
			KindCodeDataService kindCodeDataService = new KindCodeDataService();
			String vehkindCode = deflossData.getInsureTermCode() + "";
			List<KindCodeData> kindCodeList = kindCodeDataService
					.getById(deflossData.getId());
			Map<String,String> insureMap = new HashMap<String, String>();
			
			if (null != kindCodeList && !kindCodeList.isEmpty()) {
				Iterator iter = kindCodeList.iterator();
				while(iter.hasNext()){
					KindCodeData kindCodeData = (KindCodeData)iter.next();
					insureMap.put(kindCodeData.getInsureTermCode(),kindCodeData.getInsureTerm());
				}
			}else{
				insureMap.put("02", "商业第三者责任险");
				insureMap.put("BZ", "机动车交通事故责任强制险");
			}
				String defLossCarType = lossVehicle.getDefLossCarType();
				String vehkind = "";
				if (StringUtils.isEmpty(defLossCarType)
						&& defLossCarType.equals("010")) {
					vehkind = SpinnerUtils.getKey("商业第三者责任险",
							insureMap);
					deflossData.setInsureTermCode(vehkind);
					deflossData.setInsureTerm("商业第三者责任险");
				} else {
					vehkind = SpinnerUtils.getKey("车辆损失险",
							insureMap);
					deflossData.setInsureTermCode(vehkind);
					deflossData.setInsureTerm("车辆损失险");
				}
			}
		
		
		//修理信息
		ILossRepairInfoService lossRepairInfoService = new LossRepairInfoService();
		lossRepairInfoItems = lossRepairInfoService.getByRegistNo(taskId);
		
		//换件信息
		ILossFitInfoService lossFitInfoService = new LossFitInfoService();
		lossFitInfoItems = lossFitInfoService
				.getByRegistNo(taskId);
		
		//辅料信息
		ILossAssistInfoService lossAssistInfoService = new LossAssistInfoService();
		lossAssistInfoItems = lossAssistInfoService.getByRegistNo(taskId);
	    User user = UserCashe.getInstant().getUser();
	  
	    RegistThirdCarDatabase registThirdCarDatabase = new RegistThirdCarDatabase(context);
	    List<RegistThirdCarData>  registThirdCarDatas = registThirdCarDatabase.selectRegistThirdCar("regist_third_car_data", taskId);
		
		String xml = new RequestXml().updateLoss(user.getName(), taskId, registId, deflossData,registThirdCarDatas,
				lossVehicle, basicVehicle, lossRepairInfoItems,
				lossFitInfoItems, lossAssistInfoItems,bankInfo);
		new FileUtils().write2SDFromString("", "update_loss.xml", xml);
		sendRequest(xml);
		
		if (inputStream != null) {
			LossUpdateParse parser = new LossUpdateParse();
			parser.parse(inputStream);
			if (0==parser.getResult()) {
				throw new Exception("" + parser.errorMessage);
			}else{
				result = 1;
			}
		}
		return result;
	}

	/**
	 * 根据险别转换损失类型 
	 * 
	 * @param insureTermCode 险别代码
	 * @return
	 */
	private String toLossType(String insureTermCode) {
		String lossType = "01";
		if("F".equals(insureTermCode)){
			lossType = "03";
		}else if("G".equals(insureTermCode)){
			lossType = "04";
		}else if("L".equals(insureTermCode)){
			lossType = "05";
		}else if("Z".equals(insureTermCode)){
			lossType = "07";
		}
		return lossType;
	}

	/**
	 * @param indemnityDuty
	 * @return
	 */
	private String toDutyRate(String indemnityDuty) {
		String result = null;
		if("0".equals(indemnityDuty)){
			result = "100";
		}else if("1".equals(indemnityDuty)){
			result = "70";
		}else if("2".equals(indemnityDuty)){
			result = "50";
		}else if("3".equals(indemnityDuty)){
			result = "30";
		}else if("4".equals(indemnityDuty)){
			result = "0";
		}
		return result;
	}

	

}

