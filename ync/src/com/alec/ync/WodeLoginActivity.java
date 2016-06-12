package com.alec.ync;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alec.yzc.R;

public class WodeLoginActivity extends Activity implements OnClickListener{
	private TextView _back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wodelogin);
		_back=(TextView)findViewById(R.id.xiangcundingwei_back);
		_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v!=null){
			if(v.getId()==R.id.xiangcundingwei_back){
				finish();
			}
		}
		
	}			
	
}
