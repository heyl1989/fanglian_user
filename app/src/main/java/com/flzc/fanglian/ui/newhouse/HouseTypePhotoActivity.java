package com.flzc.fanglian.ui.newhouse;

import uk.co.senab.photoview.PhotoView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;

/**
 * 
 * @ClassName: HouseTypePhotoActivity 
 * @Description: 楼盘户型的大图
 * @author: qipc
 * @date: 2016年5月3日 下午2:38:21
 */
public class HouseTypePhotoActivity extends BaseActivity {
	
	private LinearLayout container;
	private String houseTypeUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newhouse_housetypephoto);
		houseTypeUrl = getIntent().getExtras().getString("houseTypeUrl", "");
		initView();
	}

	private void initView() {
		container = (LinearLayout)findViewById(R.id.ll_housetype);
		PhotoView photoView = new PhotoView(container.getContext());
		imageLoader.displayImage(houseTypeUrl, photoView,options);
		container.addView(photoView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		
		photoView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}	

}
