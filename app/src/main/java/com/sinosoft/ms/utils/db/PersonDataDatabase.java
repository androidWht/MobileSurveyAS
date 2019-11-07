package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PersonDetailData;
import com.sinosoft.ms.model.PolicyData;

/**
 * 系统名：移动查勘定损 子系统名：涉案车辆著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:18:33
 */

public class PersonDataDatabase {

	private SQLiteDatabase database;
	private Context context;

	public PersonDataDatabase(Context context) {
		this.context = context;
	}

	// 插入数据
	public void  insertPersonData(final String table, List<PersonData> personDatas)
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
				Log.d("personData insert", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("personData insert", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("personData insert", "begin");
			}
		});
         long id;
		try {

			for (int i = 0; i < personDatas.size(); i++)
			{
				PersonData bean = personDatas.get(i);
				if (bean != null)
				{
					ContentValues values = new ContentValues();
					values.putNull("id");
					values.put("registNo", bean.getRegistNo());
					values.put("lossParty", bean.getLossParty());
					values.put("severeNum", bean.getSevereNum());
					values.put("minor", bean.getMinor());
					values.put("deathNum", bean.getDeathNum());
					values.put("sumLossFee", bean.getSumLossFee());
					values.put("rescueFee", bean.getRescueFee());
					values.put("checkDate", bean.getCheckDate());
					values.put("checkSite", bean.getCheckSite());
					values.put("rescueContext", bean.getRescueContext());
					values.put("createOwner", bean.getCreateOwner());
					values.put("modfiyOwner", bean.getModfiyOwner());
					
					
					
					id = database.insert(table, null, values);
					bean.setId(id+"");
					if (id != -1) {
						List<PersonDetailData> details = bean.getPersonDetailData();
						if (details != null) {
							insertPersonDetailDatas("person_detail_data", id + "", details);
						}
					}					
				}
			}//
			database.setTransactionSuccessful();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			database.endTransaction();
			database.close();
		}
		//return id+"";
	}

	// 插入数据
	public List<PersonData> selectPersonData(final String table, String registNo)
	{
		List<PersonData> personDatas = new ArrayList<PersonData>();
		Cursor cursor = null;
		
		try 
		{
			database = DatabaseHelper.getHelper(context).openDatabase();
		    database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("personData insert", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("personData insert", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("personData insert", "begin");
			}
		});
		
		

			cursor= database.query(table, null, "registNo=?",new String[] { registNo }, null, null, null);
			cursor.moveToFirst();
			
			while (!cursor.isAfterLast()) 
			{
				PersonData bean = new PersonData();
				int id=cursor.getInt(cursor.getColumnIndex("id"));
                bean.setPersonDataId(id+"");
				bean.setRegistNo(cursor.getString(cursor
						.getColumnIndex("registNo")));
				bean.setLossParty(cursor.getString(cursor
						.getColumnIndex("lossParty")));
				bean.setSevereNum(cursor.getString(cursor
						.getColumnIndex("severeNum")));
				bean.setMinor(cursor.getString(cursor.getColumnIndex("minor")));
				bean.setDeathNum(cursor.getString(cursor
						.getColumnIndex("deathNum")));
				bean.setSumLossFee(cursor.getString(cursor
						.getColumnIndex("sumLossFee")));
				bean.setRescueFee(cursor.getString(cursor
						.getColumnIndex("rescueFee")));
				bean.setCheckDate(cursor.getString(cursor
						.getColumnIndex("checkDate")));
				bean.setCheckSite(cursor.getString(cursor
						.getColumnIndex("checkSite")));
				bean.setRescueContext(cursor.getString(cursor
						.getColumnIndex("rescueContext")));
				
				bean.setCreateOwner(cursor.getString(cursor
						.getColumnIndex("createOwner")));
				bean.setModfiyOwner(cursor.getString(cursor
						.getColumnIndex("modfiyOwner")));

				
				bean.init();
				List<PersonDetailData> detailDatas= selectPersonDetailDatas("person_detail_data",id+"", null);
				bean.setPersonDetailData(detailDatas);
				personDatas.add(bean);
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
		return personDatas;
	}

	public void deletePersonData(String table, String registNo) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {
			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("deletePersonData delete", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("deletePersonData delete", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("deletePersonData delete", "begin");

			}
		});

		try {
			
			database.execSQL("delete from person_detail_data where personId=(select personDataId from person_data where registNo='"+registNo+"')");
			database.delete(table, "registNo=?", new String[] { registNo });
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("baiscInfo detele error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}
	}

	public void deletePersonDataById(String registNo,String personDataId) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {
			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("personData delete", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("personData delete", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("personData delete", "begin");

			}
		});

		try {

			database.execSQL("delete from person_detail_data where personId="+personDataId+"");
			database.execSQL("delete from person_data where registNo='" + registNo + "' and id="+personDataId+"");
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("baiscInfo detele error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}
	}
	
	
	
	
	public void updatePersonData(String table, String registNo, List<PersonData> personDatas) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("personData delete", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("personData delete", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("personData delete", "begin");

			}
		});

		try 
		{
			for (int i = 0; i < personDatas.size(); i++)
			{
				PersonData bean = personDatas.get(i);
				ContentValues values = new ContentValues();
				values.put("registNo", bean.getRegistNo());
				values.put("lossParty", bean.getLossParty());
				values.put("severeNum", bean.getSevereNum());
				values.put("minor", bean.getMinor());
				values.put("deathNum", bean.getDeathNum());
				values.put("sumLossFee", bean.getSumLossFee());
				values.put("rescueFee", bean.getRescueFee());
				values.put("checkDate", bean.getCheckDate());
				values.put("checkSite", bean.getCheckSite());
				values.put("rescueContext", bean.getRescueContext());

				database.update(table, values, "registNo=?",new String[] { registNo });
				String personId=bean.getPersonDataId();
				List<PersonDetailData> personDetailDatas=bean.getPersonDetailData();
				if(personDetailDatas!=null&&!personDetailDatas.isEmpty()){
					updatePersonDetailDatas("person_detail_data", personId, personDetailDatas);
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
	 * 查询人伤详情
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
	public List<PersonDetailData> selectPersonDetailDatas(String table,
			String parentId, String orderBy) throws Exception {
		
		Cursor cursor = null;
		
		List<PersonDetailData> personDetails=new ArrayList<PersonDetailData>();
		
			cursor = database.query(table, null, "personId=?",
					new String[] { parentId }, null, null, orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				PersonDetailData bean = new PersonDetailData();
				bean.setId(cursor.getInt(cursor.getColumnIndex("id"))+"");
				bean.setPersonId(cursor.getString(cursor
						.getColumnIndex("personId")));
				bean.setPersonName(cursor.getString(cursor
						.getColumnIndex("personName")));
				bean.setPersonSex(cursor.getString(cursor
						.getColumnIndex("personSex")));
				bean.setAge(cursor.getString(cursor.getColumnIndex("age")));
				bean.setPersonPayType(cursor.getString(cursor
						.getColumnIndex("personPayType")));
				bean.setTreatType(cursor.getString(cursor
						.getColumnIndex("treatType")));
				bean.setHospitalName(cursor.getString(cursor
						.getColumnIndex("hospitalName")));
				bean.setLossFee(cursor.getString(cursor
						.getColumnIndex("lossFee")));
				bean.setWoundDetail(cursor.getString(cursor
						.getColumnIndex("woundDetail")));
				bean.init();
				personDetails.add(bean);
				cursor.moveToNext();

			}

		
			cursor.close();
			
	

		return personDetails;
	}

	public void updatePersonDetailDatas(String tableName, String personId,
			List<PersonDetailData> personDetailDatas) {
		for (PersonDetailData bean : personDetailDatas) {
			ContentValues values = new ContentValues();

			values.put("personId",personId);
			values.put("personName", bean.getPersonName());
			values.put("personSex", bean.getPersonSex());
			values.put("age", bean.getAge());
			values.put("personPayType", bean.getPersonPayType());
			values.put("treatType", bean.getTreatType());
			values.put("hospitalName", bean.getHospitalName());
			values.put("lossFee", bean.getLossFee());
			values.put("woundDetail", bean.getWoundDetail());
			String id = bean.getId();
			if (id != null && !id.equals("")) {
				database.update(tableName, values, "personId=? and id=?",
						new String[] { personId ,bean.getId()});
			} else {
				values.putNull("id");
				database.insert(tableName, null, values);

			}
		}
	}

	public void deletePersonDetailDatas(String tableName, String personId) {
		database.delete(tableName, "personId=?", new String[] { personId });
	}
	public void deletePersonDetailDataById( String id) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			database.delete("person_detail_data", "id=?", new String[] { id });
		} catch (Exception e1) {

			e1.printStackTrace();
		}finally{
			if(database!=null){
			database.close();
			}
		}
		
	}

	public void insertPersonDetailDatas(String tableName, String personId,
			List<PersonDetailData> personDetailDatas) {

		int size = personDetailDatas.size();
		for (int i = 0; i < size; i++) {
			PersonDetailData bean = personDetailDatas.get(i);
			bean.init();
			ContentValues values = new ContentValues();
			values.putNull("id");
			values.put("personId", personId);
			values.put("personName", bean.getPersonName());
			values.put("personSex", bean.getPersonSex());
			values.put("age", bean.getAge());
			values.put("personPayType", bean.getPersonPayType());
			values.put("treatType", bean.getTreatType());
			values.put("hospitalName", bean.getHospitalName());
			values.put("lossFee", bean.getLossFee());
			values.put("woundDetail", bean.getWoundDetail());
			database.insert(tableName, null, values);
		}
	}

	
}
