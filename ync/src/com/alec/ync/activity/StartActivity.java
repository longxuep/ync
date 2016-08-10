package com.alec.ync.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alec.ync.application.YncApplication;
import com.alec.ync.base.BaseActivity;
import com.alec.ync.base.BaseContext;
import com.alec.ync.model.City;
import com.alec.ync.model.DataCity;
import com.alec.ync.util.Constant;
import com.alec.ync.util.LocationService;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.yzc.R;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.google.gson.Gson;

/**
 * �������� Laier������
 **/

public class StartActivity extends BaseActivity {
	private LocationService locationService; //�ٶȶ�λ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_my01);
		LinearLayout mLinear = (LinearLayout) findViewById(R.id.fragment01linear);
		mLinear.setBackgroundResource(R.drawable.ic_splash_screen);
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = hand.obtainMessage();
				hand.sendMessage(msg);
			}

		}.start();
	};

	Handler hand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			Intent intent = new Intent(StartActivity.this, FrameActivity.class);
			startActivity(intent);
			/*
			 * if (isFristRun()) { Intent intent = new
			 * Intent(StartActivity.this,MainActivity.class);
			 * startActivity(intent); } else { Intent intent = new
			 * Intent(StartActivity.this,FrameActivity.class);
			 * startActivity(intent); }
			 */
			finish();
		};
	};
	@Override
	protected void onDestroy() {
		locationService.stop();
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		try {
			// -----------location config ------------
			locationService = ((YncApplication) getApplication()).locationService;
			// ��ȡlocationserviceʵ��������Ӧ����ֻ��ʼ��1��locationʵ����Ȼ��ʹ�ã����Բο�����ʾ����activity������ͨ�����ַ�ʽ��ȡlocationserviceʵ����
			locationService.registerListener(mListener);
			// ע�����
			int type = getIntent().getIntExtra("from", 0);
			if (type == 0) {
				locationService.setLocationOption(locationService.getDefaultLocationClientOption());
			} else if (type == 1) {
				locationService.setLocationOption(locationService.getOption());
			}
			locationService.start(); //��λSDK
		} catch (Exception e) {
		}
		
		super.onStart();
	}

	private boolean isFristRun() {
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"share", MODE_PRIVATE);
		boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
		Editor editor = sharedPreferences.edit();
		if (!isFirstRun) {
			return false;
		} else {
			editor.putBoolean("isFirstRun", false);
			editor.commit();
			return true;
		}
	}

	/*****
	 * @see copy funtion to you project
	 *      ��λ����ص�����дonReceiveLocation����������ֱ�ӿ������´��뵽�Լ��������޸�
	 * 
	 */
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				/**
				 * ʱ��Ҳ����ʹ��systemClock.elapsedRealtime()���� ��ȡ�����Դӿ���������ÿ�λص���ʱ�䣻
				 * location.getTime() ��ָ����˳����ν����ʱ�䣬���λ�ò������仯����ʱ�䲻��
				 */
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());// ��λ����
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());// γ��				
				app.latitude = location.getLatitude();
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());// ����
				app.longitude = location.getLongitude();
				sb.append("\nradius : ");
				sb.append(location.getRadius());// �뾶
				sb.append("\nCountryCode : ");
				sb.append(location.getCountryCode());// ������
				sb.append("\nCountry : ");
				sb.append(location.getCountry());// ��������
				sb.append("\ncitycode : ");
				sb.append(location.getCityCode());// ���б���
				sb.append("\ncity : ");
				sb.append(location.getCity());// ����
				sb.append("\nDistrict : ");
				sb.append(location.getDistrict());// ��
				sb.append("\nStreet : ");
				sb.append(location.getStreet());// �ֵ�
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());// ��ַ��Ϣ
				app.address = location.getAddrStr();
				sb.append("\nDescribe: ");
				sb.append(location.getDirection());
				sb.append("\nPoi: ");				   
				sb.append("\nʡ:");
			    sb.append(location.getProvince());
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS��λ���
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// �ٶ� ��λ��km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());// ������Ŀ
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// ���θ߶� ��λ����
					sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps�����ж�*****
					sb.append("\ndescribe : ");
					sb.append("gps��λ�ɹ�");
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ���綨λ���
					// ��Ӫ����Ϣ
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("���綨λ�ɹ�");
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
					sb.append("\ndescribe : ");
					sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("��������綨λʧ�ܣ������Ƿ�����������");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ����������������Ӧ��");
				}
				if (location.getLocType() == BDLocation.TypeCriteriaException
						|| location.getLocType() == BDLocation.TypeServerError
						|| location.getLocType() == BDLocation.TypeNetWorkException) {
					setDateSDK(location, false, sb);
					return;
				}
				System.out.print(sb.toString());
				System.out.print(app.longitude+""+"/"+app.longitude+"");
				setDateSDK(location, true, sb);
			}
		}
	};

	private void setDateSDK(BDLocation location, boolean bdissucess,
			StringBuffer sb) {

		if (!bdissucess) {// �����λʧ�� Ĭ�ϱ�����γ��
			Toast.makeText(this, sb, Toast.LENGTH_SHORT).show();
			// ���빫������
			if (app != null) {
				app.cityName = location.getCity();
			} else {
				app = (YncApplication) getApplicationContext();
				app.cityName = location.getCity();
			}
		} else {
			// ���빫������
			if (app != null) {
				app.cityName = location.getCity();
			} else {
				app = (YncApplication) getApplicationContext();
				app.cityName = location.getCity();
				
			}
		}
	}

	@Override
	protected Context getContext() {
		return this;
	}
}
