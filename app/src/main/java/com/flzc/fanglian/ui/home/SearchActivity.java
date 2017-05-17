package com.flzc.fanglian.ui.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
import com.flzc.fanglian.model.SearchHintBean;
import com.flzc.fanglian.model.SearchHintBean.Result.SearchList;
import com.flzc.fanglian.ui.adapter.SearchAdapter;
import com.flzc.fanglian.ui.newhouse.NewHouseDetailActivity;
import com.flzc.fanglian.util.StringUtils;

/**
 * 
 * @ClassName: SearchActivity
 * @Description: 搜索页面
 * @author: 薛东超
 * @date: 2016年3月8日 下午5:53:02
 */
public class SearchActivity extends BaseActivity implements OnClickListener {

	private TextView tv_search_cancle, tv_search_hothouse;
	private EditText et_search_frame;
	private LinearLayout ll_search_delete;
	private ListView lv_search_content;
	private ImageView iv_search_delete;

	private SearchAdapter adapter;
	private List<SearchList> data = new ArrayList<SearchList>();

	private String cityId;// 前一个页面传过来的，此处为了测试，直接先给它赋值
	private InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		cityId = UserInfoData.getData(Constant.LOC_CITY_ID, "52");
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		initView();
		initData();
	}
	
	@Override
	protected void onResume() {
		tv_search_hothouse.setVisibility(View.GONE);
		super.onResume();
	}
	
	private void initView() {
		tv_search_cancle = (TextView) findViewById(R.id.tv_search_cancle);
		// 热门楼盘
		tv_search_hothouse = (TextView) findViewById(R.id.tv_search_hothouse);
		tv_search_hothouse.setVisibility(View.GONE);

		et_search_frame = (EditText) findViewById(R.id.et_search_frame);
		et_search_frame.addTextChangedListener(watcher);

		ll_search_delete = (LinearLayout) findViewById(R.id.ll_search_delete);
		iv_search_delete = (ImageView) findViewById(R.id.iv_search_delete);

		lv_search_content = (ListView) findViewById(R.id.lv_search_content);
		data = new ArrayList<SearchList>();
		adapter = new SearchAdapter(this, data);
		lv_search_content.setAdapter(adapter);
		lv_search_content.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String buildingId = data.get(position).getBuildingId();
				Intent intent = new Intent(SearchActivity.this,
						NewHouseDetailActivity.class);
				intent.putExtra("buildingId", buildingId);
				startActivity(intent);
			}
		});

		tv_search_cancle.setOnClickListener(this);
		ll_search_delete.setOnClickListener(this);

		// 监听软键盘的搜索事件
		et_search_frame.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					if (!TextUtils.isEmpty(et_search_frame.getText().toString()
							.trim())) {
						Intent intent = new Intent();
						intent.setClass(SearchActivity.this,
								SearchResultListActivity.class);
						intent.putExtra("keywords", et_search_frame.getText()
								.toString().trim());
						intent.putExtra("cityId", cityId + "");
						//et_search_frame.setText("");
						startActivity(intent);
					} else {
						if (null != imm) {
							imm.toggleSoftInput(0,
									InputMethodManager.HIDE_NOT_ALWAYS);
						}
					}
				}
				return false;
			}
		});

	}

	private void initData() {
		getHotBbuildingData();
	}

	/**
	 * 
	 * @Title: getHotBbuildingData
	 * @Description: 获取热门城市列表
	 * @return: void
	 */
	private void getHotBbuildingData() {
		loadingDialog.showDialog();
		Map<String, String> map = new HashMap<String, String>();
		map.put("cityId", cityId + "");

		SimpleRequest<SearchHintBean> request = new SimpleRequest<SearchHintBean>(
				API.HOT_BUILDING, SearchHintBean.class,
				new Listener<SearchHintBean>() {
					@Override
					public void onResponse(SearchHintBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								data.clear();
								List<SearchList> list = response.getResult()
										.getList();
								data.addAll(list);
								if (data.size() != 0) {
									tv_search_hothouse
											.setVisibility(View.VISIBLE);
								}
								adapter.notifyDataSetChanged();
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
		UserApplication.getInstance().addToRequestQueue(request);
	}

	private TextWatcher watcher = new TextWatcher() {
		private String temp;

		// EditText中内容改变过程中调用,当edittext中有值，调用这个方法请求网络数据，更新ui
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			temp = s.toString();
		}

		// EditText中内容改变之前调用
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		// EditText中内容改变之后调用,清空数据集里面的内容，更新UI
		@Override
		public void afterTextChanged(Editable s) {
			data.clear();
			if (StringUtils.isNotBlank(temp)) {
				tv_search_hothouse.setVisibility(View.GONE);
				iv_search_delete.setVisibility(View.VISIBLE);
				searchHint();
			} else {
				//tv_search_hothouse.setVisibility(View.VISIBLE);
				getHotBbuildingData();
				iv_search_delete.setVisibility(View.GONE);
			}
		}
	};

	/**
	 * @Title: searchHint
	 * @Description: 动态搜索提示
	 * @return: void
	 */
	private void searchHint() {
		String keyWord = et_search_frame.getText().toString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("cityId", cityId + "");
		map.put("keyword", keyWord);
		SimpleRequest<SearchHintBean> request = new SimpleRequest<SearchHintBean>(
				API.HINT, SearchHintBean.class, new Listener<SearchHintBean>() {
					@Override
					public void onResponse(SearchHintBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								List<SearchList> listSearchLists = response
										.getResult().getList();
								data.addAll(listSearchLists);
								adapter.notifyDataSetChanged();
							} else {
								showTost(response.getMsg());
							}
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						showTost(getResources().getString(R.string.net_error));
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 取消操作
		case R.id.tv_search_cancle:
			finish();
			break;
		// 删除所搜索的内容
		case R.id.ll_search_delete:
			et_search_frame.setText("");
			break;
		default:
			break;
		}
	}

}
