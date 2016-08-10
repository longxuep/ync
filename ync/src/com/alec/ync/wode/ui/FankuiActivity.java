package com.alec.ync.wode.ui;


import java.util.ArrayList;
import java.util.List;

import com.alec.ync.adpter.FankuiMessageAdapter;
import com.alec.ync.model.FankuiMessage;
import com.alec.yzc.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 意见反馈
 * @author long
 *
 */
public class FankuiActivity extends Activity {

		private TextView mBack;
		
		/**
		 * 展示消息的listview
		 */
		private ListView mFankuiView;
		/**
		 * 文本域
		 */
		private EditText mMsg;
		/**
		 * 存储聊天消息
		 */
		private List<FankuiMessage> mDatas = new ArrayList<FankuiMessage>();
		/**
		 * 适配器
		 */
		private FankuiMessageAdapter mAdapter;

		private Handler mHandler = new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				FankuiMessage from = (FankuiMessage) msg.obj;
				mDatas.add(from);
				mAdapter.notifyDataSetChanged();
				mFankuiView.setSelection(mDatas.size() - 1);
			};
		};		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.wode_xtxx_fankui_activity);
			initview();
			mAdapter = new FankuiMessageAdapter(this, mDatas);
			//mFankuiView.setAdapter(mAdapter);
		}
		
		private void initview() {
			mBack = (TextView) findViewById(R.id._back);		
			WodeSetOnclickListener mOnclickListener = new WodeSetOnclickListener();
			mBack.setOnClickListener(mOnclickListener);	
		}	
		
		private class WodeSetOnclickListener implements View.OnClickListener {
			public void onClick(View v) {
				int mID = v.getId();
				switch (mID) {
				case R.id._back:
					finish();
					break;	
				}
			}

		}			
		
	}

