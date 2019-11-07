package com.sinosoft.ms.service.impl;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinosoft.ms.model.KindCodeData;
import com.sinosoft.ms.service.IKindCodeDataService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.db.DatabaseHelper;
/**
 * 系统名：MobileSurvey 
 * 子系统名：定损险别数据层服务接口 实现类
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public class KindCodeDataService implements IKindCodeDataService {
	private Activity activity = null;
	private SQLiteDatabase db = null;
	
	public KindCodeDataService() {
		if(null==activity){
			activity = ActivityManage.getInstance().peek();
		}
	}

	@Override
	public List<KindCodeData> getById(long id) throws Exception {
		List<KindCodeData> kindCodeDataList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from kind_code_data where car_id = "+id;
			Cursor cursor = db.rawQuery(sql, null);
			kindCodeDataList = new LinkedList<KindCodeData>();
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				KindCodeData kindCodeData = new KindCodeData();
				kindCodeData.setCarId(cursor.getLong(cursor.getColumnIndex("car_id")));
				kindCodeData.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				kindCodeData.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				kindCodeDataList.add(kindCodeData);
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
		return kindCodeDataList;
	}

	@Override
	public boolean update(KindCodeData kindCodeData) throws Exception {
		boolean result = false;
		try{
			if(null != kindCodeData){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues assistValues = new ContentValues();
				ContentValues kindValues = new ContentValues();
	//			kindValues.put("car_id", kindCodeData.getCarId());
				kindValues.put("insure_term", kindCodeData.getInsureTerm());
				kindValues.put("insure_term_code", kindCodeData.getInsureTermCode());
				db.update("kind_code_data", assistValues, "car_id=?", new String[]{String.valueOf(kindCodeData.getCarId())});
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
	public int save(KindCodeData kindCodeData) throws Exception {
		int result = 0;
		try{
			if(null != kindCodeData){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues kindValues = new ContentValues();
				kindValues.put("car_id", kindCodeData.getCarId());
				kindValues.put("insure_term", kindCodeData.getInsureTerm());
				kindValues.put("insure_term_code", kindCodeData.getInsureTermCode());
				result = (int)db.insert("kind_code_data", null, kindValues);
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
