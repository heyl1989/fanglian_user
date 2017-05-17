package com.flzc.fanglian.ui.newhouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.flzc.fanglian.model.NewHouseTypeBean;
import com.flzc.fanglian.model.NewHouseTypeBean.Result.HouseTypes;
import com.flzc.fanglian.ui.adapter.NewHouseResourceAdapter;
import com.flzc.fanglian.util.CommonUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

/**
 * 
 * @ClassName: NewHouseResourceActivity
 * @Description: 新房 房源列表页 从新房详情点击查看更多户型进入
 * @author: Tien.
 * @date: 2016年3月8日 下午1:31:22
 */
public class NewHouseResourceActivity extends BaseActivity implements
		OnClickListener {
	private RelativeLayout rl_back;
	private TextView titleName;
	private PullToRefreshGridView gridView_resource;
	private NewHouseResourceAdapter nAdapter;
	private List<HouseTypes> houseTypes = new ArrayList<HouseTypes>();
	private String unitPrice;
	private String buildingId;
	private int page = 1;
	protected boolean isRefresh = false;
	private GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newhouse_resourcelist);
		buildingId = getIntent().getStringExtra("buildingId");
		unitPrice = getIntent().getStringExtra("unitPrice");
		initItem();
		initData(page);
	}

	private void initItem() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		titleName = (TextView) findViewById(R.id.tv_title);
		titleName.setText("楼盘户型");
		// 列表
		gridView_resource = (PullToRefreshGridView) findViewById(R.id.gridView_newHouse_resource);
		mGridView = gridView_resource.getRefreshableView();
		gridView_resource.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
				isRefresh = true;
				page = 1;
				initData(page);
			}
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
				initData(++page);
			}
		});
		mGridView.setNumColumns(2);
		int tenDp = CommonUtils.dp2px(this, 10);
		CommonUtils.setMargins(mGridView, tenDp, 0, tenDp, 0);
		mGridView.setHorizontalSpacing(tenDp);
		mGridView.setVerticalSpacing(tenDp);
		nAdapter = new NewHouseResourceAdapter(NewHouseResourceActivity.this,
				houseTypes, unitPrice);
		mGridView.setAdapter(nAdapter);

	}

	private void initData(int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildingId", buildingId);
		map.put("page", page + "");
		SimpleRequest<NewHouseTypeBean> req = new SimpleRequest<NewHouseTypeBean>(
				API.QUERY_HOUSE_TYPES, NewHouseTypeBean.class,
				new Listener<NewHouseTypeBean>() {
					@Override
					public void onResponse(NewHouseTypeBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								if(isRefresh){
									houseTypes.clear();
									isRefresh = false;
								}
								// 在添加数据之前删除最后的伪造item
								List<HouseTypes> responseResult = response
										.getResult().getHouseTypes();
								houseTypes.addAll(responseResult);
								nAdapter.notifyDataSetChanged();
							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						gridView_resource.onRefreshComplete();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
						loadingDialog.dismissDialog();
						gridView_resource.onRefreshComplete();
						
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);
	}

	@Override
	public void onClick(View v) {
		if (v == rl_back) {
			finish();
		}
	}
}
