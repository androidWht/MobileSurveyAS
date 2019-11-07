package com.sinosoft.ms.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.AsyncTask;
import android.util.Log;

import com.sinosoft.ms.model.ImageCenterBean;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午9:44:51
 */

public class ImageCenterDatabase {
	private SQLiteDatabase database;
	private Context context;

	public ImageCenterDatabase(Context context) {

		this.context = context;
	}

	/**
	 * 插入影像中心数据库
	 * 
	 * @param table
	 * @param bean
	 * @throws Exception
	 */
	public void insert(String table, ImageCenterBean bean) throws Exception {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception(e1.getMessage());
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {
			@Override
			public void onRollback() {
				Log.d("imageCenter insert", "rollback");
			}
			@Override
			public void onCommit() {
				Log.d("imageCenter insert", "commit");
			}
			@Override
			public void onBegin() {
				Log.d("imageCenter insert", "begin");
			}
		});

		try {
			ContentValues values = new ContentValues();
			values.putNull("id");
			values.put("reportNo", bean.getReportNo());
			values.put("registId", bean.getRegistId());
			values.put("taskId", bean.getTaskId());
			values.put("imgName", bean.getImgName());
			values.put("location", bean.getLocation());
			values.put("imgSize", bean.getImgSize());
			values.put("type", bean.getType());
			values.put("path", bean.getPath());
			values.put("createDate", bean.getCreateDate());
			values.put("isUpload",bean.getIsUpload());
			long rowId = database.insert(table, null, values);
			if (rowId == -1) {
				throw new IllegalArgumentException("影像资料插入错误");
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 删除影像中心数据库数据
	 * 
	 * @param table
	 * @param where
	 * @param whereArgs
	 * @throws Exception
	 */
	public void delete(String table, String where, String whereArgs[])
			throws Exception {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception(e1.getMessage());
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {

			@Override
			public void onRollback() {
				Log.d("imageCenter delete", "rollback");
			}
			@Override
			public void onCommit() {
				Log.d("imageCenter delete", "commit");
			}
			@Override
			public void onBegin() {
				Log.d("imageCenter delete", "begin");
			}
		});

		try {
			int num = database.delete(table, where, whereArgs);
//			if (num == 0) {
//				throw new IllegalArgumentException("影像资料删除错误");
//			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 更新影像中心数据库
	 * 
	 * @param table
	 * @param where
	 * @param whereArgs
	 * @param bean
	 * @throws Exception
	 */
	public void update(String table, String where, String whereArgs[],
			ImageCenterBean bean) throws Exception {
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception(e1.getMessage());
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {
			@Override
			public void onRollback() {
				Log.d("imageCenter insert", "rollback");
			}
			@Override
			public void onCommit() {
				Log.d("imageCenter insert", "commit");
			}
			@Override
			public void onBegin() {
				Log.d("imageCenter insert", "begin");
			}
		});

		try {
			ContentValues values = new ContentValues();
			values.putNull("id");
			values.put("reportNo", bean.getReportNo());
			values.put("taskId", bean.getTaskId());
			values.put("registId", bean.getRegistId());
			values.put("imgName", bean.getImgName());
			values.put("location", bean.getLocation());
			values.put("imgSize", bean.getImgSize());
			values.put("type", bean.getType());
			values.put("path", bean.getPath());
			values.put("createDate", bean.getCreateDate());
			values.put("isUpload",bean.getIsUpload());
			int num = database.update(table, values, where, whereArgs);
			if (num == 0) {
				throw new IllegalArgumentException("影像资料修改错误");
			}
			database.setTransactionSuccessful();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			database.endTransaction();
			database.close();
		}
	}
 
	public void upadateFlag(String registNo){
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
			database.execSQL("update imageCenter set isUpload='1' where isUpload='0' and reportNo='"+registNo+"'" );
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			if(database!=null){
				database.close();
			}
		}
	}
	
	/**
	 * 查询影像中心
	 * 
	 * @param table
	 * @param where
	 * @param whereArgs
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<ImageCenterBean> select(String table, String where,
			String whereArgs[], String orderBy) throws Exception {
		List<ImageCenterBean> list = new ArrayList<ImageCenterBean>();
		Cursor cursor = null;
		try {
			database = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception(e1.getMessage());
		}
		database.beginTransactionWithListener(new SQLiteTransactionListener() {
			@Override
			public void onRollback() {
				Log.d("imageCenter select", "rollback");
			}

			@Override
			public void onCommit() {
				// TODO Auto-generated method stub
				Log.d("imageCenter select", "commit");
			}
			@Override
			public void onBegin() {
				Log.d("imageCenter select", "begin");
			}
		});
		try {
			cursor = database.query(table, null, where, whereArgs, null, null,
					orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				ImageCenterBean bean = new ImageCenterBean();
				bean.setId(cursor.getInt(cursor.getColumnIndex("id"))+"");
				bean.setReportNo(cursor.getString(cursor
						.getColumnIndex("reportNo")));
				bean.setTaskId(cursor.getString(cursor
						.getColumnIndex("taskId")));
				bean.setRegistId(cursor.getString(cursor
						.getColumnIndex("registId")));
				bean.setCreateDate(cursor.getString(cursor
						.getColumnIndex("createDate")));
				bean.setImgName(cursor.getString(cursor
						.getColumnIndex("imgName")));
				bean.setLocation(cursor.getString(cursor
						.getColumnIndex("location")));
				bean.setImgSize(cursor.getString(cursor.getColumnIndex("imgSize")));
				bean.setType(cursor.getString(cursor.getColumnIndex("type")));
				bean.setPath(cursor.getString(cursor.getColumnIndex("path")));
				bean.setIsUpload(cursor.getString(cursor.getColumnIndex("isUpload")));
				list.add(bean);
				cursor.moveToNext();
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			database.endTransaction();
			database.close();
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}
	
	public List<ImageCenterBean> selectNotUpload(String registNo){
		List<ImageCenterBean> imageDatas = null;
		try {
			imageDatas = select("imageCenter", "reportNo=? and isUpload=?",
					new String[] { registNo,"0" }, "createDate");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageDatas;
	}
	
	/**
	 * 选择报案ID下指定类型的图片
	 * @param registNo
	 * @param type
	 */
	public void asyncSelectByType(final String registNo,final String imageType,final DatabaseCallback callback){
		new AsyncTask<Void, Void, List<ImageCenterBean>>(){

			@Override
			protected List<ImageCenterBean> doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				List<ImageCenterBean> imageDatas = null;
				try {
					imageDatas = select("imageCenter", "reportNo=? and type=?",
							new String[] { registNo,imageType }, "createDate");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return imageDatas;

			}

			@Override
			protected void onPostExecute(List<ImageCenterBean> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				callback.post(result);
			}
			
		}.execute();
	}
	
	/**
	 * 选择报案ID下未上传的图片
	 * @param taskId
	 */
	public void asyncSelectNotUpload(final String taskId,final DatabaseCallback callback){
		new AsyncTask<Void, Void, List<ImageCenterBean>>(){

			@Override
			protected List<ImageCenterBean> doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				List<ImageCenterBean> imageDatas = null;
				try {
					imageDatas = select("imageCenter", "reportNo=? and isUpload=?",
							new String[] { taskId,"0" }, "createDate");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return imageDatas;

			}

			@Override
			protected void onPostExecute(List<ImageCenterBean> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				callback.post(result);
			}
			
		}.execute();
	}
	
	/**
	 * 选择报案ID下的所有图片
	 * @param taskId
	 * @param callback
	 */
	public void asyncSelect(final String taskId,final DatabaseCallback callback){
		new AsyncTask<Void, Void, List<ImageCenterBean>>(){

			@Override
			protected List<ImageCenterBean> doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				List<ImageCenterBean> imageDatas = null;
				try {
					imageDatas = select("imageCenter", "reportNo=?",
							new String[] { taskId }, "createDate");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return imageDatas;

			}

			@Override
			protected void onPostExecute(List<ImageCenterBean> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				callback.post(result);
			}
			
		}.execute();
	}
	
	public interface DatabaseCallback{
		void post(List<ImageCenterBean> list);
	}
}
