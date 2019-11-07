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
 * 系统名：移动查勘定损 子系统名：到达处理任务 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午5:57:18
 */

public class ArrivalProcessingAct extends Activity implements OnClickListener {

	private Button arrivalBtn, backBtn;
	private EditText reportNoEdt, reporterEdt, accidentTimeEdt, reportTimeEdt;
	private EditText phoneEdt, accidentReasonEdt, accidentAddEdt,
			arrivalCurrentAdd;
	private ProgressDialogUtil progressDialog;
	private RegistData bean;
	private InputMethodManager imm;
	private TaskCenterService taskService = null;
    private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_arrival_processing);
		ActivityManage.getInstance().push(this);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		loadController();
		loadData();
		arrivalBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
	}

	public void loadController() {
		arrivalBtn = (Button) findViewById(R.id.arrival_task_arrivalBtn);
		backBtn = (Button) findViewById(R.id.arrival_task_backBtn);
		reportNoEdt = (EditText) findViewById(R.id.arrival_report_no);
		reporterEdt = (EditText) findViewById(R.id.arrival_reporter);
		accidentTimeEdt = (EditText) findViewById(R.id.arrival_accident_time);
		reportTimeEdt = (EditText) findViewById(R.id.arrival_report_time);
		phoneEdt = (EditText) findViewById(R.id.arrival_phone);
		accidentReasonEdt = (EditText) findViewById(R.id.arrival_accident_reason);
		accidentAddEdt = (EditText) findViewById(R.id.arrival_accident_addr);
		arrivalCurrentAdd = (EditText) findViewById(R.id.arrival_current_addr);
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
		case R.id.arrival_task_arrivalBtn:
			if (null != arrivalCurrentAdd&&StringUtils.isNotEmpty(arrivalCurrentAdd.getText().toString())) {
				if (progressDialog == null) {
					progressDialog = new ProgressDialogUtil(this);
				}
				progressDialog.show("到达处理中。。");
				new Thread() {
					public void run() {
						Message msg = new Message();
						try {
							bean.setStatus(4);
							bean.setRemark(arrivalCurrentAdd.getText()
									.toString());
							taskService = new TaskCenterService();
							taskService.arrivalProcessTask(bean, arrivalCurrentAdd.getText().toString());
							msg.what = 1;
						}catch (Exception e) {
							msg.what =MyUtils.getErrorCode(e.getMessage());
							
							msg.obj = ""+e.getMessage() ;
						} finally {
		                    handler.sendMessage(msg);
						}
					}
				}.start();
			} else {
				ToastDialog.show(this, "请填写当前地址",ToastDialog.ERROR);
			}
			break;
		case R.id.arrival_task_backBtn:
			try {
				ArrivalProcessingAct.this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

	}

	Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {
			// 0表示数据出错
			// 1表示成功
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			final Intent intent = new Intent();
			switch (msg.what) {
			case 0:
				if (null != msg.obj) {// 如果不为空检查是否符合归档条件
					String errorMsg = msg.obj.toString();
					if (errorMsg.endsWith("任务已经被处理")
							|| errorMsg.endsWith("该任务已经被理赔系统接收处理")) {
						// 访问是否归档
						executeFiling(errorMsg);

					} else {// 不符合归档条件
						ToastDialog.show(ArrivalProcessingAct.this,msg.obj.toString(),ToastDialog.ERROR);
					}
				} else {// 接收任务失败
					ToastDialog.show(ArrivalProcessingAct.this, "系统服务器忙请稍后再试!", ToastDialog.ERROR);
				}
//				ToastDialog.show(ArrivalProcessingAct.this, msg.obj.toString()
//						+ "",ToastDialog.ERROR);
				break;
			case 1:
				if(dialog!=null){
					dialog.dismiss();
				}
				dialog= CustomDialog.show(ArrivalProcessingAct.this , "", "处理成功","确定","",new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						try {
							dialog.dismiss();
						    intent.putExtra("success", "success");
						    setResult(10003, intent);
							ArrivalProcessingAct.this.finish();
							ActivityManage.getInstance().pop();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, null);
				//ToastDialog.show(ArrivalProcessingAct.this, "处理成功",ToastDialog.INFO);
				break;
			case 2:// 归档成功
				// ToastDialog.show(AcceptTaskAct.this, "归档成功",
				// ToastDialog.INFO);
				dialog = CustomDialog.show(ArrivalProcessingAct.this, "", "归档成功",
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
				ToastDialog.show(ArrivalProcessingAct.this, "归档失败",
						ToastDialog.ERROR);
			break;
			case AppConstants.NO_LOGIN_CODE:
				intent.setClass(ArrivalProcessingAct.this, LoginAct.class);
				startActivity(intent);
				try {
					ArrivalProcessingAct.this.finish();
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
		dialog = CustomDialog.show(ArrivalProcessingAct.this, "信息提示", errorMsg
				+ ",是否归档?", new OnClickListener() {// 确定归档
					@Override
					public void onClick(View v) {
						if (progressDialog == null) {
							progressDialog = new ProgressDialogUtil(
									ArrivalProcessingAct.this);
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
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		if(dialog!=null){
			dialog.dismiss();
		}
	};

	public boolean onTouchEvent(MotionEvent event) {
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
	@Override
	protected void onDestroy() {
		super.onDestroy();
		arrivalBtn = null;
		backBtn = null;
		reportNoEdt = null;
		reporterEdt = null;
		accidentTimeEdt = null;
		reportTimeEdt = null;
		phoneEdt = null;
		accidentReasonEdt = null;
		accidentAddEdt = null;
		arrivalCurrentAdd = null;
		progressDialog = null;
		bean = null;
		imm = null;
		taskService = null;
	}
}
