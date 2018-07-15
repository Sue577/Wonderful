package com.zucc.wsq.a31501284.wonderfulwsq.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.zucc.wsq.a31501284.wonderfulwsq.R;
import com.zucc.wsq.a31501284.wonderfulwsq.adapter.EventSetAdapter;
import com.zucc.wsq.a31501284.wonderfulwsq.common.base.app.BaseActivity;
import com.zucc.wsq.a31501284.wonderfulwsq.common.base.app.BaseFragment;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.EventSet;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;
import com.zucc.wsq.a31501284.wonderfulwsq.fragment.EventSetFragment;
import com.zucc.wsq.a31501284.wonderfulwsq.fragment.ScheduleFragment;
import com.zucc.wsq.a31501284.wonderfulwsq.fragment.financeFragment;
import com.zucc.wsq.a31501284.wonderfulwsq.litepalData.FinanceRecord;
import com.zucc.wsq.a31501284.wonderfulwsq.task.eventset.LoadEventSetTask;

import org.bson.Document;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

/**
 * Created by dell on 2018/7/11.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, OnTaskFinishedListener<List<EventSet>> {

    //云端MongoDB数据库
    MongoClientURI uri = new MongoClientURI( "mongodb://123.206.127.199:27017/WonderfulWSQ" );
    private List<String> financeRecordJSON = new ArrayList<>();


    public static int ADD_EVENT_SET_CODE = 1;
    public static String ADD_EVENT_SET_ACTION = "action.add.event.set";
    public static int ADD_FINANCE_RECORD_CODE = 2;
    public static String ADD_FINANCE_RECORD_ACTION = "action.add.finance.record";

    private DrawerLayout dlMain;
    private LinearLayout llTitleDate;
    private TextView tvTitleMonth, tvTitleDay, tvTitle;
    private RecyclerView rvMenuEventSetList;

    private EventSetAdapter mEventSetAdapter;
    private List<EventSet> mEventSets;

    private BaseFragment mScheduleFragment, mEventSetFragment;
    private financeFragment mFinanceFragment;
    private EventSet mCurrentEventSet;
    private AddEventSetBroadcastReceiver mAddEventSetBroadcastReceiver;

    private long[] mNotes = new long[2];
    private String[] mMonthText;
    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_main);
        dlMain = searchViewById(R.id.dlMain);
        llTitleDate = searchViewById(R.id.llTitleDate);
        tvTitleMonth = searchViewById(R.id.tvTitleMonth);
        tvTitleDay = searchViewById(R.id.tvTitleDay);
        tvTitle = searchViewById(R.id.tvTitle);
        rvMenuEventSetList = searchViewById(R.id.rvMenuEventSetList);
        searchViewById(R.id.ivMainMenu).setOnClickListener(this);
        searchViewById(R.id.llMenuSchedule).setOnClickListener(this);
        searchViewById(R.id.llMenuNoCategory).setOnClickListener(this);
        searchViewById(R.id.tvMenuAddEventSet).setOnClickListener(this);
        searchViewById(R.id.llMenuFinance).setOnClickListener(this);
        searchViewById(R.id.llMenuNetworkDelete).setOnClickListener(this);
        searchViewById(R.id.llMenuNetworkDownload).setOnClickListener(this);
        searchViewById(R.id.llMenuNetworkUpload).setOnClickListener(this);
        initUi();
        initEventSetList();
        gotoScheduleFragment();
        initBroadcastReceiver();

        //初始化，将数据变成Json格式，现在有收支记录，日程记录先不弄
        initJson();
    }

    private void initBroadcastReceiver() {
        if (mAddEventSetBroadcastReceiver == null) {
            mAddEventSetBroadcastReceiver = new AddEventSetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ADD_EVENT_SET_ACTION);
            registerReceiver(mAddEventSetBroadcastReceiver, filter);
        }
    }

    private void initEventSetList() {
        mEventSets = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenuEventSetList.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        rvMenuEventSetList.setItemAnimator(itemAnimator);
        mEventSetAdapter = new EventSetAdapter(this, mEventSets);
        rvMenuEventSetList.setAdapter(mEventSetAdapter);
    }

    private void initUi() {
        dlMain.setScrimColor(Color.TRANSPARENT);
        mMonthText = getResources().getStringArray(R.array.calendar_month);
        llTitleDate.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        tvTitleMonth.setText(mMonthText[Calendar.getInstance().get(Calendar.MONTH)]);
        tvTitleDay.setText(getString(R.string.calendar_today));
        if (Build.VERSION.SDK_INT < 19) {
            TextView tvMenuTitle = searchViewById(R.id.tvMenuTitle);
            tvMenuTitle.setGravity(Gravity.CENTER_VERTICAL);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        resetMainTitleDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
        new LoadEventSetTask(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void resetMainTitleDate(int year, int month, int day) {
        llTitleDate.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        if (year == calendar.get(Calendar.YEAR) &&
                month == calendar.get(Calendar.MONTH) &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            tvTitleMonth.setText(mMonthText[month]);
            tvTitleDay.setText(getString(R.string.calendar_today));
        } else {
            if (year == calendar.get(Calendar.YEAR)) {
                tvTitleMonth.setText(mMonthText[month]);
            } else {
                tvTitleMonth.setText(String.format("%s%s", String.format(getString(R.string.calendar_year), year),
                        mMonthText[month]));
            }
            tvTitleDay.setText(String.format(getString(R.string.calendar_day), day));
        }
        setCurrentSelectDate(year, month, day);
    }

    private void resetTitleText(String name) {
        llTitleDate.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(name);
    }

    private void setCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectYear = year;
        mCurrentSelectMonth = month;
        mCurrentSelectDay = day;
    }

    //菜单点击事件+收支中按钮
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMainMenu:
                dlMain.openDrawer(Gravity.START);
                break;
            case R.id.llMenuSchedule:
                gotoScheduleFragment();
                break;
            case R.id.llMenuNoCategory:
                mCurrentEventSet = new EventSet();
                mCurrentEventSet.setName(getString(R.string.menu_no_category));
                gotoEventSetFragment(mCurrentEventSet);
                break;
            case R.id.tvMenuAddEventSet:
                gotoAddEventSetActivity();
                break;
            case R.id.llMenuFinance:
                gotoFinanceFragment();
                break;
            case R.id.btAddFinanceRecord:
                gotoAddFinanceRecordActivity();
                break;
            case R.id.btRefresh:
                Toast.makeText(MainActivity.this, "界面已刷新~", Toast.LENGTH_SHORT).show();
                gotoFinanceFragment();
                break;
            case R.id.llMenuNetworkUpload:
                Toast.makeText(MainActivity.this, "数据已备份~", Toast.LENGTH_SHORT).show();
                //云端数据库操作 插入数据 收支记录
                insertFinanceRecordInMongo();
                break;
            case R.id.llMenuNetworkDownload:
                Toast.makeText(MainActivity.this, "网络备份已下载~", Toast.LENGTH_SHORT).show();
                //云端数据库操作 下载所有数据 收支记录
                getALLFinanceRecordFromMongo();
                break;
            case R.id.llMenuNetworkDelete:
                Toast.makeText(MainActivity.this, "网络备份已删除~", Toast.LENGTH_SHORT).show();
                //云端数据库操作 删除所有数据 收支记录
                deleteAllFinanceRecordInMongo();
                break;
        }
    }


    //切换 日历fragment
    private void gotoScheduleFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);

        if (mScheduleFragment == null) {
            mScheduleFragment = ScheduleFragment.getInstance();
            ft.add(R.id.flMainContainer, mScheduleFragment);
        }
        if (mEventSetFragment != null)
            ft.hide(mEventSetFragment);
        if (mFinanceFragment != null)
            ft.hide(mFinanceFragment);
        ft.show(mScheduleFragment);
        ft.commit();

        llTitleDate.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        dlMain.closeDrawer(Gravity.START);
    }

    //切换 收支Fragment
    private void gotoFinanceFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);

        if(mFinanceFragment==null){
            mFinanceFragment=new financeFragment();
            ft.add(R.id.flMainContainer,mFinanceFragment);
        }else {
            //刷新，不叠加页面，先移除，再加回
            ft.remove(mFinanceFragment);
            mFinanceFragment=new financeFragment();
            ft.add(R.id.flMainContainer,mFinanceFragment);
        }
        if (mEventSetFragment != null)
            ft.hide(mEventSetFragment);
        ft.hide(mScheduleFragment);
        ft.show(mFinanceFragment);

        ft.commit();
        resetTitleText("收支情况");
        dlMain.closeDrawer(Gravity.START);

        //更新了收支记录，要将数据变成Json格式
        initJson();

    }

    //切换 事件集Fragment
    public void gotoEventSetFragment(EventSet eventSet) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);

        if (mCurrentEventSet != eventSet || eventSet.getId() == 0) {
            if (mEventSetFragment != null)
                ft.remove(mEventSetFragment);
            mEventSetFragment = EventSetFragment.getInstance(eventSet);
            ft.add(R.id.flMainContainer, mEventSetFragment);
        }
        if (mFinanceFragment != null)
            ft.hide(mFinanceFragment);
        ft.hide(mScheduleFragment);
        ft.show(mEventSetFragment);

        ft.commit();
        resetTitleText(eventSet.getName());
        dlMain.closeDrawer(Gravity.START);
        mCurrentEventSet = eventSet;
    }

    //跳转 添加事件集Activity
    private void gotoAddEventSetActivity() {
        startActivityForResult(new Intent(this, AddEventSetActivity.class), ADD_EVENT_SET_CODE);
    }

    //跳转 添加收支记录Activity
    public void gotoAddFinanceRecordActivity() {
        Toast.makeText(MainActivity.this, "狗子来记一笔吧", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(this, AddFinanceRecordActivity.class), ADD_FINANCE_RECORD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EVENT_SET_CODE) {
            if (resultCode == AddEventSetActivity.ADD_EVENT_SET_FINISH) {
                EventSet eventSet = (EventSet) data.getSerializableExtra(AddEventSetActivity.EVENT_SET_OBJ);
                if (eventSet != null)
                    mEventSetAdapter.insertItem(eventSet);
            }
        }
        else if(requestCode == ADD_FINANCE_RECORD_CODE){
            if (resultCode == AddFinanceRecordActivity.ADD_FINANCE_RECORD_FINISH){
                gotoFinanceFragment();
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (dlMain.isDrawerOpen(Gravity.START)) {
            dlMain.closeDrawer(Gravity.START);
        } else {
            System.arraycopy(mNotes, 1, mNotes, 0, mNotes.length - 1);
            mNotes[mNotes.length - 1] = SystemClock.uptimeMillis();
            if (SystemClock.uptimeMillis() - mNotes[0] < 1000) {
                finish();
            } else {
                Toast.makeText(this, getString(R.string.exit_app_hint), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mAddEventSetBroadcastReceiver != null) {
            unregisterReceiver(mAddEventSetBroadcastReceiver);
            mAddEventSetBroadcastReceiver = null;
        }
        super.onDestroy();
    }

    @Override
    public void onTaskFinished(List<EventSet> data) {
        mEventSetAdapter.changeAllData(data);
    }

    private class AddEventSetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ADD_EVENT_SET_ACTION.equals(intent.getAction())) {
                EventSet eventSet = (EventSet) intent.getSerializableExtra(AddEventSetActivity.EVENT_SET_OBJ);
                if (eventSet != null)
                    mEventSetAdapter.insertItem(eventSet);
            }
        }
    }

    //Json初始化
    private void initJson() {
        Gson gson = new Gson();
        financeRecordJSON = new ArrayList<>();
        //收支记录
        List<FinanceRecord> detail = DataSupport.select("financeTypeImage","financeTypeName","financePrice","financeTime").find(FinanceRecord.class);
        if(detail!=null) {
            for (FinanceRecord d : detail) {
                financeRecordJSON.add(gson.toJson(d));
            }
        }
        //日程记录
    }
    //删除云端数据库中所有收支记录
    private void deleteAllFinanceRecordInMongo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    MongoClient mongoClient = new MongoClient(uri);
                    MongoDatabase db = mongoClient.getDatabase(uri.getDatabase());
                    //数据库中的对应collection 相当于表名
                    MongoCollection<Document> collection= db.getCollection("financeRecord");
                    collection.drop();
                    mongoClient.close();
                }finally {

                }
            }
        }).start();
    }
    //获得云端数据库中所有收支记录
    private void getALLFinanceRecordFromMongo(){
        new Thread(new Runnable() {
            @Override
            public void run() {



                MongoClient mongoClient = new MongoClient(uri);
                MongoDatabase db = mongoClient.getDatabase(uri.getDatabase());
                //数据库中的对应collection 相当于表名
                MongoCollection<Document> collection= db.getCollection("financeRecord");
                //MongoCursor<Document> cursor = collection.find().projection(eq("_id")).iterator();
                MongoCursor<Document> cursor = collection.find().projection(fields(include("financeTypeImage","financeTypeName","financePrice","financeTime"), excludeId())).iterator();
                try {
                    while (cursor.hasNext()) {
                        String json = cursor.next().toJson();
                        FinanceRecord data= new FinanceRecord();
                        Gson gson = new Gson();
                        data=gson.fromJson(json, FinanceRecord.class);
                        //    DataSupport.count().find(Data.class);

                        //获取litepal数据库中收支记录
                        int flag=1;
                        List<FinanceRecord> detailFinance = DataSupport.select("financeTime").find(FinanceRecord.class);
                        if(detailFinance!=null) {
                            for (FinanceRecord d : detailFinance) {
                                String time=d.getFinanceTime();
                                if(time.equals(data.getFinanceTime())){//通过时间来判断是否有重复
                                    flag=0;
                                }
                            }
                        }
                        if(flag==1){ //该记录没有重复 可以存入litepal数据库
                            data.save();
                        }

                    }

                } finally {
                    cursor.close();
                    mongoClient.close();
                }
            }
        }).start();
    }

    //在云端数据库中插入收支记录
    private void insertFinanceRecordInMongo(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    MongoClient mongoClient = new MongoClient(uri);
                    MongoDatabase db = mongoClient.getDatabase(uri.getDatabase());
                    //数据库中的对应collection 相当于表名
                    MongoCollection<Document> collection= db.getCollection("financeRecord");
                    //插入一条记录
                    //collection.insertOne(Document.parse("{\"name\":\"Alexia\",\"age\":\"23\"}"));
                    //插入多条记录
                    if(financeRecordJSON!=null) {
                        for (String json : financeRecordJSON) {

                            collection.insertOne(Document.parse(json));
                        }
                    }
                    mongoClient.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}