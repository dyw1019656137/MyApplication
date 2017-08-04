package com.example.dyw.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dyw.myapplication.R;

/**
 * Created by dyw on 2017/7/4.
 */
//关于我们
public class AboutFragment extends Fragment{
    View view =null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.other_about_fragment,container,false);
        return view;
    }
}
