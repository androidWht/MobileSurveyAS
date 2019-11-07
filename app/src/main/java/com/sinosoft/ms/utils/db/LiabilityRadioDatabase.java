package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：涉案车辆著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:18:33
 */

public class LiabilityRadioDatabase {

	private SQLiteDatabase database;
	private Context context;

	// liablity_data

	public LiabilityRadioDatabase(Context context) {
		this.context = context;
	}
	
   public void deleteLiablity(String table,String registNo) throws Exception{
	   database = DatabaseHelper.getHelper(context).openDatabase();
	   database.delete(table, "registNo=?", new String[]{registNo});
	   database.close();
	   
   }
  
   
	// 插入数据
	public void insertLiablity(final String table,
			List<LiabilityRatio> liabilityRatio) throws Exception {

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
			int size=liabilityRatio.size();
			for (int i = 0; i < size; i++) {
				LiabilityRatio bean = liabilityRatio.get(i);
				if (bean != null) {
					ContentValues values = new ContentValues();
					values.putNull("id");
					values.put("registNo", bean.getRegistNo() + "");
					values.put("flag", bean.getFlag());
					values.put("riskCode", bean.getRiskCode());
					values.put("policyFlag", bean.getPolicyFlag());
					values.put("policyNo", bean.getPolicyNo());
					values.put("claimFlag", bean.getClaimFlag());
					values.put("indemnityDuty", bean.getIndemnityDuty());
					values.put("indemnityDutyRate", bean.getIndemnityDutyRate());
					values.put("cIIndemDuty", bean.getcIIndemDuty());
					values.put("cIDutyFlag", bean.getcIDutyFlag());
           
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
	// 
		public void updateLiablity(final String table,String registNo,
				List<LiabilityRatio> liabilityRatio) throws Exception {

			try {
				database = DatabaseHelper.getHelper(context).openDatabase();

				database.beginTransactionWithListener(new SQLiteTransactionListener() {
					@Override
					public void onRollback() {
						Log.d("LiabilityRatio update", "rollback");
					}
					@Override
					public void onCommit() {
						Log.d("LiabilityRatio update", "commit");
					}
					@Override
					public void onBegin() {
						Log.d("LiabilityRatio update", "begin");
					}
				});
				for (int i = 0; i < liabilityRatio.size(); i++) {
					LiabilityRatio bean = liabilityRatio.get(i);
					if (bean != null) {
						ContentValues values = new ContentValues();
						values.put("registNo", bean.getRegistNo() + "");
						values.put("flag", bean.getFlag());
						values.put("riskCode", bean.getRiskCode());
						values.put("policyFlag", bean.getPolicyFlag());
						values.put("policyNo", bean.getPolicyNo());
						values.put("claimFlag", bean.getClaimFlag());
						values.put("indemnityDuty", bean.getIndemnityDuty());
						values.put("indemnityDutyRate", bean.getIndemnityDutyRate());
						values.put("cIIndemDuty", bean.getcIIndemDuty());
						values.put("cIDutyFlag", bean.getcIDutyFlag());
						String id=bean.getId();
	                    if(StringUtils.isEmpty(id)){
	                    	database.insert(table, null, values);
	                    }else{
	                    	database.update(table, values, "registNo=? and id=?", new String[]{registNo,id});
	                    }
						

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
	public List<LiabilityRatio> selectLiablity(final String table,
			String registNo) throws Exception {
		List<LiabilityRatio> liabilityRatios = new ArrayList<LiabilityRatio>();
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();

			database.beginTransactionWithListener(new SQLiteTransactionListener() {

				@Override
				public void onRollback() {
					// TODO Auto-generated method stub
					Log.d("liabilityRatios select", "rollback");
				}

				@Override
				public void onCommit() {
					// TODO Auto-generated method stub
					Log.d("liabilityRatios select", "commit");
				}

				@Override
				public void onBegin() {
					// TODO Auto-generated method stub
					Log.d("liabilityRatios select", "begin");
				}
			});

			cursor = database.query(table, null, "registNo=?",
					new String[] { registNo }, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				LiabilityRatio bean = new LiabilityRatio();
				int id=cursor.getInt(cursor.getColumnIndex("id"));
				bean.setId(id+"");
				bean.setFlag(cursor.getString(cursor
						.getColumnIndex("flag")));
				bean.setRegistNo(cursor.getString(cursor
						.getColumnIndex("registNo")));
				bean.setRiskCode(cursor.getString(cursor
						.getColumnIndex("riskCode")));
				bean.setPolicyFlag(cursor.getString(cursor
						.getColumnIndex("policyFlag")));
				bean.setPolicyNo(cursor.getString(cursor
						.getColumnIndex("policyNo")));
				bean.setClaimFlag(cursor.getString(cursor
						.getColumnIndex("claimFlag")));
				bean.setIndemnityDuty(cursor.getString(cursor
						.getColumnIndex("indemnityDuty")));
				bean.setIndemnityDutyRate(cursor.getString(cursor
						.getColumnIndex("indemnityDutyRate")));
				bean.setcIIndemDuty(cursor.getString(cursor
						.getColumnIndex("cIIndemDuty")));
				bean.setcIDutyFlag(cursor.getString(cursor
						.getColumnIndex("cIDutyFlag")));
				liabilityRatios.add(bean);
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
		return liabilityRatios;
	}

}
