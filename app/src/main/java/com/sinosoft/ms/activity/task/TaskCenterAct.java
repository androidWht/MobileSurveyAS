package com.sinosoft.ms.activity.task;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.activity.MainActivity;
import com.sinosoft.ms.activity.query.LossDetailsAct;
import com.sinosoft.ms.activity.query.SurveyDetailsAct;
import com.sinosoft.ms.activity.query.TaskProgressAct;
import com.sinosoft.ms.activity.task.claimsassessment.ClaimsAssessmentAct;
import com.sinosoft.ms.activity.task.confirmdamage.ConfirmDamageAct;
import com.sinosoft.ms.activity.task.image.MediaCenterAct;
import com.sinosoft.ms.activity.task.loss.LossMenuAct;
import com.sinosoft.ms.activity.task.survey.SurveyGeneralAct;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.service.impl.TaskCenterService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConfig;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.SystemUtil;
import com.sinosoft.ms.utils.adapter.TaskCenterListAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.PopWindow;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.TaskDatabase;

/**
 * 系统名：移动查勘定损 子系统名：任务中心 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:08:53
 */

public class TaskCenterAct extends Activity implements OnClickListener
{

	private ExpandableListView taskCenterList;
	private Button taskChooseBtn;
	// (0.未接收 1.接收 2.改派 3.拒绝 4.到达 5.完成 6.归档 )
	private String[] taskType = new String[]
	{ "全部任务", "未接收", "接收", "改派", "到达", "完成", "未联系", "查勘任务", "定损任务" };
	private int[] taskCode = new int[]
	{ 90, 0, 1, 2, 4, 5, 7, 8, 9 };
	private String[] groupTypes = new String[]
	{ "今天", "昨天", "更早" };

	private PopWindow pop;
	private List<RegistData> allData = new ArrayList<RegistData>();
	private List<RegistData> currentData = new ArrayList<RegistData>();

	// private List<DeflossData> deflossData = new ArrayList<DeflossData>();

	// private DeflossData deflossData = null;

	private TaskCenterListAdapter taskAdapter;
	private Button taskReturnBtn;
	private ProgressDialogUtil progressDialog;
	private int index;
	private final int RESIGNMENT = 10001;
	private final int ACCEPT = 10002;
	private final int ARRIVAL = 10003;
	private final int REFUSE = 10004;
	private final int ARCHIVR = 10005;
	private final int PHONE = 10006;
	private final int SURVEY = 10007;
	private final int LOSS = 10008;
	private TaskCenterService service = null;
	private RegistData taskCenterBean = null;
	private long phoneStatus;

	private boolean pauseFlag = false;
	private final int SAVE_SUCCESS = 1100;
	private Dialog dialog, searchDialog, selectDialog;

	private Button mTaskFilter;// 筛选按钮
	private RadioGroup mTaskTypeGroup1, mTaskTypeGroup2, mTaskTypeGroup3;
	private RadioGroup mTaskStateGroup1, mTaskStateGroup2;
	private MyOnCheckChangedListener myOnCheckChangedListener;
	private MyStateOnCheckChangedListener myStateOnCheckChangedListener;
	private long currentTaskType = 90, currentTaskState = 0;// 搜索中的任务类型和任务状态
	private Button mNumber1Btn, mNumber2Btn, mNumber3Btn, mNumber4Btn, mNumber5Btn, mNumber6Btn, mNumber7Btn, mNumber8Btn, mNumber9Btn, mNumber0Btn, mDeleteBtn;
	private EditText serachNumbers;
	private NumberButtonClickListener numberButtonListener;
	private Button mQueryBtn, mAllResultBtn, mCancelBtn;

	private Button mAllBtn, mLossBtn, mSurveyBtn, mRetreat_lossBtn;
	private int mCurrSel;

	private Button mSurveyCaseBtn, mImageUploadBtn, mPCProcessingBtn;
	private Button testconfirmdamage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_center);

		if (AppConfig.loginFlag)
		{

		}
		else
		{
			Intent intent = new Intent(TaskCenterAct.this, MainActivity.class);
			startActivity(intent);
			finish();
			ActivityManage.getInstance().distory();
		}
		ActivityManage.getInstance().push(this);

		// 加载控件
		loadController();
		// 加载数据
		getData(0);
		// 添加监听
		taskChooseBtn.setOnClickListener(this);
		taskReturnBtn.setOnClickListener(this);
		mTaskFilter.setOnClickListener(this);
		mRetreat_lossBtn.setOnClickListener(this);
		mAllBtn.setOnClickListener(this);
		mLossBtn.setOnClickListener(this);
		mSurveyBtn.setOnClickListener(this);

		mCurrSel = 0; // 0 表示显示所有 1表示定损 2 查勘
	}

	public void loadController()
	{
		taskCenterList = (ExpandableListView) findViewById(R.id.taskCenterList);
		taskChooseBtn = (Button) findViewById(R.id.taskChoose);
		taskReturnBtn = (Button) findViewById(R.id.taskReturnBtn);
		mTaskFilter = (Button) findViewById(R.id.taskfilter);
		mRetreat_lossBtn = (Button) findViewById(R.id.retreat_lossBtn);
		mAllBtn = (Button) findViewById(R.id.allBtn);
		mLossBtn = (Button) findViewById(R.id.lossBtn);
		mSurveyBtn = (Button) findViewById(R.id.surveyBtn);

		numberButtonListener = new NumberButtonClickListener();

	}

	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (!pauseFlag)
			{
				if (progressDialog != null)
				{
					progressDialog.dismiss();
				}
				if (taskCenterBean != null && taskCenterBean.getStatus() == 90)
				{
					taskCenterBean.setStatus(phoneStatus);
				}
				// 0表示数据错误，1表示正确
				switch (msg.what)
				{
				case 0:
					String errorMsg = msg.obj.toString();
					if (StringUtils.isNotEmpty(errorMsg))
					{
						ToastDialog.show(TaskCenterAct.this, errorMsg, ToastDialog.ERROR);
					}
					else
					{
						ToastDialog.show(TaskCenterAct.this, "电话联系失败", ToastDialog.ERROR);
					}/*
					 * TaskDatabase database = new
					 * TaskDatabase(TaskCenterAct.this); try {
					 * currentData=database.selectTaskList("task", null,
					 * "reportTime"); taskAdapter = new
					 * TaskCenterListAdapter(currentData, TaskCenterAct.this,
					 * itemBtnOnClickListener);
					 * taskCenterList.setAdapter(taskAdapter); } catch
					 * (Exception e) { e.printStackTrace(); }
					 */
					break;
				case 1:
					if (allData != null)
					{
						if (!allData.isEmpty())
						{
							if (currentData == null)
							{
								currentData = new ArrayList<RegistData>();
							}
							currentData.clear();

							// /taskType;// 任务类型 (0.查勘 1.定损完成 2.简易定损 3.核损退定损)
							if (mCurrSel == 0)
							{
								currentData.addAll(allData);
							}
							else if (mCurrSel == 1) // 定损
							{
								for (int i = 0; i < allData.size(); i++)
								{
									RegistData mRegistData = allData.get(i);
									if (mRegistData.getTaskType() == 1)
										currentData.add(mRegistData);
								}
							}
							else if (mCurrSel == 2) // 查勘
							{
								for (int i = 0; i < allData.size(); i++)
								{
									RegistData mRegistData = allData.get(i);
									if (mRegistData.getTaskType() == 0)
										currentData.add(mRegistData);
								}
							}
							else if (mCurrSel == 3)
							{ // 核损退回
								for (int i = 0; i < allData.size(); i++)
								{
									RegistData mRegistData = allData.get(i);
									if (mRegistData.getStatus1() == 2)
										currentData.add(mRegistData);
								}
							}

							taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
							taskCenterList.setAdapter(taskAdapter);

							for (int i = 0; i < groupTypes.length; i++)
							{
								taskCenterList.expandGroup(i);
							}
						}
						else
						{
							ToastDialog.show(TaskCenterAct.this, "查询结果为空", ToastDialog.ERROR);
						}
					}

					break;
				case 11:
					// TODO 不知道用来干嘛
					break;
				case AppConstants.NO_LOGIN_CODE:
					Intent intent2 = getIntent();
					intent2.setClass(TaskCenterAct.this, LoginAct.class);
					startActivity(intent2);
					try
					{
						TaskCenterAct.this.finish();
						ActivityManage.getInstance().pop();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					break;
				}
			}
		}
	};

	/**
	 * 取得全部任务列表数据
	 */
	public void getData(final int type)
	{
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("数据加载中，请稍后");

		// 启动线程访问数据
		new Thread()
		{
			public void run()
			{
				Message msg = new Message();
				try
				{
					service = new TaskCenterService(TaskCenterAct.this);
					allData = service.getTaskCenterListByType(taskCode[type]);
					// /allData = service.getTaskCenterListByType(100);
					msg.what = 1;
				}
				catch (Exception e)
				{
					e.printStackTrace();
					msg.what = MyUtils.getErrorCode(e.getMessage());
					String error = e.getMessage();
					if (StringUtils.isEmpty(error))
					{
						error = "错误未知";
					}
					msg.obj = error;
				}
				finally
				{
					handler.removeMessages(0);
					handler.removeMessages(1);
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

	/*
	 * 筛选（从本地数据过滤）
	 */
	public void filterData(String lastString, long taskType, long stateType)
	{
		progressDialog.show("数据筛选中，请稍后");
		if (allData != null)
		{
			if (!allData.isEmpty())
			{
				if (currentData == null)
				{
					currentData = new ArrayList<RegistData>();
				}
				else
				{
					currentData.clear();
				}
				for (int i = 0; i < allData.size(); i++)
				{
					RegistData data = allData.get(i);
					String regist = data.getRegistNo();
					if (regist.endsWith(lastString))
					{
						if (taskType == 90)
						{
							if (stateType == 100)
							{
								currentData.add(data);
							}
							else if (stateType == data.getStatus())
							{
								currentData.add(data);
							}
						}
						else if (taskType == 1)
						{
							if (data.getTaskType() == 1 || data.getTaskType() == 2)
							{
								if (stateType == 100)
								{
									currentData.add(data);
								}
								else if (stateType == data.getStatus())
								{
									currentData.add(data);
								}
							}
						}
						else
						{
							if (taskType == data.getTaskType())
							{
								if (stateType == 100)
								{
									currentData.add(data);
								}
								else if (stateType == data.getStatus())
								{
									currentData.add(data);
								}
							}
						}
					}
				}
				if (currentData.size() > 0)
				{
					taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
					taskCenterList.setAdapter(taskAdapter);
					for (int i = 0; i < groupTypes.length; i++)
					{
						taskCenterList.expandGroup(i);
					}
				}
				else
				{
					Toast.makeText(TaskCenterAct.this, "当前无符合条件的记录", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				ToastDialog.show(TaskCenterAct.this, "查询结果为空", ToastDialog.ERROR);
			}
		}
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
	}

	/**
	 * 根据报案号后几位搜索(从后台查询数据)
	 */
	public void getData1(final int type, final String registNoEnd)
	{
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("数据加载中，请稍后");

		// 启动线程访问数据
		new Thread()
		{
			public void run()
			{
				Message msg = new Message();
				try
				{
					service = new TaskCenterService(TaskCenterAct.this);
					allData = service.getTaskCenterListByType(taskCode[type], registNoEnd);
					msg.what = 1;
				}
				catch (Exception e)
				{
					e.printStackTrace();
					msg.what = MyUtils.getErrorCode(e.getMessage());
					String error = e.getMessage();
					if (StringUtils.isEmpty(error))
					{
						error = "错误未知";
					}
					msg.obj = error;
				}
				finally
				{
					handler.removeMessages(0);
					handler.removeMessages(1);
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

	private void changButtonState()
	{
		if (mCurrSel == 0)
		{
			mAllBtn.setBackgroundResource(R.drawable.new_login_loginbtn);
			mLossBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
			mSurveyBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
			mRetreat_lossBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
		}
		else if (mCurrSel == 1)
		{
			mAllBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
			mLossBtn.setBackgroundResource(R.drawable.new_login_loginbtn);
			mSurveyBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
			mRetreat_lossBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
		}
		else if (mCurrSel == 3)
		{
			mAllBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
			mLossBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
			mSurveyBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
			mRetreat_lossBtn.setBackgroundResource(R.drawable.new_login_loginbtn);
		}
		else
		{
			mAllBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
			mLossBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
			mSurveyBtn.setBackgroundResource(R.drawable.new_login_loginbtn);
			mRetreat_lossBtn.setBackgroundResource(R.drawable.new_login_canclebtn);
		}

	}

	private void resetDataAndLayout()
	{
		Message msg = new Message();

		msg.what = 1;

		handler.removeMessages(0);
		handler.removeMessages(1);
		handler.sendMessage(msg);

	}

	public OnClickListener itemBtnOnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{

			if (v instanceof Button)
			{
				Button btn = (Button) v;
				String temp = (String) btn.getTag();
				String[] temps = temp.split(";");
				int i = 0;
				for (i = 0; i < currentData.size(); i++)
				{
					RegistData data = currentData.get(i);
					if (data.getTaskId().equals(temps[0]) && temps[1].equals(MyUtils.getTaskTypeString((int) data.getTaskType())) && data.getLicenseNo().equals(temps[2]))
					{
						taskCenterBean = data;
						index = i;
						break;
					}
				}
				if (i == currentData.size())
				{
					return;
				}
				long taskType = taskCenterBean.getTaskType();
				if (btn.getText().equals("处理"))
				{
					showAlertDialog();
				}
				else if (btn.getText().equals("拒绝"))
				{
					Intent intent = new Intent(TaskCenterAct.this, RefuseTaskAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("taskBean", taskCenterBean);
					intent.putExtra("task", bundle);
					TaskCenterAct.this.startActivityForResult(intent, REFUSE);
				}
				else if (btn.getText().equals("接收"))
				{
					Intent intent = new Intent(TaskCenterAct.this, AcceptTaskAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("taskBean", taskCenterBean);
					intent.putExtra("task", bundle);
					TaskCenterAct.this.startActivityForResult(intent, ACCEPT);

				}
				else if (btn.getText().equals("归档"))
				{
					Intent intent = new Intent(TaskCenterAct.this, ArchiveTaskAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("taskBean", taskCenterBean);
					intent.putExtra("task", bundle);
					TaskCenterAct.this.startActivityForResult(intent, ARCHIVR);
				}
				else if (btn.getText().equals("改派"))
				{
					Intent intent = new Intent(TaskCenterAct.this, ReassignmentTaskAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("taskBean", taskCenterBean);
					intent.putExtra("task", bundle);
					TaskCenterAct.this.startActivityForResult(intent, RESIGNMENT);
				}
				else if (btn.getText().equals("到达"))
				{
					Intent intent = new Intent(TaskCenterAct.this, ArrivalProcessingAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("taskBean", taskCenterBean);
					intent.putExtra("task", bundle);
					TaskCenterAct.this.startActivityForResult(intent, ARRIVAL);
				}
				else if (btn.getText().toString().equals("进度"))
				{
					Intent intent = new Intent(TaskCenterAct.this, TaskProgressAct.class);
					intent.putExtra("registNo", taskCenterBean.getRegistNo());
					intent.putExtra("registId", String.valueOf(taskCenterBean.getId()));
					intent.putExtra("taskId", taskCenterBean.getTaskId());
					startActivity(intent);
				}
				else if (btn.getText().toString().equals("查看"))
				{
					if (taskType == 0)
					{
						Intent intent = new Intent(TaskCenterAct.this, SurveyDetailsAct.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bean", taskCenterBean);

						intent.putExtra("registNo", taskCenterBean.getRegistNo());
						intent.putExtra("registId", taskCenterBean.getId());
						intent.putExtra("item", bundle);
						intent.putExtra("canEdit", false);
						TaskCenterAct.this.startActivity(intent);
					}
					else
					{
						Intent intent = new Intent(TaskCenterAct.this, LossDetailsAct.class);
						intent.putExtra("registNo", taskCenterBean.getRegistNo());
						intent.putExtra("registId", String.valueOf(taskCenterBean.getId()));
						intent.putExtra("taskId", taskCenterBean.getTaskId());
						Bundle bundle = new Bundle();
						bundle.putSerializable("bean", taskCenterBean);
						intent.putExtra("item", bundle);
						intent.putExtra("canEdit", false);
						TaskCenterAct.this.startActivity(intent);
					}
				}
				else if (btn.getText().toString().equals("转电脑处理"))
				{
					if (dialog != null)
					{
						dialog.dismiss();
						dialog = null;
					}
					long statusCode = taskCenterBean.getStatus();
					if (!(statusCode == 2 || statusCode == 6 || statusCode == 5))
					{
						dialog = CustomDialog.show(TaskCenterAct.this, "信息提示", "是否确认转电脑处理?", "是", "否", new OnClickListener()
						{
							@Override
							public void onClick(View v)
							{
								dialog.dismiss();
								changePC();
							}
						}, new OnClickListener()
						{
							@Override
							public void onClick(View v)
							{
								dialog.dismiss();
							}
						});
					}
					else
					{
						if (statusCode == 2)
						{
							Toast.makeText(TaskCenterAct.this, "您的案件已被改派，不能转电脑处理", Toast.LENGTH_SHORT).show();
						}
						else if (statusCode == 5)
						{
							Toast.makeText(TaskCenterAct.this, "您的案件已完成，不能转电脑处理", Toast.LENGTH_SHORT).show();
						}
						else
						{
							Toast.makeText(TaskCenterAct.this, "您的案件已归档，不能转电脑处理", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		}
	};

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.retreat_lossBtn:
			mCurrSel = 3;
			changButtonState();
			resetDataAndLayout();
			break;
		case R.id.allBtn:
			mCurrSel = 0;
			changButtonState();
			resetDataAndLayout();
			break;
		case R.id.lossBtn:
			mCurrSel = 1;
			changButtonState();
			resetDataAndLayout();
			break;
		case R.id.surveyBtn:
			mCurrSel = 2;
			changButtonState();
			resetDataAndLayout();
			break;

		case R.id.taskChoose:
			pop = new PopWindow(this, taskType, R.drawable.popbg, itemClickListener);
			pop.show(view);
			break;
		case R.id.taskReturnBtn:
			try
			{
				TaskCenterAct.this.finish();
				ActivityManage.getInstance().pop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		case R.id.taskfilter:
		{
			currentTaskType = 90;
			currentTaskState = 100;
			LayoutInflater inflater = getLayoutInflater();
			final View layout = inflater.inflate(R.layout.dialog_task_search, (ViewGroup) findViewById(R.id.searchdialog));
			serachNumbers = (EditText) layout.findViewById(R.id.registNo);
			serachNumbers.setInputType(InputType.TYPE_NULL);
			mNumber1Btn = (Button) layout.findViewById(R.id.number1);
			mNumber2Btn = (Button) layout.findViewById(R.id.number2);
			mNumber3Btn = (Button) layout.findViewById(R.id.number3);
			mNumber4Btn = (Button) layout.findViewById(R.id.number4);
			mNumber5Btn = (Button) layout.findViewById(R.id.number5);
			mNumber6Btn = (Button) layout.findViewById(R.id.number6);
			mNumber7Btn = (Button) layout.findViewById(R.id.number7);
			mNumber8Btn = (Button) layout.findViewById(R.id.number8);
			mNumber9Btn = (Button) layout.findViewById(R.id.number9);
			mNumber0Btn = (Button) layout.findViewById(R.id.number0);
			mDeleteBtn = (Button) layout.findViewById(R.id.delete);
			mQueryBtn = (Button) layout.findViewById(R.id.query_btn);
			mAllResultBtn = (Button) layout.findViewById(R.id.all_result);
			mCancelBtn = (Button) layout.findViewById(R.id.cancel);
			mNumber1Btn.setOnClickListener(numberButtonListener);
			mNumber2Btn.setOnClickListener(numberButtonListener);
			mNumber3Btn.setOnClickListener(numberButtonListener);
			mNumber4Btn.setOnClickListener(numberButtonListener);
			mNumber5Btn.setOnClickListener(numberButtonListener);
			mNumber6Btn.setOnClickListener(numberButtonListener);
			mNumber7Btn.setOnClickListener(numberButtonListener);
			mNumber8Btn.setOnClickListener(numberButtonListener);
			mNumber9Btn.setOnClickListener(numberButtonListener);
			mNumber0Btn.setOnClickListener(numberButtonListener);
			mDeleteBtn.setOnClickListener(numberButtonListener);
			mQueryBtn.setOnClickListener(numberButtonListener);
			mAllResultBtn.setOnClickListener(numberButtonListener);
			mCancelBtn.setOnClickListener(numberButtonListener);
			mTaskTypeGroup1 = (RadioGroup) layout.findViewById(R.id.taskRadioGroup1);
			mTaskTypeGroup2 = (RadioGroup) layout.findViewById(R.id.taskRadioGroup2);
			mTaskTypeGroup3 = (RadioGroup) layout.findViewById(R.id.taskRadioGroup3);
			mTaskStateGroup1 = (RadioGroup) layout.findViewById(R.id.radioGroup1);
			mTaskStateGroup2 = (RadioGroup) layout.findViewById(R.id.radioGroup2);
			myOnCheckChangedListener = new MyOnCheckChangedListener();
			mTaskTypeGroup1.setOnCheckedChangeListener(myOnCheckChangedListener);
			mTaskTypeGroup2.setOnCheckedChangeListener(myOnCheckChangedListener);
			mTaskTypeGroup3.setOnCheckedChangeListener(myOnCheckChangedListener);

			myStateOnCheckChangedListener = new MyStateOnCheckChangedListener();
			mTaskStateGroup1.setOnCheckedChangeListener(myStateOnCheckChangedListener);
			mTaskStateGroup2.setOnCheckedChangeListener(myStateOnCheckChangedListener);

			searchDialog = CustomDialog.show(TaskCenterAct.this, "筛选", layout);
		}
			break;
		case R.id.button1:
		{ // 非互碰自赔案件
			if (selectDialog.isShowing())
			{
				selectDialog.dismiss();
			}
			DealWithTask();
		}
			break;
		case R.id.button2:
		{ // 互碰自赔案件，仅拍照上传
			if (selectDialog.isShowing())
			{
				selectDialog.dismiss();
			}
			Intent intent = new Intent();
			intent.setClass(TaskCenterAct.this, MediaCenterAct.class);
			intent.putExtra("fromtasklistitem", true);
			intent.putExtra("registNo", taskCenterBean.getTaskId());
			intent.putExtra("registId", String.valueOf(taskCenterBean.getId()));
			Bundle bundle = new Bundle();
			bundle.putSerializable("bean", taskCenterBean);
			intent.putExtra("item", bundle);
			startActivity(intent);
		}
			break;
		case R.id.button3:
		{ // 互碰自赔案件，转电脑处理
			if (selectDialog.isShowing())
			{
				selectDialog.dismiss();
			}
			new AlertDialog.Builder(TaskCenterAct.this).setTitle("提示").setMessage("确定转电脑处理？").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					changePC();
				}
			}).show();
		}
			break;
		case R.id.button4:
			if (selectDialog.isShowing())
			{
				selectDialog.dismiss();
			}
			Intent intent = new Intent(TaskCenterAct.this, ConfirmDamageAct.class);
			intent.putExtra("reportorMobile", taskCenterBean.getReportorNumber());
			intent.putExtra("registNo", taskCenterBean.getRegistNo());
			intent.putExtra("taskId", taskCenterBean.getTaskId());
			intent.putExtra("registId", String.valueOf(taskCenterBean.getId()));
			Bundle bundle = new Bundle();
			bundle.putSerializable("bean", taskCenterBean);
			intent.putExtra("item", bundle);

			TaskCenterAct.this.startActivityForResult(intent, LOSS);
			break;
		case R.id.button5:
			if (selectDialog.isShowing())
			{
				selectDialog.dismiss();
			}
			Intent intent1 = new Intent(TaskCenterAct.this, ClaimsAssessmentAct.class);
			intent1.putExtra("reportorMobile", taskCenterBean.getReportorNumber());
			intent1.putExtra("registNo", taskCenterBean.getRegistNo());
			intent1.putExtra("taskId", taskCenterBean.getTaskId());
			intent1.putExtra("registId", String.valueOf(taskCenterBean.getId()));
			Bundle bundle1 = new Bundle();
			bundle1.putSerializable("bean", taskCenterBean);
			intent1.putExtra("item", bundle1);

			this.startActivity(intent1);

			break;
		}

	}

	class MyStateOnCheckChangedListener implements OnCheckedChangeListener
	{

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1)
		{
			switch (arg0.getId())
			{
			case R.id.radioGroup1:
				RadioButton rb = (RadioButton) arg0.findViewById(arg1);
				if (rb != null)
				{
					mTaskStateGroup2.setOnCheckedChangeListener(null);
					mTaskStateGroup2.clearCheck();
					String temp = (String) rb.getTag();
					currentTaskState = Long.parseLong(temp);
					mTaskStateGroup2.setOnCheckedChangeListener(this);
				}
				break;
			case R.id.radioGroup2:
				rb = (RadioButton) arg0.findViewById(arg1);
				if (rb != null)
				{
					mTaskStateGroup1.setOnCheckedChangeListener(null);
					mTaskStateGroup1.clearCheck();
					String temp = (String) rb.getTag();
					currentTaskState = Long.parseLong(temp);
					mTaskStateGroup1.setOnCheckedChangeListener(this);
				}
				break;
			}

		}

	}

	class MyOnCheckChangedListener implements OnCheckedChangeListener
	{

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1)
		{
			switch (arg0.getId())
			{
			case R.id.taskRadioGroup1:
				RadioButton rb = (RadioButton) arg0.findViewById(arg1);
				if (rb != null)
				{
					mTaskTypeGroup2.setOnCheckedChangeListener(null);
					mTaskTypeGroup2.clearCheck();
					mTaskTypeGroup3.setOnCheckedChangeListener(null);
					mTaskTypeGroup3.clearCheck();
					String temp = (String) rb.getTag();
					currentTaskType = Long.parseLong(temp);
					mTaskTypeGroup2.setOnCheckedChangeListener(this);
					mTaskTypeGroup3.setOnCheckedChangeListener(this);
				}
				break;
			case R.id.taskRadioGroup2:
				rb = (RadioButton) arg0.findViewById(arg1);
				if (rb != null)
				{
					// mTaskTypeGroup2.clearCheck();
					mTaskTypeGroup1.setOnCheckedChangeListener(null);
					mTaskTypeGroup1.clearCheck();
					mTaskTypeGroup3.setOnCheckedChangeListener(null);
					mTaskTypeGroup3.clearCheck();
					String temp = (String) rb.getTag();
					currentTaskType = Long.parseLong(temp);
					mTaskTypeGroup1.setOnCheckedChangeListener(this);
					mTaskTypeGroup3.setOnCheckedChangeListener(this);
				}
				break;
			case R.id.taskRadioGroup3:
				rb = (RadioButton) arg0.findViewById(arg1);
				if (rb != null)
				{
					mTaskTypeGroup1.setOnCheckedChangeListener(null);
					mTaskTypeGroup1.clearCheck();
					mTaskTypeGroup2.setOnCheckedChangeListener(null);
					mTaskTypeGroup2.clearCheck();
					String temp = (String) rb.getTag();
					currentTaskType = Long.parseLong(temp);
					mTaskTypeGroup1.setOnCheckedChangeListener(this);
					mTaskTypeGroup2.setOnCheckedChangeListener(this);
				}
				break;
			}
		}

	}

	@Override
	protected void onPause()
	{
		super.onPause();
		handler.removeCallbacksAndMessages(null);
		TaskDatabase database = new TaskDatabase(this);
		database.deleteTaskList("task", null);
		database.insertIntoTaskList("task", currentData);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null)
		{
			if (requestCode == ACCEPT)
			{
				String success = data.getStringExtra("success");
				if (success != null && success.equals("success"))
				{
					RegistData taskCenterBean = currentData.get(index);
					if (resultCode == ARCHIVR)
					{
						taskCenterBean.setStatus(6);
					}
					else if (resultCode == ACCEPT)
					{
						taskCenterBean.setStatus(4);
					}
					taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
					taskCenterList.setAdapter(taskAdapter);
					for (int i = 0; i < groupTypes.length; i++)
					{
						taskCenterList.expandGroup(i);
					}
				}
			}
			else if (requestCode == ARRIVAL)
			{
				String success = data.getStringExtra("success");
				if (success != null && success.equals("success"))
				{
					RegistData taskCenterBean = currentData.get(index);
					taskCenterBean.setStatus(4);
					taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
					taskCenterList.setAdapter(taskAdapter);
					for (int i = 0; i < groupTypes.length; i++)
					{
						taskCenterList.expandGroup(i);
					}
				}
			}
			else if (requestCode == REFUSE)
			{
				String success = data.getStringExtra("success");
				if (success != null && success.equals("success"))
				{
					RegistData taskCenterBean = currentData.get(index);
					taskCenterBean.setStatus(3);
					taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
					taskCenterList.setAdapter(taskAdapter);
					for (int i = 0; i < groupTypes.length; i++)
					{
						taskCenterList.expandGroup(i);
					}
				}
			}
			else if (requestCode == RESIGNMENT)
			{
				String success = data.getStringExtra("success");
				if (success != null && success.equals("success"))
				{
					RegistData taskCenterBean = currentData.get(index);
					taskCenterBean.setStatus(2);
					taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
					taskCenterList.setAdapter(taskAdapter);
					for (int i = 0; i < groupTypes.length; i++)
					{
						taskCenterList.expandGroup(i);
					}
				}
			}
			else if (requestCode == ARCHIVR)
			{
				String success = data.getStringExtra("success");
				if (success != null && success.equals("success"))
				{
					RegistData taskCenterBean = currentData.get(index);
					taskCenterBean.setStatus(6);
					taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
					taskCenterList.setAdapter(taskAdapter);
					for (int i = 0; i < groupTypes.length; i++)
					{
						taskCenterList.expandGroup(i);
					}
				}
			}
			else if (requestCode == PHONE)
			{
				Log.e("phone", "phone cancle");
				TaskCenterService taskService = new TaskCenterService();
				RegistData taskCenterBean = currentData.get(index);
				taskCenterBean.setStatus(90);
				try
				{
					taskService.phoneContactTask(taskCenterBean, "电话联系客户");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else if (requestCode == SURVEY)
			{
				if (resultCode == SAVE_SUCCESS)
				{
					RegistData taskCenterBean = currentData.get(index);
					taskCenterBean.setStatus(6);

					// Intent intent = new
					// Intent(TaskCenterAct.this,BluetoothActivity.class) ;
					// TaskCenterAct.this.startActivity(intent) ;
				}
				else
				{
					String success = data.getStringExtra("success");
					if (success != null && success.equals("success"))
					{
						currentData.remove(index);
					}
				}
				taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
				taskCenterList.setAdapter(taskAdapter);
				for (int i = 0; i < groupTypes.length; i++)
				{
					taskCenterList.expandGroup(i);
				}
			}
			else if (requestCode == LOSS)
			{
				if (resultCode == SAVE_SUCCESS)
				{
					RegistData taskCenterBean = currentData.get(index);
					taskCenterBean.setStatus(5);
				}
				else
				{
					String success = data.getStringExtra("success");
					if (success != null && success.equals("success"))
					{
						currentData.remove(index);
					}
				}
				taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
				taskCenterList.setAdapter(taskAdapter);
				for (int i = 0; i < groupTypes.length; i++)
				{
					taskCenterList.expandGroup(i);
				}
			}
		}
		else
		{

			/** 对处理按钮 返回 后重新加载列表数据 **/
			getData(0);// 重新请求数据（所有）
		}
	}

	private Button Button5;

	/**
	 * 对话框 是否代位求偿
	 */
	public void showAlertDialog()
	{
		// 如果是定损按键
		taskCenterBean = currentData.get(index);
		long taskType = taskCenterBean.getTaskType();
		// 如果是定损案件
		if (taskType == AppConstants.TYPE_COMPLETE_SETLOSS || taskType == AppConstants.TYPE_COUNT_RETREAT_SETLOSS || taskType == AppConstants.TYPE_RETREAT_SETLOSS)
		{
			if (dialog != null)
			{
				dialog.dismiss();
				dialog = null;
			}
			LayoutInflater inflater = getLayoutInflater();
			final View layout = inflater.inflate(R.layout.dialog_task_select, (ViewGroup) findViewById(R.id.selectdialog));
			mSurveyCaseBtn = (Button) layout.findViewById(R.id.button1);
			mImageUploadBtn = (Button) layout.findViewById(R.id.button2);
			mPCProcessingBtn = (Button) layout.findViewById(R.id.button3);
			testconfirmdamage = (Button) layout.findViewById(R.id.button4);
			Button5 = (Button) layout.findViewById(R.id.button5);
			mSurveyCaseBtn.setOnClickListener(this);
			mImageUploadBtn.setOnClickListener(this);
			mPCProcessingBtn.setOnClickListener(this);
			testconfirmdamage.setOnClickListener(this);
			Button5.setOnClickListener(this);
			selectDialog = CustomDialog.showTip(TaskCenterAct.this, "信息提示", layout);
		}
		else
		{
			// 查勘案件
			DealWithTask();
		}
	}

	private void DealWithTask()
	{

		taskCenterBean = currentData.get(index);
		long taskType = taskCenterBean.getTaskType();
		// 如果是查勘案件
		if (taskType == AppConstants.TYPE_SURVEY || taskType == AppConstants.TYPE_PRIVATE_SURVEY)
		{
			Intent intent = new Intent(TaskCenterAct.this, SurveyGeneralAct.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("bean", taskCenterBean);
			intent.putExtra("registNo", taskCenterBean.getRegistNo());
			intent.putExtra("registId", String.valueOf(taskCenterBean.getId()));
			intent.putExtra("item", bundle);
			TaskCenterAct.this.startActivityForResult(intent, SURVEY);
		}
		else
		{

			Intent intent = new Intent(TaskCenterAct.this, LossMenuAct.class);
			intent.putExtra("reportorMobile", taskCenterBean.getReportorNumber());
			intent.putExtra("registNo", taskCenterBean.getRegistNo());
			intent.putExtra("taskId", taskCenterBean.getTaskId());
			intent.putExtra("registId", String.valueOf(taskCenterBean.getId()));
			Bundle bundle = new Bundle();
			bundle.putSerializable("bean", taskCenterBean);
			intent.putExtra("item", bundle);

			TaskCenterAct.this.startActivityForResult(intent, LOSS);
		}
	}

	public void changePC()
	{
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("任务申请电脑处理");
		new Thread()
		{
			public void run()
			{
				Message msg = new Message();
				try
				{
					TaskCenterService service = new TaskCenterService();
					taskCenterBean = currentData.get(index);

					taskCenterBean.setStatus(8);
					taskCenterBean.setRemark("电脑处理");
					service.changePCSurvey(taskCenterBean, "电脑处理");
					msg.what = 1;
				}
				catch (Exception e)
				{
					msg.what = MyUtils.getErrorCode(e.getMessage());
					String error = e.getMessage();
					if (StringUtils.isEmpty(error))
					{
						error = "处理错误";
					}
					msg.obj = error;
				}
				finally
				{
					PCHandler.sendMessage(msg);
				}
			}
		}.start();

	}

	Handler PCHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			Intent intent = new Intent();
			switch (msg.what)
			{
			case 0:// 申请电脑任务 错误
				if (progressDialog != null)
				{
					progressDialog.dismiss();
				}
				ToastDialog.show(TaskCenterAct.this, "" + msg.obj.toString(), ToastDialog.ERROR);
				break;
			case 1:// 申请电脑任务 正确
				if (progressDialog != null)
				{
					progressDialog.dismiss();
				}
				ToastDialog.show(TaskCenterAct.this, "申请处理成功", ToastDialog.ERROR);
				currentData.remove(index);
				taskAdapter = new TaskCenterListAdapter(currentData, TaskCenterAct.this, itemBtnOnClickListener);
				taskCenterList.setAdapter(taskAdapter);
				for (int i = 0; i < groupTypes.length; i++)
				{
					taskCenterList.expandGroup(i);
				}
				break;
			case AppConstants.NO_LOGIN_CODE:
				intent.setClass(TaskCenterAct.this, LoginAct.class);
				startActivity(intent);
				try
				{
					TaskCenterAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			}
		}
	};

	private OnItemClickListener itemClickListener = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			if (pop != null)
			{
				pop.dismiss();
			}
			try
			{
				getData(position);
			}
			catch (Exception e)
			{

				e.printStackTrace();
			}
		}
	};

	protected void onDestroy()
	{
		pauseFlag = true;
		taskCenterList = null;
		taskChooseBtn = null;
		taskType = null;
		pop = null;
		allData = null;
		taskAdapter = null;
		taskReturnBtn = null;
		super.onDestroy();

	};

	@Override
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

	class NumberButtonClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.number1:
				serachNumbers.setText(serachNumbers.getText() + "1");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.number2:
				serachNumbers.setText(serachNumbers.getText() + "2");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.number3:
				serachNumbers.setText(serachNumbers.getText() + "3");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.number4:
				serachNumbers.setText(serachNumbers.getText() + "4");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.number5:
				serachNumbers.setText(serachNumbers.getText() + "5");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.number6:
				serachNumbers.setText(serachNumbers.getText() + "6");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.number7:
				serachNumbers.setText(serachNumbers.getText() + "7");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.number8:
				serachNumbers.setText(serachNumbers.getText() + "8");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.number9:
				serachNumbers.setText(serachNumbers.getText() + "9");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.number0:
				serachNumbers.setText(serachNumbers.getText() + "0");
				serachNumbers.setSelection(serachNumbers.getText().toString().length());
				break;
			case R.id.delete:
				if (serachNumbers.getText().length() > 0)
				{
					serachNumbers.setText(serachNumbers.getText().subSequence(0, serachNumbers.getText().length() - 1));
					serachNumbers.setSelection(serachNumbers.getText().toString().length());
				}
				break;
			case R.id.query_btn:
				filterData(serachNumbers.getText().toString(), currentTaskType, currentTaskState);
				if (searchDialog.isShowing())
				{
					searchDialog.dismiss();
				}
				break;
			case R.id.all_result:
				currentData.clear();
				currentData.addAll(allData);
				taskAdapter = new TaskCenterListAdapter(allData, TaskCenterAct.this, itemBtnOnClickListener);
				taskCenterList.setAdapter(taskAdapter);
				for (int i = 0; i < groupTypes.length; i++)
				{
					taskCenterList.expandGroup(i);
				}
				if (searchDialog.isShowing())
				{
					searchDialog.dismiss();
				}
				break;
			case R.id.cancel:
				if (searchDialog.isShowing())
				{
					searchDialog.dismiss();
				}
				break;
			default:
				break;
			}
		}

	}

}
