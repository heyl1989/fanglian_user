package com.flzc.fanglian.base;

import com.flzc.fanglian.R;
import com.flzc.fanglian.app.AppManager;
import com.flzc.fanglian.view.dialog.MyLoadingProgressBar;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 
 * @ClassName: BaseActivity 
 * @Description: TODO
 * @author: LU
 * @date: 2016-3-4 下午3:13:49
 */
public class BaseActivity extends Activity {

	protected MyLoadingProgressBar loadingDialog;
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 禁止横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		loadingDialog = new MyLoadingProgressBar(this);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading_750_438)
				.showImageForEmptyUri(R.drawable.loading_750_438)
				.cacheInMemory(false)
				.cacheOnDisk(true)
				.build();
		//创建Activity时候，添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	protected void showTost(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	protected void goOthers(Class<?> activity) {
		Intent intent = new Intent(this, activity);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		// 回收该页面缓存在内存的图片
		imageLoader.clearMemoryCache();
		super.onDestroy();
	}
}
