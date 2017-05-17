package com.flzc.fanglian.app;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * @ClassName: AppManager 
 * @Description: 应用程序Activity管理类：用于Activity管理和应用程序退出
 * @author: 薛东超
 * @date: 2016年3月14日 下午2:16:01
 */
public class AppManager {
	
	private static Stack<Activity> activityStack;
	private static AppManager instance;
	
	private AppManager(){}
	/**
	 * 单一实例
	 */
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	public Integer activityStackSize(){
		if(activityStack==null){
			return 0;
		}
		return activityStack.size();
	}
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		if(activityStack==null  ){
			return null;
		}
		
		if(activityStack.size()==0){
			return null;
		}
		
		Activity activity=activityStack.lastElement();
		return activity;
	}
	
	/***
	 * 获取Activity（堆栈中最后2个压入的）
	 * ***/
	public Activity lastTwoActivity(){
		if(activityStack==null){
			return null;
		}
		if(activityStack.size()<2){
			return null;
		}
		Activity lastActivity=activityStack.lastElement();
		activityStack.remove(lastActivity);
		Activity lastTwoActivity=activityStack.lastElement();
		activityStack.add(lastActivity);
		return lastTwoActivity;
	}
	
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	
	/**
	 * 根据activity的 名字获得activity的实例
	 * */
    @SuppressWarnings("unchecked")
	public <T extends Activity> T getActivity(Class<T> cls){  
		Activity act = null;
		for (int i = 0, size = activityStack.size(); i < size; i++){
			act =  activityStack.get(i);
			if(act.getClass() == cls){  
                return (T)act;  
            } 
	    }
        return null;  
    }  
	
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {	}
	}
}