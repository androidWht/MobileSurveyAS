package com.sinosoft.ms.utils.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.po.Manufacturer;
import com.sinosoft.ms.service.impl.ThirdPartyService;
import com.sinosoft.ms.utils.adapter.FactoryAdapter;

public class FactoryDialog {

	String[] zmIndex = {"A","B","C","D","E","F","G","H","I","J","K","L"
			,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private ExpandableListView expandablelistview = null;
	Map<String,Map<String,String>> map;
	FactoryAdapter expandAdapter = null;
	List<String> tempList = null;
	List<String> groupList = null;
	List<List<String>> childList = null;
	private EditText sreachWord;
   private Context context;
   private Dialog dialog;
	public Dialog getDialog(final Context context, final String name,Map<String,Map<String,String>> map,OnClickListener listener) {
        
		dialog = new Dialog(context, R.style.Theme_ShareDialog);
		dialog.setContentView(R.layout.dialog_factory_page);
		///this.map = map;
		loadFactory(null);
		expandablelistview = (ExpandableListView) dialog.findViewById(R.id.expandablelistview);
		expandablelistview.setGroupIndicator(null); // 去掉自带的伸缩图标
		groupList = new ArrayList<String>();
		childList = new ArrayList<List<String>>();
		expandAdapter = new FactoryAdapter(context,listener);
		Button sreachBtn=(Button)dialog.findViewById(R.id.sreachBtn);
	 sreachWord=(EditText)dialog.findViewById(R.id.sreachWord);
	 sreachWord.addTextChangedListener(new MyTestWatcher(context,listener));
		sreachBtn.setOnClickListener(new SreachListener(context,listener));
		
		int length = zmIndex.length;
		for (int i = 0; i < length; i++) {
			groupList.add(zmIndex[i]);
			Map<String,String> subMap = map.get(zmIndex[i]);
			if(null!=subMap && !subMap.isEmpty() && 0<subMap.size()){
				tempList = new ArrayList<String>();
				Iterator iter = subMap.keySet().iterator();
				while(iter.hasNext()){
					String key = (String)iter.next();
					tempList.add(key);
				}
			}
			childList.add(tempList);
		}
		expandAdapter.fatherStr = groupList;
		expandAdapter.sonStr = childList;
		expandablelistview.setAdapter(expandAdapter);
		return dialog;
	}
	
	class MyTestWatcher implements TextWatcher{
		private Context context;
		private OnClickListener listener;
		

		public MyTestWatcher(Context context, OnClickListener listener) {
			super();
			this.context = context;
			this.listener = listener;
		}

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
			findWord(context,sreachWord.getText().toString(), listener);
		}
		
	}
	
	class SreachListener implements OnClickListener {
		Context context;
		OnClickListener listener;
		public SreachListener(Context context,OnClickListener listener){
			this.context=context;
			this.listener=listener;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String word=sreachWord.getText().toString();
			if(word.equals("")){
				ToastDialog.show(context, "请输入要查找的关键字",ToastDialog.ERROR);
			}else{
				findWord(context,word,listener);
			}
		}
	};
	
	private void findWord(Context context,String word,OnClickListener listener){
		loadFactory(word);
		groupList = new ArrayList<String>();
		childList = new ArrayList<List<String>>();
		expandAdapter = new FactoryAdapter(context,listener);
		
		int length = zmIndex.length;
		for (int i = 0; i < length; i++) {
			
			Map<String,String> subMap = map.get(zmIndex[i]);
			tempList = new ArrayList<String>();
			if(null!=subMap && !subMap.isEmpty() && 0<subMap.size()){
				Iterator iter = subMap.keySet().iterator();
				while(iter.hasNext()){
					String key = (String)iter.next();
					tempList.add(key);
				}
			}
			if(!tempList.isEmpty()){
				childList.add(tempList);
				groupList.add(zmIndex[i]);
			}
			
		}
		expandAdapter.fatherStr = groupList;
		expandAdapter.sonStr = childList;
		expandablelistview.setAdapter(expandAdapter);
		
		
	}
	
	private void loadFactory(String text) {
		map = new HashMap<String,Map<String,String>>();
		ThirdPartyService thirdPartyService = new ThirdPartyService();
		List<Manufacturer> list = thirdPartyService.getFactory(text);
		
		Map a = new HashMap<String,String>();
		Map b = new HashMap<String,String>();
		Map c = new HashMap<String,String>();
		Map d = new HashMap<String,String>();
		Map e = new HashMap<String,String>();
		Map f = new HashMap<String,String>();
		Map g = new HashMap<String,String>();
		Map h = new HashMap<String,String>();
		Map i = new HashMap<String,String>();
		Map j = new HashMap<String,String>();
		Map k = new HashMap<String,String>();
		Map l = new HashMap<String,String>();
		Map m = new HashMap<String,String>();
		Map n = new HashMap<String,String>();
		Map o = new HashMap<String,String>();
		Map p = new HashMap<String,String>();
		Map q = new HashMap<String,String>();
		Map r = new HashMap<String,String>();
		Map s = new HashMap<String,String>();
		Map t = new HashMap<String,String>();
		Map u = new HashMap<String,String>();
		Map v = new HashMap<String,String>();
		Map w = new HashMap<String,String>();
		Map x = new HashMap<String,String>();
		Map y = new HashMap<String,String>();
		Map z = new HashMap<String,String>();
		if(list!=null&&!list.isEmpty()){
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			Manufacturer manufacturer = (Manufacturer)iter.next();
			if("A".equals(manufacturer.getZmIndex())){
				a.put(manufacturer.getName(), manufacturer.getId());
			}else if("B".equals(manufacturer.getZmIndex())){
				b.put(manufacturer.getName(), manufacturer.getId());
			}else if("C".equals(manufacturer.getZmIndex())){
				c.put(manufacturer.getName(), manufacturer.getId());
			}else if("D".equals(manufacturer.getZmIndex())){
				d.put(manufacturer.getName(), manufacturer.getId());
			}else if("E".equals(manufacturer.getZmIndex())){
				e.put(manufacturer.getName(), manufacturer.getId());
			}else if("F".equals(manufacturer.getZmIndex())){
				f.put(manufacturer.getName(), manufacturer.getId());
			}else if("G".equals(manufacturer.getZmIndex())){
				g.put(manufacturer.getName(), manufacturer.getId());
			}else if("H".equals(manufacturer.getZmIndex())){
				h.put(manufacturer.getName(), manufacturer.getId());
			}else if("I".equals(manufacturer.getZmIndex())){
				i.put(manufacturer.getName(), manufacturer.getId());
			}else if("J".equals(manufacturer.getZmIndex())){
				j.put(manufacturer.getName(), manufacturer.getId());
			}else if("K".equals(manufacturer.getZmIndex())){
				k.put(manufacturer.getName(), manufacturer.getId());
			}else if("L".equals(manufacturer.getZmIndex())){
				l.put(manufacturer.getName(), manufacturer.getId());
			}else if("M".equals(manufacturer.getZmIndex())){
				m.put(manufacturer.getName(), manufacturer.getId());
			}else if("N".equals(manufacturer.getZmIndex())){
				n.put(manufacturer.getName(), manufacturer.getId());
			}else if("O".equals(manufacturer.getZmIndex())){
				o.put(manufacturer.getName(), manufacturer.getId());
			}else if("P".equals(manufacturer.getZmIndex())){
				p.put(manufacturer.getName(), manufacturer.getId());
			}else if("Q".equals(manufacturer.getZmIndex())){
				q.put(manufacturer.getName(), manufacturer.getId());
			}else if("R".equals(manufacturer.getZmIndex())){
				r.put(manufacturer.getName(), manufacturer.getId());
			}else if("S".equals(manufacturer.getZmIndex())){
				s.put(manufacturer.getName(), manufacturer.getId());
			}else if("T".equals(manufacturer.getZmIndex())){
				t.put(manufacturer.getName(), manufacturer.getId());
			}else if("U".equals(manufacturer.getZmIndex())){
				u.put(manufacturer.getName(), manufacturer.getId());
			}else if("V".equals(manufacturer.getZmIndex())){
				v.put(manufacturer.getName(), manufacturer.getId());
			}else if("W".equals(manufacturer.getZmIndex())){
				w.put(manufacturer.getName(), manufacturer.getId());
			}else if("X".equals(manufacturer.getZmIndex())){
				x.put(manufacturer.getName(), manufacturer.getId());
			}else if("Y".equals(manufacturer.getZmIndex())){
				y.put(manufacturer.getName(), manufacturer.getId());
			}else if("Z".equals(manufacturer.getZmIndex())){
				z.put(manufacturer.getName(), manufacturer.getId());
			}
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

	
	/*
	public Dialog getDialog(final Context context, final String name,Map<String,Map<String,String>> map,OnChildClickListener listener) {

		final Dialog dialog = new Dialog(context, R.style.Theme_ShareDialog);
		dialog.setContentView(R.layout.factory_page);
		this.map = map;
		expandablelistview = (ExpandableListView) dialog.findViewById(R.id.expandablelistview);
		expandablelistview.setGroupIndicator(null); // 去掉自带的伸缩图标
		List groupList = new ArrayList<Map<String,String>>();
		List childList = new ArrayList<List<Map<String,String>>>();
		
		int length = zmIndex.length;
		for (int i = 0; i < length; i++) {
			Map<String,String>
			
		}
		
		
		
		for (int i = 0; i < length; i++) {
			groupList.add(zmIndex[i]);
			Map<String,String> subMap = map.get(zmIndex[i]);
			if(null!=subMap && !subMap.isEmpty() && 0<subMap.size()){
				tempList = new ArrayList<String>();
				Iterator iter = subMap.keySet().iterator();
				while(iter.hasNext()){
					String key = (String)iter.next();
					tempList.add(key);
				}
			}
			childList.add(tempList);
		}
	SimpleExpandableListAdapter expandAdapter = new SimpleExpandableListAdapter(context, null, length, length, zmIndex, null, null, length, length, zmIndex, null);
			
		expandAdapter.fatherStr = groupList;
		expandAdapter.sonStr = childList;
		expandablelistview.setAdapter(expandAdapter);
		return dialog;
	}
	*/
}