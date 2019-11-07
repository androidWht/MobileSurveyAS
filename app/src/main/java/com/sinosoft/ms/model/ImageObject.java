package com.sinosoft.ms.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * 锟芥画锟斤拷锟斤拷
 * 
 * @author linianchun
 * 
 */
public class ImageObject {

	private int MAXHW = 200;

	private float x;// x锟斤拷锟轿伙拷锟�
	private float y;// y锟斤拷锟轿伙拷锟�
	private int width;// 图片锟斤拷锟�
	private int height;// 图片锟竭讹拷
	private int rId;// 图片锟斤拷源ID

	// add by zhengtongsheng
	private Rect rect;// 图片的范围，用于当点击在上面时拖动
	private float angle;
	private float scale;
	private Rect orignRect;
	// private Bitmap scrBitmap;
	private int bitHeight;
	private int bitWidth;
	private Bitmap baseBitmap;
	private boolean deleted = false;
	private String title = "hello world";

	private boolean isScale=true;

	private ArrayList<Point> historyList = new ArrayList<Point>();

	private int index = -1;

	public ImageObject(float x, float y, int width, int height, int rId) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rId = rId;
		angle = 0;
		scale = 1;
	}

	public ImageObject(float x, float y, int width, int height, int rId,
			Rect rect) {
		super();
		this.x = x;
		this.y = y;

		if (width >= height) {
			this.width = width;
			this.height = width;

		} else {
			this.width = height;
			this.height = height;

		}

		this.rId = rId;
		this.rect = rect;
		orignRect = rect;
		angle = 0;
		scale = 1;
	}

	public ImageObject(Context context, int rId,boolean isScale) {
		this.x = 0;
		this.y = 0;
		this.rId = rId;
		baseBitmap = BitmapFactory.decodeResource(context.getResources(), rId);
		this.isScale=isScale;
		bitWidth = baseBitmap.getWidth();
		bitHeight = baseBitmap.getHeight();
		if(isScale){
			if (bitWidth > bitHeight) {
				if (MAXHW <= bitWidth) {
					bitHeight = (int) (bitHeight * 1.00f / bitWidth * MAXHW);
					bitWidth = MAXHW;
					// baseBitmap=adjustImage(bitWidth,bitHeight);
				}

			} else {
				if (MAXHW <= bitHeight) {
					bitWidth = (int) (bitWidth * 1.00f / bitHeight * MAXHW);
					bitHeight = MAXHW;
					// baseBitmap=adjustImage(bitWidth,bitHeight);
				}

			}
		}
		if (bitWidth >= bitHeight) {
			this.height = bitWidth;
			this.width = bitWidth;

		} else {
			this.height = bitHeight;
			this.width = bitHeight;

		}
		scale = 1;
		angle = 0;

		rect = new Rect();
		rect.left = (int) x;
		rect.top = (int) y;
		rect.bottom = (int) y + height;
		rect.right = (int) x + width;

	}

	

	public Bitmap adjustImage(int w, int h) {
		Bitmap tempBit = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas tempCan = new Canvas(tempBit);
		Matrix matrix = new Matrix();
		matrix.postScale(w * 1.00f / baseBitmap.getWidth(), w * 1.00f
				/ baseBitmap.getWidth());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		tempCan.drawBitmap(baseBitmap, matrix, paint);

		return tempBit;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return (int) (width * scale);
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return (int) (height * scale);
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public Rect getRect() {
		Rect r = new Rect();
		r.left = (int) x;
		r.right = (int) (width * scale) + (int) x;
		r.top = (int) y;
		r.bottom = (int) (height * scale) + (int) y;
		return r;
	}

	public void setRect(Rect rect) {
		x = rect.left;
		y = rect.top;
		width = rect.width();
		height = rect.height();

		this.rect = rect;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getScale() {

		return scale;
	}

	public void setScale(float scale) {
		if (scale < 0.2) {
			scale = 0.2f;
		} else if (scale > 2.5f) {
			scale = 2.5f;

		}
		this.scale = scale;
	}

	public Rect getOrignRect() {
		Rect r = new Rect();
		r.left = (int) x;
		r.right = width + (int) x;
		r.top = (int) y;
		r.bottom = height + (int) y;

		return orignRect;

	}

	public void addHistory(Point point) {

		if (index < historyList.size() - 1) {
			historyList.subList(index + 1, historyList.size()).clear();

		}
		index++;
		historyList.add(point);

	}

	public Point back() {
		index--;
		if (index >= 0) {
			Point point = historyList.get(index);

			return point;
		} else {
			return null;

		}

	}

	public Point front() {
		index++;
		if (index > historyList.size() - 1) {
			return null;

		} else {
			Point point = historyList.get(index);

			return point;
		}

	}

	public void removeHistory() {
		if (historyList.size() > 0) {
			historyList.remove(historyList.size() - 1);

		}

	}

	public List<Point> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(ArrayList<Point> historyList) {
		this.historyList = historyList;
	}

	public Bitmap getScrBitmap() {
		Bitmap scrBitmap = Bitmap.createBitmap((int) (width * scale),
				(int) (height * scale), Config.ARGB_8888);
		Canvas canvas = new Canvas(scrBitmap);
		Matrix matrix = new Matrix();
		float baseScale = 1.0f;

		int w = baseBitmap.getWidth();
		int h = baseBitmap.getHeight();
		if(isScale){
		if (w > h) {
			if (MAXHW <= w) {
				baseScale = MAXHW * 1.00f / w;

			}

		} else {
			if (MAXHW <= h) {
				baseScale = MAXHW * 1.00f / h;
			}

		}
		}else{
			baseScale=1;
		}
		matrix.postScale(scale * baseScale, scale * baseScale);

		matrix.postRotate(angle, (int) (bitWidth * scale) / 2, bitHeight / 2
				* scale);
		matrix.postTranslate((width - bitWidth) * scale / 2,
				(height - bitHeight) * scale / 2);

		Paint bitPaint = new Paint();
		bitPaint.setAntiAlias(true);

		canvas.drawBitmap(baseBitmap, matrix, bitPaint);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setTextSize(20 * scale);

		Rect bounds = new Rect();
		paint.getTextBounds(title, 0, title.length(), bounds);

		canvas.drawText(title, (width * scale - bounds.width()) / 2,
				bounds.height(), paint);
		return scrBitmap;
	}

	public int getBitHeight() {
		return (int) (bitHeight * scale);
	}

	public void setBitHeight(int bitHeight) {
		this.bitHeight = bitHeight;
	}

	public int getBitWidth() {
		return (int) (bitWidth * scale);
	}

	public void setBitWidth(int bitWidth) {
		this.bitWidth = bitWidth;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
