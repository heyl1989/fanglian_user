package com.flzc.fanglian.update;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * @author feicien (ithcheng@gmail.com)
 * @since 2016-07-05 17:41
 */

public class AppUtils {

	/**
	 * 
	 * @Title: getVersionCode 
	 * @Description: 获取版本号
	 * @param mContext
	 * @return
	 * @return: int
	 */
    public static int getVersionCode(Context mContext) {
        if (mContext != null) {
            try {
                return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
        return 0;
    }

    /**
     * 
     * @Title: getVersionName 
     * @Description: 获取版本号
     * @param mContext
     * @return
     * @return: String
     */
    public static String getVersionName(Context mContext) {
        if (mContext != null) {
            try {
                return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }

        return "";
    }
    /**
     * 
     * @Title: installAPk 
     * @Description: 安装apk
     * @param context
     * @param apkFile
     * @return: void
     */
    public static void installAPk(Context context,File apkFile) {
    	Intent intent = new Intent(Intent.ACTION_VIEW);
        //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
        try {
            String[] command = {"chmod", "777", apkFile.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException ignored) {
        }
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
