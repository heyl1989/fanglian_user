package com.flzc.fanglian.ui.bidding_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.BaseInfoBean;
import com.flzc.fanglian.model.BiddingInfoBean;
import com.flzc.fanglian.model.BiddingInfoBean.Result;
import com.flzc.fanglian.model.BiddingInfoBean.Result.Auction;
import com.flzc.fanglian.model.BiddingInfoBean.Result.Building;
import com.flzc.fanglian.model.BiddingInfoBean.Result.Share;
import com.flzc.fanglian.model.DaySpecialPartUserListBean;
import com.flzc.fanglian.model.DaySpecialPartUserListBean.Result.UserList;
import com.flzc.fanglian.model.RemindMeBean;
import com.flzc.fanglian.ui.ActivityRulesWebViewActivity;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.ui.newhouse.NewHouseCircumMapActivity;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.JudgeAcctivityStateUtil;
import com.flzc.fanglian.util.LogUtil;
import com.flzc.fanglian.util.SaveUserShareUtil;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.CircleImageView;
import com.flzc.fanglian.view.ObservableScrollView;
import com.flzc.fanglian.view.ObservableScrollView.ScrollViewListener;
import com.flzc.fanglian.view.TimeView;
import com.flzc.fanglian.view.TimeViewWhiteWordHM;
import com.flzc.fanglian.view.dialog.CustomDialog;
import com.flzc.fanglian.view.dialog.ShareNoLoginDialog;
import com.flzc.fanglian.view.pop.ShareToMoneyPopWindow;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 
 * @ClassName: BiddingActivity
 * @Description: 竞拍活动详情页
 * @author: LU
 * @date: 2016-3-7 上午10:54:44
 */
public class BiddingActivity extends BaseActivity implements OnClickListener ,ScrollViewListener{
	private RelativeLayout rl_back;
	private TextView tv_title;
	private TextView tv_right;
	private TextView tv_users;

	private ImageView img_preview;
	private TextView tv_bidding_time, tv_dot, tv_person_count;
	private TimeView tivm_time;
	private CircleImageView cimg_bidding_pone, cimg_bidding_ptwo, cimg_last;

	private UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constant.DESCRIPTOR);

	private String buildingInfo = "";// 房子的基本信息
	// 底部是活动未开始时候的布局
	private LinearLayout ll_bidding_bottom_one, ll_bidding_bottom_two;
	private ImageView img_consult_shop_two;
	private TextView tv_remind, tv_enroll;

	// 请求数据时候所用的参数
	private String buildingId;
	private String activityPoolId;
	private String activityId;
	private String houseSourceId;
	private int counts;// 剩余出价次数
	// 活动的状态 1==未开始 2==进行中 3==已结束
	private int state;
	// 提醒我的状态 0==未通知 1==已通知
	private String remind;
	// 取消提醒时候所用的参数
	private String remindId;
	private String userStatus;// 0为未登录，1为未报名、2为报名但未消费3次机会，3为报名了消费了三次机会
	// 活动的开始时间，下个页面用到（房源信息页面）
	private long startTime, endTime;
	private String minPrice;// 下个页面用到的竞拍价
	protected String shareUrl;
	protected String saleTell;
	protected String buildImg;
	protected String activityName;
	protected String mBuildName;
	private PopupWindow bottomWindow;
	protected int shareState;
	private TextView bidEndUsers;
	private TextView tv_partting;
	private TextView biddingGlance;
	private TextView biddingTitle;
	private TextView biddingStartPrice;
	private LinearLayout houseinfo;
	private ImageView houseMaster;
	private TextView houseKind;
	private TextView houseType;
	private TextView houseSize;
	private TextView houseFloor;
	private TextView housePosition;
	private TextView housePrice;
	private TextView houseTotal;
	private TextView bidPayMoney;
	private TextView bidTime;
	private TextView bidStartTime;
	private TextView bidEndTime;
	private LinearLayout bidRule;
	private ImageView bidHostmaster;
	private TextView bidHostname;
	private TextView bidHostcompany;
	private TextView bidHostposition;
	private TextView bidHostphone;
	private String lat;
	private String lon;
	private LinearLayout bg_downTime;
	private TextView enrollMoney;
	private TextView bid_state;
	private TextView state_second;
	private TimeViewWhiteWordHM time_hm_downtime;
	private ShareToMoneyPopWindow sharePop;
	private ImageView popImg;
	private LinearLayout ll_enRoll;
	private String city;
	public static BiddingActivity biddingActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bidding);
		biddingActivity = this;
		initView();
		initListener();
		initData();
	}

	@Override
	protected void onResume() {
		buildingInfo = "";
		getBiddingInfoData();
		setActivityCount();
		super.onResume();
	}

	private void initView() {
		// 标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("竞拍");
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setText("");
		tv_right.setCompoundDrawablesWithIntrinsicBounds(
				R.drawable.share, 0, 0, 0);
		tv_right.setCompoundDrawablePadding(0);
		//scrollView
		ObservableScrollView sv_bidding = (ObservableScrollView)findViewById(R.id.sv_bidding);
		sv_bidding.setScrollViewListener(this);			
		//预览大图
		img_preview = (ImageView) findViewById(R.id.img_preview);
		//倒计时及预览次数
		bg_downTime = (LinearLayout)findViewById(R.id.ll_bg_downtime);
		tv_bidding_time = (TextView) findViewById(R.id.tv_bidding_time);
		tivm_time = (TimeView) findViewById(R.id.tivm_time);
		biddingGlance = (TextView) findViewById(R.id.tv_activity_glance);
		//活动
		biddingTitle = (TextView)findViewById(R.id.tv_activity_title);
		biddingStartPrice = (TextView)findViewById(R.id.tv_activity_price);
		//房源信息
		houseinfo = (LinearLayout)findViewById(R.id.ll_houseinfo);
		houseMaster = (ImageView)findViewById(R.id.img_house_master);
		houseKind = (TextView)findViewById(R.id.tv_housekind);
		houseType = (TextView)findViewById(R.id.tv_house_type);
		houseSize = (TextView)findViewById(R.id.tv_house_size);
		houseFloor = (TextView)findViewById(R.id.tv_house_floor);
		housePosition = (TextView)findViewById(R.id.tv_house_position);
		housePrice = (TextView)findViewById(R.id.tv_house_price);
		houseTotal = (TextView)findViewById(R.id.tv_house_total);
		//竞拍规则
		bidPayMoney = (TextView)findViewById(R.id.tv_bidpaymoney);
		bidTime = (TextView)findViewById(R.id.tv_bidtime);
		bidStartTime = (TextView)findViewById(R.id.tv_bidstarttime);
		bidEndTime = (TextView)findViewById(R.id.tv_bidendtime);
		// 跳转用户列表
		tv_users = (TextView) findViewById(R.id.tv_users);
		tv_dot = (TextView) findViewById(R.id.tv_dot);
		tv_person_count = (TextView) findViewById(R.id.tv_person_count);
		bidEndUsers = (TextView) findViewById(R.id.tv_bidendusers);
		tv_partting = (TextView) findViewById(R.id.tv_partting);
		cimg_bidding_pone = (CircleImageView) findViewById(R.id.cimg_bidding_pone);
		cimg_bidding_ptwo = (CircleImageView) findViewById(R.id.cimg_bidding_ptwo);
		cimg_last = (CircleImageView) findViewById(R.id.cimg_last);
		//活动提供方
		bidHostmaster = (ImageView)findViewById(R.id.img_hostmaster);
		bidHostname = (TextView)findViewById(R.id.tv_hostname);
		bidHostcompany = (TextView)findViewById(R.id.tv_hostcompany);
		bidHostposition = (TextView)findViewById(R.id.tv_hostposition);
		bidHostphone = (TextView)findViewById(R.id.tv_hostphone);
		//竞拍须知
		bidRule = (LinearLayout)findViewById(R.id.ll_bidrule);
		//底部按钮
		ll_bidding_bottom_one = (LinearLayout) findViewById(R.id.ll_bidding_bottom_one);
		bid_state = (TextView)findViewById(R.id.tv_bid_state);
		state_second = (TextView)findViewById(R.id.tv_state_second);
		time_hm_downtime = (TimeViewWhiteWordHM)findViewById(R.id.time_hm_downtime);
		
		ll_bidding_bottom_two = (LinearLayout) findViewById(R.id.ll_bidding_bottom_two);
		img_consult_shop_two = (ImageView) findViewById(R.id.img_consult_shop_two);
		tv_remind = (TextView) findViewById(R.id.tv_remind);
		ll_enRoll = (LinearLayout) findViewById(R.id.ll_enRoll);
		tv_enroll = (TextView) findViewById(R.id.tv_enroll);
		enrollMoney = (TextView) findViewById(R.id.tv_enroll_money);

	}

	private void initListener() {
		rl_back.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		houseinfo.setOnClickListener(this);
		bidHostposition.setOnClickListener(this);
		bidHostphone.setOnClickListener(this);
		bidRule.setOnClickListener(this);
		tv_users.setOnClickListener(this);
		ll_bidding_bottom_one.setOnClickListener(this);
		img_consult_shop_two.setOnClickListener(this);
		tv_remind.setOnClickListener(this);
		ll_enRoll.setOnClickListener(this);
	}
	
	private void viewForData(Result result){
		
		Auction auction = result.getAuction();
		activityName = auction.getActivityName();
		city = auction.getCity();
		Building building = result.getBuilding();
		mBuildName = building.getName();
		remind = building.getRemaind();
		remindId = building.getRemaindId();
		saleTell = building.getSaleTel();
		counts = auction.getCounts();// 剩余出价次数
		houseSourceId = auction.getHouseSourceId() + "";
		Share share = result.getShare();
		shareState = share.getStatus();
		if(shareState == 1){
			showSharePop();
		}
		shareUrl = share.getShareUrl();
		// 主图
		buildImg = building.getMaster();
		imageLoader.displayImage(building.getMaster(),
				img_preview, options);
		userStatus = auction.getUserStatus();
		startTime = auction.getStartTime();
		endTime = auction.getEndTime();
		long currentTime = DateUtils.currentTime();
		state = JudgeAcctivityStateUtil.getState(
				startTime, endTime);
		// 0 即将开始, 1进行中，2已结束
		if (0 == state) {
			// 活动未开始
			bg_downTime.setBackgroundColor(Color.parseColor("#EE4B4C"));
			// 参与的人数
			if (auction.getCount() == 0) {
				tv_users.setVisibility(View.GONE);
			} else {
				tv_users.setVisibility(View.VISIBLE);
			}
			tv_person_count.setText("("
					+ auction.getCount() + ")人");
			int[] times = new int[4];
			times = JudgeAcctivityStateUtil
					.startTimeFormat(startTime);
			tivm_time.setTimes(times);
			tv_bidding_time.setText("开始时间：");
			ll_bidding_bottom_one.setVisibility(View.GONE);
			ll_bidding_bottom_two.setVisibility(View.VISIBLE);
			if ("2".equals(userStatus)|| "3".equals(userStatus)) {
				tv_enroll.setText("已报名");
				enrollMoney.setVisibility(View.GONE);
			} else if ("0".equals(userStatus)|| "1".equals(userStatus)) {
				tv_enroll.setText("去报名");
				enrollMoney.setText("保证金："+StringUtils.intMoney(auction.getDeposit())+"元");
			}
			if ("0".equals(remind)) {
				// 未通知
				tv_remind.setText("提醒我");
			} else if ("1".equals(remind)) {
				// 已通知
				tv_remind.setText("已设置提醒");
			}
		} else if (1 == state) {
			// 活动进行中
			bg_downTime.setBackgroundColor(Color.parseColor("#EE4B4C"));
			// 参与的人数
			if (auction.getCount() == 0) {
				tv_users.setVisibility(View.GONE);
			} else {
				tv_users.setVisibility(View.VISIBLE);
			}
			tv_person_count.setText("("+ auction.getCount() + ")人");
			long differentTime = endTime - currentTime;
			int[] times = new int[4];
			times = JudgeAcctivityStateUtil
					.downTimeFormat(differentTime);
			tivm_time.setDownTimes(times);
			tivm_time.run();
			tv_bidding_time.setText("距结束剩：");
			//底部状态
			ll_bidding_bottom_one.setVisibility(View.VISIBLE);
			ll_bidding_bottom_two.setVisibility(View.GONE);
			if ("0".equals(userStatus)|| "1".equals(userStatus)) {
				bid_state.setText("去报名");
				time_hm_downtime.setDownTimes(JudgeAcctivityStateUtil
						.downTimeFormat(endTime - currentTime));
				time_hm_downtime.run();
			}else if("2".equals(userStatus) && counts == 3){
				bid_state.setText("开始出价");
				time_hm_downtime.setDownTimes(JudgeAcctivityStateUtil
						.downTimeFormat(endTime - currentTime));
				time_hm_downtime.run();
			}else{
				bid_state.setText("出价");
				state_second.setText("剩余出价次数 "+counts+"次");
				time_hm_downtime.setVisibility(View.GONE);
			}
			
		} else if (2 == state) {
			// 活动已结束
			bg_downTime.setBackgroundColor(Color.parseColor("#aa000000"));
			// 参与的人数
			if (auction.getCount() == 0 || auction.getAuctionStatus() == 0) {
				tv_users.setVisibility(View.GONE);
				tv_person_count.setText("("+ auction.getCount() + ")人");
				if(auction.getCount() > 0){
					tv_users.setVisibility(View.VISIBLE);
				}
				
			} else {
				cimg_bidding_ptwo.setVisibility(View.GONE);
				cimg_last.setVisibility(View.GONE);
				tv_person_count.setVisibility(View.GONE);
				tv_partting.setText("竞拍成功：");
				tv_users.setVisibility(View.VISIBLE);
				bidEndUsers.setVisibility(View.VISIBLE);
				bidEndUsers.setText("("+auction.getCount()+")");
				cimg_bidding_pone.setVisibility(View.VISIBLE);
				imageLoader.displayImage(auction.getHeadUrl(), cimg_bidding_pone, options);
				tv_dot.setVisibility(View.VISIBLE);
				tv_dot.setText(auction.getNickName()+"");
			}
			
			int[] times = new int[4];
			times = JudgeAcctivityStateUtil
					.startTimeFormat(endTime);
			tivm_time.setTimes(times);
			tv_bidding_time.setText("结束时间：");
			//底部状态
			ll_bidding_bottom_one.setVisibility(View.VISIBLE);
			ll_bidding_bottom_one.setEnabled(false);
			ll_bidding_bottom_two.setVisibility(View.GONE);
			bid_state.setText("已结束");
			bid_state.setTextColor(Color.parseColor("#FC9292"));
			state_second.setText("竞拍成功价："+StringUtils.intMoney(auction.getBiddingPrice())+"元/㎡");
			time_hm_downtime.setVisibility(View.GONE);
		}
		minPrice = auction.getMinPrice();
		//浏览次数
		biddingGlance.setText("浏览"+result.getPvCount()+"次");
		//活动名称
		biddingTitle.setText(auction.getActivityName());
		biddingStartPrice.setText(auction.getMinPrice()+"元/㎡");
		//房源信息
		imageLoader.displayImage(auction.getHouseTypeImg(), houseMaster, options);
		houseKind.setText(auction.getHouseKind());
		houseType.setText(auction.getHouseType());
		houseSize.setText(auction.getSize()+"㎡");
		houseFloor.setText(auction.getFloor());
		housePosition.setText(auction.getArea()+auction.getBuildingNum());
		housePrice.setText("市场单价："+StringUtils.intMoney(auction.getReferPrice())+"元/㎡");
		try {
			double priceNet = Double.parseDouble(auction.getPrice());
			houseTotal.setText("市场总价："+StringUtils.intMoney(priceNet/10000+"")+"万");
		} catch (Exception e) {
			showTost("市场总价数据错误");
		}
		//竞拍规则
		bidPayMoney.setText(StringUtils.intMoney(auction.getDeposit())+"元");
		if(UserInfoData.isSingIn()){
			bidTime.setText(auction.getCounts()+"次");
		}else{
			bidTime.setText("3次");
		}
		bidStartTime.setText(DateUtils.getTime("yyyy/MM/dd HH:mm", startTime));
		bidEndTime.setText(DateUtils.getTime("yyyy/MM/dd HH:mm", endTime));
		//活动提供者
		imageLoader.displayImage(building.getMaster(), bidHostmaster, options);	
		bidHostname.setText(building.getName());
		bidHostcompany.setText(building.getAddress());
		lat = building.getLatitude();
		lon = building.getLongitude();
		
	}

	private void showSharePop() {
		if(null == sharePop){
			sharePop = new ShareToMoneyPopWindow(this, findViewById(R.id.ll_biding));
		}
		popImg = sharePop.getPopImg();
		popImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!UserInfoData.isSingIn()){
					ShareNoLoginDialog.showDialog(BiddingActivity.this);
				}else{
					configPlatforms();
					showBottomPopuwindow();
				}
				if(null != popImg){
					popImg.setAlpha(50);
				}				
			}
		});
	}
	private int lastY = 0; 
	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) { 
		if(null != popImg){
			if(lastY == scrollView.getScrollY()){
				popImg.setAlpha(255);
			}else{
				lastY = scrollView.getScrollY();
				popImg.setAlpha(50);
			}
			scrollView.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_UP ){
						popImg.setAlpha(255);
					}
					return false;
				}
			});
		}
	}

	private void initData() {
		Intent intent = getIntent();
		activityPoolId = intent.getStringExtra("activityPoolId");
		buildingId = intent.getStringExtra("buildingId");
		activityId = intent.getStringExtra("activityId");
	}

	/**
	 * 
	 * @Title: getBiddingInfoData
	 * @Description: 请求竞拍详细信息
	 * @return: void
	 */
	private void getBiddingInfoData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityPoolId", activityPoolId);
		map.put("buildingId", buildingId);
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		SimpleRequest<BiddingInfoBean> request = new SimpleRequest<BiddingInfoBean>(
				API.QUERY_AUCTION_ACTIVITY, BiddingInfoBean.class,
				new Listener<BiddingInfoBean>() {
					@Override
					public void onResponse(BiddingInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								Result result = response.getResult();
								viewForData(result);
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
		UserApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * 参加活动人数
	 * 
	 * @Title: setActivityCount
	 * @Description: TODO
	 * @return: void
	 */
	private void setActivityCount() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityId", activityId);
		map.put("page", "1");

		SimpleRequest<DaySpecialPartUserListBean> request = new SimpleRequest<DaySpecialPartUserListBean>(
				API.QUERY_AUCTION_PARTICIPANT,
				DaySpecialPartUserListBean.class,
				new Listener<DaySpecialPartUserListBean>() {
					@Override
					public void onResponse(DaySpecialPartUserListBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								com.flzc.fanglian.model.DaySpecialPartUserListBean.Result result = response
										.getResult();
								List<UserList> list = new ArrayList<DaySpecialPartUserListBean.Result.UserList>();
								list.addAll(result.getList());
								int count = list.size();
								switch (count) {
								case 0:
									cimg_bidding_pone.setVisibility(View.GONE);
									cimg_bidding_ptwo.setVisibility(View.GONE);
									cimg_last.setVisibility(View.GONE);
									tv_dot.setVisibility(View.GONE);
									tv_users.setVisibility(View.GONE);
									break;
								case 1:
									imageLoader.displayImage(list.get(0)
											.getHeadUrl(), cimg_bidding_pone,
											options);
									cimg_bidding_pone
											.setVisibility(View.VISIBLE);
									cimg_bidding_ptwo.setVisibility(View.GONE);
									cimg_last.setVisibility(View.GONE);
									tv_dot.setVisibility(View.GONE);
									break;
								case 2:
									imageLoader.displayImage(list.get(0)
											.getHeadUrl(), cimg_bidding_pone,
											options);
									imageLoader.displayImage(list.get(1)
											.getHeadUrl(), cimg_bidding_ptwo,
											options);
									cimg_bidding_pone
											.setVisibility(View.VISIBLE);
									cimg_bidding_ptwo
											.setVisibility(View.VISIBLE);
									cimg_last.setVisibility(View.GONE);
									tv_dot.setVisibility(View.GONE);
									break;
								case 3:
									imageLoader.displayImage(list.get(0)
											.getHeadUrl(), cimg_bidding_pone,
											options);
									imageLoader.displayImage(list.get(1)
											.getHeadUrl(), cimg_bidding_ptwo,
											options);
									imageLoader.displayImage(list.get(2)
											.getHeadUrl(), cimg_last, options);
									cimg_bidding_pone
											.setVisibility(View.VISIBLE);
									cimg_bidding_ptwo
											.setVisibility(View.VISIBLE);
									cimg_last.setVisibility(View.VISIBLE);
									tv_dot.setVisibility(View.GONE);
									break;
								default:
									imageLoader.displayImage(list.get(0)
											.getHeadUrl(), cimg_bidding_pone,
											options);
									imageLoader.displayImage(list.get(1)
											.getHeadUrl(), cimg_bidding_ptwo,
											options);
									imageLoader.displayImage(list.get(2)
											.getHeadUrl(), cimg_last, options);
									cimg_bidding_pone
											.setVisibility(View.VISIBLE);
									cimg_bidding_ptwo
											.setVisibility(View.VISIBLE);
									cimg_last.setVisibility(View.VISIBLE);
									tv_dot.setVisibility(View.VISIBLE);
									break;
								}
							} else {
								showTost(response.getMsg());
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.tv_right:
			// 分享
			if(shareState == 1){
				if(!UserInfoData.isSingIn()){
					ShareNoLoginDialog.showDialog(this);
				}else{
					showBottomPopuwindow();
					configPlatforms();
				}
			}else{
				showBottomPopuwindow();
				configPlatforms();
			}
			break;
		case R.id.tv_users:
			// 参与用户
			Intent partUserIntent = new Intent(this,
					BiddingUserListActivity.class);
			partUserIntent.putExtra("activityId", activityId);
			startActivity(partUserIntent);
			break;
		case R.id.ll_houseinfo:
			// 跳转房源页
			Intent houseInfoIntent = new Intent(BiddingActivity.this,
					BiddingHouseInfoActivity.class);
			houseInfoIntent.putExtra("userStatus", userStatus);
			houseInfoIntent.putExtra("houseSourceId", houseSourceId);
			houseInfoIntent.putExtra("activityPoolId", activityPoolId);
			houseInfoIntent.putExtra("buildingId", buildingId);
			houseInfoIntent.putExtra("startTime", startTime);
			houseInfoIntent.putExtra("state", state);
			houseInfoIntent.putExtra("endTime", endTime);
			houseInfoIntent.putExtra("minPrice", minPrice);
			houseInfoIntent.putExtra("activityId", activityId);
			houseInfoIntent.putExtra("counts", counts);
			houseInfoIntent.putExtra("biddingPrice", minPrice);
			startActivity(houseInfoIntent);
			break;
		case R.id.tv_hostposition:
			// 活动主办方地址
			Intent mapIntent = new Intent(this,NewHouseCircumMapActivity.class);
			mapIntent.putExtra("lon", lon);
			mapIntent.putExtra("lat", lat);
			startActivity(mapIntent);
			break;	
		case R.id.tv_hostphone:
			// 活动主办方电话
			Intent tellIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + saleTell));
			startActivity(tellIntent);
			break;
		case R.id.ll_bidrule:
			// 活动规则
			Intent ruleIntent = new Intent(this,
					ActivityRulesWebViewActivity.class);
			ruleIntent.putExtra("rule", API.AUCTIONRULE);
			startActivity(ruleIntent);
			break;
		case R.id.img_consult_shop_two:
			// 售楼热线
			Intent tellIntent2 = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:" + saleTell));
			startActivity(tellIntent2);
			// 和上面的业务一样
			break;
		case R.id.tv_remind:
			if ("0".equals(userStatus)) {
				goOthers(LoginActivity.class);
				// showTost("您还未登录，请登录");
				return;
			}
			// 提醒我按钮
			if ("0".equals(remind)) {
				remindDialog();
			} else if ("1".equals(remind)) {
				cancelRemindDialog();
			}
			break;
		case R.id.ll_bidding_bottom_one:
			//我要拍
			if ("0".equals(userStatus)) {
				goOthers(LoginActivity.class);
				// showTost("您还未登录，请登录");
			} 
			if ("1".equals(userStatus)) {
				Intent agreeMentIntent = new Intent(this,BiddingAgreementActivity.class);
				agreeMentIntent.putExtra("activityPoolId", activityPoolId);
				agreeMentIntent.putExtra("buildingId", buildingId);
				agreeMentIntent.putExtra("activityId", activityId);
				startActivity(agreeMentIntent);
			}
			if ("2".equals(userStatus)||"3".equals(userStatus)) {
				Intent intent = new Intent(BiddingActivity.this,
						BiddingHouseInfoActivity.class);
				intent.putExtra("userStatus", userStatus);
				intent.putExtra("houseSourceId", houseSourceId);
				intent.putExtra("activityPoolId", activityPoolId);
				intent.putExtra("buildingId", buildingId);
				intent.putExtra("startTime", startTime);
				intent.putExtra("state", state);
				intent.putExtra("endTime", endTime);
				intent.putExtra("minPrice", minPrice);
				intent.putExtra("activityId", activityId);
				intent.putExtra("counts", counts);
				intent.putExtra("biddingPrice", minPrice);
				startActivity(intent);
			}
			break;
		case R.id.ll_enRoll:
			// 报名
			if ("0".equals(userStatus)) {
				// 未登录
				goOthers(LoginActivity.class);
				// showTost("您还未登录，请登录");
			}
			if ("1".equals(userStatus)) {
				Intent agreeMentIntent = new Intent(this,BiddingAgreementActivity.class);
				agreeMentIntent.putExtra("activityPoolId", activityPoolId);
				agreeMentIntent.putExtra("buildingId", buildingId);
				agreeMentIntent.putExtra("activityId", activityId);
				startActivity(agreeMentIntent);
			}
			if ("2".equals(userStatus)) {
				Intent intentEnroll = new Intent(BiddingActivity.this,
						BiddingHouseInfoActivity.class);
				intentEnroll.putExtra("userStatus", userStatus);
				intentEnroll.putExtra("houseSourceId", houseSourceId);
				intentEnroll.putExtra("activityPoolId", activityPoolId);
				intentEnroll.putExtra("buildingId", buildingId);
				intentEnroll.putExtra("startTime", startTime);
				intentEnroll.putExtra("state", state);
				intentEnroll.putExtra("endTime", endTime);
				intentEnroll.putExtra("minPrice", minPrice);
				intentEnroll.putExtra("activityId", activityId);
				intentEnroll.putExtra("counts", counts);
				intentEnroll.putExtra("biddingPrice", minPrice);
				startActivity(intentEnroll);
			}
			break;
		}

	}

	/**
	 * 
	 * @Title: remindDialog
	 * @Description: 提醒我的弹框
	 * @return: void
	 */
	private void remindDialog() {
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle("确认提醒");
		builder.setContent("确认提醒后，活动开始您将收到1条短信提醒。");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				remindMe();
				dialog.dismiss();
			}

		});
		builder.create().show();
	}

	/**
	 * 
	 * @Title: cancelRemindDialog
	 * @Description: 取消提醒我的弹框
	 * @return: void
	 */
	private void cancelRemindDialog() {
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle("取消提醒");
		builder.setContent("取消后您将不会收到任何消息提醒，可能会错过活动，确定取消提醒吗？");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				cancleRemind();
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/**
	 * 
	 * @Title: remindMe
	 * @Description: 提醒我的接口请求
	 * @return: void
	 */
	private void remindMe() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityPoolId", activityPoolId);
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		SimpleRequest<RemindMeBean> request = new SimpleRequest<RemindMeBean>(
				API.REMIND, RemindMeBean.class, new Listener<RemindMeBean>() {
					@Override
					public void onResponse(RemindMeBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// 表示请求提醒成功
								remind = "1";
								tv_remind.setText("已设置提醒");
								remindId = response.getResult() + "";
							} else {
								showTost(response.getMsg());
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * 
	 * @Title: cancleRemind
	 * @Description: 取消提醒接口请求
	 * @return: void
	 */
	private void cancleRemind() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("remindId", remindId);
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.CANCEL_REMIND, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// 说明请求取消提醒成功
								remind = "0";
								tv_remind.setText("提醒我");
							} else {
								showTost(response.getMsg());
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	private void showBottomPopuwindow() {
		View view = getLayoutInflater().inflate(R.layout.share_item, null);
		TextView share_title = (TextView)view.findViewById(R.id.tv_share_title);
		ImageView img_title = (ImageView)view.findViewById(R.id.img_title);
		if (shareState == 1) {
			share_title.setVisibility(View.GONE);
			img_title.setVisibility(View.VISIBLE);
		}else{
			share_title.setVisibility(View.VISIBLE);
			img_title.setVisibility(View.GONE);
			share_title.setText("分享到");
		}
		if (null == bottomWindow) {
			bottomWindow = new PopupWindow(getApplicationContext());
		}
		// 设置破普window的属性
		bottomWindow.setWidth(LayoutParams.MATCH_PARENT);
		bottomWindow.setHeight(LayoutParams.WRAP_CONTENT);
		bottomWindow.setBackgroundDrawable(new BitmapDrawable());
		bottomWindow.setFocusable(true);
		bottomWindow.setOutsideTouchable(true);
		bottomWindow.setContentView(view);
		bottomWindow.setAnimationStyle(R.style.anim_menu_bottombar);
		bottomWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		bottomWindow.showAtLocation(tv_right, Gravity.BOTTOM, 0, 0);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		getWindow().setAttributes(lp);
		bottomWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
				if(null != popImg){
					popImg.setAlpha(255);
				}	
			}
		});
		ImageView iv_share_weixin_friend = (ImageView) view
				.findViewById(R.id.iv_share_weixin_friend);
		ImageView iv_share_weixin = (ImageView) view
				.findViewById(R.id.iv_share_weixin);
		ImageView iv_share_weibo = (ImageView) view
				.findViewById(R.id.iv_share_weibo);

		LinearLayout ll_share_cancle = (LinearLayout) view
				.findViewById(R.id.ll_share_cancle);

		// 点击叉号取消
		ll_share_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bottomWindow.dismiss();
			}
		});

		// 分享到微信朋友圈
		iv_share_weixin_friend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
				bottomWindow.dismiss();
			}
		});

		// 分享到微信
		iv_share_weixin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				performShare(SHARE_MEDIA.WEIXIN);
				bottomWindow.dismiss();
			}
		});

		// 分享到新浪微博
		iv_share_weibo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				performShare(SHARE_MEDIA.SINA);
				bottomWindow.dismiss();
			}
		});

	}

	private void performShare(SHARE_MEDIA platform) {
		mController.postShare(this, platform, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					showText += "平台分享成功";
					showTost("分享成功");
					LogUtil.e("BiddingShare", buildingId + ", "
							+ activityPoolId + ", " + houseSourceId);
					SaveUserShareUtil.saveUserShare(buildingId, activityPoolId,
							houseSourceId);
				} else {
					showTost("分享失败");
					showText += "平台分享失败";
				}
			}
		});
	}

	/**
	 * 配置分享平台参数</br>
	 */
	private void configPlatforms() {
		UMImage image = new UMImage(this, buildImg);
		String title = city+"购房新体验,"+minPrice+"元起拍、三锤定价。";
		String content = "";
		if(shareState == 1){
			content = mBuildName+"稀缺房源"+minPrice+"元起拍,违约赔付,公平公正,平台全程监管,分享还能获得现金红包。";
		}else{
			content = mBuildName+"稀缺房源"+minPrice+"元起拍,违约赔付,公平公正,平台全程监管。";
		}
		mController.getConfig().closeToast();
		// mController.setShareImage(new UMImage(BiddingActivity.this,
		// "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=31037957,2334521972&fm=58"));
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId = Constant.APP_ID;
		String appSecret = Constant.APP_SECRET;
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
		wxHandler.addToSocialSDK();
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTitle(title);
		// 设置分享文字内容
		weixinContent.setShareContent(content);
		weixinContent.setShareMedia(image);
		weixinContent.setTargetUrl(shareUrl);
		mController.setShareMedia(weixinContent);
		mController.getConfig().setSsoHandler(wxHandler);

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		CircleShareContent circleShareContent = new CircleShareContent();
		circleShareContent.setTitle(title);
		circleShareContent.setShareContent(content);
		circleShareContent.setShareMedia(image);
		circleShareContent.setTargetUrl(shareUrl);
		mController.setShareMedia(circleShareContent);
		mController.getConfig().setSsoHandler(wxCircleHandler);

		// 新浪
		SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
		sinaSsoHandler.setShareAfterAuthorize(true);
		sinaSsoHandler.addToSocialSDK();
		SinaShareContent sinaShareContent = new SinaShareContent();
		sinaShareContent.setTitle(title);
		sinaShareContent.setShareContent(content+ shareUrl+"[房链分享]");
		sinaShareContent.setShareMedia(image);
		sinaShareContent.setTargetUrl(shareUrl);
		mController.setShareMedia(sinaShareContent);
		mController.getConfig().setSsoHandler(sinaSsoHandler);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	public void onDestroy() {
		if(null != sharePop){
			sharePop.dismissPop();
		}
		super.onDestroy();
		mController.getConfig().cleanListeners();
	}

	@Override
	protected void onPause() {
		tivm_time.stop();
		time_hm_downtime.stop();
		super.onPause();
	}

	@Override
	protected void onStop() {
		tivm_time.stop();
		time_hm_downtime.stop();
		super.onStop();
	}

}
