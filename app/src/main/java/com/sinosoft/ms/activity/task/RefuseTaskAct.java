package com.sinosoft.ms.activity.task;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.service.impl.TaskCenterService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;

/**
 * 系统名：移动查勘定损 子系统名：任务中心（拒绝任务） 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午5:20:31
 */

public class RefuseTaskAct extends Activity implements OnClickListener {
	private Button refuseBtn, backBtn;
	private EditText reportNoEdt, reporterEdt, accidentTimeEdt, reportTimeEdt;
	private EditText phoneEdt, accidentReasonEdt, accidentAddEdt,
			refuseReasonEdt;

	private ProgressDialogUtil progressDialog;
	private RegistData bean;
	private InputMethodManager imm;
    private Dialog dialog;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		refuseBtn = backBtn = null;
		reportNoEdt = reporterEdt = accidentTimeEdt = reportTimeEdt = null;
		phoneEdt = accidentReasonEdt = accidentAddEdt = refuseReasonEdt = null;
		progressDialog = null;
		bean = null;
		imm = null;
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_task_refuse);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		ActivityManage.getInstance().push(this);
		// 加载视图
		loadController();
		// 加载数据
		loadData();

		// 添加监听
		refuseBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);

	}

	protected void loadController() {

		refuseBtn = (Button) findViewById(R.id.refuse_task_refuseBtn);
		backBtn = (Button) findViewById(R.id.refuse_task_backBtn);

		reportNoEdt = (EditText) findViewById(R.id.refuse_report_no);
		reporterEdt = (EditText) findViewById(R.id.refuse_reporter);
		accidentTimeEdt = (EditText) findViewById(R.id.refuse_accident_time);
		reportTimeEdt = (EditText) findViewById(R.id.refuse_report_time);
		phoneEdt = (EditText) findViewById(R.id.refuse_phone);
		accidentReasonEdt = (EditText) findViewById(R.id.refuse_accident_reason);
		accidentAddEdt = (EditText) findViewById(R.id.refuse_accident_addr);
		refuseReasonEdt = (EditText) findViewById(R.id.refuse_reason);
	}

	public void loadData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("task");

		if (bundle != null) {

			bean = (RegistData) bundle.getSerializable("taskBean");
			if (bean != null) {
				reportNoEdt.setText(bean.getRegistNo());
				reporterEdt.setText(bean.getReportorName());
				accidentTimeEdt.setText(bean.getDamageDate());
				reportTimeEdt.setText(bean.getReportDate());
				phoneEdt.setText(bean.getReportorNumber());
				accidentReasonEdt.setText(bean.getDamageName());
				accidentAddEdt.setText(bean.getDamageAddress());
			}

		}

	}

	/**
	 * 判断拒绝原因是否为空
	 * 
	 * @return
	 */
	public boolean check() {
		if (refuseReasonEdt.getText().toString().trim().equals("")) {
			return false;
		}
		return true;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refuse_task_refuseBtn:
			if (check()) {
				if (progressDialog == null) {
					progressDialog = new ProgressDialogUtil(this);
				}
				progressDialog.show("拒绝任务中。。");
				new Thread() {
					public void run() {
						Message msg = new Message();
						try {
							bean.setStatus(3);
							bean.setRemark(refuseReasonEdt.getText().toString());
							TaskCenterService taskService = new TaskCenterService();
							taskService.rejectTtask(bean, refuseReasonEdt
									.getText().toString());
							msg.what = 1;
						} catch (Exception e) {
							msg.what = MyUtils.getErrorCode(e.getMessage());
							msg.obj = e.getMessage();
						} finally {
							handler.sendMessage(msg);
						}

					}

				}.start();
			} else {
				ToastDialog.show(this, "请填写拒绝原因", ToastDialog.ERROR);

			}

			break;
		case R.id.refuse_task_backBtn:
			finish();
			break;

		}

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 0表示数据出错
			// 1表示成功
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			final Intent intent = new Intent();
			switch (msg.what) {
			case 0:
				ToastDialog.show(RefuseTaskAct.this, msg.obj.toString()
						+ ",请稍后再试", ToastDialog.ERROR);
				break;
			case 1:
				
				if(dialog!=null){
					dialog.dismiss();
				}
				dialog= CustomDialog.show(RefuseTaskAct.this , "", "任务拒绝成功","确定","",new OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							dialog.dismiss();
							intent.putExtra("success", "success");
							setResult(10004, intent);
							RefuseTaskAct.this.finish();
							ActivityManage.getInstance().pop();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, null);
				
				//ToastDialog.show(RefuseTaskAct.this, "任务拒绝成功", ToastDialog.INFO);

				
				
				break;
			default:
				if (progressDialog != null) {
					progressDialog.dismiss();
				}

				break;
			case AppConstants.NO_LOGIN_CODE:
				intent.setClass(RefuseTaskAct.this, LoginAct.class);
				startActivity(intent);
				try {
					RefuseTaskAct.this.finish();
					ActivityManage.getInstance().pop();
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			}

		}

	};

	protected void onPause() {
		super.onPause();
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

	};

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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		try {
			this.finish();
			ActivityManage.getInstance().pop() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
