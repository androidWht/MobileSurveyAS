package com.sinosoft.ms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统名：自助查勘系统 子系统名： 历史案件单例 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 2013-7-12 下午2:22:15
 */
public class LocalImgCache {
	private static LocalImgCache instance = null;

	/** 历史案件信息 **/
	private List<String> imgList = null;

	/**
	 * 单例模式-获取查勘信息
	 * 
	 * @return
	 */
	public static LocalImgCache getInstance() {
		if (instance == null) {
			instance = new LocalImgCache();
			instance.imgList = new ArrayList<String>();
		}
		return instance;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

}
