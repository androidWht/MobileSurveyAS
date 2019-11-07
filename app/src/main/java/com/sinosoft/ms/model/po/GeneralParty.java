package com.sinosoft.ms.model.po;

/**
 * 通用第三方类
 */
public class GeneralParty implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;// 编号
	private String name;// 名字
	private String code;// 代码
	
	public GeneralParty(){
		
	}
	/**
	 * @param id
	 * @param name
	 * @param code
	 */
	public GeneralParty(String id, String name, String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}

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
