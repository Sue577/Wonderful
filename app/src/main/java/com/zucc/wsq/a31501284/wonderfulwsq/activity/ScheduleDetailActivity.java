package com.zucc.wsq.a31501284.wonderfulwsq.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.mongodb.DB;
import com.zucc.wsq.a31501284.wonderfulwsq.BitmapToByte;
import com.zucc.wsq.a31501284.wonderfulwsq.SaveSD;
import com.zucc.wsq.a31501284.wonderfulwsq.common.base.app.BaseActivity;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.EventSet;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.Schedule;
import com.zucc.wsq.a31501284.wonderfulwsq.common.listener.OnTaskFinishedListener;
import com.zucc.wsq.a31501284.wonderfulwsq.common.util.ToastUtils;
import com.zucc.wsq.a31501284.wonderfulwsq.dialog.InputLocationDialog;
import com.zucc.wsq.a31501284.wonderfulwsq.dialog.SelectDateDialog;
import com.zucc.wsq.a31501284.wonderfulwsq.dialog.SelectEventSetDialog;
import com.zucc.wsq.a31501284.wonderfulwsq.task.eventset.LoadEventSetMapTask;
import com.zucc.wsq.a31501284.wonderfulwsq.task.schedule.UpdateScheduleTask;
import com.zucc.wsq.a31501284.wonderfulwsq.utils.DateUtils;
import com.zucc.wsq.a31501284.wonderfulwsq.utils.JeekUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import com.zucc.wsq.a31501284.wonderfulwsq.R;

/**
 * Created by dell on 2018/7/11.
 */

public class ScheduleDetailActivity extends BaseActivity implements View.OnClickListener, OnTaskFinishedListener<Map<Integer, EventSet>>, SelectDateDialog.OnSelectDateListener, InputLocationDialog.OnLocationBackListener, SelectEventSetDialog.OnSelectEventSetListener {

    public static int BDLocation_CODE = 1;

    public static int UPDATE_SCHEDULE_CANCEL = 1;
    public static int UPDATE_SCHEDULE_FINISH = 2;
    public static String SCHEDULE_OBJ = "schedule.obj";
    public static String CALENDAR_POSITION = "calendar.position";

    private View vScheduleColor;
    private EditText etScheduleTitle, etScheduleDesc;
    private ImageView ivScheduleEventSetIcon,ivSchedulePhoto;
    private TextView tvScheduleEventSet, tvScheduleTime, tvScheduleLocation,tvScheduleBDLocation;
    private SelectEventSetDialog mSelectEventSetDialog;
    private SelectDateDialog mSelectDateDialog;
    private InputLocationDialog mInputLocationDialog;

    private Map<Integer, EventSet> mEventSetsMap;
    private Schedule mSchedule;
    private int mPosition = -1;

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_schedule_detail);
        TextView tvTitle = searchViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.schedule_event_detail_setting));
        searchViewById(R.id.tvCancel).setOnClickListener(this);
        searchViewById(R.id.tvFinish).setOnClickListener(this);
        searchViewById(R.id.llScheduleEventSet).setOnClickListener(this);
        searchViewById(R.id.llScheduleTime).setOnClickListener(this);
        searchViewById(R.id.llScheduleLocation).setOnClickListener(this);
        searchViewById(R.id.llScheduleBDLocation).setOnClickListener(this);
        searchViewById(R.id.llSchedulePhoto).setOnClickListener(this);
        vScheduleColor = searchViewById(R.id.vScheduleColor);
        ivScheduleEventSetIcon = searchViewById(R.id.ivScheduleEventSetIcon);
        etScheduleTitle = searchViewById(R.id.etScheduleTitle);
        etScheduleDesc = searchViewById(R.id.etScheduleDesc);
        tvScheduleEventSet = searchViewById(R.id.tvScheduleEventSet);
        tvScheduleTime = searchViewById(R.id.tvScheduleTime);
        tvScheduleLocation = searchViewById(R.id.tvScheduleLocation);
        ivSchedulePhoto=searchViewById(R.id.ivSchedulePhoto);
        tvScheduleBDLocation= searchViewById(R.id.tvScheduleBDLocation);
    }

    @Override
    protected void initData() {
        super.initData();
        mEventSetsMap = new HashMap<>();
        mSchedule = (Schedule) getIntent().getSerializableExtra(SCHEDULE_OBJ);
        mPosition = getIntent().getIntExtra(CALENDAR_POSITION, -1);
        new LoadEventSetMapTask(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void bindData() {
        super.bindData();
        setScheduleData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                setResult(UPDATE_SCHEDULE_CANCEL);
                finish();
                break;
            case R.id.tvFinish:
                confirm();
                break;
            case R.id.llScheduleEventSet:
                showSelectEventSetDialog();
                break;
            case R.id.llScheduleTime:
                showSelectDateDialog();
                break;
            case R.id.llScheduleLocation:
                showInputLocationDialog();
                break;
            case R.id.llSchedulePhoto:
                openPhoto();
                break;
            case R.id.llScheduleBDLocation:
                gotoBDLocationActivity();
                break;
        }
    }
    private void gotoBDLocationActivity(){
        startActivityForResult(new Intent(this, BDLocationActivity.class), BDLocation_CODE);

    }
    private void openPhoto(){
        // TODO Auto-generated method stub
        Intent intent = new Intent();// 开启Pictures画面Type设定为image
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /*
                 * 得到新打开Activity关闭后返回的数据，你需要使用系统提供的
                 * startActivityForResult(Intent intent,int
                 * requestCode)方法打开新的Activity
                 */
        startActivityForResult(intent, 1);// 取得相片后返回本画面

    }

    private void confirm() {
        if (etScheduleTitle.getText().length() != 0) {
            mSchedule.setTitle(etScheduleTitle.getText().toString());
            mSchedule.setDesc(etScheduleDesc.getText().toString());
            new UpdateScheduleTask(this, new OnTaskFinishedListener<Boolean>() {
                @Override
                public void onTaskFinished(Boolean data) {
                    setResult(UPDATE_SCHEDULE_FINISH);
                    finish();
                }
            }, mSchedule).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            ToastUtils.showShortToast(this, R.string.schedule_input_content_is_no_null);
        }
    }

    private void showSelectEventSetDialog() {
        if (mSelectEventSetDialog == null) {
            mSelectEventSetDialog = new SelectEventSetDialog(this, this, mSchedule.getEventSetId());
        }
        mSelectEventSetDialog.show();
    }

    private void showSelectDateDialog() {
        if (mSelectDateDialog == null) {
            mSelectDateDialog = new SelectDateDialog(this, this, mSchedule.getYear(), mSchedule.getMonth(), mSchedule.getDay(), mPosition);
        }
        mSelectDateDialog.show();
    }

    private void showInputLocationDialog() {
        if (mInputLocationDialog == null) {
            mInputLocationDialog = new InputLocationDialog(this, this);
        }
        mInputLocationDialog.show();
    }

    private void setScheduleData() {
        vScheduleColor.setBackgroundResource(JeekUtils.getEventSetColor(mSchedule.getColor()));
        ivScheduleEventSetIcon.setImageResource(mSchedule.getEventSetId() == 0 ? R.mipmap.ic_detail_category : R.mipmap.ic_detail_icon);
        etScheduleTitle.setText(mSchedule.getTitle());
        etScheduleDesc.setText(mSchedule.getDesc());
        EventSet current = mEventSetsMap.get(mSchedule.getEventSetId());
        if (current != null) {
            tvScheduleEventSet.setText(current.getName());
        }
        resetDateTimeUi();
        if (TextUtils.isEmpty(mSchedule.getLocation())) {
            tvScheduleLocation.setText(R.string.click_here_select_location);
        } else {
            tvScheduleLocation.setText(mSchedule.getLocation());
        }
        //获取数据库中图片,byte[] -> Bitmap
        if(mSchedule.getPhoto()!=null){
            byte[] photo=mSchedule.getPhoto();
            Bitmap bmpout = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            ivSchedulePhoto.setImageBitmap(bmpout);
        }

    }

    //转换获取的图片（Bitmap）为Drawable
    public Drawable chage_to_drawable(Bitmap bp)
    {
        //因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
        Bitmap bm=bp;
        BitmapDrawable bd= new BitmapDrawable(getResources(), bm);
        return bd;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectEventSetDialog.ADD_EVENT_SET_CODE) {
            if (resultCode == AddEventSetActivity.ADD_EVENT_SET_FINISH) {
                EventSet eventSet = (EventSet) data.getSerializableExtra(AddEventSetActivity.EVENT_SET_OBJ);
                if (eventSet != null) {
                    mSelectEventSetDialog.addEventSet(eventSet);
                    sendBroadcast(new Intent(MainActivity.ADD_EVENT_SET_ACTION).putExtra(AddEventSetActivity.EVENT_SET_OBJ, eventSet));
                }
            }
        }
        if (requestCode == 1) {// 选取图片的返回值
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();// 指向查询结果的第一个位置
                String imgPath = cursor.getString(1); // 图片文件路径
                String imgSize = cursor.getString(2); // 图片大小
                String imgName = cursor.getString(3); // 图片文件名
                BitmapFactory.Options options = new BitmapFactory.Options();

                // 此时把options.inJustDecodeBounds 设回true，即只读边不读内容
                options.inJustDecodeBounds = true;
                // 默认是Bitmap.Config.ARGB_8888
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                try {
                    //此时不会把图片读入内存，只会获取图片宽高等信息
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                    //上面一句和下面的类似
                    //Bitmap bitmap = BitmapFactory.decodeFile(imgPath,options);
                    int heitht = options.outHeight;
                    // 根据需要设置压缩比例
                    int size = heitht / 800;
                    if (size <= 0) {
                        size = 2;
                    }
                   /*inSampleSize表示缩略图大小为原始图片大小的几分之一，
                      即如果这个值为2，则取出的缩略图的宽和高都是原始图片的1/2，
                      图片大小就为原始大小的1/4*/
                    options.inSampleSize = size;
                    // 设置options.inJustDecodeBounds重新设置为false
                    options.inPurgeable = true;// 同时设置才会有效
                    options.inInputShareable = true;//。当系统内存不够时候图片自动被回收
                    options.inJustDecodeBounds = false;
                    //此时图片会按比例压缩后被载入内存中
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                    SaveSD.saveBitmap(imgName,bitmap);//saveBitmap这个是我定义保存到SDcard中的方法
                    ivSchedulePhoto.setImageBitmap(bitmap);
                    imgPath="/sdcard/"+imgName;

                    //将图片从Bitmap变为二进制流,保存到数据库中
                    mSchedule.setPhoto(BitmapToByte.saveBitmap(bitmap));

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        if(requestCode==BDLocation_CODE){
            if(resultCode== BDLocationActivity.BDLocation_FINISH){
                tvScheduleBDLocation.setText("浙江省杭州市拱墅区湖州街");
                tvScheduleLocation.setText("浙江省杭州市拱墅区湖州街");
                mSchedule.setLocation(tvScheduleLocation.getText().toString());
            }
        }

    }

    @Override
    public void onTaskFinished(Map<Integer, EventSet> data) {
        mEventSetsMap = data;
        EventSet eventSet = new EventSet();
        eventSet.setName(getString(R.string.menu_no_category));
        mEventSetsMap.put(eventSet.getId(), eventSet);
        EventSet current = mEventSetsMap.get(mSchedule.getEventSetId());
        if (current != null) {
            tvScheduleEventSet.setText(current.getName());
        }
    }

    @Override
    public void onSelectDate(int year, int month, int day, long time, int position) {
        mSchedule.setYear(year);
        mSchedule.setMonth(month);
        mSchedule.setDay(day);
        mSchedule.setTime(time);
        mPosition = position;
        resetDateTimeUi();
    }

    private void resetDateTimeUi() {
        if (mSchedule.getTime() == 0) {
            if (mSchedule.getYear() != 0) {
                tvScheduleTime.setText(String.format(getString(R.string.date_format_no_time), mSchedule.getYear(), mSchedule.getMonth() + 1, mSchedule.getDay()));
            } else {
                tvScheduleTime.setText(R.string.click_here_select_date);
            }
        } else {
            tvScheduleTime.setText(DateUtils.timeStamp2Date(mSchedule.getTime(), getString(R.string.date_format)));
        }
    }

    @Override
    public void onLocationBack(String text) {
        mSchedule.setLocation(text);
        if (TextUtils.isEmpty(mSchedule.getLocation())) {
            tvScheduleLocation.setText(R.string.click_here_select_location);
        } else {
            tvScheduleLocation.setText(mSchedule.getLocation());
        }
    }

    @Override
    public void onSelectEventSet(EventSet eventSet) {
        mSchedule.setColor(eventSet.getColor());
        mSchedule.setEventSetId(eventSet.getId());
        vScheduleColor.setBackgroundResource(JeekUtils.getEventSetColor(mSchedule.getColor()));
        tvScheduleEventSet.setText(eventSet.getName());
        ivScheduleEventSetIcon.setImageResource(mSchedule.getEventSetId() == 0 ? R.mipmap.ic_detail_category : R.mipmap.ic_detail_icon);
    }
}
