package com.alec.ync.frament;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alec.ync.XiangcunDingweiActivity;
import com.alec.ync.XiangcunSearchActivity;
import com.alec.ync.model.ImageEntity;
import com.alec.ync.widget.LoopViewPager;
import com.alec.ync.widget.ViewPagerAutoScrollHelper;
import com.alec.ync.widget.ViewPagerIndicateHelper;
import com.alec.yzc.R;

/**
 * 乡村界面 Laier工作室
 **/

public class XiangcunFragment extends BaseFragment implements OnClickListener {

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

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_xiangcun, null);
			initview(view);
			initViewPager();
		}
		return view;
	}

	private void initview(View v) {
		mDingwei = (TextView) v.findViewById(R.id.xiangcun_dingwei);
		mSearch = (ImageView) v.findViewById(R.id.xiangcun_search);

		mDingwei.setOnClickListener(this);
		mSearch.setOnClickListener(this);

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
			}
		}

	}

}
