package com.sinosoft.ms.utils.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sinosoft.ms.R;
import com.sinosoft.ms.model.LocalImgCache;

public class GridImageAdapter extends BaseAdapter {
	private ArrayList<Map<String, Object>> list;
	private Context context;
	protected ImageLoader imageLoader;//
	private DisplayImageOptions options;
	private OnClickListener downLoadListerner;
    private boolean displayDownLoad=true;
    
    public GridImageAdapter(Context context,
			ArrayList<Map<String, Object>> list) {
		this.list = list;
		this.context = context;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.bad_img)
				.showImageForEmptyUri(R.drawable.bad_img)
				.showImageOnFail(R.drawable.bad_img).cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
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
		MyView tag = null;
		if (convertView == null) {
			View v = LayoutInflater.from(context).inflate(
					R.layout.item_gridimage, null);
			tag = new MyView();
			tag.imageView = (ImageView) v.findViewById(R.id.itemImage);
			tag.checkBox1 = (CheckBox)v.findViewById(R.id.checkBox1);
			v.setTag(tag);
			convertView = v;
		} else {
			tag = (MyView) convertView.getTag();
		}
		String image_path = list.get(position).get("itemImage").toString();
		imageLoader.displayImage("file:///"+image_path, tag.imageView, options);//显示图像
		List<String> strList = LocalImgCache.getInstance().getImgList();
		if(strList.contains(image_path)){
			tag.checkBox1.setChecked(true);
		}else{
			tag.checkBox1.setChecked(false);
		}
	    tag.checkBox1.setTag(image_path);
	 	tag.checkBox1.setOnClickListener(new CheckBoxClick());
        	 
		return convertView;
	}

	class MyView {
		ImageView imageView;
		CheckBox checkBox1;
	}
	
	/**
	 * 
	 * @author linianchun
	 *
	 */
	private class CheckBoxClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			if(v instanceof CheckBox){
				CheckBox cb = (CheckBox)v;
//				Toast.makeText(context, ""+cb.isChecked()+" = "+cb.getTag().toString(), Toast.LENGTH_LONG).show();
				List<String> strList = LocalImgCache.getInstance().getImgList();
				if(cb.isChecked()){//选中
					if(!strList.contains(cb.getTag().toString())){
						strList.add(cb.getTag().toString());
					}
				}else{//未选中
					if(strList.contains(cb.getTag().toString())){
						strList.remove(cb.getTag().toString());
					}
				}
				LocalImgCache.getInstance().setImgList(strList);
			}
		}
	}
}
