package com.flzc.fanglian.ui.me.mypurse;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
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
import com.flzc.fanglian.model.BalanceBean;
import com.flzc.fanglian.util.StringUtils;
/**
 * 
 * @ClassName: MyPurseActivity 
 * @Description: 余额
 * @author: LU
 * @date: 2016-3-4 下午3:12:22
 */
public class MyPurseActivity extends BaseActivity implements OnClickListener{

	private TextView tv_right;
	private RelativeLayout rl_detail_recode;
	private RelativeLayout rl_back;
	private RelativeLayout rl_refund;
	private TextView tv_mypurse_money;
	private RelativeLayout rl_my_bankcard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypurse);
		initView();
		initListener();
	}
	
	@Override
	protected void onResume() {
		initData();
		super.onResume();
	}

	private void initView() {
		//标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setText("提现");
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("我的账户");
		ImageView img_back = (ImageView)findViewById(R.id.img_back);
		img_back.setImageResource(R.drawable.me_white_arrow);
		//余额
		tv_mypurse_money = (TextView) findViewById(R.id.tv_mypurse_money);
		
		//下面的条目
		rl_my_bankcard = (RelativeLayout) findViewById(R.id.rl_my_bankcard);
		rl_detail_recode = (RelativeLayout) findViewById(R.id.rl_detail_recode);
		rl_refund = (RelativeLayout) findViewById(R.id.rl_refund);
		
	}

	private void initListener() {
		rl_back.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		//下面的条目
		rl_my_bankcard.setOnClickListener(this);
		rl_detail_recode.setOnClickListener(this);
		rl_refund.setOnClickListener(this);

	}

	private void initData(){
		loadingDialog.showDialog();
		Map<String,String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		
		SimpleRequest<BalanceBean> request = new SimpleRequest<BalanceBean>(API.QUERYBALANCE, BalanceBean.class,
				new Listener<BalanceBean>() {
					@Override
					public void onResponse(BalanceBean response) {
						if(response != null){
							if(0 == response.getStatus()){
								String balance = StringUtils.intMoney(response.getResult().getBalance());
								tv_mypurse_money.setText(balance + "");
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
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.tv_right:
			//提现 
			goOthers(DrawCashActivity.class);
			break;
		case R.id.rl_my_bankcard:
			//我的银行卡
			Intent intent = new Intent(this,DrawCashCardListActivity.class);
			intent.putExtra("from", "purse");
			startActivity(intent);
			break;
		case R.id.rl_detail_recode:
			goOthers(MyPurseDetailActivity.class);
			break;
		case R.id.rl_refund:
			goOthers(RefundRecordActivity.class);
			break;
		}
		
	}

}
