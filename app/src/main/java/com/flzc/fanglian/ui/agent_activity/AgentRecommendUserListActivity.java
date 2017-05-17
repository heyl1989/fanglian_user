package com.flzc.fanglian.ui.agent_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
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
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.DaySpecialPartUserListBean;
import com.flzc.fanglian.model.DaySpecialPartUserListBean.Result.UserList;
import com.flzc.fanglian.ui.adapter.DaySpecialUserListAdapter;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

public class AgentRecommendUserListActivity extends BaseActivity implements
		OnClickListener,OnLoaderListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private LoadListView lv_user_list;
	private String activityId;
	private SwipeRefreshLayout swipeRefreshLayout;
	protected boolean isRefesh;
	protected int page = 1;
	private DaySpecialUserListAdapter userAdapter;
	private List<UserList> uesrList = new ArrayList<UserList>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		activityId = getIntent().getExtras().getString("activityId", "");
		initView();
		initLIstener();
		initPartUserData(1);
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
				page  = 1;
				initPartUserData(page);;
			}
		});
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("参与用户");
		// 列表
		lv_user_list = (LoadListView) findViewById(R.id.lv_user_list);
		lv_user_list.setLoaderListener(this);
		userAdapter = new DaySpecialUserListAdapter(this,uesrList);
		lv_user_list.setAdapter(userAdapter);
	}

	/**
	 * 
	 * @Title: initData
	 * @Description: 查询天天特价参与用户
	 * @return: void
	 */
	private void initPartUserData(int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityId", activityId);
		map.put("page", page+"");
		SimpleRequest<DaySpecialPartUserListBean> req = new SimpleRequest<DaySpecialPartUserListBean>(
				API.QUERY_SALEWAY_PARTICIPANT,
				DaySpecialPartUserListBean.class,
				new Listener<DaySpecialPartUserListBean>() {
					@Override
					public void onResponse(DaySpecialPartUserListBean response) {
						if (null != response.getMsg()) {
							if (response.getStatus() == 0) {
								if(isRefesh){
									uesrList.clear();
									isRefesh = false;
								}
								List<UserList> responseList = response.getResult().getList();
								uesrList.addAll(responseList);	
								userAdapter.notifyDataSetChanged();
							}else{
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						lv_user_list.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
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
		UserApplication.getInstance().addToRequestQueue(req);

	}

	private void initLIstener() {
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
		initPartUserData(++page);
	}

}
