package com.flzc.fanglian.ui.me;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;

public class AboutFanglianActivity extends BaseActivity {
	
	private RelativeLayout rl_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_about_fanglian);
		init();
	}

	private void init() {
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		rl_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("关于房链");
		TextView vertion_name = (TextView)findViewById(R.id.tv_vertion_name);
		try {
			String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			vertion_name.setText("版本："+versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	

}
