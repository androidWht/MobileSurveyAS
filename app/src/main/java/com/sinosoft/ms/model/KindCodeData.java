package com.sinosoft.ms.model;


/**
 * 险别信息数据结构类
 * @author Administrator
 *
 */ 
public class KindCodeData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long carId;
	private String insureTerm;
	private String insureTermCode;
	
	private DeflossData deflossData;//定损基本信息
	
	/** default constructor */
	public KindCodeData() {
	}

	/** full constructor */
	public KindCodeData(long carId, String insureTerm, String insureTermCode) {
		this.carId = carId;
		this.insureTerm = insureTerm;
		this.insureTermCode = insureTermCode;
	}

	// Property accessors
	public long getCarId() {
		return this.carId;
	}

	public void setCarId(long carId) {
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
	  
  }
}