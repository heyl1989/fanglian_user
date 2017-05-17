package com.flzc.fanglian.ui.bidding_activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class PaymentSuccessActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private ImageView iv_paymentSuccess_auctionNow;
	private String orderCode;
	private TextView buildName;
	private LinearLayout biddingCounts;
	private int actStatus;
	private String buildingId;
	private String activityPoolId;
	private String houseSourceId;
	private String userStatus;
	private int state;
	private long startTime;
	private String minPrice;
	private String activityId;
	private String biddingPrice;
	private long endTime;
	private int counts;
	private View goBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_success);
		orderCode = getIntent().getExtras().getString("orderCode", "");
		initView();
		initData();
	}

	private void initView() {
		//标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("支付成功");
		//信息
		buildName = (TextView) findViewById(R.id.tv_paymentSuccess_housePostion);
		biddingCounts = (LinearLayout)findViewById(R.id.ll_bidding_counts);
		//立即竞拍
		iv_paymentSuccess_auctionNow = (ImageView) findViewById(R.id.iv_paymentSuccess_auctionNow);
		biddingCounts.setVisibility(View.GONE);
		iv_paymentSuccess_auctionNow.setVisibility(View.GONE);
		rl_back.setOnClickListener(this);
		iv_paymentSuccess_auctionNow.setOnClickListener(this);
		//返回
		goBack = (TextView)findViewById(R.id.tv_go_back);
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
								if(actStatus == 1){
									buildName.setText("已缴纳 ["+result.getBuildingName()+"]竞拍保证金！\n活动开始前5分钟您将收到消息提醒！");
									iv_paymentSuccess_auctionNow.setVisibility(View.GONE);
									biddingCounts.setVisibility(View.GONE);
									goBack.setVisibility(View.VISIBLE);
								}
								if(actStatus == 2){
									iv_paymentSuccess_auctionNow.setVisibility(View.VISIBLE);
									biddingCounts.setVisibility(View.VISIBLE);
									buildName.setText("已缴纳 ["+result.getBuildingName()+"]竞拍保证金！");
								}
								buildingId = result.getBuildingId()+"";
								activityPoolId = result.getActivityPoolId();
								houseSourceId = result.getHouseSourceId();
								userStatus = result.getUserStatus()+"";
								state = result.getActStatus();
								startTime = Long.parseLong(result.getStartTime());
								endTime = Long.parseLong(result.getEndTime());
								minPrice = result.getMinPrice()+"";
								activityId = result.getActivityId()+"";
								counts = result.getCounts();
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
		// 立即抢购
		case R.id.iv_paymentSuccess_auctionNow:
			Intent intent = new Intent(this,BiddingHouseInfoActivity.class);
			intent.putExtra("buildingId", buildingId);
			intent.putExtra("activityPoolId",activityPoolId);
			intent.putExtra("houseSourceId", houseSourceId);
			intent.putExtra("userStatus", userStatus);
			intent.putExtra("state", state-1);
			intent.putExtra("startTime", startTime);
			intent.putExtra("endTime", endTime);
			intent.putExtra("minPrice", minPrice);
			intent.putExtra("activityId", activityId);
			intent.putExtra("counts", counts);
			intent.putExtra("biddingPrice", minPrice);
			AuctionBailActivity.auctionBailActivity.finish();
			BiddingAgreementActivity.biddingAgreementActivity.finish();
			startActivity(intent);
			finish();
			break;
		case R.id.tv_go_back:
			Intent biddingInfoIntent = new Intent(this,BiddingActivity.class);
			biddingInfoIntent.putExtra("activityPoolId",activityPoolId);
			biddingInfoIntent.putExtra("buildingId", buildingId);
			biddingInfoIntent.putExtra("activityId", activityId);
			startActivity(biddingInfoIntent);
			AuctionBailActivity.auctionBailActivity.finish();
			BiddingAgreementActivity.biddingAgreementActivity.finish();
			BiddingActivity.biddingActivity.finish();
			finish();
			break;
		default:
			break;
		}
	}

}
