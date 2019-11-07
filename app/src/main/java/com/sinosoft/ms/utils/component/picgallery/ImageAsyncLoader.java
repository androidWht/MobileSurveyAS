package com.sinosoft.ms.utils.component.picgallery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class ImageAsyncLoader extends AsyncTaskLoader<List<String>> {
	private List<String> dataResult;
	private boolean dataIsReady;
	private static final String PICTURE = "pics";
	private String path;

	public ImageAsyncLoader(Context context,String path) {
		super(context);
		this.path = path;
		if (dataIsReady) {
			deliverResult(dataResult);
		} else {
			forceLoad();
		}
	}

	@Override
	public List<String> loadInBackground() {
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(path);
			if(file.exists() && file.isDirectory()){
				File files[] = file.listFiles();
				for(File f : files){
					if (f.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
						list.add(f.getAbsolutePath());
					}
				}
			}else{
				list.add(path);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
	}

	@Override
	protected void onStartLoading() {
		// 显示加载条
		Logger.LOG(this, "onStartLoading");
		super.onStartLoading();
	}

	@Override
	protected void onStopLoading() {
		// 隐藏加载条
		Logger.LOG(this, "onStopLoading");
		super.onStopLoading();
	}

	@Override
	public boolean takeContentChanged() {

		return super.takeContentChanged();
	}

}
