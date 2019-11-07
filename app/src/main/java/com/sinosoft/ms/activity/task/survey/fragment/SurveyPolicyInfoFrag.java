package com.sinosoft.ms.activity.task.survey.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PolicyDetailData;
import com.sinosoft.ms.model.PolicyKindData;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.Utility;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名：1.保单详情页面 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 下午4:21:12
 */

public class SurveyPolicyInfoFrag extends Fragment {
	private View view;

	// 商业险
	private TextView policy_policyNo;
	private TextView policy_insuredName;
	private TextView policy_licenseNo;
	private TextView policy_brandName;
	private TextView policy_startDate;
	private TextView policy_endDate;
	private ListView leftInsuranceList;
	private ListView rightInsuranceList;

	// 标题栏
	private TextView dividerTV;
	private TextView rightTitle1;
	private TextView rightTitle2;
	private TextView rightTitle3;
	private TextView rightTitle4;
	private TextView dividerTv1;
	private TextView dividerTv2;
	private LinearLayout policyLayout;
	private LinearLayout buninessLayout;

	// 交强险
	private TextView policy_policyNo2;
	private TextView policy_insuredName2;
	private TextView policy_licenseNo2;
	private TextView policy_brandName2;
	private TextView policy_startDate2;
	private TextView policy_endDate2;
	private ListView leftInsuranceList2;
	private ListView rightInsuranceList2;

	// 查勘数据库操作对象
	SurveyTreatmentDatabase2 database;
	// 保单数据列表
	List<PolicyData> policyDatas;
	// 保单详情
	PolicyDetailData policyDetailData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_insurance_info_detail,
				container, false);
		return view;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initPolicyData();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	public void initView(View policyView) {
		policy_policyNo = (TextView) policyView
				.findViewById(R.id.policy_policyNo);
		policy_insuredName = (TextView) policyView
				.findViewById(R.id.policy_insuredName);
		policy_licenseNo = (TextView) policyView
				.findViewById(R.id.policy_licenseNo);
		policy_brandName = (TextView) policyView
				.findViewById(R.id.policy_brandName);
		policy_startDate = (TextView) policyView
				.findViewById(R.id.policy_startDate);
		policy_endDate = (TextView) policyView
				.findViewById(R.id.policy_endDate);
		leftInsuranceList = (ListView) policyView
				.findViewById(R.id.leftInsuranceList);
		rightInsuranceList = (ListView) policyView
				.findViewById(R.id.rightInsuranceList);

		rightTitle1 = (TextView) policyView.findViewById(R.id.rightTitle1);
		rightTitle2 = (TextView) policyView.findViewById(R.id.rightTitle2);
		rightTitle3 = (TextView) policyView.findViewById(R.id.rightTitle3);
		rightTitle4 = (TextView) policyView.findViewById(R.id.rightTitle4);
		dividerTv1 = (TextView) policyView.findViewById(R.id.dividerTv1);
		dividerTv2 = (TextView) policyView.findViewById(R.id.dividerTv2);
		dividerTV = (TextView) policyView.findViewById(R.id.dividerTV);
		policyLayout = (LinearLayout) policyView
				.findViewById(R.id.policyLayout);
		buninessLayout = (LinearLayout) policyView
				.findViewById(R.id.businessLayout);

		// 交强险
		policy_policyNo2 = (TextView) policyView
				.findViewById(R.id.policy_policyNo2);
		policy_insuredName2 = (TextView) policyView
				.findViewById(R.id.policy_insuredName2);
		policy_licenseNo2 = (TextView) policyView
				.findViewById(R.id.policy_licenseNo2);
		policy_brandName2 = (TextView) policyView
				.findViewById(R.id.policy_brandName2);
		policy_startDate2 = (TextView) policyView
				.findViewById(R.id.policy_startDate2);
		policy_endDate2 = (TextView) policyView
				.findViewById(R.id.policy_endDate2);
		leftInsuranceList2 = (ListView) policyView
				.findViewById(R.id.leftInsuranceList2);
		rightInsuranceList2 = (ListView) policyView
				.findViewById(R.id.rightInsuranceList2);
	}

	public void refreshData() {
		initPolicyData();
	}
	
	public List<PolicyData> getPolicyDatas(){
		if(null != view){
			return policyDatas;
		}else{
			return null;
		}
	}

	/**
	 * 设置保单信息数据
	 */
	private void initPolicyData() {
		// 获取全局静态变量中的数据
		database = SurveyTreatmentDatabase2.getInstance();
		policyDatas = database.getPolicyDatas();
		if (this.policyDatas != null && !policyDatas.isEmpty()) {
			for (PolicyData p : this.policyDatas) {
				// 商业险
				if ("0801".equals(p.getRiskCode())) {
					// 显示布局
					policyLayout.setVisibility(View.VISIBLE);
					dividerTV.setVisibility(View.VISIBLE);

					policyDetailData = p.getPolicyDetailData();
					policy_policyNo2.setText(policyDetailData.getPolicyNo());
					String code = policyDetailData.getRiskCode();
					if (StringUtils.isEmpty(code)) {
						code = "";
					}
					policy_insuredName2.setText(policyDetailData
							.getInsuredName());
					policy_licenseNo2.setText(policyDetailData.getLicenseNo());
					policy_brandName2.setText(policyDetailData.getBrandName());
					policy_startDate2.setText(policyDetailData.getStartDate());
					policy_endDate2.setText(policyDetailData.getEndDate());

					List<Map<String, String>> data = new ArrayList<Map<String, String>>();
					List<PolicyKindData> policyKindDataList = p
							.getPolicyKindDataList();
					int psize = policyKindDataList.size();

					// 左列表
					List<Map<String, String>> leftList = new ArrayList<Map<String, String>>();
					// 右列表
					List<Map<String, String>> rightList = new ArrayList<Map<String, String>>();

					for (int i = 0; i < psize; i++) {
						Map<String, String> map = new HashMap<String, String>();
						PolicyKindData policyKindData = policyKindDataList
								.get(i);

						String remark = policyKindData.getRemark();
						map.put("kindName", policyKindData.getKindName());
						map.put("amount", policyKindData.getAmount());
						map.put("remark", remark);
						if (i <= psize / 2) {
							leftList.add(map);
						} else {
							rightList.add(map);
						}
						// data.add(map);
					}

					// 左列表
					SimpleAdapter leftAdapter = new SimpleAdapter(
							getActivity(), leftList,
							R.layout.item_insurance_info_detail_item, new String[] {
									"kindName", "remark" }, new int[] {
									R.id.policy_kindName, R.id.policy_remark });
					leftInsuranceList2.setAdapter(leftAdapter);
					new Utility()
							.setListViewHeightBasedOnChildren(leftInsuranceList2);

					// 右列表
					if (rightList.size() > 0) {
						SimpleAdapter rightAdapter = new SimpleAdapter(
								getActivity(), rightList,
								R.layout.item_insurance_info_detail_item,
								new String[] { "kindName", "remark" },
								new int[] { R.id.policy_kindName,
										R.id.policy_remark });
						rightInsuranceList2.setAdapter(rightAdapter);
						new Utility()
								.setListViewHeightBasedOnChildren(rightInsuranceList2);
					} else {
						rightTitle1.setVisibility(View.GONE);
						rightTitle2.setVisibility(View.GONE);
						rightInsuranceList2.setVisibility(View.GONE);
					}
				}
				// 交强险
				else {
					buninessLayout.setVisibility(View.VISIBLE);
					policyDetailData = p.getPolicyDetailData();
					policy_policyNo.setText(policyDetailData.getPolicyNo());
					String code = policyDetailData.getRiskCode();
					if (StringUtils.isEmpty(code)) {
						code = "";
					}
					policy_insuredName.setText(policyDetailData
							.getInsuredName());
					policy_licenseNo.setText(policyDetailData.getLicenseNo());
					policy_brandName.setText(policyDetailData.getBrandName());
					policy_startDate.setText(policyDetailData.getStartDate());
					policy_endDate.setText(policyDetailData.getEndDate());

					List<Map<String, String>> data = new ArrayList<Map<String, String>>();
					List<PolicyKindData> policyKindDataList = p
							.getPolicyKindDataList();
					int psize = policyKindDataList.size();

					// 左列表
					List<Map<String, String>> leftList = new ArrayList<Map<String, String>>();
					// 右列表
					List<Map<String, String>> rightList = new ArrayList<Map<String, String>>();

					for (int i = 0; i < psize; i++) {
						Map<String, String> map = new HashMap<String, String>();
						PolicyKindData policyKindData = policyKindDataList
								.get(i);

						String remark = policyKindData.getRemark();
						map.put("kindName", policyKindData.getKindName());
						map.put("amount", policyKindData.getAmount());
						map.put("remark",
								remark + "/" + policyKindData.getAmount());

						if (i <= psize / 2) {
							leftList.add(map);
						} else {
							rightList.add(map);
						}
						// data.add(map);

					}

					// 左列表
					SimpleAdapter leftAdapter = new SimpleAdapter(
							getActivity(), leftList,
							R.layout.item_insurance_info_detail_item, new String[] {
									"kindName", "remark" }, new int[] {
									R.id.policy_kindName, R.id.policy_remark });
					leftInsuranceList.setAdapter(leftAdapter);
					new Utility()
							.setListViewHeightBasedOnChildren(leftInsuranceList);

					// 右列表
					if (rightList.size() > 0) {
						SimpleAdapter rightAdapter = new SimpleAdapter(
								getActivity(), rightList,
								R.layout.item_insurance_info_detail_item,
								new String[] { "kindName", "remark" },
								new int[] { R.id.policy_kindName,
										R.id.policy_remark });
						rightInsuranceList.setAdapter(rightAdapter);
						new Utility()
								.setListViewHeightBasedOnChildren(rightInsuranceList);
					} else {
						rightTitle3.setVisibility(View.GONE);
						rightTitle4.setVisibility(View.GONE);
						rightInsuranceList.setVisibility(View.GONE);
					}
				}
			}
		}
	}

}
