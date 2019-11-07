package com.sinosoft.ms.activity.task.survey.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.TestData;
import com.sinosoft.ms.activity.query.BluetoothActivity;
import com.sinosoft.ms.activity.task.survey.SurveyAct;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.po.CacheImage;
import com.sinosoft.ms.model.po.ImageBean;
import com.sinosoft.ms.service.IPrintInfoService;
import com.sinosoft.ms.service.impl.CaseCenterService;
import com.sinosoft.ms.service.impl.PrintInfoService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.ImageCenterDatabase;
import com.sinosoft.ms.utils.db.ImageCenterDatabase.DatabaseCallback;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名：6.信息提交页面 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 下午4:25:24
 */

public class SurveySubmitFrag extends Fragment implements OnClickListener {
	private static final int ERROR = 0;
	//保存成功标志
	private static final int SAVE_SUCCESS = 1100;
	//上传更新成功标志
	private static final int UPDATE_SUCCESS = 1111;
	//图片未上传标志
	private static final int NOT_UPLOAD_IMAGES =1112;
	
	// 查勘打印预览按钮
	private Button surveyPrintBtn;
	// 查勘信息提交按钮
	private Button surveySubmitBtn;

	private ProgressDialogUtil progressDialog;
	private Dialog dialog;
	
	//报案信息
	private RegistData registData;
	//报案ID
	private String registId;
	//报案号
	private String registNo;
	
	private ImageCenterDatabase imageDatabase;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		imageDatabase = new ImageCenterDatabase(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.layout_survey_submit, container, false);
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView(view);
	}

	public void initView(View submitView) {
		// 查勘单打印
		this.surveyPrintBtn = (Button) submitView
				.findViewById(R.id.surveyPrintBtn);
		this.surveyPrintBtn.setOnClickListener(this);
		// 查勘信息提交
		this.surveySubmitBtn = (Button) submitView
				.findViewById(R.id.surveySubmitBtn);
		this.surveySubmitBtn.setOnClickListener(this);
	}
	
	/**
	 * 查勘打印预览
	 */
	private void surveyPrintBrowse(){
		progressDialog = new ProgressDialogUtil(getActivity());
		progressDialog.show("请稍候...");
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
//				try {
//					keepData();
//				} catch (Exception e) {
//					// e.printStackTrace();
//				}
				Intent intent2 = new Intent(getActivity(),
						BluetoothActivity.class);
				intent2.putExtra("isPrintting", false);
				intent2.putExtra("registId", registId);
				String title = "	           移动查勘报告打印凭证    	   ";
				TestData.setTitle(title);
				IPrintInfoService printInfoService = new PrintInfoService();
				String surveyPrint = printInfoService.surveyVoucher(
						getActivity(), registNo, registData);
				TestData.setContent(surveyPrint);
				startActivity(intent2);

				progressDialog.dismiss();
				Looper.loop();
			}

		}.start();

	}
	
	private void surveySubmit(){
		progressDialog = new ProgressDialogUtil(getActivity());
		progressDialog.show("加载中，请稍后...");
		imageDatabase.asyncSelect(registNo, new DatabaseCallback() {

			@Override
			public void post(List<ImageCenterBean> imageDatas) {
				// TODO Auto-generated method stub
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
//				imageDatas = list;
				if (imageDatas == null || imageDatas.size() == 0) {
					dialog = CustomDialog.show(
							getActivity(), "信息提示",
							"您还未现场拍照，是否需要拍照?", "需要", "不需要",
							new OnClickListener() {

								@Override
								public void onClick(View arg0) {
//									onPageSelected(1); // 跳转到现场拍照
									dialog.dismiss();
								}
							}, new OnClickListener() {

								@Override
								public void onClick(View arg0) {
//									saveData();
									submit();
									dialog.dismiss();
								}
							});
				} else {
					// 是否上传图片
					for (ImageCenterBean bean : imageDatas) {
						if (bean.getIsUpload().equals("0")) {
							CustomDialog.show(getActivity(), "信息提示",
									"图片未上传，请先上传图片!");
							handler.sendEmptyMessage(NOT_UPLOAD_IMAGES);
							return;
						}
					}
					//提交
					submit();
				}
			}
		});
	}
	
	//查看信息提交
	public void submit() {
//		String errorMsg = checkIsFinish();
		String errorMsg = "";
		if (errorMsg.equals("")) {
			progressDialog = new ProgressDialogUtil(getActivity());
			progressDialog.show("数据上传中...");
			new Thread() {
				public void run() {
					Message msg = new Message();
					try {
//						keepData();
						CaseCenterService service = new CaseCenterService(
								getActivity());
						SurveyTreatmentDatabase2 database = SurveyTreatmentDatabase2
								.getInstance();
						List<PersonData> person = new ArrayList<PersonData>();
						// person.add(database.getPersonData());
						List<RegistThirdCarData> registThirdCarDatas = database
								.getRegistThirdCarDatas();
						service.updateSurveyInfo(registId,database.getBasicSurvey(),
								registThirdCarDatas,
								database.getLiabilityRatios(),
								database.getCarDatas(), database.getProDatas(),
								database.getPersonDatas(), database.getPolicyDatas());
						msg.what = UPDATE_SUCCESS;
					} catch (Exception e) {
						e.printStackTrace();
						msg.what = MyUtils.getErrorCode(e.getMessage());
						String error = e.getMessage();
						if (StringUtils.isEmpty(error)) {
							error = "请求失败";
						}
						msg.obj = error;
					} finally {
						handler.sendMessage(msg);
					}
				};
			}.start();
		} else {
			ToastDialog.show(getActivity(), errorMsg, ToastDialog.ERROR);
		}
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(progressDialog != null){
				progressDialog.dismiss();
			}
			switch(msg.what){
			case ERROR:
				String errorMsg = "";
				if (msg != null && msg.obj != null) {
					errorMsg = msg.obj.toString();
				} else {
					errorMsg = "操作错误";
				}

				if (StringUtils.isNotEmpty(errorMsg)) {
					if (errorMsg.contains("被处理")) {
						if (dialog != null) {
							dialog.dismiss();
						}
						dialog = CustomDialog.show(getActivity(),
								"信息提示", errorMsg, "确定", "",
								new OnClickListener() {
									@Override
									public void onClick(View v) {
										try {
											if (dialog != null) {
												dialog.dismiss();
											}
											getActivity().setResult(SAVE_SUCCESS, getActivity().getIntent());
											getActivity().finish();
											ActivityManage.getInstance().pop();
										} catch (Exception e) {
											//e.printStackTrace();
										}
									}
								}, null);

					} else {
						CustomDialog.show(getActivity(), "", ""
								+ msg.obj);
					}
				} else {
					CustomDialog.show(getActivity(), "", "操作错误");
				}
				break;
			case UPDATE_SUCCESS:
				if (dialog != null) {
					dialog.dismiss();
				}
				dialog = CustomDialog.show(getActivity(), "信息提示",
						"查勘信息提交成功", "确定", "", new OnClickListener() {
							@Override
							public void onClick(View v) {
								try {
									if (dialog != null) {
										dialog.dismiss();
									}
									List<ImageBean> imgBeans = CacheImage.getInstant().getImageBeans() ;
									for(ImageBean b : imgBeans){
										if(registNo.equals(b.getRegistNo())){
											b.setUpload(false) ;
											CacheImage.getInstant().setImageBeans(imgBeans) ;
											break ;
										}
									}
									
									Intent intent2 = new Intent(getActivity(), BluetoothActivity.class);
									intent2.putExtra("isPrintting", true);
									intent2.putExtra("registId", registId);
									String title = "	           移动查勘报告打印凭证    	   ";
									TestData.setTitle(title);
									IPrintInfoService printInfoService = new PrintInfoService();
									String surveyPrint = printInfoService.surveyVoucher(getActivity(),registNo,registData);
									TestData.setContent(surveyPrint);
									startActivity(intent2);
									
//									setResult(SAVE_SUCCESS, getIntent());
									getActivity().finish();
									ActivityManage.getInstance().pop();
								} catch (Exception e) {
									//e.printStackTrace();
								}
							}
						}, null);
				
				break;
			case NOT_UPLOAD_IMAGES:
				((SurveyAct)getActivity()).scrollToMedia();
				break;
			}
		}
		
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.surveyPrintBtn:
			surveyPrintBrowse();
			break;
		case R.id.surveySubmitBtn:
			surveySubmit();
			break;
		}
	}
	
	public void refreshData(RegistData registData,String registNo,String registId){
		this.registData = registData;
		this.registNo = registNo;
		this.registId = registId;
	}

}
