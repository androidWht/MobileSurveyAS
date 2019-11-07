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
import com.sinosoft.ms.utils.LogFatory;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;

/**
 * 系统名：移动查勘定损 子系统名：任务中心 - 接收任务 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午11:56:58
 */

public class AcceptTaskAct extends Activity implements OnClickListener {

	private Button accept_task_backBtn;
	private Button accpet_task_acceptBtn;
	private InputMethodManager imm;

	private EditText reportNoEdt, reporterEdt, accidentTimeEdt, reportTimeEdt;
	private EditText phoneEdt, accidentReasonEdt, accidentAddEdt,
			acceptReasonEdt;

	private ProgressDialogUtil progressDialog;
	private RegistData bean;
	private TaskCenterService taskService = null;
	private Dialog dialog = null;
	private boolean pauseFlag = false;
	boolean isDistory = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		setContentView(R.layout.activity_task_accept);
		ActivityManage.getInstance().push(this);
		// 初始化视图
		loadViewController();
		// 加载数据
		loadData();

		accept_task_backBtn.setOnClickListener(this);
		accpet_task_acceptBtn.setOnClickListener(this);
	}

	public void loadViewController() {
		accept_task_backBtn = (Button) findViewById(R.id.accpetTaskVackBtn);
		accpet_task_acceptBtn = (Button) findViewById(R.id.acceptCallTaskBtn);

		reportNoEdt = (EditText) findViewById(R.id.accept_report_no);
		reporterEdt = (EditText) findViewById(R.id.accept_reporter);
		accidentTimeEdt = (EditText) findViewById(R.id.accept_accident_time);
		reportTimeEdt = (EditText) findViewById(R.id.accept_report_time);
		phoneEdt = (EditText) findViewById(R.id.accept_phone);
		accidentReasonEdt = (EditText) findViewById(R.id.accept_accident_reason);
		accidentAddEdt = (EditText) findViewById(R.id.accept_accident_addr);

	}

	public RegistData loadData() {
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
				return bean;
			} else {
				return null;

			}

		} else {

			return null;
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
	 * 接受任务案件事件,返回事件
	 * 
	 */

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.acceptCallTaskBtn:

			if (progressDialog == null) {
				progressDialog = new ProgressDialogUtil(this);
			}
			progressDialog.show("接收任务中...");
			new Thread() {
				public void run() {
					Message msg = new Message();
					try {
						// 接受任务，请求后台数据
						bean.setStatus(1);
						bean.init();
						bean.setRemark("接收任务");
						taskService = new TaskCenterService();
						taskService.receivingTask(bean);
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
		case R.id.accpetTaskVackBtn:
			try {
				finish();
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
			if (!pauseFlag) {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				if (dialog != null) {
					dialog.dismiss();
				}

				final Intent intent = new Intent();
				switch (msg.what) {
				case 0:// 0表示数据出错
					if (null != msg.obj) {// 如果不为空检查是否符合归档条件
						String errorMsg = msg.obj.toString();
						if (errorMsg.endsWith("任务已经被处理")
								|| errorMsg.endsWith("该任务已经被理赔系统接收处理")) {
							// 访问是否归档
							executeFiling(errorMsg);

						} else {// 不符合归档条件
							ToastDialog.show(AcceptTaskAct.this,msg.obj.toString(),ToastDialog.ERROR);
						}
					} else {// 接收任务失败
						ToastDialog.show(AcceptTaskAct.this, "系统服务器忙请稍后再试!", ToastDialog.ERROR);
					}
					break;
				case 1:// 1表示成功
					dialog = CustomDialog.show(AcceptTaskAct.this, "",
							"任务接收成功", "确定", "", new OnClickListener() {
								@Override
								public void onClick(View v) {
									try {
										dialog.dismiss();
										intent.putExtra("success", "success");
										setResult(10002, intent);
										isDistory = true;
										finish();
										ActivityManage.getInstance().pop();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}, null);

					// ToastDialog.show(AcceptTaskAct.this, "任务接收成功",
					// ToastDialog.INFO);
					LogFatory.e("接收任务成功：", "");
					break;
				case 2:// 归档成功
						// ToastDialog.show(AcceptTaskAct.this, "归档成功",
						// ToastDialog.INFO);
					dialog = CustomDialog.show(AcceptTaskAct.this, "", "归档成功",
							"确定", "", new OnClickListener() {
								@Override
								public void onClick(View v) {
									try {
										dialog.dismiss();
										intent.putExtra("success", "success");
										setResult(10005, intent);
										finish();
										ActivityManage.getInstance().pop();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}, null);

					break;
				case 3:// 归档失败
					if (null != dialog) {
						dialog.dismiss();
					}
					ToastDialog.show(AcceptTaskAct.this, "归档失败",
							ToastDialog.ERROR);
					break;
				case AppConstants.NO_LOGIN_CODE:
					intent.setClass(AcceptTaskAct.this, LoginAct.class);
					startActivity(intent);
					try {
						finish();
						ActivityManage.getInstance().pop();
					} catch (Exception e) {
						e.printStackTrace();
					}

					break;
				}

			}
		}
	};

	/**
	 * 执行归档处理
	 */
	private void executeFiling(String errorMsg) {
		dialog = CustomDialog.show(AcceptTaskAct.this, "信息提示", errorMsg
				+ ",是否归档?", new OnClickListener() {// 确定归档
					@Override
					public void onClick(View v) {
						if (progressDialog == null) {
							progressDialog = new ProgressDialogUtil(
									AcceptTaskAct.this);
						}
						progressDialog.show("归档处理中...");
						new Thread() {
							public void run() {
								Message msg = new Message();
								try {
									// 归档任务，请求后台数据
									bean.setStatus(7);
									bean.init();
									bean.setRemark("归档任务");
									taskService = new TaskCenterService();
									if (0 != taskService.archiveTask(bean)) {
										msg.what = 2;
									} else {
										msg.what = 3;
									}
								} catch (Exception e) {
									if (StringUtils.isNotEmpty(e.getMessage())
											&& (AppConstants.LOGIN_ERROR
													.equals(e.getMessage()) || AppConstants.LOGIN_FAIL
													.equals(e.getMessage()))) {
										msg.what = AppConstants.NO_LOGIN_CODE;
									} else {
										msg.what = 3;
									}
									msg.obj = e.getMessage();
								} finally {
									handler.sendMessage(msg);
								}
							}
						}.start();
					}
				}, new OnClickListener() {// 取消
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	}

	protected void onPause() {
		super.onPause();
		pauseFlag = true;
		if (dialog != null) {
			dialog.dismiss();
		}
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

	@Override
	protected void onDestroy() {
		accept_task_backBtn = null;
		accpet_task_acceptBtn = null;
		imm = null;
		reportNoEdt = reporterEdt = accidentTimeEdt = reportTimeEdt = null;
		phoneEdt = accidentReasonEdt = accidentAddEdt = acceptReasonEdt = null;
		progressDialog = null;
		bean = null;
		taskService = null;
		super.onDestroy();
	}

}
