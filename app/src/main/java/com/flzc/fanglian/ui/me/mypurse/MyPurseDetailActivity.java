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
import com.flzc.fanglian.model.AccountDetail;
import com.flzc.fanglian.model.AccountDetail.Result;
import com.flzc.fanglian.ui.adapter.PurseDetailAdapter;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: MyPurseDetailActivity
 * @Description: 明细记录
 * @author: LU
 * @date: 2016-3-4 下午3:12:03
 */
public class MyPurseDetailActivity extends BaseActivity implements
		OnClickListener,OnLoaderListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private LoadListView lv_purse_detail;

	private PurseDetailAdapter adapter;
	private List<Result> listData = new ArrayList<Result>();

	private SwipeRefreshLayout mypurse_detail_swipeRefreshLayout;
	private int page = 1;
	private boolean isReflsh = false;
	private View headView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypurse_detail);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("明细记录");
		// 明细列表
		lv_purse_detail = (LoadListView) findViewById(R.id.lv_purse_detail);
		headView = LayoutInflater.from(this).inflate(
				R.layout.layout_search_none, null);
		TextView content = (TextView) headView.findViewById(R.id.tv_text);
		content.setText("一分钱也不花\nOMG 这还是我认识的你吗⊙_⊙ ");
		lv_purse_detail.setLoaderListener(this);
		adapter = new PurseDetailAdapter(MyPurseDetailActivity.this, listData);
		lv_purse_detail.setAdapter(adapter);

		// 下拉刷新部分
		mypurse_detail_swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mypurse_detail_swipeRefreshLayout);
		// 设置卷内的颜色
		mypurse_detail_swipeRefreshLayout.setColorSchemeResources(
				android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mypurse_detail_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				isReflsh = true;
				page = 1;
				getAccountDetailData(page);
			}
		});

	}

	private void initData() {

		getAccountDetailData(1);
	}

	/**
	 * 
	 * @Title: getAccountDetailData
	 * @Description: 流水明细
	 * @param pageNo
	 * @return: void
	 */
	private void getAccountDetailData(int pageNo) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("pageNo", pageNo + "");
		map.put("pageSize", "15");// 每页显示10条

		SimpleRequest<AccountDetail> request = new SimpleRequest<AccountDetail>(
				API.ACCOUNT_DETAIL, AccountDetail.class,
				new Listener<AccountDetail>() {
					@Override
					public void onResponse(AccountDetail response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if(isReflsh){
									listData.clear();
									isReflsh = false;
								}
								List<Result> list = response.getResult();
								listData.addAll(list);
								if(null != headView){
									lv_purse_detail.removeHeaderView(headView);
								}
								if (listData.size() == 0) {
									lv_purse_detail.addHeaderView(headView);
								}
								adapter.notifyDataSetChanged();
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						lv_purse_detail.loadComplete();
						mypurse_detail_swipeRefreshLayout.setRefreshing(false);
					}
				},new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						lv_purse_detail.loadComplete();
						mypurse_detail_swipeRefreshLayout.setRefreshing(false);
						showTost(getResources().getString(R.string.net_error));
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

		default:
			break;
		}

	}

	@Override
	public void onLoad() {
		getAccountDetailData(++page);
	}

}
