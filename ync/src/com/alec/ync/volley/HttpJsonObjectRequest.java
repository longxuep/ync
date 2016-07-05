package com.alec.ync.volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.alec.ync.application.YncApplication;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
/**
 * ��չJsonObjectRequest����������������Get����URL
 * 
 * @ע�⣺uid,sign,version����3��������ҳ�治��Ҫ�������룬�ɱ��ӿ�ͳһ���� 
 * 
 * @author long
 * 
 */
public class HttpJsonObjectRequest extends RequestBase {
	private static YncApplication app;

	/**
	 * 
	 * @param method
	 *            Method.POST or Method.GET
	 * @param url
	 *            ������url
	 * @param jsonRequest
	 *            ��չ�ã���ʱ�̶���null
	 * @param listener
	 *            Listener to receive the JSON response��
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 * @param params
	 *            �����б�;POST��getͨ��;ע�⣺uid,sign,version����������ҳ�治��Ҫ��������
	 *            {@link #createParams} ���ɱ��ӿ�ͳһ����
	 * @param a
	 *            ��������Activity������
	 */
	public HttpJsonObjectRequest(int method, String url, JSONObject jsonObject, Listener<JSONObject> listener,
			final ErrorListener errorListener, Map<String, String> params, final Activity ac) {
		/* ��������ǿ��ʹ��post */
		super(method, method == Method.POST ? url : Volley.createUrlForMethodGet(url,
				createParams(params, ac)), jsonObject, listener, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.err.println(error);
				if (error instanceof TimeoutError) {
					showToastMsg(ac, "���ӳ�ʱ���뱣�����糩ͨ");
				} else if (error instanceof ServerError) {
					showToastMsg(ac, "����ʧ�ܣ�����������������Ϣ~�����Ժ�����");
				} else if (error instanceof NetworkError) {
					showToastMsg(ac, "����ʧ�ܣ���ȷ�����紦�ڴ�״̬");
				} else if (error instanceof ParseError) {
					showToastMsg(ac, "���ݸ�ʽ��������ϵ����Ա");
				}
				error.printStackTrace();
				if (errorListener != null) {
					errorListener.onErrorResponse(error);
				}
			}
		}, createParams(params, ac));
		
	}

	/**
	 * ������Ӱ汾�ż���¼��Ϣ����
	 * 
	 * @param params
	 * @return
	 */
	public static Map<String, String> createParams(Map<String, String> params, Activity ac) {

		if (params == null) {
			params = new HashMap<String, String>();
		}
		return params;
	}

	private static void showToastMsg(Activity ac, String msg) {
		if (ac != null) {
			if (!ac.isFinishing()) {
				 Toast.makeText(ac, msg, Toast.LENGTH_SHORT).show();
			}
		}
	}

}
