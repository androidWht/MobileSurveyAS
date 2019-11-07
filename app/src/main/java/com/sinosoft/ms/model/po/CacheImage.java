package com.sinosoft.ms.model.po;

import java.util.ArrayList;
import java.util.List;


/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author pei
 * @createTime 下午2:38:50
 */

public class CacheImage {
	
	private static CacheImage instant = null;
	private List<ImageBean> imageBeans = null;
	
	/**
	 * 获取单例
	 * @return
	 */
	public static CacheImage getInstant() {
		if (null == instant) {
			instant = new CacheImage();
			instant.imageBeans = new ArrayList<ImageBean>();
		}
		return instant;
	}

	/**
	 * @return the imageBeans
	 */
	public List<ImageBean> getImageBeans() {
		return imageBeans;
	}

	/**
	 * @param imageBeans the imageBeans to set
	 */
	public void setImageBeans(List<ImageBean> imageBeans) {
		this.imageBeans = imageBeans;
	}


}

