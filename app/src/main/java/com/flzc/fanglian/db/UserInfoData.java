package com.flzc.fanglian.db;

import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.http.Constant;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfoData {

	private static SharedPreferences sp = UserApplication.getInstance()
			.getSharedPreferences("userinfo",
					Context.MODE_PRIVATE);

	/**
	 * 存入数据
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
	 * 判断是否登录
	 */
	public static boolean isSingIn() {
		return sp.contains(Constant.TOKEN);
	}
	/**
	 * 查询某个值是否存在
	 */
	public static boolean isContainKey(String key) {
		return sp.contains(key);
	}
	/**
	 *删除key
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
