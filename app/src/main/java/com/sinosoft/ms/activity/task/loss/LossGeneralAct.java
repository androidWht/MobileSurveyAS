package com.sinosoft.ms.activity.task.loss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.component.CustomDialog;

/**
 * 系统名：移动查勘定损系统 子系统名：定损概要信息界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 2013-5-28 下午12:51:10
 */

public class LossGeneralAct extends Activity implements OnClickListener {
	private Button conventionLossBtn = null;
	private RegistData registData = null;
	private Button simpleLossBtn = null;
	private TextView advTxt = null;
	private String registNo = null;
	private String registId = null;
	private Button backBtn = null;
	private String taskId = null;
	private Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loss_general);
		ActivityManage.getInstance().push(this);

		intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		taskId = intent.getStringExtra("taskId");
		registId = intent.getStringExtra("registId");
		Bundle bundle = intent.getBundleExtra("item");
		if (bundle != null) {
			registData = (RegistData) bundle.getSerializable("bean");
		}
		if (registData == null) {
			registData = new RegistData();
			registData.init();
		}
		advTxt = (TextView) findViewById(R.id.advTxt);
		advTxt.setText("即用自定义模式，提供常用快速定损的配件信息，可以由定损员快速选择。配件限价。不满足条件的走原精友流程。\n数据传回业务系统的方式,请参看原定数据中定损换件,定损修理,辅料信息.");

		simpleLossBtn = (Button) findViewById(R.id.simpleLossBtn);
		conventionLossBtn = (Button) findViewById(R.id.conventionLossBtn);
		backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(this);
		simpleLossBtn.setOnClickListener(this);
		conventionLossBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		intent.putExtra("registNo", registNo);
		intent.putExtra("taskId", taskId);
		intent.putExtra("registId", registId);
		Bundle bundle = new Bundle();
		bundle.putSerializable("bean", registData);
		intent.putExtra("item", bundle);
		switch (v.getId()) {
		case R.id.simpleLossBtn:
			// 到简易定损
			intent.setClass(this, LossMenuAct.class);
			// intent.setClass(this, LossViewPager.class);
			startActivity(intent);
			try {
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finish();
			break;
		case R.id.conventionLossBtn:
			CustomDialog.show(LossGeneralAct.this, "信息提示", "暂不支持常规定损，敬请期待!");
			// 到复杂定损
			// intent.setClass(this, SimpleSetLossTreamAct.class);
			// startActivity(intent);
			// try {
			// ActivityManage.getInstance().pop() ;
			// finish() ;
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			break;
		case R.id.backBtn:
			try {
				this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		conventionLossBtn = null;
		registData = null;
		simpleLossBtn = null;
		registNo = null;
		backBtn = null;
		taskId = null;
		intent = null;
	}

}
