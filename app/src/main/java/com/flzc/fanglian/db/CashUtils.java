package com.flzc.fanglian.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.flzc.fanglian.app.UserApplication;

public class CashUtils {
	
	private static SharedPreferences sp = UserApplication.getInstance()
			.getSharedPreferences("isfirst",
					Context.MODE_PRIVATE);
	
	/**
	 * 存入布尔类型数据数据
	 */
	public static void saveData(String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 取出buer数据
	 */
	public static boolean getData(String key, boolean value) {
		return sp.getBoolean(key, value);
	}

}
