package com.example.administrator.doctorClient.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class DragLinearLayout extends LinearLayout {
	
	private final static float RATIO = 0.7f;
	
	private View mChildView;
	
	private int mLastY;
	private int mCurY;

	private Scroller mScroller;
	private ViewConfiguration mViewConfiguration;
	private int mTouchSlop;
 
	public DragLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		mViewConfiguration = ViewConfiguration.get(context);
		mTouchSlop = mViewConfiguration.getScaledMinimumFlingVelocity();//判断的触摸的最小速度
		Log.d("mTouchSlop=",mTouchSlop+"");
	}
	
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mChildView = getChildAt(0);
	}
 
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
 
		switch (ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				Log.d("tag onIntercept","down");
 
				mCurY = (int)ev.getY();
				mLastY = mCurY;
 
			case MotionEvent.ACTION_MOVE:
				Log.d("tag onIntercept","move");
				mCurY = (int)ev.getY();
				int slop = Math.abs((mCurY - mLastY));
				int mark = mCurY - mLastY;
				Log.d("tag mCurY  mLastY",mCurY +"  "+ mLastY);
				Log.d("tag slop=",mark+"");
				mLastY = mCurY;
				Log.d("tag可以反弹？",enableScrollViewIsInTopOrBottom(mChildView,mark)+"");
				if (enableScrollViewIsInTopOrBottom(mChildView,mark) && slop > 10){
					return true;
				}
 
				break;
			case MotionEvent.ACTION_UP:
				Log.d("tag onIntercept","up");
 
				break;
		}
 
		return super.onInterceptTouchEvent(ev);
	}
 
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
 
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d("tag onTouchEvent","down");
 
				mCurY = (int)ev.getY();
				mLastY = mCurY;
 
			case MotionEvent.ACTION_MOVE:
				Log.d("tag onTouchEvent","move");
 
				mCurY = (int)ev.getY();
				int mYOffset = (int) ((mCurY - mLastY) * RATIO);
				scrollBy(0,-mYOffset);//y负参数值下拉
				mLastY = mCurY;
				Log.d("getScrollY()",""+getScrollY());
 
				return true;
 
			case MotionEvent.ACTION_UP:
				Log.d("tag onTouchEvent","up");
 
				mCurY = 0;
				mLastY = 0;
				mYOffset = 0;
 
				if (!mScroller.isFinished()){
					mScroller.forceFinished(true);
				}
				mScroller.startScroll(0,getScrollY(),0,-getScrollY());
				invalidate();
 
 
			default:
				break;
		}
 
		return true;
 
	}
 
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()){
			scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
			postInvalidate();
		}
	}
 
	/**
	 * 判断会滚动的View是否位于顶部或者底部
	 * @param childview
	 * @param offset 判断是上滑动还是下滑动
	 * @return
	 */
	public  boolean enableScrollViewIsInTopOrBottom(View childview,int offset) {
 
		if (childview instanceof AbsListView) {
			return isListViewOnTopOrBottom(childview, offset);
		}
 
		return false;
	}
 
	private boolean isListViewOnTopOrBottom(View childview,int offset){
 
		AbsListView listView = (AbsListView)childview;
 
		if (listView.getLastVisiblePosition() == (listView.getCount() - 1) && offset < 0) {
 
			View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
			return (listView.getHeight() >= bottomChildView.getBottom());
 
		}else if (listView.getFirstVisiblePosition() == 0 && offset > 0){
 
			return listView.getChildAt(listView.getFirstVisiblePosition()).getTop() == 0;
 
		}
 
		return false;
	}
 
}
