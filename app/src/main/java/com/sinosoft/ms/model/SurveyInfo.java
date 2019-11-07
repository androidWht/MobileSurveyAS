package com.sinosoft.ms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统名：移动查勘 子系统名：查勘信息 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午4:02:32
 */

public class SurveyInfo
{
	private BasicSurvey basicSurvey;// 基本信息
	private List<LiabilityRatio> liabilityRatios;// 责任比例
	private List<CarData> carDatas;// 涉案车辆

	private List<PropData> propDatas;// 财产信息
	private List<PersonData> personDatas;//人伤信息
	private PersonData personData;
	private List<PolicyData> policyDatas;// 保单信息

	private List<RegistThirdCarData> registThirdCarDatas;
	private List<KindCodeData2> kindCodeDatas;

	/**
	 * @return the basicSurvey
	 */
	public BasicSurvey getBasicSurvey()
	{
		return basicSurvey;
	}

	/**
	 * @param basicSurvey
	 *            the basicSurvey to set
	 */
	public void setBasicSurvey(BasicSurvey basicSurvey)
	{
		this.basicSurvey = basicSurvey;
	}

	/**
	 * @return the liabilityRatios
	 */
	public List<LiabilityRatio> getLiabilityRatios()
	{
		return liabilityRatios;
	}

	/**
	 * @param liabilityRatios
	 *            the liabilityRatios to set
	 */
	public void setLiabilityRatios(List<LiabilityRatio> liabilityRatios)
	{
		this.liabilityRatios = liabilityRatios;
	}

	/**
	 * @return the carDatas
	 */
	public List<CarData> getCarDatas()
	{
		return carDatas;
	}

	/**
	 * @param carDatas
	 *            the carDatas to set
	 */
	public void setCarDatas(List<CarData> carDatas)
	{
		this.carDatas = carDatas;
	}

	/**
	 * @return the propDatas
	 */
	public List<PropData> getPropDatas()
	{
		return propDatas;
	}

	/**
	 * @param propDatas
	 *            the propDatas to set
	 */
	public void setPropDatas(List<PropData> propDatas)
	{
		this.propDatas = propDatas;
	}

	/**
	 * @return the personDatas
	 */

	/**
	 * @return the policyDatas
	 */
	public List<PolicyData> getPolicyDatas()
	{
		return policyDatas;
	}

	/**
	 * @return the personData
	 */
	public PersonData getPersonData()
	{
		return personData;
	}

	/**
	 * @param personData
	 *            the personData to set
	 */
	public void setPersonData(PersonData personData)
	{
		this.personData = personData;
	}

	/**
	 * @param policyDatas
	 *            the policyDatas to set
	 */
	public void setPolicyDatas(List<PolicyData> policyDatas)
	{
		this.policyDatas = policyDatas;
	}

	public List<PersonData> getPersonDatas()
	{
		return personDatas;
	}

	public void setPersonDatas(List<PersonData> personDatas)
	{
		this.personDatas = personDatas;
	}

	/**
	 * @return the registThirdCarDatas
	 */
	public List<RegistThirdCarData> getRegistThirdCarDatas()
	{
		return registThirdCarDatas;
	}

	/**
	 * @param registThirdCarDatas
	 *            the registThirdCarDatas to set
	 */
	public void setRegistThirdCarDatas(List<RegistThirdCarData> registThirdCarDatas)
	{
		this.registThirdCarDatas = registThirdCarDatas;
	}

	/**
	 * @return the kindCodeDatas
	 */
	public List<KindCodeData2> getKindCodeDatas()
	{
		return kindCodeDatas;
	}

	/**
	 * @param kindCodeDatas
	 *            the kindCodeDatas to set
	 */
	public void setKindCodeDatas(List<KindCodeData2> kindCodeDatas)
	{
		this.kindCodeDatas = kindCodeDatas;
	}

}
