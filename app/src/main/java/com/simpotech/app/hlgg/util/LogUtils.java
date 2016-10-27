package com.simpotech.app.hlgg.util;

import android.util.Log;

/**
 * 日志打印类,调试时将isDebug设为true,正式上线时将isDebug设为false
 * Created by longuto on 2016/10/27.
 */
public class LogUtils {
	private static boolean isDebug = true;	// 是否处于debug模式
	
	public static void i(String tag, String msg) {
		if(isDebug) {
			Log.i(tag, msg);
		}
	}
	
	public static void v(String tag, String msg) {
		if(isDebug) {
			Log.v(tag, msg);
		}
	}
	
	public static void w(String tag, String msg) {
		if(isDebug) {
			Log.w(tag, msg);
		}
	}
	
	
}
