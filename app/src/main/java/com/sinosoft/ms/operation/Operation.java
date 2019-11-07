package com.sinosoft.ms.operation;

import com.sinosoft.ms.utils.component.ProgressDialogUtil;

import android.app.Activity;
import android.content.Context;

public class Operation {
	protected Context context;
	protected ProgressDialogUtil progressDialog;	//进度对话框

	public Operation(Context context) {
		super();
		this.context = context;
		progressDialog = new ProgressDialogUtil(context);
	}
	
	public void showDialog(String msg){
		try {
			if(msg == null || msg.equals("")){
				msg = "正在获取信息...";
			}
			if(progressDialog != null && !((Activity)context).isFinishing()){
				progressDialog.show(msg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void dimissDialog(){
		try {
			if(progressDialog != null && !((Activity)context).isFinishing()){
				progressDialog.dismiss();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
