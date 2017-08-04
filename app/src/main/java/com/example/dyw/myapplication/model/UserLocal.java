package com.example.dyw.myapplication.model;

import com.example.dyw.myapplication.DB.DBSupport;

/**
 * Created by dyw on 2017/7/19.
 */

public class UserLocal extends DBSupport<UserLocal> {
    private String userid;
    private String username;
    private String password;
    private String headurl;
    private String userinfo;
    private String phonenum;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(String userinfo) {
        this.userinfo = userinfo;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }


}
