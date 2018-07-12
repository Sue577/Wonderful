package com.zucc.wsq.a31501284.wonderfulwsq.widget;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dell on 2018/7/11.
 */
public class StrikeThruTextView extends TextView {

    public StrikeThruTextView(Context context) {
        this(context, null);
    }

    public StrikeThruTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrikeThruTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        TextPaint paint = getPaint();
        paint.setAntiAlias(true);
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

}
