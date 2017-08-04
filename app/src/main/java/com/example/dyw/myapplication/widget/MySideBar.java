package com.example.dyw.myapplication.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.dyw.myapplication.R;

/**
 * Created by dyw on 2017/7/10.
 */

public class MySideBar extends View{
    OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 按住改变背景色
    private boolean showBkg;
    public static String[] b = { "热门", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };
    /** 被选中位置 */
    int choose = -1;
    private Paint paint = new Paint();

    public MySideBar(Context context) {
        super(context);
        paint.setTextSize(22);
    }

    public MySideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint.setTextSize(22);
    }

    public MySideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setTextSize(22);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBkg) {
            //noinspection ResourceAsColor
            canvas.drawColor(R.color.other);
        }

        float height = getHeight();
        float width = getWidth();
        // 计算单个字母高度
        float singleHeight = height / (float) (b.length);
        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.BLACK);
            if (i == choose) {
                // 选中的颜色
                paint.setColor(Color.parseColor("#3399ff"));
                // 加粗
                paint.setFakeBoldText(true);
            }
            // 设置文本坐标
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;

            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("enter touch event-------------------------->");
        int action = event.getAction();
        float y = event.getY();
        final int oldChoose = choose;
        final int c = (int) (y / getHeight() * b.length);
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onTouchingLetterChanged(c);
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onTouchingLetterChanged(c);
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                invalidate();
                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(int s);
    }

}
