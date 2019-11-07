package com.sinosoft.ms.activity.task.survey.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.survey.SurveyAct;
import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.datadialog.DateDialogUtils;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名：3.基本信息页面 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 下午4:24:25
 */

public class SurveyBasicInfoFrag extends Fragment implements OnClickListener,
		OnCheckedChangeListener {
	private View view;
	// 报案号
	private TextView registNoTxt;
	// 事故处理类型
	private Spinner accidentHandleTypeSp;
	// 损失类型
	private Spinner lossTypeSp;
	// 出险原因
	private Spinner accidentCauseSp;
	// 事故类型
	private EditText accidentTypeEdit;
	private Spinner surveyTypeSp;
	// 第一现场查勘
	private Spinner firstSiteSurveySp;
	// 查勘日期
	private EditText surveyTimeBtn;
	// 同出现地点
	private Button surveyPlaceBtn;
	// 查勘地点
	private EditText surveyAddrExt;
	// 出现地点、查勘意见
	private EditText dangerPlaceExt, surveyFeedbackExt;

	private Dialog dialog;

	/**
	 * 查勘意见
	 */
	// 查勘意见数据
	private String feedbacks[];
	// 所有查勘意见
	private String totalFeedbacks[] = null;
	// 所有是否选中查勘意见
	private boolean totalFeedbacksChecked[] = null;
	// 自定义查勘意见
	private Button localFeedBackBtn;
	private String[] surveyIdeas = new String[] { "碰撞痕迹吻合", "有交警证明", "无交警证明",
			"证件齐全", "属于保险责任" };
	/** 是否选中查勘意见 **/
	private boolean surveyIdeasSelected[] = new boolean[] { false, false,
			false, false, false };
	// 查勘意见选择
	private Button selectOptionBtn;

	// 是否双免
	private RadioGroup twoAvoidFlagGroup;
	// 是否双免
	private TextView twoTitle;
	private EditText surveyTimetext;
	// 查看基本信息
	private BasicSurvey basicSurvey;
	private SurveyTreatmentDatabase2 database;
	// 数据字典
	private Map<String, Map<String, String>> dataMap;
	private Map<String, Map<String, String>> localItemConf;

	// 事故类型
	private String accidentType;
	// 报案信息
	private RegistData registData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		database = SurveyTreatmentDatabase2.getInstance();
		dataMap = DictCashe.getInstant().getDict();
		localItemConf = AppConstants.getLocalItemConf();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_survey_basic, container, false);
		return view;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		view = null;
	}

	/**
	 * 基本信息视图
	 */
	public void initView(View basicView) {
		surveyTimeBtn = (EditText) basicView.findViewById(R.id.surveyTimetext);
		twoAvoidFlagGroup = (RadioGroup) basicView
				.findViewById(R.id.twoAvoidRadioGroup);
		twoTitle = (TextView) basicView.findViewById(R.id.twoTitle);
		twoAvoidFlagGroup.setOnCheckedChangeListener(this);

		localFeedBackBtn = (Button) basicView
				.findViewById(R.id.localFeedBackBtn); // 保存按钮
		localFeedBackBtn.setOnClickListener(this);
		registNoTxt = (TextView) basicView.findViewById(R.id.registNoTxt); // 报案号
		accidentHandleTypeSp = (Spinner) basicView
				.findViewById(R.id.accidentHandleTypeSp); // 事故处理类型
		lossTypeSp = (Spinner) basicView.findViewById(R.id.lossTypeSp); // 损失类型
		accidentCauseSp = (Spinner) basicView
				.findViewById(R.id.accidentCauseSp); // 出险原因
		accidentTypeEdit = (EditText) basicView
				.findViewById(R.id.accidentTypeEdit); // 事故类型
		firstSiteSurveySp = (Spinner) basicView
				.findViewById(R.id.firstSiteSurveySp); // 第一现场
		//surveyTimeBtn = (Button) basicView.findViewById(R.id.surveyTimeBtn); // 查勘日期

		surveyAddrExt = (EditText) basicView.findViewById(R.id.surveyAddrExt); // 查勘地点
		dangerPlaceExt = (EditText) basicView.findViewById(R.id.dangerPlaceExt); // 出险地点
		surveyFeedbackExt = (EditText) basicView
				.findViewById(R.id.surveyFeedbackExt); // 查勘意见
		selectOptionBtn = (Button) basicView.findViewById(R.id.selectOptionBtn);
		selectOptionBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Dialog dialog = new AlertDialog.Builder(getActivity())
						.setTitle("请选择查勘意见")
						.setMultiChoiceItems(
								totalFeedbacks,
								totalFeedbacksChecked,
								new DialogInterface.OnMultiChoiceClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int which, boolean isChecked) {
										totalFeedbacksChecked[which] = isChecked;
									}
								})
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										StringBuffer msgs = new StringBuffer("");
										for (int i = 0; i < totalFeedbacksChecked.length; i++) {
											if (totalFeedbacksChecked[i]) {
												msgs.append(totalFeedbacks[i])
														.append(";");
											}
										}
										surveyFeedbackExt.setText(msgs
												.toString());
										basicSurvey.setOpinion(msgs.toString());
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {

									}
								}).create();
				dialog.show();
			}
		});
		this.surveyPlaceBtn = (Button) basicView
				.findViewById(R.id.surveyPlaceBtn); // 同出现地点
		this.surveyPlaceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				surveyAddrExt.setText(dangerPlaceExt.getText());
			}
		});

	}

	/**
	 * 设置基本信息数据
	 */
	public void initData() {
		// 设置查勘意见列表
		setSurveyFeedbacks();

		basicSurvey = database.getBasicSurvey();

		if (basicSurvey == null) {
			basicSurvey = new BasicSurvey();
		}
		basicSurvey.init();// 将属性为null的设置为空字符“”；
		registNoTxt.setText(basicSurvey.getRegistNo() + "");
	//	surveyTimetext.setText(basicSurvey.getCheckDate() + "");
		surveyTimeBtn.setText(basicSurvey.getCheckDate() + "");
		surveyAddrExt.setText(basicSurvey.getCheckSite() + "");
		dangerPlaceExt.setText(basicSurvey.getDamageAddress() + "");
		surveyFeedbackExt.setText(basicSurvey.getOpinion() + "");

		String manageType = basicSurvey.getManageType();

		/** 设置事故类型默认值2013.7.1 严植培修改 **/
		if ("单方事故".equals(this.accidentType)) { // 单方事故 - 保险公司处理
			SpinnerUtils.setSpinnerData(
					getActivity(),
					accidentHandleTypeSp,
					dataMap.get("AccidentManageType"),
					SpinnerUtils.getKey("保险公司处理",
							dataMap.get("AccidentManageType")),"");
		} else if ("双方事故".equals(this.accidentType)) { // 双方事故 - 交警简易程序处理
			SpinnerUtils.setSpinnerData(
					getActivity(),
					accidentHandleTypeSp,
					dataMap.get("AccidentManageType"),
					SpinnerUtils.getKey("交警简易程序处理",
							dataMap.get("AccidentManageType")),"");
		} else {
			SpinnerUtils.setSpinnerData(
					getActivity(),
					accidentHandleTypeSp,
					dataMap.get("AccidentManageType"),
					StringUtils.isNotEmpty(manageType) ? manageType
							: SpinnerUtils.getKey("保险公司处理",
									dataMap.get("AccidentManageType")),"");
		}

		accidentTypeEdit.setText(accidentType);

		SpinnerUtils.setSpinnerData(getActivity(), accidentCauseSp,
				dataMap.get("DamageCode"), basicSurvey.getDamageCode(),"");

		surveyTimeBtn.setOnClickListener(this);

		accidentCauseSp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// 匹配出险原因和损失类型
				String accidentValue = accidentCauseSp.getSelectedItem()
						.toString();
				if (accidentValue.contains("盗抢")) {
					accidentValue = "全车盗抢";
				} else if (accidentValue.contains("玻璃破碎")) {
					accidentValue = "玻璃单独破碎";
				}
				String key = SpinnerUtils.getKey(accidentValue,
						dataMap.get("LossType"));
				SpinnerUtils.setSpinnerData(getActivity(), lossTypeSp, dataMap
						.get("LossType"), StringUtils.isNotEmpty(key) ? key
						: SpinnerUtils.getKey("非全损", dataMap.get("LossType")),"");

				// 出险原因传值到车辆信息中
				((SurveyAct) getActivity()).setAccidentCause(accidentCauseSp
						.getSelectedItem().toString());

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		String firstFlag = basicSurvey.getFirstSiteFlag();
		SpinnerUtils.setSpinnerData(
				getActivity(),
				firstSiteSurveySp,
				localItemConf.get("FirstSiteFlag"),
				StringUtils.isNotEmpty(firstFlag) ? firstFlag : SpinnerUtils
						.getKey("现场查勘", localItemConf.get("FirstSiteFlag")),"");
		//
		String EnabledSubrPlatform = basicSurvey.getEnabledSubrPlatform();
		String IsKindCodeA = basicSurvey.getIsKindCodeA();

		/**
		 * 2013.8.15 设置双免控件
		 */
		if (basicSurvey.getTwoAvoidFlag().equals("2")) {
			RadioButton btn = (RadioButton) twoAvoidFlagGroup.getChildAt(1);
			btn.setEnabled(true);
			btn.setChecked(true);
			basicSurvey.setTwoAvoidSelected("1");
		} else if (basicSurvey.getTwoAvoidFlag().equals("3")) {
			twoTitle.setVisibility(View.GONE);
			twoAvoidFlagGroup.setVisibility(View.GONE);
		} else {
			RadioButton btn = (RadioButton) twoAvoidFlagGroup.getChildAt(1);
			btn.setEnabled(false);
			btn = (RadioButton) twoAvoidFlagGroup.getChildAt(0);
			btn.setChecked(true);
			basicSurvey.setTwoAvoidSelected("0");
		}
	}

	/**
	 * 自定义查勘意见
	 */
	private EditText mAddFeedbackEdit;
	private Button mConfirmBtn, mCancelBtn;
	private Dialog addFeedbackDialog;
	private ListView mSelfDefineListView;
	private FeedbackAdapter feedAdapter;

	private void showFeedbackList(Activity activity) {
		LayoutInflater inflater = activity.getLayoutInflater();
		final View layout = inflater.inflate(
				R.layout.layout_add_feedback_dialog,
				(ViewGroup) activity.findViewById(R.id.addfeedbackdialog));
		mAddFeedbackEdit = (EditText) layout.findViewById(R.id.inputTexgt);
		mConfirmBtn = (Button) layout.findViewById(R.id.sure);
		mCancelBtn = (Button) layout.findViewById(R.id.cancel);
		mConfirmBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mAddFeedbackEdit.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "请填写意见内容", Toast.LENGTH_SHORT)
							.show();
				} else {
					try {
						saveFeedbackFile(); // 保存查勘意见至本地
						updateSurveyFeedbacks(); // 获取查勘意见
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (addFeedbackDialog.isShowing()) {
						addFeedbackDialog.dismiss();
					}
					Toast.makeText(getActivity(), "增加成功", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
		mCancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (addFeedbackDialog.isShowing()) {
					addFeedbackDialog.dismiss();
				}
			}
		});
		mSelfDefineListView = (ListView) layout.findViewById(R.id.mydefinelist);
		if (null != feedbacks && feedbacks.length > 0) {
			feedAdapter = new FeedbackAdapter();
			mSelfDefineListView.setAdapter(feedAdapter);
		}
		if (null != feedAdapter) {
			feedAdapter.notifyDataSetChanged();
		}
		addFeedbackDialog = CustomDialog
				.showTip(getActivity(), "自定义意见", layout);
	}

	// 设置查勘意见列表
	private void setSurveyFeedbacks() {
		try {
			getSurveyFile(); // 获取本地查勘信息
			if (feedbacks != null) {
				int feedbackSize = feedbacks.length + surveyIdeas.length; // 总长度
				totalFeedbacks = new String[feedbackSize]; // 实例化意见数据
				totalFeedbacksChecked = new boolean[feedbackSize];
				int index = 0;
				int checkedIndex = 0;
				// 获取所有查勘意见
				for (int i = 0; i < surveyIdeas.length; i++) {
					totalFeedbacks[index++] = surveyIdeas[i];
					totalFeedbacksChecked[checkedIndex++] = false;
				}
				// 获取所有本地查勘意见
				for (int i = 0; i < feedbacks.length; i++) {
					totalFeedbacks[index++] = feedbacks[i];
					totalFeedbacksChecked[checkedIndex++] = false;
				}
			} else {
				totalFeedbacks = new String[surveyIdeas.length];
				totalFeedbacksChecked = new boolean[surveyIdeas.length];
				// 获取所有查勘意见
				for (int i = 0; i < surveyIdeas.length; i++) {
					totalFeedbacks[i] = surveyIdeas[i];
					totalFeedbacksChecked[i] = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} // 获取本地查勘意见
	}

	/** 保存查勘意见 **/
	private void saveFeedbackFile() throws Exception {
		// 是否新添加查勘意见
		for (String feedback : this.totalFeedbacks) {
			if (mAddFeedbackEdit.getText().toString().equals(feedback)
					|| "".equals(mAddFeedbackEdit.getText().toString())) {
				return;
			}
		}
		// 是否存在SDCcard
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory()
					+ File.separator + AppConstants.SDCARD_MAIN_DIR
					+ File.separator + AppConstants.SURVEY_FILE; // 文件保存路径
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile(); // 创建文件
			}
			PrintStream ps = new PrintStream(new FileOutputStream(file, true));
			ps.println(mAddFeedbackEdit.getText().toString()
					+ "_surveyFeedback"); // 写入查勘意见
			ps.close(); // 关闭输出流
		} else {
			Toast.makeText(getActivity(), "SDCard不存在，查勘意见保存失败！",
					Toast.LENGTH_LONG).show();
		}
	}

	/** 读取查勘意见文件 **/
	public void getSurveyFile() throws Exception {
		// 是否存在SDCcard
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory()
					+ File.separator + AppConstants.SDCARD_MAIN_DIR
					+ File.separator + AppConstants.SURVEY_FILE; // 文件路径
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
				return;
			}
			Scanner scanner = new Scanner(file);
			StringBuffer msg = new StringBuffer();
			while (scanner.hasNextLine()) {
				msg.append(scanner.nextLine()); // 读取文件信息
			}
			scanner.close(); // 关闭输出流

			if (null != msg && !msg.toString().equals("")) {
				feedbacks = msg.toString().split("_surveyFeedback"); // 拆分内容信息
			} else {
				feedbacks = null;
			}
			if (feedbacks != null) {
				for (String feedback : feedbacks) {
					feedback.trim();
				}
			}
		} else {
			Toast.makeText(getActivity(), "SDCard不存在，读取失败!", Toast.LENGTH_LONG)
					.show();
		}
	}

	public void deleteFeedback(int index) throws Exception {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory()
					+ File.separator + AppConstants.SDCARD_MAIN_DIR
					+ File.separator + AppConstants.SURVEY_FILE; // 文件路径
			File file = new File(path);
			if (!file.exists()) {
				// Toast.makeText(this, "暂无任何本地定损意见!", Toast.LENGTH_LONG).show()
				// ;
				return;
			}
			Scanner scanner = new Scanner(file);
			StringBuffer msg = new StringBuffer();
			while (scanner.hasNextLine()) {
				msg.append(scanner.nextLine()); // 读取文件信息
			}
			scanner.close(); // 关闭输出流
			int index1 = msg.indexOf(feedbacks[index]);
			if (index1 != -1) {
				if (index1 + feedbacks[index].length() + 14 >= msg.length()) {
					msg.delete(msg.indexOf(feedbacks[index]), index1
							+ feedbacks[index].length());
				} else {
					msg.delete(msg.indexOf(feedbacks[index]), index1
							+ feedbacks[index].length() + 15);
				}

				if (file.exists()) {
					file.delete();
					file.createNewFile();
				}
				PrintStream ps = new PrintStream(new FileOutputStream(file,
						true));
				ps.println(msg.toString()); // 写入定损意见
				ps.close(); // 关闭输出流
			}
		} else {
			Toast.makeText(getActivity(), "SDCard不存在，读取失败!", Toast.LENGTH_LONG)
					.show();
		}
	}

	private void updateSurveyFeedbacks() {
		try {
			getSurveyFile(); // 获取本地查勘信息
			if (null != feedbacks && feedbacks.length > 0) {
				int feedbackSize = feedbacks.length + surveyIdeas.length; // 总长度
				String[] tempTotalFeedbacks = new String[feedbackSize]; // 实例化定损意见数据
				boolean[] tempTotalFeedbacksChecked = new boolean[feedbackSize];
				int index = 0;
				int checkedIndex = 0;
				// 获取所有定损意见
				for (int i = 0; i < surveyIdeas.length; i++) {
					tempTotalFeedbacks[index++] = surveyIdeas[i];
					if (index < totalFeedbacksChecked.length) {
						tempTotalFeedbacksChecked[checkedIndex++] = totalFeedbacksChecked[index - 1];
					} else {
						tempTotalFeedbacksChecked[checkedIndex++] = false;
					}
				}
				// 获取所有本地定损意见
				for (int i = 0; i < feedbacks.length; i++) {
					tempTotalFeedbacks[index++] = feedbacks[i];
					if (index < totalFeedbacksChecked.length) {
						tempTotalFeedbacksChecked[checkedIndex++] = totalFeedbacksChecked[index - 1];
					} else {
						tempTotalFeedbacksChecked[checkedIndex++] = false;
					}
				}
				totalFeedbacks = tempTotalFeedbacks;
				totalFeedbacksChecked = tempTotalFeedbacksChecked;
			} else {
				totalFeedbacks = new String[surveyIdeas.length];
				totalFeedbacksChecked = new boolean[surveyIdeas.length];
				// 获取所有定损意见
				for (int i = 0; i < surveyIdeas.length; i++) {
					totalFeedbacks[i] = surveyIdeas[i];
					totalFeedbacksChecked[i] = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} // 获取本地查勘意见
	}

	private void showDateDialog(Button dateBtn) {
		int curYear = 0, curMonth = 0, curDay = 0;
		String str = dateBtn.getText().toString();
		try {
			if (!str.trim().equals("") && str.length() >= 8) {
				curYear = Integer.valueOf(String.valueOf(str)
						.replaceAll("-", "").trim().substring(0, 4));
				curMonth = Integer.valueOf(String.valueOf(str)
						.replaceAll("-", "").trim().substring(4, 6)) - 1;
				curDay = Integer.valueOf(String.valueOf(str)
						.replaceAll("-", "").trim().substring(6, 8));
			} else {
				Calendar cal = Calendar.getInstance();
				curYear = cal.get(Calendar.YEAR);
				curMonth = cal.get(Calendar.MONTH);
				curDay = cal.get(Calendar.DAY_OF_MONTH);

			}
		} catch (Exception e) {
			Calendar cal = Calendar.getInstance();
			curYear = cal.get(Calendar.YEAR);
			curMonth = cal.get(Calendar.MONTH);
			curDay = cal.get(Calendar.DAY_OF_MONTH);
			// e.printStackTrace();
		}
		DateDialogUtils dateDialogUtils = new DateDialogUtils();
		dateDialogUtils.dateDialog(getActivity(), dateBtn, curYear, curMonth,
				curDay);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.localFeedBackBtn:
			// 显示查勘意见列表
			showFeedbackList(getActivity());
			break;
		case R.id.surveyTimeBtn:
			// 显示时间选择控件
			showDateDialog((Button) arg0);
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
		case R.id.radio0:
			basicSurvey.setTwoAvoidSelected("0");
			break;
		case R.id.radio1:
			basicSurvey.setTwoAvoidSelected("1");
			break;
		}
	}

	public final class ViewHolder {
		public TextView feedback;
		public Button deleteBtn;
	}

	class FeedbackAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return feedbacks.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return feedbacks[arg0];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.item_feedback, null);
				holder.feedback = (TextView) convertView
						.findViewById(R.id.feedback_text);
				holder.deleteBtn = (Button) convertView
						.findViewById(R.id.delete_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.feedback.setText(feedbacks[position]);
			holder.deleteBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					try {
						deleteFeedback(position);
						getSurveyFile();
						updateSurveyFeedbacks();
						if (addFeedbackDialog.isShowing()) {
							addFeedbackDialog.dismiss();
						}
					} catch (Exception e) {
						// e.printStackTrace();
					}
					Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_LONG)
							.show();
				}
			});
			return convertView;
		}

	}

	public void refreshData(String accidentType, RegistData registData) {
		this.accidentType = accidentType;
		this.registData = registData;

		// initData();
	}

	/**
	 * 检测基本信息是否完成
	 * 
	 * @return
	 */
	public String basicVerify() {
		if (null == basicSurvey) {
			database = SurveyTreatmentDatabase2.getInstance();
			basicSurvey = database.getBasicSurvey();
		}

		String errorMsg = null;
		String surveyTime = basicSurvey.getCheckDate();
		String damageDate = registData.getDamageDate();

		String EnabledSubrPlatform = basicSurvey.getEnabledSubrPlatform();
		String IsKindCodeA = basicSurvey.getIsKindCodeA();

		/*
		 * 2.1.当代位标志【EnabledSubrPlatform=1】为启用时，才允许录入代位求偿标识【SubrogationFlag】和索赔申请书标识
		 * 【ClaimSignFlag】。 2.2.没有投保车损险【IsKindCodeA=0】不能发起代位求偿【SubrogationFlag】。
		 * 2.3.代位求偿标志【SubrogationFlag】和索赔申请书标志【ClaimSignFlag】必须一致。
		 */
		String sub;
		String claim;

		if (StringUtils.isEmpty(errorMsg) && !surveyTime.equals("")
				&& !damageDate.equals("")) {
			Date enrollTime = MyUtils.format("yyyy-MM-dd", surveyTime);
			Date damageD = MyUtils.format("yyyy-MM-dd", damageDate);
			if (enrollTime != null && damageD != null) {
				boolean result = MyUtils.complateTime(surveyTime, damageDate);
				if (!result) {
					errorMsg = "基本信息中，查勘日期不能早于出险日期";
				} else {
					result = MyUtils.complateTime(surveyTime, DateTimeFactory
							.getInstance().getDat());
					if (!result) {
						errorMsg = "基本信息中，查勘日期不能晚于当天";
					}
				}
			}

		}
		if (errorMsg == null) {
			if (basicSurvey.getOpinion().equals("")) {
				errorMsg = "基本信息中，查勘意见不能为空";
			} else if (basicSurvey.getManageType().equals("")) {
				errorMsg = "事故处理类型不能为空";
			} else if (basicSurvey.getFirstSiteFlag().equals("")) {
				errorMsg = "第一现场查勘不能为空";
			}
		}
		return errorMsg;
	}

	/**
	 * 取得基本信息数据
	 * 
	 * @return
	 */
	public BasicSurvey getBasicSurvey() {
		if (null != view) {
			basicSurvey.setDamageCode(SpinnerUtils.getKey(accidentCauseSp
					.getSelectedItem().toString(), dataMap.get("DamageCode")));
			basicSurvey.setCheckSite(surveyAddrExt.getText().toString());
			basicSurvey.setCheckDate(surveyTimeBtn.getText().toString());
			basicSurvey.setDamageAddress(dangerPlaceExt.getText().toString());
			basicSurvey.setCheckDate(surveyTimeBtn.getText().toString());
			basicSurvey.setManageType(SpinnerUtils.getKey(accidentHandleTypeSp
					.getSelectedItem().toString(), dataMap
					.get("AccidentManageType")));
			basicSurvey.setLossType(SpinnerUtils.getKey(lossTypeSp
					.getSelectedItem().toString(), dataMap.get("LossType")));
			basicSurvey.setDamageCaseCode(SpinnerUtils.getKey(accidentTypeEdit
					.getText().toString(), dataMap.get("AccidentDutyType")));
			basicSurvey.setFirstSiteFlag(SpinnerUtils.getKey(firstSiteSurveySp
					.getSelectedItem().toString(), localItemConf
					.get("FirstSiteFlag")));
			basicSurvey.setOpinion(surveyFeedbackExt.getText().toString());

			// 2013.8.15 新增
			RadioButton btn = (RadioButton) view.findViewById(twoAvoidFlagGroup
					.getCheckedRadioButtonId());
			if (btn.getText().equals("是")) {
				basicSurvey.setTwoAvoidSelected("1"); // 是否双免 默认值为：2-否
			} else {
				basicSurvey.setTwoAvoidSelected("0");
			}

			/** 默认字段，默认赋值 start **/
			basicSurvey.setClaimSignFlag("0");// 索赔申请书标志 默认值为：0-否
			basicSurvey.setCaseFlag("0");// 互碰自赔 默认值为：0-否
			basicSurvey.setIntermediaryFlag("0");// 查勘类别 默认值为：0-司内查勘
			basicSurvey.setSubrogationFlag("0");// 是否代位求偿 默认值为：0-否
			/** 默认字段，默认赋值 end **/

			return basicSurvey;
		} else {
			return null;
		}
	}

}
