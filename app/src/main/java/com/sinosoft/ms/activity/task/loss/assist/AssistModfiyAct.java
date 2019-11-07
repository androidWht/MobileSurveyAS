package com.sinosoft.ms.activity.task.loss.assist;

import java.math.BigDecimal;
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
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.KindCodeData;
import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.service.IDeflossDataService;
import com.sinosoft.ms.service.IKindCodeDataService;
import com.sinosoft.ms.service.ILossAssistInfoService;
import com.sinosoft.ms.service.impl.DeflossDataService;
import com.sinosoft.ms.service.impl.KindCodeDataService;
import com.sinosoft.ms.service.impl.LossAssistInfoService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.SearchWather;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;

/**
 * 辅料修改Activity
 * 
 * @author Administrator
 * 
 */

public class AssistModfiyAct extends Activity implements OnClickListener {
	private IDeflossDataService deflossDataService;
	private ILossAssistInfoService lossAssistInfoService;
	private IKindCodeDataService kindCodeDataService;
	private LossAssistInfoItem lossAssistInfoItem;
	private List<KindCodeData> kindCodeList = null;
	private InputMethodManager imm;

	private TextView materialNameTxt;
	private TextView materialCodeTxt;
	private EditText unitPriceExt;
	private EditText countExt;
	private EditText materialFeeExt;
	private EditText remarkExt;
	private EditText insureTermEdit;

	private Button saveBtn;
	private Button backBtn;

	private String insureTerm;
	private double materialFee;
	private double unitPrice;
	private String remark;
	private long count;

	private int assistInfoId;
	private String registNo;
	private String errorMsg;
	private String taskId;
	private Map<String, String> insureTermCodeMap = null;
	private Dialog dialog;
	
	private SharedPreferences insureTermSP;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		deflossDataService = null;
		lossAssistInfoService = null;
		kindCodeDataService = null;
		lossAssistInfoItem = null;
		kindCodeList = null;
		materialNameTxt = null;
		materialCodeTxt = null;
		unitPriceExt = null;
		countExt = null;
		materialFeeExt = null;
		remarkExt = null;
		insureTermEdit = null;
		saveBtn = null;
		backBtn = null;
		insureTerm = null;
		remark = null;
		registNo = null;
		errorMsg = null;
		taskId = null;
		insureTermCodeMap = null;
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_assist_modfiy);
		ActivityManage.getInstance().push(this) ;
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		Intent intent = getIntent();
		registNo = (String) intent.getCharSequenceExtra("registNo");
		taskId = (String) intent.getCharSequenceExtra("taskId");
		assistInfoId = intent.getIntExtra("assistInfoId", 0);
		insureTermSP = getSharedPreferences("insureTerm", 0);
		// 设置视图控件
		setViewControl();

		loadData();
		unitPriceExt.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					try{
						String unitPrice = unitPriceExt.getText().toString();
						String count = countExt.getText().toString();
						if(StringUtils.isNotEmpty(unitPrice) && StringUtils.isNotEmpty(count)){
							double result = add(unitPrice,count);
							materialFeeExt.setText(String.valueOf(result));
						}
					}catch(NumberFormatException nfe){
						
					}catch(Exception e){
					}
				}
			}
		});
		countExt.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					try{
						String unitPrice = unitPriceExt.getText().toString();
						String count = countExt.getText().toString();
						if(StringUtils.isNotEmpty(unitPrice) && StringUtils.isNotEmpty(count)){
							double result = add(unitPrice,count);
							materialFeeExt.setText(String.valueOf(result));
						}
					}catch(NumberFormatException nfe){
						
					}catch(Exception e){
					}
				}
			}
		});
		saveBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	public double add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}
	public double add(double v1, long v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	} 
	
	private void setViewControl() {
		materialNameTxt = (TextView) findViewById(R.id.materialNameTxt);
		materialCodeTxt = (TextView) findViewById(R.id.materialCodeTxt);
		unitPriceExt = (EditText) findViewById(R.id.unitPriceExt);
		countExt = (EditText) findViewById(R.id.countExt);
		materialFeeExt = (EditText) findViewById(R.id.materialFeeExt);
		remarkExt = (EditText) findViewById(R.id.remarkExt);
		insureTermEdit = (EditText) findViewById(R.id.insureTermEdit);

		saveBtn = (Button) findViewById(R.id.saveBtn);
		backBtn = (Button) findViewById(R.id.backBtn);

		countExt.addTextChangedListener(new SearchWather(this, countExt,
				SearchWather.NUMBER2));
		
		
	}
	private void loadData() {
		try {
			if (null == lossAssistInfoService) {
				lossAssistInfoService = new LossAssistInfoService();
			}
			lossAssistInfoItem = lossAssistInfoService.getById(assistInfoId);
			if (null != lossAssistInfoItem) {
				materialNameTxt.setText(lossAssistInfoItem.getMaterialName());
				materialCodeTxt.setText(lossAssistInfoItem.getMaterialCode());
				unitPriceExt
						.setText((0 == lossAssistInfoItem.getUnitPrice() ? ""
								: String.valueOf(lossAssistInfoItem
										.getUnitPrice())));
				countExt.setText((0 == lossAssistInfoItem.getCount() ? ""
						: String.valueOf(lossAssistInfoItem.getCount())));
				materialFeeExt.setText((0 == lossAssistInfoItem
						.getMaterialFee() ? "" : String
						.valueOf(lossAssistInfoItem.getMaterialFee())));
				remarkExt.setText(lossAssistInfoItem.getRemark());
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.item_simple_spinner);
			if (null == deflossDataService) {
				deflossDataService = new DeflossDataService();
			}
			DeflossData deflossData = deflossDataService.getByTaskId(taskId);
			if (null == kindCodeDataService) {
				kindCodeDataService = new KindCodeDataService();
			}
			kindCodeList = kindCodeDataService.getById(deflossData.getId());
			insureTermCodeMap = new HashMap<String, String>();
			if (null != kindCodeList && !kindCodeList.isEmpty()) {
				Iterator iter = kindCodeList.iterator();
				while (iter.hasNext()) {
					KindCodeData kindCodeData = (KindCodeData) iter.next();
					insureTermCodeMap.put(kindCodeData.getInsureTermCode(),kindCodeData.getInsureTerm()
							);
				}
				
			}
			if(insureTermCodeMap.isEmpty()){
				insureTermCodeMap.put("02", "商业第三者责任险");
				insureTermCodeMap.put("BZ", "机动车交通事故责任强制险");
			}
			insureTermEdit.setText(insureTermSP.getString("value", ""));
//			SpinnerUtils.setSpinnerData(this, insureTermSp, insureTermCodeMap, lossAssistInfoItem.getInsureTermCode()+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 事件处理
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveBtn:
			try {
			
				
				if (StringUtils.isNotEmpty(unitPriceExt.getText().toString())) {
					unitPrice = Double.parseDouble(unitPriceExt.getText()
							.toString());
				}else{
					unitPrice=-1;
				}
				
				if (StringUtils.isNotEmpty(countExt.getText().toString())) {
					count = Long.parseLong(countExt.getText().toString());
				}else{
					count=-1;
				}
				if (StringUtils.isNotEmpty(remarkExt.getText().toString())) {
					remark = remarkExt.getText().toString();
				}
//				if (null != insureTermSp.getSelectedItem()) {
//					insureTerm = insureTermSp.getSelectedItem().toString();
//				}
				insureTerm = insureTermSP.getString("value", "");
				double result = add(unitPrice,count);
				materialFeeExt.setText(String.valueOf(result));
				if (StringUtils.isNotEmpty(materialFeeExt.getText().toString())) {
					materialFee = Double.parseDouble(materialFeeExt.getText()
							.toString());
				}else{
					materialFee=-1;
				}
				if (checkInputData()) {// 数据校验
					// 暂存信息
					lossAssistInfoItem.setUnitPrice(unitPrice);
					lossAssistInfoItem.setCount(count);
					lossAssistInfoItem.setMaterialFee(materialFee);
					lossAssistInfoItem.setRemark(remark);
					lossAssistInfoItem.setInsureTerm(insureTerm);
					lossAssistInfoItem.setInsureTermCode(insureTermSP.getString("key", ""));
//					lossAssistInfoItem.setInsureTermCode(SpinnerUtils.getKey(insureTerm,insureTermCodeMap));
					
					// 添加标志
					lossAssistInfoItem.setDefineType("0") ;
					if (lossAssistInfoService.update(lossAssistInfoItem)) {
						if(dialog!=null){
							dialog.dismiss();
						}
						dialog = CustomDialog.show(AssistModfiyAct.this, "信息提示",
								"保存成功!", "确定", "", new OnClickListener() {
									@Override
									public void onClick(View v) {
										try {
											if (dialog != null) {
												dialog.dismiss();
											}
											AssistModfiyAct.this.finish();
											ActivityManage.getInstance().pop() ;
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}, null);
					} else {
						ToastDialog.show(this, "添加失败", ToastDialog.ERROR);
					}
				} else {
					ToastDialog.show(this, ""+errorMsg,ToastDialog.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.backBtn:
			try {
				this.finish();
				ActivityManage.getInstance().pop() ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private boolean checkInputData() {
		errorMsg = null;
		boolean result = false;
		if (0 == unitPrice) {
			errorMsg = "单价不能为0";
		}else if(unitPrice==-1){
			errorMsg = "单价不能为空";
		}else if (0 == count) {
			errorMsg = "数量不能为0";
		}else if (-1 == count) {
			errorMsg = "数量不能为空";
		}else if (StringUtils.isEmpty(insureTerm) 
				|| insureTerm.equals("请选择")) {
			errorMsg = "险别不能为空";
		}
		String priceMsg = unitPriceExt.getText().toString() ;
		double price = 0 ;
		if(!"".equals(priceMsg)){
			price = Double.parseDouble(priceMsg) ;
		}
		
		String countMsg = countExt.getText().toString() ;
		int count = 0 ;
		if(!"".equals(countMsg)){
			count = Integer.parseInt(countMsg) ;
		}
		
		double total = price * count;
		if (!SearchWather.checkNUMBER4(String.valueOf(total))) {
			errorMsg = "总价不能大于7位整数";
		}
		/*
		 * else if(StringUtils.isEmpty(remark)){ errorMsg = "备注不能为空"; }
		 */
		if (StringUtils.isEmpty(errorMsg)) {
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
