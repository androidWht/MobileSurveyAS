package com.sinosoft.ms.service;

import java.util.List;

import android.content.Context;

import com.sinosoft.ms.model.Item;


/**
 * 系统名：移动查勘定损 子系统名：定损服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author wuhaijie
 * @createTime 16 Jan 2013 16:50:18
 */
public interface IRepairFactoryService {

	/**
	 * 更新定损状态 说明：本方法执行定损状态的更新，由系统程序调用 调用时机： 1.在现场定损完成时
	 * 
	 * @param registNo
	 *            报案号
	 * @return 修理厂列表
	 * @throws Exception
	 */
	public List<Item> getRepairFactory(String keyword,Context context) throws Exception;
	
	
}
