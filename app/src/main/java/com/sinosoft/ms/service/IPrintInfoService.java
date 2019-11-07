package com.sinosoft.ms.service;

import android.content.Context;

import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.RegistData;

/**
 * 系统名：移动查勘定损 
 * 子系统名：打印信息服务接口
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午10:27:51
 */

public interface IPrintInfoService {
	
	/**
	 * 查勘凭证
	 * 
	 * @param taskId 
	 * 			   任务ID
	 * @return 查勘凭证信息
	 */
	public String surveyVoucher(Context context,String taskId,RegistData registData);
	
	/**
	 * 定损凭证
	 * 
	 * @param taskId 
	 * 			   任务ID
	 * @param reportorMobile 
	 * 			  报案人手机号码 
	 * @return 定损凭证信息
	 */
	public String lossVoucher(Context context,DeflossData deflossData,String taskId,String reportorMobile);
}

