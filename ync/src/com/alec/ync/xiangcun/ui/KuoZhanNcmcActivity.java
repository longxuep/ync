package com.alec.ync.xiangcun.ui;

import java.util.ArrayList;

import com.alec.ync.adpter.Jshi_OneAdapter;
import com.alec.ync.model.Message;
import com.alec.yzc.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class KuoZhanNcmcActivity extends Activity {

	private TextView mBack;
	private ListView _lv_ncmc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiangcun_kuozhan_ncmc_activity);
		initview();
	}
	
	private void initview() {
		mBack = (TextView) findViewById(R.id.ncmc_back);		
		WodeSetOnclickListener mOnclickListener = new WodeSetOnclickListener();
		mBack.setOnClickListener(mOnclickListener);	
		
		_lv_ncmc = (ListView) findViewById(R.id.lv_ncmc);
		ArrayList<Message> list = new ArrayList<Message>();
		for (int i = 0; i < 10; i++) {
			Message ms = new Message();
			ms.setTitle("Ïç´å " + i);
			ms.setImag("http://www.baidu.com/img/bd_logo1.png");
			list.add(ms);
		}
		Jshi_OneAdapter oneAdapter = new Jshi_OneAdapter(this, list);
		_lv_ncmc.setAdapter(oneAdapter);
		oneAdapter.notifyDataSetChanged();		
	}	
	
	private class WodeSetOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id.ncmc_back:
				finish();
				break;	
			}
		}

	}	

}