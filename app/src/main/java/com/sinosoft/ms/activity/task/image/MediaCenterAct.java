package com.sinosoft.ms.activity.task.image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.image.camera.CameraActivity;
import com.sinosoft.ms.activity.task.loss.LossMenuAct;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.service.impl.ImageUploadService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.adapter.MediaSimpleAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ImageGallery;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.ImageCenterDatabase;

/**
 * 系统名：移动查勘 子系统名： 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午4:24:31
 */

public class MediaCenterAct extends Activity implements OnItemClickListener,
		OnClickListener {
	private boolean canEditFlag;
	private GridView mediaList;
	private Map<String, Map<String, String>> dataMap = null;

	private List<String> typeList;
	private MediaSimpleAdapter adapter;
	private Button mediaTakePictureBtn;

	private String taskId = "";
	private String registId = "";
	private RegistData taskBean;
	private String imageType;
	private List<String> listBitmap;
	private ImageGallery imageGallery;
	private RelativeLayout imageBottom;

	private Button mDirectUploadBtn, mUploadImgBtn,mediaDeleteBtn;
	private ImageButton leftArrow,rightArrow;

	private List<ImageCenterBean> imageDatas;

    private int index;//选择了第几张图片

	private List<Map<String, String>> data;
	private ImageGalleryAdapter imageAdapter;
	
	private Dialog clearDialog;
	private ImageCenterDatabase database = null;

//	private boolean isTakedPhoto = false ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_media_center);
		ActivityManage.getInstance().push(this);
		dataMap = DictCashe.getInstant().getDict();
		Intent intent = getIntent();
		canEditFlag = intent.getBooleanExtra("canEdit", true);
		Bundle bundle = intent.getBundleExtra("item");
		taskId=intent.getStringExtra("registNo");
		registId = intent.getStringExtra("registId");
		if (bundle != null) {
			taskBean = (RegistData) bundle.getSerializable("bean");
		}
		if (taskBean == null) {
			taskBean = new RegistData();
			taskBean.init();
		} 
		if(StringUtils.isEmpty(taskId)){
			taskId="";
		}
		setViewController();
		
		initData();
	}

	/**
	 * 初始化视图
	 */
	public void setViewController() {
		mediaList = (GridView) findViewById(R.id.mediaList);
		mediaTakePictureBtn = (Button) findViewById(R.id.mediaTakePictureBtn);
		imageGallery = (ImageGallery) findViewById(R.id.mediaImageGallery);
		imageBottom = (RelativeLayout) findViewById(R.id.media_gallery_bottom);
		//左右箭头
		leftArrow = (ImageButton)findViewById(R.id.leftArrowBtn);
		rightArrow = (ImageButton)findViewById(R.id.rightArrowBtn);

		mUploadImgBtn = (Button) findViewById(R.id.mUploadImgBtn);
		mediaDeleteBtn= (Button) findViewById(R.id.mediaDeleteBtn);
		mDirectUploadBtn  = (Button) findViewById(R.id.directUploadBtn);
		mUploadImgBtn.setOnClickListener(this);
		mediaDeleteBtn.setOnClickListener(this);
		mDirectUploadBtn.setOnClickListener(this);
		leftArrow.setOnClickListener(this);
		rightArrow.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		database = new ImageCenterDatabase(
				MediaCenterAct.this);
		// 案件信息
		mDirectUploadBtn.setOnClickListener(this);

		Map<String, String> listData = dataMap.get("DocData");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		typeList = new ArrayList<String>();

		if (listData != null) {
			Iterator<String> iterator = listData.keySet().iterator();
			while (iterator.hasNext()) {
				String code = iterator.next();
				String values = listData.get(code);
				typeList.add(code);
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", values);
				list.add(map);
			}
		}
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("type","现场示意图");
//		list.add(map);
		adapter = new MediaSimpleAdapter(this, list, R.layout.item_media_type,
				new String[] { "type" }, new int[] { R.id.media_item_type },
				new int[] {}, null);
		mediaList.setAdapter(adapter);
		MediaSimpleAdapter.selectItem = -1;
		mediaList.setOnItemClickListener(this);
		mediaTakePictureBtn.setOnClickListener(this);
		data = new ArrayList<Map<String, String>>();
		imageAdapter = new ImageGalleryAdapter(this, data,
				R.layout.item_media_img, new String[] { "image" },
				new int[] { R.id.mediaImage });

		imageGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				LinearLayout linear = (LinearLayout) imageBottom.getChildAt(0);
				index = position;
				for (int i = 0; i < linear.getChildCount(); i++) {
					ImageView v = (ImageView) linear.getChildAt(i);
					if (position == i) {
						v.setImageResource(R.drawable.greenicon);
					} else {
						v.setImageResource(R.drawable.grayicon);
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		imageGallery.setAdapter(imageAdapter);
		listImage();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		listImage();
	}

	public void listImage() {
		listBitmap = new ArrayList<String>();
		try {
			imageDatas = database.select("imageCenter",
					"reportNo=? and type=?",
					new String[] { taskId, imageType }, "createDate");
			if(imageDatas==null||imageDatas.isEmpty()){
				//throw new IllegalArgumentException("没有可上传的图片");
			}
			ImageCenterBean bean = null;
			for (int i = 0; i < imageDatas.size(); i++) {
				bean = imageDatas.get(i);
				listBitmap.add(bean.getPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadImage();
	}

	public void loadImage() {
		// ImageAdapter adapter = new ImageAdapter(this);
		index=0;
		data.clear();
		int size = listBitmap.size();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < size; i++) {
			map.put("image", listBitmap.get(i));
			data.add(map);
		}

		LinearLayout linear = new LinearLayout(this);
		
		for (int i = 0; i < imageAdapter.getCount(); i++) {
			ImageView img = new ImageView(this);
			LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			if (i == 0) {
				img.setImageResource(R.drawable.greenicon);
			} else {
				img.setImageResource(R.drawable.grayicon);
			}
			linear.addView(img, params);
		}
		imageBottom.removeAllViews();
		imageBottom.addView(linear);
		imageAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MediaSimpleAdapter.selectItem = position;
		adapter.notifyDataSetChanged();
//		if (position < adapter.getCount() - 1) {
			imageType = typeList.get(position);
//		} else {
//			imageType = "9999";
//		}
		listImage();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mediaTakePictureBtn:
			if (canEditFlag) {
				if (MediaSimpleAdapter.selectItem >= 0) {
//					if (MediaSimpleAdapter.selectItem < adapter.getCount() - 1) {
						Intent intent2 = new Intent();
						intent2.setClass(MediaCenterAct.this,
								CameraActivity.class);
						intent2.putExtra("registId", registId);
						intent2.putExtra("taskId", taskId);
						intent2.putExtra("imageType", imageType);
						intent2.putExtra("num", 0);
						intent2.putExtra("total", 12);
						intent2.putExtra("reportNo", taskId);
						startActivityForResult(intent2, 1231);
//					} else {
//						Intent intent = new Intent();
//						intent.setClass(MediaCenterAct.this,
//								PaintImageActivity.class);
//						intent.putExtra("imageType", imageType);
//
//						intent.putExtra("reportNo", registNo);
//						startActivityForResult(intent, 1234);
//					}
				} else {
					ToastDialog.show(this, "请先选择拍照类型", ToastDialog.ERROR);
				}
			} else {
				CustomDialog.show(MediaCenterAct.this, "信息提示",
						"该案件已经归档，不能实现拍照操作");
			}

			break;
		case R.id.mUploadImgBtn:
			if (null == imageType || imageType.equals("")) {
				CustomDialog.show(MediaCenterAct.this, "信息提示", "请先选中照片类型");
			} else {
				if (canEditFlag) {
					Intent intent = new Intent(MediaCenterAct.this,
							UploadImageActivity.class);
					intent.putExtra("registNo", taskId);
					intent.putExtra("registId", registId);
					intent.putExtra("taskId", taskId);
					intent.putExtra("imageType", imageType);
//					startActivityForResult(intent, 1232);
					startActivity(intent);
				}else {
					CustomDialog.show(MediaCenterAct.this, "信息提示",
							"该案件已经归档，不能实现上传操作");
				}
			}
			break;
		case R.id.mediaDeleteBtn:
			if (canEditFlag) {
				deleteImage();
			} else {
				CustomDialog.show(MediaCenterAct.this, "信息提示",
						"该案件已经归档，不能实现删除操作");
			}
			break;
		case R.id.directUploadBtn:
			ImageCenterDatabase database = new ImageCenterDatabase(MediaCenterAct.this);
			List<ImageCenterBean> list = database.selectNotUpload(taskId);
			if(null == list || list.isEmpty()){
				ToastDialog.show(MediaCenterAct.this, "当前没有图片或没有需要上传的图片") ;
				return ;
			}
/*			if(isTakedPhoto){
				//  禁止拍照
				ToastDialog.show(this, "当前没有图片或没有需要上传的图片") ;
				return ;
			}*/
//			if (null == imageType || imageType.equals("")) {
//				CustomDialog.show(MediaCenterAct.this, "信息提示", "请先选中照片类型");
//			} else {
				if (canEditFlag) {
					try {
						clearDialog = CustomDialog.show(MediaCenterAct.this,
								"信息提示", "是否上传所有图片?", new OnClickListener() {
									@Override
									public void onClick(View v) {
										clearDialog.dismiss();
										Intent intent = new Intent(MediaCenterAct.this,ImageUploadService.class);
										intent.putExtra("registNo", taskId);
										startService(intent);
									}
								}, new OnClickListener() {
									@Override
									public void onClick(View v) {
										clearDialog.dismiss();
									}
								});
					} catch (Exception e) {
						e.printStackTrace();
						ToastDialog.show(MediaCenterAct.this,
								"图片上传错：" + e.getMessage(), ToastDialog.ERROR);
					}
				} else {
					CustomDialog.show(MediaCenterAct.this, "信息提示",
							"该案件已经归档，不能实现上传操作");
				}
//			}
			break;
		case R.id.leftArrowBtn:
			try {
				index--;
				if(index >= 0)
					imageGallery.setSelection(index);
				else{
					index++;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case R.id.rightArrowBtn:
			try {
				index++;
				if(index < imageDatas.size())
					imageGallery.setSelection(index);
				else{
					index--;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		}
	}

	/**
	 * 图片删除事件
	 */
	public void deleteImage() {
		if (imageDatas != null && !imageDatas.isEmpty()) {
			try {
				ImageCenterBean image = imageDatas.get(index);
				File file = new File(image.getPath());

				if (image.getPath().contains("finger")) {
					file.delete();
				}
//				if (file.delete()) {
					ImageCenterDatabase database = new ImageCenterDatabase(
							MediaCenterAct.this);
					database.delete("imageCenter",
							"reportNo=? and type=? and id=?", new String[] {
							taskId, image.getType(), image.getId() });
					listBitmap.remove(index);
					imageDatas.remove(index);
					loadImage();
					imageAdapter.notifyDataSetChanged();
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			CustomDialog.show(MediaCenterAct.this, "信息提示", "没有可以删除的图片");
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		listImage();
	}

	class ImageGalleryAdapter extends SimpleAdapter {
		private Context context;
		private int resource;

		public ImageGalleryAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.resource = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView == null) {
				view = View.inflate(context, resource, null);
				ImageView imageView = (ImageView) view
						.findViewById(R.id.mediaImage);
				imageView.setBackgroundColor(0x00bbaa);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bitmap = BitmapFactory.decodeFile(
						listBitmap.get(position), options);
				imageView.setImageBitmap(bitmap);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams(new Gallery.LayoutParams(
						Gallery.LayoutParams.FILL_PARENT,
						Gallery.LayoutParams.FILL_PARENT));
				convertView = imageView;
			}
			return convertView;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaList = null;
		
		if(typeList!=null){
			typeList.clear();
			typeList = null;
		}
		MediaSimpleAdapter.selectItem = -1;
		adapter = null;
		mediaTakePictureBtn = null;
		taskId = null;
		taskBean = null;
		imageType = null;
		listBitmap = null;
		imageGallery = null;
		imageBottom = null;
		mUploadImgBtn = null;
		mediaDeleteBtn = null;
		if (imageDatas != null) {
			imageDatas.clear();
			imageDatas = null;
		}
		if (data != null) {
			data.clear();
			data = null;
		}
		imageAdapter = null;     
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		try {
			this.finish();
			ActivityManage.getInstance().pop() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
