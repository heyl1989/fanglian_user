package com.flzc.fanglian.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.http.Constant;

public class DaySpecialTempParameter {
	private static SharedPreferences sp = UserApplication.getInstance()
			.getSharedPreferences("temp", Context.MODE_PRIVATE);

	/**
	 * 存入数据long
	 */
	public static void saveData(String key, long value) {
		sp.edit().putLong(key, value).commit();
	}
	/**
	 * 
	 * @Title: saveData 
	 * @Description:存入数据String
	 * @param key
	 * @param value
	 * @return: void
	 */
	public static void saveData(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	/**
	 * 取出数据
	 */
	public static String getData(String key, String value) {
		return sp.getString(key, value);
	}
	/**
	 * 取出数据long
	 */
	public static long getData(String key, long value) {
		return sp.getLong(key, value);
	}

	/**
	 * 查询某个值是否存在
	 */
	public static boolean isSingIn() {
		return sp.contains(Constant.TOKEN);
	}

	/**
	 * 删除key
	 */
	public static void clearKey(String key) {
		sp.edit().remove(key).commit();
	}

	/**
	 * 删除所有
	 */
	public static void clearAll() {
		sp.edit().clear().commit();
	}
}
