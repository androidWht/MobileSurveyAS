package com.sinosoft.ms.service.impl;

import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.InsuredData;
import com.sinosoft.ms.service.IInsuredDataService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.db.DatabaseHelper;

/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Hijack
 * @createTime 下午4:28:10
 */

public class InsuredDataService implements IInsuredDataService{
	private Activity activity = null;
	private SQLiteDatabase db = null;
	
	public InsuredDataService() {
		if(null==activity){
			activity =ActivityManage.getInstance().peek();
		}
	}

	@Override
	public InsuredData getByRegistNo(String registNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public InsuredData getByTaskId(String taskId) throws Exception {
		// TODO Auto-generated method stub
		InsuredData insuredData = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from insured_data where task_id='"+taskId+"'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				insuredData = new InsuredData();
				insuredData.setId(cursor.getLong(cursor.getColumnIndex("id")));
				insuredData.setRegistNo(cursor.getString(cursor.getColumnIndex("regist_no")));
				insuredData.setTaskId(cursor.getString(cursor.getColumnIndex("task_id")));
				insuredData.setInsuredName(cursor.getString(cursor.getColumnIndex("insured_name")));
				insuredData.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phone_number")));
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
		return insuredData;
	}


	@Override
	public boolean update(InsuredData insuredData) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int save(InsuredData insuredData) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			if(null != insuredData){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues newValues = new ContentValues();
				insuredData.init();
				newValues.put("regist_no", insuredData.getRegistNo());
				newValues.put("task_id", insuredData.getTaskId());
				newValues.put("insured_name", insuredData.getInsuredName());
				newValues.put("phone_number", insuredData.getPhoneNumber());
				result = (int)db.insert("insured_data", null, newValues);
				db.close();
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
	public void updateInsureData(InsuredData insuredData) {
		// TODO Auto-generated method stub
		
	}

}

