package com.alec.ync.xiangcun.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alec.ync.base.BaseActivity;
import com.alec.ync.model.Village;
import com.alec.ync.model.VillageCat;
import com.alec.ync.model.VillageCunli;
import com.alec.ync.util.AndroidShare;
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
 * 乡村详情 各种详情
 * 
 * @author long
 * 
 */
public class XiangCunDetailsActivity extends BaseActivity implements
		OnClickListener {
	private ImageView _back, _share, _bj;
	private TextView _name, tv_huodong, tv_meishi,tv_techan,tv_mingsu,tv_zhixun,tv_address;
	private RelativeLayout _rl;
	private Fragment tempFragment;
	private Fragment[] fragments;
	private String region_id, village_id, village_name, address, file_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangcundetails);
		village_id = getIntent().getStringExtra("village_id") == null ? ""
				: getIntent().getStringExtra("village_id");// 接收传过来的id
		village_name = getIntent().getStringExtra("village_name") == null ? ""
				: getIntent().getStringExtra("village_name");// 接收传过来的id
		// score = getIntent().getIntExtra("score",
		// score)==null?0:getIntent().getIntExtra("score", 0);//接收传过来的id
		address = getIntent().getStringExtra("address") == null ? ""
				: getIntent().getStringExtra("address");// 接收传过来的id
		file_url = getIntent().getStringExtra("file_url") == null ? ""
				: getIntent().getStringExtra("file_url");// 接收图片传过来

		initView();

	}

	private void initView() {
		fragments = new Fragment[8];
		findViewById(R.id.tv_name).setOnClickListener(this);
		findViewById(R.id.tv_address).setOnClickListener(this);
		findViewById(R.id.view_huodong).setOnClickListener(this);
		findViewById(R.id.view_zhixun).setOnClickListener(this);
		findViewById(R.id.view_meishi).setOnClickListener(this);
		findViewById(R.id.view_techan).setOnClickListener(this);
		findViewById(R.id.view_mingsu).setOnClickListener(this);
		setab(R.id._back);
		_rl = (RelativeLayout) findViewById(R.id.rl);
		_name = (TextView) findViewById(R.id.tv_name);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_address.setText(address);
		
		tv_huodong = (TextView) findViewById(R.id.tv_huodong);
		tv_meishi = (TextView) findViewById(R.id.tv_meishi);
		tv_techan = (TextView) findViewById(R.id.tv_techan);
		tv_mingsu = (TextView) findViewById(R.id.tv_mingsu);
		tv_zhixun = (TextView) findViewById(R.id.tv_zhixun);
		
		_back = (ImageView) findViewById(R.id._back);
		_share = (ImageView) findViewById(R.id._share);
		_bj = (ImageView) findViewById(R.id._bj);
		_name.setText(village_name);
		
		ImageLoaders.loadImage(file_url, _bj, R.drawable.default_image,
				R.drawable.error_image, null);
		_back.setOnClickListener(this);
		_share.setOnClickListener(this);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		if (v != null) {
			switch (v.getId()) {
			case R.id._back:
				finish();
				break;
			case R.id._share:
				AndroidShare as = new AndroidShare(
						XiangCunDetailsActivity.this, "云农村-快快把我的乡村分享出去！",
						"http://img6.cache.netease.com/cnews/news2012/img/logo_news.png");
				as.show();
			case R.id.tv_name:
				setab(v.getId());
				break;
			case R.id.tv_address:
				setab(v.getId());
				break;
			case R.id.view_huodong:
				setab(v.getId());
				tv_huodong.setTextColor(Color.parseColor("#E6563C"));
				tv_meishi.setTextColor(Color.parseColor("#ffffff"));
				tv_techan.setTextColor(Color.parseColor("#ffffff"));
				tv_mingsu.setTextColor(Color.parseColor("#ffffff"));
				tv_zhixun.setTextColor(Color.parseColor("#ffffff"));
				break;
			case R.id.view_meishi:
				setab(v.getId());
				tv_meishi.setTextColor(Color.parseColor("#E6563C"));
				tv_huodong.setTextColor(Color.parseColor("#ffffff"));
				tv_techan.setTextColor(Color.parseColor("#ffffff"));
				tv_mingsu.setTextColor(Color.parseColor("#ffffff"));
				tv_zhixun.setTextColor(Color.parseColor("#ffffff"));
				break;
			case R.id.view_techan:
				setab(v.getId());
				tv_techan.setTextColor(Color.parseColor("#E6563C"));
				tv_huodong.setTextColor(Color.parseColor("#ffffff"));
				tv_meishi.setTextColor(Color.parseColor("#ffffff"));
				tv_mingsu.setTextColor(Color.parseColor("#ffffff"));
				tv_zhixun.setTextColor(Color.parseColor("#ffffff"));
				break;
			case R.id.view_mingsu:
				setab(v.getId());
				tv_mingsu.setTextColor(Color.parseColor("#E6563C"));
				tv_huodong.setTextColor(Color.parseColor("#ffffff"));
				tv_meishi.setTextColor(Color.parseColor("#ffffff"));
				tv_techan.setTextColor(Color.parseColor("#ffffff"));
				tv_zhixun.setTextColor(Color.parseColor("#ffffff"));
				break;
			case R.id.view_zhixun:
				setab(v.getId());
				tv_zhixun.setTextColor(Color.parseColor("#E6563C"));
				tv_huodong.setTextColor(Color.parseColor("#ffffff"));
				tv_meishi.setTextColor(Color.parseColor("#ffffff"));
				tv_techan.setTextColor(Color.parseColor("#ffffff"));
				tv_mingsu.setTextColor(Color.parseColor("#ffffff"));
				break;
			default:
				break;
			}
		}

	}

	private void setTextColor(int blue) {
		// TODO Auto-generated method stub

	}

	private void setab(int mBtnid) {
		Fragment f = null;
		switch (mBtnid) {
		case R.id.tv_name:
			if (fragments[0] == null) {
				fragments[0] = new Details_cunliFragment()
						.getvid(village_id != null ? village_id : "可能前面没有传过来数据");
			}
			f = fragments[0];
			break;
		case R.id.tv_address:
			if (fragments[1] == null) {
				fragments[1] = new Details_daohangFragment()
						.getvid(village_id != null ? village_id : "可能前面没有传过来数据");
			}
			f = fragments[1];
			break;
		case R.id.view_zhixun:
			if (fragments[2] == null) {
				fragments[2] = new Details_hudongFragment()
						.getvid(village_id != null ? village_id : "可能前面没有传过来数据");
			}
			f = fragments[2];
			break;
		case R.id.view_huodong:
			if (fragments[3] == null) {
				fragments[3] = new Details_resourceFragment().getvill(
						village_id != null ? village_id : "可能前面没有传过来数据", "1");
			}
			f = fragments[3];
			break;
		case R.id.view_meishi:
			if (fragments[4] == null) {
				fragments[4] = new Details_resourceFragment().getvill(
						village_id != null ? village_id : "可能前面没有传过来数据", "2");
			}
			f = fragments[4];
			break;
		case R.id.view_techan:
			if (fragments[5] == null) {
				fragments[5] = new Details_resourceFragment().getvill(
						village_id != null ? village_id : "可能前面没有传过来数据", "3");
			}
			f = fragments[5];
			break;
		case R.id.view_mingsu:
			if (fragments[6] == null) {
				fragments[6] = new Details_resourceFragment().getvill(
						village_id != null ? village_id : "可能前面没有传过来数据", "4");
			}
			f = fragments[6];
			break;
		default:
			if (fragments[0] == null) {
				fragments[0] = new Details_cunliFragment()
						.getvid(village_id != null ? village_id : "可能前面没有传过来数据");
			}
			f = fragments[0];
			break;
		}
		if (f != null && tempFragment != f) {
			tempFragment = f;
			setForegroundFragment(tempFragment);
		}
	}

	private void setForegroundFragment(Fragment fragment) {
		// 添加新的Fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.frame_layout, fragment);// 替换Fragment
		ft.commitAllowingStateLoss();
	}

	@Override
	protected Context getContext() {
		return this;
	}

}
