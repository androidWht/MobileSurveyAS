package com.sinosoft.ms.activity.task.image;

/**
 * 系统名：移动查勘定损 
 * 子系统名：本地图片上传
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author maya
 * @createTime 上午10:20:58
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.image.camera.Preview;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.model.LocalImgCache;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.BitmapUtil;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.adapter.GridImageAdapter;
import com.sinosoft.ms.utils.component.BitmapUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.db.ImageCenterDatabase;

public class UploadImageActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private Button mUploadBtn = null;
	private Button mBackBtn = null;// 返回按钮
	private Button homeDirBtn = null;// 根目录按钮
	private Intent intent = null;
	private LinearLayout currDirLayout = null;// 当前目录显示区
	private ArrayList<Map<String, Object>> mData = null;
	private ArrayList<Map<String, Object>> imgData = null;
	private String currentDir = null;// 记录当前目录
	private ListView dirListView = null;// 目录列表
	private GridView mGridView = null;// 内容显示gridview
	private String mDir = "/sdcard";
	private GridImageAdapter mImageAdapter;// 存储图片源的适配器(多选)
	private String imageType = null;
	private String registNo = "";
	private String registId = "";
	private String taskId = "";
	private Dialog mDialog = null;
	private MyAdapter adapter = null;
	private ProgressDialog dialog = null;
	private boolean haveNotSelectedPicture = false;
	private Dialog tipDialog;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (null != dialog && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (haveNotSelectedPicture) {
				tipDialog = CustomDialog.show(UploadImageActivity.this, "信息提示",
						"请选择图片", "确定", "", new OnClickListener() {
							public void onClick(View v) {
								tipDialog.dismiss();
							}
						}, null);
			} else {
				UploadImageActivity.this.finish();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_upload);

		intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		taskId = intent.getStringExtra("taskId");
		registId = intent.getStringExtra("registId");
		imageType = intent.getStringExtra("imageType");
		// 初始化目录
		getData0();
		currDirLayout = (LinearLayout) findViewById(R.id.currDirLayout);
		dirListView = (ListView) findViewById(R.id.dirListLv);
		adapter = new MyAdapter(this);
		dirListView.setAdapter(adapter);
		// 初始化图片
		// imgData = getImgData("/sdcard");
		mGridView = (GridView) findViewById(R.id.imageGv);
		mImageAdapter = new GridImageAdapter(this, imgData);
		mGridView.setAdapter(mImageAdapter);
		// 设置点击监听
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String pathName = imgData.get(position).get("itemImage")
						.toString();
				File file = new File(pathName);
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "image/*");
				startActivity(intent);
			}
		});

		dirListView.setOnItemClickListener(this);
		homeDirBtn = (Button) findViewById(R.id.homeDirBtn);
		homeDirBtn.setOnClickListener(new HomeDirClick());
		mBackBtn = (Button) findViewById(R.id.backBtn);
		mBackBtn.setOnClickListener(this);
		mUploadBtn = (Button) findViewById(R.id.uploadBtn);
		mUploadBtn.setOnClickListener(new localUpload());
		LocalImgCache.getInstance().getImgList().clear();
	}

	private void longTimeMethod() {
		List<String> strList = LocalImgCache.getInstance().getImgList();
		if (strList.size() == 0) {
			haveNotSelectedPicture = true;
			return;
		} else {
			haveNotSelectedPicture = false;
		}
		ImageCenterDatabase database = new ImageCenterDatabase(
				UploadImageActivity.this);
		try {
			List<ImageCenterBean> imageDatas = database.select("imageCenter",
					"reportNo=? and isUpload=? and type=?", new String[] {
							registNo, "0", imageType }, "createDate");
			for (String path : strList) {
				// 判断图片是否已选择过 start
				boolean mark = false;
				for (int i = 0; i < imageDatas.size(); i++) {
					ImageCenterBean bean = imageDatas.get(i);
					if (path.equals(bean.getPath())) {
						mark = true;
						break;
					}
				}
				if (mark) {
					continue;
				}
				// end
				File tempFile = new File(path);

				String filename = tempFile.getName().trim();
				int location = filename.indexOf(".");
				filename = filename.substring(0, location);
				
				location = filename.indexOf("_");
				filename = filename.substring(location + 1);

				if (!filename.equals("1")) {

					tempFile = new File(saveToSDCard(path));
				}

				ImageCenterBean bean = new ImageCenterBean();
				bean.setPath(tempFile.getAbsolutePath());
				bean.setReportNo(registNo);
				bean.setRegistId(registId);
				Time t = new Time();
				t.setToNow();
				bean.setCreateDate("" + t.year + "-" + (t.month + 1) + "-"
						+ t.monthDay);
				bean.setImgName(tempFile.getName().toString());
				
				bean.setImgSize(tempFile.length() / 1024 + "kb");
				bean.setIsUpload("0");
				bean.setLocation("广州");
				bean.setType(imageType);
				bean.setTaskId(taskId);
				database.insert("imageCenter", bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// finish();
		}
	}

	private class localUpload implements OnClickListener {
		@Override
		public void onClick(View v) {
			dialog = ProgressDialog.show(UploadImageActivity.this, "请稍候......",
					"");
			new Thread() {
				public void run() {
					longTimeMethod();
					handler.sendEmptyMessage(0);
				}
			}.start();
		}
	}

	public String saveToSDCard(String path) {
		Bitmap bmp = BitmapUtil.getImageThumbnail(path, 640, 480);
		// BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// newOpts.inSampleSize = 1;
		// newOpts.inJustDecodeBounds = false;
		// Bitmap bmp = BitmapFactory.decodeFile(path, newOpts);
		int w = bmp.getWidth(); // 得到图片的宽，高
		int h = bmp.getHeight();
		// if(w>h){
		// bmp = PictureUtil.rotate(bmp, 90);
		// w = bmp.getWidth();
		// h = bmp.getHeight();
		// }

		// Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, w, h);
		// 加水印
		User user = UserCashe.getInstant().getUser();
		bmp = new BitmapUtils().createBitmap(bmp, getResources(),
				R.drawable.image_logo, 1, user.getName(), null, DateTimeFactory
						.getInstance().getDateTime());

		// if (w > 1024 || h > 1024) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS"); // 格式化时间
		String filename = format.format(date) + AppConstants.IMAGE_FORMAT;
		File fileFolder = new File(Environment.getExternalStorageDirectory()
				+ "/finger/");
		if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
			fileFolder.mkdir();
		}
		File jpgFile = new File(fileFolder, filename);
		// File jpgFile = new File(path);
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(jpgFile);
			bmp.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
			outputStream.flush();
			outputStream.close(); // 关闭输出流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// }
		return jpgFile.getAbsolutePath();
	}

	/**
	 * 给图片添加水印(新修改)
	 * 
	 * @param src
	 *            图片源
	 * @param res
	 *            系统资源
	 * @param id
	 *            LOGO图片ID
	 * @param type
	 *            图片类型 1.移动查勘拍照 2.本地上传
	 * @param surveyNo
	 *            查勘员编号
	 * @return 添加水印后内容
	 */
	/*
	 * public Bitmap createBitmap(Bitmap src, Resources res, int id, int type,
	 * String surveyNo) { int w = src.getWidth(); int h = src.getHeight();
	 * Bitmap bmpTemp = Bitmap.createBitmap(w, h, Config.RGB_565); Canvas canvas
	 * = new Canvas(bmpTemp); Paint p = new Paint(); // 绘入源图片
	 * canvas.drawBitmap(src, 0, 0, p); // 绘入LOGO Bitmap logo =
	 * BitmapFactory.decodeResource(res, id);
	 * 
	 * canvas.drawBitmap(logo, w - 320, h - 65, p);
	 * 
	 * // 设置字体 Typeface font = Typeface.create("宋体", Typeface.BOLD_ITALIC);
	 * p.setColor(Color.RED); p.setTypeface(font); p.setTextSize(18); // 添加时间
	 * canvas.drawText("TIME:" + getTime(), w - 260, h - 50, p);
	 * 
	 * // 添加工号 canvas.drawText("MOBILE-" + (1 == type ? "SURVEY" : "LOCAL") +
	 * ": " + surveyNo, w - 260, h - 20, p);
	 * 
	 * // 保存新图片 canvas.save(Canvas.ALL_SAVE_FLAG); canvas.restore(); return
	 * bmpTemp; }
	 */

	/**
	 * 获取水印时间
	 * 
	 * @return 水印时间
	 */
	/*
	 * private String getTime() { Time t = new Time(); t.setToNow(); return
	 * t.year + "-" + (t.month + 1) + "-" + t.monthDay + " " + t.hour + ":" +
	 * t.minute + ":" + t.second; }
	 */

	/**
	 * 
	 */
	private void getData0() {
		mData = new ArrayList<Map<String, Object>>();// 文件夹
		imgData = new ArrayList<Map<String, Object>>();// 图片文件
		Map<String, Object> map = null;
		ArrayList<String> dirStrs = new ArrayList<String>();
		File[] files = null;

		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;

				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						dirStrs.add(columns[1]);
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						dirStrs.add(columns[1]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File fileFolder1 = new File(Environment.getExternalStorageDirectory()
				+ "/finger/");
		if (!fileFolder1.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
			fileFolder1.mkdir();
		}
		if (fileFolder1.isDirectory()) {
			files = fileFolder1.listFiles();

		}
		File fileTemp = null;
		for (int i = 0; i < files.length; i++) {
			fileTemp = files[i];
			if (fileTemp.isDirectory()) {// 按不同类型显示不同图标
				if (!fileTemp.getName().startsWith(".")) {
					map = new HashMap<String, Object>();
					map.put("title", fileTemp.getName());
					map.put("info", fileTemp.getPath());
					map.put("img", R.drawable.file_img);// 文件夹
					mData.add(map);
				}
			} else {
				if (checkImage(fileTemp)) {
					map = new HashMap<String, Object>();
					map.put("itemImage", fileTemp.getPath());
					imgData.add(map);
				}
			}
		}
	}

	private void getData() { // 将目录数据填充到链表中
		if (mData == null) {
			mData = new ArrayList<Map<String, Object>>();// 文件夹
		}
		if (imgData == null) {
			imgData = new ArrayList<Map<String, Object>>();// 图片文件
		}
		Map<String, Object> map = null;
		File f = new File(mDir); // 打开当前目录
		File[] files = f.listFiles(); // 获取当前目录中文件列表
		if (files != null) { // 将目录中文件填加到列表中
			int length = files.length;
			for (int i = 0; i < length; i++) {
				if (files[i].isDirectory()) {// 按不同类型显示不同图标
					if (!files[i].getName().startsWith(".")) {
						map = new HashMap<String, Object>();
						map.put("title", files[i].getName());
						map.put("info", files[i].getPath());
						map.put("img", R.drawable.file_img);// 文件夹
						mData.add(map);
					}
				} else {
					if (checkImage(files[i])) {
						map = new HashMap<String, Object>();
						map.put("itemImage", files[i].getPath());
						imgData.add(map);
					}
				}
			}
		}
	}

	/**
	 * 检查是否为图片文件
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	private boolean checkImage(File f) {
		boolean result = false;
		String path = f.getAbsolutePath();
		if (path.endsWith(".png") || path.endsWith(".jpg")
				|| path.endsWith(".jpeg") || path.endsWith(".bmp")
				|| path.endsWith(".gif") || path.endsWith(".PNG")
				|| path.endsWith(".JPG") || path.endsWith(".JPEG")
				|| path.endsWith(".BMP") || path.endsWith(".GIF")) {
			result = true;
		}
		return result;
	}

	public final class ViewHolder { // 定义每个列表项所含内容
		public ImageView img; // 显示图片ID
		public TextView title; // 文件目录名
	}

	public class MyAdapter extends BaseAdapter { // 实现列表内容适配器
		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		public int getCount() { // 获取列表项个数
			return mData.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		// 设置每个列表项的显示
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater
						.inflate(R.layout.item_uploadimage, null); // 设置列表项的布局
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.img.setBackgroundResource((Integer) mData.get(position).get(
					"img")); // 根据位置position设置具体内容
			holder.title.setText((String) mData.get(position).get("title"));
			holder.title.setTag((String) mData.get(position).get("info"));
			return convertView;
		}
	}

	/**
	 * 做两件事情 1.生成当前文件目录 2.获取当前文件图片做显示
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		String title = (String) mData.get(position).get("title");
		String info = (String) mData.get(position).get("info");
		setDirListView(info);

		Button child = new Button(this);
		child.setText(title);
		child.setTag(mDir);
		child.setOnClickListener(new DirClick());
		// if (getData() != null) {
		TextView arrow = new TextView(this);
		arrow.setText(">>");
		currDirLayout.addView(arrow);
		currDirLayout.addView(child);
		currentDir = mDir;// 设置为选择的最新目录
		// }
	}

	/**
	 * 检查是否有子目录
	 * 
	 * @param mDir
	 *            目录
	 * @return true有子目录 false 无子目录
	 */
	private boolean checkSubDir(String mDir) {
		boolean isSubDir = false;
		File file = new File(mDir);
		File[] fs = file.listFiles();
		int length = fs.length;
		for (int i = 0; i < length; i++) {
			File f = fs[i];
			if (f.isDirectory()) {
				isSubDir = true;
			}
		}
		return isSubDir;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backBtn:
			// mDialog = CustomDialog.show(UploadImageActivity.this, "信息提示",
			// "请确认是否己上传所有影像图片?", new OnClickListener() {
			// public void onClick(View v) {
			// finish();
			// mDialog.dismiss();
			// }
			// }, new OnClickListener() {
			// public void onClick(View v) {
			// mDialog.dismiss();
			// }
			// });
			// setResult(RESULT_OK);
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 回根目录
	 * 
	 * @author linianchun
	 * 
	 */
	private class HomeDirClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			// setDirListView("/sdcard");
			mData = null;
			getData0();
			adapter.notifyDataSetChanged();
			currDirLayout.removeAllViews();
		}
	}

	private class DirClick implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			if (arg0 instanceof Button) {
				String text = ((Button) arg0).getText().toString();// 目录名
				String tag = ((Button) arg0).getTag().toString();// 目录地址
				int index = currentDir.indexOf(text);
				if (-1 != index) {// 判断是否有这一级目录
					String str = currentDir.substring(index + text.length());// 获取到要去除的文件名
					String[] dirs = str.split("/");
					int childCount = currDirLayout.getChildCount();
					for (int i = 0; i < dirs.length; i++) {
						if (null == dirs[i] || "".equals(dirs[i])) {
							continue;
						}
						for (int j = childCount; j > 0; j--) {// 从后往前检查
							View child = currDirLayout.getChildAt(j);// 防止下标越界
							if (child instanceof Button) {
								Button btn = (Button) child;
								if (btn.getText().toString().equals(dirs[i])) {
									currDirLayout.removeViewAt(j);
									currDirLayout.removeViewAt(j - 1);
									childCount = childCount - 2;
									break;
								}
							}
						}
					}
				}
				setDirListView(tag);
			}
		}
	}

	public void setDirListView(String path) {
		mDir = path;
		mData = null;
		imgData = null;
		getData(); // 点击目录时进入子目录
		adapter.notifyDataSetChanged();

		if (imgData != null) {
			mImageAdapter = new GridImageAdapter(this, imgData);
			mGridView.setAdapter(mImageAdapter);
		}
		// 设置点击监听
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String pathName = imgData.get(position).get("itemImage")
						.toString();
				File file = new File(pathName);
				if (null != file && file.exists()) {
					Intent intent = new Intent();
					intent.setAction(android.content.Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), "image/*");
					startActivity(intent);
				}
				// Bitmap bitmap = BitmapFactory.decodeFile(pathName);
				// ImageView imageView = new
				// ImageView(UploadImageActivity.this);
				// imageView.setImageBitmap(bitmap);// 显示图片
				// new AlertDialog.Builder(UploadImageActivity.this)
				// .setTitle("图片预览").setView(imageView)
				// .setPositiveButton("关闭", null).show();
			}
		});
	}
}
