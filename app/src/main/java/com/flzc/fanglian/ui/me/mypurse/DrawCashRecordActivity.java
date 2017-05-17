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
import com.flzc.fanglian.model.AccountDrawList;
import com.flzc.fanglian.model.AccountDrawList.Result;
import com.flzc.fanglian.ui.adapter.DrawCashRecordAdapter;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: DrawCashRecordActivity
 * @Description: 提现记录
 * @author: jack
 * @date: 2016年3月29日 下午2:26:24
 */

public class DrawCashRecordActivity extends BaseActivity implements
		OnClickListener,OnLoaderListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private LoadListView lv_drawcash_record;

	private DrawCashRecordAdapter adapter;
	private List<Result> listData = new ArrayList<Result>();

	private SwipeRefreshLayout drawcash_list_swipeRefreshLayout;
	private int page = 1;
	private boolean isReflsh = false;
	private View headView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypurse_drawcashrecord);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("提现记录");
		// 列表
		lv_drawcash_record = (LoadListView) findViewById(R.id.lv_drawcash_record);
		headView = LayoutInflater.from(this).inflate(
				R.layout.layout_search_none, null);
		TextView content = (TextView) headView.findViewById(R.id.tv_text);
		content.setText("天呐~\n您居然没有提现记录⊙_⊙ ");
		lv_drawcash_record.setLoaderListener(this);
		adapter = new DrawCashRecordAdapter(DrawCashRecordActivity.this,
				listData);
		lv_drawcash_record.setAdapter(adapter);

		// 下拉刷新部分
		drawcash_list_swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.drawcash_list_swipeRefreshLayout);
		// 设置卷内的颜色
		drawcash_list_swipeRefreshLayout.setColorSchemeResources(
				android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		drawcash_list_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				isReflsh = true;
				page = 1;
				getDrawListData(page);
			}
		});

	}

	private void initData() {

		getDrawListData(1);
	}

	private void getDrawListData(int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("pageNo", page + "");
		map.put("pageSize", "10");

		SimpleRequest<AccountDrawList> request = new SimpleRequest<AccountDrawList>(
				API.ACCOUNT_USER_WITH_DRAW_LIST, AccountDrawList.class,
				new Listener<AccountDrawList>() {
					@Override
					public void onResponse(AccountDrawList response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if(isReflsh){
									listData.clear();
									isReflsh = false;
								}
								List<Result> list = response.getResult();
								listData.addAll(list);
								if(null != headView){
									lv_drawcash_record.removeHeaderView(headView);
								}
								if (listData.size() == 0) {
									lv_drawcash_record.addHeaderView(headView);
								}
								adapter.notifyDataSetChanged();
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						lv_drawcash_record.loadComplete();
						drawcash_list_swipeRefreshLayout.setRefreshing(false);
					}
				},new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						lv_drawcash_record.loadComplete();
						drawcash_list_swipeRefreshLayout.setRefreshing(false);
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
		getDrawListData(++page);
	}

}
