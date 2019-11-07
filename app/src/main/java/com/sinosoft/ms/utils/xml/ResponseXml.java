package com.sinosoft.ms.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.DicInfoBean;
import com.sinosoft.ms.model.InsuredData;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.po.NodeData;
import com.sinosoft.ms.service.InsuredDataInterface;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午11:59:05
 */

public class ResponseXml implements IResponseXml
{

	/**
	 * 解析任务中心的列表数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @return 返回的列表数据
	 * @throws Exception
	 */
	public List parserTaskList(InputStream in) throws Exception
	{
		List list = null;

		
		//TaskCenterPolicyListParse parser1= new TaskCenterPolicyListParse();
		//parser1.parse(in);
		//list = parser1.getResult();
		
		
		TaskCenterListParse parser = new TaskCenterListParse();
		parser.parse(in);
		list = parser.getResult();
		
		
		
		// String xml = getString(in);
		// LogFatory.d("xml", "任务中心取得的"+xml);

		return list;
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ms.utils.xml.IResponseXml#parserPolicyList(java.io.InputStream)
	 */
	@Override
	public List parserPolicyList(InputStream in) throws Exception
	{
		List list = null;

		
		TaskCenterPolicyListParse parser= new TaskCenterPolicyListParse();
		parser.parse(in);
		list = parser.getResult();
	// TODO Auto-generated method stub
		return list;
	}

	/**
	 * 解析任务中心 拒绝接口 返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	@Override
	public int parserRejectTask(InputStream inputStream) throws Exception
	{
		int result = 0;
		WorkStatusParse workStatusParse = new WorkStatusParse();
		workStatusParse.parse(inputStream);
		result = workStatusParse.getResult();
		return result;
	}

	/**
	 * 解析任务中心 接受任务 返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	@Override
	public void parserReceiveTask(InputStream in) throws Exception
	{

	}

	/**
	 * 解析任务中心 改派任务 返回的数据
	 * 
	 * @param in
	 * @throws Exception
	 */
	public void parserReassignTask(InputStream in) throws Exception
	{

	}

	/**
	 * 解析任务中心 归档任务 返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	@Override
	public void parserAcchiveTask(InputStream in) throws Exception
	{

	}

	/**
	 * 解析任务中心 到达任务处理 返回的数据
	 * 
	 * @param in
	 *            服务器返回的数据
	 * @throws Exception
	 */
	@Override
	public void parserArrivalProcessTask(InputStream in) throws Exception
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 将inputStream 转换为字符
	 * 
	 * @param in
	 *            字节流
	 * @return 字符串
	 * @throws Exception
	 */
	public String getString(InputStream in) throws Exception
	{
		byte[] bty = new byte[1024];
		StringBuffer result = new StringBuffer();
		int length = 0;
		try
		{
			while ((length = in.read(bty)) != -1)
			{
				result.append(new String(bty, 0, length));

			}
		}
		catch (IOException ioe)
		{
			throw new IOException(ioe.getMessage());

		}

		return result.toString();
	}

	@Override
	public DeflossData lossSreachParse(String registNo, InputStream in, InsuredDataInterface listener) throws Exception
	{
		LossSearchParser parser = new LossSearchParser();
		// new FileUtils().write2SDFromInput("", "loss.xml", in);
		parser.parse(in, registNo);
		String responseCode = parser.responseCode;

		if (StringUtils.isNotEmpty(responseCode) && responseCode.equals("0"))
		{
			String errorMsg = parser.errorMessage;
			if (StringUtils.isNotEmpty(errorMsg))
			{
				throw new IllegalArgumentException(errorMsg);
			}
			else
			{
				throw new IllegalArgumentException("错误未知");
			}
		}
		listener.getInsuredData(parser.getInsuredData());
		return parser.getDeflossData();
	}

	@Override
	public InsuredData insuredDataParse(InputStream in, String registNo) throws Exception
	{
		LossSearchParser parser = new LossSearchParser();
		// new FileUtils().write2SDFromInput("", "loss.xml", in);
		parser.parse(in, registNo);
		String responseCode = parser.responseCode;

		if (StringUtils.isNotEmpty(responseCode) && responseCode.equals("0"))
		{
			String errorMsg = parser.errorMessage;
			if (StringUtils.isNotEmpty(errorMsg))
			{
				throw new IllegalArgumentException(errorMsg);
			}
			else
			{
				throw new IllegalArgumentException("错误未知");
			}
		}

		return parser.getInsuredData();
	}

	@Override
	public List<LossFitInfoItem> changeQueryParse(InputStream inputStream) throws Exception
	{
		ChangeQueryParser parser = new ChangeQueryParser();
		parser.parse(inputStream);
		return parser.getLossFitInfoItemList();
	}

	@Override
	public List<NodeData> caseQueryParse(InputStream inputStream) throws Exception
	{
		CaseQueryParser queryParser = new CaseQueryParser();
		queryParser.parse(inputStream);
		return queryParser.getNodeList();
	}

	@Override
	public List<DicInfoBean> dicInfoParser(InputStream inputStream) throws Exception
	{
		DicInfoParser parser = new DicInfoParser();
		// new FileUtils().write2SDFromInput("", "dict.xml", inputStream);
		parser.parse(inputStream);
		return parser.getResult();
	}

	@Override
	public List<BankInfo> bankPointParse(InputStream in) throws Exception
	{
		BankPointParser parser = new BankPointParser();
		// new FileUtils().write2SDFromInput("", "loss.xml", in);
		parser.parse(in);
		String responseCode = parser.responseCode;

		if (StringUtils.isNotEmpty(responseCode) && responseCode.equals("0"))
		{
			String errorMsg = parser.errorMessage;
			if (StringUtils.isNotEmpty(errorMsg))
			{
				throw new IllegalArgumentException(errorMsg);
			}
			else
			{
				throw new IllegalArgumentException("错误未知");
			}
		}

		return parser.getBankInfoList();
	}

	@Override
	public int lossCheckNucleiParse(InputStream inputStream) throws Exception
	{
		// TODO Auto-generated method stub
		LossCheckNucleiParser parser = new LossCheckNucleiParser();
		// new FileUtils().write2SDFromInput("", "loss.xml", in);
		parser.parse(inputStream);
		String responseCode = parser.responseCode;

		if (StringUtils.isNotEmpty(responseCode) && responseCode.equals("0"))
		{
			String errorMsg = parser.errorMessage;
			if (StringUtils.isNotEmpty(errorMsg))
			{
				throw new IllegalArgumentException(errorMsg);
			}
			else
			{
				throw new IllegalArgumentException("错误未知");
			}
		}

		return parser.getCheckNucleiResult();
	}

}
