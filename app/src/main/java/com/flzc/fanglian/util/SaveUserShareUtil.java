package com.flzc.fanglian.util;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response.Listener;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.BaseInfoBean;

public class SaveUserShareUtil {
	
	public static void saveUserShare(String buildingId,String activityPoolId,String houseId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("buildingId", buildingId);
		map.put("activityPoolId", activityPoolId);
		//map.put("houseId", houseId);
		map.put("userType", "0");
		
		SimpleRequest<BaseInfoBean> request = new SimpleRequest<BaseInfoBean>(
				API.SAVE_USER_SHARE, BaseInfoBean.class,
				new Listener<BaseInfoBean>() {
					@Override
					public void onResponse(BaseInfoBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								// 说明请求成功
								
							} else {
								
							}
						}
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(request);
	}

}
