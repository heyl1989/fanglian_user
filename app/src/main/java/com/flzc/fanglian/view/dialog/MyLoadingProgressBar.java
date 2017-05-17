package com.flzc.fanglian.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.flzc.fanglian.R;

public class MyLoadingProgressBar {

	private Context context;
    private ProgressBar progressBar1;
    private View view;
    private Dialog dialog;

    public MyLoadingProgressBar(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(
                R.layout.dialog_loading_progressbar, null);
        progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
        dialog = new Dialog(context, R.style.full_screen_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
    }


    /**
     * 显示加载中
     */
    public void showDialog() {
    	if(null != dialog){
    		 dialog.show();
    	}
    }


    /**
     * 取消加载窗口
     */
    public void dismissDialog() {
    	if(null != dialog){
    		dialog.dismiss();
    	}
    }

    /**
     * 设置指定毫秒后关闭
     *
     * @param millionSecond
     */
    public void dismissDialogDelay(int millionSecond) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, millionSecond);
    }

}
