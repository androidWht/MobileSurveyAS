package com.sinosoft.ms.activity.task.image.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.sinosoft.ms.R;

/**
 * 
 * 系统名 查勘相机系统 类名 PhotographyOperation.java 类作用 处理拍照过程中相关操作
 * 
 * @author wuhaijie
 * @createTime 2014-3-11 下午3:24:07
 */
public class PhotographyUtil
{

	private static final String TAG_FOCUSIMAGE = "focus_tag";
	private static final int FOCUS_END = 0; // 对焦结束标识
	private static PhotographyUtil photographyOperation;
	private ImageView imageView; // 对焦图片
	private boolean isFocusing = false;// 对焦状态
	private PhotographyCallback callback;

	public static PhotographyUtil getInstance()
	{
		if (photographyOperation == null)
		{
			photographyOperation = new PhotographyUtil();
		}
		return photographyOperation;
	}

	/**
	 * 添加对焦框
	 * 
	 * @param context
	 * @param v
	 *            显示窗体
	 * @param event
	 *            触摸点
	 * @param callback
	 *            对焦回调
	 */
	public void addFocusNormal(Context context, View v, MotionEvent event, PhotographyCallback callback)
	{
		this.callback = callback;
		RelativeLayout layout = (RelativeLayout) v;
		// 1.更改对焦中标识
		isFocusing = false;
		// 2.移除已经添加进去的图片，并添加新的图片
		imageView = (ImageView) layout.findViewWithTag(TAG_FOCUSIMAGE);
		if (imageView != null)
		{
			layout.removeView(imageView);
		}
		imageView = new ImageView(context);
		imageView.setTag(TAG_FOCUSIMAGE);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.focus_normal);
		imageView.setImageBitmap(bitmap);

		// 3.设置图片位置为触摸点的位置
		LayoutParams params = new RelativeLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
		params.leftMargin = (int) (event.getX() - bitmap.getWidth() / 2);
		params.topMargin = (int) (event.getY() - bitmap.getHeight() / 2);
		layout.addView(imageView, params);

		// 4.启动缩放动画
		ScaleAnimation animation = new ScaleAnimation(2, 1, 2, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(300);
		animation.setFillAfter(true);
		animation.setAnimationListener(new MyAnimationListener());
		imageView.startAnimation(animation);
	}
	public void addFocusNormal(Context context, View v,  PhotographyCallback callback)
	{
		this.callback = callback;
		RelativeLayout layout = (RelativeLayout) v;
		// 1.更改对焦中标识
		isFocusing = false;
		// 2.移除已经添加进去的图片，并添加新的图片
		imageView = (ImageView) layout.findViewWithTag(TAG_FOCUSIMAGE);
		if (imageView != null)
		{
			layout.removeView(imageView);
		}
		imageView = new ImageView(context);
		imageView.setTag(TAG_FOCUSIMAGE);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.focus_normal);
		imageView.setImageBitmap(bitmap);

		// 3.设置图片位置为触摸点的位置
		LayoutParams params = new RelativeLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
		
		int w = v.getWidth();
		int h = v.getWidth();
		
		int w1 = bitmap.getWidth();
		int h1 = bitmap.getHeight();
		
		//DisplayMetrics displayMetrics = new DisplayMetrics();
		//getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		//String string = "手机屏幕分辨率为：" + displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;

		
		
		
		System.out.println("===w" +w +">>>>>>>>"+h);
		
		params.leftMargin = (int) (v.getWidth() - bitmap.getWidth()) / 2;
		params.topMargin = (int) (v.getHeight() - bitmap.getHeight()) / 2;
		
		
		//params.leftMargin = (int) (v.getWidth() - bitmap.getWidth() / 2);
		//params.topMargin = (int) (v.getHeight() - bitmap.getHeight() / 2);
		
		
		layout.addView(imageView, params);

		// 4.启动缩放动画
		ScaleAnimation animation = new ScaleAnimation(2, 1, 2, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(300);
		animation.setFillAfter(true);
		animation.setAnimationListener(new MyAnimationListener());
		imageView.startAnimation(animation);
	}

	/**
	 * 移除对焦框
	 * 
	 * @param context
	 * @param v
	 *            显示窗口
	 * @param success
	 *            是否对焦成功
	 */
	public void removeFocus(Context context, final View v, boolean success)
	{
		RelativeLayout layout = (RelativeLayout) v;
		Bitmap bitmap = null;
		// 如果对焦成功
		if (success)
		{
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.focus_success);
		}
		else
		{ // 如果对焦失败
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.focus_failure);
		}
		// 更改图片颜色
		if (isFocusing)
			imageView.setImageBitmap(bitmap);
		// 移除图片
		new Thread()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				super.run();
				try
				{
					sleep(1000);
					Message msg = new Message();
					msg.what = FOCUS_END;
					msg.obj = v;
					handler.sendMessage(msg);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}.start();

	}

	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what)
			{
			case FOCUS_END:
				RelativeLayout v = (RelativeLayout) msg.obj;
				if (isFocusing)
					v.removeView(imageView);
				callback.focusExit();
				break;
			}
		}

	};

	class MyAnimationListener implements AnimationListener
	{

		public MyAnimationListener()
		{
			super();
		}

		@Override
		public void onAnimationEnd(Animation arg0)
		{
			// TODO Auto-generated method stub
			// 更改标识为正在对焦中
			isFocusing = true;
			callback.focusEnter();
		}

		@Override
		public void onAnimationRepeat(Animation arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(Animation arg0)
		{
			// TODO Auto-generated method stub

		}

	}

	/**
	 * 
	 * 系统名 查勘相机系统 类名 PhotographyOperation.java 类作用 添加对焦图片回调
	 * 
	 * @author wuhaijie
	 * @createTime 2014-3-11 下午4:45:57
	 */
	public interface PhotographyCallback
	{
		// 进入对焦
		public void focusEnter();

		public void focusExit();
	}
}
