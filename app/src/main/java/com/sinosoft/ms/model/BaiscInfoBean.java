package com.sinosoft.ms.model;

/**
 * 系统名：移动查勘定损 子系统名：案件中心（查勘处理-基本信息） 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午3:19:54
 */

public class BaiscInfoBean {
	private String reportNo;// 报案号
	private String whether_subrogation;// 是否代位求偿
	private String claim_form;// 索赔申请书
	private String accident_handle_type;// 事故处理类型
	private String case_nature;// 案件性质
	private String claim_case_category;// 赔案类别
	private String loss_type;// 损失类别
	private String accident_cause;// 出险原因
	private String accident_type;// 事故类型
	private String survey_type;// 查勘类型
	private String survey_addr;// 查勘地点
	private String survey_time;// 查勘时间
	private String contact_person;// 联系人
	private String contact_phone;// 联系电话
	// private String reportMobile;//报案人手机
	private String touch_for;// 互碰自赔
	private String survey_of_1;// 查勘员1
	private String survey_of_2;// 查勘员2
	private String first_site_survey;// 第一现场查勘
	private String danger_place;// 出险地点
	private String accident_processing_department;// 事故处理部门
	private String mapping_class;// 查勘类别
	private String quick_office_for;// 快处快赔
	private String double_free;// 双免
	private String survey_feedback;// 查勘意见

	// add by zhengtongsheng 2013-2-16 9:14

	private String riskCode;// 险种
	private String intermediaryCode;// 公估公司代码
	private String intermediaryName;// 公估公司名称

	public BaiscInfoBean() {

	}

	/**
	 * @param reportNo
	 * @param whether_subrogation
	 * @param claim_form
	 * @param accident_handle_type
	 * @param case_nature
	 * @param claim_case_category
	 * @param loss_type
	 * @param accident_cause
	 * @param accident_type
	 * @param survey_type
	 * @param survey_addr
	 * @param survey_time
	 * @param contact_person
	 * @param contact_phone
	 * @param touch_for
	 * @param survey_of_1
	 * @param survey_of_2
	 * @param first_site_survey
	 * @param danger_place
	 * @param accident_processing_department
	 * @param mapping_class
	 * @param quick_office_for
	 * @param double_free
	 * @param survey_feedback
	 */
	public BaiscInfoBean(String reportNo, String whether_subrogation,
			String claim_form, String accident_handle_type, String case_nature,
			String claim_case_category, String loss_type,
			String accident_cause, String accident_type, String survey_type,
			String survey_addr, String survey_time, String contact_person,
			String contact_phone, String touch_for, String survey_of_1,
			String survey_of_2, String first_site_survey, String danger_place,
			String accident_processing_department, String mapping_class,
			String quick_office_for, String double_free, String survey_feedback) {
		super();
		this.reportNo = reportNo;
		this.whether_subrogation = whether_subrogation;
		this.claim_form = claim_form;
		this.accident_handle_type = accident_handle_type;
		this.case_nature = case_nature;
		this.claim_case_category = claim_case_category;
		this.loss_type = loss_type;
		this.accident_cause = accident_cause;
		this.accident_type = accident_type;
		this.survey_type = survey_type;
		this.survey_addr = survey_addr;
		this.survey_time = survey_time;
		this.contact_person = contact_person;
		this.contact_phone = contact_phone;
		this.touch_for = touch_for;
		this.survey_of_1 = survey_of_1;
		this.survey_of_2 = survey_of_2;
		this.first_site_survey = first_site_survey;
		this.danger_place = danger_place;
		this.accident_processing_department = accident_processing_department;
		this.mapping_class = mapping_class;
		this.quick_office_for = quick_office_for;
		this.double_free = double_free;
		this.survey_feedback = survey_feedback;
	}

	/**
	 * @param reportNo
	 * @param whether_subrogation
	 * @param claim_form
	 * @param accident_handle_type
	 * @param case_nature
	 * @param claim_case_category
	 * @param loss_type
	 * @param accident_cause
	 * @param accident_type
	 * @param survey_type
	 * @param survey_addr
	 * @param survey_time
	 * @param contact_person
	 * @param contact_phone
	 * @param touch_for
	 * @param survey_of_1
	 * @param survey_of_2
	 * @param first_site_survey
	 * @param danger_place
	 * @param accident_processing_department
	 * @param mapping_class
	 * @param quick_office_for
	 * @param double_free
	 * @param survey_feedback
	 * @param riskCode
	 * @param intermediaryCode
	 * @param intermediaryName
	 */
	public BaiscInfoBean(String reportNo, String whether_subrogation,
			String claim_form, String accident_handle_type, String case_nature,
			String claim_case_category, String loss_type,
			String accident_cause, String accident_type, String survey_type,
			String survey_addr, String survey_time, String contact_person,
			String contact_phone, String touch_for, String survey_of_1,
			String survey_of_2, String first_site_survey, String danger_place,
			String accident_processing_department, String mapping_class,
			String quick_office_for, String double_free,
			String survey_feedback, String riskCode, String intermediaryCode,
			String intermediaryName) {
		super();
		this.reportNo = reportNo;
		this.whether_subrogation = whether_subrogation;
		this.claim_form = claim_form;
		this.accident_handle_type = accident_handle_type;
		this.case_nature = case_nature;
		this.claim_case_category = claim_case_category;
		this.loss_type = loss_type;
		this.accident_cause = accident_cause;
		this.accident_type = accident_type;
		this.survey_type = survey_type;
		this.survey_addr = survey_addr;
		this.survey_time = survey_time;
		this.contact_person = contact_person;
		this.contact_phone = contact_phone;
		this.touch_for = touch_for;
		this.survey_of_1 = survey_of_1;
		this.survey_of_2 = survey_of_2;
		this.first_site_survey = first_site_survey;
		this.danger_place = danger_place;
		this.accident_processing_department = accident_processing_department;
		this.mapping_class = mapping_class;
		this.quick_office_for = quick_office_for;
		this.double_free = double_free;
		this.survey_feedback = survey_feedback;
		this.riskCode = riskCode;
		this.intermediaryCode = intermediaryCode;
		this.intermediaryName = intermediaryName;
	}

	/**
	 * @return the reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}

	/**
	 * @param reportNo
	 *            the reportNo to set
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	/**
	 * @return the whether_subrogation
	 */
	public String getWhether_subrogation() {
		return whether_subrogation;
	}

	/**
	 * @param whether_subrogation
	 *            the whether_subrogation to set
	 */
	public void setWhether_subrogation(String whether_subrogation) {
		this.whether_subrogation = whether_subrogation;
	}

	/**
	 * @return the claim_form
	 */
	public String getClaim_form() {
		return claim_form;
	}

	/**
	 * @param claim_form
	 *            the claim_form to set
	 */
	public void setClaim_form(String claim_form) {
		this.claim_form = claim_form;
	}

	/**
	 * @return the accident_handle_type
	 */
	public String getAccident_handle_type() {
		return accident_handle_type;
	}

	/**
	 * @param accident_handle_type
	 *            the accident_handle_type to set
	 */
	public void setAccident_handle_type(String accident_handle_type) {
		this.accident_handle_type = accident_handle_type;
	}

	/**
	 * @return the case_nature
	 */
	public String getCase_nature() {
		return case_nature;
	}

	/**
	 * @param case_nature
	 *            the case_nature to set
	 */
	public void setCase_nature(String case_nature) {
		this.case_nature = case_nature;
	}

	/**
	 * @return the claim_case_category
	 */
	public String getClaim_case_category() {
		return claim_case_category;
	}

	/**
	 * @param claim_case_category
	 *            the claim_case_category to set
	 */
	public void setClaim_case_category(String claim_case_category) {
		this.claim_case_category = claim_case_category;
	}

	/**
	 * @return the loss_type
	 */
	public String getLoss_type() {
		return loss_type;
	}

	/**
	 * @param loss_type
	 *            the loss_type to set
	 */
	public void setLoss_type(String loss_type) {
		this.loss_type = loss_type;
	}

	/**
	 * @return the accident_cause
	 */
	public String getAccident_cause() {
		return accident_cause;
	}

	/**
	 * @param accident_cause
	 *            the accident_cause to set
	 */
	public void setAccident_cause(String accident_cause) {
		this.accident_cause = accident_cause;
	}

	/**
	 * @return the accident_type
	 */
	public String getAccident_type() {
		return accident_type;
	}

	/**
	 * @param accident_type
	 *            the accident_type to set
	 */
	public void setAccident_type(String accident_type) {
		this.accident_type = accident_type;
	}

	/**
	 * @return the survey_type
	 */
	public String getSurvey_type() {
		return survey_type;
	}

	/**
	 * @param survey_type
	 *            the survey_type to set
	 */
	public void setSurvey_type(String survey_type) {
		this.survey_type = survey_type;
	}

	/**
	 * @return the survey_addr
	 */
	public String getSurvey_addr() {
		return survey_addr;
	}

	/**
	 * @param survey_addr
	 *            the survey_addr to set
	 */
	public void setSurvey_addr(String survey_addr) {
		this.survey_addr = survey_addr;
	}

	/**
	 * @return the survey_time
	 */
	public String getSurvey_time() {
		return survey_time;
	}

	/**
	 * @param survey_time
	 *            the survey_time to set
	 */
	public void setSurvey_time(String survey_time) {
		this.survey_time = survey_time;
	}

	/**
	 * @return the contact_person
	 */
	public String getContact_person() {
		return contact_person;
	}

	/**
	 * @param contact_person
	 *            the contact_person to set
	 */
	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	/**
	 * @return the contact_phone
	 */
	public String getContact_phone() {
		return contact_phone;
	}

	/**
	 * @param contact_phone
	 *            the contact_phone to set
	 */
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	/**
	 * @return the touch_for
	 */
	public String getTouch_for() {
		return touch_for;
	}

	/**
	 * @param touch_for
	 *            the touch_for to set
	 */
	public void setTouch_for(String touch_for) {
		this.touch_for = touch_for;
	}

	/**
	 * @return the survey_of_1
	 */
	public String getSurvey_of_1() {
		return survey_of_1;
	}

	/**
	 * @param survey_of_1
	 *            the survey_of_1 to set
	 */
	public void setSurvey_of_1(String survey_of_1) {
		this.survey_of_1 = survey_of_1;
	}

	/**
	 * @return the survey_of_2
	 */
	public String getSurvey_of_2() {
		return survey_of_2;
	}

	/**
	 * @param survey_of_2
	 *            the survey_of_2 to set
	 */
	public void setSurvey_of_2(String survey_of_2) {
		this.survey_of_2 = survey_of_2;
	}

	/**
	 * @return the first_site_survey
	 */
	public String getFirst_site_survey() {
		return first_site_survey;
	}

	/**
	 * @param first_site_survey
	 *            the first_site_survey to set
	 */
	public void setFirst_site_survey(String first_site_survey) {
		this.first_site_survey = first_site_survey;
	}

	/**
	 * @return the danger_place
	 */
	public String getDanger_place() {
		return danger_place;
	}

	/**
	 * @param danger_place
	 *            the danger_place to set
	 */
	public void setDanger_place(String danger_place) {
		this.danger_place = danger_place;
	}

	/**
	 * @return the accident_processing_department
	 */
	public String getAccident_processing_department() {
		return accident_processing_department;
	}

	/**
	 * @param accident_processing_department
	 *            the accident_processing_department to set
	 */
	public void setAccident_processing_department(
			String accident_processing_department) {
		this.accident_processing_department = accident_processing_department;
	}

	/**
	 * @return the mapping_class
	 */
	public String getMapping_class() {
		return mapping_class;
	}

	/**
	 * @param mapping_class
	 *            the mapping_class to set
	 */
	public void setMapping_class(String mapping_class) {
		this.mapping_class = mapping_class;
	}

	/**
	 * @return the quick_office_for
	 */
	public String getQuick_office_for() {
		return quick_office_for;
	}

	/**
	 * @param quick_office_for
	 *            the quick_office_for to set
	 */
	public void setQuick_office_for(String quick_office_for) {
		this.quick_office_for = quick_office_for;
	}

	/**
	 * @return the double_free
	 */
	public String getDouble_free() {
		return double_free;
	}

	/**
	 * @param double_free
	 *            the double_free to set
	 */
	public void setDouble_free(String double_free) {
		this.double_free = double_free;
	}

	/**
	 * @return the survey_feedback
	 */
	public String getSurvey_feedback() {
		return survey_feedback;
	}

	/**
	 * @param survey_feedback
	 *            the survey_feedback to set
	 */
	public void setSurvey_feedback(String survey_feedback) {
		this.survey_feedback = survey_feedback;
	}

	/**
	 * @return the riskCode
	 */
	public String getRiskCode() {
		return riskCode;
	}

	/**
	 * @param riskCode
	 *            the riskCode to set
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * @return the intermediaryCode
	 */
	public String getIntermediaryCode() {
		return intermediaryCode;
	}

	/**
	 * @param intermediaryCode
	 *            the intermediaryCode to set
	 */
	public void setIntermediaryCode(String intermediaryCode) {
		this.intermediaryCode = intermediaryCode;
	}

	/**
	 * @return the intermediaryName
	 */
	public String getIntermediaryName() {
		return intermediaryName;
	}

	/**
	 * @param intermediaryName
	 *            the intermediaryName to set
	 */
	public void setIntermediaryName(String intermediaryName) {
		this.intermediaryName = intermediaryName;
	}

}
