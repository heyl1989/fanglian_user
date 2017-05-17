package com.flzc.fanglian.ui.everyday_special_activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.flzc.fanglian.model.PaySucessBean;
import com.flzc.fanglian.model.PaySucessBean.Result;

/**
 * 
 * @ClassName: PaymentSuccessActivity
 * @Description: 支付成功页面
 * @author: 薛东超
 * @date: 2016年3月9日 下午3:32:44
 */
public class SpecialPaymentSuccessActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private TextView iv_paymentSuccess_auctionNow;
	private String orderCode;
	private TextView buildName;
	protected int actStatus;
	protected String buildingId;
	protected String activityPoolId;
	protected String activityId;
	private TextView goBack;
	protected String houseSourceId;
	protected long startTime;
	protected long endTime;
	protected String biddingPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specialpayment_success);
		orderCode = getIntent().getExtras().getString("orderCode", "");
		initView();
		initData();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("支付成功");
		// 内容
		buildName = (TextView) findViewById(R.id.tv_paymentsuccess_build);
		// 立即抢购
		iv_paymentSuccess_auctionNow = (TextView) findViewById(R.id.tv_paymentSuccess_auctionNow);
		iv_paymentSuccess_auctionNow.setVisibility(View.GONE);
		rl_back.setOnClickListener(this);
		iv_paymentSuccess_auctionNow.setOnClickListener(this);
		// 返回
		goBack = (TextView) findViewById(R.id.tv_go_back);
		goBack.setOnClickListener(this);
	}

	/**
	 * 
	 * @Title: initData
	 * @Description: 请求数据
	 * @return: void
	 */
	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderCode", orderCode);
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		SimpleRequest<PaySucessBean> request = new SimpleRequest<PaySucessBean>(
				API.PAYSUCCESS, PaySucessBean.class,
				new Listener<PaySucessBean>() {
					@Override
					public void onResponse(PaySucessBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								Result result = response.getResult();
								// "actStatus": 1,//1为未开始、2为进行中、3为已结束
								actStatus = result.getActStatus();
								if (actStatus == 1) {
									buildName.setText("已缴纳 ["
											+ result.getBuildingName()
											+ "]抢购保证金！\n活动开始前5分钟您将收到消息提醒！");
									iv_paymentSuccess_auctionNow
											.setVisibility(View.GONE);
									goBack.setVisibility(View.VISIBLE);
								}
								if (actStatus == 2 || actStatus == 3) {
									iv_paymentSuccess_auctionNow
											.setVisibility(View.VISIBLE);
									buildName.setText("已缴纳 ["
											+ result.getBuildingName()
											+ "]抢购保证金！\n您获得抢购特价房源机会，赶紧去抢吧！");
								}
								buildingId = result.getBuildingId() + "";
								activityPoolId = result.getActivityPoolId();
								activityId = result.getActivityId() + "";
								houseSourceId = result.getHouseSourceId();
								startTime = Long.parseLong(result.getStartTime());
								endTime = Long.parseLong(result.getEndTime());
								biddingPrice = result.getBiddingPrice();
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
						showTost(getResources().getString(R.string.net_error));
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 返回
		case R.id.rl_back:
			finish();
			break;
		// 立即竞拍
		case R.id.tv_paymentSuccess_auctionNow:
			Intent intent = new Intent(this,SpecialHouseInfoActivity.class);
			intent.putExtra("houseSourceId", houseSourceId);
			intent.putExtra("activityPoolId",activityPoolId);
			intent.putExtra("buildingId", buildingId);
			intent.putExtra("activityId", activityId);
			intent.putExtra("activityPrice", biddingPrice);
			intent.putExtra("startTime", startTime);
			intent.putExtra("endTime", endTime);
			startActivity(intent);
			ScaleBuyingCashActivity.scaleBuyingCashActivity.finish();
			SpecialAgreeMentWebViewActivity.specialAgreeMentWebViewActivity.finish();
			finish();
			break;
		case R.id.tv_go_back:
			Intent specialInfoIntent = new Intent(this,
					EveryDaySpecialActivity.class);
			specialInfoIntent.putExtra("activityPoolId", activityPoolId);
			specialInfoIntent.putExtra("buildingId", buildingId);
			specialInfoIntent.putExtra("activityId", activityId);
			startActivity(specialInfoIntent);
			SpecialAgreeMentWebViewActivity.specialAgreeMentWebViewActivity.finish();
			ScaleBuyingCashActivity.scaleBuyingCashActivity.finish();
			EveryDaySpecialActivity.everyDaySpecialActivity.finish();
			finish();
			break;
		default:
			break;
		}
	}

}
