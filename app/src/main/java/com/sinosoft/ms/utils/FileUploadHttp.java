package com.sinosoft.ms.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import com.alibaba.fastjson.JSONObject;
import com.sinosoft.ms.R;

public class FileUploadHttp extends AsyncTask<Object, Integer, Long>
{

	String uploadUrl;
	String file[];
	String[] imgNames;
	String[] types;
	String registNo;

	NotificationManager manager;
	Notification notification;
	Context context;
	int length;
	private float progress;
	CompleteListener completeListener;
	boolean[] isSuccess;

	public FileUploadHttp(Context context, CompleteListener completeListener, boolean[] isSuccess)
	{
		// this.progressBar=progressBar;
		this.context = context;
		this.isSuccess = isSuccess;
		this.completeListener = completeListener;
		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification(R.drawable.ic_launcher, "图片上传中...", System.currentTimeMillis());
		PendingIntent intent = PendingIntent.getActivity(context, 0, new Intent(), 0);
		notification.contentIntent = intent;
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		RemoteViews v = new RemoteViews(context.getPackageName(), R.layout.layout_notification_upload);
		notification.contentView = v;
		notification.contentView.setProgressBar(R.id.progressBar1, 100, 0, true);

		manager.notify(1000, notification);

	}

	private void uploadFile(File uploadFile, String actionUrl, String newName,String imgName,String type, int index)
	{
		String ret = "0";
		try
		{
			///actionUrl="http://58.17.45.9:7006/hbFileManager/fileManageController/doUpload";
			///actionUrl="http://10.1.218.95:7006/hbFileManager/fileManageController/doUpload";
			
			
			HttpPost post = new HttpPost(actionUrl);
			HttpClient httpClient = new DefaultHttpClient();
			MultipartEntity entity = new MultipartEntity();
			
			entity.addPart("bussNo", new StringBody(registNo));
			entity.addPart("typeSelect01", new StringBody("Claim"));  //类别  例如01：承保 理赔:Claim

			String typeSelect02=AppConstants.getOutercodeByCodecode(type);
			
			entity.addPart("typeSelect02", new StringBody(typeSelect02));
			entity.addPart("typeSelect03", new StringBody(type));
			
			///entity.addPart("typePathName", new StringBody("理赔业务-公共类资料-现场照片"));
			entity.addPart("typePathName", new StringBody("android--"));
			// entity.addPart(entry.getKey(), new StringBody(entry.getValue()));
			if (file != null && uploadFile.exists())
			{
				entity.addPart("file", new FileBody(uploadFile));
			}
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			int stateCode = response.getStatusLine().getStatusCode();
			if(stateCode == 200)
			{
				String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    String result = EntityUtils.toString(response.getEntity());
                    if (StringUtils.isEmpty(result)) {
                        return ;
                    }else{
                    /**把json字符串转换成json对象**/
                    	Map map=(Map)JSONObject.parseObject(result);
                    	//JSONObject obj=(JSONObject) JSONObject.parse(result);
                    	Integer retN=(Integer)map.get("code");
                    	if(retN != null)
                    		ret = String.valueOf(retN);
                    	String msg=(String)map.get("msg");
                       ///jsonResult = JSONObject.fromObject(result);
                    }
                } catch (Exception e) {
                    Log.e("上传失败:" + registNo,e.getMessage());
                }
				
				
			}else
			{
				Log.e("error", "上传失败");
			}
			post.abort();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
			/* 将Response显示于Dialog */
			if (completeListener != null)
			{
				if (ret.trim().equals("1"))
				{
					completeListener.right(index);
				}
				else
				{
					completeListener.error(index);
				}

			}
			
			
		}


	}

	/* 上传文件至Server的方法 */
	private void uploadFile1(File uploadFile, String actionUrl, String newName, int index)
	{
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		StringBuffer b = new StringBuffer();
		// RemoteViews.setMax((int)uploadFile.length());
		try
		{
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());

			ds.writeBytes(twoHyphens + boundary + end);
			if (!newName.equals(""))
			{
				ds.writeBytes("Content-Disposition: form-data; " + "name=\"file1\";filename=\"" + newName + "\"" + end);

			}

			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(uploadFile);

			/* 设置每次写入1024bytes */
			int bufferSize = 1024 * 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1)
			{
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);

			}

			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();

			InputStream is = con.getInputStream();
			int ch;

			while ((ch = is.read()) != -1)
			{
				b.append((char) ch);
			}

			Log.d("debug", "上传成功" + b.toString());
			ds.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();

			Log.e("error", "上传失败" + e.getMessage());
		}
		finally
		{
			/* 将Response显示于Dialog */
			if (completeListener != null)
			{
				if (b.toString().trim().equals("OK"))
				{
					completeListener.right(index);
				}
				else
				{
					completeListener.error(index);
				}

			}

		}
	}

	@Override
	protected Long doInBackground(Object... params)
	{
		// TODO Auto-generated method stub
		Object[] object = (Object[]) params[0];
		if (object instanceof String[])
		{
			file = (String[]) object;
		}
		uploadUrl = (String) params[1];
		///if (params.length == 3)
		{
			registNo = (String) params[2];
		}
		///else
		///{
		///	registNo = "";
		///}
		Object[] object1 = (Object[]) params[3];
		if (object1 instanceof String[])
		{
			imgNames = (String[]) object1;
		}
		
		Object[] object2 = (Object[]) params[4];
		if (object1 instanceof String[])
		{
			types = (String[]) object2;
		}
				
		
		
		int length = file.length;
		progress = 100.0f / length;
		for (int i = 0; i < length; i++)
		{
			if (!isSuccess[i])
			{
				File f = new File(file[i]);
				String type=types[i];
				String imgName=imgNames[i];
				uploadFile(f, uploadUrl, registNo + "__" + f.getName().replaceAll("_", "m"), imgName,type,i);
				publishProgress((int) (i * progress));
			}

		}
		return 100L;
	}

	@Override
	protected void onPostExecute(Long result)
	{
		// TODO Auto-generated method stub

		manager.cancel(1000);
		super.onPostExecute(result);

		if (completeListener != null)
		{
			completeListener.complete(0);
		}

	}

	@Override
	protected void onProgressUpdate(Integer... values)
	{
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);

		int value = values[0];
		notification.contentView.setTextViewText(R.id.textview1, "文件上传中(" + value + "%)");
		notification.contentView.setProgressBar(R.id.progressBar1, 100, values[0], false);

		manager.notify(1000, notification);

	}

	public interface CompleteListener
	{
		public void error(int index);

		public void right(int index);

		public void complete(int index);

	}

}
