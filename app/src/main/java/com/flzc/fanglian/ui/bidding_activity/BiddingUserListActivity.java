package com.flzc.fanglian.ui.bidding_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
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
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.BiddingUserActivityBean;
import com.flzc.fanglian.model.BiddingUserActivityBean.Result;
import com.flzc.fanglian.model.BiddingUserActivityBean.Result.ListUser;
import com.flzc.fanglian.ui.adapter.BiddingUserActivityAdapter;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

public class BiddingUserListActivity extends BaseActivity implements OnLoaderListener{

	private RelativeLayout rl_back;
	private TextView tv_title;
	private LoadListView lv_user_list;
	private String activityId;
	
	private List<ListUser> listData = new ArrayList<ListUser>();
	private BiddingUserActivityAdapter adapter;
	private SwipeRefreshLayout swipeRefreshLayout;
	protected boolean isRefesh;
	protected int page = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		Intent intent = getIntent();
		activityId = intent.getStringExtra("activityId");
		initView();
		getBiddingUserData(1);
		pullTORefresh();
	}
	
	private void pullTORefresh() {
		// 下拉刷新部分
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		// 设置卷内的颜色
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.darker_gray, 
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light, 
				android.R.color.holo_red_light);
		// 设置下拉刷新监听
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				isRefesh = true;
				page   = 1;
				getBiddingUserData(page);;
			}
		});
	}
	private void initView() {
		//标题栏
		rl_back = (RelativeLayout)findViewById(R.id.rl_back);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("参与用户");
		//列表
		lv_user_list = (LoadListView)findViewById(R.id.lv_user_list);
		lv_user_list.setLoaderListener(this);
		adapter = new BiddingUserActivityAdapter(BiddingUserListActivity.this,listData);
		lv_user_list.setAdapter(adapter);
		
		rl_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void getBiddingUserData(int page){
		loadingDialog.showDialog();
		Map<String,String> map = new HashMap<String, String>();
		map.put("activityId", activityId);
		map.put("page", page+ "");
		loadingDialog.showDialog();
		SimpleRequest<BiddingUserActivityBean> request = new SimpleRequest<BiddingUserActivityBean>(API.QUERY_AUCTION_PARTICIPANT, BiddingUserActivityBean.class, 
				new Listener<BiddingUserActivityBean>() {
					@Override
					public void onResponse(BiddingUserActivityBean response) {
						if(response != null){
							if(0 == response.getStatus()){
								if(isRefesh){
									listData.clear();
									isRefesh = false;
								}
								Result result = response.getResult();
								List<ListUser> list = result.getList();
								listData.addAll(list);
								adapter.notifyDataSetChanged();
							}else{
								showTost(response.getMsg());
							}
							loadingDialog.dismissDialog();
							// 通知listview加载完毕
							lv_user_list.loadComplete();
							swipeRefreshLayout.setRefreshing(false);
						}
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						lv_user_list.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}
	@Override
	public void onLoad() {
		getBiddingUserData(++page);
	}
	
}
