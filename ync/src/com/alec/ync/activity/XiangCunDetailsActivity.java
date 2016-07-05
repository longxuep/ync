package com.alec.ync.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.alec.ync.frament.Details_cunliFragment;
import com.alec.ync.frament.Details_daohangFragment;
import com.alec.ync.frament.Details_hudongFragment;
import com.alec.ync.frament.Details_huodongFragment;
import com.alec.ync.frament.Details_meijingFragment;
import com.alec.ync.frament.Details_meishiFragment;
import com.alec.ync.frament.Details_minsuFragment;
import com.alec.ync.frament.Details_techanFragment;
import com.alec.ync.util.AndroidShare;
import com.alec.yzc.R;

public class XiangCunDetailsActivity extends FragmentActivity implements OnClickListener{
	private ImageView _back, _share;
	private Fragment tempFragment;
	private Fragment[] fragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangcundetails);
		_back=(ImageView)findViewById(R.id.xiangcun_back);
		_share=(ImageView)findViewById(R.id.xiangcun_share);
		_back.setOnClickListener(this);
		initView();
	}
	private void initView(){
		fragments = new Fragment[8];
		findViewById(R.id.view_cunli).setOnClickListener(this);
		findViewById(R.id.view_daohang).setOnClickListener(this);
		findViewById(R.id.view_huodong).setOnClickListener(this);
		findViewById(R.id.view_hudong).setOnClickListener(this);
		findViewById(R.id.view_meijing).setOnClickListener(this);
		findViewById(R.id.view_meishi).setOnClickListener(this);
		findViewById(R.id.view_techan).setOnClickListener(this);
		findViewById(R.id.view_mingsu).setOnClickListener(this);
		setab(R.id.xiangcun_back);
	}

	@Override
	public void onClick(View v) {
		if(v!=null){
			switch (v.getId()) {
			case R.id.xiangcun_back:
				finish();
				break;
			case R.id.xiangcun_share:
				AndroidShare as = new AndroidShare(XiangCunDetailsActivity.this,"云中村-快快把我的乡村分享出去！","http://img6.cache.netease.com/cnews/news2012/img/logo_news.png");
				as.show();
			case R.id.view_cunli:
				setab(v.getId());
				break;
			case R.id.view_daohang:
				setab(v.getId());
				break;
			case R.id.view_huodong:
				setab(v.getId());
				break;
			case R.id.view_hudong:
				setab(v.getId());
				break;
			case R.id.view_meijing:
				setab(v.getId());
				break;
			case R.id.view_meishi:
				setab(v.getId());
				break;
			case R.id.view_techan:
				setab(v.getId());
				break;
			case R.id.view_mingsu:
				setab(v.getId());
				break;

			default:
				break;
			}
		}
		
	}
	private void setab(int mBtnid) {
		Fragment f = null;
		switch (mBtnid) {
		case R.id.view_cunli:
			if (fragments[0] == null) {
				fragments[0] = new Details_cunliFragment();
			}
			f = fragments[0];
			break;
		case R.id.view_daohang:
			if (fragments[1] == null) {
				fragments[1] = new Details_daohangFragment();
			}
			f = fragments[1];
			break;
		case R.id.view_huodong:
			if (fragments[2] == null) {
				fragments[2] = new Details_huodongFragment();
			}
			f = fragments[2];
			break;
		case R.id.view_hudong:
			if (fragments[3] == null) {
				fragments[3] = new Details_hudongFragment();
			}
			f = fragments[3];
			break;	
		case R.id.view_meijing:
			if (fragments[4] == null) {
				fragments[4] = new Details_meijingFragment();
			}
			f = fragments[4];
			break;	
		case R.id.view_meishi:
			if (fragments[5] == null) {
				fragments[5] = new Details_meishiFragment();
			}
			f = fragments[5];
			break;
		case R.id.view_techan:
			if (fragments[6] == null) {
				fragments[6] = new Details_techanFragment();
			}
			f = fragments[6];
			break;
		case R.id.view_mingsu:
			if (fragments[7] == null) {
				fragments[7] = new Details_minsuFragment();
			}
			f = fragments[7];
			break;
		default:
			if (fragments[0] == null) {
				fragments[0] = new Details_cunliFragment();
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
	
}
