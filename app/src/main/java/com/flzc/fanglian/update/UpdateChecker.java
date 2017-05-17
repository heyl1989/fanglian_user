package com.flzc.fanglian.update;

import android.content.Context;
import android.util.Log;

public class UpdateChecker {


    public static void checkForDialog(Context context) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_DIALOG, true).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }
    
    public static void checkForForceDialog(Context context,boolean isShowProgressDialog) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_FORCE_DIALOG, isShowProgressDialog).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }

}
