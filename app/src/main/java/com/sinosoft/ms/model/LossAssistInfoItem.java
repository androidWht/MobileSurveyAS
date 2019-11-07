package com.sinosoft.ms.model;


/**
 * 辅料实体类
 */
public class LossAssistInfoItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String registNo;//
	private String jyId;	//服务器返回唯一标识
	private String registId;
	private long serialNo;//增量序列号码，定损系统中对于同一个定损单的辅料项目只能递增不允许删除的唯一序列号码
	private String materialName;//项目名称
	private String materialCode;//项目编码
	private double unitPrice;//单价
	private long count;//数量
	private double materialFee;//金额
	private String status;//状态
	private String remark;//备注
	private String insureTermCode;//险别代码
	private String insureTerm;//险别
	private String defineType;

	// Constructors

	/** default constructor */
	public LossAssistInfoItem() {
	}

	/** minimal constructor */
	public LossAssistInfoItem(String registNo) {
		this.registNo = registNo;
	}

	/** full constructor */
	public LossAssistInfoItem(String registNo, long serialNo,
			String materialName, double unitPrice, long count,
			double materialFee, String status, String remark,
			String insureTermCode, String insureTerm) {
		this.registNo = registNo;
		this.serialNo = serialNo;
		this.materialName = materialName;
		this.unitPrice = unitPrice;
		this.count = count;
		this.materialFee = materialFee;
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

	public String getRegistNo() {
		return this.registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public long getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}

	public String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public long getCount() {
		return this.count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public double getMaterialFee() {
		return this.materialFee;
	}

	public void setMaterialFee(double materialFee) {
		this.materialFee = materialFee;
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
	
	
	public String getDefineType() {
		return defineType;
	}

	
	public void setDefineType(String defineType) {
		this.defineType = defineType;
	}

	public void init(){
		registNo=registNo==null?"":registNo;      
		
		jyId=jyId==null?"":jyId; 
		registId=registId==null?"":registId; 
		                 
		materialName=materialName==null?"":materialName;      
		materialCode=materialCode==null?"":materialCode;             
		status=status==null?"":status;                        
		remark=remark==null?"":remark;                        
		insureTermCode=insureTermCode==null?"":insureTermCode;
		insureTerm=insureTerm==null?"":insureTerm;    
		defineType=defineType==null?"":defineType;    

	}

}