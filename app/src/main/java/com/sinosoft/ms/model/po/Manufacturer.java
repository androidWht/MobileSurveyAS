package com.sinosoft.ms.model.po;

/**
 * 厂家信息
 */
public class Manufacturer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;// 编号
	private String name;// 名字
	private String code;// 代码
	private String zmIndex;//字母索引
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getZmIndex() {
		return zmIndex;
	}

	public void setZmIndex(String zmIndex) {
		this.zmIndex = zmIndex;
	}

}
