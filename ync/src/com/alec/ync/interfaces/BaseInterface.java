package com.alec.ync.interfaces;

import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
public interface BaseInterface {
	/**
	 * ��ʾloading view
	 */
	public void showLoadingView(OnDismissListener dismiss, OnCancelListener Cancel);

	/**
	 * ����loading view
	 */
	public void hideLoadingView();
	
	
	public void showToastMsgShort(String ms);
	
	public void showToastMsgLong(String ms);
}
