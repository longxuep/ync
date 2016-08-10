package com.alec.ync.application;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.alec.ync.util.Constant;
import com.alec.ync.util.ImageLoaders;
import com.alec.ync.util.LocationService;
/**
 * ������Ŀ�Ĺ�����
 * ������Է��������� ������һЩ���������� ���߳�ʼ��һЩ���� �����¼�Ժ󱣳ֵ�¼״̬ 
 * @author long
 * @date 2016��6��12��17:42:58
 */
public class YncApplication extends Application {
	public ArrayList<Activity> activities;// ����Activity������
	
	public LocationService locationService; //�ٶȶ�λ
	public String cityName="";
	public double longitude ;
	public double latitude ;
	public String address = "";
	@Override
	public void onCreate() {
		super.onCreate();
		activities = new ArrayList<Activity>();
		locationService = new LocationService(getApplicationContext());
		ImageLoaders.init(getApplicationContext());// ��ʼ������ͼƬģ��
		/* ע�⣬��������ȵ��ã��ڵ��ô˴���ʼ����Ĳ��� */
		Constant.init(getApplicationContext());// ��ʼ���ļ�����·�����ӿڵ�ַ
		
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

	/**
	 * ��ȡApp��װ����Ϣ
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}
	
}
