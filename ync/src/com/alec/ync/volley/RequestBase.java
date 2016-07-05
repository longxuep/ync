package com.alec.ync.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.alec.ync.application.YncApplication;
import com.alec.ync.util.Constant;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class RequestBase extends Request<JSONObject> {
	protected Map<String, String> params;
	protected Listener<JSONObject> mListener;
	protected static YncApplication app;

	protected Context context;
	protected String tag = "RequestBase";

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
	 */
	public RequestBase(int method, String url, JSONObject jsonObject, Listener<JSONObject> listener,
			ErrorListener errorListener, Map<String, String> params) {
		super(method, url, errorListener);
		mListener = listener;
		this.params = params;
		/* 重试 */
		setRetryPolicy(new DefaultRetryPolicy(60000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		Log.d(tag, url);
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		if (Constant.debug) {
			for (Entry<String, String> entry : params.entrySet()) {
				Log.d(tag, entry.getKey() + "：" + entry.getValue());
			}
		}
		return params;
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		String jsonString = "";
		try {
			jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

			if (Build.VERSION.SDK_INT < 14) {
				if (jsonString != null && jsonString.startsWith("\ufeff")) {
					jsonString = jsonString.substring(1);
				}
			}
			if (Constant.debug) {
				Log.i(tag, "接口返回数据：" + jsonString);
			}
			return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {

			return Response.error(new ParseError(je));
		}
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		mListener.onResponse(response);
	}
}