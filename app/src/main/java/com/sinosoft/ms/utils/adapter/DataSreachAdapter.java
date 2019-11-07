package com.sinosoft.ms.utils.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.TestData;
import com.sinosoft.ms.activity.query.BluetoothActivity;
import com.sinosoft.ms.activity.query.LossDetailsAct;
import com.sinosoft.ms.activity.query.SurveyDetailsAct;
import com.sinosoft.ms.activity.query.TaskProgressAct;
import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.SurveyInfo;
import com.sinosoft.ms.service.ILossSreachService;
import com.sinosoft.ms.service.IPrintInfoService;
import com.sinosoft.ms.service.impl.CaseCenterService;
import com.sinosoft.ms.service.impl.DeflossDataService;
import com.sinosoft.ms.service.impl.LossSreachService;
import com.sinosoft.ms.service.impl.PrintInfoService;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名：数据查询列表适配器 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 07 13:11:00 CST 2013
 */
public class DataSreachAdapter implements ExpandableListAdapter {

	private List<RegistData> list;
	private Context context;
	private String registId;

	/**
	 * @return the registId
	 */
	public String getRegistId() {
		return registId;
	}

	/**
	 * @param registId the registId to set
	 */
	public void setRegistId(String registId) {
		this.registId = registId;
	}

	public DataSreachAdapter(List<RegistData> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return list.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View childView = View
				.inflate(context, R.layout.item_data_sreach_child, null);
		TextView insuranceTime = (TextView) childView
				.findViewById(R.id.insurance_time);
		TextView insuranceReason = (TextView) childView
				.findViewById(R.id.insurance_reason);

		final RegistData bean = list.get(groupPosition);
		insuranceTime.setText(bean.getDamageDate());
		insuranceReason.setText(bean.getDamageName());
		return childView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return 1;
	}

	@Override
	public long getCombinedChildId(long groupId, long childId) {

		return childId;
	}

	@Override
	public long getCombinedGroupId(long groupId) {

		return groupId;
	}

	@Override
	public Object getGroup(int groupPosition) {

		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {

		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View parentView = View.inflate(context, R.layout.item_data_sreach_group,
				null);
		TextView reportNo = (TextView) parentView.findViewById(R.id.report_no);
		TextView taskType = (TextView) parentView.findViewById(R.id.task_type);
		TextView reportPeople = (TextView) parentView
				.findViewById(R.id.report_people);
		TextView insuranceAddr = (TextView) parentView
				.findViewById(R.id.insurance_addr);
		TextView reportTime = (TextView) parentView
				.findViewById(R.id.report_time);
		LinearLayout optionLayout = (LinearLayout) parentView
				.findViewById(R.id.optionLayout);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT) ;
		params.setMargins(0, 5, 5, 5) ;
		final RegistData bean = list.get(groupPosition);
		reportNo.setText(bean.getRegistNo());
		taskType.setText(MyUtils.getTaskTypeString((int)bean.getTaskType()));
		reportPeople.setText(bean.getReportorName());
		insuranceAddr.setText(bean.getDamageAddress());
		reportTime.setText(bean.getReportDate());
		
		Button a = new Button(context);

		a.setText(R.string.detail);
		a.setTextColor(Color.WHITE);
		a.setBackgroundResource(R.drawable.sm_loss_btn);
		a.setLayoutParams(params) ;
		optionLayout.addView(a);

		
		Button printBtn = new Button(context);
		printBtn.setOnClickListener(new PrintOnClickListener(bean)) ;
		printBtn.setText("打印");
		printBtn.setTextColor(Color.WHITE);
		printBtn.setBackgroundResource(R.drawable.sm_loss_btn);
		printBtn.setLayoutParams(params) ;
		
		// 查勘打印
		if(0 == bean.getTaskType()){
			printBtn.setOnClickListener(new PrintOnClickListener(bean)) ;
		}else{ // 定损打印
			printBtn.setOnClickListener(new PrintLossOnClickListener(bean)) ;
		}
		optionLayout.addView(printBtn);
		Button taskProgressBtn = new Button(context);
		taskProgressBtn.setTextColor(Color.WHITE);
		taskProgressBtn.setText("进度");
		taskProgressBtn.setBackgroundResource(R.drawable.sm_loss_btn);
		taskProgressBtn.setLayoutParams(params) ;
		optionLayout.addView(taskProgressBtn);

		a.setTag(groupPosition);
		taskProgressBtn.setTag(groupPosition);
		a.setOnClickListener(new ToLookListener());
		taskProgressBtn.setOnClickListener(new ToLookListener());
		return parentView;
	}

	
	// 查勘打印事件
	class PrintOnClickListener implements OnClickListener{
		ProgressDialogUtil progressDialog;
		private RegistData data ;
		
		public PrintOnClickListener(RegistData bean){
			data = bean ;
		}
		
		@Override
		public void onClick(View view) {
			progressDialog = new ProgressDialogUtil(context);
			progressDialog.show("数据加载中....");
			new Thread(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						requestSurveyInfo(context, data.getRegistNo(),String.valueOf(data.getRegistId()));//String.valueOf(data.getRegistId()
						handler.sendEmptyMessage(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}.start();
		}
		
		Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				Intent intent2 = new Intent(context, BluetoothActivity.class);
				intent2.putExtra("registId", registId);
				String title = "      移动查勘报告打印凭证";
				TestData.setTitle(title);
				IPrintInfoService printInfoService = new PrintInfoService();
				String surveyPrint = printInfoService.surveyVoucher(context,data.getRegistNo(),data);
				TestData.setContent(surveyPrint);
				context.startActivity(intent2);
			}
			
		};
		
		/**
		 * 查勘详情请求
		 * @throws Exception
		 */
		public void requestSurveyInfo(Context context,String registNo, String registId) throws Exception {
			SurveyTreatmentDatabase2 database = SurveyTreatmentDatabase2.getInstance();

			if (database.isExist(context, registNo)) {

			} else {

				CaseCenterService service = new CaseCenterService(null);
				SurveyInfo surveyInfo = service.querySurveyInfo(registNo,registId);
				BasicSurvey basicSurvey = surveyInfo.getBasicSurvey();

				List<CarData> carDatas = surveyInfo.getCarDatas();// 车辆信息.i("请求查勘数据","取得数据");
				// personData = surveyInfo.getPersonData();// 人伤信息tch(Exception
				// e){
				List<PolicyData> policyDatas = surveyInfo.getPolicyDatas();// 保单信息e.printStackTrace();
				List<PropData> proDatas = surveyInfo.getPropDatas();// 财产信息
				List<LiabilityRatio> LibbilityRadios = surveyInfo.getLiabilityRatios();
				List<RegistThirdCarData> registThirdCarDatas = surveyInfo
						.getRegistThirdCarDatas();

				database.setBasicSurvey(basicSurvey);
				database.setCarDatas(carDatas);
				// database.setPersonData(personData);
				database.setPolicyDatas(policyDatas);
				database.setProDatas(proDatas);
				database.setLiabilityRatios(LibbilityRadios);
				database.setRegistThirdCarDatas(registThirdCarDatas);
				database.save(context);
			}
		}
		
	}
	
	// 定损打印事件
	class PrintLossOnClickListener implements OnClickListener{
		ProgressDialogUtil progressDialog;
		private RegistData data ;
		private DeflossData deflossData;
		
		public PrintLossOnClickListener(RegistData bean){
			data = bean ;
		}
		
		@Override
		public void onClick(View view) {
			progressDialog = new ProgressDialogUtil(context);
			progressDialog.show("数据加载中....");
			new Thread(){

				@Override
				public void run() {
					super.run();
					try {
						requestLossInfo(data.getTaskId(),data.getRegistNo(),data.getRegistId());
						handler.sendEmptyMessage(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}.start();
			
		}
		
		Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				PrintInfoService printInfoService = new PrintInfoService();
				String printContext = printInfoService.lossVoucher(context,deflossData,data.getTaskId(),data.getReportorNumber());
				TestData.setTitle("\n");
				TestData.setContent(printContext);
				Intent intent2 = new Intent(context,
						BluetoothActivity.class);
				intent2.putExtra("registNo", data.getRegistNo());
				intent2.putExtra("taskId", data.getTaskId());
				intent2.putExtra("registId", data.getRegistId());
				intent2.putExtra("checkLoss", true);
				intent2.putExtra("content", printContext);
				context.startActivity(intent2);
			}
			
		};
		
		public void requestLossInfo(String taskId,String registNo, String registId){
			try {
				DeflossDataService deflossDataService = new DeflossDataService(context);
				deflossData = deflossDataService.getByTaskId(taskId);
				if (null == deflossData) {
					ILossSreachService iLossSreachService = new LossSreachService();
					deflossData = iLossSreachService.sreach(UserCashe
							.getInstant().getUser().getName(), taskId,
							registNo, registId);
				}
				deflossData.init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		
	}
	
	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {

	}

	@Override
	public void onGroupExpanded(int groupPosition) {

	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

	class ToLookListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			if (v instanceof Button) {
				Button btn = (Button) v;
				String btnText = btn.getText().toString();
				int tag=(Integer) btn.getTag();
				RegistData registData=list.get(tag);
				if (btnText.equals(context.getResources().getString(
						R.string.detail))) {
					Long  taskType=registData.getTaskType();
					if(taskType==0){
					Intent intent = new Intent(context,
							SurveyDetailsAct.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("bean", registData);
					
					intent.putExtra("registNo",registData.getRegistNo());
					intent.putExtra("registId",registData.getRegistId());
                    intent.putExtra("item", bundle);
                    intent.putExtra("canEdit", false);
					context.startActivity(intent);
				} else {
					Intent intent = new Intent(context,
							LossDetailsAct.class);
					intent.putExtra("registNo",
							registData.getRegistNo());
					intent.putExtra("registId", registData.getRegistId());
					intent.putExtra("taskId", registData.getTaskId());
					Bundle bundle=new Bundle();
					bundle.putSerializable("bean", registData);
					intent.putExtra("item", bundle);
					intent.putExtra("canEdit", false);
					context.startActivity(intent);
				  }
			}  else if (btnText.equals("进度")) {
					Intent intent=new Intent(context,TaskProgressAct.class);
					intent.putExtra("registNo", registData.getRegistNo());
					intent.putExtra("registId", String.valueOf(registData.getId()));
					intent.putExtra("taskId", registData.getTaskId());
					context.startActivity(intent);

				}else {
					Toast.makeText(context, "无选择项", Toast.LENGTH_LONG).show();
				}

			}

		}

	}
}
