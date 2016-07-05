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
 * 扩展JsonObjectRequest，可生产带参数的Get请求URL
 * 
 * @注意：uid,sign,version以上3个参数各页面不需要独立传入，由本接口统一传参 
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
	 *            完整的url
	 * @param jsonRequest
	 *            扩展用，暂时固定传null
	 * @param listener
	 *            Listener to receive the JSON response，
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 * @param params
	 *            参数列表;POST和get通用;注意：uid,sign,version三个参数各页面不需要独立传入
	 *            {@link #createParams} ，由本接口统一传参
	 * @param a
	 *            传入所在Activity的引用
	 */
	public HttpJsonObjectRequest(int method, String url, JSONObject jsonObject, Listener<JSONObject> listener,
			final ErrorListener errorListener, Map<String, String> params, final Activity ac) {
		/* 所有请求强制使用post */
		super(method, method == Method.POST ? url : Volley.createUrlForMethodGet(url,
				createParams(params, ac)), jsonObject, listener, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.err.println(error);
				if (error instanceof TimeoutError) {
					showToastMsg(ac, "连接超时，请保持网络畅通");
				} else if (error instanceof ServerError) {
					showToastMsg(ac, "连接失败，服务器可能正在休息~，请稍后再试");
				} else if (error instanceof NetworkError) {
					showToastMsg(ac, "连接失败，请确保网络处于打开状态");
				} else if (error instanceof ParseError) {
					showToastMsg(ac, "数据格式错误，请联系管理员");
				}
				error.printStackTrace();
				if (errorListener != null) {
					errorListener.onErrorResponse(error);
				}
			}
		}, createParams(params, ac));
		
	}

	/**
	 * 用于添加版本号及登录信息参数
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
