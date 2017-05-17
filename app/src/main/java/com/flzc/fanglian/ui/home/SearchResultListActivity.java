package com.flzc.fanglian.ui.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.SearchBean;
import com.flzc.fanglian.model.SearchBean.Result;
import com.flzc.fanglian.model.SearchBean.Result.Tags;
import com.flzc.fanglian.model.SearchHintBean;
import com.flzc.fanglian.model.SearchHintBean.Result.SearchList;
import com.flzc.fanglian.ui.adapter.SearchAdapter;
import com.flzc.fanglian.ui.adapter.SearchResultListAdapter;
import com.flzc.fanglian.ui.newhouse.NewHouseDetailActivity;
import com.flzc.fanglian.util.StringUtils;
import com.flzc.fanglian.view.LoadListView;
import com.flzc.fanglian.view.LoadListView.OnLoaderListener;

/**
 * 
 * @ClassName: SearchResultListActivity
 * @Description: 搜索结果 列表页面 UI 样式与新房列表一致 列表共用一个适配器
 * @author: Tien.
 * @date: 2016年3月10日 下午3:18:31
 */
public class SearchResultListActivity extends BaseActivity implements
		OnClickListener, OnLoaderListener {
	private RelativeLayout rl_back;
	private TextView titleName;
	private RelativeLayout layout_search_newhouse_list,
			layout_titleLayout_newHouseList, layout_titleLayout_search,
			layout_search_newhouse_cancle;
	private EditText ed_search_newHouseList;

	private LoadListView listview_search_result;
	private SearchResultListAdapter nAdapter;
	private List<Result> totalList = new ArrayList<Result>();

	private ListView listview_search_hintresult;// 搜索提示
	private List<SearchList> dataListHint = new ArrayList<SearchList>();
	private SearchAdapter searchAdapter;

	private String keywords;
	private String cityId;

	private SwipeRefreshLayout search_result_swipeRefreshLayout;
	private int page = 1;
	private boolean isReflsh = false;
	private View ly_serrch_none;
	private InputMethodManager imm ; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		initItem();
		initData();
	}

	private void initItem() {
		//标题
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		titleName = (TextView) findViewById(R.id.tv_title);
		titleName.setText("搜索结果");
		//空页面
		ly_serrch_none = findViewById(R.id.ly_serrch_none);

		// 搜索提示的listview
		listview_search_hintresult = (ListView) findViewById(R.id.listview_search_hintresult);
		searchAdapter = new SearchAdapter(SearchResultListActivity.this,
				dataListHint);
		listview_search_hintresult.setAdapter(searchAdapter);
		listview_search_hintresult
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						SearchList searchList = null;
						TextView textView;
						textView = (TextView) view
								.findViewById(R.id.tv_search_seaarch_result);
						if (textView == null) {
							return;
						}
						searchList = (SearchList) textView.getTag();
						if (searchList == null) {
							return;
						}
						//显示搜索按钮
						layout_titleLayout_newHouseList
								.setVisibility(View.VISIBLE);
						//输入楼盘名称编辑框消失
						layout_titleLayout_search.setVisibility(View.GONE);
						ed_search_newHouseList.setText("");
						if(null != imm){
							imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
						}
						String buildingId = searchList.getBuildingId();
						Intent intent = new Intent(SearchResultListActivity.this,
								NewHouseDetailActivity.class);
						intent.putExtra("buildingId", buildingId);
						startActivity(intent);
						listview_search_hintresult.setVisibility(View.GONE);
					}

				});

		// 搜索按钮
		layout_search_newhouse_list = (RelativeLayout) findViewById(R.id.layout_search_newhouse_list);
		layout_search_newhouse_list.setOnClickListener(this);

		// 显示搜索结果头部的UI
		layout_titleLayout_newHouseList = (RelativeLayout) findViewById(R.id.layout_titleLayout_newHouseList);

		// 显示可编辑头部的UI
		layout_titleLayout_search = (RelativeLayout) findViewById(R.id.layout_titleLayout_search);

		// 显示可编辑头部中 取消 字样的UI
		layout_search_newhouse_cancle = (RelativeLayout) findViewById(R.id.layout_search_newhouse_cancle);
		layout_search_newhouse_cancle.setOnClickListener(this);

		// 显示可编辑头部中的 可编辑框 UI
		ed_search_newHouseList = (EditText) findViewById(R.id.ed_search_newHouseList);
		ed_search_newHouseList.addTextChangedListener(watcher);
		// 监听软键盘的搜索事件
		ed_search_newHouseList
				.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							keywords = ed_search_newHouseList.getText().toString().trim();
							if(!TextUtils.isEmpty(keywords)){
								// 当点击搜索的时候，将可编辑头部UI换成搜索结果的UI，同时请求数据，更新UI
								layout_titleLayout_newHouseList
										.setVisibility(View.VISIBLE);
								layout_titleLayout_search.setVisibility(View.GONE);
								listview_search_hintresult.setVisibility(View.GONE);
								ed_search_newHouseList.setText("");
								// 下面是请求数据
								isReflsh = true;
								loadSearchResult(1);
								imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
							}else{
								imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
							}
							
						}
						return false;
					}
				});

		listview_search_result = (LoadListView) findViewById(R.id.listview_search_result);
		listview_search_result.setLoaderListener(this);
		nAdapter = new SearchResultListAdapter(SearchResultListActivity.this,
				totalList);
		listview_search_result.setAdapter(nAdapter);

		listview_search_result
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// textview的标记tv_houseName_item_newHouseList
						Result result = null;
						TextView textView;
						textView = (TextView) view
								.findViewById(R.id.tv_houseName_item_newHouseList);
						if (textView == null) {
							return;
						}
						result = (Result) textView.getTag();
						if (result == null) {
							return;
						}
						List<Tags> tags = new ArrayList<SearchBean.Result.Tags>();
						String buildingId = result.getId() + "";
						List<Tags> listTags = result.getTags();
						tags.addAll(listTags);
						String tagsword = "";
						for(Tags tag : tags){
							tagsword += tag.getName() + " ";
						}
						Intent intent = new Intent(SearchResultListActivity.this,
								NewHouseDetailActivity.class);
						intent.putExtra("buildingId", buildingId);
						intent.putExtra("tags",tagsword);
						startActivity(intent);
					}
				});

		// 下拉刷新部分
		search_result_swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.search_result_swipeRefreshLayout);
		// 设置卷内的颜色
		search_result_swipeRefreshLayout.setColorSchemeResources(
				android.R.color.darker_gray,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		search_result_swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				isReflsh = true;
				page = 1;
				loadSearchResult(page);
			}
		});
	}

	private TextWatcher watcher = new TextWatcher() {
		private String temp;

		// EditText中内容改变之前调用
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		// EditText中内容改变过程中调用,当edittext中有值，调用这个方法请求网络数据，更新ui
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			temp = s.toString();
		}

		// EditText中内容改变之后调用,清空数据集里面的内容，更新UI
		@Override
		public void afterTextChanged(Editable s) {
			//dataListHint.clear();
			if (StringUtils.isBlank(temp)) {
				dataListHint.clear();
				searchAdapter.notifyDataSetChanged();
				listview_search_hintresult.setVisibility(View.GONE);
			} else {
				listview_search_hintresult.setVisibility(View.VISIBLE);
				searchHint();
			}
		}
	};

	/**
	 * 
	 * @Title: searchHint
	 * @Description: 动态的搜索提示，只有达到数据之后才能显示popuwindow 否则可能会报异常
	 * @return: void
	 */
	private void searchHint() {
		String keyWord = ed_search_newHouseList.getText().toString();

		Map<String, String> map = new HashMap<String, String>();
		map.put("cityId", cityId + "");
		map.put("keyword", keyWord);
		SimpleRequest<SearchHintBean> request = new SimpleRequest<SearchHintBean>(
				API.HINT, SearchHintBean.class, new Listener<SearchHintBean>() {
					@Override
					public void onResponse(SearchHintBean response) {
						dataListHint.clear();
						if (response != null) {
							if (0 == response.getStatus()) {
								List<SearchList> listSearchLists = response
										.getResult().getList();
								dataListHint.addAll(listSearchLists);
								searchAdapter.notifyDataSetChanged();
							}else{
								showTost(response.getMsg());
							}
						}
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	private void initData() {
		Intent intent = getIntent();
		keywords = intent.getStringExtra("keywords");
		cityId = intent.getStringExtra("cityId");
		// 默认加载第一页数据
		loadSearchResult(1);
	}

	/**
	 * 
	 * @Title: loadSearchResult
	 * @Description: 加载搜索楼盘名称结果
	 * @return: void
	 */
	private void loadSearchResult(final int page) {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("cityId", cityId);
		map.put("keyword", keywords);
		map.put("page", page + "");

		SimpleRequest<SearchBean> request = new SimpleRequest<SearchBean>(
				API.SEARCH, SearchBean.class, new Listener<SearchBean>() {
					@Override
					public void onResponse(SearchBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								if(isReflsh){
									totalList.clear();
									isReflsh = false;
								}
								List<Result> list = response.getResult();
								totalList.addAll(list);
								if(page == 1){
									if(totalList.size() == 0){
										listview_search_result.setVisibility(View.GONE);
										ly_serrch_none.setVisibility(View.VISIBLE);
									}else{
										ly_serrch_none.setVisibility(View.GONE);
										listview_search_result.setVisibility(View.VISIBLE);
										nAdapter.notifyDataSetChanged();
									}
								}else{
									nAdapter.notifyDataSetChanged();
								}
							}else {
								showTost(response.getMsg());
							}
						}
						loadingDialog.dismissDialog();
						listview_search_result.loadComplete();
						search_result_swipeRefreshLayout.setRefreshing(false);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loadingDialog.dismissDialog();
						listview_search_result.loadComplete();
						search_result_swipeRefreshLayout.setRefreshing(false);
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 返回
		case R.id.rl_back:
			finish();
			break;
		// 当点击搜索框的时候，将头部搜索结果的UI换成可编辑头部的UI
		case R.id.layout_search_newhouse_list:
			//隐藏原始标题，显示搜索标题
			layout_titleLayout_newHouseList.setVisibility(View.GONE);
			layout_titleLayout_search.setVisibility(View.VISIBLE);
//			if(dataListHint.size()>0){
//				listview_search_hintresult.setVisibility(View.VISIBLE);
//			}
			break;
		// 当点击取消的时候，将可编辑头部UI换成搜索结果的UI，同时将软键盘隐藏
		case R.id.layout_search_newhouse_cancle:
			layout_titleLayout_newHouseList.setVisibility(View.VISIBLE);
			ed_search_newHouseList.setText("");
			layout_titleLayout_search.setVisibility(View.GONE);
			listview_search_hintresult.setVisibility(View.GONE);
			if(null != imm){
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onLoad() {
		loadSearchResult(++page);
	}

}
