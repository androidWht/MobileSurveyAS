package com.sinosoft.ms.service.impl;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.service.IBasicVehicleService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.db.DatabaseHelper;

/**
 * 系统名：MobileSurvey 子系统名：车辆基本信息数据层服务接口 实现类 著作权：COPYRIGHT (C) 2013 SINOSOFT
 * INFORMATION SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public class BasicVehicleService implements IBasicVehicleService {
	private Activity activity = null;
	private SQLiteDatabase db = null;

	public BasicVehicleService() {
		if (null == activity) {
			activity = ActivityManage.getInstance().peek();
		}
	}

	@Override
	public BasicVehicle getByRegistNo(String registNo) throws Exception {
		BasicVehicle basicVehicle = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from basic_vehicle where regist_no='"
					+ registNo + "'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				basicVehicle = new BasicVehicle();
				basicVehicle.setRegistNo(cursor.getString(cursor
						.getColumnIndex("regist_no")));
				basicVehicle.setCaseNo(cursor.getString(cursor
						.getColumnIndex("case_no")));
				basicVehicle.setLossNo(cursor.getLong(cursor
						.getColumnIndex("loss_no")));
				basicVehicle.setVehKindCode(cursor.getString(cursor
						.getColumnIndex("veh_kind_code")));
				basicVehicle.setVehKindName(cursor.getString(cursor
						.getColumnIndex("veh_kind_name")));
				basicVehicle.setVehCertainCode(cursor.getString(cursor
						.getColumnIndex("veh_certain_code")));
				basicVehicle.setVehCertainName(cursor.getString(cursor
						.getColumnIndex("veh_certain_name")));
				basicVehicle.setVehSeriCode(cursor.getString(cursor
						.getColumnIndex("veh_seri_code")));
				basicVehicle.setVehSeriName(cursor.getString(cursor
						.getColumnIndex("veh_seri_name")));
				basicVehicle.setVehYearType(cursor.getString(cursor
						.getColumnIndex("veh_year_type")));
				basicVehicle.setVehFactoryCode(cursor.getString(cursor
						.getColumnIndex("veh_factory_code")));
				basicVehicle.setVehFactoryName(cursor.getString(cursor
						.getColumnIndex("veh_factory_name")));
				basicVehicle.setVehBrandCode(cursor.getString(cursor
						.getColumnIndex("veh_brand_code")));
				basicVehicle.setVehBrandName(cursor.getString(cursor
						.getColumnIndex("veh_brand_name")));
				basicVehicle.setPlateNo(cursor.getString(cursor
						.getColumnIndex("plate_no")));
				basicVehicle.setSelfConfigFlag(cursor.getString(cursor
						.getColumnIndex("self_config_flag")));
				basicVehicle.setVersion(cursor.getString(cursor
						.getColumnIndex("version")));
				basicVehicle.setRemark(cursor.getString(cursor
						.getColumnIndex("remark")));
				basicVehicle.setVehGroupCode(cursor.getString(cursor
						.getColumnIndex("veh_group_code")));
				basicVehicle.setVehGroupName(cursor.getString(cursor
						.getColumnIndex("veh_group_name")));
				basicVehicle.setSumChgCompFee(cursor.getDouble(cursor
						.getColumnIndex("sum_chg_comp_fee")));
				basicVehicle.setSumRepairFee(cursor.getDouble(cursor
						.getColumnIndex("sum_repair_fee")));
				basicVehicle.setSumMaterialFee(cursor.getDouble(cursor
						.getColumnIndex("sum_material_fee")));
				basicVehicle.setSumLossFee(cursor.getDouble(cursor
						.getColumnIndex("sum_loss_fee")));
				basicVehicle.setSumRemnant(cursor.getDouble(cursor
						.getColumnIndex("sumremnant")));
				basicVehicle.setSumRescueFee(cursor.getDouble(cursor
						.getColumnIndex("sumrescuefee")));
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
		return basicVehicle;
	}

	public BasicVehicle getByTaskId(String taskId) throws Exception {
		BasicVehicle basicVehicle = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from basic_vehicle where task_id='" + taskId
					+ "'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				basicVehicle = new BasicVehicle();
				basicVehicle.setRegistNo(cursor.getString(cursor
						.getColumnIndex("regist_no")));
				basicVehicle.setCaseNo(cursor.getString(cursor
						.getColumnIndex("case_no")));
				basicVehicle.setLossNo(cursor.getLong(cursor
						.getColumnIndex("loss_no")));
				basicVehicle.setVehKindCode(cursor.getString(cursor
						.getColumnIndex("veh_kind_code")));
				basicVehicle.setVehKindName(cursor.getString(cursor
						.getColumnIndex("veh_kind_name")));
				basicVehicle.setVehCertainCode(cursor.getString(cursor
						.getColumnIndex("veh_certain_code")));
				basicVehicle.setVehCertainName(cursor.getString(cursor
						.getColumnIndex("veh_certain_name")));
				basicVehicle.setVehSeriCode(cursor.getString(cursor
						.getColumnIndex("veh_seri_code")));
				basicVehicle.setVehSeriName(cursor.getString(cursor
						.getColumnIndex("veh_seri_name")));
				basicVehicle.setVehYearType(cursor.getString(cursor
						.getColumnIndex("veh_year_type")));
				basicVehicle.setVehFactoryCode(cursor.getString(cursor
						.getColumnIndex("veh_factory_code")));
				basicVehicle.setVehFactoryName(cursor.getString(cursor
						.getColumnIndex("veh_factory_name")));
				basicVehicle.setVehBrandCode(cursor.getString(cursor
						.getColumnIndex("veh_brand_code")));
				basicVehicle.setVehBrandName(cursor.getString(cursor
						.getColumnIndex("veh_brand_name")));
				basicVehicle.setPlateNo(cursor.getString(cursor
						.getColumnIndex("plate_no")));
				basicVehicle.setSelfConfigFlag(cursor.getString(cursor
						.getColumnIndex("self_config_flag")));
				basicVehicle.setVersion(cursor.getString(cursor
						.getColumnIndex("version")));
				basicVehicle.setRemark(cursor.getString(cursor
						.getColumnIndex("remark")));
				basicVehicle.setVehGroupCode(cursor.getString(cursor
						.getColumnIndex("veh_group_code")));
				basicVehicle.setVehGroupName(cursor.getString(cursor
						.getColumnIndex("veh_group_name")));
				basicVehicle.setSumChgCompFee(cursor.getDouble(cursor
						.getColumnIndex("sum_chg_comp_fee")));
				basicVehicle.setSumRepairFee(cursor.getDouble(cursor
						.getColumnIndex("sum_repair_fee")));
				basicVehicle.setSumMaterialFee(cursor.getDouble(cursor
						.getColumnIndex("sum_material_fee")));
				basicVehicle.setSumLossFee(cursor.getDouble(cursor
						.getColumnIndex("sum_loss_fee")));
				basicVehicle.setSumRemnant(cursor.getDouble(cursor
						.getColumnIndex("sumremnant")));
				basicVehicle.setSumRescueFee(cursor.getDouble(cursor
						.getColumnIndex("sumrescuefee")));
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
		return basicVehicle;
	}

	@Override
	public boolean update(BasicVehicle basicVehicle) throws Exception {
		boolean result = false;
		try {
			if (null != basicVehicle) {
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues basicValues = new ContentValues();
				// basicValues.put("regist_no", basicVehicle.getRegistNo());
				basicValues.put("case_no", basicVehicle.getCaseNo());
				basicValues.put("loss_no", basicVehicle.getLossNo());
				basicValues.put("veh_kind_code", basicVehicle.getVehKindCode());
				basicValues.put("veh_kind_name", basicVehicle.getVehKindName());
				basicValues.put("veh_certain_code",
						basicVehicle.getVehCertainCode());
				basicValues.put("veh_certain_name",
						basicVehicle.getVehCertainName());
				basicValues.put("veh_seri_code", basicVehicle.getVehSeriCode());
				basicValues.put("veh_seri_name", basicVehicle.getVehSeriName());
				basicValues.put("veh_year_type", basicVehicle.getVehYearType());
				basicValues.put("veh_factory_code",
						basicVehicle.getVehFactoryCode());
				basicValues.put("veh_factory_name",
						basicVehicle.getVehFactoryName());
				basicValues.put("veh_brand_code",
						basicVehicle.getVehBrandCode());
				basicValues.put("veh_brand_name",
						basicVehicle.getVehBrandName());
				basicValues.put("plate_no", basicVehicle.getPlateNo());
				basicValues.put("self_config_flag",
						basicVehicle.getSelfConfigFlag());
				basicValues.put("version", basicVehicle.getVersion());
				basicValues.put("remark", basicVehicle.getRemark());
				basicValues.put("veh_group_code",
						basicVehicle.getVehGroupCode());
				basicValues.put("veh_group_name",
						basicVehicle.getVehGroupName());
				basicValues.put("sum_chg_comp_fee",
						basicVehicle.getSumChgCompFee());
				basicValues.put("sum_repair_fee",
						basicVehicle.getSumRepairFee());
				basicValues.put("sum_material_fee",
						basicVehicle.getSumMaterialFee());
				basicValues.put("sum_loss_fee", basicVehicle.getSumLossFee());
				basicValues.put("sumremnant", basicVehicle.getSumRemnant());
				basicValues.put("sumrescuefee", basicVehicle.getSumRescueFee());
				db.update("basic_vehicle", basicValues, "regist_no=?",
						new String[] { basicVehicle.getRegistNo() });
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return result;
	}

	@Override
	public int save(BasicVehicle basicVehicle) throws Exception {
		int result = 0;
		try {
			if (null != basicVehicle) {
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues basicValues = new ContentValues();
				basicValues.put("regist_no", basicVehicle.getRegistNo());
				basicValues.put("case_no", basicVehicle.getCaseNo());
				basicValues.put("loss_no", basicVehicle.getLossNo());
				basicValues.put("veh_kind_code", basicVehicle.getVehKindCode());
				basicValues.put("veh_kind_name", basicVehicle.getVehKindName());
				basicValues.put("veh_certain_code",
						basicVehicle.getVehCertainCode());
				basicValues.put("veh_certain_name",
						basicVehicle.getVehCertainName());
				basicValues.put("veh_seri_code", basicVehicle.getVehSeriCode());
				basicValues.put("veh_seri_name", basicVehicle.getVehSeriName());
				basicValues.put("veh_year_type", basicVehicle.getVehYearType());
				basicValues.put("veh_factory_code",
						basicVehicle.getVehFactoryCode());
				basicValues.put("veh_factory_name",
						basicVehicle.getVehFactoryName());
				basicValues.put("veh_brand_code",
						basicVehicle.getVehBrandCode());
				basicValues.put("veh_brand_name",
						basicVehicle.getVehBrandName());
				basicValues.put("plate_no", basicVehicle.getPlateNo());
				basicValues.put("self_config_flag",
						basicVehicle.getSelfConfigFlag());
				basicValues.put("version", basicVehicle.getVersion());
				basicValues.put("remark", basicVehicle.getRemark());
				basicValues.put("veh_group_code",
						basicVehicle.getVehGroupCode());
				basicValues.put("veh_group_name",
						basicVehicle.getVehGroupName());
				basicValues.put("sum_chg_comp_fee",
						basicVehicle.getSumChgCompFee());
				basicValues.put("sum_repair_fee",
						basicVehicle.getSumRepairFee());
				basicValues.put("sum_material_fee",
						basicVehicle.getSumMaterialFee());
				basicValues.put("sum_loss_fee", basicVehicle.getSumLossFee());
				basicValues.put("sumremnant", basicVehicle.getSumRemnant());
				basicValues.put("sumrescuefee", basicVehicle.getSumRescueFee());

				result = (int) db.insert("basic_vehicle", null, basicValues);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return result;
	}

}
