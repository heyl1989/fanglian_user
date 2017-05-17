package com.flzc.fanglian.base;

import com.flzc.fanglian.R;
import com.flzc.fanglian.view.dialog.MyLoadingProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
/**
 * 
 * @ClassName: BaseFragmentActivity 
 * @Description: TODO
 * @author: LU
 * @date: 2016-3-4 下午3:14:04
 */
public class BaseFragmentActivity extends FragmentActivity {
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected MyLoadingProgressBar loadingDialog;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		// 禁止横屏
		loadingDialog = new MyLoadingProgressBar(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading_750_438)
				.showImageForEmptyUri(R.drawable.loading_750_438)
				.cacheInMemory(false)
				.cacheOnDisk(true)
				.build();
	}

	protected void showTost(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	protected void goOthers(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		// 回收该页面缓存在内存的图片
		imageLoader.clearMemoryCache();
		super.onDestroy();
	}

}
