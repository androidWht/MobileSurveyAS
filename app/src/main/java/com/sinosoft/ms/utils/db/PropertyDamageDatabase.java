package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.DamageBean;
import com.sinosoft.ms.model.PropertyDamageBean;

/**
 * 系统名：移动查勘定损 子系统名：财产损失数据库操作 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:18:33
 */

public class PropertyDamageDatabase {

	private SQLiteDatabase database;
	private Context context;

	public PropertyDamageDatabase(Context context) {
		this.context = context;
	}

	/**
	 * 财产损失（插入数据）（“propertyDamage” 和列表信息“damage”）
	 * 
	 * @param table
	 *            表名
	 * @param bean
	 *            财产损失bean数据
	 * @throws Exception
	 */
	public void insertPropertyDamage(final String table, PropertyDamageBean bean)
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

				List<DamageBean> list = bean.getDamageList();
				ContentValues info = new ContentValues();
				info.putNull("id");
				info.put("reportNo", bean.getReportNo());
				info.put("losingParty", bean.getLosingParty());
				info.put("contactPersonDamage", bean.getContactPersonDamage());
				info.put("contactMode", bean.getContactMode());
				info.put("estimatedLossAmountDamage",
						bean.getEstimatedLossAmountDamage());
				info.put("claimCaseCategory", bean.getClaimCaseCategory());
				info.put("surveyAddr", bean.getSurveyAddr());
				info.put("rescueProcess", bean.getRescueProcess());
				info.put("remarksExt", bean.getRemarksExt());
				info.put("surveyDate", bean.getSurveyDate());

				long rowId = database.insert(table, null, info);
				if (rowId == -1) {
					throw new IllegalArgumentException("财产损失数据插入错误");
				}

				for (int i = 0; i < list.size(); i++) {

					DamageBean damageBean = list.get(i);

					if (damageBean != null) {
						ContentValues values = new ContentValues();
						values.putNull("id");
						values.put("parentId", rowId);
						values.put("lossPropertyName",
								damageBean.getLossPropertyName());
						values.put("lossAmount", damageBean.getLossAmount());
						values.put("lossExtent", damageBean.getLossExtent());
						values.put("lossTypeDamage",
								damageBean.getLossTypeDamage());
						long id = database.insert("damage", null, values);
						if (id == -1) {
							throw new IllegalArgumentException("财产损失数据插入错误");
						}
					}
				}
			}
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("basicInfo insert error", "reason" + e.getMessage());
			throw new Exception(e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}

	}

	/**
	 * 删除财产损失 （“propertyDamage” 和列表信息“damage”）
	 * 
	 * @param table
	 *            表名
	 * @param reportNo
	 *            报案号
	 */
	public void deleteInvolvedInfo(String table, String reportNo) {
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

			Cursor cursor = database.query(table, null, "reportNo=?",
					new String[] { reportNo }, null, null, null);
			cursor.moveToFirst();
			int id = -1;
			while (!cursor.isAfterLast()) {
				id = cursor.getInt(cursor.getColumnIndex("id"));
				break;
			}
			cursor.close();
			if (id != -1) {
				database.delete(table, "reportNo=?", new String[] { reportNo });
				database.delete("damage", "parentId=?",
						new String[] { id + "" });
			}

			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("baiscInfo detele error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}
	}

	/**
	 * 查询财产损失的数据
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
	public PropertyDamageBean selectInvolveList(String table, String where,
			String orderBy) throws Exception {
		PropertyDamageBean bean = null;
		int id = -1;
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();

			cursor = database.query(table, null, where, null, null, null,
					orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {

				bean = new PropertyDamageBean();
				id = cursor.getInt(cursor.getColumnIndex("id"));
				bean.setReportNo(cursor.getString(cursor
						.getColumnIndex("reportNo")));
				bean.setLosingParty(cursor.getString(cursor
						.getColumnIndex("losingParty")));
				bean.setContactPersonDamage(cursor.getColumnName(cursor
						.getColumnIndex("contactPersonDamage")));
				bean.setContactMode(cursor.getColumnName(cursor
						.getColumnIndex("contactMode")));
				bean.setEstimatedLossAmountDamage(cursor.getColumnName(cursor
						.getColumnIndex("estimatedLossAmountDamage")));
				bean.setClaimCaseCategory(cursor.getColumnName(cursor
						.getColumnIndex("claimCaseCategory")));
				bean.setSurveyAddr(cursor.getColumnName(cursor
						.getColumnIndex("surveyAddr")));
				bean.setRescueProcess(cursor.getColumnName(cursor
						.getColumnIndex("rescueProcess")));

			}
			if (cursor != null) {
				cursor.close();

			}
			List<DamageBean> list = new ArrayList<DamageBean>();
			if (bean != null) {
				Cursor c = database.query("", null, "parentId=" + id, null,
						null, null, orderBy);
				c.moveToFirst();
				while (c.isAfterLast()) {
					DamageBean b = new DamageBean();

					b.setLossTypeDamage(c.getColumnName(c
							.getColumnIndex("lossPropertyName")));
					b.setLossAmount(c.getColumnName(c
							.getColumnIndex("lossAmount")));
					b.setLossPropertyName(c.getColumnName(c
							.getColumnIndex("lossPropertyName")));
					b.setLossExtent(c.getColumnName(c
							.getColumnIndex("lossPropertyName")));
					list.add(b);
					c.moveToNext();

				}
				bean.setDamageList(list);
				if (c != null) {
					c.close();

				}

			}

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
