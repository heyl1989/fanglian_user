package com.flzc.fanglian.ui.agent_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
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
import com.flzc.fanglian.model.AgentHouseTypeBean;
import com.flzc.fanglian.model.AgentHouseTypeBean.Result;
import com.flzc.fanglian.ui.adapter.BuildingTypeAdapter;

/**
 * 
 * @ClassName: BuildingTypeActivity
 * @Description: 楼盘户型页面
 * @author: 薛东超
 * @date: 2016年3月5日 下午4:27:26
 */
public class BuildingTypeActivity extends BaseActivity implements
		OnClickListener{

	private GridView gv_bdtype_list_type;
	private TextView tv_title;
	private List<Result> result = new ArrayList<Result>();
	private RelativeLayout rl_back;
	private String activityId;
	private int actStatus;
	private String activityPoolId;
	private String buildingId;
	private BuildingTypeAdapter houseTypeAdapter;
	protected boolean isLoadMore = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_type);
		activityId = getIntent().getExtras().getString("activityId", "");
		actStatus = getIntent().getExtras().getInt("actStatus", 1);
		activityPoolId = getIntent().getExtras()
				.getString("activityPoolId", "");
		buildingId = getIntent().getExtras().getString("buildingId", "");
		initView();
		initData();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("楼盘户型");
		gv_bdtype_list_type = (GridView) findViewById(R.id.gv_bdtype_list_type);
		houseTypeAdapter = new BuildingTypeAdapter(BuildingTypeActivity.this,
				result, actStatus);
		gv_bdtype_list_type.setAdapter(houseTypeAdapter);
		gv_bdtype_list_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (actStatus == 2) {
					String ticketAmount = result.get(arg2).getTicketAmount()
							+ "";
					String houseSouseID = result.get(arg2).getSourceId();
					Intent intent = new Intent(BuildingTypeActivity.this,
							AgentRecommendHouseInfoActivity.class);
					intent.putExtra("ticketAmount", ticketAmount);
					intent.putExtra("houseSouseID", houseSouseID);
					intent.putExtra("buildingId", buildingId);
					intent.putExtra("activityPoolId", activityPoolId);
					startActivity(intent);
				} else {
					showTost("活动还没有开始");
				}

			}
		});

	}

	private void initData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityId", activityId);
		SimpleRequest<AgentHouseTypeBean> req = new SimpleRequest<AgentHouseTypeBean>(
				API.QUERY_HOUSE_SOURCE_LIST, AgentHouseTypeBean.class,
				new Listener<AgentHouseTypeBean>() {
					@Override
					public void onResponse(AgentHouseTypeBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								List<Result> responseResult = response.getResult();
								result.addAll(responseResult);
								houseTypeAdapter.notifyDataSetChanged();
							
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
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
}
