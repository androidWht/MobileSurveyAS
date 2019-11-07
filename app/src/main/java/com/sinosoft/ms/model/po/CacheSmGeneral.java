package com.sinosoft.ms.model.po;


/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author pei
 * @createTime 下午2:38:50
 */

public class CacheSmGeneral {
	
	private static CacheSmGeneral instant = null;
	private SmGeneral smGeneral = null;
	
	/**
	 * 获取单例
	 * @return
	 */
	public static CacheSmGeneral getInstant() {
		if (null == instant) {
			instant = new CacheSmGeneral();
			instant.smGeneral = new SmGeneral();
		}
		return instant;
	}

	/**
	 * @return the smGeneral
	 */
	public SmGeneral getSmGeneral() {
		return smGeneral;
	}

	/**
	 * @param smGeneral the smGeneral to set
	 */
	public void setSmGeneral(SmGeneral smGeneral) {
		this.smGeneral = smGeneral;
	}

}

