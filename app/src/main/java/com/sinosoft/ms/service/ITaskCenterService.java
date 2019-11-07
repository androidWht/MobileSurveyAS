package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.TaskBean;

/**
 * 系统名：移动查勘定损 子系统名：任务中心服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 10:52:24
 */
public interface ITaskCenterService {

	/**
	 * 根据任务类型获取任务中心列表数据 说明：本方法用来获取任务中心列表数据，反馈错误提示与内 容将以异常形式抛出，需要对异常进行捕获处理。
	 * 
	 * @param type
	 *            任务类型 0.全部任务 1.己完成 2.待接收 3.己接收 4.处理中 5.未联系
	 * @return 任务中心列表
	 * @throws Exception
	 */
	public List<RegistData> getTaskCenterListByType(int type) throws Exception;
	
	public List<PolicyData> getTaskCenterListByTypeRegistNo(int type, String registNo) throws Exception;
	/**
	 * 根据任务类型获取任务中心列表数据 说明：本方法用来获取任务中心列表数据，反馈错误提示与内 容将以异常形式抛出，需要对异常进行捕获处理。
	 * 
	 * @param type
	 *            任务类型 0.全部任务 1.己完成 2.待接收 3.己接收 4.处理中 5.未联系
	 * @param registNo 根据报案号后几位搜索列表          
	 * @return 任务中心列表
	 * @throws Exception
	 */
	public List<RegistData> getTaskCenterListByType(int type,String registNo) throws Exception;

	/**
	 * 拒绝任务
	 * 
	 * @param registData
	 *            任务基本信息
	 * @param refusalReason
	 *            拒绝原因
	 * @return 0为拒绝失败 1为成功
	 * @throws Exception
	 */
	public int rejectTtask(RegistData registData, String refusalReason)
			throws Exception;

	/**
	 * 接收任务
	 * 
	 * @param registData
	 *            任务基本信息
	 * @return 0为拒绝失败 1为成功
	 * @throws Exception
	 */
	public int receivingTask(RegistData registData) throws Exception;

	/**
	 * 归档任务
	 * 
	 * @param basicTask
	 *            任务基本信息
	 * @return 0为拒绝失败 1为成功
	 * @throws Exception
	 */
	public int archiveTask(RegistData registData) throws Exception;

	/**
	 * 查看任务
	 * 
	 * @param reportNo
	 *            报案号
	 * @return 任务中心信息
	 * @throws Exception
	 */
	public TaskBean lookTask(String reportNo) throws Exception;

	/**
	 * 撤案任务
	 * 
	 * @param registData
	 *            案件基本信息
	 * @return
	 */
	public int revokeTask(RegistData registData, String revokeReason)
			throws Exception;

	/**
	 * 改派任务
	 * 
	 * @param registData
	 *            任务基本信息
	 * @param changeReason
	 *            改派原因
	 * @return 0为改派失败 1为成功
	 * @throws Exception
	 */
	public int reassignedTask(RegistData registData, String changeReason)
			throws Exception;

	/**
	 * 到达任务
	 * 
	 * @param basicTask
	 *            任务基本信息
	 * @param currAddr
	 *            当前地点
	 * @return 0为到达失败 1为成功
	 * @throws Exception
	 */
	public int arrivalProcessTask(RegistData registData, String currAddr)
			throws Exception;
	
   /**
    * 电话联系
    * @param registData
    * @param reason
    * @return
    * @throws Exception
    */
	public int phoneContactTask(RegistData registData,String reason)throws Exception;
	/**
	 * 申请电脑处理
	 * @param registData 案件信息
	 * @param reason 原因
	 * @return 0失败 1成功
	 * @throws Exception 
	 */
	public int changePCSurvey(RegistData registData,String reason) throws Exception;
	
}
