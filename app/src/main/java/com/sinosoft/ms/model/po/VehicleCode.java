package com.sinosoft.ms.model.po;

/**
 * 车型编码
 */
public class VehicleCode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;// 编号
	private String name;// 名字
	private String code;// 代码

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

}
