package com.sinosoft.ms.service.impl;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.service.ILossAssistInfoService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.db.DatabaseHelper;
/**
 * 系统名：MobileSurvey 
 * 子系统名：定损辅料数据层服务接口 实现类
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public class LossAssistInfoService implements ILossAssistInfoService {
	private Activity activity = null;
	private SQLiteDatabase db = null;
	
	public LossAssistInfoService() {
		if(null==activity){
			activity = ActivityManage.getInstance().peek();
		}
	}

	@Override
	public List<LossAssistInfoItem> getByRegistNo(String taskId) throws Exception {
		List<LossAssistInfoItem> lossFitInfoList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from loss_assist_info_item where regist_no='"+taskId+"'";
			Cursor cursor = db.rawQuery(sql, null);
			
			lossFitInfoList = new LinkedList<LossAssistInfoItem>();
			
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				LossAssistInfoItem lossAssistInfoItem = new LossAssistInfoItem();
				lossAssistInfoItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
				lossAssistInfoItem.setRegistNo(cursor.getString(cursor.getColumnIndex("regist_no")));
				lossAssistInfoItem.setJyId(cursor.getString(cursor.getColumnIndex("jyId")));
				
				lossAssistInfoItem.setSerialNo(cursor.getLong(cursor.getColumnIndex("serial_no")));
				lossAssistInfoItem.setMaterialName(cursor.getString(cursor.getColumnIndex("material_name")));
				lossAssistInfoItem.setMaterialCode(cursor.getString(cursor.getColumnIndex("material_code")));
				lossAssistInfoItem.setUnitPrice(cursor.getDouble(cursor.getColumnIndex("unit_price")));
				lossAssistInfoItem.setCount(cursor.getLong(cursor.getColumnIndex("count")));
				lossAssistInfoItem.setMaterialFee(cursor.getDouble(cursor.getColumnIndex("material_fee")));
				lossAssistInfoItem.setStatus(cursor.getString(cursor.getColumnIndex("status")));
				lossAssistInfoItem.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
				lossAssistInfoItem.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				lossAssistInfoItem.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				lossAssistInfoItem.setDefineType(cursor.getString(cursor.getColumnIndex("define_type")));
				lossFitInfoList.add(lossAssistInfoItem);
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
		return lossFitInfoList;
	}

	@Override
	public boolean update(LossAssistInfoItem lossAssistInfoItem) throws Exception {
		boolean result = false;
		try{
			if(null != lossAssistInfoItem){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues assistValues = new ContentValues();
	//			assistValues.put("regist_no", lossAssistInfoItem.getRegistNo());
				assistValues.put("serial_no", lossAssistInfoItem.getSerialNo());
				assistValues.put("material_name", lossAssistInfoItem.getMaterialName());
				assistValues.put("material_code", lossAssistInfoItem.getMaterialCode());
				assistValues.put("unit_price", lossAssistInfoItem.getUnitPrice());
				assistValues.put("count", lossAssistInfoItem.getCount());
				assistValues.put("material_fee", lossAssistInfoItem.getMaterialFee());
				assistValues.put("status", lossAssistInfoItem.getStatus());
				assistValues.put("remark", lossAssistInfoItem.getRemark());
				assistValues.put("insure_term_code", lossAssistInfoItem.getInsureTermCode());
				assistValues.put("insure_term", lossAssistInfoItem.getInsureTerm());
				assistValues.put("define_type", lossAssistInfoItem.getDefineType());
				db.update("loss_assist_info_item", assistValues, "id=?", new String[]{String.valueOf(lossAssistInfoItem.getId())});
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
	public int save(LossAssistInfoItem lossAssistInfoItem) throws Exception {
		int result = 0;
		try{
			if(null != lossAssistInfoItem){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues assistValues = new ContentValues();
				assistValues.put("regist_no", lossAssistInfoItem.getRegistNo());
				assistValues.put("jyId", lossAssistInfoItem.getJyId());
				
				assistValues.put("serial_no", lossAssistInfoItem.getSerialNo());
				assistValues.put("material_name", lossAssistInfoItem.getMaterialName());
				assistValues.put("material_code", lossAssistInfoItem.getMaterialCode());
				assistValues.put("unit_price", lossAssistInfoItem.getUnitPrice());
				assistValues.put("count", lossAssistInfoItem.getCount());
				assistValues.put("material_fee", lossAssistInfoItem.getMaterialFee());
				assistValues.put("status", lossAssistInfoItem.getStatus());
				assistValues.put("remark", lossAssistInfoItem.getRemark());
				assistValues.put("insure_term_code", lossAssistInfoItem.getInsureTermCode());
				assistValues.put("insure_term", lossAssistInfoItem.getInsureTerm());
				assistValues.put("define_type", lossAssistInfoItem.getDefineType());
				result = (int)db.insert("loss_assist_info_item", null, assistValues);
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
	public LossAssistInfoItem getById(int assistInfoId) {
		LossAssistInfoItem lossAssistInfoItem = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from loss_assist_info_item where id="+assistInfoId;
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				lossAssistInfoItem = new LossAssistInfoItem();
				lossAssistInfoItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
				lossAssistInfoItem.setRegistNo(cursor.getString(cursor.getColumnIndex("regist_no")));
				lossAssistInfoItem.setJyId(cursor.getString(cursor.getColumnIndex("jyId")));
				
				lossAssistInfoItem.setSerialNo(cursor.getLong(cursor.getColumnIndex("serial_no")));
				lossAssistInfoItem.setMaterialName(cursor.getString(cursor.getColumnIndex("material_name")));
				lossAssistInfoItem.setMaterialCode(cursor.getString(cursor.getColumnIndex("material_code")));
				lossAssistInfoItem.setUnitPrice(cursor.getDouble(cursor.getColumnIndex("unit_price")));
				lossAssistInfoItem.setCount(cursor.getLong(cursor.getColumnIndex("count")));
				lossAssistInfoItem.setMaterialFee(cursor.getDouble(cursor.getColumnIndex("material_fee")));
				lossAssistInfoItem.setStatus(cursor.getString(cursor.getColumnIndex("status")));
				lossAssistInfoItem.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
				lossAssistInfoItem.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				lossAssistInfoItem.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				lossAssistInfoItem.setDefineType(cursor.getString(cursor.getColumnIndex("define_type")));
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
		return lossAssistInfoItem;
	}

	@Override
	public boolean delete(int assistInfoid) {
		boolean result = false;
		try{
			if(0 != assistInfoid){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				int id = db.delete("loss_assist_info_item","id=?", new String[]{String.valueOf(assistInfoid)});
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

	/* (non-Javadoc)
	 * @see com.sinosoft.ms.service.ILossAssistInfoService#deleteByAssistId(java.lang.String)
	 */
	@Override
	public boolean deleteByJyId(String jyId) {
		// TODO Auto-generated method stub
		boolean result = false;
		try{
			if(null != jyId){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				int id = db.delete("loss_assist_info_item","jyId=?", new String[]{jyId});
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
