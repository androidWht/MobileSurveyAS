package com.sinosoft.ms.service.impl;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sinosoft.ms.model.KindCodeData2;
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
public class KindCodeDataService2 {
	private Activity activity = null;
	private SQLiteDatabase db = null;
	
	public KindCodeDataService2() {
		if(null==activity){
			activity = ActivityManage.getInstance().peek();
		}
	}


	public List<KindCodeData2> getById(String id) throws Exception {
		List<KindCodeData2> KindCodeData2List = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from survey_kind_code_data where car_id = '"+id+"'";
			Cursor cursor = db.rawQuery(sql, null);
			KindCodeData2List = new LinkedList<KindCodeData2>();
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				KindCodeData2 KindCodeData2 = new KindCodeData2();
				KindCodeData2.setCarId(cursor.getString(cursor.getColumnIndex("car_id")));
				KindCodeData2.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				KindCodeData2.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				KindCodeData2List.add(KindCodeData2);
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
		return KindCodeData2List;
	}

	
	public boolean update(KindCodeData2 KindCodeData2) throws Exception {
		boolean result = false;
		try{
			if(null != KindCodeData2){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues assistValues = new ContentValues();
				ContentValues kindValues = new ContentValues();
	//			kindValues.put("car_id", KindCodeData2.getCarId());
				kindValues.put("insure_term", KindCodeData2.getInsureTerm());
				kindValues.put("insure_term_code", KindCodeData2.getInsureTermCode());
				db.update("survey_kind_code_data", assistValues, "car_id=?", new String[]{KindCodeData2.getCarId()});
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

	
	public int save(KindCodeData2 KindCodeData2) throws Exception {
		int result = 0;
		try{
			if(null != KindCodeData2){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues kindValues = new ContentValues();
				KindCodeData2.init();
				kindValues.put("car_id", KindCodeData2.getCarId());
				kindValues.put("insure_term", KindCodeData2.getInsureTerm());
				kindValues.put("insure_term_code", KindCodeData2.getInsureTermCode());
				result = (int)db.insert("survey_kind_code_data", null, kindValues);
				Log.e("survey_kind_code_data", "insert into"+result);
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
