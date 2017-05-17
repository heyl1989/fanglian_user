package com.flzc.fanglian.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.flzc.fanglian.model.UserJoinBean;
import com.flzc.fanglian.model.UserJoinBean.Result;
import com.flzc.fanglian.ui.adapter.MyParticipateListAdapter;
import com.flzc.fanglian.ui.agent_activity.AgentRecommendActivity;
import com.flzc.fanglian.ui.bidding_activity.BiddingActivity;
import com.flzc.fanglian.ui.everyday_special_activity.EveryDaySpecialActivity;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: MyParticipateListFragment
 * @Description: 我参与的活动 列表 碎片
 * @author: Tien.
 * @date: 2016年3月3日 下午7:42:54
 */
public class MyParticipateListFragment extends BaseFragment implements OnItemClickListener,OnLoaderListener{
	
	private View view;
	private LoadListView listview_participate;
	private MyParticipateListAdapter pAdapter;
	private List<Result> totalList = new ArrayList<Result>();
	private int page = 1; //加载更多
	private SwipeRefreshLayout swipeRefreshLayout;
	private boolean isRefresh = false;
	private View headView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_participate, container,
				false);
		initItem();
		initListener();
		totalList.clear();
		initData(1);
		pullTORefresh();
		return view;
	}
	
	private void pullTORefresh() {
		// 下拉刷新部分
		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
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
				isRefresh = true;
				page = 1;
				initData(page);
			}
		});
	}

	private void initItem() {
		listview_participate = (LoadListView) view
				.findViewById(R.id.listview_myParticipate_fragment);
		headView = LayoutInflater.from(mActivity).inflate(
				R.layout.layout_search_none, null);
		TextView content = (TextView) headView.findViewById(R.id.tv_text);
		content.setText("一个活动也不参加\n房小链带您去看看吧~GO!");
		listview_participate.setLoaderListener(this);
		pAdapter = new MyParticipateListAdapter(getActivity(), totalList);
		listview_participate.setAdapter(pAdapter);
	}

	private void initListener() {
		listview_participate.setOnItemClickListener(this);

	}

	private void initData(int page){
		loadingDialog.showDialog();
		Bundle bundle = getArguments();
		String actListType = bundle.getString("actListType");
		Map<String,String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("actStatus", actListType);
		map.put("page", page + "");
		
		SimpleRequest<UserJoinBean> request = new SimpleRequest<UserJoinBean>(API.QUERYUSERJOINACTIVITY, UserJoinBean.class,
				new Listener<UserJoinBean>() {
					@Override
					public void onResponse(UserJoinBean response) {
						if(response != null){
							if(0 == response.getStatus()){
								if(isRefresh == true){
									totalList.clear();
									isRefresh = false;
								}
								List<Result> list = response.getResult();
								for(Result result : list){
									if(!TextUtils.equals(result.getActivityType(), "3904")){
										totalList.add(result);
									}
								}
								if(null != headView){
									listview_participate.removeHeaderView(headView);
								}
								if (totalList.size() == 0) {
									listview_participate.addHeaderView(headView);
								}
								pAdapter.notifyDataSetChanged();
							}
						}
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						listview_participate.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						listview_participate.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				} ,map);
		UserApplication.getInstance().addToRequestQueue(request);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(totalList.size() == 0){
			return;
		}
		int actType = Integer.parseInt(totalList.get(position).getActivityType()) ;
		Intent intent = new Intent();
		String activityPoolId = totalList.get(position).getActivityPoolId();
		String buildingId = totalList.get(position).getBuildingId();
		String activityId = totalList.get(position).getActivityId();
		String remind = totalList.get(position).getRemind();
		String remindId = totalList.get(position).getRemindId();
		
		switch (actType) {
		case 3901: 
//			goOthers(BiddingActivity.class);
			intent.setClass(mActivity, BiddingActivity.class);
			intent.putExtra("activityPoolId", activityPoolId);
			intent.putExtra("buildingId", buildingId);
			intent.putExtra("activityId", activityId);
			intent.putExtra("remind", remind);
			intent.putExtra("remindId", remindId);
			startActivity(intent);
			break;
		case 3902:
			intent.setClass(mActivity, EveryDaySpecialActivity.class);
			intent.putExtra("activityPoolId", activityPoolId);
			intent.putExtra("buildingId", buildingId);
			intent.putExtra("activityId", activityId);
			intent.putExtra("remind", remind);
			intent.putExtra("remindId", remindId);
			startActivity(intent);
			break;
		case 3903:
			intent.setClass(mActivity, AgentRecommendActivity.class);
			intent.putExtra("activityPoolId", activityPoolId);
			intent.putExtra("buildingId", buildingId);
			intent.putExtra("activityId", activityId);
			intent.putExtra("remind", remind);
			intent.putExtra("remindId", remindId);
			startActivity(intent);
			break;
		}		
	}

	@Override
	public void onLoad() {
		initData(++page);
		
	}

}
