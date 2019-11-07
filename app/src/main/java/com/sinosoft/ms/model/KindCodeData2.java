package com.sinosoft.ms.model;

import com.sinosoft.ms.utils.StringUtils;


/**
 * 险别信息数据结构类
 * @author Administrator
 *
 */ 
public class KindCodeData2 implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String carId;
	private String insureTerm;
	private String insureTermCode;
	
	private DeflossData deflossData;//定损基本信息
	
	/** default constructor */
	public KindCodeData2() {
	}

	/** full constructor */
	public KindCodeData2(String carId, String insureTerm, String insureTermCode) {
		this.carId = carId;
		this.insureTerm = insureTerm;
		this.insureTermCode = insureTermCode;
	}

	// Property accessors
	public String getCarId() {
		return this.carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}
	
	public String getInsureTerm() {
		return this.insureTerm;
	}

	public void setInsureTerm(String insureTerm) {
		this.insureTerm = insureTerm;
	}

	public String getInsureTermCode() {
		return this.insureTermCode;
	}

	public void setInsureTermCode(String insureTermCode) {
		this.insureTermCode = insureTermCode;
	}

	public DeflossData getDeflossData() {
		return deflossData;
	}

	public void setDeflossData(DeflossData deflossData) {
		this.deflossData = deflossData;
	}
  public void init(){
	    insureTerm=insureTerm==null?"":insureTerm;
		insureTermCode=insureTermCode==null?"":insureTermCode;
		carId=StringUtils.isEmpty(carId)?"":carId;
  }
}