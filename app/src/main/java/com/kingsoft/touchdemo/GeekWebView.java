package com.kingsoft.touchdemo;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.webkit.WebView;
import android.widget.OverScroller;

public class GeekWebView extends WebView {

    public static final String TAG = "GeekWebView";

    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;
    private OverScroller mScroller;

    private ScaleGestureDetector.OnScaleGestureListener mScaleListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.d(TAG, String.format(" onScale:  zoomBy=%f", detector.getScaleFactor()));
                zoomBy(detector.getScaleFactor());
            } else {
                Log.d(TAG, String.format(" onScale:  zoomIn/Out"));
                if (detector.getCurrentSpan() > detector.getPreviousSpan()) {
                    zoomIn();
                } else {
                    zoomOut();
                }
            }
            return true;
        }
    };

    private GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, String.format(" onScroll:  distanceX=%f, distanceY=%f", distanceX, distanceY));
            smoothScrollBy((int) distanceX, 0);
            return true;
        }
    };

    public GeekWebView(Context context) {
        super(context);
        init(context);
    }

    public GeekWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GeekWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScaleDetector = new ScaleGestureDetector(context, mScaleListener);
        mGestureDetector = new GestureDetector(context, mGestureListener);
        mScroller = new OverScroller(context);
    }

    public void smoothScrollBy(int dx, int dy) {
        if (!mScroller.isOverScrolled()) {
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
            invalidate();
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        Log.d(TAG, " onOverScrolled clampedX: " + clampedX + " clampedY: " + clampedY);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, " dispatchTouchEvent event = " + ev);
        boolean dispatched = super.dispatchTouchEvent(ev);
        Log.d(TAG, " dispatchTouchEvent dispatched= " + dispatched);
        return dispatched;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, " onInterceptTouchEvent event = " + ev);
//        boolean intercepted = super.onInterceptTouchEvent(ev);
        Log.d(TAG, " onInterceptTouchEvent intercepted= " + true);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, " onTouchEvent event = " + event + " contentWidth: " + getContentHeight());
        int index = event.getActionIndex();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
        }

        mScaleDetector.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);

//        boolean handled = super.onTouchEvent(event);
        Log.d(TAG, String.format(" onTouchEvent: handled= %b index=%d ", true, index));
        return true;
    }


    @Override
    public boolean startNestedScroll(int axes) {
        return super.startNestedScroll(axes);
    }


}
