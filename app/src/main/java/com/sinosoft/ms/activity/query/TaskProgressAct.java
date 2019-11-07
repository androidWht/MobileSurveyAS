package com.sinosoft.ms.activity.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.po.NodeData;
import com.sinosoft.ms.service.impl.CaseStatusQueryService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;

/**
 * 系统名：移动查勘 子系统名：进度 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午3:35:54
 */

public class TaskProgressAct extends Activity implements OnClickListener {
	private CaseStatusQueryService service = null;
	private ProgressDialogUtil progressDialog = null;
	private ListView progressList = null;
	private Button confirmProgressBtn = null;
	private Button back = null;
	private String registNo = null;
	private String registId = null;
	private String taskId = null;
	private List<NodeData> list = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_progress);
		Intent intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		registId = intent.getStringExtra("registId");
		taskId = intent.getStringExtra("taskId");
		if (registNo == null) {
			registNo = "";
		}
		setViewController();
		loadData();
		// CaseStatusQueryService
	}

	public void setViewController() {
		progressList = (ListView) findViewById(R.id.progressList);
		confirmProgressBtn = (Button) findViewById(R.id.confirmProgressBtn);
		back = (Button) findViewById(R.id.backProgressBtn);
		confirmProgressBtn.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	public void loadData() {
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("加载数据中...");
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					User user = UserCashe.getInstant().getUser();
					service = new CaseStatusQueryService();
					list = service.sreach(user.getName(), registNo, registId, taskId);

					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = MyUtils.getErrorCode(e.getMessage());
					msg.obj = "" + e.getMessage();
				} finally {
					handler.sendMessage(msg);
				}
			}

		}.start();

	}

	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			// 当msg.what=0时,请求失败,可从msg.obj取得错误信息
			// 当msg.what=1时,请求成功
			switch (msg.what) {
			case 0:
				if (msg.obj == null) {
					ToastDialog.show(TaskProgressAct.this, "错误类型未知",ToastDialog.ERROR);
				} else {
					ToastDialog.show(TaskProgressAct.this, msg.obj.toString(),ToastDialog.ERROR);
				}
				Log.d("返回错误信息", "" + msg.obj.toString());
				break;
			case 1:
				if (list != null && !list.isEmpty()) {
					List<Map<String, String>> data = new ArrayList<Map<String, String>>();

					
				Iterator<NodeData> iterator=list.iterator();
				while(iterator.hasNext()){
					Map<String, String> map = new HashMap<String, String>();
					NodeData nodeData=iterator.next();
					map.put("registNo", registNo);
					map.put("taskId", nodeData.getTaskId());
					map.put("preTaskId", nodeData.getPreTaskId());
					map.put("nodeId", nodeData.getNodeId());
					map.put("nodeCName", nodeData.getNodeCName());
					map.put("state", nodeData.getState());
					data.add(map);
				}
					SimpleAdapter adapter = new SimpleAdapter(
							TaskProgressAct.this, data,
							R.layout.item_task_progress,
							new String[] { "registNo","taskId","nodeCName","state"},
							new int[] { R.id.registNo,R.id.taskId,R.id.nodeCName,R.id.state });
					progressList.setAdapter(adapter);

				} else {
					ToastDialog.show(TaskProgressAct.this, "查询结果为空",ToastDialog.ERROR);
				}
				break;
			case AppConstants.NO_LOGIN_CODE:
				Intent intent = getIntent();
				intent.setClass(TaskProgressAct.this, LoginAct.class);
				startActivity(intent);
				try {
					TaskProgressAct.this.finish();
					ActivityManage.getInstance().pop();
				} catch (Exception e) {
				}
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirmProgressBtn:
		case R.id.backProgressBtn:
			try {
				TaskProgressAct.this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		service = null;
		progressDialog = null;
		progressList = null;
		confirmProgressBtn = null;
		back = null;
		registNo = null;
		if(null!=list){
			list.clear();
		}
		list = null;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		try {
			this.finish();
			ActivityManage.getInstance().pop() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
