package com.alec.ync;


import com.alec.yzc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 乡村界面
 *Laier工作室
 **/

public class XiangcunActivity extends Activity {

	private TextView mDingwei;
	private ImageView mSearch;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangcun);
		initview();
	}


	private void initview() {
		mDingwei = (TextView) findViewById(R.id.xiangcun_dingwei);
		mSearch = (ImageView) findViewById(R.id.xiangcun_search);
		
		XiangcunOnclickListener mOnclickListener = new XiangcunOnclickListener();
		mDingwei.setOnClickListener(mOnclickListener);
		mSearch.setOnClickListener(mOnclickListener);
		
	}	

	private class XiangcunOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id.xiangcun_dingwei:
				Intent intent = new Intent(XiangcunActivity.this,XiangcunDingweiActivity.class);
				startActivity(intent);
				break;	
			case R.id.xiangcun_search:
				Intent intent2 = new Intent(XiangcunActivity.this,XiangcunSearchActivity.class);
				startActivity(intent2);
				break;			
			}
		}

	}	
	
}
