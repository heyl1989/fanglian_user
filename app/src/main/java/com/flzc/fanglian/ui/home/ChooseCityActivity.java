package com.flzc.fanglian.ui.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.OpenCityBean;
import com.flzc.fanglian.model.OpenCityBean.Result.Citys;
import com.flzc.fanglian.ui.HomeActivity;
import com.flzc.fanglian.ui.adapter.AreaOptionsAdapter;
import com.flzc.fanglian.util.GPSUtil;
import com.flzc.fanglian.view.dialog.CustomDialog;

/**
 * 
 * @ClassName: ChooseCityActivity
 * @Description: 选择城市列表页面
 * @author: Tien.
 * @date: 2016年3月10日 下午4:03:47
 */
public class ChooseCityActivity extends BaseActivity implements OnClickListener {

	private LocationService locationService;
	private RelativeLayout rl_back;
	private TextView titleName, title_return;
	private ListView mHotCityList;
	private List<Citys> hotCityList = new ArrayList<Citys>();
	private TextView locatedResult;
	private RelativeLayout locateCity;
	private int locCityID;
	private String locCityName ;
	private String from ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_city);
		if(null != getIntent().getExtras()){
			from = getIntent().getExtras().getString("from", "");
		}
		iSGPSOpen();
		initItem();
		initData();
	}
	private void iSGPSOpen() {
		//showTost(GPSUtil.isOPen(this)+"");
		if(!GPSUtil.isOPen(this)){
			CustomDialog.Builder builder = new CustomDialog.Builder(this);
			builder.setTitle("定位服务未开启");
			builder.setContent("请在系统设置中开启定位服务");
			builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面

					dialog.dismiss();
				}
			});
			builder.setNegativeButton("暂不", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					//locatedResult.setText("定位失败，点击重试");
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
	}

	private void initItem() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);

		title_return = (TextView) findViewById(R.id.title_return);
		title_return.setVisibility(View.VISIBLE);

		titleName = (TextView) findViewById(R.id.tv_title);
		titleName.setText("选择城市");
		// 定位城市
		locatedResult = (TextView) findViewById(R.id.tv_locatedCity_chooseCity);
		locatedResult.setText("定位中...");
		locateCity = (RelativeLayout) findViewById(R.id.rl_locate_city);
		// 热门城市
		mHotCityList = (ListView) findViewById(R.id.listview_hotCity_chooseCity);
		mHotCityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				UserInfoData.saveData(Constant.LOC_CITY_ID, hotCityList.get(arg2)
						.getCityId() + "");
				UserInfoData.saveData(Constant.LOC_CITY_NAME, hotCityList.get(arg2)
						.getCityName() + "");
				goOthers(HomeActivity.class);
				finish();
			}
		});

	}

	// 请求开通城市
	private void initData() {
		Map<String, String> map = new HashMap<String, String>();
		SimpleRequest<OpenCityBean> req = new SimpleRequest<OpenCityBean>(
				API.OPENCITY, OpenCityBean.class, new Listener<OpenCityBean>() {

					@Override
					public void onResponse(OpenCityBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								hotCityList.addAll(response.getResult()
										.getCitys());
								mHotCityList.setAdapter(new AreaOptionsAdapter(
										ChooseCityActivity.this, hotCityList));
							} else {
								showTost(response.getMsg());
							}
						}

					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}


	/**
	 * 显示请求字符串
	 * 
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if (locatedResult != null) {
				if(hotCityList.size() == 0){
					locCityName = str;
					locatedResult.setText(str);
				}
				queryed:for (Citys city : hotCityList) {
					if (str.contains(city.getCityName().trim())) {
						locCityID = city.getCityId();
						locCityName = city.getCityName();
						locatedResult.setText(str);
						break queryed;
					} else {
						locCityName = str;
						locatedResult.setText(str + ("(该城市暂未开通)"));
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		locationService = ((UserApplication) getApplication()).locationService;
		locationService.registerListener(mListener);
		locationService.start();
		// 注册监听
		int type = getIntent().getIntExtra("from", 0);
		if (type == 0) {
			locationService.setLocationOption(locationService
					.getDefaultLocationClientOption());
		} else if (type == 1) {
			locationService.setLocationOption(locationService.getOption());
		}
		locateCity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isFail){
					isFail = false;
					locatedResult.setText("定位中...");
					locationService.start();// 定位SDK
				}else{
					if(locatedResult.getText().equals("定位中...")){
						return;
					}
					UserInfoData.saveData(Constant.LOC_CITY_ID,
							locCityID + "");
					UserInfoData.saveData(Constant.LOC_CITY_NAME,
							locCityName + "");
					goOthers(HomeActivity.class);
					finish();
				}
			}
		});
	}

	/*****
	 * @see copy funtion to you project
	 *      定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 * 
	 */
	private boolean isFail = false;
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (null != location
					&& location.getLocType() != BDLocation.TypeServerError) {
				if (location.getCity() != null) {
					logMsg(location.getCity());
				} else {
					isFail = true;
					locatedResult.setText("定位失败，点击重试");
				}
			}
		}

	};

	/***
	 * Stop location service
	 */
	@Override
	protected void onStop() {
		locationService.unregisterListener(mListener); // 注销掉监听
		locationService.stop(); // 停止定位服务
		super.onStop();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			if(TextUtils.equals("notFromHome",from)){
				UserInfoData.saveData(Constant.LOC_CITY_ID,
						locCityID + "");
				UserInfoData.saveData(Constant.LOC_CITY_NAME,
						locCityName + "");
				goOthers(HomeActivity.class);
			}
			finish();
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(TextUtils.equals("notFromHome",from)){
				UserInfoData.saveData(Constant.LOC_CITY_ID,
						locCityID + "");
				UserInfoData.saveData(Constant.LOC_CITY_NAME,
						locCityName + "");
				goOthers(HomeActivity.class);
			}
			finish();
		}
		return false;
	}

}
