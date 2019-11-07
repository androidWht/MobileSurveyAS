package com.sinosoft.ms.utils.component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.MyUtils;

public class BitmapUtils {

	public Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {
		// 图片源
		// Bitmap bm = BitmapFactory.decodeStream(getResources()
		// .openRawResource(id));
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 设置想要的大小
		int newWidth1 = newWidth;
		int newHeight1 = newHeight;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth1) / width;
		float scaleHeight = ((float) newHeight1) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	public Bitmap saveBySize(byte[] bytes, int maxWidth, int maxHeight,
			String location, String dateStr) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
			opts.inJustDecodeBounds = true;
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int desWidth = 0;
			int desHeight = 0;
			double ratio = 0.0;
			if (maxWidth <= 0) {
				// 缩放比例

				if (srcWidth > srcHeight) {
					ratio = srcWidth / maxWidth;
					desWidth = maxWidth;
					desHeight = (int) (srcHeight / ratio);
				} else {
					ratio = srcHeight / maxHeight;
					desHeight = maxHeight;
					desWidth = (int) (srcWidth / ratio);
				}
			} else {
				ratio = 1.0;
				desHeight = srcHeight;
				desWidth = srcWidth;
			}
			// 设置输出宽度、高度
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inSampleSize = (int) Math.pow(2, ratio + 1);
			newOpts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
					newOpts);
			bitmap = createBitmap(bitmap, location, dateStr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bitmap;
	}

	/**
	 * 给图片添加水印
	 * 
	 * @param src
	 *            目标图片
	 * @param str
	 *            水印内容
	 * @param dateStr
	 *            水印时间
	 * @return 修改后图片
	 */
	public Bitmap createBitmap(Bitmap src, String location, String dateStr) {
		int w = src.getWidth();
		int h = src.getHeight();
		Bitmap bmpTemp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(bmpTemp);
		Paint p = new Paint();
		// 绘入源图片
		canvas.drawBitmap(src, 0, 0, p);
		// 设置字体
		Typeface font = Typeface.create("宋体", Typeface.BOLD_ITALIC);
		p.setColor(Color.argb(125, 226, 234, 251));
		p.setTypeface(font);
		p.setTextSize(18);
		// 绘入水印图片
		canvas.drawText(dateStr + location, w - 290, h - 20, p);
		// 保存新图片
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return bmpTemp;
	}

	/**
	 * 给图片添加水印(新修改)
	 * 
	 * @param src
	 *            图片源
	 * @param res
	 *            系统资源
	 * @param id
	 *            LOGO图片ID
	 * @param type
	 *            图片类型 1.移动查勘拍照 2.本地上传
	 * @param surveyNo
	 *            查勘员编号
	 * @param position
	 *            经纬度
	 * @param dateStr
	 *            水印时间
	 * @return 添加水印后内容
	 */
	public Bitmap createBitmap(Bitmap src, Resources res, int id, int type,
			String surveyNo, String position, String dateStr) {
		int w = src.getWidth();
		int h = src.getHeight();

		Bitmap bmpTemp = src.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bmpTemp);
		Paint p = new Paint();

		p.setDither(true); // 获取跟清晰的图像采样
		p.setFilterBitmap(true);// 过滤一些

		// 绘入源图片
		canvas.drawBitmap(src, 0, 0, p);

		// 绘入LOGO
		Bitmap logo = BitmapFactory.decodeResource(res, id);
		// //canvas.drawBitmap(logo, 0, h - 100, p);

		int scale = (int) Math.pow(2, 1);
		logo = zoomImg(logo, logo.getWidth() / scale, logo.getHeight() / scale);
		// p.setColor(Color.BLACK);
		// canvas.drawRect(0, h + logo.getHeight() / 4 - logo.getHeight() * 2,
		// w,
		// h - logo.getHeight() / 4, p);
		// /canvas.drawBitmap(logo, 0, h - 100, p);
		// 设置光源的方向
		float[] direction = new float[] { 1, 1, 1 };
		// 设置环境光亮度
		float light = 0.5f;

		// 选择要应用的反射等级
		float specular = 1;

		// 向mask应用一定级别的模糊
		float blur = 1;
		// BlurMaskFilter maskFilter = new BlurMaskFilter(10,
		// BlurMaskFilter.Blur.SOLID);
		EmbossMaskFilter emboss = new EmbossMaskFilter(direction, light,
				specular, blur);

		// // 应用mask
		p.setMaskFilter(emboss);
		// p.setMaskFilter(maskFilter);

		canvas.drawBitmap(logo, 0, h + logo.getHeight() / 4 - logo.getHeight()
				* 2-10, p);

		/*
		 * // 设置字体 int textSize = 25; Typeface font = Typeface.create("宋体",
		 * Typeface.BOLD); p.setColor(Color.RED); p.setTypeface(font);
		 * p.setTextSize(textSize); //p.setAlpha(100);
		 * 
		 * p.setAntiAlias(true);//去除锯齿 p.setFilterBitmap(true);//对位图进行滤波处理
		 * 
		 * 
		 * // 绘入水印图片 ///String text = "自助查勘 拍照:" + location + date; if (null ==
		 * position) { position = ""; } String text ="移动查勘 " + (1 == type ? "拍照"
		 * : "本地") + "(" + surveyNo + " " + position + ") " + dateStr;
		 * 
		 * while (p.measureText(text) > w - logo.getWidth()-2) {
		 * p.setTextSize(--textSize); } canvas.drawText(text, logo.getWidth()+2,
		 * h - logo.getHeight() / 4, p);
		 */

		// 设置字体
		Typeface font = Typeface.create("宋体", Typeface.BOLD);
		// p.setAntiAlias(true);// 去除锯齿
		// p.setFilterBitmap(true);// 对位图进行滤波处理
		p.setAlpha(100);

		p.setColor(Color.RED);
		p.setTypeface(font);
		int textSize = 18;
		p.setTextSize(textSize);
		p.setSubpixelText(true);

		// p.setTextSize(16);
		// 添加位置信息
		// canvas.drawText("当前位置:"+position, 0, h - 80, p);
		// 添加时间
		// canvas.drawText("TIME:"+getTime(), 0, h - 50, p);

		// 添加工号
		if (null == position) {
			position = "";
		}
		String text = "移动查勘 " + surveyNo;
		String text1 = "(" + (1 == type ? "拍照" : "本地") + " " + position + ") "
				+ dateStr;

		// if (p.measureText(text) > w)
		// {
		// while (p.measureText(text) > w)
		// {
		// p.setTextSize(--textSize);
		// }
		// canvas.drawBitmap(logo, 0, h + logo.getHeight() / 4 -
		// logo.getHeight() * 2, p);
		// canvas.drawText(text, 0, h - logo.getHeight() / 4, p);
		// }
		// else
		// {
		// if (p.measureText(text) > w -logo.getWidth()-2)
		// {
		// canvas.drawBitmap(logo, 0, h- logo.getHeight() * 2, p);
		// canvas.drawText(text, logo.getWidth()+2, h - logo.getHeight(), p);
		//
		// }
		// else
		// {
		// canvas.drawBitmap(logo, 0, h + logo.getHeight() / 4 -
		// logo.getHeight() * 2, p);
		// canvas.drawText(text, 0, h - logo.getHeight() / 4, p);
		// }
		// }

		while (p.measureText(text) > w) {
			p.setTextSize(--textSize);
		}

		// canvas.drawText(text, logo.getWidth() + 10, h + logo.getHeight()
		// / 4 - logo.getHeight() * 2 + 30, p);
		canvas.drawText(text, logo.getWidth() + 10, h + logo.getHeight() / 4
				- logo.getHeight()-10, p);

		canvas.drawText(text1, 0, h - logo.getHeight() / 4, p);

		// canvas.drawText("移动查勘-" + (1 == type ? "拍照" : "本地") + "(" + surveyNo
		// + " " + position + ") " + dateStr, 5, h - 10, p);
		// /canvas.drawText(text, 0, h - logo.getHeight() / 4, p);
		// 保存新图片
		// canvas.drawText(text1, 0, h + logo.getHeight() / 4, p);
		// System.out.println("text"
		// + (h + logo.getHeight() / 4 - logo.getHeight() * 2) + "text1"
		// + (h + logo.getHeight() / 4));
		canvas.save(Canvas.CLIP_SAVE_FLAG);
		canvas.restore();
		return bmpTemp;
	}

	// 缩放图片
	public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽�?
		int width = bm.getWidth();
		int height = bm.getHeight();
		if (width > height) {
			int temp = newWidth;
			newWidth = newHeight;
			newHeight = temp;
		}
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	}

	public int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public Bitmap decodeSampledBitmapFromResource(byte[] data, int reqWidth,
			int reqHeight, String location, String dateStr) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
				options);
		bitmap = createBitmap(bitmap, location, dateStr);
		return bitmap;
	}

	/**
	 * 给图片添加水印
	 * 
	 * @param src
	 *            目标图片
	 * @param str
	 *            水印内容
	 * @return 修改后图片
	 */
	/*
	 * public Bitmap createBitmap(byte[] bytes, int type) { Bitmap newMap =
	 * null;
	 * 
	 * // 获取图片资源 Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
	 * bytes.length, null); // 加入水印 //newMap = createBitmap(bitmap,""); return
	 * bitmap; }
	 */

	public File saveBitmap(Bitmap bitmap, String type) {
		FileUtils fileUtils = new FileUtils();
		String time = MyUtils.format("yyyyMMddHHmmss", new Date());

		FileOutputStream out = null;
		File temp = null;
		try {
			temp = new File(fileUtils.getSDPATH() + "images/");
			if (!temp.exists()) {
				temp.mkdirs();
			}
			temp = new File(fileUtils.getSDPATH() + "images/" + type
					+ File.separator);
			if (!temp.exists()) {
				temp.mkdirs();
			}
			temp = new File(fileUtils.getSDPATH() + "images/" + type
					+ File.separator + time + ".jpeg");

			out = new FileOutputStream(temp);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int options = 100;
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
			while (baos.toByteArray().length / 1024 > 400) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
				options -= 2;// 每次都减少10
			}
			if (options <= 0) {
				options = 10;
			}
			baos.writeTo(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return temp;

	}

}
