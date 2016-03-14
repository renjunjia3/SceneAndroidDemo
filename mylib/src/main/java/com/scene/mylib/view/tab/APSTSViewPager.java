package com.scene.mylib.view.tab;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by scene on 2015/8/10.
 */
public class APSTSViewPager extends ViewPager {
    private boolean mNoFocus = false; //if true, keep View don't move

    public APSTSViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public APSTSViewPager(Context context) {
        this(context, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (mNoFocus)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (mNoFocus)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    public void setNoFocus(boolean b) {
        mNoFocus = b;
    }
}