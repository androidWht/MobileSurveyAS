package com.sinosoft.ms.activity.task.image;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.sinosoft.ms.R;
import com.sinosoft.ms.utils.component.picgallery.PictureGalleryFrament;

/**
 * 
 * 系统名 		查勘相机系统 
 * 类名			ImageBrowserActivity.java
 * 类作用		图片预览
 * @author wuhaijie
 * @createTime 2014-1-26 下午2:41:42
 */
public class PictureGalleryActivity extends FragmentActivity implements OnClickListener{

	private PictureGalleryFrament fragment;
	
	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picgallery);
		setView();
		setData();
	}

	public void setView() {
		Intent intent = getIntent();
		bundle = intent.getExtras();
		setFragment();
	}

	protected void setData() {
		// TODO Auto-generated method stub
	}
	
	private void setFragment(){
		fragment = new PictureGalleryFrament();
		fragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().add(R.id.contentLayout, fragment).commit();
	}
	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		}
	}
	
	public static void startForPictureGallery(Context context,List<String> list,int position){
		Intent intent = new Intent(context,PictureGalleryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("list", (ArrayList<String>) list);
		bundle.putInt("position", position);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		context.startActivity(intent);
	}
	
	public static void startForPictureGallery(Context context,String path){
		Intent intent = new Intent(context,PictureGalleryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("path", path);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		context.startActivity(intent);
	}
	

}
