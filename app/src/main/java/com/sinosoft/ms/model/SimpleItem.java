package com.sinosoft.ms.model;
/**
 * 系统名：移动查勘定损系统 
 * 子系统名：简易配置项
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 2013-5-31 下午12:57:17
 */

public class SimpleItem {
	private String typeName;
	private String typeCode;// 参数代码
	private String valueCode;// 值代码
	private String valueName;// 值名称
	private String price;//价格
	private String remake;//备注
	
	public SimpleItem(String typeName, String typeCode, String price) {
		super();
		this.typeName = typeName;
		this.typeCode = typeCode;
		this.price = price;
	}
	
	public SimpleItem(String typeName, String typeCode, String valueCode,
			String valueName, String price, String remake) {
		super();
		this.typeName = typeName;
		this.typeCode = typeCode;
		this.valueCode = valueCode;
		this.valueName = valueName;
		this.price = price;
		this.remake = remake;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getValueCode() {
		return valueCode;
	}
	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRemake() {
		return remake;
	}
	public void setRemake(String remake) {
		this.remake = remake;
	}
	
}

