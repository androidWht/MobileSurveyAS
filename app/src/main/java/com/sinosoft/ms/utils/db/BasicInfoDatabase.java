package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.BaiscInfoBean;
import com.sinosoft.ms.model.TaskBean;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:18:33
 */

public class BasicInfoDatabase {

	private SQLiteDatabase database;
	private Context context;

	public BasicInfoDatabase(Context context) {
		this.context = context;
	}

	// 插入数据
	public void insertBasicInfo(final String table, BaiscInfoBean bean)
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
				values.put("reportNo", bean.getReportNo());
				values.put("whether_subrogation", bean.getWhether_subrogation());
				values.put("claim_form", bean.getClaim_form());
				values.put("accident_handle_type",
						bean.getAccident_handle_type());
				values.put("case_nature", bean.getCase_nature());
				values.put("claim_case_category", bean.getClaim_case_category());
				values.put("loss_type", bean.getLoss_type());
				values.put("accident_cause", bean.getAccident_cause());
				values.put("accident_type", bean.getAccident_type());
				values.put("survey_type", bean.getSurvey_type());
				values.put("survey_addr", bean.getSurvey_addr());
				values.put("survey_time", bean.getSurvey_time());
				values.put("contact_person", bean.getContact_person());
				values.put("contact_phone", bean.getContact_phone());
				values.put("touch_for", bean.getTouch_for());
				values.put("survey_of_1", bean.getSurvey_of_1());
				values.put("survey_of_2", bean.getSurvey_of_2());

				values.put("first_site_survey", bean.getFirst_site_survey());
				values.put("danger_place", bean.getDanger_place());
				values.put("accident_processing_department",
						bean.getAccident_processing_department());
				values.put("mapping_class", bean.getMapping_class());

				values.put("quick_office_for", bean.getQuick_office_for());
				values.put("double_free", bean.getDouble_free());
				values.put("survey_feedback", bean.getSurvey_feedback());

				database.insert(table, null, values);
			}
			database.setTransactionSuccessful();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			database.endTransaction();
			database.close();

		}

	}

	public void deleteBasicInfo(String table, String reportNo) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("basicInfo delete", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("basicInfo delete", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("basicInfo delete", "begin");

			}
		});

		try {
			database.delete(table, "reportNo=?", new String[] { reportNo });
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("baiscInfo detele error", "reason" + e.getMessage());

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
	public List<TaskBean> selectTaskList(String table, String where,
			String orderBy) throws Exception {
		List<TaskBean> list = null;
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			list = new ArrayList<TaskBean>();
			cursor = database.query(table, null, where, null, null, null,
					orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				TaskBean bean = new TaskBean();
				bean.setReportNO(cursor.getString(cursor
						.getColumnIndex("reportNo")));
				bean.setTaskType(cursor.getString(cursor
						.getColumnIndex("taskType")));
				bean.setAccidentLocation(cursor.getString(cursor
						.getColumnIndex("insuranceAddr")));

				bean.setReporter(cursor.getString(cursor
						.getColumnIndex("reportPeople")));

				bean.setStatus(cursor.getString(cursor.getColumnIndex("status")));

				bean.setAccidentReason(cursor.getString(cursor
						.getColumnIndex("insuranceReason")));
				bean.setAccidentDate(cursor.getString(cursor
						.getColumnIndex("insuranceTime")));
				bean.setContactPhone(cursor.getString(cursor
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
