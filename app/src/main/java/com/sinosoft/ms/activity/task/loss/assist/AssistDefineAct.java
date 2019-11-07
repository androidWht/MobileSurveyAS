package com.sinosoft.ms.activity.task.loss.assist;

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
import android.widget.Spinner;
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

public class AssistDefineAct extends Activity implements OnClickListener {
	private IDeflossDataService deflossDataService;
	private ILossAssistInfoService lossAssistInfoService;
	private IKindCodeDataService kindCodeDataService;
	private LossAssistInfoItem lossAssistInfoItem;
	private List<LossAssistInfoItem> lossAssistInfoList;
	private List<KindCodeData> kindCodeList = null;
	private InputMethodManager imm;
	private EditText materialNameTxt;
	private TextView materialCodeTxt;
	private EditText unitPriceExt;
	private EditText countExt;
	private TextView materialFeeTxt;
	private EditText remarkExt;
	private EditText insureTermEdit;

	private Button saveBtn;
	private Button backBtn;

	private String materialName;
	private String materialCode;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_assist_define);
		ActivityManage.getInstance().push(this);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		Intent intent = getIntent();
		registNo = (String) intent.getCharSequenceExtra("registNo");
		taskId = (String) intent.getCharSequenceExtra("taskId");
		assistInfoId = intent.getIntExtra("assistInfoId", 0);
		insureTermSP = getSharedPreferences("insureTerm", 0);
		// 设置视图控件
		setViewControl();

		loadData();

		saveBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
	}

	private void setViewControl() {
		materialNameTxt = (EditText) findViewById(R.id.materialNameTxt);
		materialCodeTxt = (TextView) findViewById(R.id.materialCodeTxt);
		unitPriceExt = (EditText) findViewById(R.id.unitPriceExt);
		countExt = (EditText) findViewById(R.id.countExt);
		materialFeeTxt = (TextView) findViewById(R.id.materialFeeTxt);
		/** 严植培 2013.5.14 **/
		materialFeeTxt.setEnabled(false); // 不可修改
		/** 严植培 **/
		remarkExt = (EditText) findViewById(R.id.remarkExt);
		insureTermEdit = (EditText) findViewById(R.id.insureTermEdit);

		saveBtn = (Button) findViewById(R.id.saveBtn);
		backBtn = (Button) findViewById(R.id.backBtn);

		// unitPriceExt.addTextChangedListener(new SearchWather(this,
		// unitPriceExt, SearchWather.NUMBER2));
		countExt.addTextChangedListener(new SearchWather(this, countExt,
				SearchWather.NUMBER2));
		unitPriceExt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				materialFeeTxt.setText(calculation());
			}
		});
		countExt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				materialFeeTxt.setText(calculation());
			}
		});
	}

	public String calculation() {
		String price = unitPriceExt.getText().toString();
		String mount = countExt.getText().toString();
		if (StringUtils.isNotEmpty(price) && StringUtils.isNotEmpty(mount)) {
			double p = StringUtils.toDouble(price);
			long m = StringUtils.toLong(mount);
			return p * m + "";
		}
		return "0";
	}

	private void loadData() {
		try {
			if (null == lossAssistInfoService) {
				lossAssistInfoService = new LossAssistInfoService();
			}
			lossAssistInfoList = lossAssistInfoService.getByRegistNo(taskId);
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
				materialFeeTxt.setText((0 == lossAssistInfoItem
						.getMaterialFee() ? "" : String
						.valueOf(lossAssistInfoItem.getMaterialFee())));
				remarkExt.setText(lossAssistInfoItem.getRemark());
			} else {
				lossAssistInfoItem = new LossAssistInfoItem();
				lossAssistInfoItem.setRegistNo(taskId);
			}
			lossAssistInfoItem.init();

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
					adapter.add(kindCodeData.getInsureTerm());
					insureTermCodeMap.put(kindCodeData.getInsureTermCode(),
							kindCodeData.getInsureTerm());
				}

			}
			if (insureTermCodeMap.isEmpty()) {
				insureTermCodeMap.put("02", "商业第三者责任险");
				insureTermCodeMap.put("BZ", "机动车交通事故责任强制险");
			}
			insureTermEdit.setText(insureTermSP.getString("value", ""));
			// SpinnerUtils.setSpinnerData(this, insureTermSp,
			// insureTermCodeMap, lossAssistInfoItem.getInsureTermCode()+"");
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
				materialFeeTxt.setText(calculation());
				materialName = materialNameTxt.getText().toString();
				materialCode = materialCodeTxt.getText().toString();

				if (StringUtils.isNotEmpty(unitPriceExt.getText().toString())) {
					unitPrice = Double.parseDouble(unitPriceExt.getText()
							.toString());
				} else {
					unitPrice = -1;
				}
				if (StringUtils.isNotEmpty(countExt.getText().toString())) {
					count = Long.parseLong(countExt.getText().toString());
				} else {
					count = -1;
				}

				if (StringUtils.isNotEmpty(materialFeeTxt.getText().toString())) {
					materialFee = Double.parseDouble(materialFeeTxt.getText()
							.toString());
				} else {
					// materialFee=-1;
				}
				remark = remarkExt.getText().toString();
				insureTerm = insureTermSP.getString("value", "");
				// insureTerm = insureTermSp.getSelectedItem().toString();

				if (checkInputData()) {// 数据校验
					lossAssistInfoItem.setMaterialName(materialName);
					lossAssistInfoItem.setMaterialCode(materialCode);
					lossAssistInfoItem.setUnitPrice(unitPrice);
					lossAssistInfoItem.setCount(count);
					lossAssistInfoItem.setMaterialFee(materialFee);
					lossAssistInfoItem.setRemark(remark);
					lossAssistInfoItem.setInsureTerm(insureTerm);
					lossAssistInfoItem.setInsureTermCode(insureTermSP
							.getString("key", ""));
					// lossAssistInfoItem.setInsureTermCode(SpinnerUtils.getKey(insureTerm,insureTermCodeMap));

					if (assistInfoId == 0) {
						// 添加标志
						lossAssistInfoItem.setDefineType("1");
						if (lossAssistInfoService.save(lossAssistInfoItem) == 0) {
							throw new IllegalArgumentException("保存失败");
						}

					} else {
						if (!lossAssistInfoService.update(lossAssistInfoItem)) {
							throw new IllegalArgumentException("保存失败");
						}
					}

					if (dialog != null) {
						dialog.dismiss();
					}
					dialog = CustomDialog.show(AssistDefineAct.this, "信息提示",
							"保存成功!", "确定", "", new OnClickListener() {
								@Override
								public void onClick(View v) {
									try {
										if (dialog != null) {
											dialog.dismiss();
										}
										AssistDefineAct.this.finish();
										ActivityManage.getInstance().pop();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}, null);

					// ToastDialog.show(this, "暂存成功",ToastDialog.INFO);

				} else {
					ToastDialog.show(this, "" + errorMsg, ToastDialog.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ToastDialog.show(this, "" + e.getMessage(), ToastDialog.ERROR);
			}
			break;
		case R.id.backBtn:
			try {
				this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		}
	}

	private boolean checkInputData() {
		errorMsg = null;
		boolean result = false;
		if (StringUtils.isEmpty(materialName)) {
			errorMsg = "项目名称不能为空";
		} else if (StringUtils.isEmpty(materialCode)) {
			errorMsg = "项目编码不能为空";
		} else if (0 == unitPrice) {
			errorMsg = "单价不能为0";
		} else if (unitPrice == -1) {
			errorMsg = "单价不能为空";
		} else if (0 == count) {
			errorMsg = "数量不能为0";
		} else if (-1 == count) {
			errorMsg = "数量不能为空";
		} else if (StringUtils.isEmpty(insureTerm) || insureTerm.equals("请选择")) {
			errorMsg = "险别不能为空";
		}
		String priceMsg = unitPriceExt.getText().toString();
		double price = 0;
		if (!"".equals(priceMsg)) {
			price = Double.parseDouble(priceMsg);
		}
		String countMsg = countExt.getText().toString();
		int count = 0;
		if (!"".equals(countMsg)) {
			count = Integer.parseInt(countMsg);
		}
		double total = price * count;
		if (!SearchWather.checkNUMBER4(String.valueOf(total))) {
			errorMsg = "总价不能大于7位整数";
		}/*
		 * else if (StringUtils.isEmpty(remark)) { errorMsg = "备注不能为空"; }
		 */
		if (null != lossAssistInfoList) {
			for (LossAssistInfoItem item : lossAssistInfoList) {
				String m1 = item.getMaterialName();
				String m2 = materialName;
				if (!StringUtils.isEmpty(m1) && !StringUtils.isEmpty(m2)
						&& m1.equals(m2)) {
					errorMsg = "辅料重复\n"+materialName;
					break;
				}
			}
		}
		if (StringUtils.isEmpty(errorMsg)) {
			result = true;
		}
		return result;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		try {
			this.finish();
			ActivityManage.getInstance().pop();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		deflossDataService = null;
		lossAssistInfoService = null;
		kindCodeDataService = null;
		lossAssistInfoItem = null;
		kindCodeList = null;
		materialNameTxt = null;
		materialCodeTxt = null;
		unitPriceExt = null;
		countExt = null;
		materialFeeTxt = null;
		remarkExt = null;
		insureTermEdit = null;
		saveBtn = null;
		backBtn = null;
		materialName = null;
		materialCode = null;
		insureTerm = null;
		remark = null;
		registNo = null;
		errorMsg = null;
		taskId = null;
		insureTermCodeMap = null;
	}
}
