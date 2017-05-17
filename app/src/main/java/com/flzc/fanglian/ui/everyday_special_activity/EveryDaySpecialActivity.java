package com.flzc.fanglian.ui.everyday_special_activity;

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
import com.flzc.fanglian.model.DaySpecialActivityBean;
import com.flzc.fanglian.model.DaySpecialActivityBean.Result;
import com.flzc.fanglian.model.DaySpecialActivityBean.Result.Building;
import com.flzc.fanglian.model.DaySpecialActivityBean.Result.Building.Tags;
import com.flzc.fanglian.model.DaySpecialActivityBean.Result.DaySpecial;
import com.flzc.fanglian.model.DaySpecialActivityBean.Result.Share;
import com.flzc.fanglian.model.DaySpecialPartUserListBean;
import com.flzc.fanglian.model.DaySpecialPartUserListBean.Result.UserList;
import com.flzc.fanglian.ui.ActivityRulesWebViewActivity;
import com.flzc.fanglian.ui.AllAgentListActivity;
import com.flzc.fanglian.ui.bidding_activity.BiddingActivity;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.ui.newhouse.NewHouseCircumMapActivity;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.JudgeAcctivityStateUtil;
import com.flzc.fanglian.util.LogUtil;
import com.flzc.fanglian.util.SaveUserShareUtil;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.CircleImageView;
import com.flzc.fanglian.view.ObservableScrollView;
import com.flzc.fanglian.view.TimeView;
import com.flzc.fanglian.view.TimeViewWhiteWordHM;
import com.flzc.fanglian.view.ObservableScrollView.ScrollViewListener;
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
 * @ClassName: EveryDaySpecialActivity
 * @Description: 天天特价首页
 * @author: LU
 * @date: 2016-3-7 下午7:59:42
 */
public class EveryDaySpecialActivity extends BaseActivity implements
		OnClickListener  ,ScrollViewListener{
	private RelativeLayout rl_back;
	private TextView tv_title;
	private TextView tv_right;
	private TextView tv_users;
	private ImageView buildMaster;
	private TextView userCount;
	private CircleImageView cimgOne;
	private CircleImageView cimgtwo;
	private CircleImageView cimglast;
	private TimeView timeView;
	private String activityPoolId;
	private String buildingId;
	private TextView timeState;
	private String activityId;
	private ArrayList<Tags> tags;
	private TextView tv_partting;
	private TextView tv_dot;
	private int state;
	private LinearLayout ll_bidding_bottom_one;
	private LinearLayout ll_bidding_bottom_two;
	private ImageView img_consult_shop_two;
	private TextView tv_remind;
	private TextView tv_enroll;
	// 取消提醒时候所用的参数
	private String remindId;
	private String tokenId;
	private String remind;// 提醒我的状态 0没有提醒 1有提醒
	private int userStatus;
	private int houseSourceId;
	private long startTime;
	private long endTime;
	private String marketPrice;
	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constant.DESCRIPTOR);
	private String shareUrl;
	private String saleTell;
	private String activityPrice;
	private String buildImg;
	private String sactivityName;
	private String mBuildName;
	private PopupWindow bottomWindow;
	private int shareState;
	private TextView specEndUsers;
	private TextView glanceCount;
	private ImageView activityTypeImg;
	private TextView activityTitle;
	private TextView activityPriceView;
	private LinearLayout houseinfo;
	private ImageView houseMaster;
	private TextView houseKind;
	private TextView houseType;
	private TextView houseSize;
	private TextView houseFloor;
	private TextView housePosition;
	private TextView housePrice;
	private TextView houseTotal;
	private TextView specialPayMoney;
	private TextView specialStartTime;
	private TextView specialEndTime;
	private ImageView specialHostmaster;
	private TextView specialHostname;
	private TextView specialHostcompany;
	private TextView specialHostposition;
	private TextView specialHostphone;
	private LinearLayout specialRule;
	private String lat;
	private String lon;
	private TextView enrollMoney;
	private TextView rush_state;
	private LinearLayout ll_time_state;
	private TimeViewWhiteWordHM time_hm_downtime;
	private LinearLayout bg_downTime;
	private ShareToMoneyPopWindow sharePop;
	private ImageView popImg;
	private LinearLayout ll_enroll;
	private String city;
	public static EveryDaySpecialActivity everyDaySpecialActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_everyday_special);
		everyDaySpecialActivity = this;
		initView();
		activityPoolId = getIntent().getStringExtra("activityPoolId");
		buildingId = getIntent().getStringExtra("buildingId");
		activityId = getIntent().getStringExtra("activityId");
		initListener();
		
	}
	
	@Override
	protected void onResume() {
		tokenId = UserInfoData.getData(Constant.TOKEN, "");
		initData();
		initPartUserData();
		super.onResume();
	}

	private void initView() {
		// 标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("天天特价");
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setText("");
		tv_right.setCompoundDrawablesWithIntrinsicBounds(
				R.drawable.share, 0, 0, 0);
		tv_right.setCompoundDrawablePadding(0);
		//scrollView
		ObservableScrollView sv_bidding = (ObservableScrollView)findViewById(R.id.sv_special);
		sv_bidding.setScrollViewListener(this);	
		// 楼盘主图
		buildMaster = (ImageView) findViewById(R.id.img_preview);
		// 开始时间和倒计时
		bg_downTime = (LinearLayout)findViewById(R.id.ll_bg_downtime);
		timeState = (TextView) findViewById(R.id.tv_bidding_time);
		timeView = (TimeView) findViewById(R.id.tivm_time);
		//浏览次数
		glanceCount = (TextView) findViewById(R.id.tv_activity_glance); 
		//活动图
		activityTypeImg = (ImageView) findViewById(R.id.img_activity_type);
		activityTypeImg.setImageResource(R.drawable.big_qiang);
		activityTitle = (TextView) findViewById(R.id.tv_activity_title);
		activityPriceView = (TextView) findViewById(R.id.tv_activity_price);
		activityPriceView.setCompoundDrawablesWithIntrinsicBounds(
				R.drawable.activity_special, 0, 0, 0);
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
		//支付方式
		TextView paywayContent = (TextView)findViewById(R.id.tv_payway_content);
		paywayContent.setText("抢购成功后，您将获得活动价的线下购房资格，实际购房交易在开发商售楼处完成。");
		//天天特价规则
		specialPayMoney = (TextView)findViewById(R.id.tv_specialpaymoney);
		specialStartTime =  (TextView)findViewById(R.id.tv_specialstarttime);
		specialEndTime = (TextView)findViewById(R.id.tv_specialendtime);
		// 用户列表
		tv_partting = (TextView) findViewById(R.id.tv_partting);
		tv_users = (TextView) findViewById(R.id.tv_users);
		userCount = (TextView) findViewById(R.id.tv_person_count);
		specEndUsers = (TextView) findViewById(R.id.tv_bidendusers);
		cimgOne = (CircleImageView) findViewById(R.id.cimg_bidding_pone);
		cimgtwo = (CircleImageView) findViewById(R.id.cimg_bidding_ptwo);
		cimglast = (CircleImageView) findViewById(R.id.cimg_last);
		tv_dot = (TextView) findViewById(R.id.tv_dot);
		//活动提供方
		specialHostmaster = (ImageView)findViewById(R.id.img_hostmaster);
		specialHostname = (TextView)findViewById(R.id.tv_hostname);
		specialHostcompany = (TextView)findViewById(R.id.tv_hostcompany);
		specialHostposition = (TextView)findViewById(R.id.tv_hostposition);
		specialHostphone = (TextView)findViewById(R.id.tv_hostphone);
		//特价须知
		specialRule = (LinearLayout)findViewById(R.id.ll_specialrule);
		// 领取优惠券
		ll_bidding_bottom_one = (LinearLayout) findViewById(R.id.ll_dayspecial_bottom_one);
		ll_time_state = (LinearLayout) findViewById(R.id.ll_time_state);
		rush_state = (TextView) findViewById(R.id.tv_rush_state);
		time_hm_downtime = (TimeViewWhiteWordHM) findViewById(R.id.time_hm_downtime);
		
		ll_bidding_bottom_two = (LinearLayout) findViewById(R.id.ll_dayspecial_bottom_two);
		img_consult_shop_two = (ImageView) findViewById(R.id.img_consult_shop_two);
		tv_remind = (TextView) findViewById(R.id.tv_remind);
		ll_enroll = (LinearLayout) findViewById(R.id.ll_enroll);
		tv_enroll = (TextView) findViewById(R.id.tv_enroll);
		enrollMoney = (TextView) findViewById(R.id.tv_enroll_money);

	}
	
	private void initListener() {
		rl_back.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		houseinfo.setOnClickListener(this);
		tv_users.setOnClickListener(this);
		specialHostposition.setOnClickListener(this);
		specialHostphone.setOnClickListener(this);
		specialRule.setOnClickListener(this);
		ll_bidding_bottom_one.setOnClickListener(this);
		img_consult_shop_two.setOnClickListener(this);
		tv_remind.setOnClickListener(this);
		ll_enroll.setOnClickListener(this);

	}


	/**
	 * 
	 * @Title: viewAssignment
	 * @Description: 控件赋值
	 * @return: void
	 */
	protected void viewAssignment(Result daySpecialResult) {
		Building build = daySpecialResult.getBuilding();
		mBuildName = build.getName();
		DaySpecial daySpecial = daySpecialResult.getDaySpecial();
		city = daySpecial.getCity();
		sactivityName = daySpecial.getActivityName();
		remind = build.getRemaind();
		remindId = build.getRemaindId();
		saleTell = build.getSaleTel();
		houseSourceId = daySpecial.getHouseSourceId();
		//参与人数
		int userparts = daySpecial.getCount();
		Share share = daySpecialResult.getShare();
		shareState = share.getStatus();
		if(shareState == 1){
			showSharePop();
		}
		shareUrl = share.getShareUrl();
		//主图
		buildImg = build.getMaster();
		imageLoader.displayImage(build.getMaster(), buildMaster, options);
		// 倒计时
		startTime = daySpecial.getStartTime();
		endTime = daySpecial.getEndTime();
		long currentTime = DateUtils.currentTime();
		LogUtil.e("endTime", JudgeAcctivityStateUtil.getTime("yyyy-MM-dd HH:mm:ss", currentTime));
		state = JudgeAcctivityStateUtil.getState(startTime, endTime);
		// 用户状态
		userStatus = daySpecial.getUserStatus();
		// 0为未登录，1为未报名、2为报名
		switch (state) {
		case 0:
			// 即将开始
			bg_downTime.setBackgroundColor(Color.parseColor("#EE4B4C"));
			//参与人数
			if(userparts == 0){
				tv_users.setVisibility(View.GONE);
			}else{
				tv_users.setVisibility(View.VISIBLE);
			}
			userCount.setText("(" + userparts + ")人");
			timeState.setText("开始时间：");
			timeView.setTimes(JudgeAcctivityStateUtil
					.startTimeFormat(startTime));
			tv_partting.setText("当前报名人数：");
			//底部按钮
			ll_bidding_bottom_one.setVisibility(View.GONE);
			ll_bidding_bottom_two.setVisibility(View.VISIBLE);
			if (remind.equals("0")) {
				tv_remind.setText("提醒我");
			}
			if (remind.equals("1")) {
				tv_remind.setText("已设置提醒");
			}
			if (userStatus == 1 ||userStatus == 0) {
				tv_enroll.setText("去报名");
				enrollMoney.setText("保证金："+StringUtils.intMoney(daySpecial.getDeposit())+"元");
			}
			if (userStatus == 2) {
				tv_enroll.setText("已报名");
				enrollMoney.setVisibility(View.GONE);
			}
			break;
		case 1:
			// 进行中
			bg_downTime.setBackgroundColor(Color.parseColor("#EE4B4C"));
			//参与人数
			if(userparts == 0){
				tv_users.setVisibility(View.GONE);
			}else{
				tv_users.setVisibility(View.VISIBLE);
			}
			userCount.setText("(" + userparts + ")人");
			timeState.setText("距结束剩：");
			timeView.setDownTimes(JudgeAcctivityStateUtil
					.downTimeFormat(endTime - currentTime));
			timeView.run();
			tv_partting.setText("当前参加活动人数：");
			//底部按钮
			ll_bidding_bottom_one.setVisibility(View.VISIBLE);
			ll_bidding_bottom_two.setVisibility(View.GONE);
			if (userStatus == 0||userStatus == 1) {
				rush_state.setText("去报名");
			}else{
				rush_state.setText("立即抢购");
			}
			time_hm_downtime.setDownTimes(JudgeAcctivityStateUtil
					.downTimeFormat(endTime - currentTime));
			time_hm_downtime.run();
			break;
		case 2:
			// 已结束
			bg_downTime.setBackgroundColor(Color.parseColor("#aa000000"));
			//参与人数
			if(userparts == 0 || daySpecial.getDaySpecialStatus() == 0){
				tv_users.setVisibility(View.GONE);
				userCount.setText("(" + userparts + ")人");
				if(userparts > 0){
					tv_users.setVisibility(View.VISIBLE);
				}
			}else{
				cimgtwo.setVisibility(View.GONE);
				cimglast.setVisibility(View.GONE);
				userCount.setVisibility(View.GONE);
				tv_users.setVisibility(View.VISIBLE);
				tv_partting.setText("抢购成功：");
				cimgOne.setVisibility(View.VISIBLE);
				imageLoader.displayImage(daySpecial.getHeadUrl(), cimgOne, options);
				tv_dot.setVisibility(View.VISIBLE);
				tv_dot.setText(daySpecial.getNickName());
				specEndUsers.setText("("+userparts+")");
				specEndUsers.setVisibility(View.VISIBLE);
			}
			
			timeState.setText("结束时间：");
			timeView.setTimes(JudgeAcctivityStateUtil.startTimeFormat(endTime));
			//底部按钮
			ll_bidding_bottom_one.setVisibility(View.VISIBLE);
			ll_bidding_bottom_one.setEnabled(false);
			ll_bidding_bottom_two.setVisibility(View.GONE);
			rush_state.setText("已结束");
			rush_state.setTextColor(Color.parseColor("#FC9292"));
			ll_time_state.setVisibility(View.GONE);
			break;
		}
		activityPrice = StringUtils.intMoney(daySpecial.getActivityPrice());
		//浏览人数
		glanceCount.setText("浏览"+daySpecialResult.getPvCount()+"次");
		//活动分类
		activityTitle.setText(daySpecial.getActivityName());
		try {
			double miaoShaPrice = Double.parseDouble(daySpecial.getActivityPrice());
			activityPriceView.setText(StringUtils.intMoney(miaoShaPrice/10000+"")+"万元/套");
		} catch (Exception e) {
			showTost("秒杀价数据错误");
		}
		//房源信息
		imageLoader.displayImage(daySpecial.getHouseTypeImg(), houseMaster, options);
		houseKind.setText(daySpecial.getHouseKind());
		houseType.setText(daySpecial.getHouseType());
		houseSize .setText(daySpecial.getSize()+"㎡");
		houseFloor .setText(daySpecial.getFloor());
		housePosition .setText(daySpecial.getArea()+daySpecial.getBuildingNum());
		housePrice .setText("市场单价："+StringUtils.intMoney(daySpecial.getReferPrice())+"元/㎡");
		try {
			double total = Double.parseDouble(daySpecial.getPrice());
			houseTotal .setText("市场总价："+StringUtils.intMoney(total/10000+"")+"万");
		} catch (Exception e) {
			showTost("市场总价数据错误");
		}
		//天天特价规则
		specialPayMoney.setText(StringUtils.intMoney(daySpecial.getDeposit())+"元");
		specialStartTime.setText(DateUtils.getTime("yyyy/MM/dd HH:mm", startTime));
		specialEndTime.setText(DateUtils.getTime("yyyy/MM/dd HH:mm", endTime));
		//活动提供方
		imageLoader.displayImage(build.getMaster(), specialHostmaster, options);	
		specialHostname.setText(build.getName());
		specialHostcompany.setText(build.getAddress());
		lat = build.getLatitude();
		lon = build.getLongitude();
		
	}
	
	private void showSharePop() {
		if(null == sharePop){
			sharePop = new ShareToMoneyPopWindow(this, findViewById(R.id.ll_special));
		}
		popImg = sharePop.getPopImg();
		popImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!UserInfoData.isSingIn()){
					ShareNoLoginDialog.showDialog(EveryDaySpecialActivity.this);
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
	
	/**
	 * 
	 * @Title: initData
	 * @Description: 查询天天特价参与用户
	 * @return: void
	 */
	private void initPartUserData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityId", activityId);
		map.put("page", "1");
		SimpleRequest<DaySpecialPartUserListBean> req = new SimpleRequest<DaySpecialPartUserListBean>(
				API.QUERY_DAY_SPECIAL_PARTICIPANT,
				DaySpecialPartUserListBean.class,
				new Listener<DaySpecialPartUserListBean>() {
					@Override
					public void onResponse(DaySpecialPartUserListBean response) {
						if (null != response.getResult()) {
							List<UserList> userList = new ArrayList<UserList>();
							userList.addAll(response.getResult().getList());
							switch (userList.size()) {
							case 0:
								cimgOne.setVisibility(View.GONE);
								cimgtwo.setVisibility(View.GONE);
								cimglast.setVisibility(View.GONE);
								tv_dot.setVisibility(View.GONE);
								break;
							case 1:
								imageLoader.displayImage(userList.get(0)
										.getHeadUrl(), cimgOne, options);
								cimgOne.setVisibility(View.VISIBLE);
								cimgtwo.setVisibility(View.GONE);
								cimglast.setVisibility(View.GONE);
								tv_dot.setVisibility(View.GONE);
								userCount.setVisibility(View.VISIBLE);
								break;
							case 2:
								imageLoader.displayImage(userList.get(0)
										.getHeadUrl(), cimgOne, options);
								imageLoader.displayImage(userList.get(1)
										.getHeadUrl(), cimgtwo, options);
								cimgOne.setVisibility(View.VISIBLE);
								cimgtwo.setVisibility(View.VISIBLE);
								cimglast.setVisibility(View.GONE);
								tv_dot.setVisibility(View.GONE);
								userCount.setVisibility(View.VISIBLE);
								break;
							case 3:
								imageLoader.displayImage(userList.get(0)
										.getHeadUrl(), cimgOne, options);
								imageLoader.displayImage(userList.get(1)
										.getHeadUrl(), cimgtwo, options);
								imageLoader.displayImage(userList.get(2)
										.getHeadUrl(), cimglast, options);
								cimgOne.setVisibility(View.VISIBLE);
								cimgtwo.setVisibility(View.VISIBLE);
								cimglast.setVisibility(View.VISIBLE);
								tv_dot.setVisibility(View.GONE);
								userCount.setVisibility(View.VISIBLE);
								break;
							default:
								imageLoader.displayImage(userList.get(0)
										.getHeadUrl(), cimgOne, options);
								imageLoader.displayImage(userList.get(1)
										.getHeadUrl(), cimgtwo, options);
								imageLoader.displayImage(userList.get(2)
										.getHeadUrl(), cimglast, options);
								cimgOne.setVisibility(View.VISIBLE);
								cimgtwo.setVisibility(View.VISIBLE);
								cimglast.setVisibility(View.VISIBLE);
								tv_dot.setVisibility(View.VISIBLE);
								break;
							}
							
						}

					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}
	
	/**
	 * 
	 * @Title: initData
	 * @Description: 请求天天特价详细信息数据
	 * @return: void
	 */
	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("activityPoolId", activityPoolId);
		map.put("tokenId", tokenId);
		SimpleRequest<DaySpecialActivityBean> req = new SimpleRequest<DaySpecialActivityBean>(
				API.QUERY_DAY_SPECIAL_ACTIVITY, DaySpecialActivityBean.class,
				new Listener<DaySpecialActivityBean>() {
					@Override
					public void onResponse(DaySpecialActivityBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								Result daySpecialResult = response.getResult();
								viewAssignment(daySpecialResult);
							}else{
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						
					}
				} ,map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.tv_right:
			//分享
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
		case R.id.ll_houseinfo:
			// 跳到房源页
			Intent houseInfoIntent = new Intent(this,
					SpecialHouseInfoActivity.class);
			houseInfoIntent.putExtra("userStatus", userStatus + "");
			houseInfoIntent.putExtra("houseSourceId", houseSourceId + "");
			houseInfoIntent.putExtra("activityPoolId", activityPoolId + "");
			houseInfoIntent.putExtra("buildingId", buildingId + "");
			houseInfoIntent.putExtra("marketPrice", marketPrice);
			houseInfoIntent.putExtra("activityPrice", activityPrice+"");
			houseInfoIntent.putExtra("activityId", activityId);
			houseInfoIntent.putExtra("startTime", startTime);
			houseInfoIntent.putExtra("endTime", endTime);
			startActivity(houseInfoIntent);
			break;	
		case R.id.tv_users:
			// 参与用户列表
			Intent partUserIntent = new Intent(this,
					DaySpecialUserListActivity.class);
			partUserIntent.putExtra("activityId", activityId);
			startActivity(partUserIntent);
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
		case R.id.ll_specialrule:
			//活动规则
			Intent ruleIntent = new Intent(this,ActivityRulesWebViewActivity.class);
			ruleIntent.putExtra("rule", API.SECONDKILLRULE);
			startActivity(ruleIntent);
			break;
		case R.id.tv_look_allcounselor:
			// 查看置业顾问
			Intent conselorIntent = new Intent(this, AllAgentListActivity.class);
			conselorIntent.putExtra("buildingId", buildingId);
			startActivity(conselorIntent);
			break;
		case R.id.img_consult_shop_two:
			// 和上面的业务一样
			Intent tellIntent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+saleTell));
			startActivity(tellIntent2);
			break;
		case R.id.tv_remind:
			if(userStatus == 0){
				goOthers(LoginActivity.class);
				//showTost("您还未登录，请登录");
				return;
			}
			// 提醒我按钮
			if (remind.equals("0")) {
				remindDialog();
			} else if (remind.equals("1")) {
				cancelRemindDialog();
			}
			break;
		case R.id.ll_enroll:
			if(userStatus == 0){
				goOthers(LoginActivity.class);
				//showTost("您还未登录，请登录");
				return;
			}
			if(userStatus == 1){
				Intent agreementIntent = new Intent(this,
						SpecialAgreeMentWebViewActivity.class);
				agreementIntent.putExtra("houseSourceId", houseSourceId + "");
				agreementIntent.putExtra("buildingId", buildingId + "");
				agreementIntent.putExtra("activityPoolId", activityPoolId + "");
				agreementIntent.putExtra("activityId", activityId);
				startActivity(agreementIntent);
			}
			if(userStatus == 2){
				// 报名
				Intent enRollIntent = new Intent(this,
						SpecialHouseInfoActivity.class);
				enRollIntent.putExtra("userStatus", userStatus + "");
				enRollIntent.putExtra("houseSourceId", houseSourceId + "");
				enRollIntent.putExtra("activityPoolId", activityPoolId + "");
				enRollIntent.putExtra("buildingId", buildingId + "");
				enRollIntent.putExtra("marketPrice", marketPrice);
				enRollIntent.putExtra("activityPrice", activityPrice+"");
				enRollIntent.putExtra("activityId", activityId);
				enRollIntent.putExtra("startTime", startTime);
				enRollIntent.putExtra("endTime", endTime);
				startActivity(enRollIntent);
			}
			break;
		case R.id.ll_dayspecial_bottom_one:
			//抢购
			if(userStatus == 0){
				goOthers(LoginActivity.class);
				//showTost("您还未登录，请登录");
				return;
			}
			if(userStatus == 1){
				Intent agreementIntent = new Intent(this,
						SpecialAgreeMentWebViewActivity.class);
				agreementIntent.putExtra("houseSourceId", houseSourceId + "");
				agreementIntent.putExtra("buildingId", buildingId + "");
				agreementIntent.putExtra("activityPoolId", activityPoolId + "");
				agreementIntent.putExtra("activityId", activityId);
				startActivity(agreementIntent);
			}
			if(userStatus == 2){
				// 我要秒
				Intent robIntent = new Intent(this, SpecialHouseInfoActivity.class);
				robIntent.putExtra("userStatus", userStatus + "");
				robIntent.putExtra("houseSourceId", houseSourceId + "");
				robIntent.putExtra("activityPoolId", activityPoolId + "");
				robIntent.putExtra("buildingId", buildingId + "");
				robIntent.putExtra("marketPrice", marketPrice);
				robIntent.putExtra("activityPrice", activityPrice+"");
				robIntent.putExtra("activityId", activityId);
				robIntent.putExtra("startTime", startTime);
				robIntent.putExtra("endTime", endTime);
				startActivity(robIntent);
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
	private void remindDialog(){
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle("确认提醒");
		builder.setContent("确认提醒后，活动开始您将收到1条短信提醒。");
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();

					}
				});
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
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
	private void cancelRemindDialog(){
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle("取消提醒");
		builder.setContent("取消后您将不会收到任何消息提醒，可能会错过活动，确定取消提醒吗？");
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();

					}
				});
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
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
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityPoolId", activityPoolId);
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.REMIND, BaseInfoBean.class, new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// 表示请求提醒成功
								remind = "1";
								tv_remind.setText("已设置提醒");
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
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
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("remindId", remindId + "");
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
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
					}
				} ,map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * 
	 * @Title: showBottomPopuwindow 
	 * @Description: 分享弹框
	 * @return: void
	 */
	private void showBottomPopuwindow(){
		View view = getLayoutInflater().inflate(R.layout.share_item,null);
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
		if(null == bottomWindow){
			bottomWindow = new PopupWindow(getApplicationContext());
		}
		//设置破普window的属性
		bottomWindow.setWidth(LayoutParams.MATCH_PARENT);
		bottomWindow.setHeight(LayoutParams.WRAP_CONTENT);
		bottomWindow.setBackgroundDrawable(new BitmapDrawable());
		bottomWindow.setFocusable(true);
		bottomWindow.setOutsideTouchable(true);
		bottomWindow.setContentView(view);
		bottomWindow.setAnimationStyle(R.style.anim_menu_bottombar);
		bottomWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
		
		ImageView iv_share_weixin_friend = (ImageView) view.findViewById(R.id.iv_share_weixin_friend);
		ImageView iv_share_weixin = (ImageView) view.findViewById(R.id.iv_share_weixin);
		ImageView iv_share_weibo = (ImageView) view.findViewById(R.id.iv_share_weibo);
		
 		LinearLayout ll_share_cancle = (LinearLayout) view.findViewById(R.id.ll_share_cancle);
 		
 		//点击叉号取消
 		ll_share_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bottomWindow.dismiss();
			}
		});
 		
 		//分享到微信朋友圈
 		iv_share_weixin_friend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
				bottomWindow.dismiss();
			}
		});

 		//分享到微信
 		iv_share_weixin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				performShare(SHARE_MEDIA.WEIXIN);
				bottomWindow.dismiss();
			}
		});
 		
 		//分享到新浪微博
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
					showTost("分享成功");
					showText += "平台分享成功";
					SaveUserShareUtil.saveUserShare(buildingId, activityPoolId, houseSourceId+"");
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
    	String title = city+"购房人新体验,每天一套特价房。";
    	String content = "";
    	if(shareState == 1){
			content = mBuildName+"稀缺房源限时抢购、违约赔付,公平公正,平台全程监管,分享还能获得现金红包。";
		}else{
			content = mBuildName+"稀缺房源限时抢购、违约赔付,公平公正,平台全程监管。";
		}
    	mController.getConfig().closeToast();
//		mController.setShareImage(new UMImage(BiddingActivity.this, "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=31037957,2334521972&fm=58"));
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
		
		//新浪
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
		timeView.stop();
		time_hm_downtime.stop();
		super.onPause();
	}
	@Override
	protected void onStop() {
		timeView.stop();
		time_hm_downtime.stop();
		super.onStop();
	}
}
