package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.CarDriverData;
import com.sinosoft.ms.model.CarLossData;
import com.sinosoft.ms.model.CarPolicyData;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：涉案车辆著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:18:33
 */

public class CarDatasDatabase {

	private SQLiteDatabase database;
	private Context context;

	public CarDatasDatabase(Context context) {
		this.context = context;
	}

	// 插入数据
	public void insertCarDatas(final String table, List<CarData> list)
			throws Exception {

		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				Log.d("carData insert", "rollback");
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
		try {
			for (int i = 0; i < list.size(); i++) {
				CarData bean = list.get(i);
				
				if (bean != null) {
					bean.init();
					ContentValues values = new ContentValues();
					values.putNull("id");
					values.put("checkCarId", bean.getCheckCarId());
					values.put("registNo", bean.getRegistNo());
					values.put("lossItemType", bean.getLossItemType());
					values.put("licenseNo", bean.getLicenseNo());
					values.put("carOwner", bean.getCarOwner());
					values.put("engineNo", bean.getEngineNo());
					values.put("vinNo", bean.getVinNo());
					values.put("frameNo", bean.getFrameNo());
					values.put("licenseType", bean.getLicenseType());
					values.put("liabilityType", bean.getLiabilityType());
					
					long id = database.insert(table, null, values);
					if (id != -1) {
						CarDriverData carDriver = bean.getCarDriverData();
						inserCarDriverDate("car_driver_data", id + "",
								carDriver);
						CarLossData carLossData = bean.getCarLossData();
						inserCarLossDate("car_loss_data", id + "", carLossData);
						List<CarPolicyData> carPolicyDatas=bean.getCarpolicyList();
						inserCarPolicyData("car_policy_data", id + "", carPolicyDatas);					
					}
				}
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	public void deleteCarData(String table, String registNo) {
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

			database.execSQL("delete from car_driver_data where carId=(select id from car_data where registNo='"
					+ registNo + "')");
			database.execSQL("delete from car_loss_data where carId=(select id from car_data where registNo='"
					+ registNo + "')");
			database.execSQL("delete from car_policy_data where carId=(select id from car_data where registNo='"
					+ registNo + "')");
			database.execSQL("delete from car_data where registNo='" + registNo
					+ "'");
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("baiscInfo detele error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}
	}
	
	public void deleteCarDataById(String registNo,String id) {
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

			database.execSQL("delete from car_driver_data where carId=(select id from car_data where registNo='"
					+ registNo + "' and id="+id+")");
			database.execSQL("delete from car_loss_data where carId=(select id from car_data where registNo='"
					+ registNo + "' and id="+id+")");
			database.execSQL("delete from car_policy_data where carId=(select id from car_data where registNo='"
					+ registNo + "' and id="+id+")");
			database.execSQL("delete from car_data where registNo='" + registNo
					+ "' and id="+id+"");
			database.setTransactionSuccessful();

		} catch (Exception e) {
			Log.d("baiscInfo detele error", "reason" + e.getMessage());

		} finally {
			database.endTransaction();
			database.close();

		}
	}

	public void updateCarData(String table, String reportNo,
			List<CarData> carDatas) {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				// TODO Auto-generated method stub
				Log.d("carData update", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("carData update", "commit");
			}

			@Override
			public void onBegin() {
				// TODO Auto-generated method stub
				Log.d("carData update", "begin");

			}
		});

		try {
			for (CarData bean : carDatas) {
				ContentValues values = new ContentValues();
				bean.init();
				values.put("checkCarId", bean.getCheckCarId());
				values.put("registNo", bean.getRegistNo());
				values.put("lossItemType", bean.getLossItemType());
				values.put("licenseNo", bean.getLicenseNo());
				values.put("carOwner", bean.getCarOwner());
				values.put("engineNo", bean.getEngineNo());
				values.put("vinNo", bean.getVinNo());
				values.put("frameNo", bean.getFrameNo());
				values.put("licenseType", bean.getLicenseType());
				values.put("liabilityType", bean.getLiabilityType());
				
				String carId = bean.getCarId();
				if(StringUtils.isEmpty(carId)){
					values.putNull("id");
					long id = database.insert(table, null, values);
					if (id != -1) {
						bean.setCarId(id+"");
						CarDriverData carDriver = bean.getCarDriverData();
                        inserCarDriverDate("car_driver_data", id + "",
								carDriver);

						CarLossData carLossData = bean.getCarLossData();
                        inserCarLossDate("car_loss_data", id + "", carLossData);
                        /*
                        List<CarPolicyData> carPolicys=bean.getCarpolicyList();
                        inserCarPolicyData("car_policy_data",id + "", carPolicys);
                        */
					}
					carId=id+"";
					
				}else{
					database.update(table, values, "registNo=? and id=?",
							new String[] { reportNo,bean.getCarId() });
					
					//List<CarPolicyData> carPolicyDatas = bean.getCarpolicyList();
					CarDriverData carDriverData = bean.getCarDriverData();
					CarLossData carLossData = bean.getCarLossData();
					//updateCarPolicyData("car_policy_data", carId, carPolicyDatas);
					updateCarDriverDate("car_driver_data", carId, carDriverData);
					updateCarLossDate("car_loss_data", carId, carLossData);
					/*
					List<CarPolicyData> carPolicys=bean.getCarpolicyList();
                    updateCarPolicyData("car_policy_data",carId, carPolicys);
					*/
				}
				//更新车辆承保信息 先删除后添加
				database.execSQL("delete from car_policy_data where carId='"+carId+"'");
				 List<CarPolicyData> carPolicys=bean.getCarpolicyList();
                 inserCarPolicyData("car_policy_data",carId + "", carPolicys);
			
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
	 * 查询涉案车辆的数据
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
	public List<CarData> selectCarData(String table, String registNo,
			String orderBy) {
		List<CarData> carDatas = new ArrayList<CarData>();
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			cursor = database.query(table, null, "registNo=?",
					new String[] { registNo }, null, null, orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				CarData bean = new CarData();
				String id = cursor.getString(cursor.getColumnIndex("id"));
				bean.setCarId(id);
				bean.setCheckCarId(cursor.getString(cursor
						.getColumnIndex("checkCarId")));
				bean.setRegistNo(cursor.getString(cursor
						.getColumnIndex("registNo")));
				bean.setLossItemType(cursor.getString(cursor
						.getColumnIndex("lossItemType")));
				bean.setLicenseNo(cursor.getString(cursor
						.getColumnIndex("licenseNo")));
				bean.setCarOwner(cursor.getString(cursor
						.getColumnIndex("carOwner")));
				bean.setEngineNo(cursor.getString(cursor
						.getColumnIndex("engineNo")));
				bean.setVinNo(cursor.getString(cursor.getColumnIndex("vinNo")));
				bean.setFrameNo(cursor.getString(cursor.getColumnIndex("frameNo")));
				bean.setLicenseType(cursor.getString(cursor
						.getColumnIndex("licenseType")));
				bean.setLiabilityType(cursor.getString(cursor
						.getColumnIndex("liabilityType")));
				try {
					CarDriverData carDriver = selectCarDriverDate(
							"car_driver_data", "" + id);
					bean.setCarDriverData(carDriver);
					
					CarLossData carLossData = selectCarLossDate(
							"car_loss_data", "" + id);
					bean.setCarLossData(carLossData);
					
					List<CarPolicyData> carPolicyDatas=selectCarPolicyData("car_policy_data", ""+id);
					bean.setCarpolicyList(carPolicyDatas);
					
				} catch (Exception e) {
					e.printStackTrace();
					Log.e("error", e.getMessage());
				}

				carDatas.add(bean);
				cursor.moveToNext();

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			// throw new Exception("数据库操作异常"+e1.getMessage());
		} finally {
			cursor.close();
			database.close();

		}

		return carDatas;
	}

	// carPolicyData
	/**
	 * 
	 * @param tableName
	 * @param checkCarId
	 */
	public List<CarPolicyData> selectCarPolicyData(String tableName,
			String carId) {
		List<CarPolicyData> carPolicyDatas = new ArrayList<CarPolicyData>();
		Cursor cursor = database.query(tableName, null, "carId=?",
				new String[] { carId }, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CarPolicyData bean = new CarPolicyData();
			bean.init();
			bean.setCarId(carId);
			String id = cursor.getString(cursor.getColumnIndex("id"));
			bean.setId(id);
			bean.setCheckCarId(cursor.getString(cursor
					.getColumnIndex("checkCarId")));
			bean.setPolicyFlag(cursor.getString(cursor
					.getColumnIndex("policyFlag")));
			bean.setOppRegistNoBI(cursor.getString(cursor
					.getColumnIndex("oppRegistNoBI")));
			bean.setOppPolicyNoBI(cursor.getString(cursor
					.getColumnIndex("oppPolicyNoBI")));
			bean.setOppInsurerCodeBI(cursor.getString(cursor
					.getColumnIndex("oppInsurerCodeBI")));
			bean.setOppInsurerAreaBI(cursor.getString(cursor
					.getColumnIndex("oppInsurerAreaBI")));
			bean.setOppRegistNoCI(cursor.getString(cursor
					.getColumnIndex("oppRegistNoCI")));
			bean.setOppPolicyNoCI(cursor.getString(cursor
					.getColumnIndex("oppPolicyNoCI")));
			bean.setOppInsurerCodeCI(cursor.getString(cursor
					.getColumnIndex("oppInsurerCodeCI")));
			bean.setOppInsurerAreaCI(cursor.getString(cursor
					.getColumnIndex("oppInsurerAreaCI")));
			bean.setOppIdBi(cursor.getString(cursor
					.getColumnIndex("oppIdBi")));
			bean.setOppIdCi(cursor.getString(cursor
					.getColumnIndex("oppIdCi")));
			carPolicyDatas.add(bean);
			cursor.moveToNext();
		}
		cursor.close();
		return carPolicyDatas;
	}

	public void updateCarPolicyData(String tableName, String carId,
			List<CarPolicyData> carPolicyDatas) {
		ContentValues values = new ContentValues();
		for (int i = 0; i < carPolicyDatas.size(); i++) {
			CarPolicyData bean = carPolicyDatas.get(i);
			bean.init();
			values.put("checkCarId", bean.getCheckCarId());
			values.put("policyFlag", bean.getPolicyFlag());
			values.put("oppRegistNoBI", bean.getOppRegistNoBI());
			values.put("oppPolicyNoBI", bean.getOppPolicyNoBI());
			values.put("oppInsurerCodeBI", bean.getOppInsurerCodeBI());
			values.put("oppInsurerAreaBI", bean.getOppInsurerAreaBI());
			values.put("oppRegistNoCI", bean.getOppRegistNoCI());
			values.put("oppPolicyNoCI", bean.getOppPolicyNoCI());
			values.put("oppInsurerCodeCI", bean.getOppInsurerCodeCI());
			values.put("oppInsurerAreaCI", bean.getOppInsurerAreaCI());
			values.put("carId", carId);
			values.put("oppIdBi", bean.getOppIdBi());
			values.put("oppIdCi", bean.getOppIdCi());
			String id=bean.getId();
			if(StringUtils.isEmpty(id)){
				values.putNull("id");
				database.insert(tableName, null, values);
			}else{
				database.update(tableName, values, "carId=? and id=?",
						new String[] { carId,bean.getId() });
			}
		}
	}

	public void deleteCarPolicyData(String tableName, String carId) {
		database.delete(tableName, "carId=?", new String[] { carId });
	}

	public void inserCarPolicyData(String tableName,String carId,
			List<CarPolicyData> carPolicyDatas) {

		ContentValues values = new ContentValues();

		for (int i = 0; i < carPolicyDatas.size(); i++) {
			CarPolicyData bean = carPolicyDatas.get(i);
			values.put("carId", carId);
			values.putNull("id");
			values.put("checkCarId", bean.getCheckCarId());
			values.put("policyFlag", bean.getPolicyFlag());
			values.put("oppRegistNoBI", bean.getOppRegistNoBI());
			values.put("oppPolicyNoBI", bean.getOppPolicyNoBI());
			values.put("oppInsurerCodeBI", bean.getOppInsurerCodeBI());
			values.put("oppInsurerAreaBI", bean.getOppInsurerAreaBI());
			values.put("oppRegistNoCI", bean.getOppRegistNoCI());
			values.put("oppPolicyNoCI", bean.getOppPolicyNoCI());
			values.put("oppInsurerCodeCI", bean.getOppInsurerCodeCI());
			values.put("oppInsurerAreaCI", bean.getOppInsurerAreaCI());
			values.put("oppIdBi", bean.getOppIdBi());
			values.put("oppIdCi", bean.getOppIdCi());
			database.insert(tableName, null, values);

		}

	}

	/**
	 * 涉案车辆 驾驶员信息carDriverData
	 * 
	 * @param tableName
	 * @param checkCarId
	 */
	public CarDriverData selectCarDriverDate(String tableName, String carId) {
		CarDriverData bean = new CarDriverData();
		Cursor cursor = database.query(tableName, null, "carId=?",
				new String[] { carId }, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id=cursor.getInt(cursor.getColumnIndex("id"));
			bean.setId(id+"");
			bean.setCarId(carId);
			bean.setCheckCarId(cursor.getString(cursor
					.getColumnIndex("checkCarId")));
			bean.setDriverName(cursor.getString(cursor
					.getColumnIndex("driverName")));
			bean.setDrivingCarType(cursor.getString(cursor
					.getColumnIndex("drivingCarType")));
			bean.setIdentifyType(cursor.getString(cursor
					.getColumnIndex("identifyType")));
			bean.setIdentifyNumber(cursor.getString(cursor
					.getColumnIndex("identifyNumber")));
			bean.setDrivingLicenseNo(cursor.getString(cursor
					.getColumnIndex("drivingLicenseNo")));
			bean.setLinkPhoneNumber(cursor.getString(cursor
					.getColumnIndex("linkPhoneNumber")));
			cursor.moveToNext();
		}
		cursor.close();
		return bean;
	}

	public void updateCarDriverDate(String tableName, String carId,
			CarDriverData bean) {
		ContentValues values = new ContentValues();

		values.put("checkCarId", bean.getCheckCarId());
		values.put("driverName", bean.getDriverName());
		values.put("drivingCarType", bean.getDrivingCarType());
		values.put("identifyType", bean.getIdentifyType());
		values.put("identifyNumber", bean.getIdentifyNumber());
		values.put("drivingLicenseNo", bean.getDrivingLicenseNo());
		values.put("linkPhoneNumber", bean.getLinkPhoneNumber());
		database.update(tableName, values, "carId=? and id=?", new String[] { carId,bean.getId()});

	}

	public void deleteCarDriverDate(String tableName, String checkCarId) {
		database.delete(tableName, "checkCarId=?", new String[] { checkCarId });
	}

	public void inserCarDriverDate(String tableName, String carId,
			CarDriverData bean) {

		ContentValues values = new ContentValues();
		values.putNull("id");
		values.put("carId", carId);
		values.put("checkCarId", bean.getCheckCarId());
		values.put("driverName", bean.getDriverName());
		values.put("drivingCarType", bean.getDrivingCarType());
		values.put("identifyType", bean.getIdentifyType());
		values.put("identifyNumber", bean.getIdentifyNumber());
		values.put("drivingLicenseNo", bean.getDrivingLicenseNo());
		values.put("linkPhoneNumber", bean.getLinkPhoneNumber());
		database.insert(tableName, null, values);

	}

	/**
	 * carLossData
	 * 
	 * @param tableName
	 * @param carId
	 */
	public CarLossData selectCarLossDate(String tableName, String carId) {
		CarLossData bean = new CarLossData();
		Cursor cursor = database.query(tableName, null, "carId=?",
				new String[] { carId }, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			bean.setCheckCarId(cursor.getString(cursor
					.getColumnIndex("checkCarId")));
			bean.setDamageFlag(cursor.getString(cursor
					.getColumnIndex("damageFlag")));
			bean.setReserveFlag(cursor.getString(cursor
					.getColumnIndex("reserveFlag")));
			bean.setSumLossFee(cursor.getString(cursor
					.getColumnIndex("sumLossFee")));
			bean.setRescueFee(cursor.getString(cursor
					.getColumnIndex("rescueFee")));
			bean.setCheckSite(cursor.getString(cursor
					.getColumnIndex("checkSite")));
			bean.setCheckDate(cursor.getString(cursor
					.getColumnIndex("checkDate")));
			bean.setDefSite(cursor.getString(cursor.getColumnIndex("defSite")));
			bean.setKindCode(cursor.getString(cursor.getColumnIndex("kindCode")));
			bean.setIndemnityDuty(cursor.getString(cursor
					.getColumnIndex("indemnityDuty")));
			bean.setIndemnityDutyRate(cursor.getString(cursor
					.getColumnIndex("indemnityDutyRate")));
			bean.setLossPart(cursor.getString(cursor.getColumnIndex("lossPart")));

			cursor.moveToNext();
		}
		cursor.close();
		return bean;
	}

	public void updateCarLossDate(String tableName, String carId,
			CarLossData bean) {
		ContentValues values = new ContentValues();
		values.put("checkCarId", bean.getCheckCarId());
		values.put("damageFlag", bean.getDamageFlag());
		values.put("reserveFlag", bean.getReserveFlag());
		values.put("sumLossFee", bean.getSumLossFee());
		values.put("rescueFee", bean.getRescueFee());
		values.put("checkSite", bean.getCheckSite());
		values.put("checkDate", bean.getCheckDate());
		values.put("defSite", bean.getDefSite());
		values.put("kindCode", bean.getKindCode());
		values.put("indemnityDuty", bean.getIndemnityDuty());
		values.put("indemnityDutyRate", bean.getIndemnityDutyRate());
		values.put("lossPart", bean.getLossPart());

		database.update(tableName, values, "carId=?", new String[] { carId });

	}

	public void deleteCarLossDate(String tableName, String checkCarId) {
		database.delete(tableName, "checkCarId=?", new String[] { checkCarId });
	}

	public void inserCarLossDate(String tableName, String carId,
			CarLossData bean) {

		ContentValues values = new ContentValues();

		values.put("carId", carId);
		values.put("checkCarId", bean.getCheckCarId());
		values.put("damageFlag", bean.getDamageFlag());
		values.put("reserveFlag", bean.getReserveFlag());
		values.put("sumLossFee", bean.getSumLossFee());
		values.put("rescueFee", bean.getRescueFee());
		values.put("checkSite", bean.getCheckSite());
		values.put("checkDate", bean.getCheckDate());
		values.put("defSite", bean.getDefSite());
		values.put("kindCode", bean.getKindCode());
		values.put("indemnityDuty", bean.getIndemnityDuty());
		values.put("indemnityDutyRate", bean.getIndemnityDutyRate());
		values.put("lossPart", bean.getLossPart());

		database.insert(tableName, null, values);

	}

}
