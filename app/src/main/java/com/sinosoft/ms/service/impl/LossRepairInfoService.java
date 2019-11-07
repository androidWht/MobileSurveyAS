package com.sinosoft.ms.service.impl;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.service.ILossRepairInfoService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.db.DatabaseHelper;
/**
 * 系统名：MobileSurvey 
 * 子系统名：定损换件数据层服务接口 实现类
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public class LossRepairInfoService implements ILossRepairInfoService {
	private Activity activity = null;
	private SQLiteDatabase db = null;
	
	public LossRepairInfoService() {
		if(null==activity){
			activity = ActivityManage.getInstance().peek();
		}
	}

	@Override
	public List<LossRepairInfoItem> getByRegistNo(String registNo) throws Exception {
		List<LossRepairInfoItem> lossRepairInfoList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from loss_repair_info_item where regist_no='"+registNo+"'";
			Cursor cursor = db.rawQuery(sql, null);
			lossRepairInfoList = new LinkedList<LossRepairInfoItem>();
			/*if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}*/
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				LossRepairInfoItem lossRepairInfoItem = new LossRepairInfoItem();
				lossRepairInfoItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
				lossRepairInfoItem.setRegistNo(cursor.getString(cursor.getColumnIndex("regist_no")));
				lossRepairInfoItem.setJyId(cursor.getString(cursor.getColumnIndex("jyId")));
				
				lossRepairInfoItem.setRepairItemName(cursor.getString(cursor.getColumnIndex("repair_item_name")));
				lossRepairInfoItem.setSerialNo(cursor.getLong(cursor.getColumnIndex("serial_no")));
				lossRepairInfoItem.setRepairCode(cursor.getString(cursor.getColumnIndex("repair_code")));
				lossRepairInfoItem.setRepairName(cursor.getString(cursor.getColumnIndex("repair_name")));
				lossRepairInfoItem.setRepairType(cursor.getString(cursor.getColumnIndex("repair_type")));
				lossRepairInfoItem.setRepairId(cursor.getString(cursor.getColumnIndex("repair_id")));
				lossRepairInfoItem.setRepairItemCode(cursor.getString(cursor.getColumnIndex("repair_item_code")));
				lossRepairInfoItem.setLevelCode(cursor.getString(cursor.getColumnIndex("level_code")));
				lossRepairInfoItem.setManHour(cursor.getDouble(cursor.getColumnIndex("man_hour")));
				lossRepairInfoItem.setRepairFee(cursor.getDouble(cursor.getColumnIndex("repair_fee")));
				lossRepairInfoItem.setSelfConfigFlag(cursor.getString(cursor.getColumnIndex("self_config_flag")));
				lossRepairInfoItem.setStatus(cursor.getString(cursor.getColumnIndex("status")));
				lossRepairInfoItem.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
				lossRepairInfoItem.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				lossRepairInfoItem.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				lossRepairInfoItem.setHourFee(cursor.getDouble(cursor.getColumnIndex("repair_rate")));
				lossRepairInfoItem.setDefineType(cursor.getString(cursor.getColumnIndex("define_type")));
				
				lossRepairInfoList.add(lossRepairInfoItem);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return lossRepairInfoList;
	}

	@Override
	public boolean update(LossRepairInfoItem lossRepairInfoItem) throws Exception {
		boolean result = false;
		try{
			if(null != lossRepairInfoItem){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues repairValues = new ContentValues();
	//			repairValues.put("regist_no", lossRepairInfoItem.getRegistNo());
				repairValues.put("repair_item_name", lossRepairInfoItem.getRepairItemName());
				repairValues.put("serial_no", lossRepairInfoItem.getSerialNo());
				repairValues.put("repair_code", lossRepairInfoItem.getRepairCode());
				repairValues.put("repair_name", lossRepairInfoItem.getRepairName());
				repairValues.put("repair_type", lossRepairInfoItem.getRepairType());
				repairValues.put("repair_id", lossRepairInfoItem.getRepairId());
				repairValues.put("repair_item_code", lossRepairInfoItem.getRepairItemCode());
				repairValues.put("level_code", lossRepairInfoItem.getLevelCode());
				repairValues.put("man_hour", lossRepairInfoItem.getManHour());
				repairValues.put("repair_fee", lossRepairInfoItem.getRepairFee());
				repairValues.put("self_config_flag", lossRepairInfoItem.getSelfConfigFlag());
				repairValues.put("status", lossRepairInfoItem.getStatus());
				repairValues.put("remark", lossRepairInfoItem.getRemark());
				repairValues.put("insure_term_code", lossRepairInfoItem.getInsureTermCode());
				repairValues.put("insure_term", lossRepairInfoItem.getInsureTerm());
				repairValues.put("repair_rate", lossRepairInfoItem.getHourFee());
				repairValues.put("define_type", lossRepairInfoItem.getDefineType());
				db.update("loss_repair_info_item", repairValues, "id=?", new String[]{String.valueOf(lossRepairInfoItem.getId())});
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
	public int save(LossRepairInfoItem lossRepairInfoItem) throws Exception {
		int result = 0;
		try{
			if(null != lossRepairInfoItem){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues repairValues = new ContentValues();
				repairValues.put("regist_no", lossRepairInfoItem.getRegistNo());
				repairValues.put("repair_item_name", lossRepairInfoItem.getRepairItemName());
				repairValues.put("serial_no", lossRepairInfoItem.getSerialNo());
				repairValues.put("repair_code", lossRepairInfoItem.getRepairCode());
				repairValues.put("repair_name", lossRepairInfoItem.getRepairName());
				repairValues.put("repair_type", lossRepairInfoItem.getRepairType());
				repairValues.put("jyId", lossRepairInfoItem.getJyId());
				repairValues.put("repair_id", lossRepairInfoItem.getRepairId());
				repairValues.put("repair_item_code", lossRepairInfoItem.getRepairItemCode());
				repairValues.put("level_code", lossRepairInfoItem.getLevelCode());
				repairValues.put("man_hour", lossRepairInfoItem.getManHour());
				repairValues.put("repair_fee", lossRepairInfoItem.getRepairFee());
				repairValues.put("self_config_flag", lossRepairInfoItem.getSelfConfigFlag());
				repairValues.put("status", lossRepairInfoItem.getStatus());
				repairValues.put("remark", lossRepairInfoItem.getRemark());
				repairValues.put("insure_term_code", lossRepairInfoItem.getInsureTermCode());
				repairValues.put("insure_term", lossRepairInfoItem.getInsureTerm());
				repairValues.put("repair_rate", lossRepairInfoItem.getHourFee());
				repairValues.put("define_type", lossRepairInfoItem.getDefineType());
				
				
				result = (int)db.insert("loss_repair_info_item", null, repairValues);
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
	public LossRepairInfoItem getById(int lossRepairItemId) {
		LossRepairInfoItem lossRepairInfoItem = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from loss_repair_info_item where id="+lossRepairItemId;
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				lossRepairInfoItem = new LossRepairInfoItem();
				lossRepairInfoItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
				lossRepairInfoItem.setRegistNo(cursor.getString(cursor.getColumnIndex("regist_no")));
				lossRepairInfoItem.setJyId(cursor.getString(cursor.getColumnIndex("jyId")));
				lossRepairInfoItem.setRepairItemName(cursor.getString(cursor.getColumnIndex("repair_item_name")));
				lossRepairInfoItem.setSerialNo(cursor.getLong(cursor.getColumnIndex("serial_no")));
				lossRepairInfoItem.setRepairCode(cursor.getString(cursor.getColumnIndex("repair_code")));
				lossRepairInfoItem.setRepairName(cursor.getString(cursor.getColumnIndex("repair_name")));
				lossRepairInfoItem.setRepairType(cursor.getString(cursor.getColumnIndex("repair_type")));
				lossRepairInfoItem.setRepairId(cursor.getString(cursor.getColumnIndex("repair_id")));
				lossRepairInfoItem.setRepairItemCode(cursor.getString(cursor.getColumnIndex("repair_item_code")));
				lossRepairInfoItem.setLevelCode(cursor.getString(cursor.getColumnIndex("level_code")));
				lossRepairInfoItem.setManHour(cursor.getDouble(cursor.getColumnIndex("man_hour")));
				lossRepairInfoItem.setHourFee(cursor.getDouble(cursor.getColumnIndex("man_hour")));
				lossRepairInfoItem.setRepairFee(cursor.getDouble(cursor.getColumnIndex("repair_fee")));
				lossRepairInfoItem.setSelfConfigFlag(cursor.getString(cursor.getColumnIndex("self_config_flag")));
				lossRepairInfoItem.setStatus(cursor.getString(cursor.getColumnIndex("status")));
				lossRepairInfoItem.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
				lossRepairInfoItem.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				lossRepairInfoItem.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				lossRepairInfoItem.setHourFee(cursor.getDouble(cursor.getColumnIndex("repair_rate")));
				lossRepairInfoItem.setDefineType(cursor.getString(cursor.getColumnIndex("define_type")));
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
		return lossRepairInfoItem;
	}

	@Override
	public List<LossRepairInfoItem> getRepairList(String professionId,
			String keyName,String registNo, String professionType, 
			String professionName,String repairType) {
		List<LossRepairInfoItem> lossRepairInfoList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from REPAIR_STD_ITEM where REPAIR_GROUP_ID='"+professionId+"'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			lossRepairInfoList = new LinkedList<LossRepairInfoItem>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				LossRepairInfoItem lossRepairInfoItem = new LossRepairInfoItem();
				lossRepairInfoItem.setRegistNo(registNo);//
				lossRepairInfoItem.setRepairItemName(cursor.getString(cursor.getColumnIndex("REPAIR_NAME")));//
				lossRepairInfoItem.setRepairItemCode(cursor.getString(cursor.getColumnIndex("REPAIR_CODE")));//
				lossRepairInfoItem.setRepairCode(cursor.getString(cursor.getColumnIndex("REPAIR_GROUP_ID")));//
				lossRepairInfoItem.setRepairId(cursor.getString(cursor.getColumnIndex("ID")));//
				lossRepairInfoItem.setRepairName(professionName);
				lossRepairInfoItem.setRepairType(repairType);//工种类型
				lossRepairInfoList.add(lossRepairInfoItem);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return lossRepairInfoList;
	}

	@Override
	public boolean delete(int repairInfoid) {
		boolean result = false;
		try{
			if(0 != repairInfoid){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				int id = db.delete("loss_repair_info_item","id=?", new String[]{String.valueOf(repairInfoid)});
				if(0 != id){
					result = true;
				}
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
	public boolean deleteByJyId(String jyId) {
		// TODO Auto-generated method stub
		boolean result = false;
		try{
			if(null != jyId){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				int id = db.delete("loss_repair_info_item","jyId=?", new String[]{jyId});
				if(0 != id){
					result = true;
				}
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
