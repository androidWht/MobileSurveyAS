package com.sinosoft.ms.utils.db;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.KindCodeData2;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.service.impl.KindCodeDataService2;
import com.sinosoft.ms.utils.FileUtils;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午2:18:13
 */

public class SurveyTreatmentDatabase2 {
	private static SurveyTreatmentDatabase2 database;

	private BasicSurvey basicSurvey;// 基本信息
	private List<CarData> carDatas;// 车辆信息
	private PersonData personData;// 人伤信息\
	private List<PersonData> personDatas;
	private List<PolicyData> policyDatas;// 保单信息
	private List<PropData> proDatas;// 财产信息
	private List<LiabilityRatio> liabilityRatios;

	private List<RegistThirdCarData> registThirdCarDatas;
	private List<KindCodeData2> kindCodeDatas;

	// private Vec
	public static SurveyTreatmentDatabase2 getInstance() {
		if (database == null) {
			database = new SurveyTreatmentDatabase2();

		}
		return database;

	}

	/**
	 * @param database
	 *            the database to set
	 */
	public static void setDatabase(SurveyTreatmentDatabase2 database) {
		SurveyTreatmentDatabase2.database = database;
	}

	public void save(Context context) throws Exception {
		if (basicSurvey != null) {
			// 基本信息保存
			BasicSurveyDatabase baiscDatabase = new BasicSurveyDatabase(context);
			basicSurvey.init();
			baiscDatabase.insertBasicSurvey("basic_survey", basicSurvey);
		}
		if (kindCodeDatas != null) {
//			Log.i("kindCodeDatas", "进入");
//			Log.i("kindCodeDatas", String.valueOf(kindCodeDatas.size()));
			for (KindCodeData2 kindCodeData : kindCodeDatas) {
				Log.i("kindCodeDatas", kindCodeData.getCarId());
				Log.i("kindCodeDatas", kindCodeData.getInsureTerm());
				Log.i("kindCodeDatas", kindCodeData.getInsureTermCode());
				KindCodeDataService2 kindCodeDataService = new KindCodeDataService2();
				kindCodeDataService.save(kindCodeData);
//				Log.i("success", "success");
			}

		}
		if (carDatas != null) {
			// 涉案车辆
			CarDatasDatabase vehicleDatabase = new CarDatasDatabase(context);
			vehicleDatabase.insertCarDatas("car_data", carDatas);
		}

		if (proDatas != null) {
			// 财产损失保存
			PropDataDatabase propertyDatabase = new PropDataDatabase(context);
			if (proDatas != null && !proDatas.isEmpty()) {
				propertyDatabase.insertPropData("prop_data", proDatas);
			}
		}

		if(personDatas != null){
			// 人伤
			PersonDataDatabase personDataBase = new PersonDataDatabase(context);
			if (personDatas != null && !personDatas.isEmpty()) {
			   personDataBase.insertPersonData("person_data", personDatas);
			}
		}
		
		if (policyDatas != null) {
			// 保单信息
			PolicyDatasDatabase policyDatabase = new PolicyDatasDatabase(
					context);
			policyDatabase.insertPolicyDatas("policy_data", policyDatas);
		}
		if (liabilityRatios != null) {
			LiabilityRadioDatabase liabilityRadioDatabase = new LiabilityRadioDatabase(
					context);
			liabilityRadioDatabase.insertLiablity("liablity_data",
					liabilityRatios);
		}

		if (registThirdCarDatas != null) {
			RegistThirdCarDatabase registThirdCarDataBase = new RegistThirdCarDatabase(
					context);
			registThirdCarDataBase.insertRegistThirdCar(
					"regist_third_car_data", registThirdCarDatas);
		}
	}

	public void update(Context context, String registNo) throws Exception {
		if (basicSurvey != null) {
			// 基本信息保存
			BasicSurveyDatabase baiscDatabase = new BasicSurveyDatabase(context);
			basicSurvey.init();
			baiscDatabase.updateBasicSurvey("basic_survey", registNo,
					basicSurvey);
		}
		if (carDatas != null) {
			// 涉案车辆
			CarDatasDatabase vehicleDatabase = new CarDatasDatabase(context);
			vehicleDatabase.updateCarData("car_data", registNo, carDatas);
		}
		if (personData != null) {
			// 人伤信息保存

			PersonDataDatabase personDataBase = new PersonDataDatabase(context);
			if (personDatas != null) {
				/*String id = personData.getId();
				if (id != null && !id.equals("")) {
					personDataBase.updatePersonData("person_data", registNo,personDatas);
				} else {
					personDataBase.insertPersonData("person_data", personData);
				}*/
				personDataBase.updatePersonData("person_data", registNo,personDatas);
			}
		}
		if (proDatas != null) {
			// 财产损失保存
			PropDataDatabase propertyDatabase = new PropDataDatabase(context);
			if (proDatas != null && !proDatas.isEmpty()) {
				propertyDatabase.updatePropData("prop_data", registNo, proDatas);
			}
		}
		if (liabilityRatios != null) {
			LiabilityRadioDatabase liabilityRadioDatabase = new LiabilityRadioDatabase(
					context);
			liabilityRadioDatabase.updateLiablity("liablity_data", registNo,
					liabilityRatios);
		}
		if (policyDatas != null) {
			// 保单信息
			PolicyDatasDatabase policyDatabase = new PolicyDatasDatabase(
					context);
			policyDatabase.updatePolicyData("policy_data", registNo,
					policyDatas);
		}

		if (registThirdCarDatas != null) {
			RegistThirdCarDatabase registThirdCarDataBase = new RegistThirdCarDatabase(
					context);
			// registThirdCarDataBase.deleteRegistThirdCar("regist_third_car_data",
			// registNo);
			// registThirdCarDataBase.insertRegistThirdCar("regist_third_car_data",
			// registThirdCarDatas);
			registThirdCarDataBase.updateRegistThirdCar(
					"regist_third_car_data", registNo, registThirdCarDatas);
		}

	}

	public boolean isExist(Context context, String registNo) throws Exception {
		BasicSurveyDatabase baiscDatabase = new BasicSurveyDatabase(context);
		BasicSurvey basic = baiscDatabase.selectBasicSurvey("basic_survey",
				registNo, null);

		if (basic != null) {
			return true;

		}
		return false;

	}

	/**
	 * @return the basicSurvey
	 */
	public BasicSurvey getBasicSurvey() {
		return basicSurvey;
	}

	/**
	 * @param basicSurvey
	 *            the basicSurvey to set
	 */
	public void setBasicSurvey(BasicSurvey basicSurvey) {
		this.basicSurvey = basicSurvey;
	}

	/**
	 * @return the carDatas
	 */
	public List<CarData> getCarDatas() {
		return carDatas;
	}

	/**
	 * @param carDatas
	 *            the carDatas to set
	 */
	public void setCarDatas(List<CarData> carDatas) {
		this.carDatas = carDatas;
	}

	/**
	 * @return the personData
	 */
	public PersonData getPersonData() {
		return personData;
	}

	/**
	 * @param personData
	 *            the personData to set
	 */
	public void setPersonData(PersonData personData) {
		this.personData = personData;
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
	 * @return the policyDatas
	 */
	public List<PolicyData> getPolicyDatas() {
		return policyDatas;
	}

	/**
	 * @param policyDatas
	 *            the policyDatas to set
	 */
	public void setPolicyDatas(List<PolicyData> policyDatas) {
		this.policyDatas = policyDatas;
	}

	/**
	 * @return the proDatas
	 */
	public List<PropData> getProDatas() {
		return proDatas;
	}

	/**
	 * @param proDatas
	 *            the proDatas to set
	 */
	public void setProDatas(List<PropData> proDatas) {
		this.proDatas = proDatas;
	}

	/**
	 * @return the liabilityRatios
	 */
	public List<LiabilityRatio> getLiabilityRatios() {
		return liabilityRatios;
	}

	/**
	 * @param liabilityRatios
	 *            the liabilityRatios to set
	 */
	public void setLiabilityRatios(List<LiabilityRatio> liabilityRatios) {
		this.liabilityRatios = liabilityRatios;
	}

	/**
	 * @return the registThirdCarDatas
	 */
	public List<RegistThirdCarData> getRegistThirdCarDatas() {
		return registThirdCarDatas;
	}

	/**
	 * @param registThirdCarDatas
	 *            the registThirdCarDatas to set
	 */
	public void setRegistThirdCarDatas(
			List<RegistThirdCarData> registThirdCarDatas) {
		this.registThirdCarDatas = registThirdCarDatas;
	}

	/**
	 * @return the kindCodeDatas
	 */
	public List<KindCodeData2> getKindCodeDatas() {
		return kindCodeDatas;
	}

	/**
	 * @param kindCodeDatas
	 *            the kindCodeDatas to set
	 */
	public void setKindCodeDatas(List<KindCodeData2> kindCodeDatas) {
		this.kindCodeDatas = kindCodeDatas;
	}

}
