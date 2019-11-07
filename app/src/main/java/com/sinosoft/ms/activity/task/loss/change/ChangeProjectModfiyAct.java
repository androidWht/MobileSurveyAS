package com.sinosoft.ms.activity.task.loss.change;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
 * 换件Activity
 * @author Administrator
 *
 */
public class ChangeProjectModfiyAct extends Activity implements OnClickListener ,OnItemSelectedListener{
	private Map<String,Map<String,String>> localItemConf = null;
	private IDeflossDataService deflossDataService;
	private ILossFitInfoService lossFitInfoService;
	private IKindCodeDataService kindCodeDataService;
	private LossFitInfoItem lossFitInfoItem;
	private List<KindCodeData> kindCodeList = null;
	private InputMethodManager imm;
	
	private TextView originalNameTxt;
	private TextView originalIdTxt;
	private TextView partStandardTxt;
	private TextView partGroupNameTxt;
	private EditText lossCountExt;
	private EditText remnantExt;
	private Spinner ifRemainSp;
	private EditText chgCompSetCodeEdit;	//价格方案
	private EditText remarkExt;
	private EditText insureTermEdit;	//险别
	private TextView locPrice1Ext;
	private TextView refPrice1Txt;
	private EditText surveyQuotedPriceExt;
	private TextView modifyFactoryPriceExt;
	private TextView partNameTxt;
	private Button changeItemModfiySaveBtn;
	private Button changeItemModfiyBackBtn;
	
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
	private String errorMsg;
	//private String chgCompSetCode;
	private Map<String,String> insureTermCodeMap=null;
	
	private SharedPreferences repairFactoryTypeSP;
	private SharedPreferences insureTermSP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loss_change_item_modify);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		ActivityManage.getInstance().push(this);
		Intent intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		taskId = intent.getStringExtra("taskId");
		lossFitInfoItemId = intent.getIntExtra("lossFitInfoItemId", -1);
//		repairFactoryTypeSP = getSharedPreferences("repairFactoryType", 0);
		insureTermSP = getSharedPreferences("insureTerm", 0);
		// 设置视图控件
		setViewControl();

		loadData();
		ifRemainSp.setOnItemSelectedListener(this);
		changeItemModfiySaveBtn.setOnClickListener(this);
		changeItemModfiyBackBtn.setOnClickListener(this);
	}
	
	private void setViewControl() {
		partStandardTxt = (TextView) findViewById(R.id.partStandardTxt);
		partGroupNameTxt = (TextView) findViewById(R.id.partGroupNameTxt);
		originalNameTxt = (TextView) findViewById(R.id.originalNameTxt); 
		originalIdTxt = (TextView) findViewById(R.id.originalIdTxt); 
		lossCountExt = (EditText) findViewById(R.id.lossCountExt);
		remnantExt = (EditText) findViewById(R.id.remnantExt);
		ifRemainSp = (Spinner) findViewById(R.id.ifRemainSp);
		chgCompSetCodeEdit = (EditText) findViewById(R.id.pricePlanEdit);
		remarkExt = (EditText) findViewById(R.id.remarkExt);
		insureTermEdit = (EditText) findViewById(R.id.insureTermEdit);
		locPrice1Ext = (TextView) findViewById(R.id.locPrice1Ext);
		refPrice1Txt = (TextView) findViewById(R.id.refPrice1Txt);
		surveyQuotedPriceExt = (EditText) findViewById(R.id.surveyQuotedPriceExt);
		modifyFactoryPriceExt = (TextView) findViewById(R.id.modifyFactoryPriceExt);
		partNameTxt = (TextView) findViewById(R.id.partNameTxt);
		
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
				partStandardTxt.setText(lossFitInfoItem.getPartStandard());
				partGroupNameTxt.setText(lossFitInfoItem.getPartGroupName());
				originalNameTxt.setText(lossFitInfoItem.getOriginalName());
				originalIdTxt.setText(lossFitInfoItem.getOriginalId());
				lossCountExt.setText(String.valueOf(lossFitInfoItem.getLossCount()));
				remnantExt.setText(String.valueOf(lossFitInfoItem.getRemnant()));
				remarkExt.setText(lossFitInfoItem.getRemark());
				locPrice1Ext.setText(String.valueOf(lossFitInfoItem.getRefPrice1()));
				refPrice1Txt.setText(String.valueOf(lossFitInfoItem.getRefPrice1()));
				surveyQuotedPriceExt.setText(lossFitInfoItem.getSurveyQuotedPrice());
				modifyFactoryPriceExt.setText(lossFitInfoItem.getModifyFactoryPrice());
				partNameTxt.setText(lossFitInfoItem.getPartStandard());
//				chgCompSetCodeEdit.setText(repairFactoryTypeSP.getString("value", ""));
				chgCompSetCode = lossFitInfoItem.getChgCompSetCode();
				chgCompSetCodeEdit.setText(SpinnerUtils.getValue(lossFitInfoItem.getChgCompSetCode(),localItemConf.get("ChgCompSetCode")));
//				chgCompSetCode=lossFitInfoItem.getChgCompSetCode();
//				chgCompSetCode = repairFactoryTypeSP.getString("key", "");
			}else{
				lossFitInfoItem=new LossFitInfoItem();
			}
						
			SpinnerUtils.setSpinnerData(this, ifRemainSp, localItemConf.get("Status"), lossFitInfoItem.getIfRemain()+"","");
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
				if (StringUtils.isNotEmpty(originalNameTxt.getText().toString())) {
					originalName = originalNameTxt.getText().toString();
				}
				if (StringUtils.isNotEmpty(originalIdTxt.getText().toString())) {
					originalId = originalIdTxt.getText().toString();
				}
				if (StringUtils.isNotEmpty(partStandardTxt.getText().toString())) {
					partStandard = partStandardTxt.getText().toString();
				}
				partGroupName = partGroupNameTxt.getText().toString();
				
				if(!StringUtils.isEmpty(remnantExt.getText().toString())){
					remnant = Double.parseDouble(remnantExt.getText().toString());
				}
				
				if (StringUtils.isNotEmpty(ifRemainSp.getSelectedItem().toString())) {
					ifRemain =SpinnerUtils.getKey(ifRemainSp.getSelectedItem().toString(),localItemConf.get("Status"));
				}
				/*if (StringUtils.isNotEmpty(chgCompSetCodeTxt.getText().toString())) {
					chgCompSetCode = chgCompSetCodeTxt.getText().toString();
				}*/
//				if ("1".equals(ifRemain)){
//					remark = "";
//				}else{
					if(StringUtils.isNotEmpty(remarkExt.getText().toString())) {
						remark = remarkExt.getText().toString();
					}
//				}
//				if (null!=insureTermSp.getSelectedItem()) {
//					insureTerm = insureTermSp.getSelectedItem().toString();
//				}
				insureTerm = insureTermEdit.getText().toString();
				if (StringUtils.isNotEmpty(locPrice1Ext.getText().toString())) {
					locPrice1 = Double.parseDouble(locPrice1Ext.getText()
							.toString());
				}else{
					//locPrice1=-1;
				}
				lossCount =StringUtils.toLong(lossCountExt.getText().toString());
				refPrice1 = StringUtils.toDouble(refPrice1Txt.getText().toString());
				surveyQuotedPrice=StringUtils.toDouble(surveyQuotedPriceExt.getText().toString());
				Log.e("surveyQuotedPrice", ""+surveyQuotedPrice);
				if(checkInputData()){//数据校验
					//暂存信息
					
					lossFitInfoItem.setOriginalName(originalName);
					lossFitInfoItem.setOriginalId(originalId);
					lossFitInfoItem.setPartStandard(partStandard);
					lossFitInfoItem.setPartGroupName(partGroupName);
					lossFitInfoItem.setLossCount(lossCount);
					lossFitInfoItem.setRemnant(remnant);
					lossFitInfoItem.setIfRemain(ifRemain);
					lossFitInfoItem.setChgCompSetCode(chgCompSetCode);
					lossFitInfoItem.setRemark(remark);
					lossFitInfoItem.setInsureTermCode(insureTermSP.getString("key", ""));
//					lossFitInfoItem.setInsureTermCode(SpinnerUtils.getKey(insureTerm, insureTermCodeMap));
					lossFitInfoItem.setInsureTerm(insureTerm);
					lossFitInfoItem.setLocPrice1(locPrice1);
					lossFitInfoItem.setRefPrice1(refPrice1);
					lossFitInfoItem.setLossPrice(surveyQuotedPrice);
					lossFitInfoItem.setSurveyQuotedPrice(surveyQuotedPriceExt.getText().toString());
					lossFitInfoItem.setModifyFactoryPrice(modifyFactoryPriceExt.getText().toString());
					// 添加标志
					lossFitInfoItem.setSelfConfigFlag("0") ;
					if(lossFitInfoService.update(lossFitInfoItem)){
						CustomDialog.show(this, "提示", "保存成功", new OnClickListener(){

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								ChangeProjectModfiyAct.this.finish();
								try {
									ActivityManage.getInstance().pop() ;
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}, new OnClickListener(){

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								ChangeProjectModfiyAct.this.finish();
								try {
									ActivityManage.getInstance().pop() ;
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						});

					}else{
						ToastDialog.show(this, "暂存失败", ToastDialog.ERROR);
					}
				}else{
					ToastDialog.show(this, errorMsg, ToastDialog.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.changeItemModfiyBackBtn:
			try {
				this.finish();
				ActivityManage.getInstance().pop() ;
			} catch (Exception e) {
				e.printStackTrace();
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
	
	private boolean checkInputData() {
		errorMsg = null;
		boolean result = false;
		String lossCountStr = lossCountExt.getText().toString();
		String surveyQuotedPriceStr = surveyQuotedPriceExt.getText().toString();
		double surveyQuotedPrice=StringUtils.toDouble(surveyQuotedPriceStr);
		if (StringUtils.isEmpty(lossCountStr)) {
			errorMsg = "换件数量不能为空";
		}else if("0".equals(lossCountStr)){
			errorMsg = "换件数量不能为0";
		}else if(StringUtils.isEmpty(surveyQuotedPriceStr)){
			errorMsg = "查勘报价不能为空";
		}else if("0".equals(surveyQuotedPriceStr)){
			errorMsg = "查勘报价不能为0";
		}else if(StringUtils.isEmpty(insureTerm)
				||insureTerm.equals("请选择")){
			errorMsg = "险别不能为空";
		}else if("".equals(remnantExt.getText().toString())){
			errorMsg = "请输入残值";
		}else if("请选择".equals(ifRemainSp.getSelectedItem().toString())){
            errorMsg = "请选择是否损余";
        }else if(remnant>surveyQuotedPrice*lossCount){
			errorMsg = "残值金额不能大于查勘价格";
		}else if(!SearchWather.checkNUMBER4(surveyQuotedPriceStr)){
			errorMsg = "查勘报价不能大于7位整数";
		}else if(!SearchWather.checkNUMBER4(String.valueOf(lossCount*surveyQuotedPrice))){
			errorMsg = "查勘总价不能大于7位整数";
		}
		
		if(StringUtils.isEmpty(errorMsg)){
			result = true;
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
