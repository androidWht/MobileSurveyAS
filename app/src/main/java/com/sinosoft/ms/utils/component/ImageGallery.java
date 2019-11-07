package com.sinosoft.ms.utils.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * 
 * 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS CORPORATION ALL RIGHTS
 * RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午6:12:12
 */

public class ImageGallery extends Gallery {

	/**
	 * @param context
	 * @param attrs
	 */
	public ImageGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// //返回false 解决Gallery拖拽滑动过快
		int keyCode;
		if (isScrollingLeft(e1, e2)) {
			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);
		return true;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}
	
}
