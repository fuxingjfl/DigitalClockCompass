package time.fx.com.digitalclockcompass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * NAME: Sherry
 * DATE: 2019/5/30
 * 数字时钟罗盘
 */
public class TimeRotating extends View {

    private String[] days = {"\t\t\t\t壹", "\t\t\t\t贰", "\t\t\t\t叁", "\t\t\t\t肆", "\t\t\t\t伍", "\t\t\t\t陆", "\t\t\t\t柒", "\t\t\t\t捌",
            "\t\t\t\t玖", "\t\t\t\t拾", "拾壹", "拾贰", "拾叁", "拾肆", "拾伍", "拾陆", "拾柒", "拾捌", "拾玖", "贰拾", "贰拾壹", "贰拾贰",
            "贰拾叁", "贰拾肆", "贰拾伍", "贰拾陆", "贰拾柒", "贰拾捌", "贰拾玖", "叁拾", "叁拾壹"};
    private String[] hours = {"\t\t壹", "\t\t贰", "\t\t叁", "\t\t肆", "\t\t伍", "\t\t陆", "\t\t柒", "\t\t捌", "\t\t玖", "\t\t拾", "拾壹", "拾贰"};
    private String[] minutes = {"\t\t\t\t壹", "\t\t\t\t贰", "\t\t\t\t叁", "\t\t\t\t肆", "\t\t\t\t伍", "\t\t\t\t陆", "\t\t\t\t柒", "\t\t\t\t捌",
            "\t\t\t\t玖", "\t\t\t\t拾", "拾壹", "拾贰", "拾叁", "拾肆", "拾伍", "拾陆", "拾柒", "拾捌", "拾玖", "贰拾", "贰拾壹", "贰拾贰", "贰拾叁",
            "贰拾肆", "贰拾伍", "贰拾陆", "贰拾柒", "贰拾捌", "贰拾玖", "叁拾", "叁拾壹", "叁拾贰", "叁拾叁", "叁拾肆", "叁拾伍", "叁拾陆", "叁拾柒",
            "叁拾捌", "叁拾玖", "肆拾", "肆拾壹", "肆拾贰", "肆拾叁", "肆拾肆", "肆拾伍", "肆拾陆", "肆拾柒", "肆拾捌", "肆拾玖", "伍拾", "伍拾壹",
            "伍拾贰", "伍拾叁", "伍拾肆", "伍拾伍", "伍拾陆", "伍拾柒", "伍拾捌", "伍拾玖"};
    private String[] months = {"壹", "贰", "仨", "肆", "伍", "陆", "柒", "捌", "玖", "拾", "拾壹", "拾贰"};
    private String[] seconds = minutes;

    private Paint mPaint;
    private float mScale;
    private Matrix matrix = new Matrix();
    private int monthIndex = 0;
    private int dayIndex = 0;
    private int hourIndex = 0;
    private int minuteIndex = 0;
    private int secondIndex = 0;

    public TimeRotating(Context context) {
        super(context);
        initPaint();
    }

    public TimeRotating(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public TimeRotating(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(0);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Calendar instance = Calendar.getInstance();
                Log.e("TAG","instance.get(5) - 1=="+(instance.get(Calendar.HOUR_OF_DAY)));
                monthIndex=instance.get(Calendar.MONTH);
                dayIndex = instance.get(Calendar.DAY_OF_MONTH) - 1;
                hourIndex = instance.get(Calendar.HOUR_OF_DAY) - 1;
                minuteIndex = instance.get(Calendar.MINUTE) - 1;
                secondIndex = instance.get(Calendar.SECOND) - 1;
                postInvalidate();
            }
        }, 0, 16, TimeUnit.MILLISECONDS);


    }

    private int i;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(44);

        mScale = (canvas.getWidth() / 1080f) * 0.5f; //缩放的大小
        drawText(canvas, months, monthIndex, 90); //月份
        drawText(canvas, new String[]{"月"}, 0, 190);
        drawText(canvas, days, dayIndex, 260); //日期
        drawText(canvas, new String[]{"日"}, 0, 400);
        drawText(canvas, hours, hourIndex, 480); //时
        drawText(canvas, new String[]{"时"}, 0, 580);
        drawText(canvas, minutes, minuteIndex, 660); //分
        drawText(canvas, new String[]{"分"}, 0, 790);
        drawText(canvas, seconds, secondIndex, 860); //秒
        drawText(canvas, new String[]{"秒"}, 0, 990);

    }

    private void drawText(Canvas canvas, String[] strArr, int index, float translate) {
        float length = 360.0f / strArr.length; //计算每个字符串的旋转度数
        float f3 = 0.0f - index * length; // 起始角度为0
        float f = f3; //f为下一个字符串旋转的角度，每个字符为360/length度
        int i4 = 0;
        for (String str : strArr) {
            matrix.reset(); //将当前Matrix重置为单位矩阵
            matrix.postTranslate(translate, 0); //post为后乘
            matrix.postRotate(f, 0, 0); //f为下一个字符串旋转的角度
            f += length; //f为下一个字符串旋转的角度，每个字符为360/strArr.length度
            matrix.postTranslate(getWidth() / 2, getHeight() / 2);
            matrix.postScale(mScale, mScale, getWidth() / 2, getHeight() / 2);
            canvas.setMatrix(matrix);
            mPaint.setColor(i4 == index ? Color.WHITE : -7829368);
            canvas.drawText(str, 0, 0, mPaint);
            i4++;
        }
    }

}
