package com.sinosoft.ms.service;

import android.content.Context;


/**
 * 系统名：移动查勘定损 子系统名：定损服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 16:50:18
 */
public interface ILossService {

	/**
	 * 更新定损状态 说明：本方法执行定损状态的更新，由系统程序调用 调用时机： 1.在现场定损完成时
	 * 
	 * @param registNo
	 *            报案号
	 * @return 0更新失败 1更新成功
	 * @throws Exception
	 */
	public int updataLossStatus(String registNo,String registId,Context context) throws Exception;
	
	
}
