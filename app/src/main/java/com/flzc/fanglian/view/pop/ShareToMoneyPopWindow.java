package com.flzc.fanglian.view.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.flzc.fanglian.R;

public class ShareToMoneyPopWindow{

	private PopupWindow pwMyPopWindow;
	private ImageView popImg;

	public ShareToMoneyPopWindow(Activity context,View parentView) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.redbg_popupwindow, null);
		popImg = (ImageView) layout.findViewById(R.id.iv_popup);
		popImg.setImageResource(R.drawable.share_to_get_money);
		
		if(null == pwMyPopWindow){
			pwMyPopWindow = new PopupWindow(layout);
		}
		OnTouchListener popupOnTouch = new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event){

				if (pwMyPopWindow != null){
					pwMyPopWindow.setFocusable(true);
					return true;
				}
				return false;
			}
		};

		pwMyPopWindow.getContentView().setOnTouchListener(popupOnTouch);
		popImg.measure(View.MeasureSpec.UNSPECIFIED,
		View.MeasureSpec.UNSPECIFIED);
		pwMyPopWindow.setWidth(popImg.getMeasuredWidth());
		pwMyPopWindow.setHeight(popImg.getMeasuredHeight());
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		// int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		pwMyPopWindow.showAtLocation(parentView, Gravity.RIGHT | Gravity.BOTTOM,0, height / 2 - 50);// 显示
	}
	
	public ImageView getPopImg() {
		return popImg;
	}
	
	public void dismissPop(){
		if(null != pwMyPopWindow){
			pwMyPopWindow.dismiss();
		}
	}

}
