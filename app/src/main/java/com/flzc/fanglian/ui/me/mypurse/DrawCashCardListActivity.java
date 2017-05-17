package com.flzc.fanglian.ui.me.mypurse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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
import com.flzc.fanglian.model.UserBinbCardBean;
import com.flzc.fanglian.model.UserBinbCardBean.BindCardList;
import com.flzc.fanglian.ui.adapter.BankCardAdapter;

public class DrawCashCardListActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	private RelativeLayout rl_back;
	private TextView tv_title;
	private ListView lv_cardlist;
	private TextView addCard;
	private BankCardAdapter bankCardAdapter;
	private ArrayList<BindCardList> bankCardList = new ArrayList<BindCardList>();
	private String from;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypurse_cardlist);
		Intent intent = getIntent();
		if(null != intent){
			from = intent.getStringExtra("from");
		}
		initView();
	}
	
	@Override
	protected void onResume() {
		bankCardList.clear();
		initData();
		super.onResume();
	}

	/**
	 * @Title: initView
	 * @Description: 初始化布局
	 * @return: void
	 */
	private void initView() {
		// 标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("我的银行卡");
		
		//listview
		lv_cardlist = (ListView)findViewById(R.id.lv_cardlist);
		bankCardAdapter = new BankCardAdapter(this,bankCardList);
		lv_cardlist.setAdapter(bankCardAdapter);
		lv_cardlist.setOnItemClickListener(this);
		//添加银行卡
		addCard = (TextView)findViewById(R.id.tv_add_card);
		addCard.setOnClickListener(this);
	}
	
	/**
	 * 
	 * @Title: initData 
	 * @Description: 查询我的银行卡
	 * @return: void
	 */
	private void initData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));

		loadingDialog.showDialog();
		SimpleRequest<UserBinbCardBean> request = new SimpleRequest<UserBinbCardBean>(
				API.QUERYUSERBANKCARD, UserBinbCardBean.class,
				new Listener<UserBinbCardBean>() {
					@Override
					public void onResponse(UserBinbCardBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								bankCardList.addAll(response.getResult());
								bankCardAdapter.notifyDataSetChanged();
							}else{
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
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
		case R.id.tv_add_card:
			goOthers(DrawCashAddCardActivity.class);
			break;

		default:
			break;
		}
		
	}
	final int RESULT_CODE=101;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(TextUtils.equals("purse", from)){
			return;
		}
		Intent intent=new Intent();
        intent.putExtra("bank", bankCardList.get(position).getBankName()+"");
        intent.putExtra("cardNum", bankCardList.get(position).getBankCode()+"");
        intent.putExtra("id", bankCardList.get(position).getId()+"");
        setResult(RESULT_CODE, intent);
        finish();
	}

}
