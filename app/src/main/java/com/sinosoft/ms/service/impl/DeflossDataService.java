package com.sinosoft.ms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.service.IDeflossDataService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.LogFatory;
import com.sinosoft.ms.utils.db.DatabaseHelper;
import com.sinosoft.ms.utils.db.PolicyDatasDatabase;
/**
 * 系统名：MobileSurvey 
 * 子系统名：定损数据层服务接口 实现类
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public class DeflossDataService implements IDeflossDataService {
	private Activity activity = null;
	private SQLiteDatabase db = null;
	private Context context = null;
	
	public DeflossDataService() {
		if(null==activity){
			activity =ActivityManage.getInstance().peek();
		}
		alterHandlerName();
	}
	
	public DeflossDataService(Context context){
		this.context = context;
		alterHandlerName();
	}

	@Override
	public DeflossData getByRegistNo(String registNo) throws Exception {
		DeflossData deflossData = null;
		try {
			if(context == null){
				db = DatabaseHelper.getHelper(activity).openDatabase();
			}else{
				db = DatabaseHelper.getHelper(context).openDatabase();
			}
			String sql = "select * from defloss_data where regist_no='"+registNo+"'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				deflossData = new DeflossData();
				deflossData.setId(cursor.getLong(cursor.getColumnIndex("id")));
				deflossData.setRegistId(cursor.getString(cursor.getColumnIndex("registId")));
				deflossData.setRegistNo(cursor.getString(cursor.getColumnIndex("regist_no")));
				deflossData.setTaskId(cursor.getString(cursor.getColumnIndex("task_id")));
				deflossData.setSubrogationFlag(cursor.getString(cursor.getColumnIndex("subrogation_flag")));
				deflossData.setClaimSignFlag(cursor.getString(cursor.getColumnIndex("claim_sign_flag")));
				deflossData.setCetainLossType(cursor.getString(cursor.getColumnIndex("cetain_loss_type")));
				deflossData.setRepairFactoryCode(cursor.getString(cursor.getColumnIndex("repair_factory_code")));
				deflossData.setRepairFactoryType(cursor.getString(cursor.getColumnIndex("repair_factory_type")));
				deflossData.setRepairFactoryName(cursor.getString(cursor.getColumnIndex("repair_factory_name")));
				
				deflossData.setDefLossDate(cursor.getString(cursor.getColumnIndex("def_loss_date")));
				deflossData.setDefSite(cursor.getString(cursor.getColumnIndex("def_site")));
				deflossData.setSendDate(cursor.getString(cursor.getColumnIndex("send_date")));
				deflossData.setLossLevel(cursor.getString(cursor.getColumnIndex("loss_level")));
				deflossData.setHandlerCode(cursor.getString(cursor.getColumnIndex("handler_code")));
				deflossData.setHandlerName(cursor.getString(cursor.getColumnIndex("handler_name")));
				deflossData.setEstimatedDate(cursor.getString(cursor.getColumnIndex("estimated_date")));
				deflossData.setCaseFlag(cursor.getString(cursor.getColumnIndex("case_flag")));
				deflossData.setLossPart(cursor.getString(cursor.getColumnIndex("loss_part")));
				deflossData.setDamagePurchasePrice(cursor.getString(cursor.getColumnIndex("damage_purchase_price")));
				deflossData.setIntermediaryFlag(cursor.getString(cursor.getColumnIndex("intermediary_flag")));
				deflossData.setIntermediaryCode(cursor.getString(cursor.getColumnIndex("intermediary_code")));
				deflossData.setIntermediaryName(cursor.getString(cursor.getColumnIndex("intermediary_name")));
				//2013-04-17 新增
				deflossData.setEnabledSubrPlatform(cursor.getString(cursor.getColumnIndex("enabled_subr_platform")));
				deflossData.setIsKindCodeA(cursor.getString(cursor.getColumnIndex("is_kind_codea")));
				deflossData.setIsUnilateralAccident(cursor.getString(cursor.getColumnIndex("is_unilateral_accident")));
				
				deflossData.setDamageDate(cursor.getString(cursor.getColumnIndex("damage_date")));
				deflossData.setOption(cursor.getString(cursor.getColumnIndex("option")));
				deflossData.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				deflossData.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				deflossData.setQuickClaimFlag(cursor.getString(cursor.getColumnIndex("quick_claim_flag")));
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
		return deflossData;
	}

	@Override
	public boolean update(DeflossData deflossData) throws Exception {
		boolean result = false;
		try {
			if(null != deflossData){
				if(context == null){
					db = DatabaseHelper.getHelper(activity).openDatabase();
				}else{
					db = DatabaseHelper.getHelper(context).openDatabase();
				}
				ContentValues newValues = new ContentValues();
	//			newValues.put("regist_no", deflossData.getRegistNo());
	//			newValues.put("task_id", deflossData.getTaskId());
				
				newValues.put("subrogation_flag", deflossData.getSubrogationFlag());
				newValues.put("claim_sign_flag", deflossData.getClaimSignFlag());
				newValues.put("cetain_loss_type", deflossData.getCetainLossType());
				newValues.put("repair_factory_code", deflossData.getRepairFactoryCode());
				newValues.put("repair_factory_type", deflossData.getRepairFactoryType());
				newValues.put("repair_factory_name", deflossData.getRepairFactoryName());
				newValues.put("def_loss_date", deflossData.getDefLossDate());
				newValues.put("def_site", deflossData.getDefSite());
				newValues.put("send_date", deflossData.getSendDate());
				newValues.put("loss_level", deflossData.getLossLevel());
	//			newValues.put("handler_code", deflossData.getHandlerCode());
				newValues.put("estimated_date", deflossData.getEstimatedDate());
				newValues.put("case_flag", deflossData.getCaseFlag());
				newValues.put("loss_part", deflossData.getLossPart());
				newValues.put("damage_purchase_price", deflossData.getDamagePurchasePrice());
				newValues.put("intermediary_flag", deflossData.getIntermediaryFlag());
				newValues.put("intermediary_code", deflossData.getIntermediaryCode());
				newValues.put("intermediary_name", deflossData.getIntermediaryName());
				newValues.put("option", deflossData.getOption());

	//			newValues.put("damage_date", deflossData.getDamageDate());
	//			String time = (String) DateFormat.format("yyyy-MM-dd kk:mm:ss",
	//					new Date());
	//			newValues.put("create_data", time);
	//			newValues.put("create_owner", deflossData.getHandlerCode());
				newValues.put("insure_term_code", deflossData.getInsureTermCode());
				newValues.put("insure_term", deflossData.getInsureTerm());
				newValues.put("quick_claim_flag", deflossData.getQuickClaimFlag());
				db.update("defloss_data", newValues, "task_id=?", new String[]{deflossData.getTaskId()});
				db.close();
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
	public int save(DeflossData deflossData) throws Exception {
		int result = 0;
		try{
			if(null != deflossData){
				if(context == null){
					db = DatabaseHelper.getHelper(activity).openDatabase();
				}else{
					db = DatabaseHelper.getHelper(context).openDatabase();
				}
				ContentValues newValues = new ContentValues();
				deflossData.init();
				newValues.put("regist_no", deflossData.getRegistNo());
				newValues.put("registId", deflossData.getRegistId());
				newValues.put("task_id", deflossData.getTaskId());
				newValues.put("subrogation_flag", deflossData.getSubrogationFlag());
				newValues.put("claim_sign_flag", deflossData.getClaimSignFlag());
				newValues.put("cetain_loss_type", deflossData.getCetainLossType());
				newValues.put("repair_factory_code", deflossData.getRepairFactoryCode());
				newValues.put("repair_factory_type", deflossData.getRepairFactoryType());
				newValues.put("repair_factory_name", deflossData.getRepairFactoryName());
				newValues.put("def_loss_date", deflossData.getDefLossDate());
				newValues.put("def_site", deflossData.getDefSite());
				newValues.put("send_date", deflossData.getSendDate());
				newValues.put("loss_level", deflossData.getLossLevel());
				newValues.put("handler_code", deflossData.getHandlerCode());
				newValues.put("handler_name", deflossData.getHandlerName());
				newValues.put("estimated_date", deflossData.getEstimatedDate());
				newValues.put("case_flag", deflossData.getCaseFlag());
				newValues.put("loss_part", deflossData.getLossPart());
				newValues.put("damage_purchase_price", deflossData.getDamagePurchasePrice());
				newValues.put("intermediary_flag", deflossData.getIntermediaryFlag());
				newValues.put("intermediary_code", deflossData.getIntermediaryCode());
				newValues.put("intermediary_name", deflossData.getIntermediaryName());
				
				newValues.put("enabled_subr_platform", deflossData.getEnabledSubrPlatform());
				newValues.put("is_kind_codea", deflossData.getIsKindCodeA());
				newValues.put("is_unilateral_accident", deflossData.getIsUnilateralAccident());
				
				newValues.put("damage_date", deflossData.getDamageDate());
				String time = (String) DateFormat.format("yyyy-MM-dd kk:mm:ss",
						new Date());
				newValues.put("create_data", time);
				newValues.put("option", deflossData.getOption());
				
				newValues.put("create_owner", deflossData.getHandlerCode());
				newValues.put("insure_term_code", deflossData.getInsureTermCode());
				newValues.put("insure_term", deflossData.getInsureTerm());
				newValues.put("quick_claim_flag", deflossData.getQuickClaimFlag());
				
				//2014-05-28 新增
				newValues.put("dispatch_type", deflossData.getDispatchType());
				
				result = (int)db.insert("defloss_data", null, newValues);
				db.close();
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
	
	public void alterHandlerName(){
		try {
			if(context == null){
				db = DatabaseHelper.getHelper(activity).openDatabase();
			}else{
				db = DatabaseHelper.getHelper(context).openDatabase();
			}
			db.execSQL("alter table defloss_data add handler_name varchar2(20)");
		} catch (Exception e) {
			
		} finally{
			db.close();
		}
	}


	public DeflossData getByTaskId(String taskId) throws Exception {
		DeflossData deflossData = null;
		try {
			if(context == null){
				db = DatabaseHelper.getHelper(activity).openDatabase();
			}else{
				db = DatabaseHelper.getHelper(context).openDatabase();
			}
			String sql = "select * from defloss_data where task_id='"+taskId+"'";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				cursor.close();
				throw new Exception("未找到匹配数据");
			}
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				deflossData = new DeflossData();
				deflossData.setId(cursor.getLong(cursor.getColumnIndex("id")));
				deflossData.setRegistId(cursor.getString(cursor.getColumnIndex("registId")));
				deflossData.setRegistNo(cursor.getString(cursor.getColumnIndex("regist_no")));
				deflossData.setTaskId(cursor.getString(cursor.getColumnIndex("task_id")));
				deflossData.setSubrogationFlag(cursor.getString(cursor.getColumnIndex("subrogation_flag")));
				deflossData.setClaimSignFlag(cursor.getString(cursor.getColumnIndex("claim_sign_flag")));
				deflossData.setCetainLossType(cursor.getString(cursor.getColumnIndex("cetain_loss_type")));
				deflossData.setRepairFactoryCode(cursor.getString(cursor.getColumnIndex("repair_factory_code")));
				deflossData.setRepairFactoryType(cursor.getString(cursor.getColumnIndex("repair_factory_type")));
				deflossData.setRepairFactoryName(cursor.getString(cursor.getColumnIndex("repair_factory_name")));
			
				deflossData.setDefLossDate(cursor.getString(cursor.getColumnIndex("def_loss_date")));
				deflossData.setDefSite(cursor.getString(cursor.getColumnIndex("def_site")));
				deflossData.setSendDate(cursor.getString(cursor.getColumnIndex("send_date")));
				deflossData.setLossLevel(cursor.getString(cursor.getColumnIndex("loss_level")));
				deflossData.setHandlerCode(cursor.getString(cursor.getColumnIndex("handler_code")));
				deflossData.setHandlerName(cursor.getString(cursor.getColumnIndex("handler_name")));
				deflossData.setEstimatedDate(cursor.getString(cursor.getColumnIndex("estimated_date")));
				deflossData.setCaseFlag(cursor.getString(cursor.getColumnIndex("case_flag")));
				deflossData.setLossPart(cursor.getString(cursor.getColumnIndex("loss_part")));
				deflossData.setDamagePurchasePrice(cursor.getString(cursor.getColumnIndex("damage_purchase_price")));
				deflossData.setIntermediaryFlag(cursor.getString(cursor.getColumnIndex("intermediary_flag")));
				deflossData.setIntermediaryCode(cursor.getString(cursor.getColumnIndex("intermediary_code")));
				deflossData.setIntermediaryName(cursor.getString(cursor.getColumnIndex("intermediary_name")));
				
				//2013-04-17 新增
				deflossData.setEnabledSubrPlatform(cursor.getString(cursor.getColumnIndex("enabled_subr_platform")));
				deflossData.setIsKindCodeA(cursor.getString(cursor.getColumnIndex("is_kind_codea")));
				deflossData.setIsUnilateralAccident(cursor.getString(cursor.getColumnIndex("is_unilateral_accident")));
				
				deflossData.setDamageDate(cursor.getString(cursor.getColumnIndex("damage_date")));
				deflossData.setOption(cursor.getString(cursor.getColumnIndex("option")));
				deflossData.setInsureTermCode(cursor.getString(cursor.getColumnIndex("insure_term_code")));
				deflossData.setInsureTerm(cursor.getString(cursor.getColumnIndex("insure_term")));
				deflossData.setQuickClaimFlag(cursor.getString(cursor.getColumnIndex("quick_claim_flag")));
				//2014.05-28 新增
				deflossData.setDispatchType(cursor.getString(cursor.getColumnIndex("dispatch_type")));
				cursor.moveToNext();
				
				break;
			}
			cursor.close();
			
			
			// 获取保单信息
			List<PolicyData> policyDatas = new ArrayList<PolicyData>();
			PolicyDatasDatabase policyDatabase = new PolicyDatasDatabase(activity);
			policyDatas = policyDatabase.selectPolicyData("policy_data",
					deflossData.getRegistNo());// 保单信息
			
			// 保存保单信息
			deflossData.setPolicyDatas(policyDatas) ;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return deflossData;
	}

	@Override
	public void updateInsureCode(DeflossData deflossData) {
		try {
			if(context == null){
				db = DatabaseHelper.getHelper(activity).openDatabase();
			}else{
				db = DatabaseHelper.getHelper(context).openDatabase();
			}
			String sql = "update loss_assist_info_item set insure_term = '"
					+ deflossData.getInsureTerm() + "',insure_term_code = '"
					+ deflossData.getInsureTermCode() + "' where regist_no='"
					+ deflossData.getTaskId() + "'";
			db.execSQL(sql);
			sql = "update loss_fit_info_item set insure_term = '"
					+ deflossData.getInsureTerm() + "',insure_term_code = '"
					+ deflossData.getInsureTermCode() + "' where regist_no='"
					+ deflossData.getTaskId() + "'";
			db.execSQL(sql);
			sql = "update loss_repair_info_item set insure_term = '"
					+ deflossData.getInsureTerm() + "',insure_term_code = '"
					+ deflossData.getInsureTermCode() + "' where regist_no='"
					+ deflossData.getTaskId() + "'";
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
