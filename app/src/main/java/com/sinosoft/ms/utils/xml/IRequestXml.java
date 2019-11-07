package com.sinosoft.ms.utils.xml;

import java.util.List;

import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.sub.ParamBean;
import com.sinosoft.ms.utils.LocationService;

/**
 * 系统名：移动查勘定损 子系统名：所有的请求xml都列在这里 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午10:58:47
 */

public interface IRequestXml {
    //*登陆 start
	/**
	 * 请求登陆用户的xml
	 * 
	 * @param user
	 *            当前用户
	 */
	public String requestUserXML(User user);

	// *登陆 end
	
	
	
	/**
	 * 用户退出
	 * @param user
	 * @return
	 */
	public String requestExitLogin(User user);	
	
	
	
	
	//** 任务中心 start 
	/**
	 * 请求任务中心的任务列表
	 * 
	 * @param type筛选的类型
	 * @return 返回请求的xml字符串
	 */
	public String requestTaskListXML(int type);
	
	/**
	 * 请求任务中心的任务列表
	 * 
	 * @param type筛选的类型
	 * @param registNo 报案号后几位
	 * @return 返回请求的xml字符串
	 */
	public String requestTaskListXML(int type,String registNo);

	/**
	 * 拒绝任务中心的任务
	 * 
	 * @param reportNo
	 *            拒绝任务的报案号
	 * @param 拒绝理由
	 * @return 返回请求的xml字符串
	 */
	public String requestRejectTaskXML(String reportNo, String reason);

	/**
	 * 接受任务中心的任务
	 * 
	 * @param reportNo
	 *            接收任务的报案号
	 * @return 请求的xml字符串
	 */
	public String requestReceivingTaskXML(String reportNo);

	/**
	 * 改派任务 任务中心的任务
	 * 
	 * @param reportNo
	 *            改派任务的报案号
	 * @return 请求xml字符串
	 */
	public String requestReassignTaskXml(String reportNo);

	/**
	 * 归档任务 任务中心的任务
	 * 
	 * @param reportNo
	 *            任务的报案号
	 * @return 请求的字符串
	 */
	public String requestArchiveTaskXml(String reportNo);

	/**
	 * 到达处理 任务中心的任务
	 * 
	 * @param reportNo
	 *            任务的报案号
	 * @return 请求的字符串
	 */
	public String requestArrivalProcessTaskXml(String reportNo);

	//**任务中心 end
	
	//*** 数据查询 start
	/**
	 * 数据查询
	 * @param user 用户名
	 * @return 请求的字符串
	 */
	public String requestDataSearch(ParamBean paramBean);
	
	/**
	 * 实效信息
	 * @param reportNo
	 * @return
	 */
	public String requestPrescriptionXML(String reportNo);
	
	//***数据查询 end
	
	//**** 心跳 start
	
	/**
	 * 心跳 （取得任务数）
	 * @param user
	 * @return
	 */
	public String requestTaskNumXML(User user);
	
	
	
	
	
	
	//****  心跳 end
	
	//***** 字典信息 start
	
	/**
	 * 字典信息
	 * @param user 当前用户
	 * @return
	 */
	public String requestDicInfoXML(User user,String synTime,String deptNo);
	
	//***** 字典信息 end
	
	//****** 案件中心 start
	
	/**
	 * 案件中心 取得请求的xml
	 * @param type 类型（查勘，定损....）
	 * @return
	 */
	public String requestCaseCenterListXML(int type);
	
	/**
	 * 案件中心 请求查勘信息 xml
	 * @param reportNo 报案号
	 * @param reportId 报案id号
	 * @return
	 */
	public String requestCaseCenterSurveyInfoXML(String reportNo,String registId);
	
	/**
	 * 获取定损信息查询XML
	 * 
	 * @param requestUser
	 *            请求用户
	 * @param registNo
	 *            报案号
	 * @return xml报文信息
	 */
	public String lossSreachXML(String requestUser,String registNo, String registId);
	/**
	 * 获取核損信息查询XML
	 * 
	 * @param requestUser
	 *            请求用户
	 * @param registNo
	 *            报案号
	 * @return xml报文信息
	 */
	public String confirmdamageSreachXML(String requestUser,String registNo, String registId);
	
	/**
	 * 获取换件价格查询XML
	 * 
	 * @param requestUser 请求用户
	 * @param type
	 *            1.一类、二类修理厂  2.特约修理厂
	 * @param carGroupId
	 *            车组ID
	 * @param areaId
	 *            区域ID
	 * @param typeName
	 *            零件类型名称
	 * @return 换件价格查询XML
	 */
	public String lossChangeXML(String requestUser,int type,String carGroupId, String areaId,
			String typeName);
	
	/**
	 * 定损信息更新
	 * @param reportNo
	 * @param deflossData
	 * @param lossVehicle
	 * @param basicVehicle
	 * @param lossRepairInfoItems
	 * @param lossFitInfoItems
	 * @param lossAssistInfoItems
	 * @return
	 */
	public String updateLoss(String user,String reportNo,String registId, DeflossData deflossData,List<RegistThirdCarData> registThirdCarDatas,
			LossVehicle lossVehicle, BasicVehicle basicVehicle,
			List<LossRepairInfoItem> lossRepairInfoItems,
			List<LossFitInfoItem> lossFitInfoItems,
			List<LossAssistInfoItem> lossAssistInfoItems,BankInfo bankInfo);
	
	public String updateCaseCenterSurveyInfoXML(String registId, BasicSurvey basicSurvet,
			List<RegistThirdCarData> registThirdCarDatas,
			List<LiabilityRatio> liabilityRatios, List<CarData> carDatas,
			List<PropData> propDatas, List<PersonData> personDatas,
			List<PolicyData> policyDatas);
	
	public String rreceiceUpdateXML(String requestUser, int type,
			String serverIds);

	/**
	 * 案件状态查询XML
	 * 
	 * @param requestUser
	 *            请求用户
	 * @param registNo
	 *            报案号
	 * @return 查询XML报文
	 */
	public String caseQueryXml(String requestUser, String registNo, String registId, String taskId);
	
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
	 * 				损伤部位零件Id
	 * @param keyWord
	 *            关键字
	 * @return 换件价格XML报文
	 */
	public String fitListXML(String requestUser, String type,
			String vehicleType, String carGroupId, String areaId,
			String typeName, String damagePartId, String keyWord,int tag);
	
	/**
	 * 影像资料更新报文
	 * 
	 * @param user
	 *            用户信息
	 * @param imageDatas
	 *            影像资料
	 * @return 影像资料更新报文
	 */
	public String updateImageXml(User user, List<ImageCenterBean> imageDatas);
	
	public String updateDocumentsXml(User user, String registNo);
	
	/**
	 * 价格方案
	 * 
	 * @param requestUser
	 *            用户名
	 * @param deptCode
	 *            机构代码
	 * @return 价格方案XML报文
	 */
	public String pricePlanXML(String requestUser, String deptCode);
	
	/**
	 * 请求查勘派工状态更新
	 * 
	 * @param type筛选的类型
	 * @return 返回请求的xml字符串
	 */
	public String workStatusXML(RegistData registData, String refusalReason);
	
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
			String vehseriName) ;

	/**
	 * 
	 * VIN查询
	 * 
	 * @return
	 */
	public String requestVinXML(String taskId, String registId,String licenseNo,String vinNo);
	
	/**
	 * 
	 * 银行网点查询
	 * 
	 * @param requestUser
	 * @param bankInfo
	 * @return
	 */
	public String lossBankPointQueryXml(String requestUser, BankInfo bankInfo);
	
	
	public String lossCheckNuclei(String taskId, String registId);
	
	/**
	 * 版本更新
	 * @return
	 */
	public String requestVersionXML();
	
	/**
	 * 获取修理厂列表
	 * @return
	 */
	public String requestRepairFactoryXML(String keyword);
}
   
