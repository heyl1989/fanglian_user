package com.flzc.fanglian.ui.agent_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.flzc.fanglian.model.AgentRecommendUserListBean;
import com.flzc.fanglian.model.AgentRecommendUserListBean.Result.UserList;
import com.flzc.fanglian.model.AgentRecommendlActivityBean;
import com.flzc.fanglian.model.AgentRecommendlActivityBean.Result;
import com.flzc.fanglian.model.AgentRecommendlActivityBean.Result.Building;
import com.flzc.fanglian.model.AgentRecommendlActivityBean.Result.Building.Tags;
import com.flzc.fanglian.model.AgentRecommendlActivityBean.Result.Salesway;
import com.flzc.fanglian.model.AgentRecommendlActivityBean.Result.SaleswayList;
import com.flzc.fanglian.model.AgentRecommendlActivityBean.Result.Share;
import com.flzc.fanglian.model.GoodBrokeBean;
import com.flzc.fanglian.model.GoodBrokeBean.Result.BrokeList;
import com.flzc.fanglian.ui.ActivityRulesWebViewActivity;
import com.flzc.fanglian.ui.adapter.PrivilegeHouseInfoAgentAdapter;
import com.flzc.fanglian.ui.adapter.RecommendHouseListAdapter;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.ui.newhouse.NewHouseCircumMapActivity;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.JudgeAcctivityStateUtil;
import com.flzc.fanglian.util.SaveUserShareUtil;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.CircleImageView;
import com.flzc.fanglian.view.ObservableScrollView;
import com.flzc.fanglian.view.ObservableScrollView.ScrollViewListener;
import com.flzc.fanglian.view.TimeView;
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
 * @ClassName: AgentRecommendActivity
 * @Description: 经纪人推荐活动
 * @author: LU
 * @date: 2016-3-5 下午4:22:11
 */
public class AgentRecommendActivity extends BaseActivity implements
		OnClickListener,OnItemClickListener ,ScrollViewListener{

	private RelativeLayout rl_back;
	private TextView tv_title;
	private TextView tv_right;
	private TextView tv_users;
	private String activityPoolId;
	private String buildingId;
	private String activityId;
	private String remind;
	private String remindId;
	private String tokenId;
	private TextView tv_partting;
	private TextView userCount;
	private CircleImageView cimgOne;
	private CircleImageView cimgtwo;
	private CircleImageView cimglast;
	private TextView tv_dot;
	private TextView houseTypeCount;
	private ImageView mMaster;
	private TimeView mTimeDown;
	private ImageView mImgHouseType;
	private TextView matyName;
	private TextView matyPrice;
	private TextView mhouseType;
	private long startTime;
	private long endTime;
	private int state;
	private TextView timeState;
	private TextView buildName;
	private TextView buildTags;
	private TextView buildPrice;
	private ArrayList<Tags> tags = new ArrayList<Tags>();
	private int actStatus;
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constant.DESCRIPTOR);
	private String saleTell;
	private String shareUrl;
	private String houseSourceId;
	private String activityName;
	private String mBuildName;
	private String buildImg;
	private PopupWindow bottomWindow;
	private int shareState;
	private List<BrokeList> brokeList = new ArrayList<BrokeList>();
	private List<SaleswayList> salesHouseList = new ArrayList<SaleswayList>();
	private ListView listview_agent_recommendHouseInfo;
	private PrivilegeHouseInfoAgentAdapter pAdapter;
	private LinearLayout ll_all;
	private LinearLayout ll_broker;
	private TextView recommendTitle;
	private TextView recommendHouseNum;
	private TextView moreHouse;
	private LinearLayout recommendRule;
	private ListView recommendHouseList;
	private RecommendHouseListAdapter houseListAdapter;
	private ImageView recommendHostmaster;
	private TextView recommendHostname;
	private TextView recommendHostcompany;
	private TextView recommendHostposition;
	private TextView recommendHostphone;
	private TextView recommendStarttime;
	private TextView recommendEndtime;
	private TextView getDiscountQuan;
	private TextView recommendGlance;
	private String lat;
	private String lon;
	private LinearLayout bg_downTime;
	private ShareToMoneyPopWindow sharePop;
	private ImageView popImg;
	private String topPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend_agent);
		activityPoolId = getIntent().getStringExtra("activityPoolId");
		buildingId = getIntent().getStringExtra("buildingId");
		activityId = getIntent().getStringExtra("activityId");
		remind = getIntent().getExtras().getString("remind", "");
		remindId = getIntent().getExtras().getString("remindId", "");
		initView();
		initListener();
	}

	@Override
	protected void onResume() {		
		tokenId = UserInfoData.getData(Constant.TOKEN, "");
		initData();
		initPartUserData();
		initAgent();
		super.onResume();
	}

	private void initView() {
		ll_all = (LinearLayout)findViewById(R.id.ll_all);
		// 标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("优惠房");
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setText("");
		tv_right.setCompoundDrawablesWithIntrinsicBounds(
				R.drawable.share, 0, 0, 0);
		tv_right.setCompoundDrawablePadding(0);
		//scrollView
		ObservableScrollView sv_bidding = (ObservableScrollView)findViewById(R.id.sv_recommend);
		sv_bidding.setScrollViewListener(this);	
		// 主图
		mMaster = (ImageView) findViewById(R.id.img_preview);
		// 倒计时
		bg_downTime = (LinearLayout)findViewById(R.id.ll_bg_downtime);
		timeState = (TextView) findViewById(R.id.tv_bidding_time);
		mTimeDown = (TimeView) findViewById(R.id.tivm_time);
		recommendGlance = (TextView) findViewById(R.id.tv_activity_glance);
		//活动标题
		ImageView activityType =(ImageView)findViewById(R.id.img_activity_type);
		activityType.setImageResource(R.drawable.big_hui);
		recommendTitle = (TextView)findViewById(R.id.tv_activity_title);
		recommendHouseNum = (TextView)findViewById(R.id.tv_activity_price);
		recommendHouseNum.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		recommendHouseNum.setCompoundDrawablePadding(0);
		//房源列表
		recommendHouseList = (ListView)findViewById(R.id.lv_recommendHouseList);
		recommendHouseList.setFocusable(false);
		houseListAdapter = new RecommendHouseListAdapter(this,salesHouseList);
		recommendHouseList.setAdapter(houseListAdapter);
		recommendHouseList.setOnItemClickListener(this);
		//查看更多房源
		moreHouse = (TextView)findViewById(R.id.tv_more_house);
		//支付方式
		TextView paywayContent = (TextView)findViewById(R.id.tv_payway_content);
		paywayContent.setText("优惠券领取成功后，您将获得对应的线下购房资格，实际购房交易在开发商售楼处完成。");
		//优惠房规则
		recommendStarttime= (TextView)findViewById(R.id.tv_recommendstarttime);
		recommendEndtime= (TextView)findViewById(R.id.tv_recommendendtime);
		// 跳转用户列表
		// 用户列表
		tv_partting = (TextView) findViewById(R.id.tv_partting);
		tv_partting.setText("当前参加人数：");
		userCount = (TextView) findViewById(R.id.tv_person_count);
		cimgOne = (CircleImageView) findViewById(R.id.cimg_bidding_pone);
		cimgtwo = (CircleImageView) findViewById(R.id.cimg_bidding_ptwo);
		cimglast = (CircleImageView) findViewById(R.id.cimg_last);
		tv_dot = (TextView) findViewById(R.id.tv_dot);
		tv_users = (TextView) findViewById(R.id.tv_users);
		// 优鉴经纪人
		ll_broker = (LinearLayout)findViewById(R.id.ll_broker);
		listview_agent_recommendHouseInfo = (ListView) findViewById(R.id.listview_agent_recommendHouseInfo);
		listview_agent_recommendHouseInfo.setFocusable(false);
		pAdapter = new PrivilegeHouseInfoAgentAdapter(this, brokeList,ll_all,activityPoolId,buildingId);
		listview_agent_recommendHouseInfo.setAdapter(pAdapter);
		//活动提供方
		recommendHostmaster = (ImageView)findViewById(R.id.img_hostmaster);
		recommendHostname = (TextView)findViewById(R.id.tv_hostname);
		recommendHostcompany = (TextView)findViewById(R.id.tv_hostcompany);
		recommendHostposition = (TextView)findViewById(R.id.tv_hostposition);
		recommendHostphone = (TextView)findViewById(R.id.tv_hostphone);
		//活动须知
		recommendRule = (LinearLayout)findViewById(R.id.ll_recommendrule);
		// 领取优惠券
		getDiscountQuan = (TextView)findViewById(R.id.tv_getDiscountQuan);
		getDiscountQuan.setVisibility(View.GONE);
	}
	private void initListener() {
		rl_back.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		tv_users.setOnClickListener(this);
		moreHouse.setOnClickListener(this);
		recommendRule.setOnClickListener(this);
		recommendHostposition.setOnClickListener(this);
		recommendHostphone.setOnClickListener(this);
		getDiscountQuan.setOnClickListener(this);
	}


	/**
	 * 
	 * @Title: viewAssignment
	 * @Description: 控件赋值
	 * @return: void
	 */
	protected void viewAssignment(Result agentRecommendResult) {
		Building build = agentRecommendResult.getBuilding();
		mBuildName = build.getName();
		actStatus = build.getActStatus();
		saleTell = build.getSaleTel();
		Salesway salesway = agentRecommendResult.getSalesway();
		topPrice = StringUtils.intMoney(salesway.getTopPrice());
		activityName = salesway.getActivityName();
		houseSourceId = salesway.getHouseSourceId() + "";
		Share share = agentRecommendResult.getShare();
		shareState = share.getStatus();
		if(shareState == 1){
			showSharePop();
		}
		shareUrl = share.getShareUrl();
		// 主图
		buildImg = build.getMainImage();
		imageLoader.displayImage(build.getMainImage(), mMaster, options);
		// 倒计时
		startTime = salesway.getStartTime();
		endTime = salesway.getEndTime();
		long currentTime = DateUtils.currentTime();
		state = JudgeAcctivityStateUtil.getState(startTime, endTime);
		switch (state) {
		case 0:
			// 即将开始
			bg_downTime.setBackgroundColor(Color.parseColor("#EE4B4C"));
			timeState.setText("活动开始时间：");
			mTimeDown.setTimes(JudgeAcctivityStateUtil
					.startTimeFormat(startTime));
			break;
		case 1:
			// 进行中
			bg_downTime.setBackgroundColor(Color.parseColor("#EE4B4C"));
			timeState.setText("距结束剩：");
			mTimeDown.setDownTimes(JudgeAcctivityStateUtil
					.downTimeFormat(endTime - currentTime));
			mTimeDown.run();
			break;
		case 2:
			// 已结束
			bg_downTime.setBackgroundColor(Color.parseColor("#aa000000"));
			timeState.setText("活动结束时间：");
			mTimeDown.setTimes(JudgeAcctivityStateUtil.startTimeFormat(endTime));
			getDiscountQuan.setTextColor(Color.parseColor("#FC9292"));
			getDiscountQuan.setEnabled(false);
			break;

		default:
			break;
		}
		getDiscountQuan.setVisibility(View.VISIBLE);//底部按钮
		//浏览量
		recommendGlance.setText("浏览"+agentRecommendResult.getPvCount()+"次");
		//在售户型
		List<SaleswayList> salesList = agentRecommendResult.getSaleswayList();
		salesHouseList.addAll(salesList);
		houseListAdapter.notifyDataSetChanged();
		//活动名称
		recommendTitle.setText(salesway.getActivityName());
		recommendHouseNum.setText("活动房源："+salesway.getHouseSaleCount()+"套");
		//优惠房规则
		recommendStarttime.setText(DateUtils.getTime("yyyy/MM/dd HH:mm", startTime));
		recommendEndtime.setText(DateUtils.getTime("yyyy/MM/dd HH:mm", endTime)+"");
		// 活动参加人数
		int userparts = salesway.getJoinCount();
		if (userparts == 0) {
			tv_users.setVisibility(View.GONE);
			userCount.setText("(" + userparts + ")人");
		} else {
			tv_users.setVisibility(View.VISIBLE);
			userCount.setText("(" + userparts + ")人");
		}
		//活动提供方
		imageLoader.displayImage(build.getMainImage(), recommendHostmaster, options);	
		recommendHostname.setText(build.getName());
		recommendHostcompany.setText(build.getAddress());
		lat = build.getLatitude();
		lon = build.getLongitude();
	}
	private void showSharePop() {
		if(null == sharePop){
			sharePop = new ShareToMoneyPopWindow(this, findViewById(R.id.ll_all));
		}
		popImg = sharePop.getPopImg();
		popImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!UserInfoData.isSingIn()){
					ShareNoLoginDialog.showDialog(AgentRecommendActivity.this);
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
	 * @Description: 请求经纪人推荐数据
	 * @return: void
	 */
	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("activityPoolId", activityPoolId);
		map.put("tokenId", tokenId);
		SimpleRequest<AgentRecommendlActivityBean> req = new SimpleRequest<AgentRecommendlActivityBean>(
				API.QUERY_SALES_WAY_ACTIVITY,
				AgentRecommendlActivityBean.class,
				new Listener<AgentRecommendlActivityBean>() {
					@Override
					public void onResponse(AgentRecommendlActivityBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								salesHouseList.clear();
								Result agentRecommendResult = response
										.getResult();
								viewAssignment(agentRecommendResult);

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
	 * @Title: initData
	 * @Description: 查询经纪人推荐参与用户
	 * @return: void
	 */
	private void initPartUserData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityId", activityId);
		map.put("page", "1");
		SimpleRequest<AgentRecommendUserListBean> req = new SimpleRequest<AgentRecommendUserListBean>(
				API.QUERY_SALEWAY_PARTICIPANT,
				AgentRecommendUserListBean.class,
				new Listener<AgentRecommendUserListBean>() {
					@Override
					public void onResponse(AgentRecommendUserListBean response) {
						if (null != response.getResult()) {
							List<UserList> userList = new ArrayList<UserList>();
							if(null != response.getResult().getList()){
								userList.addAll(response.getResult().getList());
							}
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
	 * @Title: initAgent 
	 * @Description:优鉴经纪人
	 * @return: void
	 */
	private void initAgent() {
		brokeList.clear();
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityPoolId", activityPoolId);
		map.put("tokenId", tokenId);
		SimpleRequest<GoodBrokeBean> req = new SimpleRequest<GoodBrokeBean>(
				API.QUERYBROKER, GoodBrokeBean.class,
				new Listener<GoodBrokeBean>() {
					@Override
					public void onResponse(GoodBrokeBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								List<BrokeList> list = response.getResult().getList();
								brokeList.addAll(list);
								
								pAdapter.notifyDataSetChanged();
							} else {
								showTost(response.getMsg());
							}
							if(brokeList.size() == 0){
								ll_broker.setVisibility(View.GONE);
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		// 分享
		case R.id.tv_right:
			if (shareState == 1) {
				if (!UserInfoData.isSingIn()) {
					ShareNoLoginDialog.showDialog(this);
				} else {
					showBottomPopuwindow();
					configPlatforms();
				}
			} else {
				showBottomPopuwindow();
				configPlatforms();
			}
			break;
		case R.id.tv_more_house:
			//查看更多房源
			if (UserInfoData.isSingIn()) {
				Intent intent = new Intent(this, BuildingTypeActivity.class);
				intent.putExtra("activityId", activityId);
				intent.putExtra("actStatus", actStatus);
				intent.putExtra("activityPoolId", activityPoolId);
				intent.putExtra("buildingId", buildingId);
				startActivity(intent);
			} else {
				goOthers(LoginActivity.class);
				// showTost("您还未登录，请登录");
			}
			break;
		case R.id.tv_users:
			//查看更多参与者
			Intent userIntent = new Intent(this,
					AgentRecommendUserListActivity.class);
			userIntent.putExtra("activityId", activityId);
			startActivity(userIntent);
			// goOthers(AgentRecommendUserListActivity.class);
			break;
		case R.id.tv_hostposition:
			// 活动提供方位置
			Intent mapIntent = new Intent(this,NewHouseCircumMapActivity.class);
			mapIntent.putExtra("lon", lon);
			mapIntent.putExtra("lat", lat);
			startActivity(mapIntent);
			
			break;	
		case R.id.tv_hostphone:
			//联系售楼处
			Intent tellIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
					+ saleTell));
			startActivity(tellIntent);
			break;
		case R.id.ll_recommendrule:
			// 看规则
			Intent ruleIntent = new Intent(this,
					ActivityRulesWebViewActivity.class);
			ruleIntent.putExtra("rule", API.CUTHOUSERULE);
			startActivity(ruleIntent);
			break;
		case R.id.tv_getDiscountQuan:
			//领取优惠券
			if (UserInfoData.isSingIn()) {
				Intent intent = new Intent(this, BuildingTypeActivity.class);
				intent.putExtra("activityId", activityId);
				intent.putExtra("actStatus", actStatus);
				intent.putExtra("activityPoolId", activityPoolId);
				intent.putExtra("buildingId", buildingId);
				startActivity(intent);
			} else {
				goOthers(LoginActivity.class);
				// showTost("您还未登录，请登录");
			}
			// goOthers(GetDiscountSuccessActivity.class);
			break;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (state == 1 || state == 2 ) {
			Intent intent = new Intent(this,AgentRecommendHouseInfoActivity.class);
			intent.putExtra("ticketAmount", salesHouseList.get(arg2).getTicketAmount()+"");
			intent.putExtra("houseSouseID", salesHouseList.get(arg2).getHouseSourceId()+"");
			intent.putExtra("buildingId", buildingId);
			intent.putExtra("activityPoolId", activityPoolId);
			intent.putExtra("activityState", state+"");
			startActivity(intent);
		} else {
			showTost("活动还没有开始");
		}
		
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
					showTost("分享成功");
					showText += "平台分享成功";
					SaveUserShareUtil.saveUserShare(buildingId, activityPoolId,
							houseSourceId + "");
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
		String title = "房链发福利了最高领取"+topPrice+"元现金购房劵。";
    	String content = "";
    	if(shareState == 1){
			content = mBuildName+"全城钜惠,最高可免费领取"+topPrice+"元现金购房抵扣劵,分享即可获得现金红包。";
		}else{
			content = mBuildName+"全城钜惠,最高可免费领取"+topPrice+"元现金购房抵扣劵。";
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
		// 设置分享文字内容
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTitle(title);
		weixinContent.setShareContent(content);
		weixinContent.setShareImage(image);
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
		sinaShareContent.setShareContent(content + shareUrl+"[房链分享]");
		sinaShareContent.setShareImage(image);
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
		mTimeDown.stop();
		super.onPause();
	}

	@Override
	protected void onStop() {
		mTimeDown.stop();
		super.onStop();
	}
	
}
