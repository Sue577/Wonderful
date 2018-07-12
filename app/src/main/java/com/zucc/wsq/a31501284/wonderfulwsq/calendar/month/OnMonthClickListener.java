package com.zucc.wsq.a31501284.wonderfulwsq.calendar.month;

/**
 * Created by dell on 2018/7/11.
 */
public interface OnMonthClickListener {
    void onClickThisMonth(int year, int month, int day);
    void onClickLastMonth(int year, int month, int day);
    void onClickNextMonth(int year, int month, int day);
}
