package com.flzc.fanglian.ui.me;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseFragmentActivity;
import com.flzc.fanglian.ui.adapter.FangLianQuanActivityAdapter;
import com.flzc.fanglian.ui.fragment.FLQuanExpiredFragment;
import com.flzc.fanglian.ui.fragment.FLQuanNoUseFragment;
import com.flzc.fanglian.ui.fragment.FLQuanUsedFragment;

/**
 * 
 * @ClassName: FangLianQuanActivity
 * @Description: 房链券页面
 * @author: 薛东超
 * @date: 2016年3月9日 下午4:26:53
 */

public class FangLianQuanActivity extends BaseFragmentActivity implements
		OnClickListener {

	private ViewPager vp_flquan_pager;

	private ArrayList<Fragment> fragments;
	private RelativeLayout rl_back;
	private TextView tv_title;
	private ImageView clickBackage_myParticipate;
	private int clickBackageHeight = 0;
	private float mCurrentCheckedRadioLeft = 0.0f;// 当前被选中的RadioButton距离左侧的距离
	private RadioButton rb_nouse_fanglianquan;
	private RadioButton rb_used_fanglianquan;
	private RadioGroup radioGroup_option_fanglianquan;
	private RadioButton rb_expired_fanglianquan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_fanglianquan);
		initView();
		initListener();
		initViewPager();
	}

	private void initView() {
		// 标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("房链券");
		// 三个选择
		radioGroup_option_fanglianquan = (RadioGroup) findViewById(R.id.radioGroup_option_fanglianquan);
		rb_nouse_fanglianquan = (RadioButton) findViewById(R.id.rb_nouse_fanglianquan);
		rb_used_fanglianquan = (RadioButton) findViewById(R.id.rb_used_fanglianquan);
		rb_expired_fanglianquan = (RadioButton) findViewById(R.id.rb_expired_fanglianquan);

		// 下面的红线
		clickBackage_myParticipate = (ImageView) findViewById(R.id.clickBackage_fanglianquan);
		// viewPager
		vp_flquan_pager = (ViewPager) findViewById(R.id.vp_flquan_pager);
		// 处理下面的红线
		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int srceen_width = displayMetrics.widthPixels;
		clickBackageHeight = clickBackage_myParticipate.getLayoutParams().height;
		clickBackage_myParticipate
				.setLayoutParams(new LinearLayout.LayoutParams(srceen_width / 3
						+ this.rb_used_fanglianquan.getPaddingLeft()
						+ this.rb_expired_fanglianquan.getPaddingRight(),
						this.clickBackageHeight));

	}

	private void initListener() {
		// 标题
		rl_back.setOnClickListener(this);
		// 三个选择
		rb_nouse_fanglianquan.setOnClickListener(this);
		rb_used_fanglianquan.setOnClickListener(this);
		rb_expired_fanglianquan.setOnClickListener(this);

	}

	private void initViewPager() {
		fragments = new ArrayList<Fragment>();

		FLQuanNoUseFragment flQuanNoUseFragment = new FLQuanNoUseFragment();
		fragments.add(flQuanNoUseFragment);
		FLQuanUsedFragment flQuanUsedFragment = new FLQuanUsedFragment();
		fragments.add(flQuanUsedFragment);
		FLQuanExpiredFragment flQuanExpiredFragment = new FLQuanExpiredFragment();
		fragments.add(flQuanExpiredFragment);

		FangLianQuanActivityAdapter fragmentPagerAdapter = new FangLianQuanActivityAdapter(
				getSupportFragmentManager(), fragments);
		vp_flquan_pager.setAdapter(fragmentPagerAdapter);
		fragmentPagerAdapter.setFragments(fragments);
		vp_flquan_pager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.rb_nouse_fanglianquan:
			RadioButton rButton1 = (RadioButton) view;
			clickBackage_myParticipate.startAnimation(switchAnimation(rButton1));
			rb_nouse_fanglianquan.setTextColor(Color.parseColor("#ed4c4c"));
			rb_used_fanglianquan.setTextColor(Color.parseColor("#333333"));
			rb_expired_fanglianquan.setTextColor(Color.parseColor("#333333"));
			vp_flquan_pager.setCurrentItem(0);
			break;

		case R.id.rb_used_fanglianquan:
			RadioButton rButton2 = (RadioButton)view;
			clickBackage_myParticipate.startAnimation(switchAnimation(rButton2));
			rb_nouse_fanglianquan.setTextColor(Color.parseColor("#333333"));
			rb_used_fanglianquan.setTextColor(Color.parseColor("#ed4c4c"));
			rb_expired_fanglianquan.setTextColor(Color.parseColor("#333333"));
			vp_flquan_pager.setCurrentItem(1);
			break;

		case R.id.rb_expired_fanglianquan:
			RadioButton rButton3 = (RadioButton)view;
			clickBackage_myParticipate.startAnimation(switchAnimation(rButton3));
			rb_nouse_fanglianquan.setTextColor(Color.parseColor("#333333"));
			rb_used_fanglianquan.setTextColor(Color.parseColor("#333333"));
			rb_expired_fanglianquan.setTextColor(Color.parseColor("#ed4c4c"));
			vp_flquan_pager.setCurrentItem(2);
			break;
		default:
			break;
		}
	}

	// 配置滑动事件
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(final int position) {
			rb_nouse_fanglianquan.setTextColor(Color.parseColor("#333333"));
			rb_used_fanglianquan.setTextColor(Color.parseColor("#333333"));
			rb_expired_fanglianquan.setTextColor(Color.parseColor("#333333"));
			RadioButton radioButton = (RadioButton) radioGroup_option_fanglianquan
					.getChildAt(position * 2);
			radioButton.setTextColor(Color.parseColor("#ed4c4c"));
			clickBackage_myParticipate
					.startAnimation(switchAnimation(radioButton));

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	// 切换动画
	private AnimationSet switchAnimation(RadioButton rb) {
		// 每次的位移动画
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation;
		// 从上次的位置移动到本次选择位置
		translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft,
				rb.getLeft(), 0f, 0f);
		animationSet.addAnimation(translateAnimation);
		animationSet.setFillBefore(true);
		animationSet.setFillAfter(true);
		animationSet.setDuration(300);

		mCurrentCheckedRadioLeft = rb.getLeft();// 更新当前蓝色横条距离左边的距离
		return animationSet;
	}

}
