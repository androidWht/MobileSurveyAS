package com.sinosoft.ms.activity.task.image.camera;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.sinosoft.ms.utils.SystemUtil;

/**
 * 
 * 系统名 查勘相机系统 类名 Preview.java 类作用 拍照控件，注明，这是一个控件，请不要往里面添加业务代码
 * 
 * @author wuhaijie
 * @createTime 2014-1-27 下午3:37:27
 */
public class Preview extends SurfaceView implements SurfaceHolder.Callback
{
	private static final String TAG = "preview";
	private static final String SP_PREVIEW_SETTING = "PreviewSetting";
	private static final String SP_FLASH_MODE = "flashMode";
	public static final int SUCCESS = 0;
	public static final int AutoFocus = 10;

	private SurfaceHolder mHolder = null;
	private Context context = null;
	private Camera mCamera = null;
	private Parameters parameters;
	// 正在拍照标识
	private boolean isTaking = false;
	// 是否有拍照权限
	private boolean isSupprotCamera = false;
	// 拍照回调函数
	private PreviewPictureCallback previewPictureCallback;
	// 拍照处理线程
	private JpegCallbackThread jpegCallbackThread;
	// 闪光灯回调函数
	private FlashModeCallback flashModeCallback;

	// 是否允许对焦
	private boolean canAutoFocus = true;
	private Size size; // 当前分辨率

	public static enum FLASH_MODE
	{
		AUTO, ON, OFF
	}

	public Preview(Context context, AttributeSet attributeSet)
	{
		super(context, attributeSet);
		this.context = context;
		mHolder = getHolder();
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mHolder.addCallback(this);
		this.canAutoFocus = false;
		this.isSupprotCamera = true;
		// this.setOnTouchListener(this);
	}

	public void surfaceCreated(SurfaceHolder holder)
	{
		try
		{
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(holder);
			mCamera.setDisplayOrientation(90);
		}
		catch (Exception e)
		{
			if (null != mCamera)
			{
				mCamera.stopPreview();
				mCamera.release();
			}
			mCamera = null;
			isSupprotCamera = false;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder)
	{
		if (null != mCamera)
		{
			mCamera.stopPreview();
			mCamera.release();
		}
		mCamera = null;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void setRightCameraOrientation(int cameraId, Camera mCamera)
	{

		CameraInfo info = new android.hardware.Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int rotation = wm.getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation)
		{
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}
		//
		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
		{
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		}
		else
		{ // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		mCamera.setDisplayOrientation(result);
	}

	protected void setDisplayOrientation(Camera camera, int angle)
	{
		Method downPolymorphic;
		try
		{
			downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]
			{ int.class });
			if (downPolymorphic != null)
				downPolymorphic.invoke(camera, new Object[]
				{ angle });
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
	{
		try
		{
		

			
			mCamera.stopPreview();
			// 设置相机参数
			parameters = mCamera.getParameters();
			if (isSupportedFlashMode())
			{
				initFlashMode();
			}
			parameters.setPictureFormat(PixelFormat.JPEG);
			parameters.setJpegQuality(85);
			parameters.setJpegThumbnailQuality(100);
			
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
			
			
			initPictureSize(w, h);
			mCamera.setParameters(parameters);
			mCamera.startPreview();
			
			
			upHandler.sendEmptyMessage(AutoFocus);			
			///=============================
			///mCamera.autoFocus(null);//自动对焦
			
			
			canAutoFocus = true;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Camera.Parameters getParameters()
	{
		if (null != parameters)
		{
			return parameters;
		}
		return null;
	}

	/**
	 * 初始化分辨率
	 * 
	 * @param w
	 *            手机屏幕宽度
	 * @param h
	 *            手机屏幕高度
	 */
	private void initPictureSize(int w, int h)
	{
		List<Size> sizes = getSupportedPictureSize();
		if (sizes == null)
		{
			return;
		}
		int width = 0;
		int height = 0;
		if (null == this.size)
		{
			int maxSize = 0;
			// 获取最大分辨率值
			for (int i = 0; i < sizes.size(); i++)
			{
				Size size = sizes.get(i);
				int pix = size.width * size.height;
				if (pix > maxSize)
				{
					maxSize = pix;
					width = size.width;
					height = size.height;
					this.size = size;
				}
			}

		}
		else
		{
			width = this.size.width;
			height = this.size.height;
		}
		parameters.setPictureSize(width, height);
		this.getLayoutParams().width = w;
		this.getLayoutParams().height = (w * width) / height;
		setPreviewSize(w, h);
	}

	/**
	 * 设置输出图片大小
	 * 
	 * @param parameters
	 */
	public void setPictureSize(View layout, Size size)
	{
		this.size = size;
		int width = size.width;
		int height = size.height;
		int w = this.getLayoutParams().width;
		int h = this.getLayoutParams().height;
		parameters.setPictureSize(size.width, size.height);
		this.getLayoutParams().width = w;
		this.getLayoutParams().height = (w * width) / height;
		// layout.getLayoutParams().width = w;
		// layout.getLayoutParams().height = (w*width)/height;
		// this.setLayoutParams(new RelativeLayout.LayoutParams(w,
		// (w*width)/height));
		layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		// mCamera.setParameters(parameters);
	}

	/**
	 * 获取当前图片分辨率
	 * 
	 * @return
	 */
	public Size getPictureSize()
	{
		return size;
	}

	private Map<Integer, List<Size>> getSupportedPreviewSize()
	{
		Map<Integer, List<Size>> map = new HashMap<Integer, List<Size>>();
		List<Size> list = parameters.getSupportedPreviewSizes();
		for (Size size : list)
		{
			// 计算分辨率比例
			int ratio = SystemUtil.resolutionRatio(size.width, size.height);
			switch (ratio)
			{
			case 18:
			case 14:
			case 11:
				List<Size> l = map.get(ratio);
				if (null == l)
				{
					l = new ArrayList<Camera.Size>();
				}
				l.add(size);
				map.put(ratio, l);
				// 根据比例，插入当前比例最大值
				// List<Size s = map.get(ratio);
				// if(null != s){
				// int pix = size.width * size.height;
				// int s_pix = s.width * s.height;
				// if(pix > s_pix){
				// map.put(ratio, size);
				// }
				// }else{
				// map.put(ratio, size);
				// }
				break;
			default:
				break;
			}
		}
		return map;
	}

	/**
	 * 设置预览图片大小
	 * 
	 * @param parameters
	 */
	private void setPreviewSize(int w, int h)
	{
		// Map<Integer,Size> sizes = getSupportedPreviewSize();
		Map<Integer, List<Size>> sizes = getSupportedPreviewSize();
		if (sizes == null)
		{
			return;
		}
		if (null != size)
		{
			int ratio = SystemUtil.resolutionRatio(size.width, size.height);
			List<Size> list = sizes.get(ratio);
			Size previewSize = getCurrentScreenSize(list, w, h);
			// Size previewSize = sizes.get(ratio);
			parameters.setPreviewSize(previewSize.width, previewSize.height);
		}

	}

	/**
	 * 获得最接近频幕宽度的尺寸
	 * 
	 * @param sizeList
	 * @param w
	 * @param h
	 * @return
	 */
	private Size getCurrentScreenSize(List<Size> sizeList, int w, int h)
	{
		if (sizeList != null && sizeList.size() > 0)
		{
			int screenWidth = h; // 由于旋转了90度
			int screenHeight = w;
			int[] arry = new int[sizeList.size()];
			int temp = 0;
			for (Size size : sizeList)
			{
				arry[temp++] = Math.abs(size.width - screenWidth);
			}
			temp = 0;
			int index = 0;
			for (int i = 0; i < arry.length; i++)
			{
				if (i == 0)
				{
					temp = arry[i];
					index = 0;
				}
				else
				{
					if (arry[i] < temp)
					{
						index = i;
						temp = arry[i];
					}
				}
			}
			return sizeList.get(index);
		}
		return null;
	}

	/**
	 * 拍照
	 */
	public void takePicture(PreviewPictureCallback previewPictureCallback)
	{
		this.previewPictureCallback = previewPictureCallback;
		isTaking = true;
		if (mCamera != null)
		{
			mCamera.takePicture(null, null, null, jpegCallback);
		}
	}

	/**
	 * 获取支持的分辨率，选出各种比例中最大的分辨率
	 * 
	 * @return
	 */
	public List<Size> getSupportedPictureSize()
	{
		List<Size> list = parameters.getSupportedPictureSizes();
		Map<Integer, Size> map = new HashMap<Integer, Size>();
		for (Size size : list)
		{
			// 计算分辨率比例
			int ratio = SystemUtil.resolutionRatio(size.width, size.height);
			switch (ratio)
			{
			case 18:
			case 14:
			case 11:
				// 根据比例，插入当前比例最大值
				Size s = map.get(ratio);
				if (null != s)
				{
					int pix = size.width * size.height;
					int s_pix = s.width * s.height;
					if (pix > s_pix)
					{
						map.put(ratio, size);
					}
				}
				else
				{
					map.put(ratio, size);
				}
				break;
			default:
				break;
			}
		}
		// 将map转为list,分辨率降序排序
		List<Size> result = new ArrayList<Camera.Size>(map.values());
		Collections.sort(result, new Comparator<Size>()
		{

			@Override
			public int compare(Size arg0, Size arg1)
			{
				// TODO Auto-generated method stub
				int flag = arg1.width * arg1.height - arg0.width * arg0.height;
				return flag;
			}
		});
		return result;
	}

	public boolean getIsTaking()
	{
		return isTaking;
	}

	public void setIsTaking(boolean isTaking)
	{
		this.isTaking = isTaking;
	}

	public boolean getCanAutoFocus()
	{
		return canAutoFocus;
	}

	/**
	 * @return the isSupprotCamera
	 */
	public boolean isSupprotCamera()
	{
		return isSupprotCamera;
	}

	/**
	 * 设置缩放比例
	 * 
	 * @param value
	 */
	public void setZoom(int value)
	{
		try
		{
			parameters.setZoom(value / 10);
			mCamera.setParameters(parameters);
			mCamera.startPreview();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 手动对焦功能
	 * 
	 * @param view
	 * @param e
	 *            对焦坐标
	 */
	@SuppressLint("NewApi")
	public void setAutoFocus(View view, MotionEvent e, final AutoFocusCallback autoFocusCallback)
	{
		// 如果对焦未成功，则执行对焦
		Log.i("canAutoFocus", canAutoFocus + "");
		if (canAutoFocus)
		{
			canAutoFocus = false;
			// 获取控件窗口的宽和高
			int vx = view.getWidth(); // 如480
			int vy = view.getHeight();// 如800
			try
			{
				// 获取触摸坐标
				float fx = (float) e.getX();// 如240
				float fy = (float) e.getY();// 如400
				parameters = mCamera.getParameters();
				int mm = parameters.getMaxNumFocusAreas();
				if (mm > 0)
				{

					parameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);

					Rect focusRect = calculateTapArea(view, fx, fy, 1f);
					Rect meteringRect = calculateTapArea(view, fx, fy, 1.5f);

					List<Area> focusAreas = new ArrayList<Area>();
					focusAreas.add(new Camera.Area(focusRect, 1000));

					parameters.setFocusAreas(focusAreas);

					List<Area> meteringAreas = new ArrayList<Area>();
					meteringAreas.add(new Camera.Area(meteringRect, 1000));

					parameters.setMeteringAreas(meteringAreas);

					mCamera.setParameters(parameters);
				}
			}
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
				// mCamera.autoFocus(null);
				// e1.printStackTrace();
			}
			catch (NoSuchMethodError e2)
			{
				// TODO: handle exception

			}
			finally
			{
				try
				{
					mCamera.autoFocus(new Camera.AutoFocusCallback()
					{

						@Override
						public void onAutoFocus(boolean arg0, Camera arg1)
						{
							// TODO Auto-generated method stub
							canAutoFocus = true;
							autoFocusCallback.autoFocus(arg0, arg1);
						}
					});
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Convert touch position x:y to {@link Camera.Area} position -1000:-1000 to
	 * 1000:1000.
	 */
	private Rect calculateTapArea(View view, float x, float y, float coefficient)
	{

		float focusAreaSize = 300;
		int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();

		int centerX = (int) ((x / view.getWidth()) * 2000 - 1000);
		int centerY = (int) ((y / view.getHeight()) * 2000 - 1000);

		int left = clamp(centerX - areaSize / 2, -1000, 1000);
		int top = clamp(centerY - areaSize / 2, -1000, 1000);

		RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);

		return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
	}

	private int clamp(int x, int min, int max)
	{
		if (x > max)
		{
			return max;
		}
		if (x < min)
		{
			return min;
		}
		return x;
	}

	public void startPreview()
	{
		if (mCamera != null)
		{
			mCamera.startPreview();
		}
	}

	public void stopPreview()
	{
		if (null != mCamera)
		{
			mCamera.stopPreview();
		}
	}

	/* 退出拍照片 */
	public void exitPicture()
	{
		if (mCamera != null)
		{
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	/**
	 * 是否支持闪光
	 * 
	 * @return
	 */
	public boolean isSupportedFlashMode()
	{
		if (mCamera == null)
		{
			mCamera = Camera.open();
		}
		parameters = mCamera.getParameters();
		List<String> modes = parameters.getSupportedFlashModes();
		if (modes != null && modes.size() != 0)
		{
			boolean autoSupported = modes.contains(Parameters.FLASH_MODE_AUTO);
			boolean onSupported = modes.contains(Parameters.FLASH_MODE_ON);
			boolean offSupported = modes.contains(Parameters.FLASH_MODE_OFF);
			return autoSupported && onSupported && offSupported;
		}
		return false;
	}

	/**
	 * 设置闪光灯模式
	 * 
	 * @param flashMode
	 */
	public void setFlashMode(FLASH_MODE flashMode)
	{
		String mode = null;
		switch (flashMode)
		{
		case AUTO:
			mode = Parameters.FLASH_MODE_AUTO;
			break;
		case ON:
			mode = Parameters.FLASH_MODE_ON;
			break;
		case OFF:
			mode = Parameters.FLASH_MODE_OFF;
			break;
		}
		try
		{
			parameters.setFlashMode(mode);
			mCamera.setParameters(parameters);
			if (null != flashModeCallback)
			{
				switch (flashMode)
				{
				case AUTO:
					flashModeCallback.onFlashAuto();
					break;
				case ON:
					flashModeCallback.onFlashOn();
					break;
				case OFF:
					flashModeCallback.onFlashOff();
					break;
				}
			}
			saveFlashMode(mode);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initFlashMode()
	{
		SharedPreferences sp = context.getSharedPreferences(SP_PREVIEW_SETTING, 0);
		String mode = sp.getString(SP_FLASH_MODE, Parameters.FLASH_MODE_AUTO);
		if (mode.equals(Parameters.FLASH_MODE_AUTO))
		{
			if (null != flashModeCallback)
			{
				flashModeCallback.onFlashAuto();
			}
		}
		else if (mode.equals(Parameters.FLASH_MODE_ON))
		{
			if (null != flashModeCallback)
			{
				flashModeCallback.onFlashOn();
			}
		}
		else
		{
			if (null != flashModeCallback)
			{
				flashModeCallback.onFlashOff();
			}
		}
		parameters.setFlashMode(mode);
	}

	private void saveFlashMode(String flashMode)
	{
		SharedPreferences sp = context.getSharedPreferences(SP_PREVIEW_SETTING, 0);
		sp.edit().putString(SP_FLASH_MODE, flashMode).commit();
	}

	public FLASH_MODE getFlashMode()
	{
		String mode = parameters.getFlashMode();
		if (mode.equals(Parameters.FLASH_MODE_AUTO))
		{
			return FLASH_MODE.AUTO;
		}
		else if (mode.equals(Parameters.FLASH_MODE_ON))
		{
			return FLASH_MODE.ON;
		}
		else
		{
			return FLASH_MODE.OFF;
		}
	}

	public void setFlashModeCallback(FlashModeCallback callback)
	{
		this.flashModeCallback = callback;
	}

	/**
	 * 输出照片
	 */
	private PictureCallback jpegCallback = new PictureCallback()
	{
		public void onPictureTaken(final byte[] bytes, Camera _camera)
		{
			jpegCallbackThread = new JpegCallbackThread(bytes);
			jpegCallbackThread.start();
		}
	};

	/**
	 * 
	 * 系统名 查勘相机系统 类名 Preview.java 类作用 拍照后回调函数
	 * 
	 * @author wuhaijie
	 * @createTime 2014-1-27 下午3:36:21
	 */
	class JpegCallbackThread extends Thread
	{

		byte[] bytes;

		public JpegCallbackThread(byte[] bytes)
		{
			super();
			this.bytes = bytes;
		}

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			super.run();
			String fileName = previewPictureCallback.doInBackground(bytes);
			Message msg = new Message();
			msg.obj = fileName;
			msg.what = SUCCESS;
			handler.sendMessage(msg);
		}

	};

	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what)
			{
			case SUCCESS:
				previewPictureCallback.postExecute(msg.obj.toString());
				if (null != mCamera)
				{
					mCamera.startPreview();
				}
				isTaking = false;
				break;
			}
		}

	};

	/**
	 * 
	 * 系统名 查勘相机系统 类名 Preview.java 类作用 拍照回调接口
	 * 
	 * @author wuhaijie
	 * @createTime 2014-1-27 下午3:36:44
	 */
	public interface PreviewPictureCallback
	{
		public String doInBackground(final byte[] bytes);

		public void postExecute(String fileName);
	}

	public interface FlashModeCallback
	{
		public void onFlashAuto();

		public void onFlashOn();

		public void onFlashOff();
	}

	public interface AutoFocusCallback
	{
		public void autoFocus(boolean arg0, Camera arg1);
	}

	// 横竖屏切换
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		if (null == mCamera)
		{
			return;
		}
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			Log.i(TAG, "横屏");
			// 横屏
			mCamera.setDisplayOrientation(0);
		}
		else
		{
			Log.i(TAG, "竖屏");
			// 竖屏
			mCamera.setDisplayOrientation(90);
		}
	}

	/**
	 * 不要触屏事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return super.onTouchEvent(event);
	}
	
	private Handler upHandler;
    public void onCameraActivity(Handler handler)
    {
    	upHandler =handler ;
    }
}
