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
 * 集市界面
 *Laier工作室
 **/

@SuppressLint("InflateParams")
public class JishiFragment1 extends BaseFragment {

	private View view;
	private TextView mDingwei;
	private final String[] TITLES = new String[] { "农货云集", "旅游专区"};
	private final Fragment[] fragments = new Fragment[TITLES.length];
	private NavigationAdapter adapter;
	
	private ViewPager viewPager;
	private TabPageIndicator pagertab;
	private int index=0;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_jishi, null);
			initView(view);
		}
		return view;
	}
	private void initView(View view){
		mDingwei = (TextView) view.findViewById(R.id.xiangcun_dingwei);
		
		pagertab = (TabPageIndicator) view.findViewById(R.id.pagertab);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		
		adapter = new NavigationAdapter(getActivity().getSupportFragmentManager(), TITLES,index);
		viewPager.setAdapter(adapter);
		pagertab.setViewPager(viewPager);
		pagertab.setCurrentItem(index);
		
		if(app!=null && app.cityName!=null){
			mDingwei.setText(app.cityName+" >");
		}
	}
	private class NavigationAdapter extends BaseFragmentPagerAdapter {
		int index;
		public NavigationAdapter(FragmentManager fm, String[] title,int index) {
			super(fm, title);
			this.index=index;
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment f = fragments[index];

			if (f == null) {
				switch (arg0) {
					case 0:
						f =new Jshi_twoFragment();
						break;
					case 1:
						f =new Jshi_threeFragment();
						break;
					default:
						f = new Jshi_twoFragment();
						break;
				}
				
			}

			return f;
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
