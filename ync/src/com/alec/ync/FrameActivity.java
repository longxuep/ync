package com.alec.ync;

import java.util.ArrayList;
import java.util.List;

import com.alec.yzc.R;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 框架界面
 *Laier工作室
 **/
@SuppressWarnings("deprecation")
public class FrameActivity extends ActivityGroup {

	private LinearLayout Ll_xiangcun,Ll_jishi,Ll_wode;
	private ImageView Img_xiangcun,Img_jishi,Img_wode;
	private TextView Tv_xiangcun,Tv_jishi,Tv_wode;
	private List<View> list = new ArrayList<View>();// 相当于数据源
	private View view = null;
	private View view1 = null;
	private View view2 = null;
	private android.support.v4.view.ViewPager mViewPager;
	private PagerAdapter pagerAdapter = null;// 数据源和viewpager之间的桥梁

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_frame);
		initView();
	}

	// 初始化控件
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.frame_pager);
		// 查找以linearlayout为按钮作用的控件
		Ll_xiangcun = (LinearLayout) findViewById(R.id.ll_xiangcun);
		Ll_jishi = (LinearLayout) findViewById(R.id.ll_jishi);
		Ll_wode = (LinearLayout) findViewById(R.id.ll_wode);
		// 查找linearlayout中的imageview
		Img_xiangcun = (ImageView) findViewById(R.id.img_xiangcun);
		Img_jishi = (ImageView) findViewById(R.id.img_jishi);
		Img_wode = (ImageView) findViewById(R.id.img_wode);
		// 查找linearlayout中的textview
		Tv_xiangcun = (TextView) findViewById(R.id.tv_xiangcun);
		Tv_jishi = (TextView) findViewById(R.id.tv_jishi);
		Tv_wode = (TextView) findViewById(R.id.tv_wode);
		createView();
		// 写一个内部类pageradapter
		pagerAdapter = new PagerAdapter() {
			// 判断再次添加的view和之前的view 是否是同一个view
			// arg0新添加的view，arg1之前的
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			// 返回数据源长度
			@Override
			public int getCount() {
				return list.size();
			}

			// 销毁被滑动走的view
			/**
			 * ViewGroup 代表了我们的viewpager 相当于activitygroup当中的view容器， 添加之前先移除。
			 * position 第几条数据 Object 被移出的view
			 * */
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// 移除view
				container.removeView(list.get(position));
			}

			/**
			 * instantiateItem viewpager要现实的view ViewGroup viewpager position
			 * 第几条数据
			 * */
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// 获取view添加到容器当中，并返回
				View v = list.get(position);
				container.addView(v);
				return v;
			}
		};
		// 设置我们的adapter
		mViewPager.setAdapter(pagerAdapter);

		MyBtnOnclick mytouchlistener = new MyBtnOnclick();
		Ll_xiangcun.setOnClickListener(mytouchlistener);
		Ll_jishi.setOnClickListener(mytouchlistener);
		Ll_wode.setOnClickListener(mytouchlistener);

		// 设置viewpager界面切换监听,监听viewpager切换第几个界面以及滑动的
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// arg0 获取 viewpager里面的界面切换到第几个的
			@Override
			public void onPageSelected(int arg0) {
				// 先清除按钮样式
				initBottemBtn();
				// 按照对应的view的tag来判断到底切换到哪个界面。
				// 更改对应的button状态
				int flag = (Integer) list.get((arg0)).getTag();
				if (flag == 0) {
					Img_xiangcun.setImageResource(R.drawable.xiangcun_pressed);
					Tv_xiangcun.setTextColor(Color.parseColor("#00b80c"));
				} else if (flag ==1) {
					Img_jishi.setImageResource(R.drawable.jishi_pressed);
					Tv_jishi.setTextColor(Color.parseColor("#00b80c"));
				} else if (flag == 2) {
					Img_wode.setImageResource(R.drawable.wode_pressed);
					Tv_wode.setTextColor(Color.parseColor("#00b80c"));
				}
			}

			/**
			 * 监听页面滑动的 arg0 表示当前滑动的view arg1 表示滑动的百分比 arg2 表示滑动的距离
			 * */
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			/**
			 * 监听滑动状态 arg0 表示我们的滑动状态 0:默认状态 1:滑动状态 2:滑动停止
			 * */
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	// 把viewpager里面要显示的view实例化出来，并且把相关的view添加到一个list当中
	private void createView() {
		view = this
				.getLocalActivityManager()
				.startActivity("near",new Intent(FrameActivity.this, XiangcunActivity.class))
				.getDecorView();
		// 用来更改下面button的样式的标志
		view.setTag(0);
		list.add(view);
		view1 = FrameActivity.this
				.getLocalActivityManager()
				.startActivity("post", new Intent(FrameActivity.this, JishiActivity.class))
				.getDecorView();
		view1.setTag(1);
		list.add(view1);
		view2 = FrameActivity.this
				.getLocalActivityManager()
				.startActivity("mall",new Intent(FrameActivity.this, WodeActivity.class))
				.getDecorView();
		view2.setTag(2);
		list.add(view2);
	}

	/**
	 * 用linearlayout作为按钮的监听事件
	 * */
	private class MyBtnOnclick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			int mBtnid = arg0.getId();
			switch (mBtnid) {
			case R.id.ll_xiangcun:
				// //设置我们的viewpager跳转那个界面0这个参数和我们的list相关,相当于list里面的下标
				mViewPager.setCurrentItem(0);
				initBottemBtn();
				Img_xiangcun.setImageResource(R.drawable.xiangcun_pressed);
				Tv_xiangcun.setTextColor(Color.parseColor("#00b80c"));
				break;
			case R.id.ll_jishi:
				mViewPager.setCurrentItem(1);
				initBottemBtn();
				Img_jishi.setImageResource(R.drawable.jishi_pressed);
				Tv_jishi.setTextColor(Color.parseColor("#00b80c"));
				break;
			case R.id.ll_wode:
				mViewPager.setCurrentItem(2);
				initBottemBtn();
				Img_wode.setImageResource(R.drawable.wode_pressed);
				Tv_wode.setTextColor(Color.parseColor("#00b80c"));
				break;
			}

		}

	}

	/**
	 * 初始化控件的颜色
	 * */
	private void initBottemBtn() {
		Img_xiangcun.setImageResource(R.drawable.yzc_xiangcun);
		Img_jishi.setImageResource(R.drawable.yzc_jishi);
		Img_wode.setImageResource(R.drawable.yzc_wode);
		Tv_xiangcun.setTextColor(getResources().getColor(R.color.yzc_textcolor));
		Tv_jishi.setTextColor(getResources().getColor(R.color.yzc_textcolor));
		Tv_wode.setTextColor(getResources().getColor(R.color.yzc_textcolor));
	}

	/**
	 * 返回按钮的监听，用来询问用户是否退出程序
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				Builder builder = new Builder(FrameActivity.this);
				builder.setTitle("提示");
				builder.setMessage("你确定要退出吗？");
				builder.setIcon(R.drawable.ic_launcher);

				DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						if (arg1 == DialogInterface.BUTTON_POSITIVE) {
							arg0.cancel();
						} else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
							FrameActivity.this.finish();
						}
					}
				};
				builder.setPositiveButton("取消", dialog);
				builder.setNegativeButton("确定", dialog);
				AlertDialog alertDialog = builder.create();
				alertDialog.show();

			}
		}
		return false;
	}
}
