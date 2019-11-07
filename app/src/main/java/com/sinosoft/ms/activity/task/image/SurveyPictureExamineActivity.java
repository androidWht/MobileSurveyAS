package com.sinosoft.ms.activity.task.image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.LookImage;
import com.sinosoft.ms.service.ILookImageService;
import com.sinosoft.ms.service.impl.LookImageService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.AsyncBitmapLoader;
import com.sinosoft.ms.utils.AsyncBitmapLoader.ImageCallBack;
import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.BitmapUtil;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.adapter.MediaSimpleAdapter;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DictCashe;

/**
 * 系统名：移动查勘 
 * 子系统名： 查看查勘影像图片
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * 界面功能:
 * 定损环节查看己上传的影像文件
 * 
 * 实现步骤:
 * 1.根据报案号获取影像数据
 * 2.获取影像缩略图
 * 3.显示缩略图
 * 4.点击缩略图下载原始图片做预览
 * 
 * @author linianchun
 * @createTime 下午4:24:31
 */

public class SurveyPictureExamineActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private boolean isHaveImage = false;
	private ProgressDialogUtil progressDialog = null; 
	private Map<String, Map<String, String>> dataMap = null;
	private ListView mediaList = null;
	private GridView mGridView = null;// 内容显示gridview
	private Button mBackBtn = null;  // 返回按钮
	private TextView mRegistNoTextView = null;
	
	private String registNo = null;
	private Intent intent = null;
	
	private Map<String, String> listData = null;//索赔清单
	private List<LookImage> lookImageList = null;
	private List<Map<String, String>> imageTypelist = null;//图片类型
	private Set<String> typeSet = null;
	private MediaSimpleAdapter adapter = null;//图片类型适配置器
	private SimpleAdapter saMenuItem = null;
	private ArrayList<Map<String, Object>> data = null;
	private String imageId = null;
	private Bitmap bigBmp = null;//原图
	private String filePath = null;//图片路径
	private AsyncBitmapLoader asyncBitmapLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey_picture_examine);
		ActivityManage.getInstance().push(this);
	
		mediaList = (ListView) findViewById(R.id.mediaList);
		mGridView = (GridView) findViewById(R.id.imageGv);
		mBackBtn = (Button) findViewById(R.id.mBackBtn);
		mRegistNoTextView = (TextView)findViewById(R.id.registNo);
		
		intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		mRegistNoTextView.setText(registNo);
		
		mBackBtn.setOnClickListener(this);
		initData();
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dataMap = DictCashe.getInstant().getDict();//获取索赔清单
		listData = dataMap.get("DocData");
		imageTypelist = new ArrayList<Map<String, String>>();
		
		asyncBitmapLoader = AsyncBitmapLoader.getInstance();
		asyncBitmapLoader.init(AppConstants.info.getDownUrl(), "");
		getImageData();//获取影像信息
	}

	/**
	 * 获取影像资料
	 */
	private void getImageData() {
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("数据加载中，请稍后");
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {		
					//1.获取影像信息
					ILookImageService service = new LookImageService();
					lookImageList = service.getImageByRegistNo(registNo);
					
					//2.获取影像缩略图
					if(null!=lookImageList && !lookImageList.isEmpty()){
						isHaveImage = true;//设置为有影像文件
						int size = 0;
						Iterator iter = lookImageList.iterator();
						while(iter.hasNext()){
							LookImage lookImage = (LookImage)iter.next();
//							Bitmap bmp = service.getImageFile(1, lookImage.getImageId());
							String bitmapXml = service.getImageXmll(1, lookImage.getImageId());
							lookImage.setBitmapXml(bitmapXml);
							String codeValue = listData.get(lookImage.getCodeType());
							/*
							 * if(null==codeValue){
//								0001	索赔申请书
//								0002	驾驶证、行驶证、体检回执
//								0004	责任认定书、协议书
//								0018	标的车修理发票
//								3008	活期存折/银行卡
								if(lookImage.getCodeType().equals("0001")){
									codeValue = "索赔申请书";
								}else if(lookImage.getCodeType().equals("0002")){
									codeValue = "驾驶证、行驶证、体检回执";
								}else if(lookImage.getCodeType().equals("0004")){
									codeValue = "责任认定书、协议书";
								}else if(lookImage.getCodeType().equals("0018")){
									codeValue = "标的车修理发票";
								}else if(lookImage.getCodeType().equals("3008")){
									codeValue = "活期存折/银行卡";
								}else{
									codeValue = lookImage.getCodeType();//没有分类将代码显示出来
								}
							}
							*/
							lookImage.setCodeValue(codeValue);
							boolean isAdd = false;
							if(null==typeSet){//过滤重复类型
								typeSet = new HashSet<String>();
								typeSet.add(codeValue);
								isAdd = true;
								size ++;
							}else{
								typeSet.add(codeValue);
								if(size!=typeSet.size()){//重复的元素
									isAdd = true;
									size ++;
								}
							}
							
							if(isAdd){//设置图片类型选项
								Map<String, String> map = new HashMap<String, String>();
								map.put("type", codeValue);
								imageTypelist.add(map);
							}
						}
					}
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

	/**
	 * 加载九宫格图片数据
	 * 
	 * @param codeType
	 *            图片类型
	 * @param list
	 *            影像图片信息
	 * @return 九宫格图片数据
	 */
	private void loadGridImage(String codeType,
			List<LookImage> list) {
		data = new ArrayList<Map<String, Object>>();
		if(null!=lookImageList && !lookImageList.isEmpty()){
			Iterator iter = lookImageList.iterator();
			while(iter.hasNext()){
				LookImage lookImage = (LookImage)iter.next();
				//如果是代码或代码名称都做为过滤条件
				if(codeType.equals(lookImage.getCodeType()) 
						|| codeType.equals(lookImage.getCodeValue())){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("itemImageXml", lookImage.getBitmapXml());
					map.put("itemText", lookImage.getCodeValue());
					map.put("codeType", lookImage.getCodeType());
					map.put("imageId", lookImage.getImageId());
					data.add(map);
				}
			}
		}
		
		if (data != null) {
			saMenuItem = new SimpleAdapter(SurveyPictureExamineActivity.this, 
					  data, //数据源 
					  R.layout.item_survey_picture_examine, //xml实现 
					  new String[]{"itemImageXml","itemText"}, 
					  new int[]{R.id.img,R.id.title});  
			/*实现ViewBinder()这个接口 (要不图片显示不出来)*/
			saMenuItem.setViewBinder(new ViewBinder() {
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					if(view instanceof ImageView && data instanceof String){
						asyncBitmapLoader.loadBitmap((ImageView)view, (String)data, new ImageCallBack(){

							@Override
							public void imageLoad(ImageView imageView,
									Bitmap bitmap) {
								// TODO Auto-generated method stub
								imageView.setImageBitmap(bitmap);
							}
						});
						return true;
					}
					return false;
/*					if(view instanceof ImageView && data instanceof Bitmap){
						ImageView i = (ImageView)view;
						i.setImageBitmap((Bitmap) data);
						return true;
					}
					return false;*/
				}
			});
			mGridView.setAdapter(saMenuItem);
			mGridView.setOnItemClickListener(new OnItemClickListener() { 
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) { 
                    imageId = (String)data.get(position).get("imageId");
                    getBigImage();
				} 
			});
		}
	}
	
	/**
	 * 获取影像资料(原图)
	 */
	private void getBigImage() {
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("数据加载中，请稍后");
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {		
					//1.获取影像信息
					ILookImageService service = new LookImageService();
					FileUtils utils = new FileUtils();
					filePath = AppConstants.PATH_CACHE + imageId + ".png";
					File file = new File(filePath);
					//如果图片不存在，则下载
					if(!file.exists()){
						bigBmp = service.getImageFile(2, imageId);
						new FileUtils().creatDir(AppConstants.PATH_CACHE);
						BitmapUtil.saveBitmap(bigBmp, filePath);
						bigBmp.recycle();
					}
					msg.what = 2;
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
	
	/**
	 * 点击左边的图片类型显示右边缩略图事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MediaSimpleAdapter.selectItem = position;
		adapter.notifyDataSetChanged();
		String type = imageTypelist.get(position).get("type");
		//设置影像图片显示区域内容
		loadGridImage(type,lookImageList);
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null ) {
				progressDialog.dismiss();
			}
			switch (msg.what) {
			case 0:
				if (null == msg.obj) {
					break;
				}
				String errorMsg = msg.obj.toString();
				if (StringUtils.isNotEmpty(errorMsg)) {
					ToastDialog.show(SurveyPictureExamineActivity.this, errorMsg,
							ToastDialog.ERROR);
				}
				break;
			case 1:
				if(!isHaveImage){
					Toast.makeText(SurveyPictureExamineActivity.this, "无影像图片", Toast.LENGTH_LONG).show();
				}
				//设置左边图片类型信息
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("type","现场示意图");
//				imageTypelist.add(map);
				adapter = new MediaSimpleAdapter(SurveyPictureExamineActivity.this, imageTypelist, R.layout.item_media_type,
						new String[] { "type" }, new int[] { R.id.media_item_type },
						new int[] {}, null);
				mediaList.setAdapter(adapter);
				MediaSimpleAdapter.selectItem = 0;
				mediaList.setOnItemClickListener(SurveyPictureExamineActivity.this);
				
				//设置影像图片显示区域内容
				if(null!=lookImageList && !lookImageList.isEmpty()){
					loadGridImage(lookImageList.get(0).getCodeType(),lookImageList);
				}
				break;
			case 2:
				PictureGalleryActivity.startForPictureGallery(getApplicationContext(), filePath);
//				Intent intent = new Intent(getApplicationContext(),PictureGalleryActivity.class);
//				intent.putExtra("path", AppConstants.PATH+"temp.png");
//				intent.putExtra("picture", bigBmp);
//				startActivity(intent);
//				ImageView imageView = new ImageView(SurveyPictureExamineActivity.this);
//				imageView.setImageBitmap(bigBmp);// 显示图片
//				new AlertDialog.Builder(SurveyPictureExamineActivity.this)
//						.setTitle("图片预览").setView(imageView)
//						.setPositiveButton("关闭", null).show();
				break;
			}
		}
	};
	
	/**
	 * 返回事件处理
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mBackBtn:
			onBackPressed();
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		try {
			this.finish();
			ActivityManage.getInstance().pop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		progressDialog = null; 
		dataMap = null;
		mediaList = null;
		mGridView = null;// 内容显示gridview
		mBackBtn = null;  // 返回按钮
		mRegistNoTextView = null;
		registNo = null;
		intent = null;
		listData = null;//索赔清单
		lookImageList = null;
		imageTypelist = null;//图片类型
		typeSet = null;
		adapter = null;//图片类型适配置器
		saMenuItem = null;
		imageId = null;
		bigBmp = null;//原图
		FileUtils.deleteFile(AppConstants.PATH_CACHE);
		if (data != null) {
			data.clear();
			data = null;
		}
	}
}