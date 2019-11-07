package com.sinosoft.ms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.sinosoft.ms.R;

public class SearchWather implements TextWatcher{
	   
	
	public static final String PHONE="^(\\d{3,4}-)?(\\d{7,8}$)|(1[0-9]{10})";//电话
	public static final String RATE="^[0-9]{1}(.[0-9]{1,2})?$";//比例
	public static final String NUMBER2="^[0-9]{1,7}+(\\.[0-9]{1,2})?$";//小数两位
	public static final String NUMBER3="^[0-9]{1,7}+(\\.[0-9]{1,2})?$";//小数两位
	public static final String NUMBER4="^[0-9]{1,7}+(\\.[0-9]{1,2})?$";//7位整数，2位小数
	public static final String NUMBER="[0-9]+";//整数
	public static final String INT="[0-9]";
	public static final String WORK="[a-zA-Z0-9]";
	
    //监听改变的文本框  
    private EditText editText;  
    private String   regEx  =  "[^a-zA-Z0-9]"; 
    private Context context;
     private PopupWindow pop;
    public SearchWather(Context context,EditText editText,String regEx){  
        this.editText = editText; 
        this.regEx=regEx;
        this.context=context;
    }  

    @Override  
    public void onTextChanged(CharSequence ss, int start, int before, int count){  
      
    }  

    public void showText(String text){
    	/*TextView txt=new TextView(context);
    	txt.setText(text);
    	if(pop!=null){
    		
    		pop.showAsDropDown(editText);
    	}else{
    		pop = new PopupWindow(txt, editText.getWidth(), txt.getHeight());

    		pop.setFocusable(true);
    		pop.setOutsideTouchable(true);
    		pop.showAsDropDown(editText);
    	}*/
    
    }
    
    
    @Override  
    public void afterTextChanged(Editable s) {  
    	  String editable = editText.getText().toString();  
		if (StringUtils.isNotEmpty(editable)) {
			if (!stringFilter(editable.toString())) {

				// 设置新的光标所在位置

				// ToastDialog.show(context, "输入的格式不正确");
				editText.setBackgroundResource(R.drawable.edit_error_text);
				showText("输入的格式不正确");
				// editText.setText(str);
				// editText.setSelection(str.length());
			} else {
				editText.setBackgroundResource(R.drawable.edit_text);
			}
		}
    }  
    @Override  
    public void beforeTextChanged(CharSequence s, int start, int count,int after) {  

    }
    public  boolean stringFilter(String str){     
        // 只允许字母和数字       
                            
        Pattern   p   =   Pattern.compile(regEx);     
        Matcher   m   =   p.matcher(str);     
        return   m.matches();     
    }
    
    public static boolean checkNUMBER4(String str){     
        // 只允许字母和数字
        Pattern   p   =   Pattern.compile(NUMBER4);     
        Matcher   m   =   p.matcher(str);     
        return   m.matches();     
    }
}  


