package com.flzc.fanglian.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.CashUtils;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.ui.home.ChooseCityActivity;
import com.flzc.fanglian.update.AppUtils;
import com.umeng.analytics.MobclickAgent;

public class WelComeActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		init();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		JPushInterface.onResume(this);
	}
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		JPushInterface.onPause(this);
	}

	private void init() {
		TextView version = (TextView)findViewById(R.id.tv_version);
		version.setText("V"+AppUtils.getVersionName(this));
		RelativeLayout rl_welcome_bg = (RelativeLayout) findViewById(R.id.rl_welcome_bg);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(2000);
		alphaAnimation.setFillAfter(true);
		
		AnimationSet animationSet = new AnimationSet(false);
		animationSet.addAnimation(alphaAnimation);
		
		rl_welcome_bg.startAnimation(animationSet);
		// 监听动画
		animationSet.setAnimationListener(new MyAnimationListener());
	}
	
	class MyAnimationListener implements AnimationListener{

		@Override
		public void onAnimationStart(Animation animation) {
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// 动画执行后回调
			// 判断是否用户第一次打开应用，是就跳到引导界面，不是就直接跳到主界面
			boolean isFirstOpen = CashUtils.getData( Constant.IS_FIRST_OPEN, true);
			if(isFirstOpen){
				// 跳到引导界面
				goOthers(GuideActivity.class);
			}else{
				if(UserInfoData.isContainKey(Constant.LOC_CITY_ID)){
					// 跳到主界面
					goOthers(HomeActivity.class);
				}else{
					Intent intent = new Intent(WelComeActivity.this,ChooseCityActivity.class);
					intent.putExtra("from", "notFromHome");
					startActivity(intent);
				}
				
			}
			finish();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}
		
	}

}
