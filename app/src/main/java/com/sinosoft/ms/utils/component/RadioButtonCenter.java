package com.sinosoft.ms.utils.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

import com.sinosoft.ms.R;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author maya
 * @createTime 下午3:27:12
 */

// 在android里，RadioButton中buttonDrawable的居中方法
// android radiobutton 居中 drawable 
// 在android里，RadioButton的ButtonDrawable是靠左放置的，而且是在onDraw方法里写死的。
// 如果我们想居中该怎么办呢？可以继承这个类并在onDraw方法里写上：

public class RadioButtonCenter extends RadioButton {
	public RadioButtonCenter(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CompoundButton, 0, 0);
		buttonDrawable = a.getDrawable(1);
		setButtonDrawable(R.drawable.emtry);
	}

	Drawable buttonDrawable;

	@Override
	protected void onDraw(Canvas canvas) {
		setButtonDrawable(null);
		super.onDraw(canvas);
		if (buttonDrawable != null) {
			buttonDrawable.setState(getDrawableState());
			final int verticalGravity = getGravity()
					& Gravity.VERTICAL_GRAVITY_MASK;
			final int height = buttonDrawable.getIntrinsicHeight();
			int y = 0;
			switch (verticalGravity) {
			case Gravity.BOTTOM:
				y = getHeight() - height;
				break;
			case Gravity.CENTER_VERTICAL:
				y = (getHeight() - height) / 2;
				break;
			}
			int buttonWidth = buttonDrawable.getIntrinsicWidth();
			
			String str=this.getText().toString();
			Paint p=this.getPaint();
			float strWidth = p.measureText(str);   
			int buttonLeft =(int) (getWidth() - buttonWidth-strWidth) / 2-10;
			
			buttonDrawable.setBounds(buttonLeft, y, buttonLeft + buttonWidth, y
					+ height);
			buttonDrawable.draw(canvas);
		}
	}
}