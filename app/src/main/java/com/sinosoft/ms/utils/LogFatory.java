package com.sinosoft.ms.utils;

import android.util.Log;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午11:03:00
 */

public class LogFatory {

	private static final boolean debug = true;
	private static final boolean error = true;
	private static final boolean info = true;
	private static final boolean warn = true;

	private static final boolean all = true;

	public static void i(String tag, String msg) {
		if (all && info) {
			Log.i(tag, msg);
		}

	}

	public static void d(String tag, String msg) {
		if (all && debug) {
			Log.d(tag, msg);
		}

	}

	public static void w(String tag, String msg) {
		if (all && warn) {
			Log.w(tag, msg);

		}

	}

	public static void e(String tag, String msg) {
		if (all && error) {
			Log.e(tag, msg);
		}

	}

}
