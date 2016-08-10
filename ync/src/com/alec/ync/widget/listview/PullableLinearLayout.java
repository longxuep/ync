package com.alec.ync.widget.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PullableLinearLayout extends LinearLayout implements Pullable {

	private PullableListView listView;
	
	public PullableLinearLayout(Context context) {
		super(context);
		PullableLinearLayout.this.setOrientation(LinearLayout.VERTICAL);
	}

	public PullableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		PullableLinearLayout.this.setOrientation(LinearLayout.VERTICAL);
	}

	@SuppressLint("NewApi")
	public PullableLinearLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		PullableLinearLayout.this.setOrientation(LinearLayout.VERTICAL);
	}
	
	public void setListView(PullableListView listView){
		this.listView = listView;
	}

	@Override
	public boolean canPullDown() {
		if (listView == null) {
			return false;
		}
		return listView.canPullDown();
	}

	@Override
	public boolean canPullUp() {
		if (listView == null) {
			return false;
		}
		return listView.canPullUp();
	}

//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//				MeasureSpec.AT_MOST);
//		super.onMeasure(widthMeasureSpec, expandSpec);
//	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		super.dispatchTouchEvent(ev);
		return true;
	}
	

}
