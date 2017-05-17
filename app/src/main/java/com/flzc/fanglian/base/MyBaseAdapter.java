package com.flzc.fanglian.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.flzc.fanglian.R;
import com.flzc.fanglian.view.dialog.MyLoadingProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class MyBaseAdapter<D> extends BaseAdapter {
	protected List<D> dList = new ArrayList<D>();
	protected Context context;
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected MyLoadingProgressBar loadingDialog;

	public MyBaseAdapter(Context context) {
		this.context = context;
		loadingDialog = new MyLoadingProgressBar(context);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading_750_438)
				.showImageForEmptyUri(R.drawable.loading_750_438)
				.cacheInMemory(false)
				.cacheOnDisk(true)
				.build();
	}

	public MyBaseAdapter(Context context, List<D> list) {
		this.context = context;
		this.dList = list;
		loadingDialog = new MyLoadingProgressBar(context);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading_750_438)
				.showImageForEmptyUri(R.drawable.loading_750_438)
				.cacheInMemory().cacheInMemory().build();
	}

	public int getCount() {
		return dList.size();
	}

	public Object getItem(int arg0) {
		return arg0;
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public abstract View getView(int position, View convertView, ViewGroup arg2);

	/**
	 * 设置集合数据
	 * 
	 * @param dList
	 */
	public void setData(List<D> dList) {
		this.dList = dList;
		notifyDataSetChanged();
	}

	/**
	 * 添加集合数据
	 * 
	 * @param aList
	 */
	public void addData(List<D> aList) {
		this.dList.addAll(aList);
		notifyDataSetChanged();
	}

	/**
	 * 获取集合数据
	 * 
	 * @return
	 */
	public List<D> getData() {
		return dList;
	}

	/**
	 * activity直接跳转
	 * 
	 * @param cls
	 */
	public void goToOthers(Class<?> cls) {
		Intent i = new Intent(context, cls);
		context.startActivity(i);
	}

	/**
	 * 提示Toast
	 * 
	 * @param message
	 */
	public void showToast(String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}
