package com.sinosoft.ms.utils.component;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 系统名：移动查勘定损 子系统名：进度对话框 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午3:38:57
 */

public class ProgressDialogUtil {
	private ProgressDialog progressDialog;
	private Context context;

	public ProgressDialogUtil(Context context) {
		this.context = context;
		progressDialog = new ProgressDialog(context);
		progressDialog.setCanceledOnTouchOutside(false);

	}

	public void setCancelable(boolean bool) {
		progressDialog.setCancelable(bool);

	}

	/**
	 * 进度对话框
	 * 
	 * @param context
	 * @param title
	 */
	public void show(String title) {

		//
		try {
			progressDialog.setTitle(title);
			progressDialog.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void dismiss() {
		if(progressDialog.isShowing()){
			progressDialog.dismiss();
		}
		

	}

}
