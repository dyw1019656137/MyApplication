package com.example.dyw.myapplication.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.dyw.myapplication.utils.NetUtil;
import com.example.dyw.myapplication.utils.ToastUtils;

/**
 * Created by Administrator on 2017/12/14.
 */

public class BaseFragment extends Fragment {

    public Context mContext;
    public boolean isNet = true;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;

        if (!NetUtil.isNetworkAvalible(mContext)){
            ToastUtils.show(mContext,"暂无网络，请检查网络!");
            isNet = false;
        }

    }
}
