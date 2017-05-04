package com.zx.housekeeper.biz;

import com.zx.housekeeper.BuildConfig;

import android.util.Log;

/**
 * 
 * @author HY
 * 
 */
public class LogWrapper {
	/**
	 * whether in debug stage
	 */
	static boolean isDeBug = true;

	/**
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (isDeBug && BuildConfig.DEBUG)
			Log.e(tag, msg);
	}

	/**
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, String msg) {
		if (isDeBug && BuildConfig.DEBUG)
			Log.w(tag, msg);
	}

	/**
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (isDeBug && BuildConfig.DEBUG)
			Log.i(tag, msg);
	}

	/**
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (isDeBug && BuildConfig.DEBUG)
			Log.d(tag, msg);
	}

	/**
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg) {
		if (isDeBug && BuildConfig.DEBUG)
			Log.v(tag, msg);
	}
}
