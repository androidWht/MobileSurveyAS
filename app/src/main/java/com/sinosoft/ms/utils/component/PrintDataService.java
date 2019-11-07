package com.sinosoft.ms.utils.component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * 系统名：移动查勘定损 
 * 子系统名:蓝牙打印
 * 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 4:04:38 PM
 */
public class PrintDataService {
	private static PrintDataService instance;
	
//	private Context context = null;
	private String deviceAddress = null;
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();
	private BluetoothDevice device = null;
	private static BluetoothSocket bluetoothSocket = null;
	private static OutputStream outputStream = null;
	private static final UUID uuid = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private boolean isConnection = false;
	final String[] items = { "复位打印机", "标准ASCII字体", "压缩ASCII字体", "字体不放大",
			"宽高加倍", "取消加粗模式", "选择加粗模式", "取消倒置打印", "选择倒置打印", "取消黑白反显", "选择黑白反显",
			"取消顺时针旋转90°", "选择顺时针旋转90°" };
	final byte[][] byteCommands = { { 0x1b, 0x40 },// 复位打印机
			{ 0x1b, 0x4d, 0x00 },// 标准ASCII字体
			{ 0x1b, 0x4d, 0x01 },// 压缩ASCII字体
			{ 0x1d, 0x21, 0x00 },// 字体不放大
			{ 0x1d, 0x21, 0x11 },// 宽高加倍
			{ 0x1b, 0x45, 0x00 },// 取消加粗模式
			{ 0x1b, 0x45, 0x01 },// 选择加粗模式
			{ 0x1b, 0x7b, 0x00 },// 取消倒置打印
			{ 0x1b, 0x7b, 0x01 },// 选择倒置打印
			{ 0x1d, 0x42, 0x00 },// 取消黑白反显
			{ 0x1d, 0x42, 0x01 },// 选择黑白反显
			{ 0x1b, 0x56, 0x00 },// 取消顺时针旋转90°
			{ 0x1b, 0x56, 0x01 },// 选择顺时针旋转90°
	};

//	private PrintDataService(Context context, String deviceAddress) {
//		super();
//		this.context = context;
//		this.deviceAddress = deviceAddress;
//		this.device = this.bluetoothAdapter.getRemoteDevice(this.deviceAddress);
//	}
	
	public PrintDataService(){
		super();
	}
	
	public static PrintDataService getInstance(){
		if(instance == null){
			instance = new PrintDataService();
		}
		return instance;
	}

	public void init(String deviceAddress){
		this.deviceAddress = deviceAddress;
		this.device = this.bluetoothAdapter.getRemoteDevice(deviceAddress);
		isConnection = false;
	}
	
	public boolean getIsConnected(){
		return isConnection;
	}
	
	
	/**
	 * 获取设备名称
	 * 
	 * @return String
	 */
	public String getDeviceName() {
		return this.device.getName();
	}

	/**
	 * 连接蓝牙设备
	 */
	public boolean connect(Context context) {
		if (!this.isConnection) {
			try {
				bluetoothSocket = this.device
						.createRfcommSocketToServiceRecord(uuid);
				bluetoothSocket.connect();
				outputStream = bluetoothSocket.getOutputStream();
				this.isConnection = true;
				if (this.bluetoothAdapter.isDiscovering()) {
					System.out.println("关闭适配器！");
					this.bluetoothAdapter.isDiscovering();
				}
			} catch (Exception e) {
				e.printStackTrace();
//				Toast.makeText(context, "连接失败！", 1).show();
				return false;
			}
//			Toast.makeText(context, this.device.getName() + "连接成功！",
//					Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return true;
		}
	}

	/**
	 * 断开蓝牙设备连接
	 */
	public void disconnect() {
		System.out.println("断开蓝牙设备连接");
		try {
			instance = null;
			isConnection = false;
			if(null != bluetoothSocket){
				bluetoothSocket.close();
			}
			if(null != outputStream){
				outputStream.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 选择指令
	 */
	public void selectCommand(final Context context) {
		new AlertDialog.Builder(context).setTitle("请选择指令")
				.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (isConnection) {
							try {
								outputStream.write(byteCommands[which]);

							} catch (IOException e) {
								Toast.makeText(context, "设置指令失败！",
										Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(context, "设备未连接，请重新连接！",
									Toast.LENGTH_SHORT).show();
						}
					}
				}).create().show();
	}

	/**
	 * 发送数据
	 */
	public void send(Context context,String sendData) {
		if (this.isConnection) {
			System.out.println("开始打印！！");
			try {
				byte[] data = sendData.getBytes("gbk");
				outputStream.write(data, 0, data.length);
				outputStream.flush();
			} catch (IOException e) {
				Toast.makeText(context, "发送失败！", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(context, "设备未连接，请重新连接！", Toast.LENGTH_SHORT)
					.show();

		}
	}

}
