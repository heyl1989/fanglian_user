package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * 
 * @ClassName: ViewpagerAdapter 
 * @Description: 通用的  viewpager + fragment 适配器
 * @author: Tien.
 * @date: 2016年3月3日 下午7:35:30
 */
public class ViewpagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	
	public ViewpagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public ViewpagerAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}
	
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
	
}
