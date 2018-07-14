package com.zucc.wsq.a31501284.wonderfulwsq.fragment;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zucc.wsq.a31501284.wonderfulwsq.R;
import com.zucc.wsq.a31501284.wonderfulwsq.adapter.FinanceAdapter;
import com.zucc.wsq.a31501284.wonderfulwsq.common.base.app.BaseFragment;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.Schedule;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;
import com.zucc.wsq.a31501284.wonderfulwsq.dialog.SelectDateDialog;
import com.zucc.wsq.a31501284.wonderfulwsq.litepalData.FinanceRecord;
import com.zucc.wsq.a31501284.wonderfulwsq.widget.SwipeItemLayout;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by dell on 2018/7/12.
 */
public class financeFragment extends Fragment{

    private FinanceAdapter financeAdapterIncome;
    private FinanceAdapter financeAdapterPay;

    public double balanceMonth=0;
    private ArrayList<FinanceRecord> listIncome = new ArrayList<>();
    private ArrayList<FinanceRecord> listPay = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finance, container, false);

        //支出列表
        RecyclerView recyclerViewIncome = (RecyclerView) view.findViewById((R.id.rvIncomeList));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewIncome.setLayoutManager(layoutManager);
        financeAdapterIncome = new FinanceAdapter(getActivity(), listIncome, getActivity().getPackageName());
        recyclerViewIncome.setAdapter(financeAdapterIncome);
        recyclerViewIncome.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this.getContext()));

        //收入列表
        RecyclerView recyclerViewPay = (RecyclerView) view.findViewById((R.id.rvPayList));
        recyclerViewPay.removeAllViews();
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this.getContext());
        recyclerViewPay.setLayoutManager(layoutManager1);
        financeAdapterPay = new FinanceAdapter(getActivity(), listPay, getActivity().getPackageName());
        recyclerViewPay.setAdapter(financeAdapterPay);
        financeAdapterPay.notifyDataSetChanged();
        recyclerViewPay.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this.getContext()));

        //列表数据初始化
        initList();
//        initTest();

        //本月结余
        TextView tvBalanceMonth=(TextView)view.findViewById(R.id.tvBalanceMonth);
        tvBalanceMonth.setText(balanceMonth+"元");

        return view;
    }

    //数据初始化
    private void initList() {

        //  Connector.getDatabase();
        List<FinanceRecord> detailIncome = DataSupport.select("financeTypeImage","financeTypeName","financePrice","financeTime").where("financePrice>0").find(FinanceRecord.class);
        if(detailIncome!=null) {
            for (FinanceRecord d : detailIncome) {
                balanceMonth=balanceMonth+d.getFinancePrice();
                listIncome.add(d);
            }
        }
        List<FinanceRecord> detailPay = DataSupport.select("financeTypeImage","financeTypeName","financePrice","financeTime").where("financePrice<0").find(FinanceRecord.class);
        if(detailPay!=null) {
            for (FinanceRecord d : detailPay) {
                balanceMonth=balanceMonth+d.getFinancePrice();
                listPay.add(d);
            }
        }
    }
    //数据初始化
    public void initTest() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = sdf.format(date);

        listIncome.add(new FinanceRecord( "work", "工作收入", 2700,str));
        listPay.add(new FinanceRecord("food", "餐饮食品", -200, str));
        listPay.add(new FinanceRecord("clothes", "衣服饰品", -120, str));
        listPay.add(new FinanceRecord("traffic", "行车交通", -330, str));

    }

}

