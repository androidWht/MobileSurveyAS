package com.sinosoft.ms.activity.task.survey.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.PropDetailData;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名：5.财产损失页面 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 下午4:25:11
 */

public class SurveyPropertyFrag extends Fragment {

	private View view;
	private LinearLayout lossAmount_ly;
	private EditText lossAmountExt;
	private Spinner isLossAmountSp;

	private List<PropData> propDatas;
	private PropData propData;
	// 报案号
	private String registNo;
	private int index;
	private String lossTypeStr = "";
	private String lossParty;
	private List<String> isLossList = new ArrayList<String>();

	// 查勘基本信息
	private SurveyTreatmentDatabase2 database;

	// 数据字典
	private Map<String, Map<String, String>> dataMap;
	private Map<String, Map<String, String>> localItemConf;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 获取查勘基本信息
		database = SurveyTreatmentDatabase2.getInstance();
		// 获取数据字典
		dataMap = DictCashe.getInstant().getDict();
		localItemConf = AppConstants.getLocalItemConf();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_survey_property, container, false);
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

	public void initView(View propView) {
		this.lossAmount_ly = (LinearLayout) propView
				.findViewById(R.id.lossAmount_ly); // 财产损失金额线性布局
		this.lossAmountExt = (EditText) propView
				.findViewById(R.id.lossAmountExt);
		this.isLossAmountSp = (Spinner) propView
				.findViewById(R.id.isLossAmountSp);
		isLossAmountSp.setOnItemSelectedListener(new MyOnItemSelectListener());
	}

	/**
	 * 设置财产损失数据
	 */
	public void initData() {
		propDatas = database.getProDatas();
		String isExits = "2";
		if (propDatas != null && !propDatas.isEmpty()) {
			propData = propDatas.get(0);
			String property = propData.getRescueFee();
			if (StringUtils.isNotEmpty(property)) {
				lossAmountExt.setText(property);
				lossAmount_ly.setVisibility(View.VISIBLE);
				isExits = "1";
			}
		}
		SpinnerUtils.setSpinnerData(getActivity(), isLossAmountSp,
				localItemConf.get("Property"), isExits,"");
	}

	class MyOnItemSelectListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if ("存在".equals(isLossAmountSp.getSelectedItem().toString())) {
				lossAmount_ly.setVisibility(View.VISIBLE);

			} else {
				lossAmount_ly.setVisibility(View.GONE);
				lossAmountExt.setText("");
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	public void refreshData(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 设置财产损失数据
	 */
	public List<PropData> getPropData() {
		if (null != view && null != isLossAmountSp) {
			String isLossAmount = isLossAmountSp.getSelectedItem().toString();
			if (isLossAmount.equals("存在")) {
				propDatas = new ArrayList<PropData>();
				if (propData == null) {
					propData = new PropData();
					propData.setRegistNo(registNo);
				}
				propData.setRescueFee(lossAmountExt.getText().toString());
				propData.setSumLossFee(lossAmountExt.getText().toString());
				/** 默认字段 **/
				propData.setReserveFlag("0");
				propData.setLossParty("地面财产");
				PropDetailData detailData = null;
				List<PropDetailData> propDetailDatas = propData
						.getPropDetailDatas();
				if (propDetailDatas == null) {
					propDetailDatas = new ArrayList<PropDetailData>();
					detailData = new PropDetailData();
					propDetailDatas.add(detailData);
					propData.setPropDetailDatas(propDetailDatas);
				}
				if (!propDetailDatas.isEmpty()) {
					detailData = propDetailDatas.get(0);
				}
				if (detailData == null) {
					detailData = new PropDetailData();
					propDetailDatas.add(detailData);
				}
				propDatas.add(propData);
				detailData.setLossItemName("默认损失财产");
				detailData.setLossFee(lossAmountExt.getText().toString());
				detailData.init();
				
				/** end **/
			} else {
				propDatas = new ArrayList<PropData>();
			}
			return propDatas;
		} else {
			return null;
		}
	}

}
