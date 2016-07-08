package com.alec.ync.activity;

import com.alec.yzc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * …Ë÷√
 * @author long
 *
 */
public class WodeSetActivity extends Activity {

	private TextView mBack;
	private RelativeLayout mHelp;
	private RelativeLayout mGuangyu;
	private RelativeLayout mFankui;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wodeset);
		initview();
	}

	private void initview() {
		mBack = (TextView) findViewById(R.id.wodeset_back);
		mHelp = (RelativeLayout) findViewById(R.id.wode_help);
		mGuangyu = (RelativeLayout) findViewById(R.id.wode_guangyu);
		mFankui = (RelativeLayout) findViewById(R.id.wode_fankui);
		
		WodeSetOnclickListener mOnclickListener = new WodeSetOnclickListener();
		mBack.setOnClickListener(mOnclickListener);
		mHelp.setOnClickListener(mOnclickListener);
		mGuangyu.setOnClickListener(mOnclickListener);
		mFankui.setOnClickListener(mOnclickListener);
		
	}	
	
	private class WodeSetOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id.wodeset_back:
				finish();
				break;	
			case R.id.wode_help:
				Intent intent = new Intent(WodeSetActivity.this,WodeSetHelpActivity.class);
				startActivity(intent);
				break;
			case R.id.wode_guangyu:
				Intent intent2 = new Intent(WodeSetActivity.this,WodeSetGuangyuActivity.class);
				startActivity(intent2);
				break;
			case R.id.wode_fankui:
				Intent intent3 = new Intent(WodeSetActivity.this,WodeSetFankuiActivity.class);
				startActivity(intent3);
				break;				
			}
		}

	}		
	
	
}
