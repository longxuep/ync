package com.alec.ync.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.alec.ync.base.BaseActivity;
import com.alec.ync.model.City;
import com.alec.ync.util.Constant;
import com.alec.ync.util.ImageLoaders;
import com.alec.ync.util.LocationUtils;
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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * �ֶ�ѡ�����
 * @author long
 *
 */
public class DingweiActivity extends BaseActivity implements OnRefreshListener, OnItemClickListener {

	private PullToRefreshLayout pullToRefreshLayout;
	private PullableListView item_listview;	
	private CityAdapter cityAdapter;
	private ArrayList<City> cityList;
	private HttpJsonObjectRequest request_info;
	private TextView _Back,_title;
	private String mTitle,mtype;
	private Boolean isRefresh;
	private int totalHeightCat = 0;
	private int page = 1;//��ҳ����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingwei_listview);
		initview();
		onRefresh(null);//�򿪽���ʱ ȥ��������
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
	
	
	//��ȡ��ֵ��Ϣ�б�
	private void getDataCityList() {
		//showLoadingView(null, null, false);
		//���ǲ������� map �����Ӳ��� ���� ��ҳ �������� ���������ͺ�
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "GetCL");
		map.put("province", "����");
		
		request_info = new HttpJsonObjectRequest(Method.GET,Constant.Url.Region, null, successListenerresource,errorListener, map, this);
		mRequestQueue.add(request_info);
	}

	Response.Listener<JSONObject> successListenerresource = new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			hideLoadingView();
			try {			
				refreshOrloadmore(PullToRefreshLayout.SUCCEED);//���سɹ�
				if (response != null) {
					Gson gson = new Gson();
					if (response.get("code").equals("success")) {
						if (response.optJSONArray("data") != null) {
							if(!isRefresh){		
								cityList = gson.fromJson(
										response.getString("data"),
										new TypeToken<ArrayList<City>>() {
										}.getType());
								if (cityList != null && cityList.size() > 0){
									showCityView(cityList);
								}
							}else{
								cityList = gson.fromJson(
										response.getString("data"),
										new TypeToken<ArrayList<City>>() {
										}.getType());
								if (cityList != null && cityList.size() > 0){
									cityAdapter.notifyDataSetChanged();
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

	// ��ʾ����б�
	private void showCityView(ArrayList<City> list) {
		cityAdapter = new CityAdapter(list);
		item_listview.setAdapter(cityAdapter);
		cityAdapter.notifyDataSetChanged();
		/* ���´��������listView�ĸ߶� */
		if (cityAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int i_length = cityAdapter.getCount();
		for (int i = 0; i < i_length; i++) {
			View listItem = cityAdapter.getView(i, null, item_listview);
			// listItem.measure(10,10);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = item_listview.getLayoutParams();
		params.height = totalHeight + cityAdapter.getCount()* (int) getResources().getDimension(R.dimen._2px);
		// ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		item_listview.setLayoutParams(params);
	}
	
	// ��ҳ����б���
	class CityAdapter extends BaseAdapter {
		ArrayList<City> list;

		public CityAdapter(ArrayList<City> list) {
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
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dingwei_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder._title = (TextView) convertView.findViewById(R.id._title);
				viewHolder._distance = (TextView) convertView.findViewById(R.id._distance);
				viewHolder.item_listview_image = (ImageView) convertView.findViewById(R.id.item_listview_image);
				convertView.setTag(viewHolder);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder._title.setText(list.get(position).getRegion_name() != null ? list.get(position).getRegion_name() : "");
			viewHolder._distance.setText(LocationUtils.DistanceOfTwoPoints(app.latitude,app.longitude,list.get(position).getLatitude(),list.get(position).getLongitude())+"");
			ImageLoaders.loadImage(list.get(position).getFile_url(),viewHolder.item_listview_image, R.drawable.default_image,R.drawable.error_image, null);
			return convertView;
		}

		public class ViewHolder {
			private TextView _title, _distance;
			private ImageView item_listview_image;
		}
	}	
	
	Response.ErrorListener errorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			hideLoadingView();
			showToastMsgShort("���������Ӵ���");
			refreshOrloadmore(PullToRefreshLayout.FAIL);//����ʧ��
		}
	};	
	
	// ��׼��ʽ������ĺ���
	private String format(double num) {
	 NumberFormat formatter = new DecimalFormat("0.##");
	 String str = formatter.format(num);
	 return str;
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
	
	// ��ҳ����б� listView �б����¼�
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		if (cityList != null && cityList.size() > 0) {
			City ct = cityList.get(position);
			showToastMsgShort(ct.getRegion_name());
			//Intent i = new Intent(getActivity(), XiangCunDetailsActivity.class);
			//i.putExtra("village_id", ct.getVillage_id() + "");// �����id����ȥ
			//i.putExtra("village_name", ct.getVillage_name() + "");// ��������ƴ���ȥ
			//i.putExtra("score", ct.getScore() + "");// ���Ǽ�����ȥ
			//i.putExtra("address", ct.getAddress() + "");// �ѵ�ַ����ȥ
			//i.putExtra("file_url", ct.getFile_url() + ""); //��ͼƬ����ȥ
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
		getDataCityList();		
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		isRefresh=true;
		page+=1;
		getDataCityList();	
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}	

}