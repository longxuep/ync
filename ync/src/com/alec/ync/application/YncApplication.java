package com.alec.ync.application;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;

import com.alec.ync.bdSDK.LocationService;
/**
 * ������Ŀ�Ĺ�����
 * ������Է��������� ������һЩ���������� ���߳�ʼ��һЩ���� �����¼�Ժ󱣳ֵ�¼״̬ 
 * @author long
 * @date 2016��6��12��17:42:58
 */
public class YncApplication extends Application {
	public ArrayList<Activity> activities;// ����Activity������
	
	public LocationService locationService;//�ٶȶ�λ
	public String cityName="";
	@Override
	public void onCreate() {
		super.onCreate();
		activities = new ArrayList<Activity>();
		locationService = new LocationService(getApplicationContext());
	}
	
	/********************** activities��� **************************************/
	/**
	 * ���Activity����
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activities.add(activity);
		activity = null;
	}

	/**
	 * �Ƴ�Activity����
	 */
	public void removeActivity(Activity activity) {
		activities.remove(activity);
		activity = null;
		
	}
}
