package com.alec.ync.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alec.ync.application.YncApplication;
import com.alec.ync.util.ToastManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/**
 * 公共的fargment 类 ；所有的fragment 类 都应该继承 BaceFragment
 * @author long
 * @date 2016年6月13日14:54:59
 */
public class BaseFragment extends Fragment{
	protected YncApplication app;
	public RequestQueue mRequestQueue;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (YncApplication)getActivity().getApplication();
		if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getActivity());
        }
	}
	/**
	 * 显示Toast信息,短时间
	 * 
	 * @param ms
	 */
	public void showToastMsgShort(String ms) {
		if (getActivity() != null && isVisible()) {
			if (!getActivity().isFinishing()) {
				ToastManager.showShortToast(getActivity(), ms);
			}
		}
	}

	/**
	 * 显示Toast信息，长时间
	 * 
	 * @param ms
	 */
	public void showToastMsgLong(String ms) {
		if (getActivity() != null && isVisible()) {
			if (!getActivity().isFinishing()) {
				ToastManager.showLongToast(getActivity(), ms);
			}
		}
	}
}
