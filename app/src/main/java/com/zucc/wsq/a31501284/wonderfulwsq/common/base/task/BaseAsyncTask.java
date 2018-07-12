package com.zucc.wsq.a31501284.wonderfulwsq.common.base.task;

import android.content.Context;
import android.os.AsyncTask;

import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;


/**
 * Created by dell on 2018/7/11.
 */
public abstract class BaseAsyncTask<T> extends AsyncTask<Void, Void, T> {

    protected Context mContext;
    protected OnTaskFinishedListener<T> mOnTaskFinishedListener;

    public BaseAsyncTask(Context context, OnTaskFinishedListener<T> onTaskFinishedListener) {
        mContext = context;
        mOnTaskFinishedListener = onTaskFinishedListener;
    }

    @Override
    protected abstract T doInBackground(Void... params);

    @Override
    protected void onPostExecute(T data) {
        super.onPostExecute(data);
        if (mOnTaskFinishedListener != null) {
            mOnTaskFinishedListener.onTaskFinished(data);
        }
    }
}
