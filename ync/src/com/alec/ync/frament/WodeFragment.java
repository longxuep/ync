package com.alec.ync.frament;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alec.ync.activity.WodeLoginActivity;
import com.alec.ync.activity.WodeRegisterActivity;
import com.alec.ync.activity.WodeSetActivity;
import com.alec.ync.activity.WodeShopCartActivity;
import com.alec.yzc.R;

/**
 * 我的界面
 *Laier工作室
 **/

public class WodeFragment extends BaseFragment {

	private TextView mWode_register, mWode_login, mWode_Shopcart;
	private ImageView mWode_set;
	
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_wode, null);
			initView(view);
		}
		return view;
	}
	private void initView(View v) {
		mWode_register = (TextView) v.findViewById(R.id.wode_register);
		mWode_login = (TextView) v.findViewById(R.id.wode_login);
		mWode_set = (ImageView) v.findViewById(R.id.wode_set);
		mWode_Shopcart = (TextView) v.findViewById(R.id.wode_shopcart);
		
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
				Intent intent = new Intent(getActivity(),WodeRegisterActivity.class);
				startActivity(intent);
				break;
			case R.id.wode_login:
				Intent intent2 = new Intent(getActivity(),WodeLoginActivity.class);
				startActivity(intent2);
				break;
			case R.id.wode_set:
				Intent intent3 = new Intent(getActivity(),WodeSetActivity.class);
				startActivity(intent3);
				break;
			case R.id.wode_shopcart:
				Intent intent4 = new Intent(getActivity(),WodeShopCartActivity.class);
				startActivity(intent4);
				break;	
			}
		}

	}		
	@Override
	public void onDestroyView() {
		((ViewGroup)view.getParent()).removeView(view);
		super.onDestroyView();
	}
}
