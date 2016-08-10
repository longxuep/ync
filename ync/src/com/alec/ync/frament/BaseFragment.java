package com.alec.ync.frament;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alec.ync.application.YncApplication;
import com.alec.ync.interfaces.BaseInterface;
import com.alec.ync.util.ToastManager;
import com.alec.ync.widget.ProgressDialog;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/**
 * ������fargment �� �����е�fragment �� ��Ӧ�ü̳� BaceFragment
 * @author long
 * @date 2016��6��13��14:54:59
 */
public abstract class BaseFragment extends Fragment implements BaseInterface{
	protected YncApplication app;
	public RequestQueue mRequestQueue;
	private ProgressDialog mProgressDialog;
	protected abstract Context getContext();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (YncApplication)getActivity().getApplication();
		if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getActivity());
        }
	}
	/**
	 * ��ʾ ���ؿ�
	 * 
	 * @param dismiss
	 * @param cancel
	 * @param isShow
	 *            ������������Ƿ�ȥ��LoadingView��true:��ȡ��,false:����
	 */
	public void showLoadingView(OnDismissListener dismiss, OnCancelListener cancel, boolean isShow) {
		if (getActivity() != null && isVisible()) {
			if (!getActivity().isFinishing()) {
	//	if (!getActivity().isFinishing()) {
			if (mProgressDialog == null) {
				initProgressDialog(dismiss, cancel, isShow);
				mProgressDialog.show();
			} else if (!mProgressDialog.isShowing()) {
				mProgressDialog.setCanceledOnTouchOutside(isShow);
				mProgressDialog.show();
			}
			}
		}	
	//	}
	}

	@Override
	public void showLoadingView(OnDismissListener dismiss, OnCancelListener cancel) {
		showLoadingView(dismiss, cancel, true);// Ĭ�ϵ���������ȡ��
	}

	@Override
	public void hideLoadingView() {
		if (getActivity() != null && isVisible()) {
			if (!getActivity().isFinishing()) {
		//if (!getActivity().isFinishing()) {
			if (mProgressDialog != null) {
				mProgressDialog.dismiss();
				mProgressDialog.setCanceledOnTouchOutside(true);// �ָ�Ĭ��״̬����Ϊ�õ��ǵ�ʵ��
			}
			}
		}	
		//}
	}

	/**
	 * ��ʼ�������жԻ���
	 * 
	 * @param isShow
	 * 
	 * @param message
	 */
	private void initProgressDialog(OnDismissListener dismiss, OnCancelListener cancel, boolean isShow) {
		mProgressDialog = ProgressDialog.createDialog(getContext());
		mProgressDialog.setCanceledOnTouchOutside(isShow);
		if (dismiss != null) {
			mProgressDialog.setOnDismissListener(dismiss);
		}
		if (cancel != null) {
			mProgressDialog.setOnCancelListener(cancel);
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
