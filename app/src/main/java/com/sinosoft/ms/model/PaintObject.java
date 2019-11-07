package com.sinosoft.ms.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * 锟芥画锟斤拷锟斤拷
 * 
 * @author linianchun
 * 
 */
public abstract class PaintObject {

	private final int MAXHW = 200;

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

	public abstract Bitmap getScrBitmap();

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

}
