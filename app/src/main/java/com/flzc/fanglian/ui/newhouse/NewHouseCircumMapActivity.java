package com.flzc.fanglian.ui.newhouse;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.util.overlayUtil.PoiOverlay;

public class NewHouseCircumMapActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener, OnGetPoiSearchResultListener,
		OnGetGeoCoderResultListener {

	private RelativeLayout rl_back;
	private TextView tv_title;
	private RadioGroup rg_bottom;
	private BaiduMap mBaiduMap;
	private PoiSearch mPoiSearch;
	private MapView mapView;
	private GeoCoder mSearch;
	private LatLng ptCenter;
	private double lon;
	private double lat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newhouse_circummap);
		if (!TextUtils.isEmpty(getIntent().getStringExtra("lon"))
				&& !TextUtils.equals("null", getIntent().getStringExtra("lon"))
				&& !TextUtils.isEmpty(getIntent().getStringExtra("lat"))
				&& !TextUtils.equals("null", getIntent().getStringExtra("lon"))) {
			lon = Double.parseDouble(getIntent().getStringExtra("lon"));
			lat = Double.parseDouble(getIntent().getStringExtra("lat"));
		}
		// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		initView();
	}

	private void initView() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("位置及周边");
		// 地图
		mapView = (MapView) findViewById(R.id.bmp_bmapView);
		int childCount = mapView.getChildCount();
		View zoom = null;
		for (int i = 0; i < childCount; i++) {
			View child = mapView.getChildAt(i);
			if (child instanceof ZoomControls) {
				zoom = child;
				break;
			}
		}
		zoom.setVisibility(View.GONE);
		// 去掉地图logo
		mapView.removeViewAt(1);
		mBaiduMap = mapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
		// 反Geo搜索
		ptCenter = new LatLng(lat, lon);
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
		// 下面的选择
		rg_bottom = (RadioGroup) findViewById(R.id.rg_bottom);
		rg_bottom.setOnCheckedChangeListener(this);
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
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_bus:
			mPoiSearch.searchNearby(new PoiNearbySearchOption()
					.location(new LatLng(lat, lon)).keyword("地铁站").radius(1000)
					.pageCapacity(50).pageNum(0));
			break;
		case R.id.rb_school:
			mPoiSearch.searchNearby(new PoiNearbySearchOption()
					.location(new LatLng(lat, lon)).keyword("学校").radius(1000)
					.pageCapacity(50).pageNum(0));
			break;
		case R.id.rb_hospital:
			mPoiSearch.searchNearby(new PoiNearbySearchOption()
					.location(new LatLng(lat, lon)).keyword("医院").radius(1000)
					.pageCapacity(50).pageNum(0));
			break;

		}

	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			showTost("抱歉，未找到结果");
		} else {
			showTost(result.getName() + ": " + result.getAddress());
		}

	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			mBaiduMap.clear();
			showTost("未找到结果");
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			mBaiduMap.addOverlay(new MarkerOptions().position(ptCenter).icon(
					BitmapDescriptorFactory
							.fromResource(R.drawable.icon_my_loction)));
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			showTost(strInfo);
		}

	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
					.poiUid(poi.uid));
			// }
			return true;
		}
	}

	@Override
	protected void onDestroy() {
		mPoiSearch.destroy();
		mapView.onDestroy();
		mSearch.destroy();
		System.gc();
		super.onDestroy();
	}

	/**
	 * GEO 搜索
	 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_my_loction)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
	}

}
