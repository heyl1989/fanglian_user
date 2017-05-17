package com.flzc.fanglian.view;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.http.API;
import com.flzc.fanglian.http.Constant;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.MessageBean;

public class DateAndTimePickerPopWindow {

	private Activity context;
	private Calendar calendar;
	private DatePicker dp_test;
	private TimePicker tp_test;
	private TextView tv_ok, tv_cancel; // 确定、取消button
	private PopupWindow popupWindow;
	private String selectDate, selectTime;
	// 选择时间与当前时间，用于判断用户选择的是否是以前的时间
	private int currentHour, currentMinute, currentDay, selectHour,
			selectMinute, selectDay;
	private View Rl_all;
	private String activityPoolId;
	private String brokerId;
	private TextView tv;

	
	public DateAndTimePickerPopWindow(Context context, View rl_all,String activityPoolId,String brokerId,TextView tv) {
		super();
		this.context = (Activity)context;
		this.Rl_all = rl_all;
		this.activityPoolId = activityPoolId;
		this.brokerId = brokerId;
		this.tv = tv;
		calendar = Calendar.getInstance();
		initPop();
	}
	private void initPop() {
		View view = View.inflate(context, R.layout.dialog_select_time, null);
		selectDate = calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH)+1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "";
//				+ DatePicker.getDayOfWeekCN(calendar.get(Calendar.DAY_OF_WEEK));
		// 选择时间与当前时间的初始化，用于判断用户选择的是否是以前的时间，如果是，弹出toss提示不能选择过去的时间
		selectDay = currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		selectMinute = currentMinute = calendar.get(Calendar.MINUTE);
		selectHour = currentHour = calendar.get(Calendar.HOUR_OF_DAY);

		selectTime = currentHour
				+ ":"
				+ ((currentMinute < 10) ? ("0" + currentMinute) : currentMinute)
				+ ":00";
		dp_test = (DatePicker) view.findViewById(R.id.dp_test);
		tp_test = (TimePicker) view.findViewById(R.id.tp_test);
		tv_ok = (TextView) view.findViewById(R.id.tv_ok);
		tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		// 设置滑动改变监听器
		dp_test.setOnChangeListener(dp_onchanghelistener);
		tp_test.setOnChangeListener(tp_onchanghelistener);
		if (null == popupWindow || !popupWindow.isShowing()) {
			popupWindow = new PopupWindow(context.getApplicationContext());
			//设置破普window的属性
			popupWindow.setWidth(LayoutParams.MATCH_PARENT);
			popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setContentView(view);
			popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
			popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindow.showAtLocation(Rl_all, Gravity.BOTTOM, 0, 0);
	        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
	        lp.alpha = 0.5f;
	        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	        context.getWindow().setAttributes(lp);
	        popupWindow.setOnDismissListener(new OnDismissListener() {
	 
	            @Override
	            public void onDismiss() {
	                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
	                lp.alpha = 1f;
	                context.getWindow().setAttributes(lp);
	            }
	        });
		}

		// 点击确定
		tv_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (selectDay == currentDay) { // 在当前日期情况下可能出现选中过去时间的情况
					if (selectHour < currentHour) {
						Toast.makeText(context, "不能选择过去的时间\n        请重新选择", 0)
								.show();
					} else if ((selectHour == currentHour)
							&& (selectMinute < currentMinute)) {
						Toast.makeText(context, "不能选择过去的时间\n        请重新选择", 0)
								.show();
					} else {
						initData(selectDate +" "+ selectTime);
//						Toast.makeText(context, selectDate + selectTime, 0)
//								.show();
						popupWindow.dismiss();
					}
				} else {
					initData(selectDate +" "+ selectTime);
//					Toast.makeText(context, selectDate + selectTime, 0).show();
					popupWindow.dismiss();
				}
			}
		});

		// 点击取消
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popupWindow.dismiss();
			}
		});
	}
	/**
	 * 
	 * @Title: initData 
	 * @Description: 提交预约时间
	 * @return: void
	 */
	private void initData(String appointmentTime) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("brokerId", brokerId);
		map.put("activityPoolId", activityPoolId);
		map.put("tokenId", UserInfoData.getData(Constant.TOKEN, ""));
		map.put("appointmentTime", appointmentTime);
		
		SimpleRequest<MessageBean> req = new SimpleRequest<MessageBean>(
				API.APPOINTMENT,
				MessageBean.class,
				new Listener<MessageBean>() {
					@Override
					public void onResponse(MessageBean response) {
						if (null != response) {
							if (response.getStatus() == 0) {
								Toast.makeText(context, "预约成功", Toast.LENGTH_SHORT).show();
								tv.setText("已预约");
							} else if(response.getStatus() == 1){
								Toast.makeText(context, "已经预约过", Toast.LENGTH_SHORT).show();
							}else if(response.getStatus() == 3){
								Toast.makeText(context, "已由置业顾问添加为客户，请直接联系置业顾问", Toast.LENGTH_SHORT).show();
							}else if(response.getStatus() == 4){
								Toast.makeText(context, "已完成楼盘带看，请直接联系置业顾问", Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(context, "预约失败", Toast.LENGTH_SHORT).show();
							}
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(context, context.getResources().getString(R.string.net_error), Toast.LENGTH_SHORT).show();
					}
				}, map);
		UserApplication.getInstance().addToRequestQueue(req);

	}

	// listeners
	DatePicker.OnChangeListener dp_onchanghelistener = new DatePicker.OnChangeListener() {
		@Override
		public void onChange(int year, int month, int day, int day_of_week) {
			selectDay = day;
			selectDate = year + "-" + month + "-" + day + "";
			//		+ DatePicker.getDayOfWeekCN(day_of_week);
		}
	};
	TimePicker.OnChangeListener tp_onchanghelistener = new TimePicker.OnChangeListener() {
		@Override
		public void onChange(int hour, int minute) {
			selectTime = hour + ":" + ((minute < 10) ? ("0" + minute) : minute)
					+ ":00";
			selectHour = hour;
			selectMinute = minute;
		}
	};

}
