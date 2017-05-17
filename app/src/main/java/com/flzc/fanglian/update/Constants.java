package com.flzc.fanglian.update;

class Constants {


    // json {"url":"http://192.168.205.33:8080/Hello/app_v3.0.1_Other_20150116.apk","versionCode":2,"updateMessage":"版本更新信息"}

    static final int TYPE_DIALOG = 1;
    
    static final int TYPE_FORCE_DIALOG = 3;

    static final String TAG = "UpdateChecker";

    static final String UPDATE_URL = "http://112.126.78.106:8102/sold/api/version/android?type=1";
    //https://raw.githubusercontent.com/feicien/android-auto-update/develop/extras/update.json
    //http://112.126.78.106:8102/sold/api/version/android?type=1
}
