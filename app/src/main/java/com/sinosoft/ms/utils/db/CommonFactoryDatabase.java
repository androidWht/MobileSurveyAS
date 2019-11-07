package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.CommonFactory;
import com.sinosoft.ms.model.TaskBean;

/**
 * 系统名：移动查勘定损 
 * 子系统名：常用修理厂
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Hijack
 * @createTime 下午8:00:40
 */

public class CommonFactoryDatabase {
	private SQLiteDatabase database;
	private Context context;

	public CommonFactoryDatabase(Context context) {
		this.context = context;
	}

	// 插入数据
	public void insertCommonFactory(CommonFactory bean)
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
				Log.d("commonRepairFactory insert", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("commonRepairFactory insert", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("commonRepairFactory insert", "begin");

			}
		});

		try {

			if (bean != null) {
				ContentValues values = new ContentValues();
				values.putNull("id");
				values.put("repairFactoryCode", bean.getRepairFactoryCode());
				values.put("repairFactoryName", bean.getRepairFactoryName());

				database.insert("commonRepairFactory", null, values);
			}
			database.setTransactionSuccessful();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			database.endTransaction();
			database.close();

		}

	}
	
	// 插入数据
	public void updateCommonFactory(CommonFactory bean)
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
				Log.d("commonRepairFactory update", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("commonRepairFactory update", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("commonRepairFactory update", "begin");

			}
		});

		try {

			if (bean != null) {
				ContentValues values = new ContentValues();
				values.put("repairFactoryCode", bean.getRepairFactoryCode());
				values.put("repairFactoryName", bean.getRepairFactoryName());

				database.update("commonRepairFactory", values, "repairFactoryCode = ?", new String[]{bean.getRepairFactoryCode()});
			}
			database.setTransactionSuccessful();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			database.endTransaction();
			database.close();

		}

	}

	public void deleteBasicInfo(String repairFactoryCode) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("commonRepairFactory delete", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("commonRepairFactory delete", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("commonRepairFactory delete", "begin");

			}
		});

		try {
			database.delete("commonRepairFactory", "repairFactoryCode=?", new String[] { repairFactoryCode });
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("commonRepairFactory detele error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}
	}

	/**
	 * 查询任务中心的数据
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
	public List<CommonFactory> selectCommonFactoryList() throws Exception {
		List<CommonFactory> list = null;
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			list = new ArrayList<CommonFactory>();
			cursor = database.query("commonRepairFactory", null, null, null, null, null,
					null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				CommonFactory bean = new CommonFactory();
				bean.setRepairFactoryCode(cursor.getString(cursor
						.getColumnIndex("repairFactoryCode")));
				bean.setRepairFactoryName(cursor.getString(cursor
						.getColumnIndex("repairFactoryName")));
				cursor.moveToNext();
				list.add(bean);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception("数据库操作异常");
		} finally {
			cursor.close();
			database.close();

		}

		return list;
	}
	
	public CommonFactory getCommonFactory(String repairFactoryCode) throws Exception {
		CommonFactory bean = null;
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			cursor = database.query("commonRepairFactory", null,"repairFactoryCode=?",new String[]{repairFactoryCode},null,null,null);
			if(cursor.moveToNext()){
				bean = new CommonFactory();
				bean.setRepairFactoryCode(cursor.getString(cursor
						.getColumnIndex("repairFactoryCode")));
				bean.setRepairFactoryName(cursor.getString(cursor
						.getColumnIndex("repairFactoryName")));
			}
//			cursor.moveToFirst();
//			while (!cursor.isAfterLast()) {
//
//			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception("数据库操作异常");
		} finally {
			cursor.close();
			database.close();

		}

		return bean;
	}

}

