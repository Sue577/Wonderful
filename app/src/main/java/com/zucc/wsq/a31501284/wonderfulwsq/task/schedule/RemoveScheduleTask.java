package com.zucc.wsq.a31501284.wonderfulwsq.task.schedule;

import android.content.Context;

import com.zucc.wsq.a31501284.wonderfulwsq.common.base.task.BaseAsyncTask;
import com.zucc.wsq.a31501284.wonderfulwsq.common.data.ScheduleDao;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;


/**
 * Created by dell on 2018/7/11.
 */
public class RemoveScheduleTask extends BaseAsyncTask<Boolean> {

    private long mId;

    public RemoveScheduleTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, long id) {
        super(context, onTaskFinishedListener);
        mId = id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ScheduleDao dao = ScheduleDao.getInstance(mContext);
        return dao.removeSchedule(mId);
    }
}
