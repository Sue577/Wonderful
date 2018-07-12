package com.zucc.wsq.a31501284.wonderfulwsq.task.eventset;

import android.content.Context;


import com.zucc.wsq.a31501284.wonderfulwsq.common.base.task.BaseAsyncTask;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.EventSet;
import com.zucc.wsq.a31501284.wonderfulwsq.common.data.EventSetDao;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;

import java.util.Map;

/**
 * Created by dell on 2018/7/11.
 */
public class LoadEventSetMapTask extends BaseAsyncTask<Map<Integer, EventSet>> {

    private Context mContext;

    public LoadEventSetMapTask(Context context, OnTaskFinishedListener<Map<Integer, EventSet>> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        mContext = context;
    }

    @Override
    protected Map<Integer, EventSet> doInBackground(Void... params) {
        EventSetDao dao = EventSetDao.getInstance(mContext);
        return dao.getAllEventSetMap();
    }

}
