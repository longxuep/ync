package com.alec.ync.adpter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 */
public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

	protected String[] title;

	public BaseFragmentPagerAdapter(FragmentManager fm, String[] title) {
		super(fm);

		this.title = title;
	}

	@Override
	public int getCount() {
		return title.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {

		return title[position];
	}

}
