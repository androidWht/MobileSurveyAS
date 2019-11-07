package com.sinosoft.ms.operation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.ms.utils.db.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBController {
	private Context context;
	private String tableName;
	private SQLiteDatabase db;

	public DBController(Context context, String tableName) {
		super();
		this.context = context;
		this.tableName = tableName;
	}

	public synchronized Object getObject(String selection,
			String[] selectionArgs, Class cls) {
		Field[] fields = cls.getDeclaredFields();
		String[] names = new String[fields.length];
		for (int i = 0; i < names.length; i++) {
			Field f = fields[i];
			f.setAccessible(true);
			fields[i].setAccessible(true);
			names[i] = f.getName();
		}

		// String tableName = cls.getSimpleName();
		// List entities = new ArrayList();
		Object entity = null;
		try {
			db = DatabaseHelper.getHelper(context).openDatabase();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(tableName, names, selection, selectionArgs,
				null, null, null);

		try {

			if (cursor.moveToNext()) {
				entity = cls.newInstance();
				for (Field f : fields) {
					f.setAccessible(true);
					// /用反射来调用相应的方法
					// /先构造出方法的名字
					String typeName = f.getType().getSimpleName();
					// /int --> Int,doble--->Double
					typeName = typeName.substring(0, 1).toUpperCase()
							+ typeName.substring(1);
					// /cuosor 的方法的名字
					String methodName = "get" + typeName;
					// /得到方法
					Method method = cursor.getClass().getMethod(methodName,
							int.class);
					Object retValue = method.invoke(cursor,
							cursor.getColumnIndex(f.getName()));
					f.set(entity, retValue);
					// f.set(entity, cursor.)
				}
				// entities.add(entity);
			}

		} catch (NullPointerException ex) {

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}

		return entity;
	}
}
