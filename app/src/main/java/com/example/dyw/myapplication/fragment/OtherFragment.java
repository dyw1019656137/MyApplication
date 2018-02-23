package com.example.dyw.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.activity.MyselfBrowseActivity;
import com.example.dyw.myapplication.activity.MyselfCollectActivity;
import com.example.dyw.myapplication.activity.MyselfPhoneActivity;
import com.example.dyw.myapplication.activity.OtherAboutActivity;
import com.example.dyw.myapplication.activity.OtherFeedbackActivity;

/**
 * Created by dyw on 2017/7/4.
 */
//其他
public class OtherFragment extends BaseFragment{
    View view = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.other_fragment,container,false);

        View other_feedback = view.findViewById(R.id.other_feedback);
        View other_about = view.findViewById(R.id.other_about);
        other_feedback.setOnClickListener(new MyClickListener());
        other_about.setOnClickListener(new MyClickListener());
        return view;
    }
    class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.other_feedback:
                    Intent intent_feedback = new Intent(getActivity(), OtherFeedbackActivity.class);
                    startActivity(intent_feedback);
                    break;
                case R.id.other_about:
                    Intent intent_about = new Intent(getActivity(), OtherAboutActivity.class);
                    startActivity(intent_about);
                    break;
            }
        }
    }
}
