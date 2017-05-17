package com.flzc.fanglian.util;

import android.content.Context;

public class AndroidUnit {


	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		//屏幕的密度系数 scale
		final float scale = context.getResources().getDisplayMetrics().density;
//		//屏幕密度的dpi
//		int scaleDpi = context.getResources().getDisplayMetrics().densityDpi;
		return (int) (pxValue / scale + 0.5f);
	}
}
