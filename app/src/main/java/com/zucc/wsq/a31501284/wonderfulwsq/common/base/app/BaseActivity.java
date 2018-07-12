package com.zucc.wsq.a31501284.wonderfulwsq.common.base.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dell on 2018/7/11.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindData();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void bindView();

    /**
     * 请求动态数据
     */
    protected void initData() {

    }

    /**
     * 绑定静态数据
     */
    protected void bindData() {

    }

    protected <VT extends View> VT searchViewById(int id) {
        VT view = (VT) findViewById(id);
        if (view == null)
            throw new NullPointerException("This resource id is invalid.");
        return view;
    }

}
