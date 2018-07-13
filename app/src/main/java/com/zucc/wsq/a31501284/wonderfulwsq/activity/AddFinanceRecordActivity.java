package com.zucc.wsq.a31501284.wonderfulwsq.activity;

import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zucc.wsq.a31501284.wonderfulwsq.R;
import com.zucc.wsq.a31501284.wonderfulwsq.common.base.app.BaseActivity;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.EventSet;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.Schedule;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;
import com.zucc.wsq.a31501284.wonderfulwsq.dialog.InputLocationDialog;
import com.zucc.wsq.a31501284.wonderfulwsq.dialog.SelectColorDialog;
import com.zucc.wsq.a31501284.wonderfulwsq.dialog.SelectDateDialog;
import com.zucc.wsq.a31501284.wonderfulwsq.dialog.SelectEventSetDialog;
import com.zucc.wsq.a31501284.wonderfulwsq.task.eventset.LoadEventSetMapTask;
import com.zucc.wsq.a31501284.wonderfulwsq.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddFinanceRecordActivity extends BaseActivity implements View.OnClickListener{

    public static int ADD_FINANCE_RECORD_CANCEL = 1;
    public static int ADD_FINANCE_RECORD_FINISH = 2;
    public static String FINANCE_RECORD_OBJ = "finance.record.obj";

    private TextView tvFinanceType, tvFinanceTime;


    @Override
    protected void bindView() {
        setContentView(R.layout.activity_add_finance_record);
        TextView tvTitle = searchViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.finance_record_title));

        searchViewById(R.id.tvCancel).setOnClickListener(this);
        searchViewById(R.id.tvFinish).setOnClickListener(this);
        searchViewById(R.id.btFinanceTypeClothes).setOnClickListener(this);
        searchViewById(R.id.btFinanceTypeFood).setOnClickListener(this);
        searchViewById(R.id.btFinanceTypeInvest).setOnClickListener(this);
        searchViewById(R.id.btFinanceTypePocketmoney).setOnClickListener(this);
        searchViewById(R.id.btFinanceTypeTraffic).setOnClickListener(this);
        searchViewById(R.id.btFinanceTypeWork).setOnClickListener(this);
        searchViewById(R.id.llFinanceTime).setOnClickListener(this);

        tvFinanceTime = searchViewById(R.id.tvFinanceTime);
        tvFinanceType = searchViewById(R.id.tvFinanceType);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                setResult(ADD_FINANCE_RECORD_CANCEL);
                finish();
                break;
            case R.id.tvFinish:
                addFinanceRecord();
                break;
            case R.id.btFinanceTypeClothes:
                selectFinanceTypeClothes();
                break;
            case R.id.btFinanceTypeFood:
                selectFinanceTypeFood();
                break;
            case R.id.btFinanceTypeTraffic:
                selectFinanceTypeTraffic();
                break;
            case R.id.btFinanceTypeWork:
                selectFinanceTypeWork();
                break;
            case R.id.btFinanceTypeInvest:
                selectFinanceTypeInvest();
                break;
            case R.id.btFinanceTypePocketmoney:
                selectFinanceTypePocketmoney();
                break;
        }

    }

    private void addFinanceRecord() {

    }

    private void selectFinanceTypeClothes() {
        Button btFinanceTypeClothes = (Button) findViewById(R.id.btFinanceTypeClothes);
        Toast.makeText(this, btFinanceTypeClothes.getText(), Toast.LENGTH_SHORT).show();
        String TimeNow=getTime();
        tvFinanceType.setText("衣服支出");
        tvFinanceTime.setText(TimeNow);
    }
    private void selectFinanceTypeFood() {
        Button btFinanceTypeFood = (Button) findViewById(R.id.btFinanceTypeFood);
        Toast.makeText(this, btFinanceTypeFood.getText(), Toast.LENGTH_SHORT).show();
        String TimeNow=getTime();
        tvFinanceType.setText("餐饮支出");
        tvFinanceTime.setText(TimeNow);
    }
    private void selectFinanceTypeTraffic() {
        Button btFinanceTypeTraffic = (Button) findViewById(R.id.btFinanceTypeTraffic);
        Toast.makeText(this, btFinanceTypeTraffic.getText(), Toast.LENGTH_SHORT).show();
        String TimeNow=getTime();
        tvFinanceType.setText("交通支出");
        tvFinanceTime.setText(TimeNow);
    }
    private void selectFinanceTypeWork() {
        Button btFinanceTypeWork = (Button) findViewById(R.id.btFinanceTypeWork);
        Toast.makeText(this, btFinanceTypeWork.getText(), Toast.LENGTH_SHORT).show();
        String TimeNow=getTime();
        tvFinanceType.setText("工作收入");
        tvFinanceTime.setText(TimeNow);
    }
    private void selectFinanceTypeInvest() {
        Button btFinanceTypeInvest = (Button) findViewById(R.id.btFinanceTypeInvest);
        Toast.makeText(this, btFinanceTypeInvest.getText(), Toast.LENGTH_SHORT).show();
        String TimeNow=getTime();
        tvFinanceType.setText("投资收入");
        tvFinanceTime.setText(TimeNow);
    }
    private void selectFinanceTypePocketmoney() {
        Button btFinanceTypePocketmoney = (Button) findViewById(R.id.btFinanceTypePocketmoney);
        Toast.makeText(this, btFinanceTypePocketmoney.getText(), Toast.LENGTH_SHORT).show();
        String TimeNow=getTime();
        tvFinanceType.setText("零花钱");
        tvFinanceTime.setText(TimeNow);
    }
    public String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;

    }
}
