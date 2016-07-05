package com.alec.ync.frament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alec.ync.XiangcunDingweiActivity;
import com.alec.ync.XiangcunSearchActivity;
import com.alec.ync.activity.XiangCunDetailsActivity;
import com.alec.ync.model.City;
import com.alec.ync.model.ImageEntity;
import com.alec.ync.model.VillageCat;
import com.alec.ync.util.Constant;
import com.alec.ync.util.ImageLoaders;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.ync.widget.LoopViewPager;
import com.alec.ync.widget.ViewPagerAutoScrollHelper;
import com.alec.ync.widget.ViewPagerIndicateHelper;
import com.alec.yzc.R;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 乡村界面 Laier工作室
 **/

public class XiangcunFragment extends BaseFragment implements OnClickListener,OnItemClickListener{

	private TextView mDingwei;
	private ImageView mSearch;
	private View view;

	/* 轮播图片 */
	private LoopViewPager viewpager;
	private LinearLayout indicator;
	private PagerAdapter pagerAdapter;
	private List<ImageEntity> pagerAdapterData;
	private ViewPagerAutoScrollHelper bannerScrollHelper;
	private ViewPagerIndicateHelper viewPagerIndicateHelper;
	private ListView item_listview;
	private GridView item_gview;
	
	private HttpJsonObjectRequest request_info;
	private ArrayList<VillageCat> vList=new ArrayList<VillageCat>();
	private ArrayList<City> cityList=new ArrayList<City>();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_xiangcun, null);
			initview(view);
			initViewPager();
			getDataVillageCat();//获取分类
			getDataCityList();//获取城市列表
		}
		return view;
	}

	private void initview(View v) {
		mDingwei = (TextView) v.findViewById(R.id.xiangcun_dingwei);
		mSearch = (ImageView) v.findViewById(R.id.xiangcun_search);
		item_listview=(ListView)v.findViewById(R.id.item_listview);
		item_listview.setOnItemClickListener(this);
		mDingwei.setOnClickListener(this);
		mSearch.setOnClickListener(this);
		item_gview=(GridView)v.findViewById(R.id.item_gview);

		if (app != null && app.cityName != null) {
			mDingwei.setText(app.cityName + " >");
		}
		viewpager = (LoopViewPager) view
				.findViewById(R.id.viewpager);
		indicator = (LinearLayout) view
				.findViewById(R.id.ll_indicator);
	}

	@Override
	public void onDestroyView() {
		((ViewGroup) view.getParent()).removeView(view);
		super.onDestroyView();
	}

	// 广告轮播
	private void initViewPager() {
		pagerAdapterData = new ArrayList<ImageEntity>();
		for (int i = 0; i < 2; i++) {
			ImageEntity imageEntity = new ImageEntity();
			if (i == 0) {
				imageEntity.setUrl(R.drawable.ic_launcher);

			} else if (i == 1) {
				imageEntity.setUrl(R.drawable.ic_launcher);
			}
			pagerAdapterData.add(imageEntity);
		}

		pagerAdapter = new PagerAdapter() {

			@Override
			public int getItemPosition(Object object) {
				return POSITION_NONE;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {

				if (object != null) {
					container.removeView((View) object);
				}
			}

			@Override
			public Object instantiateItem(ViewGroup container,
					final int position) {
				if (getCount() > 0) {
					ImageView imageView = new ImageView(getActivity());
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView.setBackgroundResource(pagerAdapterData.get(
							position).getUrl());
					container.addView(imageView);
					return imageView;
				}
				return null;
			}

			@Override
			public int getCount() {
				return pagerAdapterData.size();
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}
		};
		viewpager.setAdapter(pagerAdapter);

		bannerScrollHelper = new ViewPagerAutoScrollHelper(8, viewpager);
		viewPagerIndicateHelper = new ViewPagerIndicateHelper(viewpager,
				indicator, R.drawable.ic_launcher, R.drawable.ic_launcher)
				.setViewPagerIndicate();
		viewPagerIndicateHelper.setLisntener(bannerScrollHelper.getListener());
		bannerScrollHelper.startScroll();

	}
	//获取定位城市列表
	private void getDataCityList() {
		if(app.cityName==null) return;
		Map<String,String> map=new HashMap<String, String>();
		map.put("province", "海南");
		request_info = new HttpJsonObjectRequest(Method.GET, Constant.Url.Citylist, null, successListenercity, errorListener,
				map, getActivity());
		mRequestQueue.add(request_info);
	}   
	
	Response.Listener<JSONObject> successListenercity = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			try {
				if(response!=null){
					Gson gson=new Gson();
					VillageCat vc=null;
					if(response.get("code").equals("success")){
						if (response.optJSONArray("data") != null) {
							cityList = gson.fromJson(response.getString("data"),new TypeToken<ArrayList<City>>() {
							}.getType());
							if(cityList!=null&&cityList.size()>0) showCityView(cityList); 
						}
					}else{
						showToastMsgShort(response.get("msg").toString());
					}
				}
			} catch (Exception e) {
				showToastMsgShort("数据解析错误");
			}
		}
	};
	private void showCityView(ArrayList<City> list){
		CityAdapter cityAdapter=new CityAdapter(list);
		item_listview.setAdapter(cityAdapter);
		cityAdapter.notifyDataSetChanged();
	}
	//获取乡村分类
	private void getDataVillageCat() {
		request_info = new HttpJsonObjectRequest(Method.GET, Constant.Url.VillageCat, null, successListener, errorListener,
				new HashMap<String, String>(), getActivity());
		mRequestQueue.add(request_info);
	}   
	
	Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			try {
				if(response!=null){
					Gson gson=new Gson();
					VillageCat vc=null;
					if(response.get("code").equals("success")){
						if (response.optJSONArray("data") != null) {
							vList = gson.fromJson(response.getString("data"),new TypeToken<ArrayList<VillageCat>>() {
							}.getType());
							if(vList!=null&&vList.size()>0) showView(vList); 
						} else {
							showToastMsgShort("暂无数据");
						}
					}else{
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
	private void showView(ArrayList<VillageCat> list){
		VillAdapter villAdapter=new VillAdapter(list);
		item_gview.setAdapter(villAdapter);
		villAdapter.notifyDataSetChanged();
	}
	class VillAdapter extends BaseAdapter{
		ArrayList<VillageCat> list;
		public VillAdapter(ArrayList<VillageCat> list) {
			this.list=list;
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
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_xiangcun_gridview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.item_cat = (TextView) convertView.findViewById(R.id.item_cat);
				viewHolder.item_cat_image = (ImageView) convertView.findViewById(R.id.item_cat_image);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.item_cat.setText(list.get(position).getCat_name()!=null?list.get(position).getCat_name():"");
			ImageLoaders.loadImage(list.get(position).getCat_pic_url(), viewHolder.item_cat_image, R.drawable.default_image,
					R.drawable.error_image, null);
			return convertView;
		}
		public class ViewHolder {
			private TextView item_cat;
			private ImageView item_cat_image;
		}
	}
	class CityAdapter extends BaseAdapter{
		ArrayList<City> list;
		public CityAdapter(ArrayList<City> list) {
			this.list=list;
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
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_xiangcun_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
				//viewHolder.item_cat_image = (ImageView) convertView.findViewById(R.id.item_cat_image);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.textView1.setText(list.get(position).getRegion_name()!=null?list.get(position).getRegion_name():"");
			//ImageLoaders.loadImage(vList.get(position).getCat_pic_url(), viewHolder.item_cat_image, R.drawable.default_image,
			//		R.drawable.error_image, null);
			return convertView;
		}
		public class ViewHolder {
			private TextView textView1;
			private ImageView item_cat_image;
		}
	}
	@Override
	public void onClick(View v) {
		if (v != null) {
			switch (v.getId()) {
			case R.id.xiangcun_dingwei:
				Intent intent = new Intent(getActivity(),
						XiangcunDingweiActivity.class);
				startActivity(intent);
				break;
			case R.id.xiangcun_search:
				Intent intent2 = new Intent(getActivity(),
						XiangcunSearchActivity.class);
				startActivity(intent2);
				break;
			/*case R.id.xiangcun_item_ry:
				Intent intent3 = new Intent(getActivity(),
						XiangCunDetailsActivity.class);
				startActivity(intent3);
				break;*/
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

}
