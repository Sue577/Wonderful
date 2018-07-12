package com.zucc.wsq.a31501284.wonderfulwsq.task.schedule;

import android.content.Context;

import com.zucc.wsq.a31501284.wonderfulwsq.common.base.task.BaseAsyncTask;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.Schedule;
import com.zucc.wsq.a31501284.wonderfulwsq.common.data.ScheduleDao;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;


/**
 * Created by dell on 2018/7/11.
 */
public class AddScheduleTask extends BaseAsyncTask<Schedule> {

    private Schedule mSchedule;

    public AddScheduleTask(Context context, OnTaskFinishedListener<Schedule> onTaskFinishedListener, Schedule schedule) {
        super(context, onTaskFinishedListener);
        mSchedule = schedule;
    }

    @Override
    protected Schedule doInBackground(Void... params) {
        if (mSchedule != null) {
            ScheduleDao dao = ScheduleDao.getInstance(mContext);
            int id = dao.addSchedule(mSchedule);
            if (id != 0) {
                mSchedule.setId(id);
                return mSchedule;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
