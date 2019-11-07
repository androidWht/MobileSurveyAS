package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.PropDetailData;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：财产损失 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:18:33
 */

public class PropDataDatabase {

	private SQLiteDatabase database;
	private Context context;
	private final String prop_detail_data = "prop_detail_data";

	public PropDataDatabase(Context context) {
		this.context = context;
	}

	// 插入数据
	public void insertPropData(final String table, List<PropData> propDatas)
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
				Log.d("PropData insert", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("PropData insert", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("PropData insert", "begin");
			}
		});

		try {
			for (PropData bean : propDatas) {
				if (bean != null) {
					ContentValues values = new ContentValues();
					values.putNull("id");
					values.put("propId",bean.getPropId());
					values.put("checkPropId",bean.getCheckPropId());
					values.put("registNo", bean.getRegistNo());
					values.put("lossParty", bean.getLossParty());
					values.put("relatePersonName", bean.getRelatePersonName());
					values.put("relatePhoneNum", bean.getRelatePhoneNum());
					values.put("sumLossFee", bean.getSumLossFee());
					values.put("rescueFee", bean.getRescueFee());
					values.put("checkDate", bean.getCheckDate());
					values.put("reserveFlag", bean.getReserveFlag());
					values.put("checkSite", bean.getCheckSite());
					values.put("rescueInfo", bean.getRescueInfo());
					values.put("remark", bean.getRemark());

					long id = database.insert(table, null, values);
					List<PropDetailData> propDetailDatas = bean
							.getPropDetailDatas();
					if (propDetailDatas != null && !propDetailDatas.isEmpty()) {
						insertPropDetailDatas(prop_detail_data, id + "",
								propDetailDatas);// 保存财产详情
					}
				}
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
	public List<PropData> selectPropDatas(final String table, String registNo) {
		List<PropData> propDatas = new ArrayList<PropData>();
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();

			database.beginTransactionWithListener(new SQLiteTransactionListener() {

				@Override
				public void onRollback() {
					// TODO Auto-generated method stub
					Log.d("PropData select", "rollback");
				}

				@Override
				public void onCommit() {
					// TODO Auto-generated method stub
					Log.d("PropData select", "commit");
				}

				@Override
				public void onBegin() {
					// TODO Auto-generated method stub
					Log.d("PropData select", "begin");
				}
			});

			cursor = database.query(table, null, "registNo=?",
					new String[] { registNo }, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				PropData bean = new PropData();
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				///bean.setPropId(id + "");
				bean.setId(id + "");
				
				bean.setPropId(cursor.getString(cursor
						.getColumnIndex("propId")));
				bean.setCheckPropId(cursor.getString(cursor
						.getColumnIndex("checkPropId")));				

				bean.setRegistNo(cursor.getString(cursor
						.getColumnIndex("registNo")));
				bean.setLossParty(cursor.getString(cursor
						.getColumnIndex("lossParty")));
				bean.setRelatePersonName(cursor.getString(cursor
						.getColumnIndex("relatePersonName")));
				bean.setRelatePhoneNum(cursor.getString(cursor
						.getColumnIndex("relatePhoneNum")));
				bean.setSumLossFee(cursor.getString(cursor
						.getColumnIndex("sumLossFee")));
				bean.setRescueFee(cursor.getString(cursor
						.getColumnIndex("rescueFee")));
				bean.setCheckDate(cursor.getString(cursor
						.getColumnIndex("checkDate")));
				bean.setReserveFlag(cursor.getString(cursor
						.getColumnIndex("reserveFlag")));
				bean.setCheckSite(cursor.getString(cursor
						.getColumnIndex("checkSite")));
				bean.setRescueInfo(cursor.getString(cursor
						.getColumnIndex("rescueInfo")));
				bean.setRemark(cursor.getString(cursor.getColumnIndex("remark")));

				List<PropDetailData> propDetailDatas = selectPropDetailDatas(
						prop_detail_data, id + "", null);
				bean.setPropDetailDatas(propDetailDatas);

				propDatas.add(bean);
				cursor.moveToNext();

			}

			database.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			database.endTransaction();
			database.close();
		}
		return propDatas;
	}

	public void deletePropData(String table, String registNo) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {
			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("carData delete", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("carData delete", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("carData delete", "begin");

			}
		});

		try {

			database.execSQL("delete from prop_detail_data where propid=(select id from "
					+ table + " where registNo='" + registNo + "')");
			database.execSQL("delete from " + table + " where registNo='"
					+ registNo + "'");
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d(table + "detele error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}
	}

	public void deletePropDataById(String id, String registNo) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {
			@Override
			public void onRollback() {
				Log.d("deletePropDataById delete", "rollback");
			}

			@Override
			public void onCommit() {
				Log.d("deletePropDataById delete", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("carData delete", "begin");

			}
		});

		try {

			database.execSQL("delete from prop_detail_data where propid=(select id from "
					+ "prop_data"
					+ " where registNo='"
					+ registNo
					+ "' and id=" + id + ")");
			database.execSQL("delete from " + "prop_data" + " where registNo='"
					+ registNo + "' and id=" + id);
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("prop_data" + "detele error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}
	}

	public void updatePropData(String table, String registNo,
			List<PropData> list) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				Log.d("PropData update", "rollback");
			}
			@Override
			public void onCommit() {
				Log.d("PropData update", "commit");
			}
			@Override
			public void onBegin() {
				Log.d("PropData update", "begin");
			}
		});
		try {
			for (int i = 0; i < list.size(); i++) {
				PropData bean = list.get(i);
				bean.init();
				if (bean != null) {
					ContentValues values = new ContentValues();
					values.put("propId",bean.getPropId());
					values.put("checkPropId",bean.getCheckPropId());
					values.put("registNo", bean.getRegistNo());
					values.put("lossParty", bean.getLossParty());
					values.put("relatePersonName", bean.getRelatePersonName());
					values.put("relatePhoneNum", bean.getRelatePhoneNum());
					values.put("sumLossFee", bean.getSumLossFee());
					values.put("rescueFee", bean.getRescueFee());
					values.put("checkDate", bean.getCheckDate());
					values.put("reserveFlag", bean.getReserveFlag());
					values.put("checkSite", bean.getCheckSite());
					values.put("rescueInfo", bean.getRescueInfo());
					values.put("remark", bean.getRemark());
					String propId = bean.getId();
					if (StringUtils.isNotEmpty(propId)) {
						database.update(table, values, "registNo=? and id=?",
								new String[] { registNo, propId });
						List<PropDetailData> propDetailDatas = bean
								.getPropDetailDatas();
						if (propDetailDatas != null
								&& !propDetailDatas.isEmpty()) {
							updatePropDetailDatas(prop_detail_data, propId,
									propDetailDatas);// 保存财产详情
						}
					} else {
						values.putNull("id");
						long id = database.insert(table, null, values);
						bean.setId(id + "");
						List<PropDetailData> propDetailDatas = bean
								.getPropDetailDatas();
						if (propDetailDatas != null
								&& !propDetailDatas.isEmpty()) {
							for (int k = 0; k < propDetailDatas.size(); k++) {

								ContentValues contentValues = new ContentValues();
								PropDetailData propDetailData = propDetailDatas
										.get(k);
								propDetailData.init();
								contentValues.putNull("id");
								contentValues.put("propId", id);
								contentValues.put("lossItemName",
										propDetailData.getLossItemName());
								contentValues.put("lossFee",
										propDetailData.getLossFee());
								contentValues.put("lossDegreeCode",
										propDetailData.getLossDegreeCode());
								contentValues.put("lossSpeciesCode",
										propDetailData.getLossSpeciesCode());

								long detailId = database.insert(
										prop_detail_data, null, contentValues);
								if (detailId != -1) {
									propDetailData.setId(detailId + "");
									propDetailData.setPropId(propId);
								}

							}

						}
					}
				}
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			Log.d("PropData update error", "reason" + e.getMessage());
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 查询财产详情
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
	public List<PropDetailData> selectPropDetailDatas(String table,
			String propId, String orderBy) throws Exception {
		List<PropDetailData> propDetailDatas = new ArrayList<PropDetailData>();
		Cursor cursor = null;

		cursor = database.query(table, null, "propId=?",
				new String[] { propId }, null, null, orderBy);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PropDetailData bean = new PropDetailData();
			bean.setId(cursor.getInt(cursor.getColumnIndex("id")) + "");
			bean.setPropId(cursor.getString(cursor.getColumnIndex("propId")));
			bean.setLossItemName(cursor.getString(cursor
					.getColumnIndex("lossItemName")));
			bean.setLossFee(cursor.getString(cursor.getColumnIndex("lossFee")));
			bean.setLossDegreeCode(cursor.getString(cursor
					.getColumnIndex("lossDegreeCode")));
			bean.setLossSpeciesCode(cursor.getString(cursor
					.getColumnIndex("lossSpeciesCode")));
			
			
		    String lossItemName=bean.getLossItemName();//损失财产名称	
			String lossFee=bean.getLossFee();//损失金额	
			String lossDegreeCode=bean.getLossDegreeCode();///损失程度	代码
			String lossSpeciesCode=bean.getLossSpeciesCode();//损失种类	代码
			if(StringUtils.isNotEmpty(lossItemName)||StringUtils.isNotEmpty(lossFee)
					||StringUtils.isNotEmpty(lossDegreeCode)||StringUtils.isNotEmpty(lossSpeciesCode)){
				propDetailDatas.add(bean);
			} 
			cursor.moveToNext();

		}
		cursor.close();

		return propDetailDatas;
	}

	public void updatePropDetailDatas(String tableName, String propId,
			List<PropDetailData> propDetailDatas) {
		ContentValues values = new ContentValues();

		for (int i = 0; i < propDetailDatas.size(); i++) {
			PropDetailData bean = propDetailDatas.get(i);
			values.put("propId", propId);
			values.put("lossItemName", bean.getLossItemName());
			values.put("lossFee", bean.getLossFee());
			values.put("lossDegreeCode", bean.getLossDegreeCode());
			values.put("lossSpeciesCode", bean.getLossSpeciesCode());
			String id = bean.getId();
			if (StringUtils.isNotEmpty(id)) {
				database.update(tableName, values, "propId=? and id=?",
						new String[] { propId, bean.getId() });
			} else {
				values.putNull("id");
				long ids = database.insert(tableName, null, values);
				if (ids != -1) {
					bean.setId(ids + "");
				}
			}

		}

	}

	public void deleteCarPolicyData(String tableName, String propId) {
		database.delete(tableName, "propId=?", new String[] { propId });
	}

	public void deleteCarPolicyDataById(String id) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			database.delete("prop_detail_data", "id=?", new String[] { id });
		} catch (Exception e1) {

			e1.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}

	}

	public void insertPropDetailDatas(String tableName, String propId,
			List<PropDetailData> propDetailDatas) {
		for (int i = 0; i < propDetailDatas.size(); i++) {

			ContentValues values = new ContentValues();
			PropDetailData bean = propDetailDatas.get(i);
			values.putNull("id");
			values.put("propId", propId);
			values.put("lossItemName", bean.getLossItemName());
			values.put("lossFee", bean.getLossFee());
			values.put("lossDegreeCode", bean.getLossDegreeCode());
			values.put("lossSpeciesCode", bean.getLossSpeciesCode());
			database.insert(tableName, null, values);

		}
	}

	public void inserPropDetailData(String tableName, String propId,
			PropDetailData bean) {
		ContentValues values = new ContentValues();
		values.putNull("id");
		values.put("propId", propId);
		values.put("lossItemName", bean.getLossItemName());
		values.put("lossFee", bean.getLossFee());
		values.put("lossDegreeCode", bean.getLossDegreeCode());
		values.put("lossSpeciesCode", bean.getLossSpeciesCode());
		long id = database.insert(tableName, null, values);
		bean.setId(id + "");
	}
}
