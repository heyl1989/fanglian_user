package com.flzc.fanglian.ui.me;

import java.util.ArrayList;
import java.util.List;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseFragmentActivity;
import com.flzc.fanglian.ui.adapter.ViewpagerAdapter;
import com.flzc.fanglian.ui.fragment.MyParticipateListFragment;

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

/**
 * 
 * @ClassName: MyParticipateActivity 
 * @Description: 我参与的活动  列表页
 * @author: Tien.
 * @date: 2016年3月3日 下午7:15:32
 */
public class MyParticipateActivity extends BaseFragmentActivity implements OnClickListener{
	private TextView tv_title;
	private RelativeLayout rl_back;
	
	private ViewpagerAdapter vAdapter;
	private List<Fragment> fragments;
	private ViewPager viewPager_myParticipate;
	private float mCurrentCheckedRadioLeft = 0.0f;// 当前被选中的RadioButton距离左侧的距离
	private RadioGroup rg_myPaticipate;
	private ImageView clickBackage_myParticipate;
	private RadioButton rb_all_myParticipate,rb_doing_myParticipate,rb_end_myParticipate;
	private int clickBackageHeight = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_my_participate);
		initItem();
	}
	
	@SuppressWarnings("deprecation")
	private void initItem(){
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("我参与的活动");
		
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		
		fragments = new ArrayList<Fragment>();
		viewPager_myParticipate = (ViewPager) findViewById(R.id.viewpager_myParticipateList);
		rg_myPaticipate = (RadioGroup) findViewById(R.id.radioGroup_option_myParticipateList);
		clickBackage_myParticipate = (ImageView) findViewById(R.id.clickBackage_myParticipateList);
		rb_all_myParticipate = (RadioButton) findViewById(R.id.rb_all_myParticipateList);
		rb_all_myParticipate.setOnClickListener(this);
		rb_doing_myParticipate = (RadioButton) findViewById(R.id.rb_doing_myParticipateList);
		rb_doing_myParticipate.setOnClickListener(this);
		rb_end_myParticipate = (RadioButton) findViewById(R.id.rb_end_myParticipateList);
		rb_end_myParticipate.setOnClickListener(this);
		
		//actListType    0  为全部   2 为进行中  3为已结束
		Bundle bundle = new Bundle();
		bundle.putString("actListType", "0");
		
		Bundle bundle1 = new Bundle();
		bundle1.putString("actListType", "2");
		
		Bundle bundle2 = new Bundle();
		bundle2.putString("actListType", "3");
		
		//全部
		Fragment fragment = new MyParticipateListFragment();
		fragment.setArguments(bundle);
		fragments.add(fragment);
		
		//未消费
		Fragment fragment2 = new MyParticipateListFragment();
		fragment2.setArguments(bundle1);
		fragments.add(fragment2);
		
		//已消费
		Fragment fragment3 = new MyParticipateListFragment();
		fragment3.setArguments(bundle2);
		fragments.add(fragment3);
		
		clickBackageHeight = clickBackage_myParticipate.getLayoutParams().height;
		vAdapter = new ViewpagerAdapter(getSupportFragmentManager(), fragments);
		viewPager_myParticipate.setAdapter(vAdapter);
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int srceen_width = displayMetrics.widthPixels;

		clickBackage_myParticipate.setLayoutParams(new LinearLayout.LayoutParams(
				srceen_width/3 + this.rb_doing_myParticipate.getPaddingLeft()
						+ this.rb_end_myParticipate.getPaddingRight(),this.clickBackageHeight));
	
		viewPager_myParticipate.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				rb_all_myParticipate
				.setTextColor(Color.parseColor("#333333"));
				rb_doing_myParticipate
				.setTextColor(Color.parseColor("#333333"));
				rb_end_myParticipate
				.setTextColor(Color.parseColor("#333333"));
				RadioButton radioButton = (RadioButton) rg_myPaticipate.getChildAt(arg0 * 2);
				radioButton.setTextColor(Color.parseColor("#ed4c4c"));
				clickBackage_myParticipate.startAnimation(switchAnimation(radioButton));
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	
	}
	
	//切换动画
	private AnimationSet switchAnimation(RadioButton rb) {
		//每次的位移动画
		AnimationSet animationSet = new AnimationSet(
				true);
		TranslateAnimation translateAnimation;
		// 从上次的位置移动到本次选择位置
		translateAnimation = new TranslateAnimation(
				mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
		animationSet.addAnimation(translateAnimation);
		animationSet.setFillBefore(true);
		animationSet.setFillAfter(true);
		animationSet.setDuration(300);
		
		mCurrentCheckedRadioLeft = rb.getLeft();// 更新当前蓝色横条距离左边的距离
		return animationSet;
	}

	@Override
	public void onClick(View v) {
		if (v == rl_back) {
			finish();
		}else if (v == rb_all_myParticipate) {// 点击全部活动
			RadioButton rButton = (RadioButton) v;
			clickBackage_myParticipate.startAnimation(switchAnimation(rButton));
			rb_all_myParticipate.setTextColor(Color.parseColor("#ed4c4c"));
			rb_doing_myParticipate.setTextColor(Color.parseColor("#333333"));
			rb_end_myParticipate.setTextColor(Color.parseColor("#333333"));
			viewPager_myParticipate.setCurrentItem(0);
		}else if (v == rb_doing_myParticipate) {// 点击正在进行的活动
			RadioButton rButton = (RadioButton) v;
			clickBackage_myParticipate.startAnimation(switchAnimation(rButton));
			rb_all_myParticipate.setTextColor(Color.parseColor("#333333"));
			rb_doing_myParticipate.setTextColor(Color.parseColor("#ed4c4c"));
			rb_end_myParticipate.setTextColor(Color.parseColor("#333333"));
			viewPager_myParticipate.setCurrentItem(1);
		}else if (v == rb_end_myParticipate) {// 点击已结束的活动
			RadioButton rButton = (RadioButton) v;
			clickBackage_myParticipate.startAnimation(switchAnimation(rButton));
			rb_all_myParticipate.setTextColor(Color.parseColor("#333333"));
			rb_doing_myParticipate.setTextColor(Color.parseColor("#333333"));
			rb_end_myParticipate.setTextColor(Color.parseColor("#ed4c4c"));
			viewPager_myParticipate.setCurrentItem(2);
		}
	}
}
