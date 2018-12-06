package com.kingsoft.touchdemo;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class GeekScrollView extends NestedScrollView {
    public final static String TAG = "GeekScrollView";

    public GeekScrollView(Context context) {
        super(context);
    }

    public GeekScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GeekScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, " dispatchTouchEvent event = " + ev + " ---------------------");
        boolean dispatched = super.dispatchTouchEvent(ev);
        Log.d(TAG, " dispatchTouchEvent dispatched=" + dispatched + " ---------------------");
        return dispatched;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, " onInterceptTouchEvent event = " + ev);
        boolean intercepted = super.onInterceptTouchEvent(ev);
        Log.d(TAG, " onInterceptTouchEvent intercepted=" + intercepted);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, " onTouchEvent event = " + ev);
        boolean handled = super.onTouchEvent(ev);
        Log.d(TAG, " onTouchEvent handled=" + handled);
        return handled;
    }

}
