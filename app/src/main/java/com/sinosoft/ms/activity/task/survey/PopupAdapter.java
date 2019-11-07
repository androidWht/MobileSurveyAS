package com.sinosoft.ms.activity.task.survey;

import java.util.List;

import com.sinosoft.ms.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2017 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zkr
 * @createTime 下午1:38:32
 */

/** 
* 自定义适配器,PopupWindow listView的数据处理 
*  
 * @author royal 
*  
*/ 
class PopupAdapter extends BaseAdapter {  

	private LayoutInflater layoutInflater;  
	private Context context;  
	private List<String> licenseNos;
	private OnTouchListener onTouchListener;
	public PopupAdapter() 
	{  
	 
	}  
    public PopupAdapter(Context context,List<String> licenseNos,OnTouchListener onTouchListener) {  
            this.context = context; 
            this.licenseNos =licenseNos;
            this.onTouchListener =onTouchListener;
    }  
  
    @Override 
	public int getCount() {  
	     return licenseNos.size();  
	}  

    @Override 
    public Object getItem(int position) {  
         return null;  
    }  
 
    @Override 
    public long getItemId(int position) {  
             return position;  
    }  
  
    @Override 
    public View getView(final int position, View convertView, ViewGroup parent) {  
       Holder holder = null;  
       final String name = licenseNos.get(position);  
       if (convertView == null) {  
          layoutInflater = LayoutInflater.from(context);  
          convertView = layoutInflater.inflate(R.layout.popup, null);  
          holder = new Holder();  
          holder.tv = (TextView) convertView.findViewById(R.id.tv_account);  
          convertView.setTag(holder);  
        } 
        else {  
            holder = (Holder) convertView.getTag();  
        }  
        if (holder != null) 
        {  
            convertView.setId(position);  
            holder.setId(position);  
            holder.tv.setText(name);  
            holder.tv.setTag(name);
            holder.tv.setOnTouchListener(onTouchListener);
        }  
        return convertView;  
    }  
    class Holder {  
    	TextView tv;  
    	void setId(int position) {  
    		tv.setId(position);  
    	}  
    }  
 } 