package com.flzc.fanglian.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.CashUtils;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.ui.home.ChooseCityActivity;

public class GuideActivity extends BaseActivity implements OnClickListener{

	private Button bt_guide_start;
	private ArrayList<ImageView> images;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		init();
	}

	private void init() {
		ViewPager vp_guide_bg = (ViewPager) findViewById(R.id.vp_guide_bg);
		bt_guide_start = (Button) findViewById(R.id.bt_guide_start);
		bt_guide_start.setOnClickListener(this);
		// 准备数据
		initData();
		// 设置数据适配器
		vp_guide_bg.setAdapter(new MyPagerAdapter());
		// 监听ViewPager滚动事件
		vp_guide_bg.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initData() {
		int[] imageIds = new int[] { R.drawable.guide_1, R.drawable.guide_2,
				R.drawable.guide_3,R.drawable.guide_4 };
		images = new ArrayList<ImageView>();
		for(int i=0;i<imageIds.length;i++){
			ImageView imageView = new ImageView(this);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setBackgroundResource(imageIds[i]);
			images.add(imageView);
		}
		
	}
	
	class MyOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			if(position==images.size()-1){
				bt_guide_start.setVisibility(View.VISIBLE);
			}else{
				bt_guide_start.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			
		}
		
	}
	
	class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = images.get(position);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
		
		
	}

	@Override
	public void onClick(View v) {
		CashUtils.saveData(Constant.IS_FIRST_OPEN, false);
		if(UserInfoData.isContainKey(Constant.LOC_CITY_ID)){
			// 跳到主界面
			goOthers(HomeActivity.class);
		}else{
			Intent intent = new Intent(this,ChooseCityActivity.class);
			intent.putExtra("from", "notFromHome");
			startActivity(intent);
		}
		finish();
		
	}
}
