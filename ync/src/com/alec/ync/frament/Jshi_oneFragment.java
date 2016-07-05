package com.alec.ync.frament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alec.ync.adpter.Jshi_OneAdapter;
import com.alec.ync.model.Message;
import com.alec.ync.volley.Http;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.yzc.R;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class Jshi_oneFragment extends BaseFragment {
	private View view;
	private ListView one_listview;
	private Jshi_OneAdapter oneAdapter;
	private List<Message> list;
	private HttpJsonObjectRequest request_info;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_jshi_one, null);
			initView(view);
		}
		return view;
	}

	private void initView(View view) {
		one_listview = (ListView) view.findViewById(R.id.one_listview);
		list = new ArrayList<Message>();
		for (int i = 0; i < 10; i++) {
			Message ms = new Message();
			ms.setTitle("乡村 " + i);
			ms.setImag("http://www.baidu.com/img/bd_logo1.png");
			list.add(ms);
		}
		oneAdapter = new Jshi_OneAdapter(getActivity(), list);
		one_listview.setAdapter(oneAdapter);
		oneAdapter.notifyDataSetChanged();
		//getData();//请求数据
		loadData();
	}

	private void getData() {
		Map<String, String> params=new HashMap<String, String>();
		params.put("phone", "13647541833");
		params.put("passwd", "1833");
		String url="http://yzc.lzbxj.com/api/apiLogin.php";
		//String url="http://api.haoyangapp.com/user/login";
		//String url="http://appserver.shikee.com/user/login?uname=000&password=00000&uuid=0000";
		request_info = new HttpJsonObjectRequest(Method.GET, url, null, successListener, errorListener,
				params, getActivity());
		mRequestQueue.add(request_info);
	}
	/*加载数据*/
	private void loadData() {
		Map<String, String> params=new HashMap<String, String>();
		params.put("phone", "13647541833");
		params.put("passwd", "1833");
		String url="http://yzc.lzbxj.com/api/apiLogin.php";
		Http.sendHttp(Method.GET, getActivity(),
				url, params,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						onSuccess(response);
						
					}

				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						onError(error);
					}
				});
		
	}
	//错误
	public void onError(VolleyError error) {
		showToastMsgShort("错误"+error);
	}
	//成功返回json
	public void onSuccess(JSONObject response) {
		boolean success = false;
		try {
			showToastMsgShort("上线="+response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			try {
				showToastMsgShort("显示"+response);
			} catch (Exception e) {
			}
		}
	};

	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			System.out.println(error);
		}
	};
	@Override
	public void onDestroyView() {
		((ViewGroup) view.getParent()).removeView(view);
		super.onDestroyView();
	}
}
