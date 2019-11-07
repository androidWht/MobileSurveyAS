package com.sinosoft.ms.utils.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.LossFitInfoItem;
/**
 * 换件查询列表适配器
 * 
 * @author Administrator
 *
 */
public class ChangeQueryListAdapter extends AutoCompleteArrayAdapter<LossFitInfoItem> {
	public Map<Integer,Boolean> mChecked;
//	private List<LossFitInfoItem> fitList;
//	private HashMap<Integer, View> map = new HashMap<Integer, View>();
	private Context context;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public ChangeQueryListAdapter(Context context, int textViewResourceId,
			List<LossFitInfoItem> list) {
		super(context, textViewResourceId, list);
		// TODO Auto-generated constructor stub
//		fitList = list;
		this.context = context;
		mChecked = new HashMap<Integer, Boolean>();
//		mChecked = new ArrayList<Boolean>();
		for (int i = 0; i < list.size(); i++) {
			mChecked.put(list.get(i).getId(), false);
//			mChecked.add(false);
		}
	}
	
	
	public List<Integer> getSelectedListId(){
		List<Integer> list = new ArrayList<Integer>();
		if(null != mOriginalValues){
			for(int i = 0 ; i < mOriginalValues.size() ; i ++){
				if(mChecked.get(mOriginalValues.get(i).getId())){
					list.add(i);
				}
			}
		}else{
			for(int i = 0 ; i < mObjects.size() ; i ++){
				if(mChecked.get(mObjects.get(i).getId())){
					list.add(i);
				}
			}
		}
		return list;
	}

//	public ChangeQueryListAdapter(List<LossFitInfoItem> list, Context context) {
//		fitList = list;
//		this.context = context;
//		mChecked = new ArrayList<Boolean>();
//		for (int i = 0; i < list.size(); i++) {
//			mChecked.add(false);
//		}
//	}
//
//	@Override
//	public int getCount() {
//		return fitList.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return fitList.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
//		fitList = mObjects;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if (view == null) {
			Log.e("MainActivity", "position1 = " + position);

			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mInflater.inflate(R.layout.item_change_query, null);
			holder = new ViewHolder();
			holder.selected = (CheckBox) view.findViewById(R.id.list_select);
			holder.partNameTxt = (TextView) view.findViewById(R.id.partNameTxt);
			holder.factoryPartsNumberTxt = (TextView) view
					.findViewById(R.id.factoryPartsNumberTxt);
			holder.factoryPartsNameTxt = (TextView) view
					.findViewById(R.id.factoryPartsNameTxt);
			holder.partsGroupingTxt = (TextView) view
					.findViewById(R.id.partsGroupingTxt);
			holder.systemPriceTxt = (TextView) view
					.findViewById(R.id.systemPriceTxt);
//			map.put(position, view);
			view.setTag(holder);
		} else {
			Log.e("MainActivity", "position2 = " + position);
//			view = map.get(position);
			holder = (ViewHolder) view.getTag();
		}

		LossFitInfoItem fitInfo = mObjects.get(position);
		holder.selected.setChecked(mChecked.get(fitInfo.getId()));
		
		holder.partNameTxt.setText(fitInfo.getPartStandard());
		holder.factoryPartsNumberTxt.setText(fitInfo
				.getOriginalId());
		holder.factoryPartsNameTxt.setText(fitInfo
				.getOriginalName());
		holder.partsGroupingTxt.setText(fitInfo
				.getRemark());
		holder.systemPriceTxt
				.setText(String.valueOf(fitInfo.getRefPrice1()));
		final int p = position;
		view.setOnClickListener(new MyOnClickListener(holder.selected, position));
		return view;
	}
	
	class MyOnClickListener implements OnClickListener{
		private CheckBox selected;
		private int position;

		public MyOnClickListener(CheckBox selected, int position) {
			super();
			this.selected = selected;
			this.position = position;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			if(arg0.getId()== selected.getId()){
				selected.setChecked(!selected.isChecked());
				LossFitInfoItem fitInfo = mObjects.get(position);
				mChecked.put(fitInfo.getId(), selected.isChecked());
//			}
		}
		
	}
	
	static class ViewHolder {
		CheckBox selected;
		TextView partNameTxt;// 零件名称
		TextView factoryPartsNumberTxt;// 原厂零件编号
		TextView factoryPartsNameTxt;// 原厂零件名称
		TextView partsGroupingTxt;// 零件分组名称2
		TextView systemPriceTxt;// 系统价
	}
}
