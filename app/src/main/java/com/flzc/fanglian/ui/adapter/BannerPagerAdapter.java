package com.flzc.fanglian.ui.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.model.BannerBean.Result.BannerList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BannerPagerAdapter extends PagerAdapter {

	private List<BannerList> list;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	public BannerPagerAdapter(List<BannerList> bannerList) {
		this.list = bannerList;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading_750_438)
				.showImageForEmptyUri(R.drawable.loading_750_438)
				.cacheInMemory(false)
				.cacheOnDisk(true)
				.build();
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(UserApplication.getInstance());
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);// s设置图片的四周和imageView对齐	
		if(list.size()>0){
			imageLoader.displayImage(list.get(position % list.size()).getImgUrl(), imageView,
					options);
		}else{
			imageView.setImageResource(R.drawable.loading_750_380);
		}
		container.addView(imageView);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
