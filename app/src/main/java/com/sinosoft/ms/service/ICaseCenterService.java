package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.SurveyInfo;
import com.sinosoft.ms.model.sub.BasicTask;
import com.sinosoft.ms.model.sub.ReportBean;
import com.sinosoft.ms.model.sub.SurveyBean;

/**
 * 系统名：移动查勘定损 子系统名：案件中心服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 11:27:24
 */
public interface ICaseCenterService {

	/**
	 * 根据案件类型获取案件中心列表数据 说明：本方法用来获取案件中心列表数据，反馈错误提示与内 容将以异常形式抛出，需要对异常进行捕获处理。
	 * 
	 * @param type
	 *            任务类型 0.全部 1.查勘任务 2.简易定损 3.未完成 4.己完成
	 * @return 任务中心列表
	 * @throws Exception
	 */
	public List<RegistData> getCaseCenterListByType(int type) throws Exception;

	/**
	 * 报案信息处理 说明：本方法在用户查勘处理时做报案使用，反馈错误提示与内 容将以异常形式抛出，需要对异常进行捕获处理。
	 * 
	 * @param reportBean
	 *            报案信息
	 * @return 0为报案失败 1为成功
	 * @throws Exception
	 */
	public int report(ReportBean reportBean) throws Exception;

	/**
	 * 定损任务完成 说明：本方法在用户定损任务完成时使用，反馈错误提示与内 容将以异常形式抛出，需要对异常进行捕获处理。
	 * 
	 * @param basicTask
	 *            基本信息
	 * @param remark
	 *            定损备注
	 * @return 0为定损任务未完成 1为定损任务完成
	 * @throws Exception
	 */
	public int maintenanceTasks(BasicTask basicTask, String remark)
			throws Exception;

	/**
	 * 撤消案件 说明：本方法在用户撤消案件时使用，反馈错误提示与内 容将以异常形式抛出，需要对异常进行捕获处理。
	 * 
	 * @param basicTask
	 *            基本信息
	 * @param someReasons
	 *            撤案原因：
	 * @return 0为撤消案件失败 1为成功
	 * @throws Exception
	 */
	public int cancelCase(BasicTask basicTask, String someReasons)
			throws Exception;

	/**
	 * 查勘处理 说明：本方法做用户查勘处理时使用，反馈错误提示与内 容将以异常形式抛出，需要对异常进行捕获处理。
	 * 
	 * @param surveyBean
	 *            查勘信息
	 * @return 0为失败 1为成功
	 * @throws Exception
	 */
	public int surveyProcess(SurveyBean surveyBean) throws Exception;

	/**
	 * 查勘信息 查询
	 * @param reportNo 报案号
	 * @param registId 报案id号
	 */
	public SurveyInfo querySurveyInfo(String reportNo, String registId) throws Exception;
	
	/**
	 * 查勘信息 更新
	 * @return
	 * @throws Exception
	 */
	public int updateSurveyInfo(String registId, BasicSurvey basicSurvet,List<RegistThirdCarData> registThirdCarDatas,
			List<LiabilityRatio> liabilityRatios, List<CarData> carDatas,
			List<PropData> propDatas, List<PersonData> personDatas,
			List<PolicyData> policyDatas) throws Exception;
}
