package com.alec.ync.base;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.alec.ync.application.YncApplication;
import com.alec.ync.interfaces.BaseInterface;
import com.alec.ync.util.ToastManager;
import com.alec.ync.widget.ProgressDialog;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/**
 * ������activity �� �����е�activity �� ��Ӧ�ü̳� Baceactivity
 * @author long
 * @date 2016��6��13��14:54:59
 */
public abstract class BaseActivity extends FragmentActivity implements BaseInterface{
	protected  YncApplication app;
	public RequestQueue mRequestQueue;
	private ProgressDialog mProgressDialog;
	protected abstract Context getContext();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (YncApplication) getApplication();
		app.addActivity(this);
		if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
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
		if (!isFinishing()) {
			if (mProgressDialog == null) {
				initProgressDialog(dismiss, cancel, isShow);
				mProgressDialog.show();
			} else if (!mProgressDialog.isShowing()) {
				mProgressDialog.setCanceledOnTouchOutside(isShow);
				mProgressDialog.show();
			}
		}
	}

	@Override
	public void showLoadingView(OnDismissListener dismiss, OnCancelListener cancel) {
		showLoadingView(dismiss, cancel, true);// Ĭ�ϵ���������ȡ��
	}

	@Override
	public void hideLoadingView() {
		if (!isFinishing()) {
			if (mProgressDialog != null) {
				mProgressDialog.dismiss();
				mProgressDialog.setCanceledOnTouchOutside(true);// �ָ�Ĭ��״̬����Ϊ�õ��ǵ�ʵ��
			}
		}
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
		if (!this.isFinishing()) {
			ToastManager.showShortToast(this, ms);
		}
	}

	/**
	 * ��ʾToast��Ϣ����ʱ��
	 * 
	 * @param ms
	 */
	public void showToastMsgLong(String ms) {
		if (!this.isFinishing()) {
			ToastManager.showLongToast(this, ms);
		}
	}

}
