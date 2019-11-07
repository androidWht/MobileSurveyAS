package com.sinosoft.ms.activity.query;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.utils.component.PrintDataService;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;

/**
 * 蓝牙打印控制界面 说明： WoosimPrinter 蓝牙打印控制工具类 当中的方法： 1.BTConnection 安卓操作系统连接到蓝牙设备驱动方法
 * 2.controlCommand 命令包括空值或方法，存储在输出缓冲区的字节数组方法 3.saveSpool
 * 为了节省字节数组，送到输出bffer打印机的方法 4.printSpool
 * 通过蓝牙打印保存在输出缓冲区的数据。当模式为协议方式时，在1秒没有响应后认定为超时。
 * 如果“deletespool”参数设置为“true”，打印后删除缓冲彻底 5.closeConnection 关闭蓝牙连接方法 6.zeroBytes
 * 初始化字节数组 7.clearSpool 删除保存在数据缓冲区中的数据
 * 
 * @author linianchun
 * 
 */
public class WoosimPrinterActivity extends Activity {
	private static final String TAG = "Test";
//	private ZQPrinter PrinterService = null;
//	private boolean conn = false;

	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	private BluetoothAdapter mBtAdapter;
	private ProgressDialogUtil progressDialog;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	private String address;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		PrinterService = new ZQPrinter();

//		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_woosimprinter);
//		setResult(Activity.RESULT_CANCELED);
		// 初始化蓝牙
		init();

		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doDiscovery();
				v.setVisibility(View.GONE);
			}
		});
		Button printBtnBack = (Button)findViewById(R.id.printBtnBack);
		printBtnBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void init() {
		Intent intent = getIntent();
		boolean askConnection = intent.getBooleanExtra("askConnection", false);
		if (askConnection) {
			intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivity(intent);
		}

		// 己配对蓝牙适配器
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.item_woosimprinter_device_name);
		// 新搜索到蓝牙适配器
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.item_woosimprinter_device_name);

		// 配对蓝牙设备信息列表设置
		ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);

		// 搜索到蓝牙设备信息列表设置
		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);

		// 注册远程设备找到广播事件
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);
		// 注册蓝牙搜索完成广播事件
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		findPairedDevices();
	}
	
	private void findPairedDevices(){
		// 获取本地蓝牙适配器
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		// 获取可用蓝牙设备
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			// 将可用的蓝牙设备显示到界面
			findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
			mPairedDevicesArrayAdapter.clear();
			for (BluetoothDevice device : pairedDevices) {
				mPairedDevicesArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress());
			}
		} else {
			String noDevices = getResources().getText(R.string.none_paired)
					.toString();
			mPairedDevicesArrayAdapter.clear();
			mPairedDevicesArrayAdapter.add(noDevices);
		}
	}

	// 触发选择蓝牙列表项事件
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			try {
				mBtAdapter.cancelDiscovery();
				// 获取蓝牙MAC地址信息
				String info = ((TextView) v).getText().toString();
				address = info.substring(info.length() - 17);
				Log.i("蓝牙MAC地址:", address);
				connectToBluetooth(address);
//				print(address);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	
	private void connectToBluetooth(final String address){
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("正在连接...");
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				PrintDataService service = PrintDataService.getInstance();
				service.init(address);
				return service.connect(getApplicationContext());
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(null != progressDialog){
					progressDialog.dismiss();
				}
				if(result){
					Toast.makeText(getApplicationContext(),"连接成功！",
							Toast.LENGTH_SHORT).show();
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "连接失败！", 1).show();
				}
			}
			
		}.execute();
		
	}
	
//	private void print(final String address){
//		progressDialog = new ProgressDialogUtil(this);
//		progressDialog.show("打印中...");
//		new Thread(){
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				boolean success = true;
//				try {
//					success = printSurveyReport(address);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					success = false;
//				}
//				if(!success){
//					handler.sendEmptyMessage(0);
//				}else{
//					handler.sendEmptyMessage(0);
//				}
//			}
//			
//		}.start();
//	}
//	
//	public Handler handler = new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			if (progressDialog != null) {
//				progressDialog.dismiss();
////				progressDialog = null;
//			}
//		}
//		
//	};
//	
//
//	private boolean printSurveyReport(String address) {
//		GCUtils.execute("printSurveyReport_Start");
//		boolean result = false;
//		int nRet = PrinterService.Connect(address);
//		if (nRet == 0) {
//			conn = true;
//		}
//		System.setProperty("file.encoding", "gb2312");
//		for (int i = 0; i < 1; i++) {
//			// 打印标题
//			String title = TestData.getTitle();
//			PrinterService.PrintText(title, ZQPrinter.ALIGNMENT_CENTER,
//					ZQPrinter.FT_DEFAULT, 0);
//			PrinterService.LineFeed(1);
//
//			// 打印内容
//			String content = TestData.getContent();
//			// content = "蓝牙无电源";
//			PrinterService.PrintText(content, ZQPrinter.ALIGNMENT_LEFT,
//					ZQPrinter.FT_DEFAULT, 0);
//			PrinterService.LineFeed(1);
//
//			// 打印尾部
//			String trial = TestData.getTail(i + 1);
//			PrinterService.PrintText(trial, ZQPrinter.ALIGNMENT_CENTER,
//					ZQPrinter.FT_DEFAULT, 0);
//			PrinterService.LineFeed(4);
//		}
//		int returevlaue = PrinterService.GetStatus();
//		if (returevlaue == PrinterService.AB_SUCCESS) {
//			returevlaue = PrinterService.LineFeed(1);
//			result = true;
//		}
//		GCUtils.execute("printSurveyReport_End");
//		PrinterService.Disconnect();
//		return result;
//	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					mNewDevicesArrayAdapter.add(device.getName() + "\n"
							+ device.getAddress());
				}

			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				setTitle(R.string.select_device);
				if (mNewDevicesArrayAdapter.getCount() == 0) {
					String noDevices = getResources().getText(
							R.string.none_found).toString();
					mNewDevicesArrayAdapter.add(noDevices);
				}
			}
			findPairedDevices();
		}
	};

	/**
	 * 清理资源
	 */
	protected void onDestroy() {
		super.onDestroy();
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}
//		if (null != PrinterService) {
//			PrinterService.Disconnect();
//			PrinterService = null;
//		}
		// 终止广播
		this.unregisterReceiver(mReceiver);
	}

	private void doDiscovery() {
		
		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.scanning);
		findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		mBtAdapter.startDiscovery();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		PrinterService = new ZQPrinter();
	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		if (PrinterService != null) {
////			PrinterService.Disconnect();
//			PrinterService = null;
//		}
	}

	/**
	 * 按下事件处理
	 */
	public boolean onKeyDown(int keyConde, KeyEvent event) {
		// 返回事件处理
		if ((keyConde == KeyEvent.KEYCODE_BACK)) {

			finish();
		}
		return false;
	}
	
}

