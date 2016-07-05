package com.alec.ync.volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alec.ync.util.Constant;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;

import android.content.Context;

/**
 *
 */
public class BaseVolleyRequest extends Request<JSONObject> {

	private Listener<JSONObject> listener;
	private Context context;
	private int reconnect;
	private Map<String, String> params;
	private DefaultRetryPolicy retryPolicy;

	public BaseVolleyRequest(int method, Context context, String url, Map<String, String> params,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		this(method, context, url, params, listener, errorListener, DefaultRetryPolicy.DEFAULT_MAX_RETRIES);
	}

	public BaseVolleyRequest(int method, Context context, String url, Map<String, String> params,
			Listener<JSONObject> listener, ErrorListener errorListener, int reconnect) {
		super(method, url, errorListener);

		this.context = context;
		this.listener = listener;
		this.reconnect = reconnect;
		final Map<String, String> parameter_sign;

		if (params == null) {
			parameter_sign = new HashMap<String, String>();
		} else {
			parameter_sign = params;
		}

		Http.logParams(url, parameter_sign);
		url = Http.appendGetUrl(method, url, parameter_sign);
		this.params = parameter_sign;

	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			setRetryPolicy(null);
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			if (Constant.debug) {
				System.out.println("resp1onse : response = " + jsonString);
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

	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	/*
	 * Volley设置请求超时时间：(non-Javadoc)
	 * @see com.android.volley.Request#getRetryPolicy()
	 */
	@Override
	public RetryPolicy getRetryPolicy() {
		
		if (retryPolicy == null) {
			retryPolicy = new DefaultRetryPolicy(Http.SOCKET_TIMEOUT, reconnect,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		}
		return retryPolicy;
	}

}
