package com.alec.ync.xiangcun.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import com.alec.ync.frament.BaseFragment;
import com.alec.ync.model.Village;
import com.alec.ync.util.Constant;
import com.alec.ync.util.StringUtils;
import com.alec.ync.util.URLImageGetter;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.yzc.R;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * ����
 *Laier������
 **/

@SuppressLint("InflateParams")
public class Details_cunliFragment extends BaseFragment {

	private View view;
	private TextView mContent;
	private HttpJsonObjectRequest request_info;
	private ArrayList<Village> vList = new ArrayList<Village>();
	private String villid;
	public Details_cunliFragment getvid(String vid){
		Details_cunliFragment dc = new Details_cunliFragment();
		dc.villid = vid;
		return dc;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_details_cunli, null);
			initView(view);
			getDataVillage();
		}
		return view;
	}
	
	// ��ȡ�����Ϣ
	private void getDataVillage() {
		//���ǲ������� map �����Ӳ��� ���� ��ҳ �������� ���������ͺ�
		Map<String, String> map = new HashMap<String, String>();
		map.put("villageid", villid);
		
		request_info = new HttpJsonObjectRequest(Method.GET,Constant.Url.Village, null, successListener,errorListener, map, getActivity());
		mRequestQueue.add(request_info);
	}

	Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			try {
				if (response != null) {
					Gson gson = new Gson();
					if (response.get("code").equals("success")) {
						if (response.optJSONArray("data") != null) {
							vList = gson.fromJson(response.getString("data"),new TypeToken<ArrayList<Village>>() {}.getType());
							if (vList != null && vList.size() > 0){
								    mContent.setMovementMethod(LinkMovementMethod.getInstance()); 
								    URLImageGetter ReviewImgGetter = new URLImageGetter(getActivity(),mContent,null); 
								    mContent.setText(Html.fromHtml(vList.get(0).getContent(),ReviewImgGetter,null));
								   // mContent.setText(Html.fromHtml(vList.get(0).getContent()));
								    //mContent.setText(Html.fromHtml(vList.get(0).getContent(),new MyImageGetter(getActivity(),mContent),null));
								    //mContent.setText(Html.fromHtml(vList.get(0).getContent(),ReviewImgGetter,null));
								    //mContent.setText(Html.fromHtml(vList.get(0).getContent(), StringUtils.imageGetter, null));
						
							        /*
								    //��������
								    //String html = "<h1>this is h1</h1>"  
								    //        + "<p>This text is normal</p>"  
								    //        + "<img src='http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg' />";
								    String html = vList.get(0).getContent();
								    Spanned sp = Html.fromHtml(html, new Html.ImageGetter() {  
								        @Override  
								        public Drawable getDrawable(String source) {  
								            InputStream is = null;  
								            try {  
								                is = (InputStream) new URL(source).getContent();  
								                Drawable d = Drawable.createFromStream(is, "src");  
								                d.setBounds(0, 0, d.getIntrinsicWidth(),  
								                        d.getIntrinsicHeight());  
								                is.close();  
								                return d;  
								            } catch (Exception e) {  
								                return null;  
								            }  
								        }  
								    }, null);  
								    mContent.loadData(html, "text/html", "UTF-8"); */
							}
								//showView(vList);
						} else {
							showToastMsgShort("��������");
						}
					} else {
						showToastMsgShort(response.get("msg").toString());
					}
				}
			} catch (Exception e) {
				showToastMsgShort("���ݽ�������");
			}
		}
	};

	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			showToastMsgShort("���������Ӵ���");
		}
	};	
	
	//TextView
	private void initView(View view){
		mContent = (TextView) view.findViewById(R.id._content);
	}
	
	//WebView ����
	/*private void initView(View view){
		mContent = (WebView) view.findViewById(R.id._content); 	
		WebSettings settings = mContent.getSettings(); 
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
		settings.setDisplayZoomControls(false); //����webview���Ű�ť
		settings.setUseWideViewPort(true); //���ü��ؽ�����ҳ������Ӧ�ֻ���Ļ
	    settings.setLoadWithOverviewMode(true); 
	}*/
	
	@Override
	public void onDestroyView() {
		((ViewGroup)view.getParent()).removeView(view);
		super.onDestroyView();
	}
	@Override
	protected Context getContext() {
		return getActivity();
	}

}
