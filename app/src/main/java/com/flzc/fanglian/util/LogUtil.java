package com.flzc.fanglian.util;

import android.util.Log;
/**
 * 
 * @ClassName: LogUtil 
 * @Description: 日志管理工具类
 * @author: LU
 * @date: 2016-3-8 下午4:44:53
 */
public class LogUtil {
	private static boolean isDebug = true;
	public static void i(String tag,String  msg){
		if(isDebug){
			Log.i(tag, msg);
		}
	}
	
	public static void i(Object object,String  msg){
		if(isDebug){
			Log.i(object.getClass().getSimpleName(), msg);
		}
	}
	public static void d(String tag,String  msg){
		if(isDebug){
			Log.d(tag, msg);
		}
	}
	
	public static void d(Object object,String  msg){
		if(isDebug){
			Log.d(object.getClass().getSimpleName(), msg);
		}
	}
	 
	public static void e(String tag,String  msg){
		if(isDebug){
			Log.e(tag, msg);
		}
	}
	
	public static void e(Object object,String  msg){
		if(isDebug){
			Log.e(object.getClass().getSimpleName(), msg);
		}
	}
}
