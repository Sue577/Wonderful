package com.zucc.wsq.a31501284.wonderfulwsq.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zucc.wsq.a31501284.wonderfulwsq.R;
import com.zucc.wsq.a31501284.wonderfulwsq.common.base.app.BaseActivity;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.EventSet;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;
import com.zucc.wsq.a31501284.wonderfulwsq.common.util.ToastUtils;
import com.zucc.wsq.a31501284.wonderfulwsq.dialog.SelectColorDialog;
import com.zucc.wsq.a31501284.wonderfulwsq.task.eventset.AddEventSetTask;
import com.zucc.wsq.a31501284.wonderfulwsq.utils.JeekUtils;


/**
 * Created by dell on 2018/7/11.
 */
public class AddEventSetActivity extends BaseActivity implements View.OnClickListener, OnTaskFinishedListener<EventSet>, SelectColorDialog.OnSelectColorListener {

    public static int ADD_EVENT_SET_CANCEL = 1;
    public static int ADD_EVENT_SET_FINISH = 2;
    public static String EVENT_SET_OBJ = "event.set.obj";

    private EditText etEventSetName;
    private View vEventSetColor;
    private SelectColorDialog mSelectColorDialog;

    private int mColor = 0;

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_add_event_set);
        TextView tvTitle = searchViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.menu_add_event_set));
        etEventSetName = searchViewById(R.id.etEventSetName);
        vEventSetColor = searchViewById(R.id.vEventSetColor);
        searchViewById(R.id.tvCancel).setOnClickListener(this);
        searchViewById(R.id.tvFinish).setOnClickListener(this);
        searchViewById(R.id.rlEventSetColor).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                setResult(ADD_EVENT_SET_CANCEL);
                finish();
                break;
            case R.id.tvFinish:
                addEventSet();
                break;
            case R.id.rlEventSetColor:
                showSelectColorDialog();
                break;
        }
    }

    private void showSelectColorDialog() {
        if (mSelectColorDialog == null)
            mSelectColorDialog = new SelectColorDialog(this, this);
        mSelectColorDialog.show();
    }

    private void addEventSet() {
        String name = etEventSetName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShortToast(this, R.string.event_set_name_is_not_null);
        } else {
            EventSet eventSet = new EventSet();
            eventSet.setName(name);
            eventSet.setColor(mColor);
            new AddEventSetTask(this, this, eventSet).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    @Override
    public void onTaskFinished(EventSet data) {
        setResult(ADD_EVENT_SET_FINISH, new Intent().putExtra(EVENT_SET_OBJ, data));
        finish();
    }

    @Override
    public void onSelectColor(int color) {
        mColor = color;
        vEventSetColor.setBackgroundResource(JeekUtils.getEventSetCircle(color));
    }
}
