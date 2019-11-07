/**
 * 系统名：移动查勘定损 
 * 子系统名：任务中心
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午3:12:16
 */
package com.sinosoft.ms.utils.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.opengl.Visibility;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;

/**
 * @author sinosoft
 * 
 */
public class TaskCenterListAdapter implements ExpandableListAdapter
{

	public static List<RegistData> list1 = null;
	public static List<RegistData> list2 = null;
	public static List<RegistData> list3 = null;
	public Context context;
	public OnClickListener listener;
	private String[] groupTypes = new String[]
	{ "今天", "昨天", "更早" };
	private RegistData bean;

	/**
	 * @param list
	 * @param context
	 * @throws ParseException
	 */
	public TaskCenterListAdapter(List<RegistData> list, Context context, OnClickListener listener)
	{
		this.context = context;
		this.listener = listener;
		initLists(list);
	}

	public void initLists(List<RegistData> list)
	{
		list1 = new ArrayList<RegistData>();
		list2 = new ArrayList<RegistData>();
		list3 = new ArrayList<RegistData>();

		RegistData tempBean = null;
		Calendar curDate = Calendar.getInstance();
		curDate.setTime(DateTimeFactory.getInstance().getDat());
		Calendar todayZeroDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DATE), 0, 0, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date reportDate = null;
		Calendar reportCalendar = null;
		for (int i = 0; i < list.size(); i++)
		{
			tempBean = list.get(i);
			try
			{
				if (null != tempBean.getCreateDate() && !tempBean.getCreateDate().equals(""))
				{
					reportDate = sdf.parse(tempBean.getCreateDate());
				}
				else
				{
					continue;
				}
			}
			catch (ParseException e)
			{
				e.printStackTrace();
				continue;
			}
			reportCalendar = new GregorianCalendar(reportDate.getYear() + 1900, reportDate.getMonth(), reportDate.getDate(), reportDate.getHours(), reportDate.getMinutes(), reportDate.getSeconds());
			if (reportCalendar.getTimeInMillis() > todayZeroDate.getTimeInMillis())
			{
				list1.add(tempBean);
			}
			else if (todayZeroDate.getTimeInMillis() - reportCalendar.getTimeInMillis() <= 24 * 60 * 60 * 1000)
			{
				list2.add(tempBean);
			}
			else
			{
				list3.add(tempBean);
			}
		}
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		if (groupPosition == 0)
		{
			return list1.get(childPosition);
		}
		else if (groupPosition == 1)
		{
			return list2.get(childPosition);
		}
		else
		{
			return list3.get(childPosition);
		}
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		View v;
		if (convertView == null)
		{
			v = View.inflate(context, R.layout.item_task_center, null);
		}
		else
		{
			v = convertView;
		}
		final LinearLayout topLayout = (LinearLayout) v.findViewById(R.id.item_topview);
		final LinearLayout detailLayout = (LinearLayout) v.findViewById(R.id.detail_item_view);
		topLayout.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (detailLayout.isShown())
				{
					detailLayout.setVisibility(View.GONE);
					topLayout.setBackgroundResource(R.drawable.new_item_bg_nomal);
				}
				else
				{
					detailLayout.setVisibility(View.VISIBLE);
					topLayout.setBackgroundResource(R.drawable.new_item_bg_select);
				}
			}
		});
		if (groupPosition == 0)
		{
			bean = list1.get(childPosition);
		}
		else if (groupPosition == 1)
		{
			bean = list2.get(childPosition);
		}
		else
		{
			bean = list3.get(childPosition);
		}

		// 列表项页面
		TextView reportNo = (TextView) v.findViewById(R.id.task_report_no);
		TextView taskType = (TextView) v.findViewById(R.id.task_taskType);

		TextView status = (TextView) v.findViewById(R.id.task_status);
		reportNo.setText(bean.getRegistNo());
		// 如果是查勘案件

		// 如果是核损退回或者私内查勘
		if (bean.getTaskType() != 0 && bean.getTaskType() != 1 && bean.getTaskType() != 2)
		{
			taskType.setText(Html.fromHtml("<font color = 'red'>" + MyUtils.getTaskTypeString((int) bean.getTaskType()) + "<font>"));
		}
		else
		{
			taskType.setText(MyUtils.getTaskTypeString((int) bean.getTaskType()));
		}
		status.setText(MyUtils.getStatusString((int) bean.getStatus()));
		// 2013-07-24 18:03
		// //////////// 需求添加 //////////////////
		TextView licenseNoTxt = (TextView) v.findViewById(R.id.licenseNoTxt);
		licenseNoTxt.setText(bean.getReportorName() + "/" + bean.getLicenseNo());
		// //////////// 需求添加 //////////////////

		Button viewBtn = (Button) v.findViewById(R.id.viewBtn);
		Button refuseBtn = (Button) v.findViewById(R.id.refuseBtn);
		Button accpetBtn = (Button) v.findViewById(R.id.accpetBtn);
		Button taskProgressBtn = (Button) v.findViewById(R.id.taskProgressBtn);

		viewBtn.setVisibility(View.VISIBLE);
		refuseBtn.setVisibility(View.VISIBLE);
		accpetBtn.setVisibility(View.VISIBLE);

		switch ((int) bean.getStatus())
		{
		case 0:// 未接收
			viewBtn.setVisibility(View.GONE);
			refuseBtn.setText("接收");
			accpetBtn.setVisibility(View.GONE);
			// accpetBtn.setText("拒绝");
			break;
		case 1:// 接收
			viewBtn.setVisibility(View.GONE);
			// 理算退回，核损退回无改派
			if (bean.getTaskType() == AppConstants.TYPE_COUNT_RETREAT_SETLOSS || bean.getTaskType() == AppConstants.TYPE_RETREAT_SETLOSS)
			{
				// refuseBtn.setText("改派");
				refuseBtn.setVisibility(View.GONE);
			}
			else
			{
				refuseBtn.setText("改派");
			}
			accpetBtn.setText("到达");
			break;
		case 2:// 改派
			viewBtn.setVisibility(View.GONE);
			refuseBtn.setVisibility(View.GONE);
			accpetBtn.setVisibility(View.GONE);
			break;
		case 3:// 拒绝
			viewBtn.setText("归档");
			refuseBtn.setVisibility(View.GONE);
			accpetBtn.setVisibility(View.GONE);
			break;
		case 4:// 到达
			viewBtn.setText("处理");
			// 理算退回，核损退回无改派
			/*
			 * 去掉改派功能 if(bean.getTaskType() ==
			 * AppConstants.TYPE_COUNT_RETREAT_SETLOSS || bean.getTaskType() ==
			 * AppConstants.TYPE_RETREAT_SETLOSS){ // refuseBtn.setText("改派");
			 * refuseBtn.setVisibility(View.GONE); }else{
			 * refuseBtn.setText("改派"); }
			 */
			refuseBtn.setVisibility(View.GONE);// 去掉改派功能。
			accpetBtn.setVisibility(View.GONE);
			break;
		case 5:// 完成
			viewBtn.setText("查看");
			refuseBtn.setText("归档");
			accpetBtn.setVisibility(View.GONE);
			break;
		case 6:// 归档
			viewBtn.setText("查看");
			refuseBtn.setVisibility(View.GONE);
			accpetBtn.setVisibility(View.GONE);
			break;
		default:
			viewBtn.setVisibility(View.GONE);
			refuseBtn.setVisibility(View.GONE);
			accpetBtn.setVisibility(View.GONE);
			break;
		}

		viewBtn.setTag(bean.getTaskId() + ";" + MyUtils.getTaskTypeString((int) bean.getTaskType()) + ";" + bean.getLicenseNo());
		refuseBtn.setTag(bean.getTaskId() + ";" + MyUtils.getTaskTypeString((int) bean.getTaskType()) + ";" + bean.getLicenseNo());
		accpetBtn.setTag(bean.getTaskId() + ";" + MyUtils.getTaskTypeString((int) bean.getTaskType()) + ";" + bean.getLicenseNo());
		taskProgressBtn.setTag(bean.getTaskId() + ";" + MyUtils.getTaskTypeString((int) bean.getTaskType()) + ";" + bean.getLicenseNo());

		viewBtn.setOnClickListener(listener);
		refuseBtn.setOnClickListener(listener);
		accpetBtn.setOnClickListener(listener);
		taskProgressBtn.setOnClickListener(listener);

		// 详情页面
		Button changePC = (Button) v.findViewById(R.id.convertPcBtn); // 详情页面转Pc处理按钮
		TextView reportNo1 = (TextView) v.findViewById(R.id.task_detail_report_no);
		TextView taskType1 = (TextView) v.findViewById(R.id.task_detail_task_type);
		TextView reporter1 = (TextView) v.findViewById(R.id.task_detail_reporter);
		TextView accidentAddr = (TextView) v.findViewById(R.id.task_detail_accident_addr);
		TextView status1 = (TextView) v.findViewById(R.id.task_detail_status);
		TextView accidentReason = (TextView) v.findViewById(R.id.task_detail_accident_reason);
		TextView accidentTime = (TextView) v.findViewById(R.id.task_detail_accident_time);
		TextView phone = (TextView) v.findViewById(R.id.task_detail_phone);
		TextView reportTime = (TextView) v.findViewById(R.id.task_detail_report_time);

		TextView policy_reporter = (TextView) v.findViewById(R.id.task_policy_reporter); // 被保险人：
		TextView task_Money = (TextView) v.findViewById(R.id.task_Money); // 保险金额：
		TextView task_reBack = (TextView) v.findViewById(R.id.task_reBack); // 退
																			// 回：
		TextView task_reBackReason = (TextView) v.findViewById(R.id.task_reBackReason); // 退
																						// 回说明

		TableRow mTableRow = (TableRow) v.findViewById(R.id.table_policy_reporter);
		TableRow mTableRowMoney = (TableRow) v.findViewById(R.id.table_policy_money);
		TableRow mTableRowBack = (TableRow) v.findViewById(R.id.table_policy_back);
		TableRow mTableRowBackReason = (TableRow) v.findViewById(R.id.table_policy_backReason);

		String name = bean.getInsuredName();
		if (name == "" || name == null)
			mTableRow.setVisibility(View.GONE);
		else
		{
			mTableRow.setVisibility(View.VISIBLE);
			policy_reporter.setText(name);
		}

		String money = bean.getMoney();
		if (money == "" || money == null)
			mTableRowMoney.setVisibility(View.GONE);
		else
		{
			mTableRowMoney.setVisibility(View.VISIBLE);
			task_Money.setText(money);
		}

		int status2 = (int) bean.getStatus1();
		String dispStatus;
		if (status2 == 0)
			mTableRowBack.setVisibility(View.GONE);
		else
		{
			mTableRowBack.setVisibility(View.VISIBLE);
			if (status2 == 1)
				dispStatus = "核价退回";
			else if (status2 == 2)
				dispStatus = "核损退回";
			else
				dispStatus = "单证退回";
			task_reBack.setText(dispStatus);
		}

		String reBackReason = bean.getBackOpinion();
		if (reBackReason == "" || reBackReason == null)
			mTableRowBackReason.setVisibility(View.GONE);
		else
		{
			mTableRowBackReason.setVisibility(View.VISIBLE);
			task_reBackReason.setText(reBackReason);
		}
		if (bean.getTaskType() == 0)
		{
			changePC.setVisibility(View.GONE);
		}

		reportNo1.setText(bean.getRegistNo());
		taskType1.setText(MyUtils.getTaskTypeString((int) bean.getTaskType()));
		reporter1.setText(bean.getReportorName());
		accidentAddr.setText(bean.getDamageAddress());
		status1.setText(MyUtils.getStatusString((int) bean.getStatus()));
		accidentReason.setText(bean.getDamageName());
		accidentTime.setText(bean.getDamageDate());
		phone.setText(bean.getReportorNumber());
		reportTime.setText(bean.getReportDate());

		changePC.setTag(bean.getTaskId() + ";" + MyUtils.getTaskTypeString((int) bean.getTaskType()) + ";" + bean.getLicenseNo());
		changePC.setOnClickListener(listener);

		// }

		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		// TODO Auto-generated method stub
		if (groupPosition == 0)
		{
			return list1.size();
		}
		else if (groupPosition == 1)
		{
			return list2.size();
		}
		else
		{
			return list3.size();
		}
	}

	@Override
	public long getCombinedChildId(long groupId, long childId)
	{
		// TODO Auto-generated method stub
		return childId;
	}

	@Override
	public long getCombinedGroupId(long groupId)
	{
		// TODO Auto-generated method stub
		return groupId;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		// TODO Auto-generated method stub
		// if (groupPosition == 0) {
		// return list1.get(groupPosition);
		// }else if(groupPosition == 1){
		// return list2.get(groupPosition);
		// }else{
		// return list3.get(groupPosition);
		// }
		return null;
	}

	@Override
	public int getGroupCount()
	{
		// TODO Auto-generated method stub
		return groupTypes.length;
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		View v;
		if (convertView == null)
		{
			v = View.inflate(context, R.layout.item_task_center_group, null);
		}
		else
		{
			v = convertView;
		}
		TextView textView = (TextView) v.findViewById(R.id.title);
		textView.setText(groupTypes[groupPosition]);
		return v;
	}

	@Override
	public boolean hasStableIds()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onGroupCollapsed(int groupPosition)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onGroupExpanded(int groupPosition)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub

	}

}
