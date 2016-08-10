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
 * 公共的activity 类 ；所有的activity 类 都应该继承 Baceactivity
 * @author long
 * @date 2016年6月13日14:54:59
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
	 * 显示 加载框
	 * 
	 * @param dismiss
	 * @param cancel
	 * @param isShow
	 *            点击外面区域是否去掉LoadingView；true:可取消,false:不可
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
		showLoadingView(dismiss, cancel, true);// 默认点击外面可以取消
	}

	@Override
	public void hideLoadingView() {
		if (!isFinishing()) {
			if (mProgressDialog != null) {
				mProgressDialog.dismiss();
				mProgressDialog.setCanceledOnTouchOutside(true);// 恢复默认状态，因为用的是单实例
			}
		}
	}

	/**
	 * 初始化加载中对话框
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
	 * 显示Toast信息,短时间
	 * 
	 * @param ms
	 */
	public void showToastMsgShort(String ms) {
		if (!this.isFinishing()) {
			ToastManager.showShortToast(this, ms);
		}
	}

	/**
	 * 显示Toast信息，长时间
	 * 
	 * @param ms
	 */
	public void showToastMsgLong(String ms) {
		if (!this.isFinishing()) {
			ToastManager.showLongToast(this, ms);
		}
	}

}
