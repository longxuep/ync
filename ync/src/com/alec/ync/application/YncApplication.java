package com.alec.ync.application;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;

import com.alec.ync.bdSDK.LocationService;
/**
 * 整个项目的公共类
 * 这里可以放启动方法 或者是一些公共的类型 或者初始化一些方法 比如登录以后保持登录状态 
 * @author long
 * @date 2016年6月12日17:42:58
 */
public class YncApplication extends Application {
	public ArrayList<Activity> activities;// 所有Activity的引用
	
	public LocationService locationService;//百度定位
	public String cityName="";
	@Override
	public void onCreate() {
		super.onCreate();
		activities = new ArrayList<Activity>();
		locationService = new LocationService(getApplicationContext());
	}
	
	/********************** activities相关 **************************************/
	/**
	 * 添加Activity引用
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activities.add(activity);
		activity = null;
	}

	/**
	 * 移除Activity引用
	 */
	public void removeActivity(Activity activity) {
		activities.remove(activity);
		activity = null;
		
	}
}
