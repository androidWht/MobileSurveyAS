package com.sinosoft.ms.activity.task.loss.vehicle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.po.Brand;
import com.sinosoft.ms.model.po.Car;
import com.sinosoft.ms.model.po.Emus;
import com.sinosoft.ms.model.po.Manufacturer;
import com.sinosoft.ms.service.IThirdPartyService;
import com.sinosoft.ms.service.impl.ThirdPartyService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.FactoryDialog;
import com.sinosoft.ms.utils.component.ToastDialog;

/**
 * 车辆定型查询
 * 
 * @author Administrator
 * 
 */
public class VehicleTypeQueryAct extends Activity implements OnClickListener,
		OnItemSelectedListener {
	private String[] zmIndex = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private IThirdPartyService thirdPartyService;
	private Map<String, Map<String, String>> map;
	private List<Manufacturer> list = null;
	private List<Brand> brandList = null;
	private List<Car> carList = null;
	private List<Emus> emusList = null;
	private Button dataSreachBtn;
	private Button dataBrackBtn;
	
	private Button vehFactoryNameBtn;// 厂家名称
	private Spinner vehBrandNameSp;// 品牌名称
	private Spinner vehSeriNameSp;// 定损车系名称
	private Spinner vehGroupNameSp;// 定损车组名称
	private TableRow tableRow2;
	private TableRow tableRow3;
	private TableRow tableRow4;
	private Dialog dialog = null;
	private String emusId = null;
	private String registNo;
	private String taskId;
	private String subStr;
	private String indexValue;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		zmIndex = null;
		thirdPartyService = null;
		map = null;
		list = null;
		brandList = null;
		carList = null;
		emusList = null;
		dataSreachBtn = null;
		vehFactoryNameBtn = null;// 厂家名称
		vehBrandNameSp = null;// 品牌名称
		vehSeriNameSp = null;// 定损车系名称
		vehGroupNameSp = null;// 定损车组名称
		tableRow2 = null;
		tableRow3 = null;
		tableRow4 = null;
		dialog = null;
		emusId = null;
		registNo = null;
		taskId = null;
		subStr = null;
		indexValue = null;
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_loss_vehicle_type_sreach);
		ActivityManage.getInstance().push(this);
		
		Intent intent = getIntent();
		registNo = (String) intent.getCharSequenceExtra("registNo");
		taskId = (String) intent.getCharSequenceExtra("taskId");
		// 设置视图控件
		setViewControl();
		// 装载数据
		loadData();

		dataSreachBtn.setOnClickListener(this);
		dataBrackBtn.setOnClickListener(this);
		vehFactoryNameBtn.setOnClickListener(this);
	}

	private void setViewControl() {
		dataSreachBtn = (Button) findViewById(R.id.dataSreachBtn);
		dataBrackBtn=(Button)findViewById(R.id.dataBrackBtn);
		
		vehFactoryNameBtn = (Button) findViewById(R.id.vehFactoryNameBtn);// 厂家名称
		vehBrandNameSp = (Spinner) findViewById(R.id.vehBrandNameSp);// 品牌名称
		vehSeriNameSp = (Spinner) findViewById(R.id.vehSeriNameSp);// 定损车系名称
		vehGroupNameSp = (Spinner) findViewById(R.id.vehGroupNameSp);// 定损车组名称
		tableRow2 = (TableRow) findViewById(R.id.tableRow2);//
		tableRow3 = (TableRow) findViewById(R.id.tableRow3);//
		tableRow4 = (TableRow) findViewById(R.id.tableRow4);//
	}

	private void loadData() {
		if (null == thirdPartyService) {
			thirdPartyService = new ThirdPartyService();
		}
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		// this, R.layout.simple_spinner_item);
		// list = thirdPartyService.getFactory(null);
		// if (null != list && !list.isEmpty()) {
		// Iterator iter = list.iterator();
		// adapter.add("请选择厂家");
		// while (iter.hasNext()) {
		// Manufacturer manufacturer = (Manufacturer) iter.next();
		// adapter.add(manufacturer.getName());
		// }
		// }
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// vehFactoryNameSp.setAdapter(adapter);
		// vehFactoryNameSp.setOnItemSelectedListener(this);
		// vehFactoryNameSp.setSelection(0,true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.vehFactoryNameBtn:// 选择生产厂家事件处理

			loadFactory(null);
			FactoryDialog customDialog = new FactoryDialog();
			if(dialog!=null){
				try{
	        		dialog.dismiss();
	        	}catch(Exception e){
	        		
	        	}
			}
			dialog = customDialog.getDialog(this, "选择生产厂家", map, this);
			dialog.show();

			break;
		case R.id.subTxt:// Dialog选择框(选择生产厂家后回到主界面操作)
			TextView subText = (TextView) v;
			subStr = subText.getText().toString();
			vehFactoryNameBtn.setText(subStr);

			if (null != dialog) {
				dialog.dismiss();

				//ToastDialog.show(VehicleTypeQueryAct.this, subStr,
				//		ToastDialog.INFO);
				String key = subText.getTag().toString();
				Map<String, String> chailMap = map.get(key);
				String value = chailMap.get(subStr);
				if (StringUtils.isEmpty(value)) {
					value = "";
				}
				Manufacturer manufacturer = list.get(0);
				brandList = thirdPartyService.getBandByManufacturerId(value);
				if (null != brandList && !brandList.isEmpty()) {
					ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(
							this, R.layout.item_simple_spinner);
					brandAdapter.add("请选择品牌");
					Iterator iter = brandList.iterator();
					while (iter.hasNext()) {
						Brand brand = (Brand) iter.next();
						brandAdapter.add(brand.getName());
					}
					brandAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					vehBrandNameSp.setAdapter(brandAdapter);
					vehBrandNameSp.setOnItemSelectedListener(this);
					//vehBrandNameSp.setSelection(0, true);
					tableRow2.setVisibility(0);
					tableRow3.setVisibility(8);
					tableRow4.setVisibility(8);
				} else {
					tableRow2.setVisibility(8);
				}
			}
			break;
		case R.id.dataSreachBtn:
			if (StringUtils.isNotEmpty(emusId)) {
				Intent intent = getIntent();
				intent.putExtra("registNo", registNo);
				intent.putExtra("taskId", taskId);
				intent.putExtra("emusId", emusId);
				intent.putExtra("vehSeriName", vehSeriNameSp.getSelectedItem()
						.toString());
				intent.putExtra("vehGroupName", vehGroupNameSp
						.getSelectedItem().toString());
				/*新增时间2013.5.11 - 严植培 */
				intent.putExtra("vehFactoryName", vehFactoryNameBtn.getText().toString());
				intent.putExtra("vehBrandName", vehBrandNameSp.getSelectedItem().toString());
				intent.putExtra("vehGroupName", vehGroupNameSp.getSelectedItem().toString());
				intent.setClass(this, VehicleTypeListAct.class);
				startActivity(intent);
			} else {
				ToastDialog.show(this, "请选择查询条件", ToastDialog.ERROR);
			}
			break;
		case R.id.dataBrackBtn:
			try {
				this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}
	/**
	 * 
	 */
	private void loadFactory(String text) {
		map = new HashMap<String, Map<String, String>>();
		list = thirdPartyService.getFactory(text);
		Map a = new HashMap<String, String>();
		Map b = new HashMap<String, String>();
		Map c = new HashMap<String, String>();
		Map d = new HashMap<String, String>();
		Map e = new HashMap<String, String>();
		Map f = new HashMap<String, String>();
		Map g = new HashMap<String, String>();
		Map h = new HashMap<String, String>();
		Map i = new HashMap<String, String>();
		Map j = new HashMap<String, String>();
		Map k = new HashMap<String, String>();
		Map l = new HashMap<String, String>();
		Map m = new HashMap<String, String>();
		Map n = new HashMap<String, String>();
		Map o = new HashMap<String, String>();
		Map p = new HashMap<String, String>();
		Map q = new HashMap<String, String>();
		Map r = new HashMap<String, String>();
		Map s = new HashMap<String, String>();
		Map t = new HashMap<String, String>();
		Map u = new HashMap<String, String>();
		Map v = new HashMap<String, String>();
		Map w = new HashMap<String, String>();
		Map x = new HashMap<String, String>();
		Map y = new HashMap<String, String>();
		Map z = new HashMap<String, String>();

		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Manufacturer manufacturer = (Manufacturer) iter.next();
			if ("A".equals(manufacturer.getZmIndex())) {
				a.put(manufacturer.getName(), manufacturer.getId());
			} else if ("B".equals(manufacturer.getZmIndex())) {
				b.put(manufacturer.getName(), manufacturer.getId());
			} else if ("C".equals(manufacturer.getZmIndex())) {
				c.put(manufacturer.getName(), manufacturer.getId());
			} else if ("D".equals(manufacturer.getZmIndex())) {
				d.put(manufacturer.getName(), manufacturer.getId());
			} else if ("E".equals(manufacturer.getZmIndex())) {
				e.put(manufacturer.getName(), manufacturer.getId());
			} else if ("F".equals(manufacturer.getZmIndex())) {
				f.put(manufacturer.getName(), manufacturer.getId());
			} else if ("G".equals(manufacturer.getZmIndex())) {
				g.put(manufacturer.getName(), manufacturer.getId());
			} else if ("H".equals(manufacturer.getZmIndex())) {
				h.put(manufacturer.getName(), manufacturer.getId());
			} else if ("I".equals(manufacturer.getZmIndex())) {
				i.put(manufacturer.getName(), manufacturer.getId());
			} else if ("J".equals(manufacturer.getZmIndex())) {
				j.put(manufacturer.getName(), manufacturer.getId());
			} else if ("K".equals(manufacturer.getZmIndex())) {
				k.put(manufacturer.getName(), manufacturer.getId());
			} else if ("L".equals(manufacturer.getZmIndex())) {
				l.put(manufacturer.getName(), manufacturer.getId());
			} else if ("M".equals(manufacturer.getZmIndex())) {
				m.put(manufacturer.getName(), manufacturer.getId());
			} else if ("N".equals(manufacturer.getZmIndex())) {
				n.put(manufacturer.getName(), manufacturer.getId());
			} else if ("O".equals(manufacturer.getZmIndex())) {
				o.put(manufacturer.getName(), manufacturer.getId());
			} else if ("P".equals(manufacturer.getZmIndex())) {
				p.put(manufacturer.getName(), manufacturer.getId());
			} else if ("Q".equals(manufacturer.getZmIndex())) {
				q.put(manufacturer.getName(), manufacturer.getId());
			} else if ("R".equals(manufacturer.getZmIndex())) {
				r.put(manufacturer.getName(), manufacturer.getId());
			} else if ("S".equals(manufacturer.getZmIndex())) {
				s.put(manufacturer.getName(), manufacturer.getId());
			} else if ("T".equals(manufacturer.getZmIndex())) {
				t.put(manufacturer.getName(), manufacturer.getId());
			} else if ("U".equals(manufacturer.getZmIndex())) {
				u.put(manufacturer.getName(), manufacturer.getId());
			} else if ("V".equals(manufacturer.getZmIndex())) {
				v.put(manufacturer.getName(), manufacturer.getId());
			} else if ("W".equals(manufacturer.getZmIndex())) {
				w.put(manufacturer.getName(), manufacturer.getId());
			} else if ("X".equals(manufacturer.getZmIndex())) {
				x.put(manufacturer.getName(), manufacturer.getId());
			} else if ("Y".equals(manufacturer.getZmIndex())) {
				y.put(manufacturer.getName(), manufacturer.getId());
			} else if ("Z".equals(manufacturer.getZmIndex())) {
				z.put(manufacturer.getName(), manufacturer.getId());
			}
		}
		map.put("A", a);
		map.put("B", b);
		map.put("C", c);
		map.put("D", d);
		map.put("E", e);
		map.put("F", f);
		map.put("G", g);
		map.put("H", h);
		map.put("I", i);
		map.put("J", j);
		map.put("K", k);
		map.put("R", r);
		map.put("M", m);
		map.put("N", n);
		map.put("O", o);
		map.put("P", p);
		map.put("Q", q);
		map.put("L", l);
		map.put("S", s);
		map.put("T", t);
		map.put("U", u);
		map.put("V", v);
		map.put("W", w);
		map.put("X", x);
		map.put("Y", y);
		map.put("Z", z);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View v, int pos, long arg3) {
		if (0 != pos) {

			switch (arg0.getId()) {
			case R.id.vehBrandNameSp:
				Brand brand = brandList.get(pos - 1);
				carList = thirdPartyService.getCarByBrandId(brand.getId());
				if (null != carList && !carList.isEmpty()) {
					ArrayAdapter<String> carAdapter = new ArrayAdapter<String>(
							this, R.layout.item_simple_spinner);
					carAdapter.add("请选择车系名称");
					Iterator iter = carList.iterator();
					while (iter.hasNext()) {
						Car car = (Car) iter.next();
						carAdapter.add(car.getName());
					}
					carAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					vehSeriNameSp.setAdapter(carAdapter);
					vehSeriNameSp.setOnItemSelectedListener(this);
					vehSeriNameSp.setSelection(0, true);
					tableRow3.setVisibility(0);
					tableRow4.setVisibility(8);
				} else {
					tableRow3.setVisibility(8);
				}
				break;
			case R.id.vehSeriNameSp:
				Car car = carList.get(pos - 1);
				emusList = thirdPartyService.getEmusByCarId(car.getId());
				if (null != emusList && !emusList.isEmpty()) {
					ArrayAdapter<String> emusAdapter = new ArrayAdapter<String>(
							this, R.layout.item_simple_spinner);
					emusAdapter.add("请选择车系名称");
					Iterator iter = emusList.iterator();
					while (iter.hasNext()) {
						Emus emus = (Emus) iter.next();
						emusAdapter.add(emus.getName());
					}
					emusAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					vehGroupNameSp.setAdapter(emusAdapter);
					vehGroupNameSp.setOnItemSelectedListener(this);
					vehGroupNameSp.setSelection(0, true);
					tableRow4.setVisibility(0);
				} else {
					tableRow4.setVisibility(8);
				}
				break;
			case R.id.vehGroupNameSp:
				Emus emus = emusList.get(pos - 1);
				emusId = emus.getId();
				break;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

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
