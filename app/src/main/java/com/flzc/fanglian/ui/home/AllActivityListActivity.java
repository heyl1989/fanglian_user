package com.flzc.fanglian.ui.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseFragmentActivity;
import com.flzc.fanglian.model.SortTypeBean;
import com.flzc.fanglian.model.SortTypeBean.SortList;
import com.flzc.fanglian.ui.adapter.SortOptionAdapter;
import com.flzc.fanglian.ui.adapter.ViewpagerAdapter;
import com.flzc.fanglian.ui.fragment.every_list.EveryDaySpecialBeginListFragment;
import com.flzc.fanglian.ui.fragment.every_list.EveryDaySpecialListFragment;
import com.flzc.fanglian.ui.fragment.every_list.EveryDaySpecialOnListFragment;

/**
 * 
 * @ClassName: AllActivityListActivity
 * @Description: 天天特价，从首页跳转过来
 * @author: LU
 * @date: 2016-3-12 上午11:19:48
 */
public class AllActivityListActivity extends BaseFragmentActivity implements
		OnClickListener, OnCheckedChangeListener, OnItemClickListener {
	private RelativeLayout rl_atylist_back;
	private TextView tv_atylist_title;
	private ImageView iv_aty_sort;
	private int listType;// 特惠房3900，天天特价3902，优惠房3903，竞拍3901
	private ViewpagerAdapter vAdapter;
	private List<Fragment> fragments;
	private ViewPager viewPager_atyList;
	private float mCurrentCheckedRadioLeft = 0.0f;// 当前被选中的RadioButton距离左侧的距离
	private RadioGroup rg_atyList;
	private ImageView clickBackage_atyList;
	private RadioButton rb_all_atyList, rb_doing_atyList, rb_coming_atyList;
	private int clickBackageHeight = 0;
	private ListView listview_areaOption_popupWindow;
	private SortOptionAdapter optionAdapter;
	protected int fragmentPosition;
	private EveryDaySpecialListFragment atyDiscount;
	private EveryDaySpecialOnListFragment atyOnDiscount;
	private EveryDaySpecialBeginListFragment atyBeginDiscount;
	private View ly_sort_pop;
	private List<SortList> sortList = new ArrayList<SortList>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aty_list);
		listType = getIntent().getIntExtra("listType", 1);
		initView();
	}

	/**
	 * 
	 * @Title: initView
	 * @Description: 初始化视图
	 * @return: void
	 */
	private void initView() {
		// 标题栏
		rl_atylist_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_atylist_back.setOnClickListener(this);
		tv_atylist_title = (TextView) findViewById(R.id.tv_title);
		initTitle();
		
		// 排序
		iv_aty_sort = (ImageView) findViewById(R.id.iv_aty_sort);
		iv_aty_sort.setOnClickListener(this);
		// 三个选择
		rg_atyList = (RadioGroup) findViewById(R.id.radioGroup_option_atyList);
		rg_atyList.setOnCheckedChangeListener(this);
		rb_all_atyList = (RadioButton) findViewById(R.id.rb_all_atyList);
		rb_doing_atyList = (RadioButton) findViewById(R.id.rb_doing_atyList);
		rb_coming_atyList = (RadioButton) findViewById(R.id.rb_coming_atyList);
		// 排序的pop
		ly_sort_pop = findViewById(R.id.ly_sort_pop);
		ly_sort_pop.setOnClickListener(this);
		ly_sort_pop.findViewById(R.id.img_sort_bg).setOnClickListener(this);
		// 滑动的红线
		clickBackage_atyList = (ImageView) findViewById(R.id.clickBackage_atyList);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int srceen_width = displayMetrics.widthPixels;
		clickBackageHeight = clickBackage_atyList.getLayoutParams().height;
		clickBackage_atyList.setLayoutParams(new LinearLayout.LayoutParams(
				srceen_width / 3 + this.rb_doing_atyList.getPaddingLeft()
						+ this.rb_coming_atyList.getPaddingRight(),
				this.clickBackageHeight));
		// viewPager
		initViewPager();
		//排序初始化
		SortTypeBean sortBean1 = new SortTypeBean();
		SortTypeBean.SortList sortMap1 = sortBean1.new SortList();
		sortMap1.setSortName("按发布时间");
		sortList.add(sortMap1);
		SortTypeBean sortBean2 = new SortTypeBean();
		SortTypeBean.SortList sortMap2 = sortBean2.new SortList();
		sortMap2.setSortName("价格从高到低");
		sortList.add(sortMap2);
		SortTypeBean sortBean3 = new SortTypeBean();
		SortTypeBean.SortList sortMap3 = sortBean3.new SortList();
		sortMap3.setSortName("价格从低到高");
		sortList.add(sortMap3);
	}

	private void initTitle() {
		switch (listType) {
		case 3900:
			tv_atylist_title.setText("特惠房");
			break;
		case 3902:
			tv_atylist_title.setText("天天特价");
			break;
		case 3903:
			tv_atylist_title.setText("优惠房");
			break;
		case 3901:
			tv_atylist_title.setText("竞拍");
			break;
		}
	}

	private void initViewPager() {
		viewPager_atyList = (ViewPager) findViewById(R.id.viewpager_atyList);

		fragments = new ArrayList<Fragment>();

		atyDiscount = new EveryDaySpecialListFragment();
		Bundle bundle1 = new Bundle();
		bundle1.putString("listType", listType + "");
		atyDiscount.setArguments(bundle1);
		fragments.add(atyDiscount);
		atyOnDiscount = new EveryDaySpecialOnListFragment();
		Bundle bundle2 = new Bundle();
		bundle2.putString("listType", listType + "");
		atyOnDiscount.setArguments(bundle2);
		fragments.add(atyOnDiscount);
		atyBeginDiscount = new EveryDaySpecialBeginListFragment();
		Bundle bundle3 = new Bundle();
		bundle3.putString("listType", listType + "");
		atyBeginDiscount.setArguments(bundle3);
		fragments.add(atyBeginDiscount);

		vAdapter = new ViewpagerAdapter(getSupportFragmentManager(), fragments);
		viewPager_atyList.setAdapter(vAdapter);

		initViewPagerListener();

	}

	/**
	 * 
	 * @Title: initViewPagerListener
	 * @Description: viewPager滑动监听
	 * @return: void
	 */
	@SuppressWarnings("deprecation")
	private void initViewPagerListener() {
		viewPager_atyList.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				fragmentPosition = position;
				rb_all_atyList.setTextColor(Color.parseColor("#333333"));
				rb_doing_atyList.setTextColor(Color.parseColor("#333333"));
				rb_coming_atyList.setTextColor(Color.parseColor("#333333"));
				RadioButton radioButton = (RadioButton) rg_atyList
						.getChildAt(position * 2);
				radioButton.setTextColor(Color.parseColor("#ed4c4c"));
				clickBackage_atyList
						.startAnimation(switchAnimation(radioButton));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

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

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 返回按钮
		case R.id.rl_back:
			finish();
			break;
		// 排序按钮
		case R.id.iv_aty_sort:
			if (ly_sort_pop.getVisibility() == View.GONE) {
				ly_sort_pop.setVisibility(View.VISIBLE);
				showSortWindow();
			} else {
				ly_sort_pop.setVisibility(View.GONE);
			}
			break;
		case R.id.img_sort_bg:
			if (ly_sort_pop.getVisibility() == View.VISIBLE) {
				ly_sort_pop.setVisibility(View.GONE);
			} 
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_all_atyList:
			clickBackage_atyList
					.startAnimation(switchAnimation((RadioButton) rb_all_atyList));
			rb_all_atyList.setTextColor(Color.parseColor("#ed4c4c"));
			rb_doing_atyList.setTextColor(Color.parseColor("#333333"));
			rb_coming_atyList.setTextColor(Color.parseColor("#333333"));
			viewPager_atyList.setCurrentItem(0);
			break;
		case R.id.rb_doing_atyList:
			clickBackage_atyList
					.startAnimation(switchAnimation((RadioButton) rb_doing_atyList));
			rb_all_atyList.setTextColor(Color.parseColor("#333333"));
			rb_doing_atyList.setTextColor(Color.parseColor("#ed4c4c"));
			rb_coming_atyList.setTextColor(Color.parseColor("#333333"));
			viewPager_atyList.setCurrentItem(1);
			break;
		case R.id.rb_coming_atyList:
			clickBackage_atyList
					.startAnimation(switchAnimation((RadioButton) rb_coming_atyList));
			rb_all_atyList.setTextColor(Color.parseColor("#333333"));
			rb_doing_atyList.setTextColor(Color.parseColor("#333333"));
			rb_coming_atyList.setTextColor(Color.parseColor("#ed4c4c"));
			viewPager_atyList.setCurrentItem(2);
			break;
		}

	}

	// 弹出选择排序方式的弹窗
	private void showSortWindow() {
		
		listview_areaOption_popupWindow = (ListView) findViewById(R.id.listview_areaOption_popupWindow);
		optionAdapter = new SortOptionAdapter(this, sortList, 1);
		listview_areaOption_popupWindow.setAdapter(optionAdapter);
		listview_areaOption_popupWindow.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		for(int i=0;i<sortList.size();i++){
			if(position == i){
				sortList.get(i).setSelect(true);
			}else{
				sortList.get(i).setSelect(false);
			}
		}
		optionAdapter.notifyDataSetChanged();
		switch (fragmentPosition) {
		case 0:
			atyDiscount.sortType = position + 1;
			atyDiscount.isSort = true;
			atyDiscount.initData(position + 1 + "",1);
			break;
		case 1:
			atyOnDiscount.sortType = position + 1;
			atyOnDiscount.isSort = true;
			atyOnDiscount.initData(position + 1 + "",1);
			break;
		case 2:
			atyBeginDiscount.sortType = position + 1;
			atyBeginDiscount.isSort = true;
			atyBeginDiscount.initData(position + 1 + "",1);
			break;
		}
		ly_sort_pop.setVisibility(View.GONE);
	}
}
