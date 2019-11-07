package com.sinosoft.ms.service.impl;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.service.ILossFitInfoService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.db.DatabaseHelper;
import com.sinosoft.ms.utils.xml.IRequestXml;
import com.sinosoft.ms.utils.xml.IResponseXml;
import com.sinosoft.ms.utils.xml.RequestXml;
import com.sinosoft.ms.utils.xml.ResponseXml;
/**
 * 系统名：MobileSurvey 
 * 子系统名：定损修理数据层服务接口 实现类
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public class LossFitInfoService extends BasicHttp implements ILossFitInfoService {
	private IResponseXml responseFactoryXMLParser;
	private InputStream inputStream = null;
	private Activity activity = null;
	private SQLiteDatabase db = null;
	//private HttpUtils http = null;
	
	public LossFitInfoService() {
		if(null==activity){
			activity = ActivityManage.getInstance().peek();
		}
	}
	
	public List<LossFitInfoItem> getByString(String from,String to){
		List<LossFitInfoItem> lossFitInfoList = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from loss_fit_info_item where "+from+"='"+to+"'";
			Cursor cursor = db.rawQuery(sql, null);
			
			lossFitInfoList = new LinkedList<LossFitInfoItem>();
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				LossFitInfoItem lossFitInfoItem = new LossFitInfoItem();
				lossFitInfoItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
				lossFitInfoItem.setRegistNo(cursor.getString(cursor.getColumnIndex("regist_no")));
				lossFitInfoItem.setRegistId(cursor.getString(cursor.getColumnIndex("registId")));
				lossFitInfoItem.setJyId(cursor.getString(cursor.getColumnIndex("jyId")));
				
				lossFitInfoItem.setSerialNo(cursor.getLong(cursor.getColumnIndex("serial_no")));
				lossFitInfoItem.setOriginalId(cursor.getString(cursor.getColumnIndex("original_id")));
				lossFitInfoItem.setOriginalName(cursor.getString(cursor.getColumnIndex("original_name")));
				lossFitInfoItem.setPartId(cursor.getString(cursor.getColumnIndex("part_id")));
				lossFitInfoItem.setPartStandardCode(cursor.getString(cursor.getColumnIndex("part_standard_code")));
				lossFitInfoItem.setPartStandard(cursor.getString(cursor.getColumnIndex("part_standard")));
				lossFitInfoItem.setPartGroupCode(cursor.getString(cursor.getColumnIndex("part_group_code")));
				lossFitInfoItem.setPartGroupName(cursor.getString(cursor.getColumnIndex("part_group_name")));
				lossFitInfoItem.setLossCount(cursor.getLong(cursor.getColumnIndex("loss_count")));
				lossFitInfoItem.setLossPrice(cursor.getDouble(cursor.getColumnIndex("loss_price")));
				lossFitInfoItem.setChgRefPrice(cursor.getDouble(cursor.getColumnIndex("chg_ref_price")));
				lossFitInfoItem.setRemnant(cursor.getDouble(cursor.getColumnIndex("remnant")));
				lossFitInfoItem.setSelfConfigFlag(cursor.getString(cursor.getColumnIndex("self_config_flag")));
				lossFitInfoItem.setIfRemain(cursor.getString(cursor.getColumnIndex("if_remain")));
				lossFitInfoItem.setChgCompSetCode(cursor.getString(cursor.getColumnIndex("chg_comp_set_code")));
				lossFitInfoItem.setStatus(cursor.getString(cursor.getColumnIndex("status")));
				lossFitInfoItem.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
				lossFitInfoItem.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				lossFitInfoItem.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				lossFitInfoItem.setChgLocPrice(cursor.getDouble(cursor.getColumnIndex("chg_loc_price")));
				lossFitInfoItem.setLocPrice1(cursor.getDouble(cursor.getColumnIndex("loc_price1")));
				lossFitInfoItem.setLocPrice2(cursor.getDouble(cursor.getColumnIndex("loc_price2")));
				lossFitInfoItem.setLocPrice3(cursor.getDouble(cursor.getColumnIndex("loc_price3")));
				lossFitInfoItem.setRefPrice1(cursor.getDouble(cursor.getColumnIndex("ref_price1")));
				lossFitInfoItem.setRefPrice2(cursor.getDouble(cursor.getColumnIndex("ref_price2")));
				lossFitInfoItem.setRefPrice3(cursor.getDouble(cursor.getColumnIndex("ref_price3")));
				lossFitInfoItem.setSurveyQuotedPrice(String.valueOf(cursor.getDouble(cursor.getColumnIndex("survey_quoted_price"))));
				lossFitInfoItem.setModifyFactoryPrice(cursor.getString(cursor.getColumnIndex("modify_factory_price")));
				lossFitInfoItem.setDefineType(cursor.getString(cursor.getColumnIndex("define_type")));
				lossFitInfoList.add(lossFitInfoItem);
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
		return lossFitInfoList;
	}

	@Override
	public List<LossFitInfoItem> getByRegistNo(String taskId) throws Exception {
		return getByString("regist_no", taskId);
	}

	@Override
	public boolean update(LossFitInfoItem lossFitInfoItem) throws Exception {
		boolean result = false;
		try{
			if(null != lossFitInfoItem){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues fitInfoValues = new ContentValues();
	//			fitInfoValues.put("regist_no", lossFitInfoItem.getRegistNo());
				fitInfoValues.put("serial_no", lossFitInfoItem.getSerialNo());
				fitInfoValues.put("original_id", lossFitInfoItem.getOriginalId());
				fitInfoValues.put("original_name", lossFitInfoItem.getOriginalName());
				fitInfoValues.put("part_id", lossFitInfoItem.getPartId());
				fitInfoValues.put("part_standard_code", lossFitInfoItem.getPartStandardCode());
				fitInfoValues.put("part_standard", lossFitInfoItem.getPartStandard());
				fitInfoValues.put("part_group_code", lossFitInfoItem.getPartGroupCode());
				fitInfoValues.put("part_group_name", lossFitInfoItem.getPartGroupName());
				fitInfoValues.put("loss_count", lossFitInfoItem.getLossCount());
				fitInfoValues.put("loss_price", lossFitInfoItem.getLossPrice());
				fitInfoValues.put("chg_ref_price", lossFitInfoItem.getChgRefPrice());
				fitInfoValues.put("remnant", lossFitInfoItem.getRemnant());
				fitInfoValues.put("self_config_flag", lossFitInfoItem.getSelfConfigFlag());
				fitInfoValues.put("if_remain", lossFitInfoItem.getIfRemain());
				fitInfoValues.put("chg_comp_set_code", lossFitInfoItem.getChgCompSetCode());
				fitInfoValues.put("status", lossFitInfoItem.getStatus());
				fitInfoValues.put("remark", lossFitInfoItem.getRemark());
				fitInfoValues.put("insure_term_code", lossFitInfoItem.getInsureTermCode());
				fitInfoValues.put("insure_term", lossFitInfoItem.getInsureTerm());
				fitInfoValues.put("chg_loc_price", lossFitInfoItem.getChgLocPrice ());
				fitInfoValues.put("loc_price1", lossFitInfoItem.getLocPrice1());
				fitInfoValues.put("loc_price2", lossFitInfoItem.getLocPrice2());
				fitInfoValues.put("loc_price3", lossFitInfoItem.getLocPrice3());
				fitInfoValues.put("ref_price1", lossFitInfoItem.getRefPrice1());
				fitInfoValues.put("ref_price2", lossFitInfoItem.getRefPrice2());
				fitInfoValues.put("ref_Price3", lossFitInfoItem.getRefPrice3());
				fitInfoValues.put("survey_quoted_price", lossFitInfoItem.getSurveyQuotedPrice());
				fitInfoValues.put("modify_factory_price", lossFitInfoItem.getModifyFactoryPrice());
				db.update("loss_fit_info_item", fitInfoValues, "id=?", new String[]{String.valueOf(lossFitInfoItem.getId())});
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return result;
	}

	@Override
	public int save(LossFitInfoItem lossFitInfoItem) throws Exception {
		int result = 0;
		try{
			if(null != lossFitInfoItem){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				ContentValues fitInfoValues = new ContentValues();
				fitInfoValues.put("jyId", lossFitInfoItem.getJyId());
				fitInfoValues.put("registId", lossFitInfoItem.getRegistId());
				fitInfoValues.put("regist_no", lossFitInfoItem.getRegistNo());
				fitInfoValues.put("serial_no", lossFitInfoItem.getSerialNo());
				fitInfoValues.put("original_id", lossFitInfoItem.getOriginalId());
				fitInfoValues.put("original_name", lossFitInfoItem.getOriginalName());
				fitInfoValues.put("part_id", lossFitInfoItem.getPartId());
				fitInfoValues.put("part_standard_code", lossFitInfoItem.getPartStandardCode());
				fitInfoValues.put("part_standard", lossFitInfoItem.getPartStandard());
				fitInfoValues.put("part_group_code", lossFitInfoItem.getPartGroupCode());
				fitInfoValues.put("part_group_name", lossFitInfoItem.getPartGroupName());
				fitInfoValues.put("loss_count", lossFitInfoItem.getLossCount());
				fitInfoValues.put("loss_price", lossFitInfoItem.getLossPrice());
				fitInfoValues.put("chg_ref_price", lossFitInfoItem.getChgRefPrice());
				fitInfoValues.put("remnant", lossFitInfoItem.getRemnant());
				fitInfoValues.put("self_config_flag", lossFitInfoItem.getSelfConfigFlag());
				fitInfoValues.put("if_remain", lossFitInfoItem.getIfRemain());
				fitInfoValues.put("chg_comp_set_code", lossFitInfoItem.getChgCompSetCode());
				fitInfoValues.put("status", lossFitInfoItem.getStatus());
				fitInfoValues.put("remark", lossFitInfoItem.getRemark());
				fitInfoValues.put("insure_term_code", lossFitInfoItem.getInsureTermCode());
				fitInfoValues.put("insure_term", lossFitInfoItem.getInsureTerm());
				fitInfoValues.put("chg_loc_price", lossFitInfoItem.getChgLocPrice ());
				fitInfoValues.put("loc_price1", lossFitInfoItem.getLocPrice1());
				fitInfoValues.put("loc_price2", lossFitInfoItem.getLocPrice2());
				fitInfoValues.put("loc_price3", lossFitInfoItem.getLocPrice3());
				fitInfoValues.put("ref_price1", lossFitInfoItem.getRefPrice1());
				fitInfoValues.put("ref_price2", lossFitInfoItem.getRefPrice2());
				fitInfoValues.put("ref_Price3", lossFitInfoItem.getRefPrice3());
				fitInfoValues.put("survey_quoted_price", lossFitInfoItem.getSurveyQuotedPrice());
				fitInfoValues.put("modify_factory_price", lossFitInfoItem.getModifyFactoryPrice());
				fitInfoValues.put("define_type", lossFitInfoItem.getDefineType());
				result = (int)db.insert("loss_fit_info_item", null, fitInfoValues);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return result;
	}

	@Override
	public LossFitInfoItem getById(int lossFitInfoItemId) {
		LossFitInfoItem lossFitInfoItem = null;
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "select * from loss_fit_info_item where id="+lossFitInfoItemId;
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				lossFitInfoItem = new LossFitInfoItem();
				
				lossFitInfoItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
				lossFitInfoItem.setJyId(cursor.getString(cursor.getColumnIndex("jyId")));
				lossFitInfoItem.setRegistNo(cursor.getString(cursor.getColumnIndex("regist_no")));
				lossFitInfoItem.setSerialNo(cursor.getLong(cursor.getColumnIndex("serial_no")));
				lossFitInfoItem.setOriginalId(cursor.getString(cursor.getColumnIndex("original_id")));
				lossFitInfoItem.setOriginalName(cursor.getString(cursor.getColumnIndex("original_name")));
				lossFitInfoItem.setPartId(cursor.getString(cursor.getColumnIndex("part_id")));
				lossFitInfoItem.setPartStandardCode(cursor.getString(cursor.getColumnIndex("part_standard_code")));
				lossFitInfoItem.setPartStandard(cursor.getString(cursor.getColumnIndex("part_standard")));
				lossFitInfoItem.setPartGroupCode(cursor.getString(cursor.getColumnIndex("part_group_code")));
				lossFitInfoItem.setPartGroupName(cursor.getString(cursor.getColumnIndex("part_group_name")));
				lossFitInfoItem.setLossCount(cursor.getLong(cursor.getColumnIndex("loss_count")));
				lossFitInfoItem.setLossPrice(cursor.getDouble(cursor.getColumnIndex("loss_price")));
				lossFitInfoItem.setChgRefPrice(cursor.getDouble(cursor.getColumnIndex("chg_ref_price")));
				lossFitInfoItem.setRemnant(cursor.getDouble(cursor.getColumnIndex("remnant")));
				lossFitInfoItem.setSelfConfigFlag(cursor.getString(cursor.getColumnIndex("self_config_flag")));
				lossFitInfoItem.setIfRemain(cursor.getString(cursor.getColumnIndex("if_remain")));
				lossFitInfoItem.setChgCompSetCode(cursor.getString(cursor.getColumnIndex("chg_comp_set_code")));
				lossFitInfoItem.setStatus(cursor.getString(cursor.getColumnIndex("status")));
				lossFitInfoItem.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
				lossFitInfoItem.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				lossFitInfoItem.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				lossFitInfoItem.setChgLocPrice(cursor.getDouble(cursor.getColumnIndex("chg_loc_price")));
				lossFitInfoItem.setLocPrice1(cursor.getDouble(cursor.getColumnIndex("loc_price1")));
				lossFitInfoItem.setLocPrice2(cursor.getDouble(cursor.getColumnIndex("loc_price2")));
				lossFitInfoItem.setLocPrice3(cursor.getDouble(cursor.getColumnIndex("loc_price3")));
				lossFitInfoItem.setRefPrice1(cursor.getDouble(cursor.getColumnIndex("ref_price1")));
				lossFitInfoItem.setRefPrice2(cursor.getDouble(cursor.getColumnIndex("ref_price2")));
				lossFitInfoItem.setRefPrice3(cursor.getDouble(cursor.getColumnIndex("ref_price3")));
				lossFitInfoItem.setSurveyQuotedPrice(cursor.getString(cursor.getColumnIndex("survey_quoted_price")));
				lossFitInfoItem.setModifyFactoryPrice(cursor.getString(cursor.getColumnIndex("modify_factory_price")));
				lossFitInfoItem.setDefineType(cursor.getString(cursor.getColumnIndex("define_type")));
				
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
		return lossFitInfoItem;
	}

	@Override
	public List<LossFitInfoItem> getFitList(String requestUser,int type, String carGroupId,
			String areaId, String typeName) throws Exception {
		List<LossFitInfoItem> lossFitList = null;
		IRequestXml requestXmlFactory = null;
		try{
			requestXmlFactory = new RequestXml();
			String xml = requestXmlFactory.lossChangeXML(requestUser, type, carGroupId, areaId, typeName);
			sendRequest(xml);
			responseFactoryXMLParser = new ResponseXml();
			lossFitList = responseFactoryXMLParser.changeQueryParse(inputStream);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			responseFactoryXMLParser = null;
			requestXmlFactory = null;
			
			if(null!=inputStream){
				inputStream.close();
				inputStream = null;
			}
		}
		return lossFitList;
	}
	
	
	@Override
	public boolean delete(int Id) throws Exception {
		boolean result = false;
		try{
			if(0 != Id){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				int id = db.delete("loss_fit_info_item","id=?", new String[]{String.valueOf(Id)});
				if(0 != id){
					result = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return result;
	}
	
	@Override
	public boolean deleteByJyId(String jyId) throws Exception {
		boolean result = false;
		try{
			if(null != jyId){
				db = DatabaseHelper.getHelper(activity).openDatabase();
				int id = db.delete("loss_fit_info_item","jyId=?", new String[]{jyId});
				if(0 != id){
					result = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return result;
	}

	@Override
	public void updatePlan(String taskId, String chgCompSetCode) {
		try {
			db = DatabaseHelper.getHelper(activity).openDatabase();
			String sql = "update loss_fit_info_item set chg_comp_set_code = '"
					+ chgCompSetCode + "' where regist_no='"
					+ taskId + "'";
			db.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

}
