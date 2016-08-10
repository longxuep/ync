package com.alec.ync.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class PullableScollView extends ScrollView implements Pullable {

	public PullableScollView(Context context) {
		super(context);
	}

	public PullableScollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableScollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		return true;
	}

	@Override
	public boolean canPullUp() {
		return true;
	}
}
