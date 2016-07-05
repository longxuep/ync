package com.alec.ync;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alec.ync.frament.JishiFragment;
import com.alec.ync.frament.WodeFragment;
import com.alec.ync.frament.XiangcunFragment;
import com.alec.yzc.R;

/**
 * ��ܽ��� Laier������
 **/
@SuppressWarnings("deprecation")
public class FrameActivity extends FragmentActivity {

	private LinearLayout Ll_xiangcun, Ll_jishi, Ll_wode;
//	private ImageView Img_xiangcun, Img_jishi, Img_wode;
	private TextView Tv_xiangcun, Tv_jishi, Tv_wode;
	private Fragment tempFragment;
	private Fragment[] fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//���õײ��˵��������������
		setContentView(R.layout.activity_frame);
		initView();
		setab(R.id.ll_xiangcun);
	}

	// ��ʼ���ؼ�
	private void initView() {
		fragments = new Fragment[3];
		// ������linearlayoutΪ��ť���õĿؼ�
		Ll_xiangcun = (LinearLayout) findViewById(R.id.ll_xiangcun);
		Ll_jishi = (LinearLayout) findViewById(R.id.ll_jishi);
		Ll_wode = (LinearLayout) findViewById(R.id.ll_wode);
		// ����linearlayout�е�imageview
//		Img_xiangcun = (ImageView) findViewById(R.id.img_xiangcun);
//		Img_jishi = (ImageView) findViewById(R.id.img_jishi);
//		Img_wode = (ImageView) findViewById(R.id.img_wode);
		// ����linearlayout�е�textview
		Tv_xiangcun = (TextView) findViewById(R.id.tv_xiangcun);
		Tv_jishi = (TextView) findViewById(R.id.tv_jishi);
		Tv_wode = (TextView) findViewById(R.id.tv_wode);
		// �������ǵ�adapter

		MyBtnOnclick mytouchlistener = new MyBtnOnclick();
		Ll_xiangcun.setOnClickListener(mytouchlistener);
		Ll_jishi.setOnClickListener(mytouchlistener);
		Ll_wode.setOnClickListener(mytouchlistener);
	}

	/**
	 * ��linearlayout��Ϊ��ť�ļ����¼�
	 * */
	private class MyBtnOnclick implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			setab(arg0.getId());
		}
	}
	private void setab(int mBtnid) {
		Fragment f = null;
		switch (mBtnid) {
		case R.id.ll_xiangcun:
			// //�������ǵ�viewpager��ת�Ǹ�����0������������ǵ�list���,�൱��list������±�
			initBottemBtn();
			if (fragments[0] == null) {
				fragments[0] = new XiangcunFragment();
			}
			f = fragments[0];
			//Img_xiangcun.setImageResource(R.drawable.xiangcun_pressed);
			Tv_xiangcun.setTextColor(Color.parseColor("#E6563C"));
			break;
		case R.id.ll_jishi:
			initBottemBtn();
			if (fragments[1] == null) {
				fragments[1] = new JishiFragment();
			}
			f = fragments[1];
			//Img_jishi.setImageResource(R.drawable.jishi_pressed);
			Tv_jishi.setTextColor(Color.parseColor("#E6563C"));
			break;
		case R.id.ll_wode:
			initBottemBtn();
			if (fragments[2] == null) {
				fragments[2] = new WodeFragment();
			}
			f = fragments[2];
			//Img_wode.setImageResource(R.drawable.wode_pressed);
			Tv_wode.setTextColor(Color.parseColor("#E6563C"));
			break;
		default:
			initBottemBtn();
			if (fragments[0] == null) {
				fragments[0] = new XiangcunFragment();
			}
			f = fragments[0];
			//Img_xiangcun.setImageResource(R.drawable.xiangcun_pressed);
			Tv_xiangcun.setTextColor(Color.parseColor("#E6563C"));
			break;
		}
		if (f != null && tempFragment != f) {
			tempFragment = f;
			setForegroundFragment(tempFragment);
		}
	}
	private void setForegroundFragment(Fragment fragment) {
		// ����µ�Fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.frame_layout, fragment);// �滻Fragment
		ft.commitAllowingStateLoss();
	}

	/**
	 * ��ʼ���ؼ�����ɫ
	 * */
	private void initBottemBtn() {
		//Img_xiangcun.setImageResource(R.drawable.yzc_xiangcun);
		//Img_jishi.setImageResource(R.drawable.yzc_jishi);
		//Img_wode.setImageResource(R.drawable.yzc_wode);
		Tv_xiangcun
				.setTextColor(getResources().getColor(R.color.yzc_textcolor));
		Tv_jishi.setTextColor(getResources().getColor(R.color.yzc_textcolor));
		Tv_wode.setTextColor(getResources().getColor(R.color.yzc_textcolor));
	}
	@Override
	public void onResume() {
		super.onResume();
		outTime = 0;

	}
	private long outTime = 0;// �˳����ʱ��
	/**
	 * ���ذ�ť�ļ���������ѯ���û��Ƿ��˳�����
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				Builder builder = new Builder(FrameActivity.this);
				builder.setTitle("��ʾ");
				builder.setMessage("��ȷ��Ҫ�˳���");
				builder.setIcon(R.drawable.ic_launcher);

				DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if (arg1 == DialogInterface.BUTTON_POSITIVE) {
							arg0.cancel();
						} else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
							FrameActivity.this.finish();
						}
					}
				};
				builder.setPositiveButton("ȡ��", dialog);
				builder.setNegativeButton("ȷ��", dialog);
				AlertDialog alertDialog = builder.create();
				alertDialog.show();

			}
		}
		return false;*/
		// �����˵���
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			break;
		case KeyEvent.KEYCODE_BACK:
			long nowTime = System.currentTimeMillis() / 1000;
			if (nowTime - outTime <= 2) {
				finish();
			} else {
				Toast.makeText(this, "�ٴε�����˳�����", Toast.LENGTH_SHORT).show();
				outTime = nowTime;
			}

			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}
