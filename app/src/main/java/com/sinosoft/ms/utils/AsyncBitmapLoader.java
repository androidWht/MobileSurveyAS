package com.sinosoft.ms.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.http.AndroidHttpClient;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.sinosoft.ms.utils.net.HttpClientUtil;

public class AsyncBitmapLoader {
	private static final String TAG = "AsyncBitmapLoader";
	/**
	 * 内存图片软引用缓冲
	 */
	private static HashMap<String, Bitmap> imageCache = null;
	private static HashMap<String, Boolean> imageLoading = null;
	private File cacheDir = null;
	private static AsyncBitmapLoader mAsyncBitmapLoader = null;
	private static final String userAgent = "SAITION_USER_AGENT";
	private String path;
	private String url;

	public AsyncBitmapLoader() {

	}

	public synchronized static AsyncBitmapLoader getInstance() {
		if (null == mAsyncBitmapLoader) {
			mAsyncBitmapLoader = new AsyncBitmapLoader();
		}
		return mAsyncBitmapLoader;
	}
	
	public void init(String url,String path){
		this.url = url;
		this.path = path;
		imageCache = new HashMap<String, Bitmap>();
		imageLoading = new HashMap<String,Boolean>();
		final String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(path);
			// 检查/mnt/sdcard/saition文件夹是否存在，不存在则新建文件夹
			if (!cacheDir.exists() || !cacheDir.isDirectory()) {
				cacheDir.mkdirs();
			}
		}
	}
	
	public synchronized Bitmap loadBitmap(final ImageView imageView, final String imageURL,
			final ImageCallBack imageCallBack) {
		if (TextUtils.isEmpty(imageURL)) {
			if (null != imageView && null != imageCallBack) {
				imageCallBack.imageLoad(imageView, null);
			}
			return null;
		}
		// 在内存缓存中，则返回Bitmap对象
		if (imageCache.containsKey(imageURL)) {
			Bitmap bitmap = imageCache.get(imageURL);
			//Bitmap bitmap = reference.get();
			if (bitmap != null && ! bitmap.isRecycled()) {
				if (null != imageView && null != imageCallBack) {
					imageCallBack.imageLoad(imageView, bitmap);
				}
				return bitmap;
			}else {
				imageCache.remove(imageURL);
			}
		}
		// 加上一个对本地缓存的查找
		if (null != cacheDir) { // 本地缓存存在
			String bitmapName = imageURL
					.substring(imageURL.lastIndexOf("=") + 1);
			File[] cacheFiles = cacheDir.listFiles();
			if (null != cacheFiles) {
				int i = 0;
				for (; i < cacheFiles.length; i++) {
					if (bitmapName.equals(cacheFiles[i].getName())) {
						break;
					}
				}
				if (i < cacheFiles.length) {
					try {
						Bitmap bitmap = BitmapFactory.decodeFile(cacheDir.getPath()
								+ "/" + bitmapName);
						imageCache.put(imageURL, bitmap);
						if (bitmap != null) {
							if (null != imageView && null != imageCallBack) {
								imageCallBack.imageLoad(imageView, bitmap);
							}
							return bitmap;
						}
						
					} catch (OutOfMemoryError e) {
						System.gc();
						e.printStackTrace();
						return null;
					}
				}
			}
		}


		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (null != imageView && null != imageCallBack) {
					imageCallBack.imageLoad(imageView, (Bitmap) msg.obj);
				}
			}
		};

		// 如果不在内存缓存中，也不在本地（被jvm回收掉），则开启线程下载图片
		//如果不在下载imageURL的照片，则下载
			new Thread() {
				@Override
				public void run() {
					downloadImage(imageURL,handler);
				}
	
			}.start();
		return null;
	}
	
	private void downloadImage(String imageURL,Handler handler){
		if(null == imageLoading.get(imageURL) || !imageLoading.get(imageURL)){
			Bitmap bitmap = null;
			// 发送GET请求，并将响应内容转换成字符串
			InputStream bitmapIs = null;
			try {
//				System.out.println("download:"+imageURL);
				imageLoading.put(imageURL, true);
				bitmapIs =   HttpClientUtil.postImage(url, new StringBuffer(imageURL),"UTF-8");
				BitmapFactory.Options newOpts = new BitmapFactory.Options();
				newOpts.inSampleSize = 5;
				bitmap = BitmapFactory.decodeStream(bitmapIs,null, newOpts);
				imageCache.put(imageURL, bitmap);
				Message msg = handler.obtainMessage(0, bitmap);
				handler.sendMessage(msg);
			} catch (ClientProtocolException e1) {
				System.gc();
				Log.d(TAG, "ClientProtocolException");
				e1.printStackTrace();
				return;
			}catch (OutOfMemoryError e) {
				System.gc();
				e.printStackTrace();
			} catch (Exception e1) {
				System.gc();
				e1.printStackTrace();
				Log.d(TAG, "IOException");
				return;
			} finally {
				imageLoading.remove(imageURL);
				try {
					if (null != bitmapIs) {
						bitmapIs.close();
						bitmapIs = null;
					}
				} catch (IOException e) {
					Log.d(TAG, "IOException");
				}
			}
			// 把图片写入文件做缓存
			writeToFile(imageURL, bitmap);
		}
	}
	
	private void writeToFile(final String imageURL, Bitmap bitmap) {
		if (null == cacheDir || null == bitmap) {
			return;
		}

		File bitmapFile = new File(cacheDir.getPath() + "/"
				+ imageURL.substring(imageURL.lastIndexOf("=") + 1));
		if (!bitmapFile.exists()) {
			try {
				bitmapFile.createNewFile();
			} catch (IOException e) {
				Log.d(TAG, "writeToFile failed!");
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(bitmapFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			Log.d(TAG, "ClientProtocolException");
			return;
		} catch (IOException e) {
			Log.d(TAG, "ClientProtocolException");
			return;
		} finally {
			try {
				if (null != fos) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				Log.d(TAG, "IOException");
			}
		}
	}
	
	public void recyleBitmap(String imageURL){
		
		if (imageCache.containsKey(imageURL)) {
			Bitmap bitmap = imageCache.get(imageURL);
			//Bitmap bitmap = reference.get();
			imageCache.remove(imageURL);
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
		}
	}
	
	/**
	 * 回调接口
	 */
	public interface ImageCallBack {
		public void imageLoad(ImageView imageView, Bitmap bitmap);
	}
	
}