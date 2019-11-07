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
import java.util.Map;

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
import com.sinosoft.ms.model.PersonDetailData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.component.SpinnerUtils;

/**
 * @author sinosoft
 * 
 */
public class RiskSituationListAdapter implements ExpandableListAdapter
{


	public static List<PersonDetailData> personDetailDataList;
	private Map<String, Map<String, String>> dicConfigMap;
	public Context context;
	public OnClickListener listener;


	/**
	 * @param list
	 * @param context
	 * @throws ParseException
	 */
	public RiskSituationListAdapter(List<PersonDetailData> list, Context context, OnClickListener listener)
	{
		this.context = context;
		this.listener = listener;
		this.personDetailDataList = list;
		dicConfigMap = AppConstants.getLocalItemConf();
		//initLists(list);
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
		return personDetailDataList.get(childPosition);

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
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return 1;
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
		return personDetailDataList.get(groupPosition);
	}

	@Override
	public int getGroupCount()
	{
		// TODO Auto-generated method stub
		return personDetailDataList.size();
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
/*		View v;
		if (convertView == null)
		{
			v = View.inflate(context, R.layout.item_person_risk, null);
		}
		else
		{
			v = convertView;
		}*/
		View v = View.inflate(context, R.layout.item_person_risk, null);
		
		if(personDetailDataList.size() >0)
		{
			PersonDetailData personDetailData = personDetailDataList.get(groupPosition);		
			
			String value=SpinnerUtils.getValue(personDetailData.getPersonName(), dicConfigMap.get("personDataInsType"));
			
			TextView personLostRisk = (TextView) v.findViewById(R.id.personLostRisk);
			personLostRisk.setText("险别 :"+value);
			
			value=SpinnerUtils.getValue(personDetailData.getHospitalName(), dicConfigMap.get("personDateMoenyType"));
	        TextView personLostName = (TextView) v.findViewById(R.id.personLostName);
	        personLostName.setText("费用名称 :"+value);
	        
	        TextView personLostMoeny = (TextView) v.findViewById(R.id.personLostMoeny);
	        personLostMoeny.setText("损失金额 : ￥"+personDetailData.getLossFee());
	        
	        TextView personLostDesc = (TextView) v.findViewById(R.id.personLostDesc);

	        personLostDesc.setText("描述 :"+personDetailData.getWoundDetail());
	        
            Button removeBtn = (Button) v.findViewById(R.id.removeBtn);
            removeBtn.setTag(groupPosition);
            removeBtn.setOnClickListener(listener);
		}	
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
