package com.flzc.fanglian.util;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CommonUtils {
	public static int dp2px(Context context,int dpVaule){
		float density = context.getResources().getDisplayMetrics().density;
		int px = (int) (dpVaule * density + 0.5f);
		return px;
	}
	
    public static void setMargins (View v, int l, int t, int r, int b) {  
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {  
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();  
            p.setMargins(l, t, r, b);  
            v.requestLayout();  
        }  
    }  
    
    public static void setHeightofListView(ListView listView,Context context,int maxHeight) {
        ListAdapter mAdapter = listView.getAdapter(); 
       if (mAdapter == null) {
           return;
       }
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            //mView.measure(0, 0);
            totalHeight += mView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int measureHeight = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		params.height = measureHeight > dp2px(context, maxHeight)?dp2px(context, maxHeight):measureHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();    
    }
}
