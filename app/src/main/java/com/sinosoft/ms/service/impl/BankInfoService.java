package com.sinosoft.ms.service.impl;

import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.IBankInfoService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.db.DatabaseHelper;

/**
 * 系统名：移动查勘定损 子系统名：银行信息控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author wuhaijie
 * @createTime 上午10:22:26
 */

public class BankInfoService extends BasicHttp implements IBankInfoService {
	private Activity activity = null;
	private SQLiteDatabase db = null;

	public BankInfoService() {
		super();
		if (null == activity) {
			activity = ActivityManage.getInstance().peek();
		}
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.ms.service.IBankInfoService#save(com.sinosoft.ms.model.BankInfo
	 * )
	 */
	@Override
	public boolean save(BankInfo bankInfo) throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		//如果为空则添加，否则更新
		if(null == getByTaskId(bankInfo.getTask_id())){	
			try {
				if (null != bankInfo) {
					db = DatabaseHelper.getHelper(activity).openDatabase();
					ContentValues newValues = new ContentValues();
					newValues.put("registNo", bankInfo.getRegistNo());
					newValues.put("task_id", bankInfo.getTask_id());
					newValues.put("province", bankInfo.getProvince());
					newValues.put("city", bankInfo.getCity());
					newValues.put("accountsName", bankInfo.getAccountsName());
					newValues.put("accounts", bankInfo.getAccounts());
					newValues.put("bankName", bankInfo.getBankName());
					newValues.put("bankType", bankInfo.getBankType());
					newValues.put("bankOutlets", bankInfo.getBankOutlets());
					newValues.put("bankNumber", bankInfo.getBankNumber());
					newValues.put("mobile", bankInfo.getMobile());
					newValues.put("cityFlag", bankInfo.getCityFlag());
					newValues.put("priorityFlag", bankInfo.getPriorityFlag());
					newValues.put("purpose", bankInfo.getPurpose());
					db.insert("bank_info", null, newValues);
					result = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != db) {
					db.close();
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.ms.service.IBankInfoService#getByRegistNo(java.lang.String)
	 */
	@Override
	public BankInfo getByRegistNo(String registNo) throws Exception {
		// TODO Auto-generated method stub
		BankInfo bankInfo = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from bank_info where regist_no='" + registNo
					+ "'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				bankInfo = new BankInfo();
				bankInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
				bankInfo.setRegistNo(cursor.getString(cursor
						.getColumnIndex("registNo")));
				bankInfo.setTask_id(cursor.getString(cursor
						.getColumnIndex("task_id")));
				bankInfo.setProvince(cursor.getString(cursor
						.getColumnIndex("province")));
				bankInfo.setCity(cursor.getString(cursor.getColumnIndex("city")));
				bankInfo.setAccountsName(cursor.getString(cursor
						.getColumnIndex("accountsName")));
				bankInfo.setAccounts(cursor.getString(cursor
						.getColumnIndex("accounts")));
				bankInfo.setBankName(cursor.getString(cursor
						.getColumnIndex("bankName")));
				bankInfo.setBankType(cursor.getString(cursor
						.getColumnIndex("bankType")));
				bankInfo.setBankOutlets(cursor.getString(cursor
						.getColumnIndex("bankOutlets")));
				bankInfo.setBankNumber(cursor.getString(cursor
						.getColumnIndex("bankNumber")));
				bankInfo.setMobile(cursor.getString(cursor
						.getColumnIndex("mobile")));
				bankInfo.setCityFlag(cursor.getString(cursor
						.getColumnIndex("cityFlag")));
				bankInfo.setPriorityFlag(cursor.getString(cursor
						.getColumnIndex("priorityFlag")));
				bankInfo.setPurpose(cursor.getString(cursor
						.getColumnIndex("purpose")));
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

		return bankInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.ms.service.IBankInfoService#getByTaskId(java.lang.String)
	 */
	@Override
	public BankInfo getByTaskId(String taskId) throws Exception {
		// TODO Auto-generated method stub
		BankInfo bankInfo = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from bank_info where task_id='" + taskId
					+ "'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				bankInfo = new BankInfo();
				bankInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
				bankInfo.setRegistNo(cursor.getString(cursor
						.getColumnIndex("registNo")));
				bankInfo.setTask_id(cursor.getString(cursor
						.getColumnIndex("task_id")));
				bankInfo.setProvince(cursor.getString(cursor
						.getColumnIndex("province")));
				bankInfo.setCity(cursor.getString(cursor.getColumnIndex("city")));
				bankInfo.setAccountsName(cursor.getString(cursor
						.getColumnIndex("accountsName")));
				bankInfo.setAccounts(cursor.getString(cursor
						.getColumnIndex("accounts")));
				bankInfo.setBankName(cursor.getString(cursor
						.getColumnIndex("bankName")));
				bankInfo.setBankType(cursor.getString(cursor
						.getColumnIndex("bankType")));
				bankInfo.setBankOutlets(cursor.getString(cursor
						.getColumnIndex("bankOutlets")));
				bankInfo.setBankNumber(cursor.getString(cursor
						.getColumnIndex("bankNumber")));
				bankInfo.setMobile(cursor.getString(cursor
						.getColumnIndex("mobile")));
				bankInfo.setCityFlag(cursor.getString(cursor
						.getColumnIndex("cityFlag")));
				bankInfo.setPriorityFlag(cursor.getString(cursor
						.getColumnIndex("priorityFlag")));
				bankInfo.setPurpose(cursor.getString(cursor
						.getColumnIndex("purpose")));
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
		return bankInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.ms.service.IBankInfoService#update(com.sinosoft.ms.model
	 * .BankInfo)
	 */
	@Override
	public boolean update(BankInfo bankInfo) throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			if (null != bankInfo) {
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues newValues = new ContentValues();
				newValues.put("registNo", bankInfo.getRegistNo());
				newValues.put("task_id", bankInfo.getTask_id());
				newValues.put("province", bankInfo.getProvince());
				newValues.put("city", bankInfo.getCity());
				newValues.put("accountsName", bankInfo.getAccountsName());
				newValues.put("accounts", bankInfo.getAccounts());
				newValues.put("bankName", bankInfo.getBankName());
				newValues.put("bankType", bankInfo.getBankType());
				newValues.put("bankOutlets", bankInfo.getBankOutlets());
				newValues.put("bankNumber", bankInfo.getBankNumber());
				newValues.put("mobile", bankInfo.getMobile());
				newValues.put("cityFlag", bankInfo.getCityFlag());
				newValues.put("priorityFlag", bankInfo.getPriorityFlag());
				newValues.put("purpose", bankInfo.getPurpose());
				int id = db.update("bank_info", newValues, "task_id=?",
						new String[] { bankInfo.getTask_id() });
				if (id != 0)
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.ms.service.IBankInfoService#delete(com.sinosoft.ms.model
	 * .BankInfo)
	 */
	@Override
	public boolean delete(String taskId) throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
				db = DatabaseHelper.getHelper(activity).openDatabase();
				db.delete("bank_info", "task_id=?",
						new String[] { taskId });
				result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return result;
	}

	public List<BankInfo> requestForBankPoint(BankInfo bankInfo) throws Exception {
		User user = UserCashe.getInstant().getUser();
		// 加载任务中心的xml的文件
		String xmlText = requestXml.lossBankPointQueryXml(user.getName(), bankInfo); // 发送任务中心请求，获取服务器数据
		sendRequest(xmlText); // 解析任务中心列表

		List<BankInfo> result = responseXML.bankPointParse(inputStream);

		return result;

	}

}
