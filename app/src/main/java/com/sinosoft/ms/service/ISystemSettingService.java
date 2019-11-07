package com.sinosoft.ms.service;

/**
 * 系统名：移动查勘定损 子系统名：系统设置服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 16:19:24
 */
public interface ISystemSettingService {

	/**
	 * 清除缓存 说明：清除定时设置照片维护操作和系统产生临时文件
	 * 
	 * @return true清除成功 false清除失败
	 * @throws Exception
	 */
	public boolean clearCache() throws Exception;

	/**
	 * 检查版本更新
	 * 
	 * @return 1更新成功 0更新失败
	 * @throws Exception
	 */
	public int checkVersionUpdate() throws Exception;
}
