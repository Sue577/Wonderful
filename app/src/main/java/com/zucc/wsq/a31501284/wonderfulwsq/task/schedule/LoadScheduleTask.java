package com.zucc.wsq.a31501284.wonderfulwsq.task.schedule;

import android.content.Context;


import com.zucc.wsq.a31501284.wonderfulwsq.common.base.task.BaseAsyncTask;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.Schedule;
import com.zucc.wsq.a31501284.wonderfulwsq.common.data.ScheduleDao;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;

import java.util.List;

/**
 * Created by dell on 2018/7/11.
 */
public class LoadScheduleTask extends BaseAsyncTask<List<Schedule>> {

    private int mYear;
    private int mMonth;
    private int mDay;

    public LoadScheduleTask(Context context, OnTaskFinishedListener<List<Schedule>> onTaskFinishedListener, int year, int month, int day) {
        super(context, onTaskFinishedListener);
        mYear = year;
        mMonth = month;
        mDay = day;
    }

    @Override
    protected List<Schedule> doInBackground(Void... params) {
        ScheduleDao dao = ScheduleDao.getInstance(mContext);
        return dao.getScheduleByDate(mYear, mMonth,mDay);
    }
}
