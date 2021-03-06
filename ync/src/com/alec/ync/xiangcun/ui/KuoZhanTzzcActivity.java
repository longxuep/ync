package com.alec.ync.xiangcun.ui;

import com.alec.yzc.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class KuoZhanTzzcActivity extends Activity {

	private TextView mBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiangcun_kuozhan_tzzc_activity);
		initview();
	}
	
	private void initview() {
		mBack = (TextView) findViewById(R.id.tzzc_back);		
		WodeSetOnclickListener mOnclickListener = new WodeSetOnclickListener();
		mBack.setOnClickListener(mOnclickListener);	
	}	
	
	private class WodeSetOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id.tzzc_back:
				finish();
				break;	
			}
		}

	}	

}