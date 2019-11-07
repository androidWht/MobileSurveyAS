package com.sinosoft.ms.activity.task.loss.change;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.po.GeneralParty;
import com.sinosoft.ms.service.IThirdPartyService;
import com.sinosoft.ms.service.impl.ThirdPartyService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.ToastDialog;

/**
 * 换件查询
 * 
 * @author Administrator
 * 
 */
public class ChangeQueryAct extends Activity implements OnClickListener,
		OnItemSelectedListener {
	private IThirdPartyService thirdPartyService;
	private List<GeneralParty> partyList = null;
	private List<GeneralParty> groupList = null;
	private List<GeneralParty> typeList = null;
	private List<GeneralParty> nameList = null;
	private List<GeneralParty> keyList = null;

//	private Spinner pricePlanSp;
	private EditText pricePlanEdit;
	private Spinner replacementPartsSp;
	private Spinner partsGroupingSp;
	private Spinner partTypeSp;
	private Spinner partNameSp;
	private Spinner keywordsSp;

	//**  //
	private RadioGroup typeRad;
	
	private Button dataSreachBtn;		//查询按钮
	private Button dataBrackBtn;			//返回按钮
	private LinearLayout tableRow3;
	private LinearLayout tableRow4;
	private LinearLayout tableRow5;

	private GeneralParty generalParty;
	private String[] prices = null;
	private String pricePlanId;
	private String vehicleType;
	private String replacementId;
	private String partsGroupId;
	private String partTypeId;
	private String partNameId;
	private String keyName;
	private String registNo;
	private String pricePlan;
	private String errorMsg;
	private String taskId;
	private String damagePartId;		//损伤部位零件ID，取最后一个分组的ID
	private SharedPreferences insureTermSP;
	private SharedPreferences repairFactoryTypeSP;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		thirdPartyService = null;
		partyList = null;
		groupList = null;
		typeList = null;
		nameList = null;
		keyList = null;
		pricePlanEdit = null;
		replacementPartsSp = null;
		partsGroupingSp = null;
		partTypeSp = null;
		partNameSp = null;
		keywordsSp = null;
		dataSreachBtn = null;
		dataBrackBtn = null;
		tableRow3 = null;
		tableRow4 = null;
		tableRow5 = null;
		generalParty = null;
		prices = null;
		pricePlanId = null;
		vehicleType = null;
		replacementId = null;
		partsGroupId = null;
		partTypeId = null;
		partNameId = null;
		keyName = null;
		registNo = null;
		pricePlan = null;
		errorMsg = null;
		taskId = null;
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loss_change_query);

		ActivityManage.getInstance().push(this);

		Intent intent = getIntent();
		registNo = (String) intent.getCharSequenceExtra("registNo");
		pricePlan = (String) intent.getCharSequenceExtra("pricePlan");
		vehicleType = (String) intent.getCharSequenceExtra("vehicleType");
		taskId = (String) intent.getCharSequenceExtra("taskId");
		insureTermSP = getSharedPreferences("insureTerm", 0);
		repairFactoryTypeSP = getSharedPreferences("repairFactoryType", 0);
		// 设置视图控件
		setViewControl();
		// 加载数据
		loadData();
		dataSreachBtn.setOnClickListener(this);
		dataBrackBtn.setOnClickListener(this);
	}

	private void setViewControl() {
		pricePlanEdit = (EditText) findViewById(R.id.pricePlanEdit);
		replacementPartsSp = (Spinner) findViewById(R.id.replacementPartsSp);
		partsGroupingSp = (Spinner) findViewById(R.id.partsGroupingSp);
		partTypeSp = (Spinner) findViewById(R.id.partTypeSp);
		partNameSp = (Spinner) findViewById(R.id.partNameSp);
		keywordsSp = (Spinner) findViewById(R.id.keywordsSp);
		tableRow3 = (LinearLayout) findViewById(R.id.tableRow3);
		tableRow4 = (LinearLayout) findViewById(R.id.tableRow4);
		tableRow5 = (LinearLayout) findViewById(R.id.tableRow5);
		typeRad=(RadioGroup)findViewById(R.id.typeRad);

		dataSreachBtn = (Button) findViewById(R.id.dataSreachBtn);
		dataBrackBtn = (Button) findViewById(R.id.dataBrackBtn);
	}

	private void loadData() {
		if (null == thirdPartyService) {
			thirdPartyService = new ThirdPartyService();
		}

		pricePlanEdit.setText(repairFactoryTypeSP.getString("value", ""));
		pricePlanId = repairFactoryTypeSP.getString("key", "");
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//				R.layout.simple_spinner_item);
//		adapter.add("请选择价格方案");
//		prices = pricePlan.split("\\|");
//		for (String str : prices) {
//			adapter.add(str);
//		}
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		pricePlanSp.setAdapter(adapter);
//		pricePlanSp.setOnItemSelectedListener(this);
//		pricePlanSp.setSelection(0, true);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_simple_spinner);
		partyList = thirdPartyService.getReplacementParts();
		if (null != partyList && !partyList.isEmpty()) {
			Iterator iter = partyList.iterator();
			adapter.add("请选择更换部位");
			while (iter.hasNext()) {
				GeneralParty generalParty = (GeneralParty) iter.next();
				adapter.add(generalParty.getName());
			}
		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		replacementPartsSp.setAdapter(adapter);
		replacementPartsSp.setOnItemSelectedListener(this);
		replacementPartsSp.setSelection(0, true);

		adapter = new ArrayAdapter<String>(this, R.layout.item_simple_spinner);
		keyList = thirdPartyService.getChangeItemKeywords();
		if (null != keyList && !keyList.isEmpty()) {
			Iterator iter = keyList.iterator();
			adapter.add("请选择关键字");
			while (iter.hasNext()) {
				GeneralParty generalParty = (GeneralParty) iter.next();
				adapter.add(generalParty.getName());
			}
		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		keywordsSp.setAdapter(adapter);
		keywordsSp.setOnItemSelectedListener(this);
		keywordsSp.setSelection(0, true);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		if (pos > 0) {
			switch (arg0.getId()) {
			case R.id.replacementPartsSp:// 更换部位下拉事件
				if (partyList != null && partyList.size() > 0) {
					generalParty = partyList.get(pos - 1);
					//获取零件ID
					damagePartId = generalParty.getId();
					groupList = thirdPartyService
							.getPartsGroupName(generalParty.getId());
					ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(
							this, R.layout.item_simple_spinner);
					brandAdapter.add("请选择零件分组");
					if (null != groupList && !groupList.isEmpty()) {
						Iterator iter = groupList.iterator();
						while (iter.hasNext()) {
							GeneralParty group = (GeneralParty) iter.next();
							brandAdapter.add(group.getName());
						}
						brandAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						partsGroupingSp.setAdapter(brandAdapter);
						partsGroupingSp.setOnItemSelectedListener(this);
						partsGroupingSp.setSelection(0, true);
						tableRow3.setVisibility(0);
						tableRow4.setVisibility(8);
						tableRow5.setVisibility(8);
					} else {
						generalParty = partyList.get(pos - 1);
						tableRow3.setVisibility(8);
					}
				}
				break;
			case R.id.partsGroupingSp:// 零件分组下拉事件

				GeneralParty group = groupList.get(pos - 1);
				//获取零件ID
				damagePartId = group.getId();
				partsGroupId = group.getName();
				typeList = thirdPartyService.getPartsGroupName(group.getId());
				ArrayAdapter<String> brandAdapter2 = new ArrayAdapter<String>(
						this, R.layout.item_simple_spinner);
				brandAdapter2.add("请选择零件类型");
				if (null != typeList && !typeList.isEmpty()) {
					Iterator iter = typeList.iterator();
					while (iter.hasNext()) {
						GeneralParty types = (GeneralParty) iter.next();
						brandAdapter2.add(types.getName());
					}
					brandAdapter2
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					partTypeSp.setAdapter(brandAdapter2);
					partTypeSp.setOnItemSelectedListener(this);
					partTypeSp.setSelection(0, true);
					tableRow4.setVisibility(0);
					tableRow5.setVisibility(8);
				} else {
					generalParty = groupList.get(pos - 1);
					tableRow4.setVisibility(8);
				}
				break;
			case R.id.partTypeSp:// 零件类型下拉事件
				if (typeList != null && typeList.size() > pos-1) {
					GeneralParty namePart = typeList.get(pos - 1);
					damagePartId = namePart.getId();
					nameList = thirdPartyService.getPartsGroupName(namePart
							.getId());
					ArrayAdapter<String> brandAdapter3 = new ArrayAdapter<String>(
							this, R.layout.item_simple_spinner);
					brandAdapter3.add("请选择零件名称");
					if (null != nameList && !nameList.isEmpty()) {
						Iterator iter = nameList.iterator();
						while (iter.hasNext()) {
							GeneralParty name = (GeneralParty) iter.next();
							brandAdapter3.add(name.getName());
						}
						brandAdapter3
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						partNameSp.setAdapter(brandAdapter3);
						partNameSp.setOnItemSelectedListener(this);
						partNameSp.setSelection(0, true);
						tableRow5.setVisibility(0);
					} else {
						if (typeList != null && typeList.size() > 0) {
							generalParty = typeList.get(pos - 1);
						}
						tableRow5.setVisibility(8);
					}
				}

				break;
			case R.id.partNameSp:// 零件名称下拉事件
				if (nameList != null && nameList.size() > 0) {
					generalParty = nameList.get(pos - 1);
					damagePartId = generalParty.getId();
				}else{
					generalParty=new GeneralParty();
				}

				break;
			case R.id.keywordsSp:// 关键字下拉事件
				if (keyList != null && keyList.size() > 0) {
					keyName = keyList.get(pos - 1).getName();
					damagePartId = keyList.get(pos - 1).getId();
				}else{
					keyName="";
				}
				break;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void onClick(View v) {
		Intent intent = getIntent();
		switch (v.getId()) {
		case R.id.dataSreachBtn:
			if (checkData()) {
				intent.putExtra("pricePlanId", pricePlanId);//
				intent.putExtra("partNameId", generalParty.getName());// 零件名称
				intent.putExtra("keyName", keyName);//
				intent.putExtra("registNo", registNo);//
				intent.putExtra("taskId", taskId);//

				intent.putExtra("carGroupId", generalParty.getId());//
				intent.putExtra("vehicleType", vehicleType);// 车型编码 下页面获取
				intent.putExtra("partsGroupId", partsGroupId);// 零件分组
				intent.putExtra("damagePartId", damagePartId);			//零件分组Id
				RadioButton r=(RadioButton)findViewById(typeRad.getCheckedRadioButtonId());
				int type=1;
				if(r!=null){
					type=r.getText().toString().equals("一次点选")?1:2;
				}
				
				intent.putExtra("type",type);
				intent.setClass(ChangeQueryAct.this, ChangeQueryListAct.class);
				startActivity(intent);
			} else {
				ToastDialog.show(this, errorMsg, ToastDialog.ERROR);
			}
			break;
		case R.id.dataBrackBtn:
			try {
				this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * 
	 */
	private boolean checkData() {
		boolean result = true;
		if (StringUtils.isEmpty(pricePlanId)) {
			errorMsg = "价格方案不能为空";
			result = false;
		} else if (null == generalParty) {
			errorMsg = "零件信息不能为空";
			result = false;
		} else if (StringUtils.isEmpty(generalParty.getName())) {
			errorMsg = "零件名称不能为空";
			result = false;
		} else if (StringUtils.isEmpty(registNo)) {
			errorMsg = "报案号不能为空";
			result = false;
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
}
