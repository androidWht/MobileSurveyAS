package com.sinosoft.ms.service.impl;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.LossVehicleInsurance;
import com.sinosoft.ms.service.ILossVehicleService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.db.DatabaseHelper;
import com.sinosoft.ms.utils.db.DictCashe;
/**
 * 系统名：MobileSurvey 
 * 子系统名：定损车辆信息数据层服务接口 实现类
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public class LossVehicleService implements ILossVehicleService {
	private Activity activity = null;
	private SQLiteDatabase db = null;
	
	public LossVehicleService() {
		if(null==activity){
			activity = ActivityManage.getInstance().peek();
		}
	}

	@Override
	public LossVehicle getByRegistNo(String registNo) throws Exception {
		LossVehicle lossVehicle = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from loss_vehicle where regist_no='"+registNo+"'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				lossVehicle = new LossVehicle();
				lossVehicle.setId(cursor.getInt(cursor.getColumnIndex("id")));
				lossVehicle.setRegistNo(registNo);
				lossVehicle.setLicenseNo(cursor.getString(cursor.getColumnIndex("license_no")));
				lossVehicle.setDefLossCarType(cursor.getString(cursor.getColumnIndex("def_loss_car_type")));
				lossVehicle.setLossType(cursor.getString(cursor.getColumnIndex("loss_type")));
				lossVehicle.setCarOwner(cursor.getString(cursor.getColumnIndex("car_owner")));
				lossVehicle.setEnrollDate(cursor.getString(cursor.getColumnIndex("enroll_date")));
				lossVehicle.setCarKindCode(cursor.getString(cursor.getColumnIndex("car_kind_code")));
				lossVehicle.setFrameNo(cursor.getString(cursor.getColumnIndex("frame_no")));
				lossVehicle.setLicenseColorCode(cursor.getString(cursor.getColumnIndex("license_color_code")));
				lossVehicle.setModelCode(cursor.getString(cursor.getColumnIndex("model_code")));
				lossVehicle.setVinNo(cursor.getString(cursor.getColumnIndex("vin_no")));
				lossVehicle.setLicenseType(cursor.getString(cursor.getColumnIndex("license_type")));
				lossVehicle.setBrandName(cursor.getString(cursor.getColumnIndex("brand_name")));
				lossVehicle.setGearboxType(cursor.getString(cursor.getColumnIndex("gearbox_type")));
				lossVehicle.setGasType(cursor.getString(cursor.getColumnIndex("gas_type")));
				lossVehicle.setCarKindFrom(cursor.getString(cursor.getColumnIndex("car_kind_from")));
				lossVehicle.setEngineNo(cursor.getString(cursor.getColumnIndex("engine_no")));
				lossVehicle.setInsureComCode(cursor.getString(cursor.getColumnIndex("insure_com_code")));
				lossVehicle.setIndemDutyCi(cursor.getString(cursor.getColumnIndex("indem_duty_ci")));
				lossVehicle.setIndemnityDuty(cursor.getString(cursor.getColumnIndex("indemnity_duty")));
				lossVehicle.setIndemnityDutyRate(cursor.getString(cursor.getColumnIndex("indemnity_duty_rate")));
				
				//2014-05-28 新增
				lossVehicle.setLicenseColorCodeTo2(cursor.getString(cursor.getColumnIndex("licenseColorCodeTo2")));
				lossVehicle.setModelName(cursor.getString(cursor.getColumnIndex("model_name")));
				
				cursor.moveToNext();
				break;
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return lossVehicle;
	}

	@Override
	public boolean update(LossVehicle lossVehicle) throws Exception {
		boolean result = false;
		try{
			if(null != lossVehicle){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues newValues = new ContentValues();
				newValues.put("license_no",lossVehicle.getLicenseNo());
				newValues.put("def_loss_car_type",lossVehicle.getDefLossCarType());
				newValues.put("loss_type",lossVehicle.getLossType());
				newValues.put("car_owner",lossVehicle.getCarOwner());
				newValues.put("enroll_date",lossVehicle.getEnrollDate());
				newValues.put("car_kind_code",lossVehicle.getCarKindCode());
				newValues.put("frame_no",lossVehicle.getFrameNo());
				newValues.put("license_color_code",lossVehicle.getLicenseColorCode());
				newValues.put("model_code",lossVehicle.getModelCode());
				newValues.put("vin_no",lossVehicle.getVinNo());
				newValues.put("license_type",lossVehicle.getLicenseType());
				newValues.put("brand_name",lossVehicle.getBrandName());
				newValues.put("gearbox_type",lossVehicle.getGearboxType());
				newValues.put("gas_type",lossVehicle.getGasType());
				newValues.put("car_kind_from",lossVehicle.getCarKindFrom());
				newValues.put("engine_no",lossVehicle.getEngineNo());
				newValues.put("insure_com_code",lossVehicle.getInsureComCode());
				newValues.put("indem_duty_ci",lossVehicle.getIndemDutyCi());
				newValues.put("indemnity_duty",lossVehicle.getIndemnityDuty());
				newValues.put("indemnity_duty_rate",lossVehicle.getIndemnityDutyRate());
	//			newValues.put("regist_no",lossVehicle.getRegistNo());
				newValues.put("insure_vehicle_code",lossVehicle.getInsureVehicleCode());
				newValues.put("com_code",lossVehicle.getComCode());
				
				//2014-05-28 新增
				newValues.put("licenseColorCodeTo2", lossVehicle.getLicenseColorCodeTo2());
				newValues.put("model_name", lossVehicle.getModelName());
				
				db.update("loss_vehicle", newValues, "regist_no=?", new String[]{lossVehicle.getRegistNo()});
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return result;
	}

	@Override
	public int save(LossVehicle lossVehicle) throws Exception {
		int result = 0;
		try{
			if(null != lossVehicle){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues vehicleValues = new ContentValues();
				vehicleValues.put("license_no", lossVehicle.getLicenseNo());
				vehicleValues.put("def_loss_car_type", lossVehicle.getDefLossCarType());
				vehicleValues.put("regist_no", lossVehicle.getRegistNo());
				vehicleValues.put("loss_type", lossVehicle.getLossType());
				vehicleValues.put("car_owner", lossVehicle.getCarOwner());
				vehicleValues.put("enroll_date", lossVehicle.getEnrollDate());
				vehicleValues.put("car_kind_code", lossVehicle.getCarKindCode());
				vehicleValues.put("frame_no", lossVehicle.getFrameNo());
				vehicleValues.put("license_color_code", lossVehicle.getLicenseColorCode());
				vehicleValues.put("model_code", lossVehicle.getModelCode());
				vehicleValues.put("vin_no", lossVehicle.getVinNo());
				vehicleValues.put("license_type", lossVehicle.getLicenseType());
				vehicleValues.put("brand_name", lossVehicle.getBrandName());
				vehicleValues.put("gearbox_type", lossVehicle.getGearboxType());
				vehicleValues.put("gas_type", lossVehicle.getGasType());
				vehicleValues.put("car_kind_from", lossVehicle.getCarKindFrom());
				vehicleValues.put("engine_no", lossVehicle.getEngineNo ());
				vehicleValues.put("insure_com_code", lossVehicle.getInsureComCode());
				vehicleValues.put("indem_duty_ci", lossVehicle.getIndemDutyCi());
				vehicleValues.put("indemnity_duty", lossVehicle.getIndemnityDuty());
				if(StringUtils.isEmpty(lossVehicle.getIndemnityDutyRate())){
					String key = SpinnerUtils.getKey(lossVehicle.getIndemnityDuty(), DictCashe.getInstant().getDict().get("IndemnityDutyRate"));
					lossVehicle.setIndemnityDutyRate(key);
				}
				vehicleValues.put("indemnity_duty_rate", lossVehicle.getIndemnityDutyRate());
				
				//2014-05-28 新增
				vehicleValues.put("licenseColorCodeTo2", lossVehicle.getLicenseColorCodeTo2());
				vehicleValues.put("model_name", lossVehicle.getModelName());
				
				result = (int)db.insert("loss_vehicle", null, vehicleValues);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return result;
	}

	@Override
	public int save(LossVehicleInsurance lossVehicleInsurance) throws Exception {
		int result = 0;
		try{
			if(null != lossVehicleInsurance){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues insurValues = new ContentValues();
				lossVehicleInsurance.init();
				insurValues.put("policy_flag", lossVehicleInsurance.getPolicyFlag());
				insurValues.put("opp_regist_no_bi", lossVehicleInsurance.getOppRegistNoBi());
				insurValues.put("opp_policy_no_bi", lossVehicleInsurance.getOppPolicyNoBi());
				insurValues.put("opp_insurer_code_bi", lossVehicleInsurance.getOppInsurerCodeBi());
				insurValues.put("opp_insurer_area_bi", lossVehicleInsurance.getOppInsurerAreaBi());
				insurValues.put("opp_regist_no_ci", lossVehicleInsurance.getOppRegistNoCi());
				insurValues.put("opp_policy_no_ci", lossVehicleInsurance.getOppPolicyNoCi());
				insurValues.put("opp_insurer_code_ci", lossVehicleInsurance.getOppInsurerCodeCi());
				insurValues.put("opp_insurer_area_ci", lossVehicleInsurance.getOppInsurerAreaCi());
				insurValues.put("loss_vehicle_id", lossVehicleInsurance.getLossVehicleId());
				
				/** add by zhengtongsheng **/
				insurValues.put("oppIdBi", lossVehicleInsurance.getOppIdBi());
				insurValues.put("oppIdCi", lossVehicleInsurance.getOppIdCi());
				
				result = (int)db.insert("loss_vehicle_insurance", null, insurValues);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return result;
	}

	@Override
	public List<LossVehicleInsurance> getById(Integer id) throws Exception {
		List<LossVehicleInsurance> vehicleInsurList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from loss_vehicle_insurance where loss_vehicle_id='"+id+"'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			vehicleInsurList = new LinkedList<LossVehicleInsurance>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				LossVehicleInsurance vehicleInsurance = new LossVehicleInsurance();
				vehicleInsurance.setId(cursor.getInt(cursor.getColumnIndex("id")));
				vehicleInsurance.setPolicyFlag(cursor.getString(cursor.getColumnIndex("policy_flag")));
				vehicleInsurance.setOppRegistNoBi(cursor.getString(cursor.getColumnIndex("opp_regist_no_bi")));
				vehicleInsurance.setOppPolicyNoBi(cursor.getString(cursor.getColumnIndex("opp_policy_no_bi")));
				vehicleInsurance.setOppInsurerCodeBi(cursor.getString(cursor.getColumnIndex("opp_insurer_code_bi")));
				vehicleInsurance.setOppInsurerAreaBi(cursor.getString(cursor.getColumnIndex("opp_insurer_area_bi")));
				vehicleInsurance.setOppRegistNoCi(cursor.getString(cursor.getColumnIndex("opp_regist_no_ci")));
				vehicleInsurance.setOppPolicyNoCi(cursor.getString(cursor.getColumnIndex("opp_policy_no_ci")));
				vehicleInsurance.setOppInsurerCodeCi(cursor.getString(cursor.getColumnIndex("opp_insurer_code_ci")));
				vehicleInsurance.setOppInsurerAreaCi(cursor.getString(cursor.getColumnIndex("opp_insurer_area_ci")));
				vehicleInsurance.setLossVehicleId(cursor.getInt(cursor.getColumnIndex("loss_vehicle_id")));
				/** add by zhengtongsheng **/
				vehicleInsurance.setOppIdBi(cursor.getString(cursor.getColumnIndex("oppIdBi")));
				vehicleInsurance.setOppIdCi(cursor.getString(cursor.getColumnIndex("oppIdCi")));
				
				vehicleInsurList.add(vehicleInsurance);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return vehicleInsurList;
	}

	@Override
	public boolean update(LossVehicleInsurance lossVehicleInsurance)
			throws Exception {
		boolean result = false;
		try{
			if(null != lossVehicleInsurance){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues insurValues = new ContentValues();
				lossVehicleInsurance.init();
				insurValues.put("policy_flag", lossVehicleInsurance.getPolicyFlag());
				insurValues.put("opp_regist_no_bi", lossVehicleInsurance.getOppRegistNoBi());
				insurValues.put("opp_policy_no_bi", lossVehicleInsurance.getOppPolicyNoBi());
				insurValues.put("opp_insurer_code_bi", lossVehicleInsurance.getOppInsurerCodeBi());
				insurValues.put("opp_insurer_area_bi", lossVehicleInsurance.getOppInsurerAreaBi());
				insurValues.put("opp_regist_no_ci", lossVehicleInsurance.getOppRegistNoCi());
				insurValues.put("opp_policy_no_ci", lossVehicleInsurance.getOppPolicyNoCi());
				insurValues.put("opp_insurer_code_ci", lossVehicleInsurance.getOppInsurerCodeCi());
				insurValues.put("opp_insurer_area_ci", lossVehicleInsurance.getOppInsurerAreaCi());
				insurValues.put("loss_vehicle_id", lossVehicleInsurance.getLossVehicleId());
				/** add by zhengtongsheng **/
				insurValues.put("oppIdBi", lossVehicleInsurance.getOppIdBi());
				insurValues.put("oppIdCi", lossVehicleInsurance.getOppIdCi());
				
				db.update("loss_vehicle_insurance", insurValues, "id=?", new String[]{String.valueOf(lossVehicleInsurance.getId())});
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return result;
	}
	
	@Override
	public boolean delete(LossVehicleInsurance lossVehicleInsurance)
			throws Exception {
		boolean result = false;
		try{
			if(null != lossVehicleInsurance){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				db.delete("loss_vehicle_insurance", "id=?", new String[]{String.valueOf(lossVehicleInsurance.getId())});
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return result;
	}
}
