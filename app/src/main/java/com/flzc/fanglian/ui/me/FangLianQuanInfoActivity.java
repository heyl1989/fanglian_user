package com.flzc.fanglian.ui.me;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
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
import com.flzc.fanglian.model.FangLianQuanInfoBean;
import com.flzc.fanglian.model.FangLianQuanInfoBean.Result;
import com.flzc.fanglian.ui.agent_activity.AgentRecommendHouseInfoActivity;
import com.flzc.fanglian.ui.bidding_activity.BiddingHouseInfoActivity;
import com.flzc.fanglian.ui.everyday_special_activity.SpecialHouseInfoActivity;
import com.flzc.fanglian.util.CreateQRImageUtil;
import com.flzc.fanglian.util.DateUtils;
import com.flzc.fanglian.util.LogUtil;
import com.flzc.fanglian.util.StringUtils;
import com.google.gson.Gson;

/**
 * 
 * @ClassName: FangLianQuanInfoActivity
 * @Description: 房链券详情页面
 * @author: 薛东超
 * @date: 2016年3月9日 上午10:31:36
 */
public class FangLianQuanInfoActivity extends BaseActivity implements
		OnClickListener {
	
	private RelativeLayout rl_back;
	private TextView tv_title, tv_right;

	private TextView tv_fanglqinfo_accesschannel, tv_fanglqinfo_money,tv_fanglqinfo_money_unit,
			tv_fanglqinfo_type, tv_fanglqinfo_applybd_value,
			tv_fanglqinfo_deadline, tv_fanglqinfo_cancle_numvalue,
			tv_fanglqinfo_userange,tv_fanglqinfo_address,tv_fanglqinfo_tel;
	private ImageView iv_fanglqinfo_status,iv_fanglqinfo_two_code;

	private String sourceName;
	private String ticketId;
	private TextView look_more;
	private TextView mHouseinfo;
	private TextView mSuccessprie;
	private TextView mPrice;
	protected int activityType;
	private String buildingId;
	private String houseSourceId;
	private String activityPoolId;
	private String activityPrice;
	protected String saleTell;
	private String minPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fanglianquan_info);
		initView();
		initData();
	}

	private void initView() {
		//标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_title.setText("房链券");
		tv_right.setText("");
		//查看详情
		look_more = (TextView)findViewById(R.id.tv_look_more);
		look_more.setOnClickListener(this);
		//房源信息
		mHouseinfo = (TextView)findViewById(R.id.tv_fanglqinfo_houseinfo);
		//竞拍成功价
		mSuccessprie = (TextView)findViewById(R.id.tv_fanglqinfo_successprie);
		//参考原价
		mPrice = (TextView)findViewById(R.id.tv_fanglqinfo_price);
		
		tv_fanglqinfo_accesschannel = (TextView) findViewById(R.id.tv_fanglqinfo_accesschannel);
		tv_fanglqinfo_money = (TextView) findViewById(R.id.tv_fanglqinfo_money);
		tv_fanglqinfo_money_unit = (TextView) findViewById(R.id.tv_fanglqinfo_money_unit);
		tv_fanglqinfo_type = (TextView) findViewById(R.id.tv_fanglqinfo_type);
		tv_fanglqinfo_applybd_value = (TextView) findViewById(R.id.tv_fanglqinfo_applybd_value);
		tv_fanglqinfo_deadline = (TextView) findViewById(R.id.tv_fanglqinfo_deadline);
		tv_fanglqinfo_cancle_numvalue = (TextView) findViewById(R.id.tv_fanglqinfo_cancle_numvalue);
		tv_fanglqinfo_userange = (TextView) findViewById(R.id.tv_fanglqinfo_userange);
		tv_fanglqinfo_address = (TextView) findViewById(R.id.tv_fanglqinfo_address);
		//联系售楼处
		tv_fanglqinfo_tel = (TextView) findViewById(R.id.tv_fanglqinfo_tel);
		tv_fanglqinfo_tel.setOnClickListener(this);
		
		iv_fanglqinfo_status = (ImageView) findViewById(R.id.iv_fanglqinfo_status);
		iv_fanglqinfo_two_code = (ImageView) findViewById(R.id.iv_fanglqinfo_two_code);
		
		rl_back.setOnClickListener(this);
	}

	private void initData() {
		Intent intent = getIntent();
		sourceName = intent.getStringExtra("sourceName");
		ticketId = intent.getStringExtra("ticketId");

		tv_fanglqinfo_accesschannel.setText("获取渠道： " + sourceName);
		
		getFLQuanInfoData();
	}

	private void getFLQuanInfoData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("ticketId", ticketId);
		SimpleRequest<FangLianQuanInfoBean> request = new SimpleRequest<FangLianQuanInfoBean>(
				API.USERTICKETDETAILS, FangLianQuanInfoBean.class, new Listener<FangLianQuanInfoBean>() {
					@Override
					public void onResponse(FangLianQuanInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								Result result = response.getResult();
								activityPrice = StringUtils.intMoney(result.getAmount());
								minPrice = result.getMinPrice();
								if(result.getActivityType() == 3902){
									try {
										tv_fanglqinfo_money.setText("" + StringUtils.intMoney(Double.parseDouble(activityPrice)/10000+""));
									} catch (Exception e) {
									}
								}else{
									tv_fanglqinfo_money.setText("" + activityPrice);
								}
								tv_fanglqinfo_money_unit.setText(result.getUnit());
								tv_fanglqinfo_type.setText(result.getTicketTypeName());
								//0未使用  1已使用  2已过期
								if(0 == result.getStatus()){
									iv_fanglqinfo_status.setImageResource(R.drawable.bg_fanglqinfo_available);
								}else if(1 == result.getStatus()){
									iv_fanglqinfo_status.setVisibility(View.GONE);
									iv_fanglqinfo_status.setImageResource(R.drawable.bg_had_use);
								}else if(2 == result.getStatus()){
									iv_fanglqinfo_status.setImageResource(R.drawable.has_expired);
								}
								
								tv_fanglqinfo_applybd_value.setText(result.getBuildingName());
								
								String startTime = DateUtils.formatDateDay(result.getStartDate());
								String endTime = DateUtils.formatDateDay(result.getEndDate());
								tv_fanglqinfo_deadline.setText("有效期： " + startTime + "-" + endTime);
								//房源信息和参考价那块
								buildingId = result.getBuildingId();
								houseSourceId = result.getSourceId();
								activityPoolId = result.getSummaryId();
								activityType = result.getActivityType();
								if(result.getActivityType() == 3904){
									look_more.setVisibility(View.GONE);
									mHouseinfo.setVisibility(View.GONE);
								}else{
									mHouseinfo.setText("房源信息："+result.getHouseType()+"  "+result.getSize()+"㎡");
								}
								if(result.getActivityType() == 3901){
									String successPrice = StringUtils.intMoney(result.getSuccessPrice());
									if(!TextUtils.equals(successPrice, "0")){
										mSuccessprie.setVisibility(View.VISIBLE);
										mSuccessprie.setText("竞拍成功价："+successPrice+"元/㎡");
									}
									mPrice.setVisibility(View.VISIBLE);
									mPrice.setText("参考原价："+StringUtils.intMoney(result.getPrice())+"元/㎡");
								}
								
								tv_fanglqinfo_cancle_numvalue.setText(result.getTicketCode());
								Gson gson = new Gson();
								Map<String,String> map = new HashMap<String,String>();
								map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
								map.put("ticketCode", result.getTicketCode());
								map.put("type", "2");
								String ercode = gson.toJson(map);
								LogUtil.e("二维码", ercode);
								CreateQRImageUtil createQRImageUtil = new CreateQRImageUtil(iv_fanglqinfo_two_code);
								createQRImageUtil.createQRImage(ercode);
								
								tv_fanglqinfo_userange.setText("-该券只适用于 " + "“" + result.getBuildingName() + "”  " + "；");
								tv_fanglqinfo_address.setText("地址：" + result.getSaleAddress());
								saleTell = result.getSaleTel();
								String tellText = "电话：" + result.getSaleTel();
								SpannableString msp = new SpannableString(tellText);   
								msp.setSpan(new URLSpan(result.getSaleTel()), 3, tellText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE); 
								tv_fanglqinfo_tel.setText(msp);
							}else{
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
					}
				} ,map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 返回
		case R.id.rl_back:
			finish();
			break;
		case R.id.tv_fanglqinfo_tel:
			Intent tellIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
					+ saleTell));
			startActivity(tellIntent);
			break;
		case R.id.tv_look_more:
			Intent intent = new Intent();
			if(activityType == 3901){
				intent.putExtra("buildingId", buildingId);
				intent.putExtra("houseSourceId", houseSourceId);
				intent.putExtra("activityPoolId", activityPoolId);
				intent.putExtra("biddingPrice", minPrice+"");
				intent.putExtra("from", "fanglianquan");
				intent.setClass(this, BiddingHouseInfoActivity.class);
				startActivity(intent);
			}
			if(activityType == 3902){
				intent.putExtra("buildingId", buildingId);
				intent.putExtra("houseSourceId", houseSourceId);
				intent.putExtra("activityPoolId", activityPoolId);
				intent.putExtra("activityPrice", activityPrice+"");
				intent.putExtra("from", "fanglianquan");
				intent.setClass(this, SpecialHouseInfoActivity.class);
				startActivity(intent);
			}
			if(activityType == 3903){
				intent.putExtra("buildingId", buildingId);
				intent.putExtra("houseSouseID", houseSourceId);
				intent.putExtra("activityPoolId", activityPoolId);
				intent.putExtra("ticketAmount", activityPrice+"");
				intent.putExtra("from", "fanglianquan");
				intent.setClass(this, AgentRecommendHouseInfoActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

}
