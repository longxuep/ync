package com.alec.ync.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alec.ync.application.YncApplication;
/**
 * 公共的fargment 类 ；所有的fragment 类 都应该继承 BaceFragment
 * @author long
 * @date 2016年6月13日14:54:59
 */
public class BaseFragment extends Fragment{
	protected YncApplication app;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (YncApplication)getActivity().getApplication();
	}

}
