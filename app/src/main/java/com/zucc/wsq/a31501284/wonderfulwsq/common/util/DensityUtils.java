package com.zucc.wsq.a31501284.wonderfulwsq.common.util;

import android.content.Context;

/**
 * Created by dell on 2018/7/11.
 */

public class DensityUtils {

    public static int dipToSp(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static int spToDip(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().scaledDensity * f) + 0.5f);
    }


}
