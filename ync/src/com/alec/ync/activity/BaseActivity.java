package com.alec.ync.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.alec.ync.application.YncApplication;
import com.alec.ync.util.ToastManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/**
 * 公共的activity 类 ；所有的activity 类 都应该继承 Baceactivity
 * @author long
 * @date 2016年6月13日14:54:59
 */
public class BaseActivity extends FragmentActivity {
	protected  YncApplication app;
	public RequestQueue mRequestQueue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (YncApplication) getApplication();
		app.addActivity(this);
		if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }
	}
	/**
	 * 显示Toast信息,短时间
	 * 
	 * @param ms
	 */
	public void showToastMsgShort(String ms) {
		if (!this.isFinishing()) {
			ToastManager.showShortToast(this, ms);
		}
	}

	/**
	 * 显示Toast信息，长时间
	 * 
	 * @param ms
	 */
	public void showToastMsgLong(String ms) {
		if (!this.isFinishing()) {
			ToastManager.showLongToast(this, ms);
		}
	}

}
