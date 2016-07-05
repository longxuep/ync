package com.alec.ync.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alec.ync.application.YncApplication;
import com.alec.ync.util.ToastManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/**
 * ������fargment �� �����е�fragment �� ��Ӧ�ü̳� BaceFragment
 * @author long
 * @date 2016��6��13��14:54:59
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
	 * ��ʾToast��Ϣ,��ʱ��
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
	 * ��ʾToast��Ϣ����ʱ��
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
