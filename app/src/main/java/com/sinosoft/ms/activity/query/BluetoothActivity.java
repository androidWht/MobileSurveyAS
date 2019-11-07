package com.sinosoft.ms.activity.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.printer.ZQPrinter;
import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.TestData;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.po.NodeData;
import com.sinosoft.ms.service.impl.TaskNucleiService;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.PrintDataService;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;

public class BluetoothActivity extends Activity implements OnClickListener {
	private static String NOT_BLUETOOTH = "本机无蓝牙设备";
	private BluetoothAdapter mBtAdapter;
	private Map<String, Object> map = null;
	private ZQPrinter zQPrinter = null;
	private String macAddress;
	private int errorState;// 错误标识
	private Dialog dialog;
	
	private TextView caseCenterTitle;
	private TextView caseInfoTxt;
	private Button printBtn;
	private Button printBtnBack;
	
	//定损报案号，id
	private String taskId;
	private String registNo;
	private String registId;
	private boolean checkLoss = false;		//定损是否一打开就进行核损
	private String content;
	private ProgressDialogUtil progressDialog;
	private TaskNucleiService service = null;
	private List<NodeData> list = null;
	private boolean isLoss = false;		//是否定损案件
	private int isSuccessCheckLoss = 1;		//是否核损完成 0-核损不通过 1-核损通过
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_bluetooth);
		
		taskId = getIntent().getStringExtra("taskId");
		registNo = getIntent().getStringExtra("registNo");
		registId  = getIntent().getStringExtra("registId");
		content = getIntent().getStringExtra("content");
		checkLoss = getIntent().getBooleanExtra("checkLoss", false);
		loadController();
		
		// 是否打印凭证
		boolean isPrintting = getIntent().getBooleanExtra("isPrintting", true) ;
		if(!isPrintting){
			this.printBtn.setVisibility(View.GONE) ;
		}
		
		printBtn.setOnClickListener(this);
		printBtnBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});

		initTitle();
		
	}

	private void loadController() {
		caseCenterTitle = (TextView)findViewById(R.id.caseCenterTitle);
		caseInfoTxt = (TextView) findViewById(R.id.caseInfoTxt);
		printBtn = (Button) findViewById(R.id.printBtn);
		if(content == null || content.equals("")){
			content = TestData.getContent();
		}
		// String content = "测试打印";
		content = "\t\t\t" + TestData.getTitle() + "\n" + content ;
//				+ "\n\t\t\t\t\t" + "第 1 联";

		caseInfoTxt.setText(content);
		printBtn.setText("确认打印");
		printBtnBack = (Button) findViewById(R.id.printBtnBack);

	}
	
	private void initTitle(){
		//设置标题，如果是定损案件
		if((null != taskId && !taskId.equals("")) && (null!= registNo && !registNo.equals(""))){
			caseCenterTitle.setText("移动定损打印凭证");
			isLoss = true;	
			isSuccessCheckLoss = 0;
			if(checkLoss){
				checkTask();
			}
		}else{
			isLoss = false;
			caseCenterTitle.setText("移动查勘打印凭证");
		}
		
	}

	private void loadBluetooth() {

		// 获取本地蓝牙适配器
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		// 本地是否有蓝牙设备
		if (null == mBtAdapter) {
			errorState = 2;
			return;
		}
		// 用户蓝牙设备是否己经开启,没有开启提示用户建立连接
		if (!mBtAdapter.isEnabled()) {
			errorState = 0;
			return;
		}
		// 获取可用蓝牙设备
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
		if (pairedDevices.size() <= 0) {
			errorState = 3;
			return;
		}
		macAddress = filterDevice(pairedDevices);
		if (null != macAddress) {
			errorState = 1;
		} else {
			errorState = 4;
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.printBtn:
			if(isLoss){
				if(isSuccessCheckLoss==1){
					handler.sendEmptyMessage(2);
				}else{
					checkTask();
				}
			}else{
				handler.sendEmptyMessage(2);
			}
			break;
		case R.id.printBtnBack:
			finish();

			break;
		}
	}
	
	//核损中
	private void checkTask(){
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("加载数据中...");
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					User user = UserCashe.getInstant().getUser();
					service = new TaskNucleiService();
					isSuccessCheckLoss = service.checkTaskNuclei(taskId,registId);
					
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
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (progressDialog != null) {
				progressDialog.dismiss();
//				progressDialog = null;
			}
			switch(msg.what){
			case 0:
				if(null == msg.obj || ((String)msg.obj).equals("")){
					msg.obj = "核损未通过";
					isSuccessCheckLoss = 0;
				}
				ToastDialog.show(BluetoothActivity.this, msg.obj+"",
						ToastDialog.ERROR);
				break;
			case 1:		//成功
				//核损不通过
				if(isSuccessCheckLoss==0){
					handler.sendEmptyMessage(0);
				}else{
					dialog = CustomDialog.show(BluetoothActivity.this, "提示", "核损通过，是否进行打印？", new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							handler.sendEmptyMessage(2);
						}
						
					}, null);
				}
				break;
			case 2:
				PrintDataService service = PrintDataService.getInstance();
				if(service.getIsConnected()){
					service.send(BluetoothActivity.this,TestData.getString());
				}else{
					Intent intent = new Intent();
					intent.putExtra("askConnection", true);
					intent.setClass(BluetoothActivity.this,
							WoosimPrinterActivity.class);
					startActivity(intent);
				}
//				loadBluetooth();
				//开始打印
//				switch (errorState) {
//				case 0:// 蓝牙未开启
//					System.out.println("未开启");
//					Intent intent = new Intent();
//					intent.putExtra("askConnection", true);
//					intent.setClass(BluetoothActivity.this,
//							WoosimPrinterActivity.class);
//					startActivity(intent);
//					break;
//				case 1:// 成功
//					System.out.println("成功");
//					print();
//					break;
//				case 2:// 无蓝牙
//					System.out.println("无蓝牙");
//					ToastDialog.show(BluetoothActivity.this,
//							BluetoothActivity.NOT_BLUETOOTH, ToastDialog.ERROR);
//					break;
//				case 3:// 未配对
//					System.out.println("为配对");
//				case 4:// 未可用蓝牙
//					System.out.println("无可用蓝牙");
//					intent = new Intent();
//					intent.setClass(BluetoothActivity.this,
//							WoosimPrinterActivity.class);
//					startActivity(intent);
//					break;
//				}
				break;
			case 3:
				dialog = CustomDialog.show(BluetoothActivity.this, "提示", "连接打印机失败 重新进入蓝牙设置", new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Intent intent = new Intent();
						intent.setClass(BluetoothActivity.this,
								WoosimPrinterActivity.class);
						startActivity(intent);
					}
					
				}, null);
				break;
			case 4:
				break;
			}

		}
		
	};


	/**
	 * 过虑蓝牙设备
	 * 
	 * @return 蓝牙设备MAC地址
	 */
	public String filterDevice(Set<BluetoothDevice> pairedDevices) {
		String macAddress = null;
		if (null == map) {
			map = new HashMap<String, Object>();
		}
		for (BluetoothDevice device : pairedDevices) {
			Log.d("MainActivity",
					device.getName() + " - " + device.getAddress());
			if (device.getName().contains("TS-M200")) {
				// 是否己经筛选过了
//				if (null != map && !map.isEmpty()) {
//					if (null != map.get(device.getName())) {
//						continue;
//					}
//				}
				map.put(device.getName(), device.getAddress());
				macAddress = device.getAddress();
				// 检查蓝牙地址是否有效
				if (BluetoothAdapter.checkBluetoothAddress(macAddress)) {
					break;
				}
			}
		}
		return macAddress;
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		zQPrinter = new ZQPrinter();
	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (null != zQPrinter) {
//			zQPrinter.Disconnect();
			zQPrinter = null;
		}
	}

	@Override
	protected void onDestroy() {
		setResult(1100);
		mBtAdapter = null;
		map = null;
		PrintDataService.getInstance().disconnect();
		macAddress = null;
		caseInfoTxt = null;
		printBtn = null;
		super.onDestroy();
	}

}
