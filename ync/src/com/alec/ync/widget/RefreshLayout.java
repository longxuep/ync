package com.alec.ync.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.alec.yzc.R;

public class RefreshLayout extends SwipeRefreshLayout implements OnScrollListener{
	private int mTouchSlop; //������������ʱ����������
    private ListView mListView;
    private OnLoadListener mOnLoadListener; //�������ؼ���
    private View mListViewFooter; //�ײ�����ʱ�Ĳ���
    private int mYDown; //����ʱ��y����
    private int mLastY; //̧��ʱ��y����
    private boolean isLoading; //�Ƿ����ڼ���
	public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //��ʾ�����¼�����С����
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.swiperefreshlayout_footer, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //��ʼ��listview����
        if (mListView == null) {
            int childs = getChildCount();
            if (childs > 0) {
                View childView = getChildAt(0);
                if (childView instanceof ListView) {
                    mListView = (ListView) childView;
                    mListView.setOnScrollListener(this);
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            mYDown = (int) ev.getRawY();
            break;
        case MotionEvent.ACTION_UP:
            if (canLoad()) 
                loadData();
            break;
        case MotionEvent.ACTION_MOVE:
            mLastY = (int) ev.getRawY();
            break;
        default:
            break;
        }
        return super.dispatchTouchEvent(ev);
    }

    //�ж��Ƿ���Լ��ظ���
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }
    //�ж��Ƿ񵽴��˵ײ�
    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }
    //�ж��Ƿ�����������
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    //���ز���
    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
            mOnLoadListener.onLoad();
        }
    }
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mListView.addFooterView(mListViewFooter);
        }
        else {
            mListView.removeFooterView(mListViewFooter);
            mYDown = 0;
            mLastY = 0;
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    @Override
    public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        //�������ײ�Ҳ���Լ��ظ���
        if (canLoad()) {
            loadData();
        }
    }

    //���ظ���ļ�����
    public static interface OnLoadListener {
        public void onLoad();
    }
}
