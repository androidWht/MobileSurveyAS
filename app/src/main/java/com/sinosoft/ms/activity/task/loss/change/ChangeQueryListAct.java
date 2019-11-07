package com.sinosoft.ms.activity.task.loss.change;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.IFitListService;
import com.sinosoft.ms.service.ILossFitInfoService;
import com.sinosoft.ms.service.impl.FitListService;
import com.sinosoft.ms.service.impl.LossFitInfoService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.adapter.ChangeQueryListAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;
/**
 * 换件列表选择
 * @author Administrator
 *
 */
public class ChangeQueryListAct extends Activity implements OnClickListener{
	private List<Integer> listItemID = new ArrayList<Integer>();
	private List<LossFitInfoItem> lossFitInfoItemList = null;	//从服务器获取的换件列表
	private List<LossFitInfoItem> myLossFitInfoItemList = null;	//本地换件列表
	private ILossFitInfoService lossFitInfoService = null;
	private IFitListService fitListService = null;
	private ProgressDialogUtil progressDialog = null;
	private ChangeQueryListAdapter adapter = null;
	private Button confirmBtn = null;
	private Button backBtn = null;
	private EditText searchEdit = null;
	private ListView lv = null;
	
	private String pricePlanId = null;//价格方案
	private String partNameId = null;//零件名称
	private String keyName = null;//关键字
	private String registNo = null;//
	private String carGroupId = null;//车组ID
	private String vehicleType = null;//车型编码
	private String partsGroupId = null;
	private String damagePartId = null;		//损伤部位零件Id
	private String taskId = null;
	private int tag=0;				//1：一次点选 ； 2：二次点选
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ActivityManage.getInstance().push(this);
		setContentView(R.layout.activity_change_query_list);
		
		Intent intent = getIntent();
		pricePlanId = (String)intent.getCharSequenceExtra("pricePlanId");
		partNameId = (String)intent.getCharSequenceExtra("partNameId");
		keyName = (String)intent.getCharSequenceExtra("keyName");
		registNo = (String)intent.getCharSequenceExtra("registNo");
		taskId= (String)intent.getCharSequenceExtra("taskId");
		carGroupId=(String)intent.getCharSequenceExtra("carGroupId");
		vehicleType=(String)intent.getCharSequenceExtra("vehicleType");
		partsGroupId =(String)intent.getCharSequenceExtra("partsGroupId");
		damagePartId = (String)intent.getCharSequenceExtra("damagePartId");
		tag=intent.getIntExtra("type", 1);
		confirmBtn = (Button) findViewById(R.id.confirmBtn);
		backBtn = (Button) findViewById(R.id.backBtn);
		lv = (ListView) findViewById(R.id.lvperson);
		searchEdit = (EditText)findViewById(R.id.editText1);

		loadData();
		
		confirmBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
		searchEdit.addTextChangedListener(new MyTextWathcer());
	}

	/**
	 * 模拟数据
	 */
	private void loadData() {
		try {
			progressDialog = new ProgressDialogUtil(this);
			progressDialog.show("数据加载中...");
			new Thread() {
				public void run() {
					Message msg = new Message();
					try {
						//获取区域
//						String type = null;
						if("01".equals(pricePlanId)){
							pricePlanId = "001";
//							type = "001";
						}else if("02".equals(pricePlanId)){
							pricePlanId = "002";
//							type = "002";
						}else if("03".equals(pricePlanId)){
							pricePlanId = "003";
//							type = "003";
						}
						//BusinessFactory.getInstance().getDeptNo()
						User user=UserCashe.getInstant().getUser();
						fitListService = new FitListService();
						lossFitInfoItemList = fitListService.execute(user.getName(),
								pricePlanId, vehicleType, carGroupId,
								user.getDeptNo(),
								partNameId, damagePartId,keyName,tag);
						lossFitInfoService = new LossFitInfoService();
						myLossFitInfoItemList = lossFitInfoService.getByRegistNo(taskId);
						msg.what = 1;
					} catch (Exception e) {
						e.printStackTrace();
						msg.what = MyUtils.getErrorCode(e.getMessage());
						msg.obj = "" + e.getMessage();
					} finally {
						handler.sendMessage(msg);
					}
				};
			}.start();
			
		} catch (Exception e) {
			e.printStackTrace();
			ToastDialog.show(this, e.getMessage(),ToastDialog.ERROR);
		}
	}


	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.confirmBtn:
			try{
				listItemID.clear();
				listItemID = adapter.getSelectedListId();
//				int size = adapter.mChecked.size();
//				for (int i = 0; i < size; i++) {
//					if (adapter.mChecked.get(i)) {
//						listItemID.add(i);
//					}
//				}
				if (listItemID.size() == 0) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							ChangeQueryListAct.this);
					builder1.setMessage("没有选中任何记录");
					builder1.show();
				} else {
					if(null==lossFitInfoService){
						lossFitInfoService = new LossFitInfoService();
					}
					StringBuilder sb = new StringBuilder();
					Map<String,Map<String,String>> dicConfMap=AppConstants.getLocalItemConf();
					LossFitInfoItem repeatItem = null;	//partId重复的Item
					for (int i = 0; i < listItemID.size(); i++){
						LossFitInfoItem lossFitInfoItem = lossFitInfoItemList.get(listItemID.get(i));
						//partId不能与本地已有的换件重复
						for(LossFitInfoItem item : myLossFitInfoItemList){
							String p1 = lossFitInfoItem.getPartId();
							String p2 = item.getPartId();
							if(!StringUtils.isEmpty(p1) && !StringUtils.isEmpty(p2) && p1.equals(p2)){
								repeatItem = item;
								break;
							}
						}
					}
					if(null != repeatItem){
						CustomDialog.show(ChangeQueryListAct.this, "", "换件重复\n"+repeatItem.getPartStandard()+"\n"+repeatItem.getOriginalName());
						return;
					}else{
						for (int i = 0; i < listItemID.size(); i++) {
							sb.append("ItemID=" + listItemID.get(i) + " . ");
							LossFitInfoItem lossFitInfoItem = lossFitInfoItemList.get(listItemID.get(i));
							//如果没有重复，则添加
							SharedPreferences insureTermSP = getSharedPreferences("insureTerm", 0);
							lossFitInfoItem.setInsureTermCode(insureTermSP.getString("key", ""));
							lossFitInfoItem.setInsureTerm(insureTermSP.getString("value", ""));
							
							lossFitInfoItem.setChgCompSetCode(pricePlanId);
							lossFitInfoItem.setRegistNo(taskId);
							lossFitInfoItem.setIfRemain("0");		//是否损余 1：是  |  0 ：否
							lossFitInfoItem.setLossPrice(Double.valueOf(lossFitInfoItem.getRefPrice1()));
							lossFitInfoItem.setSurveyQuotedPrice(String.valueOf(lossFitInfoItem.getRefPrice1()));
							// 添加标志
							lossFitInfoItem.setSelfConfigFlag("0") ;
							if(0==lossFitInfoService.save(lossFitInfoItem)){
						
							}
						}
						
						finish();
						ActivityManage.getInstance().pop();
						Activity activity= ActivityManage.getInstance().peek();
						if(activity!=null&&activity instanceof ChangeQueryAct){
								activity.finish();
								ActivityManage.getInstance().pop();
							}
						}
					}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		case R.id.backBtn:
			try {
				ChangeQueryListAct.this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
			}
			break;
		}
	}
	
	//用于更新界面 跳转
  	private Handler handler = new Handler() {
  		public void handleMessage(Message msg) {
  			if (progressDialog != null) {
				progressDialog.dismiss();
			}
  			switch (msg.what) {
  			case 0:
  				ToastDialog.show(ChangeQueryListAct.this, msg.obj.toString(),ToastDialog.ERROR);
  				break;
  			case 1:
//  				Toast.makeText(ChangeQueryListAct.this, lossFitInfoItemList.size()+"", Toast.LENGTH_LONG).show();
  				if(null != lossFitInfoItemList && !lossFitInfoItemList.isEmpty()){
  					int i = 0;
  					for (LossFitInfoItem lossFitInfoItem : lossFitInfoItemList) {
  						lossFitInfoItem.setId(i++);
						lossFitInfoItem.setRegistNo(registNo);
						lossFitInfoItem.setChgCompSetCode(pricePlanId);
						lossFitInfoItem.setPartGroupName(partsGroupId);
					}
  					adapter = new ChangeQueryListAdapter(ChangeQueryListAct.this,0,lossFitInfoItemList);
  					lv.setAdapter(adapter);
  				}else{
  					ToastDialog.show(ChangeQueryListAct.this, "无查询数据",ToastDialog.ERROR);
  					
  				}
  				break;
  			case AppConstants.NO_LOGIN_CODE:
				Intent intent = getIntent();
				intent.setClass(ChangeQueryListAct.this, LoginAct.class);
				startActivity(intent);
				try {
					ChangeQueryListAct.this.finish();
					ActivityManage.getInstance().pop();
				} catch (Exception e) {
				}
				break;
  			}
  		};
  	};
  	
  	class MyTextWathcer implements TextWatcher{

		/* (non-Javadoc)
		 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
		 */
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
		 */
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
		 */
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			if(null != lossFitInfoItemList && !lossFitInfoItemList.isEmpty()){
			adapter.getFilter().filter(searchEdit.getText().toString());}
		}
  		
  	}
  	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		listItemID = null;
		if(null!=lossFitInfoItemList){
			lossFitInfoItemList.clear();
		}
		lossFitInfoItemList = null;
		lossFitInfoService = null;
		fitListService = null;
		progressDialog = null;
		adapter = null;
		confirmBtn = null;
		backBtn = null;
		lv = null;

		pricePlanId = null;//价格方案
		partNameId = null;//零件名称
		keyName = null;//关键字
		registNo = null;//
		carGroupId = null;//车组ID
		vehicleType = null;//车型编码
		partsGroupId = null;
		taskId = null;
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