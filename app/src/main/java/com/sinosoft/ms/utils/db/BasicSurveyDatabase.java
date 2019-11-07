package com.sinosoft.ms.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.BasicSurvey;

/**
 * 系统名：移动查勘定损 子系统名 ：查勘基本信息 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:18:33
 */

public class BasicSurveyDatabase {

	private SQLiteDatabase database;
	private Context context;

	public BasicSurveyDatabase(Context context) {
		this.context = context;
		alertCheckerName();
	}

	// 插入数据
	public void insertBasicSurvey(final String table, BasicSurvey bean)
			throws Exception {

		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("basicInfo insert", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("basicInfo insert", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("basicInfo insert", "begin");

			}
		});

		try {

			if (bean != null) {
				ContentValues values = new ContentValues();
				values.putNull("id");
				values.put("registNo",bean.getRegistNo());                
				values.put("task_id",bean.getTask_id()+"");                  
				values.put("subrogationFlag",bean.getSubrogationFlag());  
				values.put("claimSignFlag",bean.getClaimSignFlag());      
				values.put("damageCode",bean.getDamageCode());            
				values.put("checkSite",bean.getCheckSite());              
				values.put("checkDate",bean.getCheckDate());              
				values.put("checker1",bean.getChecker1());    
				values.put("checkerName",bean.getCheckerName()); 
				values.put("caseFlag",bean.getCaseFlag());                
				values.put("damageAddress",bean.getDamageAddress());      
				values.put("intermediaryFlag",bean.getIntermediaryFlag());
				values.put("intermediaryCode",bean.getIntermediaryCode());
				values.put("intermediaryName",bean.getIntermediaryName());
				values.put("manageType",bean.getManageType());            
				values.put("lossType",bean.getLossType());                
				values.put("damageCaseCode",bean.getDamageCaseCode());    
				values.put("firstSiteFlag",bean.getFirstSiteFlag());      
				values.put("olutionUnit",bean.getSolutionUnit());         
				values.put("riskCode",bean.getRiskCode());                
				values.put("opinion",bean.getOpinion());    
				values.put("EnabledSubrPlatform", bean.getEnabledSubrPlatform());
				values.put("IsKindCodeA", bean.getIsKindCodeA());
				values.put("twoAvoidFlag",bean.getTwoAvoidFlag());
				values.put("twoAvoidSelected",bean.getTwoAvoidSelected());

				database.insert(table, null, values);
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
	
	private void alertCheckerName(){
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			String sql = "alter table basic_survey add checkerName varchar2(20)";
			database.execSQL(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} finally {
			database.close();
		}
	}
	
	// 插入数据
		public void updateBasicSurvey(final String table,String registNo,BasicSurvey bean)
				throws Exception {

			try {
				database = DatabaseHelper.getHelper(context).openDatabase();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			database.beginTransactionWithListener(new SQLiteTransactionListener() {

				@Override
				public void onRollback() {
					// TODO Auto-generated method stub
					Log.d("basicInfo insert", "rollback");
				}

				@Override
				public void onCommit() {
					// TODO Auto-generated method stub
					Log.d("basicInfo insert", "commit");
				}

				@Override
				public void onBegin() {
					// TODO Auto-generated method stub
					Log.d("basicInfo insert", "begin");

				}
			});

			try {

				if (bean != null) {
					ContentValues values = new ContentValues();
					
					values.put("registNo",bean.getRegistNo());                
					values.put("task_id",bean.getTask_id()+"");                  
					values.put("subrogationFlag",bean.getSubrogationFlag());  
					values.put("claimSignFlag",bean.getClaimSignFlag());      
					values.put("damageCode",bean.getDamageCode());            
					values.put("checkSite",bean.getCheckSite());              
					values.put("checkDate",bean.getCheckDate());              
					values.put("checker1",bean.getChecker1());     
					values.put("checkerName",bean.getCheckerName());
					values.put("caseFlag",bean.getCaseFlag());                
					values.put("damageAddress",bean.getDamageAddress());      
					values.put("intermediaryFlag",bean.getIntermediaryFlag());
					values.put("intermediaryCode",bean.getIntermediaryCode());
					values.put("intermediaryName",bean.getIntermediaryName());
					values.put("manageType",bean.getManageType());            
					values.put("lossType",bean.getLossType());                
					values.put("damageCaseCode",bean.getDamageCaseCode());    
					values.put("firstSiteFlag",bean.getFirstSiteFlag());      
					values.put("olutionUnit",bean.getSolutionUnit());         
					values.put("riskCode",bean.getRiskCode());                
					values.put("opinion",bean.getOpinion());   
					values.put("EnabledSubrPlatform", bean.getEnabledSubrPlatform());
					values.put("IsKindCodeA", bean.getIsKindCodeA());
					values.put("twoAvoidFlag",bean.getTwoAvoidFlag());
					values.put("twoAvoidSelected",bean.getTwoAvoidSelected());

					database.update(table, values, "registNo=?", new String[]{registNo});
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

//	public void deleteBasicSurvey(String table, String reportNo) {
//		try {
//			database = DatabaseHelper.getHelper(context).openDatabase();
//		} catch (Exception e1) {
//
//			e1.printStackTrace();
//		}
//		database.beginTransactionWithListener(new SQLiteTransactionListener() {
//
//			@Override
//			public void onRollback() {
//				// TODO Auto-generated method stub
//				Log.d("basicInfo delete", "rollback");
//			}
//
//			@Override
//			public void onCommit() {
//				// TODO Auto-generated method stub
//				Log.d("basicInfo delete", "commit");
//			}
//
//			@Override
//			public void onBegin() {
//				// TODO Auto-generated method stub
//				Log.d("basicInfo delete", "begin");
//
//			}
//		});
//
//		try {
//			database.delete(table, "registNo=?", new String[] { reportNo });
//			database.setTransactionSuccessful();
//
//		} catch (Exception e) {
//			Log.d("baiscInfo detele error", "reason" + e.getMessage());
//
//		} finally {
//			database.endTransaction();
//			database.close();
//
//		}
//	}

	/**
	 * 查询查勘处理基本信息
	 * 
	 * @param table
	 *            表名
	 * @param where
	 *            查询过滤条件
	 * @param orderBy
	 *            排序方式
	 * @return 任务中心的数据
	 * @throws Exception
	 */
	public BasicSurvey selectBasicSurvey(String table, String registNo,
			String orderBy)  {
		
		Cursor cursor = null;
		BasicSurvey bean=null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			
			cursor = database.query(table, null, "registNo=?", new String[]{registNo}, null, null,
					orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				bean=new BasicSurvey();
				bean.setRegistNo(cursor.getString(cursor.getColumnIndex("registNo")));                                                                  
				bean.setTask_id(cursor.getString(cursor.getColumnIndex("task_id")));
				bean.setSubrogationFlag(cursor.getString(cursor.getColumnIndex("subrogationFlag")));
				bean.setClaimSignFlag(cursor.getString(cursor.getColumnIndex("claimSignFlag")));
				bean.setDamageCode(cursor.getString(cursor.getColumnIndex("damageCode")));
				bean.setCheckSite(cursor.getString(cursor.getColumnIndex("checkSite")));
				bean.setCheckDate(cursor.getString(cursor.getColumnIndex("checkDate")));
				bean.setChecker1(cursor.getString(cursor.getColumnIndex("checker1")));
				bean.setCheckerName(cursor.getString(cursor.getColumnIndex("checkerName")));
				bean.setCaseFlag(cursor.getString(cursor.getColumnIndex("caseFlag")));
				bean.setDamageAddress(cursor.getString(cursor.getColumnIndex("damageAddress")));
				bean.setIntermediaryFlag(cursor.getString(cursor.getColumnIndex("intermediaryFlag")));
				bean.setIntermediaryCode(cursor.getString(cursor.getColumnIndex("intermediaryCode")));
				bean.setIntermediaryName(cursor.getString(cursor.getColumnIndex("intermediaryName")));
				bean.setManageType(cursor.getString(cursor.getColumnIndex("manageType")));
				bean.setLossType(cursor.getString(cursor.getColumnIndex("lossType")));
				bean.setDamageCaseCode(cursor.getString(cursor.getColumnIndex("damageCaseCode")));
				bean.setFirstSiteFlag(cursor.getString(cursor.getColumnIndex("firstSiteFlag")));
				bean.setSolutionUnit(cursor.getString(cursor.getColumnIndex("olutionUnit")));
				bean.setRiskCode(cursor.getString(cursor.getColumnIndex("riskCode")));
				bean.setOpinion(cursor.getString(cursor.getColumnIndex("opinion")));
				
				bean.setEnabledSubrPlatform(cursor.getString(cursor.getColumnIndex("EnabledSubrPlatform")));
				bean.setIsKindCodeA(cursor.getString(cursor.getColumnIndex("IsKindCodeA")));
				bean.setTwoAvoidFlag(cursor.getString(cursor.getColumnIndex("twoAvoidFlag")));
				bean.setTwoAvoidSelected(cursor.getString(cursor.getColumnIndex("twoAvoidSelected")));
				cursor.moveToNext();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			
		} finally {
			cursor.close();
			database.close();

		}

		return bean;
	}
	
	/**
	 * 保单是否存在
	 * @param table
	 * @param registNo
	 * @param orderBy
	 * @return
	 */
	public boolean isPolicyExist(String table, String policyNo)  {
		
		Cursor cursor = null;
		boolean flag = false ;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			cursor = database.query(table, null, "policyNo=?", new String[]{policyNo}, null, null,
					null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				flag = true ;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			cursor.close();
			database.close();

		}

		return flag ;
	}

}
