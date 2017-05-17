package com.flzc.fanglian.ui.me.mypurse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
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
import com.flzc.fanglian.model.AccountBackDetailBean;
import com.flzc.fanglian.model.AccountBackDetailBean.Result;
import com.flzc.fanglian.ui.adapter.RefundRecordAdapter;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: RefundRecordActivity
 * @Description: 退款记录
 * @author: jack
 * @date: 2016年3月29日 下午2:24:46
 */

public class RefundRecordActivity extends BaseActivity implements
		OnClickListener,OnLoaderListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private LoadListView lv_refund_reccord;

	private RefundRecordAdapter adapter;
	private List<Result> listData = new ArrayList<Result>();

	private SwipeRefreshLayout refundrecord_swipeRefreshLayout;
	private int page = 1;
	private boolean isReflsh = false;
	private View headView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypurse_refundrecord);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("退款记录");
		// 退款列表
		lv_refund_reccord = (LoadListView) findViewById(R.id.lv_refund_reccord);
		headView = LayoutInflater.from(this).inflate(
				R.layout.layout_search_none, null);
		TextView content = (TextView) headView.findViewById(R.id.tv_text);
		content.setText("退款不是好现象哦~\n还是不退的好⊙v⊙ ");
		lv_refund_reccord.setLoaderListener(this);
		adapter = new RefundRecordAdapter(RefundRecordActivity.this, listData);
		lv_refund_reccord.setAdapter(adapter);

		refundrecord_swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refundrecord_swipeRefreshLayout);
		// 设置卷内的颜色
		refundrecord_swipeRefreshLayout.setColorSchemeResources(
				android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		refundrecord_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				isReflsh = true;
				page = 1;
				getAccountBackDetailData(page);
			}
		});
	}

	private void initData() {

		getAccountBackDetailData(1);
	}

	private void getAccountBackDetailData(int pageNo) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("pageNo", pageNo + "");
		map.put("pageSize", "15");

		SimpleRequest<AccountBackDetailBean> request = new SimpleRequest<AccountBackDetailBean>(
				API.ACCOUNT_BACK_DETAIL, AccountBackDetailBean.class,
				new Listener<AccountBackDetailBean>() {
					@Override
					public void onResponse(AccountBackDetailBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if(isReflsh){
									listData.clear();
									isReflsh = false;
								}
								List<Result> list = response.getResult();
								listData.addAll(list);
								if(null != headView){
									lv_refund_reccord.removeHeaderView(headView);
								}
								if (listData.size() == 0) {
									lv_refund_reccord.addHeaderView(headView);
								}
								adapter.notifyDataSetChanged();
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						lv_refund_reccord.loadComplete();
						refundrecord_swipeRefreshLayout.setRefreshing(false);
					}
				},new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						lv_refund_reccord.loadComplete();
						refundrecord_swipeRefreshLayout.setRefreshing(false);
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	private void initListener() {
		rl_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		}

	}

	@Override
	public void onLoad() {
		getAccountBackDetailData(++page);
	}

}
