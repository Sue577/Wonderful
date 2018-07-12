package com.zucc.wsq.a31501284.wonderfulwsq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.zucc.wsq.a31501284.wonderfulwsq.R;


/**
 * Created by dell on 2018/7/11.
 */
public class InputLocationDialog extends Dialog implements View.OnClickListener {

    private OnLocationBackListener mOnLocationBackListener;
    private EditText etLocationContent;

    public InputLocationDialog(Context context, OnLocationBackListener onLocationBackListener) {
        super(context, R.style.DialogFullScreen);
        mOnLocationBackListener = onLocationBackListener;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_input_location);
        etLocationContent = (EditText) findViewById(R.id.etLocationContent);
        findViewById(R.id.tvCancel).setOnClickListener(this);
        findViewById(R.id.tvConfirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                dismiss();
                break;
            case R.id.tvConfirm:
                if (mOnLocationBackListener != null) {
                    mOnLocationBackListener.onLocationBack(etLocationContent.getText().toString());
                }
                dismiss();
                break;
        }
    }

    public interface OnLocationBackListener {
        void onLocationBack(String text);
    }
}
