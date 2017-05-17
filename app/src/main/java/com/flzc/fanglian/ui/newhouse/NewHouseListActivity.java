package com.flzc.fanglian.ui.newhouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
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
import com.flzc.fanglian.model.AreaBean;
import com.flzc.fanglian.model.AreaBean.Result.Areas;
import com.flzc.fanglian.model.NewHouseListBean;
import com.flzc.fanglian.model.NewHouseListBean.Result;
import com.flzc.fanglian.model.NewHouseListBean.Result.Tags;
import com.flzc.fanglian.model.SortTypeBean;
import com.flzc.fanglian.model.SortTypeBean.SortList;
import com.flzc.fanglian.ui.adapter.AreaLeftAdapter;
import com.flzc.fanglian.ui.adapter.NewHouseListAdapter;
import com.flzc.fanglian.ui.adapter.SortOptionAdapter;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: NewHouseListActivity
 * @Description: 新房列表页
 * @author: Tien.
 * @date: 2016年3月7日 下午5:10:27
 */
public class NewHouseListActivity extends BaseActivity implements
		OnClickListener, OnLoaderListener {
	private RelativeLayout rl_back;
	private TextView tv_title;
	private LoadListView listview_newHouse;
	private NewHouseListAdapter nAdapter;
	private RelativeLayout layout_areaOption_newHouseList,
			layout_sortOption_newHouseList;
	private TextView tv_areaOption_newHouseList, tv_sortOption_newHouseList;
	private ImageView img_areaOption_newHouseList, img_sortOption_newHouseList;
	private ListView listview_areaOption_popupWindow;
	private SortOptionAdapter optionAdapter;
	// 数据
	private List<Areas> areaList = new ArrayList<Areas>();
	private List<NewHouseListBean.Result> newHouseList = new ArrayList<NewHouseListBean.Result>();
	private int areaId = 0;
	private int sortType = 0;
	private String cityId;
	private View ly_pop_area;
	private View ly_pop_sort;
	private ListView lvLeft;
	private SwipeRefreshLayout swipeRefreshLayout;
	protected int page = 1;
	private boolean isClear = true;
	private AreaLeftAdapter areaAdapter;
	private List<SortList> sortList = new ArrayList<SortList>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newhouse_list);
		cityId = UserInfoData.getData(Constant.LOC_CITY_ID, "52");
		initItem();
		pullTORefresh();
		initPop();
		initCityData();
		initNewHouseList(0, 0, 1);
	}

	private void pullTORefresh() {
		// 下拉刷新部分
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		// 设置卷内的颜色
		swipeRefreshLayout.setColorSchemeResources(android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		// 设置下拉刷新监听
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				isClear = true;
				page = 1;
				initNewHouseList(sortType, areaId, page);
			}
		});
	}

	private void initItem() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("新房");
		// 筛选布局
		ly_pop_area = findViewById(R.id.ly_pop_area);
		ly_pop_area.findViewById(R.id.img_area_bg).setOnTouchListener(
				new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						return true;
					}
				});
		ly_pop_sort = findViewById(R.id.ly_pop_sort);
		ly_pop_sort.findViewById(R.id.img_sort_bg).setOnTouchListener(
				new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						return true;
					}
				});

		layout_sortOption_newHouseList = (RelativeLayout) findViewById(R.id.layout_sortOption_newHouseList);
		layout_sortOption_newHouseList.setOnClickListener(this);
		layout_areaOption_newHouseList = (RelativeLayout) findViewById(R.id.layout_areaOption_newHouseList);
		layout_areaOption_newHouseList.setOnClickListener(this);

		layout_areaOption_newHouseList.setTag("close");
		layout_sortOption_newHouseList.setTag("close");

		tv_areaOption_newHouseList = (TextView) findViewById(R.id.tv_areaOption_newHouseList);
		tv_sortOption_newHouseList = (TextView) findViewById(R.id.tv_sortOption_newHouseList);

		img_areaOption_newHouseList = (ImageView) findViewById(R.id.img_areaOption_newHouseList);
		img_sortOption_newHouseList = (ImageView) findViewById(R.id.img_sortOption_newHouseList);

		listview_newHouse = (LoadListView) findViewById(R.id.listview_newHouse);
		listview_newHouse.setLoaderListener(this);
		nAdapter = new NewHouseListAdapter(NewHouseListActivity.this,
				newHouseList);
		listview_newHouse.setAdapter(nAdapter);
		listview_newHouse.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				List<Tags> tags = new ArrayList<Tags>();
				tags.addAll(newHouseList.get(arg2).getTags());
				String tagsword = "";
				for (Tags tag : tags) {
					tagsword += tag.getName() + " ";
				}
				Intent intent = new Intent(NewHouseListActivity.this,
						NewHouseDetailActivity.class);
				intent.putExtra("buildingId", newHouseList.get(arg2).getId()
						+ "");
				intent.putExtra("tags", tagsword);
				startActivity(intent);
			}
		});

	}

	/**
	 * 
	 * @Title: initCityData
	 * @Description: 请求地区数据
	 * @return: void
	 */
	private void initCityData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cityId", UserInfoData.getData(Constant.LOC_CITY_ID, "52"));
		SimpleRequest<AreaBean> req = new SimpleRequest<AreaBean>(
				API.QUERY_SCREENING_CONDITIONS, AreaBean.class,
				new Listener<AreaBean>() {

					@Override
					public void onResponse(AreaBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								List<Areas> areas = response.getResult()
										.getAreas();
								AreaBean areaBean = new AreaBean();
								AreaBean.Result result = areaBean.new Result();
								AreaBean.Result.Areas area = result.new Areas();
								area.setAreaName("全部");
								area.setId(0);
								areaList.add(area);
								areaList.addAll(areas);
								areaAdapter.notifyDataSetChanged();
							}
						}

					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	/**
	 * 
	 * @Title: initNewHouseList
	 * @Description: 请求新房列表
	 * @param i
	 * @return: void
	 */
	private void initNewHouseList(int sortType, int areaId, int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("areaId", areaId + "");
		map.put("sortType", sortType + "");
		map.put("page", page + "");
		map.put("cityId", cityId);
		SimpleRequest<NewHouseListBean> req = new SimpleRequest<NewHouseListBean>(
				API.LIST_HOUSE, NewHouseListBean.class,
				new Listener<NewHouseListBean>() {
					@Override
					public void onResponse(NewHouseListBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								if (isClear) {
									newHouseList.clear();
								}
								List<Result> list = response.getResult();
								newHouseList.addAll(list);
								nAdapter.notifyDataSetChanged();
								if(isClear){
									listview_newHouse.setSelection(0);
								}

							} else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						listview_newHouse.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						// 通知listview加载完毕
						listview_newHouse.loadComplete();
						swipeRefreshLayout.setRefreshing(false);
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	/**
	 * 初始化地域选择和条件筛选
	 * 
	 * @param popType
	 */
	private void initPop() {
		lvLeft = (ListView) findViewById(R.id.listview_left_moreOption_popupWindow);
		areaAdapter = new AreaLeftAdapter(this, areaList);
		lvLeft.setAdapter(areaAdapter);
		// final ListView lvRight = (ListView)
		// findViewById(R.id.listview_right_moreOption_popupWindow);
		lvLeft.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				areaList.get(position).setSelected(true);
				areaAdapter.notifyDataSetChanged();
				dissmissArea();
				isClear = true;
				areaId = areaList.get(position).getId();
				page = 1;
				initNewHouseList(sortType, areaId, page);
			}
		});

		SortTypeBean sortBean1 = new SortTypeBean();
		SortTypeBean.SortList sortMap1 = sortBean1.new SortList();
		sortMap1.setSortName("按发布时间");
		sortList.add(sortMap1);
		SortTypeBean sortBean2 = new SortTypeBean();
		SortTypeBean.SortList sortMap2 = sortBean2.new SortList();
		sortMap2.setSortName("按价格");
		sortList.add(sortMap2);

		listview_areaOption_popupWindow = (ListView) findViewById(R.id.listview_areaOption_popupWindow);
		optionAdapter = new SortOptionAdapter(NewHouseListActivity.this,
				sortList, 1);
		listview_areaOption_popupWindow.setAdapter(optionAdapter);
		listview_areaOption_popupWindow
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						for(int i=0;i<sortList.size();i++){
							if(position == i){
								sortList.get(i).setSelect(true);
							}else{
								sortList.get(i).setSelect(false);
							}
						}
						optionAdapter.notifyDataSetChanged();
						dissmissSort();
						isClear = true;
						sortType = position;
						page = 1;
						initNewHouseList(sortType, areaId, page);

					}
				});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.layout_areaOption_newHouseList:
			if (ly_pop_sort.getVisibility() == View.VISIBLE) {
				dissmissSort();
			}
			if (ly_pop_area.getVisibility() == View.VISIBLE) {
				dissmissArea();
			} else {
				showArea();
			}
			break;
		case R.id.layout_sortOption_newHouseList:
			if (ly_pop_area.getVisibility() == View.VISIBLE) {
				dissmissArea();
			}
			if (ly_pop_sort.getVisibility() == View.VISIBLE) {
				dissmissSort();
			} else {
				showSort();
			}
			break;

		}
	}

	/**
	 * 显示排序
	 */
	private void showSort() {
		tv_sortOption_newHouseList.setTextColor(Color.parseColor("#dc4242"));
		img_sortOption_newHouseList.setImageResource(R.drawable.arrow_up_red);
		ly_pop_sort.setVisibility(View.VISIBLE);

	}

	/**
	 * 显示地域
	 */
	private void showArea() {
		tv_areaOption_newHouseList.setTextColor(Color.parseColor("#dc4242"));
		img_areaOption_newHouseList.setImageResource(R.drawable.arrow_up_red);
		ly_pop_area.setVisibility(View.VISIBLE);

	}

	/**
	 * 消失地域
	 */
	private void dissmissArea() {
		tv_areaOption_newHouseList.setTextColor(Color.parseColor("#666666"));
		img_areaOption_newHouseList
				.setImageResource(R.drawable.icon_drop_button);
		ly_pop_area.setVisibility(View.GONE);

	}

	/**
	 * 消失排序
	 */
	private void dissmissSort() {
		tv_sortOption_newHouseList.setTextColor(Color.parseColor("#666666"));
		img_sortOption_newHouseList
				.setImageResource(R.drawable.icon_drop_button);
		ly_pop_sort.setVisibility(View.GONE);
	}

	@Override
	public void onLoad() {
		isClear = false;
		++page;
		initNewHouseList(sortType, areaId, page);
	}

}
