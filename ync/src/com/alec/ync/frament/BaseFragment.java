package com.alec.ync.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alec.ync.application.YncApplication;
/**
 * ������fargment �� �����е�fragment �� ��Ӧ�ü̳� BaceFragment
 * @author long
 * @date 2016��6��13��14:54:59
 */
public class BaseFragment extends Fragment{
	protected YncApplication app;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (YncApplication)getActivity().getApplication();
	}

}
