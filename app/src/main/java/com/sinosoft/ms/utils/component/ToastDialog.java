package com.sinosoft.ms.utils.component;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：Toast提示工具类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午10:38:11
 */

public class ToastDialog {
	public static final int INFO = 0;
	public static final int ERROR = 1;
	private static WindowManager mWindowManager = null;
	private static View view = null;
	
	 public static void show(Context context, String msg,int type) {
		 try{
			 switch(type){
			 case INFO:
				 show(context,msg);
				 break;
			 case ERROR:
				 CustomDialog.show(context, msg, msg);
				 break;
			 }
			 
		 }catch(Exception e){
    }
}

	/*
	 * public static void show(Context context, String msg) {
	 * 
	 * Toast toast=new Toast(context); View view=View.inflate(context,
	 * R.layout.toast, null); TextView
	 * text=(TextView)view.findViewById(R.id.info);
	 * 
	 * 
	 * if(StringUtils.isEmpty(msg)){ msg=""; } text.setText(msg);
	 * toast.setView(view); toast.setDuration(3000); toast.show();
	 * //Toast.makeText(context, msg, Toast.LENGTH_LONG).show(); }
	 */
	
	  public static void show(Context context, String msg) {
	    Toast toast = new Toast(context);
		View view = View.inflate(context, R.layout.dialog_toast, null);
		TextView text = (TextView) view.findViewById(R.id.info);
		ImageView icon = (ImageView) view.findViewById(R.id.toast_icon);
        icon.setBackgroundResource(R.drawable.toast_info);
		if (StringUtils.isEmpty(msg)) {
			msg = "";
		}
		text.setText(msg);
		int duration = msg.length() / 10;
		toast.setView(view);
		toast.setDuration(5000 + duration);
		toast.show();
	}
	 
/*
	public static void show(Context context, String msg, int type) {
		     cancelCurrentToast();
	         mWindowManager = (WindowManager) context
						.getSystemService(Context.WINDOW_SERVICE);
			view = View.inflate(context, R.layout.toast, null);
			TextView text = (TextView) view.findViewById(R.id.info);
			ImageView icon = (ImageView) view.findViewById(R.id.toast_icon);
			switch (type) {
			case INFO:
				icon.setImageResource(R.drawable.toast_info);
				break;
			case ERROR:
				icon.setImageResource(R.drawable.toast_error);
				break;
			}
			if (StringUtils.isEmpty(msg)) {
				msg = "";
			}
			text.setText(msg);

			WindowManager.LayoutParams params = new WindowManager.LayoutParams();
			params.gravity = Gravity.BOTTOM;
			params.alpha = 0.85f;
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
					| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
			params.format = PixelFormat.TRANSLUCENT;
			params.verticalMargin = 0.2f;
			params.windowAnimations = android.R.style.Animation_Toast;
			if (view.getParent() == null) {
				mWindowManager.addView(view, params);
			}

			int duration = 0;
			if (msg.length() > 10) {
				duration = (msg.length() - 10) / 10 * 1000;// 增加十个就增加一秒
			}
			handler.sendEmptyMessageDelayed(101, 2000 + duration);
	
		// handler.removeMessages(101);
		// handler.sendEmptyMessageDelayed(101, 2000);
	}
*/

	static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			cancelCurrentToast();

		}

	};
	public static void cancelCurrentToast() {
		if (view != null && view.getParent() != null) {
			handler.removeMessages(101);
			mWindowManager.removeView(view);
			mWindowManager=null;
			view=null;
		}
	}
}
