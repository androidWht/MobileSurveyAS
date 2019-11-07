package com.sinosoft.ms.activity.task.survey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PersonDetailData;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.IdcardValidator;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.adapter.ExpandableUtil;
import com.sinosoft.ms.utils.adapter.RiskSituationListAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
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

public class SurveyAddPersonAct extends Activity implements OnClickListener
{

	private InputMethodManager imm;
	private Map<String, Map<String, String>> dicMap;// 字典信息
	private Map<String, Map<String, String>> dicConfigMap;



	
	
	// 所有伤情类别
	private String totalFeedbacks[] = null;
	// 所有是否选中伤情类别
	private boolean totalFeedbacksChecked[] = null;
	private String[] surveyIdeas;
	private String[] surveyIdeasCode;
	/** 是否选中查勘意见 **/
	private boolean surveyIdeasSelected[];

	
	private  Context mContext;
	
	private  Spinner  personTypeSp;      //人员类型
	private  EditText personNameExt;     //受伤人员姓名
	private  Spinner  personCertTypeSp;  //证件类型
	private  EditText personCertExt;     //证件号码
	private  Spinner  lossPersonSexSp;   //受伤人员性别
	private  EditText personInfoExt;     //人伤属性
	private  Spinner lossPersonDivSp;    //是否死亡
	
	private  ImageButton personDropDown;
	private  Button selectPersonOptionBtn;
	
	private  Button selectOptionBtn; // 伤情类别选择
	private  EditText personTypeDisp; //伤情类别显示	
	
	private  ExpandableListView riskSituation; //险别情况	
	private  RiskSituationListAdapter riskSituationAdapter; 
	
	private Button saveCarBtn; // 保存
	private Button backCarBtn; // 返回	
	
	
	
	
	private ImageButton ibtn_dropDown;
	
	private PopupWindow pop;  
	private PopupAdapter adapter;
	private List<String> licenseNos;
	private List<CarData> carDatas;	
	private SurveyTreatmentDatabase2 database;
	private ListView listView; 
	private EditText propSrcExt;
	private boolean isShow = false; 	
	
	
	// 对话框
	private Dialog dialog;	
	private PersonData personData;
	private List<PersonDetailData> personDetailDataList;
	private PersonDetailData personDetailData;
	
	private Spinner personRiskTypeSp; //险别
    
	private Spinner personMoneyNameSp; //费用名称
	
	private EditText personLostMoenyExt; //损失金额
	
	private EditText personLostDecExt; //损失程度描述
	
	private Button saveRiskBtn;   //保存
	private Button cancleRishBtn;  //取消	
	
	private Button addPersonBtn;
	
	private Dialog searchDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey_add_person);
		mContext =this;
		
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		ActivityManage.getInstance().push(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("item");
		
		
		
		if (bundle != null)
		{
			personData = (PersonData) bundle.getSerializable("bean");
		}
		else
		{
			personData = new PersonData();
		}
		
		personDetailDataList = personData.getPersonDetailData();
		if(personDetailDataList == null)
		{	
			personDetailDataList = new ArrayList<PersonDetailData>();
		}
		///personDetailData = personDetailDataList.get(0);
		
		
		
		database = SurveyTreatmentDatabase2.getInstance();
		carDatas = database.getCarDatas();		
		
		this.setViewController(); // 设置控件
		// 获取缓存信息
		dicMap = DictCashe.getInstant().getDict();
		dicConfigMap = AppConstants.getLocalItemConf();
		this.setData(); // 设置处理界面信息
		this.saveCarBtn.setOnClickListener(this);
		this.backCarBtn.setOnClickListener(this);
		this.addPersonBtn.setOnClickListener(this);
	}

	/**
	 * 初始化视图组件
	 */
	public void setViewController()
	{
	
		this.personTypeSp= (Spinner) super.findViewById(R.id.personTypeSp); //人员类型
		this.personNameExt = (EditText) super.findViewById(R.id.personNameExt); //受伤人员姓名
		this.personCertTypeSp= (Spinner) super.findViewById(R.id.personCertTypeSp);  //证件类型
		this.personCertExt = (EditText) super.findViewById(R.id.personCertExt);  //证件号码
		this.lossPersonSexSp= (Spinner) super.findViewById(R.id.lossPersonSexSp); //受伤人员性别
		this.personInfoExt = (EditText) super.findViewById(R.id.personInfoExt);  //人伤属性
		
		this.lossPersonDivSp = (Spinner) super.findViewById(R.id.lossPersonDivSp); //是否死亡
		
		this.personDropDown = (ImageButton) super.findViewById(R.id.personDropDown); //物损属情
		this.personDropDown.setOnClickListener(this);
		
		this.selectPersonOptionBtn = (Button) super.findViewById(R.id.selectPersonOptionBtn); //伤情类别选择
		this.selectPersonOptionBtn.setOnClickListener(this);
		
		this.selectOptionBtn= (Button) findViewById(R.id.selectOptionBtn); // 伤情类别选择
		this.personTypeDisp = (EditText) super.findViewById(R.id.personTypeDisp); //伤情类别显示
		
		this.riskSituation = (ExpandableListView) findViewById(R.id.riskSituation);
		
		
		this.saveCarBtn = (Button) findViewById(R.id.saveCarBtn); // 保存
		this.backCarBtn = (Button) findViewById(R.id.backCarBtn); // 返回	
		this.addPersonBtn = (Button) findViewById(R.id.addPersonBtn);
	}
	
	/**
	 * 设置界面数据
	 */
	public void setData()
	{
		surveyIdeas = new String[] { "01 颅脑损伤","02 容貌损伤","03 颈部损伤",	"04 颈椎损伤","05 锁骨骨折",    
				"06 胸骨骨折","07 肋骨骨折","08 脊柱骨折","09 脊髓骨折",	"10 盆骨骨折","11 股骨头骨折",  
				"12 上臂骨折","13 下肢骨折","14 手部骨折","15 足部骨折",	"16 心脏损伤","17 肺部损伤",    
				"18 肝脏损伤","19 脾脏损伤","20 胰脏损伤","21 肾脏损伤",	"22 其他内脏损伤","23	软组织挫伤","24 其他损伤",    };
		
		surveyIdeasCode = new String[]{ "01", "02", "03", "04", "05", "06", "07", "08", "09",	"10", "11",  
				                        "12", "13", "14", "15",	"16", "17", "18", "19", "20",   "21", "22",
				                        "23", "24",    };
		
		
		String saveCode =personData.getCheckSite();//保存的伤情险别
		surveyIdeasSelected= new boolean[surveyIdeas.length];
		
		setSurveyFeedbacks();
		
		String disp="";
		for(int i=0;i<surveyIdeas.length;i++)
		{
			///surveyIdeasSelected[i]=false;

			String code =surveyIdeasCode[i];
			if(StringUtils.isNotEmpty(saveCode))
			{
				if(saveCode.indexOf(code) != -1)
				{
					totalFeedbacksChecked[i]=true;
					disp+=surveyIdeas[i];
					disp+=";";
				}
			}
		}
		personTypeDisp.setText(disp);
		
		
		licenseNos = new ArrayList<String>(); 
		if(carDatas!=null && carDatas.size()>0){
			for(int i=0;i<carDatas.size();i++){
			
				CarData carData = carDatas.get(i);
			    if(StringUtils.isNotEmpty(carData.getLicenseNo()))
				  licenseNos.add(carData.getLicenseNo());
			}
		}
		
		this.personNameExt.setText(personData.getLossParty()); //姓名
		this.personCertExt.setText(personData.getCheckDate()); //证件号码
		this.personInfoExt.setText(personData.getModfiyOwner()); //人伤属情
		
		
		SpinnerUtils.setPersonSpinnerData(this, personTypeSp, dicConfigMap.get("personDataBDType"),personData.getCreateOwner());// 损失类别
		SpinnerUtils.setPersonSpinnerData(this, personCertTypeSp, dicConfigMap.get("certificatesType"),personData.getRescueFee());// 证件类型
		SpinnerUtils.setPersonSpinnerData(this, lossPersonSexSp, dicConfigMap.get("sexType"),personData.getSumLossFee());// 性别
		SpinnerUtils.setPersonSpinnerData(this, lossPersonDivSp, dicConfigMap.get("dieType"),personData.getDeathNum());// 是否死亡
		
		//险别情况
		riskSituationAdapter = new RiskSituationListAdapter(personDetailDataList, mContext, riskClickListener);
		this.riskSituation.setAdapter(riskSituationAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(riskSituation);
	
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
            personInfoExt.setText(name);  
            return true;  
        }  
    }; 
	// 设置查勘意见列表
	private void setSurveyFeedbacks() {
		totalFeedbacks = new String[surveyIdeas.length];
		totalFeedbacksChecked = new boolean[surveyIdeas.length];
		// 获取所有所伤情类别
		for (int i = 0; i < surveyIdeas.length; i++) {
			totalFeedbacks[i] = surveyIdeas[i];
			totalFeedbacksChecked[i] = false;
	   }
	}

    
    

	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.addPersonBtn:  //增加人伤损失
			 LayoutInflater inflater = getLayoutInflater();
			 final View layout = inflater.inflate(R.layout.dialog_person_rish, (ViewGroup) findViewById(R.id.dialog_person_rish));
			 personDetailData =  new PersonDetailData();
				
			 personRiskTypeSp = (Spinner)layout.findViewById(R.id.personRiskTypeSp);      //险别
			 SpinnerUtils.setPersonSpinnerData(this, personRiskTypeSp, dicConfigMap.get("personDataInsType"),personDetailData.getPersonName());// 损失类别
				
				
			 personMoneyNameSp= (Spinner)layout.findViewById(R.id.personMoneyNameSp);     //费用名称
			 SpinnerUtils.setPersonSpinnerData(this, personMoneyNameSp, dicConfigMap.get("personDateMoenyType"),personDetailData.getPersonSex());// 损失类别
	
			 personLostMoenyExt= (EditText)layout.findViewById(R.id.personLostMoenyExt);  //损失金额
			 personLostDecExt= (EditText)layout.findViewById(R.id.personLostDecExt);      //损失程度描述
			 saveRiskBtn= (Button)layout.findViewById(R.id.saveRiskBtn);                  //保存
			 cancleRishBtn= (Button)layout.findViewById(R.id.cancleRishBtn);              //取消
			 saveRiskBtn.setOnClickListener(riskClickListener);
			 cancleRishBtn.setOnClickListener(riskClickListener);
			 searchDialog = CustomDialog.show(mContext, "增加人伤损失", layout);
			 break;
		case R.id.selectPersonOptionBtn: //选择伤情类别
			Dialog dialog = new AlertDialog.Builder(mContext).setTitle("伤情类别").setMultiChoiceItems(
					totalFeedbacks,
					totalFeedbacksChecked,
					new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface arg0,
								int which, boolean isChecked) {
							totalFeedbacksChecked[which] = isChecked;
						}
					})
			       .setPositiveButton("确定",new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0,
								int arg1) {
							StringBuffer msgs = new StringBuffer("");
							StringBuffer smsgs = new StringBuffer("");
							for (int i = 0; i < totalFeedbacksChecked.length; i++) {
								if (totalFeedbacksChecked[i]) 
								{
									msgs.append(totalFeedbacks[i]).append(";");
									smsgs.append(surveyIdeasCode[i]).append(";");
								}
							}
							personTypeDisp.setText(msgs.toString());
							personData.setCheckSite(smsgs.toString());
						}
					})
			       .setNegativeButton("取消",new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0,int arg1) {

						}
					}).create();
	               dialog.show();
			 break;
		case R.id.personDropDown: //选择物损属情
			if (pop == null) 
			{  
				if (adapter == null) 
				{  
				   adapter = new PopupAdapter(mContext,licenseNos,onTouchListener);  
				   listView = new ListView(mContext);  
				   pop = new PopupWindow(listView, personInfoExt.getWidth(), LayoutParams.WRAP_CONTENT);  
				   listView.setAdapter(adapter);  
				   pop.showAsDropDown(personInfoExt);  
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
				pop.showAsDropDown(personInfoExt);  
				isShow = true;  
			 } 			 
			 break;
		case R.id.saveCarBtn:
			String errorMsg = check();
			if (StringUtils.isEmpty(errorMsg))
			{
				Intent data = getData();
				this.setResult(4445, data);
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
				SurveyAddPersonAct.this.finish();
				ActivityManage.getInstance().pop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		}
	}
	public OnClickListener riskClickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			switch (v.getId())
			{
			case R.id.removeBtn: //移除人伤损失
				   final int curr = (Integer) v.getTag();
					// 如果是删除按钮
					dialog = CustomDialog.show(mContext, "信息提示", "是否确定删除?", new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							dialog.dismiss();
							PersonDetailData personDetailData = personDetailDataList.get(curr);
							personDetailDataList.remove(personDetailData);
							riskSituationAdapter = new RiskSituationListAdapter(personDetailDataList, mContext, riskClickListener);
							riskSituation.setAdapter(riskSituationAdapter);
							ExpandableUtil.setListViewHeightBasedOnChildren(riskSituation);						
						}
					}, new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							dialog.dismiss();
						}
					});

				 break;
			
			case R.id.saveRiskBtn:   //保存
				String errorMsg = checkPersonRish();
				if (StringUtils.isEmpty(errorMsg))
				{
					 if (searchDialog.isShowing())
					 {
						searchDialog.dismiss();
					 }
					 String personRiskType = SpinnerUtils.getKey(personRiskTypeSp.getSelectedItem().toString(), dicConfigMap.get("personDataInsType"));
					 personDetailData.setPersonName(personRiskType);

					 String personMoneyName = SpinnerUtils.getKey(personMoneyNameSp.getSelectedItem().toString(), dicConfigMap.get("personDateMoenyType"));
					 personDetailData.setHospitalName(personMoneyName);  //费用名称
					 
					 String personLostMoeny =personLostMoenyExt.getText().toString();
					 personDetailData.setLossFee(personLostMoeny);
					 
					 String personLostDec =personLostDecExt.getText().toString();
					 personDetailData.setWoundDetail(personLostDec);
					 
					 personDetailDataList.add(personDetailData);
					 
					 riskSituationAdapter = new RiskSituationListAdapter(personDetailDataList, mContext, riskClickListener);
					 riskSituation.setAdapter(riskSituationAdapter);
					 ExpandableUtil.setListViewHeightBasedOnChildren(riskSituation);
				}else{
				        ToastDialog.show(mContext, errorMsg, ToastDialog.ERROR);
				}
				 break;
			case R.id.cancleRishBtn:  //取消
				if (searchDialog.isShowing())
				{
					searchDialog.dismiss();
				}
				 break;
			
			}
		}
	};
	public String checkPersonRish()
	{
		String errorMsg = null;
		try{
			if (personRiskTypeSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("险别不能空");
			}
			else if (personMoneyNameSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("费用名称不能空");
			}
			else if (personLostMoenyExt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("损失金额不能空");
			}
    	}
		catch (Exception e)
		{
			errorMsg = e.getMessage();
		}

		return errorMsg;
	}
	
	
	public Intent getData()
	{
		
		String val = SpinnerUtils.getKey(personTypeSp.getSelectedItem().toString(), dicConfigMap.get("personDataBDType"));   //人员类型
		personData.setCreateOwner(val);
		
		val=personNameExt.getText().toString();  //受伤人员姓名
		personData.setLossParty(val);
		
		val = SpinnerUtils.getKey(personCertTypeSp.getSelectedItem().toString(), dicConfigMap.get("certificatesType"));   //证件类型
		personData.setRescueFee(val);
		
		val=personCertExt.getText().toString(); //证件号码
		personData.setCheckDate(val);
		
		val = SpinnerUtils.getKey(lossPersonSexSp.getSelectedItem().toString(), dicConfigMap.get("sexType"));   //受伤人员性别
		personData.setSumLossFee(val);
		
		val = SpinnerUtils.getKey(lossPersonDivSp.getSelectedItem().toString(), dicConfigMap.get("dieType"));   //是否死亡
		personData.setDeathNum(val);		
		
		val = personInfoExt.getText().toString();  //人伤属性
		personData.setModfiyOwner(val);

		personData.setPersonDetailData(personDetailDataList);
		
		Intent data = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("personData", personData); // 详细信息
		data.putExtra("item", bundle);
		return data;	
    }	
	public String check()
	{
		String errorMsg = null;
		try{
			if (personTypeSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("人员类型不能空");
			}
			else if (personNameExt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("受伤人员姓名为能为空");
			}
			else if (personCertTypeSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("证件类型不能空");
			}
			else if (personCertExt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("证件号码不能空");
			}
			else if (lossPersonSexSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("受伤人员性别不能空");
			}
			else if (lossPersonDivSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("是否死亡不能空");
			}			
			else if (personInfoExt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("人伤属性不能空");
			}
			
			//校验身份证合法性
			String val = SpinnerUtils.getKey(personCertTypeSp.getSelectedItem().toString(), dicConfigMap.get("certificatesType"));
			if(StringUtils.isNotEmpty(val))
			{
				if(val.equalsIgnoreCase("01")) //居民身份证
				{
					val=personCertExt.getText().toString(); //证件号码
					if(StringUtils.isNotEmpty(val)){
					   
						boolean ret =IdcardValidator.isValidatedAllIdcard(val);
						if(ret == false)
							throw new IllegalArgumentException("身份证输入错误");	
					}
				}
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
