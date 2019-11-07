package com.sinosoft.ms.model;


/**
 * LossFitInfoItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class LossFitInfoItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String registId;
	private String jyId;	//服务器返回精友Id
	private String registNo;// 报案号，但是在实际使用中应该传taskId
	private long serialNo;// 估损单唯一序号
	private String originalId;// 原厂零件编号
	private String originalName;// 原厂零件名称
	private String partId;// 定损系统零件唯一ID，定损系统内部的零件编码
	private String partStandardCode;// 配件标准代码，配件的标准代码，如 “前保险杠皮”是001100
	private String partStandard;// 配件标准名称，配件的标准名称，如“前保险杠皮” - 零件名称
	private String partGroupCode;// 配件部位代码，配件分组代码
	private String partGroupName;// 配件部位名称，配件分组名称 
	private long lossCount;// 换件数量
	private double lossPrice;// 定损价格
	private double chgRefPrice;// 价格方案系统价格
	private double remnant;// 残值
	private String selfConfigFlag;// 自定义配件标记 是/否-1/0
	private String ifRemain;// 是否损余 1/0
	private String chgCompSetCode;// 价格方案编码
	private String status;// 状态，A-新增，U-修改，D-删
	private String remark;// 备注
	private String insureTermCode;// 险别代码
	private String insureTerm;// 险别
	private double chgLocPrice;// 价格方案本地价格，供定损人员使用
	private double locPrice1;// 本地价
	private double locPrice2;// 本地价2
	private double locPrice3;// 本地价3
	private double refPrice1;// 系统价格1
	private double refPrice2;// 系统价格2
	private double refPrice3;// 系统价格3
	private String surveyQuotedPrice;// 查勘报价
	private String modifyFactoryPrice;// 修改厂价格
	
	private String replacementParts;// 更换部位1
	private String partsGrouping;// 零件分组名称2
	private String partType;// 零件类型3
	private String partName;// 零件名称
	private String keywords;// 关键字
	private String defineType;//是否自定义

	/** default constructor */
	public LossFitInfoItem() {
	}

	/** minimal constructor */
	public LossFitInfoItem(String registNo) {
		this.registNo = registNo;
	}

	/** full constructor */
	public LossFitInfoItem(String registNo, long serialNo, String originalId,
			String originalName, String partId, String partStandardCode,
			String partStandard, String partGroupCode, String partGroupName,
			long lossCount, double lossPrice, double chgRefPrice,
			double remnant, String selfConfigFlag, String ifRemain,
			String chgCompSetCode, String status, String remark,
			String insureTermCode, String insureTerm, double chgLocPrice,
			double locPrice1, double locPrice2, double locPrice3,
			double refPrice1, double refPrice2, double refPrice3) {
		this.registNo = registNo;
		this.serialNo = serialNo;
		this.originalId = originalId;
		this.originalName = originalName;
		this.partId = partId;
		this.partStandardCode = partStandardCode;
		this.partStandard = partStandard;
		this.partGroupCode = partGroupCode;
		this.partGroupName = partGroupName;
		this.lossCount = lossCount;
		this.lossPrice = lossPrice;
		this.chgRefPrice = chgRefPrice;
		this.remnant = remnant;
		this.selfConfigFlag = selfConfigFlag;
		this.ifRemain = ifRemain;
		this.chgCompSetCode = chgCompSetCode;
		this.status = status;
		this.remark = remark;
		this.insureTermCode = insureTermCode;
		this.insureTerm = insureTerm;
		this.chgLocPrice = chgLocPrice;
		this.locPrice1 = locPrice1;
		this.locPrice2 = locPrice2;
		this.locPrice3 = locPrice3;
		this.refPrice1 = refPrice1;
		this.refPrice2 = refPrice2;
		this.refPrice3 = refPrice3;
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

	public long getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}

	public String getOriginalId() {
		return this.originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getOriginalName() {
		return this.originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getPartId() {
		return this.partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartStandardCode() {
		return this.partStandardCode;
	}

	public void setPartStandardCode(String partStandardCode) {
		this.partStandardCode = partStandardCode;
	}

	public String getPartStandard() {
		return this.partStandard;
	}

	public void setPartStandard(String partStandard) {
		this.partStandard = partStandard;
	}

	public String getPartGroupCode() {
		return this.partGroupCode;
	}

	public void setPartGroupCode(String partGroupCode) {
		this.partGroupCode = partGroupCode;
	}

	public String getPartGroupName() {
		return this.partGroupName;
	}

	public void setPartGroupName(String partGroupName) {
		this.partGroupName = partGroupName;
	}

	public long getLossCount() {
		return this.lossCount;
	}

	public void setLossCount(long lossCount) {
		this.lossCount = lossCount;
	}

	public double getLossPrice() {
		return this.lossPrice;
	}

	public void setLossPrice(double lossPrice) {
		this.lossPrice = lossPrice;
	}

	public double getChgRefPrice() {
		return this.chgRefPrice;
	}

	public void setChgRefPrice(double chgRefPrice) {
		this.chgRefPrice = chgRefPrice;
	}

	public double getRemnant() {
		return this.remnant;
	}

	public void setRemnant(double remnant) {
		this.remnant = remnant;
	}

	public String getSelfConfigFlag() {
		return this.selfConfigFlag;
	}

	public void setSelfConfigFlag(String selfConfigFlag) {
		this.selfConfigFlag = selfConfigFlag;
	}

	public String getIfRemain() {
		return this.ifRemain;
	}

	public void setIfRemain(String ifRemain) {
		this.ifRemain = ifRemain;
	}

	public String getChgCompSetCode() {
		return this.chgCompSetCode;
	}

	public void setChgCompSetCode(String chgCompSetCode) {
		this.chgCompSetCode = chgCompSetCode;
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

	public double getChgLocPrice() {
		return this.chgLocPrice;
	}

	public void setChgLocPrice(double chgLocPrice) {
		this.chgLocPrice = chgLocPrice;
	}

	public double getLocPrice1() {
		return this.locPrice1;
	}

	public void setLocPrice1(double locPrice1) {
		this.locPrice1 = locPrice1;
	}

	public double getLocPrice2() {
		return this.locPrice2;
	}

	public void setLocPrice2(double locPrice2) {
		this.locPrice2 = locPrice2;
	}

	public double getLocPrice3() {
		return this.locPrice3;
	}

	public void setLocPrice3(double locPrice3) {
		this.locPrice3 = locPrice3;
	}

	public double getRefPrice1() {
		return this.refPrice1;
	}

	public void setRefPrice1(double refPrice1) {
		this.refPrice1 = refPrice1;
	}

	public double getRefPrice2() {
		return this.refPrice2;
	}

	public void setRefPrice2(double refPrice2) {
		this.refPrice2 = refPrice2;
	}

	public double getRefPrice3() {
		return this.refPrice3;
	}

	public void setRefPrice3(double refPrice3) {
		this.refPrice3 = refPrice3;
	}

	public String getSurveyQuotedPrice() {
		return surveyQuotedPrice;
	}

	public void setSurveyQuotedPrice(String surveyQuotedPrice) {
		this.surveyQuotedPrice = surveyQuotedPrice;
	}

	public String getModifyFactoryPrice() {
		return modifyFactoryPrice;
	}

	public void setModifyFactoryPrice(String modifyFactoryPrice) {
		this.modifyFactoryPrice = modifyFactoryPrice;
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
		jyId= jyId == null?"":jyId;
		registId= registId == null?"":registId;
		                             
		originalId=originalId==null?"":originalId;                        
		originalName=originalName==null?"":originalName;                  
		partId=partId==null?"":partId;                                    
		partStandardCode=partStandardCode==null?"":partStandardCode;      
		partStandard=partStandard==null?"":partStandard;                  
		partGroupCode=partGroupCode==null?"":partGroupCode;               
		partGroupName=partGroupName==null?"":partGroupName;               
		                                
		selfConfigFlag=selfConfigFlag==null?"":selfConfigFlag;            
		ifRemain=ifRemain==null?"":ifRemain;                              
		chgCompSetCode=chgCompSetCode==null?"":chgCompSetCode;            
		status=status==null?"":status;                                    
		remark=remark==null?"":remark;                                    
		insureTermCode=insureTermCode==null?"":insureTermCode;            
		insureTerm=insureTerm==null?"":insureTerm;                        
		                       
		surveyQuotedPrice=surveyQuotedPrice==null?"":surveyQuotedPrice;   
		modifyFactoryPrice=modifyFactoryPrice==null?"":modifyFactoryPrice;
		replacementParts=replacementParts==null?"":replacementParts;      
		partsGrouping=partsGrouping==null?"":partsGrouping;               
		partType=partType==null?"":partType;                              
		partName=partName==null?"":partName;                              
		keywords=keywords==null?"":keywords;
		defineType=defineType==null?"":defineType;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return partStandard;
	}

	
	
}