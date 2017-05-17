package com.flzc.fanglian.ui.newhouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.flzc.fanglian.model.CounselorListBean;
import com.flzc.fanglian.model.CounselorListBean.Result.CounselorList;
import com.flzc.fanglian.model.NewHouseDetailBean;
import com.flzc.fanglian.model.NewHouseDetailBean.Result;
import com.flzc.fanglian.model.NewHouseDetailBean.Result.BuildActivity;
import com.flzc.fanglian.model.NewHouseDetailBean.Result.HouseTypes;
import com.flzc.fanglian.model.NewHouseDetailBean.Result.Jztags;
import com.flzc.fanglian.model.NewHouseDetailBean.Result.Share;
import com.flzc.fanglian.model.NewHouseDetailBean.Result.Tags;
import com.flzc.fanglian.model.NewHouseDetailBean.Result.Wytags;
import com.flzc.fanglian.model.NewHouseDetailBean.Result.Zxtags;
import com.flzc.fanglian.ui.AllAgentListActivity;
import com.flzc.fanglian.ui.adapter.CounselorAdapter;
import com.flzc.fanglian.ui.adapter.NewHouseActivityTypeAdapter;
import com.flzc.fanglian.ui.adapter.NewHouseHouseTypeAdapter;
import com.flzc.fanglian.ui.agent_activity.AgentRecommendActivity;
import com.flzc.fanglian.ui.bidding_activity.BiddingActivity;
import com.flzc.fanglian.ui.everyday_special_activity.EveryDaySpecialActivity;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.LogUtil;
import com.flzc.fanglian.util.SaveUserShareUtil;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.ListViewForScrollView;
import com.flzc.fanglian.view.dialog.ShareNoLoginDialog;
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
 * @ClassName: NewHouseDetailActivity
 * @Description: 新房详情页 点击新房列表页跳转
 * @author: Tien.
 * @date: 2016年3月7日 下午7:51:06
 */
public class NewHouseDetailActivity extends BaseActivity implements
		OnClickListener {
	private RelativeLayout rl_back;
	private TextView titleName;
	private LinearLayout rl_checkImgLayout_newHouseList;
	private RelativeLayout rl_checkMoreTypeLayout_newHouseDetail;
	private TextView tv_checkMoreAgent_newHouseDetail;// 查看更多置业顾问
	private String buildingId;
	private RelativeLayout rl_share;
	private ImageView mBuildingMaster;
	private TextView mBuildingPicCount;
	private TextView mBuildingName;
	private TextView mBuildingTag;
	private TextView mBuildingPrice;
	private TextView mBuildingAdress;
	private RelativeLayout rl_more;
	private RelativeLayout rl_up;
	private TextView tv_compare;
	private LinearLayout ll_up;
	private View ly_buildinfo;
	private TextView liveTime;
	private TextView propertyType;
	private TextView parkSpace;
	private TextView onSale;
	private TextView greenRate;
	private TextView plotRadio;
	private TextView develops;
	private TextView floorSpace;
	private TextView buildType;
	private TextView zxState;
	private TextView buildArea;
	private TextView openTime;
	private TextView saleXuke;
	private TextView saleAddress;
	private ListViewForScrollView lv_houseType;
	private ListView counselorListView;
	private ListView lvActivityType;
	private RelativeLayout rl_circum;
	private TextView houseTypeCount;
	private String tags;
	private TextView buildIntro;
	private ImageView mapView;
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constant.DESCRIPTOR);
	private LinearLayout ll_bottomLayout_newHouseListDetail;
	private ImageView imgShare;
	private RelativeLayout rl_newhouse_counselor;
	private TextView checkMoreHouseType;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newhouse_detail);
		buildingId = getIntent().getExtras().getString("buildingId", "");
		tags = getIntent().getStringExtra("tags");
		initItem();
		initListener();
		initData();
		initCounselor();
	}

	private void initItem() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		titleName = (TextView) findViewById(R.id.tv_title);
		rl_share = (RelativeLayout) findViewById(R.id.rl_shareLayout_newHouseDetail);
		rl_share.setOnClickListener(this);
		imgShare = (ImageView) findViewById(R.id.img_iconShare_newHouseDetail);
//		shareUrl = Constant.BUILDING_SHARE + "?inviteCode="
//				+ UserInfoData.getData(Constant.MY_INVITE_CODE, "")
//				+ "&tokenId=" + UserInfoData.getData(Constant.TOKEN, "")
//				+ "&buildingId=" + buildingId + "&userType=" + 0
//				+ "&page=1&pageSize=10";
		// 共XX张图
		rl_checkImgLayout_newHouseList = (LinearLayout) findViewById(R.id.rl_checkImgLayout_newHouseList);
		rl_checkImgLayout_newHouseList.bringToFront();
		mBuildingPicCount = (TextView) findViewById(R.id.detail_area_house_pic_count_biddingDetail);
		// 楼盘主图
		mBuildingMaster = (ImageView) findViewById(R.id.img_mainImg_newHouseDetail);
		// 楼盘名称参考价的布局
		mBuildingName = (TextView) findViewById(R.id.tv_houseName_newHouseDetail);
		mBuildingTag = (TextView) findViewById(R.id.tv_tags_newHouseDetail);
		mBuildingPrice = (TextView) findViewById(R.id.tv_price_newHouseDetail);
		// 楼盘地址
		mBuildingAdress = (TextView) findViewById(R.id.tv_address_newHouseDetail);
		// 查看更多信息
		buildIntro = (TextView) findViewById(R.id.tv_introduction_newHouseDetail);
		rl_more = (RelativeLayout) findViewById(R.id.rl_checkMoreLayout_newHouseDetail);
		ly_buildinfo = findViewById(R.id.ly_buildinfo);
		rl_up = (RelativeLayout) findViewById(R.id.rl_detail_up);
		tv_compare = (TextView) findViewById(R.id.tv_compare_build);
		ll_up = (LinearLayout) findViewById(R.id.ll_detail_up);
		// 初始化楼盘更多信息
		initBuildInfo();
		// 活动
		lvActivityType = (ListView) findViewById(R.id.lv_activity_type);
		lvActivityType.setFocusable(false);
		// 在售户型
		rl_checkMoreTypeLayout_newHouseDetail = (RelativeLayout) findViewById(R.id.rl_checkMoreTypeLayout_newHouseDetail);
		rl_checkMoreTypeLayout_newHouseDetail.setOnClickListener(this);
		lv_houseType = (ListViewForScrollView) findViewById(R.id.listview_houseType_newHouseDetail);
		houseTypeCount = (TextView) findViewById(R.id.tv_houseTypeCount_newHouseDetail);
		checkMoreHouseType = (TextView) findViewById(R.id.tv_checkMoreHouse_newHouseDetail);
		lv_houseType.setFocusable(false);
		// 周边
		mapView = (ImageView) findViewById(R.id.bmapView);
		rl_circum = (RelativeLayout) findViewById(R.id.rl_circum);
		// 查看更多置业顾问
		tv_checkMoreAgent_newHouseDetail = (TextView) findViewById(R.id.tv_checkMorecounselor_newHouseDetail);
		tv_checkMoreAgent_newHouseDetail.setOnClickListener(this);
		// 置业顾问列表
		counselorListView = (ListView) findViewById(R.id.listview_agentList_newHouseListDetail);
		rl_newhouse_counselor = (RelativeLayout) findViewById(R.id.rl_newhouse_counselor);
		counselorListView.setFocusable(false);
		// 售楼处地址
		ll_bottomLayout_newHouseListDetail = (LinearLayout) findViewById(R.id.ll_bottomLayout_newHouseListDetail);

	}

	/**
	 * 
	 * @Title: initBuildInfo
	 * @Description:楼盘详细信息
	 * @return: void
	 */
	private void initBuildInfo() {
		// 入住时间
		liveTime = (TextView) findViewById(R.id.tv_live_time);
		// 物业类型
		propertyType = (TextView) findViewById(R.id.tv_property_type);
		// 停车位
		parkSpace = (TextView) findViewById(R.id.tv_park_space);
		// 在售数
		onSale = (TextView) findViewById(R.id.tv_onSale);
		// 绿化率
		greenRate = (TextView) findViewById(R.id.tv_green_rate);
		// 容积率
		plotRadio = (TextView) findViewById(R.id.tv_plot_radio);
		// 开发商
		develops = (TextView) findViewById(R.id.tv_develops);
		// 占地面积
		floorSpace = (TextView) findViewById(R.id.tv_floor_space);
		// 建筑类型
		buildType = (TextView) findViewById(R.id.tv_buildtype);
		// 装修状况
		zxState = (TextView) findViewById(R.id.tv_house_type);
		// 建筑面积
		buildArea = (TextView) findViewById(R.id.tv_build_area);
		// 开盘时间
		openTime = (TextView) findViewById(R.id.tv_open_time);
		// 预售许可
		saleXuke = (TextView) findViewById(R.id.tv_sale_xuke);
		// 售楼处地址
		saleAddress = (TextView) findViewById(R.id.tv_sale_address);

	}

	/**
	 * 
	 * @param buildInfo
	 * @Title: buildInfoData
	 * @Description:楼盘详细信息
	 * @return: void
	 */
	private void buildInfoData(Result buildInfo) {
		// 入住时间
		if (!TextUtils.isEmpty(buildInfo.getPossession())) {
			liveTime.setText("预计"+DateUtils.getTime("yyyy年MM月", Long.parseLong(buildInfo
					.getPossession()))+"交房");
		}
		// 物业类型
		List<Wytags> wyTags = new ArrayList<Wytags>();
		wyTags.addAll(buildInfo.getWytags());
		String wyTagsInfo = "";
		for (Wytags wyTag : wyTags) {
			wyTagsInfo += wyTag.getName() + " ";
		}
		propertyType.setText(wyTagsInfo);
		// 停车位
		parkSpace.setText(buildInfo.getParkNum() + "");
		// 在售数
		onSale.setText(buildInfo.getSaleCount() + "户");
		// 绿化率
		greenRate.setText(buildInfo.getGreenRate() + "%");
		// 容积率
		plotRadio.setText(buildInfo.getVolRate());
		// 开发商
		develops.setText(buildInfo.getBuilder());
		// 占地面积
		floorSpace.setText(buildInfo.getFieldRate() + "㎡");
		// 建筑类型
		List<Jztags> jzTags = new ArrayList<Jztags>();
		jzTags.addAll(buildInfo.getJztags());
		String jzTagsInfo = "";
		for (Jztags jzTag : jzTags) {
			jzTagsInfo += jzTag.getName() + " ";
		}
		buildType.setText(jzTagsInfo);
		// 装修状况
		List<Zxtags> zxtags = new ArrayList<Zxtags>();
		zxtags.addAll(buildInfo.getZxtags());
		String houseZXInfo = "";
		for (Zxtags zxtag : zxtags) {
			houseZXInfo += zxtag.getName() + " ";
		}
		zxState.setText(houseZXInfo);
		// 建筑面积
		buildArea.setText(buildInfo.getBuildingSize() + "㎡");
		// 开盘时间
		if (!TextUtils.isEmpty(buildInfo.getOpenTime())) {
			openTime.setText(DateUtils.formatDate(Long.parseLong(buildInfo
					.getOpenTime())));
		}
		// 预售许可
		saleXuke.setText(buildInfo.getLicence());
		// 售楼处地址
		saleAddress.setText(buildInfo.getSaleAddress());

	}

	private void initListener() {
		rl_more.setOnClickListener(this);
		tv_compare.setOnClickListener(this);
		ll_up.setOnClickListener(this);
		mBuildingMaster.setOnClickListener(this);
		rl_circum.setOnClickListener(this);
		ll_bottomLayout_newHouseListDetail.setOnClickListener(this);
		mapView.setOnClickListener(this);
	}

	/**
	 * 
	 * @Title: initData
	 * @Description: 请求楼盘详细信息
	 * @return: void
	 */
	private List<HouseTypes> houseTypes = new ArrayList<NewHouseDetailBean.Result.HouseTypes>();
	private String unitPrice;
	private String lon;
	private String lat;
	private String saleTell;
	private String shareUrl;
	private String buildImg;
	private String sBuildName;
	private String buildMemo;
	private PopupWindow bottomWindow;
	private int shareState;

	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		SimpleRequest<NewHouseDetailBean> req = new SimpleRequest<NewHouseDetailBean>(
				API.DETAILS, NewHouseDetailBean.class,
				new Listener<NewHouseDetailBean>() {
					@Override
					public void onResponse(NewHouseDetailBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								Result detailResult = response.getResult();
								viewAsignment(detailResult);
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
	 * @Title: initCounselor
	 * @Description: 请求置业顾问数据
	 * @return: void
	 */
	private void initCounselor() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("page", 1 + "");
		map.put("pageSize", 3 + "");
		SimpleRequest<CounselorListBean> req = new SimpleRequest<CounselorListBean>(
				API.QUERY_BUILDER_LIST, CounselorListBean.class,
				new Listener<CounselorListBean>() {
					@Override
					public void onResponse(CounselorListBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								List<CounselorList> counselorList = new ArrayList<CounselorList>();
								if(null != response.getResult().getList()){
									counselorList.addAll(response.getResult().getList());
								}
								if (null != counselorList
										&& counselorList.size() == 0) {
									rl_newhouse_counselor
											.setVisibility(View.GONE);
								}
								counselorListView
										.setAdapter(new CounselorAdapter(
												NewHouseDetailActivity.this,
												counselorList));
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
	 * @Title: viewAsignment
	 * @Description: 控件赋值
	 * @return: void
	 */
	protected void viewAsignment(Result detailResult) {
		if (null == detailResult) {
			showTost("楼盘详情返回错误");
			return;
		}
		// 标题
		sBuildName = detailResult.getName();
		titleName.setText(detailResult.getName());
		// 分享
		Share share = detailResult.getShare();
		shareState = share.getStatus();
		if (share.getStatus() == 1) {
			imgShare.setVisibility(View.VISIBLE);
		}
		shareUrl = share.getShareUrl();
		// 共XX张图
		if (detailResult.getBuildingImgCount() == 0) {
			mBuildingMaster.setEnabled(false);
		}
		mBuildingPicCount.setText("共" + detailResult.getBuildingImgCount()
				+ "张图");
		// 楼盘主图
		buildImg = detailResult.getMaster();
		imageLoader.displayImage(detailResult.getMaster(), mBuildingMaster,
				options);
		// 参考价那块
		mBuildingName.setText(detailResult.getName());
		List<Tags> tags = new ArrayList<Tags>();
		tags.addAll(detailResult.getTags());
		String tagsword = "";
		for (Tags tag : tags) {
			tagsword += tag.getName() + " ";
		}
		mBuildingTag.setText(tagsword);
		// 单价
		unitPrice = StringUtils.intMoney(detailResult.getSoldPrice());
		mBuildingPrice.setText("¥" + unitPrice + "元/㎡");
		// 地址
		mBuildingAdress.setText(detailResult.getAddress());
		// 楼盘详细信息
		String sbuildMemo = detailResult.getRemark();
		//showTost(detailResult.getRemark()+"");
		if(TextUtils.isEmpty(sbuildMemo)){
			buildMemo = detailResult.getName();
		}else{
			buildMemo = sbuildMemo;
		}
		if(sbuildMemo.length()>20){
			buildMemo = sbuildMemo.substring(0, 20);
		}
		if(!TextUtils.isEmpty(sbuildMemo)){
			buildIntro.setText(sbuildMemo);
		}else{
			buildIntro.setVisibility(View.GONE);
		}
		buildInfoData(detailResult);
		// 活动
		List<BuildActivity> buildActivity = new ArrayList<BuildActivity>();
		List<BuildActivity> tempactivity = detailResult.getActivity();
		for (BuildActivity activity : tempactivity) {
			if (activity.getActivityType() != 3904) {
				buildActivity.add(activity);
			}
		}
		lvActivityType.setAdapter(new NewHouseActivityTypeAdapter(this,
				buildActivity));
		lvActivityTypeOnItemClick(buildActivity);
		// 户型
		int saleCount = detailResult.getHouseTypeCount();
		houseTypes.addAll(detailResult.getHouseTypes());
		if (houseTypes.size() == 0) {
			rl_checkMoreTypeLayout_newHouseDetail.setVisibility(View.GONE);
		}
		houseTypeCount.setText("(" +saleCount + "种)");
		lv_houseType.setAdapter(new NewHouseHouseTypeAdapter(this, houseTypes,
				unitPrice));
		// 位置和周边
		lon = detailResult.getLon();
		lat = detailResult.getLat();
		String url = "http://api.map.baidu.com/staticimage?width=400&height=300&center="
				+ lon + "," + lat + "&zoom=18";
		imageLoader.displayImage(url, mapView, options);
		// 售楼处热线
		saleTell = detailResult.getSaleTel();
	}

	/**
	 * 
	 * @Title: lvActivityTypeOnItemClick
	 * @Description: 活动列表的点击事件
	 * @return: void
	 */
	private void lvActivityTypeOnItemClick(
			final List<BuildActivity> buildActivity) {
		lvActivityType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BuildActivity activity = buildActivity.get(position);
				String activityType = activity.getActivityType() + "";
				String activityPoolId = activity.getId();
				String buildingId = activity.getBuildingId();
				String activityId = activity.getActivityId();
				String remind = activity.getRemaind();
				String remindId = activity.getRemaindId();

				if (activityType.equals("3901")) {
					Intent intent = new Intent(NewHouseDetailActivity.this,
							BiddingActivity.class);
					intent.putExtra("activityPoolId", activityPoolId);
					intent.putExtra("buildingId", buildingId);
					intent.putExtra("activityId", activityId);
					intent.putExtra("remind", remind);
					intent.putExtra("remindId", remindId);
					startActivity(intent);
				} else if (activityType.equals("3902")) {
					Intent intent = new Intent(NewHouseDetailActivity.this,
							EveryDaySpecialActivity.class);
					intent.putExtra("activityPoolId", activityPoolId);
					intent.putExtra("buildingId", buildingId);
					intent.putExtra("activityId", activityId);
					intent.putExtra("remind", remind);
					intent.putExtra("remindId", remindId);
					startActivity(intent);
				} else if (activityType.equals("3903")) {
					Intent intent = new Intent(NewHouseDetailActivity.this,
							AgentRecommendActivity.class);
					intent.putExtra("activityPoolId", activityPoolId);
					intent.putExtra("buildingId", buildingId);
					intent.putExtra("activityId", activityId);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.rl_shareLayout_newHouseDetail:
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
		case R.id.img_mainImg_newHouseDetail:
			// 新房图片页
			Intent photoIntent = new Intent(this, NewHousePhotoActivity.class);
			photoIntent.putExtra("houseId", buildingId);
			startActivity(photoIntent);
			break;
		case R.id.rl_checkMoreLayout_newHouseDetail:
			// 查看更多信息
			ly_buildinfo.setVisibility(View.VISIBLE);
			rl_more.setVisibility(View.GONE);
			rl_up.setVisibility(View.VISIBLE);
			break;
		case R.id.ll_detail_up:
			// 查看更多信息
			ly_buildinfo.setVisibility(View.GONE);
			rl_more.setVisibility(View.VISIBLE);
			rl_up.setVisibility(View.GONE);
			break;
		case R.id.tv_compare_build:
			// 查看更多信息
			showTost("对比楼盘");
			break;
		case R.id.rl_checkMoreTypeLayout_newHouseDetail:
			// 查看更多户型
			Intent intentType = new Intent(NewHouseDetailActivity.this,
					NewHouseResourceActivity.class);
			intentType.putExtra("buildingId", buildingId);
			intentType.putExtra("unitPrice", unitPrice + "");
			startActivity(intentType);
			break;
		case R.id.bmapView:
			// 查看周边配套
			Intent intentMap1 = new Intent(NewHouseDetailActivity.this,
					NewHouseCircumMapActivity.class);
			intentMap1.putExtra("lon", lon);
			intentMap1.putExtra("lat", lat);
			startActivity(intentMap1);
			break;
		case R.id.rl_circum:
			// 查看周边配套
			Intent intentMap = new Intent(NewHouseDetailActivity.this,
					NewHouseCircumMapActivity.class);
			intentMap.putExtra("lon", lon);
			intentMap.putExtra("lat", lat);
			startActivity(intentMap);
			break;
		case R.id.tv_checkMorecounselor_newHouseDetail:
			// 查看更多置业顾问
			Intent intent = new Intent(NewHouseDetailActivity.this,
					AllAgentListActivity.class);
			intent.putExtra("buildingId", buildingId);
			startActivity(intent);
			break;

		case R.id.ll_bottomLayout_newHouseListDetail:
			// 售楼热线
			Intent tellIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
					+ saleTell));
			startActivity(tellIntent);
			break;
		}
	}

	private void showBottomPopuwindow() {
		View view = getLayoutInflater().inflate(R.layout.share_item, null);
		TextView share_title = (TextView)view.findViewById(R.id.tv_share_title);
		if (shareState == 1) {
			share_title.setText("分享成功可获得红包奖励");
		}else{
			share_title.setText("分享到");
		}
		if(null == bottomWindow){
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
		bottomWindow.showAtLocation(rl_share, Gravity.BOTTOM, 0, 0);
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
					LogUtil.e("BuildShare", buildingId+", "+"0"+", "+"0");
					SaveUserShareUtil.saveUserShare(buildingId, "0", "0");
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
		mController.getConfig().closeToast();
		// mController.setShareImage(new UMImage(NewHouseDetailActivity.this,
		// shareUrl));
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId = Constant.APP_ID;
		String appSecret = Constant.APP_SECRET;
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
		wxHandler.addToSocialSDK();
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTitle(sBuildName+"");

		// 设置分享文字内容
		weixinContent.setShareContent("我在房链发现一个不错的楼盘，快来看看。");
		weixinContent.setShareMedia(image);
		weixinContent.setTargetUrl(shareUrl+"");
		mController.setShareMedia(weixinContent);
		mController.getConfig().setSsoHandler(wxHandler);

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		CircleShareContent circleShareContent = new CircleShareContent();
		circleShareContent.setTitle(sBuildName+"");
		circleShareContent.setShareContent("我在房链发现一个不错的楼盘，快来看看。");
		circleShareContent.setShareMedia(image);
		circleShareContent.setTargetUrl(shareUrl+"");
		mController.setShareMedia(circleShareContent);
		mController.getConfig().setSsoHandler(wxCircleHandler);

		// 新浪
		SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
		sinaSsoHandler.setShareAfterAuthorize(true);
		sinaSsoHandler.addToSocialSDK();
		SinaShareContent sinaShareContent = new SinaShareContent();
		sinaShareContent.setTitle("");
		sinaShareContent.setShareContent(sBuildName+"我在房链发现一个不错的楼盘，快来看看。"+"请点击："+ shareUrl+"[房链分享]");
		sinaShareContent.setShareMedia(image);
		sinaShareContent.setTargetUrl(shareUrl+"");
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
		super.onDestroy();
		mController.getConfig().cleanListeners();
	}
}
