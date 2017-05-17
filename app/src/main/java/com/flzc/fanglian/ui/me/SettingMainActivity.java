package com.flzc.fanglian.ui.me;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flzc.fanglian.R;
import com.flzc.fanglian.base.BaseActivity;
import com.flzc.fanglian.db.UserInfoData;
import com.flzc.fanglian.ui.login_register.LoginActivity;
import com.flzc.fanglian.update.UpdateChecker;
import com.flzc.fanglian.util.DataCleanUtil;
import com.flzc.fanglian.view.dialog.CustomDialog;

/**
 * 
 * @ClassName: SettingMainActivity
 * @Description: 设置页面
 * @author: Tien.
 * @date: 2016年3月8日 下午6:13:13
 */
public class SettingMainActivity extends BaseActivity implements
		OnClickListener {
	private RelativeLayout rl_back;
	private TextView titleName;
	private RelativeLayout layout_logout_settingMain;
	private RelativeLayout mCleanCash;
	private TextView mCashSize;
	private RelativeLayout layout_aboutUs_settingMain;
	private RelativeLayout checkVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_main);
		
	}
	@Override
	protected void onResume() {
		initItem();
		super.onResume();
	}

	private void initItem() {
		// 标题栏
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		rl_back.setOnClickListener(this);
		titleName = (TextView) findViewById(R.id.tv_title);
		titleName.setText("设置");
		// 清除缓存
		mCleanCash = (RelativeLayout) findViewById(R.id.layout_cleanCache_settingMain);
		mCashSize = (TextView) findViewById(R.id.tv_cacheSize_settingMain);
		mCashSize.setText(DataCleanUtil.getCacheSize(getCacheDir()));
		mCleanCash.setOnClickListener(this);
		//检测新版本
		checkVersion = (RelativeLayout) findViewById(R.id.rl_check_version);
		checkVersion.setOnClickListener(this);
		// 退出登录
		layout_logout_settingMain = (RelativeLayout) findViewById(R.id.layout_logout_settingMain);
		if (!UserInfoData.isSingIn()) {
			layout_logout_settingMain.setVisibility(View.GONE);
		}
		layout_aboutUs_settingMain = (RelativeLayout) findViewById(R.id.layout_aboutUs_settingMain);
		layout_logout_settingMain.setOnClickListener(this);
		layout_aboutUs_settingMain.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.layout_logout_settingMain:
			//退出登录，清空所有数据
			conFirmLogout();
			break;
		case R.id.layout_aboutUs_settingMain:
			//关于房链
			goOthers(AboutFanglianActivity.class);
			break;
		case R.id.layout_cleanCache_settingMain:
			// 清除缓存
			DataCleanUtil.deleteFolderFile(getCacheDir(), true);
			mCashSize.setText(DataCleanUtil.getCacheSize(getCacheDir()));
			showTost("清除缓存成功");
			break;
		case R.id.rl_check_version:
			// 清除缓存
			UpdateChecker.checkForDialog(this);
			break;

		}

	}
	/**
	 * 确认退出的dialog
	 */
	private void conFirmLogout() {
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle("退出登录");
		builder.setContent("确认退出登录？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				UserInfoData.clearAll();
				goOthers(LoginActivity.class);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
