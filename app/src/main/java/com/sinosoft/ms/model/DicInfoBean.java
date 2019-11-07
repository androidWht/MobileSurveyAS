package com.sinosoft.ms.model;

import java.util.List;

/**
 * 系统名：移动查勘 子系统名：字典信息 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午5:57:29
 */

public class DicInfoBean {
	private String type;//字典类型
	private String name;//字典名字
	private List<Item> list;//字典列表

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the list
	 */
	public List<Item> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<Item> list) {
		this.list = list;
	}

}
