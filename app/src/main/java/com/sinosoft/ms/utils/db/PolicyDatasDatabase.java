package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PolicyDetailData;
import com.sinosoft.ms.model.PolicyDriverData;
import com.sinosoft.ms.model.PolicyEngageData;
import com.sinosoft.ms.model.PolicyKindData;

/**
 * 系统名：移动查勘定损 子系统名：涉案车辆著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:18:33
 */

public class PolicyDatasDatabase {

	private SQLiteDatabase database;
	private Context context;
	private String policy_detail_data = "policy_detail_data";
	private String policy_driver_data = "policy_driver_data";
	private String policy_engage_data = "policy_engage_data";
	private String policy_kind_data = "policy_kind_data";

	public PolicyDatasDatabase(Context context) {
		this.context = context;
	}

	// 插入数据
	public void insertPolicyDatas(final String table,
			List<PolicyData> policyDatas) throws Exception {

		database = DatabaseHelper.getHelper(context).openDatabase();

		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				Log.d("PolicyDatas insert", "rollback");
			}

			@Override
			public void onCommit() {
				Log.d("PolicyDatas insert", "commit");
			}

			@Override
			public void onBegin() {
				Log.d("PolicyDatas insert", "begin");
			}
		});

		try {
			for (int i = 0; i < policyDatas.size(); i++) {
				PolicyData bean = policyDatas.get(i);
				if (bean != null) {
					ContentValues values = new ContentValues();
					values.putNull("id");

					values.put("policyNo", bean.getPolicyNo());
					values.put("registNo", bean.getRegistNo());
					values.put("insuredName", bean.getInsuredName());
					values.put("comCName", bean.getComCName());
					values.put("startDate", bean.getStartDate());
					values.put("endDate", bean.getEndDate());
					values.put("riskCode", bean.getRiskCode());

					long id = database.insert(table, null, values);
					try {
						PolicyDetailData policyDetailData = bean
								.getPolicyDetailData();
						policyDetailData.setPolicyNo(bean.getPolicyNo());
						
						List<PolicyDriverData> policyDriverDataList = bean
								.getPolicyDriverDataList();
						List<PolicyEngageData> policyEngageDataList = bean
								.getPolicyEngageDataList();
						List<PolicyKindData> policyKindDataList = bean
								.getPolicyKindDataList();

						insertPolicyDetailData(policy_detail_data, id + "",
								policyDetailData);// 保单详情
						insertpolicyDriverData(policy_driver_data, id + "",
								policyDriverDataList);
						insertPolicyEngageDatas(policy_engage_data, id + "",
								policyEngageDataList);
						inserPolicyKindData(policy_kind_data, id + "",
								policyKindDataList);
					} catch (Exception e) {
						e.printStackTrace();
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
	public List<PolicyData> selectPolicyData(final String table, String registNo)
			throws Exception {
		List<PolicyData> policyDatas = new ArrayList<PolicyData>();
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();

			database.beginTransactionWithListener(new SQLiteTransactionListener() {

				@Override
				public void onRollback() {
					Log.d("carData select", "rollback");
				}

				@Override
				public void onCommit() {
					Log.d("caraData insert", "commit");
				}

				@Override
				public void onBegin() {
					Log.d("caraData insert", "begin");
				}
			});

			cursor = database.query(table, null, "registNo=?",
					new String[] { registNo }, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				PolicyData bean = new PolicyData();
				bean.setPolicyNo(cursor.getString(cursor
						.getColumnIndex("policyNo")));
				bean.setRegistNo(cursor.getString(cursor
						.getColumnIndex("registNo")));
				bean.setInsuredName(cursor.getString(cursor
						.getColumnIndex("insuredName")));
				bean.setComCName(cursor.getString(cursor
						.getColumnIndex("comCName")));
				bean.setStartDate(cursor.getString(cursor
						.getColumnIndex("startDate")));
				bean.setEndDate(cursor.getString(cursor
						.getColumnIndex("endDate")));
				bean.setRiskCode(cursor.getString(cursor
						.getColumnIndex("riskCode")));

				PolicyDetailData policyDetailData = selectPolicyDetailData(
						policy_detail_data, id + "", null);
				List<PolicyDriverData> policyDriverDataList = selectPolicyDriverDatas(
						policy_driver_data, id + "", null);
				List<PolicyEngageData> policyEngageDataList = selectPolicyEngageDatas(
						policy_engage_data, id + "", null);
				List<PolicyKindData> policyKindDataList = selectPolicyKindDatas(
						policy_kind_data, id + "", null);

				bean.setPolicyDetailData(policyDetailData);
				bean.setPolicyDriverDataList(policyDriverDataList);
				bean.setPolicyEngageDataList(policyEngageDataList);
				bean.setPolicyKindDataList(policyKindDataList);

				policyDatas.add(bean);
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
		return policyDatas;
	}

	public void deletePolicyDatas(String table, String registNo) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {
			@Override
			public void onRollback() {
				Log.d("deletePolicyDatas delete", "rollback");
			}

			@Override
			public void onCommit() {
				Log.d("deletePolicyDatas delete", "commit");
			}

			@Override
			public void onBegin() {
				Log.d("deletePolicyDatas delete", "begin");

			}
		});

		try {

			Cursor cursor = database.query("", new String[] { "id" },
					"registNo=?", new String[] { "" }, null, null, null);
			cursor.moveToFirst();
			int id = -1;
			while (!cursor.isAfterLast()) {
				id = cursor.getInt(cursor.getColumnIndex("id"));

			}
			cursor.close();
			if (id != -1) {
				database.delete(table, "registNo=?", new String[] { registNo });
				deletePolicyDetailData(policy_detail_data, id + "");
				deletePolicyKindData(policy_kind_data, id + "");
				deletepolicyDriverData(policy_driver_data, id + "");
				deletePolicyEngageData(policy_engage_data, id + "");

			}

			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d(" deletePolicyDatas error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}
	}

	public void updatePolicyData(String table, String policyNo,
			List<PolicyData> policyDatas) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				Log.d("carData delete", "rollback");
			}

			@Override
			public void onCommit() {
				Log.d("carData delete", "commit");
			}

			@Override
			public void onBegin() {
				Log.d("carData delete", "begin");

			}
		});

		try {

			for (int i = 0; i < policyDatas.size(); i++) {
				PolicyData bean = policyDatas.get(i);
				if (bean != null) {
					ContentValues values = new ContentValues();
					values.putNull("id");
					values.put("policyNo", bean.getPolicyNo());
					values.put("registNo", bean.getRegistNo());
					values.put("insuredName", bean.getInsuredName());
					values.put("comCName", bean.getComCName());
					values.put("startDate", bean.getStartDate());
					values.put("endDate", bean.getEndDate());
					values.put("riskCode", bean.getRiskCode());
					database.update(table, values, "policyNo=?",
							new String[] { policyNo });
				}
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
	 * 查询保单详情
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
	public PolicyDetailData selectPolicyDetailData(String table,
			String policyId, String orderBy) throws Exception {
		PolicyDetailData bean = null;
		Cursor cursor = null;
		
			cursor = database.query(table, null, "policyId=?",
					new String[] { policyId }, null, null, orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				bean = new PolicyDetailData();
				bean.setPolicyId(cursor.getString(cursor
						.getColumnIndex("policyId")));
				bean.setPolicyNo(cursor.getString(cursor
						.getColumnIndex("policyNo")));
				bean.setRiskCode(cursor.getString(cursor
						.getColumnIndex("riskCode")));
				bean.setInsuredName(cursor.getString(cursor
						.getColumnIndex("insuredName")));
				bean.setLicenseNo(cursor.getString(cursor
						.getColumnIndex("licenseNo")));
				bean.setLicenseColor(cursor.getString(cursor
						.getColumnIndex("licenseColor")));
				bean.setBrandName(cursor.getString(cursor
						.getColumnIndex("brandName")));
				bean.setPurchasePrice(cursor.getString(cursor
						.getColumnIndex("purchasePrice")));
				bean.setStartDate(cursor.getString(cursor
						.getColumnIndex("startDate")));
				bean.setEndDate(cursor.getString(cursor
						.getColumnIndex("endDate")));
				bean.setClauseType(cursor.getString(cursor
						.getColumnIndex("clauseType")));
				bean.setCarKind(cursor.getString(cursor
						.getColumnIndex("carKind")));
				bean.setCarOwner(cursor.getString(cursor
						.getColumnIndex("carOwner")));
				bean.setEngineNo(cursor.getString(cursor
						.getColumnIndex("engineNo")));
				bean.setFrameNo(cursor.getString(cursor
						.getColumnIndex("frameNo")));
				bean.setVinNo(cursor.getString(cursor.getColumnIndex("vinNo")));
				bean.setRunArea(cursor.getString(cursor
						.getColumnIndex("runArea")));
				bean.setEnrollDate(cursor.getString(cursor
						.getColumnIndex("enrollDate")));
				bean.setSeatCount(cursor.getString(cursor
						.getColumnIndex("seatCount")));
				bean.setUseNature(cursor.getString(cursor
						.getColumnIndex("useNature")));
				bean.setColorCode(cursor.getString(cursor
						.getColumnIndex("colorCode")));
				bean.setEndorseTimes(cursor.getString(cursor
						.getColumnIndex("endorseTimes")));
				bean.setComCName(cursor.getString(cursor
						.getColumnIndex("comCName")));
				bean.setM2Flag(cursor.getString(cursor.getColumnIndex("m2Flag")));
				bean.setClaimTimes(cursor.getString(cursor
						.getColumnIndex("claimTimes")));
				bean.setIdentifyNumber(cursor.getString(cursor
						.getColumnIndex("identifyNumber")));
				bean.setVipType(cursor.getString(cursor
						.getColumnIndex("vipType")));

				cursor.moveToNext();

			}

		
			if (cursor != null) {
				cursor.close();
			}

		

		return bean;
	}

	public void updatePolicyDetailDatas(String tableName, String policyNo,
			List<PolicyDetailData> policyDetailDatas) {

		for (int i = 0; i < policyDetailDatas.size(); i++) {
			ContentValues values = new ContentValues();
			PolicyDetailData bean = policyDetailDatas.get(i);
			values.put("policyNo", bean.getPolicyNo());
			values.put("riskCode", bean.getRiskCode());
			values.put("insuredName", bean.getInsuredName());
			values.put("licenseNo", bean.getLicenseNo());
			values.put("licenseColor", bean.getLicenseColor());
			values.put("brandName", bean.getBrandName());
			values.put("purchasePrice", bean.getPurchasePrice());
			values.put("startDate", bean.getStartDate());
			values.put("endDate", bean.getEndDate());
			values.put("clauseType", bean.getClauseType());
			values.put("carKind", bean.getCarKind());
			values.put("carOwner", bean.getCarOwner());
			values.put("engineNo", bean.getEngineNo());
			values.put("frameNo", bean.getFrameNo());
			values.put("vinNo", bean.getVinNo());
			values.put("runArea", bean.getRunArea());
			values.put("enrollDate", bean.getEnrollDate());
			values.put("seatCount", bean.getSeatCount());
			values.put("useNature", bean.getUseNature());
			values.put("colorCode", bean.getColorCode());
			values.put("endorseTimes", bean.getEndorseTimes());
			values.put("comCName", bean.getComCName());
			values.put("m2Flag", bean.getM2Flag());
			values.put("claimTimes", bean.getClaimTimes());
			values.put("identifyNumber", bean.getIdentifyNumber());
			values.put("vipType", bean.getVipType());

			database.update(tableName, values, "policyNo=?",
					new String[] { policyNo });

		}

	}

	public void deletePolicyDetailData(String tableName, String policyId) {
		database.delete(tableName, "policyId=?", new String[] { policyId });
	}

	public void insertPolicyDetailData(String tableName, String policyId,
			PolicyDetailData bean) {

		ContentValues values = new ContentValues();
		values.put("policyId", policyId);
		values.put("policyNo", bean.getPolicyNo());
		values.put("riskCode", bean.getRiskCode());
		values.put("insuredName", bean.getInsuredName());
		values.put("licenseNo", bean.getLicenseNo());
		values.put("licenseColor", bean.getLicenseColor());
		values.put("brandName", bean.getBrandName());
		values.put("purchasePrice", bean.getPurchasePrice());
		values.put("startDate", bean.getStartDate());
		values.put("endDate", bean.getEndDate());
		values.put("clauseType", bean.getClauseType());
		values.put("carKind", bean.getCarKind());
		values.put("carOwner", bean.getCarOwner());
		values.put("engineNo", bean.getEngineNo());
		values.put("frameNo", bean.getFrameNo());
		values.put("vinNo", bean.getVinNo());
		values.put("runArea", bean.getRunArea());
		values.put("enrollDate", bean.getEnrollDate());
		values.put("seatCount", bean.getSeatCount());
		values.put("useNature", bean.getUseNature());
		values.put("colorCode", bean.getColorCode());
		values.put("endorseTimes", bean.getEndorseTimes());
		values.put("comCName", bean.getComCName());
		values.put("m2Flag", bean.getM2Flag());
		values.put("claimTimes", bean.getClaimTimes());
		values.put("identifyNumber", bean.getIdentifyNumber());
		values.put("vipType", bean.getVipType());

		database.insert(tableName, null, values);

	}

	// policyKindDatas
	/**
	 * 
	 * @param table
	 * @param policyId
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<PolicyKindData> selectPolicyKindDatas(String table,
			String policyId, String orderBy) throws Exception {
		List<PolicyKindData> policyKindDatas = new ArrayList<PolicyKindData>();
		Cursor cursor = null;
	
			cursor = database.query(table, null, "policyId=?",
					new String[] { policyId }, null, null, orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				PolicyKindData bean = new PolicyKindData();
				bean.setPolicyNo(cursor.getString(cursor
						.getColumnIndex("policyNo")));
				bean.setKindName(cursor.getString(cursor
						.getColumnIndex("kindName")));
				bean.setAmount(cursor.getString(cursor.getColumnIndex("amount")));
				bean.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
				policyKindDatas.add(bean);
				cursor.moveToNext();

			}

		
			cursor.close();
			

		return policyKindDatas;
	}

	public void updatePolicyKindDatas(String tableName, String policyNo,
			List<PolicyKindData> policyKindDatas) {
		ContentValues values = new ContentValues();

		for (int i = 0; i < policyKindDatas.size(); i++) {
			PolicyKindData bean = new PolicyKindData();
			values.put("policyNo", bean.getPolicyNo());
			values.put("kindName", bean.getKindName());
			values.put("amount", bean.getAmount());
			values.put("remark", bean.getRemark());
			database.update(tableName, values, "policyNo=?",
					new String[] { policyNo });

		}

	}

	public void deletePolicyKindData(String tableName, String policyId) {
		database.delete(tableName, "policyId=?", new String[] { policyId });
	}

	public void inserPolicyKindData(String tableName, String policyId,
			List<PolicyKindData> policyKindDatas) {
		for (int i = 0; i < policyKindDatas.size(); i++) {
			ContentValues values = new ContentValues();
			PolicyKindData bean = policyKindDatas.get(i);
			values.put("policyId", policyId);
			values.put("policyNo", bean.getPolicyNo());
			values.put("kindName", bean.getKindName());
			values.put("amount", bean.getAmount());
			values.put("remark", bean.getRemark());
			database.insert(tableName, null, values);

		}
	}

	//

	// policyDriverData
	/**
	 * 
	 * @param table
	 * @param policyId
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<PolicyDriverData> selectPolicyDriverDatas(String table,
			String policyId, String orderBy) throws Exception {
		List<PolicyDriverData> policyDriverDatas = new ArrayList<PolicyDriverData>();
		Cursor cursor = null;
		
			cursor = database.query(table, null, "policyId=?",
					new String[] { policyId }, null, null, orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				PolicyDriverData bean = new PolicyDriverData();
				bean.setPolicyId(cursor.getString(cursor
						.getColumnIndex("policyId")));
				bean.setDriverName(cursor.getString(cursor
						.getColumnIndex("driverName")));
				bean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				bean.setIdentifyNumber(cursor.getString(cursor
						.getColumnIndex("identifyNumber")));
				bean.setDrivingLicenseNo(cursor.getString(cursor
						.getColumnIndex("drivingLicenseNo")));
				bean.setAcceptLicenseDate(cursor.getString(cursor
						.getColumnIndex("acceptLicenseDate")));
				bean.setPolicyNo(cursor.getString(cursor
						.getColumnIndex("policyNo")));
				policyDriverDatas.add(bean);

				cursor.moveToNext();

			}

			cursor.close();
		

		return policyDriverDatas;
	}

	public void updatePpolicyDriverDatas(String tableName, String policyNo,
			List<PolicyDriverData> policyDriverDatas) {
		ContentValues values = new ContentValues();

		for (int i = 0; i < policyDriverDatas.size(); i++) {
			PolicyDriverData bean = policyDriverDatas.get(i);
			values.put("driverName", bean.getDriverName());
			values.put("sex", bean.getSex());
			values.put("identifyNumber", bean.getIdentifyNumber());
			values.put("drivingLicenseNo", bean.getDrivingLicenseNo());
			values.put("acceptLicenseDate", bean.getAcceptLicenseDate());
			values.put("policyNo", bean.getPolicyNo());
			database.update(tableName, values, "policyNo=?",
					new String[] { policyNo });

		}

	}

	public void deletepolicyDriverData(String tableName, String policyId) {
		database.delete(tableName, "policyId=?", new String[] { policyId });
	}

	public void insertpolicyDriverData(String tableName, String policyId,
			List<PolicyDriverData> policyDriverDatas) {
		for (int i = 0; i < policyDriverDatas.size(); i++) {
			ContentValues values = new ContentValues();
			PolicyDriverData bean = policyDriverDatas.get(i);
			values.put("policyId", policyId);
			values.put("driverName", bean.getDriverName());
			values.put("sex", bean.getSex());
			values.put("identifyNumber", bean.getIdentifyNumber());
			values.put("drivingLicenseNo", bean.getDrivingLicenseNo());
			values.put("acceptLicenseDate", bean.getAcceptLicenseDate());
			values.put("policyNo", bean.getPolicyNo());
			database.insert(tableName, null, values);

		}

	}

	// policyEngageDatas

	public List<PolicyEngageData> selectPolicyEngageDatas(String table,
			String policyId, String orderBy) throws Exception {
		List<PolicyEngageData> policyEngageDatas = new ArrayList<PolicyEngageData>();
		Cursor cursor = null;
		
			cursor = database.query(table, null, "policyId=?",
					new String[] { policyId }, null, null, orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				PolicyEngageData bean = new PolicyEngageData();
				bean.setId(cursor.getLong(cursor.getColumnIndex("id")));
				bean.setClauseCode(cursor.getString(cursor
						.getColumnIndex("clauseCode")));
				bean.setClauses(cursor.getString(cursor
						.getColumnIndex("clauses")));
				bean.setPolicyNo(cursor.getString(cursor
						.getColumnIndex("policyNo")));
				policyEngageDatas.add(bean);

				cursor.moveToNext();

			}

		
			cursor.close();
	

		return policyEngageDatas;
	}

	public void updatePolicyEngageDatas(String tableName, String policyNo,
			List<PolicyEngageData> policyEngageDatas) {
		ContentValues values = new ContentValues();

		for (int i = 0; i < policyEngageDatas.size(); i++) {
			PolicyEngageData bean = policyEngageDatas.get(i);
			values.put("clauseCode", bean.getClauseCode());
			values.put("clauses", bean.getClauses());
			values.put("policyNo", bean.getPolicyNo());
			database.update(tableName, values, "policyNo=? and id=?",
					new String[] { policyNo, bean.getId() + "" });

		}

	}

	public void deletePolicyEngageData(String tableName, String policyId) {
		database.delete(tableName, "policyId=?", new String[] { policyId });
	}

	public void insertPolicyEngageDatas(String tableName, String policyId,
			List<PolicyEngageData> policyEngageDatas) {
		try {
			for (int i = 0; i < policyEngageDatas.size(); i++) {
				ContentValues values = new ContentValues();
				PolicyEngageData bean = policyEngageDatas.get(i);
				values.putNull("id");
				values.put("policyId", policyId);
				values.put("clauseCode", bean.getClauseCode());
				values.put("clauses", bean.getClauses());
				values.put("policyNo", bean.getPolicyNo());
				database.insert(tableName, null, values);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
