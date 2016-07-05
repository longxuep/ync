package com.alec.ync.volley;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.content.Context;

import com.alec.ync.util.Constant;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

/**
 */

public class Http{
public static int SOCKET_TIMEOUT = 8 * 1000;
	
	
	/**
	 * 防止工具类被实例化
	 */
	private Http(){
		throw new AssertionError();
	}
	
	/**
	 * 请求网络，必须检查parameter里面是否有 null值，否则volley会报错 
	 * @param method	Method.POST、Method.GET
	 * @param context	上下文
	 * @param url		请求连接
	 * @param parameter	请求参数
	 * @param listener	成功回调
	 * @param errorListener	失败回调
	 */
	public static void sendHttp(int method, final Context context, String url,final Map<String, String> parameter, final Listener<JSONObject> listener,ErrorListener errorListener) {
		sendHttp(method, context, url, parameter, listener, errorListener, false, DefaultRetryPolicy.DEFAULT_MAX_RETRIES);
	}
	
	public static void sendHttp(int method, final Context context, String url,final Map<String, String> parameter, final Listener<JSONObject> listener,ErrorListener errorListener, final boolean isShowLogin) {
		sendHttp(method, context, url, parameter, listener, errorListener, isShowLogin, DefaultRetryPolicy.DEFAULT_MAX_RETRIES);
	}

	/**
	 * 请求网络，必须检查parameter里面是否有 null值，否则volley会报错 
	 * @param method	Method.POST、Method.GET
	 * @param context	上下文
	 * @param url		请求连接
	 * @param parameter	请求参数
	 * @param listener	成功回调
	 * @param errorListener	失败回调
	 */
	public static void sendHttp(int method, final Context context, String url,final Map<String, String> parameter, final Listener<JSONObject> listener,ErrorListener errorListener, final boolean isShowLogin, final int reTries) {
		
		Request<JSONObject> request = new BaseVolleyRequest(method, context, url, parameter, listener, errorListener);
		
		NetUtil netContext = NetUtil.getInstance(context);
		netContext.getJsonRequestQueue().add(request);
	}

	public static String appendGetUrl(int method, String url, final Map<String, String> parameter_sign) {
		
		//转换 GET为 链接模式
		if (method == Method.GET && parameter_sign!=null) {
			url = bindGetUrlParams(url, parameter_sign);
		}
		return url;
	}

	/**
	 * 绑定参数 到 Get请求url
	 */
	public static String bindGetUrlParams(String url,
			final Map<String, String> params) {
		try {
			StringBuffer sb = new StringBuffer();
			for (Object o : params.keySet()) {
				sb.append(o).append("=").append(URLEncoder.encode(params.get(o), "UTF-8")).append("&");
			}
			String s = sb.toString();
			//去掉结尾的&符号
			if (s.endsWith("&")){
				s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");  
			}
			url = url+"?"+s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	public static void logParams(String url, final Map<String, String> parameter_sign) {
		//System.out.println("resp1onse  url = " + url);
		for (Iterator<Map.Entry<String, String>> it = parameter_sign.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			//System.out.println("resp1onse  " + entry.getKey() + " = " + entry.getValue());
		}
	}
}