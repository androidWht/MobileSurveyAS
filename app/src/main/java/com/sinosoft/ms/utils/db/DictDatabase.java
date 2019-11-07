package com.sinosoft.ms.utils.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.DicInfoBean;
import com.sinosoft.ms.model.Item;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午4:58:19
 */

public class DictDatabase {

	/**
	 * 获取字典信息
	 * 
	 * @return 字典信息集合
	 */
	public static List<String> getDict(Context context, String type_code) {
		List<String> dateList = null;
		SQLiteDatabase db = null;
		try {
			db = DatabaseHelper.getHelper(context).openDatabase();
			String sql = " select type_code,value_code,value_name "
					+ " from dict "
					+ " where valid_mark = '1' and type_code = '" + type_code
					+ "'" + " order by type_code asc,value_code asc";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}

			dateList = new ArrayList<String>();

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				String typeCode = cursor.getString(cursor
						.getColumnIndex("type_code"));
				String valueCode = cursor.getString(cursor
						.getColumnIndex("value_code"));
				String valueName = cursor.getString(cursor
						.getColumnIndex("value_name"));

				dateList.add(valueName);
				cursor.moveToNext();
				Log.e("erro", valueName);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return dateList;
	}
	public void clearDic(Context context) throws Exception{
		SQLiteDatabase db = null;
		try{
			db = DatabaseHelper.getHelper(context).openDatabase();
			db.execSQL("delete  from dict");
		}catch(Exception e){
			e.printStackTrace();
			if(db!=null){
				db.close();
			}
			throw e;
		}
		if(db!=null){
			db.close();
		}
	}
	public void insertDic(Context context,List<DicInfoBean> dicInfoBeans) throws Exception{
		List<String> dateList = null;
		SQLiteDatabase db = null;
		try {
			db = DatabaseHelper.getHelper(context).openDatabase();
			
			if(dicInfoBeans!=null&&dicInfoBeans.size()>0){
				for(int i=0;i<dicInfoBeans.size();i++){
					DicInfoBean bean=dicInfoBeans.get(i);
					List<Item> items=bean.getList();
					
					for(int j=0;j<items.size();j++){
						ContentValues values=new ContentValues();
						values.putNull("id");
						values.put("type_code", bean.getType());
						values.put("type_name", bean.getName());
						Item item=items.get(j);
						values.put("value_code", item.getCode());
						values.put("value_name",item.getValue());
						String fmt = "yyyy-MM-dd";
						SimpleDateFormat formate = new SimpleDateFormat(fmt);
						Date date =new Date();
						String dateStr = formate.format(date);
						values.put("create_date",dateStr);
						values.put("create_by","8000161");
						values.put("valid_mark","1");
						db.insert("dict", null, values);
					}
					
				}
			}
			
		}catch(Exception e){
			throw e;
		}finally{
			db.close();
			
		}
		
	}
	
	/**
	 * 装载字典信息
	 * 
	 * @return
	 */
	public Map<String, Map<String, String>> getDict(Context context) {
		Map<String, Map<String, String>> dateMap = null;
		SQLiteDatabase db = null;
		try {
			db = DatabaseHelper.getHelper(context).openDatabase();
			String sql = " select type_code,value_code,value_name "
					+ " from dict " + " where valid_mark = '1' "
					+ " order by type_code asc,value_code asc";
			Cursor cursor = db.rawQuery(sql, null);
			dateMap = new HashMap<String, Map<String, String>>();
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}

			Map<String, String> dictMap = new LinkedHashMap<String, String>();
			String temp = null;

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				String typeCode = cursor.getString(cursor
						.getColumnIndex("type_code"));
				String valueCode = cursor.getString(cursor
						.getColumnIndex("value_code"));
				String valueName = cursor.getString(cursor
						.getColumnIndex("value_name"));
				// LogFatory.i("BasicInfoAct", "typeCode=" + typeCode +
				// " valueCode="
				// + valueCode + " valueName=" + valueName);
				if (null == temp) {
					temp = typeCode;
				}

				if (!temp.equals(typeCode)) {
					dateMap.put(temp, dictMap);

					// 设置下一项数据
					dictMap = new LinkedHashMap<String, String>();
					temp = typeCode;
				}
				dictMap.put(valueCode, valueName);
				cursor.moveToNext();
			}

			// 结束数据装入
			if (null != dictMap && !dictMap.isEmpty()) {
				dateMap.put(temp, dictMap);
				dictMap = null;
				temp = null;
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return dateMap;
	}
	
	/**
	 * 更新字典表中的修理厂信息
	 * @param context
	 * @param repairFactoryCode
	 * @param repairFactoryName
	 * @return
	 */
	public void updateCommonFactory(Context context,String repairFactoryCode,String repairFactoryName)
			throws Exception {
		SQLiteDatabase database = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("DictDatabase update", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("DictDatabase update", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("DictDatabase update", "begin");

			}
		});

		try {

			if (!StringUtils.isEmpty(repairFactoryCode) && !StringUtils.isEmpty(repairFactoryName)) {
				ContentValues values = new ContentValues();
				values.put("value_name", repairFactoryName);

				database.update("dict", values, "type_code = ? and value_code = ?", new String[]{repairFactoryCode,"RepairFactory"});
			}
			database.setTransactionSuccessful();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			database.endTransaction();
			database.close();

		}

	}

}
