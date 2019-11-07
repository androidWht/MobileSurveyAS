package com.sinosoft.ms.utils.component.picgallery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
/**
 * 
 * 系统名 		查勘相机系统 
 * 类名			ImageBrowserAdapter.java
 * 类作用		全屏左右滑动图片预览适配器
 * @author wuhaijie
 * @createTime 2014-3-14 下午3:36:36
 */
public class PictureGalleryAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<ScaleImageView> imageViews = new ArrayList<ScaleImageView>();

	private ImageCacheManager imageCache;

	private List<String> mList;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bitmap bitmap = (Bitmap) msg.obj;
			Bundle bundle = msg.getData();
			String url = bundle.getString("url");
			for (int i = 0; i < imageViews.size(); i++) {
				if (imageViews.get(i).getTag().equals(url)) {
					imageViews.get(i).setImageBitmap(bitmap);
				}
			}
		}
	};

	public void setData(List<String> data) {
		this.mList = data;
		notifyDataSetChanged();
	}

	public PictureGalleryAdapter(Context context) {
		this.context = context;
		imageCache = ImageCacheManager.getInstance(context);
	}

	@Override
	public int getCount() {
		return mList != null ? mList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ScaleImageView view = new ScaleImageView(context);
		view.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		String item = mList.get(position);
		if (mList != null) {
			Bitmap bmp;
			try {
				bmp = imageCache.get(item);
				view.setTag(item);
				if (bmp != null) {
					view.setImageBitmap(bmp);
				} 
				if (!this.imageViews.contains(view)) {
					imageViews.add(view);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return view;
	}


}
