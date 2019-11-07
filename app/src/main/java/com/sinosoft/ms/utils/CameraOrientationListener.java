package com.sinosoft.ms.utils;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;
/**
 * 
 * 类名			CameraOrientationListener.java
 * 类作用		手机根据重力方向来返回拍摄出来的图片方向
 * 1,，90°为竖屏方向
 * 2，在Activity中声明定义此对象，实现onDirectionChanged方法返回当前相机角度
 * 3，并调用enable方法来开启重力传感器
 * 4，在离开页面时必须调用disable方法关闭传感器
 * @author wuhaijie
 * @createTime 2014-4-11 下午2:19:49
 */
public abstract class CameraOrientationListener extends OrientationEventListener{
	private int orientation = 90;
	
	
	public CameraOrientationListener(Context context) {
		super(context,SensorManager.SENSOR_DELAY_NORMAL);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param context
	 * @param rate	SensorManager.SENSOR_DELAY_NORMAL
	 */
	public CameraOrientationListener(Context context, int rate) {
		super(context, rate);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onOrientationChanged(int orientations) {
		// TODO Auto-generated method stub
		if(orientations > 325 || orientations <= 45){  
			//325 < o <= 45
            orientation = 90;
        }else if(orientations > 45 && orientations <= 135){  
        	//45 < o <= 135
            orientation = 180;  
        }else if(orientations > 135 && orientations <= 225){  
        	//135 < o <225
            orientation = 270;  
        }else {  
        	//225 < o <= 325
            orientation = 0;
        }
		onDirectionChanged(orientation);
	}
	
	public abstract void onDirectionChanged(int direction);
	
}