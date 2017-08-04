package com.example.dyw.myapplication.model;

import java.io.Serializable;

/**
 * Created by dyw on 2017/7/18.
 */

/*
* {
    "data": [
        {
            "password": "dadasdsa",
            "headurl": "31212313",
            "id": 7,
            "phonenum": "1232312313",
            "username": "asdffg",
            "info": "dasdasawa"
        },
        {
            "password": "123",
            "headurl": "12312312",
            "id": 1,
            "phonenum": "123456789",
            "username": "dyw",
            "info": "好帅啊"
        }
    ]
}
*
* */
public class UserItem implements Serializable{
    private String id;
    private String password;
    private String phonenum;
    private String username;
    private String info;
    private String headurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }
}
