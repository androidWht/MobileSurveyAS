package com.sinosoft.ms.activity.task.survey.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.image.PictureGalleryActivity;
import com.sinosoft.ms.activity.task.image.UploadImageActivity;
import com.sinosoft.ms.activity.task.image.camera.CameraActivity;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.po.CacheImage;
import com.sinosoft.ms.model.po.ImageBean;
import com.sinosoft.ms.service.impl.ImageUploadService;
import com.sinosoft.ms.utils.adapter.MediaSimpleAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ImageGallery;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.ImageCenterDatabase;

/**
 * 系统名：移动查勘定损 子系统名：2.影像中心页面 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 下午4:22:28
 */

public class SurveyMediaCenterFrag extends Fragment implements OnClickListener, OnItemClickListener, OnItemSelectedListener
{
	private static final int PHOTOGRAPH_CODE = 1231;

	// 影像上传、本地图片添加、删除图片按钮
	private Button mDirectUploadBtn, mUploadImgBtn, mediaDeleteBtn;
	// 左右箭头，对应上一张，下一张图片
	private ImageButton leftArrowBtn, rightArrowBtn;
	// 图片类型列表
	private GridView mediaList;
	// 拍照按钮
	private Button mediaTakePictureBtn;
	// 图片列表
	private ImageGallery imageGallery;
	private RelativeLayout imageBottom;

	// 对话框
	private Dialog dialog;
	private ProgressDialogUtil progressDialog;

	private ImageCenterDatabase imageDatabase;
	// 图片类型代码列表
	private List<String> typeList;
	private MediaSimpleAdapter mediadAdapter;
	private List<Map<String, String>> mediaData;
	private ImageGalleryAdapter imageAdapter;

	// 当前类型下的图片列表
	private List<String> listBitmap;
	private List<ImageCenterBean> imageDatas;
	// 图片类型
	private String imageType;
	// 图片类型下标
	private int typeIndex = 1;
	// 图片下标
	private int index;

	// 报案信息
	private RegistData registData;
	private Map<String, Map<String, String>> dataMap;// 数据字典集合
	// 任务号
	private String taskId;
	// 报案号
	private String registNo;
	// 报案ID
	private String registId;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dataMap = DictCashe.getInstant().getDict();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.layout_media_center, container, false);
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
	}

	// 影像中心控件
	public void initView(View cameraView)
	{
		mediaList = (GridView) cameraView.findViewById(R.id.mediaList);
		mediaTakePictureBtn = (Button) cameraView.findViewById(R.id.mediaTakePictureBtn);
		imageGallery = (ImageGallery) cameraView.findViewById(R.id.mediaImageGallery);

		// imageGallery.startAnimation(RotateAnimation) ;

		imageBottom = (RelativeLayout) cameraView.findViewById(R.id.media_gallery_bottom);

		// 本地上传
		mUploadImgBtn = (Button) cameraView.findViewById(R.id.mUploadImgBtn);
		mediaDeleteBtn = (Button) cameraView.findViewById(R.id.mediaDeleteBtn);
		// 影像上传
		mDirectUploadBtn = (Button) cameraView.findViewById(R.id.directUploadBtn);

		leftArrowBtn = (ImageButton) cameraView.findViewById(R.id.leftArrowBtn);
		rightArrowBtn = (ImageButton) cameraView.findViewById(R.id.rightArrowBtn);

		// 设置监听
		mUploadImgBtn.setOnClickListener(this);
		mediaDeleteBtn.setOnClickListener(this);
		mDirectUploadBtn.setOnClickListener(this);
		leftArrowBtn.setOnClickListener(this);
		rightArrowBtn.setOnClickListener(this);

		mDirectUploadBtn.setOnClickListener(this);
		mediaList.setOnItemClickListener(this);
		mediaTakePictureBtn.setOnClickListener(this);
		// 图片预览
		imageGallery.setOnItemClickListener(this);
		imageGallery.setOnItemSelectedListener(this);
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mediadAdapter != null)
		{
			mediadAdapter.selectItem = -1;
		}
	}

	public void refreshData(RegistData registData, String registNo, String registId)
	{
		this.registData = registData;
		this.registNo = registNo;
		this.registId = registId;
		initMediaCenterData();

	}

	private void initData()
	{
		if (null != registData && null != registNo)
		{
			initMediaCenterData();
		}
	}

	// 设置影像中心信息
	public void initMediaCenterData()
	{
		imageDatabase = new ImageCenterDatabase(getActivity());
		// 案件信息
		taskId = registData.getTaskId();

		// 初始化图片类型列表
		List<Map<String, String>> list = initTypeList();
		// 初始化图片类型列表
		mediadAdapter = new MediaSimpleAdapter(getActivity(), list, R.layout.item_media_type, new String[]
		{ "type" }, new int[]
		{ R.id.media_item_type }, new int[]
		{}, null);
		mediaList.setAdapter(mediadAdapter);
		mediaList.setSelection(typeIndex);
		// MediaSimpleAdapter.selectItem = typeIndex;

		mediaData = new ArrayList<Map<String, String>>();
		imageAdapter = new ImageGalleryAdapter(getActivity(), mediaData, R.layout.item_media_img, new String[]
		{ "image" }, new int[]
		{ R.id.mediaImage });

		imageGallery.setAdapter(imageAdapter);
		getImageListByType(registNo, imageType);
	}

	private List<Map<String, String>> initTypeList()
	{
		Map<String, String> listData = dataMap.get("DocData");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		typeList = new ArrayList<String>();

		if (listData != null)
		{
			Iterator<String> iterator = listData.keySet().iterator();
			while (iterator.hasNext())
			{
				String code = iterator.next();
				String values = listData.get(code);
				typeList.add(code);
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", values);
				list.add(map);
			}
		}
		return list;
	}

	public void getImages()
	{
		listBitmap = new ArrayList<String>();
		try
		{
			imageDatas = imageDatabase.select("imageCenter", "reportNo=? and type=?", new String[]
			{ registNo, imageType }, "createDate");
			if (imageDatas == null || imageDatas.isEmpty())
			{
				// throw new IllegalArgumentException("没有可上传的图片");
			}
			ImageCenterBean bean = null;
			for (int i = 0; i < imageDatas.size(); i++)
			{
				bean = imageDatas.get(i);
				listBitmap.add(bean.getPath());
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	private void scrollToImage(int position)
	{
		LinearLayout linear = (LinearLayout) imageBottom.getChildAt(0);
		index = position;
		for (int i = 0; i < linear.getChildCount(); i++)
		{
			ImageView v = (ImageView) linear.getChildAt(i);
			if (position == i)
			{
				v.setImageResource(R.drawable.greenicon);
			}
			else
			{
				v.setImageResource(R.drawable.grayicon);
			}
		}
	}

	Handler mediaHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what)
			{
			case 0:
				imageGallery.setSelection(0);
				mediadAdapter.notifyDataSetChanged();
				break;
			case 1:
				getImageListByType(registNo, imageType);
				progressDialog.dismiss();
				break;
			case 2:
				progressDialog.dismiss();
				showImages();
				break;
			}
		}

	};

	// 在线程中获取当前类型下的图片列表
	public void getImageListByType(String registNo, String imageType)
	{
		imageDatabase.asyncSelectByType(registNo, imageType, new ImageCenterDatabase.DatabaseCallback()
		{

			@Override
			public void post(List<ImageCenterBean> list)
			{
				// TODO Auto-generated method stub
				listBitmap = new ArrayList<String>();
				try
				{
					imageDatas = list;
					if (imageDatas == null || imageDatas.isEmpty())
					{
						// throw new IllegalArgumentException("没有可上传的图片");
					}
					ImageCenterBean bean = null;
					for (int i = 0; i < imageDatas.size(); i++)
					{
						bean = imageDatas.get(i);
						listBitmap.add(bean.getPath());
					}
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}
				showImages();
			}
		});

	}

	public void showImages()
	{
		// ImageAdapter adapter = new ImageAdapter(this);
		index = 0;
		mediaData.clear();
		int size = listBitmap.size();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < size; i++)
		{
			map.put("image", listBitmap.get(i));
			mediaData.add(map);
		}

		LinearLayout linear = new LinearLayout(getActivity());

		for (int i = 0; i < imageAdapter.getCount(); i++)
		{
			ImageView img = new ImageView(getActivity());
			LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			if (i == 0)
			{
				img.setImageResource(R.drawable.greenicon);
			}
			else
			{
				img.setImageResource(R.drawable.grayicon);
			}
			linear.addView(img, params);
		}
		imageBottom.removeAllViews();
		imageBottom.addView(linear);
		imageAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// 影像中心
		case R.id.leftArrowBtn:
			previousPicture();
			break;
		case R.id.rightArrowBtn:
			nextPicture();
			break;
		// 影响拍照
		case R.id.mediaTakePictureBtn:
			mediaPhotograph();
			break;
		// 插入本地图片
		case R.id.mUploadImgBtn:
			insertLocalPhotograh();
			break;
		// 删除图片
		case R.id.mediaDeleteBtn:
			deleteImage();
			break;
		// 影像上传
		case R.id.directUploadBtn:
			photographUpload();
			break;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.mediaImageGallery:
			showImageBrowser(arg2);
			break;
		case R.id.mediaList:
			showMediaList(arg2);
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
	{
		scrollToImage(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent)
	{
	}

	private void showImageBrowser(int position)
	{
		String image = listBitmap.get(position);
		PictureGalleryActivity.startForPictureGallery(getActivity(), listBitmap, position);

		// Bitmap bitmap = BitmapFactory.decodeFile(image);
		// ImageView imageView = new ImageView(getActivity());
		// imageView.setImageBitmap(bitmap);// 显示图片
		// if (null == dialog || !dialog.isShowing())
		// dialog = new AlertDialog.Builder(getActivity())
		// .setTitle("图片预览").setView(imageView)
		// .setPositiveButton("关闭", null).show();
	}

	private void showMediaList(final int position)
	{
		typeIndex = position;
		progressDialog = new ProgressDialogUtil(getActivity());
		progressDialog.show("加载中...");
		new Thread()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				super.run();
				getImages();
				MediaSimpleAdapter.selectItem = position;
				mediaHandler.sendEmptyMessage(0);
				if (position < mediadAdapter.getCount())
				{
					imageType = typeList.get(position);
				}
				else
				{
					imageType = "9999";
				}
				mediaHandler.sendEmptyMessage(1);
			}

		}.start();
	}

	// 上一张图片
	private void previousPicture()
	{
		try
		{
			index--;
			if (index >= 0)
			{
				imageGallery.setSelection(index);
			}
			else
			{
				index++;
			}
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// 下一张图片
	private void nextPicture()
	{
		try
		{
			index++;
			if (index < imageDatas.size())
			{
				imageGallery.setSelection(index);
			}
			else
			{
				index--;
			}
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// 影像拍照
	private void mediaPhotograph()
	{
		if (MediaSimpleAdapter.selectItem >= 0)
		{
			if (MediaSimpleAdapter.selectItem < mediadAdapter.getCount())
			{
				Intent intent2 = new Intent();
				intent2.setClass(getActivity(), CameraActivity.class);
				intent2.putExtra("imageType", imageType);
				intent2.putExtra("num", 0);
				intent2.putExtra("total", 12);
				intent2.putExtra("reportNo", registNo);
				intent2.putExtra("taskId", taskId);
				intent2.putExtra("registId", registId);
				startActivityForResult(intent2, PHOTOGRAPH_CODE);
			}
		}
		else
		{
			ToastDialog.show(getActivity(), "请先选择拍照类型", ToastDialog.ERROR);
		}
	}

	// 插入本地图片
	private void insertLocalPhotograh()
	{
		if (null == imageType || imageType.equals(""))
		{
			CustomDialog.show(getActivity(), "信息提示", "请先选中照片类型");
		}
		else
		{
			Intent intent = new Intent(getActivity(), UploadImageActivity.class);
			intent.putExtra("registNo", registNo);
			if (null != taskId)
			{
				intent.putExtra("taskId", taskId);
			}
			intent.putExtra("registId", registId);
			intent.putExtra("imageType", imageType);
			startActivityForResult(intent, PHOTOGRAPH_CODE);
			// startActivity(intent);
		}
	}

	// 影像上传
	private void photographUpload()
	{
		progressDialog = new ProgressDialogUtil(getActivity());
		progressDialog.show("加载中...");
		new AsyncTask<Void, Void, List<ImageCenterBean>>()
		{

			@Override
			protected List<ImageCenterBean> doInBackground(Void... arg0)
			{
				// TODO Auto-generated method stub
				ImageCenterDatabase database = new ImageCenterDatabase(getActivity());
				return database.selectNotUpload(registNo);
			}

			@Override
			protected void onPostExecute(List<ImageCenterBean> list)
			{
				// TODO Auto-generated method stub
				super.onPostExecute(list);
				progressDialog.dismiss();
				if (null == list || list.isEmpty())
				{
					ToastDialog.show(getActivity(), "当前没有图片或没有需要上传的图片");
					return;
				}
				try
				{
					dialog = CustomDialog.show(getActivity(), "信息提示", "是否上传所有图片?", new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							dialog.dismiss();
							List<ImageBean> imglist = CacheImage.getInstant().getImageBeans();
							if (imglist.isEmpty())
							{
								ImageBean entry = new ImageBean();
								entry.setRegistNo(registNo);
								entry.setUpload(true);
								imglist.add(entry);
								CacheImage.getInstant().setImageBeans(imglist);
							}
							else
							{
								boolean isExist = false;
								for (ImageBean img : imglist)
								{
									if (registNo.equals(img.getRegistNo()))
									{
										isExist = true;
										img.setRegistNo(registNo);
										img.setUpload(true);
										CacheImage.getInstant().setImageBeans(imglist);
										break;
									}
								}
								if (!isExist)
								{
									ImageBean entry = new ImageBean();
									entry.setRegistNo(registNo);
									entry.setUpload(true);
									imglist.add(entry);
									CacheImage.getInstant().setImageBeans(imglist);
								}
							}
							// isTakedPhoto = true ;
							Intent intent = new Intent(getActivity(), ImageUploadService.class);
							intent.putExtra("registNo", registNo);
							getActivity().startService(intent);
						}
					}, new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							dialog.dismiss();
						}
					});
				}
				catch (Exception e)
				{
					// e.printStackTrace();
					ToastDialog.show(getActivity(), "图片上传错：" + e.getMessage(), ToastDialog.ERROR);
				}
			}

		}.execute();

	}

	/**
	 * 图片删除事件
	 */
	public void deleteImage()
	{
		if (imageDatas != null && !imageDatas.isEmpty())
		{
			progressDialog = new ProgressDialogUtil(getActivity());
			progressDialog.show("正在操作中...");
			new Thread()
			{

				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					super.run();
					try
					{
						ImageCenterBean image = imageDatas.get(index);
						File file = new File(image.getPath());

						if (image.getPath().contains("finger"))
						{
							file.delete();
						}
						// if (file.delete()) {
						ImageCenterDatabase database = new ImageCenterDatabase(getActivity());
						database.delete("imageCenter", "reportNo=? and type=? and id=?", new String[]
						{ registNo, image.getType(), image.getId() });
						listBitmap.remove(index);
						imageDatas.remove(index);
						mediaHandler.sendEmptyMessage(2);
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}.start();
		}
		else
		{
			CustomDialog.show(getActivity(), "信息提示", "没有可以删除的图片");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PHOTOGRAPH_CODE)
		{
			getImageListByType(registNo, imageType); // 重新加载照片
		}
	}

	class ImageGalleryAdapter extends SimpleAdapter
	{
		private Context context;
		private int resource;

		public ImageGalleryAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
		{
			super(context, data, resource, from, to);
			this.context = context;
			this.resource = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub
			View view;
			if (convertView == null)
			{
				view = View.inflate(context, resource, null);
				ImageView imageView = (ImageView) view.findViewById(R.id.mediaImage);
				imageView.setBackgroundColor(0x00bbaa);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bitmap = BitmapFactory.decodeFile(listBitmap.get(position), options);
				imageView.setImageBitmap(bitmap);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
				convertView = imageView;
			}
			return convertView;
		}
	}

}
