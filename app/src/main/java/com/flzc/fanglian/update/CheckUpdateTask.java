package com.flzc.fanglian.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.flzc.fanglian.R;
import com.flzc.fanglian.app.UserApplication;
import com.flzc.fanglian.http.SimpleRequest;
import com.flzc.fanglian.model.UpdateBean;
import com.flzc.fanglian.model.UpdateBean.Result;;

/**
 * @author Lu
 * @since 2016-07-05 19:21
 */
class CheckUpdateTask {

	private ProgressDialog dialog;
	private Context mContext;
	private int mType;
	private boolean mShowProgressDialog;
	private static final String url = Constants.UPDATE_URL;

	CheckUpdateTask(Context context, int type, boolean showProgressDialog) {

		this.mContext = context;
		this.mType = type;
		this.mShowProgressDialog = showProgressDialog;

	}

	public void execute() {
		if (mShowProgressDialog) {
			dialog = new ProgressDialog(mContext);
			dialog.setMessage(mContext
					.getString(R.string.android_auto_update_dialog_checking));
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}
		SimpleRequest<UpdateBean> req = new SimpleRequest<UpdateBean>(url,
				UpdateBean.class, new Listener<UpdateBean>() {

					@Override
					public void onResponse(UpdateBean response) {
						if (response != null) {
							if (0 == response.getStatus()) {
								paseData(response);
							} else {
								Toast.makeText(mContext, "服务器异常", 0).show();
							}
						}
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						Toast.makeText(mContext, "网络错误", 0).show();
					}
				});
		UserApplication.getInstance().addToRequestQueue(req);
	}

	protected void paseData(UpdateBean response) {
		Result data = response.getResult();
		String updateMessage = data.getLastMemo();
		Log.e("mesage", updateMessage);
		String apkUrl = data.getUrl();
		// 测试apk地址
		//apkUrl ="http://121.22.246.88/qiniu-app-cdn.pgyer.com/04170e8f3d8511c0bfebfc2adff3cd72.apk?e=1470276958&attname=FanglianUser_1.1.3_106.apk&token=6fYeQ7_TVB5L0QSzosNFfw2HU8eJhAirMF5VxV9G:3ZUeWJW6gn2jZQ7A9j2NfkM10KY=&wsiphost=local";
		String apkVersion = data.getVersionLast();
		String updateVersion = data.getVersionUpdate();
		// apk信息
		int versionCode = AppUtils.getVersionCode(mContext);
		String versionName = AppUtils.getVersionName(mContext);
		// 字符串替换
		int intApkVersion = 0;
		int intVersionName = 0;
		int intUpdateVersion = 0;
		try {
			intApkVersion = Integer.parseInt(apkVersion.replaceAll("\\.", ""));
			intVersionName = Integer
					.parseInt(versionName.replaceAll("\\.", ""));
			intUpdateVersion = Integer.parseInt(updateVersion.replaceAll("\\.",
					""));
		} catch (Exception e) {
			Log.e("data", "version is wrong");
		}
		// DIALOG
		if (mType == Constants.TYPE_DIALOG) {
			if (intApkVersion > intVersionName) {
				showDialog(mContext, updateMessage, apkUrl);
			}else if (mShowProgressDialog) {
				Toast.makeText(
						mContext,
						mContext.getString(R.string.android_auto_update_toast_no_new_update),
						Toast.LENGTH_SHORT).show();
			}
		} 
		// FORCE_DIALOG
		if (mType == Constants.TYPE_FORCE_DIALOG) {
			if (intUpdateVersion > intVersionName) {
				showForceDialog(mContext, updateMessage, apkUrl, updateVersion);
			}
		}

	}

	/**
	 * Show dialog
	 */
	private void showDialog(Context context, String content, String apkUrl) {
		UpdateDialog.show(context, content, apkUrl);
	}

	/**
	 * Show force dialog
	 */
	private void showForceDialog(Context context, String content,
			String apkUrl, String updateVersion) {
		UpdateDialog.showForce(context, content, apkUrl, updateVersion);
	}
}
