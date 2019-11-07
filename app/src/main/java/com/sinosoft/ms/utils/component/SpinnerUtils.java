package com.sinosoft.ms.utils.component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.Bank;
import com.sinosoft.ms.model.SimpleItem;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘
 * 子系统名：
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午11:52:57
 */

public class SpinnerUtils {
	
	public static void setSpinnerData(Context context,Spinner spinner, Map<String, String> map,
			String code,String spinnerFlag) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				context, R.layout.item_simple_spinner){

					@Override
					public View getDropDownView(int position, View convertView,
							ViewGroup parent) {
						// TODO Auto-generated method stub
						View view = super.getDropDownView(position, convertView, parent);

				        // simple_spinner_item 有 android.R.id.text1 TextView视图:         
			            TextView text = (TextView)view.findViewById(android.R.id.text1);
			            text.setTextColor(Color.BLACK);//choose your color :)         

				        return view;
					}
			
		};
		int index = 0;
		int tempIndex = 0;
		adapter.add("请选择");
		if (null != map && !map.isEmpty()) {
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				tempIndex++;
				String key = (String) iter.next();
				String value = map.get(key);
				if (code.equals(key)) {
					index = tempIndex;
				}
				if(StringUtils.isNotEmpty(spinnerFlag))
				{
					if(spinnerFlag.equalsIgnoreCase("IndemnityDuty"))  //商业险赔偿责任
					{
						String tmpVal = value;
						if(key.equals("4") || key.equals("9"))
						{
							value = tmpVal +(" (0%)");
						}
						else if(key.equals("0") || key.equals("5"))
						{
							value = tmpVal +(" (100%)");
						}	
						else if(key.equals("1"))
						{
							value = tmpVal +(" (70%)");
						}	
						else if(key.equals("2"))
						{
							value = tmpVal +(" (50%)");
						}	
						else if(key.equals("3"))
						{
							value = tmpVal +(" (30%)");
						}							
					}//
				}//
				
				adapter.add(value);
			}

		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(index);

	}
	//设置物损
	public static void setPropSpinnerData(Context context,Spinner spinner, Map<String, String> map,String code) 
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.item_simple_spinner)
		{

				@Override
				public View getDropDownView(int position, View convertView,ViewGroup parent) 
				{
					// TODO Auto-generated method stub
					View view = super.getDropDownView(position, convertView, parent);
		            TextView text = (TextView)view.findViewById(android.R.id.text1);
		            text.setTextColor(Color.BLACK);//choose your color :)         
			        return view;
				}
			
		};
		int index = 0;
		int tempIndex = 0;
		adapter.add("请选择");
		if (null != map && !map.isEmpty()) 
		{
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) 
			{
				tempIndex++;
				String key = (String) iter.next();
				String value = map.get(key);
				if(StringUtils.isNotEmpty(code)){
					if (code.equals(key)) {
						index = tempIndex;
					}
				}
				adapter.add(value);
			}

		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(index);

	}
	
	//设置人伤
	public static void setPersonSpinnerData(Context context,Spinner spinner, Map<String, String> map,String code) 
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.item_simple_spinner)
		{

				@Override
				public View getDropDownView(int position, View convertView,ViewGroup parent) 
				{
					// TODO Auto-generated method stub
					View view = super.getDropDownView(position, convertView, parent);
		            TextView text = (TextView)view.findViewById(android.R.id.text1);
		            text.setTextColor(Color.BLACK);//choose your color :)         
			        return view;
				}
			
		};
		int index = 0;
		int tempIndex = 0;
		adapter.add("请选择");
		if (null != map && !map.isEmpty()) 
		{
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) 
			{
				tempIndex++;
				String key = (String) iter.next();
				String value = map.get(key);
				if(StringUtils.isNotEmpty(code)){
					if (code.equals(key)) {
						index = tempIndex;
					}
				}
				adapter.add(value);
			}

		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(index);

	}
	
	
	
	//设置银行列表
	public static void setSpinnerData(Context context,Spinner spinner, List<Bank> bankList,
			String code) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				context, R.layout.item_simple_spinner);
		int index = 0;
		int tempIndex = 0;
		adapter.add("请选择");
		if (null != bankList && !bankList.isEmpty()) {
			for(Bank bank : bankList){
				tempIndex++;
				adapter.add(bank.getValue());
				if(code.equals(bank.getValue())){
					index = tempIndex;
				}
			}
		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(index);

	}
	//银行列表获取Key
	public static String getKey(String text,List<Bank> bankList){
		String keyStr = "";
		if(StringUtils.isNotEmpty(text) 
				&& null!=bankList && !bankList.isEmpty()){
			for(Bank bank : bankList){
				if(bank.getValue().equals(text)){
					keyStr = bank.getCode();
				}
			}
		}
		return keyStr;
	}
	
	/**
	 * 获取字典 key
	 * @param text 字典的 value
	 * @param map 字典集合
	 * @return key
	 */
	public static String getKey(String text,Map<String, String> map){
		String keyStr = "";
		if(StringUtils.isNotEmpty(text) 
				&& null!=map && !map.isEmpty()){
			Iterator iter = map.keySet().iterator();
			while(iter.hasNext()){
				String key = (String)iter.next();
				String value = map.get(key);
				if(StringUtils.isNotEmpty(value)
						&& value.equals(text)){
					keyStr = key;
					break;
				}
			}
		}
		return keyStr;
	}
	

	/**
	 * 获取字典 value
	 * @param text 字典的 key
	 * @param map 字典集合
	 * @return value
	 */
	public static String getValue(String text,Map<String, String> map){
		String value = "";
		if(StringUtils.isNotEmpty(text) && null!=map && !map.isEmpty()){
			value = map.get(text);
		}
		return null==value?"":value;
	}
	

	public static void set(Context context,Spinner spinner, Map<String, SimpleItem> simpMap,
			String code) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				context, R.layout.item_simple_spinner);
		int index = 0;
		int tempIndex = 0;
		adapter.add("请选择");
		if (null != simpMap && !simpMap.isEmpty()) {
			Iterator<String> iter = simpMap.keySet().iterator();
			while (iter.hasNext()) {
				tempIndex++;
				String key = (String) iter.next();
				SimpleItem value = simpMap.get(key);
				if (code.equals(key)) {
					index = tempIndex;
				}
				adapter.add(value.getTypeName());
			}

		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(index);
	}
	
	/**
	 * 获取字典 key
	 * @param text 字典的 value
	 * @param map 字典集合
	 * @return key
	 */
	public static String key(String text,Map<String, SimpleItem> map){
		String keyStr = "";
		if(StringUtils.isNotEmpty(text) 
				&& null!=map && !map.isEmpty()){
			Iterator iter = map.keySet().iterator();
			while(iter.hasNext()){
				String key = (String)iter.next();
				SimpleItem simpleItem = map.get(key);
				if(null!=simpleItem && simpleItem.getTypeName().equals(text)){
					keyStr = key;
					break;
				}
			}
		}
		return keyStr;
	}
	
	/**
	 * 获取字典 value
	 * @param text 字典的 key
	 * @param map 字典集合
	 * @return value
	 */
	public static SimpleItem value(String text,Map<String, SimpleItem> map){
		SimpleItem value = null;
		if(StringUtils.isNotEmpty(text) && null!=map && !map.isEmpty()){
			value = map.get(text);
		}
		return null==value ? null : value;
	}
}

