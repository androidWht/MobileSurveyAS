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
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;

/**
 * 系统名：移动查勘定损 子系统名：任务中心（申请改派） 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午11:24:02
 */

public class ReassignmentTaskAct extends Activity implements OnClickListener {
	private Button reassignBtn;
	private Button backBtn;
	private EditText reportNoEdt, reporterEdt, accidentTimeEdt, reportTimeEdt;
	private EditText phoneEdt, accidentReasonEdt, accidentAddEdt,
			reassignReasonEdt;
	private InputMethodManager imm;
	private ProgressDialogUtil progressDialog;
	private RegistData bean;
	private TaskCenterService taskService = null;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_reassignment);
		ActivityManage.getInstance().push(this);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		// 初始化视图
		loadController();
		// 初始化数据
		loadData();
		reassignBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
	}

	public void loadController() {
		reassignBtn = (Button) findViewById(R.id.reassign_task_reassignBtn);
		backBtn = (Button) findViewById(R.id.reassign_task_backBtn);
		reportNoEdt = (EditText) findViewById(R.id.reassign_report_no);
		reporterEdt = (EditText) findViewById(R.id.reassign_reporter);
		accidentTimeEdt = (EditText) findViewById(R.id.reassign_accident_time);
		reportTimeEdt = (EditText) findViewById(R.id.reassign_report_time);
		phoneEdt = (EditText) findViewById(R.id.reassign_phone);
		accidentReasonEdt = (EditText) findViewById(R.id.reassign_accident_reason);
		accidentAddEdt = (EditText) findViewById(R.id.reassign_accident_addr);
		reassignReasonEdt = (EditText) findViewById(R.id.reassign_reason);
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
	 * 检测改派原因
	 */
	public boolean check() {
		if (reassignReasonEdt.getText().toString().trim().equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reassign_task_reassignBtn:
			if (check()) {
				if (progressDialog == null) {
					progressDialog = new ProgressDialogUtil(this);
				}
				progressDialog.show("改派任务中。。");
				new Thread() {
					public void run() {
						Message msg = new Message();
						try {
							bean.setStatus(2);
							taskService = new TaskCenterService();
							bean.setRemark(reassignReasonEdt.getText()
									.toString());
							taskService.reassignedTask(bean, reassignReasonEdt
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
				ToastDialog.show(this, "请填写改派原因", ToastDialog.ERROR);
			}
			break;
		case R.id.reassign_task_backBtn:
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
//				ToastDialog.show(ReassignmentTaskAct.this, msg.obj.toString()
//						+ ",请稍后再试", ToastDialog.ERROR);
				if (null != msg.obj) {// 如果不为空检查是否符合归档条件
					String errorMsg = msg.obj.toString();
					if (errorMsg.endsWith("任务已经被处理")
							|| errorMsg.endsWith("该任务已经被理赔系统接收处理")) {
						// 访问是否归档
						executeFiling(errorMsg);

					} else {// 不符合归档条件
						ToastDialog.show(ReassignmentTaskAct.this,msg.obj.toString(),ToastDialog.ERROR);
					}
				} else {// 接收任务失败
					ToastDialog.show(ReassignmentTaskAct.this, "系统服务器忙请稍后再试!", ToastDialog.ERROR);
				}
				break;
			case 1:
				if (dialog != null) {
					dialog.dismiss();
				}
				dialog = CustomDialog.show(ReassignmentTaskAct.this, "",
						"处理成功", "确定", "", new OnClickListener() {
							@Override
							public void onClick(View v) {
								try {
									dialog.dismiss();
									intent.putExtra("success", "success");
									setResult(10001, intent);
									ReassignmentTaskAct.this.finish();
									ActivityManage.getInstance().pop();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}, null);
				// ToastDialog.show(ReassignmentTaskAct.this,
				// "任务改派成功",ToastDialog.INFO);
				break;
			case 2:// 归档成功
				// ToastDialog.show(AcceptTaskAct.this, "归档成功",
				// ToastDialog.INFO);
				dialog = CustomDialog.show(ReassignmentTaskAct.this, "", "归档成功",
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
				ToastDialog.show(ReassignmentTaskAct.this, "归档失败",
						ToastDialog.ERROR);
			break;
			case AppConstants.NO_LOGIN_CODE:

				intent.setClass(ReassignmentTaskAct.this, LoginAct.class);
				startActivity(intent);
				try {
					ReassignmentTaskAct.this.finish();
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

	/**
	 * 执行归档处理
	 */
	private void executeFiling(String errorMsg) {
		dialog = CustomDialog.show(ReassignmentTaskAct.this, "信息提示", errorMsg
				+ ",是否归档?", new OnClickListener() {// 确定归档
					@Override
					public void onClick(View v) {
						if (progressDialog == null) {
							progressDialog = new ProgressDialogUtil(
									ReassignmentTaskAct.this);
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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

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
		super.onDestroy();
		reassignBtn = null;
		backBtn = null;
		reportNoEdt = null;
		reporterEdt = null;
		accidentTimeEdt = null;
		reportTimeEdt = null;
		phoneEdt = null;
		accidentReasonEdt = null;
		accidentAddEdt = null;
		reassignReasonEdt = null;
		imm = null;
		progressDialog = null;
		bean = null;
		taskService = null;
	}
}
