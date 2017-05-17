package com.flzc.fanglian.app;

import java.io.File;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.location.service.LocationService;
import com.baidu.location.service.WriteLog;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.analytics.MobclickAgent;
/**
 * 
 * @ClassName: UserApplication 
 * @Description: TODO
 * @author: LU
 * @date: 2016-3-4 下午3:14:26
 */
public class UserApplication extends Application {
	
	private static final String TAG = UserApplication.class.getSimpleName();
	private RequestQueue requestQueue;
	private static UserApplication mInstance;
	public LocationService locationService;
    public Vibrator mVibrator;
	//是否需要刷新
	private boolean isReflush = false;
	public static synchronized UserApplication getInstance() {
		return mInstance;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		//初始化定位SDK
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());  
		//初始化极光推送
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        //初始化 ImageLoader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder
				(getApplicationContext()).threadPriority(Thread.NORM_PRIORITY-2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				.build();
      //.writeDebugLogs()// Remove for release app
		ImageLoader.getInstance().init(config);
		/**
	     * 友盟统计
	     */
	    MobclickAgent.setDebugMode(false);
	     /**
         * 设置需要友盟错误统计功能
         */
	    MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setCatchUncaughtExceptions(true);
	}
	
	public RequestQueue getRequestQueue() {
		if (requestQueue == null)
			requestQueue = Volley.newRequestQueue(getApplicationContext());
		return requestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	public void cancelPendingRequest(Object tag) {
		if (requestQueue != null) {
			requestQueue.cancelAll(tag);
		}
	}

	public boolean isReflush() {
		return isReflush;
	}

	public void setReflush(boolean isReflush) {
		this.isReflush = isReflush;
	}

}
