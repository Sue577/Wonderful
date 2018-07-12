package com.zucc.wsq.a31501284.wonderfulwsq.task.eventset;

import android.content.Context;


import com.zucc.wsq.a31501284.wonderfulwsq.common.base.task.BaseAsyncTask;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.EventSet;
import com.zucc.wsq.a31501284.wonderfulwsq.common.data.EventSetDao;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;

import java.util.List;

/**
 * Created by dell on 2018/7/11.
 */
public class LoadEventSetTask extends BaseAsyncTask<List<EventSet>> {

    private Context mContext;

    public LoadEventSetTask(Context context, OnTaskFinishedListener<List<EventSet>> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        mContext = context;
    }

    @Override
    protected List<EventSet> doInBackground(Void... params) {
        EventSetDao dao = EventSetDao.getInstance(mContext);
        return dao.getAllEventSet();
    }

}
