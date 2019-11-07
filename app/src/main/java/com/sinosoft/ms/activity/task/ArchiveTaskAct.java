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
 * 系统名：移动查勘定损 子系统名：归档任务处理 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午11:16:51
 */

public class ArchiveTaskAct extends Activity implements OnClickListener {
	Button archiveBtn;
	Button backBtn;
	private EditText reportNoEdt, reporterEdt, accidentTimeEdt, reportTimeEdt;
	private EditText phoneEdt, accidentReasonEdt, accidentAddEdt,
			archiveReasonEdt;
	private ProgressDialogUtil progressDialog;
	private RegistData bean;
	private InputMethodManager imm;
	private TaskCenterService taskService = null;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_archive);

		ActivityManage.getInstance().push(this);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		loadController();
		loadData();
		archiveBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);

	}

	public void loadController() {
		archiveBtn = (Button) findViewById(R.id.archive_task_archiveBtn);
		backBtn = (Button) findViewById(R.id.archive_task_backBtn);

		reportNoEdt = (EditText) findViewById(R.id.archive_report_no);
		reporterEdt = (EditText) findViewById(R.id.archive_reporter);
		accidentTimeEdt = (EditText) findViewById(R.id.archive_accident_time);
		reportTimeEdt = (EditText) findViewById(R.id.archive_report_time);
		phoneEdt = (EditText) findViewById(R.id.archive_phone);
		accidentReasonEdt = (EditText) findViewById(R.id.archive_accident_reason);
		accidentAddEdt = (EditText) findViewById(R.id.archive_accident_addr);
		// archiveReasonEdt=(EditText)findViewById(R.id.archive_report_no);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.archive_task_archiveBtn:
			if (progressDialog == null) {
				progressDialog = new ProgressDialogUtil(this);
			}
			progressDialog.show("归档任务中...");
			new Thread() {
				public void run() {
					Message msg = new Message();
					try {
						bean.setStatus(7);
						bean.setRemark("归档任务");
						taskService = new TaskCenterService();
						taskService.archiveTask(bean);
						msg.what = 1;
					} catch (Exception e) {
						msg.what = MyUtils.getErrorCode(e.getMessage());
						msg.obj = e.getMessage();
					} finally {
						handler.sendMessage(msg);
					}
				}

			}.start();
			break;
		case R.id.archive_task_backBtn:
			try {
				ArchiveTaskAct.this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
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

				ToastDialog.show(ArchiveTaskAct.this, msg.obj.toString()
						+ ",请稍后再试", ToastDialog.ERROR);
				break;
			case 1:
				// ToastDialog.show(ArchiveTaskAct.this,
				// "任务归档成功",ToastDialog.INFO);

				if (dialog != null) {
					dialog.dismiss();
				}
				dialog = CustomDialog.show(ArchiveTaskAct.this, "", "任务归档成功",
						"确定", "", new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								intent.putExtra("success", "success");
								setResult(10005, intent);
								try {
									ArchiveTaskAct.this.finish();
									ActivityManage.getInstance().pop();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}, null);

				break;
			case AppConstants.NO_LOGIN_CODE:
				intent.setClass(ArchiveTaskAct.this, LoginAct.class);
				startActivity(intent);
				try {
					ArchiveTaskAct.this.finish();
					ActivityManage.getInstance().pop();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				if (progressDialog != null) {
					progressDialog.dismiss();
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		try {
			this.finish();
			ActivityManage.getInstance().pop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	/**
	 * 销毁资源
	 */
	@Override
	protected void onDestroy() {

		super.onDestroy();
		archiveBtn = null;
		backBtn = null;
		reportNoEdt = reporterEdt = accidentTimeEdt = reportTimeEdt = phoneEdt = accidentReasonEdt = accidentAddEdt = archiveReasonEdt = null;
		progressDialog = null;
		bean = null;
		imm = null;
		taskService = null;
	}

}
