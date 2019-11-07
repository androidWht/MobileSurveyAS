package com.sinosoft.ms.model;

import com.sinosoft.ms.utils.StringUtils;


/**
 * 车辆基本信息 entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BasicVehicle implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String registNo;
	private String caseNo;
	private long lossNo;
	private String vehKindCode;
	private String vehKindName;
	private String vehCertainCode;
	private String vehCertainName;
	private String vehSeriCode;
	private String vehSeriName;
	private String vehYearType;
	private String vehFactoryCode;
	private String vehFactoryName;
	private String vehBrandCode;
	private String vehBrandName;
	private String plateNo;
	private String selfConfigFlag;
	private String version;
	private String remark;
	private String vehGroupCode;
	private String vehGroupName;
	private double sumChgCompFee;
	private double sumRepairFee;
	private double sumMaterialFee;
	private double sumLossFee;
	private double sumRemnant;
	private double sumRescueFee;
	
	

	// Constructors

	/** default constructor */
	public BasicVehicle() {
	}

	/** minimal constructor */
	public BasicVehicle(String registNo) {
		this.registNo = registNo;
	}

	/** full constructor */
	public BasicVehicle(String registNo, String caseNo, long lossNo,
			String vehKindCode, String vehKindName, String vehCertainCode,
			String vehCertainName, String vehSeriCode, String vehSeriName,
			String vehYearType, String vehFactoryCode, String vehFactoryName,
			String vehBrandCode, String vehBrandName, String plateNo,
			String selfConfigFlag, String version, String remark,
			String vehGroupCode, String vehGroupName, double sumChgCompFee,
			double sumRepairFee, double sumMaterialFee, double sumLossFee) {
		this.registNo = registNo;
		this.caseNo = caseNo;
		this.lossNo = lossNo;
		this.vehKindCode = vehKindCode;
		this.vehKindName = vehKindName;
		this.vehCertainCode = vehCertainCode;
		this.vehCertainName = vehCertainName;
		this.vehSeriCode = vehSeriCode;
		this.vehSeriName = vehSeriName;
		this.vehYearType = vehYearType;
		this.vehFactoryCode = vehFactoryCode;
		this.vehFactoryName = vehFactoryName;
		this.vehBrandCode = vehBrandCode;
		this.vehBrandName = vehBrandName;
		this.plateNo = plateNo;
		this.selfConfigFlag = selfConfigFlag;
		this.version = version;
		this.remark = remark;
		this.vehGroupCode = vehGroupCode;
		this.vehGroupName = vehGroupName;
		this.sumChgCompFee = sumChgCompFee;
		this.sumRepairFee = sumRepairFee;
		this.sumMaterialFee = sumMaterialFee;
		this.sumLossFee = sumLossFee;
	}

	// Property accessors
	public String getRegistNo() {
		return this.registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public String getCaseNo() {
		return this.caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public long getLossNo() {
		return this.lossNo;
	}

	public void setLossNo(long lossNo) {
		this.lossNo = lossNo;
	}

	public String getVehKindCode() {
		return this.vehKindCode;
	}

	public void setVehKindCode(String vehKindCode) {
		this.vehKindCode = vehKindCode;
	}

	public String getVehKindName() {
		return this.vehKindName;
	}

	public void setVehKindName(String vehKindName) {
		this.vehKindName = vehKindName;
	}

	public String getVehCertainCode() {
		return this.vehCertainCode;
	}

	public void setVehCertainCode(String vehCertainCode) {
		this.vehCertainCode = vehCertainCode;
	}

	public String getVehCertainName() {
		return this.vehCertainName;
	}

	public void setVehCertainName(String vehCertainName) {
		this.vehCertainName = vehCertainName;
	}

	public String getVehSeriCode() {
		return this.vehSeriCode;
	}

	public void setVehSeriCode(String vehSeriCode) {
		this.vehSeriCode = vehSeriCode;
	}

	public String getVehSeriName() {
		return this.vehSeriName;
	}

	public void setVehSeriName(String vehSeriName) {
		this.vehSeriName = vehSeriName;
	}

	public String getVehYearType() {
		return this.vehYearType;
	}

	public void setVehYearType(String vehYearType) {
		this.vehYearType = vehYearType;
	}

	public String getVehFactoryCode() {
		return this.vehFactoryCode;
	}

	public void setVehFactoryCode(String vehFactoryCode) {
		this.vehFactoryCode = vehFactoryCode;
	}

	public String getVehFactoryName() {
		return this.vehFactoryName;
	}

	public void setVehFactoryName(String vehFactoryName) {
		this.vehFactoryName = vehFactoryName;
	}

	public String getVehBrandCode() {
		return this.vehBrandCode;
	}

	public void setVehBrandCode(String vehBrandCode) {
		this.vehBrandCode = vehBrandCode;
	}

	public String getVehBrandName() {
		return this.vehBrandName;
	}

	public void setVehBrandName(String vehBrandName) {
		this.vehBrandName = vehBrandName;
	}

	public String getPlateNo() {
		return this.plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getSelfConfigFlag() {
		return this.selfConfigFlag;
	}

	public void setSelfConfigFlag(String selfConfigFlag) {
		this.selfConfigFlag = selfConfigFlag;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVehGroupCode() {
		return this.vehGroupCode;
	}

	public void setVehGroupCode(String vehGroupCode) {
		this.vehGroupCode = vehGroupCode;
	}

	public String getVehGroupName() {
		return this.vehGroupName;
	}

	public void setVehGroupName(String vehGroupName) {
		this.vehGroupName = vehGroupName;
	}

	public double getSumChgCompFee() {
		return this.sumChgCompFee;
	}

	public void setSumChgCompFee(double sumChgCompFee) {
		this.sumChgCompFee = sumChgCompFee;
	}

	public double getSumRepairFee() {
		return this.sumRepairFee;
	}

	public void setSumRepairFee(double sumRepairFee) {
		this.sumRepairFee = sumRepairFee;
	}

	public double getSumMaterialFee() {
		return this.sumMaterialFee;
	}

	public void setSumMaterialFee(double sumMaterialFee) {
		this.sumMaterialFee = sumMaterialFee;
	}

	public double getSumLossFee() {
		return this.sumLossFee;
	}

	public void setSumLossFee(double sumLossFee) {
		this.sumLossFee = sumLossFee;
	}
	public double getSumRemnant() {
		return sumRemnant;
	}
	public void setSumRemnant(double sumRemnant) {
		this.sumRemnant = sumRemnant;
	}
	public double getSumRescueFee() {
		return sumRescueFee;
	}
	public void setSumRescueFee(double sumRescueFee) {
		this.sumRescueFee = sumRescueFee;
	}

	public void init(){
    	registNo=registNo==null?"":registNo;                  
    	caseNo=caseNo==null?"":caseNo;                        
    	                    
    	vehKindCode=vehKindCode==null?"":vehKindCode;         
    	vehKindName=vehKindName==null?"":vehKindName;         
    	vehCertainCode=vehCertainCode==null?"":vehCertainCode;
    	vehCertainName=vehCertainName==null?"":vehCertainName;
    	vehSeriCode=vehSeriCode==null?"":vehSeriCode;         
    	vehSeriName=vehSeriName==null?"":vehSeriName;         
    	vehYearType=vehYearType==null?"":vehYearType;         
    	vehFactoryCode=vehFactoryCode==null?"":vehFactoryCode;
    	vehFactoryName=vehFactoryName==null?"":vehFactoryName;
    	vehBrandCode=vehBrandCode==null?"":vehBrandCode;      
    	vehBrandName=vehBrandName==null?"":vehBrandName;      
    	plateNo=plateNo==null?"":plateNo;                     
    	selfConfigFlag=selfConfigFlag==null?"":selfConfigFlag;
    	version=version==null?"":version;                     
    	remark=remark==null?"":remark;                        
    	vehGroupCode=vehGroupCode==null?"":vehGroupCode;      
    	vehGroupName=vehGroupName==null?"":vehGroupName;    
    }
}