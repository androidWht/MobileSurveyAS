package com.sinosoft.ms.model;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;

public class LineObject extends PaintObject {
	Paint paint;
	Path path;

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	@Override
	public Bitmap getScrBitmap() {
		// TODO Auto-generated method stub
		return null;
	}

}
