package com.alec.ync.xiangcun.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alec.ync.base.BaseActivity;
import com.alec.ync.jishi.ui.JishiSearchActivity;
import com.alec.ync.model.City;
import com.alec.ync.model.DataCity;
import com.alec.ync.model.Village;
import com.alec.ync.model.VillageCat;
import com.alec.ync.util.Constant;
import com.alec.ync.util.ImageLoaders;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.ync.widget.listview.PullToRefreshLayout;
import com.alec.ync.widget.listview.PullableListView;
import com.alec.ync.widget.listview.PullToRefreshLayout.OnRefreshListener;
import com.alec.yzc.R;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 乡村列表 集合
 * 
 * @author long
 * 
 */
public class XiangcunListActivity extends BaseActivity implements OnItemClickListener,OnRefreshListener, OnClickListener {

	private TextView mBack , _catname;
	private ListView xiangcun_listView;
	private HttpJsonObjectRequest request_info;
	private String cat_id, cat_name;
	private PullToRefreshLayout pullToRefreshLayout;
	private PullableListView item_listview;
	private VillageAdapter villageAdapter;
	private ArrayList<Village> villageList;
	private ArrayList<Village> villeList;
	private int page = 1;//分页参数
	private Boolean isRefresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangcunlist);
		cat_id = getIntent().getStringExtra("cat_id")==null?"":getIntent().getStringExtra("cat_id");//接收传过来的id
		cat_name = getIntent().getStringExtra("cat_name")==null?"":getIntent().getStringExtra("cat_name");//接收传过来的id
		initview();
		onRefresh(null);//打开界面时 去请求数据
	}

	private void initview() {
		pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pullToRefreshLayout);
		pullToRefreshLayout.setOnRefreshListener(this);
		mBack = (TextView) findViewById(R.id._back);
		mBack.setOnClickListener(this);
		_catname = (TextView) findViewById(R.id.tv_catname);
		_catname.setText(cat_name+"主题");		
		item_listview = (PullableListView) findViewById(R.id.item_listview);
		item_listview.setOnItemClickListener(this);
		isRefresh = false;
	}

	// 获取首页乡村列表
	private void getDataVillageList() {
		if (app.cityName == null)
			return;
		//showLoadingView(null, null, false);
		//这是参数集合 map 如果多加参数 比如 分页 或者其他 在这里加入就好
		Map<String, String> map = new HashMap<String, String>();
        map.put("catid", cat_id);
		map.put("regionid", "97");
		map.put("page", page+"");
		
		request_info = new HttpJsonObjectRequest(Method.GET,Constant.Url.Village, null, successListenervillage,errorListener, map, this);
		mRequestQueue.add(request_info);
	}

	Response.Listener<JSONObject> successListenervillage = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			hideLoadingView();
			try {
				refreshOrloadmore(PullToRefreshLayout.SUCCEED);//加载成功
				if (response != null) {
					Gson gson = new Gson();
					VillageCat vc = null;
					if (response.get("code").equals("success")) {
						if (response.optJSONArray("data") != null) {
							if(!isRefresh){
								//if(villageList!=null)villageList.clear();
								villeList = gson.fromJson(
										response.getString("data"),
										new TypeToken<ArrayList<Village>>() {
										}.getType());
								if (villeList != null && villeList.size() > 0){
									//villageList.addAll(villeList);
									showVillageView(villeList);
								}
							}else{
								villeList = gson.fromJson(
										response.getString("data"),
										new TypeToken<ArrayList<Village>>() {
										}.getType());
								if (villeList != null && villeList.size() > 0){
									villageList.addAll(villeList);
									villageAdapter.notifyDataSetChanged();
								}
							}
						}
					} else {
						showToastMsgShort(response.get("msg").toString());
					}
				}
			} catch (Exception e) {
				showToastMsgShort("数据解析错误");
			}
			isRefresh=false;
		}
		
	};

	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			hideLoadingView();
			showToastMsgShort("服务器链接错误");
			refreshOrloadmore(PullToRefreshLayout.FAIL);//加载失败
		}
	};	
	
	// 显示乡村列表
	private void showVillageView(ArrayList<Village> list) {
		villageAdapter = new VillageAdapter(list);
		item_listview.setAdapter(villageAdapter);
		villageAdapter.notifyDataSetChanged();
		/* 以下代码是设计listView的高度 */
		if (villageAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int i_length = villageAdapter.getCount();
		for (int i = 0; i < i_length; i++) {
			View listItem = villageAdapter.getView(i, null, item_listview);
			// listItem.measure(10,10);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = item_listview.getLayoutParams();
		params.height = totalHeight + villageAdapter.getCount()* (int) getResources().getDimension(R.dimen._2px);
		// ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		item_listview.setLayoutParams(params);
	}


	private class WodeSetOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id._back:
				finish();
				break;
			}
		}

	}

	// 乡村列表类
	class VillageAdapter extends BaseAdapter {
		ArrayList<Village> list;

		public VillageAdapter(ArrayList<Village> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// 获取乡村列表显示窗口
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_xiangcun_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.textView1 = (TextView) convertView.findViewById(R.id._description);
				viewHolder.tv_english = (TextView) convertView.findViewById(R.id.tv_english);
				viewHolder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
				viewHolder.textView2 = (TextView) convertView.findViewById(R.id.tv_title);
				viewHolder.textView3 = (TextView) convertView.findViewById(R.id._price);
				viewHolder.item_listview_image = (ImageView) convertView.findViewById(R.id.item_listview_image);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.textView1.setText(list.get(position).getVillage_name() != null ? list.get(position).getVillage_name() : "");
			viewHolder.tv_english.setText(list.get(position).getVillage_english() != null ? list.get(position).getVillage_english() : "");
			viewHolder.tv_description.setText(list.get(position).getDescription() != null ? list.get(position).getDescription() : "");
			viewHolder.textView2.setText((list.get(position).getKeywords() != null ? list.get(position).getKeywords() : "").replace(";", " "));
			viewHolder.textView3.setText(list.get(position).getAddress() != null ? list.get(position).getAddress() : "");
			ImageLoaders.loadImage(list.get(position).getFile_url(),viewHolder.item_listview_image, R.drawable.default_image,R.drawable.error_image, null);
			return convertView;
		}

		public class ViewHolder {
			private TextView textView1, textView2, textView3, tv_english,tv_description;
			private ImageView item_listview_image;
		}
	}

	@Override
	public void onClick(View v) {
		if (v != null) {
			switch (v.getId()) {
			case R.id._back:
				finish();
				break;
			}
		}

	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Intent i=new Intent(this,XiangCunDetailsActivity.class);
		i.putExtra("region_id",villageList.get(position).getVillage_id()+"");//把id传过去
		startActivity(i);
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		isRefresh=false;
		page=1;
		getDataVillageList();
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		isRefresh=true;
		page+=1;
		getDataVillageList();// 获取城市列表
		
	}
	//refreshOrloadmore(PullToRefreshLayout.FAIL);//加载失败
	//refreshOrloadmore(PullToRefreshLayout.SUCCEED);//加载成功
	//关闭下上拉状态
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
		return null;
	}

}
