package com.zucc.wsq.a31501284.wonderfulwsq.utils;

import android.content.Context;

/**
 * Created by dell on 2018/7/12.
 */
public class ParseUtil {
    private Context mContext;
    public ParseUtil(Context context) {
        mContext = context;
    }
    public int sp2px(int value) {
        float v = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value * v + 0.5f);
    }
    public int dp2px(int value) {
        float v = mContext.getResources().getDisplayMetrics().density;
        return (int) (value * v + 0.5f);
    }
}
