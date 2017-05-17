package com.flzc.fanglian.update;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

class UpdateDialog {

	private static ProgressDialog dialog;

	@SuppressLint("NewApi")
	static void show(final Context context, String content,
			final String downloadUrl) {
		if (isContextValid(context)) {
			LayoutInflater inflater = LayoutInflater.from(context);
			LinearLayout layout = (LinearLayout) inflater.inflate(
					R.layout.custom_dialog, null);

			// 对话框
			final AlertDialog dialog = new AlertDialog.Builder(context,
					R.style.myalterstyle).create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			dialog.getWindow().setBackgroundDrawableResource(
					R.drawable.customdialogbg);
			dialog.setContentView(layout);
			// 控件
			TextView diallogTitle = (TextView) layout
					.findViewById(R.id.custom_diallog_title);
			TextView diallogContent = (TextView) layout
					.findViewById(R.id.custom_dialog_content);
			diallogContent.setGravity(Gravity.LEFT);
			Button diallogCancle = (Button) layout
					.findViewById(R.id.custom_dialog_cancle_btn);
			Button diallogConfirm = (Button) layout
					.findViewById(R.id.custom_dialog_confirm_btn);

			diallogTitle.setText(R.string.android_auto_update_dialog_title);
			diallogContent.setText(content.replace("\\n", "\n"));
			diallogCancle
					.setText(R.string.android_auto_update_dialog_btn_cancel);
			diallogCancle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			diallogConfirm
					.setText(R.string.android_auto_update_dialog_btn_download);
			diallogConfirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					goToDownload(context, downloadUrl);
				}
			});
		}
	}

	public static void showForce(final Context context, String content,
			final String apkUrl, String updateVersion) {

		if (isContextValid(context)) {
			LayoutInflater inflater = LayoutInflater.from(context);
			LinearLayout layout = (LinearLayout) inflater.inflate(
					R.layout.force_upgrade_dialog, null);

			// 对话框
			final AlertDialog dialog = new AlertDialog.Builder(context)
					.create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK
							&& event.getRepeatCount() == 0) {
						return true;
					} else {
						return false;
					}
				}
			});
			dialog.show();
			dialog.getWindow().setContentView(layout);
			// 控件
			TextView updateTitle = (TextView) layout
					.findViewById(R.id.tv_update_title);
			TextView updateContent = (TextView) layout
					.findViewById(R.id.tv_update_content);
			updateTitle.setText(updateVersion + "版本更新内容");
			updateContent.setText(content.replace("\\n", "\n"));
			layout.findViewById(R.id.tv_update).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							goToDownload(context, apkUrl);
						}
					});

		}
	}

	private static boolean isContextValid(Context context) {
		return context instanceof Activity
				&& !((Activity) context).isFinishing();
	}

	private static void goToDownload(Context context, String downloadUrl) {
		downApk(downloadUrl,context);
		dialog = new ProgressDialog(context);
		//设置最大值为100  
		dialog.setMax(100); 
        //设置进度条风格STYLE_HORIZONTAL  
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); 
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.show();
	}

	// 下载apk
	public static void downApk(final String downloadUrl, final Context context) {
		// 指定文件类型
		String[] allowedContentTypes = new String[] { ".*" };
		// 获取二进制数据如图片和其他文件
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(30000);
		client.get(downloadUrl, new BinaryHttpResponseHandler(allowedContentTypes) {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] binaryData) {

				// 文件夹地址
				String tempPath = "Download";
				// 文件地址
				String filePath = tempPath + "/" + "fanglian" + ".apk";
				// 下载成功后需要做的工作
				Log.e("binaryData:", "共下载了：" + binaryData.length);

				FileUtils fileutils = new FileUtils(context);

				// 判断sd卡上的文件夹是否存在
				if (!fileutils.isFileExist(tempPath)) {
					fileutils.createSDDir(tempPath);
				}

				// 删除已下载的apk
				if (fileutils.isFileExist(filePath)) {
					fileutils.deleteFile(filePath);
				}

				InputStream inputstream = new ByteArrayInputStream(binaryData);
				if (inputstream != null) {
					fileutils.write2SDFromInput(binaryData.length,filePath, inputstream);
					try {
						inputstream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//安装APK前关闭progressDialog
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				AppUtils.installAPk(context, new File(Environment.getExternalStorageDirectory() + "/"+filePath));

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] binaryData, Throwable error) {
				Log.i("http", error.getMessage());
			}
			
			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);

				// 下载进度显示
				dialog.setProgress(count);

				Log.e("下载 Progress>>>>>", bytesWritten + " / " + totalSize);
			}
			
			@Override
			public void onRetry(int retryNo) {
				// TODO Auto-generated method stub
				super.onRetry(retryNo);
				// 返回重试次数
			}

		});
	}
}
