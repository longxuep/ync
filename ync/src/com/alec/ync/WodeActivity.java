package com.alec.ync;

import com.alec.yzc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的界面
 *Laier工作室
 **/

public class WodeActivity extends Activity {

	private TextView mWode_register, mWode_login, mWode_Shopcart;
	private ImageView mWode_set;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wode);
		initView();
	}

	private void initView() {
		mWode_register = (TextView) findViewById(R.id.wode_register);
		mWode_login = (TextView) findViewById(R.id.wode_login);
		mWode_set = (ImageView) findViewById(R.id.wode_set);
		mWode_Shopcart = (TextView) findViewById(R.id.wode_shopcart);
		
		WodeOnclickListener mOnclickListener = new WodeOnclickListener();
		mWode_register.setOnClickListener(mOnclickListener);
		mWode_login.setOnClickListener(mOnclickListener);	
		mWode_set.setOnClickListener(mOnclickListener);
		mWode_Shopcart.setOnClickListener(mOnclickListener);		
	}	
	
	private class WodeOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id.wode_register:
				Intent intent = new Intent(WodeActivity.this,WodeRegisterActivity.class);
				startActivity(intent);
				break;
			case R.id.wode_login:
				Intent intent2 = new Intent(WodeActivity.this,WodeLoginActivity.class);
				startActivity(intent2);
				break;
			case R.id.wode_set:
				Intent intent3 = new Intent(WodeActivity.this,WodeSetActivity.class);
				startActivity(intent3);
				break;
			case R.id.wode_shopcart:
				Intent intent4 = new Intent(WodeActivity.this,WodeShopCartActivity.class);
				startActivity(intent4);
				break;	
			}
		}

	}		
	
}
