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
 * 启动界面 Laier工作室
 **/

public class StartActivity extends BaseActivity {
	private LocationService locationService; //百度定位

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
			// 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
			locationService.registerListener(mListener);
			// 注册监听
			int type = getIntent().getIntExtra("from", 0);
			if (type == 0) {
				locationService.setLocationOption(locationService.getDefaultLocationClientOption());
			} else if (type == 1) {
				locationService.setLocationOption(locationService.getOption());
			}
			locationService.start(); //定位SDK
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
	 *      定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 * 
	 */
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				/**
				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
				 */
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());// 定位类型
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());// 纬度				
				app.latitude = location.getLatitude();
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());// 经度
				app.longitude = location.getLongitude();
				sb.append("\nradius : ");
				sb.append(location.getRadius());// 半径
				sb.append("\nCountryCode : ");
				sb.append(location.getCountryCode());// 国家码
				sb.append("\nCountry : ");
				sb.append(location.getCountry());// 国家名称
				sb.append("\ncitycode : ");
				sb.append(location.getCityCode());// 城市编码
				sb.append("\ncity : ");
				sb.append(location.getCity());// 城市
				sb.append("\nDistrict : ");
				sb.append(location.getDistrict());// 区
				sb.append("\nStreet : ");
				sb.append(location.getStreet());// 街道
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());// 地址信息
				app.address = location.getAddrStr();
				sb.append("\nDescribe: ");
				sb.append(location.getDirection());
				sb.append("\nPoi: ");				   
				sb.append("\n省:");
			    sb.append(location.getProvince());
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// 速度 单位：km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());// 卫星数目
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// 海拔高度 单位：米
					sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
					sb.append("\ndescribe : ");
					sb.append("gps定位成功");
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					// 运营商信息
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("网络定位成功");
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
					sb.append("\ndescribe : ");
					sb.append("离线定位成功，离线定位结果也是有效的");
				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("服务端网络定位失败，请检查是否开启网络链接");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启应用");
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

		if (!bdissucess) {// 如果定位失败 默认北京经纬度
			Toast.makeText(this, sb, Toast.LENGTH_SHORT).show();
			// 存入公共变量
			if (app != null) {
				app.cityName = location.getCity();
			} else {
				app = (YncApplication) getApplicationContext();
				app.cityName = location.getCity();
			}
		} else {
			// 存入公共变量
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
