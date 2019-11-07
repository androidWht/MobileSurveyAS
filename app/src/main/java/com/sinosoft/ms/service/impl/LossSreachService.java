package com.sinosoft.ms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.InsuredData;
import com.sinosoft.ms.model.KindCodeData;
import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.LossVehicleInsurance;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.service.IBasicVehicleService;
import com.sinosoft.ms.service.IDeflossDataService;
import com.sinosoft.ms.service.IKindCodeDataService;
import com.sinosoft.ms.service.ILossAssistInfoService;
import com.sinosoft.ms.service.ILossFitInfoService;
import com.sinosoft.ms.service.ILossRepairInfoService;
import com.sinosoft.ms.service.ILossSreachService;
import com.sinosoft.ms.service.ILossVehicleService;
import com.sinosoft.ms.service.InsuredDataInterface;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.db.RegistThirdCarDatabase;

/**
 * 系统名：移动查勘定损 子系统名：定损信息查询服务接口实现 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 05 13:11:00 CST 2013
 */
public class LossSreachService extends BasicHttp implements ILossSreachService
{
	private Activity activity = null;
	private SQLiteDatabase db = null;
	private InsuredData insuredData = null;

	public LossSreachService()
	{
		activity = ActivityManage.getInstance().peek();
	}

	/**
	 * 定损信息查询
	 */
	@Override
	public DeflossData sreach(String requestUser, String registNo, String num, String registId,String time) throws Exception
	{
		DeflossData deflossData = null;

		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.lossSreachXML(requestUser, registNo, registId); // 发送任务中心请求，获取服务器数据

			sendRequest(xmlText); // 解析任务中心列表
//			 new FileUtils().write2SDFromInput("", "query_loss_bak2.xml",
//			 inputStream);
			deflossData = responseXML.lossSreachParse(num, inputStream, new InsuredDataInterface()
			{
				@Override
				public void getInsuredData(InsuredData insuredData)
				{
					LossSreachService.this.insuredData = insuredData;
				}

			});
			// insuredData = responseXML.insuredDataParse(inputStream);

			// 保存定损查询信息
			if (null != deflossData)
			{
				saveLossData(deflossData, registNo, registId,time);
			}
			if (null != insuredData)
			{
				saveInsuredData(insuredData);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			destroy();
		}
		return deflossData;
	}

	private void saveInsuredData(InsuredData insuredData)
	{
		try
		{
			insuredData.init();
			InsuredDataService insuredDataService = new InsuredDataService();
			insuredDataService.save(insuredData);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 保存定损数据
	 * 
	 * @param deflossData
	 * @throws Exception
	 */
	private void saveLossData(DeflossData deflossData, String registNo, String registId,String time) throws Exception
	{
		try
		{
			if (null == deflossData)
			{
				return;
			}
			long id = 0;
			IDeflossDataService deflossDataService = new DeflossDataService();
			// deflossData.setRegistNo(registNo);
			deflossData.init();
			// 没有界面录入所以这里默认为"轻度受损"
			deflossData.setLossLevel("02");
			deflossData.setDefLossDate(time);
			long deflossId = deflossDataService.save(deflossData);
			if (0 == deflossId)
			{
				throw new IllegalArgumentException("保存定损基本信息失败");
			}
			
			// 车辆基本信息
			IBasicVehicleService basicVehicleService = new BasicVehicleService();
			BasicVehicle basicVehicle = deflossData.getBasicVehicle();
			basicVehicle.init();
		//	deflossData.setDefLossDate("");
			basicVehicle.setCaseNo(deflossData.getRegistNo() + "");
			basicVehicle.setRegistNo(deflossData.getTaskId());
			if (0 == basicVehicleService.save(basicVehicle))
			{
				throw new IllegalArgumentException("保存车辆基本信息失败");
			}

			// 定损车辆信息
			LossVehicle lossVehicle = deflossData.getLossVehicle();

			if (null != lossVehicle)
			{
				lossVehicle.init();

				ILossVehicleService lossVehicleService = new LossVehicleService();
				lossVehicle.setRegistNo(deflossData.getTaskId());

				id = lossVehicleService.save(lossVehicle);
				if (0 == id)
				{
					throw new IllegalArgumentException("保存定损车辆信息失败");
				}

				// 车辆承保信息
				List<LossVehicleInsurance> vehicleInsurList = lossVehicle.getLossVehicleInsuranceList();
				if (null != vehicleInsurList && !vehicleInsurList.isEmpty())
				{
					Iterator iter = vehicleInsurList.iterator();
					while (iter.hasNext())
					{
						LossVehicleInsurance insurance = (LossVehicleInsurance) iter.next();
						insurance.init();
						insurance.setLossVehicleId((int) id);
						if (0 == lossVehicleService.save(insurance))
						{
							throw new IllegalArgumentException("保存车辆承保信息失败");
						}
					}
				}
			}

			// 维修信息
			List<LossFitInfoItem> fitInfoList = deflossData.getFitInfoList();
			if (null != fitInfoList && !fitInfoList.isEmpty())
			{
				ILossFitInfoService lossFitInfoService = new LossFitInfoService();

				Iterator iter = fitInfoList.iterator();
				while (iter.hasNext())
				{
					LossFitInfoItem lossFitInfoItem = (LossFitInfoItem) iter.next();
					lossFitInfoItem.init();
					lossFitInfoItem.setRegistNo(deflossData.getTaskId());
					lossFitInfoItem.setRegistId(registId);
					lossFitInfoService.deleteByJyId(lossFitInfoItem.getJyId());
					id = lossFitInfoService.save(lossFitInfoItem);
					if (0 == id)
					{
						throw new IllegalArgumentException("保存维修信息失败");
					}
				}
			}
			// 换件信息
			List<LossRepairInfoItem> repairInfoList = deflossData.getRepairInfoList();
			if (null != repairInfoList && !repairInfoList.isEmpty())
			{
				ILossRepairInfoService lossRepairInfoService = new LossRepairInfoService();
				Iterator iter = repairInfoList.iterator();
				while (iter.hasNext())
				{
					LossRepairInfoItem lossRepairInfoItem = (LossRepairInfoItem) iter.next();
					lossRepairInfoItem.setRegistNo(deflossData.getTaskId());
					lossRepairInfoItem.setRegistId(registId);
					lossRepairInfoService.deleteByJyId(lossRepairInfoItem.getJyId());
					id = lossRepairInfoService.save(lossRepairInfoItem);
					if (0 == id)
					{
						throw new IllegalArgumentException("保存换件信息失败");
					}
				}
			}

			// 辅料信息
			List<LossAssistInfoItem> assistInfoList = deflossData.getAssistInfoList();
			if (null != assistInfoList && !assistInfoList.isEmpty())
			{
				ILossAssistInfoService lossAssistInfoService = new LossAssistInfoService();
				Iterator iter = assistInfoList.iterator();
				while (iter.hasNext())
				{
					LossAssistInfoItem lossAssistInfoItem = (LossAssistInfoItem) iter.next();
					lossAssistInfoItem.init();
					lossAssistInfoItem.setRegistNo(deflossData.getTaskId());
					lossAssistInfoItem.setRegistId(registId);
					lossAssistInfoService.deleteByJyId(lossAssistInfoItem.getJyId());
					id = lossAssistInfoService.save(lossAssistInfoItem);
					if (0 == id)
					{
						throw new IllegalArgumentException("保存辅料信息失败");
					}
				}
			}

			// 险别信息
			List<KindCodeData> kindCodeDataList = deflossData.getKindCodeDataList();
			if (null != kindCodeDataList && !kindCodeDataList.isEmpty() && 0 < kindCodeDataList.size())
			{
				IKindCodeDataService kindCodeDataService = new KindCodeDataService();

				Iterator iter = kindCodeDataList.iterator();
				while (iter.hasNext())
				{
					KindCodeData kindCodeData = (KindCodeData) iter.next();
					kindCodeData.init();
					if (null == kindCodeData)
					{
						continue;
					}
					kindCodeData.setCarId(deflossId);
					id = kindCodeDataService.save(kindCodeData);
					if (0 == id)
					{
						throw new IllegalArgumentException("保存险别信息失败");
					}
				}
			}

			// registThirdCarDatas 信息,遍历三者车信息，找到与数据库中相符的信息并更新
			updateThirdCarData(deflossData.getRegistThirdCarDatas(), registNo);

			// 保单信息
			List<PolicyData> policyDatas = deflossData.getPolicyDatas();

			BankInfoService bankInfoService = new BankInfoService();
			bankInfoService.save(deflossData.getBankInfo());

			// 保单号
			// String policyNo = "" ;
			// if(policyDatas != null && !policyDatas.isEmpty()){
			// policyNo = policyDatas.get(0).getPolicyNo() ;
			// }
			// // 保单是否存在
			// BasicSurveyDatabase policyDB = new BasicSurveyDatabase(activity);
			// if(!policyDB.isPolicyExist("policy_data", policyNo)){
			// if (policyDatas != null) {
			// // 保单信息
			// PolicyDatasDatabase policyDatabase = new PolicyDatasDatabase(
			// activity);
			// policyDatabase.insertPolicyDatas("policy_data", policyDatas);
			// }
			// }
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (null != db)
			{
				db.close();
			}
		}
	}

	public void updateThirdCarData(List<RegistThirdCarData> list, String registNo) throws Exception
	{
		if (null == list)
		{
			return;
		}
		RegistThirdCarDatabase registThirdCarDatabase = new RegistThirdCarDatabase(activity);
		// 服务器是否返回了新内容
		boolean isThirdCarRefresh = false;

		List<RegistThirdCarData> thirdCarList = registThirdCarDatabase.selectRegistThirdCar("regist_third_car_data", registNo);
		// 将本地数据库列表换成Map
		Map<String, RegistThirdCarData> thirdCarMap = new HashMap<String, RegistThirdCarData>();
		for (RegistThirdCarData data : thirdCarList)
		{
			thirdCarMap.put(data.getBrandName(), data);
		}

		// 遍历服务器列表
		for (RegistThirdCarData data : list)
		{
			// 将服务器的列表新增到Map中
			if (null == thirdCarMap.get(data.getBrandName()))
			{
				data.init();
			}
			thirdCarMap.put(data.getBrandName(), data);
		}

		// 将Map转换为List，此时Map包含服务器的内容，以及本地缓存中服务器没有的内容
		// 新三者车列表
		List<RegistThirdCarData> newCarList = new ArrayList<RegistThirdCarData>();
		Iterator<String> iter = thirdCarMap.keySet().iterator();
		while (iter.hasNext())
		{
			String brandName = (String) iter.next();
			RegistThirdCarData value = thirdCarMap.get(brandName);
			newCarList.add(value);
		}
		registThirdCarDatabase.deleteRegistThirdCar("regist_third_car_data", registNo);
		registThirdCarDatabase.insertRegistThirdCar("regist_third_car_data", newCarList);

	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ms.service.ILossSreachService#sreach(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DeflossData sreach(String requestUser, String taskId,
			String registNo, String registId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
