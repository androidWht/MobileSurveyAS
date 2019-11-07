package com.sinosoft.ms.activity.task.loss.change;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.KindCodeData;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.service.IDeflossDataService;
import com.sinosoft.ms.service.IKindCodeDataService;
import com.sinosoft.ms.service.ILossFitInfoService;
import com.sinosoft.ms.service.impl.DeflossDataService;
import com.sinosoft.ms.service.impl.KindCodeDataService;
import com.sinosoft.ms.service.impl.LossFitInfoService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.SearchWather;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;

/**
 * 换件 自定义 Activity
 * @author Administrator
 *
 */
public class ChangeProjectdefineAct extends Activity implements OnClickListener,OnItemSelectedListener{
	private Map<String,Map<String,String>> localItemConf = null;
	private IDeflossDataService deflossDataService;
	private ILossFitInfoService lossFitInfoService;
	private IKindCodeDataService kindCodeDataService;
	private LossFitInfoItem lossFitInfoItem;
	private List<KindCodeData> kindCodeList = null;
	private InputMethodManager imm;
	
	private EditText originalNameTxt;
	private EditText originalIdTxt;
	private EditText partStandardTxt;
	private EditText partGroupNameTxt;
	private EditText lossCountExt;
	private EditText remnantExt;
	private Spinner ifRemainSp;
	private TextView chgCompSetCodeTxt;
	private EditText remarkExt;
	private EditText insureTermEdit;
	private EditText pricePlanEdit;		//价格方案
	private EditText locPrice1Ext;
	private EditText refPrice1Txt;
	private EditText surveyQuotedPriceExt;
	private TextView modifyFactoryPriceExt;
	private EditText partNameTxt;
	private Button changeItemModfiySaveBtn;
	private Button changeItemModfiyBackBtn;
	
	private boolean isUpdate = false;
	private String registNo;
	private String taskId;
	private String originalId;
	private String originalName;
	private String partStandard;
	private String partGroupName;
	private long lossCount;
	private double remnant;
	private String ifRemain;
	private String chgCompSetCode;
	private String remark;
	private String insureTerm;
	private double locPrice1;
	private double refPrice1;
	private double surveyQuotedPrice;
	private double modifyFactoryPrice;
	private int lossFitInfoItemId;
	
	private SharedPreferences repairFactoryTypeSP;
	private SharedPreferences insureTermSP;

	private Dialog dialog;

	//private String chgCompSetCode;
	private Map<String,String> insureTermCodeMap=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loss_change_item_define);
		ActivityManage.getInstance().push(this) ;
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		Intent intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		taskId = intent.getStringExtra("taskId");
		insureTermSP = getSharedPreferences("insureTerm", 0);
		repairFactoryTypeSP = getSharedPreferences("repairFactoryType", 0);
		
		lossFitInfoItemId = intent.getIntExtra("lossFitInfoItemId", -1);
		if(registNo==null){
			registNo="";
		}
		if(taskId==null){
			taskId="";
		}
		
		// 设置视图控件
		setViewControl();

		loadData();
		ifRemainSp.setOnItemSelectedListener(this);
		changeItemModfiySaveBtn.setOnClickListener(this);
		changeItemModfiyBackBtn.setOnClickListener(this);
	}
	
	private void setViewControl() {
		partStandardTxt = (EditText) findViewById(R.id.partStandardExt);
		partGroupNameTxt = (EditText) findViewById(R.id.partGroupNameExt);
		originalNameTxt = (EditText) findViewById(R.id.originalNameExt); 
		originalIdTxt = (EditText) findViewById(R.id.originalIdExt); 
		lossCountExt = (EditText) findViewById(R.id.lossCountExt);
		remnantExt = (EditText) findViewById(R.id.remnantExt);
		ifRemainSp = (Spinner) findViewById(R.id.ifRemainSp);
		chgCompSetCodeTxt = (TextView) findViewById(R.id.chgCompSetCodeTxt);
		remarkExt = (EditText) findViewById(R.id.remarkExt);
		insureTermEdit = (EditText) findViewById(R.id.insureTermEdit);
		locPrice1Ext = (EditText) findViewById(R.id.locPrice1Ext);
		refPrice1Txt = (EditText) findViewById(R.id.refPrice1Ext);
		surveyQuotedPriceExt = (EditText) findViewById(R.id.surveyQuotedPriceExt);
		modifyFactoryPriceExt = (TextView) findViewById(R.id.modifyFactoryPriceExt);
		partNameTxt = (EditText) findViewById(R.id.partNameExt);
		pricePlanEdit = (EditText) findViewById(R.id.pricePlanEdit);
		changeItemModfiySaveBtn = (Button) findViewById(R.id.changeItemModfiySaveBtn);
		changeItemModfiyBackBtn = (Button) findViewById(R.id.changeItemModfiyBackBtn);
	}
	
	private void loadData() {
		try{
			if(null==lossFitInfoService){
				lossFitInfoService = new LossFitInfoService();
			}
			localItemConf = AppConstants.getLocalItemConf();
			lossFitInfoItem = lossFitInfoService.getById(lossFitInfoItemId);
			if(null!=lossFitInfoItem){
				isUpdate = true;
				partStandardTxt.setText(lossFitInfoItem.getPartStandard());
				partGroupNameTxt.setText(lossFitInfoItem.getPartGroupName());
				originalNameTxt.setText(lossFitInfoItem.getOriginalName());
				originalIdTxt.setText(lossFitInfoItem.getOriginalId());
				lossCountExt.setText(String.valueOf(lossFitInfoItem.getLossCount()));
				remnantExt.setText(String.valueOf(lossFitInfoItem.getRemnant()));
				remarkExt.setText(lossFitInfoItem.getRemark());
				locPrice1Ext.setText(String.valueOf(lossFitInfoItem.getLocPrice1()));
				refPrice1Txt.setText(String.valueOf(lossFitInfoItem.getRefPrice1()));
				surveyQuotedPriceExt.setText(lossFitInfoItem.getSurveyQuotedPrice());
				modifyFactoryPriceExt.setText(lossFitInfoItem.getModifyFactoryPrice());
				partNameTxt.setText(lossFitInfoItem.getPartStandard());
				chgCompSetCodeTxt.setText(SpinnerUtils.getValue(lossFitInfoItem.getChgCompSetCode(),localItemConf.get("ChgCompSetCode")));
				chgCompSetCode=lossFitInfoItem.getChgCompSetCode();
			}else{
				lossFitInfoItem=new LossFitInfoItem();
				lossFitInfoItem.init();
				
				//换件损余默认设置为否，残值默认为0
				lossFitInfoItem.setIfRemain("0");
				lossFitInfoItem.setRemnant(0);
				remnantExt.setText(String.valueOf(lossFitInfoItem.getRemnant()));
				chgCompSetCode = repairFactoryTypeSP.getString("key", "");
				if("01".equals(chgCompSetCode)){
					chgCompSetCode = "001";
				}else if("02".equals(chgCompSetCode)){
					chgCompSetCode = "002";
				}else if("03".equals(chgCompSetCode)){
					chgCompSetCode = "003";
				}
				lossFitInfoItem.setChgCompSetCode(chgCompSetCode);
			}
				//下拉框设置值
			SpinnerUtils.setSpinnerData(this, ifRemainSp, localItemConf.get("Status"), lossFitInfoItem.getIfRemain()+"","");
			pricePlanEdit.setText(SpinnerUtils.getValue(lossFitInfoItem.getChgCompSetCode(),localItemConf.get("ChgCompSetCode")));
//			pricePlanEdit.setText(repairFactoryTypeSP.getString("value", ""));
//			pricePlanId = "";
//			SpinnerUtils.setSpinnerData(this, pricePlanSp, localItemConf.get("ChgCompSetCode"), lossFitInfoItem.getChgCompSetCode()+"");
			if("1".equals(lossFitInfoItem.getIfRemain())){//"是否损余"选择"是"时，残值只能为0
				remnantExt.setVisibility(View.GONE);
			}
			if(null==deflossDataService){
				deflossDataService = new DeflossDataService();
			}
			DeflossData deflossData = deflossDataService.getByTaskId(taskId);
			if(null==kindCodeDataService){
				kindCodeDataService = new KindCodeDataService();
			}
			
			kindCodeList = kindCodeDataService.getById(deflossData.getId());
			insureTermCodeMap=new HashMap<String, String>();
			if(null!=kindCodeList && !kindCodeList.isEmpty()){
				Iterator iter = kindCodeList.iterator();
				
				while(iter.hasNext()){
					KindCodeData kindCodeData = (KindCodeData)iter.next();
					insureTermCodeMap.put(kindCodeData.getInsureTermCode(),kindCodeData.getInsureTerm());
				}
				
			}
			if(insureTermCodeMap.isEmpty()){
				insureTermCodeMap.put("02", "商业第三者责任险");
				insureTermCodeMap.put("BZ", "机动车交通事故责任强制险");
			}
			
			insureTermEdit.setText(insureTermSP.getString("value", ""));
//			SpinnerUtils.setSpinnerData(this, insureTermSp, insureTermCodeMap, lossFitInfoItem.getInsureTermCode()+"");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 事件处理
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.changeItemModfiySaveBtn:
			try {
				originalName = originalNameTxt.getText().toString();
				originalId = originalIdTxt.getText().toString();
				partStandard = partStandardTxt.getText().toString();
			    partGroupName = partGroupNameTxt.getText().toString();
				
				
				if (StringUtils.isNotEmpty(remnantExt.getText().toString())) {//
					remnant = Double.parseDouble(remnantExt.getText().toString());
				}
				if (StringUtils.isNotEmpty(ifRemainSp.getSelectedItem().toString())) {
					ifRemain =SpinnerUtils.getKey(ifRemainSp.getSelectedItem().toString(),localItemConf.get("Status"));
				}
//				if ("1".equals(ifRemain)){
//					remark = "";
//				}else{
					if(StringUtils.isNotEmpty(remarkExt.getText().toString())) {
						remark = remarkExt.getText().toString();
					}
//				}
//				if (StringUtils.isNotEmpty(chgCompSetCodeTxt.getText().toString())) {
//					chgCompSetCode = chgCompSetCodeTxt.getText().toString();
//				}
//				if (null!=insureTermSp.getSelectedItem()) {
//					insureTerm = insureTermSp.getSelectedItem().toString();
//				}
				insureTerm = insureTermSP.getString("value", "");
//				chgCompSetCode = repairFactoryTypeSP.getString("key", "");
//				chgCompSetCode = SpinnerUtils.getKey(pricePlanSp.getSelectedItem().toString(), localItemConf.get("ChgCompSetCode"));
				lossCount = StringUtils.toLong(lossCountExt.getText().toString());
				//数据校验
				if(checkInputData()){
					//暂存信息
					lossFitInfoItem.setRegistNo(taskId);
					lossFitInfoItem.setOriginalName(partNameTxt.getText().toString());
					lossFitInfoItem.setOriginalId("000000");
					lossFitInfoItem.setPartId("CUSTOM");
					lossFitInfoItem.setPartStandard(partNameTxt.getText().toString());
					lossFitInfoItem.setPartStandardCode("000000");
					lossFitInfoItem.setPartGroupName(partGroupName);
					lossFitInfoItem.setLossCount(lossCount);
					lossFitInfoItem.setRemnant(remnant);
					lossFitInfoItem.setIfRemain(ifRemain);
					lossFitInfoItem.setChgCompSetCode(chgCompSetCode);
					lossFitInfoItem.setRemark(remark);
					lossFitInfoItem.setInsureTermCode(insureTermSP.getString("key", ""));
//					lossFitInfoItem.setInsureTermCode(SpinnerUtils.getKey(insureTerm, insureTermCodeMap));
					lossFitInfoItem.setInsureTerm(insureTerm);
					lossFitInfoItem.setLossPrice(surveyQuotedPrice);
					lossFitInfoItem.setLocPrice1(locPrice1);
					lossFitInfoItem.setRefPrice1(refPrice1);
					lossFitInfoItem.setSurveyQuotedPrice(String.valueOf(surveyQuotedPrice));
					lossFitInfoItem.setModifyFactoryPrice(String.valueOf(modifyFactoryPrice));
					// 添加标志
					lossFitInfoItem.setSelfConfigFlag("1") ;
					if(!isUpdate){
						lossFitInfoItem.setDefineType("1");
						if(lossFitInfoService.save(lossFitInfoItem)==0){
							throw new IllegalArgumentException("暂存失暂败");
						}
					}else{
						if(!lossFitInfoService.update(lossFitInfoItem)){
							throw new IllegalArgumentException("暂存失暂败");
						}
					}
					if(dialog!=null){
						dialog.dismiss();
					}
					dialog = CustomDialog.show(ChangeProjectdefineAct.this, "信息提示",
							"保存成功!", "确定", "", new OnClickListener() {
								@Override
								public void onClick(View v) {
									try {
										if (dialog != null) {
											dialog.dismiss();
										}
										ChangeProjectdefineAct.this.finish();
										ActivityManage.getInstance().pop() ;
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}, null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ToastDialog.show(this, ""+e.getMessage(),ToastDialog.ERROR);
			}
			break;
		case R.id.changeItemModfiyBackBtn:
			try{
				this.finish();
				ActivityManage.getInstance().pop();
			}catch(Exception e){
				
			}
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {
		case R.id.ifRemainSp:
			String temp = ifRemainSp.getSelectedItem().toString();
			if("是".equals(temp)){
				remnantExt.setVisibility(View.GONE);
				remnantExt.setText("0");
			}else{
				remnantExt.setVisibility(View.VISIBLE);
			}
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	
	}
	
	/**
	 * 校验数据
	 * 
	 * @return
	 * @throws Exception 
	 */
	private boolean checkInputData() throws Exception {
		boolean result = false;
		try{
			String temp = lossCountExt.getText().toString();
			if (StringUtils.isEmpty(temp)) {
				throw new IllegalArgumentException("换件数量不能为空");
			}else if("0".equals(temp)){
				throw new IllegalArgumentException("换件数量不能为0");
			}
			
			temp = locPrice1Ext.getText().toString();
			if (StringUtils.isNotEmpty(temp)) {
				locPrice1 = StringUtils.toDouble(temp);
			}
			
			temp = refPrice1Txt.getText().toString();
			if (StringUtils.isNotEmpty(temp)) {
				refPrice1 = StringUtils.toDouble(temp);
			}
			
			temp = surveyQuotedPriceExt.getText().toString();
			if (StringUtils.isEmpty(temp)) {
				throw new IllegalArgumentException("查勘报价不能为空");
			}else if("0".equals(temp)){
				throw new IllegalArgumentException("查勘报价不能为0");
			}else if(!SearchWather.checkNUMBER4(temp)){
				throw new IllegalArgumentException("查勘报价不能大于7位整数");
			}else{
				surveyQuotedPrice = StringUtils.toDouble(temp);
			}
			
			//查勘总价
			int num1 = Integer.parseInt(lossCountExt.getText().toString());
			String total = String.valueOf(num1 * surveyQuotedPrice);
			if(!SearchWather.checkNUMBER4(total)){
				throw new IllegalArgumentException("查勘总价不能大于7位整数");
			}
				
				
			temp = modifyFactoryPriceExt.getText().toString();
			if (StringUtils.isNotEmpty(temp)) {
				modifyFactoryPrice = StringUtils.toDouble(temp);
			}
			
			String errorMsg = null;
			if(partNameTxt.getText().toString().equals("")){
				errorMsg = "零件名称不能为空";
			}else if(StringUtils.isEmpty(insureTerm)
					||insureTerm.equals("请选择")){
				errorMsg = "险别不能为空";
			}else if("".equals(remnantExt.getText().toString())){
                errorMsg = "请输入残值";
            }else if("请选择".equals(ifRemainSp.getSelectedItem().toString())){
                errorMsg = "请选择是否损余";
            }else if(remnant>surveyQuotedPrice*lossCount){
				errorMsg = "残值金额不能大于查勘价格";
			}
			

/*			if(StringUtils.isEmpty(partStandard)){
				errorMsg = "更换部位不能为空";
			}else if(StringUtils.isEmpty(partGroupName)){
				errorMsg = "零件分组名称不能为空";
			}else if(StringUtils.isEmpty(originalName)){
				errorMsg = "原厂零件名称不能为空";
			}else if(StringUtils.isEmpty(originalId)){
				errorMsg = "原厂零件编号不能为空";
			}else if(StringUtils.isEmpty(insureTerm)
					||insureTerm.equals("请选择")){
				errorMsg = "险别不能为空";
			}else if(!originalName.equals(partStandard)){
				errorMsg = "更换部位必须与原厂零件名称一致";
			}else if(remnant>surveyQuotedPrice*lossCount){
				errorMsg = "残值金额不能大于查勘价格";
			}*/
			
			/*else if(0==locPrice1){
			errorMsg = "本地价不能为0";
			}else if(-1==locPrice1){
				errorMsg = "本地价不能为空";
			}else if(0==modifyFactoryPrice){
				errorMsg = "修理厂价格不能为0";
			}else if(-1==modifyFactoryPrice){
				errorMsg = "修理厂价格不能为空";
			}else if(0==remnant){
				errorMsg = "请输入残值";
			}*/
			if(StringUtils.isNotEmpty(errorMsg)){
				throw new IllegalArgumentException(errorMsg);
			}
			result = true;
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		try {
			this.finish();
			ActivityManage.getInstance().pop() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		localItemConf=null;
		
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (getCurrentFocus() != null) {
			if (getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(this.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.dispatchTouchEvent(ev);
	}
}
