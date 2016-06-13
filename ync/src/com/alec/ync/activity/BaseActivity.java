package com.alec.ync.activity;

import com.alec.ync.application.YncApplication;

import android.app.Activity;
import android.os.Bundle;
/**
 * 公共的activity 类 ；所有的activity 类 都应该继承 Baceactivity
 * @author long
 * @date 2016年6月13日14:54:59
 */
public class BaseActivity extends Activity {
	protected  YncApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (YncApplication) getApplication();
		app.addActivity(this);
	}
	

}
