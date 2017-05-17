package com.flzc.fanglian.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.flzc.fanglian.model.CounselorListBean;
import com.flzc.fanglian.model.CounselorListBean.Result.CounselorList;
import com.flzc.fanglian.ui.adapter.AllCounselorAdapter;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: AllAgentListActivity 
 * @Description: 查看所有  置业顾问的列表页面
 * @author: Tien.
 * @date: 2016年3月10日 下午5:19:48
 */
public class AllAgentListActivity extends BaseActivity implements OnClickListener,OnLoaderListener{
	private RelativeLayout rl_back;
	private TextView titleName;
	private LoadListView listview_all_agent;
	private AllCounselorAdapter cAdapter;
	private List<CounselorList> list = new ArrayList<CounselorList>();
	private String buildingId;
	private int page = 1;
	private SwipeRefreshLayout swipeRefreshLayout;
	protected boolean isRefresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_agentlist);
		buildingId = getIntent().getStringExtra("buildingId");
		pullTORefresh();
		initItem();
		initData(page);
	}
	
	private void pullTORefresh() {
		// 下拉刷新部分
		swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
		// 设置卷内的颜色
		swipeRefreshLayout.setColorSchemeResources(android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		// 设置下拉刷新监听
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				isRefresh = true;
				page = 1;
				initData(page);
			}
		});
	}
	
	private void initItem(){
		//标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);		
		titleName = (TextView) findViewById(R.id.tv_title);
		titleName.setText("置业顾问");
		//列表
		listview_all_agent = (LoadListView) findViewById(R.id.listview_all_agent);
		listview_all_agent.setLoaderListener(this);
		cAdapter = new AllCounselorAdapter(AllAgentListActivity.this, list);
		listview_all_agent.setAdapter(cAdapter);
	}
	
	/**
	 * 
	 * @Title: initData 
	 * @Description: 请求置业顾问所有数据
	 * @return: void
	 */
	private void initData(int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("page", page +"");
		map.put("pageSize", "10");
		SimpleRequest<CounselorListBean> req = new SimpleRequest<CounselorListBean>(
				API.QUERY_BUILDER_LIST, CounselorListBean.class, new Listener<CounselorListBean>() {
					@Override
					public void onResponse(CounselorListBean response) {
						if(response != null){
							if(response.getStatus() == 0){
								if(isRefresh){
									list.clear();
									isRefresh = false;
								}
								if(null != response.getResult().getList()){
									List<CounselorList> counselor = response.getResult().getList();
									list.addAll(counselor);
								}
								cAdapter.notifyDataSetChanged();
							}else{
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						listview_all_agent.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						listview_all_agent.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_back:
				finish();
				break;

			default:
				break;
		}
	}

	@Override
	public void onLoad() {
		
		initData(++page);
	}
}
