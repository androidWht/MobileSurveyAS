package com.sinosoft.ms.activity.task.loss.repair;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.model.SimpleItem;
import com.sinosoft.ms.service.IDeflossDataService;
import com.sinosoft.ms.service.ILossRepairInfoService;
import com.sinosoft.ms.service.impl.DeflossDataService;
import com.sinosoft.ms.service.impl.LossRepairInfoService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.SearchWather;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;

/**
 * 系统名：移动查勘定损系统 子系统名：新增修理界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 2013-5-28 下午12:05:40
 */

public class RepairAddAct extends Activity implements OnClickListener, OnItemSelectedListener
{
	private ILossRepairInfoService lossRepairInfoService = null;
	private IDeflossDataService deflossDataService = null;
	private LossRepairInfoItem lossRepairInfo = null;
	private Map<String, SimpleItem> repairMap = null;
	private DeflossData deflossData = null;
	private InputMethodManager imm = null;
	private boolean isUpdate = false;
	private Map<String, Map<String, String>> localMap = null;

	private Spinner repairNameSp = null;// 修理名称
	private EditText repairWorkeHoursExt = null;// 维修工时
	private EditText workeHoursFeeExt = null;// 工时费率
	private EditText repairMoneyExt = null;// 维修金额
	private EditText remarkExt = null;//
	private Dialog dialog = null;

	private String repairName = null;// 修理名称
	private String repairWorkeHours = null;// 维修工时
	private String workeHoursFee = null;// 工时费率
	private String repairMoney = null;// 维修金额
	private String remark = null;
	private String errorMsg = null;

	private String taskId = null;
	private int lossRepairItemId = 0;
	private Button saveBtn = null;
	private Button backBtn = null;

	private Button selectOptionBtn = null;
	private Button localFeedbackBtn = null;
	private Dialog saveDialog; // 查勘意见提示框
	private String totalFeedbacks[] = null; // 所有备注信息
	private boolean totalFeedbacksChecked[] = null; // 所有是否选中备注信息

	private double price = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair_add);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		ActivityManage.getInstance().push(this);
		Intent intent = getIntent();
		localMap = AppConstants.getLocalItemConf();
		taskId = (String) intent.getCharSequenceExtra("taskId");
		lossRepairItemId = intent.getIntExtra("lossRepairItemId", 0);
		// 设置视图控件
		setViewControl();
		// 装载数据
		loadData();

	}

	/**
	 * 设置视图控件
	 */
	private void setViewControl()
	{
		repairNameSp = (Spinner) findViewById(R.id.repairNameSp);// 修理名称
		repairWorkeHoursExt = (EditText) findViewById(R.id.repairWorkeHoursExt);// 维修工时
		workeHoursFeeExt = (EditText) findViewById(R.id.workeHoursFeeExt);// 工时费率
		repairMoneyExt = (EditText) findViewById(R.id.repairMoneyExt);// 维修金额
		remarkExt = (EditText) findViewById(R.id.remarkExt);//

		saveBtn = (Button) findViewById(R.id.saveBtn);//
		backBtn = (Button) findViewById(R.id.backBtn);//

		workeHoursFeeExt.addTextChangedListener(new SearchWather(this, workeHoursFeeExt, SearchWather.NUMBER4));
		repairWorkeHoursExt.addTextChangedListener(new SearchWather(this, repairWorkeHoursExt, SearchWather.NUMBER4));
		// 装载事件
		// repairWorkeHoursExt.setOnItemSelectedListener(this); // 维修工时
		workeHoursFeeExt.setOnFocusChangeListener(focusListener);
		repairNameSp.setOnItemSelectedListener(this);
		saveBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);

		selectOptionBtn = (Button) findViewById(R.id.selectOptionBtn);
		totalFeedbacks = new String[]
		{ "前杠", "左前杠", "右前杠", "左A柱", "右A柱", "左后视镜", "右后视镜", "头盖", "车顶", "左前叶", "右前叶", "左下裙", "右下裙", "左前门", "右前门", "左后门", "右后门", "左后杠", "右后杠", "后杠", "左后叶", "右后叶", "尾盖" };
		totalFeedbacksChecked = new boolean[]
		{ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false };
		selectOptionBtn.setOnClickListener(this);
		localFeedbackBtn = (Button) findViewById(R.id.localFeedbackBtn);
		localFeedbackBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				remarkExt.requestFocus();
				remarkExt.setEnabled(true);
				remarkExt.setSelection(remarkExt.getText().toString().length());
			}
		});
	}

	/**
	 * 加载数据
	 */
	private void loadData()
	{
		try
		{
			if (null == deflossDataService)
			{
				deflossDataService = new DeflossDataService();
			}
			if (null == lossRepairInfoService)
			{
				lossRepairInfoService = new LossRepairInfoService();
			}
			lossRepairInfo = lossRepairInfoService.getById(lossRepairItemId);
			deflossData = deflossDataService.getByTaskId(taskId);
			// 获取修理选项列表
			repairMap = getRepairMap();
			if (null != lossRepairInfo)
			{
				isUpdate = true;
				
//				if (0 < lossRepairInfo.getManHour())
//				{
//					repairWorkeHoursExt.setText(lossRepairInfo.getManHour() + "");
//					// SpinnerUtils.setSpinnerData(this, repairWorkeHoursSp,
//					// this.localMap.get("CountNum"),
//					// ((int) lossRepairInfo.getManHour()) + ""); // 维修工时
//				}
//				if (0 < lossRepairInfo.getHourFee())
//				{
//					BigDecimal bd = new BigDecimal(String.valueOf(lossRepairInfo.getHourFee()));
//					workeHoursFeeExt.setText(bd.toPlainString());
//					price = lossRepairInfo.getHourFee();
//				}
				
				
				if (0 < lossRepairInfo.getRepairFee())
				{
					BigDecimal bd = new BigDecimal(String.valueOf(lossRepairInfo.getRepairFee()));
					repairMoneyExt.setText(bd.toPlainString());
				}
				remarkExt.setText(lossRepairInfo.getRemark());
			}
			else
			{
				lossRepairInfo = new LossRepairInfoItem();
				// SpinnerUtils.setSpinnerData(this, repairWorkeHoursSp,
				// this.localMap.get("CountNum"), ""); // 维修工时
			}
			SpinnerUtils.set(this, repairNameSp, repairMap, lossRepairInfo.getRepairCode() + "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	private Map<String, SimpleItem> getRepairMap()
	{
		/** 2013-6-5 修改修理名称下拉选项 严植培 **/
		Map<String, SimpleItem> map = new HashMap<String, SimpleItem>();
		map.put("C00", new SimpleItem("拆装项目", "C00", "C00", "拆装项目", "1000", ""));
		map.put("B00", new SimpleItem("钣金项目", "B00", "B00", "钣金项目", "1000", ""));
		map.put("D00", new SimpleItem("电工项目", "D00", "D00", "电工项目", "1000", ""));
		map.put("X00", new SimpleItem("机修项目", "X00", "X00", "机修项目", "1000", ""));
		map.put("Q00", new SimpleItem("喷漆项目", "Q00", "Q00", "喷漆项目", "2000", ""));

		/*
		 * Map<String, SimpleItem> map = new HashMap<String,SimpleItem>();
		 * map.put("C100000", new SimpleItem("拆装项目","C100000","1000"));
		 * map.put("B100000", new SimpleItem("钣金项目","B100000","1000"));
		 * map.put("D100000", new SimpleItem("电工项目","D100000","1000"));
		 * map.put("X100000", new SimpleItem("机修项目","X100000","1000"));
		 * map.put("Q100000", new SimpleItem("喷漆项目","Q100000","2000"));
		 */

		/*
		 * map.put("B090000", new SimpleItem("车架校正", "B090000", "B100000",
		 * "钣金项目", "1000", "")); map.put("B089000", new SimpleItem("车身修理",
		 * "B089000", "B100000", "钣金项目", "1000", "")); map.put("B007000", new
		 * SimpleItem("发动机罩", "B007000", "B100000", "钣金项目", "1000", ""));
		 * map.put("B001620", new SimpleItem("前保险杠骨架", "B001620", "B100000",
		 * "钣金项目", "1000", "")); map.put("B001100", new SimpleItem("前保险杠皮",
		 * "B001100", "B100000", "钣金项目", "1000", "")); map.put("B053000", new
		 * SimpleItem("前轮罩板（右）", "B053000", "B100000", "钣金项目", "1000", ""));
		 * map.put("B052000", new SimpleItem("前轮罩板（左）", "B052000", "B100000",
		 * "钣金项目", "1000", "")); map.put("B026000", new SimpleItem("前翼子板（右）",
		 * "B026000", "B100000", "钣金项目", "1000", "")); map.put("B025000", new
		 * SimpleItem("前翼子板（左）", "B025000", "B100000", "钣金项目", "1000", ""));
		 * map.put("Q090000", new SimpleItem("车架", "Q090000", "Q100000", "喷漆项目",
		 * "2000", "")); map.put("Q088000", new SimpleItem("驾驶室", "Q088000",
		 * "Q100000", "喷漆项目", "2000", "")); map.put("Q089000", new
		 * SimpleItem("全车喷漆", "Q089000", "Q100000", "喷漆项目", "2000", ""));
		 * map.put("Q007000", new SimpleItem("发动机罩", "Q007000", "Q100000",
		 * "喷漆项目", "2000", "")); map.put("Q001100", new SimpleItem("前保险杠皮",
		 * "Q001100", "Q100000", "喷漆项目", "2000", "")); map.put("Q001620", new
		 * SimpleItem("前保险杠体", "Q001620", "Q100000", "喷漆项目", "2000", ""));
		 * map.put("Q001410", new SimpleItem("前杠导流板", "Q001410", "Q100000",
		 * "喷漆项目", "2000", "")); map.put("Q032000", new SimpleItem("前隔壁板",
		 * "Q032000", "Q100000", "喷漆项目", "2000", "")); map.put("Q009101", new
		 * SimpleItem("前横梁", "Q009101", "Q100000", "喷漆项目", "2000", ""));
		 * map.put("Q053000", new SimpleItem("前轮罩板（右）", "Q053000", "Q100000",
		 * "喷漆项目", "2000", "")); map.put("Q052000", new SimpleItem("前轮罩板（左）",
		 * "Q052000", "Q100000", "喷漆项目", "2000", "")); map.put("Q050500", new
		 * SimpleItem("前面板", "Q050500", "Q100000", "喷漆项目", "2000", ""));
		 * map.put("Q026000", new SimpleItem("前翼子板（右）", "Q026000", "Q100000",
		 * "喷漆项目", "2000", "")); map.put("Q025000", new SimpleItem("前翼子板（左）",
		 * "Q025000", "Q100000", "喷漆项目", "2000", "")); map.put("D039630", new
		 * SimpleItem("空调控制器拆装", "D039630", "D100000", "电工项目", "1000", ""));
		 * map.put("D029100", new SimpleItem("仪表台拆装", "D029100", "D100000",
		 * "电工项目", "1000", "")); map.put("D029140", new SimpleItem("仪表台下护板拆装",
		 * "D029140", "D100000", "电工项目", "1000", "")); map.put("D029150", new
		 * SimpleItem("手套箱拆装", "D029150", "D100000", "电工项目", "1000", ""));
		 * map.put("D028000", new SimpleItem("组合仪表拆装", "D028000", "D100000",
		 * "电工项目", "1000", "")); map.put("C001100", new SimpleItem("前保险杠皮",
		 * "C001100", "C100000", "拆装项目", "1000", "")); map.put("C001340", new
		 * SimpleItem("前保险杠饰条", "C001340", "C100000", "拆装项目", "1000", ""));
		 * map.put("C001410", new SimpleItem("前保险杠下导流板", "C001410", "C100000",
		 * "拆装项目", "1000", "")); map.put("C001521", new SimpleItem("前保险杠电眼",
		 * "C001521", "C100000", "拆装项目", "1000", "")); map.put("C001620", new
		 * SimpleItem("前保险杠骨架", "C001620", "C100000", "拆装项目", "1000", ""));
		 * map.put("C001621", new SimpleItem("前保险杠支架", "C001621", "C100000",
		 * "拆装项目", "1000", "")); map.put("C002000", new SimpleItem("中网",
		 * "C002000", "C100000", "拆装项目", "1000", "")); map.put("C002300", new
		 * SimpleItem("中网框", "C002300", "C100000", "拆装项目", "1000", ""));
		 * map.put("C004000", new SimpleItem("前大灯", "C004000", "C100000",
		 * "拆装项目", "1000", "")); map.put("C004420", new SimpleItem("前转向灯",
		 * "C004420", "C100000", "拆装项目", "1000", "")); map.put("C004500", new
		 * SimpleItem("前雾灯", "C004500", "C100000", "拆装项目", "1000", ""));
		 * map.put("C025000", new SimpleItem("前翼子板", "C025000", "C100000",
		 * "拆装项目", "1000", "")); map.put("C025002", new SimpleItem("前翼子板防撞条",
		 * "C025002", "C100000", "拆装项目", "1000", "")); map.put("C025300", new
		 * SimpleItem("前翼子板灯", "C025300", "C100000", "拆装项目", "1000", ""));
		 * map.put("C025410", new SimpleItem("前翼子板内胶", "C025410", "C100000",
		 * "拆装项目", "1000", "")); map.put("C007000", new SimpleItem("发动机罩",
		 * "C007000", "C100000", "拆装项目", "1000", "")); map.put("C007100", new
		 * SimpleItem("发动机罩内衬", "C007100", "C100000", "拆装项目", "1000", ""));
		 * map.put("C007300", new SimpleItem("发动机罩锁总成", "C007300", "C100000",
		 * "拆装项目", "1000", "")); map.put("C007400", new SimpleItem("发动机罩撑杆",
		 * "C007400", "C100000", "拆装项目", "1000", "")); map.put("C007500", new
		 * SimpleItem("发动机罩铰链", "C007500", "C100000", "拆装项目", "1000", ""));
		 * map.put("C007600", new SimpleItem("发动机罩拉线", "C007600", "C100000",
		 * "拆装项目", "1000", "")); map.put("C050600", new SimpleItem("散热器框架",
		 * "C050600", "C100000", "拆装项目", "1000", "")); map.put("C052000", new
		 * SimpleItem("前轮罩板", "C052000", "C100000", "拆装项目", "1000", ""));
		 * map.put("X046000", new SimpleItem("检修转向机", "X046000", "X100000",
		 * "机修项目", "1000", "")); map.put("X049210", new SimpleItem("更换燃油泵",
		 * "X049210", "X100000", "机修项目", "1000", ""));
		 */
		return map;
	}

	private OnFocusChangeListener focusListener = new OnFocusChangeListener()
	{
		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			countFee();
		}
	};

	/**
	 * 计算修理金额
	 */
	public void countFee()
	{
//		workeHoursFee = workeHoursFeeExt.getText().toString();
//		repairWorkeHours = repairWorkeHoursExt.getText().toString();
//		BigDecimal bd = new BigDecimal(String.valueOf(MyUtils.mult(StringUtils.toDouble(workeHoursFee), repairWorkeHours)));
//		repairMoneyExt.setText(bd.toPlainString());
		
		

		repairMoney = repairMoneyExt.getText().toString();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.saveBtn:
			try
			{
				if (checkInputData())
				{// 数据校验
					// 暂存信息
					///repairMoney = String.valueOf(StringUtils.toDouble(repairWorkeHours) * StringUtils.toDouble(workeHoursFee));
					///repairMoneyExt.setText(repairMoney);
					
					
					lossRepairInfo.setRegistNo(taskId);
					// 保存工种，由于精友数据会返回二级项目，而客户端不存在二级项目，所以当用户没有修改修理名称的情况下
					// 应该保留原有repairCode,和repairId
					String key = SpinnerUtils.key(repairName, repairMap);
					
					if (StringUtils.isNotEmpty(key))
					{
						// 如果现有的repairItemCode与所选择的repairItemCode类别一样，则不做任何操作
						// 如果用户修改了修理名称，则修改
						if (StringUtils.isEmpty(lossRepairInfo.getRepairItemCode()) || !lossRepairInfo.getRepairItemCode().contains(key))
						{
							SimpleItem simpleItem = SpinnerUtils.value(key, repairMap);
							lossRepairInfo.setRepairItemName(repairName);
							lossRepairInfo.setRepairItemCode(key);
							lossRepairInfo.setRepairCode(simpleItem.getValueCode());// 工种代码
							lossRepairInfo.setRepairName(simpleItem.getValueName());// 工种名称
							lossRepairInfo.setRepairId("CUSTOM");
						}

					}
					lossRepairInfo.setStatus("A");// 新增
					lossRepairInfo.setLevelCode("1");// 轻度受损
					
					///lossRepairInfo.setManHour(StringUtils.toDouble(repairWorkeHours)); // 工时
					///lossRepairInfo.setHourFee(StringUtils.toDouble(workeHoursFee));
					
					lossRepairInfo.setRepairFee(StringUtils.toDouble(repairMoney));
					
					lossRepairInfo.setRemark(remark);
					lossRepairInfo.setInsureTerm(deflossData.getInsureTerm());
					lossRepairInfo.setInsureTermCode(deflossData.getInsureTermCode());// /暂时修改
					// 添加标志
					lossRepairInfo.setSelfConfigFlag("0");
					if (!isUpdate)
					{
						if (lossRepairInfoService.save(lossRepairInfo) == 0)
						{
							throw new IllegalArgumentException("保存失败");
						}
					}
					else
					{
						if (!lossRepairInfoService.update(lossRepairInfo))
						{
							throw new IllegalArgumentException("保存失败");
						}
					}

					if (dialog != null)
					{
						dialog.dismiss();
					}
					dialog = CustomDialog.show(this, "信息提示", "保存成功!", "确定", "", new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							try
							{
								if (dialog != null)
								{
									dialog.dismiss();
								}
								RepairAddAct.this.finish();
								ActivityManage.getInstance().pop();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}, null);
				}
				else
				{
					ToastDialog.show(this, errorMsg, ToastDialog.ERROR);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				ToastDialog.show(this, "" + e.getMessage(), ToastDialog.ERROR);
			}
			break;
		case R.id.backBtn:
			try
			{
				this.finish();
				ActivityManage.getInstance().pop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		case R.id.selectOptionBtn:
		{// 备注的选择按钮
			Dialog dialog = new AlertDialog.Builder(RepairAddAct.this).setTitle("请选择备注信息").setMultiChoiceItems(totalFeedbacks, totalFeedbacksChecked, new DialogInterface.OnMultiChoiceClickListener()
			{

				@Override
				public void onClick(DialogInterface arg0, int which, boolean isChecked)
				{
					totalFeedbacksChecked[which] = isChecked;
				}
			}).setPositiveButton("确定", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface arg0, int arg1)
				{
					StringBuffer msgs = new StringBuffer("");
					for (int i = 0; i < totalFeedbacksChecked.length; i++)
					{
						if (totalFeedbacksChecked[i])
						{
							msgs.append(totalFeedbacks[i]).append(";");
						}
					}
					remarkExt.setText(msgs.toString());
					// remarkExt.setEnabled(false);
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface arg0, int arg1)
				{
					// remarkExt.setEnabled(false);
				}
			}).create();
			dialog.show();
		}
			break;
		}
	}

	private boolean checkInputData()
	{
		errorMsg = null;
		boolean result = false;
		repairName = repairNameSp.getSelectedItem().toString();
		repairWorkeHours = repairWorkeHoursExt.getText().toString(); // 工时
		workeHoursFee = workeHoursFeeExt.getText().toString();
		remark = remarkExt.getText().toString();
		countFee();
		if ("请选择".equals(repairName))
		{
			errorMsg = "请选择修理名称";
		}
//		else if (StringUtils.isEmpty(repairWorkeHours))
//		{
//			errorMsg = "请输入维修工时";
//		}
		// else if(StringUtils.toDouble(repairMoneyExt.getText().toString()) >
		// price){
		// errorMsg = "维修金额不能超过" + price ;
		// }
//		else if (0 >= StringUtils.toDouble(repairWorkeHours))
//		{
//			errorMsg = "维修工时不能小于或等于零";
//		}
//		else if (StringUtils.isEmpty(workeHoursFee))
//		{
//			errorMsg = "请输入工时费率";
//		}
//		else if (0 >= StringUtils.toDouble(repairWorkeHours))
//		{
//			errorMsg = "工时费率不能小于或等于零";
//		}
		else if (0 >= StringUtils.toDouble(repairMoney))
		{
			errorMsg = "维修金额不能小于或等于零";
		}
		else if (!SearchWather.checkNUMBER4(String.valueOf(StringUtils.toDouble(repairMoney))))
		{
			errorMsg = "维修金额总额不能大于7位整数";
		}		
//		else if (!SearchWather.checkNUMBER4(String.valueOf(StringUtils.toDouble(repairWorkeHours) * StringUtils.toDouble(workeHoursFee))))
//		{
//			errorMsg = "维修金额总额不能大于7位整数";
//		}
		if (StringUtils.isEmpty(errorMsg))
		{
			result = true;
		}
		return result;
	}

	@Override
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
	{
		if (0 != position)
		{
			String key = SpinnerUtils.key(repairNameSp.getSelectedItem().toString(), repairMap);
			if (StringUtils.isNotEmpty(key))
			{
				SimpleItem simpleItem = SpinnerUtils.value(key, repairMap);
				if (null != simpleItem)
				{
					// workeHoursFeeExt.setText(simpleItem.getPrice());
					price = StringUtils.toDouble(simpleItem.getPrice());
				}
				// if ("请选择".equals(repairWorkeHoursSp.getSelectedItem()
				// .toString())) {
				// repairWorkeHoursSp.setSelection(1);
				// }
				countFee();
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0)
	{
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		lossRepairInfoService = null;
		deflossDataService = null;
		lossRepairInfo = null;
		repairMap = null;
		deflossData = null;
		imm = null;
		isUpdate = false;

		repairNameSp = null;// 修理名称
		repairWorkeHoursExt = null;// 维修工时
		workeHoursFeeExt = null;// 工时费率
		repairMoneyExt = null;// 维修金额
		remarkExt = null;//
		dialog = null;

		repairName = null;// 修理名称
		repairWorkeHours = null;// 维修工时
		workeHoursFee = null;// 工时费率
		repairMoney = null;// 维修金额
		remark = null;
		errorMsg = null;

		taskId = null;
		lossRepairItemId = 0;
		saveBtn = null;
		backBtn = null;
	}

}
