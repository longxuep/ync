package com.alec.ync.xiangcun.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.alec.ync.base.BaseActivity;
import com.alec.ync.model.Resource;
import com.alec.ync.model.Village;
import com.alec.ync.model.VillageCat;
import com.alec.ync.util.Constant;
import com.alec.ync.util.ImageLoaders;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.ync.widget.listview.PullToRefreshLayout;
import com.alec.ync.widget.listview.PullableListView;
import com.alec.ync.widget.listview.PullToRefreshLayout.OnRefreshListener;
import com.alec.yzc.R;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class XiangcunKuoZhanActivity extends BaseActivity implements OnRefreshListener, OnItemClickListener {

	private PullToRefreshLayout pullToRefreshLayout;
	private PullableListView item_listview;	
	private ResourceAdapter resourceAdapter;
	private ArrayList<Resource> resourceList;
	private HttpJsonObjectRequest request_info;
	private TextView _Back,_title;
	private String mTitle,mtype;
	private Boolean isRefresh;
	private int totalHeightCat = 0;
	private int page = 1;//分页参数
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiangcun_kuozhan_activity);
		mTitle = getIntent().getStringExtra("title")==null?"":getIntent().getStringExtra("title");//接收传过来的id
		mtype = getIntent().getStringExtra("type")==null?"":getIntent().getStringExtra("type");
		initview();
		onRefresh(null);//打开界面时 去请求数据
	}
	
	private void initview() {
		pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pullToRefreshLayout);
		pullToRefreshLayout.setOnRefreshListener(this);
		
		_Back = (TextView) findViewById(R.id._back);		
		_title = (TextView) findViewById(R.id._title);
		_title.setText(mTitle);
		WodeSetOnclickListener mOnclickListener = new WodeSetOnclickListener();
		_Back.setOnClickListener(mOnclickListener);	
		
		item_listview = (PullableListView) findViewById(R.id.item_listview);
		item_listview.setOnItemClickListener(this);
		
		isRefresh = false;
	}	
	
	//获取增值信息列表
	private void getDataResourceList() {
		//showLoadingView(null, null, false);
		//这是参数集合 map 如果多加参数 比如 分页 或者其他 在这里加入就好
		Map<String, String> map = new HashMap<String, String>();
		map.put("villageid", "64");
		map.put("type", mtype);
		map.put("page", page+"");
		
		request_info = new HttpJsonObjectRequest(Method.GET,Constant.Url.Resource, null, successListenerresource,errorListener, map, this);
		mRequestQueue.add(request_info);
	}

	Response.Listener<JSONObject> successListenerresource = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			hideLoadingView();
			try {			
				refreshOrloadmore(PullToRefreshLayout.SUCCEED);//加载成功
				if (response != null) {
					Gson gson = new Gson();
					if (response.get("code").equals("success")) {
						if (response.optJSONArray("data") != null) {
							if(!isRefresh){		
								resourceList = gson.fromJson(
										response.getString("data"),
										new TypeToken<ArrayList<Resource>>() {
										}.getType());
								if (resourceList != null && resourceList.size() > 0){
									showResourceView(resourceList);
								}
							}else{
								resourceList = gson.fromJson(
										response.getString("data"),
										new TypeToken<ArrayList<Resource>>() {
										}.getType());
								if (resourceList != null && resourceList.size() > 0){
									resourceAdapter.notifyDataSetChanged();
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

	// 显示乡村列表
	private void showResourceView(ArrayList<Resource> list) {
		resourceAdapter = new ResourceAdapter(list);
		item_listview.setAdapter(resourceAdapter);
		resourceAdapter.notifyDataSetChanged();
		/* 以下代码是设计listView的高度 */
		if (resourceAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int i_length = resourceAdapter.getCount();
		for (int i = 0; i < i_length; i++) {
			View listItem = resourceAdapter.getView(i, null, item_listview);
			// listItem.measure(10,10);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = item_listview.getLayoutParams();
		params.height = totalHeight + resourceAdapter.getCount()* (int) getResources().getDimension(R.dimen._2px);
		// ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		item_listview.setLayoutParams(params);
	}
	
	// 首页乡村列表类
	class ResourceAdapter extends BaseAdapter {
		ArrayList<Resource> list;

		public ResourceAdapter(ArrayList<Resource> list) {
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
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xiangcun_kuozhan_item, null);
				viewHolder = new ViewHolder();
				viewHolder._title = (TextView) convertView.findViewById(R.id._title);
				//viewHolder._price = (TextView) convertView.findViewById(R.id._price);
				//viewHolder._stock = (TextView) convertView.findViewById(R.id._stock);
				viewHolder._click = (TextView) convertView.findViewById(R.id._click);
				viewHolder._description = (TextView) convertView.findViewById(R.id._description);
				viewHolder.item_listview_image = (ImageView) convertView.findViewById(R.id.item_listview_image);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder._title.setText(list.get(position).getResource_name() != null ? list.get(position).getResource_name() : "");
			//viewHolder._price.setText(list.get(position).getPrice() != null ? list.get(position).getPrice() : "");
			//viewHolder._stock.setText(list.get(position).getStock() != null ? list.get(position).getStock() : "");
			viewHolder._click.setText(list.get(position).getClick() != null ? list.get(position).getClick() : "");
			viewHolder._description.setText(list.get(position).getDescription() != null ? list.get(position).getDescription() : "");
			ImageLoaders.loadImage(list.get(position).getFile_url(),viewHolder.item_listview_image, R.drawable.default_image,R.drawable.error_image, null);
			return convertView;
		}

		public class ViewHolder {
			private TextView _title, _price, _stock, _click,_description;
			private ImageView item_listview_image;
		}
	}	
	
	
	
	
	
	
	
	
	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			hideLoadingView();
			showToastMsgShort("服务器链接错误");
			refreshOrloadmore(PullToRefreshLayout.FAIL);//加载失败
		}
	};	
	
	
	
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
	
	// 首页乡村列表 listView 列表点击事件
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		if (resourceList != null && resourceList.size() > 0) {
			Resource ct = resourceList.get(position);
			showToastMsgShort(ct.getDescription());
			//Intent i = new Intent(getActivity(), XiangCunDetailsActivity.class);
			//i.putExtra("village_id", ct.getVillage_id() + "");// 把乡村id传过去
			//i.putExtra("village_name", ct.getVillage_name() + "");// 把乡村名称传过去
			//i.putExtra("score", ct.getScore() + "");// 把星级传过去
			//i.putExtra("address", ct.getAddress() + "");// 把地址传过去
			//i.putExtra("file_url", ct.getFile_url() + ""); //把图片传过去
			//startActivity(i);
		}
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
	
	
	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		totalHeightCat = 0;
		isRefresh=false;
		page=0;
		getDataResourceList();		
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		isRefresh=true;
		page+=1;
		getDataResourceList();	
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}	

}