package com.alec.ync;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alec.yzc.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 启动界面
 *Laier工作室 
 **/

public class StartActivity extends Activity {

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
					// TODO Auto-generated catch block
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
			Intent intent = new Intent(StartActivity.this,FrameActivity.class);
			startActivity(intent);
			/*
			if (isFristRun()) {
				Intent intent = new Intent(StartActivity.this,MainActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(StartActivity.this,FrameActivity.class);
				startActivity(intent);
			}
			*/
			finish();
		};
	};

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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

		}
		return true;
	}
}
