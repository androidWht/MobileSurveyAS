package com.sinosoft.ms.activity.task.image.camera;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.image.PictureGalleryActivity;
import com.sinosoft.ms.activity.task.image.camera.PhotographyUtil.PhotographyCallback;
import com.sinosoft.ms.activity.task.image.camera.Preview.AutoFocusCallback;
import com.sinosoft.ms.activity.task.image.camera.Preview.FLASH_MODE;
import com.sinosoft.ms.activity.task.image.camera.Preview.FlashModeCallback;
import com.sinosoft.ms.activity.task.image.camera.Preview.PreviewPictureCallback;
import com.sinosoft.ms.utils.BitmapUtil;
import com.sinosoft.ms.utils.CameraOrientationListener;
import com.sinosoft.ms.utils.LocationService;
import com.sinosoft.ms.utils.SystemUtil;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;

/**
 * 
 * 系统名 查勘相机系统 类名 CameraActivity.java 类作用 拍照界面
 * 
 * @author wuhaijie
 * @createTime 2014-1-24 下午2:56:25
 */
public class CameraActivity extends Activity implements OnClickListener
{
	private static final String TAG = "CameraActivity";
	/**
	 * 图片缩略图大小
	 */
	public static final int IMAGE_THUMBNAIL_WIDTH = 96;
	public static final int IMAGE_THUMBNAIL_HEIGHT = 128;

	private Button takephotoBtn; // 拍照按钮
	private Button resolutionBtn; // 分辨率按钮
	private ImageView flashImage; // 闪光灯
	private ImageView previewImage; // 预览
	private RelativeLayout mPreviewLayout;
	private Preview mPreview = null; // 拍照实时预览界面
	private SeekBar seekBar; // 缩放进度条
	private ProgressDialogUtil mProgressDialog; // 进度对话框

	private OrientationEventListener mOrientationListener; // 手机方向监听
	private int orientation; // 方向

	private PhotographyUtil operation;
	private List<String> imageList; // 图片列表

	private String imageType = null;
	private String reportNo = null;
	private String taskId = null;
	private String registId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		setView();
		setData();
		
		mPreview.onCameraActivity(handler);
		
	}

	
	
	
	protected void setView()
	{
		takephotoBtn = (Button) findViewById(R.id.takephotoBtn);
		resolutionBtn = (Button) findViewById(R.id.resolutionBtn);
		flashImage = (ImageView) findViewById(R.id.flashImage);
		mPreviewLayout = (RelativeLayout) findViewById(R.id.previewLayout);
		previewImage = (ImageView) findViewById(R.id.previewImage);
		mPreview = (Preview) findViewById(R.id.preview);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setOnSeekBarChangeListener(new SeekBarChangeListener());
		previewImage.setOnClickListener(this);
		takephotoBtn.setOnClickListener(this);
		resolutionBtn.setOnClickListener(this);
		flashImage.setOnClickListener(this);

		// 设置对焦监听
		mPreviewLayout.setOnTouchListener(new AutoFocusListener());
		mOrientationListener = new CameraOrientationListener(this, SensorManager.SENSOR_DELAY_NORMAL)
		{

			@Override
			public void onDirectionChanged(int direction)
			{
				orientation = direction;
			}
		};
		

	}

	protected void setData()
	{
		mProgressDialog = new ProgressDialogUtil(this);
		operation = PhotographyUtil.getInstance();

		Intent intent = getIntent();
		reportNo = intent.getStringExtra("reportNo");
		taskId = intent.getStringExtra("taskId");
		registId = intent.getStringExtra("registId");
		imageType = intent.getStringExtra("imageType");

		// 设置闪光灯回调
		mPreview.setFlashModeCallback(new MyFlashModeCallback());

		imageList = new ArrayList<String>();

	}

	/**
	 * 自动对焦监听
	 */
	class AutoFocusListener implements OnTouchListener
	{

		@Override
		public boolean onTouch(final View view, final MotionEvent arg1)
		{
			// 添加对焦
			operation.addFocusNormal(getApplicationContext(), view, arg1, new MyPhotographyCallback(arg1));
			return false;
		}
	}

	class MyPhotographyCallback implements PhotographyCallback
	{
		MotionEvent event;

		public MyPhotographyCallback(MotionEvent event)
		{
			super();
			this.event = event;
		}

		@Override
		public void focusEnter()
		{
			// 进入对焦
			mPreview.setAutoFocus(mPreviewLayout, event, new AutoFocusCallback()
			{

				@Override
				public void autoFocus(boolean success, Camera arg1)
				{
					Log.i("cameraActivity", success + "");
					// 移除对焦图片
					operation.removeFocus(getApplicationContext(), mPreviewLayout, success);
				}
			});
		}

		@Override
		public void focusExit()
		{

		}

	}

	/**
	 * 镜头缩放监听
	 */
	class SeekBarChangeListener implements OnSeekBarChangeListener
	{

		@Override
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2)
		{
			if (mPreview.getCanAutoFocus())
			{
				mPreview.setZoom(arg1);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0)
		{

		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0)
		{

		}

	}

	@Override
	public void onClick(View arg0)
	{
		switch (arg0.getId())
		{
		case R.id.takephotoBtn:
			takePicture(orientation);
			break;
		case R.id.resolutionBtn:
			showResolution();
			break;
		case R.id.flashImage:
			setFlashMode();
			break;
		case R.id.previewImage:
			// 打开预览图片
			if (!imageList.isEmpty())
			{
				Intent intent = new Intent(getApplicationContext(), PictureGalleryActivity.class);
				PictureGalleryActivity.startForPictureGallery(getApplicationContext(), imageList, imageList.size() - 1);
			}
			break;
		}
	}

	/**
	 * 设置闪光灯模式
	 */
	public void setFlashMode()
	{
		if (null != mPreview)
		{
			FLASH_MODE mode = mPreview.getFlashMode();
			switch (mode)
			{
			case AUTO:
				// 如果当前是自动模式，则改为打开模式
				mPreview.setFlashMode(FLASH_MODE.ON);
				break;
			case OFF:
				// 如果当前是关闭模式，则改为自动模式
				mPreview.setFlashMode(FLASH_MODE.AUTO);
				break;
			case ON:
				// 如果当前是打开模式，则改为关闭模式
				mPreview.setFlashMode(FLASH_MODE.OFF);
				break;
			}
		}
	}

	class MyFlashModeCallback implements FlashModeCallback
	{

		@Override
		public void onFlashOn()
		{
			flashImage.setBackgroundResource(R.drawable.flash_on);
		}

		@Override
		public void onFlashOff()
		{
			flashImage.setBackgroundResource(R.drawable.flash_off);
		}

		@Override
		public void onFlashAuto()
		{
			flashImage.setBackgroundResource(R.drawable.flash_auto);
		}
	}

	/**
	 * 拍照
	 */
	private void takePicture(final int orientation)
	{
		Log.i(TAG, "Orientation : " + orientation);
		if (!mPreview.getIsTaking())
		{
			mProgressDialog.show("保存中...");
			mPreview.takePicture(new PreviewPictureCallback()
			{

				@Override
				public String doInBackground(byte[] bytes)
				{
					// String fileName =
					// ImageOperation.getInstance(getApplicationContext()).saveAsWatermark(bytes,orientation,mPreview.getPictureSize());
					// return fileName;
					try
					{
						// 临时图片
						String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "finger" + File.separator + "temp.jpg";
						File fileFolder = new File(Environment.getExternalStorageDirectory() + "/finger/");
						// 如果目录不存在，则创建一个名为"finger"的目录
						if (!fileFolder.exists())
						{
							fileFolder.mkdir();
						}
						// 创建临时图片
						BitmapUtil.saveImageTemp(bytes, imagePath);
						// 保存图片
						String position = "(" + LocationService.getLngS() + "," + LocationService.getLatS() + ")";
						String result = BitmapUtil.savePhoto(getApplicationContext(), imagePath, orientation, reportNo, taskId, registId, position, imageType); // 保存图片到sd卡中
						// 删除临时图片
						BitmapUtil.deleteImageTemp(imagePath);
						return result;
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					return null;

				}

				@Override
				public void postExecute(String filePath)
				{
					Message msg = new Message();
					msg.obj = filePath;
					msg.what = Preview.SUCCESS;
					handler.sendMessage(msg);
				}
			});
		}
	}

	/**
	 * 弹出选择分辨率框
	 */
	public void showResolution()
	{
		// 获取设备支持的分辨率
		final List<Size> list = mPreview.getSupportedPictureSize();
		String str[] = new String[list.size()];
		Size size = mPreview.getPictureSize();
		int selected = 0;
		// 根据当前设备选择的分辨率来设置
		for (int i = 0; i < list.size(); i++)
		{
			Size s = list.get(i);
			int ratio = SystemUtil.resolutionRatio(s.width, s.height);
			switch (ratio)
			{
			case 18:
				str[i] = "16 : 9";
				break;
			case 14:
				str[i] = "4 : 3";
				break;
			case 11:
				str[i] = "1 : 1";
				break;
			}
			if (s.width == size.width && s.height == size.height)
			{
				selected = i;
			}
		}
		// 弹出对话框
		new AlertDialog.Builder(this).setTitle("选择分辨率").setSingleChoiceItems(str, selected, new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				mPreview.setPictureSize(mPreviewLayout, list.get(arg1));
				arg0.dismiss();
			}
		}).show();
	}

	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			switch (msg.what)
			{
			case Preview.SUCCESS:
				mProgressDialog.dismiss();
				if (null != msg.obj)
				{
					refreshPreviewImage(msg.obj.toString());
				}
				break;
			case Preview.AutoFocus:
				 operation.addFocusNormal(getApplicationContext(),mPreviewLayout,new MyPhotographyCallback(null));
				 break;
			}
		}

	};

	private void refreshPreviewImage(String filePath)
	{
		Bitmap bitmap = BitmapUtil.getImageThumbnail(filePath, IMAGE_THUMBNAIL_WIDTH, IMAGE_THUMBNAIL_HEIGHT);
		if (bitmap != null)
		{
			previewImage.setImageBitmap(bitmap);
			imageList.add(filePath);
		}
		else
		{
			Drawable d = this.getResources().getDrawable(R.drawable.preview);
			previewImage.setImageBitmap(BitmapUtil.toBitmap(d));
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mPreview.startPreview();
		if (mOrientationListener != null)
		{// 先判断下防止出现空指针异常
			mOrientationListener.enable();
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		mPreview.stopPreview();
		if (mOrientationListener != null)
		{// 先判断下防止出现空指针异常
			mOrientationListener.disable();
		}
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		mPreview.exitPicture();
		takephotoBtn = null;
		flashImage = null;
		mPreviewLayout = null;
		mPreview = null; // 拍照实时预览界面
		seekBar = null;
		mProgressDialog = null;
		mOrientationListener = null;
		operation = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
		case KeyEvent.KEYCODE_CAMERA: // 按下拍照按钮
			takePicture(orientation);
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (!mPreview.isSupprotCamera())
		{
			new AlertDialog.Builder(this).setTitle("提示").setMessage("运行错误或未获取摄像权限，请设置").setPositiveButton("退出", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface arg0, int arg1)
				{
					// TODO Auto-generated method stub
					finish();
				}
			}).setCancelable(false).show();
		}
	}

}
