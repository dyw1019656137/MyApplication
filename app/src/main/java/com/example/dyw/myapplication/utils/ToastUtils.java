package com.example.dyw.myapplication.utils;
/**
 * Created by dyw on 2017/8/15.
 */

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 */

public class ToastUtils {
    private static Toast mToast = null;    //全局唯一的Toast

    private ToastUtils()
    {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * @param context
     * @param message
     */
    public static void show(final Context context, final CharSequence message)
    {
        if (context instanceof Activity){
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null)
                    {
                        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                    }else {
                        mToast.setText(message);
                    }
                }
            });
        }else {
            if (mToast == null)
            {
                mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            }else {
                mToast.setText(message);
            }
        }
        mToast.show();

    }
}
