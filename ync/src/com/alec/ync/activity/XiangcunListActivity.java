package com.alec.ync.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alec.ync.model.City;
import com.alec.ync.model.DataCity;
import com.alec.ync.model.Introduction;
import com.alec.ync.util.Constant;
import com.alec.ync.util.ImageLoaders;
import com.alec.ync.volley.HttpJsonObjectRequest;
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
public class XiangcunListActivity extends BaseActivity implements
		OnItemClickListener {

	private TextView mBack;
	private ListView xiangcun_listView;
	private ArrayList<Introduction> cityList = new ArrayList<Introduction>();
	private HttpJsonObjectRequest request_info;
	private String cat_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangcunlist);
		cat_id = getIntent().getStringExtra("cat_id")==null?"":getIntent().getStringExtra("cat_id");//接收传过来的id
		initview();
		getDataCityList();//加载数据
	}

	private void initview() {
		mBack = (TextView) findViewById(R.id.xiangcundingwei_back);
		WodeSetOnclickListener mOnclickListener = new WodeSetOnclickListener();
		mBack.setOnClickListener(mOnclickListener);
		xiangcun_listView = (ListView) findViewById(R.id.xiangcun_listView);
		xiangcun_listView.setOnItemClickListener(this);
		
	}

	// 获取定位城市列表
	private void getDataCityList() {
		if(cat_id==null||cat_id.equals("")) return;
		if(DataCity.getCity().getRegion_id()==null||DataCity.getCity().getRegion_id().equals(""))return;
		Map<String, String> map = new HashMap<String, String>();
		map.put("catid", cat_id);
		map.put("regionid", "97");
		request_info = new HttpJsonObjectRequest(Method.GET,
				Constant.Url.Village, null, successListenercity,
				errorListener, map, this);
		mRequestQueue.add(request_info);
	}

	Response.Listener<JSONObject> successListenercity = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			try {
				if (response != null) {
					Gson gson = new Gson();
					if (response.get("code").equals("success")) {
						if (response.optJSONArray("data") != null) {
							cityList = gson.fromJson(
									response.getString("data"),
									new TypeToken<ArrayList<Introduction>>() {
									}.getType());
							if (cityList != null && cityList.size() > 0)
								showView(cityList);
						}
					} else {
						showToastMsgShort(response.get("msg").toString());
					}
				}
			} catch (Exception e) {
				showToastMsgShort("数据解析错误");
			}
		}
	};
	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			showToastMsgShort("服务器链接错误");
		}
	};

	private void showView(ArrayList<Introduction> list) {
		if(list!=null){
			CityAdapter cityAdapter = new CityAdapter(list);
			xiangcun_listView.setAdapter(cityAdapter);
			cityAdapter.notifyDataSetChanged();
		}
	}

	private class WodeSetOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			switch (mID) {
			case R.id.xiangcundingwei_back:
				finish();
				break;
			}
		}

	}

	class CityAdapter extends BaseAdapter {
		ArrayList<Introduction> list;

		public CityAdapter(ArrayList<Introduction> list) {
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.fragment_xiangcun_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.textView1 = (TextView) convertView
						.findViewById(R.id.textView1);
				viewHolder.textView2 = (TextView) convertView
						.findViewById(R.id.textView2);
				viewHolder.textView3 = (TextView) convertView
						.findViewById(R.id.textView3);
				 viewHolder.item_listview_image = (ImageView)convertView.findViewById(R.id.item_listview_image);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.textView1
					.setText(list.get(position).getVillage_name() != null ? list
							.get(position).getVillage_name() : "");
			viewHolder.textView2
			.setText(list.get(position).getKeywords() != null ? list
					.get(position).getKeywords() : "");
			viewHolder.textView3
			.setText(list.get(position).getAddress() != null ? list
					.get(position).getAddress() : "");
			 ImageLoaders.loadImage(list.get(position).getFile_url(),
			 viewHolder.item_listview_image, R.drawable.default_image,
			 R.drawable.error_image, null);
			return convertView;
		}

		public class ViewHolder {
			private TextView textView1,textView2,textView3;
			private ImageView item_listview_image;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i=new Intent(this,XiangCunDetailsActivity.class);
		i.putExtra("region_id",cityList.get(position).getVillage_id()+"");//把id传过去
		startActivity(i);
	}

}