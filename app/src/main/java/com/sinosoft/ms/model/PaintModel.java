package com.sinosoft.ms.model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Environment;

/**
 * Ӧ�ó�������
 * 
 * @author linianchun
 * 
 */
public class PaintModel {
	private static PaintModel appConstants;

	public static int CURR_CONTROL;// ��ǰѡ��ؼ�
	private static ImageObject currentPaintObj;
	public static int INDEX;
	private static boolean isLine = false;
	public static boolean isPaint = false;
	private static boolean isSave = false;
	public static boolean isScale = false;

	private static List<LineObject> listPath = new ArrayList<LineObject>();
	// add by zhengtongsheng 2012-12-17
	private static List<ImageObject> paintMap;
	public static final int tdX = 1;
	public static final int tdY = 1;

	private Bitmap bitmap;

	private List<Object> history = new ArrayList<Object>();// 历史动作
	private int historyIndex = -1;// 用来区别历史步骤
	private List<Object> tempHistory = new ArrayList<Object>();// 用于返回撤销
	private List<Point> listPoint = new ArrayList<Point>();

	private Paint paint = new Paint();

	/* ��ͼ�̵߳��ü��ʱ�� */
	public static final int THREED_CALL_TIME = 10;

	public static PaintModel getInstance() {
		if (appConstants == null) {
			appConstants = new PaintModel();

		}
		return appConstants;
	}

	public static List<LineObject> getListPath() {
		return listPath;
	}

	public static boolean isLine() {
		return isLine;
	}

	public static boolean isPaint() {
		return isPaint;
	}

	public static boolean isSave() {
		return isSave;
	}

	public static boolean isScale() {
		return isScale;
	}

	public static void setLine(boolean isLine) {
		PaintModel.isLine = isLine;
	}

	public static void setListPath(List<LineObject> listPath) {
		PaintModel.listPath = listPath;
	}

	public static void setPaint(boolean isPaint) {
		PaintModel.isPaint = isPaint;
	}

	public static void setSave(boolean isSave) {
		PaintModel.isSave = isSave;
	}

	public static void setScale(boolean isScale) {
		PaintModel.isScale = isScale;
	}

	public void addPaintObj(ImageObject paintObj) {
		if (paintMap == null) {
			paintMap = new ArrayList<ImageObject>();
		}
		paintMap.add(paintObj);

		addHistory(paintObj);
	}

	public boolean removePaintObj(ImageObject paintObj) {
		if (paintMap != null && paintMap.size() > 0) {
			paintObj.setDeleted(true);

			paintMap.remove(paintObj);
			addHistory2(paintObj);

			return true;
		} else {
			return false;
		}

	}

	public void addPath(LineObject lineObject) {
		if (listPath != null) {
			listPath.add(lineObject);
			addHistory(lineObject);
		}

	}

	public void down() {
		if (currentPaintObj != null) {
			int y = (int) currentPaintObj.getY();
			y += tdY;
			currentPaintObj.setY(y);
		}
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public ImageObject getCurrentPaintObj() {
		return currentPaintObj;
	}

	public Paint getPaint() {

		return paint;
	}

	public List<ImageObject> getPaintMap() {
		return paintMap;
	}

	public void left() {
		if (currentPaintObj != null) {
			int x = (int) currentPaintObj.getX();
			x -= tdX;
			currentPaintObj.setX(x);
		}
	}

	public void removeAllPath() {
		if (listPath != null && listPath.size() > 0) {
			listPath.clear();

		}

	}

	public void removePath() {
		if (listPath != null) {
			if (listPath.size() > 1) {
				listPath.remove(listPath.size() - 1);

			} else if (listPath.size() == 1) {
				listPath.remove(0);

			}

		}

	}

	public void right() {
		if (currentPaintObj != null) {
			int x = (int) currentPaintObj.getX();
			x += tdX;
			currentPaintObj.setX(x);
		}
	}

	public void save() {
		if (isSave) {
            
			Bitmap bit = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas c = new Canvas(bit);
			c.drawBitmap(bitmap, 0, 0, null);
			if (bitmap != null) {
				try {

					FileOutputStream fos = null;
					String path = Environment.getExternalStorageDirectory()
							+ "";
					File file = new File(path);
					if (!file.exists()) {
						file.mkdirs();
					}
					File file2 = new File(file, "make.png");
					fos = new FileOutputStream(file2);
					if (null != fos) {
						boolean is = bit.compress(Bitmap.CompressFormat.PNG,
								100, fos);
						fos.flush();
						fos.close();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void setCurrentPaintObj(ImageObject current) {
		currentPaintObj = current;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public void setPaintMap(List<ImageObject> paintMap) {
		this.paintMap = paintMap;
	}

	public void up() {
		if (currentPaintObj != null) {
			int y = (int) currentPaintObj.getY();
			y -= tdY;
			currentPaintObj.setY(y);
		}
	}

	/*
	 * 
	 * 历史
	 */
	public void addHistory(Object object) {
		if (object instanceof ImageObject) {
			ImageObject img = (ImageObject) object;
			img.addHistory(new Point((int) img.getX(), (int) img.getY()));

		}
		if (historyIndex < history.size() - 1) {
			history.subList(historyIndex + 1, history.size()).clear();
			reFreshPaintMap();

		}
		historyIndex++;

		history.add(object);
	}

	public void reFreshPaintMap() {
		for (int i = 0; i < paintMap.size(); i++) {
			ImageObject o = (ImageObject) paintMap.get(i);
			o.setDeleted(false);
		}

	}

	public void addHistory2(Object object) {
		ImageObject img = (ImageObject) object;
		// Point p = new Point((int) img.getX(), (int) img.getY());
		// listPoint.add(p);
		if (historyIndex < history.size() - 1) {
			history.subList(historyIndex + 1, history.size()).clear();
			// reFreshPaintMap();

		}
		history.add(img);
		historyIndex++;
		// tempHistory.clear();

	}

	public void removeHistory() {
		if (historyIndex >= 0) {
			Object object = history.get(historyIndex);
			historyIndex--;

			if (object instanceof LineObject) {
				if (listPath.size() > 0) {
					listPath.remove(object);

				}

			} else if (object instanceof ImageObject) {

				if (paintMap.contains(object)) {
					ImageObject img = (ImageObject) object;
					int index = img.getIndex();
					if (index > 0) {
						Point point = img.back();

						img.setX(point.x);
						img.setY(point.y);

					} else if (index == 0) {
						ImageObject imgObj = (ImageObject) object;
						img.back();
						paintMap.remove(imgObj);
					}

				} else {
					ImageObject img = (ImageObject) object;
					paintMap.add(img);
					/*
					 * if(img.isDeleted()){ img.back(); historyIndex-=1; }
					 */
				}

			}

		}

	}

	public void backHistory() {
		if (historyIndex < history.size() - 1) {
			historyIndex++;
			Object object = history.get(historyIndex);

			// 得到该对象在数组里的重复次数

			// tempHistory.remove(tempHistory.size() - 1);
			if (object instanceof LineObject) {
				listPath.add((LineObject) object);
			} else if (object instanceof ImageObject) {
				ImageObject img = (ImageObject) object;
				int index = img.getIndex();
				if (!img.isDeleted()) {
					if (index > -1) {
						Point p = img.front();
						// img.addHistory(p);，
						if (p != null) {
							img.setX(p.x);
							img.setY(p.y);
						}

					} else if (index == -1) {
						img.front();
						paintMap.add(img);
					}

				} else if (img.isDeleted()) {
					tempHistory = history.subList(historyIndex + 1,
							history.size());
					int num = Collections.frequency(tempHistory, object);
					if (num > 1) {

						if (paintMap.contains(img)) {
							Point p = img.front();
							if (p != null) {
								img.setX(p.x);
								img.setY(p.y);
							}

						} else {
							// img.front();
							paintMap.add(img);

						}

					} else if (num == 1) {
						ImageObject o = (ImageObject) object;
						// o.front();
						// historyIndex--;

						paintMap.remove(o);

					}

				}

			}

		}

	}

	public void clearAllPaintObject() {
		history.clear();
		historyIndex = -1;
		// tempHistory.clear();
		if (paintMap != null) {
			paintMap.clear();
		}
		if (listPath != null) {
			listPath.clear();

		}

	}

}
