package com.zucc.wsq.a31501284.wonderfulwsq.calendar.schedule;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dell on 2018/7/11.
 */
public class ScheduleRecyclerView extends RecyclerView {

    public ScheduleRecyclerView(Context context) {
        this(context, null);
    }

    public ScheduleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isScrollTop() {
        return computeVerticalScrollOffset() == 0;
    }

    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        if (getOnFocusChangeListener() != null) {
            getOnFocusChangeListener().onFocusChange(child, false);
            getOnFocusChangeListener().onFocusChange(focused, true);
        }
    }

}