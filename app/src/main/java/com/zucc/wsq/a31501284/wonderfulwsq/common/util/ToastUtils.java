package com.zucc.wsq.a31501284.wonderfulwsq.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dell on 2018/7/11.
 */
public class ToastUtils {

    private static Toast mToast;

    public static void showShortToast(Context context, String text) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showShortToast(Context context, int resId) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showLongToast(Context context, String text) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        mToast.show();
    }

    public static void showLongToast(Context context, int resId) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

}
