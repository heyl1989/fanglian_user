package com.flzc.fanglian.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseFragment;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.BannerBean;
import com.flzc.fanglian.model.BannerBean.Result.BannerList;
import com.flzc.fanglian.model.HeadNewsBean;
import com.flzc.fanglian.model.HeadNewsBean.Result.HeadNewsList;
import com.flzc.fanglian.model.NewHouseListBean;
import com.flzc.fanglian.model.RecommendActivityBean;
import com.flzc.fanglian.model.RecommendActivityBean.Result;
import com.flzc.fanglian.model.RecommendActivityBean.Result.RecommendActivity;
import com.flzc.fanglian.ui.adapter.BannerPagerAdapter;
import com.flzc.fanglian.ui.adapter.HomeHotBuildAdapter;
import com.flzc.fanglian.ui.agent_activity.AgentRecommendActivity;
import com.flzc.fanglian.ui.bidding_activity.BiddingActivity;
import com.flzc.fanglian.ui.everyday_special_activity.EveryDaySpecialActivity;
import com.flzc.fanglian.ui.home.AllActivityListActivity;
import com.flzc.fanglian.ui.home.ChooseCityActivity;
import com.flzc.fanglian.ui.home.SearchActivity;
import com.flzc.fanglian.ui.newhouse.NewHouseDetailActivity;
import com.flzc.fanglian.util.AndroidUnit;
import com.flzc.fanglian.util.JudgeAcctivityStateUtil;
import com.flzc.fanglian.view.AutoTextView;

/**
 * 
 * @ClassName: HomeFragment
 * @Description: 首页
 * @author: LU
 * @date: 2016-3-4 下午3:13:11
 */
public class HomeFragment extends BaseFragment implements OnClickListener,
		ViewPager.OnPageChangeListener {

	private View view;
	private RelativeLayout layout_titleLayout_mainFragment;// 头部布局
	private RelativeLayout rl_specialHouse_home, rl_agent_home,
			rl_bidding_home, rl_moreAct_home, layout_cityLayout_mainFragment;// 三个活动列表入口
																				// 更多推荐活动入口
																				// 选择城市入口
	private ImageView ed_search_mainFragment;
	private AutoTextView mHeadNews;
	private ViewPager mViewpager;

	private static int sCount;
	private List<HeadNewsBean.Result.HeadNewsList> headNewsList = new ArrayList<HeadNewsBean.Result.HeadNewsList>();
	private List<BannerBean.Result.BannerList> bannerList = new ArrayList<BannerBean.Result.BannerList>();
	private List<Result> activityList = new ArrayList<Result>();
	private List<NewHouseListBean.Result> newHouseList = new ArrayList<NewHouseListBean.Result>();
	private int count;
	private boolean isLock;
	// 延时轮播
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				mHeadNews.next();
				count++;
				if (headNewsList.size() != 0) {
					mHeadNews.setText(headNewsList.get(
							count % headNewsList.size()).getMemo());
				}
				super.sendEmptyMessageDelayed(0, 3000);
				break;
			case 1:
				mViewpager
						.setCurrentItem(mViewpager.getCurrentItem() + 1, true);
				super.sendEmptyMessageDelayed(1, 3000);
				break;
			}
		}
	};
	private LinearLayout mActivityContainer;
	private TextView tv_city;
	private LinearLayout ll_dots;
	protected ArrayList<ImageView> dotImgList = new ArrayList<ImageView>();
	private RelativeLayout rl_morehot_build;
	private ListView lv_home_hotbuild;
	private HomeHotBuildAdapter hotBuildAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_home2, container, false);
		sCount = Integer.MAX_VALUE;
		count = sCount / 2;
		isLock = false;
		headNewsList.clear();
		bannerList.clear();
		activityList.clear();
		dotImgList.clear();
		newHouseList.clear();
		if(ll_dots != null){
			ll_dots.removeAllViews();
		}
		initItem();
		initHearNews();
		initAvtivity();
		initBanner();
		initNewHouseList();

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		boolean isRefresh = tv_city.getText().equals(
				UserInfoData.getData(Constant.LOC_CITY_NAME, "城市名"));
		if (!isRefresh) {
			headNewsList.clear();
			bannerList.clear();
			activityList.clear();
			dotImgList.clear();
			newHouseList.clear();
			ll_dots.removeAllViews();
			// 需要刷新
			tv_city.setText(UserInfoData.getData(Constant.LOC_CITY_NAME, "城市名"));
			initHearNews();
			initAvtivity();
			initBanner();
			initNewHouseList();
		}
		if (isRefresh && isLock) {
			handler.sendEmptyMessage(1);
			handler.sendEmptyMessage(0);
		}

	}

	@SuppressWarnings("deprecation")
	private void initItem() {
		// 城市名
		tv_city = (TextView) view.findViewById(R.id.tv_city_mainFragment);
		if (null == UserInfoData.getData(Constant.LOC_CITY_NAME, "城市名")) {
			tv_city.setText("城市名");
		} else {
			tv_city.setText(UserInfoData.getData(Constant.LOC_CITY_NAME, "城市名"));
		}
		// 搜索
		ed_search_mainFragment = (ImageView) view
				.findViewById(R.id.img_search_mainFragment);
		ed_search_mainFragment.setOnClickListener(this);
		// 选择城市
		layout_cityLayout_mainFragment = (RelativeLayout) view
				.findViewById(R.id.layout_cityLayout_mainFragment);
		layout_cityLayout_mainFragment.setOnClickListener(this);
		// 轮播
		mViewpager = (ViewPager) view.findViewById(R.id.autoViewpager_main);
//      //宽高比是1.78(width/height)
        int width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        float height = width/6.42f;//得到对应的高
        mViewpager.getLayoutParams().height = (int)height;
        mViewpager.requestLayout();
		ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);
		// 房链头条
		mHeadNews = (AutoTextView) view
				.findViewById(R.id.autoTextview_headTitle_mainFragment);
		mHeadNews.setText("房链头条加载中...");
		// 推荐活动
		rl_moreAct_home = (RelativeLayout) view
				.findViewById(R.id.rl_moreAct_home);
		rl_moreAct_home.setOnClickListener(this);
		mActivityContainer = (LinearLayout) view
				.findViewById(R.id.ll_activity_container);

		// 最下面三个按钮，天天特价，优惠房，竞拍
		rl_specialHouse_home = (RelativeLayout) view
				.findViewById(R.id.rl_specialHouse_home);
		rl_specialHouse_home.setOnClickListener(this);

		rl_agent_home = (RelativeLayout) view.findViewById(R.id.rl_agent_home);
		rl_agent_home.setOnClickListener(this);

		rl_bidding_home = (RelativeLayout) view
				.findViewById(R.id.rl_bidding_home);
		rl_bidding_home.setOnClickListener(this);
		// 热门楼盘
		rl_morehot_build = (RelativeLayout) view
				.findViewById(R.id.rl_morehot_build);
		rl_morehot_build.setOnClickListener(this);
		lv_home_hotbuild = (ListView) view.findViewById(R.id.lv_home_hotbuild);
		lv_home_hotbuild.setFocusable(false);
		hotBuildAdapter = new HomeHotBuildAdapter(mActivity, newHouseList);
		lv_home_hotbuild.setAdapter(hotBuildAdapter);
		lv_home_hotbuild.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mActivity,
						NewHouseDetailActivity.class);
				intent.putExtra("buildingId", newHouseList.get(position)
						.getId() + "");
				startActivity(intent);
			}
		});
	}

	/**
	 * 
	 * @Title: initHearNews
	 * @Description: 请求房链头条数据
	 * @return: void
	 */
	private void initHearNews() {
		Map<String, String> map = new HashMap<String, String>();
		SimpleRequest<HeadNewsBean> req = new SimpleRequest<HeadNewsBean>(
				API.HEADLINE, HeadNewsBean.class, new Listener<HeadNewsBean>() {

					@Override
					public void onResponse(HeadNewsBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								List<HeadNewsList> list = response.getResult()
										.getList();
								headNewsList.addAll(list);
								// 发送延时消息
								handler.sendEmptyMessage(0);
							} else {
								showTost(response.getMsg());
							}

						}

					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	/**
	 * 
	 * @Title: initBanner
	 * @Description: 请求轮播数据
	 * @return: void
	 */
	private void initBanner() {
		//UserInfoData.getData(Constant.LOC_CITY_ID, "52")
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("cityId",UserInfoData.getData(Constant.LOC_CITY_ID, "52"));
		SimpleRequest<BannerBean> req = new SimpleRequest<BannerBean>(
				API.BANNER, BannerBean.class, new Listener<BannerBean>() {

					@Override
					public void onResponse(BannerBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								List<BannerList> list = response.getResult()
										.getList();
								bannerList.addAll(list);
								// 添加小圆点
								dotImgList = new ArrayList<ImageView>();
								for (int i = 0; i < bannerList.size(); i++) {
									ImageView imgDot = new ImageView(mActivity);
									LinearLayout.LayoutParams ll_dot_lp = new LinearLayout.LayoutParams(
											LinearLayout.LayoutParams.WRAP_CONTENT,
											LinearLayout.LayoutParams.WRAP_CONTENT);
									ll_dot_lp.leftMargin = AndroidUnit.dip2px(
											mActivity, 8);
									ll_dot_lp.width = AndroidUnit.dip2px(
											mActivity, 8);
									ll_dot_lp.height = AndroidUnit.dip2px(
											mActivity, 8);
									imgDot.setLayoutParams(ll_dot_lp);
									if (i == 0) {
										imgDot.setBackgroundResource(R.drawable.shape_vp_checked);
									} else {
										imgDot.setBackgroundResource(R.drawable.shape_vp_unchecked);
									}
									dotImgList.add(imgDot);
									ll_dots.addView(imgDot);
								}
								mViewpager.setAdapter(new BannerPagerAdapter(bannerList));
								// 默认设置中间的某个item
								mViewpager.setCurrentItem(bannerList.size() * 100000);
								// 延时发送消息
								handler.sendEmptyMessageDelayed(1, 3000);
								mViewpager
										.setOnPageChangeListener(HomeFragment.this);

							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);
	}

	/**
	 * 
	 * @Title: initAvtivity
	 * @Description: 请求推荐活动数据
	 * @return: void
	 */
	private void initAvtivity() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cityId", UserInfoData.getData(Constant.LOC_CITY_ID, "52"));
		map.put("page", "1");
		map.put("pageSize", "5");
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("type", "2");
		SimpleRequest<RecommendActivityBean> req = new SimpleRequest<RecommendActivityBean>(
				API.QUERY_ACTIVITY_RECOMMEND, RecommendActivityBean.class,
				new Listener<RecommendActivityBean>() {
					@Override
					public void onResponse(RecommendActivityBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								List<Result> list = response.getResult();
								for (Result result : list) {
									if (result.getActivity().getActivityType() != 3904) {
										activityList.add(result);
									}
								}
								addActivity();
							} else {
								showTost(response.getMsg());
							}

						}

					}

				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	/**
	 * 
	 * @Title: initNewHouseList
	 * @Description: 请求热门楼盘
	 * @param i
	 * @return: void
	 */
	private void initNewHouseList() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cityId", UserInfoData.getData(Constant.LOC_CITY_ID, "52"));
		SimpleRequest<NewHouseListBean> req = new SimpleRequest<NewHouseListBean>(
				API.SUGGESTED_BUILDING, NewHouseListBean.class,
				new Listener<NewHouseListBean>() {
					@Override
					public void onResponse(NewHouseListBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								List<NewHouseListBean.Result> list = response
										.getResult();
								newHouseList.addAll(list);
								hotBuildAdapter.notifyDataSetChanged();
							} else {
								showTost(response.getMsg());
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	/**
	 * 
	 * @Title: addActivity
	 * @Description: 动态添加活动
	 * @return: void
	 */
	private void addActivity() {
		int count;
		if (activityList.size() > 5) {
			count = 5;
		} else {
			count = activityList.size();
		}
		mActivityContainer.removeAllViews();
		for (final Result result : activityList) {
			FrameLayout framelayout = (FrameLayout) LayoutInflater.from(
					mActivity).inflate(R.layout.item_home_horizantal_scrll,
					mActivityContainer, false);
			// 查找控件
			ImageView img_activity = (ImageView) framelayout
					.findViewById(R.id.img_activity);
			ImageView img_activity_state = (ImageView) framelayout
					.findViewById(R.id.img_activity_state);
			TextView tv_activity_name = (TextView) framelayout
					.findViewById(R.id.tv_activity_name);
			TextView tv_build_name = (TextView) framelayout
					.findViewById(R.id.tv_build_name);
			// 控件赋值
			imageLoader.displayImage(result.getMaster(), img_activity, options);
			long startTime = result.getActivity().getActStartTime();
			long endTime = 	result.getActivity().getActEndTime();
			int atyState = JudgeAcctivityStateUtil.getState(startTime, endTime);
			switch (atyState) {
			case 0:
				// 即将开始
				img_activity_state
						.setImageResource(R.drawable.coming_soon_home);
				break;
			case 1:
				// 进行中
				img_activity_state.setImageResource(R.drawable.doing_home);
				break;
			case 2:
				// 结束
				img_activity_state.setImageResource(R.drawable.end_home);
				break;
			}
			tv_activity_name.setText(result.getActivity().getActivityName());
			tv_build_name.setText(result.getName());
			mActivityContainer.addView(framelayout);
			// 每一个的点击事件
			framelayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					RecommendActivity activity = result.getActivity();
					String activityType = activity.getActivityType() + "";
					String activityId = activity.getActivityId() + "";
					String activityPoolId = activity.getId() + "";
					String buildingId = result.getId() + "";
					String remind = result.getRemaind();
					String remindId = result.getRemaindId();

					if (activityType.equals("3901")) {
						Intent intent = new Intent(mActivity,
								BiddingActivity.class);
						intent.putExtra("activityPoolId", activityPoolId);
						intent.putExtra("buildingId", buildingId);
						intent.putExtra("activityId", activityId);
						intent.putExtra("remind", remind);
						intent.putExtra("remindId", remindId);
						startActivity(intent);
					} else if (activityType.equals("3902")) {
						Intent intent = new Intent(mActivity,
								EveryDaySpecialActivity.class);
						intent.putExtra("activityPoolId", activityPoolId);
						intent.putExtra("buildingId", buildingId);
						intent.putExtra("activityId", activityId);
						intent.putExtra("remind", remind);
						intent.putExtra("remindId", remindId);
						startActivity(intent);
					} else if (activityType.equals("3903")) {
						Intent intent = new Intent(mActivity,
								AgentRecommendActivity.class);
						intent.putExtra("activityPoolId", activityPoolId);
						intent.putExtra("buildingId", buildingId);
						intent.putExtra("activityId", activityId);
						intent.putExtra("remind", remind);
						intent.putExtra("remindId", remindId);
						startActivity(intent);
					}
				}
			});
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_specialHouse_home:// 点击天天特价
			Intent specialIntent = new Intent(mActivity,
					AllActivityListActivity.class);
			specialIntent.putExtra("listType", 3902);
			mActivity.startActivity(specialIntent);
			break;
		case R.id.rl_agent_home:// 点击优惠房
			Intent priviledgeIntent = new Intent(mActivity,
					AllActivityListActivity.class);
			priviledgeIntent.putExtra("listType", 3903);
			mActivity.startActivity(priviledgeIntent);
			break;
		case R.id.rl_bidding_home:// 点击竞拍
			Intent biddingIntent = new Intent(mActivity,
					AllActivityListActivity.class);
			biddingIntent.putExtra("listType", 3901);
			mActivity.startActivity(biddingIntent);
			break;
		case R.id.img_search_mainFragment:// 点击搜索
			goOthers(SearchActivity.class);
			break;
		case R.id.rl_moreAct_home:// 点击更多推荐活动,后来该为进入特惠房
			// goOthers(RecommendAtyListActivity.class);
			Intent tehuiIntent = new Intent(mActivity,
					AllActivityListActivity.class);
			tehuiIntent.putExtra("listType", 3900);
			mActivity.startActivity(tehuiIntent);
			break;
		case R.id.layout_cityLayout_mainFragment:
			// 城市选择
			goOthers(ChooseCityActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		isLock = true;
		handler.removeCallbacksAndMessages(null);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int showing) {
		for (int i = 0; i < dotImgList.size(); i++) {
			if (i == showing % dotImgList.size()) {
				dotImgList.get(i).setBackgroundResource(
						R.drawable.shape_vp_checked);
			} else {
				dotImgList.get(i).setBackgroundResource(
						R.drawable.shape_vp_unchecked);
			}
		}
	}

}
