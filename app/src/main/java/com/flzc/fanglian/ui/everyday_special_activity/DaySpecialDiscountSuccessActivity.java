package com.flzc.fanglian.ui.everyday_special_activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.AgentRecommendHouseInfoBean;
import com.flzc.fanglian.model.AgentRecommendHouseInfoBean.Result;
import com.flzc.fanglian.ui.me.FangLianQuanActivity;

/**
 * 
 * @ClassName: GetDiscountSuccessActivity
 * @Description: 优惠券领取成功页面
 * @author: 薛东超
 * @date: 2016年3月9日 下午3:23:11
 */
public class DaySpecialDiscountSuccessActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title, tv_right;
	private ImageView iv_checkFangLQ_getDiscountSuccess;
	private String ticketAmount;
	private String houseSouseId;
	private String buildingId;
	private String activityPoolId;
	private String tokenId;
	private TextView mQuan;
	private TextView mbuildName;
	private TextView mbuildAdress;
	private TextView mhouseSize;
	private TextView mbuildNum;
	private TextView munitNum;
	private TextView mhouseKind;
	private TextView mfloor;
	private TextView mhouseType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_everyday_discount_success);
		ticketAmount = getIntent().getExtras().getString("ticketAmount", "");
		houseSouseId = getIntent().getExtras().getString("houseSouseID", "");
		buildingId = getIntent().getExtras().getString("buildingId", "");
		activityPoolId = getIntent().getExtras()
				.getString("activityPoolId", "");
		tokenId = UserInfoData.getData(Constant.TOKEN, "");
		initView();
		initData();
	}

	private void initView() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_right = (TextView) findViewById(R.id.tv_right);

		tv_title.setText("优惠券领取成功");
		tv_right.setText("");
		// 券的大小
		mQuan = (TextView) findViewById(R.id.tv_getDiscountSuccess_money);
		mQuan.setText("恭喜您！成功获得以活动价" + ticketAmount + "万元购买特价房的资格");
		// 楼盘信息
		mbuildName = (TextView) findViewById(R.id.tv_buildname);
		mbuildAdress = (TextView) findViewById(R.id.tv_buildadress);
		mhouseType = (TextView) findViewById(R.id.tv_houseType_getDiscountSuccess);
		mhouseSize = (TextView) findViewById(R.id.tv_acreagee_getDiscountSuccess);
		mbuildNum = (TextView) findViewById(R.id.tv_buildingNum_getDiscountSuccess);
		munitNum = (TextView) findViewById(R.id.tv_unitNum_getDiscountSuccess);
		mfloor = (TextView) findViewById(R.id.tv_floor_getDiscountSuccess);
		mhouseKind = (TextView) findViewById(R.id.tv_residenceType_getDiscountSuccess);

		iv_checkFangLQ_getDiscountSuccess = (ImageView) findViewById(R.id.iv_checkFangLQ_getDiscountSuccess);

		iv_checkFangLQ_getDiscountSuccess.setOnClickListener(this);
		rl_back.setOnClickListener(this);
	}

	/**
	 * 
	 * @Title: initData
	 * @Description: 请求房链券详情
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
								Result result = response.getResult();
								mhouseType.setText(result.getHouseTypeName());
								mhouseSize.setText(result.getSize()+"㎡");
								mbuildNum.setText(result.getBuildingNum());
								munitNum.setText(result.getBuildingUnit());
								mfloor.setText(result.getFloor());
								mhouseKind.setText(result.getHouseKind());
								mbuildName.setText(result.getBuildingName());
								mbuildAdress.setText(result.getAddress());
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 返回
		case R.id.rl_back:
			finish();
			break;
		// 跳转到房链券
		case R.id.iv_checkFangLQ_getDiscountSuccess:
			goOthers(FangLianQuanActivity.class);
			break;
		default:
			break;
		}
	}

}
