package com.flzc.fanglian.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.flzc.fanglian.ui.login_register.LoginActivity;

public class ShareNoLoginDialog {
	
	public static void showDialog(final Context context){
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setTitle("提示");
		builder.setContent("您当前尚未登录，需要在登录成功完成分享才能获得红包。");
		builder.setNegativeButton("前往登录", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(context,LoginActivity.class);
				context.startActivity(intent);
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("暂不登录", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}

		});
		builder.create().show();
	}
}
