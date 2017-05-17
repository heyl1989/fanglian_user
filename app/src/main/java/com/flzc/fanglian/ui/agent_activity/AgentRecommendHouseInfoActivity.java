package com.flzc.fanglian.ui.agent_activity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
import com.flzc.fanglian.model.AgentRecommendHouseInfoBean;
import com.flzc.fanglian.model.AgentRecommendHouseInfoBean.Result;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.dialog.CustomDialog;
import com.flzc.fanglian.view.dialog.CustomDialog.Builder;

/**
 * 
 * @ClassName: AgentRecommendHouseInfoActivity
 * @Description: 经纪人推荐活动 房源详情页
 * @author: Tien.
 * @date: 2016年3月5日 下午4:26:45
 */
public class AgentRecommendHouseInfoActivity extends BaseActivity implements
		OnClickListener {
	private RelativeLayout rl_backLayout_recommendHouseInfo;
	private String ticketAmount;
	private String buildingId;
	private String activityPoolId;
	private String tokenId;
	private String houseSouseId;
	private ImageView mMaster;
	private TextView mPrice;
	private TextView mDiscountQuan;
	private TextView mhouseType;
	private TextView mhouseSize;
	private TextView mbuildNum;
	private TextView munitNum;
	private TextView mfloor;
	private TextView mhouseKind;
	private TextView mbuildName;
	private TextView mbuildAdress;
	private ImageView img_getReward_recommendHouseInfo;
	private String houseStatus;
	private DecimalFormat df;
	private TextView marketPrice;
	private TextView discountPrice;
	private TextView marketTotal;
	private TextView discountTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agent_recommend_houseinfo);
		ticketAmount = getIntent().getExtras().getString("ticketAmount", "");
		houseSouseId = getIntent().getExtras().getString("houseSouseID", "");
		buildingId = getIntent().getExtras().getString("buildingId", "");
		activityPoolId = getIntent().getExtras()
				.getString("activityPoolId", "");
		tokenId = UserInfoData.getData(Constant.TOKEN, "");
		df = new DecimalFormat("###.00");  
		initItem();
		//initData();
	}
	
	@Override
	protected void onResume() {
		initData();
		super.onResume();
	}


	private void initItem() {
		// 标题栏
		rl_backLayout_recommendHouseInfo = (RelativeLayout) findViewById(R.id.rl_backLayout_recommendHouseInfo);
		rl_backLayout_recommendHouseInfo.bringToFront();
		rl_backLayout_recommendHouseInfo.setOnClickListener(this);
		// 主图
		mMaster = (ImageView) findViewById(R.id.img_mainImg_recommendHouseInfo);
		mPrice = (TextView) findViewById(R.id.tv_referPrice_recommendHouseInfo);
		mDiscountQuan = (TextView) findViewById(R.id.tv_discount_recommendHouseInfo);
		mDiscountQuan.setText(ticketAmount + "");
		//价格
		marketPrice = (TextView) findViewById(R.id.tv_marketprice_recommendHouseInfo);
		marketTotal = (TextView) findViewById(R.id.tv_markettotal_recommendHouseInfo);
		discountPrice = (TextView) findViewById(R.id.tv_discountprice_recommendHouseInfo);
		discountTotal = (TextView) findViewById(R.id.tv_discounttotal_recommendHouseInfo);
		// 户型
		mhouseType = (TextView) findViewById(R.id.tv_houseType_recommendHouseInfo);
		mhouseSize = (TextView) findViewById(R.id.tv_houseSize_recommendHouseInfo);
		mbuildNum = (TextView) findViewById(R.id.tv_buildingNum_recommendHouseInfo);
		munitNum = (TextView) findViewById(R.id.tv_unitNum_recommendHouseInfo);
		mfloor = (TextView) findViewById(R.id.tv_floor_recommendHouseInfo);
		mhouseKind = (TextView) findViewById(R.id.tv_residenceType_recommendHouseInfo);
		mbuildName = (TextView) findViewById(R.id.tv_buildname);
		mbuildAdress = (TextView) findViewById(R.id.tv_adress);
		// 领取优惠券
		img_getReward_recommendHouseInfo = (ImageView) findViewById(R.id.img_getReward_recommendHouseInfo);
		img_getReward_recommendHouseInfo.setEnabled(false);
		img_getReward_recommendHouseInfo.setOnClickListener(this);
	}

	/**
	 * 
	 * @Title: initData
	 * @Description: 请求房源数据信息
	 * @return: void
	 */
	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("houseSourceId", houseSouseId);
		map.put("tokenId", tokenId);
		map.put("activityPoolId", activityPoolId);
		SimpleRequest<AgentRecommendHouseInfoBean> req = new SimpleRequest<AgentRecommendHouseInfoBean>(
				API.QUERY_HOUSE_SOURCE, AgentRecommendHouseInfoBean.class,
				new Listener<AgentRecommendHouseInfoBean>() {
					@Override
					public void onResponse(AgentRecommendHouseInfoBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								if (response.getResult().getImgs().size() > 0) {
									imageLoader.displayImage(response
											.getResult().getImgs().get(0)
											.getImgUrl(), mMaster, options);
								}
								Result result = response.getResult();
								//价格
								marketPrice.setText(StringUtils.intMoney(result.getReferPrice())+"元/㎡");
								marketTotal.setText(StringUtils.intMoney(result.getReferTotalPrice())+"万");
								discountPrice.setText(StringUtils.intMoney(result.getPrice())+"元/㎡");
								discountTotal.setText(StringUtils.intMoney(result.getTotalPrice())+"万");
								String size = result.getSize();
								String price = StringUtils.intMoney(result.getPrice());
								try {
									double dbSize = Double.parseDouble(size);
									double dbPrice = Double.parseDouble(price);
									String totalPrice = df.format(dbSize*dbPrice/10000)+"";
								} catch (Exception e) {
									showTost("数据错误");
								}
								mPrice.setText(StringUtils.intMoney(result.getTotalPrice()) + "万");
								mhouseType.setText(result.getHouseTypeName());
								mhouseSize.setText(result.getSize()+"㎡");
								mbuildNum.setText(result.getBuildingNum());
								munitNum.setText(result.getBuildingUnit());
								mfloor.setText(result.getFloor());
								mhouseKind.setText(result.getHouseKind());
								mbuildName.setText(result.getBuildingName());
								mbuildAdress.setText(result.getAddress());
								houseStatus = result.getHouseStatus();
								bottomButtonState();
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}
	/**
	 * 
	 * @Title: bottomButtonState 
	 * @Description: 底部按钮状态
	 * @return: void
	 */
	protected void bottomButtonState() {
		if(Integer.parseInt(houseStatus) == 1){
			img_getReward_recommendHouseInfo.setImageResource(R.drawable.get_reward_no);
		}else{
			img_getReward_recommendHouseInfo.setEnabled(true);
		}
		img_getReward_recommendHouseInfo.setVisibility(View.VISIBLE);
		if(null != getIntent().getExtras()){
			String activityState = getIntent().getExtras().getString("activityState", "");
			if(TextUtils.equals("1",activityState)){
				//活动进行中
				if(UserInfoData.isSingIn()){
					if(Integer.parseInt(houseStatus) == 1){
						img_getReward_recommendHouseInfo.setImageResource(R.drawable.get_reward_no);
						img_getReward_recommendHouseInfo.setVisibility(View.VISIBLE);
					}else{
						img_getReward_recommendHouseInfo.setVisibility(View.VISIBLE);
						img_getReward_recommendHouseInfo.setEnabled(true);
					}
				}else{
					img_getReward_recommendHouseInfo.setVisibility(View.VISIBLE);
					img_getReward_recommendHouseInfo.setEnabled(true);
				}
			}
			if(TextUtils.equals("2",activityState)){
				//活动结束
				if(UserInfoData.isSingIn()){
					if(Integer.parseInt(houseStatus) == 1){
						img_getReward_recommendHouseInfo.setImageResource(R.drawable.get_reward_no);
						img_getReward_recommendHouseInfo.setVisibility(View.VISIBLE);
					}else{
						img_getReward_recommendHouseInfo.setVisibility(View.GONE);
					}
				}else{
					img_getReward_recommendHouseInfo.setVisibility(View.GONE);
				}
			}
		}
		if(null != getIntent().getExtras()){
			String from = getIntent().getExtras().getString("from", "");
			if(TextUtils.equals("fanglianquan",from)){
				img_getReward_recommendHouseInfo.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_backLayout_recommendHouseInfo:
			finish();
			break;
		case R.id.img_getReward_recommendHouseInfo:
			//领取优惠券
			if(UserInfoData.isSingIn()){
				if(Integer.parseInt(houseStatus) == 1){
					showTost("已被抢");
				}
				if(Integer.parseInt(houseStatus) == 0){
					showConfirmGetDialog();
				}
			}else{
				goOthers(LoginActivity.class);
			}
			break;

		default:
			break;
		}

	}

	/**
	 * 
	 * @Title: showConfirmGetDialog
	 * @Description: 是否确认领取房链券
	 * @return: void
	 */
	private void showConfirmGetDialog() {
		Builder builder = new CustomDialog.Builder(this);
		builder.setTitle("领取房链券");
		builder.setContent("确认领取房链券？");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveFLQuan();
				dialog.dismiss();
			}

		});
		builder.create().show();
	}

	/**
	 * 
	 * @Title: saveFLQuan
	 * @Description: 保存房链券到云
	 * @return: void
	 */
	protected void saveFLQuan() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("sourceId", houseSouseId);
		map.put("tokenId", tokenId);
		map.put("activityPoolId", activityPoolId);
		SimpleRequest<AgentRecommendHouseInfoBean> req = new SimpleRequest<AgentRecommendHouseInfoBean>(
				API.RECEIVE_COUPON, AgentRecommendHouseInfoBean.class,
				new Listener<AgentRecommendHouseInfoBean>() {
					@Override
					public void onResponse(AgentRecommendHouseInfoBean response) {
						if (null != response.getMsg()) {
							if (response.getStatus() == 0) {
								Intent intent = new Intent(
										AgentRecommendHouseInfoActivity.this,
										GetDiscountSuccessActivity.class);
								intent.putExtra("ticketAmount", ticketAmount);
								intent.putExtra("houseSouseID", houseSouseId);
								intent.putExtra("buildingId", buildingId);
								intent.putExtra("activityPoolId",
										activityPoolId);
								startActivity(intent);
							} else {
								showTost(response.getMsg());
							}

						}
						loadingDialog.dismissDialog();

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

}
