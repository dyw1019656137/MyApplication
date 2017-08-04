package com.example.dyw.myapplication.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.dyw.myapplication.R;

/**
 * Created by dyw on 2017/7/10.
 */

public class FloatWindow {
    private Context context;
    private TextView overlay;
    private WindowManager windowManager;

    public FloatWindow() {

    }

    public void setContext(Context context) {
        this.context = context;

    }

    public TextView getWindowWidget(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        return overlay;
    }
}
