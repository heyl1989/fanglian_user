package com.flzc.fanglian.ui.fragment;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseFragment;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.FangLianQuanBean;
import com.flzc.fanglian.model.FangLianQuanBean.Result.FangLianQuanList;
import com.flzc.fanglian.ui.adapter.FangLianQuanFragmentAdapter;
import com.flzc.fanglian.ui.me.FangLianQuanInfoActivity;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: FLQuanUsedFragment
 * @Description: 房链券列表已使用 碎片
 * @author: 薛东超
 * @date: 2016年3月9日 上午11:27:42
 */

public class FLQuanUsedFragment extends BaseFragment implements
		OnLoaderListener {

	private View view;
	private LoadListView lv_flquan_info;

	private List<FangLianQuanList> list = new ArrayList<FangLianQuanList>();
	private FangLianQuanFragmentAdapter adapter;

	private SwipeRefreshLayout flquan_swipeRefreshLayout;
	private int page = 1;
	private boolean isReflsh = false;
	private View headView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_flquan, container, false);
		list.clear();
		initView();
		initData();
		return view;
	}

	private void initView() {
		lv_flquan_info = (LoadListView) view.findViewById(R.id.lv_flquan_info);
		headView = LayoutInflater.from(mActivity).inflate(
				R.layout.layout_search_none, null);
		TextView content = (TextView) headView.findViewById(R.id.tv_text);
		content.setText("矮油~\n一张券券也没有");
		lv_flquan_info.setLoaderListener(this);
		adapter = new FangLianQuanFragmentAdapter(mActivity, list);
		lv_flquan_info.setAdapter(adapter);
		lv_flquan_info.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FangLianQuanList fangLianQuanList = null;
				TextView textView = (TextView) view
						.findViewById(R.id.tv_flquan_com_deadtime);
				if (textView == null) {
					return;
				}
				fangLianQuanList = (FangLianQuanList) textView.getTag();
				if (fangLianQuanList == null) {
					return;
				}
				Intent intent = new Intent(mActivity,
						FangLianQuanInfoActivity.class);
				intent.putExtra("sourceName", fangLianQuanList.getSourceName());
				intent.putExtra("ticketId", fangLianQuanList.getId());
				startActivity(intent);
			}
		});

		// 下拉刷新部分
		flquan_swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.flquan_swipeRefreshLayout);
		// 设置卷内的颜色
		flquan_swipeRefreshLayout.setColorSchemeResources(
				android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		flquan_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				isReflsh = true;
				page = 1;
				getFangLianQuanData(page);
			}
		});
	}

	private void initData() {

		getFangLianQuanData(1);
	}

	/**
	 * 
	 * @Title: getFangLianQuanData
	 * @Description: 请求网络得到数据集
	 * @return: void
	 */
	private void getFangLianQuanData(final int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		// 1代表已使用
		map.put("status", "1");
		map.put("page", page + "");
		SimpleRequest<FangLianQuanBean> request = new SimpleRequest<FangLianQuanBean>(
				API.USERTICKETLIST, FangLianQuanBean.class,
				new Listener<FangLianQuanBean>() {
					@Override
					public void onResponse(FangLianQuanBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if(isReflsh){
									list.clear();
									isReflsh = false;
								}
								if(response.getResult() != null){
									list.addAll(response.getResult().list);
									if(null != headView){
										lv_flquan_info.removeHeaderView(headView);
									}
									if (list.size() == 0) {
										lv_flquan_info.addHeaderView(headView);
									}
									adapter.notifyDataSetChanged();
								}
							}else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						lv_flquan_info.loadComplete();
						flquan_swipeRefreshLayout.setRefreshing(false);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
						lv_flquan_info.loadComplete();
						flquan_swipeRefreshLayout.setRefreshing(false);
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	@Override
	public void onLoad() {
		getFangLianQuanData(++page);
	}
}
