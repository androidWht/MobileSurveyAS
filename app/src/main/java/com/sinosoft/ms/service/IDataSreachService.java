package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.AgeingStatBean;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.sub.ParamBean;

/**
 * 系统名：移动查勘定损 子系统名：数据查询服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 15:20:27
 */
public interface IDataSreachService {

	/**
	 * 执行数据查询 说明：实现分页功能
	 * 
	 * @param paramBean
	 *            参数
	 * @return 数据查询结果
	 * @throws Exception
	 */
	public List<RegistData> execute(ParamBean paramBean) throws Exception;

	/**
	 * 获取实效列表信息
	 * 
	 * @param reportNo
	 *            报案号
	 * @return 实效列表信息
	 * @throws Exception
	 */
	public List<AgeingStatBean> getPrescriptionListByReportNo(String reportNo)
			throws Exception;

}
