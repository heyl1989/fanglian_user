package com.flzc.fanglian.ui.bidding_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.flzc.fanglian.model.QueryHouseSourceBean;
import com.flzc.fanglian.model.QueryHouseSourceBean.Result;
import com.flzc.fanglian.model.QueryHouseSourceBean.Result.CurrActUser;
import com.flzc.fanglian.model.QueryHouseSourceBean.Result.Imgs;
import com.flzc.fanglian.model.SaveUserBidding;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.JudgeAcctivityStateUtil;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.TimeDownView;
import com.flzc.fanglian.view.TimeViewWhiteWord;
import com.flzc.fanglian.view.dialog.CustomDialog;
import com.flzc.fanglian.view.dialog.CustomDialog.Builder;

/**
 * 
 * @ClassName: BiddingHouseInfoActivity
 * @Description: 竞拍活动 房源信息页面
 * @author: Tien.
 * @date: 2016年3月7日 上午10:49:22
 */
public class BiddingHouseInfoActivity extends BaseActivity implements
		OnClickListener {
	private RelativeLayout rl_backLayout_biddingHouseInfo;// 返回
	private TextView img_getReward_biddingHouseInfo;// 立即出价
	private View popView;// 竞拍弹窗的view
	private ImageView img_mainImg_biddingHouseInfo;// 主图
	private TextView tv_currentPrice_biddingHouseInfo;// 起拍价格 自己算
	private TextView tv_formerPrice_biddingHouseInfo;// 市场价格
	private TextView tv_houseType_biddingHouseInfo;// 户型
	private TextView tv_acreagee_biddingHouseInfo;// 建筑面积
	private TextView tv_buildingNum_biddingHouseInfo;// 楼号
	private TextView tv_unitNum_biddingHouseInfo;// 单元号
	private TextView tv_floor_biddingHouseInfo;// 楼层
	private TextView tv_residenceType_biddingHouseInfo;// 住宅类型
	private TextView tv_name_biddingHouseInfo;// 楼盘名字
	private TextView tv_address_biddingHouseInfo;// 楼盘地址
	private RelativeLayout rl_activity_time_down;

	private String userStatus;// 0为未登录，1为未报名、2为报名但未消费3次机会，3为报名了消费了三次机会
	/**
	 * 请求接口所用参数，上个页面穿过来
	 */
	private String houseSourceId;
	private String activityPoolId;
	private String buildingId;
	private int state;// 活动状态 0==未开始 1==进行中 2==已结束
	private long startTime, endTime;// 活动的开始时间和结束时间
	private int minPrice;// 竞拍价
	private String activityId;
	private int sendPrice;// 竞拍出的价格
	private int counts;// 剩余出价次数

	private TimeViewWhiteWord time_view_downtime;
	/**
	 * 当前拍手信息
	 */
	private String userHeadUrl;
	private String userPrice;
	private String userName;
	private boolean isCurUserNullInfo;// 当前拍手信息是否为空
	private String biddingPrice;
	protected String price;
	private PopupWindow popupWindow;
	private PopupWindow successWindow;
	private ImageView img_bidderImg_popupWindow_bidding;
	private TextView tv_bidderName_popupWindow_bidding;
	private TextView tv_bidderPrice_popupWindow_bidding;
	private TextView tv_remind_times;
	private RelativeLayout layout_confirmLayout_popupWindow_bidding;
	private TextView tv_confirmHint_popupWindow_bidding;
	private EditText tv_biddingMoney;
	public static BiddingHouseInfoActivity biddingHouseInfoActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bidding_houseinfo);
		biddingHouseInfoActivity = this;
		initItem();
		initData();
	}

	@Override
	protected void onResume() {
		initBottomView();
		super.onResume();
	}

	private void initItem() {
		rl_backLayout_biddingHouseInfo = (RelativeLayout) findViewById(R.id.rl_backLayout_biddingHouseInfo);
		rl_backLayout_biddingHouseInfo.setOnClickListener(this);
		rl_backLayout_biddingHouseInfo.bringToFront();

		img_getReward_biddingHouseInfo = (TextView) findViewById(R.id.tv_getReward_biddingHouseInfos);
		img_getReward_biddingHouseInfo.setOnClickListener(this);

		img_mainImg_biddingHouseInfo = (ImageView) findViewById(R.id.img_mainImg_biddingHouseInfo);

		tv_currentPrice_biddingHouseInfo = (TextView) findViewById(R.id.tv_currentPrice_biddingHouseInfo);
		tv_formerPrice_biddingHouseInfo = (TextView) findViewById(R.id.tv_formerPrice_biddingHouseInfo);
		tv_houseType_biddingHouseInfo = (TextView) findViewById(R.id.tv_houseType_biddingHouseInfo);
		tv_acreagee_biddingHouseInfo = (TextView) findViewById(R.id.tv_acreagee_biddingHouseInfo);
		tv_buildingNum_biddingHouseInfo = (TextView) findViewById(R.id.tv_buildingNum_biddingHouseInfo);
		tv_unitNum_biddingHouseInfo = (TextView) findViewById(R.id.tv_unitNum_biddingHouseInfo);
		tv_floor_biddingHouseInfo = (TextView) findViewById(R.id.tv_floor_biddingHouseInfo);
		tv_residenceType_biddingHouseInfo = (TextView) findViewById(R.id.tv_residenceType_biddingHouseInfo);
		tv_name_biddingHouseInfo = (TextView) findViewById(R.id.tv_name_biddingHouseInfo);
		tv_address_biddingHouseInfo = (TextView) findViewById(R.id.tv_address_biddingHouseInfo);
		time_view_downtime = (TimeViewWhiteWord) findViewById(R.id.time_view_downtime);
		rl_activity_time_down = (RelativeLayout) findViewById(R.id.rl_activity_time_down);

		if (null != getIntent().getExtras()) {
			String from = getIntent().getExtras().getString("from", "");
			if (TextUtils.equals("fanglianquan", from)) {
				findViewById(R.id.ll_bidding_bottom).setVisibility(View.GONE);
			}
		}
	}

	private void initData() {
		Intent intent = getIntent();
		buildingId = intent.getStringExtra("buildingId");
		activityPoolId = intent.getStringExtra("activityPoolId");
		houseSourceId = intent.getStringExtra("houseSourceId");
		userStatus = intent.getStringExtra("userStatus");
		state = intent.getIntExtra("state", 10);
		startTime = intent.getLongExtra("startTime", 0);
		endTime = intent.getLongExtra("endTime", 0);
		try {
			if(!TextUtils.isEmpty(intent.getStringExtra("minPrice"))){
				minPrice = (Integer.parseInt(intent.getStringExtra("minPrice")));
			}
		} catch (Exception e) {
			showTost("数据错误");
		}
		activityId = intent.getStringExtra("activityId");
		sendPrice = minPrice;
		counts = intent.getIntExtra("counts", 0);
		biddingPrice = intent.getStringExtra("biddingPrice");
		// initBottomView();
		getHouseSourceData();
	}

	/**
	 * 
	 * @Title: initBottomView
	 * @Description: 初始化底部视图，看是否需要缴纳保证金
	 * @return: void
	 * // 0 即将开始, 1进行中，2已结束
	 */
	private void initBottomView() {
		if (0 == state) {
			// 活动未开始
			rl_activity_time_down.setVisibility(View.VISIBLE);
			long currentTime = DateUtils.currentTime();
			int[] times = new int[4];
			times = JudgeAcctivityStateUtil.downTimeFormat(startTime
					- currentTime);
			time_view_downtime.setDownTimes(times);
			time_view_downtime.run();
			if("0".equals(userStatus)){
				// 未登录,底部显示缴纳保证金
				img_getReward_biddingHouseInfo.setText("立即缴纳保证金");
			}
			if ("1".equals(userStatus)) {
				// 未报名,底部显示缴纳保证金
				img_getReward_biddingHouseInfo.setText("立即缴纳保证金");
			}
			if ("2".equals(userStatus)) {
				// 报名但未消费3次机会
				img_getReward_biddingHouseInfo.setText("立即出价");
			}
		}
		if (1 == state) {
			rl_activity_time_down.setVisibility(View.GONE);
			if("0".equals(userStatus)){
				// 未登录,底部显示缴纳保证金
				img_getReward_biddingHouseInfo.setText("立即缴纳保证金");
			}
			if ("1".equals(userStatus)) {
				// 未报名,底部显示缴纳保证金
				img_getReward_biddingHouseInfo.setText("立即缴纳保证金");
			}
			if ("2".equals(userStatus)) {
				// 报名但未消费3次机会
				img_getReward_biddingHouseInfo.setText("立即出价");
			}
			if ("3".equals(userStatus)) {
				// 报名但未消费3次机会
				img_getReward_biddingHouseInfo.setText("立即出价");
				img_getReward_biddingHouseInfo.setTextColor(Color.parseColor("#fd8888"));
				img_getReward_biddingHouseInfo.setEnabled(false);
			}

		}
		if (2 == state) {
			rl_activity_time_down.setVisibility(View.GONE);
			img_getReward_biddingHouseInfo.setText("出价失效");
			img_getReward_biddingHouseInfo.setTextColor(Color.parseColor("#fd8888"));
			img_getReward_biddingHouseInfo.setEnabled(false);
		}
	}

	/**
	 * 
	 * @Title: getHouseSourceData
	 * @Description: 得到房源数据
	 * @param buildingId
	 *            楼盘ID
	 * @param houseSourceId
	 *            房源ID
	 * @return: void
	 */
	private void getHouseSourceData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("activityPoolId", activityPoolId);
		map.put("houseSourceId", houseSourceId);
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		SimpleRequest<QueryHouseSourceBean> request = new SimpleRequest<QueryHouseSourceBean>(
				API.QUERY_HOUSE_SOURCE, QueryHouseSourceBean.class,
				new Listener<QueryHouseSourceBean>() {
					@Override
					public void onResponse(QueryHouseSourceBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								Result result = response.getResult();
								CurrActUser currActUser = result
										.getCurrActUser();
								if (null != currActUser) {
									userHeadUrl = currActUser.getUserHeadUrl();
									userName = currActUser.getUserName();
									userPrice = currActUser.getUserPrice();
									isCurUserNullInfo = false;
								} else {
									isCurUserNullInfo = true;
								}

								// 产品说只有一张图，后台给的数组，暂时先取第一条数据
								List<Imgs> list = new ArrayList<QueryHouseSourceBean.Result.Imgs>();
								list = result.getImgs();
								if (0 == list.size()) {
									img_mainImg_biddingHouseInfo
											.setBackgroundResource(R.drawable.loading_750_438);
								} else {
									imageLoader.displayImage(list.get(0)
											.getImgUrl(),
											img_mainImg_biddingHouseInfo,
											options);
								}
								price = StringUtils.intMoney(result.getPrice());// 市场价
								tv_currentPrice_biddingHouseInfo
										.setText(biddingPrice + "");
								tv_formerPrice_biddingHouseInfo.setText(price
										+ "元/㎡");
								tv_houseType_biddingHouseInfo.setText(result
										.getHouseTypeName());
								String size = result.getSize();// 建筑面积
								tv_acreagee_biddingHouseInfo
										.setText(size + "㎡");
								tv_buildingNum_biddingHouseInfo.setText(result
										.getBuildingNum());
								tv_unitNum_biddingHouseInfo.setText(result
										.getBuildingUnit());
								tv_floor_biddingHouseInfo.setText(result
										.getFloor());
								tv_residenceType_biddingHouseInfo
										.setText(result.getHouseKind() + "");
								tv_name_biddingHouseInfo.setText(result
										.getBuildingName());
								tv_address_biddingHouseInfo.setText(result
										.getAddress());
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
						showTost(getResources().getString(R.string.net_error));
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_backLayout_biddingHouseInfo:
			finish();
			break;
		case R.id.tv_getReward_biddingHouseInfos:
			//state = 1;
			//userStatus = "2";
			// 缴纳——竞拍按钮
			if (!UserInfoData.isSingIn()) {
				goOthers(LoginActivity.class);
			}
			// 测试时 点击弹出出价窗口 有数据后需要判断是否 缴纳保证金
			if (0 == state) {
				// 活动开始前
				if ("1".equals(userStatus)) {
					// 跳转缴纳保证金页面流程
					showConfirmDialog();
				} else if ("2".equals(userStatus) || "3".equals(userStatus)) {
					showTost("活动还未开始");
				}
			} else if (1 == state) {
				// 活动进行中
				if ("1".equals(userStatus)) {
					// 跳转缴纳保证金页面流程
					showConfirmDialog();
				} else if ("2".equals(userStatus) || "3".equals(userStatus)) {
					// 已报名，有出价的机会
					if (counts == 0) {
						showTost("出价机会已用完");
					} else {
						showBiddingWindow();
					}
				}
				// else if("3".equals(userStatus)){
				// //没机会出价了，不做处理
				// showTost("你没有出价机会了");
				// }
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @Title: showConfirmDialog
	 * @Description: 确认缴纳保证金的弹出框
	 * @return: void
	 */
	private void showConfirmDialog() {
		CustomDialog.Builder builder = new Builder(this);
		builder.setTitle("竞拍保证金");
		builder.setContent("缴纳竞拍保证金后您会获得3次出价机会，竞拍不成功，保证金会在1个工作日退回原卡。");
		builder.setNegativeButton("取消", new CustomDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("立即缴纳 ", new CustomDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(BiddingHouseInfoActivity.this,
						BiddingAgreementActivity.class);
				intent.putExtra("activityPoolId", activityPoolId);
				intent.putExtra("activityId", activityId);
				intent.putExtra("buildingId", buildingId);
				startActivity(intent);
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 点击出价 弹出竞拍出价弹窗
	private void showBiddingWindow() {
		
		LayoutInflater layoutInflater = LayoutInflater
				.from(BiddingHouseInfoActivity.this);
		popView = layoutInflater.inflate(R.layout.popupwindow_bidding, null);		
		
		if (null == popupWindow) {
			popupWindow = new PopupWindow(popView,
					WindowManager.LayoutParams.FILL_PARENT,
					WindowManager.LayoutParams.WRAP_CONTENT, false);
		} else {
			popupWindow = null;
			popupWindow = new PopupWindow(popView,
					WindowManager.LayoutParams.FILL_PARENT,
					WindowManager.LayoutParams.WRAP_CONTENT, false);
		}
		popupWindow.setFocusable(true); // 弹出框获取焦点
		popupWindow
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindow.showAtLocation(
				findViewById(R.id.layout_mainLayout_biddingHouseInfo),
				Gravity.BOTTOM, 0, 0);
		
		RelativeLayout layout_closeLayout_popupWindow_bidding = (RelativeLayout) popView
				.findViewById(R.id.layout_closeLayout_popupWindow_bidding);
		layout_closeLayout_popupWindow_bidding
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popupWindow.dismiss();
					}
				});

		layout_confirmLayout_popupWindow_bidding = (RelativeLayout) popView
				.findViewById(R.id.layout_confirmLayout_popupWindow_bidding);
		tv_confirmHint_popupWindow_bidding = (TextView) popView
				.findViewById(R.id.tv_confirmHint_popupWindow_bidding);

		RelativeLayout layout_addMoneyLayout_popupWindow_bidding = (RelativeLayout) popView
				.findViewById(R.id.layout_addMoneyLayout_popupWindow_bidding);
		RelativeLayout layout_subtractMoneyLayout_popupWindow_bidding = (RelativeLayout) popView
				.findViewById(R.id.layout_subtractMoneyLayout_popupWindow_bidding);

		tv_biddingMoney = (EditText) popView
				.findViewById(R.id.tv_biddingMoney_popupWindow_bidding);
		TextView tv_startPrice_popupWindow_bidding = (TextView) popView
				.findViewById(R.id.tv_startPrice_popupWindow_bidding);
		tv_bidderName_popupWindow_bidding = (TextView) popView
				.findViewById(R.id.tv_bidderName_popupWindow_bidding);
		tv_bidderPrice_popupWindow_bidding = (TextView) popView
				.findViewById(R.id.tv_bidderPrice_popupWindow_bidding);

		// 剩余出价次数
		tv_remind_times = (TextView) popView.findViewById(R.id.tv_remind_times);
		if (0 == counts) {
			tv_remind_times.setVisibility(View.GONE);
		} else {
			tv_remind_times.setVisibility(View.VISIBLE);
			tv_remind_times.setText("剩余" + counts + "次出价机会");
		}
		if (0 == counts) {
			layout_confirmLayout_popupWindow_bidding.setBackgroundColor(Color
					.parseColor("#c4c4c4"));
			tv_confirmHint_popupWindow_bidding.setText("出价机会已用完");
		}
		// 拍手头像
		img_bidderImg_popupWindow_bidding = (ImageView) popView
				.findViewById(R.id.img_bidderImg_popupWindow_bidding);
		// 竞拍价
		tv_startPrice_popupWindow_bidding.setText("¥ " + minPrice + "元/㎡");
		// 倒计时
		TimeDownView timedownview_popupWindow_biddingDetail = (TimeDownView) popView
				.findViewById(R.id.timedownview_popupWindow_biddingDetail);
		int[] times = new int[4];
		long currentTime = DateUtils.currentTime();
		times = JudgeAcctivityStateUtil.downTimeFormat(endTime - currentTime);
		timedownview_popupWindow_biddingDetail.setTimes(times);
		timedownview_popupWindow_biddingDetail.run();
		// 当前拍手信息
		if (isCurUserNullInfo) {
			img_bidderImg_popupWindow_bidding.setVisibility(View.VISIBLE);
			tv_bidderPrice_popupWindow_bidding.setVisibility(View.VISIBLE);
			tv_bidderName_popupWindow_bidding.setVisibility(View.VISIBLE);
			imageLoader.displayImage(userHeadUrl,
					img_bidderImg_popupWindow_bidding, options);
			tv_bidderName_popupWindow_bidding.setText(userName);
			tv_bidderPrice_popupWindow_bidding.setText("¥ " + userPrice);
		} else {
			img_bidderImg_popupWindow_bidding.setVisibility(View.GONE);
			tv_bidderPrice_popupWindow_bidding.setVisibility(View.GONE);
			tv_bidderName_popupWindow_bidding.setVisibility(View.VISIBLE);
			tv_bidderName_popupWindow_bidding.setText("暂无出价用户");
			tv_bidderName_popupWindow_bidding.setTextColor(Color
					.parseColor("#999999"));
		}
		tv_biddingMoney.setText(minPrice + "");

		// 点击减少按钮
		layout_subtractMoneyLayout_popupWindow_bidding
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String lowPrice = "0";
						if(TextUtils.isEmpty(tv_biddingMoney.getText().toString()
								.trim())){
							lowPrice = "0";
						}else{
							lowPrice = tv_biddingMoney.getText().toString().trim();
						}
						
						if (Integer.parseInt(lowPrice) <= minPrice) {
							showTost("您的出价低于起拍价，请重新出价");
							return;
						}
						sendPrice = sendPrice - 100;
						if (sendPrice <= 0) {
							sendPrice = 0;
						}
						tv_biddingMoney.setText(sendPrice + "");
					}
				});
		// 点击增加按钮
		layout_addMoneyLayout_popupWindow_bidding
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String higtPrice = "0";
						if(TextUtils.isEmpty(tv_biddingMoney.getText().toString().trim())){
							higtPrice = "0";
						}else{
							higtPrice = tv_biddingMoney.getText().toString().trim();
						}
//						try {
//							if (Integer.parseInt(higtPrice) >= (int) Double
//									.parseDouble(price)) {
//								showTost("您的出价高于楼盘市场价，请重新出价");
//								return;
//							}
//						} catch (Exception e) {
//							showTost("数据错误");
//						}

						sendPrice = sendPrice + 100;
						tv_biddingMoney.setText(sendPrice + "");
					}
				});

		// 点击出价
		layout_confirmLayout_popupWindow_bidding
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (0 == counts) {
							layout_confirmLayout_popupWindow_bidding
									.setBackgroundColor(Color
											.parseColor("#c4c4c4"));
							tv_confirmHint_popupWindow_bidding
									.setText("出价机会已用完");
							return;
						}
						try {
//							if (sendPrice >= (int) Double
//									.parseDouble(price)) {
//								showTost("您的出价高于楼盘市场价，请重新出价");
//								return;
//							}
							if (sendPrice <= minPrice) {
								showTost("您的出价低于起拍价，请重新出价");
								return;
							}
						} catch (Exception e) {
							showTost("数据错误");
						}
						submitBiddingPrice();
					}
				});
		//编辑框的监听
		tv_biddingMoney.addTextChangedListener(watcher);
		
	}
	
	private TextWatcher watcher = new TextWatcher() {
		
		private String temp;
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			temp = s.toString();
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			//showTost(temp+"");
			if(TextUtils.isEmpty(temp)){
				sendPrice = 0;
				//tv_biddingMoney.setText(0+"");
			}
			if(!TextUtils.isEmpty(temp)){
				try {
					sendPrice = Integer.parseInt(temp);
				} catch (Exception e) {
				}
			}
		}
	};

	/**
	 * 
	 * @Title: submitBiddingPrice
	 * @Description: 提交竞拍价格
	 * @return: void
	 */
	private void submitBiddingPrice() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityId", activityId);
		map.put("price", sendPrice + "");
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		SimpleRequest<SaveUserBidding> request = new SimpleRequest<SaveUserBidding>(
				API.SAVE_USER_BIDDING, SaveUserBidding.class,
				new Listener<SaveUserBidding>() {
					@Override
					public void onResponse(SaveUserBidding response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								loadingDialog.dismissDialog();
								// 成功了以后，再将出价改回去
								//sendPrice = minPrice;
								com.flzc.fanglian.model.SaveUserBidding.Result result = response
										.getResult();
								imageLoader.displayImage(result.getHeadUrl(),
										img_bidderImg_popupWindow_bidding,
										options);
								tv_bidderName_popupWindow_bidding
										.setText(result.getUserName());
								tv_remind_times.setVisibility(View.VISIBLE);
								tv_bidderPrice_popupWindow_bidding.setText("¥ "
										+ result.getPrice());
								counts = 3 - result.getBiddingCount();
								tv_remind_times
										.setText("剩余" + counts + "次出价机会");
								if (0 == counts) {
									layout_confirmLayout_popupWindow_bidding
											.setBackgroundColor(Color
													.parseColor("#c4c4c4"));
									tv_confirmHint_popupWindow_bidding
											.setText("出价机会已用完");
								}
								showBiddingSuccessWindow(counts);
							} else {
								showTost(response.getMsg());
							}
						}
						if(null != loadingDialog){
							loadingDialog.dismissDialog();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						showTost(getResources().getString(R.string.net_error));
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * 
	 * @Title: showBiddingSuccessWindow
	 * @Description: 出价成功的popwindow
	 * @param bidingCounts
	 * @return: void
	 */
	private void showBiddingSuccessWindow(int bidingCounts) {

		LayoutInflater layoutInflater = LayoutInflater
				.from(BiddingHouseInfoActivity.this);
		View successView = layoutInflater.inflate(
				R.layout.popupwindow_bidding_success, null);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		if (null == successWindow) {
			successWindow = new PopupWindow(successView, width, height);
		} else {
			successWindow = null;
			successWindow = new PopupWindow(successView, width, height);
		}
		successWindow.setFocusable(false); // 弹出框获取焦点
		successWindow.showAtLocation(
				findViewById(R.id.layout_mainLayout_biddingHouseInfo),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

		TextView tv_contentHint_answer_wrong = (TextView) successView
				.findViewById(R.id.tv_contentHint_biddingSuccess);
		tv_contentHint_answer_wrong.setText("亲，你已经出价成功，你还有" + bidingCounts
				+ "次出价机会！");

		RelativeLayout rl_backLayout_biddingSuccess = (RelativeLayout) successView
				.findViewById(R.id.rl_backLayout_biddingSuccess);
		rl_backLayout_biddingSuccess.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				successWindow.dismiss();
			}
		});
	}

	@Override
	protected void onPause() {
		time_view_downtime.stop();
		super.onPause();
	}

	@Override
	protected void onStop() {
		time_view_downtime.stop();
		super.onStop();
	}
}
