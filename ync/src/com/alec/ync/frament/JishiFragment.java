package com.alec.ync.frament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alec.ync.activity.DingweiActivity;
import com.alec.ync.adpter.Jshi_TwoAdapter;
import com.alec.ync.jishi.ui.FilterActivity;
import com.alec.ync.jishi.ui.JishiSearchActivity;
import com.alec.ync.model.Jishi;
import com.alec.ync.model.Message;
import com.alec.ync.util.Constant;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.ync.widget.listview.PullToRefreshLayout;
import com.alec.ync.widget.listview.PullToRefreshLayout.OnRefreshListener;
import com.alec.ync.widget.listview.PullableGridView;
import com.alec.yzc.R;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JishiFragment extends BaseFragment implements OnClickListener,OnItemClickListener,OnRefreshListener {
	private View view;
	private PullableGridView two_item_gview;
	private PullToRefreshLayout pullToRefreshLayout;
	private Jshi_TwoAdapter twoAdapter;
	private ArrayList<Jishi> list;
	private ImageView _Search,_Filter;
	private ArrayList<Jishi> jishiList;
	private HttpJsonObjectRequest request_info;	
	private int page = 1;//��ҳ����
	private Boolean isRefresh;//true ������false ����
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_jshi_two, null);
			initView(view);
			onRefresh(null);//�򿪽���ʱ ȥ��������
		}
		return view;
	}
	private void initView(View view){
		_Search = (ImageView) view.findViewById(R.id._search);
		_Filter = (ImageView) view.findViewById(R.id._filter);
		_Search.setOnClickListener(this);
		_Filter.setOnClickListener(this);
		pullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.pullToRefreshLayout);
		pullToRefreshLayout.setOnRefreshListener(this);
		
		two_item_gview=(PullableGridView)view.findViewById(R.id.two_item_gview);
		two_item_gview.setOverScrollMode(View.OVER_SCROLL_NEVER); //ȥ��SrollView��GrdiView��ListView��ViewPager�Ȼ�������Ե�Ĺ���Ч��
		two_item_gview.setOnItemClickListener(this);
		jishiList = new ArrayList<Jishi>();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void onClick(View v) {
		if (v != null) {
			switch (v.getId()) {
			case R.id._search:
				Intent intent2 = new Intent(getActivity(),JishiSearchActivity.class);
				startActivity(intent2);
				break;	
			case R.id._filter:
				Intent intent3 = new Intent(getActivity(),FilterActivity.class);
				startActivity(intent3);
				break;					
			}
		}
	}
	
	@Override
	public void onDestroyView() {
		((ViewGroup)view.getParent()).removeView(view);
		super.onDestroyView();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Jishi good = jishiList.get(position);
			showToastMsgShort(good.getGoods_name());
			//Intent i = new Intent(getActivity(), XiangcunListActivity.class);
			//i.putExtra("cat_id", .getCat_id() + "");
			//i.putExtra("cat_name", vc.getCat_name() + "");
			//startActivity(i);		
	}
	/*
	 * ��ȡJSON����
	 */

	/* ��ȡ��ҳ����б� */
	private void getDataJishiList() {
		if (app.cityName == null)
			return;
		showLoadingView(null, null, false);
		//���ǲ������� map �����Ӳ��� ���� ��ҳ �������� ���������ͺ�
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", page+"");
		request_info = new HttpJsonObjectRequest(Method.GET,Constant.Url.GOOGS, null, successListenerad, errorListener,map, getActivity());
		mRequestQueue.add(request_info);
	}
	Response.Listener<JSONObject> successListenerad = new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			try {
				hideLoadingView();
				refreshOrloadmore(PullToRefreshLayout.SUCCEED);//���سɹ�
				if (response != null) {
					Gson gson = new Gson();
					if (response.get("code").equals("success")) {
						if (response.optJSONArray("data") != null) {
							if(isRefresh){
								if(jishiList!=null&&jishiList.size()>0)jishiList.clear();
								list = gson.fromJson(response.getString("data"),new TypeToken<ArrayList<Jishi>>() {}.getType());
								System.out.print(list);
								if (list != null && list.size() > 0){
									jishiList.addAll(list);
									twoAdapter=new Jshi_TwoAdapter(getActivity(),jishiList);
									two_item_gview.setAdapter(twoAdapter);
									twoAdapter.notifyDataSetChanged();
								}
							}else{
								list = gson.fromJson(
										response.getString("data"),
										new TypeToken<ArrayList<Jishi>>() {
										}.getType());
								jishiList.addAll(list);
								twoAdapter.notifyDataSetChanged();
							}

						}
					} else {
						showToastMsgShort(response.get("msg").toString());
					}
				}
			} catch (Exception e) {
				showToastMsgShort("���ݽ�������");
			}
			isRefresh=false;
		}
	};
	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			hideLoadingView();
			refreshOrloadmore(PullToRefreshLayout.FAIL);//����ʧ��
		}
	};
	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		isRefresh=true;
		page=1;
		getDataJishiList(); // ��ȡ�б�
	}
	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		isRefresh=false;
		page+=1;
		getDataJishiList(); // ��ȡ�б�
	}
	//refreshOrloadmore(PullToRefreshLayout.FAIL);//����ʧ��
	//refreshOrloadmore(PullToRefreshLayout.SUCCEED);//���سɹ�
	protected final void refreshOrloadmore(int i) {
		if (i == PullToRefreshLayout.SUCCEED) {
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
		} else {
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
		}

	}
	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return getActivity();
	}

}
