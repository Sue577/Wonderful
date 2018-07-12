package com.zucc.wsq.a31501284.wonderfulwsq.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dell on 2018/7/11.
 */
public class AbsoluteRecyclerView extends RecyclerView {

    public AbsoluteRecyclerView(Context context) {
        super(context);
        setFocusable(false);
    }

    public AbsoluteRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(false);
    }

    public AbsoluteRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

}
