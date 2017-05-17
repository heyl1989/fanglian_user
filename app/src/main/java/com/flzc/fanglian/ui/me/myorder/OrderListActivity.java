package com.flzc.fanglian.ui.me.myorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.flzc.fanglian.model.UserOrderListBean;
import com.flzc.fanglian.model.UserOrderListBean.Result;
import com.flzc.fanglian.ui.adapter.MyOrderListAdapter;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: OrderListActivity
 * @Description: 我的订单列表
 * @author: LU
 * @date: 2016-3-8 下午6:53:26
 */
public class OrderListActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener,OnLoaderListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private LoadListView lv_order;
	private SwipeRefreshLayout swipeRefreshLayout;

	private List<Result> listData = new ArrayList<UserOrderListBean.Result>();
	private MyOrderListAdapter adapter;

	private int page = 1;
	private boolean isReflsh = false;
	private View headView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_orderlist);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("订单");

		// 下拉刷新部分
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.order_list_swipeRefreshLayout);
		// 设置卷内的颜色
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				isReflsh = true;
				page = 1;
				getOrderListData(page);
			}
		});

		// list
		lv_order = (LoadListView) findViewById(R.id.lv_order);
		headView = LayoutInflater.from(this).inflate(
				R.layout.layout_search_none, null);
		TextView content = (TextView) headView.findViewById(R.id.tv_text);
		content.setText("您还没有订单哦╭(╯^╰)╮ \n优惠房这么多,快去看看吧");
		lv_order.setLoaderListener(this);
		adapter = new MyOrderListAdapter(OrderListActivity.this, listData);
		lv_order.setAdapter(adapter);
	}

	private void initData() {

		getOrderListData(1);
	}

	private void getOrderListData(int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("page", page + "");

		SimpleRequest<UserOrderListBean> request = new SimpleRequest<UserOrderListBean>(
				API.USER_ORDER_LIST, UserOrderListBean.class,
				new Listener<UserOrderListBean>() {
					@Override
					public void onResponse(UserOrderListBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if(isReflsh){
									listData.clear();
									isReflsh = false;
								}
								List<Result> list = response.getResult();
								listData.addAll(list);
								if(null != headView){
									lv_order.removeHeaderView(headView);
								}
								if (listData.size() == 0) {
									lv_order.addHeaderView(headView);
								}
								adapter.notifyDataSetChanged();
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						lv_order.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
						lv_order.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	private void initListener() {
		rl_back.setOnClickListener(this);
		lv_order.setOnItemClickListener(this);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView textView;
		Result result = null;
		textView = (TextView) view.findViewById(R.id.tv_order_time);
		if (textView == null) {
			return;
		}
		result = (Result) textView.getTag();
		if (result == null) {
			return;
		}
		Intent intent = new Intent(OrderListActivity.this,
				OrderDetailActivity.class);
		intent.putExtra("master", result.getMaster());
		intent.putExtra("orderFlowId", result.getOrderFlow());
		intent.putExtra("actState", result.getActStatus());
		startActivity(intent);
	}

	@Override
	public void onLoad() {
		getOrderListData(++page);
	}

}
