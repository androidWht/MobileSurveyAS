package com.sinosoft.ms.utils.component.picgallery;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sinosoft.ms.R;
import com.sinosoft.ms.utils.SystemUtil;
/**
 * 
 * 系统名 		查勘相机系统 
 * 类名			ImageViewFrament.java
 * 类作用		图片全屏左右滑动预览窗口
 * @author wuhaijie
 * @createTime 2014-3-14 下午3:23:52
 */
public class PictureGalleryFrament extends Fragment implements LoaderCallbacks<List<String>>{
	private static final String TAG = "ImageViewFrament";
	private PicGallery gallery;		//画廊控件
	
	private int winSize[];
	
	private PictureGalleryAdapter mAdapter;
	
	private String path;
	
	private List<String> list;
	private int position;
	
	public PictureGalleryAdapter getAdapter() {
		return mAdapter;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		winSize = SystemUtil.getWinSize(getActivity());
		Bundle bundle = getArguments();
		path = bundle.getString("path");
		list = bundle.getStringArrayList("list");
		position = bundle.getInt("position");
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_picgallery, null);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//获取图片对象
		
		setGallery(view);
	}
	
	/**
	 * 初始化控件
	 * @param view
	 */
	private void setGallery(View view){
		gallery = (PicGallery) view.findViewById(R.id.picGallery);
		gallery.setScreenData(winSize[0], winSize[1]);
		gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
		gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
		gallery.setDetector(new GestureDetector(getActivity(),
				new MySimpleGesture()));
		mAdapter = new PictureGalleryAdapter(getActivity());
		gallery.setAdapter(mAdapter);
		
		if(null != path){
			getLoaderManager().initLoader(0, null, this);
		}else if(null != list){
			mAdapter.setData(list);
			gallery.setSelection(position);
		}
		
	}

	private class MySimpleGesture extends SimpleOnGestureListener {
		// 按两下的第二下Touch down时触发
		public boolean onDoubleTap(MotionEvent e) {
			//缩放图片
			View view = gallery.getSelectedView();
			if (view instanceof ScaleImageView) {
				ScaleImageView imageView = (ScaleImageView) view;
				if (imageView.getScale() > imageView.getMiniZoom()) {
					imageView.zoomTo(imageView.getMiniZoom());
				} else {
					imageView.zoomTo(imageView.getMaxZoom());
				}

			} else {

			}
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			return true;
		}
	}

	@Override
	public Loader<List<String>> onCreateLoader(int arg0, Bundle arg1) {
		return new ImageAsyncLoader(getActivity(),path);
	}

	@Override
	public void onLoadFinished(Loader<List<String>> arg0, List<String> list) {
		mAdapter.setData(list);
	}

	@Override
	public void onLoaderReset(Loader<List<String>> arg0) {
		mAdapter.setData(null);
	}

}
