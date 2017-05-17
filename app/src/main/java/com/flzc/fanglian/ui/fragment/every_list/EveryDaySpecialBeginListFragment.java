package com.flzc.fanglian.ui.fragment.every_list;

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
import com.flzc.fanglian.model.AtyListBean;
import com.flzc.fanglian.model.AtyListBean.Result;
import com.flzc.fanglian.model.AtyListBean.Result.BuildActivity;
import com.flzc.fanglian.ui.adapter.DiscountActivityListAdapter;
import com.flzc.fanglian.ui.agent_activity.AgentRecommendActivity;
import com.flzc.fanglian.ui.bidding_activity.BiddingActivity;
import com.flzc.fanglian.ui.everyday_special_activity.EveryDaySpecialActivity;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: AtyListFragment
 * @Description: 活动列表的 碎片
 * @author: Tien.
 * @date: 2016年3月11日 下午3:16:29
 */
public class EveryDaySpecialBeginListFragment extends BaseFragment implements
		OnLoaderListener {

	private View view;
	private LoadListView lv_atylist_activityinfo;
	private DiscountActivityListAdapter adapter;
	private String listType;// 特惠房3900，竞拍3901，天天特价3902，优惠房3903，
	protected List<Result> data = new ArrayList<AtyListBean.Result>();
	private String tokenId;

	private SwipeRefreshLayout aty_list_swipeRefreshLayout;
	private int page = 1;
	private boolean isReflsh = true;
	public int sortType = 0;//1按照发布时间，2按价格，3按开盘时间
	public boolean isSort = true;//是否排序
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_aty_list, container, false);
		data.clear();
		initItem();
		listType = getArguments().getString("listType");
		tokenId = UserInfoData.getData(Constant.TOKEN, "");
		initData(sortType + "",page);
		return view;
	}

	private void initItem() {
		lv_atylist_activityinfo = (LoadListView) view
				.findViewById(R.id.lv_atylist_activityinfo);
		lv_atylist_activityinfo.setLoaderListener(this);
		adapter = new DiscountActivityListAdapter(mActivity, data);
		lv_atylist_activityinfo.setAdapter(adapter);

		lv_atylist_activityinfo
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						BuildActivity activity = data.get(arg2).getActivity();
						String activityType = activity.getActivityType() + "";
						String activityPoolId = activity.getId() + "";
						String buildingId = data.get(arg2).getId() + "";
						String activityId = activity.getActivityId() + "";
						String remind = data.get(arg2).getRemaind();
						String remindId = data.get(arg2).getRemaindId();

						if (activityType.equals("3901")) {
							Intent intent = new Intent(mActivity,
									BiddingActivity.class);
							intent.putExtra("activityPoolId", activityPoolId);
							intent.putExtra("buildingId", buildingId);
							intent.putExtra("activityId", activityId);
							intent.putExtra("remind", remind);
							intent.putExtra("remindId", remindId);
							startActivity(intent);
						} else if (activityType.equals("3902")) {
							Intent intent = new Intent(mActivity,
									EveryDaySpecialActivity.class);
							intent.putExtra("activityPoolId", activityPoolId);
							intent.putExtra("buildingId", buildingId);
							intent.putExtra("activityId", activityId);
							intent.putExtra("remind", remind);
							intent.putExtra("remindId", remindId);
							startActivity(intent);
						} else if (activityType.equals("3903")) {
							Intent intent = new Intent(mActivity,
									AgentRecommendActivity.class);
							intent.putExtra("activityPoolId", activityPoolId);
							intent.putExtra("buildingId", buildingId);
							intent.putExtra("activityId", activityId);
							intent.putExtra("remind", remind);
							intent.putExtra("remindId", remindId);
							startActivity(intent);
						}
					}
				});
		// 下拉刷新部分
		aty_list_swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.aty_list_swipeRefreshLayout);
		// 设置卷内的颜色
		aty_list_swipeRefreshLayout.setColorSchemeResources(
				android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		aty_list_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				isReflsh = true;
				page = 1;
				initData(sortType + "", page);
			}
		});
	}

	/**
	 * 
	 * @Title: initData
	 * @Description: TODO activity中的id(是下个页面的activityPoolId) id ==
	 *               下个页面的buildingId
	 * @param sortType
	 *            (1按照发布时间，2按价格，3按开盘时间) activityStatus(0全部，1未开始，2进行中，3已结束)
	 * @return: void
	 */
	public void initData(String sortType,int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("cityId", UserInfoData.getData(Constant.LOC_CITY_ID, "52"));
		map.put("activityType", listType);
		map.put("activityStatus", "2");
		map.put("sortType", sortType);
		map.put("tokenId", tokenId);
		map.put("page", page + "");
		SimpleRequest<AtyListBean> req = new SimpleRequest<AtyListBean>(
				API.LIST_ACTIVITY, AtyListBean.class,
				new Listener<AtyListBean>() {
					@Override
					public void onResponse(AtyListBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if(isReflsh){
									data.clear();
								}
								if(isSort){
									data.clear();
								}
								data.addAll(response.getResult());
								adapter.notifyDataSetChanged();
								if(isSort){
									lv_atylist_activityinfo.setSelection(0);
								}
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						lv_atylist_activityinfo.loadComplete();
						aty_list_swipeRefreshLayout.setRefreshing(false);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						lv_atylist_activityinfo.loadComplete();
						aty_list_swipeRefreshLayout.setRefreshing(false);
					}
				},map);
		UserApplication.getInstance().addToRequestQueue(req);
	}

	@Override
	public void onLoad() {
		isReflsh = false;
		isSort = false;
		initData(sortType + "", ++page);
	}

}
