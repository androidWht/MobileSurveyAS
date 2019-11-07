package com.sinosoft.ms.utils.download;

import java.io.File;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.utils.component.CustomDialog;

/**
 * 系统名：移动查勘定损 子系统名：版本更新 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author yanzhipei
 * @createTime 上午11:23:28
 */
@SuppressWarnings("all")
public class DownloadManager {

	private Context mContext;

	private String version;

	// 提示语
	private String updateMsg = "发现新版本,建议立即更新使用.";

	// 返回的安装包url
	private String apkUrl = null;;

	private Dialog noticeDialog;

	private Dialog downloadDialog;

	// 下载线程
	private Thread downloadThread;
	/* 下载包安装路径 */
	private static final String savePath = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/mobileSurvey/download/";

	private static final String saveFileName = savePath
			+ "MobileSureyRelease.apk";

	/* 进度条与通知ui刷新的handler和msg常量 */
	private ProgressBar progressBar;

	private static final int DOWN_UPDATE = 1;

	private static final int DOWN_OVER = 2;

	private int progress;

	private Thread downLoadThread;

	private boolean interceptFlag = false;

	private String force = null;

	private DownLoadCallback downLoadCallback;

	/*
	 * private Handler mHandler = new Handler() { public void
	 * handleMessage(Message msg) { switch (msg.what) { case DOWN_UPDATE:
	 * mProgress.setProgress(progress); break; case DOWN_OVER: SharedPreferences
	 * sharedPerences = mContext .getSharedPreferences("base_info",
	 * mContext.MODE_WORLD_READABLE); SharedPreferences.Editor edit =
	 * sharedPerences.edit();
	 * 
	 * edit.putString("sysVersion", version); // 保存版本信息 User
	 * user=UserCashe.getInstant().getUser(); edit.putString(user.getName(),
	 * "");
	 * 
	 * edit.clear(); edit.commit() ; installApk(); // 安装APK break; default:
	 * break; } }; };
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressBar.setProgress(msg.getData().getInt("size"));
				float num = (float) progressBar.getProgress()
						/ (float) progressBar.getMax();
				int result = (int) (num * 100);
				// resultView.setText(result+ "%");

				// 显示下载成功信息
				if (progressBar.getProgress() == progressBar.getMax()) {
					Toast.makeText(mContext, "文件下载成功", 1).show();
					SharedPreferences sharedPerences = mContext
							.getSharedPreferences("base_info",
									mContext.MODE_WORLD_READABLE);
					SharedPreferences.Editor edit = sharedPerences.edit();

					edit.clear();
					edit.commit();
					installApk(); // 安装APK
				}
				break;
			case -1:
				// 显示下载错误信息
				Toast.makeText(mContext, "文件下载失败", 1).show();
				break;
			}
		}
	};

	public DownloadManager(Context context, String apkUrl, String version,
			String updateMsg) {
		this.mContext = context;
		this.apkUrl = apkUrl; // 设置访问URL
		this.version = version; // 版本号
		this.updateMsg = updateMsg;
	}

	// 外部接口让主Activity调用
	public void checkUpdateInfo(String force, DownLoadCallback callback) {
		this.downLoadCallback = callback;
		this.force = force;
		showNoticeDialog(downLoadCallback);
	}

	private void showNoticeDialog(final DownLoadCallback callback) {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("新版本：" + version);
		builder.setMessage(updateMsg);
		builder.setCancelable(false);
		builder.setNeutralButton("浏览器下载", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();
			
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(apkUrl));
				mContext.startActivity(intent);
			}

		});

		// 强制/非强制
		if ("1".equals(this.force)) {
			builder.setNegativeButton("退出", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					System.exit(0);
				}
			});
		} else {
			builder.setNegativeButton("以后再说", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (null != callback)
						callback.uploadLater();
				}
			});
		}
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	public interface DownLoadCallback {
		public void uploadLater();
	}

	/*
	 * private void download(final String path, final File savedir) { new
	 * Thread(new Runnable() {
	 * 
	 * @Override public void run() { //开启3个线程进行下载 FileDownloader loader = new
	 * FileDownloader(mContext, path, savedir, 3);
	 * progressBar.setMax(loader.getFileSize());//设置进度条的最大刻度为文件的长度
	 * 
	 * try { loader.download(new DownloadProgressListener() {
	 * 
	 * @Override public void onDownloadSize(int size) {//实时获知文件已经下载的数据长度 Message
	 * msg = new Message(); msg.what = 1; msg.getData().putInt("size", size);
	 * handler.sendMessage(msg);//发送消息 } }); } catch (Exception e) {
	 * handler.obtainMessage(-1).sendToTarget(); } } }).start(); }
	 */

	/**
	 * 安装apk
	 * 
	 * @param url
	 */
	private void installApk() {
		String filename = savePath
				+ apkUrl.substring(apkUrl.lastIndexOf('/') + 1);
		File apkfile = new File(filename);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);

	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

}
