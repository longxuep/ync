package com.alec.ync.activity;

import com.alec.ync.application.YncApplication;

import android.app.Activity;
import android.os.Bundle;
/**
 * ������activity �� �����е�activity �� ��Ӧ�ü̳� Baceactivity
 * @author long
 * @date 2016��6��13��14:54:59
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
