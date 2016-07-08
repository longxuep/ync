package com.alec.ync.activity;

import com.alec.yzc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * °ïÖúÖÐÐÄ
 * @author long
 *
 */
public class WodeSetHelpActivity extends Activity {

	private TextView mBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wodesethelp);
		initview();
	}
	
	private void initview() {
		mBack = (TextView) findViewById(R.id.wodesethelp_back);		
		WodeSetOnclickListener mOnclickListener = new WodeSetOnclickListener();
		mBack.setOnClickListener(mOnclickListener);	
	}	
	
	private class WodeSetOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id.wodesethelp_back:
				finish();
				break;	
			}
		}

	}			
	
}
