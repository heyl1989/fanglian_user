package com.flzc.fanglian.ui.newhouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.senab.photoview.PhotoView;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.NewHousePhotoBean;
import com.flzc.fanglian.model.NewHousePhotoBean.Result.Imgs;
import com.flzc.fanglian.view.HackyViewPager;
/**
 * 
 * @ClassName: NewHousePhotoActivity 
 * @Description: 楼盘图片浏览
 * @author: LU
 * @date: 2016-3-15 下午12:39:48
 */
public class NewHousePhotoActivity extends BaseActivity implements OnClickListener{
	
	private HackyViewPager photo_view_pager;
	private String houseId;
	private ArrayList<String> photoList;
	private RelativeLayout rl_back;
	private TextView tv_title;
	protected List<Imgs> imags = new ArrayList<NewHousePhotoBean.Result.Imgs>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newhouse_photo);
		houseId = getIntent().getStringExtra("houseId");
		initView();
		innitListener();
		initData();
	}


	@SuppressWarnings("deprecation")
	private void initView() {
		//标题栏
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		tv_title = (TextView)findViewById(R.id.tv_title);
		//viewPager
		photo_view_pager = (HackyViewPager) findViewById(R.id.photo_view_pager);		
		photo_view_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				tv_title.setText(position+1+"/"+imags.size()+"  "+imags.get(position).getName());

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
		
	}
	
	private void innitListener() {
		rl_back.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;

		}
		
	}

	/**
	 * 
	 * @Title: initData 
	 * @Description: 请求楼盘图片列表
	 * @return: void
	 */
	private void initData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", houseId);
		SimpleRequest<NewHousePhotoBean> req = new SimpleRequest<NewHousePhotoBean>(
				API.QUERY_BXUILDING_IMGS, NewHousePhotoBean.class,
				new Listener<NewHousePhotoBean>() {
					@Override
					public void onResponse(NewHousePhotoBean response) {
						if(null != response){
							if(response.getStatus() == 0){
								imags  = response.getResult().getImgs();
								tv_title.setText(1+"/"+imags.size()+"  "+imags.get(0).getName());
								photo_view_pager.setAdapter(new SamplePagerAdapter());
							}else{
								showTost(response.getMsg());
							}
						}
						
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);
		
	}


	class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imags.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int i_position) {
			PhotoView photoView = new PhotoView(container.getContext());
			imageLoader.displayImage(imags.get(i_position).getUrl(), photoView,options);
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}


}
