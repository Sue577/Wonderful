package com.zucc.wsq.a31501284.wonderfulwsq.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zucc.wsq.a31501284.wonderfulwsq.R;
import com.zucc.wsq.a31501284.wonderfulwsq.utils.ParseUtil;

import java.text.DecimalFormat;

/**
 * Created by dell on 2018/7/12.
 */
public class PieChart extends View {
    private Paint piePaint;
    private Paint innerPaint;
    private Paint textPaint;
    private Paint imgPaint;
    private float[] datas;
    private String[] colors = new String[]{
            "#7dd196","#f8805b",
    };
    private float start;
    private float centerX;
    private float centerY;
    private ParseUtil parseUtil;
    private static DecimalFormat df = new DecimalFormat(".00");
    public PieChart(Context context) {
        super(context);
        parseUtil = new ParseUtil(context);
        init();
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseUtil = new ParseUtil(context);
        init();
    }

    private void init() {
        piePaint = new Paint();//画扇形的画笔
        piePaint.setAntiAlias(true);
        piePaint.setStyle(Paint.Style.FILL);
        innerPaint = new Paint();//画内圆的画笔
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setColor(Color.WHITE);
        textPaint = new Paint();//画文本的画笔
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        imgPaint = new Paint();//画位图的画笔
        imgPaint.setAntiAlias(true);
        imgPaint.setStyle(Paint.Style.FILL);
        start = -90;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
        * 有数据时的情况
        * */
        if(datas != null) {
            float sum = sumOfData();//计算传入的数据总和
            start = -90;//从-90度开始绘制扇形
            int width = getWidth();//屏幕宽度
            int pieWidth = width - parseUtil.dp2px(160);//饼形图宽度
            centerX = width/2;//饼形图中心x坐标
            centerY = parseUtil.dp2px(48)+pieWidth/2;//饼形图中心y坐标
            /*
            * 创建绘制扇形所需要的RectF，设置的上下左右边界
            * */
            RectF recf = new RectF();
            recf.left = parseUtil.dp2px(80);//左边界x坐标
            recf.top = parseUtil.dp2px(48);//上边界y坐标
            recf.right = parseUtil.dp2px(80) + pieWidth;//右边界x坐标
            recf.bottom = parseUtil.dp2px(48) + pieWidth;//下边界y坐标

            for(int i = 0; i < datas.length; i++){
                piePaint.setColor(Color.parseColor(colors[i]));
                float angle = datas[i]/sum*360;//通过当前数据与数据总和的比例计算出扇形的角度
                /*
                * 绘制扇形，上面设置的recf确定了扇形的圆心及半径
                * */
                canvas.drawArc(recf, start, angle, true, piePaint);
                start += angle;//下一次扇形绘制的起始角度
            }
            /*
            * 上面循环绘制的一个个扇形最终会组成一个实心圆，以此圆圆心画一个白色的小圆就可以实现圆环的效果
            * */
            canvas.drawCircle(centerX, centerY, pieWidth/2-parseUtil.dp2px(36), innerPaint);

            textPaint.setColor(getResources().getColor(R.color.black));
            textPaint.setTextSize(parseUtil.sp2px(20));//标题大小
            canvas.drawText("收入"+df.format(datas[0]), centerX+parseUtil.dp2px(100), centerY, textPaint);

            textPaint.setColor(getResources().getColor(R.color.black));
            textPaint.setTextSize(parseUtil.sp2px(20));//标题大小
            canvas.drawText("支出"+df.format(datas[1]), centerX-parseUtil.dp2px(100), centerY, textPaint);

            textPaint.setColor(getResources().getColor(R.color.gray));
            textPaint.setTextSize(parseUtil.sp2px(14));
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("本月结余", centerX, centerY-parseUtil.dp2px(12), textPaint);
            textPaint.setColor(getResources().getColor(R.color.red));
            textPaint.setTextSize(parseUtil.sp2px(18));
            canvas.drawText(df.format(datas[0]-datas[1]), centerX, centerY+parseUtil.dp2px(12), textPaint);
        }
        /*
        * 无数据时的情况
        * */
        else{
            Bitmap noData = BitmapFactory.decodeResource(getResources(),
                    getResources().getIdentifier("no_data",
                            "drawable", getContext().getPackageName()));
            Rect mDestRect = new Rect((getWidth()-parseUtil.dp2px(56))/2, parseUtil.dp2px(106),
                    getWidth()/2+parseUtil.dp2px(28), parseUtil.dp2px(182));
            canvas.drawBitmap(noData, null, mDestRect, imgPaint);
            textPaint.setColor(getResources().getColor(R.color.gray));
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(parseUtil.sp2px(14));
            canvas.drawText("无数据", getWidth()/2, parseUtil.dp2px(206), textPaint);
        }
    }

    private float sumOfData(){
        float sum = 0;
        for(int i = 0; i < datas.length; i++){
            sum += datas[i];
        }
        return sum;
    }

    public void setDatas(float[] datas) {
        this.datas = datas;
    }
}
