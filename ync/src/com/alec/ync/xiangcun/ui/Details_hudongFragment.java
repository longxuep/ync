package com.alec.ync.xiangcun.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alec.ync.adpter.BaseFragmentPagerAdapter;
import com.alec.ync.frament.BaseFragment;
import com.alec.ync.widget.TabPageIndicator;
import com.alec.yzc.R;

/**
 * ����
 *Laier������
 **/

@SuppressLint("InflateParams")
public class Details_hudongFragment extends BaseFragment {

	private View view;
	private String villid;
	public Details_hudongFragment getvid(String vid){
		Details_hudongFragment dc = new Details_hudongFragment();
		dc.villid = vid;
		return dc;
	}	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_details_zhixun, null);
			initView(view);
		}
		return view;
	}
	private void initView(View view){
		
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