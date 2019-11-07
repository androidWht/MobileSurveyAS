package com.sinosoft.ms.utils.component;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：定制提示框控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午10:23:49
 */

public class CustomDialog {

	public static Dialog dialog;
	public static void show(Context context, String title, String content) {
		
		if(dialog!=null&&dialog.isShowing()){
			try{
			dialog.dismiss();
		   }catch(Exception e){
		   }
		}
		dialog = new Dialog(context, R.style.dialog);
		View view = View.inflate(context, R.layout.dialog, null);
		dialog.setContentView(view);
		LinearLayout layout = (LinearLayout) dialog
				.findViewById(R.id.button_layout);
		Button ok = new Button(context);
		ok.setTextColor(context.getResources().getColor(R.color.white)) ;
		ok.setText("确定");
		
		ok.setBackgroundResource(R.drawable.new_dialog_btns);
		ok.setPadding(5, 5, 5, 5);
		LayoutParams parmas = new LayoutParams(LayoutParams.FILL_PARENT,
				80, 1);
		layout.addView(ok, parmas);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});
       
		TextView contentTxt = (TextView) dialog
				.findViewById(R.id.custom_dialog_content);
		TextView titleTxt = (TextView) dialog
				.findViewById(R.id.custom_dialog_title);
		//titleTxt.setText(title);
		contentTxt.setText(content);
		dialog.setCancelable(false);
		try{
		 dialog.show(); 
		}catch(Exception e){
		}
	}
	
	public static Dialog show(Context context, String title, View contentView) {
		if (dialog != null && dialog.isShowing()) {
			try {
				dialog.dismiss();
			} catch (Exception e) {
			}
		}
		dialog = new Dialog(context, R.style.dialog);
		View view = View.inflate(context, R.layout.dialog1, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(lp);
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.contentlayout);
		layout.addView(contentView);
		dialog.setContentView(view);
		TextView titleText = (TextView)view.findViewById(R.id.custom_dialog_title);
		titleText.setText(title);
		try {
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dialog;
	}
	
	public static Dialog showTip(Context context, String title, View contentView) {
		if (dialog != null && dialog.isShowing()) {
			try {
				dialog.dismiss();
			} catch (Exception e) {
			}
		}
		dialog = new Dialog(context, R.style.dialog);
		View view = View.inflate(context, R.layout.dialog2, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(lp);
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.contentlayout);
		layout.addView(contentView);
		dialog.setContentView(view);
		TextView titleText = (TextView)view.findViewById(R.id.custom_dialog_title);
		titleText.setText(title);
		try {
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dialog;
	}
	
	public static Dialog showDataSearch(Context context, String title, View contentView) {
		if (dialog != null && dialog.isShowing()) {
			try {
				dialog.dismiss();
			} catch (Exception e) {
			}
		}
		dialog = new Dialog(context, R.style.dialog);
		View view = View.inflate(context, R.layout.dialog_data_search, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(lp);
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.contentlayout);
		layout.addView(contentView);
		dialog.setContentView(view);
		try {
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dialog;
	}

	/**
	 * 自定义对话框，两个按钮
	 * 
	 * @param context
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param OkListener
	 *            确定按钮的事件
	 * @param cancleListener
	 *            取消按钮的事件
	 * @return 实例本身
	 */
	public static Dialog show(Context context, String title, String content,
			OnClickListener OkListener, OnClickListener cancleListener) {

		
		return show(context,"信息提示",content,"确定","取消",
				OkListener,cancleListener);
	}
	
	public static Dialog show(Context context, String title, String content,String okText,String cancleText,
			OnClickListener OkListener,OnClickListener cancleListener) {

		View view = View.inflate(context, R.layout.dialog, null);
		TextView contentTxt = (TextView) view
				.findViewById(R.id.custom_dialog_content);
		TextView titleTxt = (TextView) view
				.findViewById(R.id.custom_dialog_title);
		titleTxt.setText("信息提示");
		contentTxt.setText(content);
		final Dialog dialog = new Dialog(context, R.style.dialog);
		
		dialog.setContentView(view);
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.button_layout);
		Button ok = new Button(context);// 确定按钮
		ok.setText(okText);
		ok.setTextColor(context.getResources().getColor(R.color.white)) ;
		ok.setBackgroundResource(R.drawable.new_dialog_btns);
		ok.setPadding(10, 10, 10, 10);
		
		LayoutParams parmas = new LayoutParams(LayoutParams.WRAP_CONTENT,
				80, 1);
		parmas.setMargins(10, 0, 0, 0) ;
		layout.addView(ok, parmas);
		if (StringUtils.isNotEmpty(cancleText)) {
			Button cancle = new Button(context);
			cancle.setText(cancleText);

			cancle.setBackgroundResource(R.drawable.new_dialog_btns);
			cancle.setPadding(10, 10, 10, 10);
			cancle.setTextColor(context.getResources().getColor(R.color.white)) ;
			layout.addView(cancle, parmas);
			if (cancleListener != null) {
				cancle.setOnClickListener(cancleListener);
			} else {
				cancle.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
			}
		}
		if (OkListener != null) {
			ok.setOnClickListener(OkListener);
		} else {
			ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();

				}
			});
		}
		
		if(!((Activity)context).isFinishing() && !dialog.isShowing()){
			dialog.show();
		}
		dialog.setCancelable(false);
		return dialog;
	}
	
	
	/**
	 * 对话框，一个按钮
	 * 
	 * @param context
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param OkListener
	 *            确定按钮的事件
	 * 
	 * return dialog
	 */
	
	public static Dialog show(Context context, String title, String content,OnClickListener listen) {
		
		if(dialog!=null&&dialog.isShowing()){
			try{
			dialog.dismiss();
		   }catch(Exception e){
		   }
		}
		dialog = new Dialog(context, R.style.dialog);
		View view = View.inflate(context, R.layout.dialog, null);
		dialog.setContentView(view);
		LinearLayout layout = (LinearLayout) dialog
				.findViewById(R.id.button_layout);
		Button ok = new Button(context);
		ok.setTextColor(context.getResources().getColor(R.color.white)) ;
		ok.setText("确定");
		
		ok.setBackgroundResource(R.drawable.new_dialog_btns);
		ok.setPadding(5, 5, 5, 5);
		LayoutParams parmas = new LayoutParams(LayoutParams.FILL_PARENT,
				80, 1);
		layout.addView(ok, parmas);
//		ok.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//
//			}
//		});
		dialog.setCancelable(false);
		ok.setOnClickListener(listen);
       
		TextView contentTxt = (TextView) dialog
				.findViewById(R.id.custom_dialog_content);
		TextView titleTxt = (TextView) dialog
				.findViewById(R.id.custom_dialog_title);
		//titleTxt.setText(title);
		contentTxt.setText(content);
		try{
		 dialog.show(); 
		}catch(Exception e){
		}
		return dialog;
	}
	

}
