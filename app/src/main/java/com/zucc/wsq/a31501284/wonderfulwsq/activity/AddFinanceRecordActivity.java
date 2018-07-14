package com.zucc.wsq.a31501284.wonderfulwsq.activity;

import android.content.Intent;
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
import com.zucc.wsq.a31501284.wonderfulwsq.litepalData.FinanceRecord;

import java.util.Date;

public class AddFinanceRecordActivity extends BaseActivity implements View.OnClickListener{

    public static int ADD_FINANCE_RECORD_CANCEL = 1;
    public static int ADD_FINANCE_RECORD_FINISH = 2;
    public static String FINANCE_RECORD_OBJ = "finance.record.obj";

    private FinanceRecord financeRecord= new FinanceRecord();

    private EditText etInputPrice;
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

        //初始化 收支类别 时间
        String TimeNow=getTime();
        tvFinanceType.setText("餐饮支出");
        tvFinanceTime.setText(TimeNow);
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
                selectFinanceType("衣服支出","clothes",-1);
                break;
            case R.id.btFinanceTypeFood:
                selectFinanceType("餐饮支出","food",-1);
                break;
            case R.id.btFinanceTypeTraffic:
                selectFinanceType("交通支出","traffic",-1);
                break;
            case R.id.btFinanceTypeWork:
                selectFinanceType("工作收入","work",1);
                break;
            case R.id.btFinanceTypeInvest:
                selectFinanceType("投资收入","invest",1);
                break;
            case R.id.btFinanceTypePocketmoney:
                selectFinanceType("零花钱","pocketmoney",1);
                break;
        }

    }

    private void addFinanceRecord() {
        etInputPrice=(EditText)findViewById(R.id.etInputPrice);
        try {
            //设置数据库表数据
            double d= Double.parseDouble(etInputPrice.getText().toString());
            financeRecord.setFinancePrice(d*financeRecord.getFinanceTypeId());
            //保存数据库表数据
            financeRecord.save();
            Toast.makeText(this,"保存成功啦啦啦",Toast.LENGTH_SHORT).show();

            //返回
            Intent intent = new Intent();
            setResult(ADD_FINANCE_RECORD_FINISH ,intent);
            finish();
        }
        catch (Exception e){
            Toast.makeText(this,"请输入金额!!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectFinanceType(String typeName,String typeImage,int typeId) {
        Toast.makeText(this, typeName, Toast.LENGTH_SHORT).show();
        String timeNow=getTime();
        tvFinanceType.setText(typeName);
        tvFinanceTime.setText(timeNow);

        //设置数据库表数据
        financeRecord.setFinanceTypeName(typeName);
        financeRecord.setFinanceTypeImage(typeImage);
        financeRecord.setFinanceTypeId(typeId);
        financeRecord.setFinanceTime(timeNow);
    }

    public String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;

    }
}
