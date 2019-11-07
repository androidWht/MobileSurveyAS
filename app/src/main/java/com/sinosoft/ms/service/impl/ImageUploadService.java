package com.sinosoft.ms.service.impl;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.FileUploadHttp;
import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.LogFatory;
import com.sinosoft.ms.utils.LogRecoder;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.ImageCenterDatabase;

/**
 * 系统名：移动查勘 子系统名：后台服务，后台程序上传 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午2:18:51
 */

public class ImageUploadService extends Service {
	
	private String registNo;
	private boolean[] isSuccess;
	private List<ImageCenterBean> imageDatas;
	private NotificationManager manager;
	private FileUploadHttp upload;
	private final int UPLOAD_NUM = 3;
	private final int UPLOAD_XML_NUM = 3;
	private int currentNum = 0;
	private int currentXmlNum = 0;
	private static UploadCallback callback;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		LogFatory.d("service", "create");

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		if (intent != null) {
			registNo = intent.getStringExtra("registNo");
			if (StringUtils.isNotEmpty(registNo)) {
				try {
					uploadImage();
				} catch (Exception e) {
					ToastDialog.show(this, "图片上传错误", ToastDialog.ERROR);
					e.printStackTrace();
				}
			} else {
				ToastDialog.show(this, "报案号为空", ToastDialog.ERROR);
			}
		} else {
			ToastDialog.show(this, "图片上传错误", ToastDialog.ERROR);
		}

		LogFatory.d("service", "start");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		upload.cancel(true);
		manager.cancelAll();
		LogFatory.d("service", "onDestroy");
	}

	/**
	 * 媒体中心更新接口
	 */
	public void updateXml(final long time) {
		new Thread() {
			public void run() {
				CaseCenterService service = new CaseCenterService(null);
				Message msg = new Message();
				try {
					Thread.sleep(time);
					service.updateImage(imageDatas);
					msg.what = 1;
				} catch (Exception e) {
					msg.what = MyUtils.getErrorCode(e.getMessage());
					msg.obj = e.getMessage();
				} finally {
					handler.sendMessage(msg);
				}
			}
		}.start();

	}

	/**
	 * 执行文件上传 * @param files
	 */
	public void upload(final String[] files,final String[] imgNames,final String[] types) {
		upload = new FileUploadHttp(this,
				new FileUploadHttp.CompleteListener() {
					@Override
					public void complete(int index) {
						if (!isUpload()) {
							currentNum++;
							if (currentNum <= UPLOAD_NUM) {
								try {
									Thread.sleep(5000);
									upload(files,imgNames,types);
									//LogFatory.i("图片上传成功", "图片上传失败，重新上传");
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
							} else {
								showNotifaction(1003, "图片上传失败",
										"文件上传失败，请检查网络连接...",
										Notification.FLAG_AUTO_CANCEL);
								if(null != callback){
									callback.onFailure();
								}
								LogRecoder.writeError(registNo+"图片上传成功,文件上传失败，请检查网络连接...");
							}
						} else {
							//showNotifaction(1003, "图片上传成功", "正在更新数据中...",Notification.FLAG_ONGOING_EVENT);
							updateXml(0);
							//LogFatory.i("图片上传成功", "图片上传成功");
						}
					}

					@Override
					public void error(int index) {
						isSuccess[index] = false;
						LogFatory.i("图片上传", index + "图片上传失败");
					}

					@Override
					public void right(int index) {
						isSuccess[index] = true;
						LogFatory.i("图片上传成功", index + "图片上传成功");
					}
				}, isSuccess);
		//upload.execute(files, AppConstants.IMGURL, registNo + "");
		///upload.execute(files, AppConstants.info.getImgUrl(), registNo + "",imgNames,types);
		upload.execute(files, AppConstants.info.getDownImagUrl(), registNo + "",imgNames,types);
	}

	/**
	 * 判断是否上传成功
	 * 
	 * @return
	 */
	public boolean isUpload() {
		boolean isrun = false;
		int length = isSuccess.length;
		for (int i = 0; i < length; i++) {
			isrun = isSuccess[i];
			if (!isrun) {
				break;
			}
		}
		if (isrun) {// ok
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询改报案号的图片，上传
	 * 
	 * @throws Exception
	 */
	public void uploadImage() throws Exception {
		FileUtils utils = new FileUtils();
		ImageCenterDatabase database = new ImageCenterDatabase(this);
		try {
			imageDatas = database.select("imageCenter", "reportNo=? and isUpload=?",
					new String[] { registNo,"0" }, "createDate");
			if (imageDatas != null && !imageDatas.isEmpty()) {
				int length = imageDatas.size();

				LogFatory.e("list", imageDatas.size() + "gege");
				String filePaths[] = new String[length];
				
				String imgNames[] = new String[length];
				String types[]   = new String[length];
				

				isSuccess = new boolean[length];
				for (int i = 0; i < length; i++) {
					ImageCenterBean bean = imageDatas.get(i);
					filePaths[i] = bean.getPath();
					imgNames[i] =  bean.getImgName();
					types[i] = bean.getType();
					isSuccess[i] = false;
				}
				upload(filePaths,imgNames,types);
			} else {
				if(null != callback){
					callback.onSuccess();
				}
				ToastDialog.show(this, "当前没有图片或着没有需要上传的图片");
			}
		} catch (Exception e) {
			LogFatory.e("图片上传", "失败原因：" + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	private void showNotifaction(int notifyId, String title, String message,
			int flag) {
		// 在状态栏上显示轮询结果
		manager.cancel(notifyId);
		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		notification.tickerText = message;
		notification.when = when;
		// 设置跳转
		Intent intent = new Intent();
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		// 通知时既震动又屏幕发亮还有默认的声音 这里用的是ALL
		// n.defaults = Notification.DEFAULT_ALL;
		// 点击通知后自动消失
		notification.flags = flag;
		notification.setLatestEventInfo(this, title, message, pendingIntent);
		manager.notify(notifyId, notification);
	}
	private void cancel(int notifyId) {
		if (manager != null) {
			manager.cancel(notifyId);
		}

	}
	/**
	 * 图片上传后返回
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			cancel(1003);
			switch (msg.what) {
			case 0:
				String error=""+msg.obj;
				LogRecoder.writeError(registNo+error);
				if(!error.contains("重复")){
					currentXmlNum++;
					if(currentXmlNum<UPLOAD_XML_NUM){
						updateXml(5000);
						Log.e("upload again","重新上传数据");
					}else{
						//if(error.equals("网络连接异常!")){
							showNotifaction(1003, "影像文件上传提示", error,
									Notification.FLAG_AUTO_CANCEL);
						//}
					}
				}else{
					showNotifaction(1003, "影像文件上传提示", error,
							Notification.FLAG_AUTO_CANCEL);
				}
				if(null != callback){
					callback.onFailure();
				}
				break;
			case 1:
				showNotifaction(1003, "影像文件上传提示", "上传成功",
						Notification.FLAG_AUTO_CANCEL);
				if(null != callback){
					callback.onSuccess();
				}
				new Thread(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						ImageCenterDatabase database = new ImageCenterDatabase(ImageUploadService.this);
						database.upadateFlag(registNo);
					}
					
				}.start();
				break;
			}
		}
	};
	public static void setUploadCallback(UploadCallback callback){
		ImageUploadService.callback = callback;
	}
	public interface UploadCallback{
		public void onSuccess();
		public void onFailure();
	}
}
