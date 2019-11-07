package com.sinosoft.ms.activity.query;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.sub.ParamBean;
import com.sinosoft.ms.service.IDataSreachService;
import com.sinosoft.ms.service.impl.DataSreachService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.adapter.DataSreachAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.component.datadialog.DateDialogUtils;

/**
 * 系统名：移动查勘定损 子系统名：数据查询-查询结果界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午11:51:11
 */
public class DataSreachListAct extends Activity implements OnClickListener{
	private ExpandableListView dataSreachList = null;
	private DataSreachAdapter adapter = null;
	private List<RegistData> list = null;
	private Button dataBrackBtn = null;
	private ProgressDialogUtil progressDialog = null;
	private IDataSreachService service = null;
    private ParamBean searchBean = null;
    private Button mDataSearchBtn;
    private View dialogLayout;
    private Dialog dialog,dataSearchDialog;
    
    //DataSreachAct.java
    private Button dataSreachBtn;//
	private EditText reportNoExt;// 报案号
	private CheckBox surveyCb;// 任务类型
	private CheckBox retreatLossCb;	//核损退回
	private CheckBox retreatCountLossCb;//理算退回
	private CheckBox privateSurveyCb;	//私内查勘
	private CheckBox lossCb;
	private Button reportTimeFromBtn;// 报案日期起
	private Button reportTimeToBtn;// 报案日期终
	private EditText phoneExt;// 联系电话
	private EditText reportPeopleExt;// 联系人
	private Button insuranceTimeFromBtn;// 出险日期起
	private Button insuranceTimeToBtn;// 出险日期终

	/** 封装日期控件 */
	private DateDialogUtils dateDialogUtils = null;
	/** 声明按钮lock **/
	public static boolean lock = false;
	private int y, m, d, hour, min;
	private InputMethodManager imm;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ActivityManage.getInstance().push(this);
		setContentView(R.layout.activity_data_sreach_list);

		dateDialogUtils = new DateDialogUtils();
		// 设置视图控件
		setViewControl();
	}

	/**
	 * 
	 */
	private void loadDialogView() {
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		// 设置视图控件
		setDialogViewControl();
		// 加载数据
		loadData();
		// 设置控件事件
		dataSreachBtn.setOnClickListener(new DataSreachListener());
		dataBrackBtn.setOnClickListener(new DataSreachListener());

		reportTimeFromBtn
				.setOnClickListener(new DateBtnClick(reportTimeFromBtn));
		reportTimeToBtn.setOnClickListener(new DateBtnClick(reportTimeToBtn));
		insuranceTimeFromBtn.setOnClickListener(new DateBtnClick(
				insuranceTimeFromBtn));
		insuranceTimeToBtn.setOnClickListener(new DateBtnClick(
				insuranceTimeToBtn));
		ActivityManage.getInstance().push(this);
	}

	/**
	 * 设置视图控件
	 */
	private void setViewControl() {
		dataSreachList = (ExpandableListView) findViewById(R.id.dataSreachList);
		dataBrackBtn = (Button) findViewById(R.id.dataBrackBtn);
		dataBrackBtn.setOnClickListener(this);
		mDataSearchBtn = (Button) findViewById(R.id.dataSearchBtn);
		mDataSearchBtn.setOnClickListener(this);
	}
	
	/**
	 * 
	 */
	private void setDialogViewControl() {
		dataSreachBtn = (Button) dialogLayout.findViewById(R.id.dataSreachBtn);
		dataBrackBtn = (Button) dialogLayout.findViewById(R.id.dataBrackBtn);
		reportNoExt = (EditText) dialogLayout.findViewById(R.id.reportNoExt);
		surveyCb = (CheckBox) dialogLayout.findViewById(R.id.surveyCb);
		retreatLossCb = (CheckBox) dialogLayout.findViewById(R.id.retreatLossCB);
		retreatCountLossCb = (CheckBox) dialogLayout.findViewById(R.id.retreatCountLossCB);
		privateSurveyCb = (CheckBox) dialogLayout.findViewById(R.id.privateSurveyCB);
		lossCb = (CheckBox) dialogLayout.findViewById(R.id.lossCb);

		reportTimeFromBtn = (Button) dialogLayout.findViewById(R.id.reportTimeFromBtn);
		reportTimeToBtn = (Button) dialogLayout.findViewById(R.id.reportTimeToBtn);
		phoneExt = (EditText) dialogLayout.findViewById(R.id.contactPhoneExt);
		reportPeopleExt = (EditText) dialogLayout.findViewById(R.id.contactPersonExt);
		insuranceTimeFromBtn = (Button) dialogLayout.findViewById(R.id.insuranceTimeFromBtn);
		insuranceTimeToBtn = (Button) dialogLayout.findViewById(R.id.insuranceTimeToBtn);
	}
	
	/**
	 * 取得要查询的条件
	 * 
	 * @return 取得数据对象
	 */
	public ParamBean getSearchWord() {
		ParamBean bean = new ParamBean();
		bean.setReportNo(reportNoExt.getText().toString());
		String taskType = "";
		List<String> list = new ArrayList<String>();
		if(surveyCb.isChecked()){
			list.add("0");
		}
		if(lossCb.isChecked()){
			list.add("1");
		}
		if(retreatLossCb.isChecked()){
			list.add("3");
		}
		if(privateSurveyCb.isChecked()){
			list.add("4");
		}
		if(retreatCountLossCb.isChecked()){
			list.add("5");
		}
		for(int i = 0 ; i < list.size() ; i++){
			//如果还有下一个，则使用逗号隔开
			if(i == 0){
				taskType = list.get(i);
			}else{
				taskType = taskType +","+ list.get(i);
			}
		}
		bean.setRegistId("");
		bean.setTaskTypeSreach(taskType);
		bean.setContactPhone(phoneExt.getText().toString());
		bean.setContactPerson(reportPeopleExt.getText().toString());
		bean.setReportTimeFrom(reportTimeFromBtn.getText().toString());
		bean.setReportTimeTo(reportTimeToBtn.getText().toString());
		bean.setInsuranceTimeFrom(insuranceTimeFromBtn.getText().toString());
		bean.setInsuranceTimeTo(insuranceTimeToBtn.getText().toString());

		return bean;
	}
	
	/**
	 * 数据查询事件处理
	 * 
	 * @author linianchun
	 * 
	 */
	class DataSreachListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.dataBrackBtn:
//				try {
//					DataSreachListAct.this.finish();
//					ActivityManage.getInstance().pop();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				dataSearchDialog.dismiss();
				break;
			case R.id.dataSreachBtn:
//				Intent intent = getIntent();
//				intent.setClass(DataSreachListAct.this, DataSreachListAct.class);
//				ParamBean bean = getSearchWord();
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("bean", bean);
//				intent.putExtra("item", bundle);
//				startActivity(intent);
				if (dataSearchDialog.isShowing()) {
					dataSearchDialog.dismiss();
				}
//				list.clear();
				// 加载列表数据
				loadListData();
				break;
			}
		}
	}

	/*
	 * 
	 * 下面用于点击文本外隐藏键盘
	 */
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null) {
				if (getCurrentFocus().getWindowToken() != null) {
					imm.hideSoftInputFromWindow(this.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}
	
	class DateBtnClick implements OnClickListener {
		Button dateBtn = null;

		public DateBtnClick(Button dateButton) {
			this.dateBtn = dateButton;
		}

		@Override
		public void onClick(View v) {

//			if (!lock) {
				int curYear, curMonth, curDay;
				curYear = Integer.valueOf(String.valueOf(dateBtn.getText())
						.replaceAll("-", "").trim().substring(0, 4));
				curMonth = Integer.valueOf(String.valueOf(dateBtn.getText())
						.replaceAll("-", "").trim().substring(4, 6)) - 1;
				curDay = Integer.valueOf(String.valueOf(dateBtn.getText())
						.replaceAll("-", "").trim().substring(6, 8));
				dateDialogUtils.dateDialog(DataSreachListAct.this, dateBtn,
						curYear, curMonth, curDay);
				lock = true;
//			}
		}
	}
	
	/**
	 * 加载数据
	 */
	private void loadData() {
//		Calendar cal;
		// 获得日期实例
//		cal = Calendar.getInstance();
		// 获取相应属性
//		y = cal.get(Calendar.YEAR);
//		m = cal.get(Calendar.MONTH);
//		d = cal.get(Calendar.DAY_OF_MONTH);
//		hour = cal.get(Calendar.HOUR_OF_DAY);
//		min = cal.get(Calendar.MINUTE);
		//获取日期实例
		DateTimeFactory factory = DateTimeFactory.getInstance();
		y = factory.getYear();
		m = factory.getMonth();
		d = factory.getDate();
		
		reportTimeFromBtn.setText(String.valueOf(y) + "-"
				+ String.valueOf(m + 100).substring(1) + "-"
				+ String.valueOf(d + 100).substring(1));
		reportTimeToBtn.setText(String.valueOf(y) + "-"
				+ String.valueOf(m + 101).substring(1) + "-"
				+ String.valueOf(d + 100).substring(1));
		insuranceTimeFromBtn.setText(String.valueOf(y) + "-"
				+ String.valueOf(m + 100).substring(1) + "-"
				+ String.valueOf(d + 100).substring(1));
		insuranceTimeToBtn.setText(String.valueOf(y) + "-"
				+ String.valueOf(m + 101).substring(1) + "-"
				+ String.valueOf(d + 100).substring(1));
	}

	/**
	 * 加载列表数据
	 */
	private void loadListData() {
		searchBean = getSearchWord();
		if (searchBean == null) {
			searchBean = new ParamBean();
		}
		
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("数据加载中....");
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					// 取得查询条件
					service = new DataSreachService();
					list = service.execute(searchBean);
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = MyUtils.getErrorCode(e.getMessage());
					msg.obj = "" + e.getMessage();
				} finally {
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

	// 页面更新
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				// 测试
				CustomDialog.show(DataSreachListAct.this, "信息提示", "错误:"
						+ msg.obj.toString());
				if (list == null) {
					list = new ArrayList<RegistData>();
				}
				adapter = new DataSreachAdapter(list, DataSreachListAct.this);
//				adapter.setRegistId(registId);
				dataSreachList.setAdapter(adapter);

				break;
			case 1:
				if (list.size() == 0) {
					ToastDialog.show(DataSreachListAct.this, "数据查询为空",ToastDialog.ERROR);
				} else if (list.size() > 0) {
					adapter = new DataSreachAdapter(list, DataSreachListAct.this);
//					adapter.setRegistId(registId);
					dataSreachList.setAdapter(adapter);
				}
				break;
			case AppConstants.NO_LOGIN_CODE:
				Intent intent = getIntent();
				intent.setClass(DataSreachListAct.this,LoginAct.class);
				startActivity(intent);
				try{
					DataSreachListAct.this.finish();
					ActivityManage.getInstance().pop();
				}catch(Exception e){
				}
				break;
			}
		};
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dataSreachList = null;
		adapter = null;
		if (null != list) {
			list.clear();
			list = null;
		}
		dataBrackBtn = null;
		progressDialog = null;
		service = null;
	    searchBean = null;
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dataSearchBtn:{
			LayoutInflater inflater = getLayoutInflater();
			dialogLayout = inflater.inflate(R.layout.layout_data_sreach,
					(ViewGroup) findViewById(R.id.data_sreach_dialog));
//			lock = false;
			loadDialogView();
			dataSearchDialog = CustomDialog.showDataSearch(DataSreachListAct.this, "查询", dialogLayout);
		}
			break;
		case R.id.dataBrackBtn:
			DataSreachListAct.this.finish();
			try {
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
}
