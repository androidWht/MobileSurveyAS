package com.sinosoft.ms.activity.task.survey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损系统 子系统名
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 2013-5-28 下午12:03:59
 */

public class SurveyAddPropAct extends Activity implements OnClickListener
{

	private InputMethodManager imm;
	private Map<String, Map<String, String>> dicMap;// 字典信息
	private Map<String, Map<String, String>> dicConfigMap;


	private PropData propData;
	private Spinner loss_PropTypeSp; // 损失类别
	private EditText propPartExt;    //损失名称
	private Spinner loss_PropInsTypeSp;// 损失险别
	private Spinner loss_PropMtypeSp; //费用名称
	private EditText dangerMoneyExt;//费用金额
	//private Spinner loss_typePropWSp;//物损属情
	private EditText propDescExt; //损失程度描述
	private Button saveCarBtn; // 保存
	private Button backCarBtn; // 返回	
	private ImageButton ibtn_dropDown;
	
	private PopupWindow pop;  
	private PopupAdapter adapter;
	private List<String> licenseNos;
	private List<CarData> carDatas;	
	private SurveyTreatmentDatabase2 database;
	private Context mContext;
	private ListView listView; 
	private EditText propSrcExt;
	private boolean isShow = false; 	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey_add_prop);
		mContext =this;
		
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		ActivityManage.getInstance().push(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("item");
		if (bundle != null)
		{
			propData = (PropData) bundle.getSerializable("bean");
		}
		else
		{
			propData = new PropData();
		}
		
		database = SurveyTreatmentDatabase2.getInstance();
		carDatas = database.getCarDatas();		
		
		this.setViewController(); // 设置控件
		// 获取缓存信息
		dicMap = DictCashe.getInstant().getDict();
		dicConfigMap = AppConstants.getLocalItemConf();
		this.setData(); // 设置处理界面信息
		this.saveCarBtn.setOnClickListener(this);
		this.backCarBtn.setOnClickListener(this);
	}

	/**
	 * 初始化视图组件
	 */
	public void setViewController()
	{
		this.loss_PropTypeSp = (Spinner) super.findViewById(R.id.loss_PropTypeSp); // 损失类别
		
		this.propPartExt = (EditText) super.findViewById(R.id.propPartExt); // 损失名称
		this.loss_PropInsTypeSp = (Spinner) super.findViewById(R.id.loss_PropInsTypeSp); //损失险别
		this.loss_PropMtypeSp = (Spinner) super.findViewById(R.id.loss_PropMtypeSp); //费用名称
		this.dangerMoneyExt = (EditText) super.findViewById(R.id.dangerMoneyExt); //损失金额
		///this.loss_typePropWSp = (Spinner) super.findViewById(R.id.loss_typePropWSp);//物损属情
		this.propDescExt =(EditText) super.findViewById(R.id.propDescExt); //损失程度描述
		
		this.propSrcExt =(EditText) super.findViewById(R.id.propSrcExt);
		this.ibtn_dropDown = (ImageButton) super.findViewById(R.id.ibtn_dropDown); //物损属情
		ibtn_dropDown.setOnClickListener(this);
		
		this.saveCarBtn = (Button) findViewById(R.id.saveCarBtn); // 保存
		this.backCarBtn = (Button) findViewById(R.id.backCarBtn); // 返回		
	}
	
	/**
	 * 设置界面数据
	 */
	public void setData()
	{
		licenseNos = new ArrayList<String>(); 
		if(carDatas!=null && carDatas.size()>0){
			for(int i=0;i<carDatas.size();i++){
			
				CarData carData = carDatas.get(i);
			    if(StringUtils.isNotEmpty(carData.getLicenseNo()))
				  licenseNos.add(carData.getLicenseNo());
			}
		}
		
		this.propPartExt.setText(propData.getLossParty()); // 损失名称
		this.propSrcExt.setText(propData.getRelatePersonName());
		this.dangerMoneyExt.setText(propData.getSumLossFee()); //损失金额
		this.propDescExt.setText(propData.getRescueInfo()); //损失程度描述

		
		
		
		SpinnerUtils.setPropSpinnerData(this, loss_PropTypeSp, dicConfigMap.get("ProDateType"),propData.getCheckDate());// 损失类别
		String code =propData.getReserveFlag();
		if(StringUtils.isEmpty(code)){
			code="BZ";
			if(propData.getCheckDate().equals("6"))  //车上物
			code="08";
			
		}
		SpinnerUtils.setPropSpinnerData(this, loss_PropInsTypeSp, dicConfigMap.get("ProDateInsType"), code); //险别
	
		code =propData.getCheckSite();
		///if(StringUtils.isEmpty(code)){
			code="03";
		///}		
		SpinnerUtils.setPropSpinnerData(this, loss_PropMtypeSp, dicConfigMap.get("ProDateWuType"), code); //费用名称
		loss_PropTypeSp.setOnItemSelectedListener(onItemSelectClick);
	}

	private OnItemSelectedListener onItemSelectClick = new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		{
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{
			System.out.println("--------");
		}
	};
	private OnTouchListener onTouchListener= new OnTouchListener() {  
		  
        @Override 
        public boolean onTouch(View v, MotionEvent event) {  
            // TODO Auto-generated method stub  
            pop.dismiss();
            pop=null;
            adapter=null;
            isShow = false;  
            String name =(String)v.getTag();
            propSrcExt.setText(name);  
            return true;  
        }  
    }; 


	public Intent getData()
	{
		String checkPropId = SpinnerUtils.getKey(loss_PropTypeSp.getSelectedItem().toString(), dicConfigMap.get("ProDateType"));
		propData.setCheckDate(checkPropId);
		
		String lossParty =propPartExt.getText().toString();
		propData.setLossParty(lossParty);
		
		
		String reserveFlag = SpinnerUtils.getKey(loss_PropInsTypeSp.getSelectedItem().toString(), dicConfigMap.get("ProDateInsType"));
		propData.setReserveFlag(reserveFlag);
	
		String checkSite = SpinnerUtils.getKey(loss_PropMtypeSp.getSelectedItem().toString(), dicConfigMap.get("ProDateWuType"));
		propData.setCheckSite(checkSite);
		
		String lossFee =dangerMoneyExt.getText().toString();
		propData.setSumLossFee(lossFee);
		
		String relatePersonName = propSrcExt.getText().toString();
		propData.setRelatePersonName(relatePersonName);
		
		String rescueInfo =propDescExt.getText().toString();
		propData.setRescueInfo(rescueInfo);
		
		Intent data = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("propData", propData); // 详细信息
		data.putExtra("item", bundle);
		return data;	
    }

	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.ibtn_dropDown: //选择物损属情
			if (pop == null) 
			{  
				if (adapter == null) 
				{  
				   adapter = new PopupAdapter(mContext,licenseNos,onTouchListener);  
				   listView = new ListView(mContext);  
				   pop = new PopupWindow(listView, propSrcExt.getWidth(), LayoutParams.WRAP_CONTENT);  
				   listView.setAdapter(adapter);  
				   pop.showAsDropDown(propSrcExt);  
				   isShow = true;  
				}  
			 } 
			 else if (isShow) 
			 {  
				pop.dismiss();  
				isShow = false;  
			 } 
			 else if (!isShow) 
			 {  
				pop.showAsDropDown(propSrcExt);  
				isShow = true;  
			 } 			 
			 break;
		case R.id.saveCarBtn:
			String errorMsg = check();
			if (StringUtils.isEmpty(errorMsg))
			{
				Intent data = getData();
				this.setResult(4443, data);
				try
				{
					finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				ToastDialog.show(this, errorMsg, ToastDialog.ERROR);
			}
			break;
		case R.id.backCarBtn:
			try
			{
				SurveyAddPropAct.this.finish();
				ActivityManage.getInstance().pop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		}
	}
	public String check()
	{
		String errorMsg = null;
		try{
			if (loss_PropTypeSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("损失类别不能空");
			}
			else if (propPartExt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("损失名称不能空");
			}
			else if (loss_PropInsTypeSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("险别不能空");
			}
			else if (loss_PropMtypeSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("费用名称不能空");
			}
			else if (propSrcExt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("物损属性");
			}
			
		}
		catch (Exception e)
		{
			errorMsg = e.getMessage();
		}

		return errorMsg;
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		try
		{
			this.finish();
			// ActivityManage.getInstance().pop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 取消键盘的弹起
	 */
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{

			if (getCurrentFocus() != null)
			{
				if (getCurrentFocus().getWindowToken() != null)
				{
					imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 拦截返回按钮事件
	 */
	public void onBackPressed()
	{
		super.onBackPressed();
		try
		{
			this.finish();
			ActivityManage.getInstance().pop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		if (getCurrentFocus() != null)
		{
			if (getCurrentFocus().getWindowToken() != null)
			{
				imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.dispatchTouchEvent(ev);
	}
}
