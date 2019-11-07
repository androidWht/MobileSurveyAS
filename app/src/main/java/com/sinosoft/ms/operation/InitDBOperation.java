package com.sinosoft.ms.operation;

import android.content.Context;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.operation.DBController;

/**
 * 
 * 系统名 		自助查勘系统 
 * 类名			InitDBOperation
 * 类作用		初始化数据库
 * @author 吴海杰
 * @createTime 2013-11-28 下午7:28:07
 */
public class InitDBOperation extends Operation implements TableName{
	
	public InitDBOperation(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化数据库
	 */
	public void initDatabase(){
		DBController dc = new DBController(context, "imageCenter");
		dc.getObject(null, null, ImageCenterBean.class);
	}
}
