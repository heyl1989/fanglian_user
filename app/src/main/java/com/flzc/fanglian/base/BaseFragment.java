package com.flzc.fanglian.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.flzc.fanglian.R;
import com.flzc.fanglian.view.dialog.MyLoadingProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 
 * @ClassName: BaseFragment 
 * @Description: TODO
 * @author: LU
 * @date: 2016-3-4 下午3:13:57
 */
public class BaseFragment extends Fragment {

	protected MyLoadingProgressBar loadingDialog;
	protected Activity mActivity;// 上下文对象
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
		mActivity = getActivity();
		loadingDialog = new MyLoadingProgressBar(mActivity);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading_750_438)
				.showImageForEmptyUri(R.drawable.loading_750_438)
				.cacheInMemory(false)
				.cacheOnDisk(true)
				.build();
	}

	protected void showTost(String text) {
		Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
	}

	protected void goOthers(Class<?> activity) {
		Intent intent = new Intent(mActivity, activity);
		startActivity(intent);
	}

}
