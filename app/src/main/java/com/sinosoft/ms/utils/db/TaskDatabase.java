package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：任务中心数据操作 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午10:24:54
 */

public class TaskDatabase {
	private SQLiteDatabase database;
	private Context context;

	public TaskDatabase(Context context) {
		this.context = context;
	}

	// 插入数据

	/**
	 * 插入数据
	 * 
	 * @param table
	 * @param list
	 */

	public void insertIntoTaskList(final String table, final List<RegistData> list) {

		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("task insert", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("task insert", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("task insert", "begin");

			}
		});

		try {
			for (int i = 0; i < list.size(); i++) {
				RegistData bean = list.get(i);

				ContentValues values = new ContentValues();
				values.putNull("id");
				values.put("reportNo", bean.getRegistNo());
				values.put("taskType", bean.getTaskType());
				values.put("reportPeople", bean.getReportorName());
				values.put("insuranceAddr", bean.getDamageAddress());
				values.put("status", bean.getStatus()+"");
				values.put("insuranceReason", bean.getDamageName());
				values.put("insuranceTime", bean.getDamageDate());
				values.put("phone", bean.getReportorNumber());
				values.put("reportTime", bean.getReportDate());
				database.insert(table, null, values);
			}
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("task insert error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}

	}

	/**
	 * 删除数据
	 * 
	 * @param table
	 * @param status
	 */
	public void deleteTaskList(String table, String status) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("task delete", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("task delete", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("task delete", "begin");

			}
		});

		try {
			database.delete(table, null,null);
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("task detele error", "reason" + e.getMessage());

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
	public List<RegistData> selectTaskList(String table, String where,
			String orderBy) throws Exception {
		List<RegistData> list = null;
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			list = new ArrayList<RegistData>();
			cursor = database.query(table, null, where, null, null, null,
					orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				RegistData bean = new RegistData();
				bean.setRegistNo(cursor.getString(cursor
						.getColumnIndex("reportNo")));
				//(cursor.getString(cursor.getColumnIndex("taskType"))
				bean.setTaskType(StringUtils.toLong(cursor.getString(cursor.getColumnIndex("taskType"))));
				bean.setDamageAddress(cursor.getString(cursor
						.getColumnIndex("insuranceAddr")));

				bean.setReportorName(cursor.getString(cursor
						.getColumnIndex("reportPeople")));

				bean.setStatus(StringUtils.toLong(cursor.getString(cursor.getColumnIndex("status"))));

				bean.setDamageName(cursor.getString(cursor
						.getColumnIndex("insuranceReason")));
				bean.setDamageDate(cursor.getString(cursor
						.getColumnIndex("insuranceTime")));
				bean.setReportorNumber(cursor.getString(cursor
						.getColumnIndex("phone")));
				bean.setReportDate(cursor.getString(cursor
						.getColumnIndex("reportTime")));
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

}
