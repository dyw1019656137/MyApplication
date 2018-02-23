package com.example.dyw.myapplication.model;

import com.example.dyw.myapplication.DB.DBSupport;

/**
 * Created by dyw on 2017/7/19.
 */

public class UserLocal extends DBSupport<UserLocal> {
    private int userid;
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
