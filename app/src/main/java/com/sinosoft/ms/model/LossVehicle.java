package com.sinosoft.ms.model;

import java.util.List;

/**
 * 定损车辆信息
 */
public class LossVehicle implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String licenseNo;
	private String defLossCarType;
	private String lossType;
	private String carOwner;
	private String enrollDate;
	private String carKindCode;
	private String frameNo;
	private String licenseColorCode;
	private String modelCode;
	private String vinNo;
	private String licenseType;
	private String brandName;
	private String gearboxType;
	private String gasType;
	private String carKindFrom;
	private String engineNo;
	private String insureComCode;
	private String indemDutyCi;
	private String indemnityDuty;
	private String indemnityDutyRate;
	private String registNo;
	
	//新增加 2013-03-01 11:38
	private String insureVehicleCode;//承保车型编码（已经定型的车辆必传）
	private String ComCode;//定损员所属分公司代码
	
	//2014-05-28 新增
	private String licenseColorCodeTo2;	//号牌底色
	private String modelName;		//车型名称

	private List<LossVehicleInsurance> lossVehicleInsuranceList;
	
	// Constructors

	/** default constructor */
	public LossVehicle() {
	}

	/** minimal constructor */
	public LossVehicle(String licenseNo, String defLossCarType,
			String registNo) {
		this.licenseNo = licenseNo;
		this.defLossCarType = defLossCarType;
		this.registNo = registNo;
	}

	/** full constructor */
	public LossVehicle(String licenseNo, String defLossCarType,
			String lossType, String carOwner, String enrollDate,
			String carKindCode, String frameNo, String licenseColorCode,
			String modelCode, String vinNo, String licenseType,
			String brandName, String gearboxType, String gasType,
			String carKindFrom, String engineNo, String insureComCode,
			String indemDutyCi, String indemnityDuty, String indemnityDutyRate,
			String registNo) {
		this.licenseNo = licenseNo;
		this.defLossCarType = defLossCarType;
		this.lossType = lossType;
		this.carOwner = carOwner;
		this.enrollDate = enrollDate;
		this.carKindCode = carKindCode;
		this.frameNo = frameNo;
		this.licenseColorCode = licenseColorCode;
		this.modelCode = modelCode;
		this.vinNo = vinNo;
		this.licenseType = licenseType;
		this.brandName = brandName;
		this.gearboxType = gearboxType;
		this.gasType = gasType;
		this.carKindFrom = carKindFrom;
		this.engineNo = engineNo;
		this.insureComCode = insureComCode;
		this.indemDutyCi = indemDutyCi;
		this.indemnityDuty = indemnityDuty;
		this.indemnityDutyRate = indemnityDutyRate;
		this.registNo = registNo;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getRegistNo() {
		return this.registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	
	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getDefLossCarType() {
		return this.defLossCarType;
	}

	public void setDefLossCarType(String defLossCarType) {
		this.defLossCarType = defLossCarType;
	}

	public String getLossType() {
		return this.lossType;
	}

	public void setLossType(String lossType) {
		this.lossType = lossType;
	}

	public String getCarOwner() {
		return this.carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public String getEnrollDate() {
		return this.enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getCarKindCode() {
		return this.carKindCode;
	}

	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}

	public String getFrameNo() {
		return this.frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public String getLicenseColorCode() {
		return this.licenseColorCode;
	}

	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	public String getModelCode() {
		return this.modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public String getLicenseType() {
		return this.licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getGearboxType() {
		return this.gearboxType;
	}

	public void setGearboxType(String gearboxType) {
		this.gearboxType = gearboxType;
	}

	public String getGasType() {
		return this.gasType;
	}

	public void setGasType(String gasType) {
		this.gasType = gasType;
	}

	public String getCarKindFrom() {
		return this.carKindFrom;
	}

	public void setCarKindFrom(String carKindFrom) {
		this.carKindFrom = carKindFrom;
	}

	public String getEngineNo() {
		return this.engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getInsureComCode() {
		return this.insureComCode;
	}

	public void setInsureComCode(String insureComCode) {
		this.insureComCode = insureComCode;
	}

	public String getIndemDutyCi() {
		return this.indemDutyCi;
	}

	public void setIndemDutyCi(String indemDutyCi) {
		this.indemDutyCi = indemDutyCi;
	}

	public String getIndemnityDuty() {
		return this.indemnityDuty;
	}

	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}

	public String getIndemnityDutyRate() {
		return this.indemnityDutyRate;
	}

	public void setIndemnityDutyRate(String indemnityDutyRate) {
		this.indemnityDutyRate = indemnityDutyRate;
	}
	
	public String getInsureVehicleCode() {
		return insureVehicleCode;
	}

	public void setInsureVehicleCode(String insureVehicleCode) {
		this.insureVehicleCode = insureVehicleCode;
	}
	
	public String getComCode() {
		return ComCode;
	}

	public void setComCode(String comCode) {
		ComCode = comCode;
	}

	public List<LossVehicleInsurance> getLossVehicleInsuranceList() {
		return lossVehicleInsuranceList;
	}

	public void setLossVehicleInsuranceList(
			List<LossVehicleInsurance> lossVehicleInsuranceList) {
		this.lossVehicleInsuranceList = lossVehicleInsuranceList;
	}
	
	public String getLicenseColorCodeTo2() {
		return licenseColorCodeTo2;
	}

	public void setLicenseColorCodeTo2(String licenseColorCodeTo2) {
		this.licenseColorCodeTo2 = licenseColorCodeTo2;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void init(){
		licenseNo=licenseNo==null?"":licenseNo;                        
		defLossCarType=defLossCarType==null?"":defLossCarType;         
		lossType=lossType==null?"":lossType;                           
		carOwner=carOwner==null?"":carOwner;                           
		enrollDate=enrollDate==null?"":enrollDate;                     
		carKindCode=carKindCode==null?"":carKindCode;                  
		frameNo=frameNo==null?"":frameNo;                              
		licenseColorCode=licenseColorCode==null?"":licenseColorCode;   
		modelCode=modelCode==null?"":modelCode;                        
		vinNo=vinNo==null?"":vinNo;                                    
		licenseType=licenseType==null?"":licenseType;                  
		brandName=brandName==null?"":brandName;                        
		gearboxType=gearboxType==null?"":gearboxType;                  
		gasType=gasType==null?"":gasType;                              
		carKindFrom=carKindFrom==null?"":carKindFrom;                  
		engineNo=engineNo==null?"":engineNo;                           
		insureComCode=insureComCode==null?"":insureComCode;            
		indemDutyCi=indemDutyCi==null?"":indemDutyCi;                  
		indemnityDuty=indemnityDuty==null?"":indemnityDuty;            
		indemnityDutyRate=indemnityDutyRate==null?"":indemnityDutyRate;
		registNo=registNo==null?"":registNo; 
		
		licenseColorCodeTo2=licenseColorCodeTo2==null?"":licenseColorCodeTo2; 
		modelName=modelName==null?"":modelName; 

		
	}
	
}