package com.sinosoft.ms.model;

/**
 * 定损车辆承保信息数据结构类
 */
public class VehicleType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String modelName;// 厂商名称
	private String tradeName;// 车型名称
	private String carName;// 车系名称
	private String categoryName;// 种类名称
	private String seat;// 座位
	private String price;// 价格
	private String volume;// 排量
	private String description;// 描述说明
	private String modelYear;// 年款
	private String remark;
	private String VehCertainCode;//定损车型编码
	private String VehCertainName;//定损车型名称
	
	public VehicleType() {
		super();
	}

	public VehicleType(String modelName, String tradeName, String carName,
			String categoryName, String seat, String price, String volume,
			String description, String modelYear,String remark) {
		super();
		this.modelName = modelName;
		this.tradeName = tradeName;
		this.carName = carName;
		this.categoryName = categoryName;
		this.seat = seat;
		this.price = price;
		this.volume = volume;
		this.description = description;
		this.modelYear = modelYear;
		this.remark = remark;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVehCertainCode() {
		return VehCertainCode;
	}

	public void setVehCertainCode(String vehCertainCode) {
		VehCertainCode = vehCertainCode;
	}

	public String getVehCertainName() {
		return VehCertainName;
	}

	public void setVehCertainName(String vehCertainName) {
		VehCertainName = vehCertainName;
	}

}