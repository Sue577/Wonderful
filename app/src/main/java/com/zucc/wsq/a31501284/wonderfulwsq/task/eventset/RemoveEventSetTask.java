package com.zucc.wsq.a31501284.wonderfulwsq.task.eventset;

import android.content.Context;

import com.zucc.wsq.a31501284.wonderfulwsq.common.base.task.BaseAsyncTask;
import com.zucc.wsq.a31501284.wonderfulwsq.common.data.EventSetDao;
import com.zucc.wsq.a31501284.wonderfulwsq.common.data.ScheduleDao;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;


/**
 * Created by dell on 2018/7/11.
 */
public class RemoveEventSetTask extends BaseAsyncTask<Boolean> {

    private int mId;

    public RemoveEventSetTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, int id) {
        super(context, onTaskFinishedListener);
        mId = id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ScheduleDao scheduleDao = ScheduleDao.getInstance(mContext);
        scheduleDao.removeScheduleByEventSetId(mId);
        EventSetDao dao = EventSetDao.getInstance(mContext);
        return dao.removeEventSet(mId);
    }
}
