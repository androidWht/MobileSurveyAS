package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sinosoft.ms.model.Bank;

/**
 * 系统名：移动查勘定损系统 
 * 子系统名：字典缓存信息
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * 			SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author nianchun.li
 * @createTime Fri Feb 22 23:39:49 CST 2013
 */
public class DictCashe {
	private static DictCashe instant = null;
	private Map<String, Map<String, String>> dateMap = null;
	private static List<Bank> bankList;
	
	/**
	 * 获取缓存字典信息实例
	 * 
	 * @return 字典信息实例
	 */
	public static DictCashe getInstant() {
		if (null == instant) {
//			Log.e("dictionary", "dictionary service");
			instant = new DictCashe();
			instant.dateMap = new HashMap<String, Map<String, String>>();
		}
		return instant;
	}
	
	public static List<Bank> getBankList(Context context){
		if(null == bankList){
			bankList = new ArrayList<Bank>();
			SQLiteDatabase db = null;
			try {
				db = DatabaseHelper.getHelper(context).openDatabase();
				String sql = " select id,type_code,value_code,value_name "
						+ " from dict " + " where valid_mark = '1' and type_code = 'CodeToBank' "
						+ " order by value_code asc";
				Cursor cursor = db.rawQuery(sql, null);
				Bank bank;
				while(cursor.moveToNext()){
					bank = new Bank();
					bank.setId(cursor.getInt(cursor.getColumnIndex("id")));
					bank.setCode(cursor.getString(cursor
							.getColumnIndex("value_code")));
					bank.setValue(cursor.getString(cursor
							.getColumnIndex("value_name")));
					bankList.add(bank);
				}
			}catch(Exception e){
				
			}
		}
		return bankList;
	}

	public Map<String, Map<String, String>> getDict() {
		return dateMap;
	}

	public void setDict(Map<String, Map<String, String>> dateMap) {
		this.dateMap = dateMap;
	}
	
	public void updateRepairFactory(String repairFactoryCode,String repairFactoryName){
		Map<String,String> map = dateMap.get("RepairFactory");
		map.put(repairFactoryCode, repairFactoryName);
	}
}
