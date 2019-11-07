package com.sinosoft.ms.model;


/**
 * 修理实体类
 * 
 * @author MyEclipse Persistence Tools
 */
public class LossRepairInfoItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String registNo;
	private String jyId;//服务器返回精友ID
	
	private String registId;
	private String repairItemName;//维修项名称/修理名称(大修发动机)
	private String repairItemCode;//维修项代码/修改编码(X008000)
	private long serialNo;//增量序列号码，定损系统中对于同一个定损单的换件项目只能递增不允许删除的唯一序列号码
	private String repairCode;//工种代码
	private String repairName;//工种名称(机修项目)
	private String repairType;//工种类型(机修)
	private String repairId;//定损系统修理唯一ID，定损系统内部的修理编号
	
	private String levelCode;//修理项目受损程度编号
	private double manHour;//维修工时
	private double hourFee;//工时费率(未要求传)
	private double repairFee;//修理金额
	private String selfConfigFlag;//自定义修理件标记，是/否-1/0
	private String status;//状态，A-新增，U-修改，D-删除
	private String remark;//备注
	private String insureTermCode;//险别代码
	private String insureTerm;//险别
	 private String defineType;//自定义 标志为“1”为自定义

	// Constructors

	/** default constructor */
	public LossRepairInfoItem() {
	}

	/** minimal constructor */
	public LossRepairInfoItem(String registNo, String repairItemName) {
		this.registNo = registNo;
		this.repairItemName = repairItemName;
	}

	/** full constructor */
	public LossRepairInfoItem(String registNo, String repairItemName,
			long serialNo, String repairCode, String repairName,
			String repairId, String repairItemCode, String levelCode,
			double manHour, double repairFee, String selfConfigFlag,
			String status, String remark, String insureTermCode,
			String insureTerm) {
		this.registNo = registNo;
		this.repairItemName = repairItemName;
		this.serialNo = serialNo;
		this.repairCode = repairCode;
		this.repairName = repairName;
		this.repairId = repairId;
		this.repairItemCode = repairItemCode;
		this.levelCode = levelCode;
		this.manHour = manHour;
		this.repairFee = repairFee;
		this.selfConfigFlag = selfConfigFlag;
		this.status = status;
		this.remark = remark;
		this.insureTermCode = insureTermCode;
		this.insureTerm = insureTerm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegistNo() {
		return this.registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public String getRepairItemName() {
		return this.repairItemName;
	}

	public void setRepairItemName(String repairItemName) {
		this.repairItemName = repairItemName;
	}

	public long getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}

	public String getRepairCode() {
		return this.repairCode;
	}

	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}

	public String getRepairName() {
		return this.repairName;
	}

	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}
	


	/**
	 * @return the jyId
	 */
	public String getJyId() {
		return jyId;
	}

	/**
	 * @param jyId the jyId to set
	 */
	public void setJyId(String jyId) {
		this.jyId = jyId;
	}

	/**
	 * @return the registId
	 */
	public String getRegistId() {
		return registId;
	}

	/**
	 * @param registId the registId to set
	 */
	public void setRegistId(String registId) {
		this.registId = registId;
	}

	public String getRepairId() {
		return this.repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}

	public String getRepairItemCode() {
		return this.repairItemCode;
	}

	public void setRepairItemCode(String repairItemCode) {
		this.repairItemCode = repairItemCode;
	}

	public String getLevelCode() {
		return this.levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public double getManHour() {
		return this.manHour;
	}

	public void setManHour(double manHour) {
		this.manHour = manHour;
	}

	public double getHourFee() {
		return hourFee;
	}

	public void setHourFee(double hourFee) {
		this.hourFee = hourFee;
	}

	public double getRepairFee() {
		return this.repairFee;
	}

	public void setRepairFee(double repairFee) {
		this.repairFee = repairFee;
	}

	public String getSelfConfigFlag() {
		return this.selfConfigFlag;
	}

	public void setSelfConfigFlag(String selfConfigFlag) {
		this.selfConfigFlag = selfConfigFlag;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInsureTermCode() {
		return this.insureTermCode;
	}

	public void setInsureTermCode(String insureTermCode) {
		this.insureTermCode = insureTermCode;
	}

	public String getInsureTerm() {
		return this.insureTerm;
	}

	public void setInsureTerm(String insureTerm) {
		this.insureTerm = insureTerm;
	}

	/**
	 * @return the defineType
	 */
	public String getDefineType() {
		return defineType;
	}

	/**
	 * @param defineType the defineType to set
	 */
	public void setDefineType(String defineType) {
		this.defineType = defineType;
	}

	public void init(){
		registNo=registNo==null?"":registNo;                   
		repairItemName=repairItemName==null?"":repairItemName; 
		repairItemCode=repairItemCode==null?"":repairItemCode; 
		                  
		repairCode=repairCode==null?"":repairCode;             
		repairName=repairName==null?"":repairName;             
		repairType=repairType==null?"":repairType;             
		repairId=repairId==null?"":repairId;                   
		levelCode=levelCode==null?"":levelCode;   
		
		registId=registId==null?"":registId;         
		jyId=jyId==null?"":jyId;         
		                  
		           
		selfConfigFlag=selfConfigFlag==null?"":selfConfigFlag; 
		status=status==null?"":status;                         
		remark=remark==null?"":remark;                         
		insureTermCode=insureTermCode==null?"":insureTermCode; 
		insureTerm=insureTerm==null?"":insureTerm;     
		defineType=defineType==null?"":defineType;     

		
	}
	
}