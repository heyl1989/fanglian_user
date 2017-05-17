package com.flzc.fanglian.ui.everyday_special_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.flzc.fanglian.db.DaySpecialTempParameter;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.DaySpecialHouseInfoBean;
import com.flzc.fanglian.model.DaySpecialHouseInfoBean.Result;
import com.flzc.fanglian.model.DaySpecialHouseInfoBean.Result.Imgs;
import com.flzc.fanglian.model.SeckillBean;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.JudgeAcctivityStateUtil;
import com.flzc.fanglian.util.LogUtil;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.TimeViewWhiteWord;
import com.flzc.fanglian.view.dialog.CustomDialog;
import com.flzc.fanglian.view.dialog.CustomDialog.Builder;

/**
 * 
 * @ClassName: SpecialHouseInfoActivity
 * @Description: 天天特价活动房源信息
 * @author: LU
 * @date: 2016-3-7 下午8:00:38
 */
public class SpecialHouseInfoActivity extends BaseActivity implements
		OnClickListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private TextView tv_right;
	private String houseSourceId;
	private String activityPoolId;
	private String buildingId;
	private String tokenId;
	private ImageView masterImg;
	private TextView atyPrice;
	private TextView marketPrice;
	private TextView buildName;
	private TextView buildAdress;
	private TextView houseType;
	private TextView houseSize;
	private TextView buildingNum;
	private TextView unitNum;
	private TextView houseFloor;
	private TextView buildKind;
	private long startTime;
	private long endTime;
	private TextView tv_scare;
	private int atyState;
	private LinearLayout ll_timedown;
	private TextView tv_hadscare;
	private TimeViewWhiteWord tivm_time;
	protected String payStatus;
	private String dmarketPrice;
	private String activityId;
	protected String houseStatus;
	private String activityPrice;
	public static SpecialHouseInfoActivity specialHouseInfoActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_everyday_special_info);
		specialHouseInfoActivity = this;
		LogUtil.e("Intent", getIntent().getExtras()+"");
		if(null != getIntent().getExtras()){
			houseSourceId = getIntent().getExtras().getString("houseSourceId", "");
			DaySpecialTempParameter.saveData(Constant.HS_ID, houseSourceId);
			activityPoolId = getIntent().getExtras().getString("activityPoolId", "");
			DaySpecialTempParameter.saveData(Constant.ATY_ID, activityPoolId);
			buildingId = getIntent().getExtras().getString("buildingId", "");
			DaySpecialTempParameter.saveData(Constant.BD_ID, buildingId);
			activityPrice = getIntent().getExtras().getString("activityPrice", "0");
			DaySpecialTempParameter.saveData(Constant.A_PRICE, activityPrice);
			activityId = getIntent().getExtras().getString("activityId", "");
			DaySpecialTempParameter.saveData(Constant.A_ID, activityId);
			startTime = getIntent().getExtras().getLong("startTime");
			DaySpecialTempParameter.saveData(Constant.ST, startTime);
			endTime = getIntent().getExtras().getLong("endTime");
			DaySpecialTempParameter.saveData(Constant.ET, endTime);
		}else{
			houseSourceId = DaySpecialTempParameter.getData(Constant.HS_ID, "");
			activityPoolId = DaySpecialTempParameter.getData(Constant.ATY_ID, "");
			buildingId = DaySpecialTempParameter.getData(Constant.BD_ID, "");
			activityPrice = DaySpecialTempParameter.getData(Constant.A_PRICE, "");
			activityId = DaySpecialTempParameter.getData(Constant.A_ID, "");
			startTime = DaySpecialTempParameter.getData(Constant.ST, DateUtils.currentTime());
			endTime = DaySpecialTempParameter.getData(Constant.ET, DateUtils.currentTime());
		}
		
		tokenId = UserInfoData.getData(Constant.TOKEN, "");
		initView();
		initListener();
		//initData();
	}
	
	@Override
	protected void onResume() {
		initData();
		super.onResume();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_title.setVisibility(View.INVISIBLE);
		tv_right.setVisibility(View.INVISIBLE);
		// 主图
		masterImg = (ImageView) findViewById(R.id.img_master);
		// 活动价
		atyPrice = (TextView) findViewById(R.id.tv_currentPrice_biddingHouseInfo);
		try {
			double price = Double.parseDouble(activityPrice);
			atyPrice.setText(StringUtils.intMoney(price/10000+""));
		} catch (Exception e) {
			showTost("数据错误");
		}
		// 市场价
		marketPrice = (TextView) findViewById(R.id.tv_formerPrice_biddingHouseInfo);
		// 楼盘
		buildName = (TextView) findViewById(R.id.tv_buildname);
		// 地址
		buildAdress = (TextView) findViewById(R.id.tv_buildadress);
		// 户型
		houseType = (TextView) findViewById(R.id.tv_houseType_biddingHouseInfo);
		// 建筑面积
		houseSize = (TextView) findViewById(R.id.tv_housesize_biddingHouseInfo);
		// 楼号
		buildingNum = (TextView) findViewById(R.id.tv_buildingNum_biddingHouseInfo);
		// 单元号
		unitNum = (TextView) findViewById(R.id.tv_unitNum_biddingHouseInfo);
		// 楼层
		houseFloor = (TextView) findViewById(R.id.tv_floor_biddingHouseInfo);
		// 楼层
		buildKind = (TextView) findViewById(R.id.tv_residenceType_biddingHouseInfo);
		// 抢购按钮
		tv_scare = (TextView) findViewById(R.id.tv_scare);
		//倒计时
		tivm_time = (TimeViewWhiteWord) findViewById(R.id.tivm_time);
		ll_timedown = (LinearLayout) findViewById(R.id.ll_timedown);
		//已被抢黑色条
		tv_hadscare = (TextView) findViewById(R.id.tv_hadscare);
		if(null != getIntent().getExtras()){
			String from = getIntent().getExtras().getString("from", "");
			if(TextUtils.equals("fanglianquan",from)){
				findViewById(R.id.ll_bottom).setVisibility(View.GONE);;
			}
		}
	
	}
	/**
	 * 
	 * @Title: initData
	 * @Description: 天天特价房源信息
	 * @return: void
	 */
	// “payStatus”:”0”,//0为未登录、1为未缴费、2为已缴费
	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("houseSourceId", houseSourceId);
		map.put("tokenId", tokenId);
		map.put("activityPoolId", activityPoolId);
		SimpleRequest<DaySpecialHouseInfoBean> req = new SimpleRequest<DaySpecialHouseInfoBean>(
				API.QUERY_HOUSE_SOURCE, DaySpecialHouseInfoBean.class,
				new Listener<DaySpecialHouseInfoBean>() {
					@Override
					public void onResponse(DaySpecialHouseInfoBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								Result result = response.getResult();
								List<Imgs> imgs = new ArrayList<Imgs>();
								imgs.addAll(response.getResult().getImgs());
								if(imgs.size() > 0){
									imageLoader.displayImage(response.getResult()
											.getImgs().get(0).getImgUrl(),
											masterImg, options);
								}								
								//atyPrice.setText(result.getPrice()+"元/㎡");
								houseStatus = result.getHouseStatus();
								buildName.setText(result.getBuildingName());
								buildAdress.setText(result.getAddress());
								houseType.setText(result.getHouseTypeName());
								houseSize.setText(result.getSize()+"㎡");
								String totalPrice = StringUtils.intMoney(result.getTotalPrice());
								marketPrice.setText(totalPrice+"万");
								buildingNum.setText(result.getBuildingNum());
								unitNum.setText(result.getBuildingUnit());
								houseFloor.setText(result.getFloor());
								buildKind.setText(result.getHouseKind());
								payStatus = result.getPayStatus();
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
						loadingDialog.dismissDialog();
						
					}
				} ,map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	/** 
	 * @Title: bottomButtonState
	 * @Description: 底部按钮状态
	 * @return: void
	 */
	//“payStatus”:”0”,//0为未登录、1为未缴费、2为已缴费
	//houseStatus”:”1”,//0为未被秒、1为已被秒
	protected void bottomButtonState() {
		//1458822524441
		//payStatus = "2";
		atyState = JudgeAcctivityStateUtil.getState(startTime, endTime);
		long currentTime = DateUtils.currentTime();
		LogUtil.e("☆", currentTime+"");
		switch (atyState) {
		case 0:
			// 即将开始
			if (payStatus.equals("0")) {
				tv_scare.setText("立即缴纳保证金");
			}
			if (payStatus.equals("1")) {
				tv_scare.setText("立即缴纳保证金");
			}
			if (payStatus.equals("2")) {
				tv_scare.setText("抢购");
				tv_scare.setTextColor(Color.parseColor("#fd8888"));
				tv_scare.setEnabled(false);
				ll_timedown.setVisibility(View.VISIBLE);
				tivm_time.setDownTimes(JudgeAcctivityStateUtil
						.downTimeFormat(startTime - currentTime));
				tivm_time.run();
			}
			break;
		case 1:
			//进行中
			if (payStatus.equals("0")) {
				tv_scare.setText("立即缴纳保证金");
			}
			if (payStatus.equals("1")) {
				
				if(houseStatus.equals("0")){
					//未被秒
					tv_scare.setText("立即缴纳保证金");
				}else{
					//被秒
					tv_scare.setText("已被抢");
					tv_scare.setTextColor(Color.parseColor("#fd8888"));
					tv_scare.setEnabled(false);
					tv_hadscare.setVisibility(View.VISIBLE);//已被抢黑色条
				}
			}
			if (payStatus.equals("2")) {
				if(houseStatus.equals("0")){
					//未被秒
					tv_scare.setText("抢购");
				}else{
					//被秒
					tv_scare.setText("已被抢");
					tv_scare.setTextColor(Color.parseColor("#fd8888"));
					tv_scare.setEnabled(false);
					tv_hadscare.setVisibility(View.VISIBLE);//已被抢黑色条
				}
				
			}			
			
			break;
		case 2:
			//已结束
			//被秒
			tv_scare.setText("已被抢");
			tv_scare.setTextColor(Color.parseColor("#fd8888"));
			tv_scare.setEnabled(false);
			tv_hadscare.setVisibility(View.GONE);//已被抢黑色条
			break;

		}
	}

	private void initListener() {
		rl_back.setOnClickListener(this);
		tv_scare.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.tv_scare:
			switch (atyState) {
			case 0:
				// 即将开始
				if (payStatus.equals("0")) {
					goOthers(LoginActivity.class);
				}
				if (payStatus.equals("1")) {
					showConfirmDialog();
				}				
				
				break;
			case 1:
				// 进行中
				if (payStatus.equals("0")) {
					goOthers(LoginActivity.class);
				}
				if (payStatus.equals("1")) {
					showConfirmDialog();
				}
				if (payStatus.equals("2")) {
					saveFLQuan();
				}
				
				break;
				
			}

		}

	}

	/**
	 * 
	 * @Title: showConfirmDialog 
	 * @Description: 确认缴纳保证金的弹出框
	 * @return: void
	 */
	private void showConfirmDialog() {
		CustomDialog.Builder  builder = new Builder(this);
		builder.setTitle("抢购保证金");
		builder.setContent("缴纳抢购保证金后您会获得抢购机会，抢购不成功，保证金会在3~15个工作日退回原卡。");
		builder.setNegativeButton("取消", new CustomDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("立即缴纳 ", new CustomDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(SpecialHouseInfoActivity.this,SpecialAgreeMentWebViewActivity.class);
				intent.putExtra("houseSourceId", houseSourceId + "");
				intent.putExtra("buildingId", buildingId + "");
				intent.putExtra("activityPoolId", activityPoolId + "");
				intent.putExtra("activityId", activityId);
				startActivity(intent);
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
		map.put("activityId", activityId);
		map.put("tokenId", tokenId);
		map.put("summaryId", activityPoolId);
		SimpleRequest<SeckillBean> req = new SimpleRequest<SeckillBean>(
				API.SECKILL, SeckillBean.class,
				new Listener<SeckillBean>() {
					@Override
					public void onResponse(SeckillBean response) {
						if (null != response.getMsg()) {
							if (response.getStatus() == 0) {
								Intent intent = new Intent(
										SpecialHouseInfoActivity.this,
										DaySpecialDiscountSuccessActivity.class);
								intent.putExtra("ticketAmount", Integer.parseInt(activityPrice)/10000+"");
								intent.putExtra("houseSouseID", houseSourceId);
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
	@Override
	protected void onPause() {
		tivm_time.stop();
		super.onPause();
	}
	@Override
	protected void onStop() {
		tivm_time.stop();
		super.onStop();
	}
}
