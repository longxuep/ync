package com.alec.ync.frament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.alec.ync.activity.DingweiActivity;
import com.alec.ync.jishi.ui.JishiSearchActivity;
import com.alec.ync.model.Ad;
import com.alec.ync.model.ImageEntity;
import com.alec.ync.model.Village;
import com.alec.ync.model.VillageCat;
import com.alec.ync.util.Constant;
import com.alec.ync.util.ImageLoaders;
import com.alec.ync.util.LocationUtils;
import com.alec.ync.volley.HttpJsonObjectRequest;
import com.alec.ync.widget.LoopViewPager;
import com.alec.ync.widget.ViewPagerAutoScrollHelper;
import com.alec.ync.widget.ViewPagerIndicateHelper;
import com.alec.ync.widget.listview.PullToRefreshLayout;
import com.alec.ync.widget.listview.PullToRefreshLayout.OnRefreshListener;
import com.alec.ync.widget.listview.PullableListView;
import com.alec.ync.xiangcun.ui.XiangCunDetailsActivity;
import com.alec.ync.xiangcun.ui.XiangcunKuoZhanActivity;
import com.alec.ync.xiangcun.ui.XiangcunListActivity;
import com.alec.yzc.R;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ������ Laier������  OnTouchListener
 **/

public class XiangcunFragment extends BaseFragment implements OnClickListener,
		OnItemClickListener,OnRefreshListener{
	
	private TextView mDingwei;
	private ImageView mSearch;
	private View view;

	/* �ֲ�ͼƬ */
	private LoopViewPager viewpager;
	private LinearLayout indicator;
	private PagerAdapter pagerAdapter;
	private List<ImageEntity> pagerAdapterData;
	private List<Ad> pagerAdapterData_Ad;
	private ViewPagerAutoScrollHelper bannerScrollHelper;
	private ViewPagerIndicateHelper viewPagerIndicateHelper;
	private PullableListView item_listview;
	//private GridView item_gview;
	private Boolean isRefresh;
	/* ��չ��Ŀ */
	private RelativeLayout _daizhong,_daiyang,_nongchou,_hudong,_huodong,_meishi,_techan,_minsu;

	private HttpJsonObjectRequest request_info;
	private ArrayList<VillageCat> vList = new ArrayList<VillageCat>();
	private ArrayList<Village> villageList;
	private ArrayList<Village> villeList;
	private ArrayList<Ad> vAd = new ArrayList<Ad>();
	private View addheaderview;

	private PullToRefreshLayout pullToRefreshLayout;
	private static final int REFRESH_COMPLETE = 0X110;
	private int totalHeightCat = 0;
	private int page = 1;//��ҳ����
	private VillageAdapter villageAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.xiangcun_activity, null);
			initview(view);
			onRefresh(null);//�򿪽���ʱ ȥ��������
		}
		return view;
	}

	private void getData() {
		getDataAdList(); // ��ȡ����б�
		//getDataVillageCat();// ��ȡ����
		getDataVillageList();// ��ȡ�����б�
	}

	private void initview(View v) {
		
		pullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.pullToRefreshLayout);
		pullToRefreshLayout.setOnRefreshListener(this);
		
		// ������ ��λ������
		mDingwei = (TextView) v.findViewById(R.id._dingwei);
		mSearch = (ImageView) v.findViewById(R.id._search);
		if(app!=null && app.cityName!=null){
			mDingwei.setText(app.cityName+">");
		} else {
			mDingwei.setText("��λ>");
		}
		mDingwei.setOnClickListener(this);
		mSearch.setOnClickListener(this);		
		// ����������
		item_listview = (PullableListView) v.findViewById(R.id.item_listview);
		item_listview.setOnItemClickListener(this);
		
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		addheaderview = inflater.inflate(R.layout.fragment_xiangcun_listview_item_addheaderview, null);
		item_listview.addHeaderView(addheaderview, null, false);
		//item_gview = (GridView) item_listview.findViewById(R.id.item_gview);
		//item_gview.setOnItemClickListener(new XcGview());
		
		isRefresh = false;
		villageList = new ArrayList<Village>();
		viewpager = (LoopViewPager) view.findViewById(R.id.viewpager);
		indicator = (LinearLayout) view.findViewById(R.id.ll_indicator);
		
		_daizhong = (RelativeLayout) view.findViewById(R.id.r_daizhong);
		_daizhong.setOnClickListener(this);
		_daiyang = (RelativeLayout) view.findViewById(R.id.r_daiyang);
		_daiyang.setOnClickListener(this);
		_nongchou = (RelativeLayout) view.findViewById(R.id.r_nongchou);
		_nongchou.setOnClickListener(this);
		_hudong = (RelativeLayout) view.findViewById(R.id.r_hudong);
		_hudong.setOnClickListener(this);
		_huodong = (RelativeLayout) view.findViewById(R.id.r_huodong);
		_huodong.setOnClickListener(this);
		_meishi = (RelativeLayout) view.findViewById(R.id.r_meishi);
		_meishi.setOnClickListener(this);
		_techan = (RelativeLayout) view.findViewById(R.id.r_techan);
		_techan.setOnClickListener(this);
		_minsu = (RelativeLayout) view.findViewById(R.id.r_minsu);
		_minsu.setOnClickListener(this);
	}

	@Override
	public void onDestroyView() {
		((ViewGroup) view.getParent()).removeView(view);
		super.onDestroyView();
	}
	
	/*
	 * ��ȡJSON����
	 */

	/* ��ȡ��ҳ����б� */
	private void getDataAdList() {
		if (app.cityName == null)
			return;
		request_info = new HttpJsonObjectRequest(Method.GET,Constant.Url.AdList, null, successListenerad, errorListener,null, getActivity());
		mRequestQueue.add(request_info);
	}

	Response.Listener<JSONObject> successListenerad = new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			try {
				if (response != null) {
					Gson gson = new Gson();
					if (response.get("code").equals("success")) {
						if (response.optJSONArray("data") != null) {
							pagerAdapterData = gson.fromJson(
									response.getString("data"),
									new TypeToken<ArrayList<ImageEntity>>() {
									}.getType());
							if (pagerAdapterData != null
									&& pagerAdapterData.size() > 0)
								initViewPager();

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

	// ����ֲ� ����δ�Ӷ�ȡ���ӿ�����
	private void initViewPager() {
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
					ImageLoaders.loadImage(pagerAdapterData.get(position)
							.getAd_code(), imageView, R.drawable.default_image,
							R.drawable.error_image, null);
					container.addView(imageView);
					imageView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v){
							if(pagerAdapterData.get(position).getAd_name()!=null) {
								showToastMsgShort(pagerAdapterData.get(position).getAd_name());
							}  
						}
					});
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
		bannerScrollHelper = new ViewPagerAutoScrollHelper(4, viewpager);
		viewPagerIndicateHelper = new ViewPagerIndicateHelper(viewpager,indicator, R.drawable.line_0, R.drawable.line_1).setViewPagerIndicate();
		viewPagerIndicateHelper.setLisntener(bannerScrollHelper.getListener());
		bannerScrollHelper.startScroll();
	}

	// ��ȡ������
	/*
	private void getDataVillageCat() {
		request_info = new HttpJsonObjectRequest(Method.GET,Constant.Url.VillageCat, null, successListener, errorListener,new HashMap<String, String>(), getActivity());
		mRequestQueue.add(request_info);
	}

	Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			try {
				if (response != null) {
					Gson gson = new Gson();
					VillageCat vc = null;
					if (response.get("code").equals("success")) {
						if (response.optJSONArray("data") != null) {
							vList = gson.fromJson(response.getString("data"),
									new TypeToken<ArrayList<VillageCat>>() {
									}.getType());
							if (vList != null && vList.size() > 0){
								
							}
								showView(vList);
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
    */

	// ���������� �б�
	/*
	private void showView(ArrayList<VillageCat> list) {
		VillAdapter villAdapter = new VillAdapter(list);
		item_gview.setAdapter(villAdapter);
		villAdapter.notifyDataSetChanged();
		 //���´��������listView�ĸ߶� 
		if (villAdapter == null) {
			return;
		}
		int i_length = (villAdapter.getCount() % 2 == 0) ? (villAdapter.getCount() / 2) : (villAdapter.getCount() / 2 + 1);
		for (int i = 0; i < i_length; i++) {
			View listItem = villAdapter.getView(i, null, item_gview);
			listItem.measure(0, 0);
			totalHeightCat += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = item_gview.getLayoutParams();
		params.height = totalHeightCat + villAdapter.getCount() / 2 * (int) getResources().getDimension(R.dimen._2px);
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		item_gview.setLayoutParams(params);
	}
	*/
	
	
	//���ؾ�ѡ������
	/*private void JSBT(){
		View convertView = LayoutInflater.from(item_listview.getContext()).inflate(R.layout.fragment_xiangcun_listview_item_header, null);
			
	}
	*/
	// ��ȡ��ҳ����б�
	private void getDataVillageList() {
		if (app.cityName == null)
			return;
		showLoadingView(null, null, false);
		//���ǲ������� map �����Ӳ��� ���� ��ҳ �������� ���������ͺ�
		Map<String, String> map = new HashMap<String, String>();
		map.put("regionid", "97");
		map.put("page", page+"");
		
		request_info = new HttpJsonObjectRequest(Method.GET,Constant.Url.Village, null, successListenervillage,errorListener, map, getActivity());
		mRequestQueue.add(request_info);
	}

	Response.Listener<JSONObject> successListenervillage = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			hideLoadingView();
			try {
				refreshOrloadmore(PullToRefreshLayout.SUCCEED);//���سɹ�
				if (response != null) {
					Gson gson = new Gson();
					VillageCat vc = null;
					if (response.get("code").equals("success")) {
						if (response.optJSONArray("data") != null) {
							if(!isRefresh){
								if(villageList!=null)villageList.clear();
								villeList = gson.fromJson(
										response.getString("data"),
										new TypeToken<ArrayList<Village>>() {
										}.getType());
								if (villeList != null && villeList.size() > 0){
									villageList.addAll(villeList);
									showVillageView(villageList);
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
				showToastMsgShort("���ݽ�������");
			}
			isRefresh=false;
		}
		
	};

	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			hideLoadingView();
			showToastMsgShort("���������Ӵ���");
			refreshOrloadmore(PullToRefreshLayout.FAIL);//����ʧ��
		}
	};	
	
	// ��ʾ����б�
	private void showVillageView(ArrayList<Village> list) {
		villageAdapter = new VillageAdapter(list);
		item_listview.setAdapter(villageAdapter);
		villageAdapter.notifyDataSetChanged();
		/* ���´��������listView�ĸ߶� */
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
		params.height = totalHeight + villageAdapter.getCount()* (int) getResources().getDimension(R.dimen._2px)+ totalHeightCat;
		// ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		item_listview.setLayoutParams(params);
	}

	
	/*
	 * ������
	 */

	/* ��ҳ���������� */
	/*
	class VillAdapter extends BaseAdapter {
		ArrayList<VillageCat> list;

		public VillAdapter(ArrayList<VillageCat> list) {
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

		// ��ȡ��������ʾ����
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
			viewHolder.item_cat.setText(list.get(position).getCat_name() != null ? list.get(position).getCat_name() : "");
			ImageLoaders.loadImage(list.get(position).getCat_pic_url(),viewHolder.item_cat_image, R.drawable.default_image,R.drawable.error_image, null);
			return convertView;
		}

		public class ViewHolder {
			private TextView item_cat;
			private ImageView item_cat_image;
		}
	}
    */
	// ��ҳ����б���
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

		// ��ȡ����б���ʾ����
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
				viewHolder.tv_distance = (TextView) convertView.findViewById(R.id._distance);
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
			viewHolder.tv_distance.setText(LocationUtils.DistanceOfTwoPoints(app.latitude,app.longitude,list.get(position).getLatitude(),list.get(position).getLongitude())+"");
			ImageLoaders.loadImage(list.get(position).getFile_url(),viewHolder.item_listview_image, R.drawable.default_image,R.drawable.error_image, null);
			return convertView;
		}

		public class ViewHolder {
			private TextView textView1, textView2, textView3, tv_english,tv_description,tv_distance;
			private ImageView item_listview_image;
		}
	}
	
	
	/*
	 * ���е���¼�(non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */

	@Override
	public void onClick(View v) {
		if (v != null) {
			switch (v.getId()) {
			case R.id._dingwei:
				Intent intent = new Intent(getActivity(),DingweiActivity.class);
				startActivity(intent);
				break;
			case R.id._search:
				Intent intent2 = new Intent(getActivity(),JishiSearchActivity.class);
				startActivity(intent2);
				break;
			case R.id.r_daizhong:
				Intent intent3 = new Intent(getActivity(),XiangcunKuoZhanActivity.class);
				intent3.putExtra("title", "���Ŵ���");
				intent3.putExtra("type", "5");
				startActivity(intent3);
				//showToastMsgShort("�������");
				break;
			case R.id.r_daiyang:
				Intent intent4 = new Intent(getActivity(),XiangcunKuoZhanActivity.class);
				intent4.putExtra("title", "���Ŵ���");
				intent4.putExtra("type", "6");
				startActivity(intent4);
				//showToastMsgShort("�������");
				break;
			case R.id.r_nongchou:	
				Intent intent5 = new Intent(getActivity(),XiangcunKuoZhanActivity.class);
				intent5.putExtra("title", "����ũ��");
				intent5.putExtra("type", "7");
				startActivity(intent5);
				//showToastMsgShort("���ũ��");
				break;	
			case R.id.r_hudong:
				Intent intent6 = new Intent(getActivity(),XiangcunKuoZhanActivity.class);
				intent6.putExtra("title", "���Ż���");
				startActivity(intent6);
				//showToastMsgShort("�������");
				break;
			case R.id.r_huodong:
				Intent intent7 = new Intent(getActivity(),XiangcunKuoZhanActivity.class);
				intent7.putExtra("title", "��ѡ�");
				intent7.putExtra("type", "1");
				startActivity(intent7);
				//showToastMsgShort("����");
				break;	
			case R.id.r_meishi:
				Intent intent8 = new Intent(getActivity(),XiangcunKuoZhanActivity.class);
				intent8.putExtra("title", "��ѡ��ʳ");
				intent8.putExtra("type", "2");
				startActivity(intent8);
				//showToastMsgShort("�����ʳ");
				break;
			case R.id.r_techan:
				Intent intent9 = new Intent(getActivity(),XiangcunKuoZhanActivity.class);
				intent9.putExtra("title", "��ѡ�ز�");
				intent9.putExtra("type", "3");
				startActivity(intent9);
				//showToastMsgShort("����ز�");
				break;	
			case R.id.r_minsu:
				Intent intent10 = new Intent(getActivity(),XiangcunKuoZhanActivity.class);
				intent10.putExtra("title", "��ѡ����");
				intent10.putExtra("type", "4");
				startActivity(intent10);
				//showToastMsgShort("�������");
				break;	
			/*
			 * case R.id.xiangcun_item_ry: Intent intent3 = new
			 * Intent(getActivity(), XiangCunDetailsActivity.class);
			 * startActivity(intent3); break;
			 */
			}
		}

	}

	// �������������¼� Gview�� OnItemClickListener����¼�
	class XcGview implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (vList != null && vList.size() > 0) {
				VillageCat vc = vList.get(position);
				Intent i = new Intent(getActivity(), XiangcunListActivity.class);
				i.putExtra("cat_id", vc.getCat_id() + "");
				i.putExtra("cat_name", vc.getCat_name() + "");
				startActivity(i);
			}
		}

	}

	// ��ҳ����б� listView �б����¼�
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int positions = position - 1;
		if (villageList != null && villageList.size() > 0) {
			Village ct = villageList.get(positions);
			Intent i = new Intent(getActivity(), XiangCunDetailsActivity.class);
			i.putExtra("village_id", ct.getVillage_id() + "");// �����id����ȥ
			i.putExtra("village_name", ct.getVillage_name() + "");// ��������ƴ���ȥ
			//i.putExtra("score", ct.getScore() + "");// ���Ǽ�����ȥ
			i.putExtra("address", ct.getAddress() + "");// �ѵ�ַ����ȥ
			i.putExtra("file_url", ct.getFile_url() + ""); //��ͼƬ����ȥ
			startActivity(i);
		}
	}
	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		totalHeightCat = 0;
		isRefresh=false;
		page=1;
		getData();
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		isRefresh=true;
		page+=1;
		getDataVillageList();// ��ȡ�����б�
		
	}
	//refreshOrloadmore(PullToRefreshLayout.FAIL);//����ʧ��
	//refreshOrloadmore(PullToRefreshLayout.SUCCEED);//���سɹ�
	//�ر�������״̬
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
		return getActivity();
	}
}
