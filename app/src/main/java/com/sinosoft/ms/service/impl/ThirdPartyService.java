package com.sinosoft.ms.service.impl;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sinosoft.ms.model.VehicleType;
import com.sinosoft.ms.model.po.Brand;
import com.sinosoft.ms.model.po.Car;
import com.sinosoft.ms.model.po.Emus;
import com.sinosoft.ms.model.po.GeneralParty;
import com.sinosoft.ms.model.po.Manufacturer;
import com.sinosoft.ms.service.IThirdPartyService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.db.DatabaseHelper;

public class ThirdPartyService implements IThirdPartyService {
	private Activity activity = null;
	private SQLiteDatabase db = null;
	
	public ThirdPartyService() {
		if(null==activity){
			activity = ActivityManage.getInstance().peek();
		}
	}

	@Override
	public List<Manufacturer> getFactory() {
		List<Manufacturer> manufacturerList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select ID,CJMC,CJBM from ZC_QCCJXXB where scbz='0'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			manufacturerList = new LinkedList<Manufacturer>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Manufacturer manufacturer = new Manufacturer();
				manufacturer.setId(cursor.getString(cursor.getColumnIndex("ID")));
				manufacturer.setName(cursor.getString(cursor.getColumnIndex("CJMC")));
				manufacturer.setCode(cursor.getString(cursor.getColumnIndex("CJBM")));
				manufacturerList.add(manufacturer);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return manufacturerList;
	}


	@Override
	public List<Manufacturer> getFactory(String text) {
		List<Manufacturer> manufacturerList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select ID,CJMC,CJBM,CSJC,PYCX,substr(PYCX, 1, 1) as PY from ZC_QCCJXXB where scbz='0' ";
			if(StringUtils.isNotEmpty(text)){
				String regularExpression = "^[a-zA-Z]$";
				if(text.matches(regularExpression)){
					sql += " and PYCX like '"+text+"%' ";
				}else{
					sql += " and CSJC like '%"+text+"%' or CJMC like '%"+text+"%' ";
				}
			}
			sql += " order by PYCX ";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			manufacturerList = new LinkedList<Manufacturer>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Manufacturer manufacturer = new Manufacturer();
				manufacturer.setId(cursor.getString(cursor.getColumnIndex("ID")));
				manufacturer.setName(cursor.getString(cursor.getColumnIndex("CJMC")));
				manufacturer.setCode(cursor.getString(cursor.getColumnIndex("CJBM")));
				manufacturer.setZmIndex(cursor.getString(cursor.getColumnIndex("PY")));
				manufacturerList.add(manufacturer);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return manufacturerList;
	}
	
	@Override
	public List<Brand> getBandByManufacturerId(String id) {
		List<Brand> brandList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from ZC_CLPPXXB where cjid='"+id+"' and  scbz='0'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			brandList = new LinkedList<Brand>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Brand brand = new Brand();
				brand.setId(cursor.getString(cursor.getColumnIndex("ID")));
				brand.setName(cursor.getString(cursor.getColumnIndex("PPMC")));
				brand.setCode(cursor.getString(cursor.getColumnIndex("PPBM")));
				brandList.add(brand);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return brandList;
	}

	@Override
	public List<Car> getCarByBrandId(String id) {
		List<Car> carList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from ZC_CXXXB where ppid='"+id+"' and  scbz='0'";
			Log.e("sql",sql);
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			carList = new LinkedList<Car>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Car car = new Car();
				car.setId(cursor.getString(cursor.getColumnIndex("ID")));
				car.setName(cursor.getString(cursor.getColumnIndex("CXMC")));
				car.setCode(cursor.getString(cursor.getColumnIndex("PYCX")));
				carList.add(car);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return carList;
	}

	@Override
	public List<Emus> getEmusByCarId(String id) {
		List<Emus> emusList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from ZC_CLFZXXB where cxid='"+id+"' and  scbz='0'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			emusList = new LinkedList<Emus>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Emus emus = new Emus();
				emus.setId(cursor.getString(cursor.getColumnIndex("ID")));
				emus.setName(cursor.getString(cursor.getColumnIndex("ZBMC")));
				emus.setCode(cursor.getString(cursor.getColumnIndex("ZBBIANMA")));
				emusList.add(emus);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return emusList;
	}

	@Override
	public List<VehicleType> getVehicleTypeByEmusId(String id,String vehSeriName) {
		List<VehicleType> vehicleCodeList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from ZC_CLZLB where zbid='"+id+"'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			vehicleCodeList = new LinkedList<VehicleType>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				VehicleType vehicleType = new VehicleType();
				vehicleType.setTradeName(cursor.getString(cursor.getColumnIndex("CLBZMC")));// 车型名称
				vehicleType.setPrice("");// 价格(表中无)
				vehicleType.setCarName(vehSeriName);// 车系名称(表中无)
				vehicleType.setCategoryName("");// 种类名称(表中无)
				vehicleType.setDescription(cursor.getString(cursor.getColumnIndex("MSSM")));
				vehicleType.setModelName(cursor.getString(cursor.getColumnIndex("CSMC")));
				vehicleType.setModelYear(cursor.getString(cursor.getColumnIndex("NK")));
				vehicleType.setRemark(cursor.getString(cursor.getColumnIndex("BZ")));
				vehicleType.setSeat(cursor.getString(cursor.getColumnIndex("ZW")));
				vehicleType.setVolume(cursor.getString(cursor.getColumnIndex("PL")));
				vehicleType.setVehCertainCode(cursor.getString(cursor.getColumnIndex("CLBIANMA")));
				vehicleType.setVehCertainName(cursor.getString(cursor.getColumnIndex("CLBZMC")));
				
				vehicleCodeList.add(vehicleType);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return vehicleCodeList;
	}
	
	@Override
	public List<GeneralParty> getReplacementParts() {
		List<GeneralParty> generalPartyList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from PJ_ZD_ZCJGZD " +
							"where id in(select distinct fzgx from PJ_ZD_ZCJGZD) " +
							"and fzgx='0' " +
							"and scbz='0'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			generalPartyList = new LinkedList<GeneralParty>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				GeneralParty generalParty = new GeneralParty();
				generalParty.setId(cursor.getString(cursor.getColumnIndex("ID")));
				generalParty.setName(cursor.getString(cursor.getColumnIndex("BJMC")));
				generalParty.setCode(cursor.getString(cursor.getColumnIndex("ZBBM")));
				generalPartyList.add(generalParty);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return generalPartyList;
	}
	
	@Override
	public List<GeneralParty> getPartsGroupName(String id) {
		List<GeneralParty> generalPartyList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from PJ_ZD_ZCJGZD where fzgx='"+id+"'";
			Cursor cursor = db.rawQuery(sql, null);
			generalPartyList = new LinkedList<GeneralParty>();
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				GeneralParty generalParty = new GeneralParty();
				generalParty.setId(cursor.getString(cursor.getColumnIndex("ID")));
				generalParty.setName(cursor.getString(cursor.getColumnIndex("BJMC")));
				generalParty.setCode(cursor.getString(cursor.getColumnIndex("ZBBM")));
				generalPartyList.add(generalParty);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return generalPartyList;
	}
	
	@Override
	public List<GeneralParty> getChangeItemKeywords() {
		List<GeneralParty> generalPartyList = null;
		String[] keywords = new String[]{"前","后","左","右","中","上","下","杠","灯","盖","门","窗","板","壳","饰","网","镜","车","电",
				"气","器","盒","机","圈","向","钢","泵","管","带","火","罩","油","轮","桥","锁","梁","空","框","雨",
				"柱","线","玻","扇","碳","线","进","排","动","轴","表","标","口","水","轨","震","转"};
		generalPartyList = new LinkedList<GeneralParty>();
		for(String key : keywords){
			GeneralParty generalParty = new GeneralParty();
			generalParty.setName(key);
			generalPartyList.add(generalParty);
		}
		return generalPartyList;
	}
	
	@Override
	public List<GeneralParty> getRepairTypeById(String id) {
		List<GeneralParty> generalPartyList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select ID,REPAIR_GROUP_NAME,REPAIR_GROUP_NAME_PY from REPAIR_GROUP_RELATION where PARENT_ID='"+id+"'";
			
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			generalPartyList = new LinkedList<GeneralParty>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				GeneralParty generalParty = new GeneralParty();
				generalParty.setId(cursor.getString(cursor.getColumnIndex("ID")));
				generalParty.setName(cursor.getString(cursor.getColumnIndex("REPAIR_GROUP_NAME")));
				generalParty.setCode(cursor.getString(cursor.getColumnIndex("REPAIR_GROUP_NAME_PY")));
				generalPartyList.add(generalParty);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return generalPartyList;
	}
	
	@Override
	public List<GeneralParty> getAssistItem() {
		List<GeneralParty> generalPartyList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from ASSIST_STD_ITEM";
			
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			generalPartyList = new LinkedList<GeneralParty>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				GeneralParty generalParty = new GeneralParty();
				generalParty.setId(cursor.getString(cursor.getColumnIndex("ID")));
				generalParty.setName(cursor.getString(cursor.getColumnIndex("ASSIST_NAME")));
				generalParty.setCode(cursor.getString(cursor.getColumnIndex("ASSIST_NAME_PY")));
				generalPartyList.add(generalParty);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return generalPartyList;
	}
	
	

}
