package com.zucc.wsq.a31501284.wonderfulwsq.task.eventset;

import android.content.Context;

import com.zucc.wsq.a31501284.wonderfulwsq.common.base.task.BaseAsyncTask;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.EventSet;
import com.zucc.wsq.a31501284.wonderfulwsq.common.data.EventSetDao;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;


/**
 * Created by dell on 2018/7/11.
 */
public class AddEventSetTask extends BaseAsyncTask<EventSet> {

    private EventSet mEventSet;

    public AddEventSetTask(Context context, OnTaskFinishedListener<EventSet> onTaskFinishedListener, EventSet eventSet) {
        super(context, onTaskFinishedListener);
        mEventSet = eventSet;
    }

    @Override
    protected EventSet doInBackground(Void... params) {
        if (mEventSet != null) {
            EventSetDao dao = EventSetDao.getInstance(mContext);
            int id = dao.addEventSet(mEventSet);
            if (id != 0) {
                mEventSet.setId(id);
                return mEventSet;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
