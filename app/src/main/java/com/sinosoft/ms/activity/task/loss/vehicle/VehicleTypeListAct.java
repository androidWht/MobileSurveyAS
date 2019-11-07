package com.sinosoft.ms.activity.task.loss.vehicle;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.VehicleType;
import com.sinosoft.ms.service.impl.VehicleTypeService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.adapter.VehicleTypeAdapter;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;

/**
 * 车辆定型信息
 */
public class VehicleTypeListAct extends Activity implements OnClickListener {
	private VehicleTypeService vehicleTypeService = null;
	private ExpandableListView lossVehicleList = null;
	private List<VehicleType> vehicleTypeList = null;
	private int toTag = 0;
	private ProgressDialogUtil progress = null;
	private VehicleTypeAdapter adapter = null;
	private String emusId = null;
	private String vehSeriName = null;
	private String registNo = null;
	private String taskId = null;
	private Button backBtn;
	
	private String vehFactoryName ;
	private String vehBrandName ;
	private String vehGroupName ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		vehicleTypeService = null;
		lossVehicleList = null;
		vehicleTypeList = null;
		progress = null;
		adapter = null;
		emusId = null;
		vehSeriName = null;
		registNo = null;
		taskId = null;
		backBtn = null;
		vehFactoryName = null ;
		vehBrandName = null ;
		vehGroupName = null ;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_vehicle_type_list);
		ActivityManage.getInstance().push(this);

		Intent intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		taskId = intent.getStringExtra("taskId");
		emusId = intent.getStringExtra("emusId");
		vehSeriName = intent.getStringExtra("vehSeriName");
		toTag = intent.getIntExtra("toTag",0);
		vehFactoryName = intent.getStringExtra("vehFactoryName");
		vehBrandName = intent.getStringExtra("vehBrandName");
		vehGroupName = intent.getStringExtra("vehGroupName");
		lossVehicleList = (ExpandableListView) findViewById(R.id.lossVehicleList);
		backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(this);
		// 加载列表数据
		loadListData();

	}

	/**
	 * 加载列表数据
	 */
	private void loadListData() {
		progress = new ProgressDialogUtil(this);
		progress.show("数据加载中");
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					vehicleTypeService = new VehicleTypeService();
					User user = UserCashe.getInstant().getUser();
					vehicleTypeList = vehicleTypeService.execute(
							user.getName(), emusId, vehSeriName);
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = MyUtils.getErrorCode(e.getMessage());
					msg.obj = e.getMessage();
				} finally {
					handler.sendMessage(msg);
				}
			}

		}.start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (progress != null) {
				progress.dismiss();
			}
			switch (msg.what) {
			case 0:

				break;
			case 1:
				if (null != vehicleTypeList && !vehicleTypeList.isEmpty()) {
					adapter = new VehicleTypeAdapter(vehicleTypeList, registNo,
							taskId, VehicleTypeListAct.this,
							toTag,vehFactoryName,vehBrandName,vehGroupName);
					lossVehicleList.setAdapter(adapter);
				}
				break;
			case AppConstants.NO_LOGIN_CODE:
				
				try {
					VehicleTypeListAct.this.finish();
					ActivityManage.getInstance().pop();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		};

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backBtn:
			try {
				VehicleTypeListAct.this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
			}
			break;
		}
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
