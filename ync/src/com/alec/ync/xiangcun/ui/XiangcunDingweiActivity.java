package com.alec.ync.xiangcun.ui;

import com.alec.yzc.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * 手动选择城市
 * @author long
 *
 */
public class XiangcunDingweiActivity extends Activity {

	private TextView mBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangcundingwei);
		initview();
	}
	
	private void initview() {
		mBack = (TextView) findViewById(R.id.xiangcundingwei_back);		
		WodeSetOnclickListener mOnclickListener = new WodeSetOnclickListener();
		mBack.setOnClickListener(mOnclickListener);	
	}	
	
	private class WodeSetOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id.xiangcundingwei_back:
				finish();
				break;	
			}
		}

	}	

}