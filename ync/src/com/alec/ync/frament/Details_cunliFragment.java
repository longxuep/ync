package com.alec.ync.frament;

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
import com.alec.ync.widget.TabPageIndicator;
import com.alec.yzc.R;

/**
 * 村里
 *Laier工作室
 **/

@SuppressLint("InflateParams")
public class Details_cunliFragment extends BaseFragment {

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_details_cunli, null);
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
		return getActivity();
	}
}
