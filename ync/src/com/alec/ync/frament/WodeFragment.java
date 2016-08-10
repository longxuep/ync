package com.alec.ync.frament;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.alec.ync.wode.ui.FankuiActivity;
import com.alec.ync.wode.ui.GuangyuActivity;
import com.alec.ync.wode.ui.HelpActivity;
import com.alec.ync.wode.ui.LoginActivity;
import com.alec.ync.wode.ui.RegisterActivity;
import com.alec.ync.wode.ui.ShopCartActivity;
import com.alec.ync.wode.ui.WdscActivity;
import com.alec.ync.wode.ui.WdxxActivity;
import com.alec.ync.wode.ui.WtjdxcActivity;
import com.alec.yzc.R;

/**
 * 我的界面
 *Laier工作室
 **/

public class WodeFragment extends BaseFragment {

    private RelativeLayout _wddd,_wdsc,_wddzdy,_wdxchd,_help,_guangyu,_fankui,_login,_wtjdxc;
    private TextView mWode_Shopcart;
    private ImageView _wdxx;

	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.wode_activity, null);
			initView(view);
		}
		return view;
	}
	private void initView(View v) {
    /*第二期开发项
		_wddd = (RelativeLayout) v.findViewById(R.id._wddd);
	    _wddzdy = (RelativeLayout) v.findViewById(R.id._wddzdy);
        _wdxchd = (RelativeLayout) v.findViewById(R.id._wdxchd);
        mWode_Shopcart = (TextView) v.findViewById(R.id.wode_shopcart);
	 */
		_wdxx = (ImageView) v.findViewById(R.id._wdxx);
		_wtjdxc = (RelativeLayout) v.findViewById(R.id._wtjdxc);
		_wdsc = (RelativeLayout) v.findViewById(R.id._wdsc);
        _help = (RelativeLayout) v.findViewById(R.id._help);
        _guangyu = (RelativeLayout) v.findViewById(R.id._guangyu);
        _fankui = (RelativeLayout) v.findViewById(R.id._fankui);
        _login = (RelativeLayout) v.findViewById(R.id._login);
		
		WodeOnclickListener mOnclickListener = new WodeOnclickListener();
		_wdxx.setOnClickListener(mOnclickListener);
		_wtjdxc.setOnClickListener(mOnclickListener);
		_wdsc.setOnClickListener(mOnclickListener);
		_login.setOnClickListener(mOnclickListener);	
		_help.setOnClickListener(mOnclickListener);
		_guangyu.setOnClickListener(mOnclickListener);
		_fankui.setOnClickListener(mOnclickListener);
		//mWode_Shopcart.setOnClickListener(mOnclickListener);		
	}	
	
	private class WodeOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id._wdxx:
				Intent intent0 = new Intent(getActivity(),WdxxActivity.class);
				startActivity(intent0);
				break;
			case R.id._wtjdxc:
				Intent intent1 = new Intent(getActivity(),WtjdxcActivity.class);
				startActivity(intent1);
				break;
			case R.id._wdsc:
				Intent intent2 = new Intent(getActivity(),WdscActivity.class);
				startActivity(intent2);
				break;
			case R.id._login:
				Intent intent3 = new Intent(getActivity(),LoginActivity.class);
				startActivity(intent3);
				break;
			case R.id._help:
				Intent intent4 = new Intent(getActivity(),HelpActivity.class);
				startActivity(intent4);
				break;
			case R.id._guangyu:
				Intent intent5 = new Intent(getActivity(),GuangyuActivity.class);
				startActivity(intent5);
				break;
			case R.id._fankui:
				Intent intent6 = new Intent(getActivity(),FankuiActivity.class);
				startActivity(intent6);				
				break;		
			/*	第二期开发
			case R.id.wode_shopcart:
				Intent intent4 = new Intent(getActivity(),ShopCartActivity.class);
				startActivity(intent4);
				break;	
			*/	
			}
		}

	}		
	@Override
	public void onDestroyView() {
		((ViewGroup)view.getParent()).removeView(view);
		super.onDestroyView();
	}
	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return getActivity();
	}
}
