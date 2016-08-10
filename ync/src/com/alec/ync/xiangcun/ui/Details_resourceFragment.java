package com.alec.ync.xiangcun.ui;

	import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alec.ync.frament.BaseFragment;
import com.alec.ync.model.Resource;
import com.alec.ync.util.Constant;
import com.alec.ync.util.ImageLoaders;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.ync.widget.listview.PullToRefreshLayout;
import com.alec.ync.widget.listview.PullToRefreshLayout.OnRefreshListener;
import com.alec.ync.widget.listview.PullableListView;
import com.alec.yzc.R;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

	/**
	 * 村庄资源
	 *Laier工作室
	 **/

	@SuppressLint("InflateParams")
	public class Details_resourceFragment extends BaseFragment implements OnRefreshListener,OnItemClickListener{

		private View view;
		private PullableListView item_listview;
		private PullToRefreshLayout pullToRefreshLayout;
		private HttpJsonObjectRequest request_info;
		private ResourceAdapter resourceAdapter;
		//private ArrayList<Resource> resourceList;
		private ArrayList<Resource> resList;
		private int page = 1;//分页参数
		private Boolean isRefresh;
		private String villid;
		private String villtype;
		public Details_resourceFragment getvill(String vid, String vtype){
			Details_resourceFragment dc = new Details_resourceFragment();
			dc.villid = vid;
			dc.villtype = vtype;
			return dc;
			
		}	
		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			if (view == null) {
				view = inflater.inflate(R.layout.fragment_details_resource, null);
				initView(view);
				onRefresh(null);//打开界面时 去请求数据
			}
			return view;
		}
		private void initView(View view){
			pullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.pullToRefreshLayout);
			pullToRefreshLayout.setOnRefreshListener(this);
			item_listview = (PullableListView) view.findViewById(R.id.item_listview);
			item_listview.setOnItemClickListener(this);
			resList = new ArrayList<Resource>();
			isRefresh = false;		
		}

		//获取增值信息列表
		private void getDataResourceList() {
			//showLoadingView(null, null, false);
			//这是参数集合 map 如果多加参数 比如 分页 或者其他 在这里加入就好
			Map<String, String> map = new HashMap<String, String>();
			map.put("villageid", villid);
			map.put("type", villtype);
			map.put("page", page+""); //前期先显示 ， 后期加 分页
			
			request_info = new HttpJsonObjectRequest(Method.GET,Constant.Url.Resource, null, successListenerresource,errorListener, map, getActivity());
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
									resList = gson.fromJson(
											response.getString("data"),
											new TypeToken<ArrayList<Resource>>() {
											}.getType());
									if (resList != null && resList.size() > 0){
										//resourceList.addAll(resList);
										showResourceView(resList);
									}
								}else{
									resList = gson.fromJson(
											response.getString("data"),
											new TypeToken<ArrayList<Resource>>() {
											}.getType());
									if (resList != null && resList.size() > 0){
										//resourceList.addAll(resList);
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
			item_listview.setAdapter(resourceAdapter);//这个地方里面错误
			resourceAdapter.notifyDataSetChanged();
			/* 以下代码是设计listView的高度  这里不需要用*/
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
			params.height = totalHeight + resourceAdapter.getCount()* (int) getResources().getDimension(R.dimen._50px);
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
					viewHolder.rl_v1=(RelativeLayout) convertView.findViewById(R.id.rl_v1);
					convertView.setTag(viewHolder);
				} else {
					viewHolder = (ViewHolder) convertView.getTag();
				}
				final int pnum=position;
				if(list!=null&&list.size()>0){
					viewHolder._title.setText(list.get(position).getResource_name() != null ? list.get(position).getResource_name() : "没有标题");
					//viewHolder._price.setText(list.get(position).getPrice() != null ? list.get(position).getPrice() : "0.00");
					//viewHolder._stock.setText(list.get(position).getStock() != null ? list.get(position).getStock() : "0/0");		
					viewHolder._click.setText(list.get(position).getClick() != null ? list.get(position).getClick() : "0");
					viewHolder._description.setText(list.get(position).getDescription() != null ? list.get(position).getDescription() : "当前活动无描述！");
					ImageLoaders.loadImage(list.get(position).getFile_url(),viewHolder.item_listview_image, R.drawable.default_image,R.drawable.error_image, null);
					viewHolder.rl_v1.setOnClickListener(new ckR(pnum));
				}
				return convertView;
			}

			public class ViewHolder {
				private TextView _title, _click,_description;
				private ImageView item_listview_image;
				private RelativeLayout rl_v1;
			}
		}	
		//点击效果
		class ckR implements OnClickListener{
			int position;
			private ckR(int position){
				this.position=position;
			}
			@Override
			public void onClick(View v) {
				showToastMsgShort("点击效果"+resList.get(position).getResource_name());
			}};
		
		Response.ErrorListener errorListener = new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				hideLoadingView();
				showToastMsgShort("服务器链接错误");
			}
		};	
			
		@Override
		public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
			isRefresh=false;
			page=0;
			getDataResourceList();
		}

		@Override
		public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
			isRefresh=true;
			page+=1;
			getDataResourceList();// 获取城市列表
			
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
		
		// 首页乡村列表 listView 列表点击事件
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			showToastMsgShort("OK");	//还有这里，这个没有起作用？
			//Resource ct = resList.get(position);
				//showToastMsgShort(ct.getResource_name());
				//Intent i = new Intent(getActivity(), XiangcunListActivity.class);
				//i.putExtra("cat_id", .getCat_id() + "");
				//i.putExtra("cat_name", vc.getCat_name() + "");
				//startActivity(i);		
		}
			
		
		
		@Override
		public void onDestroyView() {
			((ViewGroup)view.getParent()).removeView(view);
			super.onDestroyView();
		}
		@Override
		protected Context getContext() {
			// TODO Auto-generated method stub
			return getActivity();
		}
	}