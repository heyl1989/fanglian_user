package com.flzc.fanglian.util;

import java.util.Set;

import com.flzc.fanglian.app.UserApplication;

import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @ClassName JpushUtil
 * @Description: Jpush 工具类
 * @author: Joe
 * @date: 2016-3-12
 */

public class JpushUtil {
	private String TAG = this.getClass().getName();

	/**
	 * 设置用户的JPush Alias UUID_ 1、开发商 2、销售经理 3 置业顾问 4 经纪人 5 用户
	 */
	public void setBrokerAlias(String alias) {
		if (null != alias) {
			/** UUID_ 1、开发商 2、销售经理 3 置业顾问 4 经纪人 5 用户 */
			JPushInterface.setAliasAndTags(UserApplication.getInstance(),
					alias, null, mAliasCallback);
		}
	}

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				LogUtil.i(TAG, logs);
				break;
			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				LogUtil.i(TAG, logs);
				break;
			default:
				logs = "Failed with errorCode = " + code;
				LogUtil.e(TAG, logs);
			}

		}

	};
}
