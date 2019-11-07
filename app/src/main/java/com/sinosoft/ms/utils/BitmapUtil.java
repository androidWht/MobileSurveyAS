package com.sinosoft.ms.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.image.camera.Preview;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.utils.component.BitmapUtils;
import com.sinosoft.ms.utils.db.ImageCenterDatabase;

public class BitmapUtil {

	/**
	 * 保存图片临时文件
	 * 
	 * @param bitmapdata
	 * @param imagePathTemp
	 * @throws IOException
	 */
	public static void saveImageTemp(byte[] bitmapdata, String imagePathTemp)
			throws IOException {
		FileOutputStream fos = null;
		try {
			File f = new File(imagePathTemp);
			if (!f.exists()) {
				f.createNewFile();
			}
			fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.close();
		} finally {
			fos.close();
		}
	}

	/** 保存方法 */
	public static void saveBitmap(Bitmap bitmap, String path) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 删除图片临时文件
	 * 
	 * @param imagePathTemp
	 * @throws IOException
	 */
	public static void deleteImageTemp(String imagePathTemp) {
		File file = new File(imagePathTemp);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
	 * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
	 * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
	 * 
	 * @param imagePath
	 *            图像的路径
	 * @param width
	 *            指定输出图像的宽度
	 * @param height
	 *            指定输出图像的高度
	 * @return 生成的缩略图
	 */
	public static Bitmap getImageThumbnail(String imagePath, int w1, int h1) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int height = h1;
		int width = w1;
		if (h > w) {
			width = h1;
			height = w1;
		}
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		/*
		 * //调整方向(图片横竖屏显示纠正) Matrix matrix = new Matrix(); matrix.reset();
		 * matrix.postRotate(90);
		 */
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, null, true);
		return bitmap;
	}

	/**
	 * 将Drawable 转换为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap toBitmap(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
	}

	/**
	 * 将Bitmap 转换�?Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable toDrawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}

	public static Bitmap rotate(Bitmap bitmap, int rotation) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.postRotate(rotation);
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	/**
	 * 压缩图片
	 * 
	 * @param data
	 * @throws IOException
	 */
	public static String savePhoto(Context context, String imagePath,
			int orientation, String reportNo, String taskId, String registId,
			String position, String imageType) throws IOException {
		Bitmap bmp = BitmapUtil.getImageThumbnail(imagePath, 640, 480);
		bmp = BitmapUtil.rotate(bmp, orientation);
		// 加水印
		User user = UserCashe.getInstant().getUser();
		// 获取经纬度
		bmp = new BitmapUtils().createBitmap(bmp, context.getResources(),
				R.drawable.image_logo, 1, user.getName(), position,
				DateTimeFactory.getInstance().getDateTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS"); // 格式化时间
		String filename = format.format(new Date()) + "_1"
				+ AppConstants.IMAGE_FORMAT;
		
		File fileFolder = new File(Environment.getExternalStorageDirectory()
				+ "/finger/");
		if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
			fileFolder.mkdir();
		}
		File jpgFile = new File(fileFolder, filename);
		FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
		bmp.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
		outputStream.flush();
		outputStream.close(); // 关闭输出流

		try {
			ImageCenterBean bean = getFileInfo(jpgFile, reportNo, taskId,
					registId, position, imageType);
			ImageCenterDatabase database = new ImageCenterDatabase(context);
			database.insert("imageCenter", bean);
			// list.add(bmp);
			// pathList.add(jpgFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jpgFile.getAbsolutePath();
	}

	/**
	 * 取得图片文件的信息
	 * 
	 * @param file
	 *            文件File
	 * @return bean数据
	 */
	public static ImageCenterBean getFileInfo(File file, String reportNo,
			String taskId, String registId, String position, String imageType) {
		ImageCenterBean bean = new ImageCenterBean();
		bean.setReportNo(reportNo);
		bean.setTaskId(taskId);
		bean.setRegistId(registId);
		bean.setImgName(file.getName());
		bean.setPath(file.getAbsolutePath());
		Time t = new Time();
		t.setToNow();
		bean.setCreateDate("" + t.year + "-" + (t.month + 1) + "-" + t.monthDay);
		bean.setImgSize(file.length() / 1024 + "kb");
		bean.setLocation(position);
		bean.setType(imageType);
		bean.setIsUpload("0");
		return bean;
	}
}