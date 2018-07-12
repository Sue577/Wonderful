package com.zucc.wsq.a31501284.wonderfulwsq.task.schedule;

import android.content.Context;

import com.zucc.wsq.a31501284.wonderfulwsq.common.base.task.BaseAsyncTask;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.Schedule;
import com.zucc.wsq.a31501284.wonderfulwsq.common.data.ScheduleDao;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;


/**
 * Created by dell on 2018/7/11.
 */
public class UpdateScheduleTask extends BaseAsyncTask<Boolean> {

    private Schedule mSchedule;

    public UpdateScheduleTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, Schedule schedule) {
        super(context, onTaskFinishedListener);
        mSchedule = schedule;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (mSchedule != null) {
            ScheduleDao dao = ScheduleDao.getInstance(mContext);
            return dao.updateSchedule(mSchedule);
        } else {
            return false;
        }
    }
}
