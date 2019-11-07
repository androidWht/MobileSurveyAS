package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.RegistThirdCarData;

/**
 * 系统名：移动查勘定损 子系统名：涉案车辆著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:18:33
 */

public class RegistThirdCarDatabase {

	private SQLiteDatabase database;
	private Context context;

	// liablity_data

	public RegistThirdCarDatabase(Context context) {
		this.context = context;
	}
	
   public void deleteRegistThirdCar(String table,String registNo) throws Exception{
	   database = DatabaseHelper.getHelper(context).openDatabase();
	   database.delete(table, "registNo=?", new String[]{registNo});
	   database.close();
	   
   }
  
   
	// 插入数据
	public void insertRegistThirdCar(final String table,
			List<RegistThirdCarData> registThirdCarDatas) throws Exception {

		try {
			database = DatabaseHelper.getHelper(context).openDatabase();

			database.beginTransactionWithListener(new SQLiteTransactionListener() {

				@Override
				public void onRollback() {
					// TODO Auto-generated method stub
					Log.d("LiabilityRatio insert", "rollback");
				}

				@Override
				public void onCommit() {
					// TODO Auto-generated method stub
					Log.d("LiabilityRatio insert", "commit");
				}

				@Override
				public void onBegin() {
					// TODO Auto-generated method stub
					Log.d("LiabilityRatio insert", "begin");
				}
			});

			for (int i = 0; i < registThirdCarDatas.size(); i++) {
				RegistThirdCarData bean = registThirdCarDatas.get(i);
				if (bean != null) {
					ContentValues values = new ContentValues();
					values.putNull("id");
					values.put("registNo", bean.getRegistNo() + "");
					values.put("licenseNo", bean.getLicenseNo());
					values.put("company", bean.getCompany());
					values.put("brandName", bean.getBrandName());
					values.put("thirdPolicyNo", bean.getThirdPolicyNo());
				
					database.insert(table, null, values);

				}
			}
			database.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			database.endTransaction();
			database.close();
		}
	}
	// 插入数据
		public void updateRegistThirdCar(final String table,String registNo,
				List<RegistThirdCarData> registThirdCarDatas) throws Exception {

			try {
				database = DatabaseHelper.getHelper(context).openDatabase();

				database.beginTransactionWithListener(new SQLiteTransactionListener() {

					@Override
					public void onRollback() {
						// TODO Auto-generated method stub
						Log.d("LiabilityRatio insert", "rollback");
					}

					@Override
					public void onCommit() {
						// TODO Auto-generated method stub
						Log.d("LiabilityRatio insert", "commit");
					}

					@Override
					public void onBegin() {
						// TODO Auto-generated method stub
						Log.d("LiabilityRatio insert", "begin");
					}
				});
				 database.delete(table, "registNo=?", new String[]{registNo});
				 
				for (int i = 0; i < registThirdCarDatas.size(); i++) {
					RegistThirdCarData bean = registThirdCarDatas.get(i);
					if (bean != null) {
						ContentValues values = new ContentValues();
						
						values.put("registNo", registNo);
						values.put("licenseNo", bean.getLicenseNo());
						values.put("company", bean.getCompany());
						values.put("brandName", bean.getBrandName());
						values.put("thirdPolicyNo", bean.getThirdPolicyNo());
					
						database.update(table, values, "registNo=?", new String[]{registNo});

					}
				}
				database.setTransactionSuccessful();

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			} finally {
				database.endTransaction();
				database.close();
			}
		}
	
	
	
	
	//数据
	public List<RegistThirdCarData> selectRegistThirdCar(final String table,
			String registNo) throws Exception {
		List<RegistThirdCarData> registThirdCarDatas = new ArrayList<RegistThirdCarData>();
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();

			database.beginTransactionWithListener(new SQLiteTransactionListener() {

				@Override
				public void onRollback() {
					// TODO Auto-generated method stub
					Log.d("RegistThirdCarData select", "rollback");
				}

				@Override
				public void onCommit() {
					// TODO Auto-generated method stub
					Log.d("RegistThirdCarData select", "commit");
				}

				@Override
				public void onBegin() {
					// TODO Auto-generated method stub
					Log.d("RegistThirdCarData select", "begin");
				}
			});

			cursor = database.query(table, null, "registNo=?",
					new String[] { registNo }, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				RegistThirdCarData bean = new RegistThirdCarData();
				bean.setLicenseNo(cursor.getString(cursor
						.getColumnIndex("licenseNo")));
				bean.setRegistNo(cursor.getString(cursor
						.getColumnIndex("registNo")));
				bean.setCompany(cursor.getString(cursor
						.getColumnIndex("company")));
				bean.setBrandName(cursor.getString(cursor
						.getColumnIndex("brandName")));
				bean.setThirdPolicyNo(cursor.getString(cursor
						.getColumnIndex("thirdPolicyNo")));
				registThirdCarDatas.add(bean);
				cursor.moveToNext();

			}

			database.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			cursor.close();
			database.endTransaction();
			database.close();
		}
		return registThirdCarDatas;
	}

}
