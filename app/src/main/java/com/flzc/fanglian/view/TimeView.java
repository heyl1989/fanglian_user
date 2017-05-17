package com.flzc.fanglian.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;

public class TimeView extends RelativeLayout implements Runnable {

	private TextView timedown_day, timedown_hour, timedown_min,
			timedown_second;
	private int[] times;
	private long mday, mhour, mmin, msecond;
	private TextView timedown_month;
	private TextView tv_month;
	private TextView tv_mile;
	private TextView tv_day;

	public TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TimeView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		View.inflate(context, R.layout.time_layout, TimeView.this);
		timedown_month = (TextView) this.findViewById(R.id.timedown_month);
		tv_month = (TextView) this.findViewById(R.id.tv_month);
		tv_day = (TextView) this.findViewById(R.id.tv_day);
		timedown_day = (TextView) this.findViewById(R.id.timedown_day);
		timedown_hour = (TextView) this.findViewById(R.id.timedown_hour);
		timedown_min = (TextView) this.findViewById(R.id.timedown_min);
		timedown_second = (TextView) this.findViewById(R.id.timedown_second);
		tv_mile = (TextView) this.findViewById(R.id.tv_mile);
	}

	public void setDownTimes(int[] times) {
		timedown_second.setVisibility(View.VISIBLE);
		tv_mile.setVisibility(View.VISIBLE);
		timedown_month.setVisibility(View.GONE);
		tv_month.setVisibility(View.GONE);
		this.times = times;
		mday = times[0];
		mhour = times[1];
		mmin = times[2];
		msecond = times[3];
		timedown_day.setText(times[0]+"");
		timedown_hour.setText(times[1]+"");
		timedown_min.setText(times[2]+"");
		timedown_second.setText(times[3]+"");
	}
	public void setTimes(int[] times) {
		timedown_month.setVisibility(View.VISIBLE);
		tv_month.setVisibility(View.VISIBLE);
		timedown_second.setVisibility(View.GONE);
		tv_mile.setVisibility(View.GONE);
		tv_day.setText("日");
		this.times = times;
		timedown_month.setText(times[0]<10?"0"+times[0]:times[0]+"");
		timedown_month.setBackgroundColor(Color.TRANSPARENT);
		timedown_day.setText(times[1]<10?"0"+times[1]:times[1]+"");
		timedown_day.setBackgroundColor(Color.TRANSPARENT);
		timedown_hour.setText(times[2]<10?"0"+times[2]:times[2]+"");
		timedown_hour.setBackgroundColor(Color.TRANSPARENT);
		timedown_min.setText(times[3]<10?"0"+times[3]:times[3]+"");
		timedown_min.setBackgroundColor(Color.TRANSPARENT);
	}

	/**
	 * 倒计时计
	 */
	private void ComputeTime() {
		msecond--;
		if (msecond < 0) {
			mmin--;
			msecond = 59;
			if (mmin < 0) {
				mmin = 59;
				mhour--;
				if (mhour < 0) {
					mhour = 59;
					mday--;
				}
			}
		}
	}
	
	@Override
	public void run() {
		
		if (times[0] == 0 && times[1] == 0 && times[2] == 0 && times[3] == 0) {

		} else {
			ComputeTime();
			String strTime = "还剩" + mday + "天" + mhour + "小时" + mmin + "分钟"
					+ msecond + "秒";
			// this.setText(strTime);
			String mdayString = mday < 10 ? "0" + mday : mday + "";
			String mhourString = mhour < 10 ? "0" + mhour : mhour + "";
			String mminString = mmin < 10 ? "0" + mmin : mmin + "";
			String msecondString = msecond < 10 ? "0" + msecond : msecond + "";

			timedown_day.setText(mdayString);
			timedown_hour.setText(mhourString);
			timedown_min.setText(mminString);
			// android:background="#fcf9f4"

			timedown_second.setText(msecondString);
			// this.setBackgroundColor(R.color.textcolor_black);
			if (mday <= 0 && mhour == 0 && mmin == 0 && msecond == 0) {
				return;
			}
			postDelayed(this, 1000);
		}

	}
	
	public void stop(){
		removeCallbacks(this);
	}

	public void setText(){
		timedown_day.setText(times[0]+"");
		timedown_hour.setText(times[1]+"");
		timedown_min.setText(times[2]+"");
		timedown_second.setText( times[3]+"");
	}
}
