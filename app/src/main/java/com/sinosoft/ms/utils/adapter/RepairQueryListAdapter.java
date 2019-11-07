package com.sinosoft.ms.utils.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.LossRepairInfoItem;
/**
 * 换件查询列表适配器
 * 
 * @author Administrator
 *
 */

public class RepairQueryListAdapter extends BaseAdapter {
	public List<Boolean> mChecked;
	private List<LossRepairInfoItem> list;
	private HashMap<Integer, View> map = new HashMap<Integer, View>();
	private Context context;

	public RepairQueryListAdapter(List<LossRepairInfoItem> repairProjects,
			Context context){
		list = repairProjects;
		this.context = context;
		mChecked = new ArrayList<Boolean>();
		for (int i = 0; i < list.size(); i++) {
			mChecked.add(false);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		repairHolder holder = null;
		if (map.get(position) == null) {
			Log.e("MainActivity", "position1 = " + position);

			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mInflater.inflate(R.layout.item_repair_query, null);
			holder = new repairHolder();
			holder.selected = (CheckBox) view.findViewById(R.id.list_select);
			holder.partNameTxt = (TextView) view.findViewById(R.id.repairNameTxt);
			holder.factoryPartsNumberTxt = (TextView) view.findViewById(R.id.repairNumberTxt);
			holder.factoryPartsNameTxt = (TextView) view.findViewById(R.id.professionTypeTxt);
			holder.partsGroupingTxt = (TextView) view.findViewById(R.id.professionNameTxt);
			holder.systemPriceTxt = (TextView) view.findViewById(R.id.professionCodeTxt);
			final int p = position;
			map.put(position, view);
			holder.selected.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					mChecked.set(p, cb.isChecked());
				}
			});
			view.setTag(holder);
		} else {
			Log.e("MainActivity", "position2 = " + position);
			view = map.get(position);
			holder = (repairHolder) view.getTag();
		}
		
		LossRepairInfoItem  lossRepairInfo  = list.get(position);
		holder.selected.setChecked(mChecked.get(position));
		holder.partNameTxt.setText(lossRepairInfo.getRepairItemName());
		holder.factoryPartsNumberTxt.setText(lossRepairInfo.getRepairItemCode());
		holder.factoryPartsNameTxt.setText(lossRepairInfo.getRepairType());
		holder.partsGroupingTxt.setText(lossRepairInfo.getRepairName());
		holder.systemPriceTxt.setText(lossRepairInfo.getRepairCode());
		return view;
	}

	 class repairHolder {
		CheckBox selected;
		TextView partNameTxt;// 零件名称
		TextView factoryPartsNumberTxt;// 原厂零件编号
		TextView factoryPartsNameTxt;// 原厂零件名称
		TextView partsGroupingTxt;// 零件分组名称2
		TextView systemPriceTxt;// 系统价
	}
}
