package com.sinosoft.ms.utils.xml;

import java.io.InputStream;
import java.util.List;

import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.DicInfoBean;
import com.sinosoft.ms.model.InsuredData;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.po.NodeData;
import com.sinosoft.ms.service.InsuredDataInterface;

/**
 * 系统名：移动查勘定损 子系统名：解析所有的返回xml 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午11:59:05
 */

public interface IResponseXml {

	/**
	 * 解析任务中心的列表数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @return 返回的列表数据
	 * @throws Exception
	 */
	public List parserTaskList(InputStream in) throws Exception;
	
	/**
	 * 解析保单
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @return 返回的列表数据
	 * @throws Exception
	 */	
	public List parserPolicyList(InputStream in) throws Exception;

	/**
	 * 解析任务中心拒绝接口返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	public int parserRejectTask(InputStream in) throws Exception;

	/**
	 * 解析任务中心接受任务返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	public void parserReceiveTask(InputStream in) throws Exception;

	/**
	 * 解析任务中心 改派任务 返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	public void parserReassignTask(InputStream in) throws Exception;

	/**
	 * 解析任务中心 归档任务 返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	public void parserAcchiveTask(InputStream in) throws Exception;

	/**
	 * 解析任务中心 到达任务处理 返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	public void parserArrivalProcessTask(InputStream in) throws Exception;
	
	/**
	 * 定损信息查询解释
	 * 
	 * @param in
	 *            服务器响应资源
	 * @return 定损信息
	 * @throws Exception
	 */
	public DeflossData lossSreachParse(String registNo,InputStream in,InsuredDataInterface insuredDataListener) throws Exception;

	public List<LossFitInfoItem> changeQueryParse(InputStream inputStream)throws Exception;

	/**
	 * 案件状态查询解释
	 * 
	 * @param inputStream
	 *            服务端返回报文
	 * @return 案件状态列表
	 */
	public List<NodeData> caseQueryParse(InputStream inputStream) throws Exception;

	/**
	 * 影像中心服务接口解释
	 * 
	 * @param inputStream
	 *            服务端返回报文
	 * @return 影像中心列表
	 */
	public List<DicInfoBean> dicInfoParser(InputStream inputStream) throws Exception;

	/**
	 * 定损银行网点解析
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public List<BankInfo> bankPointParse(InputStream inputStream) throws Exception;

	/**
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public InsuredData insuredDataParse(InputStream in,String registNo) throws Exception;
	
	/**
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public int lossCheckNucleiParse(InputStream inputStream) throws Exception;
}
