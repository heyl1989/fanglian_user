package com.flzc.fanglian.view;

import com.flzc.fanglian.R;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TimeDownView extends RelativeLayout implements Runnable {

	private TextView timedown_day, timedown_hour, timedown_min,
			timedown_second;
	private Paint mPaint; // 画笔,包含了画几何图形、文本等的样式和颜色信息
	private int[] times;
	private long mday, mhour, mmin, msecond;// 天，小时，分钟，秒
	private boolean run = false; // 是否启动了

	public TimeDownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public TimeDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TimeDownView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		View.inflate(context, R.layout.timedown_layout, TimeDownView.this);
		timedown_day = (TextView) this.findViewById(R.id.timedown_day);
		timedown_hour = (TextView) this.findViewById(R.id.timedown_hour);
		timedown_min = (TextView) this.findViewById(R.id.timedown_min);
		timedown_second = (TextView) this.findViewById(R.id.timedown_second);

		mPaint = new Paint();
	}

	public int[] getTimes() {
		return times;
	}

	public void setTimes(int[] times) {
		this.times = times;
		mday = times[0];
		mhour = times[1];
		mmin = times[2];
		msecond = times[3];
	}

	/**
	 * 倒计时计算
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
					// 倒计时结束
					mhour = 59;
					mday--;
				}
			}
		}
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		if (times[0] == 0 && times[1] == 0 && times[2] == 0 && times[3] == 0) {

		} else {
			// 标示已经启动
			run = true;
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

}
