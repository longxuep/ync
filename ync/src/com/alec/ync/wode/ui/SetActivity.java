package com.alec.ync.wode.ui;

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
public class SetActivity extends Activity {

	private TextView mBack;
	private RelativeLayout mHelp;
	private RelativeLayout mGuangyu;
	private RelativeLayout mFankui;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wode_xtxx_activity);
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
				Intent intent = new Intent(SetActivity.this,HelpActivity.class);
				startActivity(intent);
				break;
			case R.id.wode_guangyu:
				Intent intent2 = new Intent(SetActivity.this,GuangyuActivity.class);
				startActivity(intent2);
				break;
			case R.id.wode_fankui:
				Intent intent3 = new Intent(SetActivity.this,FankuiActivity.class);
				startActivity(intent3);
				break;				
			}
		}

	}		
	
	
}
